package net.deniro.land.module.message.entity;

import lombok.Data;
import net.deniro.land.common.entity.QueryParam;

/**
 * 消息
 *
 * @author deniro
 *         2016/1/21
 */
@Data
public class MsgResultQueryParam extends QueryParam {

    /**
     * 是否已删除 0未删 1已删
     */
    private Integer isDel;

    /**
     * 页码，从1开始算起
     */
    private Integer pageNo = 1;

    /**
     * 每页条数，固定不变
     */
    private Integer limit;

    /**
     * 用户ID
     */
    private Integer userId;

}
