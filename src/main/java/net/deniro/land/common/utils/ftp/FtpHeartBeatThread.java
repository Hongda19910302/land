package net.deniro.land.common.utils.ftp;

import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;
import net.deniro.land.common.service.Constants;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URL;

/**
 * FTP服务器心跳线程
 *
 * @author deniro
 *         2015/12/8
 */
public class FtpHeartBeatThread implements Runnable {

    static Logger logger = Logger.getLogger(FtpHeartBeatThread.class);


    private FtpUtils ftpUtils;

    /**
     * ftp客户端
     */
    private FTPClient client;

    public FTPClient getClient() {
        return client;
    }

    public FtpHeartBeatThread(FTPClient client, FtpUtils ftpUtils) {
        this.client = client;
        this.ftpUtils = ftpUtils;
    }

    /**
     * 连接
     *
     * @throws IOException
     * @throws FTPException
     * @throws FTPIllegalReplyException
     */
    public void connect() throws IOException, FTPException, FTPIllegalReplyException {
        client = new FTPClient();
        client.setCharset(Constants.CHARSET);
        client.setType(FTPClient.TYPE_BINARY);
        client.connect(new URL(ftpUtils.getPrefix() + ftpUtils.getIp()).getHost(),
                ftpUtils.getPort());
        client.login(ftpUtils.getAccount(), ftpUtils.getPassword());
        client.currentDirectory();
        logger.info("FTP服务器连接状态：" + client.isConnected());
    }

    /**
     * FTP服务器心跳检测，如果断开连接，则进行重连
     */
    public void run() {
        try {
            while (true) {
                if (client == null || !client.isConnected() || !client.isAuthenticated()) {
                    connect();
                    Thread.sleep(1000);
                }
            }

        } catch (Exception ex) {
            logger.error("FTP服务器连接失败，尝试重连。失败原因：" + ex.getMessage());
            try {
                Thread.sleep(1000);
                run();
            } catch (InterruptedException e) {
                logger.error("FTP服务器心跳检测", e);
            }
        }
    }
}
