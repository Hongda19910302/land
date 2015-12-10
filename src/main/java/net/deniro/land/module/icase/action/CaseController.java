package net.deniro.land.module.icase.action;

import net.deniro.land.common.dwz.AjaxResponse;
import net.deniro.land.common.dwz.AjaxResponseError;
import net.deniro.land.common.dwz.AjaxResponseSuccess;
import net.deniro.land.common.utils.UUIDGenerator;
import net.deniro.land.common.utils.ftp.FtpUtils;
import net.deniro.land.module.icase.entity.CaseParam;
import net.deniro.land.module.icase.service.CaseService;
import net.deniro.land.module.system.action.BaseController;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 案件
 *
 * @author deniro
 *         2015/11/23
 */
@Controller
@RequestMapping("/case")
public class CaseController extends BaseController {

    static Logger logger = Logger.getLogger(CaseController.class);

    @Autowired
    private CaseService caseService;

    @Autowired
    private FtpUtils ftpUtils;

    /**
     * 上传【违法照片】
     *
     * @param illegalPhotos
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/uploadIllegalPhotos")
    @ResponseBody
    public AjaxResponse uploadIllegalPhotos(@RequestParam("illegalPhotosFileInput")
                                            MultipartFile illegalPhotos)
            throws IOException {
        if (!illegalPhotos.isEmpty()) {
            illegalPhotos.transferTo(new File("F:/temp/" + illegalPhotos.getOriginalFilename()));
            return new AjaxResponseSuccess("上传成功");
        } else {
            return new AjaxResponseError("上传失败");
        }
    }

    /**
     * 删除已上传的文件
     *
     * @param key
     * @return
     */
    @RequestMapping(value = "/delUploadedFiles")
    @ResponseBody
    public AjaxResponse delUploadedFiles(String key,String paths) {
        uploadFileNames.remove(key);
        return new AjaxResponseSuccess("删除成功");
    }

    /**
     * 获取已上传文件的路径列表
     *
     * @param key
     * @param session
     * @return
     */
    @RequestMapping(value = "/lookupUploadedFiles")
    public String lookupUploadedFiles(String key, HttpSession session, ModelMap mm) {
        key = key + getCurrentUserId(session);
        List<String> paths = uploadFileNames.get(key);
        mm.addAttribute("paths", paths);
        mm.addAttribute("key", key);
        return COMPONENT_IMAGES_DISPLAY_URL;
    }


    /**
     * 上传【单据文书】
     *
     * @param multipartFile
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/uploadCaseDocuments")
    @ResponseBody
    public AjaxResponse uploadCaseDocuments(@RequestParam("caseDocumentsFileInput")
                                            MultipartFile multipartFile, HttpSession session)
            throws IOException {
        if (!multipartFile.isEmpty()) {
            boolean isOk = uploadToTemp("caseDocuments", multipartFile, session);
            if (isOk) {
                return new AjaxResponseSuccess("上传成功");
            } else {
                return new AjaxResponseError("上传失败");
            }
        } else {
            return new AjaxResponseError("上传失败");
        }
    }

    /**
     * 跳转至【案件查询】
     *
     * @return
     */
    @RequestMapping(value = "/query")
    public String query(CaseParam queryParam, ModelMap mm) {
        super.pageSearch(mm, caseService.findPage(queryParam), queryParam, "case/query");
        return COMPONENT_PAGE_SEARCH_URL;
    }

    /**
     * 跳转至【我的案件】
     *
     * @return
     */
    @RequestMapping(value = "/my")
    public String my(CaseParam queryParam, ModelMap mm) {
        super.pageSearch(mm, caseService.findPage(queryParam), queryParam, "case/my");
        return COMPONENT_PAGE_SEARCH_URL;
    }
}
