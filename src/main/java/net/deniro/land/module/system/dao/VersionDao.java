package net.deniro.land.module.system.dao;

import net.deniro.land.common.dao.BaseDao;
import net.deniro.land.common.dao.Page;
import net.deniro.land.common.service.Constants;
import net.deniro.land.module.system.entity.TVersion;
import net.deniro.land.module.system.entity.VersionQueryParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;

/**
 * 版本说明
 *
 * @author deniro
 *         2015/11/25
 */
@Repository
public class VersionDao extends BaseDao<TVersion> {

    /**
     * 分页查询
     *
     * @param queryParam 查询参数
     * @return
     */
    public Page findPage(VersionQueryParam queryParam) {
        StringBuilder hql = new StringBuilder(" from TVersion t where 1=1 ");

        LinkedHashMap<String, Object>
                params = new LinkedHashMap<String, Object>();

        /**
         * 新增查询条件
         */
        if (StringUtils.isNotBlank(queryParam.getNo())) {
            hql.append(" and t.no like :getNo");
            params.put("getNo", queryParam.getNo() + "%");
        }
        if (StringUtils.isNotBlank(queryParam.getCreateTimeBegin())) {
            hql.append(" and date_format(t.createTime,'" + Constants.MYSQL_DATE_FORMAT +
                    "')" +
                    ">=").append(":getCreateTimeBegin");
            params.put("getCreateTimeBegin", queryParam.getCreateTimeBegin());
        }
        if (StringUtils.isNotBlank(queryParam.getCreateTimeEnd())) {
            hql.append(" and date_format(t.createTime,'" + Constants.MYSQL_DATE_FORMAT + "')" +
                    "<=").append(":getCreateTimeEnd");
            params.put("getCreateTimeEnd", queryParam.getCreateTimeEnd());
        }

        hql.append(" order by t.createTime desc");

        return super.pagedQuery(hql.toString(), queryParam.getPageNum(), queryParam
                .getNumPerPage(), params);
    }

}
