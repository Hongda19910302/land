package net.deniro.land.module.system.dao;

import net.deniro.land.common.dao.BaseDao;
import net.deniro.land.module.system.entity.TRegionRelation;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 单位或部门与区域映射关系
 *
 * @author deniro
 *         2016/1/7
 */
@Repository
public class RegionRelationDao extends BaseDao<TRegionRelation> {

    /**
     * 依据单位ID，获取与区域的映射关系
     *
     * @param companyId
     * @return
     */
    public List<TRegionRelation> findByCompanyId(Integer companyId) {
        String hql = "from TRegionRelation t where t.relationType=0 and t" +
                ".relationId=?";
        return this.find(hql, companyId);
    }
}
