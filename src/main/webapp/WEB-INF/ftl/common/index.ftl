<#--模块ID-->
<#assign moduleId=queryParam.moduleId>

<#--请求URL-->
<#assign url="${actionUrl}?moduleId=${moduleId}">


<script type="text/javascript">
    $(function () {
        //查询条件初始化
    <#list moduleSearchCfg as cfg>
        $("#${cfg.fieldName}_${moduleId}").val("${queryParam[cfg.fieldName]!""}");
    </#list>

        //绑定重置按钮
        $("#resetBtn_${moduleId}").click(function () {
            $("#searchForm_${moduleId}").clearForm();
        });

    });
</script>

<#include "include/pagerForm.ftl">

<#--查询条件表单-->
<div class="pageHeader">
    <form id="searchForm_${moduleId}" onsubmit="return navTabSearch(this);"
          action="${url}"
          method="post">
        <div class="searchBar">
            <table class="searchContent">
                <tr>
                <#list moduleSearchCfg as cfg>
                    <td>${cfg.displayName}：</td>
                    <td>
                        <#switch cfg.inputType>
                            <#case "TEXT">
                                <input type="text" name="${cfg.fieldName}" id="${cfg
                                .fieldName}_${moduleId}"
                                       value="${queryParam
                                       .fieldName!""}"
                                <#break>
                            <#case "SELECT">
                                <select class="combox" name="${cfg.fieldName}" id="${cfg
                                .fieldName}_${moduleId}">
                                    <option value="" selected="selected">所有</option>
                                    <#assign selectListDataSet=cfg.selectListDataSet>
                                    <#if selectListDataSet?exists>
                                        <#list selectListDataSet?keys as key>
                                            <option
                                                    value="${key}">${selectListDataSet[key]}</option>
                                        </#list>
                                    </#if>
                                </select>
                                <#break>
                        </#switch>
                    </td>
                </#list>
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
                                <button id="resetBtn_${moduleId}" type="button">重置</button>
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
            <th width="80" style="text-align:center" >状态</th>
        </tr>
        </thead>
        <tbody>

        <#--渲染表格内容-->
        <#list page.data as data>
        <tr rel="${data.backRoleId}">
            <td>${data.backRoleName}</td>
            <td>${data.comment}</td>
            <td>${data.companyName}</td>
            <td >

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