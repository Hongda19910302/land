package net.deniro.land.module.icase.service;

import net.deniro.land.module.icase.dao.CaseDao;
import net.deniro.land.module.system.entity.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * 依据创建者ID，获取巡查员列表
     *
     * @param creatorId
     * @return
     */
    public List<User> findByCreatorId(Integer creatorId) {
        try {
            return caseDao.findByCreatorId(creatorId);
        } catch (Exception e) {
            logger.error("依据创建者ID，获取巡查员列表", e);
            return new ArrayList<User>();
        }
    }
}
