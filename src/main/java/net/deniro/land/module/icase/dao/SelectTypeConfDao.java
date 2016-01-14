package net.deniro.land.module.icase.dao;

import net.deniro.land.common.dao.BaseDao;
import net.deniro.land.module.icase.entity.TSelectTypeConf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * 下拉框数据类型与可变字段映射关系
 *
 * @author deniro
 *         2016/1/14
 */
@Repository
public class SelectTypeConfDao extends BaseDao<TSelectTypeConf> {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * 插入
     *
     * @param variableFieldId
     * @param dataTypeId
     * @return
     */
    public int insert(Integer variableFieldId, Integer dataTypeId) {
        String sql = "insert into t_select_type_conf(VARIABLE_FIELD_ID,SELECT_TYPE_ID) " +
                "values(:variableFieldId,:dataTypeId)";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("variableFieldId", variableFieldId);
        params.put("dataTypeId", dataTypeId);

        return namedParameterJdbcTemplate.update(sql, params);
    }

    /**
     * 更新
     *
     * @param variableFieldId
     * @param dataTypeId
     * @return
     */
    public int update(Integer variableFieldId, Integer dataTypeId) {
        String sql = "update t_select_type_conf set SELECT_TYPE_ID=:dataTypeId " +
                "where VARIABLE_FIELD_ID=:variableFieldId";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("variableFieldId", variableFieldId);
        params.put("dataTypeId", dataTypeId);

        return namedParameterJdbcTemplate.update(sql, params);
    }

    /**
     * 依据可变字段ID，删除所有记录
     *
     * @param variableFieldId
     * @return
     */
    @Deprecated
    public int deleteAllByVariableFieldId(Integer variableFieldId) {
        String sql = "delete from t_select_type_conf where " +
                "VARIABLE_FIELD_ID=:variableFieldId";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("variableFieldId", variableFieldId);

        return namedParameterJdbcTemplate.update(sql, params);
    }


}
