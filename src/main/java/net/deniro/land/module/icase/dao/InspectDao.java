package net.deniro.land.module.icase.dao;

import net.deniro.land.common.dao.BaseDao;
import net.deniro.land.module.icase.entity.TInspect;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 巡查记录
 *
 * @author deniro
 *         2015/11/12
 */
@Repository
public class InspectDao extends BaseDao<TInspect> {

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
}
