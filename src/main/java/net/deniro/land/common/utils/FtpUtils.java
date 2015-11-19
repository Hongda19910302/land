package net.deniro.land.common.utils;

import it.sauronsoftware.ftp4j.*;
import lombok.Data;
import net.deniro.land.common.service.Constants;
import org.apache.commons.lang3.StringUtils;
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
                    client.changeDirectory(dir);
                } else {//如果不存在，则创建文件夹，并作为当前文件夹
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
     * 判断路径是否存在
     *
     * @param path 路径
     * @return
     */
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
                        return PathType.NO_EXIST;
                    }
                }
            } else {//尝试返回当前路径的上一级，如果正常，则说明是文件夹
                try {
                    client.changeDirectory(path);
                    client.changeDirectoryUp();
                    return PathType.DIRECTORY;
                } catch (Exception e) {
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
