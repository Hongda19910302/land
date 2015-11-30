package net.deniro.land.module.system.dao;

import net.deniro.land.common.dao.BaseDao;
import net.deniro.land.common.dao.Page;
import net.deniro.land.module.system.entity.TVersionItem;
import net.deniro.land.module.system.entity.VersionItemQueryParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 版本项
 *
 * @author deniro
 *         2015/11/25
 */
@Repository
public class VersionItemDao extends BaseDao<TVersionItem> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 分组统计每个版本的版本项总数
     *
     * @return
     */
    public Map<Integer, Long> countByVersionIdGroup() {
        StringBuilder sql = new StringBuilder(" SELECT version_id,count(1) count FROM " +
                "t_version_item t");
        sql.append(" GROUP BY t.version_id");

        List<Map<String, Object>> data = jdbcTemplate.queryForList(sql.toString());
        Map<Integer, Long> count = new HashMap<Integer, Long>();
        for (Map<String, Object> o : data) {
            count.put((Integer) o.get("version_id"), (Long) o.get("count"));
        }
        return count;
    }

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
        if (StringUtils.isNotBlank(queryParam.getVersionId())) {
            hql.append(" and t.versionId = :getVersionId");
            params.put("getVersionId", NumberUtils.toInt(queryParam.getVersionId()));
        }

        hql.append(" order by t.id desc");

        return super.pagedQuery(hql.toString(), queryParam.getPageNum(), queryParam
                .getNumPerPage(), params);
    }
}
