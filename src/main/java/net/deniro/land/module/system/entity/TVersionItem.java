package net.deniro.land.module.system.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
* 版本项说明
*
* @author deniro（AnnotationHibernateCodeMarker）
*         2015-11-25
*/
@Data
@Entity
@Table(name = "t_version_item")
public class TVersionItem implements Serializable {


    /**
    * 
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = true,length = 11)
    private Integer id;

    /**
    * 版本说明主键
    */
    @Column(name = "version_id", nullable = true,length = 11)
    private Integer versionId;

    /**
    * 版本项备注
    */
    @Column(name = "remarks", nullable = true,length = 300)
    private String remarks;

    /**
    * 版本项类型；BUG、NEW、OPTIMIZE
    */
    @Column(name = "type", nullable = true,length = 20)
    private String type;

    /**
    * 当前时间
    */
    @Column(name = "create_time", nullable = true,length = 0)
    private Date createTime;
}
