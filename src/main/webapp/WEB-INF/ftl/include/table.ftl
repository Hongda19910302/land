<#--表格容器-->
<table class="table" width="100%" layoutH="138">
    <thead>
    <tr>

        <th width="150">角色名称</th>
        <th width="150">角色描述</th>
        <th width="150">单位名称</th>
        <th width="80" style="text-align:center">状态</th>
    </tr>
    </thead>
    <tbody>

    <#--渲染表格内容-->
    <#list page.data as data>
    <tr rel="${data.backRoleId}">
        <td>${data.backRoleName}</td>
        <td>${data.comment}</td>
        <td>${data.companyName}</td>
        <td>

            <#switch data.status>
                <#case 0>
                    正常
                    <#break>
                <#case 1>
                    禁用
                    <#break>
                <#case 2>
                    删除
                    <#break>
            </#switch>
        </td>
    </tr>
    </#list>
    </tbody>
</table>