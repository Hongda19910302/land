package net.deniro.land.module.icase.entity;

import lombok.Data;
import net.deniro.land.common.entity.QueryParam;

import java.util.ArrayList;
import java.util.List;

/**
 * 案件 查询参数
 *
 * @author deniro
 *         2015/11/10
 */
@Data
public class CaseQueryParam extends QueryParam {

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
    private List<TCase.CaseStatus> includeStatus=new ArrayList<TCase.CaseStatus>();

}
