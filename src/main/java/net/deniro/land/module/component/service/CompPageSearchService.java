package net.deniro.land.module.component.service;

import net.deniro.land.common.dao.Page;
import net.deniro.land.module.component.dao.CompPageSearchDao;
import net.deniro.land.module.component.dao.CompPageSearchFormDao;
import net.deniro.land.module.component.dao.CompPageSearchTableDao;
import net.deniro.land.module.component.dao.CompPageSearchToolBarDao;
import net.deniro.land.module.component.entity.*;
import net.deniro.land.module.system.entity.DataSetType;
import net.deniro.land.module.system.service.CompanyService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;

/**
 * 分页查询组件
 *
 * @author deniro
 *         2015/10/26
 */
@Service
public class CompPageSearchService {

    static Logger logger = Logger.getLogger(CompPageSearchService.class);

    @Autowired
    private CompPageSearchDao compPageSearchDao;

    @Autowired
    private CompanyService companyService;

    @Resource(name = "commonStatus")
    private Map<String, String> commonStatus;

    @Resource(name = "roleStatus")
    private Map<String, String> roleStatus;

    @Resource(name = "hasStatus")
    private Map<String, String> hasStatus;

    @Resource(name = "userStatus")
    private Map<String, String> userStatus;

    @Resource(name = "sex")
    private Map<String, String> sex;

    @Resource(name = "variableFieldType")
    private Map<String, String> variableFieldType;

    @Resource(name = "isYes")
    private Map<String, String> isYes;

    @Resource(name = "caseIsUpload")
    private Map<String, String> caseIsUpload;

    @Resource(name = "caseStatus")
    private Map<String, String> caseStatus;

    @Resource(name = "versionType")
    private Map<String, String> versionType;

    @Resource(name = "inputType")
    private Map<String, String> inputType;

    @Resource(name = "dataSetType")
    private Map<String, String> dataSetTypes;

    @Resource(name = "PageSearchBtnOpenType")
    private Map<String, String> pageSearchBtnOpenType;

    @Autowired
    private CompPageSearchFormDao compPageSearchFormDao;

    @Autowired
    private CompPageSearchTableDao compPageSearchTableDao;

    @Autowired
    private CompPageSearchToolBarDao compPageSearchToolBarDao;

    /**
     * 分页查询组件中的工具栏
     *
     * @param queryParam 查询参数
     * @return
     */
    public Page findPage(CompPageSearchToolBarQueryParam queryParam) {
        try {
            return compPageSearchToolBarDao.findPage(queryParam);
        } catch (Exception e) {
            logger.error("分页查询组件中的工具栏", e);
            return new Page();
        }
    }

    /**
     * 分页查询组件中的分页表格
     *
     * @param queryParam 查询参数
     * @return
     */
    public Page findPage(CompPageSearchTableQueryParam queryParam) {
        try {
            return compPageSearchTableDao.findPage(queryParam);
        } catch (Exception e) {
            logger.error("分页查询组件中的分页表格", e);
            return new Page();
        }
    }

    /**
     * 分页查询组件中的查询表单
     *
     * @param queryParam 查询参数
     * @return
     */
    public Page findPage(CompPageSearchFormQueryParam queryParam) {
        try {
            return compPageSearchFormDao.findPage(queryParam);
        } catch (Exception e) {
            logger.error("分页查询组件中的查询表单", e);
            return new Page();
        }
    }

    /**
     * 分页查询
     *
     * @param queryParam 查询参数
     * @return
     */
    public Page findPage(CompPageSearchQueryParam queryParam) {
        try {
            return compPageSearchDao.findPage(queryParam);
        } catch (Exception e) {
            logger.error("分页查询", e);
            return new Page();
        }
    }

    /**
     * 查询 分页查询组件配置信息
     *
     * @param id
     * @return
     */
    public CompPageSearch findById(Integer id) {
        try {
            CompPageSearch pageSearch = compPageSearchDao.load(id);

            /**
             * 处理表格中的数据转换
             */
            Set<CompPageSearchTable> tableFields = pageSearch
                    .getCompPageSearchTableFields();
            for (CompPageSearchTable field : tableFields) {
                String dataSetType = field
                        .getTransformDataSetType();
                if (StringUtils.isBlank(dataSetType)) {
                    continue;
                }
                switch (DataSetType.valueOf(dataSetType)) {
                    case COMMON_STATUS:
                        field.setTransformDataSet(commonStatus);
                        break;
                    case ROLE_STATUS:
                        field.setTransformDataSet(roleStatus);
                        break;
                    case HAS_STATUS:
                        field.setTransformDataSet(hasStatus);
                        break;
                    case SEX:
                        field.setTransformDataSet(sex);
                        break;
                    case USER_STATUS:
                        field.setTransformDataSet(userStatus);
                        break;
                    case VARIABLE_FIELD_TYPE:
                        field.setTransformDataSet(variableFieldType);
                        break;
                    case IS_YES:
                        field.setTransformDataSet(isYes);
                        break;
                    case CASE_IS_UPLOAD:
                        field.setTransformDataSet(caseIsUpload);
                        break;
                    case CASE_STATUS:
                        field.setTransformDataSet(caseStatus);
                        break;
                    case VERSION_TYPE:
                        field.setTransformDataSet(versionType);
                        break;
                    case INPUT_TYPE:
                        field.setTransformDataSet(inputType);
                        break;
                    case DATA_SET_TYPE:
                        field.setTransformDataSet(dataSetTypes);
                        break;
                    case PAGE_SEARCH_BTN_OPEN_TYPE:
                        field.setTransformDataSet(pageSearchBtnOpenType);
                        break;
                    case COMPANY:
                        field.setTransformDataSet(companyService
                                .findAllInSelect());
                        break;


                }
            }
            pageSearch.setCompPageSearchTableFields(tableFields);

            /**
             * 处理查询表单中的数据集转换
             */
            Set<CompPageSearchForm> formFields = pageSearch.getCompPageSearchFormFields();
            for (CompPageSearchForm field : formFields) {
                switch (InputType.valueOf(field.getInputType())) {
                    case TEXT:
                        break;
                    case SELECT://填充下拉选择数据
                        switch (DataSetType.valueOf(field
                                .getSelectListDataSetType())) {
                            case COMMON_STATUS:
                                field.setSelectListDataSet(commonStatus);
                                break;
                            case COMPANY:
                                field.setSelectListDataSet(companyService
                                        .findAllInSelect());
                                break;
                            case ROLE_STATUS:
                                field.setSelectListDataSet(roleStatus);
                                break;
                            case HAS_STATUS:
                                field.setSelectListDataSet(hasStatus);
                                break;
                            case SEX:
                                field.setSelectListDataSet(sex);
                                break;
                            case USER_STATUS:
                                field.setSelectListDataSet(userStatus);
                                break;
                            case VARIABLE_FIELD_TYPE:
                                field.setSelectListDataSet(variableFieldType);
                                break;
                            case IS_YES:
                                field.setSelectListDataSet(isYes);
                                break;
                            case CASE_IS_UPLOAD:
                                field.setSelectListDataSet(caseIsUpload);
                                break;
                            case CASE_STATUS:
                                field.setSelectListDataSet(caseStatus);
                                break;
                            case VERSION_TYPE:
                                field.setSelectListDataSet(versionType);
                                break;
                        }
                        break;
                }
            }

            return pageSearch;
        } catch (Exception e) {
            logger.error("查询 分页查询组件配置信息", e);
            return new CompPageSearch();
        }
    }
}
