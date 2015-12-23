package net.deniro.land.module.icase.action;

import net.deniro.land.api.entity.Images;
import net.deniro.land.common.dwz.AjaxResponse;
import net.deniro.land.common.dwz.AjaxResponseError;
import net.deniro.land.common.dwz.AjaxResponseSuccess;
import net.deniro.land.common.service.Constants;
import net.deniro.land.common.utils.PropertiesReader;
import net.deniro.land.common.utils.ftp.FtpUtils;
import net.deniro.land.module.component.entity.FTPUploadFile;
import net.deniro.land.module.icase.entity.*;
import net.deniro.land.module.icase.entity.TAttachmentRelation.RelationType;
import net.deniro.land.module.icase.service.CaseService;
import net.deniro.land.module.system.action.BaseController;
import net.deniro.land.module.system.entity.TRegion;
import net.deniro.land.module.system.entity.User;
import net.deniro.land.module.system.service.RegionService;
import net.deniro.land.module.system.service.UserService;
import org.apache.commons.collections.map.MultiKeyMap;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static net.deniro.land.common.dwz.AjaxResponseSuccess.MENU_TAB_PREFIX;
import static net.deniro.land.common.dwz.AjaxResponseSuccess.NAB_TAB_ID_SPLIT;
import static net.deniro.land.module.component.entity.FTPUploadFile.FileSource.FTP;
import static net.deniro.land.module.component.entity.FTPUploadFile.FileSource.TEMP;
import static net.deniro.land.module.icase.entity.TAttachment.AttachmentType.BILL;
import static net.deniro.land.module.icase.entity.TAttachment.AttachmentType.PHOTO;
import static net.deniro.land.module.icase.entity.TAttachmentRelation.RelationType.CASE;

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

    @Resource(name = "caseStatus")
    private Map<String, String> caseStatus;

    @Autowired
    private VariableSelectRelation variableSelectRelation;

    /**
     * 【草稿箱】模块
     */
    public static final String DRAFT_ID = MENU_TAB_PREFIX + "28";

    /**
     * 查询案件巡查记录
     *
     * @param caseId 案件ID
     * @param mm
     * @return
     */
    @RequestMapping(value = "/findInspects")
    public String findInspects(Integer caseId, ModelMap mm) {

        //巡查记录
        if (caseId != null) {
            List<TInspect> inspects = caseService.findInspectById(caseId);
            mm.addAttribute("inspects", inspects);
        }


        return "case/inspects";
    }

    /**
     * 查询案件详情
     *
     * @param caseId 案件ID
     * @return
     */
    @RequestMapping(value = "/findById")
    public String findById(Integer caseId, ModelMap mm, HttpSession session) {
        TCase tCase = caseService.findById(caseId);

        //巡查员
        User inspector = tCase.getInspector();
        if (inspector != null) {
            tCase.setInspectorName(inspector.getName());
        }

        //创建者
        User creator = tCase.getCreator();
        if (creator != null) {
            tCase.setCreatorName(creator.getName());
        }

        //案件状态转换为显示值
        if (tCase.getStatus() != null) {
            tCase.setStatusInDisplay(caseStatus.get(String.valueOf(tCase.getStatus())));
        }

        //所在地区
        if (tCase.getRegionId() != null) {
            tCase.setRegionName(tCase.getFindRegion().getName());
        }

        /**
         * 可变字段赋值
         */
        //获取可变字段键值对
        MultiKeyMap variableSelects = variableSelectRelation.getVariableSelects();
        User user = getCurrentUser(session);
        //违建类型
        if (tCase.getIllegalType() != null) {
            tCase.setIllegalTypeInDisplay(findVariableDisplayName(variableSelects, user,
                    "illegalType", String.valueOf(tCase
                            .getIllegalType())));
        }
        //用地性质
        if (tCase.getLandUsage() != null) {
            tCase.setLandUsageInDisplay(findVariableDisplayName(variableSelects, user,
                    "landUsage", String.valueOf(tCase
                            .getLandUsage())));
        }
        //违法现状
        if (tCase.getStatus() != null) {
            tCase.setStatusInDisplay(findVariableDisplayName(variableSelects, user,
                    "surveyResult", String.valueOf(tCase.getStatus())));
        }
        //案件来源
        if (tCase.getCaseSource() != null) {
            tCase.setCaseSourceInDisplay(findVariableDisplayName(variableSelects, user,
                    "caseSource", String.valueOf(tCase.getCaseSource())));
        }


        mm.addAttribute("tCase", tCase);

        return "case/detail";
    }

    /**
     * 删除案件
     *
     * @param caseId
     * @return
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public AjaxResponse delete(Integer caseId) {
        boolean isOk = caseService.delete(caseId);
        if (isOk) {
            //刷新【草稿箱】模块
            List<String> navTabIds = new ArrayList<String>();
            navTabIds.add(DRAFT_ID);
            return getAjaxSuccess("案件已删除", navTabIds);
        } else {
            return new AjaxResponseError("案件删除失败");
        }
    }


    /**
     * 上传【单据文书】的key
     */
    public static final String UPLOAD_CASE_DOCUMENTS_KEY_PREFIX = "caseDocuments";

    /**
     * 获取上传【单据文书】的key
     *
     * @param session
     * @return
     */
    public String getCaseDocumentsKey(Integer id, HttpSession session) {
        StringBuilder str = new StringBuilder(UPLOAD_CASE_DOCUMENTS_KEY_PREFIX);
        if (id != null) {
            str.append(id);
        }
        str.append(getCurrentUserId(session));
        return str.toString();
    }

    /**
     * 上传【违法照片】的key
     */
    public static final String UPLOAD_ILLEGAL_PHOTOS_KEY_PREFIX = "illegalPhotos";

    /**
     * 获取上传【违法照片】的key
     *
     * @param session
     * @return
     */
    public String getIllegalPhotosKey(Integer id, HttpSession session) {
        StringBuilder str = new StringBuilder(UPLOAD_ILLEGAL_PHOTOS_KEY_PREFIX);
        if (id != null) {
            str.append(id);
        }
        str.append(getCurrentUserId(session));
        return str.toString();
    }

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

            mm.addAttribute("fileUploadId", tCase.getCaseId());//用于文件上传的ID
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
            List<String> navTabIds = new ArrayList<String>();

            String queryCaseId = MENU_TAB_PREFIX + "10";//【案件查询】模块

            /**
             * 新增附件
             */
            String ftpRealPath = ftpUtils.getRealPath(caseParam.getUserId());

            if (caseParam.getCaseId() != null) {//修改
                if (BooleanUtils.toBoolean(caseParam.getIsDraft())) {//继续存为草稿，刷新【草稿箱】模块
                    navTabIds.add(DRAFT_ID);
                } else {//预立案，刷新【草稿箱】、【案件查询】模块
                    caseParam.setStatus(String.valueOf(TCase.CaseStatus.PREPARE.code()));
                    navTabIds.add(DRAFT_ID);
                    navTabIds.add(queryCaseId);
                }

                //修改单据文书
                String caseDocumentsKey = getCaseDocumentsKey(caseParam.getCaseId(), session);
                List<Images> files = findToUploadFilesByKey(caseDocumentsKey, ftpRealPath,
                        BILL);

                //修改违法照片
                String illegalPhotosKey = getIllegalPhotosKey(caseParam.getCaseId(), session);
                files.addAll(findToUploadFilesByKey(illegalPhotosKey, ftpRealPath, PHOTO));

                caseParam.setAttachmentList(files);


                isOk = caseService.modifyCase(caseParam);

                //清空待上传文件
                uploadFileNames.remove(caseDocumentsKey);
                uploadFileNames.remove(illegalPhotosKey);

                tip = "案件修改";
            } else {//新增


                //新增单据文书
                String caseDocumentsKey = getCaseDocumentsKey(null, session);
                List<Images> files = findToUploadFilesByKey(caseDocumentsKey, ftpRealPath,
                        BILL);

                //新增违法照片
                String illegalPhotosKey = getIllegalPhotosKey(null, session);
                files.addAll(findToUploadFilesByKey(illegalPhotosKey, ftpRealPath, PHOTO));

                caseParam.setAttachmentList(files);
                isOk = caseService.addCase(caseParam);

                //清空待上传文件
                uploadFileNames.remove(caseDocumentsKey);
                uploadFileNames.remove(illegalPhotosKey);

                if (BooleanUtils.toBoolean(caseParam.getIsDraft())) {//继续存为草稿，刷新【草稿箱】模块
                    navTabIds.add(DRAFT_ID);
                } else {//预立案，刷新【案件查询】模块
                    navTabIds.add(queryCaseId);
                }
                tip = "案件新增";
            }

            if (isOk) {
                AjaxResponseSuccess ajaxResponseSuccess = new AjaxResponseSuccess(tip + "成功");
                ajaxResponseSuccess.setNavTabIds(StringUtils.join(navTabIds.toArray(),
                        NAB_TAB_ID_SPLIT));
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
     * @param multipartFile
     * @param id
     * @param session
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/uploadIllegalPhotos")
    @ResponseBody
    public AjaxResponse uploadIllegalPhotos(@RequestParam("illegalPhotosFileInput")
                                            MultipartFile multipartFile, Integer id, HttpSession session)
            throws IOException {
        if (!multipartFile.isEmpty()) {
            boolean isOk = uploadToTemp(getIllegalPhotosKey(id, session), multipartFile,
                    session);
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
     * 上传【单据文书】
     *
     * @param multipartFile
     * @param id
     * @param session
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/uploadCaseDocuments")
    @ResponseBody
    public AjaxResponse uploadCaseDocuments(@RequestParam("caseDocumentsFileInput")
                                            MultipartFile multipartFile, Integer id,
                                            HttpSession
                                                    session)
            throws IOException {
        if (!multipartFile.isEmpty()) {
            boolean isOk = uploadToTemp(getCaseDocumentsKey(id, session), multipartFile,
                    session);
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
     * @param queryParam
     * @param mm
     * @param session
     * @return
     */
    @RequestMapping(value = "/my")
    public String my(CaseParam queryParam, ModelMap mm, HttpSession session) {

        if (queryParam != null) {
            queryParam.setUserId(String.valueOf(getCurrentUserId(session)));
        }

        super.pageSearch(mm, caseService.findPage(queryParam), queryParam, "case/my");
        return COMPONENT_PAGE_SEARCH_URL;
    }

    /**
     * 删除已上传的文件
     *
     * @param key
     * @param fileName   文件名称
     * @param filePath   文件路径
     * @param fileSource 文件来源
     * @param id         当前对象ID
     * @return
     */
    @RequestMapping(value = "/delUploadedFiles")
    @ResponseBody
    public AjaxResponse delUploadedFiles(String key, String fileName, String filePath, String
            fileSource, Integer id) {
        if (StringUtils.isBlank(fileName)) {//删除全部
            uploadFileNames.remove(key);

            //删除所有附件记录
            if (id != null) {
                //todo 这里未考虑其他模块的文件上传情况
                caseService.deleteAllAttachments(id, RelationType.CASE);
            }
        } else {//删除某个文件

            if (StringUtils.equals(fileSource, TEMP.name())) {
                List<FTPUploadFile> files = uploadFileNames.get(key);
                for (Iterator<FTPUploadFile> it = files.iterator(); it.hasNext(); ) {
                    FTPUploadFile file = it.next();
                    if (StringUtils.equals(file.getFileName(), fileName)) {
                        it.remove();
                    }
                }
            } else if (StringUtils.equals(fileSource, FTP.name())) {
                caseService.deleteAllByFilePath(filePath);
            }

        }

        return new AjaxResponseSuccess("删除成功");
    }

    /**
     * 获取已上传文件的路径列表
     *
     * @param key
     * @param id      当前操作对象的ID
     * @param session
     * @param mm
     * @return
     */
    @RequestMapping(value = "/lookupUploadedFiles")
    public String lookupUploadedFiles(String key, Integer id, HttpSession session,
                                      ModelMap mm) {
        Integer userId = getCurrentUserId(session);
        key = key + userId;
        List<FTPUploadFile> ftpUploadFiles = uploadFileNames.get(key);

        if (id != null) {//从附件表中取文件地址
            if (ftpUploadFiles == null) {
                ftpUploadFiles = new ArrayList<FTPUploadFile>();
            }


            if (!ftpUploadFiles.isEmpty()) {//清空缓存中的来源于FTP类型的文件，以便后面重新获取
                for (Iterator<FTPUploadFile> it = ftpUploadFiles.iterator(); it.hasNext(); ) {
                    FTPUploadFile file = it.next();
                    if (file.getFileSource() == FTPUploadFile
                            .FileSource.FTP) {
                        it.remove();
                    }
                }
            }

            //todo 这里未考虑其他模块的文件上传情况
            List<TAttachment> attachments = caseService.findAttachments(id, CASE);
            for (TAttachment attachment : attachments) {

                if (StringUtils.contains(key, UPLOAD_CASE_DOCUMENTS_KEY_PREFIX)) {//单据
                    if (attachment.getAttachmentType() != TAttachment.AttachmentType.BILL.code()) {
                        continue;
                    }
                } else if (StringUtils.contains(key, UPLOAD_ILLEGAL_PHOTOS_KEY_PREFIX)) {//照片
                    if (attachment.getAttachmentType() != TAttachment.AttachmentType.PHOTO.code()) {
                        continue;
                    }
                }

                FTPUploadFile file = new FTPUploadFile();
                file.setFilePath(attachment.getAddr());
                file.setFileSource(FTP);
                ftpUploadFiles.add(file);
            }

            String ftpHttpUrl = PropertiesReader.value(Constants
                    .FTP_HTTP_URL_PREFIX_ID) + ftpUtils.getRealPath(String.valueOf(userId));
            mm.addAttribute("ftpHttpUrl", ftpHttpUrl);
        } else {
            mm.addAttribute("filePathPrefix", "/temp");//文件来自于临时路径
        }

        mm.addAttribute("ftpUploadFiles", ftpUploadFiles);
        mm.addAttribute("key", key);
        mm.addAttribute("id", id);
        return COMPONENT_IMAGES_DISPLAY_URL;
    }
}
