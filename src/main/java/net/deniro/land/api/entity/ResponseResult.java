package net.deniro.land.api.entity;

import lombok.Data;

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
