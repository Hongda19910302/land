{
"result":"${r.result?default('')}",
"describe":"${r.describe?default('')}",
"list":[
<#if departmentList??>
    <#list departmentList as data>
    {
    "department":{
    "departmentId":"${(data.departmentId)!'0'}",
    "departmentName": "${(data.name)!''}"
    },
    "xcy": {
    "xcyId": "",
    "xcyAccount":"",
    "xcyName": "",
    "post":""
    }
    }
        <#if data_has_next>,</#if>
    </#list>
,
</#if>
<#if userList??>
    <#list userList as user>
    {
    "department":{
    "departmentId":"",
    "departmentName": ""
    },
    "xcy": {
    "xcyId":"${(user.userId)!'0'}",
    "xcyAccount":"${(user.account)!''}",
    "xcyName":"${(user.name)!''}",
    "post":"${(user.position)!''}"
    }
    }
        <#if user_has_next>,</#if>
    </#list>
</#if>
]

}