package net.deniro.land.mobile.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 响应结果
 *
 * @author deniro
 *         2015/11/5
 */
@Data
public class ResponseResult {
    /**
     * 结果码；100-成功；200-失败
     */
    private String result;

    /**
     * 结果描述
     */
    private String describe;


}
