package net.deniro.land.module.component.entity;

/**
 * 输入框类型
 *
 * @author deniro
 *         2015/10/26
 */
public enum InputType {
    /**
     * 文本输入框
     */
    TEXT,

    /**
     * 下拉选择框
     */
    SELECT,

    /**
     * 起止日期选择框
     */
    BEGIN_END_DATE,

    /**
     * 区域选择框
     */
    REGION,

    /**
     * 隐藏字段
     */
    HIDDEN,

    /**
     * 多行文本框
     */
    TEXTAREA,

    /**
     * 地理坐标选择框
     */
    MAP,

    /**
     * 可变字段
     */
    VARIABLE,

    /**
     * 文件上传
     */
    UPLOAD,

    /**
     * 单位与部门选择
     */
    COMPANY_DEPARTMENT
}
