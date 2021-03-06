package net.deniro.land.module.system.entity;

import javax.persistence.*;

import lombok.Data;

import java.io.Serializable;

/**
 * 角色
 *
 * @author deniro
 *         2015/10/10
 */
@Data
@Entity
@Table(name = "t_back_role")
public class Role implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BACK_ROLE_ID", unique = true, nullable = false)
    private Integer backRoleId;

    /**
     * 角色名称
     */
    @Column(name = "BACK_ROLE_NAME", nullable = true, length = 30)
    private String backRoleName;

    /**
     * 角色注释
     */
    @Column(name = "COMMENT", nullable = true, length = 100)
    private String comment;

    /**
     * 状态；0:正常 1：禁用 2：删除
     */
    @Column(name = "STATUS", nullable = true, length = 100)
    private Integer status;

    /**
     * 单位ID
     */
    @Column(name = "COMPANY_ID", nullable = true, length = 11)
    private Integer companyId;

    /**
     * 单位名称
     */
    @Column(name = "COMPANY_NAME", nullable = true, length = 200)
    private String companyName;

    /**
     * 是否有删除权限；0:没有,1:有
     */
    @Column(name = "DEL_ROLE", nullable = true, length = 2)
    private Integer delRole = 0;

    /**
     * 是否有上报权限；0:没有,1:有
     */
    @Column(name = "REPORT_ROLE", nullable = true, length = 2)
    private Integer reportRole = 0;

    /**
     * 角色状态
     */
    public enum Status {
        /**
         * 正常
         */
        NORMAL(0),
        /**
         * 禁用
         */
        DISABLE(1),
        /**
         * 删除
         */
        DELETE(2);

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

}
