package net.deniro.land.api.entity;

import lombok.Data;

/**
 * 案件可变字段
 *
 * @author deniro
 *         2015/11/11
 */
@Data
public class CaseVariableField {

    /**
     * 字段key
     */
    private String fieldKey;

    /**
     * 字段名
     */
    private String fieldName;

    /**
     * 字段实际值
     */
    private String fieldValue;

    /**
     * 字段显示出来的值；主要用于下拉显示的值，若为非下拉，值为“”
     */
    private String fieldShow;

    /**
     * 是否下拉；
     * 0：下拉
     * 1：非下拉
     */
    private Integer pullDown;

    /**
     * 是否隐藏； 0：隐藏 1：显示
     */
    private Integer isHide;

    /**
     * 可变字段ID
     */
    private Integer variableFieldId;

    /**
     * 表字段名称
     */
    private String tableField;
}
