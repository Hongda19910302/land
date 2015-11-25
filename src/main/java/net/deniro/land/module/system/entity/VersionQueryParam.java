package net.deniro.land.module.system.entity;

import lombok.Data;
import net.deniro.land.common.entity.QueryParam;

import java.util.Date;

/**
 * 版本 查询参数
 *
 * @author deniro
 *         2015/11/25
 */
@Data
public class VersionQueryParam extends QueryParam {
    /**
     * 版本号
     */
    private String no;

    /**
     * 创建时间开始
     */
    private String createTimeBegin;

    /**
     * 创建时间结束
     */
    private String createTimeEnd;
}
