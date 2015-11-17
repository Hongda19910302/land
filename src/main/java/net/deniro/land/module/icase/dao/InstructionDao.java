package net.deniro.land.module.icase.dao;

import net.deniro.land.common.dao.BaseDao;
import net.deniro.land.common.dao.Page;
import net.deniro.land.module.icase.entity.InstructionQueryParam;
import net.deniro.land.module.icase.entity.TInstruction;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * 案件批示
 *
 * @author deniro
 *         2015/11/16
 */
@Repository
public class InstructionDao extends BaseDao<TInstruction> {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * 统计案件批示条数
     *
     * @param queryParam 案件批示查询参数
     * @return
     */
    public Integer count(InstructionQueryParam queryParam) {
        StringBuilder sql = new StringBuilder(" select count(1) from t_instruction t " +
                "where" +
                " " +
                "1=1 ");

        Map<String, Object> params = new HashMap<String, Object>();

        /**
         * 新增查询条件
         */
        if (StringUtils.isNotBlank(queryParam.getStatus())) {
            sql.append(" and t.status =:status ");
            params.put("status", queryParam.getStatus());
        }
        if (StringUtils.isNotBlank(queryParam.getCaseId())) {
            sql.append(" and t.case_id =:caseId ");
            params.put("caseId", queryParam.getCaseId());
        }
        if (StringUtils.isNotBlank(queryParam.getUserId())) {
            sql.append(" and t.user_id =:userId ");
            params.put("userId", queryParam.getUserId());
        }

        return namedParameterJdbcTemplate.queryForInt(sql.toString(), params);
    }

    /**
     * 分页查询
     *
     * @param queryParam 查询参数
     * @return
     */
    public Page findPage(InstructionQueryParam queryParam) {
        StringBuilder hql = new StringBuilder(" from TInstruction t where 1=1 ");

        /**
         * 新增查询条件
         */
        if (StringUtils.isNotBlank(queryParam.getStatus())) {
            hql.append(" and t.status = '").append(queryParam.getStatus())
                    .append("'");
        }
        if (StringUtils.isNotBlank(queryParam.getCaseId())) {
            hql.append(" and t.caseId = '").append(queryParam.getCaseId()).append("'");
        }

        hql.append(" order by instructionDate desc");

        return super.pagedQuery(hql.toString(), queryParam.getPageNum(), queryParam
                .getNumPerPage());
    }

}
