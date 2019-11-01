<%--
  Created by IntelliJ IDEA.
  User: ggh
  Date: 2019/9/21
  Time: 16:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <!-- head and css -->
    <jsp:include page="/common/head.jsp"></jsp:include>
    <title>Title</title>
</head>
<body>

<!-- 引入 nav 导航条 -->
<jsp:include page="/common/nav.jsp"></jsp:include>

<%--展示div--%>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-info">
                <div class="panel-heading">
                    分类管理
                    <input type="button" class="btn btn-danger btn-sm" value="添加" onclick="addCategory()"/>
                    <input type="button" class="btn btn-danger btn-sm" value="删除" onclick="deleteCategory()"/>
                    <input type="button" class="btn btn-info btn-sm" value="修改" onclick="updateCategory()"/>
                </div>
                <ul id="categoryTree" class="ztree" style="width: 230px; overflow: auto;"></ul>
            </div>
        </div>
    </div>
</div>
<%--新增--%>
<div id="addCategoryDiv" style="display: none;">
    <form class="form-horizontal">
        <div class="form-group">
            <label class="col-sm-2 control-label">类型名</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="add_categoryName" placeholder="请输入类型名...">
            </div>
        </div>
    </form>
</div>
<%--修改--%>
<div id="updateCategoryDiv" style="display: none;">
    <form class="form-horizontal">
        <div class="form-group">
            <label class="col-sm-2 control-label">类型名</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="update_categoryName" placeholder="请输入类型名...">
            </div>
        </div>
    </form>
</div>
</body>
<script>
    var v_addCategoryDiv;
    var v_updateCategoryDiv;
    $(function () {
        findCategoryList();
        //备份
        bulidDiv();
    })

    //查询
    function findCategoryList() {
        $.ajax({
            url: "/category/findCategoryList.jhtml",
            data: "",
            dataType: "json",
            type: "post",
            success: function (res) {
                if (res.code == 200) {
                    var setting = {
                        data: {
                            simpleData: {
                                enable: true,
                            },
                            key: {
                                url: "xUrl"
                            }
                        },
                        check: {
                            chkboxType: {"Y": "ps", "N": "s"}
                        }
                    }
                    $.fn.zTree.init($("#categoryTree"), setting, res.data);
                    var treeObj = $.fn.zTree.getZTreeObj("categoryTree");
                    treeObj.expandAll(true);
                }
            }
        })
    }

    //备份
    function bulidDiv() {
        v_addCategoryDiv = $("#addCategoryDiv").html();
        v_updateCategoryDiv = $("#updateCategoryDiv").html();
    }

    /**
     * 新增节点
     */
    var add;

    function addCategory() {
        var treeObj = $.fn.zTree.getZTreeObj("categoryTree");
        var nodes = treeObj.getSelectedNodes();
        //等于1说明选择了父节点
        if (nodes.length == 1) {
            add = bootbox.dialog({
                title: '添加资源',
                message: $("#addCategoryDiv form"),
                size: 'large',
                buttons: {
                    cancel: {
                        label: "关闭",
                        className: 'btn-danger',
                        callback: function () {
                            //console.log('Custom cancel clicked');
                        }
                    },
                    confirm: {
                        label: "添加",
                        className: 'btn-primary',
                        callback: function () {
                            var pid = nodes[0].id;
                            var name = $("#add_categoryName", add).val();
                            $.ajax({
                                url: "/category/add.jhtml",
                                data: {
                                    "fatherId": pid,
                                    "categoryName": name,
                                },
                                dataType: "json",
                                type: "post",
                                success: function (res) {
                                    if (res.code == 200) {
                                        var node = {"id": res.data, "pId": pid, "name": name};
                                        treeObj.addNodes(nodes[0], node);
                                    }
                                }
                            })
                        }
                    }
                }
            });
            $("#addCategoryDiv").html(v_addCategoryDiv);
        } else if (nodes.length > 1) {
            bootbox.alert({
                message: "<span class='glyphicon glyphicon-exclamation-sign'></span>只能选择一个节点",
                size: 'small',
                title: "提示信息"
            });
        } else {
            bootbox.alert({
                message: "<span class='glyphicon glyphicon-exclamation-sign'></span>请选择一个节点",
                size: 'small',
                title: "提示信息"
            });
        }
    }

    //修改
    var update;

    function updateCategory() {
        var treeObj = $.fn.zTree.getZTreeObj("categoryTree");
        var nodes = treeObj.getSelectedNodes();
        if (nodes.length == 1) {
            var name = nodes[0].name;
            $("#update_categoryName").val(name);
            update = bootbox.dialog({
                title: '修改资源',
                message: $("#updateCategoryDiv form"),
                size: 'large',
                buttons: {
                    cancel: {
                        label: "关闭",
                        className: 'btn-danger',
                        callback: function () {
                            //console.log('Custom cancel clicked');
                        }
                    },
                    confirm: {
                        label: "修改",
                        className: 'btn-primary',
                        callback: function () {
                            var id = nodes[0].id;
                            var name = $("#update_categoryName", update).val();
                            $.ajax({
                                url: "/category/updateCategory.jhtml",
                                data: {
                                    "id": id,
                                    "categoryName": name,
                                },
                                dataType: "json",
                                type: "post",
                                success: function (res) {
                                    if (res.code == 200) {
                                        nodes[0].name = name;
                                        //更新
                                        treeObj.updateNode(nodes[0]);
                                    }
                                }
                            })
                        }
                    }
                }
            });
            $("#updateWealthDiv").html(updateWealthDiv);
        } else if (nodes.length > 1) {
            bootbox.alert({
                message: "<span class='glyphicon glyphicon-exclamation-sign'></span>只能选择一个节点",
                size: 'small',
                title: "提示信息"
            });
        } else {
            bootbox.alert({
                message: "<span class='glyphicon glyphicon-exclamation-sign'></span>请选择一个节点",
                size: 'small',
                title: "提示信息"
            });
        }
    }

    //删除
    function deleteCategory() {
        var newTree = $.fn.zTree.getZTreeObj("categoryTree");
        var nodes = newTree.getSelectedNodes();
        if (nodes.length > 0) {
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
                    //这个result是boolean类型的 点确认是true 反之false
                    if (result) {
                        //获取被选中节点的所有子节点
                        var nodeArr = newTree.transformToArray(nodes);
                        var ids = [];
                        for (var i = 0; i < nodeArr.length; i++) {
                            ids.push(nodeArr[i].id);
                        }
                        $.ajax({
                            url: "/category/deleteCategory.jhtml",
                            type: "post",
                            data: {
                                "ids": ids,
                            },
                            dataType: "json",
                            success: function (res) {
                                if (res.code == 200) {
                                    for (var i = 0; i < nodeArr.length; i++) {
                                        newTree.removeNode(nodeArr[i]);
                                    }
                                }
                            }
                        })
                    }
                }
            })
        } else {
            bootbox.alert({
                message: "<span class='glyphicon glyphicon-exclamation-sign'></span>请选择一个节点",
                size: 'small',
                title: "提示信息"
            });
        }
    }
</script>
</html>
