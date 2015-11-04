package net.deniro.land.module.icase.entity;

import lombok.Data;
import net.deniro.land.common.entity.QueryParam;

/**
 * 案件字段管理 查询参数
 *
 * @author deniro
 *         2015/11/4
 */
@Data
public class VariableFieldQueryParam extends QueryParam {

    /**
     * 单位ID
     */
    private String companyId;

    /**
     * 字段显示名称
     */
    private String fieldName;

}
