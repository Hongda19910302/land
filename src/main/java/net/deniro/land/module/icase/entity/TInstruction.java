package net.deniro.land.module.icase.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
* 案件批示
*
* @author deniro（AnnotationHibernateCodeMarker）
*         2015-11-16
*/
@Data
@Entity
@Table(name = "t_instruction")
public class TInstruction implements Serializable {


    /**
    * 主键ID
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "instruction_id", nullable = true,length = 11)
    private Integer instructionId;

    /**
    * 案件ID
    */
    @Column(name = "case_id", nullable = true,length = 11)
    private Integer caseId;

    /**
    * 批示人ID
    */
    @Column(name = "user_id", nullable = true,length = 11)
    private Integer userId;

    /**
    * 批示人名称
    */
    @Column(name = "user_name", nullable = true,length = 60)
    private String userName;

    /**
    * 批示内容
    */
    @Column(name = "content", nullable = true,length = 600)
    private String content;

    /**
    * 批示时间
    */
    @Column(name = "instruction_date", nullable = true)
    private Date instructionDate;

    /**
    * 案件批示状态； 0：未删除；1：已删除
    */
    @Column(name = "status", nullable = true,length = 1)
    private Integer status;

    /**
     * 案件批示状态
     */
    public enum InstructionStatus {
        /**
         * 正常（未删除）
         */
        NORMAL(0),
        /**
         * 已删除
         */
        DEL(1);

        private int code;

        InstructionStatus(int code) {
            this.code = code;
        }

        /**
         * 获取枚举对象
         *
         * @param code 来源码
         * @return
         */
        public static InstructionStatus get(int code) {
            InstructionStatus[] sources = InstructionStatus.values();
            for (InstructionStatus source : sources) {
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
