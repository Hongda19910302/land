package net.deniro.land.common.utils.code.mysql.entity;

import lombok.Data;

/**
 * 字段定义
 *
 * @author deniro
 *         2015/10/23
 */
@Data
public class FieldDefinition {

    /**
     * 字段名
     */
    private String name;

    /**
     * 字段在java类中的名称
     */
    private String className;

    /**
     * 注释
     */
    private String comment;

    /**
     * 数据类型
     */
    private String dataType;

    /**
     * 字符类型最大长度
     */
    private int characterMaximumLength;

    /**
     * 数值精度
     */
    private int numbericPrecision;

    /**
     * 字段键类型
     */
    private String columnKey;

    /**
     * 字段类型
     */
    private String columnType;


}
