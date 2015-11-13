package net.deniro.land.common.utils;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * json工具
 *
 * @author deniro
 *         2015/11/13
 */
public class JsonUtils {

    static Logger logger = Logger.getLogger(JsonUtils.class);

    /**
     * 读取json，转化为对象
     * <p>
     * 可以转换List<T>、Map<K,V>等泛型
     *
     * @param jsonStr         json字符串
     * @param collectionClass 集合类型
     * @param elementClasses  集合内部的元素类型
     * @param <T>
     * @return
     */
    public static <T> T readJson(String jsonStr, Class<?> collectionClass, Class<?>...
            elementClasses) {
        ObjectMapper mapper = new ObjectMapper();

        JavaType javaType = mapper.getTypeFactory().constructParametricType
                (collectionClass, elementClasses);

        try {
            return mapper.readValue(jsonStr, javaType);
        } catch (IOException e) {
            logger.error("读取json，转化为对象", e);
            return null;
        }
    }
}
