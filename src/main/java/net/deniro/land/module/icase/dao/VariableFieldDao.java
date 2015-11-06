package net.deniro.land.module.icase.dao;

import net.deniro.land.common.dao.BaseDao;
import net.deniro.land.common.dao.Page;
import net.deniro.land.module.icase.entity.TVariableField;
import net.deniro.land.module.icase.entity.VariableFieldQueryParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static net.deniro.land.module.icase.entity.TVariableField.BelongToTable.T_CASE;
import static net.deniro.land.module.icase.entity.TVariableField.Status.*;

/**
 * 字段（主要是案件字段）
 *
 * @author deniro
 *         2015/11/4
 */
@Repository
public class VariableFieldDao extends BaseDao<TVariableField> {

    /**
     * 依据单位ID，获取字段信息
     *
     * @param companyId
     * @return
     */
    public List<TVariableField> findByCompanyId(Integer companyId) {
        StringBuilder hql = new StringBuilder(" from TVariableField t where 1=1 ");
        hql.append(" and company.companyId = ?");
        hql.append(" and tableType =  ").append(T_CASE.code());
        hql.append(" and status =").append(AVAILABLE.code());
        return this.find(hql.toString(), companyId);
    }

    /**
     * 分页查询
     *
     * @param queryParam 查询参数
     * @return
     */
    public Page findPage(VariableFieldQueryParam queryParam) {
        StringBuilder hql = new StringBuilder(" from TVariableField t where 1=1 ");

        /**
         * 新增查询条件
         */
        if (StringUtils.isNotBlank(queryParam.getFieldName())) {
            hql.append(" and t.dataField.fieldName like '").append(queryParam.getFieldName())
                    .append("%'");
        }
        if (StringUtils.isNotBlank(queryParam.getCompanyId())) {
            hql.append(" and t.companyId = '").append(queryParam.getCompanyId()).append("'");
        }

        hql.append(" order by variableFieldId desc");

        return super.pagedQuery(hql.toString(), queryParam.getPageNum(), queryParam
                .getNumPerPage());
    }
}
