package net.deniro.land.module.icase.service;

import net.deniro.land.common.utils.ftp.FtpUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.List;

/**
 * 上传文件至FTP服务器
 *
 * @author deniro
 *         2015/12/17
 */
public class FTPUploadService implements Runnable {

    static Logger logger = Logger.getLogger(FTPUploadService.class);

    private FtpUtils ftpUtils;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 待上传的文件列表
     */
    private List<File> fileList;

    /**
     * @param userId   用户ID
     * @param fileList 待上传的文件列表
     */
    public FTPUploadService(String userId, List<File> fileList, FtpUtils ftpUtils) {
        this.userId = userId;
        this.fileList = fileList;
        this.ftpUtils = ftpUtils;
    }

    public void run() {
        try {
            for (File file : fileList) {
                boolean isOk=ftpUtils.upload(ftpUtils.getRealPath(userId), file);
                logger.info("文件【"+file.getName()+"】上传结果："+isOk);
            }
        } catch (Exception e) {
            logger.error("上传文件至FTP服务器", e);
        }

    }
}
