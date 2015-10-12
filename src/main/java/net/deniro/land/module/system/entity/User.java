package net.deniro.land.module.system.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 账号
 *
 * @author deniro
 *         2015/10/10
 */
@Data
@Entity
@Table(name = "t_user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID", unique = true, nullable = false)
    private Integer userId;

    /**
     * 账号
     */
    @Column(name = "ACCOUNT", nullable = true, length = 30)
    private String account;

    /**
     * 密码
     */
    @Column(name = "PASSWORD", nullable = true, length = 64)
    private String password;

    /**
     * 手机号
     */
    @Column(name = "TEL", nullable = true, length = 11)
    private String tel;

    /**
     * 地址
     */
    @Column(name = "ADDR", nullable = true, length = 255)
    private String addr;

    /**
     * 性别； 0：女 1：男
     */
    @Column(name = "SEX", nullable = true, length = 1)
    private Integer sex;

    /**
     * 邮箱
     */
    @Column(name = "EMAIL", nullable = true, length = 60)
    private String email;

    /**
     * 创建时间
     */
    @Column(name = "CREATE_TIME", nullable = true)
    private java.sql.Timestamp createTime;

    /**
     * 领导人ID
     */
    @Column(name = "LEADER_ID", nullable = true, length = 11)
    private Integer leaderId;

    /**
     * 职位
     */
    @Column(name = "POSITION", nullable = true, length = 20)
    private String position;

    /**
     * 部门id
     */
    @Column(name = "DEPARTMENT_ID", nullable = true, length = 11)
    private Integer departmentId;

    /**
     * 单位ID
     */
    @Column(name = "COMPANY_ID", nullable = true, length = 11)
    private Integer companyId;

    /**
     * 状态；0:正常 1：冻结：2注销
     */
    @Column(name = "STATUS", nullable = true, length = 2)
    private Integer status;

    /**
     * 用户名
     */
    @Column(name = "NAME", nullable = true, length = 20)
    private String name;

    /**
     * 电话
     */
    @Column(name = "PHONE", nullable = true, length = 11)
    private String phone;

    /**
     * 角色id
     */
    @Column(name = "BACK_ROLE_ID", nullable = true, length = 11)
    private Integer roleId;

    /**
     * 角色
     */
    @ManyToOne
    @JoinColumn(name = "BACK_ROLE_ID", referencedColumnName = "BACK_ROLE_ID", nullable = true, insertable = false, updatable = false)
    private Role role;

    /**
     * 部门
     */
    @ManyToOne
    @JoinColumn(name = "DEPARTMENT_ID", referencedColumnName = "DEPARTMENT_ID", nullable = true, insertable = false, updatable = false)
    private Department department;

    /**
     * 单位
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "COMPANY_ID", referencedColumnName = "COMPANY_ID", nullable = true, insertable = false, updatable = false)
    private Company company;

    /**
     * 用户状态
     */
    public enum UserStatus {
        /**
         * 正常
         */
        NORMAL(0),
        /**
         * 冻结
         */
        FREEZE(1),
        /**
         * 注销
         */
        CANCEL(2);

        private int code;

        UserStatus(int code) {
            this.code = code;
        }

        /**
         * 获取枚举对象
         *
         * @param code 来源码
         * @return
         */
        public static UserStatus get(int code) {
            UserStatus[] sources = UserStatus.values();
            for (UserStatus source : sources) {
                if (source.getCode() == code) {
                    return source;
                }
            }
            return null;
        }

        public int getCode() {
            return code;
        }
    }

    /**
     * 登录来源
     */
    public enum LoginSource {
        /**
         * android
         */
        ANDROID(0),
        /**
         * web
         */
        WEB(1);

        private int code;

        LoginSource(int code) {
            this.code = code;
        }

        /**
         * 获取枚举对象
         *
         * @param code 来源码
         * @return
         */
        public static LoginSource get(int code) {
            LoginSource[] sources = LoginSource.values();
            for (LoginSource source : sources) {
                if (source.getCode() == code) {
                    return source;
                }
            }
            return null;
        }

        public int getCode() {
            return code;
        }
    }

}
