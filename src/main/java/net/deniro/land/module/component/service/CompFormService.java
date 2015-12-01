package net.deniro.land.module.component.service;

import net.deniro.land.module.component.dao.CompFormDao;
import net.deniro.land.module.component.entity.CompForm;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 表单
 *
 * @author deniro
 *         2015/12/1
 */
@Service
public class CompFormService {

    static Logger logger = Logger.getLogger(CompFormService.class);

    @Autowired
    private CompFormDao compFormDao;

    /**
     * 查询 表单组件配置信息
     *
     * @param id
     * @return
     */
    public CompForm findById(Integer id) {
        try {
            return compFormDao.load(id);
        } catch (Exception e) {
            logger.error(" 查询 表单组件配置信息", e);
            return new CompForm();
        }
    }
}
