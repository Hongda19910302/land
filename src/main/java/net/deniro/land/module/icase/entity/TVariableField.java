package net.deniro.land.module.icase.entity;

import lombok.Data;
import lombok.Setter;
import net.deniro.land.common.utils.SpringContextUtils;
import net.deniro.land.module.icase.dao.SelectTypeDao;
import net.deniro.land.module.system.entity.Company;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 字段（主要是案件字段）
 *
 * @author deniro（AnnotationHibernateCodeMarker）
 *         2015-11-4
 */
@Data
@Entity
@Table(name = "t_variable_field")
public class TVariableField implements Serializable {


    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "variable_field_id", nullable = true, length = 10)
    private Integer variableFieldId;

    /**
     * 客户端对应字段key
     */
    @Column(name = "field_key", nullable = true, length = 30)
    private String fieldKey;

    /**
     * 数据库对应表字段ID
     */
    @Column(name = "data_field_id", nullable = true, length = 10)
    private Integer dataFieldId;

    /**
     * 单位ID
     */
    @Column(name = "company_id", nullable = true, length = 10)
    private Integer companyId;

    /**
     * 字段别名
     */
    @Column(name = "alias", nullable = true, length = 30)
    private String alias;

    /**
     * 所属表类型 0：案件表
     */
    @Column(name = "table_type", nullable = true, length = 10)
    private Integer tableType;

    /**
     * 状态 0:可用 1：禁用
     */
    @Column(name = "status", nullable = true, length = 10)
    private Integer status;

    /**
     * 字段类型 0：整数 1：带小数 2：字符串 3：日期字符串
     */
    @Column(name = "type", nullable = true, length = 10)
    private Integer type;

    /**
     * 是否必填 0：必填 1：非必填
     */
    @Column(name = "is_null", nullable = true, length = 10)
    private Integer isNull;

    /**
     * 是否下拉 0：下拉1：非下拉
     */
    @Column(name = "is_pulldown", nullable = true, length = 10)
    private Integer isPullDown;

    /**
     * 是否隐藏 0：隐藏 1：显示
     */
    @Column(name = "is_hide", nullable = true, length = 10)
    private Integer isHide;

    /**
     * 是否是默认值：1是  0或者空则不是
     */
    @Column(name = "is_default", nullable = true, length = 10)
    private Integer isDefault;

    /**
     * 数据键值对 ID
     */
    @Transient
    private Integer dataTypeId;

    /**
     * 单位
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id", referencedColumnName = "company_id", nullable = true, insertable = false, updatable = false)
    private Company company;

    /**
     * 数据库对应表字段
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "data_field_id", referencedColumnName = "data_field_id", nullable = true, insertable = false, updatable = false)
    private TDataField dataField;

    /**
     * 下拉框数据
     */
    @Transient
    @Setter
    private List<TSelectType> selectTypeList;

    /**
     * 获取下拉框数据
     *
     * @return
     */
    @Transient
    public List<TSelectType> getSelectTypeList() {
        if (isPullDown != null && isPullDown == 0) {//设置下拉框数据
            if (this.getVariableFieldId() != null) {
                selectTypeList = ((SelectTypeDao) SpringContextUtils.
                        getBean("selectTypeDao")).findByVariableFieldId
                        (variableFieldId);
            }
        }
        return selectTypeList;
    }

    /**
     * 通过值，获取下拉框类型
     *
     * @param value 值
     * @return
     */
    @Deprecated
    public TSelectType getSelectTypeByValue(Integer value) {
        TSelectType selectType = new TSelectType();

        if (value == null) {
            return selectType;
        }
        if (this.getIsPullDown() != null && this.getIsPullDown().equals(0)) {

            selectType = ((SelectTypeDao) SpringContextUtils.
                    getBean("selectTypeDao")).findByFieldAndValue
                    (variableFieldId, value);

        }

        return selectType;
    }


    /**
     * 所属表
     */
    public enum BelongToTable {
        /**
         * 案件表
         */
        T_CASE(0);

        private int code;

        BelongToTable(int code) {
            this.code = code;
        }

        /**
         * 获取枚举对象
         *
         * @param code 码
         * @return
         */
        public static BelongToTable get(int code) {
            BelongToTable[] sources = BelongToTable.values();
            for (BelongToTable source : sources) {
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
     * 状态
     */
    public enum Status {
        /**
         * 可用
         */
        AVAILABLE(0),
        /**
         * 禁用
         */
        disable(1);

        private int code;

        Status(int code) {
            this.code = code;
        }

        /**
         * 获取枚举对象
         *
         * @param code 码
         * @return
         */
        public static Status get(int code) {
            Status[] sources = Status.values();
            for (Status source : sources) {
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
