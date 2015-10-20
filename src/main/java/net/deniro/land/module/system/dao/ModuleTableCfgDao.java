package net.deniro.land.module.system.dao;

import net.deniro.land.common.dao.BaseDao;
import net.deniro.land.module.system.entity.ModuleTableCfg;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 模块分页表格配置
 *
 * @author deniro
 *         2015/10/20
 */
@Repository
public class ModuleTableCfgDao extends BaseDao<ModuleTableCfg> {

    /**
     * 查询
     *
     * @param moduleId 模块ID
     * @return
     */
    public List<ModuleTableCfg> findByModuleId(Integer moduleId) {
        StringBuilder hql = new StringBuilder(" from ModuleTableCfg t where 1=1 ");
        hql.append(" and t.moduleId=").append(moduleId).append(" order by t.orderNo");
        return this.find(hql.toString(), new Object[]{});
    }
}
