package net.deniro.land.module.icase.entity;

import lombok.Data;

import java.util.List;

/**
 * 模块树结点
 *
 * @author deniro
 *         2016/1/13
 */
@Data
public class ModuleTreeNode {

    private Integer backPrivilegeId;

    /**
     * 菜单项名称（模块名称）
     */
    private String name;

    /**
     * 上级菜单项ID
     */
    private Integer parentId;

    /**
     * 是否为父节点；true：是；false：否
     */
    private String isParent;

    /**
     * 父节点展开图标
     */
    private String iconOpen;

    /**
     * 父节点折叠图标
     */
    private String iconClose;

    /**
     * 叶子节点图标
     */
    private String icon;

    /**
     * 节点的勾选状态
     */
    private String checked="false";

    /**
     * 节点半勾选状态
     */
    private String halfCheck="false";

    /**
     * 子节点
     */
    private List<ModuleTreeNode> children;

    /**
     * 是否展开
     */
    private String open = "true";


}
