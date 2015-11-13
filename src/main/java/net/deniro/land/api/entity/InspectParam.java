package net.deniro.land.api.entity;

import lombok.Data;

/**
 * 案件巡查参数
 *
 * @author deniro
 *         2015/11/13
 */
@Data
public class InspectParam {
    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 案件ID
     */
    private Integer caseId;

    /**
     * 巡查结果；
     * 0：违规搭建
     * 1：制止不住
     * 2：制止到位
     * 3：未拆除
     * 4：已拆除
     * 5：已审批
     * 6：已批复
     * 7：复耕
     * 8：查处移送
     * 9：房屋维修
     */
    private Integer inspectResult;

    /**
     * 案件状态；
     * 0:未结案
     * 1:申请结案
     */
    private Integer caseStatus;

    /**
     * 备注
     */
    private String remark;

    /**
     * 照片；json字符串，格式见 net.deniro.land.api.entity.Images
     */
    private String images;

    /**
     * 案件状态
     */
    public enum CaseStatus {
        /**
         * 未结案
         */
        NO_CLOSE(0),
        /**
         * 申请结案
         */
        APPLY(1);

        private int code;

        CaseStatus(int code) {
            this.code = code;
        }

        /**
         * 获取枚举对象
         *
         * @param code 码
         * @return
         */
        public static CaseStatus get(int code) {
            CaseStatus[] sources = CaseStatus.values();
            for (CaseStatus source : sources) {
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


}
