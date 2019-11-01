<%--
  Created by IntelliJ IDEA.
  User: ggh
  Date: 2019/8/14
  Time: 22:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户添加</title>
</head>
<body>
<form action="/user/add.jhtml" method="post">
    用户名:<input name="userName"><br>
    真实姓名:<input name="realName"><br>
    密码:<input type="password" name="password"><br>
    性别:
    <input type="radio" value="1" name="sex">男&nbsp;&nbsp;
    <input type="radio" value="0" name="sex">女<br>
    年龄:<input type="text" name="age"><br>
    电话:<input name="phone"><br>
    邮箱:<input name="email"><br>
    <input type="submit">
</form>
</body>
</html>
