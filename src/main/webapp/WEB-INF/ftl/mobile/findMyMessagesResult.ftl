{
"result":"${r.result?default('')}",
"describe":"${r.describe?default('')}"
<#if page??>
,
"totalnum":${page.totalSize.toString()},
"totalpagenum":${page.totalPageSize.toString()},
"list":
[<#list page.currentPageData as data>
{
"messageId":${data.msgId.toString()!''},
"messageType":"1",
"messageIsRead":"${(data.isRead)!''}",
"date":"${(data.createTime)!''}",
"title":"${data.ruleName?default('')?replace('\r\n','')?replace('\n','')?replace('"','&#34;')}",
"content":"${data.msgContent?default('')?replace('\r\n','')?replace('\n','')?replace('"','&#34;')}",
"caseId":"${(data.getTCase().caseId)!''}",

"caseState":"1"
}
    <#if data_has_next>,</#if>
</#list>
]
</#if>
}