package net.deniro.land.module.system.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 模块查询配置
 *
 * @author deniro
 *         2015/10/19
 */
@Data
@Entity
@Table(name = "t_module_search_cfg")
@Deprecated
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
     * 输入框类型
     */
    @Column(name = "input_type", nullable = true, length = 100)
    private String inputType;

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

    /**
     * 下拉列表数据集类型
     */
    @Column(name = "select_list_data_set_type", nullable = true, length = 100)
    private String DataSetType;

    /**
     * 下拉列表数据集，key：值；value：显示值
     */
    @Transient
    private Map<String, String> selectListDataSet = new LinkedHashMap<String, String>();


    /**
     * 输入框类型
     */
    @Deprecated
    public enum InputType {
        /**
         * 文本输入框
         */
        TEXT,

        /**
         * 下拉选择框
         */
        SELECT
    }

}
