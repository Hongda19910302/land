package net.deniro.land.api.entity;

import lombok.Data;

/**
 * 案件 移动端查询参数
 *
 * @author deniro
 *         2015/11/10
 */
@Data
public class CaseMobileQueryParam {
    /**
     * 执行查询操作的人员Id
     */
    private String userId;
    /**
     * 查询类型；
     * 0：案件查询
     * 1：我的案件(我创建的)
     * 2：需要立案审核的案件(预立案状态)
     * 3：需要巡查的案件(巡查制止状态)
     * 4：需要一级结案审核的案件(申请结案)
     * 5：需要二级结案审核的案件(一次结案审核通过)
     * 6: 回收站的案件（被删除）
     */
    private String searchType;

    /**
     * 页码，从1开始算起
     */
    private Integer pageNo;

    /**
     * 每页条数，固定不变
     */
    private Integer limit;
}
