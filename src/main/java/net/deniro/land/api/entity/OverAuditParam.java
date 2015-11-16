package net.deniro.land.api.entity;

import lombok.Data;

/**
 * 结案审核参数
 *
 * @author deniro
 *         2015/11/16
 */
@Data
public class OverAuditParam {

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 案件ID
     */
    private Integer caseId;

    /**
     * 审核结果；
     * 0:审批通过
     * 1:审批不通过
     */
    private Integer caseStatus;

    /**
     * 审核结果
     */
    public enum AuditResult {
        /**
         * 审批通过
         */
        PASS(0),
        /**
         * 审批不通过
         */
        NO_PASS(1);

        private int code;

        AuditResult(int code) {
            this.code = code;
        }

        /**
         * 获取枚举对象
         *
         * @param code 来源码
         * @return
         */
        public static AuditResult get(int code) {
            AuditResult[] sources = AuditResult.values();
            for (AuditResult source : sources) {
                if (source.code() == code) {
                    return source;
                }
            }
            return null;
        }

        public int code() {
            return code;
        }
    }

    /**
     * 核实类型；
     * 1：一级审核
     * 2：二级审核
     */
    private Integer checkType;

    /**
     * 核实类型
     */
    public enum CheckType {
        /**
         * 一级审核
         */
        FIRST(1),
        /**
         * 二级审核
         */
        SECOND(2),
        /**
         * 未知
         */
        UNKNOWN(-1);

        private int code;

        CheckType(int code) {
            this.code = code;
        }

        /**
         * 获取枚举对象
         *
         * @param code 来源码
         * @return
         */
        public static AuditResult get(int code) {
            AuditResult[] sources = AuditResult.values();
            for (AuditResult source : sources) {
                if (source.code() == code) {
                    return source;
                }
            }
            return null;
        }

        public int code() {
            return code;
        }
    }

    /**
     * 备注
     */
    private String remark;

    /**
     * 照片；json字符串，格式见 net.deniro.land.api.entity.Images
     */
    private String images;
}
