package net.deniro.land.module.icase.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 下拉框数据类型与可变字段映射关系
 *
 * @author deniro（AnnotationHibernateCodeMarker）
 *         2015-11-9
 */
@Data
@Entity
@Table(name = "t_select_type_conf")
public class TSelectTypeConf implements Serializable {


    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "conf_id", nullable = true, length = 10)
    private Integer confId;

    /**
     * 可变字段ID
     */
    @Column(name = "variable_field_id", nullable = true, length = 10)
    private Integer variableFieldId;

    /**
     * 下拉框数据类型ID
     */
    @Column(name = "select_type_id", nullable = true, length = 10)
    private Integer selectTypeId;

    @ManyToOne
    @JoinColumn(name = "VARIABLE_FIELD_iD", referencedColumnName = "VARIABLE_FIELD_ID", nullable = true)
    private TVariableField variableField;

    @ManyToOne
    @JoinColumn(name = "SELECT_TYPE_ID", referencedColumnName = "SELECT_TYPE_ID", nullable = true)
    private TSelectType selectType;
}
