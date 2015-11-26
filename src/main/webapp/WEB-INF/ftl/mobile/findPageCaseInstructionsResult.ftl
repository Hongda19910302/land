{
"result":"${r.result?default('')}",
"describe":"${r.describe?default('')}",
"caseInstruction":{
<#if tCase??>
"caseId":"${(tCase.caseId)?default('')}",
"instructionState":"${tCase.instructionState?default('')?replace('\r\n','')?replace('\n','')?replace('"','&#34;')}",
"place":"<#if tCase.findRegion??><#if tCase.findRegion.parentRegion??><#if tCase.findRegion.parentRegion.parentRegion??><#if tCase.findRegion.parentRegion.parentRegion.parentRegion??>${tCase.findTRegion.parentRegion.parentRegion.parentRegion.name}</#if>${tCase.findRegion.parentRegion.parentRegion.name}</#if>${tCase.findRegion.parentRegion.name}</#if>${tCase.findRegion.name}</#if>",
"date":"<#if tCase.createTime??>${(tCase.createTime)?string('yyyy-MM-dd')}</#if>",
"punisher":"${(tCase.parties)?default('')}",
"totalpagenum":"<#if nativePage??>${nativePage.getTotalPageCount()?default('')}<#else>0</#if>",
"totalnum":<#if nativePage??>${nativePage.getTotalCount()?default('')}<#else>0</#if>,
"pageNo":${pageNo},
"instructionList":[
    <#if nativePage??>
        <#if nativePage.data??>
            <#list nativePage.data as data>
            {
            "instructionId":"${(data.instructionId)?default('')}",
            "userId":"${(data.userId)?default('')}",
            "userName":"${data.userName?default('')?replace('\r\n','')?replace('\n','')?replace('"','&#34;')}",
            "date":"<#if data.instructionDate??>${(data.instructionDate)?string('yyyy-MM-dd HH:mm:ss')}</#if>",
            "content":"${data.content?default('')?replace('\r\n','')?replace('\n','')?replace('"','&#34;')}"

            }
                <#if data_has_next>,</#if>
            </#list>
        </#if>
    </#if>
]
</#if>
}

}