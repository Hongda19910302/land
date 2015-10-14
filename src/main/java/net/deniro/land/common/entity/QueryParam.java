package net.deniro.land.common.entity;

import lombok.Data;

/**
 * 查询参数
 *
 * @author deniro
 *         2015/10/14
 */
@Data
public class QueryParam {

    /**
     * 页号，从1开始
     */
    private int pageNo = 1;

    /**
     * 每页记录条数
     */
    private int pageSize = 20;
}
