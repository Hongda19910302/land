<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>国土行政执法信息平台</title>
    <link href="${resourceRoot}/dwz/themes/css/login.css" rel="stylesheet" type="text/css"/>

    <script src="${resourceRoot}/dwz/js/jquery-1.7.2.min.js"></script>
    <script type="text/javascript">
        $(function () {

            //验证
            $('form :input').blur(function () {//为表单元素添加失去焦点事件
                var $parent = $(this).parent();

                //删除以前的提醒元素
                $parent.find(".formtips").remove();

                //验证用户名
                if ($(this).is('#username')) {
                    if (this.value == '') {
                        var errorMsg = '请输入用户名！';
                        $(".formtips").html(errorMsg);
                    }
                }
                //验证密码
                if ($(this).is('#password')) {
                    if (this.value == '') {
                        var errorMsg = '请输入密码！';
                        $(".formtips").html(errorMsg);
                    }
                }
                //验证验证码
                if ($(this).is('#code')) {
                    if (this.value == '') {
                        var errorMsg = '请输入验证码！';
                        $(".formtips").html(errorMsg);
                    }
                }
            });
        });
    </script>
</head>

<body>
<div id="login">
    <div id="login_header">
        <h1 class="login_logo">
            <a href="#"><img
                    src="${resourceRoot}/dwz/themes/default/images/login_logo.gif"/></a>
        </h1>

        <div class="login_headerContent">
            <div class="navList">
                <ul>
                    <li><a href="#">设为首页</a></li>
                    <li><a href="#">反馈</a></li>
                    <li><a href="#" target="_blank">帮助</a></li>
                </ul>
            </div>
            <h2 class="login_title"><img
                    src="${resourceRoot}/dwz/themes/default/images/login_title.png"/></h2>
        </div>
    </div>
    <div id="login_content">
        <div class="loginForm">
            <form>
                <p>
                    <label>用户名：</label>
                    <input type="text" id="username" name="username" size="20"
                           class="login_input"/>
                </p>

                <p>
                    <label>密码：</label>
                    <input type="password" id="password" name="password" size="20"
                           class="login_input"/>
                </p>

                <p>
                    <label>验证码：</label>
                    <input class="code" id="code" type="text" size="5"/>
                    <span><img src="${resourceRoot}/dwz/themes/default/images/header_bg.png"
                               alt="" width="75" height="24"/></span>
                </p>

                <div class="login_bar">
                    <input class="sub" type="submit" value=" "/>
                </div>

                <p>
                    <span class="formtips onError"></span>
                </p>
            </form>

        </div>
        <div class="login_banner"><img
                src="${resourceRoot}/dwz/themes/default/images/login_banner.jpg"/></div>
        <div class="login_main">
            <ul class="helpList">
                <li><a href="#">下载驱动程序</a></li>
                <li><a href="#">如何安装密钥驱动程序？</a></li>
                <li><a href="#">忘记密码怎么办？</a></li>
                <li><a href="#">为什么登录失败？</a></li>
            </ul>
            <div class="login_inner">
                <p>您可以使用 网易网盘 ，随时存，随地取</p>

                <p>您还可以使用 闪电邮 在桌面随时提醒邮件到达，快速收发邮件。</p>

                <p>在 百宝箱 里您可以查星座，订机票，看小说，学做菜…</p>
            </div>
        </div>
    </div>
    <div id="login_footer">
        Copyright &copy; 2015 deniro All Rights Reserved.
    </div>
</div>
</body>
</html>