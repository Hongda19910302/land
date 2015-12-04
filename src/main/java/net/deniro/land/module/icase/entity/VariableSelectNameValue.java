package net.deniro.land.module.icase.entity;

import lombok.Data;

/**
 * 单位ID与可变字段key的下拉项相关信息
 *
 * @author deniro
 *         2015/12/4
 */
@Data
public class VariableSelectNameValue {

    /**
     * 单位ID
     */
    private String companyId;

    /**
     * 字段关键字
     */
    private String fieldKey;

    /**
     * 下拉项名称
     */
    private String selectName;

    /**
     * 下拉项值
     */
    private String selectValue;
}
