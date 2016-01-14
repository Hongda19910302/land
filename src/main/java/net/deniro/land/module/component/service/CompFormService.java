package net.deniro.land.module.component.service;

import net.deniro.land.module.component.dao.CompFormDao;
import net.deniro.land.module.component.entity.CompForm;
import net.deniro.land.module.component.entity.CompFormItem;
import net.deniro.land.module.component.entity.InputType;
import net.deniro.land.module.icase.entity.VariableSelectRelation;
import net.deniro.land.module.icase.service.DataFieldService;
import net.deniro.land.module.icase.service.DataTypeService;
import net.deniro.land.module.system.entity.DataSetType;
import net.deniro.land.module.system.service.CompanyService;
import net.deniro.land.module.system.service.RoleService;
import org.apache.commons.collections.map.MultiKeyMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * 表单
 *
 * @author deniro
 *         2015/12/1
 */
@Service
public class CompFormService {

    static Logger logger = Logger.getLogger(CompFormService.class);

    /**
     * 传入表单模板的对象名称
     */
    public static final String OBJECT_NAME = "obj";

    @Autowired
    private CompFormDao compFormDao;

    @Autowired
    private VariableSelectRelation variableSelectRelation;

    @Resource(name = "commonStatus")
    private Map<String, String> commonStatus;

    @Resource(name = "isYes")
    private Map<String, String> isYes;

    @Resource(name = "sex")
    private Map<String, String> sex;

    @Resource(name = "variableFieldType")
    private Map<String, String> variableFieldType;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private DataFieldService dataFieldService;

    @Autowired
    private DataTypeService dataTypeService;

    /**
     * 查询 表单组件配置信息
     *
     * @param id
     * @return
     */
    public CompForm findById(Integer id, Integer companyId) {
        try {
            MultiKeyMap selects = variableSelectRelation.getVariableSelects();

            CompForm form = compFormDao.load(id);

            Set<CompFormItem> items = form.getCompFormItems();
            for (CompFormItem item : items) {
                InputType inputType = InputType.valueOf(item.getInputType());
                switch (inputType) {
                    case SELECT://下拉选择框
                        switch (DataSetType.valueOf(item
                                .getSelectListDataSetType())) {
                            case COMMON_STATUS:
                                item.setSelectListDataSet(commonStatus);
                                break;
                            case COMPANY:
                                item.setSelectListDataSet(companyService
                                        .findAllInSelect());
                                break;
                            case CASE_DATA_TYPE:
                                item.setSelectListDataSet(dataFieldService.findAllInSelect());
                                break;
                            case VARIABLE_FIELD_TYPE:
                                item.setSelectListDataSet(variableFieldType);
                                break;
                            case ROLE:
                                item.setSelectListDataSet(roleService.findAllInSelect());
                                break;
                            case SEX:
                                item.setSelectListDataSet(sex);
                                break;
                            case IS_YES:
                                item.setSelectListDataSet(isYes);
                                break;
                            case CASE_SELECT_TYPE:
                                item.setSelectListDataSet(dataTypeService.findAllInSelect());
                                break;

                        }
                        break;
                    case VARIABLE://可变字段
                        LinkedHashMap<String, String> select = (LinkedHashMap<String,
                                String>) selects.get(String.valueOf(companyId), item
                                .getInputName());
                        if (select != null && !select.isEmpty()) {
                            item.setSelectListDataSet(select);
                        }
                        break;
                }

            }
            return form;
        } catch (Exception e) {
            logger.error(" 查询 表单组件配置信息", e);
            return new CompForm();
        }
    }
}
