package net.deniro.land.module.icase.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 数据库对应表字段
 *
 * @author deniro（AnnotationHibernateCodeMarker）
 *         2015-11-4
 */
@Data
@Entity
@Table(name = "t_data_field")
public class TDataField implements Serializable {


    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "data_field_id", nullable = true, length = 10)
    private Integer dataFieldId;

    /**
     * 所属表名
     */
    @Column(name = "table_name", nullable = true, length = 30)
    private String tableName;

    /**
     * 表字段名称
     */
    @Column(name = "table_field", nullable = true, length = 30)
    private String tableField;

    /**
     * 客户端对应的名称
     */
    @Column(name = "key", nullable = true, length = 30)
    private String key;

    /**
     * 字段显示名称
     */
    @Column(name = "field_name", nullable = true, length = 30)
    private String fieldName;
}
