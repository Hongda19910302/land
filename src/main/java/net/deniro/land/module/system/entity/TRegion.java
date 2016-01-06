package net.deniro.land.module.system.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 行政区域
 *
 * @author deniro（AnnotationHibernateCodeMarker）
 *         2015-10-28
 */
@Data
@Entity
@Table(name = "t_region")
public class TRegion implements Serializable {


    /**
     * 默认行政区域Code
     */
    public static final String DEFAULT_REGION_CODE = "000000";

    /**
     * 当前选择的区域ID
     */
    @Transient
    private Integer currentRegionId;

    /**
     * 当前选择的节点的父节点区域ID
     */
    @Transient
    private Integer currentRegionParentId;

    /**
     * 操作类型（新增同级区域、新增子区域）
     */
    @Transient
    private String operateType;


    /**
     * 行政地区ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "region_id", nullable = true, length = 11)
    private Integer regionId;

    /**
     * 行政地区名
     */
    @Column(name = "name", nullable = true, length = 120)
    private String name;

    /**
     * 上级行政地区ID
     */
    @Column(name = "parent_id", nullable = true, length = 11)
    private Integer parentId;

    /**
     * 行政地区级别
     */
    @Column(name = "region_level", nullable = true, length = 11)
    private Integer regionLevel;

    /**
     * 行政地区简称
     */
    @Column(name = "short_name", nullable = true, length = 20)
    private String shortName;

    /**
     * 行政地区说明
     */
    @Column(name = "des", nullable = true, length = 100)
    private String des;

    /**
     * 行政地区代码
     */
    @Column(name = "region_code", nullable = true, length = 20)
    private String regionCode;

    /**
     * 状态 0:正常 1:停用
     */
    @Column(name = "status", nullable = true, length = 10)
    private Integer status;

    /**
     * 精度
     */
    @Column(name = "lng", nullable = true, length = 0)
    private BigDecimal lng;

    /**
     * 纬度
     */
    @Column(name = "lat", nullable = true, length = 0)
    private BigDecimal lat;

    /**
     * 上级区域
     */
    @ManyToOne
    @JoinColumn(name = "PARENT_ID", referencedColumnName = "REGION_ID", nullable = true, insertable = false, updatable = false)
    private TRegion parentRegion;

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

    /**
     * 操作类型
     */
    public enum OperateType {
        /**
         * 新增同级区域
         */
        ADD_BROTHER,

        /**
         * 新增子区域
         */
        ADD_CHILD,

        /**
         * 编辑
         */
        EDIT
    }
}
