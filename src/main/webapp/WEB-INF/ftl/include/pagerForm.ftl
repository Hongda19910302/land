<#--分页器表单-->
<form id="pagerForm" method="post" action="${url}">
    <input type="hidden" name="pageNum" value="1"/>
    <input type="hidden" name="numPerPage" value="${page.pageSize}"/>
    <input type="hidden" name="orderField" value="${param.orderField}"/>

    <#list moduleSearchCfg as cfg>
        <input type="hidden" name="${cfg.fieldName}" value="${queryParam[cfg.fieldName]}"/>
    </#list>
</form>