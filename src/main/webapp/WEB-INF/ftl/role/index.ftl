<script type="text/javascript">
    $(function () {
            //显示条数赋值
            $(".perPageNumCombox").val("${page.pageSize}");
    });
</script>

<form id="pagerForm" method="post" action="/role/index">
    <input type="hidden" name="status" value="${param.status}">
    <input type="hidden" name="keywords" value="${param.keywords}"/>
    <input type="hidden" name="pageNum" value="1"/>
    <input type="hidden" name="numPerPage" value="${page.pageSize}"/>
    <input type="hidden" name="orderField" value="${param.orderField}"/>
</form>

<#--查询条件-->
<div class="pageHeader">
    <form onsubmit="return navTabSearch(this);" action="/role/index" method="post">
        <div class="searchBar">
            <table class="searchContent">
                <tr>
                    <td>
                        我的客户：<input type="text" name="keyword"/>
                    </td>
                    <td>
                        <select class="combox" name="province">
                            <option value="">所有省市</option>
                            <option value="北京">北京</option>
                            <option value="上海">上海</option>
                            <option value="天津">天津</option>
                            <option value="重庆">重庆</option>
                            <option value="广东">广东</option>
                        </select>
                    </td>
                    <td>
                        建档日期：<input type="text" class="date" readonly="true"/>
                    </td>
                </tr>
            </table>
            <div class="subBar">
                <ul>
                    <li>
                        <div class="buttonActive">
                            <div class="buttonContent">
                                <button type="submit">检索</button>
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
            <li><a class="add" href="demo_page4.html" target="navTab"><span>添加</span></a>
            </li>
            <li><a class="delete" href="demo/common/ajaxDone.html?uid={sid_user}"
                   target="ajaxTodo" title="确定要删除吗?"><span>删除</span></a></li>
            <li><a class="edit" href="demo_page4.html?uid={sid_user}" target="navTab"><span>修改</span></a>
            </li>
            <li class="line">line</li>
            <li><a class="icon" href="demo/common/dwz-team.xls" target="dwzExport"
                   targetType="navTab" title="实要导出这些记录吗?"><span>导出EXCEL</span></a></li>
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

<#--分页条-->
    <div class="panelBar pageBar">
        <div class="pages">
            <span>显示</span>
            <select class="combox perPageNumCombox" name="numPerPage"
                    onchange="navTabPageBreak({numPerPage:this.value})">
                <option value="20">20</option>
                <option value="50">50</option>
                <option value="100">100</option>
                <option value="200">200</option>
            </select>
            <span>条，共 ${page.totalCount}  条</span>
        </div>
        <div class="pagination" targetType="navTab" totalCount="${page.totalCount}"
             numPerPage="${page.pageSize}"
    </div>
</div>