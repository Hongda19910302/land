<#--组件ID-->
<#assign componentId=queryParam.componentId>

<#--请求URL-->
<#assign url="${actionUrl}?componentType=${queryParam.componentType}&componentId=${queryParam
.componentId}">

<#--查询表单字段配置信息-->
<#assign formFields=compPageSearch.compPageSearchFormFields>

<#--表格字段配置信息-->
<#assign tableFields=compPageSearch.compPageSearchTableFields>



<#-------------------------脚本 开始-------------------------------------------->
<script type="text/javascript">
    $(function () {
        //查询条件初始化
    <#list formFields as field>
        $("#${field.fieldName}_${componentId}").val("${queryParam[field.fieldName]!""}");
    </#list>

        //绑定重置按钮
        $("#resetBtn_${componentId}").click(function () {
            $("#searchForm_${componentId}").clearForm();
        });

    });
</script>
<#-------------------------脚本 结束--------------------------------------------->

<#-------------------------分页器表单 开始---------------------------------------->
<form id="pagerForm" method="post" action="${url}">
    <input type="hidden" name="pageNum" value="1"/>
    <input type="hidden" name="numPerPage" value="${page.pageSize}"/>
    <input type="hidden" name="orderField" value="${param.orderField}"/>
<#list formFields as field>
    <input type="hidden" name="${field.fieldName}" value="${queryParam[field.fieldName]}"/>
</#list>
</form>
<#-------------------------分页器表单 结束---------------------------------------->

<#-----------------------------渲染查询条件表单 开始----------------------------------->
<div class="pageHeader">

<#--表单中的元素命名规则为 “xxx_模块ID” -->
    <form id="searchForm_${componentId}" onsubmit="return navTabSearch(this);"
          action="${url}"
          method="post">
        <div class="searchBar">
            <table class="searchContent">
                <tr>

                <#list formFields as field>
                    <td>${field.displayName}：</td>
                    <td>

                    <#--依照输入框的类型，进行渲染-->
                        <#switch field.inputType>
                            <#case "TEXT">
                                <input type="text" name="${field.fieldName}" id="${field
                                .fieldName}_${componentId}"
                                       value="${queryParam
                                       .fieldName!""}"
                                <#break>
                            <#case "SELECT">
                                <select class="combox" name="${field.fieldName}" id="${field
                                .fieldName}_${componentId}">
                                    <option value="" selected="selected">所有</option>
                                    <#assign selectListDataSet=field.selectListDataSet>
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

        <#--渲染按钮组-->
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
                                <button id="resetBtn_${componentId}" type="button">重置
                                </button>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </form>
</div>
<#-----------------------------渲染查询条件表单 结束----------------------------------->

<div class="pageContent">

<#--todo 此处添加工具栏-->

<#------------------------------渲染表格 开始-------------------------------------->

<#--表格主键-->
<#assign tableKey="">

<#--表格容器-->
    <table class="table" width="100%" layoutH="138">
        <thead>
        <tr>

        <#list tableFields as field>
        <#--找到主键并赋值-->
            <#if field.isKey=="true">
                <#assign tableKey=field.fieldName>
            <#--渲染表格头标题-->
            <#else>
                <th width="${field.width}"
                    style="${field.style!""}">${field.displayName}</th>
            </#if>
        </#list>

        </tr>
        </thead>
        <tbody>


        <#--渲染表格内容-->
        <#list page.data as data>
        <tr rel="${data[tableKey]}">

            <#list tableFields as field>
                <#--字段值-->
                <#assign fieldValue=data[field.fieldName]>

                <#--非主键展示-->
                <#if field.isKey=="false">
                <td
                <#--添加样式-->
                    <#if field.style??>
                            style="${field.style}"
                    </#if>
                        >
                <#--数据转换后展示-->
                <#if field.transformDataSetType??>
                    <#list field.transformDataSet?keys as key>
                        <#if key=="${fieldValue}">
                        ${field.transformDataSet[key]}
                        </#if>
                    </#list>

                <#--普通展示-->
                <#else>
                ${fieldValue}
                </#if>

                </td>
                </#if>
            </#list>
        </tr>
        </#list>
        </tbody>
    </table>
<#------------------------------渲染表格 结束-------------------------------------->

<#-------------------------------分页条 开始---------------------------------------->
    <script type="text/javascript">
        $(function () {
            //对显示条数赋值
            $(".perPageNumCombox").val("${page.pageSize}");
        });
    </script>

    <div class="panelBar pageBar">
        <div class="pages">
            <span>显示</span>
            <select class="combox perPageNumCombox" name="numPerPage"
                    onchange="navTabPageBreak({numPerPage:this.value})">
                <option value="10">10</option>
                <option value="20">20</option>
                <option value="50">50</option>
                <option value="100">100</option>
                <option value="200">200</option>
            </select>
            <span>条，共 ${page.totalCount}  条</span>
        </div>
        <div class="pagination" targetType="navTab" totalCount="${page.totalCount}"
             numPerPage="${page.pageSize}" currentPage="${page.start / page.pageSize + 1}"
    </div>
</div>
<#-------------------------------分页条 结束---------------------------------------->