package net.deniro.land.module.component.dao;

import net.deniro.land.common.dao.BaseDao;
import net.deniro.land.common.dao.Page;
import net.deniro.land.common.service.Constants;
import net.deniro.land.module.component.entity.CompPageSearch;
import net.deniro.land.module.component.entity.CompPageSearchQueryParam;
import net.deniro.land.module.system.entity.VersionQueryParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;

/**
 * 分页查询组件
 *
 * @author deniro
 *         2015/10/26
 */
@Repository
public class CompPageSearchDao extends BaseDao<CompPageSearch>{

    /**
     * 分页查询
     *
     * @param queryParam 查询参数
     * @return
     */
    public Page findPage(CompPageSearchQueryParam queryParam) {
        StringBuilder hql = new StringBuilder(" from CompPageSearch t where 1=1 ");

        LinkedHashMap<String, Object>
                params = new LinkedHashMap<String, Object>();

        /**
         * 新增查询条件
         */
        if (StringUtils.isNotBlank(queryParam.getName())) {
            hql.append(" and t.name like :getName");
            params.put("getName", queryParam.getName() + "%");
        }

        hql.append(" order by t.id desc");

        return super.pagedQuery(hql.toString(), queryParam.getPageNum(), queryParam
                .getNumPerPage(), params);
    }
}
