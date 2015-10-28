package net.deniro.land.module.component.entity;

import lombok.Data;

/**
 * 树型控件查询参数
 *
 * @author deniro
 *         2015/10/28
 */
@Data
public class TreeQueryParam {

    /**
     * 单位ID
     */
    private Integer companyId;

    /**
     * 部门ID
     */
    private String departmentId;

}
