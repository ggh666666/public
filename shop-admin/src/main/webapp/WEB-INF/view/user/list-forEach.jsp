<%--
  Created by IntelliJ IDEA.
  User: ggh
  Date: 2019/8/18
  Time: 19:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<html>
<head>
    <title>list</title>
    <link type="text/css" rel="stylesheet" href="/js/bootstrap-3.3.7-dist/css/bootstrap.css">
    <link type="text/css" rel="stylesheet" href="/js/DataTables-1.10.18/css/dataTables.bootstrap.css">
    <style type="text/css" rel="stylesheet">
        tfoot input {
            width: 100%;
            padding: 3px;
            box-sizing: border-box;
        }
    </style>
</head>
<body>
<table id="example" class="table table-striped table-bordered" style="width:100%">
    <thead>
    <tr>
        <th>id</th>
        <th>用户名</th>
        <th>真实姓名</th>
        <th>性别</th>
        <th>年龄</th>
        <th>电话</th>
        <th>邮箱</th>
    </tr>
    </thead>
    <tbody>

    <c:forEach items="${userList}" var="user">
        <tr>
            <td>${user.id}</td>
            <td>${user.userName}</td>
            <td>${user.realName}</td>
            <td>${user.sex == 1 ? "男" : "女"}</td>
            <td>${user.age}</td>
            <td>${user.phone}</td>
            <td>${user.email}</td>
        </tr>
    </c:forEach>

    </tbody>
    <tfoot>
    <tr>
        <th>id</th>
        <th>用户名</th>
        <th>真实姓名</th>
        <th>性别</th>
        <th>年龄</th>
        <th>电话</th>
        <th>邮箱</th>
    </tr>
    </tfoot>
</table>
<script src="/js/jquery-3.3.1.js"></script>
<script src="/js/DataTables-1.10.18/js/jquery.dataTables.min.js"></script>
<script src="/js/DataTables-1.10.18/js/dataTables.bootstrap.min.js"></script>
<script>
    $(document).ready(function () {
        // Setup - add a text input to each footer cell
        $('#example tfoot th').each(function () {
            var title = $(this).text();
            $(this).html('<input type="text" placeholder="Search ' + title + '" />');
        });

        //需在配置dataTable配置属性之前var 否则条件查询无效
        var table = $('#example').DataTable();

        $('#example').DataTable({
            "language": {
                "url": "/js/DataTables-1.10.18/Chinese.json"
            }
        });


        // Apply the search
        table.columns().every(function () {
            var that = this;

            $('input', this.footer()).on('keyup change clear', function () {
                alert(this.value)
                alert(that.search())
                if (that.search() !== this.value) {
                    that
                        .search(this.value)
                        .draw();
                }
            });
        });
    });
</script>
</body>
</html>
