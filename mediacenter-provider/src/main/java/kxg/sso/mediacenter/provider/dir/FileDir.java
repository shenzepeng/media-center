package kxg.sso.mediacenter.provider.dir;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;

/**
 * 要写注释呀
 */
@Component
public class FileDir {
    private  String IMG_FILE_BOX;

    @SneakyThrows
    @PostConstruct
    public void init(){
        ///Users/mac/iss/ocr
        File file = new File("");
        String filePath = file.getCanonicalPath();
        File imgPath = new File(filePath+"/imgs");
        if (!imgPath.exists()) { //用来测试此路径名表示的文件或目录是否存在
            imgPath.mkdirs();
        }
        IMG_FILE_BOX=filePath+"/imgs";
    }

    public String getFileDir(){
        return this.IMG_FILE_BOX;
    }
}
