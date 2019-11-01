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
    <div class="panel panel-info">
        <div class="panel-heading">
            <h3 class="panel-title">忘记密码</h3>
        </div>
        <div class="panel-body">
            <form class="form-signin">
                <h2 class="form-signin-heading">重置密码</h2>
                <label for="email" class="sr-only">邮箱</label>
                <input type="email" id="email" class="form-control" placeholder="邮箱" required>
                <div style="text-align: center;">
                    <button type="button" class="btn btn-primary" onclick="forgetPassword()">
                        <%--<span class="glyphicon glyphicon-search" aria-hidden="true"></span>--%>提交
                    </button>
                    <button type="reset" class="btn btn-default">
                        <%--<span class="glyphicon glyphicon-refresh" aria-hidden="true"></span>--%>重置
                    </button>
                </div>
            </form>
        </div>
    </div>


</div> <!-- /container -->


<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script src="/js/assets/js/ie10-viewport-bug-workaround.js"></script>

<!-- 0.0 -->
<script src="/js/jquery-3.3.1.js"></script>


<!-- -.- -->
<script>
    $(document).ready(function () {

    });

    //重置密码
    function forgetPassword() {
        var email = $('#email').val();
        $.ajax({
            url: '/user/forgetPassword.jhtml',
            post: 'post',
            data: {'email': email},
            dataType: 'json',
            success: function (res) {
                if (res.code == 200) {
                    alert('重置密码成功');
                    location.href = '/login.jsp';
                } else {
                    alert(res.msg);
                }
            }
        });
    }
</script>
</body>
</html>

