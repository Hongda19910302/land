package net.deniro.land.common.service.dwz;

/**
 * 返回结果（正常状态）
 *
 * @author deniro
 *         15-3-27下午5:52
 */
public class ResultSuccess extends Result {

    public ResultSuccess(String message) {
        super(200, message);
    }
}
