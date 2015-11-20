package net.deniro.land.api;

import junit.framework.Assert;
import net.deniro.land.api.entity.AssignParam;
import net.deniro.land.api.entity.InspectParam;
import net.deniro.land.api.entity.OverAuditParam;
import net.deniro.land.common.utils.HttpUtils;
import net.deniro.land.module.system.entity.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * @author deniro
 *         2015/11/5
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-context-base.xml",
        "classpath:spring/spring-context-db.xml", "classpath:spring/spring-context-tx" +
        ".xml", "classpath:spring/spring-context-dataset-type.xml",
        "classpath:spring/spring-context-ftp.xml"})
@TransactionConfiguration
@Transactional
public class MobileControllerTest {

    /**
     * 旧版URL前缀
     */
    public static final String URL_PREFIX = "http://192.168.4.121:9080/gtweb/android/";

    /**
     * 新版URL前缀
     */
    public static final String NEW_URL_PREFIX = "http://localhost:8080/gtweb/android/";

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getFtpInfo() {
        String queryString = "userId=1&imgCount=3";

        String action = "get-ftp-info";
        String url = URL_PREFIX + action;
        String newUrl = NEW_URL_PREFIX + action;

        Assert.assertEquals(HttpUtils.doGet(url, queryString, false), HttpUtils.doGet(newUrl,
                queryString, false));
    }

    @Test
    public void modifyCase() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("caseId", "130");
        params.put("punisher", "林波3");
        params.put("placeId", "263");
        params.put("east", "263");
        params.put("south", "263");
        params.put("west", "263");
        params.put("north", "263");
        params.put("illegalAddr", "26.9713");
        params.put("space", "26.2973");
        params.put("caseComment", "背书3");
        params.put("gpsFlag", "2");
        params.put("gpsX", "26.239773");
        params.put("gpsY", "26.239723");
//        params.put("xcyId", "3");
        params.put("idCardNum", "23297923");
        params.put("floorSpace", "2629.23");
        params.put("buildingSpace", "26.233");
        params.put("illegalType", "2");
        params.put("landUsage", "2");
        params.put("currentStatus", "3");
        params.put("inspectResult", "2");
        params.put("caseSource", "24");


        String action = "update-case";
        String url = URL_PREFIX + action;
        String newUrl = NEW_URL_PREFIX + action;

//        Assert.assertEquals(HttpUtils.doPost(url, params, false), HttpUtils.doPost(newUrl,
//                params, false));
    }

    @Test
    public void addCase() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("userId", "1");
        params.put("parties", "林波");
        params.put("regionId", "26");
        params.put("eastTo", "26");
        params.put("southTo", "26");
        params.put("westTo", "26");
        params.put("northTo", "26");
        params.put("illegalAddr", "26.971");
        params.put("illegalAreaSpace", "26.297");
        params.put("remark", "背书");
        params.put("gpsFlag", "1");
        params.put("lng", "26.23977");
        params.put("lat", "26.23972");
        params.put("idCardNum", "2329792");
        params.put("floorSpace", "2629.2");
        params.put("buildingSpace", "26.23");
        params.put("illegalType", "1");
        params.put("illegalUse", "1");
        params.put("landUsage", "1");
        params.put("currentStatus", "2");
        params.put("inspectResult", "2");
        params.put("caseSource", "26");
        params.put("status", "2");
        params.put("companyId", "1");
        params.put("departmentId", "26");


        String action = "create-case";
        String url = URL_PREFIX + action;
        String newUrl = NEW_URL_PREFIX + action;

//        Assert.assertEquals(HttpUtils.doPost(url, params, false), HttpUtils.doPost(newUrl,
//                params, false));
    }


    @Test
    public void findPageCaseInstructionList() {
        String queryString = "caseId=336&pageNo=1&limit=10";

        String action = "get-instruction-list";
        String url = URL_PREFIX + action;
        String newUrl = NEW_URL_PREFIX + action;

//        Assert.assertEquals(HttpUtils.doGet(url, queryString, false), HttpUtils.doGet(newUrl,
//                queryString, false));
    }

    @Test
    public void addInstruction() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("userId", "1");
        params.put("caseId", "129");
        params.put("content", "37杀我诶UFO额");


        String action = "add-instruction";
        String url = URL_PREFIX + action;
        String newUrl = NEW_URL_PREFIX + action;

//        Assert.assertEquals(HttpUtils.doPost(url, params, false), HttpUtils.doPost(newUrl,
//                params, false));
    }

    @Test
    public void delInstruction() {
        String queryString = "instructionId=1";

        String action = "del-instruction";
        String url = URL_PREFIX + action;
        String newUrl = NEW_URL_PREFIX + action;

//        Assert.assertEquals(HttpUtils.doGet(url, queryString, false), HttpUtils.doGet(newUrl,
//                queryString, false));
    }

    @Test
    public void findPageCaseInstructions() {
        String queryString = "caseId=336&pageNo=1&limit=10";

        String action = "get-instruction-details";
        String url = URL_PREFIX + action;
        String newUrl = NEW_URL_PREFIX + action;

//        Assert.assertEquals(HttpUtils.doGet(url, queryString, false), HttpUtils.doGet(newUrl,
//                queryString, false));
    }

    @Test
    public void findPageCaseForInstruction() {
        String queryString = "userId=37&pageNo=1&limit=10";

        String action = "get-instruction-case";
        String url = URL_PREFIX + action;
        String newUrl = NEW_URL_PREFIX + action;

//        Assert.assertEquals(HttpUtils.doGet(url, queryString, false), HttpUtils.doGet(newUrl,
//                queryString, false));
    }

    @Test
    public void caseAssign() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("userId", "1");
        params.put("caseId", "129");
        params.put("xcyId", "37");
        params.put("type", String.valueOf(AssignParam.AssignType.INSPECTOR.code()));


        String action = "change-xcy";
        String url = URL_PREFIX + action;
        String newUrl = NEW_URL_PREFIX + action;

//        Assert.assertEquals(HttpUtils.doPost(url, params, false), HttpUtils.doPost(newUrl,
//                params, false));
    }

    @Test
    public void overCaseAudit() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("userId", "1");
        params.put("caseId", "129");
        params.put("checkType", "1");
        params.put("remark", "overCaseAudit哈哈");
        params.put("caseStatus", String.valueOf(OverAuditParam.AuditResult.PASS.code
                ()));
        params.put("images", "[" +
                "{" +
                "\"imageAddr\":\"overCaseAudit://weoruo/fwdw\",\n" +
                "\"imageType\":\"1\"\n" +
                "}" + ",{" +
                "\"imageAddr\":\"://233/overCaseAudit\",\n" +
                "\"imageType\":\"0\"\n" +
                "}" +
                "]");


        String action = "case-over-audit";
        String url = URL_PREFIX + action;
        String newUrl = NEW_URL_PREFIX + action;

//        Assert.assertEquals(HttpUtils.doPost(url, params, false), HttpUtils.doPost(newUrl,
//                params, false));
    }

    @Test
    public void inspectCase() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("userId", "1");
        params.put("caseId", "129");
        params.put("inspectResult", "0");
        params.put("remark", "inspectCase");
        params.put("caseStatus", String.valueOf(InspectParam.CaseStatus.NO_CLOSE.code()));
        params.put("images", "[" +
                "{" +
                "\"imageAddr\":\"inspectCase://weoruo/fwdw\",\n" +
                "\"imageType\":\"1\"\n" +
                "}" + ",{" +
                "\"imageAddr\":\"://inspectCase/f331是非法的wdw\",\n" +
                "\"imageType\":\"0\"\n" +
                "}" +
                "]");


        String action = "inspect-case";
        String url = URL_PREFIX + action;
        String newUrl = NEW_URL_PREFIX + action;

//        Assert.assertEquals(HttpUtils.doPost(url, params, false), HttpUtils.doPost(newUrl,
//                params, false));
    }

    @Test
    public void auditCase() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("userId", "1");
        params.put("caseId", "129");
        params.put("auditResult", "0");
        params.put("remark", "哈哈");


        String action = "case-register-audit";
        String url = URL_PREFIX + action;
        String newUrl = NEW_URL_PREFIX + action;

        Assert.assertEquals(HttpUtils.doPost(url, params, false), HttpUtils.doPost(newUrl,
                params, false));
    }

    @Test
    public void findFlowRecordByCaseId() {
        String queryString = "caseId=129";

        String action = "get-case-flow-record";
        String url = URL_PREFIX + action;
        String newUrl = NEW_URL_PREFIX + action;

//        Assert.assertEquals(HttpUtils.doGet(url, queryString, false), HttpUtils.doGet(newUrl,
//                queryString, false));
    }


    @Test
    public void findInspectAndAuditById() {
        String queryString = "caseId=129";

        String action = "get-inspect-record";
        String url = URL_PREFIX + action;
        String newUrl = NEW_URL_PREFIX + action;

//        Assert.assertEquals(HttpUtils.doGet(url, queryString, false), HttpUtils.doGet(newUrl,
//                queryString, false));
    }


    @Test
    public void findCaseById() {
        String queryString = "caseId=129";

        String action = "case-detail";
        String url = URL_PREFIX + action;
        String newUrl = NEW_URL_PREFIX + action;

//        Assert.assertEquals(HttpUtils.doGet(url, queryString, false), HttpUtils.doGet(newUrl,
//                queryString, false));
    }

    @Test
    public void complexPageCase() {
        String queryString = "userId=37&searchType=2&pageNo=1&limit=10&regionId=102" +
                "&beginDate=2014-10-23&creatorName=18905910011";

        String action = "query-case";
        String url = URL_PREFIX + action;
        String newUrl = NEW_URL_PREFIX + action;

//        Assert.assertEquals(HttpUtils.doGet(url, queryString, false), HttpUtils.doGet(newUrl,
//                queryString, false));
    }

    @Test
    public void findPageCase() {
        String queryString = "userId=37&searchType=2&pageNo=1&limit=10";

        String action = "search-case";
        String url = URL_PREFIX + action;
        String newUrl = NEW_URL_PREFIX + action;

//        Assert.assertEquals(HttpUtils.doGet(url, queryString, false), HttpUtils.doGet(newUrl,
//                queryString, false));
    }

    @Test
    public void findInspectorByUserId() {
        String queryString = "xcyId=1";

        String action = "get-inspector-by-userId";
        String url = URL_PREFIX + action;
        String newUrl = NEW_URL_PREFIX + action;

//        Assert.assertEquals(HttpUtils.doGet(url, queryString, false), HttpUtils.doGet(newUrl,
//                queryString, false));
    }

    @Test
    public void findInspectorsByDepartmentId() {
        String queryString = "departmentId=1";

        String action = "get-inspector-by-com";
        String url = URL_PREFIX + action;
        String newUrl = NEW_URL_PREFIX + action;

//        Assert.assertEquals(HttpUtils.doGet(url, queryString, false), HttpUtils.doGet(newUrl,
//                queryString, false));
    }

    @Test
    public void findInspectorsByCreatorId() {
        String queryString = "creatorId=37";

        String action = "get-recommond-inspector";
        String url = URL_PREFIX + action;
        String newUrl = NEW_URL_PREFIX + action;

//        Assert.assertEquals(HttpUtils.doGet(url, queryString, false), HttpUtils.doGet(newUrl,
//                queryString, false));
    }

    @Test
    public void findVariableFieldByCompanyId() {
        String queryString = "companyId=1";

        String action = "get-variable-field-list";
        String url = URL_PREFIX + action;
        String newUrl = NEW_URL_PREFIX + action;

//        Assert.assertEquals(HttpUtils.doGet(url, queryString, false), HttpUtils.doGet(newUrl,
//                queryString, false));
    }

    @Test
    public void findRegionChildrenByRegionId() {
        String queryString = "regionId=31";

        String action = "get-next-child-region";
        String url = URL_PREFIX + action;
        String newUrl = NEW_URL_PREFIX + action;

//        Assert.assertEquals(HttpUtils.doGet(url, queryString, false), HttpUtils.doGet(newUrl,
//                queryString, false));
    }

    @Test
    public void findTopRegionByCompanyId() {
        String queryString = "comparyId=31";

        String action = "get-top-region-by-company";
        String url = URL_PREFIX + action;
        String newUrl = NEW_URL_PREFIX + action;

//        Assert.assertEquals(HttpUtils.doGet(url, queryString, false), HttpUtils.doGet(newUrl,
//                queryString, false));
    }

    @Test
    public void testLogin() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("account", "admin");
        params.put("password", "7bbd73250516f069");
        params.put("loginType", String.valueOf(User.LoginType.NORMAL.code()));
        params.put("source", String.valueOf(User.LoginSource.ANDROID.getCode()));

        String action = "user-login";
        String url = URL_PREFIX + action;
        String newUrl = NEW_URL_PREFIX + action;

//        Assert.assertEquals(HttpUtils.doGet(url, params, false), HttpUtils.doGet(newUrl,
//                params, false));

    }
}