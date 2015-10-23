package net.deniro.land.module.component.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
* 分页查询组件
*
* @author deniro（AnnotationHibernateCodeMarker）
*         2015-10-23
*/
@Data
@Entity
@Table(name = "comp_page_search")
public class CompPageSearch implements Serializable {


/**
* 主键
*/
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "id", nullable = true, length = 10)
private Integer id;

/**
* 实例名称
*/
@Column(name = "name", nullable = true, length = 100)
private String name;

/**
* 备注
*/
@Column(name = "remarks", nullable = true, length = 500)
private String remarks;
}
