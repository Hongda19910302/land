package net.deniro.land.api.entity;

/**
 * 成功响应结果
 *
 * @author deniro
 *         2015/11/6
 */
public class SuccessResult extends ResponseResult {

    public SuccessResult() {
        super.setResult(ResultCode.SUCCESS.value());
        super.setDescribe("操作成功");
    }

    /**
     * @param desc 操作描述
     */
    public SuccessResult(String desc) {
        super.setResult(ResultCode.SUCCESS.value());
        super.setDescribe(desc);
    }
}
