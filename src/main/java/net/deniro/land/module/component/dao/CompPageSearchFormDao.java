package net.deniro.land.module.component.dao;

import net.deniro.land.common.dao.BaseDao;
import net.deniro.land.common.dao.Page;
import net.deniro.land.module.component.entity.CompPageSearchForm;
import net.deniro.land.module.component.entity.CompPageSearchFormQueryParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;

/**
 * 分页查询组件中的查询表单
 *
 * @author deniro
 *         2015/12/21
 */
@Repository
public class CompPageSearchFormDao extends BaseDao<CompPageSearchForm> {

    /**
     * 分页查询
     *
     * @param queryParam 查询参数
     * @return
     */
    public Page findPage(CompPageSearchFormQueryParam queryParam) {
        StringBuilder hql = new StringBuilder(" from CompPageSearchForm t where 1=1 ");

        LinkedHashMap<String, Object>
                params = new LinkedHashMap<String, Object>();

        /**
         * 新增查询条件
         */
        if (StringUtils.isNotBlank(queryParam.getDisplayName())) {
            hql.append(" and t.displayName like :getDisplayName");
            params.put("getDisplayName", queryParam.getDisplayName() + "%");
        }
        if(queryParam.getCid()!=null){
            hql.append(" and t.compPageSearch.id = :getId");
            params.put("getId", queryParam.getCid());
        }

        hql.append(" order by t.orderNo");

        return super.pagedQuery(hql.toString(), queryParam.getPageNum(), queryParam
                .getNumPerPage(), params);
    }
}
