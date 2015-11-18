package net.deniro.land.common.utils;

import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;
import lombok.Data;
import net.deniro.land.common.service.Constants;
import org.apache.log4j.Logger;

import java.io.IOException;

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
     * 地址
     */
    private String url;
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
            client.connect(url, port);
            client.login(account, password);

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
