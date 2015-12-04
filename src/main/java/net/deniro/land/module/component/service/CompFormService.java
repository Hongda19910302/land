package net.deniro.land.module.component.service;

import net.deniro.land.module.component.dao.CompFormDao;
import net.deniro.land.module.component.entity.CompForm;
import net.deniro.land.module.component.entity.CompFormItem;
import net.deniro.land.module.system.entity.DataSetType;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    @Autowired
    private CompFormDao compFormDao;

    @Resource(name = "caseLandUsage")
    private Map<String, String> caseLandUsage;

    /**
     * 查询 表单组件配置信息
     *
     * @param id
     * @return
     */
    public CompForm findById(Integer id) {
        try {
            CompForm form = compFormDao.load(id);

            Set<CompFormItem> items = form.getCompFormItems();
            for (CompFormItem item : items) {
                if (StringUtils.equals(item.getInputType(), "SELECT")) {//下拉选择框数据初始化
                    switch (DataSetType.valueOf(item
                            .getSelectListDataSetType())) {
                        case CASE_LAND_USAGE:
                            item.setSelectListDataSet(caseLandUsage);
                            break;
                    }
                }
            }
            return form;
        } catch (Exception e) {
            logger.error(" 查询 表单组件配置信息", e);
            return new CompForm();
        }
    }
}
