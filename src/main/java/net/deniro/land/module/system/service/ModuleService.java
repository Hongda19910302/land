package net.deniro.land.module.system.service;

import net.deniro.land.module.system.dao.MenuDao;
import net.deniro.land.module.system.dao.ModuleSearchCfgDao;
import net.deniro.land.module.system.entity.Company;
import net.deniro.land.module.system.entity.MenuItem;
import net.deniro.land.module.system.entity.ModuleSearchCfg;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static net.deniro.land.module.system.entity.ModuleSearchCfg.InputType;
import static net.deniro.land.module.system.entity.ModuleSearchCfg.SelectListDataSetType;

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

    @Autowired
    private CompanyService companyService;

    /**
     * 查询
     *
     * @param moduleId 模块ID
     * @return
     */
    public List<ModuleSearchCfg> findByModuleId(Integer moduleId) {
        try {
            List<ModuleSearchCfg> cfgs = moduleSearchCfgDao.findByModuleId(moduleId);


            for (ModuleSearchCfg cfg : cfgs) {
                switch (InputType.valueOf(cfg.getInputType())) {
                    case TEXT:
                        break;
                    case SELECT://填充下拉选择数据
                        switch (SelectListDataSetType.valueOf(cfg
                                .getSelectListDataSetType())) {
                            case COMPANY:
                                cfg.setSelectListDataSet(companyService
                                        .findAllInSelect());
                                break;
                        }
                        break;
                }
            }
            return cfgs;
        } catch (Exception e) {
            logger.error("查询", e);
            return new ArrayList<ModuleSearchCfg>();
        }
    }
}
