package net.deniro.land.module.icase.entity;

import lombok.Data;
import net.deniro.land.module.system.entity.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 案件审查信息
 *
 * @author deniro（AnnotationHibernateCodeMarker）
 *         2015-11-12
 */
@Data
@Entity
@Table(name = "t_case_audit")
public class TCaseAudit implements Serializable {


    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "case_audit_id", nullable = true, length = 11)
    private Integer caseAuditId;

    /**
     * 案件ID
     */
    @Column(name = "case_id", nullable = true, length = 11)
    private Integer caseId;

    /**
     * 审查时间
     */
    @Column(name = "audit_time", nullable = true)
    private Date auditTime;

    /**
     * 审查结果 0:通过1：不通过
     */
    @Column(name = "audit_result", nullable = true, length = 2)
    private Integer auditResult;

    /**
     * 备注
     */
    @Column(name = "remark", nullable = true, length = 200)
    private String remark;

    /**
     * 审查人ID
     */
    @Column(name = "auditer_id", nullable = true, length = 11)
    private Integer auditerId;

    /**
     * 序号 区分二次审核
     */
    @Column(name = "audit_no", nullable = true, length = 2)
    private Integer auditNo;

    /**
     * 审核类型 0：立案 1：结案
     */
    @Column(name = "audit_type", nullable = true, length = 2)
    private Integer auditType;

    /**
     * 案件
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CASE_ID", referencedColumnName = "CASE_ID", nullable = true, insertable = false, updatable = false)
    private TCase findCase;

    /**
     * 审查人
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AUDITER_ID", referencedColumnName = "USER_ID", insertable = false, updatable = false)
    private User findAuditer;
}
