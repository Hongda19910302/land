package net.deniro.land.module.icase.dao;

import net.deniro.land.common.dao.BaseDao;
import net.deniro.land.module.icase.entity.TSelectType;
import org.springframework.stereotype.Repository;

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
}
