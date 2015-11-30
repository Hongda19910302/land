<#--分页查询组件-->

<#--组件ID-->
<#assign componentId=queryParam.componentId>

<#--请求URL-->
<#assign url="${actionUrl}?componentType=${queryParam.componentType}&componentId=${queryParam
.componentId}">

<#--查询表单字段配置信息（正常）-->
<#assign formFields=compPageSearch.normalFormFields>

<#--查询表单字段配置信息（隐藏）-->
<#assign hiddenFormFields=compPageSearch.hiddenFormFields>

<#--表格字段配置信息-->
<#assign tableFields=compPageSearch.compPageSearchTableFields>



<#-------------------------脚本 开始-------------------------------------------->
<script type="text/javascript">
    $(function () {

    <#--处理隐藏的表单字段-->
    <#list hiddenFormFields as field>
        $("#${field.fieldName}_${componentId}").val("${queryParam[field.fieldName]!""}");
    </#list>


        //初始化 查询条件
    <#list formFields as field>
        $("#${field.fieldName}_${componentId}").val("${queryParam[field.fieldName]!""}");

        <#switch field.inputType>
            <#case "BEGIN_END_DATE"><#--起止日期选择框参数初始化-->
                $("#${field.fieldName}Begin_${componentId}").val
                ("${queryParam[field.fieldName+"Begin"]!""}");
                $("#${field.fieldName}End_${componentId}").val
                ("${queryParam[field.fieldName+"End"]!""}");
                <#break>
            <#case "REGION"><#--区域选择框参数初始化-->
                $("#${field.fieldName}Id_${componentId}").val
                ("${queryParam[field.fieldName+"Id"]!""}");
                $("#${field.fieldName}Name_${componentId}").val
                ("${queryParam[field.fieldName+"Name"]!""}");
                <#break>
        </#switch>

    </#list>

        //初始化 单位或部门选择组件参数
    <#if compPageSearch.isLookupCompanyDepartment=="true">
        $("#companyId_${componentId}").val("${queryParam["companyId"]!""}");
        $("#companyName_${componentId}").val("${queryParam["companyName"]!""}");
        $("#departmentId_${componentId}").val("${queryParam["departmentId"]!""}");
        $("#departmentName_${componentId}").val("${queryParam["departmentName"]!""}");
    </#if>

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

<#--处理隐藏的表单字段-->
<#list hiddenFormFields as field>
    <input type="hidden" name="${field.fieldName}" value="${queryParam[field.fieldName]}"/>
</#list>

<#list formFields as field>
    <input type="hidden" name="${field.fieldName}" value="${queryParam[field.fieldName]}"/>

    <#switch field.inputType>
        <#case "BEGIN_END_DATE"><#--保存起止日期选择框参数-->
            <input type="hidden" name="${field.fieldName}Begin"
                   value="${queryParam[field.fieldName+"Begin"]!""}"/>
            <input type="hidden" name="${field.fieldName}End"
                   value="${queryParam[field.fieldName+"End"]!""}"/>
            <#break>
        <#case "REGION">
            <input type="hidden" name="${field.fieldName}Id"
                   value="${queryParam[field.fieldName+"Id"]!""}"/>
            <input type="hidden" name="${field.fieldName}Name"
                   value="${queryParam[field.fieldName+"Name"]!""}"/>
            <#break>
    </#switch>
</#list>

<#--保存单位或部门选择组件参数-->
<#if compPageSearch.isLookupCompanyDepartment=="true">
    <input type="hidden" name="companyId" value="${queryParam["companyId"]}"/>
    <input type="hidden" name="companyName" value="${queryParam["companyName"]}"/>
    <input type="hidden" name="departmentId" value="${queryParam["departmentId"]}"/>
    <input type="hidden" name="departmentName" value="${queryParam["departmentName"]}"/>
</#if>
</form>
<#-------------------------分页器表单 结束---------------------------------------->

<#-----------------------------渲染查询条件表单 开始----------------------------------->
<div class="pageHeader">

<#--表单中的元素命名规则为 “xxx_模块ID” -->
    <form id="searchForm_${componentId}" onsubmit="return navTabSearch(this);"
          action="${url}"
          method="post">

    <#--处理隐藏的表单字段-->
    <#list hiddenFormFields as field>
        <input type="hidden" id="${field.fieldName}_${componentId}"
               name="${field.fieldName}" value="${queryParam[field
        .fieldName]}"/>
    </#list>

        <div class="searchBar">
            <table class="searchContent">

            <#--查询表单每行最大列数-->
            <#assign formMaxColumnCount=4>
            <#--当前已处理的列数-->
            <#assign currentColumnCount=0>

            <#--依照输入框的类型，进行渲染-->
            <#list formFields as field>
            <#--如果超过每行最大列数，则进行换行处理-->
                <#if ((field?counter)-1)%(formMaxColumnCount)==0>
                <tr>
                </#if>

            <#--<td>[${field?counter}][${currentColumnCount}]</td>-->

                <#switch field.inputType>

                    <#case "TEXT"> <#--文本框-->
                        <td>${field.displayName}：</td>
                        <td>
                            <input type="text" name="${field.fieldName}" id="${field
                            .fieldName}_${componentId}"
                                   value="${queryParam
                                   .fieldName!""}"
                        </td>
                        <#break>

                    <#--下拉选择框-->
                    <#case "SELECT">
                        <td>${field.displayName}：</td>
                        <td>
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
                        </td>
                        <#break>

                    <#--起止日期选择框-->
                    <#case "BEGIN_END_DATE">
                        <td>
                        ${field.displayName}：
                        </td>
                        <td>
                            <input type="text" id="${field.fieldName}Begin_${componentId}"
                                   name="${field
                                   .fieldName}Begin"
                                   class="date lookupDateInput"
                                   readonly="true"/>
                            <a class="inputDateButton lookupBtn" href="javascript:;
            ">选择开始${field.displayName}</a>
                        </td>
                        <td class="inputDateEndLabel">
                            <span>-</span>
                        </td>
                        <td>
                            <input type="text" id="${field.fieldName}End_${componentId}"
                                   name="${field.fieldName}End"
                                   class="date lookupDateInput"
                                   readonly="true"/>
                            <a class="inputDateButton lookupBtn" href="javascript:;
            ">选择结束${field.displayName}</a>
                        </td>
                        <#break>



                    <#--区域选择框-->
                    <#case "REGION">
                        <td>所在区域：</td>
                        <td class="lookupRegionBtn">
                            <input name="${field.fieldName}Id"
                                   id="${field.fieldName}Id_${componentId}" value=""
                                   type="hidden"/>
            <span><input class="lookupInput"
                         name="${field.fieldName}Name"
                         id="${field.fieldName}Name_${componentId}"
                         type="text" readonly></span>
                            </span>

                            <a class="btnLook lookupBtn"
                               href="/comp/lookupRegion?pageSearchComponentId=${componentId}"
                               target="dialog"
                            <#--rel:标识此弹出层ID-->
                               rel="lookupRegion"
                            <#--resizable：是否可变大小-->
                               resizable="false"
                            <#--minable：是否可最小化-->
                               minable="false"
                            <#--maxable：是否可最大化-->
                               maxable="false"
                            <#--是否将背景遮盖-->
                               mask="true"
                            <#--弹出框宽度-->
                               width="600"
                            <#--弹出框高度-->
                               height="480"
                            <#--标题-->
                               title="选择区域"></a>
                        </td>
                        <#break>

                </#switch>

            <#--当到限定最大的一列时，重置当前列数-->
                <#if ((currentColumnCount+1)==formMaxColumnCount)>
                    <#assign currentColumnCount=0>
                </tr>
                </#if>
            <#--当前列数递增-->
                <#if (currentColumnCount<formMaxColumnCount)>
                    <#assign currentColumnCount=currentColumnCount+1>
                </#if>

            </#list>

            <#--渲染单位与区域选择组件-->
            <#if compPageSearch.isLookupCompanyRegion=="true">

                <#if currentColumnCount gte 2>
                <tr>
                </#if>

                <td colspan="4" class="lookupCompanyRegionBtn">
                    <span class="lookupCompanyRegionBtnCompanyTag">归属单位：</span>
                    <input name="companyId" id="companyId_${componentId}" value=""
                           type="hidden"/>
                        <span><input class="lookupCompanyRegionBtnCompanyName"
                                name="companyName" id="companyName_${componentId}"
                                     type="text" readonly></span>
                    <span class="lookupCompanyRegionBtnRegionTag">所属区域：</span>
                    <input name="regionId" id="regionId_${componentId}" value=""
                           type="hidden"/>
                        <span >
                            <input class="lookupCompanyRegionBtnRegionName"
                                   name="regionName" id="regionName_${componentId}"
                                   type="text"

                                   readonly>
                        </span>

                    <a class="btnLook"
                       href="/comp/lookupCompanyRegion?pageSearchComponentId=${componentId}"
                       target="dialog"
                    <#--rel:标识此弹出层ID-->
                       rel="lookupCompanyRegion"
                    <#--resizable：是否可变大小-->
                       resizable="false"
                    <#--minable：是否可最小化-->
                       minable="false"
                    <#--maxable：是否可最大化-->
                       maxable="false"
                    <#--是否将背景遮盖-->
                       mask="true"
                    <#--弹出框宽度-->
                       width="600"
                    <#--弹出框高度-->
                       height="480"
                    <#--标题-->
                       title="选择单位与所属区域（可只选单位）"></a>
                </td>

                <#if currentColumnCount gte 2>
                </tr>
                </#if>
            </#if>


            <#--渲染单位与部门选择组件-->
            <#if compPageSearch.isLookupCompanyDepartment=="true">
                <td class="lookupCompanyDepartmentBtn">
                    <span>归属单位：</span>
                    <input name="companyId" id="companyId_${componentId}" value=""
                           type="hidden"/>
                        <span><input name="companyName" id="companyName_${componentId}"
                                     type="text" readonly></span>
                    <span>部门：</span>
                    <input name="departmentId" id="departmentId_${componentId}" value=""
                           type="hidden"/>
                        <span>
                            <input name="departmentName" id="departmentName_${componentId}"
                                   type="text"

                                   readonly>
                        </span>

                    <a class="btnLook"
                       href="/comp/lookupCompanyDepartment?pageSearchComponentId=${componentId}"
                       target="dialog"
                    <#--rel:标识此弹出层ID-->
                       rel="lookupCompanyDepartment"
                    <#--resizable：是否可变大小-->
                       resizable="false"
                    <#--minable：是否可最小化-->
                       minable="false"
                    <#--maxable：是否可最大化-->
                       maxable="false"
                    <#--是否将背景遮盖-->
                       mask="true"
                    <#--弹出框宽度-->
                       width="600"
                    <#--弹出框高度-->
                       height="480"
                    <#--标题-->
                       title="选择单位与部门（可只选单位）"></a>
                </td>

            </#if>


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

<#-----------------------------工具栏 开始----------------------------------->
<#--工具栏配置信息-->
<#assign toolBtns=compPageSearch.compPageSearchToolBar>

<#if toolBtns?size!=0>
    <div class="panelBar">
        <ul class="toolBar">
            <#list toolBtns as toolBtn>
                <#assign toolBtnClassName=toolBtn.btnClassName>
                <li><a id="${toolBtnClassName}_${componentId}"
                       class="${toolBtnClassName}"><span>详情</span></a>
                </li>
            </#list>
        </ul>
    </div>

    <script type="text/javascript">
        $(function () {
            <#list toolBtns as toolBtn>
                var $toolBarBtn = $("#${toolBtn.btnClassName}_${componentId}");
                $toolBarBtn.live("click", function (event) {
                    //console.log($("#table_tbody_${componentId}").length);
                    var $tbody = $("#table_tbody_${componentId}");
                    if ($tbody.length == 0) {//没有数据，提示
                        alertMsg.warn("没有数据，无需处理！");
                        event.preventDefault();
                        return;
                    }

                    var $select = $tbody.find(".selected");
                    if ($select.length == 0) {//未选择某条记录，提示
                        alertMsg.warn("请选择某条记录！")
                        event.preventDefault();
                        return;
                    }

                    //console.log($select.attr("rel"));
                    var selectedId = $select.attr("rel");//已选择的记录ID
                    var url = "${toolBtn.url}" + selectedId;
                    var containId = "${componentId}" + selectedId;//容器ID
                    var containTitle = "${toolBtn.tabName} (" + selectedId + ")";//容器标题

                    <#switch toolBtn.openType>
                        <#case "TAB">
                            //打开新页签
                            navTab.openTab(containId, url, {
                                title: containTitle, fresh: ${toolBtn.isFresh}
                            });
                            <#break>
                        <#--todo 对话框有问题，检索时后端的页面也会同时刷新-->
                        <#case "DIALOG">
                            //打开新对话框
                            $.pdialog.open(url, containId, containTitle, {
                                //高度
                                height:  ${toolBtn.dialogHeight},
                                //宽度
                                width:  ${toolBtn.dialogWidth},
                                //最小高度
                                minH:  ${toolBtn.dialogMinHeight},
                                //最小宽度
                                minW:  ${toolBtn.dialogMinWidth},
                                //是否最大化打开
                                max:  ${toolBtn.dialogInitIsMax},
                                //是否使用遮罩
                                mask:  ${toolBtn.dialogIsMask},
                                //是否可缩放
                                resizable:  ${toolBtn.dialogIsResizable},
                                //是否可拖拉
                                drawable:  ${toolBtn.dialogIsDrawable},
                                //是否有【最大化】功能
                                maxable:  ${toolBtn.dialogIsMaxable},
                                //是否有【最小化】功能
                                minable:  ${toolBtn.dialogIsMinable},
                                //再次点击时是否刷新数据
                                fresh:  ${toolBtn.isFresh}
                            });
                            <#break>
                    </#switch>
                });
            </#list>
        });
    </script>
</#if>
<#-----------------------------工具栏 结束----------------------------------->

<#------------------------------渲染表格 开始-------------------------------------->

<#--表格主键-->
<#assign tableKey="">

<#--表格容器-->
    <table class="table" width="100%" layoutH="${compPageSearch.noTableHeight}">
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
        <tbody id="table_tbody_${componentId}">


        <#--渲染表格内容-->
        <#list page.data as data>
        <tr rel="${data[tableKey]}">

            <#list tableFields as field>
            <#--获取字段值-->
            <#--处理 对象"."语法-->
                <#if field.fieldName?contains(".")>
                    <#assign childObjName=field.fieldName?keep_before(".")>
                    <#assign childObjFieldName=field.fieldName?keep_after(".")>
                    <#assign fieldValue=data[childObjName][childObjFieldName]>
                <#else>
                    <#assign fieldValue=data[field.fieldName]>
                </#if>



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
                    <#--处理数据字典中key为多值的情况-->
                        <#list field.transformDataSet?keys as key>
                            <#assign currentfieldValue="">
                            <#list key?split("-") as childKey>
                                <#if childKey=="${fieldValue}">
                                    <#assign currentfieldValue="${field.transformDataSet[key]}">
                                </#if>
                            </#list>
                        ${currentfieldValue}
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