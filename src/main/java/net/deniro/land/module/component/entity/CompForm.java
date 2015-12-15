package net.deniro.land.module.component.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

/**
* 表单组件
*
* @author deniro（AnnotationHibernateCodeMarker）
*         2015-12-1
*/
@Data
@Entity
@Table(name = "comp_form")
public class CompForm implements Serializable {


    /**
    * 表单组件ID
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = true,length = 11)
    private Integer id;

    /**
     * 按钮栏高度（单位px）
     */
    @Column(name = "button_bar_height", nullable = true,length = 5)
    private Integer buttonBarHeight;

    /**
    * 名称
    */
    @Column(name = "name", nullable = true,length = 30)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "compForm", fetch = FetchType.EAGER)
    @OrderBy("orderNo ASC ")
    private Set<CompFormItem> compFormItems;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "compForm", fetch = FetchType.EAGER)
    @OrderBy("orderNo ASC ")
    private Set<CompFormBtn> compFormBtns;


}
