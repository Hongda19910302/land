package net.deniro.land.module.system.dao;

import net.deniro.land.common.dao.BaseDao;
import net.deniro.land.common.dao.Page;
import net.deniro.land.common.service.Constants;
import net.deniro.land.module.system.entity.TVersionItem;
import net.deniro.land.module.system.entity.VersionItemQueryParam;
import net.deniro.land.module.system.entity.VersionQueryParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;

/**
 * 版本项
 *
 * @author deniro
 *         2015/11/25
 */
@Repository
public class VersionItemDao extends BaseDao<TVersionItem> {

    /**
     * 分页查询
     *
     * @param queryParam 查询参数
     * @return
     */
    public Page findPage(VersionItemQueryParam queryParam) {
        StringBuilder hql = new StringBuilder(" from TVersionItem t where 1=1 ");

        LinkedHashMap<String, Object>
                params = new LinkedHashMap<String, Object>();

        /**
         * 新增查询条件
         */
        if (StringUtils.isNotBlank(queryParam.getType())) {
            hql.append(" and t.type = :getType");
            params.put("getType", queryParam.getType());
        }

        hql.append(" order by t.id desc");

        return super.pagedQuery(hql.toString(), queryParam.getPageNum(), queryParam
                .getNumPerPage(), params);
    }
}
