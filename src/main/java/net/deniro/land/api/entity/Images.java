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
     * 附件类型 0:照片 1：单据 2:压缩文件 3：word文件
     */
    private Integer imageType;
}
