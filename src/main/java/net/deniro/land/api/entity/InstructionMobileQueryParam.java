package net.deniro.land.api.entity;

import lombok.Data;

/**
 * 案件批示列表查询参数
 *
 * @author deniro
 *         2015/11/16
 */
@Data
public class InstructionMobileQueryParam {

    /**
     * 执行查询操作的人员Id
     */
    private Integer userId;

    /**
     * 案件ID
     */
    private Integer caseId;

    /**
     * 页码，从1开始算起
     */
    private Integer pageNo = 1;

    /**
     * 每页条数，固定不变
     */
    private Integer limit;
}
