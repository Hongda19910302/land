package net.deniro.land.module.icase.entity;

import lombok.Data;
import net.deniro.land.module.system.entity.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 案件流转记录
 *
 * @author deniro（AnnotationHibernateCodeMarker）
 *         2015-11-12
 */
@Data
@Entity
@Table(name = "t_case_flow_record")
public class TCaseFlowRecord implements Serializable {


    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "case_flow_record", nullable = true, length = 11)
    private Integer caseFlowRecord;

    /**
     * 操作者ID
     */
    @Column(name = "operater_id", nullable = true, length = 11)
    private Integer operaterId;

    /**
     * 案件原操作者ID
     */
    @Column(name = "from_user_id", nullable = true, length = 11)
    private Integer fromUserId;

    /**
     * 接受者ID
     */
    @Column(name = "to_user_id", nullable = true, length = 11)
    private Integer toUserId;

    /**
     * 操作
     */
    @Column(name = "operation", nullable = true, length = 600)
    private String operation;

    /**
     * 创建时间
     */
    @Column(name = "create_time", nullable = true)
    private Date createTime;

    /**
     * 操作类型；1：立案 2：立案审核 3：申请结案 4：一次结案审核 5：二次结案审核 6:删除 7:案件指派
     */
    @Column(name = "operation_type", nullable = true, length = 2)
    private Integer operationType;

    /**
     * 案件ID
     */
    @Column(name = "case_id", nullable = true, length = 11)
    private Integer caseId;

    /**
     * 案件原操作者
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FROM_USER_ID", referencedColumnName = "USER_ID", nullable = true, insertable = false, updatable = false)
    private User findFromUser;

    /**
     * 接受者
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TO_USER_ID", referencedColumnName = "USER_ID", nullable = true, insertable = false, updatable = false)
    private User findToUser;

    /**
     * 操作者
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OPERATER_ID", referencedColumnName = "USER_ID", nullable = true, insertable = false, updatable = false)
    private User findOperater;

    /**
     * 案件
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CASE_ID", referencedColumnName = "CASE_ID", nullable = true, insertable = false, updatable = false)
    private TCase findCase;


}
