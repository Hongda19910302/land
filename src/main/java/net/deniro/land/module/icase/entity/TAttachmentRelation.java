package net.deniro.land.module.icase.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 附件与案件映射关系
 *
 * @author deniro（AnnotationHibernateCodeMarker）
 *         2015-11-12
 */
@Data
@Entity
@Table(name = "t_attachment_relation")
public class TAttachmentRelation implements Serializable {


    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attachment_relation_id", nullable = true, length = 11)
    private Integer attachmentRelationId;

    /**
     * 关联类型 0：案件 1：巡查记录 2:案件审核 4:受理单
     */
    @Column(name = "relation_type", nullable = true, length = 2)
    private Integer relationType;

    /**
     * 案件ID
     */
    @Column(name = "relation_id", nullable = true, length = 11)
    private Integer relationId;

    /**
     * 附件ID
     */
    @Column(name = "attachment_id", nullable = true, length = 11)
    private Integer attachmentId;
}
