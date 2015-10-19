package net.deniro.land.module.system.service;

import net.deniro.land.module.system.dao.MenuDao;
import net.deniro.land.module.system.dao.ModuleSearchCfgDao;
import net.deniro.land.module.system.entity.MenuItem;
import net.deniro.land.module.system.entity.ModuleSearchCfg;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 模块
 *
 * @author deniro
 *         2015/10/19
 */
@Service
public class ModuleService {

    static Logger logger = Logger.getLogger(ModuleService.class);

    @Autowired
    private ModuleSearchCfgDao moduleSearchCfgDao;

    /**
     * 查询
     *
     * @param moduleId 模块ID
     * @return
     */
    public List<ModuleSearchCfg> findByModuleId(Integer moduleId) {
        try {
            return moduleSearchCfgDao.findByModuleId(moduleId);
        } catch (Exception e) {
            logger.error("查询", e);
            return new ArrayList<ModuleSearchCfg>();
        }
    }
}
