package net.deniro.land.module.icase.action;

import net.deniro.land.common.dwz.AjaxResponse;
import net.deniro.land.common.dwz.AjaxResponseError;
import net.deniro.land.module.component.service.CompFormService;
import net.deniro.land.module.icase.entity.TDataField;
import net.deniro.land.module.icase.entity.TVariableField;
import net.deniro.land.module.icase.entity.VariableFieldQueryParam;
import net.deniro.land.module.icase.entity.VariableSelectRelation;
import net.deniro.land.module.icase.service.DataFieldService;
import net.deniro.land.module.icase.service.VariableFieldService;
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
import static net.deniro.land.module.icase.entity.TVariableField.BelongToTable.T_CASE;
import static net.deniro.land.module.icase.entity.TVariableField.Status.AVAILABLE;

/**
 * 案件字段
 *
 * @author deniro
 *         2015/11/4
 */
@Controller
@RequestMapping("/variableField")
public class VariableFieldController extends BaseController {
    @Autowired
    private VariableFieldService variableFieldService;

    @Autowired
    private DataFieldService dataFieldService;

    @Autowired
    private VariableSelectRelation VariableSelectRelation;

    /**
     * 【案件字段管理】页签
     */
    public static final String VARIABLE_FIELD_ID = MENU_TAB_PREFIX + "27";

    /**
     * 修改状态
     *
     * @param variableFieldId ID
     * @param status          状态
     * @return
     */
    @RequestMapping(value = "/changeStatus")
    @ResponseBody
    public AjaxResponse changeStatus(Integer variableFieldId, Integer status) {
        if (variableFieldId == null || status == null) {
            return new AjaxResponseError("操作失败");
        }

        TVariableField entity = variableFieldService.findById(variableFieldId);
        entity.setStatus(status);//设置状态值
        boolean isOk = variableFieldService.update(entity);
        if (isOk) {
            List<String> navTabIds = new ArrayList<String>();
            navTabIds.add(VARIABLE_FIELD_ID);

            VariableSelectRelation.init();

            return getAjaxSuccessAndCloseCurrentDialog("操作成功", navTabIds);
        } else
            return new AjaxResponseError("操作失败");
    }

    /**
     * 新增或编辑
     *
     * @param entity
     * @return
     */
    @RequestMapping(value = "/addOrEdit")
    @ResponseBody
    public AjaxResponse addOrEdit(TVariableField entity) {

        if (entity == null) {
            return new AjaxResponseError("操作失败");
        }

        List<String> navTabIds = new ArrayList<String>();
        navTabIds.add(VARIABLE_FIELD_ID);

        if (entity.getVariableFieldId() == null || entity.getVariableFieldId() == 0) {//新增

            entity.setStatus(AVAILABLE.code());
            entity.setFieldKey(getFieldKey(entity.getDataFieldId()));
            entity.setTableType(T_CASE.code());

            boolean isOk = variableFieldService.add(entity);
            if (isOk) {
                VariableSelectRelation.init();
                return getAjaxSuccessAndCloseCurrentDialog("操作成功", navTabIds);
            } else
                return new AjaxResponseError("操作失败");
        } else {//编辑
            TVariableField newEntity = variableFieldService.findById(entity.getVariableFieldId());
            newEntity.setFieldKey(getFieldKey(entity.getDataFieldId()));
            newEntity.setDataFieldId(entity.getDataFieldId());
            newEntity.setCompanyId(entity.getCompanyId());
            newEntity.setAlias(entity.getAlias());
            newEntity.setTableType(entity.getTableType());
            newEntity.setType(entity.getType());
            newEntity.setIsNull(entity.getIsNull());
            newEntity.setIsPullDown(entity.getIsPullDown());
            newEntity.setIsHide(entity.getIsHide());

            boolean isOk = variableFieldService.update(newEntity);
            if (isOk) {
                VariableSelectRelation.init();
                return getAjaxSuccessAndCloseCurrentDialog("操作成功", navTabIds);
            } else
                return new AjaxResponseError("操作失败");
        }
    }

    /**
     * 获取FieldKey
     *
     * @param dataFieldId
     * @return
     */
    public String getFieldKey(Integer dataFieldId) {
        List<TDataField> tDataFields = dataFieldService.findAll();
        for (TDataField tDataField : tDataFields) {
            if (tDataField.getDataFieldId() == dataFieldId) {
                return tDataField.getTableField();
            }
        }
        return "";
    }

    /**
     * 跳转至新增或编辑页面
     *
     * @param componentId
     * @param variableFieldId ID
     * @param mm
     * @param session
     * @return
     */
    @RequestMapping(value = "/addOrEditIndex")
    public String addOrEditIndex(Integer componentId, Integer variableFieldId, ModelMap mm,
                                 HttpSession session) {

        if (variableFieldId != null) {//编辑
            TVariableField entity = variableFieldService.findById(variableFieldId);
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
    public String index(VariableFieldQueryParam queryParam, ModelMap mm) {
        super.pageSearch(mm, variableFieldService.findPage(queryParam), queryParam, "variableField/index");
        return COMPONENT_PAGE_SEARCH_URL;
    }

}
