package net.deniro.land.module.icase.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
* 数据键值对
*
* @author deniro（AnnotationHibernateCodeMarker）
*         2015-11-4
*/
@Data
@Entity
@Table(name = "t_data_type")
public class TDataType implements Serializable {


    /**
    * 主键ID
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "data_type_id", nullable = true, length = 10)
    private Integer dataTypeId;

    /**
    * 数据库对应类型名
    */
    @Column(name = "data_type_name", nullable = true, length = 20)
    private String dataTypeName;

    /**
    * 数据库对应类型值
    */
    @Column(name = "data_type_value", nullable = true, length = 10)
    private Integer dataTypeValue;

    /**
     * 是否为父节点；true：是；false：否
     */
    @Transient
    private String isParent;

    /**
     * 叶子节点图标
     */
    @Transient
    private String icon;

    /**
     * 节点的勾选状态
     */
    @Transient
    private String checked = "false";

    /**
     * 树型结点ID
     */
    @Transient
    private Integer id;

    /**
     * 树型结点名称
     */
    @Transient
    private String name;
}
