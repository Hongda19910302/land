package net.deniro.land.module.icase.dao;

import net.deniro.land.common.dao.BaseDao;
import net.deniro.land.module.icase.entity.TAttachment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static net.deniro.land.module.icase.entity.TAttachmentRelation.RelationType;

/**
 * 附件
 *
 * @author deniro
 *         2015/11/12
 */
@Repository
public class AttachmentDao extends BaseDao<TAttachment> {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * 删除所有相关记录
     *
     * @param relationId   关联的ID
     * @param relationType 关联类型
     * @return 返回受影响的条数
     */
    public int deleteAll(Integer relationId, RelationType relationType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("relationId", relationId);
        params.put("relationType", relationType.code());

        String delRelationSql = "DELETE FROM " +
                "t_attachment_relation WHERE " +
                "RELATION_ID=:relationId AND RELATION_TYPE=:relationType";

        String delAttachmentSql = "DELETE FROM t_attachment  WHERE ATTACHMENT_ID in(SELECT " +
                "w.ATTACHMENT_ID FROM t_attachment_relation w WHERE w.RELATION_ID=:relationId AND w.RELATION_TYPE=:relationType)";

        int count = 0;
        count = count + namedParameterJdbcTemplate.update(delRelationSql, params);
        count = count + namedParameterJdbcTemplate.update(delAttachmentSql, params);
        return count;
    }

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
