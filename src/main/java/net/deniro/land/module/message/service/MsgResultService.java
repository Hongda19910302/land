package net.deniro.land.module.message.service;

import net.deniro.land.common.dao.Page;
import net.deniro.land.module.message.dao.MsgResultDao;
import net.deniro.land.module.message.entity.MsgResultQueryParam;
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
     * 获取
     *
     * @param msgId
     * @return
     */
    public TMsgResult get(Integer msgId) {
        try {
            return msgResultDao.get(msgId);
        } catch (Exception e) {
            logger.error("获取", e);
            return new TMsgResult();
        }
    }

    /**
     * 更新
     *
     * @param msgResult
     * @return
     */
    public boolean update(TMsgResult msgResult) {
        try {
            msgResultDao.update(msgResult);
            return true;
        } catch (Exception e) {
            logger.error("更新", e);
            return false;
        }
    }

    /**
     * 分页查询
     *
     * @param queryParam 查询参数
     * @return
     */
    public Page findPage(MsgResultQueryParam queryParam) {
        try {
            return msgResultDao.findPage(queryParam);
        } catch (Exception e) {
            logger.error("分页查询", e);
            return new Page();
        }
    }

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
