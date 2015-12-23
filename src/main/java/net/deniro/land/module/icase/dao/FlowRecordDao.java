package net.deniro.land.module.icase.dao;

import net.deniro.land.common.dao.BaseDao;
import net.deniro.land.module.icase.entity.TCaseFlowRecord;
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
 * 案件流转记录
 *
 * @author deniro
 *         2015/11/12
 */
@Repository
public class FlowRecordDao extends BaseDao<TCaseFlowRecord> {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * 查询案件（返回的对象包含操作者名称、发送者名称、接收者名称）
     * @param caseId
     * @return
     */
    public List<TCaseFlowRecord> findByCaseIdWithName(Integer caseId) {
        StringBuilder sql = new StringBuilder("SELECT w.*,v.NAME operatorName,x.name fromName,y.name toName FROM t_case_flow_record w ");
        sql.append(" LEFT JOIN t_user v ON w.OPERATER_ID=v.USER_ID");
        sql.append(" LEFT JOIN t_user x ON w.FROM_USER_ID=x.USER_ID");
        sql.append(" LEFT JOIN t_user y ON w.TO_USER_ID=y.USER_ID");
        sql.append(" WHERE 1=1");

        Map<String, Object> params = new HashMap<String, Object>();
        if (caseId != null) {
            sql.append(" and case_id=:caseId");
            params.put("caseId", caseId);
        }

        return namedParameterJdbcTemplate.query(sql.toString(), params, new
                RowMapper<TCaseFlowRecord>() {
                    public TCaseFlowRecord mapRow(ResultSet resultSet, int i) throws SQLException {
                        TCaseFlowRecord entity = new TCaseFlowRecord();
                        entity.setCaseFlowRecord(resultSet.getInt("CASE_FLOW_RECORD"));
                        entity.setOperaterId(resultSet.getInt("OPERATER_ID"));
                        entity.setFromUserId(resultSet.getInt("FROM_USER_ID"));
                        entity.setToUserId(resultSet.getInt("TO_USER_ID"));
                        entity.setOperation(resultSet.getString("OPERATION"));
                        entity.setCreateTime(resultSet.getTimestamp("CREATE_TIME"));
                        entity.setOperationType(resultSet.getInt("OPERATION_TYPE"));
                        entity.setCaseId(resultSet.getInt("CASE_ID"));
                        entity.setOperatorName(resultSet.getString("operatorName"));
                        entity.setFromName(resultSet.getString("fromName"));
                        entity.setToName(resultSet.getString("toName"));
                        return entity;
                    }
                });
    }

    /**
     * 通过案件ID，查询流转记录
     *
     * @param caseId
     * @return
     */
    public List<TCaseFlowRecord> findByCaseId(Integer caseId) {
        String hql = " from TCaseFlowRecord where 1=1  and caseId = ?";
        return this.find(hql, caseId);
    }

}
