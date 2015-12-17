package net.deniro.land.common.utils.ftp;

import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;
import lombok.Data;
import net.deniro.land.common.service.Constants;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.StringTokenizer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

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
     * 获取实际上传路径
     * @param userId 当前用户ID
     * @return
     */
    public String getRealPath(String userId) {
        return getBaseDir() + Constants.FTP_PATH_SPLIT + getRealDir() +
                Constants.FTP_PATH_SPLIT + userId + Constants.FTP_PATH_SPLIT + getImgDir();
    }


    /**
     * 生成FTP临时图片存放路径
     *
     * @param userId 用户ID
     * @return
     */
    public String generateTempImgPath(Integer userId) {
        if (userId == null) {
            userId = -1;
        }
        return getBaseDir() + Constants.FTP_PATH_SPLIT + getTempDir() +
                Constants.FTP_PATH_SPLIT + userId + Constants.FTP_PATH_SPLIT + getImgDir();
    }

    /**
     * 判断目录是否存在
     *
     * @param dir 目录
     * @return
     */
    public boolean isDirExist(String dir) {
        try {
            client.changeDirectory(dir);
        } catch (Exception e) {
            logger.info("目录【" + dir + "】不存在");
            return false;
        }
        return true;
    }

    /**
     * 上传文件
     *
     * @param path 文件路径
     * @param file 文件
     * @return
     */
    public boolean upload(String path, File file) {
        if (StringUtils.isBlank(path) || file == null) {
            return false;
        }

        try {
            mkDirs(path);


            while (!heartBeatThread.getClient().isConnected()) {//如果未连接，则等待1s重新获取
                Thread.sleep(1000);
            }
            client = heartBeatThread.getClient();

            client.changeDirectory(path);//切换到指定路径下
            client.upload(file, new CustomFTPDataTransferListener(file.getName()));//上传

            return true;
        } catch (Exception e) {
            logger.error("上传多个文件", e);
            return false;
        }
    }

    /**
     * 创建层级目录
     *
     * @param path
     */
    public void mkDirs(String path) {
        if (StringUtils.isBlank(path)) {
            return;
        }
        client = heartBeatThread.getClient();
        if (!client.isConnected()) {
            return;
        }

        try {
            String currentPath = client.currentDirectory();//当前路径
            client.changeDirectory(Constants.FTP_PATH_SPLIT);//切换到根目录
            StringTokenizer dirs = new StringTokenizer(path, Constants.FTP_PATH_SPLIT);
            String temp;
            while (dirs.hasMoreElements()) {
                temp = dirs.nextElement().toString();
                if (!isDirExist(temp)) {//创建并进入目录
                    logger.info("开始创建目录【" + temp + "】");
                    client.createDirectory(temp);
                    client.changeDirectory(temp);
                }
            }

            client.changeDirectory(currentPath);
        } catch (IOException e) {
            logger.error("创建层级目录", e);
            client = null;
        } catch (FTPIllegalReplyException e) {
            logger.error("创建层级目录", e);
            client = null;
        } catch (FTPException e) {
            logger.error("创建层级目录", e);
            client = null;
        } catch (Exception e) {
            logger.error("创建层级目录", e);
            client = null;
        } finally {
            close();
        }
    }


    /**
     * 声明一个执行器
     */
    private Executor executor;

    /**
     * 心跳线程
     */
    private FtpHeartBeatThread heartBeatThread;

    /**
     * 启动心跳连接
     */
    public void initHeartBeat() {
        heartBeatThread = new FtpHeartBeatThread(client, this);
        executor = Executors.newSingleThreadExecutor();
        executor.execute(heartBeatThread);
    }

    /**
     * FTP重连次数
     */
    @Deprecated
    private static AtomicInteger tryCount = new AtomicInteger(0);

    /**
     * FTP客户端初始化
     */
    @Deprecated
    public void init() {
        try {
            client = new FTPClient();
            client.setCharset(Constants.CHARSET);
            client.setType(FTPClient.TYPE_BINARY);
            client.connect(new URL(prefix + ip).getHost(), port);
            client.login(account, password);
            client.currentDirectory();
            logger.info("已连接FTP服务器");
            tryCount.set(0);
        } catch (Exception e) {
            logger.warn("FTP服务器连接失败，尝试第【" + tryCount.incrementAndGet() + "】次重连...");
            try {

                //休眠2s后，尝试重连
                Thread.sleep(2000);
                init();
                tryCount.incrementAndGet();
            } catch (InterruptedException e1) {
                logger.error("FTP客户端初始化", e1);
            }
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
