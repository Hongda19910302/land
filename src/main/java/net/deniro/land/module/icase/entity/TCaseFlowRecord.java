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
     * 操作者名称
     */
    @Transient
    private String operatorName;

    /**
     * 案件原操作者ID
     */
    @Column(name = "from_user_id", nullable = true, length = 11)
    private Integer fromUserId;

    /**
     * 发送者名称
     */
    @Transient
    private String fromName;

    /**
     * 接受者ID
     */
    @Column(name = "to_user_id", nullable = true, length = 11)
    private Integer toUserId;

    /**
     * 接收者名称
     */
    @Transient
    private String toName;

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

    /**
     * 操作类型；用于显示
     */
    @Transient
    private String operationTypeInDisplay;

    /**
     * 操作类型
     */
    public enum OperationType {
        /**
         * 立案
         */
        REGISTER(1),
        /**
         * 立案审核
         */
        REGISTER_AUDIT(2),
        /**
         * 申请结案
         */
        APPLY_CLOSE(3),
        /**
         * 一次结案审核
         */
        FIRST_CLOSE_APPLY(4),
        /**
         * 二次结案审核
         */
        SECOND_CLOSE_APPLY(5),
        /**
         * 删除
         */
        DEL(6),
        /**
         * 案件被撤销
         */
        CANCELED(7),
        /**
         * 一次结案审核被驳回
         */
        FIRST_CLOSE_APPLY_REJECT(8),
        /**
         * 二次结案审核被驳回
         */
        SECOND_CLOSE_APPLY_REJECT(9);

        private int code;

        OperationType(int code) {
            this.code = code;
        }

        /**
         * 获取枚举对象
         *
         * @param code 来源码
         * @return
         */
        public static OperationType get(int code) {
            OperationType[] sources = OperationType.values();
            for (OperationType source : sources) {
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
