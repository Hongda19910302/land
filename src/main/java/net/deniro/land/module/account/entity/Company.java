package net.deniro.land.module.account.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 单位
 *
 * @author deniro
 *         2015/10/10
 */
@Data
@Entity
@Table(name = "t_company")
public class Company implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//主键由数据库自动生成
    @Column(name = "COMPANY_ID", unique = true, nullable = false)
    private Integer companyId;

    /**
     * 单位名称
     */
    @Column(name = "COMPANY_NAME", nullable = true, length = 20)
    private String companyName;

    /**
     * 上级单位id
     */
    @Column(name = "PARENT_ID", nullable = true, length = 11)
    private Integer parentId;

    /**
     * 单位描述
     */
    @Column(name = "DES", nullable = true, length = 100)
    private String des;

    /**
     * 组织机构代码
     */
    @Column(name = "CODE", nullable = true, length = 10)
    private String code;

    /**
     * 状态 todo:有哪些？
     */
    @Column(name = "STATUS", nullable = true, length = 2)
    private Integer status;
}
