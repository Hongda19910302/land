package net.deniro.land.module.system.dao;

import net.deniro.land.common.dao.BaseDao;
import net.deniro.land.module.system.entity.ModuleSearchCfg;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 模块查询配置
 *
 * @author deniro
 *         2015/10/19
 */
@Repository
@Deprecated
public class ModuleSearchCfgDao extends BaseDao<ModuleSearchCfg> {

    /**
     * 查询
     *
     * @param moduleId 模块ID
     * @return
     */
    public List<ModuleSearchCfg> findByModuleId(Integer moduleId) {
        StringBuilder hql = new StringBuilder(" from ModuleSearchCfg t where 1=1 ");
        hql.append(" and t.moduleId=").append(moduleId).append(" order by t.orderNo");
        return this.find(hql.toString(), new Object[]{});
    }
}
