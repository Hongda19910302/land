package net.deniro.land.module.icase.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 附件与案件相关（巡查记录、审查记录等等）的映射关系
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
     * 关联类型 0：案件 1：巡查记录 2:案件审核 3：草稿 4:受理单 5：反馈意见
     */
    @Column(name = "relation_type", nullable = true, length = 2)
    private Integer relationType;

    /**
     * 关系表的ID
     */
    @Column(name = "relation_id", nullable = true, length = 11)
    private Integer relationId;

    /**
     * 附件ID
     */
    @Column(name = "attachment_id", nullable = true, length = 11)
    private Integer attachmentId;

    /**
     * 关联类型
     */
    public enum RelationType {
        /**
         * 案件
         */
        CASE(0),
        /**
         * 巡查记录
         */
        INSPECT(1),
        /**
         * 案件审核
         */
        AUDIT(2),
        /**
         * 草稿
         */
        DRAFT(3),
        /**
         * 受理单
         */
        ACCEPTANCE(4),
        /**
         * 反馈意见
         */
        FEEDBACK(5);

        private int code;

        RelationType(int code) {
            this.code = code;
        }

        /**
         * 获取枚举对象
         *
         * @param code 来源码
         * @return
         */
        public static RelationType get(int code) {
            RelationType[] sources = RelationType.values();
            for (RelationType source : sources) {
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
