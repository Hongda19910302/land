package net.deniro.land.module.system.dao;

import net.deniro.land.common.dao.BaseDao;
import net.deniro.land.module.system.entity.TRegion;
import net.deniro.land.module.system.entity.TRegionRelation;
import org.springframework.stereotype.Repository;

import java.util.List;

import static net.deniro.land.module.system.entity.TRegionRelation.RelationType.COMPANY;

/**
 * 行政区域
 *
 * @author deniro
 *         2015/10/28
 */
@Repository
public class RegionDao extends BaseDao<TRegion> {

    /**
     * 依据区域ID，获取子区域列表
     *
     * @param regionId
     * @return
     */
    public List<TRegion> findChildrenByRegionId(Integer regionId) {
        String hql = " from TRegion t where 1=1 ";
        hql += " and t.parentRegion.regionId=? ";
        return this.find(hql, regionId);
    }

    /**
     * 依据单位ID，获取行政区域
     *
     * @param companyId
     * @return
     */
    public List<TRegion> findByCompanyId(Integer companyId) {
        StringBuilder hql = new StringBuilder("select t from TRegionRelation s,TRegion t ");
        hql.append("where t.regionId = s.regionId ");
        hql.append(" and s.relationType=? ");
        hql.append(" and s.relationId=? ");
        hql.append(" order by t.regionLevel");
        return this.find(hql.toString(), COMPANY.code(), companyId);
    }


    /**
     * 根据部门ID或单位ID，获取区域信息
     *
     * @param id           部门ID或单位ID
     * @param relationType 关联类型（部门或者单位）
     * @return
     */
    public List<TRegion> findById(Integer id, TRegionRelation.RelationType relationType) {
        StringBuilder hql = new StringBuilder("select t from TRegionRelation s,TRegion t " +
                "where" +
                " t.regionId=s.regionId");
        hql.append(" and s.relationType=?");
        hql.append(" and s.relationId=?");
        hql.append(" order by t.regionLevel");
        return this.find(hql.toString(), new Object[]{relationType.code(), id});
    }
}
