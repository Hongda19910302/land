package net.deniro.land.common.utils;

import net.deniro.land.common.service.Constants;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
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
     * 模拟Post请求
     *
     * @param url      请求地址
     * @param params   请求参数
     * @param isPretty 返回的结果字符串是否美化
     * @return
     */
    public static String doPost(String url, Map<String, String> params, boolean isPretty) {
        if (StringUtils.isBlank(url)) {
            return "";
        }

        /**
         * 添加参数
         */
        PostMethod httpMethod = new PostMethod(url);
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                httpMethod.addParameter(entry.getKey(),entry.getValue());
            }
        }

        StringBuilder response = new StringBuilder();
        HttpClient httpClient = new HttpClient();
        executeMethod(isPretty, response, httpClient, httpMethod);

        return response.toString();
    }

    /**
     * 模拟Get请求
     *
     * @param url         请求地址
     * @param queryString 请求参数串，形如param=value&param2=value2
     * @param isPretty    返回的结果字符串是否美化
     * @return
     */
    public static String doGet(String url, String queryString, boolean isPretty) {
        if (StringUtils.isBlank(url)) {
            return "";
        }

        HttpMethod httpMethod = new GetMethod(url);

        /**
         * 添加参数
         */
        if (StringUtils.isNotBlank(queryString)) {
            httpMethod.setQueryString(queryString);
        }

        StringBuilder response = new StringBuilder();
        HttpClient httpClient = new HttpClient();
        executeMethod(isPretty, response, httpClient, httpMethod);

        return response.toString();
    }


    /**
     * 模拟Get请求
     *
     * @param url      请求地址
     * @param params   请求参数，只支持字符串格式
     * @param isPretty 返回的结果字符串是否美化
     * @return
     */
    public static String doGet(String url, Map<String, String> params, boolean isPretty) {
        if (StringUtils.isBlank(url)) {
            return "";
        }

        /**
         * 添加参数
         */
        HttpMethod httpMethod = new GetMethod(url);
        if (params != null && !params.isEmpty()) {//添加参数
            List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
            for (String key : params.keySet()) {
                NameValuePair pair = new NameValuePair(key, params.get(key));
                pairs.add(pair);
            }
            httpMethod.setQueryString(pairs.toArray(new NameValuePair[0]));
        }

        HttpClient httpClient = new HttpClient();
        StringBuilder response = new StringBuilder();
        executeMethod(isPretty, response, httpClient, httpMethod);

        return response.toString();
    }

    /**
     * 模拟http请求
     *
     * @param isPretty   返回的结果字符串是否美化
     * @param response   返回的结果字符串
     * @param httpClient
     * @param httpMethod
     */
    private static void executeMethod(boolean isPretty, StringBuilder response, HttpClient httpClient, HttpMethod httpMethod) {
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
            logger.error("模拟http请求", e);
        } finally {
            httpMethod.releaseConnection();
        }
    }
}
