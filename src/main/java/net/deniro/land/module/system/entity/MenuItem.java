package net.deniro.land.module.system.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单项（即模块）
 *
 * @author deniro
 *         2015/10/12
 */
@Data
@Entity
@Table(name = "T_BACK_PRIVILEGE")
public class MenuItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BACK_PRIVILEGE_ID", unique = true, nullable = false)
    private Integer backPrivilegeId;

    /**
     * 菜单项名称（模块名称）
     */
    @Column(name = "NAME", nullable = true, length = 20, insertable = false, updatable =
            false)
    private String name;

    /**
     * action url地址
     */
    @Column(name = "URL2", nullable = true, length = 200, insertable = false, updatable =
            false)
    private String url;

    /**
     * 级别
     */
    @Column(name = "LEVEL", nullable = true, length = 2, insertable = false, updatable =
            false)
    private Integer level;

    /**
     * 上级菜单项ID
     */
    @Column(name = "PARENT_ID", nullable = true, length = 11, insertable = false, updatable = false)
    private Integer parentId;

    /**
     * 排序号
     */
    @Column(name = "SORT_NO", nullable = true, length = 11)
    private Integer sortNo;

    /**
     * 子菜单项
     */
    @Transient
    private List<MenuItem> child = new ArrayList<MenuItem>();

    /**
     * 是否为父节点；true：是；false：否
     */
    @Getter
    @Setter
    @Transient
    private String isParent;

    /**
     * 父节点展开图标
     */
    @Getter
    @Setter
    @Transient
    private String iconOpen;

    /**
     * 父节点折叠图标
     */
    @Getter
    @Setter
    @Transient
    private String iconClose;

    /**
     * 叶子节点图标
     */
    @Getter
    @Setter
    @Transient
    private String icon;

}
