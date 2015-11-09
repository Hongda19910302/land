{
"result":"${r.result?default('')}",
"describe":"${r.describe?default('')}",
"xcyList":[
<#if userList??>
    <#list userList as data>
    {
    "xcyId":"${(data.userId)!''}",
    "xcyAccount":"${(data.account)!''}",
    "xcyName":"${(data.name)!''}",
    "post":"${(data.position)!''}"
    }
        <#if data_has_next>,</#if>
    </#list>
</#if>
]

}