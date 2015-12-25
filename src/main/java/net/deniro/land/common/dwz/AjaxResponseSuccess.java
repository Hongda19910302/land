package net.deniro.land.common.dwz;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Ajax 响应（正常状态）
 *
 * @author deniro
 *         15-3-27下午5:52
 */
@Data
public class AjaxResponseSuccess extends AjaxResponse {


    /**
     * 需要重新载入内容navTabIds，多个以逗号分隔
     */
    private String navTabIds = "";

    /**
     * navTabIds分隔符
     */
    public static final String NAB_TAB_ID_SPLIT = ",";

    /**
     * 如果是closeCurrent就会关闭当前tab
     */
    private String callbackType = "";

    /**
     * 菜单模块的tab前缀
     */
    public static final String MENU_TAB_PREFIX = "child_menu_";

    /**
     * 设置关闭当前tab
     */
    public void setCloseCurrent() {
        callbackType = "closeCurrent";
    }

    /**
     * 设置关闭当前对话框
     */
    public void setCloseCurrentDialog(){
        callbackType="closeCurrentDialog";
    }

    public AjaxResponseSuccess(String message) {
        super(200, message);
    }
}
