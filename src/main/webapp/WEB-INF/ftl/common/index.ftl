<#--模块ID-->
<#assign moduleId=queryParam.moduleId>

<#--请求URL-->
<#assign url="${actionUrl}?moduleId=${moduleId}">


<#include "include/js.ftl">

<#include "include/pagerForm.ftl">
<#include "include/queryConditionForm.ftl">


<div class="pageContent">
<#include "include/toolBar.ftl">
<#include "include/table.ftl">
<#include "include/pagingBar.ftl">
</div>