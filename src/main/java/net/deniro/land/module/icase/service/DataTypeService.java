package net.deniro.land.module.icase.service;

import net.deniro.land.common.dao.Page;
import net.deniro.land.module.icase.dao.DataTypeDao;
import net.deniro.land.module.icase.entity.DataTypeQueryParam;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 案件下拉项
 *
 * @author deniro
 *         2015/11/4
 */
@Service
public class DataTypeService {

    static Logger logger = Logger.getLogger(DataTypeService.class);

    @Autowired
    private DataTypeDao dataTypeDao;

    /**
     * 分页查询
     *
     * @param queryParam 查询参数
     * @return
     */
    public Page findPage(DataTypeQueryParam queryParam) {
        try {
            return dataTypeDao.findPage(queryParam);
        } catch (Exception e) {
            logger.error("分页查询", e);
            return new Page();
        }
    }
}


