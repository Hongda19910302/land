package net.deniro.land.common.utils;

import net.deniro.land.common.service.Constants;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * http 工具
 *
 * @author deniro
 *         2015/11/5
 */
public class HttpUtils {

    static Logger logger = Logger.getLogger(HttpUtils.class);


    /**
     * 模拟Get请求
     *
     * @param url      请求地址
     * @param params   请求参数
     * @param isPretty 返回的结果字符串是否美化
     * @return
     */
    public static String doGet(String url, Map<String, String> params, boolean isPretty) {
        if (StringUtils.isBlank(url)) {
            return "";
        }

        StringBuilder response = new StringBuilder();

        HttpClient httpClient = new HttpClient();
        HttpMethod httpMethod = new GetMethod(url);
        if (params != null && !params.isEmpty()) {//添加参数
            List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
            for (String key : params.keySet()) {
                NameValuePair pair = new NameValuePair(key, params.get(key));
                pairs.add(pair);
            }
            httpMethod.setQueryString(pairs.toArray(new NameValuePair[0]));
        }

        try {
            httpClient.executeMethod(httpMethod);
            logger.info("getStatusCode：" + httpMethod.getStatusCode());
            if (httpMethod.getStatusCode() == HttpStatus.SC_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(httpMethod
                        .getResponseBodyAsStream(), Constants.CHARSET));

                String line;
                while ((line = reader.readLine()) != null) {
                    if (isPretty) {
                        response.append(line).append(Constants.NEW_LINE);
                    } else {
                        response.append(line);
                    }
                }

                reader.close();
            }

        } catch (IOException e) {
            logger.error("模拟Get请求", e);
        } finally {
            httpMethod.releaseConnection();
        }

        return response.toString();
    }
}
