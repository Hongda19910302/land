package net.deniro.land.common.entity;

import lombok.Data;

/**
 * 查询参数
 *
 * @author deniro
 *         2015/10/14
 */
@Data
public abstract class QueryParam {

    /**
     * 页号，从1开始
     */
    private int pageNum = 1;

    /**
     * 每页记录条数
     */
    private int numPerPage = 20;

    /**
     * 排序字段名称
     */
    private String orderField;

    /**
     * 升序（asc）或降序（desc）
     */
    private String orderDirection;

    /**
     * 模块ID
     */
    private Integer moduleId;
}
