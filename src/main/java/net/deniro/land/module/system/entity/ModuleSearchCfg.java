package net.deniro.land.module.system.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * 模块查询配置
 *
 * @author deniro
 *         2015/10/19
 */
@Data
@Entity
@Table(name = "t_module_search_cfg")
public class ModuleSearchCfg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
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
     * 表单类型；1-文本输入框；2-下拉框
     */
    @Column(name = "type", nullable = true, length = 1)
    private Integer type;

    /**
     * 对应的模块ID
     */
    @Column(name = "module_id", nullable = true, length = 32)
    private Integer moduleId;

    /**
     * 排序号
     */
    @Column(name = "order_no", nullable = true, length = 32)
    private Integer orderNo;


}
