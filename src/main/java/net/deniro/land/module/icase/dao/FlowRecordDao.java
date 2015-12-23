package net.deniro.land.module.icase.dao;

import net.deniro.land.common.dao.BaseDao;
import net.deniro.land.module.icase.entity.TCaseFlowRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 案件流转记录
 *
 * @author deniro
 *         2015/11/12
 */
@Repository
public class FlowRecordDao extends BaseDao<TCaseFlowRecord> {



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
