package net.deniro.land.module.system.service;

import net.deniro.land.module.system.dao.ModuleSearchCfgDao;
import net.deniro.land.module.system.dao.ModuleTableCfgDao;
import net.deniro.land.module.system.entity.DataSetType;
import net.deniro.land.module.system.entity.ModuleSearchCfg;
import net.deniro.land.module.system.entity.ModuleTableCfg;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static net.deniro.land.module.system.entity.ModuleSearchCfg.InputType;

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
    private ModuleTableCfgDao moduleTableCfgDao;

    @Autowired
    private CompanyService companyService;

    @Resource(name = "roleStatus")
    private Map<String, String> roleStatus;

    /**
     * 查询 模块分页表格配置
     *
     * @param moduleId 模块ID
     * @return
     */
    public List<ModuleTableCfg> findForTableCfg(Integer moduleId) {
        try {
            List<ModuleTableCfg> cfgs = moduleTableCfgDao.findByModuleId(moduleId);
            for (ModuleTableCfg cfg : cfgs) {
                String dataSetType=cfg
                        .getTransformDataSetType();
                if(StringUtils.isBlank(dataSetType)){
                    continue;
                }
                switch (DataSetType.valueOf(dataSetType)) {
                    case ROLE_STATUS:
                        cfg.setTransformDataSet(roleStatus);
                }
            }
            return cfgs;
        } catch (Exception e) {
            logger.error("查询 模块分页表格配置", e);
            return new ArrayList<ModuleTableCfg>();
        }
    }


    /**
     * 查询 模块查询配置
     *
     * @param moduleId 模块ID
     * @return
     */
    public List<ModuleSearchCfg> findForSearchCfg(Integer moduleId) {
        try {
            List<ModuleSearchCfg> cfgs = moduleSearchCfgDao.findByModuleId(moduleId);


            for (ModuleSearchCfg cfg : cfgs) {
                switch (InputType.valueOf(cfg.getInputType())) {
                    case TEXT:
                        break;
                    case SELECT://填充下拉选择数据
                        switch (DataSetType.valueOf(cfg
                                .getDataSetType())) {
                            case COMPANY:
                                cfg.setSelectListDataSet(companyService
                                        .findAllInSelect());
                                break;
                            case ROLE_STATUS:
                                cfg.setSelectListDataSet(roleStatus);
                        }
                        break;
                }
            }
            return cfgs;
        } catch (Exception e) {
            logger.error("查询 模块查询配置", e);
            return new ArrayList<ModuleSearchCfg>();
        }
    }
}
