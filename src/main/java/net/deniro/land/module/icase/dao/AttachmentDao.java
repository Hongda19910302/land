package net.deniro.land.module.icase.dao;

import net.deniro.land.common.dao.BaseDao;
import net.deniro.land.module.icase.entity.TAttachment;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;

import static net.deniro.land.module.icase.entity.TAttachmentRelation.RelationType;

/**
 * 附件
 *
 * @author deniro
 *         2015/11/12
 */
@Repository
public class AttachmentDao extends BaseDao<TAttachment> {

    /**
     * 查询附件列表
     *
     * @param auditId      审核记录ID
     * @param relationType 关联类型
     * @return
     */
    public List<TAttachment> findByAuditIdAndType(Integer auditId, RelationType relationType) {
        StringBuilder hql = new StringBuilder("from TAttachment t where t.attachmentId in ( select r" +
                ".attachmentId from TAttachmentRelation r where 1=1 ");

        LinkedHashMap<String, Object>
                params = new LinkedHashMap<String, Object>();

        if (auditId != null) {
            hql.append(" and r.relationId=:auditId ");
            params.put("auditId", auditId);
        }
        if (relationType != null) {
            hql.append(" and r.relationType=:relationType ");
            params.put("relationType", relationType.code());
        }

        hql.append(")");

        return this.findByNamedParam(hql.toString(), params);
    }


}
