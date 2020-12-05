package kxg.sso.mediacenter.provider.controller;

import kxg.sso.mediacenter.provider.common.SzpJsonResult;
import kxg.sso.mediacenter.provider.handler.CheckRemarkHandler;
import kxg.sso.mediacenter.provider.service.FileService;
import kxg.sso.mediacenter.response.UpFileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * 要写注释呀
 */
@RestController
@RequestMapping("upload")
public class UploadByUrlController {
    @Autowired
    private FileService ossService;
    @Autowired
    private CheckRemarkHandler checkRemarkHandler;

    @PostMapping("upload")
    public SzpJsonResult<UpFileResponse> uploadImg(String fileUrl, String remark) {
        if (checkRemarkHandler.findRemarkIsExist(remark)){
            return SzpJsonResult.ok(ossService.uploadByUrl(fileUrl,remark));
        }
        return SzpJsonResult.errorMsg("remark is not right");
    }
}
