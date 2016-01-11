package net.deniro.land.module.icase.dao;

import net.deniro.land.common.dao.BaseDao;
import net.deniro.land.module.icase.entity.TDataField;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 数据库对应表字段
 *
 * @author deniro
 *         2016/1/11
 */
@Repository
public class DataFieldDao extends BaseDao<TDataField> {

    /**
     * 查询所有
     *
     * @return
     */
    public List<TDataField> findAll() {
        StringBuilder hql = new StringBuilder(" from TDataField where 1=1 ");
        return this.find(hql.toString(), new Object[]{});
    }
}
