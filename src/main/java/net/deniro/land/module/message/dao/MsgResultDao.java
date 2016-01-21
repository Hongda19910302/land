package net.deniro.land.module.message.dao;

import net.deniro.land.common.dao.BaseDao;
import net.deniro.land.common.dao.Page;
import net.deniro.land.module.message.entity.MsgResultQueryParam;
import net.deniro.land.module.message.entity.TMsgResult;
import org.springframework.stereotype.Repository;

/**
 * 消息
 *
 * @author deniro
 *         2016/1/21
 */
@Repository
public class MsgResultDao extends BaseDao<TMsgResult> {

    /**
     * 分页查询
     *
     * @param queryParam 查询参数
     * @return
     */
    public Page findPage(MsgResultQueryParam queryParam) {
        StringBuilder hql = new StringBuilder(" from TMsgResult t where 1=1 ");

        /**
         * 新增查询条件
         */
        if (queryParam.getIsDel() != null) {
            hql.append(" and t.isDel = ").append(queryParam.getIsDel());
        }
        if (queryParam.getUserId() != null) {
            hql.append(" and t.userId = ").append(queryParam.getUserId());
        }

        hql.append(" order by createTime desc");

        return super.pagedQuery(hql.toString(), queryParam.getPageNum(), queryParam
                .getNumPerPage());
    }
}
