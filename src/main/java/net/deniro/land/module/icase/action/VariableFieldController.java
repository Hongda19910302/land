package net.deniro.land.module.icase.action;

import net.deniro.land.common.dwz.AjaxResponse;
import net.deniro.land.common.dwz.AjaxResponseError;
import net.deniro.land.module.component.service.CompFormService;
import net.deniro.land.module.icase.entity.*;
import net.deniro.land.module.icase.service.DataFieldService;
import net.deniro.land.module.icase.service.VariableFieldService;
import net.deniro.land.module.system.action.BaseController;
import org.apache.commons.lang3.StringUtils;
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

    /**
     * 【案件字段管理】页签
     */
    public static final String VARIABLE_FIELD_ID = MENU_TAB_PREFIX + "27";

    /**
     * 设置下拉项
     *
     * @param selectedIds
     * @param variableFieldId
     * @return
     */
    @RequestMapping(value = "/setSelectOpinions")
    @ResponseBody
    public AjaxResponse setSelectOpinions(String selectedIds, Integer variableFieldId) {
        if (StringUtils.isBlank(selectedIds) || variableFieldId == null) {
            return new AjaxResponseError("操作失败");
        }

        boolean isOk = variableFieldService.setSelectOpinions(selectedIds, variableFieldId);
        if (isOk) {
            List<String> navTabIds = new ArrayList<String>();
            navTabIds.add(VARIABLE_FIELD_ID);
            return getAjaxSuccessAndCloseCurrentDialog("操作成功", navTabIds);
        } else
            return new AjaxResponseError("操作失败");
    }

    /**
     * 查询所有下拉项的树节点
     *
     * @param variableFieldId ID
     * @return
     */
    @RequestMapping(value = "/findAllSelectOpinionNodes")
    @ResponseBody
    public List<TDataType> findAllSelectOpinionNodes(Integer variableFieldId) {
        return variableFieldService.findAllNodes(variableFieldId);
    }

    /**
     * 设置下拉项页面
     *
     * @param variableFieldId
     * @return
     */
    @RequestMapping(value = "/setSelectOpinionsIndex")
    public String setSelectOpinionsIndex(Integer variableFieldId, ModelMap mm) {
        mm.addAttribute("variableFieldId", variableFieldId);
        return "case/setSelectOpinionsIndex";
    }

    /**
     * 修改状态
     *
     * @param confId ID
     * @param status 状态
     * @return
     */
    @RequestMapping(value = "/changeStatus")
    @ResponseBody
    public AjaxResponse changeStatus(Integer confId, Integer status) {
        if (confId == null || status == null) {
            return new AjaxResponseError("操作失败");
        }

        CaseField caseField = variableFieldService.findRelationById(confId);
        TVariableField entity = variableFieldService.findById(caseField.getVariableFieldId());
        entity.setStatus(status);//设置状态值
        boolean isOk = variableFieldService.update(entity);
        if (isOk) {
            List<String> navTabIds = new ArrayList<String>();
            navTabIds.add(VARIABLE_FIELD_ID);
            return getAjaxSuccessAndCloseCurrentDialog("操作成功", navTabIds);
        } else
            return new AjaxResponseError("操作失败");
    }

    /**
     * 删除
     *
     * @param confId
     * @return
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public AjaxResponse delete(Integer confId) {
        if (confId == null) {
            return new AjaxResponseError("操作失败");
        }

        List<String> navTabIds = new ArrayList<String>();
        navTabIds.add(VARIABLE_FIELD_ID);

        boolean isOk = variableFieldService.delete(confId);
        if (isOk) {
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

        if (entity.getConfId() == null || entity.getConfId() == 0) {//新增

            entity.setStatus(AVAILABLE.code());
            entity.setFieldKey(getFieldKey(entity.getDataFieldId()));
            entity.setTableType(T_CASE.code());
            entity.setIsNull(0);
            entity.setIsPullDown(0);
            entity.setIsHide(1);
            entity.setType(0);
            entity.setAlias(getFieldName(entity.getDataFieldId()));

            boolean isOk = variableFieldService.add(entity);
            if (isOk) {
                return getAjaxSuccessAndCloseCurrentDialog("操作成功", navTabIds);
            } else
                return new AjaxResponseError("操作失败");
        } else {//编辑
            return new AjaxResponseError("操作失败");
        }
    }

    /**
     * 获取FieldName
     *
     * @param dataFieldId
     * @return
     */
    @Deprecated
    public String getFieldName(Integer dataFieldId) {
        List<TDataField> tDataFields = dataFieldService.findAll();
        for (TDataField tDataField : tDataFields) {
            if (tDataField.getDataFieldId() == dataFieldId) {
                return tDataField.getFieldName();
            }
        }
        return "";
    }

    /**
     * 获取FieldKey
     *
     * @param dataFieldId
     * @return
     */
    @Deprecated
    public String getFieldKey(Integer dataFieldId) {
        List<TDataField> tDataFields = dataFieldService.findAll();
        for (TDataField tDataField : tDataFields) {
            if (tDataField.getDataFieldId() == dataFieldId) {
                return tDataField.getKey();
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
