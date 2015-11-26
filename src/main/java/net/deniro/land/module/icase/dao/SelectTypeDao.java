package net.deniro.land.module.icase.dao;

import net.deniro.land.common.dao.BaseDao;
import net.deniro.land.module.icase.entity.TSelectType;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 下拉框数据类型
 *
 * @author deniro
 *         2015/11/9
 */
@Repository
public class SelectTypeDao extends BaseDao<TSelectType> {

    /**
     * 依据可变字段ID，获取下拉框数据类型
     *
     * @param variableFieldId
     * @return
     */
    public List<TSelectType> findByVariableFieldId(Integer variableFieldId) {
        String hql = "select t from TSelectTypeConf s,TSelectType t where s.selectType.selectTypeId = t.selectTypeId ";
        hql += " and s.variableField.variableFieldId = ? ";
        return this.find(hql, variableFieldId);
    }

    /**
     * 获取下拉框类型列表
     *
     * @param variableFieldId 变量字段ID
     * @param dataTypeValue   数据键值对的值
     * @return
     */
    public List<TSelectType> findListByFieldAndValue(Integer variableFieldId, Integer
            dataTypeValue) {


        StringBuilder hql = new StringBuilder("select t from TSelectTypeConf s,TSelectType t where s.selectType.selectTypeId = t.selectTypeId ");

        LinkedHashMap<String, Object>
                params = new LinkedHashMap<String, Object>();

        if (variableFieldId != null) {
            hql.append(" and s.variableField.variableFieldId = :variableFieldId ");
            params.put("variableFieldId", variableFieldId);
        }

        if (dataTypeValue != null) {
            hql.append(" and t.dataType.dataTypeValue = :dataTypeValue");
            params.put("dataTypeValue", dataTypeValue);
        }

        return this.findByNamedParam(hql.toString(), params);


    }

    /**
     * 获取下拉框类型
     *
     * @param variableFieldId 变量字段ID
     * @param dataTypeValue   数据键值对的值
     * @return
     */
    public TSelectType findByFieldAndValue(Integer variableFieldId, Integer dataTypeValue) {
        List<TSelectType> selectTypeList = findListByFieldAndValue(variableFieldId,
                dataTypeValue);
        if (selectTypeList == null || selectTypeList.isEmpty()) {
            return new TSelectType();
        } else {
            return selectTypeList.get(0);
        }
    }

}
