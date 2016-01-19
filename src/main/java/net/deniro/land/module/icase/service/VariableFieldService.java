package net.deniro.land.module.icase.service;

import net.deniro.land.common.dao.Page;
import net.deniro.land.module.icase.dao.DataTypeDao;
import net.deniro.land.module.icase.dao.SelectTypeConfDao;
import net.deniro.land.module.icase.dao.VariableFieldDao;
import net.deniro.land.module.icase.entity.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 案件字段
 *
 * @author deniro
 *         2015/11/4
 */
@Service
public class VariableFieldService {

    static Logger logger = Logger.getLogger(VariableFieldService.class);

    @Autowired
    private VariableFieldDao variableFieldDao;

    @Autowired
    private SelectTypeConfDao selectTypeConfDao;

    @Autowired
    private DataTypeDao dataTypeDao;

    /**
     * 查询所有下拉项信息
     *
     * @param variableFieldId 可变字段ID
     * @return
     */
    public List<TDataType> findAllNodes(Integer variableFieldId) {
        try {
            List<TDataType> dataTypes = dataTypeDao.findAll();
            if (dataTypes == null)
                return new ArrayList<TDataType>();

            //勾选有设置的下拉项
            List<TDataType> selectedDataTypes = dataTypeDao.findByVariableId
                    (variableFieldId);
            for (TDataType dataType : dataTypes) {
                for (TDataType selectedDataType : selectedDataTypes) {
                    if (dataType.getDataTypeId() == selectedDataType.getDataTypeId()) {
                        dataType.setChecked("true");
                    }
                    dataType.setName(dataType.getDataTypeName());
                    dataType.setId(dataType.getDataTypeId());
                }
            }
            return dataTypes;
        } catch (Exception e) {
            logger.error("查询所有下拉项信息", e);
            return new ArrayList<TDataType>();
        }
    }

    /**
     * 删除
     *
     * @param confId
     * @return
     */
    public boolean delete(Integer confId) {
        try {
            TSelectTypeConf conf = selectTypeConfDao.get(confId);
            if (conf != null) {
                TVariableField variableField = variableFieldDao.get(conf.getVariableFieldId
                        ());
                if (variableField != null) {
                    variableFieldDao.remove(variableField);
                }

                selectTypeConfDao.remove(conf);
            }

            return true;
        } catch (Exception e) {
            logger.error("删除", e);
            return false;
        }
    }

    /**
     * 更新
     *
     * @param entity
     * @return
     */
    public boolean update(TVariableField entity) {
        try {
            variableFieldDao.update(entity);
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
    public boolean add(TVariableField entity) {
        try {
            variableFieldDao.save(entity);

            selectTypeConfDao.insert(entity.getVariableFieldId(), entity.getDataTypeId());
            return true;
        } catch (Exception e) {
            logger.error("新增", e);
            return false;
        }
    }

    /**
     * 依据ID，获取案件字段对象
     *
     * @param id
     * @return
     */
    public CaseField findRelationById(Integer id) {
        try {
            TSelectTypeConf conf = selectTypeConfDao.get(id);
            CaseField entity = new CaseField();
            entity.setConfId(conf.getConfId());
            entity.setVariableFieldId(conf.getVariableFieldId());
            entity.setDataTypeId(conf.getSelectTypeId());
            return entity;
        } catch (Exception e) {
            logger.error(" 依据ID，获取案件字段对象", e);
            return new CaseField();
        }
    }


    /**
     * 依据ID，获取对象
     *
     * @param id
     * @return
     */
    public TVariableField findById(Integer id) {
        try {
            return variableFieldDao.get(id);
        } catch (Exception e) {
            logger.error(" 依据ID，获取对象", e);
            return new TVariableField();
        }
    }

    /**
     * 依据单位ID和客户端对应字段key，获取字段信息
     *
     * @param companyId 单位ID
     * @param fieldKey  客户端对应字段key
     * @return
     */
    public TVariableField find(Integer companyId, String fieldKey) {
        try {
            return variableFieldDao.find(companyId, fieldKey);
        } catch (Exception e) {
            logger.error(" 依据单位ID和客户端对应字段key，获取字段信息", e);
            return new TVariableField();
        }
    }

    /**
     * 依据单位ID，获取字段信息
     *
     * @param companyId
     * @return
     */
    public List<TVariableField> findByCompanyId(Integer companyId) {
        try {
            return variableFieldDao.findByCompanyId(companyId);
        } catch (Exception e) {
            logger.error("依据单位ID，获取字段信息", e);
            return new ArrayList<TVariableField>();
        }
    }

    /**
     * 分页查询
     *
     * @param queryParam 查询参数
     * @return
     */
    public Page findPage(VariableFieldQueryParam queryParam) {
        try {
            return variableFieldDao.findPage(queryParam);
        } catch (Exception e) {
            logger.error("分页查询", e);
            return new Page();
        }
    }
}
