package net.deniro.land.api;

import junit.framework.Assert;
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
        ".xml", "classpath:spring/spring-context-dataset-type.xml"})
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
    public void complexPageCase(){
        String queryString = "userId=37&searchType=2&pageNo=1&limit=10&regionId=102" +
                "&beginDate=2014-10-23&creatorName=18905910011";

        String action = "query-case";
        String url = URL_PREFIX + action;
        String newUrl = NEW_URL_PREFIX + action;

        Assert.assertEquals(HttpUtils.doGet(url, queryString, false), HttpUtils.doGet(newUrl,
                queryString, false));
    }

    @Test
    public void findPageCase(){
        String queryString = "userId=37&searchType=2&pageNo=1&limit=10";

        String action = "search-case";
        String url = URL_PREFIX + action;
        String newUrl = NEW_URL_PREFIX + action;

        Assert.assertEquals(HttpUtils.doGet(url, queryString, false), HttpUtils.doGet(newUrl,
                queryString, false));
    }

    @Test
    public void findInspectorByUserId(){
        String queryString = "xcyId=1";

        String action = "get-inspector-by-userId";
        String url = URL_PREFIX + action;
        String newUrl = NEW_URL_PREFIX + action;

//        Assert.assertEquals(HttpUtils.doGet(url, queryString, false), HttpUtils.doGet(newUrl,
//                queryString, false));
    }

    @Test
    public void findInspectorsByDepartmentId(){
        String queryString = "departmentId=1";

        String action = "get-inspector-by-com";
        String url = URL_PREFIX + action;
        String newUrl = NEW_URL_PREFIX + action;

//        Assert.assertEquals(HttpUtils.doGet(url, queryString, false), HttpUtils.doGet(newUrl,
//                queryString, false));
    }

    @Test
    public void findInspectorsByCreatorId(){
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