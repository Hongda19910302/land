package net.deniro.land.module.system.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

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
     * 行政地区ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "region_id", nullable = true, length = 10)
    private Integer regionId;

    /**
     * 行政地区名
     */
    @Column(name = "name", nullable = true, length = 120)
    private String name;

    /**
     * 上级行政地区ID
     */
    @Column(name = "parent_id", nullable = true, length = 10)
    private Integer parentId;

    /**
     * 行政地区级别
     */
    @Column(name = "region_level", nullable = true, length = 10)
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
     * 状态0:正常 1:停用
     */
    @Column(name = "status", nullable = true, length = 10)
    private Integer status;

    /**
     * 精度
     */
    @Column(name = "lng", nullable = true, length = 0)
    private String lng;

    /**
     * 纬度
     */
    @Column(name = "lat", nullable = true, length = 0)
    private String lat;
}