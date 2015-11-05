package net.deniro.land.module.mobile.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 客户端权限（临时）
 * <p>
 *
 * @author deniro（AnnotationHibernateCodeMarker）
 *         2015-11-5
 */
@Data
@Entity
@Table(name = "t_client_privilege")
public class TClientPrivilege implements Serializable {


    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "privilege_id", nullable = true, length = 10)
    private Integer privilegeId;

    /**
     * 权限名称
     */
    @Column(name = "privilege_name", nullable = true, length = 20)
    private String privilegeName;

    /**
     * 排序号
     */
    @Column(name = "privilege_order", nullable = true, length = 10)
    private Integer privilegeOrder;

    /**
     * 是否有权限；1:有权限，0:没权限
     */
    @Column(name = "is_exist", nullable = true, length = 10)
    private Integer isExist;

    /**
     * 权限类型
     */
    public enum PrivilegeType {
        /**
         * 无权限
         */
        NO(0),
        /**
         * 有权限
         */
        YES(1);

        private int code;

        PrivilegeType(int code) {
            this.code = code;
        }

        /**
         * 获取枚举对象
         *
         * @param code 来源码
         * @return
         */
        public static PrivilegeType get(int code) {
            PrivilegeType[] values = PrivilegeType.values();
            for (PrivilegeType value : values) {
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
}
