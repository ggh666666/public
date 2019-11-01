<%--
  Created by IntelliJ IDEA.
  User: ggh
  Date: 2019/8/29
  Time: 17:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="https://v3.bootcss.com/favicon.ico">

    <title>登录页面</title>

    <!-- Bootstrap core CSS -->
    <link href="/js/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <link href="/js/assets/css/ie10-viewport-bug-workaround.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="/js/assets/signin/signin.css" rel="stylesheet">

    <!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
    <!--[if lt IE 9]><script src="/js/assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
    <script src="/js/assets/js/ie-emulation-modes-warning.js"></script>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>

<div class="container">

    <form class="form-signin">
        <h2 class="form-signin-heading">请登录</h2>
        <label for="userName" class="sr-only">用户名</label>
        <input type="text" id="userName" class="form-control" placeholder="用户名" required autofocus>
        <label for="password" class="sr-only">密码</label>
        <input type="password" id="password" class="form-control" placeholder="密码" required>
        <label for="password" class="sr-only">验证码</label>
        <input type="text" id="code" class="form-control" placeholder="验证码" required>
        <img src="/img/code" onclick="this.src +='?' + Math.random()">
        <div class="checkbox">
            <label>
                <input type="checkbox" value="remember-me"> 记住我
            </label>
            <label>
                <a href="/password.jsp">忘记密码</a>
            </label>
        </div>
        <%--<button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>--%>
        <div style="text-align: center;">
            <button type="button" class="btn btn-primary" onclick="login()">
                <%--<span class="glyphicon glyphicon-search" aria-hidden="true"></span>--%>登录
            </button>
            <button type="reset" class="btn btn-default">
                <%--<span class="glyphicon glyphicon-refresh" aria-hidden="true"></span>--%>重置
            </button>
        </div>
    </form>

</div> <!-- /container -->


<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script src="/js/assets/js/ie10-viewport-bug-workaround.js"></script>

<!-- 0.0 -->
<script src="/js/jquery-3.3.1.js"></script>


<!-- -.- -->
<script>
    $(document).ready(function () {

    });

    //登录
    function login() {
        var userName = $('#userName').val();
        var password = $('#password').val();
        var code = $('#code').val();
        $.ajax({
            url: '/user/login.jhtml',
            post: 'post',
            data: {'userName': userName, 'password': password, 'code': code},
            dataType: 'json',
            success: function (res) {
                if (res.code == 200) {
                    location.href = '/index.jhtml';
                } else {
                    alert(res.msg);
                }
            }
        });
    }
</script>
</body>
</html>

