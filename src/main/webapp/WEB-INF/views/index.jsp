<%--
  Created by IntelliJ IDEA.
  User: deniro
  Date: 2015/10/12
  Time: 15:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>国土行政执法信息平台2.0</title>

    <link href="${resourceRoot}/dwz/themes/default/style.css" rel="stylesheet"
          type="text/css" media="screen"/>
    <link href="${resourceRoot}/dwz/themes/css/core.css" rel="stylesheet" type="text/css"
          media="screen"/>
    <link href="${resourceRoot}/dwz/themes/css/print.css" rel="stylesheet" type="text/css"
          media="print"/>
    <link href="${resourceRoot}/dwz/uploadify/css/uploadify.css" rel="stylesheet"
          type="text/css" media="screen"/>
    <!--[if IE]>
    <link href="${resourceRoot}/dwz/themes/css/ieHack.css" rel="stylesheet" type="text/css"
          media="screen"/>
    <![endif]-->

    <!--[if lte IE 9]>
    <script src="${resourceRoot}/dwz/js/speedup.js" type="text/javascript"></script>
    <![endif]-->

    <script src="${resourceRoot}/dwz/js/jquery-1.7.2.js" type="text/javascript"></script>
    <script src="${resourceRoot}/dwz/js/jquery.cookie.js" type="text/javascript"></script>
    <script src="${resourceRoot}/dwz/js/jquery.validate.js" type="text/javascript"></script>
    <script src="${resourceRoot}/dwz/js/jquery.bgiframe.js" type="text/javascript"></script>
    <script src="${resourceRoot}/dwz/xheditor/xheditor-1.2.1.min.js"
            type="text/javascript"></script>
    <script src="${resourceRoot}/dwz/xheditor/xheditor_lang/zh-cn.js"
            type="text/javascript"></script>
    <script src="${resourceRoot}/dwz/uploadify/scripts/jquery.uploadify.js"
            type="text/javascript"></script>

    <script src="${resourceRoot}/dwz/js/dwz.core.js" type="text/javascript"></script>
    <script src="${resourceRoot}/dwz/js/dwz.util.date.js" type="text/javascript"></script>
    <script src="${resourceRoot}/dwz/js/dwz.validate.method.js"
            type="text/javascript"></script>
    <script src="${resourceRoot}/dwz/js/dwz.barDrag.js" type="text/javascript"></script>
    <script src="${resourceRoot}/dwz/js/dwz.drag.js" type="text/javascript"></script>
    <script src="${resourceRoot}/dwz/js/dwz.tree.js" type="text/javascript"></script>
    <script src="${resourceRoot}/dwz/js/dwz.accordion.js" type="text/javascript"></script>
    <script src="${resourceRoot}/dwz/js/dwz.ui.js" type="text/javascript"></script>
    <script src="${resourceRoot}/dwz/js/dwz.theme.js" type="text/javascript"></script>
    <script src="${resourceRoot}/dwz/js/dwz.switchEnv.js" type="text/javascript"></script>
    <script src="${resourceRoot}/dwz/js/dwz.alertMsg.js" type="text/javascript"></script>
    <script src="${resourceRoot}/dwz/js/dwz.contextmenu.js" type="text/javascript"></script>
    <script src="${resourceRoot}/dwz/js/dwz.navTab.js" type="text/javascript"></script>
    <script src="${resourceRoot}/dwz/js/dwz.tab.js" type="text/javascript"></script>
    <script src="${resourceRoot}/dwz/js/dwz.resize.js" type="text/javascript"></script>
    <script src="${resourceRoot}/dwz/js/dwz.dialog.js" type="text/javascript"></script>
    <script src="${resourceRoot}/dwz/js/dwz.dialogDrag.js" type="text/javascript"></script>
    <script src="${resourceRoot}/dwz/js/dwz.sortDrag.js" type="text/javascript"></script>
    <script src="${resourceRoot}/dwz/js/dwz.cssTable.js" type="text/javascript"></script>
    <script src="${resourceRoot}/dwz/js/dwz.stable.js" type="text/javascript"></script>
    <script src="${resourceRoot}/dwz/js/dwz.taskBar.js" type="text/javascript"></script>
    <script src="${resourceRoot}/dwz/js/dwz.ajax.js" type="text/javascript"></script>
    <script src="${resourceRoot}/dwz/js/dwz.pagination.js" type="text/javascript"></script>
    <script src="${resourceRoot}/dwz/js/dwz.database.js" type="text/javascript"></script>
    <script src="${resourceRoot}/dwz/js/dwz.datepicker.js" type="text/javascript"></script>
    <script src="${resourceRoot}/dwz/js/dwz.effects.js" type="text/javascript"></script>
    <script src="${resourceRoot}/dwz/js/dwz.panel.js" type="text/javascript"></script>
    <script src="${resourceRoot}/dwz/js/dwz.checkbox.js" type="text/javascript"></script>
    <script src="${resourceRoot}/dwz/js/dwz.history.js" type="text/javascript"></script>
    <script src="${resourceRoot}/dwz/js/dwz.combox.js" type="text/javascript"></script>
    <script src="${resourceRoot}/dwz/js/dwz.print.js" type="text/javascript"></script>

    <!-- 可以用dwz.min.js替换前面全部dwz.*.js (注意：替换是下面dwz.regional.zh.js还需要引入)
    <script src="bin/dwz.min.js" type="text/javascript"></script>
    -->
    <script src="${resourceRoot}/dwz/js/dwz.regional.zh.js" type="text/javascript"></script>

    <script type="text/javascript">
        $(function () {
            DWZ.init("${resourceRoot}/dwz/dwz.frag.xml", {
                loginUrl: "login.jsp", loginTitle: "登录",	// 弹出登录对话框
                statusCode: {ok: 200, error: 300, timeout: 301}, //【可选】
                pageInfo: {
                    pageNum: "pageNum",
                    numPerPage: "numPerPage",
                    orderField: "orderField",
                    orderDirection: "orderDirection"
                }, //【可选】
                keys: {statusCode: "statusCode", message: "message"}, //【可选】
                ui: {hideMode: 'offsets'}, //【可选】hideMode:navTab组件切换的隐藏方式，支持的值有’display’，’offsets’负数偏移位置的值，默认值为’display’
                debug: false,	// 调试模式 【true|false】
                callback: function () {
                    initEnv();
                    $("#themeList").theme({themeBase: "themes"}); // themeBase 相对于index页面的主题base路径
                }
            });

            /**
             * 退出
             */
            $("#exit").click(function () {
                location.href = "/user/exit";
            });
        });

    </script>
</head>

<body scroll="no">
<div id="layout">
    <div id="header">
        <div class="headerNav">
            <div id="logo2">国土行政执法信息平台2.0</div>
            <a class="logo" href="#">标志</a>
            <ul class="nav">
                <li><a id="changePassword" href="#">修改密码</a></li>
                <li><a id="exit" href="#">退出</a></li>
            </ul>
        </div>
        <!-- navMenu -->

    </div>

    <div id="leftside">
        <div id="sidebar_s">
            <div class="collapse">
                <div class="toggleCollapse">
                    <div></div>
                </div>
            </div>
        </div>
        <div id="sidebar">
            <div class="toggleCollapse"><h2>主菜单</h2>

                <div>收缩</div>
            </div>

            <div class="accordion" fillSpace="sidebar">
                <div class="accordionHeader">
                    <h2><span>Folder</span>典型页面</h2>
                </div>
                <div class="accordionContent">
                    <ul class="tree treeFolder">
                        <li><a href="demo_page1.html" target="navTab" rel="demo_page1">查询我的客户</a>
                        </li>
                        <li><a href="demo_page1.html" target="navTab" rel="demo_page2">表单查询页面</a>
                        </li>
                        <li><a href="demo_page4.html" target="navTab" rel="demo_page4">表单录入页面</a>
                        </li>
                        <li><a href="demo_page5.html" target="navTab" rel="demo_page5">有文本输入的表单</a>
                        </li>
                    </ul>
                </div>
                <div class="accordionHeader">
                    <h2><span>Folder</span>典型页面</h2>
                </div>
                <div class="accordionContent">
                    <ul class="tree treeFolder">
                        <li><a href="demo_page1.html" target="navTab" rel="demo_page1">查询我的客户</a>
                        </li>
                        <li><a href="demo_page1.html" target="navTab" rel="demo_page2">表单查询页面</a>
                        </li>
                        <li><a href="demo_page4.html" target="navTab" rel="demo_page4">表单录入页面</a>
                        </li>
                        <li><a href="demo_page5.html" target="navTab" rel="demo_page5">有文本输入的表单</a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <div id="container">
        <div id="navTab" class="tabsPage">
            <div class="tabsPageHeader">
                <div class="tabsPageHeaderContent">
                    <!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
                    <ul class="navTab-tab">
                        <li tabid="main" class="main"><a href="javascript:;"><span><span
                                class="home_icon">我的主页</span></span></a></li>
                    </ul>
                </div>
                <div class="tabsLeft">left</div>
                <!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" -->
                <div class="tabsRight">right</div>
                <!-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" -->
                <div class="tabsMore">more</div>
            </div>
            <ul class="tabsMoreList">
                <li><a href="javascript:;">主页</a></li>
            </ul>
            <div class="navTab-panel tabsPageContent layoutBox">
                <div class="page unitBox">
                    <div class="pageFormContent" layoutH="80">
                        <div id="mainIntroduction">
                            <h2>欢迎进入行政执法平台</h2>
                            <img src="${resourceRoot}/dwz/images/main_introduction.jpg"
                                 width="793" height="256" />
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>

</div>

<div id="footer">Copyright &copy; 2015 deniro</div>

</body>
</html>
