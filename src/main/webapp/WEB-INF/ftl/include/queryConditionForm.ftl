<#--渲染查询条件表单-->
<div class="pageHeader">

    <#--表单中的元素命名规则为 “xxx_模块ID” -->
    <form id="searchForm_${moduleId}" onsubmit="return navTabSearch(this);"
          action="${url}"
          method="post">
        <div class="searchBar">
            <table class="searchContent">
                <tr>

                <#list moduleSearchCfg as cfg>
                    <td>${cfg.displayName}：</td>
                    <td>

                        <#--依照输入框的类型，进行渲染-->
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
                                <button id="resetBtn_${moduleId}" type="button">重置</button>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </form>
</div>