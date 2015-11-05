package net.deniro.land.mobile.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
