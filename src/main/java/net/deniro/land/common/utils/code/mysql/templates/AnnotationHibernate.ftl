package ${packagePath};

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

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
                <#assign fieldLength=field.numbericPrecision>
                <#assign fieldType="Integer">
                <#break >
        </#switch>
    @Column(name = "${field.name}", nullable = true, length = ${fieldLength})
    private ${fieldType} ${field.className};
    </#list>
}
