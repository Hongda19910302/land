package net.deniro.land.module.component.entity;

import lombok.Data;
import net.deniro.land.common.entity.QueryParam;

/**
 * 分页查询组件中的分页表格
 *
 * @author deniro
 *         2015/12/21
 */
@Data
public class CompPageSearchTableQueryParam extends QueryParam {
    /**
     * 查询字段显示名称
     */
    private String displayName;

    /**
     * 组件ID
     */
    private Integer cid;
}
