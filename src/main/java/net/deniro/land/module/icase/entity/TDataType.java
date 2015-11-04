package net.deniro.land.module.icase.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
* 案件下拉项
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
}
