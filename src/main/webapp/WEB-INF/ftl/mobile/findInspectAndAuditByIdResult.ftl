{
"result":"${r.result?default('')}",
"describe":"${r.describe?default('')}",
"caseState":"<#if tCase.status??><#if tCase.status==1>预立案<#elseif tCase.status==2>巡查制止<#elseif tCase.status==3>申请结案<#elseif tCase.status==4>结案审核通过<#elseif tCase.status==5>二次结案审核通过<#elseif tCase.status==6>删除案件<#elseif tCase.status==7>撤销案件<#else>全部案件</#if></#if>",
"inspectList":[
<#if tCase??>
{
"inspectResult":"${(tCase.surveyResult)!''}",
"inspectType":"新建案件巡查",
"createDt":"${(tCase.createTime)?string('yyyy-MM-dd')}",
"remark":"${(tCase.remark)?default('')}",
"picList": [
    <#if tCase.getAttachmentList()??>
        <#list tCase.getAttachmentList() as data1>
        {
        "picId": "${data1.attachmentId?default('')}",
        "imageAddr": "${data1.addr?default('')}",
        "imageType": "${data1.attachmentType?default('')}"
        }
            <#if data1_has_next>,</#if>
        </#list>
    </#if>
]
}
</#if>
<#if inspectList??>
    <#list inspectList as data>
    ,{
    "inspectResult":"${(data.inspectResult)!''}",
    "inspectType":"第${data.inspectNo?default(0)}次巡查",
    "createDt":"<#if data.createTime??>${(data.createTime)?string('yyyy-MM-dd')}</#if>",
    "remark":"${(data.remark)?default('')}",
    "picList": [
        <#if data.getAttachmentList()??>
            <#list data.getAttachmentList() as data2>
            {
            "picId": "${data2.attachmentId?default('')}",
            "imageAddr": "${data2.addr?default('')}",
            "imageType": "${data2.attachmentType?default('')}"
            }
            </#list>
        </#if>
    ]
    }
    </#list>
</#if>
<#if caseAuditList??>
    <#list caseAuditList as data3>
    ,{
    "inspectResult":"",
    "inspectType":"第${data3.auditNo?default(0)}次结案审核",
    "createDt":"<#if data3.auditTime??>${(data3.auditTime)?string('yyyy-MM-dd')}</#if>",
    "remark":"${(data3.remark)?default('')}",
    "picList": [
        <#if data3.getAttachmentList()??>
            <#list data3.getAttachmentList() as data4>
            {
            "picId": "${data4.attachmentId?default('')}",
            "imageAddr": "${data4.addr?default('')}",
            "imageType": "${data4.attachmentType?default('')}"
            }
                <#if data4_has_next>,</#if>
            </#list>
        </#if>
    ]
    }
    </#list>
</#if>
]

}