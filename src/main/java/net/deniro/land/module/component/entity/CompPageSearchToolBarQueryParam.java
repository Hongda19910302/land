package net.deniro.land.module.component.entity;

import lombok.Data;
import net.deniro.land.common.entity.QueryParam;

/**
 * 分页查询组件中的工具栏
 *
 * @author deniro
 *         2015/12/21
 */
@Data
public class CompPageSearchToolBarQueryParam extends QueryParam {

    /**
     * 按钮名称
     */
    private String btnName;

    /**
     * 组件ID
     */
    private Integer cid;
}
