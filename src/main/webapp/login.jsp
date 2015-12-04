<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>${platformInfo}</title>

    <%--添加应用图标--%>
    <link rel="shortcut icon" href="favicon.ico" type="image/x-icon"/>

    <link href="${resourceRoot}/dwz/opentip/opentip.css" rel="stylesheet" type="text/css"/>
    <link href="${resourceRoot}/dwz/themes/css/login.css" rel="stylesheet" type="text/css"/>

    <script src="${resourceRoot}/dwz/js/jquery-1.7.2.min.js"
            type="text/javascript"></script>
    <script src="${resourceRoot}/dwz/opentip/opentip-jquery.min.js"
            type="text/javascript"></script>
    <script src="${resourceRoot}/dwz/form/jquery.form.min.js"
            type="text/javascript"></script>

    <script type="text/javascript">
        $(function () {
            handleCodeImg();
            login();
        });

        //登录
        function login() {
            $('#loginForm').ajaxForm({
                target: '.formtips',
                url: '/user/login',
                beforeSubmit: function () {
                    validate();
                    if ($('.formtips').children().length > 0) {//验证不通过
                        return false;
                    }
                    return true;
                },
                success: function (responseText) {
                    var $formtips = $(".formtips");
                    //删除以前的提醒元素
                    $formtips.children().remove();

                    if (responseText) {
                        if (responseText.statusCode == 200) {//登录成功，跳转到主界面
                            location.href = "/index";
                        } else {//登录失败
                            $formtips.append($('<li class="onError">' + responseText.message + '</li>'));
                        }
                    } else {
                        $formtips.append($('<li class="onError">连不上服务器，登录失败！</li>'));
                    }
                }
            });
        }

        //处理验证码图片
        function handleCodeImg() {
            var $codeImg = $("#codeImg");
            var codeImgTip = new Opentip($codeImg, {showOn: null, style: 'dark'});//验证码图片提示
            $codeImg.click(function () {//点击验证码更新图片
                $(this).attr("src", '/stickyImg?' + Math.floor(Math.random() * 100));
                codeImgTip.hide();
            }).mouseover(function () {//出现提示
                codeImgTip.setContent("看不清，换一张");
                codeImgTip.show();
            }).mouseout(function () {//隐藏提示
                codeImgTip.hide();
            });
        }

        /**
         * 验证
         */
        function validate() {
            var $formtips = $(".formtips");

            //删除以前的提醒元素
            $formtips.children().remove();

            //验证账号
            if ($("#account").val() == '') {
                $formtips.append($('<li class="onError">请输入账号！</li>'));
            }

            //验证密码
            if ($("#password").val() == '') {
                $formtips.append($('<li class="onError">请输入密码！</li>'));
            }

            //验证验证码
            if ($("#code").val() == '') {
                $formtips.append($('<li class="onError">请输入验证码！</li>'));
            }
        }
    </script>
</head>

<body>
<div id="login">
    <div id="login_header">
        <h1 class="login_logo">
            <a href="#"><img
                    src="${resourceRoot}/dwz/themes/default/images/login_logo.jpg"/></a>
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
            <form id="loginForm" method="post">
                <input type="hidden" name="loginSourceCode" value="1"/>

                <p>
                    <label>账号：</label>
                    <input type="text" id="account" name="account" size="20"
                           class="login_input"/>
                </p>

                <p>
                    <label>密码：</label>
                    <input type="password" id="password" name="password" size="20"
                           class="login_input"/>
                </p>

                <p>
                    <label>验证码：</label>
                    <input class="code" id="code" name="code" type="text" size="5"/>
                    <span><img id="codeImg" src="/stickyImg"
                               alt="" width="75" height="24"/></span>
                </p>


                <div class="login_bar">
                    <div
                            class="button"><input id="loginBtn" class="sub" type="submit"
                                                  value=" "
                            /></div>
                </div>

                <p>
                <ul class="formtips"></ul>
                </p>
            </form>

        </div>
        <div class="login_banner"><img
                src="${resourceRoot}/dwz/themes/default/images/login_banner.jpg"/></div>
        <div class="login_main">
            <%--<ul class="helpList">--%>
            <%--<li><a href="#">下载驱动程序</a></li>--%>
            <%--<li><a href="#">如何安装密钥驱动程序？</a></li>--%>
            <%--<li><a href="#">忘记密码怎么办？</a></li>--%>
            <%--<li><a href="#">为什么登录失败？</a></li>--%>
            <%--</ul>--%>
            <%--<div class="login_inner">--%>
            <%--<p>发现在初始，解决在萌芽</p>--%>
            <%--</div>--%>
        </div>
    </div>
    <div id="login_footer">
        ${platformInfo} Copyright &copy; 2015 deniro All Rights Reserved.
    </div>
</div>
</body>
</html>