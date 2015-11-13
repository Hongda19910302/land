package net.deniro.land.module.icase.dao;

import net.deniro.land.common.dao.BaseDao;
import net.deniro.land.module.icase.entity.TInspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 巡查记录
 *
 * @author deniro
 *         2015/11/12
 */
@Repository
public class InspectDao extends BaseDao<TInspect> {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * 获取巡查记录列表
     *
     * @param caseId 案件ID
     * @return
     */
    public List<TInspect> findByCaseId(Integer caseId) {
        String hql = " from TInspect where 1=1 and caseId = ?";
        return this.find(hql, caseId);
    }

    /**
     * 根据案件ID，统计案件的巡查记录数
     *
     * @param caseId 案件ID
     * @return
     */
    public Integer countByCaseId(Integer caseId) {
        String sql = "select count(*) from t_inspect where case_id = :caseId";

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("caseId", caseId);

        return namedParameterJdbcTemplate.queryForInt(sql, params);
    }
}
