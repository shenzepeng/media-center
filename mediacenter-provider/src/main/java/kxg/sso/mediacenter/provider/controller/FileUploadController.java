package kxg.sso.mediacenter.provider.controller;


import kxg.sso.mediacenter.provider.common.SzpJsonResult;
import kxg.sso.mediacenter.provider.handler.CheckRemarkHandler;
import kxg.sso.mediacenter.provider.service.FileService;
import kxg.sso.mediacenter.response.UpFileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: szp
 * @Date: 2019-09-29 14:53
 * @Description: 沈泽鹏写点注释吧
 */
@RestController
@RequestMapping("file")
public class FileUploadController {
    @Autowired
    private FileService ossService;
    @Autowired
    private CheckRemarkHandler checkRemarkHandler;
     @PostMapping("upload")
    public SzpJsonResult<UpFileResponse> uploadImg(HttpServletRequest request, MultipartFile file,String remark) {
       if (checkRemarkHandler.findRemarkIsExist(remark)){
           return SzpJsonResult.ok(ossService.uploadFile(file,request,remark));
       }
        return SzpJsonResult.errorMsg("remark is not right");
    }

}
