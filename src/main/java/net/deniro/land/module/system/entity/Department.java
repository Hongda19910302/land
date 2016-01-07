package net.deniro.land.module.system.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 部门
 *
 * @author deniro
 *         2015/10/10
 */
@Entity
@Table(name = "t_department")
public class Department implements Serializable {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DEPARTMENT_ID", unique = true, nullable = false)
    private Integer departmentId;

    /**
     * 部门名称
     */
    @Getter
    @Setter
    @Column(name = "NAME", nullable = true, length = 60)
    private String name;

    /**
     * 上级部门ID
     */
    @Getter
    @Setter
    @Column(name = "PARENT_ID", nullable = true, length = 11)
    private Integer parentId;


    /**
     * 所属单位ID
     */
    @Getter
    @Setter
    @Column(name = "COMPANY_ID", nullable = true, length = 11)
    private Integer companyId;


    /**
     * 状态 0:正常 1：禁用 2：删除
     */
    @Getter
    @Setter
    @Column(name = "STATUS", nullable = true, length = 2)
    private Integer status;

    /**
     * 是否为父节点；true：是；false：否
     */
    @Getter
    @Setter
    @Transient
    private String isParent;

    /**
     * 父节点展开图标
     */
    @Getter
    @Setter
    @Transient
    private String iconOpen;

    /**
     * 父节点折叠图标
     */
    @Getter
    @Setter
    @Transient
    private String iconClose;

    /**
     * 叶子节点图标
     */
    @Getter
    @Setter
    @Transient
    private String icon;


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

    /**
     * 角色状态
     */
    public enum Status {
        /**
         * 正常
         */
        NORMAL(0),
        /**
         * 禁用
         */
        DISABLE(1),
        /**
         * 删除
         */
        DELETE(2);

        private int code;

        Status(int code) {
            this.code = code;
        }

        /**
         * 获取枚举对象
         *
         * @param code 来源码
         * @return
         */
        public static Status get(int code) {
            Status[] sources = Status.values();
            for (Status source : sources) {
                if (source.code() == code) {
                    return source;
                }
            }
            return null;
        }

        public int code() {
            return code;
        }
    }

    /**
     * 当前选择的单位ID
     */
    @Getter
    @Setter
    @Transient
    private Integer currentCompanyId;

    /**
     * 当前选择的部门ID
     */
    @Getter
    @Setter
    @Transient
    private Integer currentDepartmentId;

    /**
     * 当前选择的部门的父部门ID
     */
    @Getter
    @Setter
    @Transient
    private Integer currentDepartmentParentId;

    /**
     * 操作类型
     */
    @Getter
    @Setter
    @Transient
    private String operateType;

    /**
     * 操作类型
     */
    public enum OperateType {
        /**
         * 新增顶级部门
         */
        ADD_TOP,
        /**
         * 新增同级部门
         */
        ADD_BROTHER,

        /**
         * 新增子部门
         */
        ADD_CHILD,

        /**
         * 编辑
         */
        EDIT
    }
}
