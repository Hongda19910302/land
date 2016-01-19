package net.deniro.land.module.icase.dao;

import net.deniro.land.common.dao.BaseDao;
import net.deniro.land.common.dao.Page;
import net.deniro.land.module.icase.entity.CaseField;
import net.deniro.land.module.icase.entity.TVariableField;
import net.deniro.land.module.icase.entity.VariableFieldQueryParam;
import net.deniro.land.module.icase.entity.VariableSelectNameValue;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.deniro.land.module.icase.entity.TVariableField.BelongToTable.T_CASE;
import static net.deniro.land.module.icase.entity.TVariableField.Status.AVAILABLE;

/**
 * 字段（主要是案件字段）
 *
 * @author deniro
 *         2015/11/4
 */
@Repository
public class VariableFieldDao extends BaseDao<TVariableField> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * 获取（单位ID+可变字段key）与下拉框名值对映射关系
     *
     * @return
     */
    public List<VariableSelectNameValue> findVariableSelects() {
        StringBuilder sql = new StringBuilder("SELECT x.COMPANY_ID,x.FIELD_KEY,");
        sql.append(" t.DATA_TYPE_VALUE,t.DATA_TYPE_NAME");
        sql.append(" FROM t_variable_field x");
        sql.append(" LEFT JOIN");
        sql.append(" t_select_type_conf y");
        sql.append(" ON x.VARIABLE_FIELD_ID=y.VARIABLE_FIELD_ID");
        sql.append(" LEFT JOIN");
        sql.append(" t_select_type z ON y.SELECT_TYPE_ID=z.SELECT_TYPE_ID");
        sql.append(" LEFT JOIN t_data_type t ON z.DATA_TYPE_ID=t.DATA_TYPE_ID");
        sql.append(" WHERE x.TABLE_TYPE=0 AND t.DATA_TYPE_VALUE is not null and x" +
                ".status=0");
        sql.append(" ORDER BY x.COMPANY_ID,x.FIELD_KEY,t.DATA_TYPE_VALUE");

        return jdbcTemplate.query(sql.toString(), new RowMapper() {
            public VariableSelectNameValue mapRow(ResultSet resultSet, int i) throws SQLException {
                VariableSelectNameValue entity = new VariableSelectNameValue();
                entity.setCompanyId(String.valueOf(resultSet.getInt("COMPANY_ID")));
                entity.setFieldKey(resultSet.getString("FIELD_KEY"));
                entity.setSelectName(resultSet.getString("DATA_TYPE_NAME"));
                entity.setSelectValue(resultSet.getString("DATA_TYPE_VALUE"));
                return entity;
            }
        });

    }

    /**
     * 依据单位ID和客户端对应字段key，获取字段信息
     *
     * @param companyId 单位ID
     * @param fieldKey  客户端对应字段key
     * @return
     */
    public TVariableField find(Integer companyId, String fieldKey) {
        StringBuilder hql = new StringBuilder(" from TVariableField t where 1=1 ");
        hql.append(" and company.companyId = ?");
        hql.append(" and fieldKey = ? ");
        List<TVariableField> data = this.find(hql.toString(), companyId, fieldKey);
        TVariableField tVariableField = new TVariableField();
        if (data != null && !data.isEmpty()) {
            tVariableField = data.get(0);
        }
        return tVariableField;
    }

    /**
     * 依据单位ID，获取字段信息
     *
     * @param companyId
     * @return
     */
    public List<TVariableField> findByCompanyId(Integer companyId) {
        StringBuilder hql = new StringBuilder(" from TVariableField t where 1=1 ");
        hql.append(" and company.companyId = ?");
        hql.append(" and tableType =  ").append(T_CASE.code());
        hql.append(" and status =").append(AVAILABLE.code());
        return this.find(hql.toString(), companyId);
    }

    /**
     * 分页查询
     *
     * @param queryParam
     * @return
     */
    @Deprecated
    public Page findPage2(VariableFieldQueryParam queryParam) {
        StringBuilder sql = new StringBuilder(" FROM t_select_type_conf a,t_variable_field b,t_data_type c," +
                "t_data_field d");
        sql.append(" WHERE a.VARIABLE_FIELD_ID=b.VARIABLE_FIELD_ID");
        sql.append(" AND a.SELECT_TYPE_ID=c.DATA_TYPE_ID");
        sql.append(" AND b.DATA_FIELD_ID=d.DATA_FIELD_ID");


        Map<String, Object> params = new HashMap<String, Object>();
        /**
         * 新增查询条件
         */
        if (StringUtils.isNotBlank(queryParam.getFieldName())) {
            sql.append(" and d.FIELD_NAME like '").append(queryParam
                    .getFieldName())
                    .append("%'");
        }
        if (StringUtils.isNotBlank(queryParam.getCompanyId())) {
            sql.append(" and b.COMPANY_ID = '").append(queryParam.getCompanyId()).append("'");
        }

        //查询总数SQL
        String countSql = "select count(1) " + sql.toString();
        int count = namedParameterJdbcTemplate.queryForInt(countSql, params);

        sql.append(" ORDER BY a.CONF_ID DESC");


        //分页
        int start = queryParam.getNumPerPage() * (queryParam
                .getPageNum() - 1);//起始位置
        sql.append(" limit ").append(start).append(",").append
                (queryParam.getNumPerPage());
        String selectSql = " SELECT a.CONF_ID,b.VARIABLE_FIELD_ID,d.FIELD_NAME," +
                "c.DATA_TYPE_ID,c.DATA_TYPE_NAME,b  .COMPANY_ID,b.STATUS,  " +
                "b.ALIAS,b.IS_HIDE " + sql.toString();

        //查询
        List<CaseField> datas = namedParameterJdbcTemplate.query(selectSql, params, new
                RowMapper<CaseField>() {
                    public CaseField mapRow(ResultSet resultSet, int i) throws SQLException {
                        CaseField entity = new CaseField();
                        entity.setConfId(resultSet.getInt("CONF_ID"));
                        entity.setVariableFieldId(resultSet.getInt("VARIABLE_FIELD_ID"));
                        entity.setFieldName(resultSet.getString("FIELD_NAME"));
                        entity.setDataTypeId(resultSet.getInt("DATA_TYPE_ID"));
                        entity.setDataTypeName(resultSet.getString("DATA_TYPE_NAME"));
                        entity.setCompanyId(resultSet.getInt("COMPANY_ID"));
                        entity.setStatus(resultSet.getInt("STATUS"));
                        entity.setAlias(resultSet.getString("ALIAS"));
                        entity.setIsHide(resultSet.getInt("IS_HIDE"));
                        return entity;
                    }
                });

        return new Page(queryParam
                .getNumPerPage(), start, datas, count);

    }

    /**
     * 分页查询
     *
     * @param queryParam 查询参数
     * @return
     */
    public Page findPage(VariableFieldQueryParam queryParam) {
        StringBuilder hql = new StringBuilder(" from TVariableField t where 1=1 ");

        /**
         * 新增查询条件
         */
        if (StringUtils.isNotBlank(queryParam.getFieldName())) {
            hql.append(" and t.dataField.fieldName like '").append(queryParam.getFieldName())
                    .append("%'");
        }
        if (StringUtils.isNotBlank(queryParam.getCompanyId())) {
            hql.append(" and t.companyId = '").append(queryParam.getCompanyId()).append("'");
        }

        hql.append(" order by variableFieldId desc");

        return super.pagedQuery(hql.toString(), queryParam.getPageNum(), queryParam
                .getNumPerPage());
    }
}
