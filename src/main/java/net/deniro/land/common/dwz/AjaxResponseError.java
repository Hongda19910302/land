package net.deniro.land.common.dwz;

import org.springframework.validation.FieldError;

import java.util.List;

/**
 * Ajax 响应（出错状态）
 *
 * @author deniro
 *         15-3-27下午5:47
 */
public class AjaxResponseError extends AjaxResponse {

    public AjaxResponseError(String message, List<FieldError> fieldErrors) {
        super(ERROR_STATUS, message, fieldErrors);
    }

    public AjaxResponseError(String message) {
        super(ERROR_STATUS, message);
    }
}
