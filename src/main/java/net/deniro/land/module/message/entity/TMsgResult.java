package net.deniro.land.module.message.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 消息
 */
@Data
@Entity
@Table(name = "t_msg_result")
public class TMsgResult implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "MSG_ID", unique = true, nullable = false)
    private Integer msgId;

    /**
     * 用户ID
     */
    @Column(name = "USER_ID", nullable = false, length = 11)
    private Integer userId;

    /**
     * 案件ID
     */
    @Column(name = "CASE_ID", nullable = false, length = 11)
    private Integer caseId;

    /**
     * 短信通道：0-集时通、1-河北smgp短信通道（未使用）
     */
    @Column(name = "SMS_CHANNEL", nullable = true, length = 1)
    private Integer smsChannel;

    /**
     * 创建时间
     */
    @Column(name = "CREATE_TIME", nullable = false)
    private Date createTime;

    /**
     * 是否已读 0未读 1已读
     */
    @Column(name = "IS_READ", nullable = false, length = 1)
    private Integer isRead;

    /**
     * 是否已删除 0未删 1已删
     */
    @Column(name = "IS_DEL", nullable = false, length = 1)
    private Integer isDel;

    /**
     * 短信规则名称（消息标题）
     */
    @Column(name = "RULE_NAME", nullable = false, length = 32)
    private String ruleName;

    /**
     * 消息内容
     */
    @Column(name = "MSG_CONTENT", nullable = false, length = 200)
    private String msgContent;


}
