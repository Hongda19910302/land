package net.deniro.land.common.service.dwz;

import com.google.common.collect.Lists;
import lombok.Data;
import net.deniro.land.common.service.Constants;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 返回结果
 *
 * @author deniro
 *         15-3-27下午5:45
 */
@Data
public abstract class Result {


    private static Logger logger = Logger.getLogger(Result.class);

    /**
     * 正常码
     */
    public static final int NORMAL_STATUS = 200;

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
    private int statusCode = NORMAL_STATUS;
    /**
     * 提示信息
     */
    private String message = "";



    /**
     * @param statusCode 状态码
     * @param message    提示信息
     */
    protected Result(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public Result() {
    }

    /**
     * @param message 提示信息
     */
    public Result(String message) {
        this.message = message;
    }

    /**
     * @param statusCode  状态码
     * @param message     提示信息
     * @param fieldErrors 校验错误列表
     */
    public Result(int statusCode, String message, List<FieldError> fieldErrors) {
        this.statusCode = statusCode;
        this.message = message;
        if (fieldErrors != null)
            setFieldErrors(fieldErrors);
    }

    /**
     * 额外信息
     */
    private Map<String, Object> extrasInfo = new HashMap();

    public Object get(String key) {
        return extrasInfo.get(key);
    }

    public void set(String key, Object value) {
        extrasInfo.put(key, value);
    }

    /**
     * 是否成功
     *
     * @return
     */
    public boolean isSuccess() {
        return this.statusCode == NORMAL_STATUS;
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
        this.message = this.message + "：" + Constants.HTML_NEW_LINE + StringUtils.join(errors.toArray(), Constants.HTML_NEW_LINE);
    }
}
