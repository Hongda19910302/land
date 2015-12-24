package net.deniro.land.module.icase.dao;

import net.deniro.land.common.dao.BaseDao;
import net.deniro.land.common.dao.Page;
import net.deniro.land.common.service.Constants;
import net.deniro.land.module.icase.entity.CaseParam;
import net.deniro.land.module.icase.entity.CaseVariableField;
import net.deniro.land.module.icase.entity.TCase;
import net.deniro.land.module.icase.entity.TCase.CaseStatus;
import net.deniro.land.module.icase.entity.VariableDataValueSelectName;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.deniro.land.module.icase.entity.TCase.CaseStatus.*;
import static net.deniro.land.module.icase.entity.TCase.RecycleStatus.NO;

/**
 * 案件
 *
 * @author deniro
 *         2015/11/9
 */
@Repository
public class CaseDao extends BaseDao<TCase> {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * 统计案件数
     * <p>
     * 查询参数中，只做了部分条件【创建开始时间、部门ID、回收状态】
     *
     * @param param
     * @return
     */
    public int count(CaseParam param) {
        StringBuilder sql = new StringBuilder("select count(1) from t_case t where 1=1 ");

        Map<String, Object> params = new HashMap<String, Object>();

        if (param.getCreateBeginDate() != null) {
            sql.append(" and date_format(t.create_time,'%Y-%m-%d')>=:beginDate");
            params.put("beginDate", param.getCreateBeginDate());
        }

        if (StringUtils.isNotBlank(param.getDepartmentId())) {
            sql.append(" and department_id=:departmentId");
            params.put("departmentId", param.getDepartmentId());
        }

        if (StringUtils.isNotBlank(param.getRecycleStatus())) {
            sql.append(" and recycle_status=:recycleStatus");
            params.put("recycleStatus", param.getRecycleStatus());
        }

        return namedParameterJdbcTemplate.queryForInt(sql.toString(), params);

    }

    /**
     * 查询所有 可变字段+数据值，确定选择类型名称 映射列表
     * <p>
     * 有需要，可以做成系统缓存
     *
     * @return
     */
    public List<VariableDataValueSelectName> findAllVSD() {
        StringBuilder sql = new StringBuilder("SELECT * FROM v_find_select_type_by_variable_and_value");

        Map<String, Object> params = new HashMap<String, Object>();
        return namedParameterJdbcTemplate.query(sql.toString(), params, new
                RowMapper<VariableDataValueSelectName>() {
                    public VariableDataValueSelectName mapRow(ResultSet resultSet, int i) throws SQLException {
                        Integer variableFieldId = resultSet.getInt("VARIABLE_FIELD_ID");
                        Integer dataTypeValue = resultSet.getInt("DATA_TYPE_VALUE");
                        String selectTypeName = resultSet.getString("SELECT_TYPE_NAME");

                        VariableDataValueSelectName entity = new
                                VariableDataValueSelectName();
                        entity.setDataTypeValue(dataTypeValue);
                        entity.setSelectTypeName(selectTypeName);
                        entity.setVariableFieldId(variableFieldId);
                        return entity;
                    }
                });
    }

    /**
     * 依据案件ID，查询案件可变字段列表
     *
     * @param caseId
     * @return
     */
    public List<CaseVariableField> findVariablesById(Integer caseId) {
        StringBuilder sql = new StringBuilder("SELECT x.FIELD_KEY, x.ALIAS, x.IS_HIDE,x.IS_PULLDOWN,x.VARIABLE_FIELD_ID,y.TABLE_FIELD");
        sql.append(" FROM t_variable_field x,t_data_field y");
        sql.append(" where x.DATA_FIELD_ID=y.DATA_FIELD_ID");
        sql.append(" AND x.COMPANY_ID =(");
        sql.append(" SELECT a.COMPANY_ID FROM t_case a WHERE a.CASE_ID=:caseId");
        sql.append(" );");

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("caseId", caseId);

        return namedParameterJdbcTemplate.query(sql.toString(), params, new
                RowMapper<CaseVariableField>() {
                    public CaseVariableField mapRow(ResultSet resultSet, int i) throws SQLException {
                        CaseVariableField entity = new CaseVariableField();
                        entity.setFieldKey(resultSet.getString("FIELD_KEY"));
                        entity.setFieldName(resultSet.getString("ALIAS"));
                        entity.setIsHide(resultSet.getInt("IS_HIDE"));
                        entity.setPullDown(resultSet.getInt("IS_PULLDOWN"));
                        entity.setVariableFieldId(resultSet.getInt("VARIABLE_FIELD_ID"));
                        entity.setTableField(resultSet.getString("TABLE_FIELD"));
                        return entity;
                    }
                });

    }

    /**
     * 依据ID，获取案件
     *
     * @param caseId
     * @return
     */
    public TCase findById(Integer caseId) {
        String hql = " from TCase where recycleStatus =0 and caseId=? ";
        List<TCase> list = this.find(hql, caseId);
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        } else {
            return new TCase();
        }
    }


    /**
     * 分页查询
     *
     * @param queryParam 查询参数
     * @return
     */
    public Page findPage(CaseParam queryParam) {
        StringBuilder sql = new StringBuilder(" from t_case t left join t_user u on t" +
                ".creater_id=u.user_id left join t_user v on t.inspector_id=v.user_id" +
                " left join t_region w on t.region_id=w.region_id ");

//        if (StringUtils.isNotBlank(queryParam.getXcyName()) || StringUtils.isNotBlank
//                (queryParam.getCreatorName())) {//关联用户表
//            sql.append(" ,t_user u ");
//        }

        sql.append(" where 1=1 ");

//        if (StringUtils.isNotBlank(queryParam.getXcyName()) || StringUtils.isNotBlank
//                (queryParam.getCreatorName())) {//添加关联用户表条件
//            sql.append(" and t.creater_id = u.user_id ");
//        }

        Map<String, Object> params = new HashMap<String, Object>();

        /**
         * 新增查询条件
         */
        if(StringUtils.isNotBlank(queryParam.getRecycleStatus())){
            sql.append(" and t.recycle_status=").append(queryParam.getRecycleStatus());
        }else{
            sql.append(" and t.recycle_status=").append(NO.code());//正常状态
        }



        /**
         * 新增需要包含的状态
         */
        if (!queryParam.getIncludeStatus().isEmpty()) {
            sql.append(" and t.status in(");
            for (CaseStatus caseStatus : queryParam.getIncludeStatus()) {
                sql.append(caseStatus.code()).append(",");
            }
            sql.deleteCharAt(sql.length() - 1);
            sql.append(")");
        }


        /**
         * 移动端状态条件
         */
        String mobileStatus = queryParam.getMoblieStatus();//移动端状态码
        if (StringUtils.isNotBlank(mobileStatus)) {

            CaseStatus caseStatus = getStatusMobileCode(Integer.valueOf(mobileStatus)
            );

            if (caseStatus != null) {
                int status = caseStatus.code();//状态真实码


                //添加状态
                if (status != ALL.code()) {
                    sql.append(" and t.status=:status");
                    params.put("status", status);
                }

                String departmentId = queryParam.getDepartmentId();
                if (status == INSPECT.code()) {//巡查员条件
                    sql.append(" and t.department_id=:departmentId");
                    params.put("departmentId", departmentId);
                    sql.append(" and t.inspector_id=:inspectorId");
                    params.put("inspectorId", queryParam.getUserId());
                } else {
                    if (StringUtils.isNotBlank(departmentId)) {
                        sql.append(" and find_in_set(t.department_id,getChildDepartment" +
                                "(:departmentId))");
                        params.put("departmentId", departmentId);
                    }
                }
            }
        } else {
            if (StringUtils.isNotBlank(queryParam.getUserId())) {
                sql.append(" and t.creater_id=:creatorId");
                params.put("creatorId", queryParam.getUserId());
            }

            if (StringUtils.isNotBlank(queryParam.getDepartmentId())) {
                sql.append(" and FIND_IN_SET(t.DEPARTMENT_ID, getChildDepartment" +
                        "(:departmentId))");
                params.put("departmentId", queryParam.getDepartmentId());
            }
        }

        if (queryParam.getCompanyId() != null && queryParam.getCompanyId() != 0) {
            sql.append(" and t.company_id=:getCompanyId");
            params.put("getCompanyId", queryParam.getCompanyId());
        }

        if (StringUtils.isNotBlank(queryParam.getBeginDate())) {
            sql.append(" and date_format(t.CREATE_TIME,'%Y-%m-%d')>=:beginDate");
            params.put("beginDate", queryParam.getBeginDate());
        }
        if (StringUtils.isNotBlank(queryParam.getEndDate())) {
            sql.append(" and date_format(t.CREATE_TIME,'%Y-%m-%d')<=:endDate");
            params.put("endDate", queryParam.getEndDate());
        }
        if (StringUtils.isNotBlank(queryParam.getCaseNum())) {
            sql.append(" and t.case_Num like :caseNum");
            params.put("caseNum", "%" + queryParam.getCaseNum() + "%");
        }
        if (queryParam.getCaseStatus() != null && queryParam.getCaseStatus() != 0) {
            sql.append(" and t.status=:caseStatus");
            params.put("caseStatus", queryParam.getCaseStatus());
        }
        if (StringUtils.isNotBlank(queryParam.getParties())) {
            sql.append(" and t.parties like :parties");
            params.put("parties", "%" + queryParam.getParties() + "%");
        }


        //创建者名称
        if (StringUtils.isNotBlank
                (queryParam.getCreatorName())) {
            sql.append(" and u.name like :creatorName");
            params.put("creatorName", "%" + (queryParam.getCreatorName()) + "%");
        }

        //巡查者名称
        if (StringUtils.isNotBlank(queryParam.getXcyName())) {
            sql.append(" and v.name like :xcyName");
            params.put("xcyName", "%" + queryParam.getXcyName() + "%");
        }

        /**
         * 区域
         */
        if (queryParam.getRegionId() != null && queryParam.getRegionId() != 0) {
            sql.append(" and FIND_IN_SET(t.REGION_ID, getChildRegion(:regionId))");
            params.put("regionId", queryParam.getRegionId());
        }


        /**
         * 是否上报
         */
        String isUpload = queryParam.getIsUpload();
        if (StringUtils.isNotBlank(isUpload)) {
            String[] keys = (StringUtils.split(isUpload, Constants.DATA_TYPE_MULTI_KEY_SPLIT));
            sql.append(" and (");
            for (int i = 0; i < keys.length; i++) {
                String key = keys[i];
                if (StringUtils.equals(key, "0")) {
                    sql.append(" t.is_upload is null ");
                } else {
                    sql.append(" t.is_upload = :isUpload" + i);
                    params.put("isUpload" + i, key);
                }
                if (i != keys.length - 1) {
                    sql.append(" or");
                }
            }
            sql.append(")");

        }

        /**
         * 创建时间开始、结束
         */
        if (StringUtils.isNotBlank(queryParam.getCreateTimeBegin())) {
            sql.append(" and date_format(t.create_time,'" + Constants.MYSQL_DATE_FORMAT + "')" +
                    ">=").append(":getCreateTimeBegin");
            params.put("getCreateTimeBegin", queryParam.getCreateTimeBegin());
        }
        if (StringUtils.isNotBlank(queryParam.getCreateTimeEnd())) {
            sql.append(" and date_format(t.create_time,'" + Constants.MYSQL_DATE_FORMAT + "')" +
                    "<=").append(":getCreateTimeEnd");
            params.put("getCreateTimeEnd", queryParam.getCreateTimeEnd());
        }

        if (BooleanUtils.toBoolean(queryParam.getIsDraft())) {//草稿箱
            sql.append(" and t.status=:caseStatus");
            params.put("caseStatus", CaseStatus.DRAFT.code());
        }else{//其他情况，查询不到草稿箱数据
            sql.append(" and t.status!=:caseStatus");
            params.put("caseStatus", CaseStatus.DRAFT.code());
        }


        //模块条件
        if(queryParam.getModuleType()!=null){
            switch (queryParam.getModuleType()){
                case MY_CASE://只能查询到自己创建的案件
                    sql.append(" and t.creater_id=:getUserId");
                    params.put("getUserId", queryParam.getUserId());
                    break;
            }
        }

        //查询总数SQL
        String countSql = "select count(1) " + sql.toString();
        int count = namedParameterJdbcTemplate.queryForInt(countSql, params);

        sql.append(" order by t.create_time desc");

        //分页
        int start = queryParam.getNumPerPage() * (queryParam
                .getPageNum() - 1);//起始位置
        sql.append(" limit ").append(start).append(",").append
                (queryParam.getNumPerPage());
        String selectSql = " select t.*,v.name inspectorName,u.name creatorName,w.name regionName,w.region_id  regionId  " + sql.toString();

        //查询
        List<TCase> datas = namedParameterJdbcTemplate.query(selectSql, params, new
                RowMapper<TCase>() {
                    public TCase mapRow(ResultSet resultSet, int i) throws SQLException {
                        TCase entity = new TCase();
                        entity.setCaseId(resultSet.getInt("CASE_ID"));
                        entity.setCaseNum(resultSet.getString("CASE_NUM"));
                        entity.setCreateTime(resultSet.getTimestamp("CREATE_TIME"));
                        entity.setCreaterId(resultSet.getInt("CREATER_ID"));
                        entity.setParties(resultSet.getString("PARTIES"));
                        entity.setIdCardNum(resultSet.getString("ID_CARD_NUM"));
                        entity.setRegionId(resultSet.getInt("REGION_ID"));
                        entity.setIllegalArea(resultSet.getString("ILLEGAL_AREA"));
                        entity.setEastTo(resultSet.getString("EAST_TO"));
                        entity.setWestTo(resultSet.getString("WEST_TO"));
                        entity.setSouthTo(resultSet.getString("SOUTH_TO"));
                        entity.setNorthTo(resultSet.getString("NORTH_TO"));
                        entity.setIllegalAreaSpace(resultSet.getFloat("ILLEGAL_AREA_SPACE"));
                        entity.setFloorSpace(resultSet.getFloat("FLOOR_SPACE"));
                        entity.setBuildingSpace(resultSet.getFloat("BUILDING_SPACE"));
                        entity.setIllegalType(resultSet.getInt("ILLEGAL_TYPE"));
                        entity.setLandUsage(resultSet.getInt("LAND_USAGE"));
                        entity.setCurrentStatus(resultSet.getInt("CURRENT_STATUS"));
                        entity.setSurveyResult(resultSet.getInt("SURVEY_RESULT"));
                        entity.setCaseSource(resultSet.getInt("CASE_SOURCE"));
                        entity.setInspectorId(resultSet.getInt("INSPECTOR_ID"));
                        entity.setRemark(resultSet.getString("REMARK"));
                        entity.setLng(resultSet.getBigDecimal("lng"));
                        entity.setLat(resultSet.getBigDecimal("lat"));
                        entity.setLocateType(resultSet.getInt("LOCATE_TYPE"));
                        entity.setRecycleStatus(resultSet.getInt("RECYCLE_STATUS"));
                        entity.setStatus(resultSet.getInt("STATUS"));
                        entity.setModifyTime(resultSet.getDate("MODIFY_TIME"));
                        entity.setDelTime(resultSet.getDate("DEL_TIME"));
                        entity.setCompanyId(resultSet.getInt("COMPANY_ID"));
                        entity.setDepartmentId(resultSet.getInt("DEPARTMENT_ID"));
                        entity.setIsUpload(resultSet.getInt("IS_UPLOAD"));
                        entity.setIllegalUse(resultSet.getInt("ILLEGAL_USE"));
                        entity.setInspectorName(resultSet.getString("inspectorName"));
                        entity.setRegionName(resultSet.getString("regionName"));
                        entity.setCreatorName(resultSet.getString("creatorName"));
                        entity.setRegionName(resultSet.getString("regionName"));
                        entity.setRegionId(resultSet.getInt("regionId"));
                        return entity;
                    }
                });

        return new Page(queryParam
                .getNumPerPage(), start, datas, count);
    }
}
