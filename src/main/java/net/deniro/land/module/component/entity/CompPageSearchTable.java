package net.deniro.land.module.component.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 分页查询表格
 *
 * @author deniro（AnnotationHibernateCodeMarker）
 *         2015-10-23
 */
@Setter
@Getter
@Entity
@Table(name = "comp_page_search_table")
public class CompPageSearchTable implements Serializable {


    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = true, length = 10)
    private Integer id;

    /**
     * 字段名称
     */
    @Column(name = "field_name", nullable = true, length = 100)
    private String fieldName;

    /**
     * 字段显示名称
     */
    @Column(name = "display_name", nullable = true, length = 100)
    private String displayName;

    /**
     * 宽度
     */
    @Column(name = "width", nullable = true, length = 10)
    private Integer width;

    /**
     * css样式
     */
    @Column(name = "style", nullable = true, length = 100)
    private String style;

    /**
     * 排序号
     */
    @Column(name = "order_no", nullable = true, length = 10)
    private Integer orderNo;


    /**
     * 转换数据集类型
     */
    @Column(name = "transform_data_set_type", nullable = true, length = 50)
    private String transformDataSetType;

    /**
     * 是否为主键
     */
    @Column(name = "is_key", nullable = true, length = 5)
    private String isKey;

    @ManyToOne
    @JoinColumn(name="comp_page_search_id")
    private CompPageSearch compPageSearch;

    /**
     * 转换数据集
     */
    @Transient
    private Map<String, String> transformDataSet = new LinkedHashMap<String, String>();

    @Override
    public String toString() {
        return "CompPageSearchTable{" +
                "isKey='" + isKey + '\'' +
                ", id=" + id +
                ", fieldName='" + fieldName + '\'' +
                ", displayName='" + displayName + '\'' +
                ", width=" + width +
                ", style='" + style + '\'' +
                ", orderNo=" + orderNo +
                ", transformDataSetType='" + transformDataSetType + '\'' +
                '}';
    }
}
