package net.deniro.land.common.utils;

import it.sauronsoftware.ftp4j.*;
import lombok.Data;
import net.deniro.land.common.service.Constants;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.StringTokenizer;
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
     * 路径类型
     */
    public enum PathType {
        /**
         * 不存在
         */
        NO_EXIST,
        /**
         * 文件
         */
        FILE,
        /**
         * 文件夹
         */
        DIRECTORY;

    }

    /**
     * 创建路径中包含的多个文件夹
     *
     * @param path
     */
    @Deprecated
    public void createDirs(String path) {
        if (StringUtils.isBlank(path)) {
            return;
        }

        try {
            String[] dirs = path.split(Constants.FTP_PATH_SPLIT);
            for (int i = 0; i < dirs.length; i++) {
                String dir = dirs[i];
                if (StringUtils.isBlank(dir)) {
                    continue;
                }
                if (isExist(dir)) {//如果存在，则进入文件夹
                    logger.info("路径【" + dir + "】存在");
                    client.changeDirectory(dir);
                } else {//如果不存在，则创建文件夹，并作为当前文件夹
                    logger.info("路径【" + dir + "】不存在");
                    client.createDirectory(dir);
                    client.changeDirectory(dir);
                }
            }

            //重置当前路径
            client.changeDirectory(Constants.FTP_PATH_SPLIT);
        } catch (IOException e) {
            logger.error("创建路径中包含的多个文件夹", e);
        } catch (FTPIllegalReplyException e) {
            logger.error("创建路径中包含的多个文件夹", e);
        } catch (FTPException e) {
            logger.error("创建路径中包含的多个文件夹", e);
        }
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
     * 创建层级目录
     *
     * @param path
     */
    public void mkDirs(String path) {
        if (StringUtils.isBlank(path)) {
            return;
        }

        try {
            //ftp连接不稳定，因此每次使用时直接重连
            init();

            String currentPath = client.currentDirectory();//当前路径
            client.changeDirectory(Constants.FTP_PATH_SPLIT);//切换到根目录
            StringTokenizer dirs = new StringTokenizer(path, Constants.FTP_PATH_SPLIT);
            String temp;
            while (dirs.hasMoreElements()) {
                temp = dirs.nextElement().toString();
                if (!isDirExist(temp)) {//创建并进入目录
                    logger.info("开始创建目录【" + temp+"】");
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
     * 判断路径是否存在
     *
     * @param path 路径
     * @return
     */
    @Deprecated
    public boolean isExist(String path) {
        PathType pathType = getPathType(path);
        if (pathType == PathType.NO_EXIST) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 返回路径类型
     *
     * @param path 路径
     * @return
     */
    @Deprecated
    public PathType getPathType(String path) {

        PathType pathType = PathType.NO_EXIST;

        try {
            FTPFile[] files = client.list(path);

            if (files.length > 1) {//文件夹
                return PathType.DIRECTORY;
            } else if (files.length == 1) {
                FTPFile file = files[0];
                if (file.getType() == FTPFile.TYPE_DIRECTORY) {//文件夹
                    return PathType.DIRECTORY;
                } else {//可能是文件，需要进一步判断
                    try {//根据大小，判断是否是文件夹，文件夹长度为1
                        int length = client.list(path + "/" + file.getName()).length;
                        if (length == 1) {//文件夹
                            return PathType.DIRECTORY;
                        } else {//文件
                            return PathType.FILE;
                        }
                    } catch (Exception e) {
                        logger.error("【根据大小，判断是否是文件夹，文件夹长度为1】异常");
                        return PathType.NO_EXIST;
                    }
                }
            } else {//尝试返回当前路径的上一级，如果正常，则说明是文件夹
                try {
                    client.changeDirectory(path);
                    client.changeDirectoryUp();
                    return PathType.DIRECTORY;
                } catch (Exception e) {
                    logger.error("【尝试返回当前路径的上一级】异常");
                    return pathType;
                }
            }
        } catch (IOException e) {
            logger.error("返回路径类型", e);
            return pathType;
        } catch (FTPIllegalReplyException e) {
            logger.error("返回路径类型", e);
            return pathType;
        } catch (FTPDataTransferException e) {
            logger.error("返回路径类型", e);
            return pathType;
        } catch (FTPAbortedException e) {
            logger.error("返回路径类型", e);
            return pathType;
        } catch (FTPListParseException e) {
            logger.error("返回路径类型", e);
            return pathType;
        } catch (FTPException e) {
            logger.error("返回路径类型", e);
            return pathType;
        }
    }

    /**
     * FTP重连次数
     */
    private static AtomicInteger tryCount = new AtomicInteger(0);

    /**
     * FTP客户端初始化
     */
    public void init() {
        try {
            client = new FTPClient();
            client.setCharset(Constants.CHARSET);
            client.setType(FTPClient.TYPE_BINARY);
            client.connect(new URL(prefix + ip).getHost(), port);
//            client.getConnector().setConnectionTimeout(15000);
            client.login(account, password);
            client.currentDirectory();
            logger.info("已连接FTP服务器");
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
