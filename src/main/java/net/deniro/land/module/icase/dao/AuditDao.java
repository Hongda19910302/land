package net.deniro.land.module.icase.dao;

import net.deniro.land.common.dao.BaseDao;
import net.deniro.land.module.icase.entity.TCaseAudit;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 案件审查记录
 *
 * @author deniro
 *         2015/11/12
 */
@Repository
public class AuditDao extends BaseDao<TCaseAudit> {

    /**
     * 查询审查记录列表
     *
     * @param caseId 案件ID
     * @return
     */
    public List<TCaseAudit> findByCaseId(Integer caseId) {
        String hql = " from TCaseAudit where 1=1 and caseId = ?";
        return this.find(hql, caseId);
    }
}
