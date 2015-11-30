package net.deniro.land.module.system.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
* 版本说明
*
* @author deniro（AnnotationHibernateCodeMarker）
*         2015-11-25
*/
@Data
@Entity
@Table(name = "t_version")
public class TVersion implements Serializable {


    /**
    * 主键
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = true,length = 11)
    private Integer id;

    /**
    * 版本号
    */
    @Column(name = "no", nullable = true,length = 10)
    private String no;

    /**
    * 版本说明
    */
    @Column(name = "remarks", nullable = true,length = 300)
    private String remarks;

    /**
    * 创建时间
    */
    @Column(name = "create_time", nullable = true,length = 0)
    private Date createTime;

    /**
     * 更新项总数
     */
    @Transient
    private Long itemSum;
}
