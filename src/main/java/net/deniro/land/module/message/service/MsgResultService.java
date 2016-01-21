package net.deniro.land.module.message.service;

import net.deniro.land.module.message.dao.MsgResultDao;
import net.deniro.land.module.message.entity.TMsgResult;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 消息
 *
 * @author deniro
 *         2016/1/21
 */
@Service
public class MsgResultService {

    static Logger logger = Logger.getLogger(MsgResultService.class);

    @Autowired
    private MsgResultDao msgResultDao;

    /**
     * 新增
     *
     * @param entity
     * @return
     */
    public boolean add(TMsgResult entity) {
        if (entity == null) return false;

        try {
            msgResultDao.save(entity);
            return true;
        } catch (Exception e) {
            logger.error("新增", e);
            return false;
        }
    }
}
