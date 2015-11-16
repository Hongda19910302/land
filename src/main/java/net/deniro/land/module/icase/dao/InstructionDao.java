package net.deniro.land.module.icase.dao;

import net.deniro.land.common.dao.BaseDao;
import net.deniro.land.common.dao.Page;
import net.deniro.land.module.icase.entity.InstructionQueryParam;
import net.deniro.land.module.icase.entity.TInstruction;
import net.deniro.land.module.system.entity.DepartmentQueryParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

/**
 * 案件批示
 *
 * @author deniro
 *         2015/11/16
 */
@Repository
public class InstructionDao extends BaseDao<TInstruction> {

    /**
     * 分页查询
     *
     * @param queryParam 查询参数
     * @return
     */
    public Page findPage(InstructionQueryParam queryParam) {
        StringBuilder hql = new StringBuilder(" from TInstruction t where 1=1 ");

        /**
         * 新增查询条件
         */
        if (StringUtils.isNotBlank(queryParam.getStatus())) {
            hql.append(" and t.status = '").append(queryParam.getStatus())
                    .append("'");
        }
        if (StringUtils.isNotBlank(queryParam.getCaseId())) {
            hql.append(" and t.caseId = '").append(queryParam.getCaseId()).append("'");
        }

        hql.append(" order by instructionDate desc");

        return super.pagedQuery(hql.toString(), queryParam.getPageNum(), queryParam
                .getNumPerPage());
    }

}
