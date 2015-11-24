package net.deniro.land.module.component.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

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

    /**
     * 非表格高度（用于查询表单、表格标题、分页条）（单位为px）
     */
    @Column(name = "no_table_height", nullable = true, length = 100)
    private String noTableHeight;

    /**
     * 查询表单是否包含单位或部门选择组件
     */
    @Column(name = "is_lookup_company_department", nullable = true, length = 5)
    private String isLookupCompanyDepartment;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "compPageSearch", fetch = FetchType.EAGER)
    @OrderBy("orderNo ASC ")
    private Set<CompPageSearchForm> compPageSearchFormFields;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "compPageSearch", fetch = FetchType.EAGER)
    @OrderBy("orderNo ASC ")
    private Set<CompPageSearchTable> compPageSearchTableFields;

}
