package net.deniro.land.module.account.entity;

import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 部门
 *
 * @author deniro
 *         2015/10/10
 */
@Data
@Entity
@Table(name = "t_department")
public class Department implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DEPARTMENT_ID", unique = true, nullable = false)
    private Integer departmentId;

    /**
     * 部门名称
     */
    @Column(name = "NAME", nullable = true, length = 60)
    private String name;

    /**
     * 上级部门
     */
    @Column(name = "PARENT_ID", nullable = true, length = 11)
    private Integer parentId;


    /**
     * 所属单位
     */
    @Column(name = "COMPANY_ID", nullable = true, length = 11)
    private Integer companyId;


    /**
     * 状态 todo:有哪些？
     */
    @Column(name = "STATUS", nullable = true, length = 2)
    private Integer status;

    /**
     * 部门
     */
    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PARENT_ID", referencedColumnName = "DEPARTMENT_ID", insertable = false, updatable = false)
    private Department department;

    /**
     * 单位
     */
    @Setter
    @ManyToOne
    @JoinColumn(name = "COMPANY_ID", referencedColumnName = "COMPANY_ID", insertable = false, updatable = false)
    private Company company;

}
