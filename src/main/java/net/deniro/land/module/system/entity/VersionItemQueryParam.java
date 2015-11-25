package net.deniro.land.module.system.entity;

import lombok.Data;
import net.deniro.land.common.entity.QueryParam;

/**
 * 版本项
 *
 * @author deniro
 *         2015/11/25
 */
@Data
public class VersionItemQueryParam extends QueryParam {
    /**
     * 版本项类型
     */
    private String type;

    /**
     * 版本说明ID
     */
    private String versionId;
}
