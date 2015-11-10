package net.deniro.land.module.icase.entity;

import lombok.Data;
import net.deniro.land.common.entity.QueryParam;

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

}
