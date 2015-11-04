package net.deniro.land.module.system.entity;

import lombok.Data;
import net.deniro.land.common.entity.QueryParam;

/**
 * 单位 查询参数
 *
 * @author deniro
 *         2015/11/4
 */
@Data
public class CompanyQueryParam extends QueryParam {

    /**
     * 名称
     */
    private String companyName;

    /**
     * 状态 0:正常 1：禁用 2：删除
     */
    private String status;

    /**
     * 所属单位ID
     */
    private String companyId;
}
