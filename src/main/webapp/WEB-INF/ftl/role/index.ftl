
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


<form id="pagerForm" method="post" action="/role/index">
    <input type="hidden" name="pageNum" value="1"/>
    <input type="hidden" name="numPerPage" value="${page.pageSize}"/>
    <input type="hidden" name="orderField" value="${param.orderField}"/>

    <input type="hidden" name="backRoleName" value="${queryParam.backRoleName}"/>
    <input type="hidden" name="companyId" value="${queryParam.companyId}"/>
    <input type="hidden" name="status" value="${queryParam.status}"/>
</form>

<#--查询条件-->
<div class="pageHeader">
    <form id="roleSearchForm" onsubmit="return navTabSearch(this);" action="/role/index"
          method="post">
        <div class="searchBar">
            <table class="searchContent">
                <tr>
                    <td>
                        角色名称：<input type="text" name="backRoleName" value="${queryParam
                    .backRoleName!""}">
                    </td>
                    <td>单位名称：</td>
                    <td>
                        <select class="combox" id="roleCompanyId" name="companyId">
                            <option value="" selected="selected">所有单位</option>

                        <#--渲染单位下拉框-->
                        <#list companys as company>
                            <option value="${company.companyId}">${company.companyName}</option>
                        </#list>

                        </select>
                    </td>

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

<#--工具栏-->
    <div class="panelBar toolBarPanel">
        <ul class="toolBar">
            <li><a class="add" href="#" target="navTab"><span>添加</span></a>
            </li>
            <li><a class="delete" href="#"
                   target="ajaxTodo" title="确定要删除吗?"><span>删除</span></a></li>
            <li><a class="edit" href="#" target="navTab"><span>修改</span></a>
            </li>
            <li class="line">line</li>
            <li><a class="icon" href="#" target="dwzExport"
                   targetType="navTab" title="确实要导出这些记录吗?"><span>导出EXCEL</span></a></li>
        </ul>
    </div>

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

<#include "include/paging.ftl">

</div>