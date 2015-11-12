package net.deniro.land.module.icase.service;

import net.deniro.land.common.dao.Page;
import net.deniro.land.module.icase.dao.CaseDao;
import net.deniro.land.module.icase.dao.InspectDao;
import net.deniro.land.module.icase.entity.*;
import net.deniro.land.module.system.dao.UserDao;
import net.deniro.land.module.system.entity.User;
import org.apache.commons.collections.map.MultiKeyMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Column;
import java.lang.reflect.Field;
import java.util.List;

/**
 * 案件
 *
 * @author deniro
 *         2015/11/9
 */
@Service
public class CaseService {

    static Logger logger = Logger.getLogger(CaseService.class);

    @Autowired
    private CaseDao caseDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private InspectDao inspectDao;

    public TInspect findInspectByCaseId(Integer caseId){
//        inspectDao.get()
        return null;
    }

    /**
     * 依据案件ID，查询案件可变字段列表
     *
     * 设置字段值、下拉框显示名称
     *
     * @param caseId
     * @return
     */
    public List<CaseVariableField> findVariablesById(Integer caseId) {

        List<CaseVariableField> variableFields = caseDao.findVariablesById(caseId);

        /**
         * 组装多键Map；key：可变字段+数据值；value:选择类型名称
         */
        MultiKeyMap data = new MultiKeyMap();
        List<VariableDataValueSelectName> vsd = caseDao.findAllVSD();
        for (VariableDataValueSelectName v : vsd) {
            data.put(v.getVariableFieldId(), v.getDataTypeValue(), v.getSelectTypeName());
        }

        /**
         * 设置字段值、下拉框显示名称
         */
        TCase tCase = caseDao.findById(caseId);
        for (CaseVariableField v : variableFields) {
            Object fieldValue = getFieldValue(tCase, v.getTableField());
            v.setFieldValue(fieldValue);
            v.setFieldShow((String) data.get(v.getVariableFieldId(), NumberUtils.toInt
                    (String.valueOf(fieldValue))));
        }

        return variableFields;
    }

    /**
     * 获取对象实例中的字段值
     *
     * @param t         对象实例
     * @param fieldName 对象名称
     * @param <T>       对象类型
     * @return
     */
    private static <T> Object getFieldValue(T t, String fieldName) {
        Object r = null;
        try {
            Class clazz = t.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                Column column = field.getAnnotation(Column.class);
                if (column != null && StringUtils.equalsIgnoreCase(column.name(), fieldName)) {
                    field.setAccessible(true);
                    r = field.get(t);
                }
            }
        } catch (IllegalAccessException e) {
            logger.error("获取对象实例中的字段值", e);
        }
        return r;
    }

    /**
     * 依据ID，获取案件
     *
     * @param caseId
     * @return
     */
    public TCase findById(Integer caseId) {
        try {
            return caseDao.findById(caseId);
        } catch (Exception e) {
            logger.error(" 依据ID，获取案件", e);
            return new TCase();
        }
    }

    /**
     * 分页查询
     *
     * @param queryParam 查询参数
     * @return
     */
    public Page findPage(CaseQueryParam queryParam) {
        try {

            User user = userDao.get(NumberUtils.toInt(queryParam.getUserId()));
            if (user != null) {
                queryParam.setDepartmentId(String.valueOf(user.getDepartmentId()));
            }

            return caseDao.findPage(queryParam);
        } catch (Exception e) {
            logger.error("分页查询", e);
            return new Page();
        }
    }


}
