<#--分页器表单-->
<form id="pagerForm" method="post" action="/role/index">
    <input type="hidden" name="pageNum" value="1"/>
    <input type="hidden" name="numPerPage" value="${page.pageSize}"/>
    <input type="hidden" name="orderField" value="${param.orderField}"/>

    <input type="hidden" name="backRoleName" value="${queryParam.backRoleName}"/>
    <input type="hidden" name="companyId" value="${queryParam.companyId}"/>
    <input type="hidden" name="status" value="${queryParam.status}"/>
</form>