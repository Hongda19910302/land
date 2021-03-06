package ${packagePath};

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* ${tableDefinition.comment}
*
* @author deniro（AnnotationHibernateCodeMarker）
*         ${markDate?date}
*/
@Data
@Entity
@Table(name = "${tableDefinition.name}")
public class ${className} implements Serializable {

    <#list tableDefinition.fieldDefinitions as field>

    /**
    * ${field.comment}
    */
    <#--字段长度值-->
    <#assign fieldLength=field.characterMaximumLength>
    <#--字段类型-->
    <#assign fieldType="String">
    <#if field.columnKey=="PRI">
    <#--主键-->
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    </#if>
    <#--如果是数值类型，特殊处理-->
        <#switch field.dataType>
            <#case "int">
                <#assign columnType=field.columnType>
                <#assign fieldLength=columnType?remove_beginning("int(")?remove_ending(")")>
                <#assign fieldType="Integer">
                <#break >
            <#case "datetime">
                 <#assign fieldType="Date">
                <#break>
            <#case "float">
                <#assign fieldType="Float">
                <#break>
            <#case "decimal">
                <#assign fieldType="BigDecimal">
                <#break>

        </#switch>
    @Column(name = "${field.name}", nullable = true<#if field.dataType!="datetime">,length = ${fieldLength}</#if>)
    private ${fieldType} ${field.className};
    </#list>
}
