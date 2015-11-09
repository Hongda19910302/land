package net.deniro.land.module.icase.service;

import net.deniro.land.common.dao.Page;
import net.deniro.land.module.icase.dao.VariableFieldDao;
import net.deniro.land.module.icase.entity.TVariableField;
import net.deniro.land.module.icase.entity.VariableFieldQueryParam;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 案件字段
 *
 * @author deniro
 *         2015/11/4
 */
@Service
public class VariableFieldService {

    static Logger logger = Logger.getLogger(VariableFieldService.class);

    @Autowired
    private VariableFieldDao variableFieldDao;

    /**
     * 依据单位ID，获取字段信息
     *
     * @param companyId
     * @return
     */
    public List<TVariableField> findByCompanyId(Integer companyId) {
        try {
            return variableFieldDao.findByCompanyId(companyId);
        } catch (Exception e) {
            logger.error("依据单位ID，获取字段信息", e);
            return new ArrayList<TVariableField>();
        }
    }

    /**
     * 分页查询
     *
     * @param queryParam 查询参数
     * @return
     */
    public Page findPage(VariableFieldQueryParam queryParam) {
        try {
            return variableFieldDao.findPage(queryParam);
        } catch (Exception e) {
            logger.error("分页查询", e);
            return new Page();
        }
    }
}
