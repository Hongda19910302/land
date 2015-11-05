package net.deniro.land.api.entity;

import lombok.Data;
import net.deniro.land.module.system.entity.User;

/**
 * 登录 响应结果
 *
 * @author deniro
 *         2015/11/5
 */
@Data
@Deprecated
public class LoginResult extends ResponseResult {

    /**
     * 账户
     */
    private User user;

}
