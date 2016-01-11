package net.deniro.land.module.icase.action;

import net.deniro.land.common.dwz.AjaxResponse;
import net.deniro.land.common.dwz.AjaxResponseError;
import net.deniro.land.module.component.service.CompFormService;
import net.deniro.land.module.icase.entity.DataTypeQueryParam;
import net.deniro.land.module.icase.entity.TDataType;
import net.deniro.land.module.icase.service.DataTypeService;
import net.deniro.land.module.system.action.BaseController;
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
 * 案件下拉项
 *
 * @author deniro
 *         2015/11/4
 */
@Controller
@RequestMapping("/dataType")
public class DataTypeController extends BaseController {
    @Autowired
    private DataTypeService dataTypeService;

    /**
     * 【案件下拉项管理】页签
     */
    public static final String Data_Type_ID = MENU_TAB_PREFIX + "33";

    /**
     * 新增或编辑
     *
     * @param entity
     * @return
     */
    @RequestMapping(value = "/addOrEdit")
    @ResponseBody
    public AjaxResponse addOrEdit(TDataType entity) {

        if (entity == null) {
            return new AjaxResponseError("操作失败");
        }

        List<String> navTabIds = new ArrayList<String>();
        navTabIds.add(Data_Type_ID);

        if (entity.getDataTypeId() == null || entity.getDataTypeId() == 0) {//新增

            boolean isOk = dataTypeService.add(entity);
            if (isOk) {
                return getAjaxSuccessAndCloseCurrentDialog("操作成功", navTabIds);
            } else
                return new AjaxResponseError("操作失败");
        } else {//编辑
            TDataType newEntity = dataTypeService.findById(entity.getDataTypeId());
            newEntity.setDataTypeValue(entity.getDataTypeValue());

            boolean isOk = dataTypeService.update(newEntity);
            if (isOk) {
                return getAjaxSuccessAndCloseCurrentDialog("操作成功", navTabIds);
            } else
                return new AjaxResponseError("操作失败");
        }
    }

    /**
     * 跳转至新增或编辑页面
     *
     * @param componentId
     * @param dataTypeId  ID
     * @param mm
     * @param session
     * @return
     */
    @RequestMapping(value = "/addOrEditIndex")
    public String addOrEditIndex(Integer componentId, Integer dataTypeId, ModelMap mm,
                                 HttpSession session) {

        if (dataTypeId != null) {//编辑
            TDataType entity = dataTypeService.findById(dataTypeId);
            mm.addAttribute(CompFormService.OBJECT_NAME, entity);
        }

        form(componentId, mm, session);
        return COMPONENT_FORM_URL;
    }

    /**
     * 跳转至案件字段主界面
     *
     * @return
     */
    @RequestMapping(value = "/index")
    public String index(DataTypeQueryParam queryParam, ModelMap mm) {
        super.pageSearch(mm, dataTypeService.findPage(queryParam), queryParam, "dataType/index");
        return COMPONENT_PAGE_SEARCH_URL;
    }
}
