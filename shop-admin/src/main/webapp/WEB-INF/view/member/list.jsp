<%--
  Created by IntelliJ IDEA.
  User: ggh
  Date: 2019/10/21
  Time: 20:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <!-- head and css -->
    <jsp:include page="/common/head.jsp"></jsp:include>
    <title>Title</title>
    <style type="text/css" rel="stylesheet">
        tfoot input {
            width: 100%;
            padding: 3px;
            box-sizing: border-box;
        }
    </style>
</head>
<body>
<!-- 引入 nav 导航条 -->
<jsp:include page="/common/nav.jsp"></jsp:include>

<div class="container">

    <!-- search -->
    <div class="row">
        <div class="col-md-12">
            <!-- 会员查询 -->
            <div class="panel panel-info">
                <div class="panel-heading">会员查询</div>
                <div class="panel-body">
                    <form class="form-horizontal" id="searchForm" method="post">
                        <div class="form-group">
                            <label for="username" class="col-sm-2 control-label">会员名</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="username" name="username"
                                       placeholder="请输入会员名...">
                            </div>

                            <label for="realName" class="col-sm-2 control-label">真实姓名</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="realName" name="realName"
                                       placeholder="请输入真实姓名...">
                            </div>

                            <label for="mobile" class="col-sm-2 control-label">手机号</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="mobile" name="mobile"
                                       placeholder="请输入手机号...">
                            </div>
                        </div>

                        <!-- -->
                        <input name="area1" type="hidden">
                        <input name="area2" type="hidden">
                        <input name="area3" type="hidden">
                        <input name="area4" type="hidden">
                        <div class="form-group" id="areaSearchDiv">
                            <label class="col-sm-2 control-label">地区</label>

                        </div>

                        <div style="text-align: center;">
                            <button type="button" class="btn btn-primary" onclick="search()">
                                <span class="glyphicon glyphicon-search" aria-hidden="true"></span>查询
                            </button>
                            <button type="reset" class="btn btn-default" onclick="resetRoleSelect('search_roleSelect')">
                                <span class="glyphicon glyphicon-refresh" aria-hidden="true"></span>重置
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- datatables -->
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-info">
                <div class="panel-heading">会员列表</div>
                <table id="example" class="table table-striped table-bordered" style="width:100%">
                    <thead>
                    <tr>
                        <th>id</th>
                        <th>会员名</th>
                        <th>真实姓名</th>
                        <th>手机号</th>
                        <th>地区</th>
                    </tr>
                    </thead>
                    <tfoot>
                    <tr>
                        <th>id</th>
                        <th>会员名</th>
                        <th>真实姓名</th>
                        <th>手机号</th>
                        <th>地区</th>
                    </tr>
                    </tfoot>
                </table>
            </div>

        </div>
    </div>
</div>


<script>
    var datatable;

    $(document).ready(function () {
        initDataTables();
        // 初始化条件查询的地区下拉框
        initArea('areaSearchDiv', 0);
    });

    //初始化地区
    function initArea(elementId, areaId, obj) {
        if (obj) {
            // 删除父级的之后的同级元素
            $(obj).parent().nextAll().remove();
            // 动态给下拉列表添加属性
            $(obj).attr("data-areaName", obj.options[obj.selectedIndex].text);
        }
        $.ajax({
            type: "post",
            url: "/area/findChildAreaList.jhtml",
            data: {"id": areaId},
            success: function (data) {
                if (data.code == 200) {
                    if (data.data.length == 0) {
                        return;
                    }
                    // 在指定的div中追加select
                    var v_select = '<div class="col-sm-2"><select class="form-control" name="areaSelect" onchange="initArea(\'' + elementId + '\', this.value, this, 1)"><option value="-1">==请选择==</option>';
                    var v_areaArr = data.data;
                    for (var i = 0; i < v_areaArr.length; i++) {
                        v_select += "<option value='" + v_areaArr[i].id + "' data-areaName='" + v_areaArr[i].areaName + "'>" + v_areaArr[i].areaName + "</option>";
                    }
                    v_select += "</select></div>";
                    $("#" + elementId).append(v_select);

                }
            }
        })
    }

    //条件查询
    function search() {
        //获取参数
        var username = $("#username").val();
        var realName = $("#realName").val();
        var mobile = $("#mobile").val();
        //
        var v_area1 = $($("select[name='areaSelect']", $("#searchForm"))[0]).val();
        var v_area2 = $($("select[name='areaSelect']", $("#searchForm"))[1]).val();
        var v_area3 = $($("select[name='areaSelect']", $("#searchForm"))[2]).val();
        var v_area4 = $($("select[name='areaSelect']", $("#searchForm"))[3]).val();

        var param = {};

        //
        param.area1 = v_area1;
        param.area2 = v_area2;
        param.area3 = v_area3;
        param.area4 = v_area4;

        param.username = username;
        param.realName = realName;
        param.mobile = mobile;
        //传递参数
        datatable.settings()[0].ajax.data = param;
        datatable.ajax.reload();
    }

    //初始化datatables
    function initDataTables() {
        datatable = $('#example').DataTable({
            //语言
            "language": {
                "url": "/js/DataTables-1.10.18/Chinese.json"
            },

            //自定义每页条数下拉框
            "lengthMenu": [5, 10, 15],

            //全局搜索框
            "searching": false,

            //排序按钮
            "ordering": false,


            //ajax请求从后台查询数据
            "processing": true,
            "serverSide": true,
            "ajax": {
                "url": "/member/list.jhtml",
                "type": "POST",
                "dataSrc": function (res) {
                    res.draw = res.data.draw;
                    res.recordsTotal = res.data.recordsTotal;
                    res.recordsFiltered = res.data.recordsFiltered;
                    return res.data.data;
                }
                //, "data": {ids: [1,2]}
            },
            "columns": [
                {
                    "data": "id",
                    "render": function (data, type, row, meta) {

                        return '<input stubr="1" type="checkbox" value="' + data + '">';
                    }
                },
                {"data": "username"},
                {"data": "realName"},
                {"data": "mobile"},
                {"data": "areaInfo"},
            ],
            "drawCallback": function (s) {//fndrawCallback
            },
            "initComplete": function () {

            }
        });

    }
</script>
</body>
</html>
