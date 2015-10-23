package net.deniro.land.common.utils.code.mysql.entity;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

/**
 * 表定义
 *
 * @author deniro
 *         2015/10/23
 */
@Data
public class TableDefinition {

    /**
     * 表名
     */
    private String name;

    /**
     * 注释
     */
    private String comment;

    /**
     * 表字段定义列表
     */
    List<FieldDefinition> fieldDefinitions = new LinkedList<FieldDefinition>();


}
