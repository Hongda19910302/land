package net.deniro.land.module.icase.entity;

import net.deniro.land.module.icase.dao.VariableFieldDao;
import org.apache.commons.collections.map.MultiKeyMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * （单位ID+可变字段key）与下拉框名值对映射关系
 *
 * @author deniro
 *         2015/12/4
 */
public class VariableSelectRelation {

    static Logger logger = Logger.getLogger(VariableSelectRelation.class);

    @Autowired
    private VariableFieldDao variableFieldDao;

    /**
     * key：（单位ID+可变字段key）value：下拉框名值对map
     */
    private MultiKeyMap variableSelects = new MultiKeyMap();

    public MultiKeyMap getVariableSelects() {
        return variableSelects;
    }

    /**
     * 初始化
     */
    public void init() {
        List<VariableSelectNameValue> selects = variableFieldDao.findVariableSelects();
        for (VariableSelectNameValue select : selects) {
            String companyId = select.getCompanyId();
            String fieldKey = select.getFieldKey();
            if (variableSelects.containsKey(companyId, fieldKey)) {//放入同一个map中
                LinkedHashMap<String, String> map = (LinkedHashMap<String, String>) variableSelects.get(companyId, fieldKey);
                map.put(select.getSelectValue(), select.getSelectName());
            } else {//新建
                LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
                map.put(select.getSelectValue(), select.getSelectName());
                variableSelects.put(companyId, fieldKey, map);
            }
        }
        logger.debug("variableSelects:" + variableSelects);
        logger.info("（单位ID+可变字段key）与下拉框名值对映射关系初始化完成，共" + variableSelects.size() + "个");
    }
}
