{
"result":"${r.result?default('')}",
"describe":"${r.describe?default('')}",
"list":[
<#if caseFlowRecordList??>
    <#list caseFlowRecordList as data>
    {
    "personStartId":"${(data.fromUser)?default('')}",
    "personEndId":"${(data.toUser)?default('')}",
    "personStart":"${(data.findFromUser.name)?default('')}",
    "personEnd":"${(data.findToUser.name)?default('')}",
    "doing":"${(data.operation)?default('')}",
    "date":"<#if data.createTime??>${(data.createTime)?string('yyyy-MM-dd HH:mm')}</#if>"
    }
        <#if data_has_next>,</#if>
    </#list>
</#if>
]
}