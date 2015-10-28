package net.deniro.land.module.system.dao;

import net.deniro.land.common.dao.BaseDao;
import net.deniro.land.module.system.entity.TRegion;
import net.deniro.land.module.system.entity.TRegionRelation;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 行政区域
 *
 * @author deniro
 *         2015/10/28
 */
@Repository
public class RegionDao extends BaseDao<TRegion> {

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
