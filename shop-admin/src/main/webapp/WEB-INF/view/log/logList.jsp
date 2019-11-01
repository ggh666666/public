<%--
  Created by IntelliJ IDEA.
  User: ggh
  Date: 2019/9/13
  Time: 22:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <jsp:include page="/common/head.jsp"></jsp:include>
</head>
<body>
<jsp:include page="/common/nav.jsp"></jsp:include>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-info">
                <div class="panel-heading">日志查询</div>
                <div class="panel-body">
                    <form class="form-horizontal" id="productForm">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">用户名</label>
                            <div class="col-sm-4">
                                <input type="email" class="form-control" id="userName" placeholder="请输入用户名...">
                            </div>
                            <label class="col-sm-2 control-label">真实姓名</label>
                            <div class="col-sm-4">
                                <input type="email" class="form-control" id="realName" placeholder="请输入真实姓名...">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">时间范围</label>
                            <div class="col-sm-4">
                                <div class="input-group">
                                    <input type="text" class="form-control" id="minTime" placeholder="开始日期..."
                                           aria-describedby="basic-addon2">
                                    <span class="input-group-addon"><i class="glyphicon glyphicon-transfer"></i></span>
                                    <input type="text" class="form-control" id="maxTime" placeholder="结束日期..."
                                           aria-describedby="basic-addon2">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">操作信息</label>
                            <div class="col-sm-4">
                                <input type="email" class="form-control" id="content" placeholder="请输入操作信息...">
                            </div>
                            <label class="col-sm-2 control-label">状态</label>
                            <div class="col-sm-4">
                                <select id="status" class="form-control">
                                    <option value="-1">===请选择===</option>
                                    <option value="1">成功</option>
                                    <option value="0">失败</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <div align="center">
                                <button class="btn btn-primary" type="button" onclick="search()"><i
                                        class="glyphicon glyphicon-search"></i>查询
                                </button>
                                <button class="btn btn-default" type="reset"><i class="glyphicon glyphicon-refresh"></i>重置
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-info">
                <div class="panel-heading">日志列表</div>
                <table style="table-layout:fixed;word-break:break-all;" id="logTable"
                       class="table table-striped table-hover table-striped table-bordered">
                    <thead>
                    <tr class="active">
                        <th style="text-align: center;">选择</th>
                        <th style="text-align: center;">用户名</th>
                        <th style="text-align: center;">真实姓名</th>
                        <th style="text-align: center;">操作信息</th>
                        <th style="text-align: center;">日志信息</th>
                        <th style="text-align: center;">状态</th>
                        <th style="text-align: center;">操作时间</th>
                        <th style="text-align: center;">失败原因</th>
                        <th style="text-align: center;">详细信息</th>
                    </tr>
                    </thead>
                    <tbody id="tbody" style="text-align: center;font-weight: bold;"></tbody>
                    <tfoot>
                    <tr class="active">
                        <th style="text-align: center;">选择</th>
                        <th style="text-align: center;">用户名</th>
                        <th style="text-align: center;">真实姓名</th>
                        <th style="text-align: center;">操作信息</th>
                        <th style="text-align: center;">日志信息</th>
                        <th style="text-align: center;">状态</th>
                        <th style="text-align: center;">操作时间</th>
                        <th style="text-align: center;">失败原因</th>
                        <th style="text-align: center;">详细信息</th>
                    </tr>
                    </tfoot>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
<script>
    $(function () {
        $("#minTime").datetimepicker({
            format: 'YYYY-MM-DD HH:mm',
            locale: 'zh-CN',
            showClear: true
        });
        $("#maxTime").datetimepicker({
            format: 'YYYY-MM-DD HH:mm',
            locale: 'zh-CN',
            showClear: true
        });
        findLogList();
    })

    //查询
    var logTable;

    function findLogList() {
        var columName = [
            {
                "data": "id",
                "render": function (data) {//这个data就是咱们查到的pageInfo中的数据集合里的对象
                    return '<input name="ids" type="checkbox" value="' + data + '"/>'
                }
            },
            {"data": "userName"},
            {"data": "realName"},
            {"data": "content"},
            {"data": "info"},
            {
                "data": "status",
                "render": function (data, type, row, meta) {
                    return data == 1 ? "成功" : data == 0 ? "失败" : "空";
                }
            },
            {"data": "createDate"},
            {"data": "errorMsg"},
            {"data": "detail"}
        ];
        /* 渲染datatables */
        logTable = $('#logTable').DataTable({
            "processing": true,
            "serverSide": true,
            "ordering": false,//是否启用排序
            "ajax": {
                "url": "/log/findLogList.jhtml",
                "type": "POST"
            },
            searching: false,
            "lengthMenu": [5, 10, 20, 40, 80],
            language: {
                "url": "/js/DataTables-1.10.18/Chinese.json"
            },
            columns: columName,//设置列的初始化属性
            "createdRow": function (row, data, dataIndex) {
                if (data.info != null) {
                    if (data.info.length > 10) {//只有超长，才有td点击事件
                        $(row).children('td').eq(4).attr('onclick', 'javascript:changeShowRemarks(this);');
                    }
                    $(row).children('td').eq(4).attr('content', data.info);
                }
            },
        });
    }

    //刷新方法 / 条件查询
    function search() {
        var v_userName = $("#userName").val();
        var v_realName = $("#realName").val();
        var v_minTimt = $("#minTime").val();
        var v_maxTime = $("#maxTime").val();
        var v_content = $("#content").val();
        var v_status = $("#status").val();
        var v_param = {};
        v_param.userName = v_userName;
        v_param.realName = v_realName;
        v_param.minTime = v_minTimt;
        v_param.maxTime = v_maxTime;
        v_param.content = v_content;
        v_param.status = v_status;
        logTable.settings()[0].ajax.data = v_param;
        logTable.ajax.reload();
    };

    //切换显示备注信息，显示部分或者全部
    function changeShowRemarks(obj) {//obj是td
        var content = $(obj).attr("content");
        if (content != null && content != '') {
            if ($(obj).attr("isDetail") == 'true') {//当前显示的是详细备注，切换到显示部分
                //$(obj).removeAttr('isDetail');//remove也可以
                $(obj).attr('isDetail', false);
                $(obj).html(getPartialRemarksHtml(content));
            } else {//当前显示的是部分备注信息，切换到显示全部
                $(obj).attr('isDetail', true);
                $(obj).html(getTotalRemarksHtml(content));
            }
        }
    }

    //部分备注信息
    function getPartialRemarksHtml(remarks) {
        return remarks.substr(0, 10) + '&nbsp;&nbsp;<a href="javascript:void(0);" ><b>更多</b></a>';
    }

    //全部备注信息
    function getTotalRemarksHtml(remarks) {
        return remarks + '&nbsp;&nbsp;<a href="javascript:void(0);" >收起</a>';
    }
</script>
</html>
