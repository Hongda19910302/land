package net.deniro.land.module.component.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 分页查询组件之工具栏
 *
 * @author deniro（AnnotationHibernateCodeMarker）
 *         2015-11-25
 */
@Setter
@Getter
@Entity
@Table(name = "comp_page_search_tool_bar")
public class CompPageSearchToolBar implements Serializable {


    /**
     * 工具ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = true, length = 11)
    private Integer id;

    /**
     * 按钮名称
     */
    @Column(name = "btn_name", nullable = true, length = 30)
    private String btnName;

    /**
     * 按钮样式类名称
     */
    @Column(name = "btn_class_name", nullable = true, length = 30)
    private String btnClassName;

    /**
     * url
     */
    @Column(name = "url", nullable = true, length = 300)
    private String url;

    /**
     * 新页签名称
     */
    @Column(name = "tab_name", nullable = true, length = 30)
    private String tabName;

    /**
     * 排序号
     */
    @Column(name = "order_no", nullable = true, length = 10)
    private Integer orderNo;

    /**
     * 打开方式；TAB：新页签；DIALOG：对话框
     */
    @Column(name = "open_type", nullable = true, length = 10)
    private String openType;

    /**
     * 对话框高度（单位为px）（打开方式为对话框时方有效）
     */
    @Column(name = "dialog_height", nullable = true, length = 5)
    private Integer dialogHeight;

    /**
     * 对话框宽度（单位为px）（打开方式为对话框时方有效）
     */
    @Column(name = "dialog_width", nullable = true, length = 5)
    private Integer dialogWidth;

    /**
     * 对话框最小高度（单位为px）（打开方式为对话框时方有效）
     */
    @Column(name = "dialog_min_height", nullable = true, length = 5)
    private Integer dialogMinHeight;

    /**
     * 对话框最小宽度（单位为px）（打开方式为对话框时方有效）
     */
    @Column(name = "dialog_min_width", nullable = true, length = 5)
    private Integer dialogMinWidth;

    /**
     * 对话框初始化时是否最大化打开（打开方式为对话框时方有效）
     */
    @Column(name = "dialog_init_is_max", nullable = true, length = 5)
    private String dialogInitIsMax;

    /**
     * 对话框是否使用遮罩（打开方式为对话框时方有效）
     */
    @Column(name = "dialog_is_mask", nullable = true, length = 5)
    private String dialogIsMask;

    /**
     * 对话框是否可缩放（打开方式为对话框时方有效）
     */
    @Column(name = "dialog_is_resizable", nullable = true, length = 5)
    private String dialogIsResizable;

    /**
     * 对话框是否可拖拉（打开方式为对话框时方有效）
     */
    @Column(name = "dialog_is_drawable", nullable = true, length = 5)
    private String dialogIsDrawable;

    /**
     * 对话框是否有【最大化】功能（打开方式为对话框时方有效）
     */
    @Column(name = "dialog_is_maxable", nullable = true, length = 5)
    private String dialogIsMaxable;

    /**
     * 对话框是否有【最小化】功能（打开方式为对话框时方有效）
     */
    @Column(name = "dialog_is_minable", nullable = true, length = 5)
    private String dialogIsMinable;

    /**
     * 再次点击时是否刷新数据
     */
    @Column(name = "is_fresh", nullable = true, length = 5)
    private String isFresh;

    /**
     * 确认框展示信息
     */
    @Column(name = "confirm_tip", nullable = true, length = 100)
    private String confirmTip;

    @ManyToOne
    @JoinColumn(name = "comp_page_search_id")
    private CompPageSearch compPageSearch;
}
