package net.deniro.land.module.icase.dao;

import net.deniro.land.common.dao.BaseDao;
import net.deniro.land.common.dao.Page;
import net.deniro.land.module.icase.entity.TDataType;
import net.deniro.land.module.icase.entity.DataTypeQueryParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

/**
 * 案件下拉项
 *
 * @author deniro
 *         2015/11/4
 */
@Repository
public class DataTypeDao extends BaseDao<TDataType> {

    /**
     * 分页查询
     *
     * @param queryParam 查询参数
     * @return
     */
    public Page findPage(DataTypeQueryParam queryParam) {
        StringBuilder hql = new StringBuilder(" from TDataType t where 1=1 ");

        /**
         * 新增查询条件
         */
        if (StringUtils.isNotBlank(queryParam.getDataTypeName())) {
            hql.append(" and t.dataTypeName like '").append(queryParam.getDataTypeName())
                    .append("%'");
        }


        hql.append(" order by t.dataTypeId desc");

        return super.pagedQuery(hql.toString(), queryParam.getPageNum(), queryParam
                .getNumPerPage());
    }
}
