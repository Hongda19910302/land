{
"result":"${r.result?default('')}",
"describe":"${r.describe?default('')}"
<#if page??>
,
"totalpagenum":"<#if page??>${page.getTotalPageCount()?default('')}<#else>0</#if>",
"totalnum":<#if page??>${page.getTotalCount()?default('')}<#else>0</#if>,
"pageNo":${pageNo},
"list":
[<#list page.data as data>
{
"messageId":"${(data.msgId)?default('')}",
"messageType":"1",
"messageIsRead":"${(data.isRead)!''}",
"date":"${(data.createTime)!''}",
"title":"${data.ruleName?default('')?replace('\r\n','')?replace('\n','')?replace('"','&#34;')}",
"content":"${data.msgContent?default('')?replace('\r\n','')?replace('\n','')?replace('"','&#34;')}",
"caseId":"${(data.caseId)!''}",

"caseState":"1"
}
    <#if data_has_next>,</#if>
</#list>
]
</#if>
}