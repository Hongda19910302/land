package net.deniro.land.module.system.service;

import net.deniro.land.common.dao.Page;
import net.deniro.land.common.entity.QueryParam;
import net.deniro.land.module.system.dao.MenuDao;
import net.deniro.land.module.system.dao.RoleDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 角色
 *
 * @author deniro
 *         2015/10/14
 */
@Service
public class RoleService {

    static Logger logger = Logger.getLogger(RoleService.class);

    @Autowired
    private RoleDao roleDao;

    /**
     * 分页查询
     *
     * @param queryParam 查询参数
     * @return
     */
    public Page findPage(QueryParam queryParam) {
        try {
            return roleDao.findPage(queryParam);
        } catch (Exception e) {
            logger.error("分页查询", e);
            return new Page();
        }
    }
}
