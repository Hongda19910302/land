package net.deniro.land.common.service;

import net.deniro.land.common.spring.mvc.ResourcePathExposer;

/**
 * 常量
 *
 * @author deniro
 *         15-4-1上午10:29
 */
public class Constants {
    /**
     * Java 换行符
     */
    public static final String NEW_LINE = System.getProperty("line.separator");
    /**
     * js 换行符
     */
    public static final String JS_NEW_LINE = "\n";
    /**
     * html 换行符
     */
    public static final String HTML_NEW_LINE = "<br>";

    /**
     * 字符集
     */
    public static final String CHARSET = "UTF-8";

    /**
     * ftp路径分隔符
     */
    public static final String FTP_PATH_SPLIT = "/";

    /**
     * 文件扩展名前缀
     */
    public static final String FILE_EXTENSION_PREFIX = ".";

    /**
     * 树型图标路径前缀
     */
    public static final String TREE_ICON_PATH_PREFIX = "/dwz/themes/default/images/dialog/";

    /**
     * 数据字典多键分隔符
     */
    public static final String DATA_TYPE_MULTI_KEY_SPLIT = "-";

    /**
     * mysql日期格式
     */
    public static final String MYSQL_DATE_FORMAT = "%Y-%m-%d";

    /**
     * FTP中的HTTP地址访问前缀ID
     */
    public static final String FTP_HTTP_URL_PREFIX_ID = "httpPrefix";

    /**
     * 展示模式
     */
    public static final String DISPLAY_MODE = "DISPLAY";

    /**
     * 对话框图片路径
     */
    public static final String DIALOG_IMG_PATH = ResourcePathExposer.getResourceRoot() +
            "/dwz/themes/default/images/dialog/";
}
