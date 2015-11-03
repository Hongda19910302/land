package net.deniro.land.module.component.entity;

import lombok.Data;

/**
 * 单位树节点
 *
 * @author deniro
 *         2015/11/2
 */
@Data
public class CompanyTreeNode {

    /**
     * 主键
     */
    private String companyId;

    /**
     * 名称
     */
    private String name;
}
