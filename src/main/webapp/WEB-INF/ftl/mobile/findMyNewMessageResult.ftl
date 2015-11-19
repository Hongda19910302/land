{
"result":"${r.result?default('')}",
"describe":"${r.describe?default('')}",
"rate":"${freuqency?default('')}",
<#if tMsgResult??>
"messageItem":{
"messageId":"${(tMsgResult.msgId)!''}",
"messageType":"1",
"messageIsRead":"${(tMsgResult.isRead)!''}",
"date":"${(tMsgResult.sendTime)!''}",
"title":"${(tMsgResult.ruleName)!''}",
"content":"${(tMsgResult.msgContent)!''}",
"caseId":"${(tMsgResult.getTCase().caseId)!''}",
"caseState":"1"
}
</#if>
}