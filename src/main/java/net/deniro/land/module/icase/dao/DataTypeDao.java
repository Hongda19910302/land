package net.deniro.land.module.icase.dao;

import net.deniro.land.common.dao.BaseDao;
import net.deniro.land.common.dao.Page;
import net.deniro.land.module.icase.entity.DataTypeQueryParam;
import net.deniro.land.module.icase.entity.TDataType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 案件下拉项
 *
 * @author deniro
 *         2015/11/4
 */
@Repository
public class DataTypeDao extends BaseDao<TDataType> {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * 根据可变字段ID，查询下拉项列表
     *
     * @return
     */
    public List<TDataType> findByVariableId(Integer variableId) {
        StringBuilder sql = new StringBuilder("SELECT *");
        sql.append(" FROM t_data_type v WHERE v.DATA_TYPE_ID in(");
        sql.append(" SELECT u.DATA_TYPE_ID FROM t_select_type u");
        sql.append(" WHERE u.SELECT_TYPE_ID in(");
        sql.append(" SELECT t.select_type_id FROM t_select_type_conf t");
        sql.append(" WHERE t.VARIABLE_FIELD_ID=:variableId))");

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("variableId", variableId);

        return namedParameterJdbcTemplate.query(sql.toString(), params, new
                RowMapper<TDataType>() {
                    public TDataType mapRow(ResultSet resultSet, int i) throws SQLException {
                        TDataType entity = new
                                TDataType();
                        entity.setDataTypeId(resultSet.getInt("DATA_TYPE_ID"));
                        entity.setDataTypeName(resultSet.getString("DATA_TYPE_NAME"));
                        entity.setDataTypeValue(resultSet.getInt("DATA_TYPE_VALUE"));
                        return entity;
                    }
                });
    }

    /**
     * 查询所有
     *
     * @return
     */
    public List<TDataType> findAll() {
        StringBuilder hql = new StringBuilder(" from TDataType where 1=1 ");
        return this.find(hql.toString(), new Object[]{});
    }

    /**
     * 依据可变字段key,获取数据键值对
     *
     * @param variableFieldKey
     * @return
     */
    public List<TDataType> findByVariableFieldKey(String variableFieldKey) {
        StringBuilder sql = new StringBuilder("SELECT y.* FROM t_data_type y");
        sql.append(" WHERE y.DATA_TYPE_ID in(");
        sql.append(" SELECT x.DATA_TYPE_ID FROM t_select_type x");
        sql.append(" WHERE x.SELECT_TYPE_ID in(");
        sql.append(" SELECT v.SELECT_TYPE_ID FROM t_select_type_conf v");
        sql.append(" WHERE v.variable_field_id in(");
        sql.append(" SELECT w.variable_field_id FROM t_variable_field w");
        sql.append(" WHERE w.COMPANY_ID=1 AND w.FIELD_KEY=:variableFieldKey)))");

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("variableFieldKey", variableFieldKey);

        return namedParameterJdbcTemplate.query(sql.toString(), params, new
                RowMapper<TDataType>() {
                    public TDataType mapRow(ResultSet resultSet, int i) throws SQLException {
                        TDataType entity = new
                                TDataType();
                        entity.setDataTypeId(resultSet.getInt("DATA_TYPE_ID"));
                        entity.setDataTypeName(resultSet.getString("DATA_TYPE_NAME"));
                        entity.setDataTypeValue(resultSet.getInt("DATA_TYPE_VALUE"));
                        return entity;
                    }
                });
    }

    /**
     * 分页查询
     *
     * @param queryParam 查询参数
     * @return
     */
    public Page findPage(DataTypeQueryParam queryParam) {
        StringBuilder hql = new StringBuilder(" from TDataType t where 1=1 ");

        /**
         * 新增查询条件
         */
        if (StringUtils.isNotBlank(queryParam.getDataTypeName())) {
            hql.append(" and t.dataTypeName like '").append(queryParam.getDataTypeName())
                    .append("%'");
        }


        hql.append(" order by t.dataTypeId desc");

        return super.pagedQuery(hql.toString(), queryParam.getPageNum(), queryParam
                .getNumPerPage());
    }
}
