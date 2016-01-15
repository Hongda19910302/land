package net.deniro.land.module.icase.entity;

import lombok.Data;

/**
 * 案件字段
 *
 * @author deniro
 *         2016/1/14
 */
@Data
public class CaseField {

    /**
     * 主键
     */
    private Integer confId;

    /**
     * 可变字段ID
     */
    private Integer variableFieldId;

    /**
     * 字段名称
     */
    private String fieldName;

    /**
     * 字段Key
     */
    private String fieldKey;

    /**
     * 字段ID
     */
    private Integer dataFieldId;

    /**
     * 下拉项ID
     */
    private Integer dataTypeId;

    /**
     * 下拉项名称
     */
    private String dataTypeName;

    /**
     * 单位ID
     */
    private Integer companyId;

    /**
     * 状态
     */
    private Integer status;
}