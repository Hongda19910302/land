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
        "instructionState":"${(data.instructionState)?default('0')}",
        "place":"<#if data.findRegion??><#if data.findRegion.parentRegion??><#if data.findRegion.parentRegion.parentRegion??><#if data.findRegion.parentRegion.parentRegion.parentRegion??>${data.findRegion.parentRegion.parentRegion.parentRegion.name}</#if>${data.findRegion.parentRegion.parentRegion.name}</#if>${data.findRegion.parentRegion.name}</#if>${data.findRegion.name}</#if>",
        "date":"<#if data.createTime??>${(data.createTime)?string('yyyy-MM-dd')}</#if>",
        "punisher":"${(data.parties)?default('')}"
        }
            <#if data_has_next>,</#if>
        </#list>
    </#if>
</#if>
]

}