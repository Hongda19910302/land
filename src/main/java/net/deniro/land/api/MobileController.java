package net.deniro.land.api;

import net.deniro.land.api.entity.*;
import net.deniro.land.common.dao.Page;
import net.deniro.land.common.service.Constants;
import net.deniro.land.common.service.dwz.Result;
import net.deniro.land.common.utils.FtpUtils;
import net.deniro.land.module.icase.entity.*;
import net.deniro.land.module.icase.service.CaseService;
import net.deniro.land.module.icase.service.VariableFieldService;
import net.deniro.land.module.system.entity.Department;
import net.deniro.land.module.system.entity.TRegion;
import net.deniro.land.module.system.entity.User;
import net.deniro.land.module.system.service.DepartmentService;
import net.deniro.land.module.system.service.RegionService;
import net.deniro.land.module.system.service.UserService;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static net.deniro.land.module.icase.entity.TCaseAudit.AuditResult.get;

/**
 * 移动客户端接口
 *
 * @author deniro
 *         2015/11/5
 */
@Controller
@RequestMapping("gtweb/android")
public class MobileController {

    static Logger logger = Logger.getLogger(MobileController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RegionService regionService;

    @Autowired
    private VariableFieldService variableFieldService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private CaseService caseService;

    @Autowired
    private FtpUtils ftpUtils;

    /**
     * 渲染文件的路径前缀
     */
    public static final String URL_PREFIX = "mobile/";

    /**
     * 通用响应结果模板文件名称
     */
    public static final String COMMON_RESULT_TEMPLATE_NAME = "commonResult";

    /**
     * 获取ftp服务器信息
     *
     * @param userId   用户ID
     * @param imgCount 上传图片总张数
     * @param mm
     * @return
     */
    @RequestMapping(value = "get-ftp-info")
    public String getFtpInfo(Integer userId, Integer imgCount, ModelMap
            mm) {
        ResponseResult r = null;

        try {
            mm.addAttribute("ftpUtils", ftpUtils);

            /**
             * 生成图片文件名
             */
            DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            String s = df.format(new Date());
            StringBuilder picNames = new StringBuilder("");
            for (int i = 1; i <= imgCount; i++) {
                picNames.append(s + "-" + i + ",");
            }
            picNames.deleteCharAt(picNames.length() - 1);
            mm.addAttribute("picNames", picNames);

            /**
             * 生成临时路径字符串
             */
            String tempPath = ftpUtils.getBaseDir() + Constants.FTP_PATH_SPLIT + ftpUtils.getTempDir() +
                    Constants.FTP_PATH_SPLIT + userId + Constants.FTP_PATH_SPLIT + ftpUtils.getImgDir();
            mm.addAttribute("tempPath", tempPath);

            /**
             * 生成实际路径字符串
             */
            String realPath = ftpUtils.getBaseDir() + Constants.FTP_PATH_SPLIT + ftpUtils.getRealDir() +
                    Constants.FTP_PATH_SPLIT + userId + Constants.FTP_PATH_SPLIT + ftpUtils.getImgDir();
            mm.addAttribute("realPath", realPath);

            /**
             * 在ftp上创建文件夹
             */
            ftpUtils.createDirs(tempPath);
            ftpUtils.createDirs(realPath);


            r = new SuccessResult();
        } catch (Exception e) {
            logger.error("获取ftp服务器信息", e);
            r = new FailureResult();
        } finally {
            mm.addAttribute("r", r);
            return URL_PREFIX + "getFtpInfoResult";
        }
    }

    /**
     * 修改案件
     *
     * @param caseParam 案件参数
     * @param mm
     * @return
     */
    @RequestMapping(value = "update-case")
    public String modifyCase(CaseParam caseParam, ModelMap
            mm) {
        ResponseResult r = null;

        try {
            boolean isOk = caseService.modifyCase(caseParam);
            if (isOk) {
                r = new SuccessResult();
            } else {
                r = new FailureResult();
            }
        } catch (Exception e) {
            logger.error("修改案件", e);
            r = new FailureResult();
        } finally {
            mm.addAttribute("r", r);
            return URL_PREFIX + COMMON_RESULT_TEMPLATE_NAME;
        }
    }

    /**
     * 新增案件
     *
     * @param caseParam 案件参数
     * @param mm
     * @return
     */
    @RequestMapping(value = "create-case")
    public String addCase(CaseParam caseParam, ModelMap
            mm) {
        ResponseResult r = null;

        try {
            boolean isOk = caseService.addCase(caseParam);
            if (isOk) {
                r = new SuccessResult();
            } else {
                r = new FailureResult();
            }
        } catch (Exception e) {
            logger.error("新增案件", e);
            r = new FailureResult();
        } finally {
            mm.addAttribute("r", r);
            return URL_PREFIX + COMMON_RESULT_TEMPLATE_NAME;
        }
    }

    /**
     * 分页查询 案件批示列表
     *
     * @param param 案件 移动端查询参数
     * @param mm
     * @return
     */
    @RequestMapping(value = "get-instruction-list")
    public String findPageCaseInstructionList(InstructionMobileQueryParam param,
                                              ModelMap mm) {
        return findPageCaseInstructions(param, mm);
    }

    /**
     * 新增案件批示
     *
     * @param userId  用户ID
     * @param caseId  案件ID
     * @param content 批示内容
     * @param mm
     * @return
     */
    @RequestMapping(value = "add-instruction")
    public String addInstruction(Integer userId, Integer caseId, String content, ModelMap
            mm) {
        ResponseResult r = null;

        try {
            boolean isOk = caseService.addInstruction(userId, caseId, content);
            if (isOk) {
                r = new SuccessResult();
            } else {
                r = new FailureResult();
            }
        } catch (Exception e) {
            logger.error("新增案件批示", e);
            r = new FailureResult();
        } finally {
            mm.addAttribute("r", r);
            return URL_PREFIX + COMMON_RESULT_TEMPLATE_NAME;
        }
    }

    /**
     * 删除案件批示
     *
     * @param instructionId 案件批示ID
     * @param mm
     * @return
     */
    @RequestMapping(value = "del-instruction")
    public String delInstruction(Integer instructionId, ModelMap mm) {
        ResponseResult r = null;

        try {
            boolean isOk = caseService.delInstruction(instructionId);
            if (isOk) {
                r = new SuccessResult();
            } else {
                r = new FailureResult();
            }
        } catch (Exception e) {
            logger.error("删除案件批示", e);
            r = new FailureResult();
        } finally {
            mm.addAttribute("r", r);
            return URL_PREFIX + COMMON_RESULT_TEMPLATE_NAME;
        }
    }

    /**
     * 分页查询 案件批示列表
     *
     * @param param 案件 移动端查询参数
     * @param mm
     * @return
     */
    @RequestMapping(value = "get-instruction-details")
    public String findPageCaseInstructions(InstructionMobileQueryParam param,
                                           ModelMap mm) {
        ResponseResult r = null;

        try {
            InstructionQueryParam instructionQueryParam = new InstructionQueryParam();
            instructionQueryParam.setNumPerPage(param.getLimit());
            instructionQueryParam.setPageNum(param.getPageNo());
            instructionQueryParam.setCaseId(String.valueOf(param.getCaseId()));

            BeanUtils.copyProperties(param, instructionQueryParam);

            Page page = caseService.findPageInstructions(instructionQueryParam);
            mm.addAttribute("nativePage", page);
            mm.addAttribute("pageNo", param.getPageNo());

            TCase tCase = caseService.findById(param.getCaseId());
            tCase.setInstructionState(String.valueOf(caseService.getInstructionState(param
                            .getCaseId(),
                    param
                            .getUserId()).code()));//设置案件批示状态
            mm.addAttribute("tCase", tCase);
            r = new SuccessResult();
        } catch (Exception e) {
            logger.error("  分页查询 案件批示");
            r = new FailureResult();
        } finally {
            mm.addAttribute("r", r);
            return URL_PREFIX + "findPageCaseInstructionsResult";
        }
    }

    /**
     * 分页查询案件（符合批示条件）
     *
     * @param caseMobileQueryParam 案件 移动端查询参数
     * @param mm
     * @return
     */
    @RequestMapping(value = "get-instruction-case")
    public String findPageCaseForInstruction(CaseMobileQueryParam caseMobileQueryParam,
                                             ModelMap mm) {
        ResponseResult r = null;

        try {
            CaseParam caseParam = new CaseParam();
            caseParam.setNumPerPage(caseMobileQueryParam.getLimit());
            caseParam.setPageNum(caseMobileQueryParam.getPageNo());
            caseParam.setUserId(caseMobileQueryParam.getUserId());

            BeanUtils.copyProperties(caseMobileQueryParam, caseParam);

            Page page = caseService.findPageForInstruction(caseParam);
            mm.addAttribute("nativePage", page);
            mm.addAttribute("pageNo", caseMobileQueryParam.getPageNo());

            r = new SuccessResult();
        } catch (Exception e) {
            logger.error("  分页查询 案件批示");
            r = new FailureResult();
        } finally {
            mm.addAttribute("r", r);
            return URL_PREFIX + "findPageCaseForInstructionResult";
        }
    }

    /**
     * 案件指派
     *
     * @param param 流转参数
     * @param mm
     * @return
     */
    @RequestMapping(value = "change-xcy")
    public String caseAssign(AssignParam param, ModelMap mm) {
        ResponseResult r = null;

        try {
            boolean isOk = caseService.assign(param);
            if (isOk) {
                r = new SuccessResult();
            } else {
                r = new FailureResult();
            }
        } catch (Exception e) {
            logger.error("案件流转", e);
            r = new FailureResult();
        } finally {
            mm.addAttribute("r", r);
            return URL_PREFIX + COMMON_RESULT_TEMPLATE_NAME;
        }
    }


    /**
     * 结案审核
     *
     * @param overAuditParam 结案审核参数
     * @param mm
     * @return
     */
    @RequestMapping(value = "case-over-audit")
    public String overCaseAudit(OverAuditParam overAuditParam, ModelMap mm) {
        ResponseResult r = null;

        try {
            boolean isOk = caseService.overAudit(overAuditParam);
            if (isOk) {
                r = new SuccessResult();
            } else {
                r = new FailureResult();
            }
        } catch (Exception e) {
            logger.error("结案审核", e);
            r = new FailureResult();
        } finally {
            mm.addAttribute("r", r);
            return URL_PREFIX + COMMON_RESULT_TEMPLATE_NAME;
        }
    }

    /**
     * 巡查案件
     *
     * @param inspectParam 案件巡查参数
     * @param mm
     * @return
     */
    @RequestMapping(value = "inspect-case")
    public String inspectCase(InspectParam inspectParam, ModelMap mm) {
        ResponseResult r = null;

        try {
            boolean isOk = caseService.inspect(inspectParam);
            if (isOk) {
                r = new SuccessResult();
            } else {
                r = new FailureResult();
            }
        } catch (Exception e) {
            logger.error("巡查案件", e);
            r = new FailureResult();
        } finally {
            mm.addAttribute("r", r);
            return URL_PREFIX + COMMON_RESULT_TEMPLATE_NAME;
        }
    }

    /**
     * 新建立案审核记录
     *
     * @param userId      用户ID
     * @param caseId      案件ID
     * @param auditResult 审核结果
     * @param remark      备注
     */
    @RequestMapping(value = "case-register-audit")
    public String auditCase(Integer userId, Integer caseId, Integer auditResult, String
            remark, ModelMap mm) {
        ResponseResult r = null;

        if (auditResult == null) {
            mm.addAttribute("r", new FailureResult());
            return URL_PREFIX + COMMON_RESULT_TEMPLATE_NAME;
        }

        try {
            boolean isOk = caseService.audit(userId, caseId, get(auditResult), remark);
            if (isOk) {
                r = new SuccessResult();
            } else {
                r = new FailureResult();
            }
        } catch (Exception e) {
            logger.error("新建立案审核记录", e);
            r = new FailureResult();
        } finally {
            mm.addAttribute("r", r);
            return URL_PREFIX + COMMON_RESULT_TEMPLATE_NAME;
        }
    }

    /**
     * 案件详情-案件流转记录
     *
     * @param caseId
     * @param mm
     * @return
     */
    @RequestMapping(value = "get-case-flow-record")
    public String findFlowRecordByCaseId(Integer caseId, ModelMap mm) {
        ResponseResult r = null;

        try {
            mm.addAttribute("caseFlowRecordList", caseService.findFlowRecordByCaseId
                    (caseId));

            r = new SuccessResult();
        } catch (Exception e) {
            logger.error("案件详情-案件流转记录");
            r = new FailureResult();
        } finally {
            mm.addAttribute("r", r);
            return URL_PREFIX + "findFlowRecordByCaseIdResult";
        }
    }

    /**
     * 案件详情-巡查记录+核查记录
     *
     * @param caseId
     * @param mm
     * @return
     */
    @RequestMapping(value = "get-inspect-record")
    public String findInspectAndAuditById(Integer caseId, ModelMap mm) {
        ResponseResult r = null;

        try {
            List<CaseVariableField> fields = caseService.findVariablesById(caseId);
            TCase tCase = caseService.findById(caseId, fields);
            mm.addAttribute("tCase", tCase);
            mm.addAttribute("inspectList", caseService.findInspectById(caseId, fields));
            mm.addAttribute("caseAuditList", caseService.findAuditById(caseId));

            r = new SuccessResult();
        } catch (Exception e) {
            logger.error("案件详情-巡查记录+核查记录");
            r = new FailureResult();
        } finally {
            mm.addAttribute("r", r);
            return URL_PREFIX + "findInspectAndAuditByIdResult";
        }
    }


    /**
     * 案件详情
     *
     * @param caseId
     * @param mm
     * @return
     */
    @RequestMapping(value = "case-detail")
    public String findCaseById(Integer caseId, ModelMap mm) {
        ResponseResult r = null;

        try {
            TCase tCase = caseService.findById(caseId);
            mm.addAttribute("tCase", tCase);
            mm.addAttribute("tVariableFieldList", caseService.findVariablesById(caseId));

            r = new SuccessResult();
        } catch (Exception e) {
            logger.error("案件详情");
            r = new FailureResult();
        } finally {
            mm.addAttribute("r", r);
            return URL_PREFIX + "findCaseByIdResult";
        }
    }

    /**
     * 分页查询案件（搜索案件）
     *
     * @param caseMobileQueryParam 案件 移动端查询参数
     * @param mm
     * @return
     */
    @RequestMapping(value = "query-case")
    public String findPageCase2(CaseMobileQueryParam caseMobileQueryParam, ModelMap mm) {
        return findPageCase(caseMobileQueryParam, mm);
    }

    /**
     * 分页查询案件
     *
     * @param caseMobileQueryParam 案件 移动端查询参数
     * @param mm
     * @return
     */
    @RequestMapping(value = "search-case")
    public String findPageCase(CaseMobileQueryParam caseMobileQueryParam, ModelMap mm) {
        ResponseResult r = null;

        try {
            CaseParam caseParam = new CaseParam();
            caseParam.setNumPerPage(caseMobileQueryParam.getLimit());
            caseParam.setPageNum(caseMobileQueryParam.getPageNo());
            caseParam.setMoblieStatus(caseMobileQueryParam.getSearchType());

            BeanUtils.copyProperties(caseMobileQueryParam, caseParam);

            System.out.println(caseParam);

            Page page = caseService.findPage(caseParam);
            mm.addAttribute("nativePage", page);
            mm.addAttribute("pageNo", caseMobileQueryParam.getPageNo());

            r = new SuccessResult();
        } catch (Exception e) {
            logger.error("分页查询案件");
            r = new FailureResult();
        } finally {
            mm.addAttribute("r", r);
            return URL_PREFIX + "findPageCaseResult";
        }
    }

    /**
     * 依据用户ID，获取巡查员信息
     *
     * @param xcyId 用户ID（即UserId）
     * @param mm
     * @return
     */
    @RequestMapping(value = "get-inspector-by-userId")
    public String findInspectorByUserId(Integer xcyId, ModelMap mm) {
        ResponseResult r = null;

        try {
            User user = userService.get(xcyId);
            mm.addAttribute("user", user);

            r = new SuccessResult();
        } catch (Exception e) {
            logger.error("依据用户ID，获取巡查员信息");
            r = new FailureResult();
        } finally {
            mm.addAttribute("r", r);
            return URL_PREFIX + "findInspectorByUserIdResult";
        }
    }

    /**
     * 依据部门ID，获取巡查员
     *
     * @param departmentId
     * @param mm
     * @return
     */
    @RequestMapping(value = "get-inspector-by-com")
    public String findInspectorsByDepartmentId(Integer departmentId, ModelMap mm) {
        ResponseResult r = null;

        try {
            List<User> users = userService.findInspectorsByDepartmentId(departmentId);
            mm.addAttribute("userList", users);

            List<Department> departments = departmentService.findChilds(departmentId);
            mm.addAttribute("departmentList", departments);

            r = new SuccessResult();
        } catch (Exception e) {
            logger.error("获取单位网下的巡查员");
            r = new FailureResult();
        } finally {
            mm.addAttribute("r", r);
            return URL_PREFIX + "findInspectorsByDepartmentIdResult";
        }
    }

    /**
     * 依据创建者ID，获取巡查员列表
     *
     * @param creatorId
     * @param mm
     * @return
     */
    @RequestMapping(value = "get-recommond-inspector")
    public String findInspectorsByCreatorId(Integer creatorId, ModelMap mm) {
        ResponseResult r = null;

        try {
            List<User> list = userService.findInspectorsByCreatorId(creatorId);
            mm.addAttribute("userList", list);
            r = new SuccessResult();
        } catch (Exception e) {
            logger.error(" 依据单位ID，获取字段信息");
            r = new FailureResult();
        } finally {
            mm.addAttribute("r", r);
            return URL_PREFIX + "findInspectorsByCreatorIdResult";
        }
    }

    /**
     * 依据单位ID，获取字段信息
     *
     * @param companyId
     * @param mm
     * @return
     */
    @RequestMapping(value = "get-variable-field-list")
    public String findVariableFieldByCompanyId(Integer companyId, ModelMap mm) {
        ResponseResult r = null;

        try {
            List<TVariableField> list = variableFieldService.findByCompanyId(companyId);
            mm.addAttribute("tVariableFieldList", list);
            r = new SuccessResult();
        } catch (Exception e) {
            logger.error(" 依据单位ID，获取字段信息");
            r = new FailureResult();
        } finally {
            mm.addAttribute("r", r);
            return URL_PREFIX + "findVariableFieldByCompanyIdResult";
        }
    }

    /**
     * 依据区域ID，获取子区域列表
     *
     * @param regionId
     * @param mm
     * @return
     */
    @RequestMapping(value = "get-next-child-region")
    public String findRegionChildrenByRegionId(Integer regionId, ModelMap mm) {
        ResponseResult r = null;

        try {
            List<TRegion> regions = regionService.findChildrenByRegionId(regionId);
            mm.addAttribute("regionList", regions);
            r = new SuccessResult();
        } catch (Exception e) {
            logger.error("  依据区域ID，获取子区域列表");
            r = new FailureResult();
        } finally {
            mm.addAttribute("r", r);
            return URL_PREFIX + "findRegionChildrenByRegionIdResult";
        }
    }

    /**
     * 根据单位id获取顶级行政机构
     *
     * @param companyId
     * @return
     */
    @RequestMapping(value = "/get-top-region-by-company")
    public String findTopRegionByCompanyId(Integer companyId, ModelMap mm) {

        ResponseResult r = null;

        try {
            List<TRegion> regions = regionService.findByCompanyId(companyId);
            mm.addAttribute("regionList", regions);
            r = new SuccessResult();
        } catch (Exception e) {
            logger.error("根据单位id获取顶级行政机构");
            r = new FailureResult();
        } finally {
            mm.addAttribute("r", r);
            return URL_PREFIX + "findTopRegionByCompanyIdResult";
        }
    }

    /**
     * 登录
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/user-login")
    public String login(LoginParam param, ModelMap mm) {

        /**
         * 尝试登录
         */
        int loginTypeCode = NumberUtils.toInt(param.getLoginType());
        int loginSourceCode = NumberUtils.toInt(param.getSource());
        Result result = userService.login(param.getAccount(), param.getPassword(),
                loginSourceCode, loginTypeCode);

        /**
         * 生成结果
         */
        ResponseResult r = new ResponseResult();
        if (result.isSuccess()) {//成功后，设置账户对象
            mm.addAttribute("user", result.get(UserService.USER_CODE));
            r.setResult(ResultCode.SUCCESS.value());
        } else {//失败
            r.setResult(ResultCode.FAILURE.value());
        }
        r.setDescribe(result.getMessage());

        mm.addAttribute("r", r);

        return URL_PREFIX + "loginResult";
    }
}
