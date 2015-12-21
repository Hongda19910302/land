package net.deniro.land.module.component.dao;

import net.deniro.land.common.dao.BaseDao;
import net.deniro.land.common.dao.Page;
import net.deniro.land.module.component.entity.CompPageSearchToolBar;
import net.deniro.land.module.component.entity.CompPageSearchToolBarQueryParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;

/**
 * 分页查询组件中的分页表格
 *
 * @author deniro
 *         2015/12/21
 */
@Repository
public class CompPageSearchToolBarDao extends BaseDao<CompPageSearchToolBar> {

    /**
     * 分页查询
     *
     * @param queryParam 查询参数
     * @return
     */
    public Page findPage(CompPageSearchToolBarQueryParam queryParam) {
        StringBuilder hql = new StringBuilder(" from CompPageSearchToolBar t where 1=1 ");

        LinkedHashMap<String, Object>
                params = new LinkedHashMap<String, Object>();

        /**
         * 新增查询条件
         */
        if (StringUtils.isNotBlank(queryParam.getBtnName())) {
            hql.append(" and t.btnName like :getBtnName");
            params.put("getBtnName", queryParam.getBtnName()+"%");
        }
        if (queryParam.getCid() != null) {
            hql.append(" and t.compPageSearch.id = :getId");
            params.put("getId", queryParam.getCid());
        }

        hql.append(" order by t.orderNo");

        return super.pagedQuery(hql.toString(), queryParam.getPageNum(), queryParam
                .getNumPerPage(), params);
    }
}
