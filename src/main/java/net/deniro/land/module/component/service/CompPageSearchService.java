package net.deniro.land.module.component.service;

import net.deniro.land.module.component.dao.CompPageSearchDao;
import net.deniro.land.module.component.entity.CompPageSearch;
import net.deniro.land.module.component.entity.CompPageSearchForm;
import net.deniro.land.module.component.entity.CompPageSearchTable;
import net.deniro.land.module.component.entity.InputType;
import net.deniro.land.module.system.entity.DataSetType;
import net.deniro.land.module.system.entity.ModuleSearchCfg;
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
