package net.deniro.land.module.component.entity;

import lombok.Data;
import net.deniro.land.common.entity.QueryParam;

/**
 * 分页查询组件中的查询表单
 *
 * @author deniro
 *         2015/12/21
 */
@Data
public class CompPageSearchFormQueryParam extends QueryParam {
    /**
     * 查询字段显示名称
     */
    private String displayName;

    /**
     * 组件ID
     */
    private Integer cid;
}
