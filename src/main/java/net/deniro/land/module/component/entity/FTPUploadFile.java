package net.deniro.land.module.component.entity;

import lombok.Data;

/**
 * 待上传文件
 *
 * @author deniro
 *         2015/12/17
 */
@Data
public class FTPUploadFile {

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件路径
     */
    private String filePath;

}
