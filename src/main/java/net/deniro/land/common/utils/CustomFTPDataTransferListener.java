package net.deniro.land.common.utils;

import it.sauronsoftware.ftp4j.FTPDataTransferListener;
import org.apache.log4j.Logger;

/**
 * 自定义FTP数据传输监听器
 *
 * @author deniro
 *         2015/12/8
 */
public class CustomFTPDataTransferListener implements FTPDataTransferListener {

    static Logger logger = Logger.getLogger(CustomFTPDataTransferListener.class);

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * @param fileName
     */
    public CustomFTPDataTransferListener(String fileName) {
        this.fileName = fileName;
    }

    public void started() {
        logger.info("【" + fileName + "】传输开始...");
    }

    public void transferred(int i) {
        logger.info("【" + fileName + "】已传输" + i + "个字节");
    }

    public void completed() {
        logger.info("【" + fileName + "】传输完成");
    }

    public void aborted() {
        logger.info("【" + fileName + "】传输被取消");
    }

    public void failed() {
        logger.info("【" + fileName + "】传输失败");
    }
}
