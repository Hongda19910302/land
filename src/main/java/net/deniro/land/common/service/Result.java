package net.deniro.land.common.service;

import java.io.Serializable;

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

}
