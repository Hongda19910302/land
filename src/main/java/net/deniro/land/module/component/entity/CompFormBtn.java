package net.deniro.land.module.component.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
*  表单按钮
*
* @author deniro（AnnotationHibernateCodeMarker）
*         2015-12-15
*/
@Setter
@Getter
@Entity
@Table(name = "comp_form_btn")
public class CompFormBtn implements Serializable {


    /**
    * 表单按钮主键
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = true,length = 11)
    private Integer id;

    /**
    * 按钮名称
    */
    @Column(name = "name", nullable = true,length = 30)
    private String name;

    /**
     * 按钮样式
     */
    @Column(name = "btn_class", nullable = true,length = 30)
    private String btnClass;

    /**
    * 按钮类型；submit-提交按钮；button-普通按钮
    */
    @Column(name = "type", nullable = true,length = 10)
    private String type;

    /**
    * action的URL
    */
    @Column(name = "action_url", nullable = true,length = 300)
    private String actionUrl;

    /**
     * 排序号
     */
    @Column(name = "order_no", nullable = true, length = 10)
    private Integer orderNo;

    @ManyToOne
    @JoinColumn(name = "form_id")
    private CompForm compForm;
}
