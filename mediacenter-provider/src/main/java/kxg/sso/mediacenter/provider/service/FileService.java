package kxg.sso.mediacenter.provider.service;


import kxg.sso.mediacenter.response.UpFileResponse;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;

/**
 * 要写注释呀
 */
public interface FileService {
    UpFileResponse uploadFile(MultipartFile file, HttpServletRequest request,String remark) ;
    UpFileResponse uploadByUrl(String url,String remark );
}
