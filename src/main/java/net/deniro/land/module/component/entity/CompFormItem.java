package net.deniro.land.module.component.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 表单项
 *
 * @author deniro（AnnotationHibernateCodeMarker）
 *         2015-12-1
 */
@Setter
@Getter
@Entity
@Table(name = "comp_form_item")
public class CompFormItem implements Serializable {


    /**
     * 表单项ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = true, length = 11)
    private Integer id;

    /**
     * 字段标题
     */
    @Column(name = "title", nullable = true, length = 30)
    private String title;

    /**
     * 输入框类型；TEXT-文本输入框；SELECT-下拉选择框；TEXTAREA-多行文本框
     */
    @Column(name = "input_type", nullable = true, length = 30)
    private String inputType;

    /**
     * 输入框名称
     */
    @Column(name = "input_name", nullable = true, length = 30)
    private String inputName;

    /**
     * 输入框class名称
     */
    @Column(name = "input_class", nullable = true, length = 30)
    private String inputClass;

    /**
     * 下拉框数据集类型
     */
    @Column(name = "select_list_data_set_type")
    private String selectListDataSetType;

    /**
     * 输入框css样式代码
     */
    @Column(name = "input_css_style")
    private String inputCssStyle;

    /**
     * 是否只读
     */
    @Column(name = "is_read_only", nullable = true, length = 5)
    private String isReadOnly;

    /**
     * 是否禁用
     */
    @Column(name = "is_disabled", nullable = true, length = 5)
    private String isDisabled;

    /**
     * 输入框最大长度
     */
    @Column(name = "text_max_length", nullable = true, length = 3)
    private Integer textMaxLength;

    /**
     * 输入框最小长度
     */
    @Column(name = "text_min_length", nullable = true, length = 3)
    private Integer textMinLength;

    /**
     * 输入框提示信息
     */
    @Column(name = "text_alt", nullable = true, length = 30)
    private String textAlt;

    /**
     * 某个输入框ID（该输入框的值必须等于某个输入框的值）
     */
    @Column(name = "text_equal_to_id", nullable = true, length = 30)
    private String textEqualToId;

    /**
     * 输入框最大数值
     */
    @Column(name = "text_max_num", nullable = true, length = 5)
    private Integer textMaxNum;

    /**
     * 输入框最小数值
     */
    @Column(name = "text_min_num", nullable = true, length = 5)
    private Integer textMinNum;

    /**
     * 服务端验证URL
     */
    @Column(name = "text_remote_url", nullable = true, length = 30)
    private String textRemoteUrl;

    /**
     * 自定义验证JS函数名称
     */
    @Column(name = "text_custom_valid_func_name", nullable = true, length = 30)
    private String textCustomValidFuncName;

    /**
     * 自定义验证JS函数体
     */
    @Column(name = "text_custom_valid_func_content", nullable = true, length = 500)
    private String textCustomValidFuncContent;

    /**
     * 多行文本框总列数
     */
    @Column(name = "textarea_cols", nullable = true, length = 3)
    private Integer textareaCols;

    /**
     * 多行文本框总行数
     */
    @Column(name = "textarea_rows", nullable = true, length = 3)
    private Integer textareaRows;

    /**
     * 排序号
     */
    @Column(name = "order_no", nullable = true, length = 10)
    private Integer orderNo;

    @ManyToOne
    @JoinColumn(name = "form_id")
    private CompForm compForm;

    /**
     * 下拉列表数据集，key：值；value：显示值
     */
    @Transient
    private Map<String, String> selectListDataSet = new LinkedHashMap<String, String>();
}
