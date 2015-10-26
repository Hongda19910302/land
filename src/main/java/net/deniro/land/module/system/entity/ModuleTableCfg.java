package net.deniro.land.module.system.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 模块分页表格配置
 *
 * @author deniro
 *         2015/10/20
 */
@Data
@Entity
@Table(name = "t_module_table_cfg")
@Deprecated
public class ModuleTableCfg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    /**
     * 字段名称
     */
    @Column(name = "field_name", nullable = true, length = 100)
    private String fieldName;

    /**
     * 查询字段显示名称
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
    @Column(name = "style", nullable = true, length = 1000)
    private String style;

    /**
     * 排序号
     */
    @Column(name = "order_no", nullable = true, length = 1000)
    private Integer orderNo;

    /**
     * 模块ID
     */
    @Column(name = "module_id", nullable = true, length = 32)
    private String moduleId;

    /**
     * 转换数据集类型
     */
    @Column(name = "transform_data_set_type", nullable = true, length = 100)
    private String transformDataSetType;

    /**
     * 是否为主键
     */
    @Column(name = "is_key", nullable = true, length = 10)
    private String iskey;

    /**
     * 转换数据集
     */
    @Transient
    private Map<String, String> transformDataSet = new LinkedHashMap<String, String>();

}
