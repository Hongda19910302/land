package net.deniro.land.module.icase.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
* 下拉框数据类型
*
* @author deniro（AnnotationHibernateCodeMarker）
*         2015-11-9
*/
@Data
@Entity
@Table(name = "t_select_type")
public class TSelectType implements Serializable {


    /**
    * 主键ID
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "select_type_id", nullable = true, length = 10)
    private Integer selectTypeId;

    /**
    * 下拉框数据类型名
    */
    @Column(name = "select_type_name", nullable = true, length = 20)
    private String selectTypeName;

    /**
    * 数据库对应类型ID
    */
    @Column(name = "data_type_id", nullable = true, length = 10)
    private Integer dataTypeId;

    /**
    * 注释（不用）
    */
    @Column(name = "comment", nullable = true, length = 100)
    private String comment;

    /**
    * 状态（不用）
    */
    @Column(name = "status", nullable = true, length = 10)
    private Integer status;

    @OneToOne
    @JoinColumn(name = "DATA_TYPE_ID", referencedColumnName = "DATA_TYPE_ID", nullable = true)
    private TDataType dataType;
}
