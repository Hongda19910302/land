package net.deniro.land.mobile;

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
    public void testLogin() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("account", "admin");
        params.put("password", "7bbd73250516f069");
        params.put("loginType", String.valueOf(User.LoginType.NORMAL.code()));
        params.put("source", String.valueOf(User.LoginSource.ANDROID.getCode()));

        String action = "user-login";
        String url = URL_PREFIX + action;
        String newUrl = NEW_URL_PREFIX + action;

        Assert.assertEquals(HttpUtils.doGet(url, params, false), HttpUtils.doGet(newUrl,
                params, false));

    }
}