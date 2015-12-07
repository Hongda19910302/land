package net.deniro.land.common.dwz;

import com.google.common.collect.Lists;
import net.deniro.land.common.service.Constants;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.validation.FieldError;

import java.util.List;

/**
 * Ajax 响应
 *
 * @author deniro
 *         15-3-27下午5:45
 */
public abstract class AjaxResponse {


    private static Logger logger = Logger.getLogger(AjaxResponse.class);

    /**
     * 错误码
     */
    public static final int ERROR_STATUS = 300;

    /**
     * 校验错误列表
     */
    private List<FieldError> fieldErrors = Lists.newArrayList();
    /**
     * 状态码
     */
    private int statusCode = 200;
    /**
     * 提示信息
     */
    private String message = "";


    /**
     * @param statusCode 状态码
     * @param message    提示信息
     */
    protected AjaxResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    /**
     * @param message 提示信息
     */
    public AjaxResponse(String message) {
        this.message = message;
    }

    /**
     * @param statusCode  状态码
     * @param message     提示信息
     * @param fieldErrors 校验错误列表
     */
    public AjaxResponse(int statusCode, String message, List<FieldError> fieldErrors) {
        this.statusCode = statusCode;
        this.message = message;
        if (fieldErrors != null)
            setFieldErrors(fieldErrors);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<FieldError> getFieldErrors() {
        return fieldErrors;
    }

    /**
     * 设置错误信息列表
     *
     * @param fieldErrors 错误信息列表
     */
    public void setFieldErrors(List<FieldError> fieldErrors) {

        this.fieldErrors = fieldErrors;

        //格式化
        List<String> errors = Lists.newArrayList();
        for (int i = 0; i < fieldErrors.size(); i++) {
            FieldError fieldError = fieldErrors.get(i);
            logger.debug("fieldError：" + fieldError);
            errors.add("【" + (i + 1) + "】" + fieldError.getField() + "：" + fieldError.getDefaultMessage());
        }
        for (FieldError fieldError : fieldErrors) {

        }
        this.message = this.message + "：" + Constants.HTML_NEW_LINE + StringUtils.join(errors.toArray(), Constants.HTML_NEW_LINE);
    }
}
