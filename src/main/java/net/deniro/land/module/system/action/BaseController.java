package net.deniro.land.module.system.action;

import net.deniro.land.common.dao.Page;
import net.deniro.land.common.entity.QueryParam;
import net.deniro.land.module.component.entity.CompPageSearch;
import net.deniro.land.module.component.entity.ComponentType;
import net.deniro.land.module.component.service.CompPageSearchService;
import net.deniro.land.module.system.service.ModuleService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

import javax.management.Query;

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
    public static final String COMPONENT_PAGE_SEARCH_URL="/component/page-search";

    @Deprecated
    @Autowired
    private ModuleService moduleService;

    @Autowired
    private CompPageSearchService compPageSearchService;

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
