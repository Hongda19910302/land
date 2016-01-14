package net.deniro.land.module.icase.service;

import net.deniro.land.common.dao.Page;
import net.deniro.land.module.icase.dao.DataTypeDao;
import net.deniro.land.module.icase.entity.DataTypeQueryParam;
import net.deniro.land.module.icase.entity.TDataType;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 案件下拉项
 *
 * @author deniro
 *         2015/11/4
 */
@Service
public class DataTypeService {

    static Logger logger = Logger.getLogger(DataTypeService.class);

    @Autowired
    private DataTypeDao dataTypeDao;

    /**
     * 更新
     *
     * @param entity
     * @return
     */
    public boolean update(TDataType entity) {
        try {
            dataTypeDao.update(entity);
            return true;
        } catch (Exception e) {
            logger.error("更新", e);
            return false;
        }
    }

    /**
     * 新增
     *
     * @param entity
     * @return
     */
    public boolean add(TDataType entity) {
        try {
            dataTypeDao.save(entity);
            return true;
        } catch (Exception e) {
            logger.error("新增", e);
            return false;
        }
    }

    /**
     * 依据ID，获取对象
     *
     * @param dataTypeId
     * @return
     */
    public TDataType findById(Integer dataTypeId) {
        try {
            return dataTypeDao.get(dataTypeId);
        } catch (Exception e) {
            logger.error(" 依据ID，获取对象", e);
            return new TDataType();
        }
    }

    /**
     * 依据可变字段key,获取数据键值对
     *
     * @param variableFieldKey
     * @return
     */
    public List<TDataType> findByVariableFieldKey(String variableFieldKey) {
        try {
            return dataTypeDao.findByVariableFieldKey(variableFieldKey);
        } catch (Exception e) {
            logger.error(" 依据可变字段key,获取数据键值对", e);
            return new ArrayList<TDataType>();
        }
    }

    /**
     * 查询所有（用于下拉选择）,key：ID；value：名称
     *
     * @return
     */
    public Map<String, String> findAllInSelect() {
        try {
            Map<String, String> maps = new LinkedHashMap<String, String>();
            List<TDataType> entities = dataTypeDao.findAll();
            for (TDataType entity : entities) {
                maps.put(String.valueOf(entity.getDataTypeId()), entity.getDataTypeName());
            }
            return maps;
        } catch (Exception e) {
            logger.error("查询所有", e);
            return new HashMap<String, String>();
        }
    }

    /**
     * 分页查询
     *
     * @param queryParam 查询参数
     * @return
     */
    public Page findPage(DataTypeQueryParam queryParam) {
        try {
            return dataTypeDao.findPage(queryParam);
        } catch (Exception e) {
            logger.error("分页查询", e);
            return new Page();
        }
    }
}


