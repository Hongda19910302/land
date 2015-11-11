package net.deniro.land.module.icase.service;

import net.deniro.land.common.dao.Page;
import net.deniro.land.module.icase.dao.CaseDao;
import net.deniro.land.module.icase.entity.CaseQueryParam;
import net.deniro.land.module.icase.entity.TCase;
import net.deniro.land.module.system.dao.UserDao;
import net.deniro.land.module.system.entity.User;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 案件
 *
 * @author deniro
 *         2015/11/9
 */
@Service
public class CaseService {

    static Logger logger = Logger.getLogger(CaseService.class);

    @Autowired
    private CaseDao caseDao;

    @Autowired
    private UserDao userDao;

    /**
     * 依据ID，获取案件
     *
     * @param caseId
     * @return
     */
    public TCase findById(Integer caseId) {
        try {
            return caseDao.findById(caseId);
        } catch (Exception e) {
            logger.error(" 依据ID，获取案件", e);
            return new TCase();
        }
    }

    /**
     * 分页查询
     *
     * @param queryParam 查询参数
     * @return
     */
    public Page findPage(CaseQueryParam queryParam) {
        try {

            User user = userDao.get(NumberUtils.toInt(queryParam.getUserId()));
            if (user != null) {
                queryParam.setDepartmentId(String.valueOf(user.getDepartmentId()));
            }

            return caseDao.findPage(queryParam);
        } catch (Exception e) {
            logger.error("分页查询", e);
            return new Page();
        }
    }


}
