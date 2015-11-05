package net.deniro.land.module.mobile.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
* 角色与权限关系
*
* @author deniro（AnnotationHibernateCodeMarker）
*         2015-11-5
*/
@Data
@Entity
@Table(name = "t_back_role_privilege")
public class TBackRolePrivilege implements Serializable {


    /**
    * 角色权限id
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "back_role_privilege_id", nullable = true, length = 10)
    private Integer backRolePrivilegeId;

    /**
    * 角色id
    */
    @Column(name = "back_role_id", nullable = true, length = 10)
    private Integer backRoleId;

    /**
    * 权限id（模块id）
    */
    @Column(name = "back_privilege_id", nullable = true, length = 10)
    private Integer backPrivilegeId;
}
