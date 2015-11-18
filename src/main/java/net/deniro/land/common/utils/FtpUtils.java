package net.deniro.land.common.utils;

import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;
import lombok.Data;
import net.deniro.land.common.service.Constants;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URL;

/**
 * Ftp 工具
 *
 * @author deniro
 *         2015/11/18
 */
@Data
public class FtpUtils {

    static Logger logger = Logger.getLogger(FtpUtils.class);

    /**
     * 地址前缀
     */
    private String prefix;

    /**
     * IP地址
     */
    private String ip;
    /**
     * 端口
     */
    private Integer port;
    /**
     * 账号
     */
    private String account;
    /**
     * 密码
     */
    private String password;

    /**
     * 基本路径
     */
    private String baseDir;

    /**
     * 临时文件夹
     */
    private String tempDir;
    /**
     * 实际文件夹
     */
    private String realDir;

    /**
     * 图片文件夹
     */
    private String imgDir;


    /**
     * ftp客户端
     */
    private FTPClient client;

    /**
     * FTP客户端初始化
     */
    public void init() {
        try {

            client = new FTPClient();
            client.setCharset(Constants.CHARSET);
            client.setType(FTPClient.TYPE_BINARY);
            client.connect(new URL(prefix + ip).getHost(), port);
            client.getConnector().setConnectionTimeout(15000);
            client.login(account, password);
            logger.info("已连接FTP服务器");

        } catch (IOException e) {
            logger.error("FTP客户端初始化", e);
        } catch (FTPIllegalReplyException e) {
            logger.error("FTP客户端初始化", e);
        } catch (FTPException e) {
            logger.error("FTP客户端初始化", e);
        }
    }

    /**
     * 关闭连接
     */
    public void close() {
        if (client != null) {
            try {
                client.disconnect(false);
            } catch (IOException e) {
                logger.error("关闭连接", e);
            } catch (FTPIllegalReplyException e) {
                logger.error("关闭连接", e);
            } catch (FTPException e) {
                logger.error("关闭连接", e);
            }
        }
    }
}
