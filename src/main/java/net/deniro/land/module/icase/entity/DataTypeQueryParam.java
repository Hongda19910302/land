package net.deniro.land.module.icase.entity;

import lombok.Data;
import net.deniro.land.common.entity.QueryParam;

/**
 * 案件下拉项
 *
 * @author deniro
 *         2015/11/4
 */
@Data
public class DataTypeQueryParam extends QueryParam {

    /**
     * 数据库对应类型名
     */
    private String dataTypeName;


}
