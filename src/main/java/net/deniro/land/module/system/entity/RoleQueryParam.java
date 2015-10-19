package net.deniro.land.module.system.entity;

import net.deniro.land.common.entity.QueryParam;
import lombok.Data;

/**
 * 角色查询参数
 *
 * @author deniro
 *         2015/10/19
 */
@Data
public class RoleQueryParam extends QueryParam {
    /**
     * 角色名称
     */
    private String backRoleName;

    /**
     * 单位ID
     */
    private Integer companyId;

    /**
     * 状态
     */
    private Integer status;
}
