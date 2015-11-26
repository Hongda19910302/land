{
"result":"${r.result?default('')}",
"describe":"${r.describe?default('')}",
"totalpagenum":"<#if page??>${page.getTotalPageCount()?default('')}<#else>0</#if>",
"totalnum":<#if page??>${page.getTotalCount()?default('')}<#else>0</#if>,
"pageNo":${pageNo},
"instructionList":[
<#if page??>
    <#if page.currentPageData??>
        <#list page.currentPageData as data>
        {
        "instructionId":"${(data.instructionId)?default('')}",
        "userId":"${(data.userId)?default('')}",
        "userName":"${data.userName?default('')?replace('\r\n','')?replace('\n','')?replace('"','&#34;')}",
        "date":"<#if data.instructionDate??>${(data.instructionDate)?string('yyyy-MM-dd HH:mm:ss')}</#if>",
        "content":"${data.content?default('')?replace('\r\n','')?replace('\n','')?replace('"','&#34;')}"
        <#--"place":"<#if data.findTRegion??><#if data.findTRegion.parentRegion??><#if data.findTRegion.parentRegion.parentRegion??><#if data.findTRegion.parentRegion.parentRegion.parentRegion??>${data.findTRegion.parentRegion.parentRegion.parentRegion.name}</#if>${data.findTRegion.parentRegion.parentRegion.name}</#if>${data.findTRegion.parentRegion.name}</#if>${data.findTRegion.name}</#if>",
        "date":"<#if data.createTime??>${(data.createTime)?string('yyyy-MM-dd')}</#if>",
        "punisher":"${(data.parties)?default('')}"-->
        }
            <#if data_has_next>,</#if>
        </#list>
    </#if>
</#if>
]

}
