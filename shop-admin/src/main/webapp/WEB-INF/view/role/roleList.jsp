<%--
  Created by IntelliJ IDEA.
  User: Ming
  Date: 2019/8/21
  Time: 20:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <!-- head and css -->
    <jsp:include page="/common/head.jsp"></jsp:include>
    <title>Title</title>

</head>
<body>
<jsp:include page="/common/nav.jsp"></jsp:include>
<%--展示--%>
<div class="container">
    <div style="background-color: #0facd2">
        <button class="btn btn-primary" onclick="addRole()"><i class="glyphicon glyphicon-plus"></i>添加</button>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-info">
                <div class="panel-heading">角色列表</div>
                <table id="roleTable" class="table table-striped table-hover table-striped table-bordered">
                    <thead>
                    <tr class="active">
                        <th style="text-align: center;">选择</th>
                        <th style="text-align: center;">id</th>
                        <th style="text-align: center;">角色名称</th>
                        <th style="text-align: center;">资源</th>
                        <th style="text-align: center;">操作</th>
                    </tr>
                    </thead>
                    <tbody id="tbody" style="text-align: center;font-weight: bold;"></tbody>
                    <tfoot>
                    <tr class="active">
                        <th style="text-align: center;">选择</th>
                        <th style="text-align: center;">id</th>
                        <th style="text-align: center;">角色名称</th>
                        <th style="text-align: center;">资源</th>
                        <th style="text-align: center;">操作</th>
                    </tr>
                    </tfoot>
                </table>
            </div>
        </div>
    </div>
</div>
<%--新增--%>
<div id="addRole" style="display: none;">
    <form class="form-horizontal">
        <div class="form-group">
            <label class="col-sm-2 control-label">角色名称</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="add_roleName" placeholder="请输入角色名称...">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">资源</label>
            <div class="col-sm-4">
                <ul id="add_ztree" class="ztree" style="width: 230px; overflow: auto;"></ul>
            </div>
        </div>
    </form>
</div>
<%--修改--%>
<div id="updateRole" style="display: none;">
    <form class="form-horizontal">
        <div class="form-group">
            <label class="col-sm-2 control-label">角色名称</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="update_roleName" placeholder="请输入角色名称...">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">资源</label>
            <div class="col-sm-4">
                <ul id="update_ztree" class="ztree" style="width: 230px; overflow: auto;"></ul>
            </div>
        </div>
    </form>
</div>
</body>
<script>
    var addRoleDiv;
    var updateRoleDiv;
    $(function () {
        findRoleByList();
        addRoleDiv = $("#addRole").html();
        updateRoleDiv = $("#updateRole").html();
    });
    var roleTable;

    /*刷新方法*/
    function search() {
        roleTable.ajax.reload();
    }

    /*这是查询角色的方法*/
    function findRoleByList() {
        var columName = [
            {
                "data": function (data) {
                    return '<input type="checkbox" value=""+data.id+""/>'
                }
            },
            {"data": "id"},
            {"data": "roleName"},
            {"data": "wealthNames"},
            {
                "data": "id",
                "render": function (data, type, row, meta) {
                    return '<div class="btn-group" role="group" aria-label="...">' +
                        '<button class="btn btn-danger btn-sm" onclick="deleteRole(\'' + data + '\')"><span class="glyphicon glyphicon-trash" style="color: #ffffff;"></span></button>' +
                        '<button class="btn btn-info btn-sm" data-toggle="modal" onclick="toUpdateRole(\'' + data + '\')" data-target="#myModal"><span class="glyphicon glyphicon-pencil" style="color: #ffffff;"></span></button>' +
                        '</div>'
                }
            },
        ];
        /* 渲染datatables */
        roleTable = $('#roleTable').DataTable({
            "processing": true,
            "serverSide": true,
            "ajax": {
                "url": "/role/findRoleByList.jhtml",
                "type": "POST"
            },
            searching: false,
            "lengthMenu": [5, 10, 20, 40, 80],
            language: {
                "url": "/js/DataTables-1.10.18/Chinese.json"
            },
            columns: columName,//设置列的初始化属性
        });
    }

    var add;

    /*这是添加角色的方法*/
    function addRole() {
        add = bootbox.dialog({
            title: '添加角色',
            message: $("#addRole form"),
            size: 'large',
            buttons: {
                cancel: {
                    label: "关闭",
                    className: 'btn-danger',
                    callback: function () {
                    }
                },
                confirm: {
                    label: "确定",
                    className: 'btn-primary',
                    callback: function () {
                        var v_roleName = $("#add_roleName", add).val();
                        //获取选中的资源
                        var atreeObj = $.fn.zTree.getZTreeObj("add_ztree");
                        var nodes = atreeObj.getCheckedNodes(true);
                        var ids = [];
                        if (nodes.length > 0) {
                            for (var i = 0; i < nodes.length; i++) {
                                ids.push(nodes[i].id);
                            }
                        }
                        $.ajax({
                            url: "/role/addRole.jhtml",
                            data: {
                                "roleName": v_roleName,
                                "wealthIds": ids.toString(),
                            },
                            type: "post",
                            dataType: "json",
                            success: function (res) {
                                if (res.code == "200") {
                                    console.log(res.msg);
                                    //刷新
                                    search();
                                }
                            }
                        })
                    }

                }
            }
        });
        $("#addRole").html(addRoleDiv);
        initZtreeList();
    }

    /*这是查询ztree菜单树的方法*/
    function initZtreeList() {
        $.ajax({
            url: "/wealth/findZtreeList.jhtml",
            data: "",
            dataType: "json",
            type: "post",
            success: function (res) {
                if (res.code == 200) {
                    var setting = {
                        data: {
                            simpleData: {
                                enable: true,
                            }
                        },
                        check: {
                            //开启复选框
                            enable: true,
                            //chkboxType: { "Y": "s", "N": "s" }
                        }
                    }
                    $.fn.zTree.init($("#add_ztree", add), setting, res.data);
                    /*var treeObj = $.fn.zTree.getZTreeObj("add_ztree");
                    treeObj.expandAll(true);*/
                }
            },
            error: function (res) {
                alert("败北");
            }
        })
    }

    /*这是修改div查询ztree菜单树的方法*/
    function initUpdateZtreeList() {
        $.ajax({
            url: "/wealth/findZtreeList.jhtml",
            data: "",
            dataType: "json",
            type: "post",
            async: false,
            success: function (res) {
                if (res.code == 200) {
                    var setting = {
                        data: {
                            simpleData: {
                                enable: true,
                            }
                        },
                        check: {
                            //开启复选框
                            enable: true,
                            chkboxType: {"Y": "ps", "N": "s"}
                        }
                    }
                    $.fn.zTree.init($("#update_ztree"), setting, res.data);
                    var treeObj = $.fn.zTree.getZTreeObj("update_ztree");
                    treeObj.expandAll(true);
                }
            },
            error: function (res) {
                alert("败北");
            }
        })
    }

    /*这是回显的方法*/
    function toUpdateRole(id) {
        initUpdateZtreeList();
        $.ajax({
            url: "/role/toUpdateRole.jhtml",
            data: {
                "id": id
            },
            dataType: "json",
            type: "post",
            success: function (res) {
                if (res.code == "200") {
                    $("#update_roleName").val(res.data.roleName);
                    var atreeObj = $.fn.zTree.getZTreeObj("update_ztree");
                    var v_wealthIds = res.data.wealthIds;
                    for (var i = 0; i < v_wealthIds.length; i++) {
                        var id = v_wealthIds[i];
                        var node = atreeObj.getNodeByParam("id", id, null);
                        atreeObj.checkNode(node, true, null);
                    }
                    updateRole(res.data.id);
                }
            }
        })
    }

    /*这是修改角色的方法*/
    var update;

    function updateRole(id) {
        update = bootbox.dialog({
            title: '修改角色',
            message: $("#updateRole form"),
            size: 'large',
            //closeButton:false,
            buttons: {
                cancel: {
                    label: "关闭",
                    className: 'btn-danger',
                    callback: function () {
                        //console.log('Custom cancel clicked');
                    }
                },
                confirm: {
                    label: "确定",
                    className: 'btn-primary',
                    callback: function () {
                        var v_roleName = $("#update_roleName", update).val();
                        var atreeObj = $.fn.zTree.getZTreeObj("update_ztree");
                        var nodes = atreeObj.getCheckedNodes(true);
                        var ids = [];
                        if (nodes.length > 0) {
                            for (var i = 0; i < nodes.length; i++) {
                                ids.push(nodes[i].id);
                            }
                        }
                        $.ajax({
                            url: "/role/updateRole.jhtml",
                            data: {
                                "roleName": v_roleName,
                                "id": id,
                                "wealthIds": ids.toString()
                            },
                            type: "post",
                            dataType: "json",
                            success: function (res) {
                                if (res.code == "200") {
                                    console.log(res.msg);
                                    //刷新
                                    search();
                                }
                            }
                        })
                    }
                }
            }
        });
        $("#updateRole").html(updateRoleDiv);
    }

    /*删除角色的方法*/
    function deleteRole(id) {
        bootbox.confirm({
            message: "你确定删除吗?",
            size: 'small',
            title: "提示信息",
            buttons: {
                confirm: {
                    label: '<span class="glyphicon glyphicon-ok"></span>确定',
                    className: 'btn-success'
                },
                cancel: {
                    label: '<span class="glyphicon glyphicon-remove"></span>取消',
                    className: 'btn-danger'
                }
            },
            callback: function (result) {
                if (result) {
                    $.ajax({
                        url: "/role/deleteRole.jhtml",
                        data: {
                            "id": id
                        },
                        dataType: "json",
                        type: "post",
                        success: function (res) {
                            if (res.code == "200") {
                                console.log(res.msg);
                                search();
                            }
                        }
                    })
                }
            }
        })
    }
</script>
</html>
