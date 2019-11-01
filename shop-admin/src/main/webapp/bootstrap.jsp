<%--
  Created by IntelliJ IDEA.
  User: ggh
  Date: 2019/8/19
  Time: 12:11
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
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="/js/bootstrap-3.3.7-dist/css/bootstrap.min.css">
    <style>
        .show-grid [class ^="col-"] {
            height: 40px;
            background-color: #eee;
            border: 1px solid red;
        }
    </style>

    <!-- HTML5 shim 和 Respond.js 是为了让 IE8 支持 HTML5 元素和媒体查询（media queries）功能 -->
    <!-- 警告：通过 file:// 协议（就是直接将 html 页面拖拽到浏览器中）访问页面时 Respond.js 不起作用 -->
    <!--[if lt IE 9]>
    <script src="https://cdn.jsdelivr.net/npm/html5shiv@3.7.3/dist/html5shiv.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/respond.js@1.4.2/dest/respond.min.js"></script>
    <![endif]-->
</head>
<body class="show-grid">
<div class="container">
    <div class="row">
        <div class="col-md-12" style="height: 40px;"></div>
    </div>
    <div class="row" style="height: 150px;">
        <div class="col-md-3" style="height: 150px;">
            <div class="row" style="height: 40px;">
                <div class="col-md-6" style="height: 40px;"></div>
                <div class="col-md-6" style="height: 40px;"></div>
            </div>
            <div class="row" style="height: 110px;">
                <div class="col-md-6" style="height: 110px;"></div>
                <div class="col-md-6" style="height: 110px;"></div>
            </div>
        </div>
        <div class="col-md-6" style="height: 150px;">
            <div class="row" style="height: 75px;">
                <div class="col-md-12" style="height: 75px;"></div>
            </div>
            <div class="row" style="height: 75px;">
                <div class="col-md-12" style="height: 75px;"></div>
            </div>
        </div>
        <div class="col-md-3" style="height: 150px;">
            <div class="row" style="height: 40px;">
                <div class="col-md-6" style="height: 40px;"></div>
                <div class="col-md-6" style="height: 40px;"></div>
            </div>
            <div class="row" style="height: 110px;">
                <div class="col-md-6" style="height: 110px;"></div>
                <div class="col-md-6" style="height: 110px;"></div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-5"></div>
        <div class="col-md-1"></div>
        <div class="col-md-1"></div>
        <div class="col-md-5"></div>
    </div>
    <div class="row">
        <div class="col-md-5"></div>
        <div class="col-md-1"></div>
        <div class="col-md-1"></div>
        <div class="col-md-5"></div>
    </div>
</div>
<!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
<script href="/js/jquery-3.3.1.js"></script>
<!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
<script href="/js/bootstrap-3.3.7-dist/js/bootstrap.js"></script>
</body>
</html>
