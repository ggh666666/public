<%--
  Created by IntelliJ IDEA.
  User: ggh
  Date: 2019/8/14
  Time: 22:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <!-- head and css -->
    <jsp:include page="/common/head.jsp"></jsp:include>
    <title>list</title>
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
            <!-- 用户查询 -->
            <div class="panel panel-info">
                <div class="panel-heading">用户查询</div>
                <div class="panel-body">
                    <form class="form-horizontal" id="searchForm" method="post">
                        <div class="form-group">
                            <label for="userName" class="col-sm-2 control-label">用户名</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="userName" name="userName"
                                       placeholder="请输入用户名...">
                            </div>

                            <label for="realName" class="col-sm-2 control-label">真实姓名</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="realName" name="realName"
                                       placeholder="请输入真实姓名...">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="beginAge" class="col-sm-2 control-label">年龄范围</label>
                            <div class="col-sm-4">
                                <div class="input-group">
                                    <input type="number" class="form-control" id="beginAge" name="beginAge"
                                           placeholder="开始年龄..."
                                           aria-describedby="basic-addon1">
                                    <span class="input-group-addon" id>
                                        <span class="glyphicon glyphicon-transfer" aria-hidden="true"></span>
                                    </span>
                                    <input type="number" class="form-control" id="endAge" name="endAge"
                                           placeholder="结束年龄..."
                                           aria-describedby="basic-addon1">
                                </div>
                            </div>

                            <label for="minPay" class="col-sm-2 control-label">薪资范围</label>
                            <div class="col-sm-4">
                                <div class="input-group">
                                    <input type="text" class="form-control" id="minPay" name="minPay"
                                           placeholder="最小薪资..."
                                           aria-describedby="basic-addon1">
                                    <span class="input-group-addon" id>
                                        <span class="glyphicon glyphicon-yen" aria-hidden="true"></span>
                                    </span>
                                    <input type="text" class="form-control" id="maxPay" name="maxPay"
                                           placeholder="最大薪资..."
                                           aria-describedby="basic-addon1">
                                </div>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="beginEntryTime" class="col-sm-2 control-label">入职时间</label>
                            <div class="col-sm-4">
                                <div class="input-group">
                                    <input type="text" class="form-control" id="beginEntryTime" name="beginEntryTime"
                                           placeholder="开始时间..."
                                           aria-describedby="basic-addon1">
                                    <span class="input-group-addon" id>
                                        <span class="glyphicon glyphicon-calendar" aria-hidden="true"></span>
                                    </span>
                                    <input type="text" class="form-control" id="endEntryTime" name="endEntryTime"
                                           placeholder="结束时间..."
                                           aria-describedby="basic-addon1">
                                </div>
                            </div>

                            <label for="" class="col-sm-2 control-label">角色</label>
                            <div class="col-sm-4" id="search_roleDiv">
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

    <!-- export -->
    <div class="row">
        <div class="col-md-12">
            <div style="text-align: left; background: #e4b9b9;">
                <button type="button" class="btn btn-primary" onclick="showAddUser()">
                    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>添加
                </button>
                <button type="button" class="btn btn-danger" onclick="batchDelete()">
                    <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>批量删除
                </button>
                <button type="button" class="btn btn-success" onclick="exportExcel()">
                    <span class="glyphicon glyphicon-export" aria-hidden="true"></span>导出excel
                </button>
                <button type="button" class="btn btn-success" onclick="exportPdf()">
                    <span class="glyphicon glyphicon-export" aria-hidden="true"></span>导出pdf
                </button>
                <button type="button" class="btn btn-success" onclick="exportWord()">
                    <span class="glyphicon glyphicon-export" aria-hidden="true"></span>导出word
                </button>
            </div>
        </div>
    </div>

    <!-- datatables -->
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-info">
                <div class="panel-heading">用户列表</div>
                <table id="example" class="table table-striped table-bordered" style="width:100%">
                    <thead>
                    <tr>
                        <th>id</th>
                        <th>用户名</th>
                        <th>真实姓名</th>
                        <th>性别</th>
                        <th>年龄</th>
                        <th>薪资</th>
                        <th>入职时间</th>
                        <th>地区</th>
                        <th>电话</th>
                        <th>邮箱</th>
                        <th>角色</th>
                        <th>状态</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tfoot>
                    <tr>
                        <th>id</th>
                        <th>用户名</th>
                        <th>真实姓名</th>
                        <th>性别</th>
                        <th>年龄</th>
                        <th>薪资</th>
                        <th>入职时间</th>
                        <th>地区</th>
                        <th>电话</th>
                        <th>邮箱</th>
                        <th>角色</th>
                        <th>状态</th>
                        <th>操作</th>
                    </tr>
                    </tfoot>
                </table>
            </div>

        </div>
    </div>
</div>

<span id="addUserSpan" hidden>
    <form class="form-horizontal" action="" id="addForm">
        <div class="form-group">
            <label for="add_userName" class="col-sm-2 control-label">用户名</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" name="userName" id="add_userName" placeholder="userName...">
            </div>
        </div>
        <div class="form-group">
            <label for="add_realName" class="col-sm-2 control-label">真实姓名</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" name="realName" id="add_realName" placeholder="realName...">
            </div>
        </div>
        <div class="form-group">
            <label for="add_password" class="col-sm-2 control-label">密码</label>
            <div class="col-sm-10">
                <input type="password" class="form-control" name="password" id="add_password" placeholder="password...">
            </div>
        </div>
        <div class="form-group">
            <label for="add_password" class="col-sm-2 control-label">确认密码</label>
            <div class="col-sm-10">
                <input type="password" class="form-control" name="confirmPassword" id="add_confirmPassword"
                       placeholder="confirmPassword...">
            </div>
        </div>
        <div class="form-group">
            <label for="" class="col-sm-2 control-label">性别</label>
            <div class="col-sm-10">
                <label class="radio-inline">
                <input type="radio" name="sex" id="add_inlineRadio1" value="1"> 男
                </label>
                <label class="radio-inline">
                    <input type="radio" name="sex" id="add_inlineRadio2" value="0"> 女
                </label>
            </div>
        </div>
        <div class="form-group">
            <label for="add_age" class="col-sm-2 control-label">年龄</label>
            <div class="col-sm-10">
                <input type="number" class="form-control" name="age" id="add_age" placeholder="age...">
            </div>
        </div>
        <div class="form-group">
            <label for="add_pay" class="col-sm-2 control-label">薪资</label>
            <div class="col-sm-10">
                <input type="number" class="form-control" name="pay" id="add_pay" placeholder="pay...">
            </div>
        </div>
        <div class="form-group">
            <label for="add_entryTime" class="col-sm-2 control-label">入职时间</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" name="entryTime" id="add_entryTime" placeholder="entryTime...">
            </div>
        </div>
        <div class="form-group">
            <label for="add_phone" class="col-sm-2 control-label">电话</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" name="phone" id="add_phone" placeholder="phone...">
            </div>
        </div>
        <div class="form-group">
            <label for="add_email" class="col-sm-2 control-label">邮箱</label>
            <div class="col-sm-10">
                <input type="email" class="form-control" name="email" id="add_email" placeholder="email...">
            </div>
        </div>
        <div class="form-group">
            <label for="" class="col-sm-2 control-label">角色</label>
            <div class="col-sm-10" id="add_roleDiv">
            </div>
        </div>
        <div class="form-group" id="add_areaDiv">
            <label class="col-sm-2 control-label">地区</label>
        </div>
    </form>

</span>

<span id="updateUserSpan" hidden>
    <form class="form-horizontal" action="" id="updateForm">
        <div class="form-group">
            <label for="update_userName" class="col-sm-2 control-label">用户名</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" readonly name="update_userName" id="update_userName"
                       placeholder="userName...">
            </div>
        </div>
        <div class="form-group">
            <label for="update_realName" class="col-sm-2 control-label">真实姓名</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" name="update_realName" id="update_realName"
                       placeholder="realName...">
            </div>
        </div>
        <div class="form-group">
            <label for="" class="col-sm-2 control-label">性别</label>
            <div class="col-sm-10">
                <label class="radio-inline">
                <input type="radio" name="update_sex" id="update_inlineRadio1" value="1"> 男
                </label>
                <label class="radio-inline">
                    <input type="radio" name="update_sex" id="update_inlineRadio2" value="0"> 女
                </label>
            </div>
        </div>
        <div class="form-group">
            <label for="update_age" class="col-sm-2 control-label">年龄</label>
            <div class="col-sm-10">
                <input type="number" class="form-control" name="update_age" id="update_age" placeholder="age...">
            </div>
        </div>
        <div class="form-group">
            <label for="update_pay" class="col-sm-2 control-label">薪资</label>
            <div class="col-sm-10">
                <input type="number" class="form-control" name="update_pay" id="update_pay" placeholder="pay...">
            </div>
        </div>
        <div class="form-group">
            <label for="update_entryTime" class="col-sm-2 control-label">入职时间</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" name="update_entryTime" id="update_entryTime"
                       placeholder="entryTime...">
            </div>
        </div>
        <div class="form-group">
            <label for="update_phone" class="col-sm-2 control-label">电话</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" name="update_phone" id="update_phone" placeholder="phone...">
            </div>
        </div>
        <div class="form-group">
            <label for="update_email" class="col-sm-2 control-label">邮箱</label>
            <div class="col-sm-10">
                <input type="email" class="form-control" name="update_email" id="update_email" placeholder="email...">
            </div>
        </div>
        <div class="form-group">
            <label for="" class="col-sm-2 control-label">角色</label>
            <div class="col-sm-10" id="update_roleDiv">
            </div>
        </div>
        <div class="form-group" id="update_areaDiv">
            <label class="col-sm-2 control-label" id="areaLable">地区</label>

        </div>
    </form>

</span>
<script>
    var datatable;

    //备份form
    var addUserSpan;
    var addUserHtml;
    var updateUserSpan;
    var updateUserHtml;
    $(document).ready(function () {
        //initValidator_search();
        initDataTables();
        initBindEvent();
        initDate();
        initRoleSelect("search");
        initRoleSelect("update");
        //initRoleCheckbox("search");
        //initRoleCheckbox("update");

        //备份添加和修改span中的内容
        addUserSpan = $('#addUserSpan');
        addUserHtml = addUserSpan.html();
        addUserSpan.html("");
        updateUserSpan = $('#updateUserSpan');
        updateUserHtml = updateUserSpan.html();

        // 初始化条件查询的地区下拉框
        initArea('areaSearchDiv', 0);

    });

    //初始化地区
    function initArea(elementId, areaId, obj, edit) {
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
                    if (edit)
                        $("#" + elementId, update_dialog).append(v_select);
                    else
                        $("#" + elementId).append(v_select);

                }
            }
        })
    }

    //条件查询
    function search() {
        //获取参数
        var userName = $("#userName").val();
        var realName = $("#realName").val();
        var beginAge = $("#beginAge").val();
        var endAge = $("#endAge").val();
        var minPay = $("#minPay").val();
        var maxPay = $("#maxPay").val();
        var beginEntryTime = $("#beginEntryTime").val();
        var endEntryTime = $("#endEntryTime").val();
        var roleIds = getSelected("search", null).toString();

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

        param.userName = userName;
        param.realName = realName;
        param.beginAge = beginAge;
        param.endAge = endAge;
        param.minPay = minPay;
        param.maxPay = maxPay;
        param.beginEntryTime = beginEntryTime;
        param.endEntryTime = endEntryTime;
        param.roleIds = roleIds;
        //传递参数
        datatable.settings()[0].ajax.data = param;
        datatable.ajax.reload();
    }

    <!-- export -->
    //导出excel
    function exportExcel() {
        //
        var v_area1 = $($("select[name='areaSelect']", $("#searchForm"))[0]).val();
        var v_area2 = $($("select[name='areaSelect']", $("#searchForm"))[1]).val();
        var v_area3 = $($("select[name='areaSelect']", $("#searchForm"))[2]).val();
        var v_area4 = $($("select[name='areaSelect']", $("#searchForm"))[3]).val();

        $('input[name=area1]').val(v_area1);
        $('input[name=area2]').val(v_area2);
        $('input[name=area3]').val(v_area3);
        $('input[name=area4]').val(v_area4);

        var searchForm = $("#searchForm");
        searchForm.prop('action', '/user/exportExcel.jhtml');
        searchForm.submit();
    }

    //导出pdf
    function exportPdf() {
        var searchForm = $("#searchForm");
        searchForm.prop('action', '/user/exportPdf.jhtml');
        searchForm.submit();
    }

    //导出word
    function exportWord() {
        var searchForm = $("#searchForm");
        searchForm.prop('action', '/user/exportWord.jhtml');
        searchForm.submit();
    }

    //显示添加弹框
    function showAddUser() {
        //var obj = $('#addUserSpan');
        //var html = obj.html();

        var dialog = bootbox.dialog({
            title: '用户添加',
            message: addUserHtml,
            size: 'large',
            buttons: {
                cancel: {
                    label: "<span class=\"glyphicon glyphicon-remove\" aria-hidden=\"true\"></span>&nbsp;取消",
                    className: 'btn-danger',
                    callback: function () {
                        console.log('Custom cancel clicked');
                    }
                },
                //自定义按钮
                /*noclose: {
                    label: "I don't close the modal!",
                    className: 'btn-warning',
                    callback: function(){
                        console.log('Custom button clicked');
                        return false;
                    }
                },*/

                ok: {
                    label: "<span class=\"glyphicon glyphicon-ok\" aria-hidden=\"true\"></span>&nbsp;确认",
                    className: 'btn-info',
                    callback: function () {
                        var validate = $("#addForm").data('bootstrapValidator').isValid();//获取验证结果
                        $("#addForm").data('bootstrapValidator').validate();//手动调用验证 显示所有验证提示
                        if (validate) {



                            //obj.html("");
                            var userName = $("[name=userName]", dialog).val();//$("[name=userName]")[1].value
                            var realName = $("[name=realName]", dialog).val();
                            var password = $("[name=password]").val();
                            var sex = $("[name=sex]:checked").val();
                            var age = $("[name=age]").val();
                            var pay = $("[name=pay]").val();
                            var entryTime = $("[name=entryTime]").val();
                            var phone = $("[name=phone]").val();
                            var email = $("[name=email]").val();
                            var roleIds = getSelected("add", null).toString();

                            //
                            var v_area1 = $($("select[name='areaSelect']", dialog)[0]).val();
                            var v_area2 = $($("select[name='areaSelect']", dialog)[1]).val();
                            var v_area3 = $($("select[name='areaSelect']", dialog)[2]).val();
                            var v_area4 = $($("select[name='areaSelect']", dialog)[3]).val();
                            var v_area1Name = $($("select[name='areaSelect']", dialog)[0]).attr("data-areaname");
                            var v_area2Name = $($("select[name='areaSelect']", dialog)[1]).attr("data-areaname");
                            var v_area3Name = $($("select[name='areaSelect']", dialog)[2]).attr("data-areaname");
                            var v_area4Name = $($("select[name='areaSelect']", dialog)[3]).attr("data-areaname");
                            var v_areaInfo = v_area1Name + "-->" + v_area2Name + '-->' + v_area3Name + '-->' + v_area4Name;

                            var param = {
                                userName: userName,
                                realName: realName,
                                password: password,
                                sex: sex,
                                age: age,
                                pay: pay,
                                entryTime: entryTime,
                                phone: phone,
                                email: email,
                                roleIds: roleIds
                            };

                            //
                            param.area1 = v_area1;
                            param.area2 = v_area2;
                            param.area3 = v_area3;
                            param.area4 = v_area4;
                            param.areaInfo = v_areaInfo;

                            //obj.html(html);
                            $.ajax({
                                url: '/user/addAjax.jhtml',
                                type: 'post',
                                data: param,
                                dataType: 'text',
                                success: function (res) {
                                    bootbox.alert(res);
                                    datatable.ajax.reload();
                                },
                                error: function () {
                                    alert('addUser失败');
                                }
                            });
                            console.log('Custom OK clicked');
                        } else {
                            alert('表单验证未通过');
                            return false;//阻止bootbox弹框消失
                        }
                    }
                }
            }
        });
        initValidator_add();
        initRoleSelect("add");
        //initRoleCheckbox("add");
        initAddDate();
        initArea('add_areaDiv', 0);
    }

    //显示修改弹框
    function showUpdateUser(id) {
        //阻止事件冒泡[事件传播]
        window.event.stopPropagation();
        $.ajax({
            url: '/user/findUser.jhtml',
            type: 'post',
            data: {'id': id},
            dataType: 'json',
            success: function (res) {
                if (res.code == 200) {
                    var v_user = res.data;

                    //角色复选下拉框回选
                    var roleIds = v_user.roleIds;
                    $("#update_roleSelect").selectpicker('val', roleIds);//val, id数组
                    /*for (var i = 0; i < roleIds.length; i++) {
                        $("#update_roleSelect option").each(function () {
                            if(roleIds[i] == this.value){
                                this.selected = true;
                                return;
                            }
                        });
                    }
                    $("#update_roleSelect").selectpicker('refresh');//刷新框中显示的值*/


                    /*//角色复选框回选
                    for (var i = 0; i < roleIds.length; i++) {
                        $("input[name=update_roleCheckbox]").each(function () {
                            if(roleIds[i] == this.value){
                                this.checked = true;
                                return;
                            }
                        });
                    }*/


                    $('#update_userName').val(v_user.userName);
                    $('#update_realName').val(v_user.realName);
                    $('[name=update_sex]').each(function () {
                        if ($(this).val() == v_user.sex) {
                            $(this).prop('checked', true);
                        }
                    });
                    $('#update_age').val(v_user.age);
                    $('#update_pay').val(v_user.pay);
                    $('#update_entryTime').val(v_user.entryTime);
                    $('#update_phone').val(v_user.phone);
                    $('#update_email').val(v_user.email);

                    $("#update_areaDiv").append("<label class='col-sm-2 control-label' id='areaInfoLabel'>" + v_user.areaInfo + "</label>");
                    $("#update_areaDiv").append("<button type=\"button\" class=\"btn btn-primary\" onclick=\"editArea('" + v_user.areaInfo + "');\"><span class=\"glyphicon glyphicon-pencil\" aria-hidden=\"true\"></span><span id=\"areaButtonText\">编辑</span></button>");

                    var dialog = bootbox.dialog({
                        title: '用户修改',
                        message: $('#updateUserSpan form'),
                        size: 'large',
                        buttons: {
                            cancel: {
                                label: "<span class=\"glyphicon glyphicon-remove\" aria-hidden=\"true\"></span>&nbsp;取消",
                                className: 'btn-danger',
                                callback: function () {
                                    console.log('Custom cancel clicked');
                                }
                            },
                            ok: {
                                label: "<span class=\"glyphicon glyphicon-ok\" aria-hidden=\"true\"></span>&nbsp;确认",
                                className: 'btn-info',
                                callback: function () {
                                    $("#updateForm", dialog).data('bootstrapValidator').validate();
                                    var validate = $("#updateForm", dialog).data('bootstrapValidator').isValid();
                                    if (validate) {
                                        var userName = $("#update_userName", dialog).val();//$("[name=userName]")[1].value
                                        var realName = $("#update_realName", dialog).val();
                                        var sex = $("[name=update_sex]:checked", dialog).val();
                                        var age = $("#update_age", dialog).val();
                                        var pay = $("#update_pay", dialog).val();
                                        var entryTime = $("#update_entryTime", dialog).val();
                                        var phone = $("#update_phone", dialog).val();
                                        var email = $("#update_email", dialog).val();
                                        var roleIds = getSelected("update", dialog).toString();

                                        //
                                        var v_area1;
                                        var v_area2;
                                        var v_area3;
                                        var v_area4;
                                        var v_areaName;
                                        if (editFlag == 0) {
                                            // 用户没有编辑
                                            v_area1 = v_user.area1;
                                            v_area2 = v_user.area2;
                                            v_area3 = v_user.area3;
                                            v_area4 = v_user.area4;
                                            v_areaName = v_user.areaInfo;
                                        } else {
                                            // 用户编辑了
                                            v_area1 = $($("select[name='areaSelect']", dialog)[0]).val();
                                            v_area2 = $($("select[name='areaSelect']", dialog)[1]).val();
                                            v_area3 = $($("select[name='areaSelect']", dialog)[2]).val();
                                            v_area4 = $($("select[name='areaSelect']", dialog)[3]).val();
                                            var v_area1Name = $($("select[name='areaSelect']", dialog)[0]).attr("data-areaname");
                                            var v_area2Name = $($("select[name='areaSelect']", dialog)[1]).attr("data-areaname");
                                            var v_area3Name = $($("select[name='areaSelect']", dialog)[2]).attr("data-areaname");
                                            var v_area4Name = $($("select[name='areaSelect']", dialog)[3]).attr("data-areaname");
                                            v_areaName = v_area1Name + "-->" + v_area2Name + '-->' + v_area3Name + '-->' + v_area4Name;
                                        }

                                        var param = {
                                            id: id,
                                            userName: userName,
                                            realName: realName,
                                            sex: sex,
                                            age: age,
                                            pay: pay,
                                            entryTime: entryTime,
                                            phone: phone,
                                            email: email,
                                            roleIds: roleIds
                                        };

                                        //
                                        param.area1 = v_area1;
                                        param.area2 = v_area2;
                                        param.area3 = v_area3;
                                        param.area3 = v_area4;
                                        param.areaInfo = v_areaName;


                                        $.ajax({
                                            url: '/user/updateAjax.jhtml',
                                            type: 'post',
                                            data: param,
                                            dataType: 'json',
                                            success: function (res) {
                                                if (res.code == 200) {
                                                    alert(res.msg);
                                                }
                                                datatable.ajax.reload();
                                            },
                                            error: function () {
                                                alert('updateUser失败');
                                            }
                                        });
                                    } else {
                                        //alert('表单验证未通过');
                                        return false;
                                    }
                                    console.log('Custom OK clicked');
                                }
                            }
                        }
                    });
                    update_dialog = dialog;
                    initValidator_update();
                    initUpdateDate();
                    initRoleSelect("update");
                    //initRoleCheckbox("update");
                    //还原
                    updateUserSpan.html(updateUserHtml);
                }
            }
        });
    }

    var update_dialog;
    var editFlag = 0;

    function editArea(areaName) {
        if (editFlag == 0) {
            // 清空地区名的label
            $("#areaInfoLabel", update_dialog).remove();
            // 改变按钮文字
            $("#areaButtonText", update_dialog).html("取消编辑");
            // 改变标志位
            editFlag = 1;
            // 添加下拉框
            initArea('update_areaDiv', 0, null, 1);
        } else {
            // 删除所有的地区下拉列表
            $("#update_areaDiv div", update_dialog).remove();
            // 改变按钮文字
            $("#areaButtonText", update_dialog).html("编辑");
            // 回填标签
            //$("#update_areaDiv", update).append("<label class='col-sm-4 control-label' id='areaInfoLabel'>"+areaName+"</label>");
            // append是在指定元素 内部的所有元素的后面 追加。
            // after是在指定元素的 后面 追加。
            $("#areaLable", update_dialog).after("<label class='col-sm-2 control-label' id='areaInfoLabel'>" + areaName + "</label>");
            // 重置标志位
            editFlag = 0;
        }
    }

    //删除用户
    function delUser(id) {
        //阻止事件冒泡[事件传播]
        window.event.stopPropagation();
        bootbox.confirm({
            message: "确认要删除此用户吗?",
            buttons: {
                confirm: {
                    label: '确认',
                    className: 'btn-success'
                },
                cancel: {
                    label: '取消',
                    className: 'btn-danger'
                }
            },
            callback: function (result) {
                console.log('This was logged in the callback: ' + result);
                if (result) {
                    $.ajax({
                        url: '/user/delete.jhtml',
                        type: 'post',
                        data: 'id=' + id,
                        dataType: 'json',
                        success: function (res) {
                            if (res.code == 200) {
                                //删除成功后移除ids中多余的id
                                ids.splice(ids.indexOf(id), 1);
                                bootbox.alert(res.msg);
                            }
                            datatable.ajax.reload();
                        },
                        error: function () {
                            alert('delUser失败');
                        }
                    });
                }
            }
        });


    }

    //解锁用户
    function unlockUser(id) {
        //阻止事件冒泡[事件传播]
        window.event.stopPropagation();
        $.ajax({
            url: '/user/unlockUser.jhtml',
            data: {'id': id},
            type: 'post',
            dataType: 'json',
            success: function (res) {
                if (res.code == 200) {
                    alert(res.msg);
                    //刷新datatables
                    datatable.ajax.reload();
                }
            }
        });
    }

    //重置密码
    function resetPassword(id) {
        //阻止事件冒泡[事件传播]
        window.event.stopPropagation();
        $.ajax({
            url: '/user/resetPassword.jhtml',
            type: 'post',
            data: {id: id},
            dataType: 'json',
            success: function (res) {
                if (res.code == 200) {
                    alert(res.msg);
                }
            }
        });
    }

    //重置复选下拉框
    function resetRoleSelect(selectId) {
        $('#' + selectId).selectpicker('deselectAll');
    }

    //获取复选下拉框选中的值 返回array
    function getSelected(prefix, dialog) {
        var roleIds = [];
        /*var roleSelecteds;
        if(dialog == null){
            roleSelecteds= $('#' + prefix + '_roleSelect option:selected');
        }else{
            roleSelecteds= $('#' + prefix + '_roleSelect option:selected',dialog);
        }
        roleSelecteds.each(function () {
            roleIds.push($(this).val());
        });*/
        roleIds = $('#' + prefix + '_roleSelect', dialog).val();
        console.log(roleIds);
        return roleIds;
    }

    //初始化角色复选下拉框
    function initRoleSelect(prefix) {
        $.ajax({
            url: "/role/list.jhtml",
            type: "post",
            dataType: "json",
            success: function (res) {
                var str = "";
                str += '<select id="' + prefix + '_roleSelect" name="roleIds" multiple>';
                for (var i = 0; i < res.length; i++) {
                    str += '<option value="' + res[i].id + '">';
                    str += res[i].roleName;
                    str += '</option>';
                }
                str += '</select>';
                $('#' + prefix + '_roleDiv').html(str);
                $('#' + prefix + '_roleSelect').selectpicker();//动态生成时直接给class='selectpicker'不行
            },
            error: function () {
                alert("初始化" + prefix + "复选框失败");
            }
        });
    }

    //获取角色复选框选中的array
    function getRoleChecked(prefix, dialog) {
        var roleIds = [];
        var roleCheckeds;
        if (dialog == null) {
            roleCheckeds = $("input[name=" + prefix + "_roleCheckbox]:checked");
        } else {
            roleCheckeds = $("input[name=" + prefix + "_roleCheckbox]:checked", dialog);
        }
        roleCheckeds.each(function () {
            roleIds.push($(this).val());
        });
        return roleIds;
    }

    //初始化角色复选框
    function initRoleCheckbox(prefix) {
        $.ajax({
            url: "/role/list.jhtml",
            type: "post",
            dataType: "json",
            success: function (res) {
                var str = "";
                for (var i = 0; i < res.length; i++) {
                    str += '<label class="checkbox-inline">';
                    str += '    <input type="checkbox" name="' + prefix + '_roleCheckbox" value="' + res[i].id + '">' + res[i].roleName;
                    str += '</label>';
                }
                $('#' + prefix + '_roleDiv').html(str);
            },
            error: function () {
                alert("初始化" + prefix + "复选框失败");
            }
        });
    }

    //初始化日期框 --datetimepicker
    function initDate() {
        $('#beginEntryTime').datetimepicker({
            format: 'YYYY-MM-DD',
            locale: 'zh-CN',
            showClear: true
        });
        $('#endEntryTime').datetimepicker({
            format: 'YYYY-MM-DD',
            locale: 'zh-CN',
            showClear: true
        });
    }

    function initAddDate() {
        $('[name=entryTime]').datetimepicker({
            format: 'YYYY-MM-DD',
            locale: 'zh-CN',
            showClear: true
        });
    }

    function initUpdateDate() {
        $('#update_entryTime').datetimepicker({
            format: 'YYYY-MM-DD',
            locale: 'zh-CN',
            showClear: true
        });
    }

    //初始化修改验证器
    function initValidator_update() {
        $('#updateForm').bootstrapValidator({
            message: 'This value is not valid',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                update_userName: {
                    validators: {
                        notEmpty: {
                            message: '用户名不能为空'
                        },
                        stringLength: {
                            min: 6,
                            max: 12,
                            message: '长度在6-12之间'
                        },
                        regexp: {
                            regexp: /^\w+$/,
                            message: '只能是数字和字母以及下划线'
                        }
                    }
                },
                update_email: {
                    validators: {
                        regexp: {
                            regexp: /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
                            message: '格式不对'
                        },
                        emailAddress: {
                            enabled: false//禁用默认
                        }
                    }
                },
                update_phone: {
                    validators: {
                        regexp: {
                            regexp: /^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\d{8}$/,
                            message: '请输入11位的手机号'
                        }
                    }
                }
            }
        });
    }

    //初始化添加验证器
    function initValidator_add() {
        $('#addForm').bootstrapValidator({
            message: 'This value is not valid',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                userName: {
                    validators: {//验证器
                        remote: {//发送异步请求进行验证
                            url: '/user/userUnique.jhtml',//返回值需为{"valid":true}的json格式
                            data: function (validator) {//传递参数
                                return {
                                    userName: validator.getFieldElements('userName').val()
                                };
                            },
                            message: '用户名已存在'//错误提示
                        },
                        notEmpty: {//非空
                            message: '用户名不能为空'
                        },
                        stringLength: {//长度
                            min: 6,
                            max: 12,
                            message: '长度在6-12之间'
                        },
                        regexp: {//正则
                            regexp: /^\w+$/,
                            message: '只能是数字和字母以及下划线'
                        }
                    }
                },
                password: {
                    validators: {
                        notEmpty: {
                            message: '密码不能为空'
                        },
                        stringLength: {
                            min: 6,
                            max: 8,
                            message: '长度在6-8之间'
                        },
                        regexp: {
                            regexp: /^\w+$/,
                            message: '只能是数字和字母以及下划线'
                        },
                        identical: {
                            field: 'confirmPassword'
                        }
                    }
                },
                confirmPassword: {//确认密码  需要双向绑定
                    validators: {
                        notEmpty: {
                            message: '确认密码不能为空'
                        },
                        identical: {
                            field: 'password'
                        }
                    }
                },
                email: {
                    validators: {
                        regexp: {
                            regexp: /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
                            message: '格式不对'
                        },
                        emailAddress: {
                            enabled: false//禁用默认
                        }
                    }
                },
                phone: {
                    validators: {
                        regexp: {
                            regexp: /^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\d{8}$/,
                            message: '请输入11位的手机号'
                        }
                    }
                }
            }
        });
    }

    //初始化搜索验证器
    function initValidator_search() {
        $('#searchForm').bootstrapValidator({
            message: 'This value is not valid',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                userName: {
                    validators: {
                        regexp: {
                            regexp: /^\w+$/,
                            message: '只能是数字和字母以及下划线'
                        }
                    }
                }
            }
        });
    }

    var ids = [];//批量删除的id数组
    //批量删除
    function batchDelete() {
        if (ids.length > 0) {
            bootbox.confirm({
                message: "确认要删除吗?",
                buttons: {
                    confirm: {
                        label: '确认',
                        className: 'btn-success'
                    },
                    cancel: {
                        label: '取消',
                        className: 'btn-danger'
                    }
                },
                callback: function (result) {
                    console.log('This was logged in the callback: ' + result);
                    if (result) {
                        $.ajax({//ajax批量删除
                            url: '/user/batchDelete.jhtml',
                            type: 'post',
                            data: {'ids': ids},
                            //dataType:'json',
                            success: function (res) {
                                //清空ids
                                ids = [];
                                //重新加载数据
                                datatable.ajax.reload();
                            },
                            error: function () {
                                bootbox.alert('删除失败');
                            }
                        });
                    }
                }
            });
        } else {
            bootbox.alert('选一个啊');
        }

    }

    //初始化复选框选中事件
    function initBindEvent() {
        $('#example tbody').on('click', 'tr', function () {
            var tr = $(this);
            var checkbox = tr.find('[type=checkbox]');
            var id = checkbox.val();
            var index = ids.indexOf(id);
            var flag = tr.attr('flag');
            if (!flag) {//!把js对象转换成boolean  null undefined "" 为false
                tr.attr('flag', '1');
                tr.css('background-color', 'blue');
                checkbox.prop('checked', true);
                //将选中行的id存入ids
                ids.push(id);
            } else {
                tr.removeAttr('flag');
                tr.css('background-color', '');
                checkbox.prop('checked', false);
                //取消选中后删除数组中的id
                if (index != -1)//如果index为-1就会删除数组末尾的元素
                    ids.splice(index, 1);//获取元素在array中的下标 没有则返回-1
            }
            console.log(ids)
        })
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
                "url": "/user/list.jhtml",
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
                {"data": "userName"},
                {"data": "realName"},
                {
                    "data": "sex",
                    "render": function (data, type, row, meta) {
                        return data == 1 ? "男" : "女";
                    }
                },
                {"data": "age"},
                {"data": "pay"},
                {"data": "entryTime"},
                {"data": "areaInfo"},
                {"data": "phone"},
                {"data": "email"},
                {"data": "roleNames"},
                {
                    "data": "lock",
                    "render": function (data, type, row, meta) {
                        return data ? "锁定" : "正常";
                    }
                },
                {
                    "data": "id",
                    "render": function (data, type, row, meta) {
                        //console.log(row);
                        return '<div class="btn-group" role="group" aria-label="...">' +
                            '<button type="button" class="btn btn-info" onclick="showUpdateUser(' + data + ')">' +
                            '    <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改' +
                            '</button>' +
                            '<button type="button" class="btn btn-danger" onclick="delUser(' + data + ')">' +
                            '    <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>删除' +
                            '</button>' +
                            '</div>' +
                            '<button type="button" ' + (row.lock ? '' : 'disabled') + ' class="btn btn-success" onclick="unlockUser(' + data + ')">' +
                            '    <span class="glyphicon glyphicon-lock" aria-hidden="true"></span>解锁' +
                            '</button>' +
                            '<button type="button" class="btn btn-success" onclick="resetPassword(' + data + ')">' +
                            '    <span class="glyphicon glyphicon-refresh" aria-hidden="true"></span>重置密码' +
                            '</button>'
                            ;
                    }
                }
            ],
            "drawCallback": function (s) {//fndrawCallback
                //复选框分页回选
                $('#example tbody tr').each(function () {
                    var tr = $(this);
                    var checkbox = tr.find('[type=checkbox]');
                    var index = ids.indexOf(checkbox.val());
                    if (index != -1) {
                        checkbox.prop('checked', true);
                        tr.css('background-color', 'blue');
                    }
                });
            },
            "initComplete": function () {

            }
        });

    }
</script>
</body>
</html>
