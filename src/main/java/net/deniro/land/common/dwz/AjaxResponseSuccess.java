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
     * 需要重新载入内容navTabId
     */
    private String navTabId = "";

    /**
     * 如果是closeCurrent就会关闭当前tab
     */
    private String callbackType = "";

    /**
     * 菜单模块的tab前缀
     */
    public static final String MENU_TAB_PREFIX="child_menu_";

    /**
     * 设置为关闭当前tab
     */
    public void setCloseCurrent() {
        callbackType = "closeCurrent";
    }

    public AjaxResponseSuccess(String message) {
        super(200, message);
    }
}
