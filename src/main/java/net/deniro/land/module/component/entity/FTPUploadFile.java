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

    /**
     * 文件来源
     */
    private FileSource fileSource = FileSource.TEMP;

    /**
     * 文件来源
     */
    public enum FileSource {
        /**
         * 临时文件夹
         */
        TEMP,
        /**
         * ftp地址
         */
        FTP
    }


}
