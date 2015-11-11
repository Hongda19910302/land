{
"result":"${r.result?default('')}",
"describe":"${r.describe?default('')}",
<#if tCase??>
"punisher":"${(tCase.parties)!''}",
"placeId":"${(tCase.regionId)!''}",
"placeName":"<#if tCase.findTRegion??><#if tCase.findTRegion.parentRegion??><#if tCase.findTRegion.parentRegion.parentRegion??><#if tCase.findTRegion.parentRegion.parentRegion.parentRegion??>${tCase.findTRegion.parentRegion.parentRegion.parentRegion.name}</#if>${tCase.findTRegion.parentRegion.parentRegion.name}</#if>${tCase.findTRegion.parentRegion.name}</#if>${tCase.findTRegion.name}</#if>",
"east":"${(tCase.eastTo)!''}",
"south":"${(tCase.southTo)!''}",
"west":"${(tCase.westTo)!''}",
"north":"${(tCase.northTo)!''}",
"illegaladdr":"${(tCase.illegalArea)!''}",
"xcyId":"${(tCase.inspectorId)!''}",
"xcyName":"${(tCase.findInspector.name)!''}",
"space":"${(tCase.illegalAreaSpace?string('0.00'))!'0'}",
"status":"${(tCase.status)!''}",
"casecomment":"${(tCase.remark)!''}",
"gpsFlag":"${(tCase.locateType)!''}",
"gpsX":"${(tCase.lng?string('#.000000'))!'200'}",
"gpsY":"${(tCase.lat?string('#.000000'))!'200'}",
"list": [
    <#if tVariableFieldList??>
        <#list tVariableFieldList as data>
        {
            <#assign value = (action.getCaseFieldValue(tCase.caseId,data.findDataField.tableField))/>
        "fieldKey": "${(data.fieldKey)!''}",
        "fieldName":"${(data.alias)!''}",
        "fieldValue": "<#if value??>${value}</#if>",
        "isHide":"${(data.isHide)!0}",
            <#if data.isPullDown?? && data.isPullDown==0>
            "fieldShow": "<#if value?? && value gt 0 && (data.getSelectTypeByValue(value))??>${data.getSelectTypeByValue(value).selectTypeName}</#if>",
            </#if>
        "pullDown": "${(data.isPullDown)!0}"
        }
            <#if data_has_next>,</#if>
        </#list>
    </#if>
]
</#if>
}