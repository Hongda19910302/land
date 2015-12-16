package net.deniro.land.module.icase.action;

import net.deniro.land.common.dwz.AjaxResponse;
import net.deniro.land.common.dwz.AjaxResponseError;
import net.deniro.land.common.dwz.AjaxResponseSuccess;
import net.deniro.land.common.utils.ftp.FtpUtils;
import net.deniro.land.module.icase.entity.CaseParam;
import net.deniro.land.module.icase.entity.TCase;
import net.deniro.land.module.icase.service.CaseService;
import net.deniro.land.module.system.action.BaseController;
import net.deniro.land.module.system.entity.TRegion;
import net.deniro.land.module.system.entity.User;
import net.deniro.land.module.system.service.RegionService;
import net.deniro.land.module.system.service.UserService;
import org.apache.commons.lang3.StringUtils;
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
import java.text.DecimalFormat;
import java.util.List;

import static net.deniro.land.common.dwz.AjaxResponseSuccess.MENU_TAB_PREFIX;

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
    private RegionService regionService;

    @Autowired
    private FtpUtils ftpUtils;

    /**
     * 跳转至【新建或编辑案件】表单
     *
     * @param componentId 组件ID
     * @param caseId      案件ID
     * @param mm
     * @param session
     * @return
     */
    @RequestMapping(value = "/addOrEditForm")
    public String addOrEditForm(Integer componentId, Integer caseId, ModelMap mm,
                                HttpSession
                                        session) {
        User user = (User) session.getAttribute(UserService.USER_CODE);

        if (caseId != null) {//编辑
            TCase tCase = caseService.findById(caseId);

            //处理区域信息
            TRegion region = tCase.getFindRegion();
            if (region != null) {
                tCase.setRegionName(region.getName());
                tCase.setRegionId(region.getRegionId());
            }

            //处理地理坐标信息
            DecimalFormat df = new DecimalFormat("#.00000");//保留小数点后5位
            if (tCase.getLng() != null) {
                tCase.setCoordinateLongitude(df.format(tCase.getLng()));
            }
            if (tCase.getLat() != null) {
                tCase.setCoordinateLatitude(df.format(tCase.getLat()));
            }


            mm.addAttribute("obj", tCase);
        } else {//新增
            //获取当前用户的区域信息
            List<TRegion> regions = regionService.findByCompanyIdForTree(user.getCompanyId());
            if (regions != null && !regions.isEmpty()) {
                mm.addAttribute("currentUserRegion", regions.get(0));
            }
        }

        form(componentId, mm, session);
        return COMPONENT_FORM_URL;
    }


    /**
     * 新增或修改案件
     *
     * @param caseParam 案件参数
     * @return
     */
    @RequestMapping(value = "addOrEdit")
    @ResponseBody
    public AjaxResponse addOrEditCase(CaseParam caseParam, HttpSession session) {
        try {
            caseParam.setUserId(String.valueOf(getCurrentUserId(session)));

            boolean isOk;
            String tip = "";
            String navTabId = "";
            if (caseParam.getCaseId() != null) {//修改
                isOk = caseService.modifyCase(caseParam);
                navTabId = MENU_TAB_PREFIX + "28";
                tip = "案件修改";
            } else {//新增
                isOk = caseService.addCase(caseParam);
                navTabId = MENU_TAB_PREFIX + "10";
                tip = "案件新增";
            }

            if (isOk) {
                AjaxResponseSuccess ajaxResponseSuccess = new AjaxResponseSuccess(tip + "成功");
                ajaxResponseSuccess.setNavTabId(navTabId);
                ajaxResponseSuccess.setCloseCurrent();
                return ajaxResponseSuccess;
            } else {
                return new AjaxResponseError(tip + "失败");
            }
        } catch (Exception e) {
            logger.error("新增或修改案件", e);
            return new AjaxResponseError("操作失败");
        }
    }

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
     * @param fileName 文件名称
     * @return
     */
    @RequestMapping(value = "/delUploadedFiles")
    @ResponseBody
    public AjaxResponse delUploadedFiles(String key, String fileName) {
        if (StringUtils.isBlank(fileName)) {//删除全部
            uploadFileNames.remove(key);
        } else {//删除某个文件
            uploadFileNames.get(key).remove(fileName);
        }

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
     * 跳转至【草稿箱】
     *
     * @return
     */
    @RequestMapping(value = "/draft")
    public String draft(CaseParam queryParam, ModelMap mm, HttpSession session) {
        queryParam.setIsDraft("true");

        return query(queryParam, mm, session);
    }


    /**
     * 跳转至【案件查询】
     *
     * @return
     */
    @RequestMapping(value = "/query")
    public String query(CaseParam queryParam, ModelMap mm, HttpSession session) {
        queryParam.setUserId(String.valueOf(getCurrentUserId(session)));
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
