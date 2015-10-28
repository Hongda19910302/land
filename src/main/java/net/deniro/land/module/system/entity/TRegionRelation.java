package net.deniro.land.module.system.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 单位或部门与区域映射关系
 *
 * @author deniro（AnnotationHibernateCodeMarker）
 *         2015-10-28
 */
@Data
@Entity
@Table(name = "t_region_relation")
public class TRegionRelation implements Serializable {


    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "region_relation_id", nullable = true, length = 10)
    private Integer regionRelationId;

    /**
     * 关联类型 0:单位表 1:部门表
     */
    @Column(name = "relation_type", nullable = true, length = 10)
    private Integer relationType;

    /**
     * 关联id（单位ID或部门ID）
     */
    @Column(name = "relation_id", nullable = true, length = 10)
    private Integer relationId;

    /**
     * 行政区域id
     */
    @Column(name = "region_id", nullable = true, length = 10)
    private Integer regionId;
}
