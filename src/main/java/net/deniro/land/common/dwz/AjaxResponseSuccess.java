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


    public AjaxResponseSuccess(String message) {
        super(200, message);
    }
}
