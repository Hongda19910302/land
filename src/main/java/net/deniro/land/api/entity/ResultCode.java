package net.deniro.land.api.entity;

import org.apache.commons.lang3.StringUtils;

/**
 * 结果码
 *
 * @author deniro
 *         2015/11/5
 */
public enum ResultCode {

    /**
     * 成功
     */
    SUCCESS("100"),
    /**
     * 失败
     */
    FAILURE("200");

    private String value;

    ResultCode(String value) {
        this.value = value;
    }

    /**
     * 获取枚举对象
     *
     * @param value 码
     * @return
     */
    public static ResultCode get(String value) {
        ResultCode[] codes = ResultCode.values();
        for (ResultCode code : codes) {
            if (StringUtils.equals(code.value(), value)) {
                return code;
            }
        }
        return null;
    }

    public String value() {
        return value;
    }
}
