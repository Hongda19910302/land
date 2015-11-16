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
        "place":"<#if data.findTRegion??><#if data.findTRegion.parentRegion??><#if data.findTRegion.parentRegion.parentRegion??><#if data.findTRegion.parentRegion.parentRegion.parentRegion??>${data.findTRegion.parentRegion.parentRegion.parentRegion.name}</#if>${data.findTRegion.parentRegion.parentRegion.name}</#if>${data.findTRegion.parentRegion.name}</#if>${data.findTRegion.name}</#if>",
        "date":"<#if data.createTime??>${(data.createTime)?string('yyyy-MM-dd')}</#if>",
        "punisher":"${(data.parties)?default('')}"
        }
            <#if data_has_next>,</#if>
        </#list>
    </#if>
</#if>
]

}