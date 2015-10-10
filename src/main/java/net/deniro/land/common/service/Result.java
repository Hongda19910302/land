package net.deniro.land.common.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;

/**
 * 结果
 *
 * @author deniro
 *         2015/10/10
 */
@Data
public class Result implements Serializable {
    /**
     * 是否成功
     */
    private boolean isSuccess;
    /**
     * 信息
     */
    private String message;

    /**
     * 额外信息
     */
    private Map<String, Object> extrasInfo = new HashMap();

    public Object get(String key) {
        return extrasInfo.get(key);
    }

    public void set(String key, Object value) {
        extrasInfo.put(key, value);
    }


}
