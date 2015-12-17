package net.deniro.land.module.system.action;

import net.deniro.land.common.dao.Page;
import net.deniro.land.common.entity.QueryParam;
import net.deniro.land.common.service.Constants;
import net.deniro.land.common.utils.UUIDGenerator;
import net.deniro.land.common.utils.ftp.FtpUtils;
import net.deniro.land.module.component.entity.*;
import net.deniro.land.module.component.service.CompFormService;
import net.deniro.land.module.component.service.CompPageSearchService;
import net.deniro.land.module.system.entity.User;
import net.deniro.land.module.system.service.ModuleService;
import net.deniro.land.module.system.service.UserService;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 基础
 *
 * @author deniro
 *         2015/10/20
 */
public class BaseController {

    static Logger logger = Logger.getLogger(BaseController.class);

    /**
     * 分页查询组件URL地址
     */
    public static final String COMPONENT_IMAGES_DISPLAY_URL = "/component/images-display";

    /**
     * 分页查询组件URL地址
     */
    public static final String COMPONENT_PAGE_SEARCH_URL = "/component/page-search";

    /**
     * 表单组件URL地址
     */
    public static final String COMPONENT_FORM_URL = "/component/form";

    @Deprecated
    @Autowired
    private ModuleService moduleService;

    @Autowired
    private CompPageSearchService compPageSearchService;

    @Autowired
    private CompFormService compFormService;

    @Autowired
    private FtpUtils ftpUtils;

    /**
     * 上传文件的临时路径
     */
    public static final String UPLOAD_TEMP_PATH = "/temp/";

    /**
     * 待上传的文件；key：关键字；value：待上传的文件
     */
    public static Map<String, List<FTPUploadFile>> uploadFileNames = Collections.synchronizedMap(new
            HashMap<String,
                    List<FTPUploadFile>>());

    /**
     * 获取文件上传的临时绝对路径
     *
     * @param session
     * @return
     */
    public String getUploadTempAbsolutePath(HttpSession session) {
        return getAbsolutePath(session) + UPLOAD_TEMP_PATH;
    }

    /**
     * 获取项目绝对路径
     *
     * @param session
     * @return
     */
    public String getAbsolutePath(HttpSession session) {
        return session.getServletContext().getRealPath("/");
    }

    /**
     * 上传至临时文件夹
     *
     * @param key           关键字
     * @param multipartFile
     * @param session
     * @return
     */
    public boolean uploadToTemp(String key, MultipartFile multipartFile, HttpSession
            session) {
        File file = new File(getUploadTempAbsolutePath(session) + UUIDGenerator.get() + Constants
                .FILE_EXTENSION_PREFIX + FilenameUtils
                .getExtension
                        (multipartFile.getOriginalFilename()));
        logger.info("待上传文件：" + file.getName());

        try {
            multipartFile.transferTo(file);

            //保存待上传的文件列表到缓存
            List<FTPUploadFile> list = null;
            if (uploadFileNames.containsKey(key)) {
                list = uploadFileNames.get(key);
            } else {
                list = new ArrayList<FTPUploadFile>();
            }
            FTPUploadFile FTPUploadFile = new FTPUploadFile();
            FTPUploadFile.setFileName(file.getName());
            FTPUploadFile.setFilePath(file.getAbsolutePath());
            list.add(FTPUploadFile);
            uploadFileNames.put(key, list);

            return true;
        } catch (IOException e) {
            logger.error("上传至临时文件夹", e);
            return false;
        }
    }

    /**
     * 上传文件至FTP服务器中的临时图片路径
     *
     * @param multipartFile
     * @param session
     * @return
     */
    @Deprecated
    public boolean uploadToFTPInTempImg(MultipartFile multipartFile, HttpSession session) {
        Integer userId = getCurrentUserId(session);
        if (userId == -1) {
            return false;
        }

        String path = ftpUtils.generateTempImgPath(userId);
        return uploadToFTP(multipartFile, path);
    }

    /**
     * 上传文件至FTP服务器
     *
     * @param multipartFile 待上传的文件
     * @param path          上传到的文件路径
     * @return
     */
    @Deprecated
    public boolean uploadToFTP(MultipartFile multipartFile, String path) {
        ftpUtils.mkDirs(path);//创建路径

        //上传
        try {
            String fileName = multipartFile.getOriginalFilename();
            File file = File.createTempFile(UUIDGenerator.get(),
                    Constants.FILE_EXTENSION_PREFIX + FilenameUtils.getExtension(fileName));
            multipartFile.transferTo(file);
            return ftpUtils.upload(path, file);
        } catch (IOException e) {
            logger.error("上传至FTP服务器", e);
            return false;
        }
    }

    /**
     * 获取当前登录用户的ID，如果不存在，则返回-1
     *
     * @param session
     * @return
     */
    public Integer getCurrentUserId(HttpSession session) {
        User user = (User) session.getAttribute(UserService.USER_CODE);
        if (user != null) {
            return user.getUserId();
        } else {
            return -1;
        }
    }

    /**
     * 调用表单组件
     *
     * @param componentId
     * @param mm
     * @param session
     */
    public void form(Integer componentId, ModelMap mm, HttpSession session) {
        User user = (User) session.getAttribute(UserService.USER_CODE);

        CompForm compForm = compFormService.findById(componentId, user.getCompanyId());
        mm.addAttribute("compForm", compForm);
        mm.addAttribute("componentId", "form_" + componentId + "_" + RandomUtils.nextInt(0, 100));

    }

    /**
     * 调用分页查询组件
     *
     * @param mm
     * @param page       分页查询结果
     * @param queryParam 查询参数
     * @param actionUrl  action地址
     */
    public void pageSearch(ModelMap mm, Page page, QueryParam queryParam, String actionUrl) {
        try {
            //分页查询
            mm.addAttribute("page", page);

            //传递查询参数
            mm.addAttribute("queryParam", queryParam);

            //传递action地址
            mm.addAttribute("actionUrl", actionUrl);

            switch (ComponentType.valueOf(queryParam.getComponentType())) {
                case PAGE_SEARCH:
                    CompPageSearch compPageSearch = compPageSearchService.findById(queryParam
                            .getComponentId());

                    /**
                     * 处理隐藏的表单查询参数
                     */
                    Set<CompPageSearchForm> fields = compPageSearch
                            .getCompPageSearchFormFields();
                    List<CompPageSearchForm> normalFields = new
                            ArrayList<CompPageSearchForm>();
                    List<CompPageSearchForm> hiddenFields = new
                            ArrayList<CompPageSearchForm>();
                    for (CompPageSearchForm field : fields) {
                        if (StringUtils.equals(field.getInputType(), InputType.HIDDEN.toString())) {
                            hiddenFields.add(field);
                        } else {
                            normalFields.add(field);
                        }
                    }
                    compPageSearch.setHiddenFormFields(hiddenFields);
                    compPageSearch.setNormalFormFields(normalFields);

//                    logger.info("compPageSearch:"+compPageSearch);
                    mm.addAttribute("compPageSearch", compPageSearch);
                    break;
                default:
                    logger.error("未指定合法的组件类型；queryParam：" + queryParam);
                    break;
            }
        } catch (IllegalArgumentException e) {
            logger.error("调用分页查询组件", e);
        }
    }

    /**
     * 处理模块
     *
     * @param mm
     * @param page       分页查询结果
     * @param queryParam 查询参数
     * @param actionUrl  action地址
     */
    @Deprecated
    public void handleModule(ModelMap mm, Page page, QueryParam queryParam, String actionUrl) {
        //分页查询
        mm.addAttribute("page", page);

        //传递查询参数
        mm.addAttribute("queryParam", queryParam);

        //模块查询表单配置
        mm.addAttribute("moduleSearchCfg", moduleService.findForSearchCfg(queryParam.getModuleId()));

        //模块分页表格配置
        mm.addAttribute("moduleTableCfg", moduleService.findForTableCfg(queryParam
                .getModuleId()));

        //传递action地址
        mm.addAttribute("actionUrl", actionUrl);
    }
}
