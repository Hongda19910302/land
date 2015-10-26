package net.deniro.land.module.component.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 分页查询表单
 * <p>
 * 这里不可加@Data注释，lombok插件生成的hashCode方法与hibernate互相冲突
 *
 * @author deniro（AnnotationHibernateCodeMarker）
 *         2015-10-23
 */
@Setter
@Getter
@Entity
@Table(name = "comp_page_search_form")
public class CompPageSearchForm implements Serializable {


    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = true, length = 10)
    private Integer id;

    /**
     * 查询字段名称
     */
    @Column(name = "field_name", nullable = true, length = 100)
    private String fieldName;

    /**
     * 查询字段显示名称
     */
    @Column(name = "display_name", nullable = true, length = 100)
    private String displayName;

    /**
     * 输入框类型；TEXT-文本输入框；SELECT-下拉选择框
     */
    @Column(name = "input_type", nullable = true, length = 50)
    private String inputType;


    /**
     * 排序号
     */
    @Column(name = "order_no", nullable = true, length = 10)
    private Integer orderNo;

    /**
     * 下拉框数据集类型
     */
    @Column(name = "select_list_data_set_type", nullable = true, length = 50)
    private String selectListDataSetType;

    @ManyToOne
    @JoinColumn(name = "comp_page_search_id")
    private CompPageSearch compPageSearch;

    @Override
    public String toString() {
        return "CompPageSearchForm{" +
                "selectListDataSetType='" + selectListDataSetType + '\'' +
                ", id=" + id +
                ", fieldName='" + fieldName + '\'' +
                ", displayName='" + displayName + '\'' +
                ", inputType='" + inputType + '\'' +
                ", orderNo=" + orderNo +
                '}';
    }
}
