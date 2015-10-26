package net.deniro.land.module.component.service;

import net.deniro.land.module.component.dao.CompPageSearchDao;
import net.deniro.land.module.component.entity.CompPageSearch;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 分页查询组件
 *
 * @author deniro
 *         2015/10/26
 */
@Service
public class CompPageSearchService {

    static Logger logger = Logger.getLogger(CompPageSearchService.class);

    @Autowired
    private CompPageSearchDao compPageSearchDao;

    /**
     * 查询 分页查询组件配置信息
     *
     * @param id
     * @return
     */
    public CompPageSearch findById(Integer id) {
        try {
            return compPageSearchDao.load(id);
        } catch (Exception e) {
            logger.error("查询 分页查询组件配置信息", e);
            return new CompPageSearch();
        }
    }
}
