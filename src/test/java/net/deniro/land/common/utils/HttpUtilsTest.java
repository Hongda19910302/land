package net.deniro.land.common.utils;

import net.deniro.land.module.system.entity.User;
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
public class HttpUtilsTest {

    @Test
    public void doPost(){
//        String url = "http://192.168.4.121:9080/gtweb/android/case-register-audit";
        Map<String, String> params = new HashMap<String, String>();
        params.put("userId", "1");
        params.put("caseId", "129");
        params.put("auditResult", "1");
        params.put("remark", "哈哈");

        String url2="http://192.168.1.102:8080/gtweb/android/case-register-audit";

        System.out.println(HttpUtils.doPost(url2, params, false));
    }

    @Test
    public void testDoGet() throws Exception {
        String url = "http://192.168.4.121:9080/gtweb/android/user-login";
        Map<String, String> params = new HashMap<String, String>();
        params.put("account", "admin");
        params.put("password", "7bbd73250516f069");
        params.put("loginType", String.valueOf(User.LoginType.NORMAL.code()));
        params.put("source", String.valueOf(User.LoginSource.ANDROID.getCode()));

        String url2="http://localhost:8080/gtweb/android/user-login";

        System.out.println(HttpUtils.doGet(url, params, true));
        System.out.println(HttpUtils.doGet(url2, params, true));


    }
}