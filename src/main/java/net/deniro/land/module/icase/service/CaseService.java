package net.deniro.land.module.icase.service;

import net.deniro.land.api.entity.AssignParam;
import net.deniro.land.api.entity.Images;
import net.deniro.land.api.entity.InspectParam;
import net.deniro.land.api.entity.OverAuditParam;
import net.deniro.land.common.dao.Page;
import net.deniro.land.common.service.Constants;
import net.deniro.land.common.utils.JsonUtils;
import net.deniro.land.common.utils.PropertiesReader;
import net.deniro.land.common.utils.TimeUtils;
import net.deniro.land.common.utils.ftp.FtpUtils;
import net.deniro.land.module.icase.dao.*;
import net.deniro.land.module.icase.entity.*;
import net.deniro.land.module.icase.entity.TAttachmentRelation.RelationType;
import net.deniro.land.module.icase.entity.TCase.InstructionState;
import net.deniro.land.module.icase.entity.TCaseAudit.AuditResult;
import net.deniro.land.module.message.entity.TMsgResult;
import net.deniro.land.module.message.service.MsgResultService;
import net.deniro.land.module.system.dao.RegionDao;
import net.deniro.land.module.system.dao.UserDao;
import net.deniro.land.module.system.entity.TRegion;
import net.deniro.land.module.system.entity.User;
import org.apache.commons.collections.map.MultiKeyMap;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.Column;
import java.io.File;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static net.deniro.land.api.entity.OverAuditParam.CheckType;
import static net.deniro.land.module.icase.entity.TCase.CaseStatus.*;
import static net.deniro.land.module.icase.entity.TCase.IsReport.TRUE;
import static net.deniro.land.module.icase.entity.TCase.RecycleStatus.NO;
import static net.deniro.land.module.icase.entity.TCaseAudit.Type.OVER;
import static net.deniro.land.module.icase.entity.TCaseAudit.Type.START;
import static net.deniro.land.module.icase.entity.TCaseFlowRecord.OperationType;
import static net.deniro.land.module.icase.entity.TCaseFlowRecord.OperationType.*;
import static net.deniro.land.module.icase.entity.TInstruction.InstructionStatus.NORMAL;

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

    @Autowired
    private AuditDao auditDao;

    @Autowired
    private FlowRecordDao flowRecordDao;

    @Autowired
    private AttachmentDao attachmentDao;

    @Autowired
    private AttachmentRelationDao attachmentRelationDao;

    @Autowired
    private InstructionDao instructionDao;

    @Autowired
    private RegionDao regionDao;

    @Autowired
    private FtpUtils ftpUtils;

    @Autowired
    private DataTypeService dataTypeService;

    @Autowired
    private MsgResultService msgResultService;

    @Resource(name = "messageTempalte")
    private Map<String, String> messageTempalte;

    /**
     * 巡查结果的数据映射关系
     */
    public static List<TDataType> SURVEY_RESULT_DATA_TYPES = null;
    /**
     * 巡查结果的键值对
     */
    public static Map<Integer, String> SURVEY_RESULT_DATA_TYPES_MAP = new HashMap<Integer, String>();


    /**
     * 上报案件
     *
     * @param caseId 案件ID
     * @return
     */
    public boolean report(Integer caseId) {
        try {
            TCase tCase = caseDao.get(caseId);

            //如果案件已上报过，则无须重复上报
            if (tCase.getIsUpload() != null && tCase.getIsUpload() == TRUE.code()) {
                return false;
            }

            tCase.setIsUpload(TRUE.code());
            caseDao.update(tCase);
            return true;
        } catch (Exception e) {
            logger.error("上报案件", e);
            return false;
        }
    }

    /**
     * 恢复案件
     *
     * @param caseId 案件ID
     */
    public boolean recovery(Integer caseId) {
        try {
            TCase tCase = caseDao.get(caseId);
            tCase.setRecycleStatus(TCase.RecycleStatus.NO.code());
            caseDao.update(tCase);
            return true;
        } catch (Exception e) {
            logger.error("恢复案件", e);
            return false;
        }
    }

    /**
     * 删除案件（可恢复）
     *
     * @param caseId 案件ID
     */
    public boolean fakeDelete(Integer caseId) {
        try {
            TCase tCase = caseDao.get(caseId);
            tCase.setDelTime(new Date());
            tCase.setRecycleStatus(TCase.RecycleStatus.YES.code());
            caseDao.update(tCase);
            return true;
        } catch (Exception e) {
            logger.error("删除案件（可恢复）", e);
            return false;
        }
    }

    /**
     * 删除案件
     *
     * @param caseId 案件ID
     */
    public boolean delete(Integer caseId) {
        try {
            TCase tCase = caseDao.get(caseId);
            if (tCase != null) {
                caseDao.remove(tCase);
            }
            return true;
        } catch (Exception e) {
            logger.error("删除案件", e);
            return false;
        }
    }

    /**
     * 删除所有相关附件记录
     *
     * @param filePath 文件路径
     * @return
     */
    public boolean deleteAllByFilePath(String filePath) {
        try {
            attachmentDao.deleteAllByFilePath(filePath);
            return true;
        } catch (Exception e) {
            logger.error("删除所有相关附件记录", e);
            return false;
        }
    }

    /**
     * 删除所有附件相关记录
     *
     * @param relationId   关联的ID
     * @param relationType 关联类型
     * @return 返回受影响的条数
     */
    public boolean deleteAllAttachments(Integer relationId, RelationType relationType) {
        try {
            attachmentDao.deleteAll(relationId, relationType);
            return true;
        } catch (Exception e) {
            logger.error("删除所有附件相关记录", e);
            return false;
        }
    }

    /**
     * 获取附件
     *
     * @param caseId       案件ID
     * @param relationType 关联类型
     * @return
     */
    public List<TAttachment> findAttachments(Integer caseId, RelationType relationType) {
        try {
            return attachmentDao.findByAuditIdAndType(caseId,
                    relationType);
        } catch (Exception e) {
            logger.error("获取附件", e);
            return new ArrayList<TAttachment>();
        }
    }

    /**
     * 修改案件
     *
     * @param caseParam 案件参数
     * @return
     */
    public boolean modifyCase(CaseParam caseParam) {

        try {
            TCase tCase = caseDao.get(caseParam.getCaseId());
            if (tCase == null) {
                return false;
            }

            if (caseParam.getFloorSpace() != null) {
                tCase.setFloorSpace(caseParam.getFloorSpace());
            }
            if (caseParam.getBuildingSpace() != null) {
                tCase.setBuildingSpace(caseParam.getBuildingSpace());
            }
            if (caseParam.getIdCardNum() != null) {
                tCase.setIdCardNum(caseParam.getIdCardNum());
            }
            if (caseParam.getPunisher() != null) {
                tCase.setParties(caseParam.getPunisher());
            }
            if (caseParam.getPlaceId() != null) {
                tCase.setRegionId(caseParam.getPlaceId());
            }

            //东南西北至
            if (caseParam.getEast() != null) {
                tCase.setEastTo(caseParam.getEast());
            } else if (caseParam.getEastTo() != null) {
                tCase.setEastTo(caseParam.getEastTo());
            }
            if (caseParam.getWest() != null) {
                tCase.setWestTo(caseParam.getWest());
            } else if (caseParam.getWestTo() != null) {
                tCase.setWestTo(caseParam.getWestTo());
            }
            if (caseParam.getNorth() != null) {
                tCase.setNorthTo(caseParam.getNorth());
            } else if (caseParam.getNorthTo() != null) {
                tCase.setNorthTo(caseParam.getNorthTo());
            }
            if (caseParam.getSouth() != null) {
                tCase.setSouthTo(caseParam.getSouth());
            } else if (caseParam.getSouthTo() != null) {
                tCase.setSouthTo(caseParam.getSouthTo());
            }

            //违法地点
            if (caseParam.getIllegalAddr() != null) {
                tCase.setIllegalArea(caseParam.getIllegalAddr());
            } else if (caseParam.getIllegalArea() != null) {
                tCase.setIllegalArea(caseParam.getIllegalArea());
            }


            if (caseParam.getIllegalType() != null) {
                tCase.setIllegalType(caseParam.getIllegalType());
            }
            if (caseParam.getLandUsage() != null) {
                tCase.setLandUsage(caseParam.getLandUsage());
            }
            if (caseParam.getCurrentStatus() != null) {
                tCase.setCurrentStatus(caseParam.getCurrentStatus());
            }
            if (caseParam.getCaseSource() != null) {
                tCase.setCaseSource(caseParam.getCaseSource());
            }

            //违法面积
            if (caseParam.getSpace() != null) {
                tCase.setIllegalAreaSpace(caseParam.getSpace());
            } else if (caseParam.getIllegalAreaSpace() != null) {
                tCase.setIllegalAreaSpace(caseParam.getIllegalAreaSpace());
            }

            //备注
            if (caseParam.getCaseComment() != null) {
                tCase.setRemark(caseParam.getCaseComment());
            } else if (caseParam.getRemark() != null) {
                tCase.setRemark(caseParam.getRemark());
            }

            //巡查结果
            if (caseParam.getInspectResult() != null) {
                tCase.setSurveyResult(caseParam.getInspectResult());
            } else if (caseParam.getSurveyResult() != null) {
                tCase.setSurveyResult(caseParam.getSurveyResult());
            }

            //案件来源
            if (caseParam.getCaseSource() != null) {
                tCase.setCaseSource(caseParam.getCaseSource());
            }


            if (caseParam.getGpsFlag() != null) {
                tCase.setLocateType(caseParam.getGpsFlag());
            }

            //经纬度
            if (caseParam.getGpsX() != null) {
                tCase.setLng(caseParam.getGpsX());
            } else if (StringUtils.isNotBlank(caseParam.getCoordinateLongitude())) {
                tCase.setLng(new BigDecimal(caseParam.getCoordinateLongitude()));
            }

            if (caseParam.getGpsY() != null) {
                tCase.setLat(caseParam.getGpsY());
            } else if (StringUtils.isNotBlank(caseParam.getCoordinateLatitude())) {
                tCase.setLat(new BigDecimal(caseParam.getCoordinateLatitude()));
            }


            if (caseParam.getXcyId() != null) {
                tCase.setInspectorId(caseParam.getXcyId());
            }

            //当事人
            if (StringUtils.isNotBlank(caseParam.getParties())) {
                tCase.setParties(caseParam.getParties());
            }

            //所在区域
            if (caseParam.getRegionId() != null) {
                tCase.setRegionId(caseParam.getRegionId());
            }

            //违建用途
            if (caseParam.getIllegalUse() != null) {
                tCase.setIllegalUse(caseParam.getIllegalUse());
            }

            //案件状态
            if (StringUtils.isNotBlank(caseParam.getStatus())) {
                tCase.setStatus(NumberUtils.toInt(caseParam.getStatus()));
            }

            tCase.setModifyTime(new Date());

            caseDao.update(tCase);

            addAttachments(caseParam.getAttachmentList(), tCase.getCaseId(), RelationType.CASE);
            uploadFTP(caseParam);

            return true;
        } catch (Exception e) {
            logger.error("修改案件", e);
            return false;
        }


    }

    /**
     * 新增案件
     *
     * @param caseParam 案件参数
     * @return
     */
    public boolean addCase(CaseParam caseParam) {

        try {
            TCase tCase = new TCase();
            tCase.setIdCardNum(caseParam.getIdCardNum());
            tCase.setCurrentStatus(caseParam.getCurrentStatus());
            tCase.setCreaterId(NumberUtils.toInt(caseParam.getUserId()));

            //处理当事人
            if (StringUtils.isNotBlank(caseParam.getPunisher())) {
                tCase.setParties(caseParam.getPunisher());
                caseParam.setParties(caseParam.getPunisher());
            } else {
                tCase.setParties(caseParam.getParties());
            }


            tCase.setRegionId(caseParam.getPlaceId());
            tCase.setEastTo(caseParam.getEastTo());
            tCase.setWestTo(caseParam.getWestTo());
            tCase.setNorthTo(caseParam.getNorthTo());
            tCase.setSouthTo(caseParam.getSouthTo());

            //处理违法面积
            if (caseParam.getSpace() != null && caseParam.getSpace() != 0) {
                tCase.setIllegalAreaSpace(caseParam.getSpace());
                caseParam.setIllegalAreaSpace(caseParam.getSpace());
            } else {
                tCase.setIllegalAreaSpace(caseParam.getIllegalAreaSpace());
            }

            //处理违法地址
            if (StringUtils.isNotBlank(caseParam.getIllegalAddr())) {
                tCase.setIllegalArea(caseParam.getIllegalAddr());
                caseParam.setIllegalArea(caseParam.getIllegalAddr());
            } else {
                tCase.setIllegalArea(caseParam.getIllegalArea());
            }


            tCase.setIllegalType(caseParam.getIllegalType());
            tCase.setIllegalUse(caseParam.getIllegalUse());
            tCase.setLandUsage(caseParam.getLandUsage());
            tCase.setCaseSource(caseParam.getCaseSource());
            tCase.setBuildingSpace(caseParam.getBuildingSpace());
            tCase.setFloorSpace(caseParam.getFloorSpace());

            //处理备注
            if (StringUtils.isNotBlank(caseParam.getCaseComment())) {
                tCase.setRemark(caseParam.getCaseComment());
            } else {
                tCase.setRemark(caseParam.getRemark());
            }

            //处理巡查结果
            if (caseParam.getInspectResult() != null) {
                tCase.setSurveyResult(caseParam.getInspectResult());
            } else {
                tCase.setSurveyResult(caseParam.getSurveyResult());
            }


            tCase.setLocateType(caseParam.getGpsFlag());

            //处理经度和纬度
            if (caseParam.getGpsX() != null) {
                tCase.setLng(caseParam.getGpsX());
            } else if (caseParam.getLng() != null) {
                tCase.setLng(caseParam.getLng());
            } else if (StringUtils.isNotBlank(caseParam.getCoordinateLongitude())) {
                tCase.setLng(new BigDecimal(caseParam.getCoordinateLongitude()));
            }
            if (caseParam.getGpsY() != null) {
                tCase.setLat(caseParam.getGpsY());
            } else if (caseParam.getLat() != null) {
                tCase.setLat(caseParam.getLat());
            } else if (StringUtils.isNotBlank(caseParam.getCoordinateLongitude())) {
                tCase.setLat(new BigDecimal(caseParam.getCoordinateLatitude()));
            }

            if (BooleanUtils.toBoolean(caseParam.getIsDraft())) {//草稿
                tCase.setStatus(DRAFT.code());
            } else {//立案
                tCase.setStatus(PREPARE.code());
            }

            tCase.setRecycleStatus(NO.code());
            tCase.setCreateTime(new Date());

            /**
             * 设置用户相关信息，并获取案件数
             */
            int caseCount = 0;
            if (StringUtils.isNotBlank(caseParam.getUserId())) {
                //设置用户相关信息
                User user = userDao.get(NumberUtils.toInt(caseParam.getUserId()));
                tCase.setCompanyId(user.getCompanyId());
                tCase.setDepartmentId(user.getDepartmentId());

                //获取案件数
                CaseParam countParam = new CaseParam();
                countParam.setCreateBeginDate(TimeUtils.getCurrentDate());
                countParam.setDepartmentId(String.valueOf(user.getDepartmentId()));
                countParam.setRecycleStatus(String.valueOf(TCase.RecycleStatus.NO.code()));
                caseCount = caseDao.count(countParam);
            }


            /**
             * 生成并设置案件号
             */
            StringBuilder caseNum = new StringBuilder("");

            //添加地区码
            Integer regionId = caseParam.getRegionId();
            if (caseParam.getPlaceId() != null) {
                regionId = caseParam.getPlaceId();
            }
            if (regionId != null) {
                TRegion region = regionDao.get(regionId);
                caseNum.append(region.getRegionCode() != null ? region.getRegionCode() : TRegion.DEFAULT_REGION_CODE);
                tCase.setRegionId(regionId);
            }

            //添加日期
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMdd");
            Date date = new Date();
            caseNum.append(simpleDateFormat.format(date));
            //根据当日的案件数，生成编号
            String code = String.valueOf(caseCount);
            if (code.length() > 3) {//只取前三
                code = code.substring(0, 3);
            }
            caseNum.append(code);
            tCase.setCaseNum(caseNum.toString());

            caseDao.save(tCase);

            /**
             * 新增附件
             */
            if (StringUtils.isNotBlank(caseParam.getImages())) {
                addAttachments(caseParam.getImages(), tCase.getCaseId(), RelationType
                        .CASE);
            } else {
                addAttachments(caseParam.getAttachmentList(), tCase.getCaseId(), RelationType.CASE);
                uploadFTP(caseParam);
            }


            /**
             * 新增流程日志
             */
            addFlowLog(tCase.getCaseId(), NumberUtils.toInt(caseParam.getUserId()),
                    OperationType
                            .REGISTER,
                    "建立新案件", caseParam.getRemark());

            /**
             * 新增消息
             */
            caseParam.setCaseId(tCase.getCaseId());
            msgResultService.add(generateMsg(caseParam, "立案通知"));

            return true;
        } catch (Exception e) {
            logger.error("新增案件", e);
            return false;
        }


    }

    /**
     * 生成消息
     *
     * @param caseParam
     * @param type      消息模板类型
     * @return
     */
    private TMsgResult generateMsg(CaseParam caseParam, String type) {
        TMsgResult entity = new TMsgResult();
        entity.setCaseId(caseParam.getCaseId());
        entity.setUserId(NumberUtils.toInt(caseParam.getUserId()));

        entity.setCreateTime(new Date());
        entity.setIsDel(0);
        entity.setIsRead(0);

        String title = type;
        entity.setRuleName(title);
        String content = messageTempalte.get(title);
        content = StringUtils.replace(content, "%{地址}", caseParam.getIllegalArea());
        content = StringUtils.replace(content, "%{违法当事人}", caseParam.getParties());
        content = StringUtils.replace(content, "%{违法面积}", String.valueOf(caseParam
                .getIllegalAreaSpace
                        ()));
        entity.setMsgContent(content);
        return entity;
    }

    /**
     * 上传附件至FTP服务器
     *
     * @param caseParam
     */
    private void uploadFTP(CaseParam caseParam) {
        if (caseParam == null || caseParam.getAttachmentList() == null || caseParam
                .getAttachmentList().isEmpty()) {
            return;
        }

        List<File> files = new ArrayList<File>();
        for (Images image : caseParam.getAttachmentList()) {
            if (image.getFileName() == null) {//已经在FTP服务器上，则无需上传
                continue;
            }
            File file = new File(image.getFileActualPath());
            files.add(file);
        }
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new FTPUploadService(caseParam.getUserId(), files, ftpUtils));
    }

    /**
     * 新增案件批示
     *
     * @param userId  用户ID
     * @param caseId  案件ID
     * @param content 批示内容
     * @return
     */
    public boolean addInstruction(Integer userId, Integer caseId, String content) {

        try {
            /**
             * 保存案件批示
             */
            TInstruction instruction = new TInstruction();
            instruction.setUserId(userId);
            instruction.setCaseId(caseId);
            instruction.setContent(content);
            instruction.setInstructionDate(new Date());
            instruction.setStatus(NORMAL.code());

            //设置用户名称
            User user = userDao.get(userId);
            if (user != null) {
                instruction.setUserName(user.getName());
            }
            instructionDao.save(instruction);

            //todo 新增批示通知短信

            return true;
        } catch (Exception e) {
            logger.error("新增案件批示", e);
            return false;
        }


    }

    /**
     * 删除案件批示
     *
     * @param instructionId 案件批示ID
     * @return
     */
    public boolean delInstruction(Integer instructionId) {
        try {
            int count = instructionDao.del(instructionId);
            return count > 0;
        } catch (Exception e) {
            logger.error("删除案件批示", e);
            return false;
        }
    }

    /**
     * 获取案件批示状态
     *
     * @param caseId 案件ID
     * @param userId 用户ID
     */
    public InstructionState getInstructionState(Integer caseId, Integer userId) {
        //默认为【未批示】状态
        InstructionState instructionState = InstructionState.NO_INSTRUCTION;

        /**
         * 查询我批示的记录数
         */
        InstructionQueryParam param = new InstructionQueryParam();
        param.setCaseId(String.valueOf(caseId));
        param.setUserId(String.valueOf(userId));
        int meCount = instructionDao.count(param);

        /**
         * 查询其他人批示的记录数
         */
        InstructionQueryParam param2 = new InstructionQueryParam();
        param2.setCaseId(String.valueOf(caseId));
        param2.setStatus(String.valueOf(NORMAL.code()));
        int otherCount = instructionDao.count(param2);

        if (meCount > 0) {
            instructionState = InstructionState.ME_HAS_INSTRUCTION;
        } else if (otherCount > 0) {
            instructionState = InstructionState.OTHER_HAS_INSTRUCTION;
        }

        return instructionState;
    }

    /**
     * 分页查询案件批示
     *
     * @param queryParam 案件批示查询参数
     * @return
     */
    public Page findPageInstructions(InstructionQueryParam queryParam) {
        try {
            if (queryParam != null) {
                queryParam.setStatus(String.valueOf(NORMAL.code()));
            }

            return instructionDao.findPage(queryParam);
        } catch (Exception e) {
            logger.error("分页查询案件批示", e);
            return new Page();
        }
    }

    /**
     * 分页查询 案件批示
     *
     * @param queryParam 查询参数
     * @return
     */
    public Page findPageForInstruction(CaseParam queryParam) {
        try {

            //设置部门ID
            queryParam.setDepartmentId(findDepartmentByUserId(queryParam.getUserId()));

            /**
             * 新增状态
             */
            List<TCase.CaseStatus> includeStatus = new ArrayList<TCase.CaseStatus>();
            includeStatus.add(PREPARE);
            includeStatus.add(TCase.CaseStatus.INSPECT);
            includeStatus.add(TCase.CaseStatus.APPLY);
            includeStatus.add(TCase.CaseStatus.FIRST_OVER);
            queryParam.setIncludeStatus(includeStatus);

            Page page = caseDao.findPage(queryParam);
            List<TCase> cases = page.getData();

            //设置批示状态，这里非常耗性能，待优化
            for (TCase aCase : cases) {
                aCase.setInstructionState(String.valueOf(getInstructionState(aCase.getCaseId(),
                        NumberUtils.toInt(queryParam.getUserId())).code()));
            }
            return page;
        } catch (Exception e) {
            logger.error(" 分页查询 案件批示", e);
            return new Page();

        }
    }

    /**
     * 通过用户ID，获取部门ID
     *
     * @param userId 用户ID
     */
    private String findDepartmentByUserId(String userId) {
        User user = userDao.get(NumberUtils.toInt(userId));
        if (user != null) {
            return String.valueOf(user.getDepartmentId());
        } else {
            return "";
        }
    }

    /**
     * 指派案件
     *
     * @param assignParam 流转参数
     * @return
     */
    public boolean assign(AssignParam assignParam) {
        try {
            Date currentDate = new Date();

            /**
             * 更新案件
             */
            TCase tCase = caseDao.findById(assignParam.getCaseId());
            tCase.setInspectorId(assignParam.getXcyId());
            tCase.setModifyTime(currentDate);
            if (assignParam.getType() == AssignParam.AssignType.INSPECTOR.code()) {//设定巡查员的部门ID
                tCase.setDepartmentId(userDao.get(assignParam.getXcyId()).getDepartmentId());
            }
            caseDao.update(tCase);

            /**
             * 新增流程日志
             */
            addFlowLog(assignParam.getCaseId(), assignParam.getUserId(), assignParam.getXcyId(),
                    assignParam.getXcyId(),
                    OperationType
                            .CANCELED,
                    "案件指派", "");

            return true;
        } catch (Exception e) {
            logger.error(" 流转案件（指派案件）", e);
            return false;
        }


    }

    /**
     * 结案审核（一次或者二次）
     *
     * @param userId        账号ID
     * @param caseId        案件ID
     * @param operationType 操作类型
     * @param opinion       审核意见
     * @return
     */
    public boolean overAudit(Integer userId, Integer caseId, OperationType operationType,
                             String opinion) {

        try {
            Date currentDate = new Date();

            /**
             * 判断审核类型
             */
            TCase tCase = caseDao.findById(caseId);
            AuditResult auditResult = AuditResult.NO_PASS;
            Integer auditNo = 1;//审核类型；1：一次审核；2：二次审核
            String description = "";//流程描述
            switch (operationType) {
                case FIRST_CLOSE_APPLY:
                    auditResult = AuditResult.PASS;
                    tCase.setStatus(FIRST_OVER.code());
                    description = "一次结案审核通过";
                    break;
                case FIRST_CLOSE_APPLY_REJECT:
                    tCase.setStatus(INSPECT.code());
                    tCase.setIsUpload(TCase.IsReport.FALSE.code());
                    description = "一次结案审核被驳回";
                    break;
                case SECOND_CLOSE_APPLY:
                    auditResult = AuditResult.PASS;
                    auditNo = 2;
                    tCase.setStatus(SECOND_CLOSE_APPLY.code());
                    description = "二次结案审核通过";
                    break;
                case SECOND_CLOSE_APPLY_REJECT:
                    auditNo = 2;
                    tCase.setStatus(INSPECT.code());
                    description = "二次结案审核被驳回";
                    break;
                default:
                    return false;
            }

            /**
             * 更新案件状态
             */
            caseDao.update(tCase);

            /**
             * 新增案件审核
             */
            TCaseAudit caseAudit = new TCaseAudit();
            caseAudit.setAuditerId(userId);
            caseAudit.setAuditResult(auditResult.code());
            caseAudit.setCaseId(caseId);
            caseAudit.setRemark(opinion);
            caseAudit.setAuditType(OVER.code());
            caseAudit.setAuditNo(auditNo);
            caseAudit.setAuditTime(currentDate);
            auditDao.save(caseAudit);

            /**
             * 新增流程日志
             */
            addFlowLog(caseId, userId, operationType,
                    description, opinion);

            //todo 如果是二次结案，则删除短信池中的案件

            return true;
        } catch (Exception e) {
            logger.error("结案审核", e);
            return false;
        }

    }

    /**
     * 结案审核
     *
     * @param overAuditParam
     * @return
     */
    public boolean overAudit(OverAuditParam overAuditParam) {

        try {
            Date currentDate = new Date();

            /**
             * 新增案件审核
             */
            TCaseAudit caseAudit = new TCaseAudit();
            caseAudit.setAuditerId(overAuditParam.getUserId());
            caseAudit.setAuditResult(overAuditParam.getCaseStatus());
            caseAudit.setCaseId(overAuditParam.getCaseId());
            caseAudit.setRemark(overAuditParam.getRemark());
            caseAudit.setAuditType(OVER.code());
            caseAudit.setAuditNo(overAuditParam.getCheckType());
            caseAudit.setAuditTime(currentDate);
            auditDao.save(caseAudit);

            /**
             * 判断审核类型
             */
            CheckType checkType = null;
            if (overAuditParam.getCheckType() == CheckType.FIRST.code()) {
                checkType = CheckType.FIRST;
            } else if (overAuditParam.getCheckType() == CheckType.SECOND.code
                    ()) {
                checkType = CheckType.SECOND;
            } else {
                logger.error("审核类型未知：" + overAuditParam.getCheckType());
                return false;
            }

            /**
             * 更新案件状态
             */
            TCase tCase = caseDao.findById(overAuditParam.getCaseId());
            if (caseAudit.getAuditResult() == AuditResult.PASS.code()) {//审核通过
                switch (checkType) {
                    case FIRST://一级审核
                        tCase.setStatus(FIRST_OVER.code());
                        break;
                    case SECOND://二级审核
                        tCase.setStatus(SECOND_OVER.code());
                        break;
                }
            }
            caseDao.update(tCase);


            /**
             * 设置操作类型、流程描述
             */
            OperationType operationType = null;
            String description = "";//流程描述
            switch (checkType) {
                case FIRST://一级审核
                    operationType =
                            FIRST_CLOSE_APPLY;
                    description = "通过一次结案审核！";
                    break;
                case SECOND://二级审核
                    operationType = SECOND_CLOSE_APPLY;
                    description = "通过二次结案审核！";
                    break;
            }

            /**
             * 新增流程日志
             */
            addFlowLog(overAuditParam.getCaseId(), overAuditParam.getUserId(), operationType,
                    description, overAuditParam.getRemark());

            /**
             * 新增附件
             */
            addAttachments(overAuditParam.getImages(), caseAudit.getCaseAuditId(), RelationType.AUDIT);

            //todo 如果是二次结案，则删除短信池中的案件

            return true;
        } catch (Exception e) {
            logger.error("结案审核", e);
            return false;
        }

    }


    /**
     * 巡查案件
     *
     * @param inspectParam
     * @return 是否成功
     */
    public boolean inspect(InspectParam inspectParam) {

        try {
            Date currentDate = new Date();

            /**
             * 更新案件
             */
            TCase tCase = caseDao.findById(inspectParam.getCaseId());
            if (inspectParam.getCaseStatus() == InspectParam.CaseStatus.NO_CLOSE
                    .code()) {//未结案
                tCase.setStatus(INSPECT.code());
            } else {//申请结案
                tCase.setStatus(APPLY.code());
            }
            caseDao.update(tCase);

            /**
             * 创建巡查记录
             */
            TInspect inspect = new TInspect();
            inspect.setCaseId(inspectParam.getCaseId());
            inspect.setInspectorId(inspectParam.getUserId());
            inspect.setRemark(inspectParam.getRemark());
            inspect.setCreateTime(currentDate);
            inspect.setInspectResult(String.valueOf(inspectParam.getInspectResult()));
            //巡查序号【这种方式在并发状态下，会造成序号重复】
            Integer inspectNo = inspectDao.countByCaseId(inspectParam.getCaseId()) + 1;
            inspect.setInspectNo(inspectNo);
            inspectDao.save(inspect);


            /**
             * 新增附件
             */
            addAttachments(inspectParam.getImages(), inspect.getInspectId(), RelationType.INSPECT);

            return true;
        } catch (Exception e) {
            logger.error("巡查案件", e);
            return false;
        }

    }


    /**
     * 新建 立案审核记录
     *
     * @param userId      用户ID
     * @param caseId      案件ID
     * @param inspectorId 巡查员ID
     * @param auditResult 审核结果
     * @param remark      备注
     * @return 是否成功
     */
    public boolean audit(Integer userId, Integer caseId, Integer inspectorId, AuditResult
            auditResult, String
                                 remark) {
        if (caseId == null) {
            return false;
        }

        Date currentDate = new Date();

        try {
            /**
             * 新建审核记录
             */
            TCaseAudit audit = new TCaseAudit();
            audit.setCaseId(caseId);
            audit.setAuditTime(currentDate);
            audit.setRemark(remark);
            audit.setAuditerId(userId);
            audit.setAuditType(START.code());
            audit.setAuditResult(auditResult.code());//设置审核结果
            auditDao.save(audit);

            /**
             * 设置案件状态、操作类型
             */
            TCase tCase = caseDao.findById(caseId);
            String description = "";//操作描述
            OperationType operationType = null;
            switch (auditResult) {
                case PASS:
                    if (tCase.getInspectorId() != null) {
                        inspectorId = tCase.getInspectorId();
                    } else {
                        tCase.setInspectorId(inspectorId);
                    }
                    User inspector = userDao.get(inspectorId);
                    tCase.setDepartmentId(inspector.getDepartmentId());//设置下一节点操作部门
                    tCase.setStatus(INSPECT.code());
                    description = "通过立案审核！";
                    operationType = REGISTER_AUDIT;

                    //新增消息
                    CaseParam caseParam = new CaseParam();
                    caseParam.setCaseId(caseId);
                    caseParam.setUserId(String.valueOf(inspector.getUserId()));
                    caseParam.setIllegalArea(tCase.getIllegalArea());
                    caseParam.setIllegalAreaSpace(tCase.getIllegalAreaSpace());
                    caseParam.setParties(tCase.getParties());
                    msgResultService.add(generateMsg(caseParam, "一级消息"));

                    break;
                case NO_PASS:
                    tCase.setStatus(CANCEL.code());
                    description = "撤销案件！";
                    operationType = CANCELED;
                    break;
            }

            //更新案件
            caseDao.update(tCase);

            /**
             * 新增流程日志
             */
            addFlowLog(caseId, userId, operationType,
                    description, remark);

            return true;
        } catch (Exception e) {
            logger.error("新建立案审核记录", e);
            return false;
        }
    }

    /**
     * 通过案件ID，查询流转记录（返回的对象包含操作者名称、发送者名称、接收者名称）
     *
     * @param caseId
     * @return
     */
    public List<TCaseFlowRecord> findFlowRecordByCaseIdWithName(Integer caseId) {
        try {
            return flowRecordDao.findByCaseIdWithName(caseId);
        } catch (Exception e) {
            logger.error(" 通过案件ID，查询流转记录", e);
            return new ArrayList<TCaseFlowRecord>();
        }
    }

    /**
     * 通过案件ID，查询流转记录
     *
     * @param caseId
     * @return
     */
    public List<TCaseFlowRecord> findFlowRecordByCaseId(Integer caseId) {
        try {
            return flowRecordDao.findByCaseId(caseId);
        } catch (Exception e) {
            logger.error(" 通过案件ID，查询流转记录", e);
            return new ArrayList<TCaseFlowRecord>();
        }
    }

    /**
     * 查询审查记录列表（已结案）
     *
     * @param caseId 案件ID
     * @return
     */
    public List<TCaseAudit> findAuditById(Integer caseId) {
        try {
            return auditDao.findByCaseId(caseId);
        } catch (Exception e) {
            logger.error(" 查询审查记录列表（已结案）", e);
            return new ArrayList<TCaseAudit>();
        }
    }

    /**
     * 初始化巡查结果的键值对
     */
    public void initInspectResultMap() {
        if (SURVEY_RESULT_DATA_TYPES == null) {
            SURVEY_RESULT_DATA_TYPES = dataTypeService.findByVariableFieldKey
                    ("surveyResult");
            for (TDataType dataType : SURVEY_RESULT_DATA_TYPES) {
                SURVEY_RESULT_DATA_TYPES_MAP.put(dataType
                        .getDataTypeValue(), dataType.getDataTypeName());
            }

        }
    }

    /**
     * 获取巡查记录列表
     *
     * @param caseId 案件ID
     * @return
     */
    public List<TInspect> findInspectById(Integer caseId) {
        try {
            List<TInspect> inspects = inspectDao.findByCaseId(caseId);

            //设置巡查结果显示名称

            if (SURVEY_RESULT_DATA_TYPES_MAP.isEmpty()) {
                initInspectResultMap();
            }

            if (!SURVEY_RESULT_DATA_TYPES_MAP.isEmpty()) {
                for (TInspect inspect : inspects) {
                    String inspectResultName = SURVEY_RESULT_DATA_TYPES_MAP
                            .get(NumberUtils.toInt(inspect
                                    .getInspectResult()));
                    inspect.setInspectResultName
                            (inspectResultName);
                }
            }


            return inspects;
        } catch (Exception e) {
            logger.error("获取巡查记录列表", e);
            return new ArrayList<TInspect>();
        }
    }

    /**
     * 依据案件ID，查询案件可变字段列表
     * <p>
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
     * 依据ID，获取案件；并设置巡查结果
     *
     * @param caseId
     * @param fields 可变字段
     * @return
     */
    @Deprecated
    public TCase findById(Integer caseId, List<CaseVariableField> fields) {
        try {
            TCase tCase = caseDao.findById(caseId);
            tCase.setSurveyResult(getActualValue(fields, "surveyResult"));
            return tCase;
        } catch (Exception e) {
            logger.error(" 依据ID，获取案件；并设置巡查结果", e);
            return new TCase();
        }
    }


    /**
     * 获取可变字段的实际值
     *
     * @param fields         案件可变字段列表
     * @param ClassFieldName 类字段名称
     * @return
     */
    private static Integer getActualValue(List<CaseVariableField> fields, String
            ClassFieldName) {
        for (CaseVariableField field : fields) {
            if (StringUtils.equals(field.getFieldKey(), ClassFieldName)) {
                return NumberUtils.toInt(field.getFieldShow());
            }
        }
        return -1;
    }

    /**
     * 分页查询
     *
     * @param queryParam 查询参数
     * @return
     */
    public Page findPage(CaseParam queryParam) {
        try {

            //设置部门ID
            if (StringUtils.isNotBlank(queryParam.getUserId())) {
                queryParam.setDepartmentId(findDepartmentByUserId(queryParam.getUserId()));
            }

            return caseDao.findPage(queryParam);
        } catch (Exception e) {
            logger.error("分页查询", e);
            return new Page();

        }
    }

    /**
     * 新增流程日志
     *
     * @param caseId        案件ID
     * @param operatorId    操作者ID
     * @param fromUserId    案件最初巡查员ID
     * @param toUserId      案件指派的巡查员ID
     * @param operationType 操作类型
     * @param description   描述
     * @param remark        备注
     */
    private void addFlowLog(Integer caseId, Integer operatorId, Integer fromUserId, Integer
            toUserId,
                            OperationType
                                    operationType, String description, String remark) {
        TCaseFlowRecord flowRecord = new TCaseFlowRecord();
        flowRecord.setCaseId(caseId);
        flowRecord.setOperaterId(operatorId);
        flowRecord.setOperationType(operationType.code());
        flowRecord.setFromUserId(fromUserId);
        if (toUserId != null) {
            flowRecord.setToUserId(toUserId);
        }
        flowRecord.setCreateTime(new Date());

        //设置流程描述
        if (StringUtils.isNotBlank(remark)) {//加入审核意见
            description = description + " 审核意见：" + remark;
        }
        flowRecord.setOperation(description);
        flowRecordDao.save(flowRecord);
    }

    /**
     * 新增流程日志
     *
     * @param caseId        案件ID
     * @param operatorId    操作者ID
     * @param operationType 操作类型
     * @param description   描述
     * @param remark        备注
     */
    private void addFlowLog(Integer caseId, Integer operatorId,
                            OperationType
                                    operationType, String description, String remark) {
        addFlowLog(caseId, operatorId, operatorId, null, operationType, description, remark);
    }

    /**
     * 新增附件
     *
     * @param images       附件信息
     * @param relationId   关联的资源ID
     * @param relationType 资源类型
     */
    private void addAttachments(List<Images> images, Integer relationId, RelationType
            relationType) {
        if (images == null || images.isEmpty()) {
            return;
        }

        for (Images image : images) {
            //创建附件
            TAttachment attachment = new TAttachment();
            attachment.setAddr(PropertiesReader.value(Constants.FTP_HTTP_URL_PREFIX_ID) +
                    image.getImageAddr());
            attachment.setAttachmentType(image.getImageType());
            attachment.setCreateTime(new Date());
            attachmentDao.save(attachment);

            //创建附件关系
            TAttachmentRelation tAttachmentRelation = new TAttachmentRelation();
            tAttachmentRelation.setAttachmentId(attachment.getAttachmentId());
            tAttachmentRelation.setRelationId(relationId);
            tAttachmentRelation.setRelationType(relationType.code());
            attachmentRelationDao.save(tAttachmentRelation);
        }
    }

    /**
     * 新增附件
     *
     * @param imagesInJson 附件信息（json格式）
     * @param relationId   关联的资源ID
     * @param relationType 资源类型
     */
    private void addAttachments(String imagesInJson, Integer relationId, RelationType relationType) {
        if (StringUtils.isBlank(imagesInJson)) {
            return;
        }

        List<Images> images = JsonUtils.readJson(imagesInJson, List
                .class, Images.class);

        addAttachments(images, relationId, relationType);
    }

}
