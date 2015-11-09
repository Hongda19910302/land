package net.deniro.land.module.system.entity;

import lombok.Getter;
import lombok.Setter;
import net.deniro.land.common.utils.SpringContextUtils;
import net.deniro.land.module.mobile.service.PrivilegeService;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 账号
 * <p>
 * 不能使用@Data注释，会造成递归堆栈溢出
 *
 * @author deniro
 *         2015/10/10
 */
@Entity
@Table(name = "t_user")
public class User implements Serializable {

    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID", unique = true, nullable = false)
    private Integer userId;

    /**
     * 账号
     */
    @Setter
    @Getter
    @Column(name = "ACCOUNT", nullable = true, length = 30)
    private String account;

    /**
     * 密码
     */
    @Setter
    @Getter
    @Column(name = "PASSWORD", nullable = true, length = 64)
    private String password;

    /**
     * 手机号
     */
    @Setter
    @Getter
    @Column(name = "TEL", nullable = true, length = 11)
    private String tel;

    /**
     * 地址
     */
    @Getter
    @Column(name = "ADDR", nullable = true, length = 255)
    private String addr;

    /**
     * 性别； 0：女 1：男
     */
    @Setter
    @Getter
    @Column(name = "SEX", nullable = true, length = 1)
    private Integer sex;

    /**
     * 邮箱
     */
    @Setter
    @Getter
    @Column(name = "EMAIL", nullable = true, length = 60)
    private String email;

    /**
     * 创建时间
     */
    @Setter
    @Getter
    @Column(name = "CREATE_TIME", nullable = true)
    private java.sql.Timestamp createTime;

    /**
     * 领导人ID
     */
    @Setter
    @Getter
    @Column(name = "LEADER_ID", nullable = true, length = 11)
    private Integer leaderId;

    /**
     * 职位
     */
    @Setter
    @Getter
    @Column(name = "POSITION", nullable = true, length = 20)
    private String position;

    /**
     * 部门id
     */
    @Setter
    @Getter
    @Column(name = "DEPARTMENT_ID", nullable = true, length = 11)
    private Integer departmentId;

    /**
     * 单位ID
     */
    @Setter
    @Getter
    @Column(name = "COMPANY_ID", nullable = true, length = 11)
    private Integer companyId;

    /**
     * 状态；0:正常 1：冻结：2注销
     */
    @Setter
    @Getter
    @Column(name = "STATUS", nullable = true, length = 2)
    private Integer status;

    /**
     * 用户名
     */
    @Setter
    @Getter
    @Column(name = "NAME", nullable = true, length = 20)
    private String name;

    /**
     * 电话
     */
    @Setter
    @Getter
    @Column(name = "PHONE", nullable = true, length = 11)
    private String phone;

    /**
     * 角色id
     */
    @Setter
    @Getter
    @Column(name = "BACK_ROLE_ID", nullable = true, length = 11)
    private Integer roleId;

    /**
     * 角色
     */
    @Getter
    @ManyToOne
    @JoinColumn(name = "BACK_ROLE_ID", referencedColumnName = "BACK_ROLE_ID", nullable = true, insertable = false, updatable = false)
    private Role role;

    /**
     * 部门
     */
    @Getter
    @ManyToOne
    @JoinColumn(name = "DEPARTMENT_ID", referencedColumnName = "DEPARTMENT_ID", nullable = true, insertable = false, updatable = false)
    private Department department;

    /**
     * 单位
     */
    @Getter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "COMPANY_ID", referencedColumnName = "COMPANY_ID", nullable = true, insertable = false, updatable = false)
    private Company company;

    @Setter
    @Getter
    @Transient
    private String authority;

    /**
     * 获取权限（用于客户端接口）
     *
     * @return
     */
    public String getAuthority() {
        authority = ((PrivilegeService) SpringContextUtils.
                getBean("privilegeService")).findByRoleId(roleId);
        return authority;
    }

    /**
     * 登陆方式
     */
    public enum LoginType {
        /**
         * 正常
         */
        NORMAL(0),
        /**
         * 工作添翼
         */
        GZTY(1);

        private int code;

        LoginType(int code) {
            this.code = code;
        }

        /**
         * 获取枚举对象
         *
         * @param code 来源码
         * @return
         */
        public static LoginType get(int code) {
            LoginType[] values = LoginType.values();
            for (LoginType value : values) {
                if (value.code() == code) {
                    return value;
                }
            }
            return null;
        }

        public int code() {
            return code;
        }
    }

    /**
     * 用户状态
     */
    public enum Status {
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

        Status(int code) {
            this.code = code;
        }

        /**
         * 获取枚举对象
         *
         * @param code 来源码
         * @return
         */
        public static Status get(int code) {
            Status[] sources = Status.values();
            for (Status source : sources) {
                if (source.code() == code) {
                    return source;
                }
            }
            return null;
        }

        public int code() {
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
