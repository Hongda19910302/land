<script type="text/javascript">
    $(function () {
        //对显示条数赋值
        $(".perPageNumCombox").val("${page.pageSize}");

        //查询条件初始化
        $("#roleCompanyId").val("${queryParam.companyId!""}");
        $("#roleStatus").val("${queryParam.status!""}");


        //绑定重置按钮
        $("#roleResetBtn").click(function () {
            $("#roleSearchForm").clearForm();
        });

    });
</script>

<#include "include/pagerForm.ftl">

<#--查询条件-->
<div class="pageHeader">
    <form id="roleSearchForm" onsubmit="return navTabSearch(this);" action="/role/index"
          method="post">
        <div class="searchBar">
            <table class="searchContent">
                <tr>
                <#--渲染查询条件表单-->
                <#list moduleSearchCfg as cfg>
                    <td>${cfg.displayName}：</td>
                    <td>
                    <input type="text" name="${cfg.fieldName}" value="${queryParam
                    .fieldName!""}"
                    </td>

                    <#--<td>-->
                    <#--<select class="combox" name="${cfg.fieldName}">-->
                        <#--<option value="" selected="selected">所有</option>-->

                        <#--<#assign selectDatas=cfg.selectDataListObjectName>-->
                    <#--&lt;#&ndash;渲染单位下拉框&ndash;&gt;-->
                        <#--<#if selectDatas?exists>-->
                            <#--<#list selectDatas as data>-->
                                <#--<option value="${data.value}">${data.name}</option>-->
                            <#--</#list>-->
                        <#--</#if>-->
                    <#--</select>-->
                    <#--</td>-->




                </#list>

                    <td>状态：</td>
                    <td>
                        <select class="combox" id="roleStatus" name="status">
                            <option value="" selected="selected">所有状态</option>
                            <option value="0">正常</option>
                            <option value="1">禁用</option>
                        </select>
                    </td>
                </tr>
            </table>
            <div class="subBar">
                <ul>
                    <li>
                        <div class="button">
                            <div class="buttonContent">
                                <button type="submit">检索</button>
                            </div>
                        </div>
                        <div class="button">
                            <div class="buttonContent">
                                <button id="roleResetBtn" type="button">重置</button>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </form>
</div>

<div class="pageContent">

<#include "include/toolBar.ftl">

<#--表格容器-->
    <table class="table" width="100%" layoutH="138">
        <thead>
        <tr>
            <th width="150">角色名称</th>
            <th width="150">角色描述</th>
            <th width="150">单位名称</th>
            <th width="80" align="center">状态</th>
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

<#include "include/pagingBar.ftl">

</div>