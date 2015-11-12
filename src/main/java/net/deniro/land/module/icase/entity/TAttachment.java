package net.deniro.land.module.icase.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* 附件
*
* @author deniro（AnnotationHibernateCodeMarker）
*         2015-11-12
*/
@Data
@Entity
@Table(name = "t_attachment")
public class TAttachment implements Serializable {


    /**
    * 主键ID
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attachment_id", nullable = true,length = 11)
    private Integer attachmentId;

    /**
    * 附件类型 0:照片 1：单据 2:压缩文件 3：word文件
    */
    @Column(name = "attachment_type", nullable = true,length = 2)
    private Integer attachmentType;

    /**
    * 地址
    */
    @Column(name = "addr", nullable = true,length = 200)
    private String addr;

    /**
    * 上传时间
    */
    @Column(name = "create_time", nullable = true)
    private Date createTime;
}
