package net.deniro.land.api.entity;

import lombok.Data;

/**
 * 指派参数
 *
 * @author deniro
 *         2015/11/16
 */
@Data
public class AssignParam {

    /**
     * 执行操作的用户ID
     */
    private Integer userId;

    /**
     * 案件ID
     */
    private Integer caseId;

    /**
     * 要流转给某个巡查员的ID
     */
    private Integer xcyId;

    /**
     * 流转类型；
     * 0:立案人指派
     * 1:巡查员转交
     */
    private Integer type;


    /**
     * 指派类型
     */
    public enum AssignType {
        /**
         * 立案人指派
         */
        REGISTER(0),
        /**
         * 巡查员转交
         */
        INSPECTOR(1);

        private int code;

        AssignType(int code) {
            this.code = code;
        }

        /**
         * 获取枚举对象
         *
         * @param code 来源码
         * @return
         */
        public static AssignType get(int code) {
            AssignType[] sources = AssignType.values();
            for (AssignType source : sources) {
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
