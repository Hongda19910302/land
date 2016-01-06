package net.deniro.land.module.system.action;

import net.deniro.land.common.dwz.AjaxResponse;
import net.deniro.land.common.dwz.AjaxResponseError;
import net.deniro.land.module.component.service.CompFormService;
import net.deniro.land.module.system.entity.TRegion;
import net.deniro.land.module.system.entity.TRegion.OperateType;
import net.deniro.land.module.system.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static net.deniro.land.common.dwz.AjaxResponseSuccess.MENU_TAB_PREFIX;

/**
 * 行政区域
 *
 * @author deniro
 *         2016/1/5
 */
@Controller
@RequestMapping("/region")
public class RegionController extends BaseController {

    @Autowired
    private RegionService regionService;

    /**
     * 【行政区域】页签
     */
    public static final String REGION_ID = MENU_TAB_PREFIX + "26";

    /**
     * 新增或更新
     *
     * @param region
     * @return
     */
    @RequestMapping("/addOrEdit")
    @ResponseBody
    public AjaxResponse addOrEdit(TRegion region) {
        if (region == null || region.getCurrentRegionId() == null || region.getCurrentRegionId
                () == 0) {
            return new AjaxResponseError("操作失败");
        }

        if (region.getRegionId() == null || region.getRegionId() == 0) {//新增

            OperateType operateType;
            try {
                operateType = OperateType.valueOf(region.getOperateType());
            } catch (IllegalArgumentException e) {
                logger.error("operateType无法解析");
                return new AjaxResponseError("操作失败");
            }

            switch (operateType) {
                case ADD_BROTHER://新增同级区域
                    if (region.getCurrentRegionParentId() == null || region.getCurrentRegionParentId()
                            == 0) {//新增顶级区域
                    } else {//新增非顶级区域，需要保存父区域ID
                        region.setParentId(region.getCurrentRegionParentId());
                    }
                    break;
                case ADD_CHILD://新增子区域
                    region.setParentId(region.getCurrentRegionId());
                    break;
            }


            boolean isOk = regionService.add(region);

            if (isOk) {
                List<String> navTabIds = new ArrayList<String>();
                navTabIds.add(REGION_ID);
                return getAjaxSuccessAndCloseCurrentDialog("操作成功", navTabIds);
            } else
                return new AjaxResponseError("操作失败");

        } else {//更新
            return new AjaxResponseError("操作失败");
        }
    }

    /**
     * 跳转到新增或编辑行政区域页
     *
     * @param componentId
     * @param currentRegionId       当前选择的区域ID
     * @param currentRegionParentId 当前选择的节点的父节点区域ID
     * @param operateType           操作类型
     * @param mm
     * @param session
     * @return
     */
    @RequestMapping("/addOrEditIndex")
    public String addOrEditIndex(Integer componentId, Integer currentRegionId, Integer
            currentRegionParentId, String operateType,
                                 ModelMap mm,
                                 HttpSession
                                         session) {

        TRegion region = new TRegion();
        region.setCurrentRegionId(currentRegionId);
        region.setCurrentRegionParentId(currentRegionParentId);
        region.setOperateType(operateType);

        mm.addAttribute(CompFormService.OBJECT_NAME, region);

        form(componentId, mm, session);
        return COMPONENT_FORM_URL;
    }

    /**
     * 跳转到行政区域管理页
     *
     * @return
     */
    @RequestMapping("/index")
    public String index() {
        return "system/region/index";
    }
}
