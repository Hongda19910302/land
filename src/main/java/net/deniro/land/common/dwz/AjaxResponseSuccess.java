package net.deniro.land.common.dwz;

/**
 * Ajax 响应（正常状态）
 *
 * @author deniro
 *         15-3-27下午5:52
 */
public class AjaxResponseSuccess extends AjaxResponse{

    public AjaxResponseSuccess(String message) {
        super(200, message);
    }
}
