package net.deniro.land.module.icase.entity;

import lombok.Data;

/**
 * 可变字段+数据值，确定选择类型名称
 *
 * @author deniro
 *         2015/11/11
 */
@Data
public class VariableDataValueSelectName {

    /**
     * 可变字段ID
     */
    private Integer variableFieldId;

    /**
     * 数据映射值
     */
    private Integer dataTypeValue;

    /**
     * 下拉框显示名称
     */
    private String selectTypeName;


}
