package net.deniro.land.module.icase.service;

import net.deniro.land.module.icase.dao.DataFieldDao;
import net.deniro.land.module.icase.entity.TDataField;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 数据库对应表字段
 *
 * @author deniro
 *         2016/1/11
 */
@Service
public class DataFieldService {

    static Logger logger = Logger.getLogger(DataFieldService.class);

    @Autowired
    private DataFieldDao dataFieldDao;

    /**
     * 查询所有
     *
     * @return
     */
    public List<TDataField> findAll() {
        return dataFieldDao.findAll();
    }

    /**
     * 查询所有（用于下拉选择）,key：ID；value：名称
     *
     * @return
     */
    public Map<String, String> findAllInSelect() {

        List<String> supportedFieldNames = Arrays.asList("用地性质", "违建类型",
                "巡查结果",
                "案件来源");//目前支持的字段名称列表

        try {
            Map<String, String> maps = new LinkedHashMap<String, String>();
            List<TDataField> entities = dataFieldDao.findAll();
            for (TDataField entity : entities) {
                if (supportedFieldNames.contains(entity.getFieldName()))
                    maps.put(String.valueOf(entity.getDataFieldId()), entity.getFieldName());
            }
            return maps;
        } catch (Exception e) {
            logger.error("查询所有", e);
            return new HashMap<String, String>();
        }
    }
}
