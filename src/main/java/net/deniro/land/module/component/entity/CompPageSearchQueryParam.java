package net.deniro.land.module.component.entity;

import lombok.Data;
import net.deniro.land.common.entity.QueryParam;

/**
 * 分页查询组件 查询条件
 *
 * @author deniro
 *         2015/12/21
 */
@Data
public class CompPageSearchQueryParam extends QueryParam {

    /**
     * 组件名称
     */
    private String name;
}
