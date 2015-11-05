package net.deniro.land.mobile.entity;

import lombok.Data;

/**
 * 登录参数
 *
 * @author deniro
 *         2015/11/5
 */
@Data
public class LoginParam {

    /**
     * 账号
     */
    private String account;

    /**
     * 密码；如果是工作添翼登录，密码为“ykgzty”如果是工作添翼登录，密码为“ykgzty”
     */
    private String password;

    /**
     * 登录类型；0-正常登录；1-工作添翼登录
     */
    private String loginType;
}
