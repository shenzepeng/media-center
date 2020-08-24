package kxg.sso.mediacenter.provider.service.impl;

import kxg.sso.mediacenter.provider.dao.FileDao;
import kxg.sso.mediacenter.provider.handler.OSSClientUtil;
import kxg.sso.mediacenter.provider.pojo.File;
import kxg.sso.mediacenter.provider.service.FileService;
import kxg.sso.mediacenter.provider.utils.MD5Utils;
import kxg.sso.mediacenter.response.UpFileResponse;

import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.util.Date;

/**
 * 要写注释呀
 */
@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private OSSClientUtil ossClient;
    @Autowired
    private FileDao fileDao;


    @SneakyThrows
    @Override
    public UpFileResponse uploadFile(MultipartFile file, HttpServletRequest request,String remark) {
        if (file.isEmpty()){
            throw new RuntimeException("图像不能为空");
        }
        UpFileResponse fileUrl=new UpFileResponse();
        String name = ossClient.uploadImg2Oss(file);
        String imgUrlEachOne = ossClient.getImgUrl(name);
        //将http转化为https
        String httpToHttps = imgUrlEachOne.replaceAll("http://", "https://");
        File f=new File();
        f.setFileUrl(httpToHttps);
        f.setRemark(remark);
        f.setMd5(MD5Utils.getMD5String(file.getBytes()));
        fileDao.addFile(f);
        BeanUtils.copyProperties(fileUrl,f);
        return fileUrl;
    }

}
