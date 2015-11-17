package net.deniro.land.module.icase.entity;

import lombok.Data;
import net.deniro.land.common.entity.QueryParam;

/**
 * 案件批示查询参数
 *
 * @author deniro
 *         2015/11/16
 */
@Data
public class InstructionQueryParam extends QueryParam {

    /**
     * 状态
     */
    private String status;

    /**
     * 案件ID
     */
    private String caseId;

    /**
     * 用户ID
     */
    private String userId;

}
