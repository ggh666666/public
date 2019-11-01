<%--
  Created by IntelliJ IDEA.
  User: Ming
  Date: 2019/9/10
  Time: 18:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <!-- head and css -->
    <jsp:include page="/common/head.jsp"></jsp:include>
    <title>修改密码</title>
</head>
<body>
<!-- 引入 nav 导航条 -->
<jsp:include page="/common/nav.jsp"></jsp:include>
<form class="form-horizontal">
    <div class="form-group">
        <label class="col-sm-2 control-label"></label>
        <label class="col-sm-2 control-label">原密码</label>
        <div class="col-sm-4">
            <input type="password" class="form-control" id="oldPassword"
                   placeholder="请输入原密码...">
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label"></label>
        <label class="col-sm-2 control-label">新密码</label>
        <div class="col-sm-4">
            <input type="password" class="form-control" id="newPassword" name="realName"
                   placeholder="请输入新密码...">
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label"></label>
        <label class="col-sm-2 control-label">确认密码</label>
        <div class="col-sm-4">
            <input type="password" class="form-control" id="conPassword" name="realName"
                   placeholder="请输入确认密码...">
        </div>
    </div>
    <div class="form-group">
        <div align="center">
            <button class="btn btn-primary" type="button" onclick="updatePassword()">修改</button>
            <button class="btn btn-default" type="reset">重置</button>
        </div>
    </div>
</form>
</body>
<script>
    function updatePassword() {
        var v_oldPassword = $("#oldPassword").val();
        var v_newPassword = $("#newPassword").val();
        var v_conPassword = $("#conPassword").val();
        var v_id = '${user.id}';
        alert(v_id);
        if (v_newPassword == v_conPassword) {
            $.ajax({
                url: "/user/updatePassword.jhtml",
                data: {
                    oldPassword: v_oldPassword,
                    newPassword: v_newPassword,
                    conPassword: v_conPassword,
                    userId: v_id
                },
                type: "post",
                success: function (res) {
                    if (res.code == 200) {
                        bootbox.alert({
                            message: "<span class='glyphicon glyphicon-exclamation-sign'></span>修改成功,请重新登录",
                            size: 'small',
                            title: "提示信息"
                        });
                    }
                }
            })
        } else {
            bootbox.alert({
                message: "<span class='glyphicon glyphicon-exclamation-sign'></span>新密码和确认密码不一致",
                size: 'small',
                title: "提示信息"
            });
        }
    }
</script>
</html>
