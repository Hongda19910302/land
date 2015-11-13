package net.deniro.land.api.entity;

import lombok.Data;

/**
 * 图片
 *
 * @author deniro
 *         2015/11/13
 */
@Data
public class Images {
    /**
     * 文件地址
     */
    private String imageAddr;

    /**
     * 文件类型；
     * 0:拆除前/后照片
     * 1:制止通知单
     */
    private Integer imageType;
}
