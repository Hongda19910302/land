<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>${platformInfo}</title>

    <link href="${resourceRoot}/dwz/themes/default/style.land.css" rel="stylesheet"
          type="text/css" media="screen"/>
    <link href="${resourceRoot}/dwz/themes/css/core.land.css" rel="stylesheet"
          type="text/css"
          media="screen"/>
    <link href="${resourceRoot}/dwz/themes/css/land.css" rel="stylesheet"
          type="text/css"
          media="screen"/>
    <link href="${resourceRoot}/dwz/themes/css/print.css" rel="stylesheet" type="text/css"
          media="print"/>

    <link href="${resourceRoot}/dwz/uploadify/css/uploadify.css" rel="stylesheet"
          type="text/css" media="screen"/>

    <link href="${resourceRoot}/dwz/zTree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet"
          type="text/css" media="screen"/>

    <link href="${resourceRoot}/dwz/opentip/opentip.css" rel="stylesheet" type="text/css"/>

    <link href="${resourceRoot}/dwz/galleria/themes/classic/galleria.classic.css"
          rel="stylesheet" type="text/css"/>


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

    <script src="${resourceRoot}/dwz/form/jquery.form.land.js"
            type="text/javascript"></script>

    <script src="${resourceRoot}/dwz/xheditor/xheditor-1.2.1.min.js"
            type="text/javascript"></script>
    <script src="${resourceRoot}/dwz/xheditor/xheditor_lang/zh-cn.js"
            type="text/javascript"></script>


<#--树型组件-->
    <script src="${resourceRoot}/dwz/zTree/js/jquery.ztree.core-3.5.js"
            type="text/javascript"></script>

<#--提示组件-->
    <script src="${resourceRoot}/dwz/opentip/opentip-jquery.min.js"
            type="text/javascript"></script>

<#--文件上传组件-->
    <script src="${resourceRoot}/dwz/uploadify/scripts/jquery.uploadify.land.js"
            type="text/javascript"></script>

<#--图片展示组件-->
    <script src="${resourceRoot}/dwz/galleria/galleria-1.4.2.land.js"
            type="text/javascript"></script>
    <script src="${resourceRoot}/dwz/galleria/themes/classic/galleria.classic.js"
            type="text/javascript">
    </script>

    <#--打印组件-->
    <script src="${resourceRoot}/dwz/print/jQuery.print.js" type="text/javascript"></script>

    <script src="${resourceRoot}/dwz/js/dwz.core.land.js" type="text/javascript"></script>
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
    <script src="${resourceRoot}/dwz/js/dwz.ajax.land.js" type="text/javascript"></script>
    <script src="${resourceRoot}/dwz/js/dwz.pagination.js" type="text/javascript"></script>
    <script src="${resourceRoot}/dwz/js/dwz.database.land.js"
            type="text/javascript"></script>
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
    </script>
    <script src="${resourceRoot}/dwz/js/land.index.js" type="text/javascript"></script>
</head>

<body scroll="no">
<div id="layout">
    <div id="header">
        <div class="headerNav">
            <a class="logo" href="#">标志</a>
            <span>您好！${currentUser.name}</span>
            <ul class="nav">
                <li><a href="user/modifyPwdIndex?componentId=2"
                       target="navTab">修改密码</a></li>
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
            <#--渲染主菜单模块-->
            <#list allMenu as top>

                <div class="accordionHeader">
                    <h2><span>Folder</span>${top.name}</h2>
                </div>

            <#--渲染子菜单模块-->
                <div class="accordionContent">
                    <ul class="tree treeFolder">

                        <#list top.child as child>
                            <li><a href="${child.url}" target="navTab"
                                   rel="child_menu_${child.backPrivilegeId}">${child
                            .name}</a>
                            </li>
                        </#list>

                    </ul>
                </div>

            </#list>
            </div>
        </div>
    </div>
    <div id="container">
        <div id="navTab" class="tabsPage">
            <div class="tabsPageHeader">
                <div class="tabsPageHeaderContent">
                    <!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
                    <ul class="navTab-tab">
                        <li tabid="main" class="main"><a href="javascript:"><span><span
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
                <li><a href="javascript:">主页</a></li>
            </ul>
            <div class="navTab-panel tabsPageContent layoutBox">
                <div class="page unitBox">
                    <div class="pageFormContent" layoutH="80">
                        <div id="mainIntroduction">
                            <h2>欢迎进入行政执法平台</h2>
                            <img src="${resourceRoot}/dwz/images/main_introduction.jpg"
                                 width="793" height="256"/>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>

</div>

<div id="footer">${platformInfo} ${copyright}</div>

<#--地图组件-->
<script src="http://api.tianditu.com/js/maps.js" type="text/javascript"></script>
<script src="http://api.tianditu.com/js/service.js" type="text/javascript"></script>
<script src="http://api.tianditu.com/js/maptools.js" type="text/javascript"></script>
</body>
</html>
