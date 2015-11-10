package net.deniro.land.module.icase.dao;

import net.deniro.land.common.dao.BaseDao;
import net.deniro.land.common.dao.Page;
import net.deniro.land.module.icase.entity.CaseQueryParam;
import net.deniro.land.module.icase.entity.TCase;
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
     * 分页查询
     *
     * @param queryParam 查询参数
     * @return
     */
    public Page findPage(CaseQueryParam queryParam) {
        StringBuilder sql = new StringBuilder(" select distinct t.* from t_case t where " +
                "1=1 ");

        Map<String, Object> params = new HashMap<String, Object>();

        /**
         * 新增查询条件
         */
        sql.append(" and t.recycle_status=").append(NO.code());//正常状态

        if (StringUtils.isNotBlank(queryParam.getUserId())) {
            sql.append(" and t.creater_id=:creatorId");
            params.put("creatorId", queryParam.getUserId());
        }


        /**
         * 移动端状态条件
         */
        String mobileStatus = queryParam.getMoblieStatus();//移动端状态码
        if (StringUtils.isNotBlank(mobileStatus)) {

            int status = getStatusMobileCode(Integer.valueOf(mobileStatus)
            ).code();//状态真实码

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


        sql.append(" order by t.create_time desc");

        //分页
        int start = queryParam.getNumPerPage() * (queryParam
                .getPageNum() - 1);//起始位置
        sql.append(" limit ").append(start).append(",").append
                (queryParam.getPageNum());

        //查询
        List<TCase> datas = namedParameterJdbcTemplate.query(sql.toString(), params, new
                RowMapper<TCase>() {
                    public TCase mapRow(ResultSet resultSet, int i) throws SQLException {
                        TCase entity = new TCase();
                        entity.setCaseId(resultSet.getInt("CASE_ID"));
                        entity.setCaseNum(resultSet.getString("CASE_NUM"));
                        entity.setCreateTime(resultSet.getDate("CREATE_TIME"));
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
                        return entity;
                    }
                });

        return new Page(queryParam
                .getNumPerPage(), start, datas, datas.size());
    }
}
