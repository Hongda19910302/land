package net.deniro.land.module.icase.service;

import net.deniro.land.api.entity.AssignParam;
import net.deniro.land.api.entity.Images;
import net.deniro.land.api.entity.InspectParam;
import net.deniro.land.api.entity.OverAuditParam;
import net.deniro.land.common.dao.Page;
import net.deniro.land.common.utils.JsonUtils;
import net.deniro.land.common.utils.PropertiesReader;
import net.deniro.land.module.icase.dao.*;
import net.deniro.land.module.icase.entity.*;
import net.deniro.land.module.icase.entity.TAttachmentRelation.RelationType;
import net.deniro.land.module.icase.entity.TCase.InstructionState;
import net.deniro.land.module.icase.entity.TCaseAudit.AuditResult;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static net.deniro.land.api.entity.OverAuditParam.CheckType;
import static net.deniro.land.module.icase.entity.TCase.CaseStatus.*;
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
            if (count > 0) {
                return true;
            } else {
                return false;
            }
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
    public Page findPageForInstruction(CaseQueryParam queryParam) {
        try {

            //设置部门ID
            queryParam.setDepartmentId(findDepartmentByUserId(queryParam.getUserId()));

            /**
             * 新增状态
             */
            List<TCase.CaseStatus> includeStatus = new ArrayList<TCase.CaseStatus>();
            includeStatus.add(TCase.CaseStatus.PREPARE);
            includeStatus.add(TCase.CaseStatus.INSPECT);
            includeStatus.add(TCase.CaseStatus.APPLY);
            includeStatus.add(TCase.CaseStatus.FIRST_OVER);
            queryParam.setIncludeStatus(includeStatus);

            return caseDao.findPage(queryParam);
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
                            .ASSIGN,
                    "案件指派", "");

            return true;
        } catch (Exception e) {
            logger.error(" 流转案件（指派案件）", e);
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
                caseDao.update(tCase);
            }


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
            inspect.setInspectResult(inspectParam.getInspectResult());
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
     * @param auditResult 审核结果
     * @param remark      备注
     * @return 是否成功
     */
    public boolean audit(Integer userId, Integer caseId, AuditResult auditResult, String
            remark) {

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
                    User user = userDao.get(userId);
                    tCase.setDepartmentId(user.getDepartmentId());//设置下一节点操作部门
                    tCase.setStatus(INSPECT.code());
                    description = "通过立案审核！";
                    operationType = REGISTER_AUDIT;
                    break;
                case NO_PASS:
                    tCase.setStatus(CANCEL.code());
                    description = "撤销案件！";
                    operationType = ASSIGN;
                    break;
            }

            //更新案件
            caseDao.update(tCase);

            /**
             * 新增流程日志
             */
            addFlowLog(caseId, userId, operationType,
                    description, remark);

            //todo 立案审核通过，写入短信

            return true;
        } catch (Exception e) {
            logger.error("新建立案审核记录", e);
            return false;
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
     * 获取巡查记录列表
     *
     * @param caseId 案件ID
     * @return
     */
    public List<TInspect> findInspectById(Integer caseId, List<CaseVariableField> fields) {
        try {
            List<TInspect> inspects = inspectDao.findByCaseId(caseId);
            for (TInspect inspect : inspects) {
                inspect.setInspectResult(getActualValue(fields, "inspectResult"));
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
    public Page findPage(CaseQueryParam queryParam) {
        try {

            //设置部门ID
            queryParam.setDepartmentId(findDepartmentByUserId(queryParam.getUserId()));

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
     * @param imagesInJson 附件信息（json格式）
     * @param relationId   关联的资源ID
     * @param relationType 资源类型
     */
    private void addAttachments(String imagesInJson, Integer relationId, RelationType relationType) {
        List<Images> images = JsonUtils.readJson(imagesInJson, List
                .class, Images.class);
        for (Images image : images) {
            //创建附件
            TAttachment attachment = new TAttachment();
            attachment.setAddr(PropertiesReader.value("httpPrefix") + image.getImageAddr());
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

}
