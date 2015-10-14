<%--
  Created by IntelliJ IDEA.
  User: deniro
  Date: 2015/10/13
  Time: 17:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script type="text/javascript">
    $(function () {
        $.get("/role/findPage", function (result) {
            console.log(result);

            renderPageBar(result);
            renderTable(result);

        });
    });

    //渲染表格
    function renderTable(result){
        var columnsConfig = [//列配置
            {
                "name": "角色名称",//记录标题名称
                "width": "100"//宽度，单位px
            },
            {
                "name": "角色描述",
                "width": "100"
            },
            {
                "name": "单位名称",
                "width": "100"
            },
            {
                "name": "状态",
                "width": "100"
            }
        ];

        //渲染表格标题列
        var html = new Array();
        html.push('<table class="table" width="100%" layoutH="138">');
        html.push('<thead>');
        html.push('<tr>');

        for(var i=0;i<columnsConfig.length;i++){
            var columnConfig=columnsConfig[i];
            html.push('<th width="'+columnConfig.width+'">'+columnConfig.name+'</th>');
        }

        html.push('</tr>');
        html.push('</thead>');

        //渲染表格内容
        html.push('<tbody>');
        $.each(result.data,function (index, data) {
            html.push('<tr rel="'+data.backRoleId+'">');
            html.push('<td>'+data.backRoleName+'</td>');
            html.push('<td>'+data.comment+'</td>');
            html.push('<td>'+data.companyName+'</td>');
            html.push('<td>'+data.status+'</td>');
        });


        html.push('</table>');

        console.log(html.join(""));
        $(".toolBarPanel",navTab.getCurrentPanel()).after(html.join(""));
        $("table.table",navTab.getCurrentPanel()).jTable();
    }

    /**
     * 渲染导航条
     *
     * @param result 数据
     */
    function renderPageBar(result) {
        var html = new Array();
        html.push('<div class="pages">');
        html.push('<span>显示</span>');
        html.push('<select class="combox" name="numPerPage"');
        html.push('onchange="navTabPageBreak({numPerPage:this.value})">');
        html.push('<option value="20">20</option>');
        html.push('<option value="50">50</option>');
        html.push('<option value="100">100</option>');
        html.push('<option value="200">200</option>');
        html.push('</select>');
        html.push('<span>条，共 ' + result.totalCount + ' 条</span>');
        html.push('</div>');
        html.push('<div class="pagination" targetType="navTab" totalCount="' + result.totalCount + '" numPerPage="' + result.pageSize + '"');

        //计算需要展示的总页号
        var pageNumShown = 10;
        if (result.totalPageCount < 10) {
            pageNumShown = result.totalPageCount;
        }

        html.push('pageNumShown=' + pageNumShown + ' currentPage="' + result.currentPageNo + '"></div>');
        $(".pageBar",navTab.getCurrentPanel()).html(html.join(""));

        //生成导航条
        $("div.pagination",navTab.getCurrentPanel()).each(function () {
            var $this = $(this);
            $this.pagination({
                targetType: $this.attr("targetType"),
                rel: $this.attr("rel"),
                totalCount: $this.attr("totalCount"),
                numPerPage: $this.attr("numPerPage"),
                pageNumShown: $this.attr("pageNumShown"),
                currentPage: $this.attr("currentPage")
            });
        });
    }
</script>

<form id="pagerForm" method="post" action="demo_page1.html">
    <input type="hidden" name="status" value="${param.status}">
    <input type="hidden" name="keywords" value="${param.keywords}"/>
    <input type="hidden" name="pageNum" value="1"/>
    <input type="hidden" name="numPerPage" value="${model.numPerPage}"/>
    <input type="hidden" name="orderField" value="${param.orderField}"/>
</form>

<%--查询条件--%>
<div class="pageHeader">
    <form onsubmit="return navTabSearch(this);" action="demo_page1.html" method="post">
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
                    <li><a class="button" href="demo_page6.html" target="dialog" mask="true"
                           title="查询框"><span>高级检索</span></a></li>
                </ul>
            </div>
        </div>
    </form>
</div>

<div class="pageContent">

    <%--工具栏--%>
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

    <%--表格容器--%>

    <%--分页条--%>
    <div class="panelBar pageBar">
    </div>
</div>

