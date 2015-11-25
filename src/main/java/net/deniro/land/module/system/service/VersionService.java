package net.deniro.land.module.system.service;

import net.deniro.land.common.dao.Page;
import net.deniro.land.module.system.dao.VersionDao;
import net.deniro.land.module.system.dao.VersionItemDao;
import net.deniro.land.module.system.entity.VersionItemQueryParam;
import net.deniro.land.module.system.entity.VersionQueryParam;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 版本说明
 *
 * @author deniro
 *         2015/11/25
 */
@Service
public class VersionService {

    static Logger logger = Logger.getLogger(VersionService.class);

    @Autowired
    private VersionDao versionDao;

    @Autowired
    private VersionItemDao versionItemDao;

    /**
     * 分页查询 版本项
     *
     * @param queryParam 查询参数
     * @return
     */
    public Page findItemsPage(VersionItemQueryParam queryParam) {
        try {
            return versionItemDao.findPage(queryParam);
        } catch (Exception e) {
            logger.error("分页查询 版本项", e);
            return new Page();
        }
    }

    /**
     * 分页查询
     *
     * @param queryParam 查询参数
     * @return
     */
    public Page findPage(VersionQueryParam queryParam) {
        try {
            return versionDao.findPage(queryParam);
        } catch (Exception e) {
            logger.error("分页查询", e);
            return new Page();
        }
    }
}
