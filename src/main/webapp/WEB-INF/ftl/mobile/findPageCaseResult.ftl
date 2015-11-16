{
"result":"${r.result?default('')}",
"describe":"${r.describe?default('')}",
"totalpagenum":"<#if nativePage??>${nativePage.getTotalPageCount()?default('')}<#else>0</#if>",
"totalnum":<#if nativePage??>${nativePage.getTotalCount()?default('')}<#else>0</#if>,
"pageNo":${pageNo},
"caseList":[
<#if nativePage??>
    <#if nativePage.data??>
        <#list nativePage.data as data>
        {
        "caseId":"${(data.caseId)?default('')}",
        "caseState":"<#if data.status??><#if data.status==1>预立案<#elseif data.status==2>巡查制止<#elseif data.status==3>申请结案<#elseif data.status==4>结案审核通过<#elseif data.status==5>二次结案审核通过<#elseif data.status==6>删除案件<#elseif data.status==7>撤销案件<#else>全部案件</#if></#if>",
        "place":"<#if data.findRegion??><#if data.findRegion.parentRegion??><#if data.findRegion.parentRegion.parentRegion??><#if data.findRegion.parentRegion.parentRegion.parentRegion??>${data.findRegion.parentRegion.parentRegion.parentRegion.name}</#if>${data.findRegion.parentRegion.parentRegion.name}</#if>${data.findRegion.parentRegion.name}</#if>${data.findRegion.name}</#if>",
        "punisher":"${(data.parties)?default('')}",
        "illegalAddr":"${(data.illegalArea)?default('')}",
        "date":"<#if data.createTime??>${(data.createTime)?string('yyyy-MM-dd')}</#if>"
        }
            <#if data_has_next>,</#if>
        </#list>
    </#if>
</#if>
]

}