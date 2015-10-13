package net.deniro.land.module.system.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 菜单项
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
     * 菜单名称
     */
    @Column(name = "NAME", nullable = true, length = 11, insertable = false, updatable = false)
    private String name;

    @Column(name = "URL2", nullable = true, length = 11, insertable = false, updatable =
            false)
    private String url;

    /**
     * 级别
     */
    @Column(name = "LEVEL", nullable = true, length = 11, insertable = false, updatable = false)
    private Integer level;

    /**
     * 上级菜单项id
     */
    @Column(name = "PARENT_ID", nullable = true, length = 11, insertable = false, updatable = false)
    private Integer parentId;

    /**
     * 排序号
     */
    @Column(name = "SORT_NO", nullable = true, length = 11)
    private Integer sortNo;

    /**
     * 父菜单项
     */
    @ManyToOne
    @JoinColumn(name = "PARENT_ID", referencedColumnName = "BACK_PRIVILEGE_ID", nullable = true)
    private MenuItem parentMenuItem;

}
