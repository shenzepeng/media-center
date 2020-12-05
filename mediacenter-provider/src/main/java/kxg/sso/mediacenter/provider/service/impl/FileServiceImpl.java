package kxg.sso.mediacenter.provider.service.impl;

import kxg.sso.mediacenter.provider.dao.FileDao;
import kxg.sso.mediacenter.provider.dir.FileDir;
import kxg.sso.mediacenter.provider.handler.OSSClientUtil;
import kxg.sso.mediacenter.provider.pojo.File;
import kxg.sso.mediacenter.provider.service.FileService;
import kxg.sso.mediacenter.provider.utils.MD5Utils;
import kxg.sso.mediacenter.provider.utils.VerifyFileTypeUtils;
import kxg.sso.mediacenter.response.UpFileResponse;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 要写注释呀
 */
@Slf4j
@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private OSSClientUtil ossClient;
    @Autowired
    private FileDao fileDao;
    @Autowired
    private FileDir fileDir;
    @Autowired
    private MailService mailService;
    @SneakyThrows
    @Override
    public UpFileResponse uploadFile(MultipartFile file, HttpServletRequest request, String remark) {
        if (file.isEmpty()) {
            throw new RuntimeException("file can not be null");
        }
        UpFileResponse fileUrl = new UpFileResponse();
        String md5String = MD5Utils.getMD5String(file.getBytes());
        List<File> fileByMd5 = fileDao.findFileByMd5(md5String);
        if (!CollectionUtils.isEmpty(fileByMd5)) {
            fileUrl.setCreateTime(fileByMd5.get(0).getCreateTime());
            fileUrl.setUrl(fileByMd5.get(0).getFileUrl().split("\\?")[0]);
            return fileUrl;
        }
        String name = ossClient.uploadImg2Oss(file);
        String imgUrlEachOne = ossClient.getImgUrl(name);
        //将http转化为https
        String httpToHttps = imgUrlEachOne.replaceAll("http://", "https://");
        File f = new File();
        f.setFileUrl(httpToHttps);
        f.setRemark(remark);
        f.setMd5(md5String);
        fileDao.addFile(f);
        fileUrl.setCreateTime(new Date());
        fileUrl.setUrl(httpToHttps.split("\\?")[0]);
        return fileUrl;
    }


    @Override
    public UpFileResponse uploadByUrl(String url, String remark) {
        try {
            UpFileResponse fileUrl = new UpFileResponse();
            String parseSuffix = parseXSXC(url);
            String fileName = UUID.randomUUID().toString() + "." + parseSuffix;
            String imagePath = fileDir.getFileDir() + "/" + fileName;
            java.io.File localFile = new java.io.File(imagePath);
            log.info("fileName {}", fileName);
            // 生成的图片位置
            FileUtils.copyURLToFile(new URL(url), localFile, 200, 2000);
            String md5Hex = DigestUtils.md5Hex(new FileInputStream(imagePath));
            log.info("imagePath {},md5 {}", imagePath, md5Hex);
            List<File> fileByMd5 = fileDao.findFileByMd5(md5Hex);
            if (!CollectionUtils.isEmpty(fileByMd5)) {
                fileUrl.setCreateTime(fileByMd5.get(0).getCreateTime());
                fileUrl.setUrl(fileByMd5.get(0).getFileUrl().split("\\?")[0]);
                deleteFile(imagePath);
                return fileUrl;
            }
            //对文件进行修复，有的文件没有后缀，有的胡写，通过文件头还原真正的文件
            String fileType = VerifyFileTypeUtils.getFileType(localFile);
            log.info("fileType {}",fileType);
            //将文件上传到阿里云
            //将文件上传到文件服务器
            String name = ossClient.uploadImg2Oss(localFile, UUID.randomUUID().toString()+"."+fileType);
            String imgUrlEachOne = ossClient.getImgUrl(name);
            log.info("imgUrlEachOne {}", imgUrlEachOne);
            //将http转化为https
            String httpToHttps = imgUrlEachOne.replaceAll("http://", "https://");
            File f = new File();
            f.setFileUrl(httpToHttps);
            f.setRemark(remark);
            f.setMd5(md5Hex);
            fileDao.addFile(f);
            fileUrl.setCreateTime(new Date());
            fileUrl.setUrl(httpToHttps.split("\\?")[0]);
            UpFileResponse upFileResponse = new UpFileResponse();
            upFileResponse.setUrl(httpToHttps.split("\\?")[0]);
            upFileResponse.setCreateTime(new Date());
            deleteFile(imagePath);
            return upFileResponse;
        } catch (Throwable e) {
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(()->{
                List<String> recivers = mailService.getRecivers();
                mailService.sent(recivers,"文件上传错误",e.toString());
            });
            throw new RuntimeException(e);
        }


    }

    /**
     * 通过网络获取图片
     *
     * @param url
     * @return
     */
    public static BufferedImage getUrlByBufferedImage(String url) {
        try {
            URL urlObj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
            // 连接超时
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setConnectTimeout(25000);
            // 读取超时 --服务器响应比较慢,增大时间
            conn.setReadTimeout(25000);
            conn.setRequestMethod("GET");
            conn.addRequestProperty("Accept-Language", "zh-cn");
            conn.addRequestProperty("Content-type", "image/jpeg");
            conn.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727)");
            conn.connect();
            BufferedImage bufImg = ImageIO.read(conn.getInputStream());
            conn.disconnect();
            return bufImg;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    final static Pattern pattern = Pattern.compile("\\S*[?]\\S*");

    /**
     * 获取链接的后缀名
     *
     * @return
     */
    private static String parseSuffix(String url) {

        Matcher matcher = pattern.matcher(url);

        String[] spUrl = url.toString().split("/");
        int len = spUrl.length;
        String endUrl = spUrl[len - 1];

        if (matcher.find()) {
            String[] spEndUrl = endUrl.split("\\?");

            return spEndUrl[0].split("\\.")[1];
        }
        return endUrl.split("\\.")[1];
    }

    private static String parseXSXC(String url) {
        String[] spUrl = url.toString().split("\\?");
        String[] split = spUrl[0].split("\\.");
        return split[split.length - 1];
    }

    private void deleteFile(String filePath) {
        /*
         * 删除当前目录下的test.txt文件
         */
        java.io.File file = new java.io.File(filePath);
        if (file.exists()) {
            file.delete();
        }
    }

}
