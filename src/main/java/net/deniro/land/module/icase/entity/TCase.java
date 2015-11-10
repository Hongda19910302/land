package net.deniro.land.module.icase.entity;

import lombok.Data;
import lombok.Setter;
import net.deniro.land.module.system.entity.TRegion;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 案件
 *
 * @author deniro（AnnotationHibernateCodeMarker）
 *         2015-11-9
 */
@Data
@Entity
@Table(name = "t_case")
public class TCase implements Serializable {

    /**
     * 案件巡查模块ID
     */
    public static final Integer CASE_INSPECTOR_PRIVILEGE_MODULE_ID = 12;

    /**
     * 案件ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "case_id", nullable = true, length = 10)
    private Integer caseId;

    /**
     * 案件号
     */
    @Column(name = "case_num", nullable = true, length = 30)
    private String caseNum;

    /**
     * 创建时间
     */
    @Column(name = "create_time", nullable = true, length = 0)
    private Date createTime;

    /**
     * 创建人id
     */
    @Column(name = "creater_id", nullable = true, length = 10)
    private Integer createrId;

    /**
     * 当事人
     */
    @Column(name = "parties", nullable = true, length = 20)
    private String parties;

    /**
     * 身份证号码
     */
    @Column(name = "id_card_num", nullable = true, length = 18)
    private String idCardNum;

    /**
     * 所在地区ID
     */
    @Column(name = "region_id", nullable = true, length = 10)
    private Integer regionId;

    /**
     * 违法地点
     */
    @Column(name = "illegal_area", nullable = true, length = 200)
    private String illegalArea;

    /**
     * 东至
     */
    @Column(name = "east_to", nullable = true, length = 50)
    private String eastTo;

    /**
     * 西至
     */
    @Column(name = "west_to", nullable = true, length = 50)
    private String westTo;

    /**
     * 南至
     */
    @Column(name = "south_to", nullable = true, length = 50)
    private String southTo;

    /**
     * 北至
     */
    @Column(name = "north_to", nullable = true, length = 50)
    private String northTo;

    /**
     * 违法面积
     */
    @Column(name = "illegal_area_space", nullable = true, length = 0)
    private Float illegalAreaSpace;

    /**
     * 占地面积
     */
    @Column(name = "floor_space", nullable = true, length = 0)
    private Float floorSpace;

    /**
     * 建筑面积
     */
    @Column(name = "building_space", nullable = true, length = 0)
    private Float buildingSpace;

    /**
     * 违建类型；1.违法用地 2.违法建设
     */
    @Column(name = "illegal_type", nullable = true, length = 10)
    private Integer illegalType;

    /**
     * 用地性质；1:非耕地 2:农田
     */
    @Column(name = "land_usage", nullable = true, length = 10)
    private Integer landUsage;

    /**
     * 违法现状
     */
    @Column(name = "current_status", nullable = true, length = 10)
    private Integer currentStatus;

    /**
     * 巡查结果；1：制止不住 2：制止到位 3：未拆除
     * 4：已拆除 5：已审批 6：已批复 7：复耕 8：查处移送 9：房屋维修 10：违规搭建
     */
    @Column(name = "survey_result", nullable = true, length = 10)
    private Integer surveyResult;

    /**
     * 案件来源
     */
    @Column(name = "case_source", nullable = true, length = 10)
    private Integer caseSource;

    /**
     * 巡查员ID
     */
    @Column(name = "inspector_id", nullable = true, length = 10)
    private Integer inspectorId;

    /**
     * 备注
     */
    @Column(name = "remark", nullable = true, length = 200)
    private String remark;

    /**
     * 经度
     */
    @Column(name = "lng", nullable = true, length = 0)
    private BigDecimal lng;

    /**
     * 纬度
     */
    @Column(name = "lat", nullable = true, length = 0)
    private BigDecimal lat;

    /**
     * 定位类型
     */
    @Column(name = "locate_type", nullable = true, length = 10)
    private Integer locateType;

    /**
     * 回收状态；0：不处于；1：已处于
     */
    @Column(name = "recycle_status", nullable = true, length = 10)
    private Integer recycleStatus;

    /**
     * 案件状态 0：全部案件 1:预立案 2:巡查制止 3:申请结案 4:结案审核通过 5:二次结案审核通过 6:删除 7：撤销案件
     */
    @Column(name = "status", nullable = true, length = 10)
    private Integer status;

    /**
     * 修改时间
     */
    @Column(name = "modify_time", nullable = true, length = 0)
    private Date modifyTime;

    /**
     * 删除时间
     */
    @Column(name = "del_time", nullable = true, length = 0)
    private Date delTime;

    /**
     * 单位id
     */
    @Column(name = "company_id", nullable = true, length = 10)
    private Integer companyId;

    /**
     * 部门id
     */
    @Column(name = "department_id", nullable = true, length = 10)
    private Integer departmentId;

    /**
     * 是否上报  1：未上报  2：已上报
     */
    @Column(name = "is_upload", nullable = true, length = 10)
    private Integer isUpload;

    /**
     *
     */
    @Column(name = "illegal_use", nullable = true, length = 10)
    private Integer illegalUse;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REGION_ID", referencedColumnName = "REGION_ID" ,insertable = false, updatable = false)
    private TRegion findRegion;


    /**
     * 案件状态
     */
    public enum CaseStatus {
        /**
         * 所有
         */
        ALL(0, 0),
        /**
         * 预立案
         */
        PREPARE(1,2),
        /**
         * 巡查制止
         */
        INSPECT(2,3),
        /**
         * 申请结案
         */
        APPLY(3,4),
        /**
         * 结案审核通过
         */
        FIRST_OVER(4,5),
        /**
         * 二次结案审核通过
         */
        SECOND_OVER(5),

        /**
         * 删除
         */
        DEL(6),

        /**
         * 撤销案件
         */
        CANCEL(7);

        /**
         * 移动端码
         */
        private int moblieCode;

        private int code;

        CaseStatus(int code) {
            this.code = code;
        }

        CaseStatus(int code,int moblieCode) {

            this.moblieCode = moblieCode;
            this.code = code;
        }

        /**
         * 获取枚举对象
         *
         * @param mobileCode 码
         * @return
         */
        public static CaseStatus getStatusMobileCode(int mobileCode) {
            CaseStatus[] sources = CaseStatus.values();
            for (CaseStatus source : sources) {
                if (source.moblieCode == mobileCode) {
                    return source;
                }
            }
            return null;
        }

        /**
         * 获取枚举对象
         *
         * @param code 码
         * @return
         */
        public static CaseStatus get(int code) {
            CaseStatus[] sources = CaseStatus.values();
            for (CaseStatus source : sources) {
                if (source.code() == code) {
                    return source;
                }
            }
            return null;
        }

        public int code() {
            return code;
        }

        public int mobileCode() {
            return moblieCode;
        }
    }

    /**
     * 回收状态
     */
    public enum RecycleStatus {
        /**
         * 正常
         */
        NO(0),
        /**
         * 禁用
         */
        YES(1);

        private int code;

        RecycleStatus(int code) {
            this.code = code;
        }

        /**
         * 获取枚举对象
         *
         * @param code 码
         * @return
         */
        public static RecycleStatus get(int code) {
            RecycleStatus[] sources = RecycleStatus.values();
            for (RecycleStatus source : sources) {
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
