package net.deniro.land.module.icase.entity;

import lombok.Data;
import net.deniro.land.module.system.entity.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 巡查记录
 *
 * @author deniro（AnnotationHibernateCodeMarker）
 *         2015-11-12
 */
@Data
@Entity
@Table(name = "t_inspect")
public class TInspect implements Serializable {


    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inspect_id", nullable = true, length = 11)
    private Integer inspectId;

    /**
     * 案件ID
     */
    @Column(name = "case_id", nullable = true, length = 11)
    private Integer caseId;

    /**
     * 巡查序号
     */
    @Column(name = "inspect_no", nullable = true, length = 2)
    private Integer inspectNo;

    /**
     * 巡查时间
     */
    @Column(name = "create_time", nullable = true)
    private Date createTime;

    /**
     * 巡查结果；0：违规搭建1：制止不住2：制止到位3：未拆除
     * 4：已拆除5：已审批6：已批复7：复耕8：查处移送9：房屋维修
     */
    @Column(name = "inspect_result", nullable = true, length = 2)
    private Integer inspectResult;

    /**
     * 备注
     */
    @Column(name = "remark", nullable = true, length = 200)
    private String remark;

    /**
     * 巡查员ID
     */
    @Column(name = "inspector_id", nullable = true, length = 11)
    private Integer inspectorId;

    /**
     * 案件
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CASE_ID", referencedColumnName = "CASE_ID", nullable = true, insertable = false, updatable = false)
    private TCase findCase;

    /**
     * 巡查员
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INSPECTOR_ID", referencedColumnName = "USER_ID", insertable = false, updatable = false)
    private User findInspector;
}
