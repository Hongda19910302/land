package net.deniro.land.common.service.dwz;

import org.springframework.validation.FieldError;

import java.util.List;

/**
 * 返回结果（出错状态）
 *
 * @author deniro
 *         15-3-27下午5:47
 */
public class ResultError extends Result {

    public ResultError() {
        super(ERROR_STATUS, "系统异常！");
    }

    public ResultError(String message, List<FieldError> fieldErrors) {
        super(ERROR_STATUS, message, fieldErrors);
    }

    public ResultError(String message) {
        super(ERROR_STATUS, message);
    }
}
