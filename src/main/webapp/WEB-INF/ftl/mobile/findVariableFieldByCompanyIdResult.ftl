{
"result":"${r.result?default('')}",
"describe":"${r.describe?default('')}",
"list":[
<#if tVariableFieldList??>
    <#list tVariableFieldList as data>
    {
    "fieldKey":"${(data.fieldKey)!''}",
    "fieldName":"${(data.alias)!''}",
    "isRequired":${(data.isNull)!0},
    "pullDown":${(data.isPullDown)!0},
    "fieldType":${(data.type)!0},
    "pullDownList":[
        <#if data.isPullDown==0>
            <#if data.getSelectTypeList()??>
                <#list data.getSelectTypeList() as selectType>{
                "key":"${(selectType.selectTypeName)!''}",
                    <#if selectType.dataType.dataTypeValue??>"value":"${(selectType.dataType.dataTypeValue)}"</#if>
                }
                    <#if selectType_has_next>,</#if>
                </#list>
            </#if>
        </#if>
    ],
    "isHide":${(data.isHide)!0}
    }
        <#if data_has_next>,</#if>
    </#list>
</#if>
]
}
