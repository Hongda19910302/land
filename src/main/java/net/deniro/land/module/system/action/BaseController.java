package net.deniro.land.module.system.action;

import net.deniro.land.common.dao.Page;
import net.deniro.land.common.entity.QueryParam;
import net.deniro.land.common.service.Constants;
import net.deniro.land.common.utils.FtpUtils;
import net.deniro.land.common.utils.UUIDGenerator;
import net.deniro.land.module.component.entity.CompPageSearch;
import net.deniro.land.module.component.entity.CompPageSearchForm;
import net.deniro.land.module.component.entity.ComponentType;
import net.deniro.land.module.component.entity.InputType;
import net.deniro.land.module.component.service.CompPageSearchService;
import net.deniro.land.module.system.entity.User;
import net.deniro.land.module.system.service.ModuleService;
import net.deniro.land.module.system.service.UserService;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    public static final String COMPONENT_PAGE_SEARCH_URL = "/component/page-search";

    @Deprecated
    @Autowired
    private ModuleService moduleService;

    @Autowired
    private CompPageSearchService compPageSearchService;

    @Autowired
    private FtpUtils ftpUtils;

    /**
     * 上传至FTP服务器
     *
     * @param multipartFile 待上传的文件
     * @param path          上传到的文件路径
     * @return
     */
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
