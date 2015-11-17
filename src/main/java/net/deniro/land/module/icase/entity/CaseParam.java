package net.deniro.land.module.icase.entity;

import lombok.Data;
import net.deniro.land.common.entity.QueryParam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 案件 查询参数（也可作为提交参数）
 *
 * @author deniro
 *         2015/11/10
 */
@Data
public class CaseParam extends QueryParam {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 部门ID
     */
    private String departmentId;

    /**
     * 移动端的状态
     */
    private String moblieStatus;

    /**
     * 开始时间
     */
    private String beginDate;

    /**
     * 结束时间
     */
    private String endDate;

    /**
     * 案件号
     */
    private String caseNum;

    /**
     * 案件状态
     */
    private Integer caseStatus;

    /**
     * 违法当事人
     */
    private String parties;

    /**
     * 巡查员姓名
     */
    private String xcyName;

    /**
     * 创建人姓名
     */
    private String creatorName;

    /**
     * 地区id
     */
    private Integer regionId;

    /**
     * 包含的状态
     */
    private List<TCase.CaseStatus> includeStatus = new ArrayList<TCase.CaseStatus>();

    /**
     * 东至
     */
    private String eastTo;

    /**
     * 南至
     */
    private String southTo;

    /**
     * 西至
     */
    private String westTo;

    /**
     * 北至
     */
    private String northTo;

    /**
     * 违法地点
     */
    private String illegalAddr;

    /**
     * 违法面积
     */
    private Float illegalAreaSpace;

    /**
     * 备注
     */
    private String remark;

    /**
     * Gps类型
     */
    private Integer gpsFlag;

    /**
     * Gps经度
     */
    private BigDecimal lng;

    /**
     * Gps纬度
     */
    private BigDecimal lat;

    /**
     * 巡查员id
     */
    private Integer inspectorId;

    /**
     * 身份证号码
     */
    private String idCardNum;

    /**
     * 占地面积
     */
    private Float floorSpace;

    /**
     * 建筑面积
     */
    private Float buildingSpace;

    /**
     * 违建类型
     */
    private Integer illegalType;

    /**
     * 用地性质
     */
    private Integer landUsage;

    /**
     * 违法现状
     */
    private Integer currentStatus;

    /**
     * 巡查结果
     */
    private Integer inspectResult;

    /**
     * 案件来源
     */
    private Integer caseSource;

    /**
     * 状态
     */
    private String status;

    /**
     * 公司ID
     */
    private Integer companyId;

    /**
     * 案件ID
     */
    private Integer caseId;

    /**
     * 照片；json字符串，格式见 net.deniro.land.api.entity.Images
     */
    private String images;

    /**
     * 未知
     */
    private Integer illegalUse;

    /**
     * 回收状态
     */
    private String recycleStatus;

    /**
     * 创建开始日期 格式yyyy-MM-dd
     */
    private String createBeginDate;


}
