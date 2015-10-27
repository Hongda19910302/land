package net.deniro.land.module.system.entity;

import lombok.Data;
import net.deniro.land.common.entity.QueryParam;

/**
 * 账户 查询参数
 *
 * @author deniro
 *         2015/10/27
 */
@Data
public class UserQueryParam extends QueryParam{

    /**
     * 姓名
     */
    private String name;

    /**
     * 账号
     */
    private String account;
}
