package kxg.sso.mediacenter.provider.controller;


import kxg.sso.mediacenter.provider.common.SzpJsonResult;
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
     @PostMapping("upload")
    public SzpJsonResult<UpFileResponse> uploadImg(HttpServletRequest request, MultipartFile file,String remark) {
        return SzpJsonResult.ok(ossService.uploadFile(file,request,remark));
    }
//    @RequestMapping(value = "/headImgUpload", method = RequestMethod.POST)
//    @ResponseBody
//    public Map<String, Object> headImgUpload(HttpServletRequest request, MultipartFile file) {
//        Map<String, Object> value = new HashMap<String, Object>();
//        value.put("success", true);
//        value.put("errorCode", 0);
//        value.put("errorMsg", "");
//        try {
//            //此处是调用上传服务接口，4是需要更新的userId 测试数据。
//            String string = ossService.updateHead(file, 4);
//            value.put("data", string);
//        } catch (Exception e) {
//            e.printStackTrace();
//            value.put("success", false);
//            value.put("errorCode", 200);
//            value.put("errorMsg", "图片上传失败");
//        }
//        return value;
//    }
}
