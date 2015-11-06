package net.deniro.land.api.entity;

/**
 * 失败响应结果
 *
 * @author deniro
 *         2015/11/6
 */
public class FailureResult extends ResponseResult{

    public FailureResult() {
        super.setDescribe("操作失败");
        super.setResult(ResultCode.FAILURE.value());
    }

    /**
     * @param desc 操作描述
     */
    public FailureResult(String desc) {
        super.setResult(ResultCode.FAILURE.value());
        super.setDescribe(desc);
    }
}
