<%--
  Created by IntelliJ IDEA.
  User: ggh
  Date: 2019/8/23
  Time: 12:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <!-- head and css -->
    <jsp:include page="/common/head.jsp"></jsp:include>
    <title>商品展示页面</title>
    <%--<link href="/js/bootstrap-3.3.7-dist/css/bootstrap.css" rel="stylesheet">--%>
    <link rel="stylesheet" href="/js/DataTables-1.10.18/css/dataTables.bootstrap.min.css"/>
    <link rel="stylesheet" href="/js/bootstrap-datetimepicker/css/bootstrap-datetimepicker.css"/>

</head>
<body>

<!-- 引入 nav 导航条 -->
<jsp:include page="/common/nav.jsp"></jsp:include>

<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-info">
                <div class="panel-heading">商品查询</div>
                <div class="panel-body">
                    <form class="form-horizontal" id="productForm">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">商品名称</label>
                            <div class="col-sm-4">
                                <input type="email" class="form-control" id="productName" name="productName"
                                       placeholder="请输入商品名...">
                            </div>
                            <label class="col-sm-2 control-label">价格</label>
                            <div class="col-sm-4">
                                <input type="email" class="form-control" id="price" name="price" placeholder="请输入价格...">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">生产日期</label>
                            <div class="col-sm-4">
                                <div class="input-group">
                                    <input type="text" class="form-control" id="minTime" name="minTime"
                                           placeholder="开始日期..." aria-describedby="basic-addon2">
                                    <span class="input-group-addon"><i class="glyphicon glyphicon-transfer"></i></span>
                                    <input type="text" class="form-control" id="maxTime" name="maxTime"
                                           placeholder="结束日期..." aria-describedby="basic-addon2">
                                </div>
                            </div>
                        </div>

                        //
                        <input type="hidden" name="category1">
                        <input type="hidden" name="category2">
                        <input type="hidden" name="category3">
                        <div class="form-group" id="categorySearchDiv">
                            <label class="col-sm-2 control-label">分类</label>

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
    <div style="background-color: #0facd2">
        <button class="btn btn-primary" onclick="add()"><i class="glyphicon glyphicon-plus"></i>添加</button>
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
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-info">
                <div class="panel-heading">商品列表</div>
                <table id="productTable" class="table table-striped table-hover table-striped table-bordered">
                    <thead>
                    <tr class="active">
                        <th style="text-align: center;">选择</th>
                        <th style="text-align: center;">id</th>
                        <th style="text-align: center;">商品名称</th>
                        <th style="text-align: center;">价格</th>
                        <th style="text-align: center;">商品主图</th>
                        <th style="text-align: center;">分类</th>
                        <th style="text-align: center;">生产日期</th>
                        <th style="text-align: center;">库存</th>
                        <th style="text-align: center;">热销状态</th>
                        <th style="text-align: center;">上架状态</th>
                        <th style="text-align: center;">操作</th>
                    </tr>
                    </thead>
                    <tbody id="tbody" style="text-align: center;font-weight: bold;"></tbody>
                    <tfoot>
                    <tr class="active">
                        <th style="text-align: center;">选择</th>
                        <th style="text-align: center;">id</th>
                        <th style="text-align: center;">商品名称</th>
                        <th style="text-align: center;">价格</th>
                        <th style="text-align: center;">商品主图</th>
                        <th style="text-align: center;">分类</th>
                        <th style="text-align: center;">生产日期</th>
                        <th style="text-align: center;">库存</th>
                        <th style="text-align: center;">热销状态</th>
                        <th style="text-align: center;">上架状态</th>
                        <th style="text-align: center;">操作</th>
                    </tr>
                    </tfoot>
                </table>
            </div>
        </div>
    </div>
</div>
<%--添加--%>
<div id="addProduct" style="display: none;">
    <form class="form-horizontal">
        <div class="form-group">
            <label class="col-sm-2 control-label">商品名称</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="add_productName" placeholder="请输入商品名称...">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">价格</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="add_price" placeholder="请输入价格...">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">生产日期</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="add_producedDate" placeholder="请选择生产日期...">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">产品主图</label>
            <div class="col-sm-10">
                <input type="file" name="myfile" data-ref="add_mainImagePath" class="col-sm-10 myfile" value=""/>
                <input type="hidden" id="add_mainImagePath" value="">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">库存</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="add_stock" placeholder="请输入库存...">
            </div>
        </div>
        <div class="form-group" id="add_categoryDiv">
            <label class="col-sm-2 control-label">分类</label>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">热销状态</label>
            <div class="col-sm-4">
                <input type="radio" name="add_sellState" value="1">热销
                <input type="radio" name="add_sellState" value="2">不热销
            </div>
        </div>
    </form>
</div>
<%--修改--%>
<div id="updateProduct" style="display: none;">
    <form class="form-horizontal">
        <input type="hidden" id="id">
        <div class="form-group">
            <label class="col-sm-2 control-label">商品名称</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="update_productName" placeholder="请输入商品名称...">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">价格</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="update_price" placeholder="请输入价格...">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">生产日期</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="update_producedDate" placeholder="请选择生产日期...">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">产品主图</label>
            <div class="col-sm-10">
                <input type="file" name="myfile" data-ref="update_mainImagePath" class="col-sm-10 myfile" value=""/>
                <input type="hidden" id="update_mainImagePath" value="">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">库存</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="update_stock" placeholder="请输入库存...">
            </div>
        </div>
        <div class="form-group" id="update_categoryDiv">
            <label class="col-sm-2 control-label" id="categoryLable">分类</label>

        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">热销状态</label>
            <div class="col-sm-4">
                <input type="radio" name="update_sellState" value="1">热销
                <input type="radio" name="update_sellState" value="2">不热销
            </div>
        </div>
    </form>
</div>
</body>

<script>
    var addProductDiv;
    var updateProductDiv;
    $(function () {
        toList();
        //备份
        addProductDiv = $("#addProduct").html();
        $("#addProduct").html("");
        updateProductDiv = $("#updateProduct").html();
        $("#updateProduct").html("");
        uploadFile();
        addTime("add_producedDate");
        addTime("update_producedDate");
        addTime("minTime");
        addTime("maxTime");

        // 初始化条件查询的分类下拉框
        initCategory('categorySearchDiv', 0);
    })

    //初始化分类
    function initCategory(elementId, categoryId, obj) {
        if (obj) {
            // 删除父级的之后的同级元素
            $(obj).parent().nextAll().remove();
            // 动态给下拉列表添加属性
            $(obj).attr("data-categoryName", obj.options[obj.selectedIndex].text);
        }
        $.ajax({
            type: "post",
            url: "/category/findChildCategoryList.jhtml",
            data: {"id": categoryId},
            success: function (data) {
                if (data.code == 200) {
                    if (data.data.length == 0) {
                        return;
                    }
                    // 在指定的div中追加select
                    var v_select = '<div class="col-sm-2"><select class="form-control" name="categorySelect" onchange="initCategory(\'' + elementId + '\', this.value, this)"><option value="-1">==请选择==</option>';
                    var v_cateArr = data.data;
                    for (var i = 0; i < v_cateArr.length; i++) {
                        v_select += "<option value='" + v_cateArr[i].id + "' data-categoryName='" + v_cateArr[i].categoryName + "'>" + v_cateArr[i].categoryName + "</option>";
                    }
                    v_select += "</select></div>";
                    $("#" + elementId).append(v_select);

                }
            }
        })
    }

    var productTable;

    /*查询商品*/
    function toList() {
        var columName = [
            {
                "data": "id",
                "render": function (data) {//这个data就是咱们查到的pageInfo中的数据集合里的对象
                    return '<input name="ids" type="checkbox" value="' + data + '"/>'
                }
            },
            {"data": "id"},
            {"data": "productName"},
            {"data": "price"},
            {
                "data": "mainImagePath",
                "render": function (data, type, row, meta) {
                    return '<img src="http://192.168.214.128/img/' + data + '" height="150px"/>'
                    //return '<img src="http://192.168.124.79/img/'+data+'" height="150px"/>'
                }
            },
            {"data": "categoryInfo"},
            {"data": "producedDate"},
            {"data": "stock"},
            {
                "data": "sellState",
                "render": function (data) {
                    return data == 1 ? "热销" : "不热销";
                }
            },
            {
                "data": "status",
                "render": function (data) {
                    return data == 1 ? "正常" : "下架";
                }
            },
            {
                "data": "id",
                "render": function (data, type, row, meta) {
                    return '<div class="btn-group" role="group" aria-label="...">' +
                        '<button class="btn btn-danger btn-sm" onclick="deleteProduct(\'' + data + '\',\'' + row.mainImagePath + '\')"><span class="glyphicon glyphicon-trash" style="color: #ffffff;"></span>删除</button>' +
                        '<button class="btn btn-info btn-sm" data-toggle="modal" data-target="#myModal" onclick="toUpdateProduct(\'' + data + '\')"><span class="glyphicon glyphicon-pencil" style="color: #ffffff;"></span>修改</button>' +
                        '<button class="' + (row.status == 1 ? "btn btn-info btn-sm" : "btn btn-success btn-sm") + '" data-toggle="modal" data-target="#myModal" onclick="updateStatus(' + row.status + ',' + data + ')">' + (row.status == 1 ? "下架" : "上架") + '</button>' +
                        '</div>'
                }
            },
        ];
        /* 渲染datatables */
        productTable = $('#productTable').DataTable({
            "processing": true,
            "serverSide": true,
            "ordering": false,//是否启用排序
            "ajax": {
                "url": "/product/productList.jhtml",
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

    /*上架 下架*/
    function updateStatus(status, id) {
        bootbox.confirm({
            message: status == 1 ? "你确定要下架吗?" : "你确定要上架吗?",
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
                    $.ajax({
                        url: "/product/updateStatus.jhtml",
                        type: "post",
                        data: {
                            "status": status,
                            "id": id
                        },
                        dataType: "json",
                        success: function (res) {
                            if (res.code == 200) {
                                console.log(res.msg);
                                search();
                            }
                        }
                    })
                }
            }
        })
    }

    /*这是刷新方法*/
    function search() {
        var productName = $("#productName").val();
        var price = $("#price").val();
        var minTime = $("#minTime").val();
        var maxTime = $("#maxTime").val();

        //
        var v_category1 = $($("select[name='categorySelect']", $("#productForm"))[0]).val();
        var v_category2 = $($("select[name='categorySelect']", $("#productForm"))[1]).val();
        var v_category3 = $($("select[name='categorySelect']", $("#productForm"))[2]).val();

        var v_param = {};

        //
        v_param.category1 = v_category1;
        v_param.category2 = v_category2;
        v_param.category3 = v_category3;

        v_param.productName = productName;
        v_param.price = price;
        v_param.minTime = minTime;
        v_param.maxTime = maxTime;
        productTable.settings()[0].ajax.data = v_param;
        productTable.ajax.reload();
    }

    /*添加商品*/
    function add() {
        var add = bootbox.dialog({
            title: '添加商品',
            // message: $("#addProduct form"),
            message: addProductDiv,
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
                        var v_productName = $("#add_productName", add).val();
                        var v_price = $("#add_price", add).val();
                        var v_producedDate = $("#add_producedDate", add).val();
                        var v_mainImagePath = $("#add_mainImagePath").val();
                        var v_stock = $("#add_stock", add).val();
                        var v_sellState = $("[name='add_sellState']:checked", add).val();

                        //
                        var v_category1 = $($("select[name='categorySelect']", add)[0]).val();
                        var v_category2 = $($("select[name='categorySelect']", add)[1]).val();
                        var v_category3 = $($("select[name='categorySelect']", add)[2]).val();
                        var v_category1Name = $($("select[name='categorySelect']", add)[0]).attr("data-categoryname");
                        var v_category2Name = $($("select[name='categorySelect']", add)[1]).attr("data-categoryname");
                        var v_category3Name = $($("select[name='categorySelect']", add)[2]).attr("data-categoryname");
                        var v_categoryInfo = v_category1Name + "-->" + v_category2Name + '-->' + v_category3Name;

                        var v_param = {};

                        //
                        v_param.category1 = v_category1;
                        v_param.category2 = v_category2;
                        v_param.category3 = v_category3;
                        v_param.categoryInfo = v_categoryInfo;

                        v_param.productName = v_productName;
                        v_param.price = v_price;
                        v_param.mainImagePath = v_mainImagePath;
                        v_param.producedDate = v_producedDate;
                        v_param.stock = v_stock;
                        v_param.sellState = v_sellState;
                        $.ajax({
                            url: "/product/add.jhtml",
                            data: v_param,
                            type: "post",
                            dataType: "json",
                            success: function (res) {
                                if (res.code == "200") {
                                    console.log(res.msg);
                                    search();
                                }
                            }
                        })
                    }
                }
            }
        });
        //还原
        // $("#addProduct").html(addProductDiv);
        uploadFile();
        addTime("add_producedDate");
        initCategory('add_categoryDiv', 0);
    }

    var oldUrl = '';

    /*图片上传*/
    function uploadFile() {
        $(".myfile").fileinput({
            //上传的地址
            uploadUrl: "/product/uploadFile.jhtml",
            uploadAsync: true, //默认异步上传
            showUpload: false, //是否显示上传按钮,跟随文本框的那个
            showRemove: false, //显示移除按钮,跟随文本框的那个
            showCaption: true,//是否显示标题,就是那个文本框
            showPreview: true, //是否显示预览,不写默认为true
            dropZoneEnabled: false,//是否显示拖拽区域，默认不写为true，但是会占用很大区域
            //minImageWidth: 50, //图片的最小宽度
            //minImageHeight: 50,//图片的最小高度
            //maxImageWidth: 1000,//图片的最大宽度
            //maxImageHeight: 1000,//图片的最大高度
            //maxFileSize: 0,//单位为kb，如果为0表示不限制文件大小
            //minFileCount: 0,
            maxFileCount: 1, //表示允许同时上传的最大文件个数
            enctype: 'multipart/form-data',
            validateInitialCount: true,
            previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
            msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
            allowedFileTypes: ['image'],//配置允许文件上传的类型
            allowedPreviewTypes: ['image'],//配置所有的被预览文件类型
            allowedPreviewMimeTypes: ['jpg', 'png', 'gif'],//控制被预览的所有mime类型
            language: 'zh'
        })
        //异步上传返回结果处理
        $('.myfile').on('fileerror', function (event, data, msg) {
            console.log("fileerror");
            console.log(data);
        });
        //异步上传返回结果处理
        $(".myfile").on("fileuploaded", function (event, data, previewId, index) {
            console.log("fileuploaded");
            var ref = $(this).attr("data-ref");
            $("#" + ref + "").val(data.response.url);
        });
        //上传前
        $('.myfile').on('filepreupload', function (event, data, previewId, index) {
            console.log("filepreupload");
        });
    }

    /*时间插件*/
    function addTime(name) {
        $("#" + name).datetimepicker({
            format: 'YYYY-MM-DD',
            locale: 'zh-CN',
            showClear: true
        })
    }

    /*删除*/
    function deleteProduct(id, mainImagePath) {
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
                    //删除
                    $.ajax({
                        url: "/product/deleteProduct.jhtml",
                        type: "post",
                        data: {
                            "id": id,
                            "mainImagePath": mainImagePath
                        },
                        dataType: "json",
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

    /*回显数据*/
    function toUpdateProduct(id) {
        $.ajax({
            url: "/product/toUpdateProduct.jhtml",
            data: {
                "id": id
            },
            dataType: "json",
            type: "post",
            success: function (res) {
                if (res.code == "200") {
                    var data = res.data;
                    updateProduct(data);
                    $("#id").val(data.id);
                    $("#update_productName").val(data.productName);
                    $("#update_price").val(data.price);
                    $("#update_producedDate").val(data.producedDate);
                    $("#update_mainImagePath").val(data.mainImagePath);
                    oldUrl = data.mainImagePath;
                    $("#update_stock").val(data.stock);
                    $("[name='update_sellState']").each(function () {
                        if ($(this).val() == data.sellState) {
                            $(this).attr("checked", true);
                        }
                    })
                    $("#update_categoryDiv").append("<label class='col-sm-2 control-label' id='categoryInfoLabel'>" + data.categoryInfo + "</label>");
                    $("#update_categoryDiv").append("<button type=\"button\" class=\"btn btn-primary\" onclick=\"editCategory('" + data.categoryInfo + "');\"><span class=\"glyphicon glyphicon-pencil\" aria-hidden=\"true\"></span><span id=\"cateButtonText\">编辑</span></button>");

                } else {
                    console.log(res.msg);
                }
            }
        })
    }

    /*修改*/
    function updateProduct(data) {
        var update = bootbox.dialog({
            title: '修改用户',
            // message: $("#updateProduct form"),
            message: updateProductDiv,
            size: 'large',
            buttons: {
                cancel: {
                    label: "关闭",
                    className: 'btn-danger',
                    callback: function () {
                        console.log('Custom cancel clicked');
                    }
                },
                confirm: {
                    label: "修改",
                    className: 'btn-primary',
                    callback: function () {
                        //图片删除
                        if (oldUrl != "") {
                            $.ajax({
                                url: '/product/deleteFile.jhtml',
                                type: 'post',
                                data: 'url=' + oldUrl,
                                dataType: 'json',
                                success: function (res) {

                                },
                                error: function () {
                                    alert('删除旧图片失败');
                                }
                            });
                        }

                        var id = $("#id", update).val();
                        var v_productName = $("#update_productName", update).val();
                        var v_price = $("#update_price", update).val();
                        var v_producedDate = $("#update_producedDate", update).val();
                        var v_mainImagePath = $("#update_mainImagePath").val();

                        //
                        var v_category1;
                        var v_category2;
                        var v_category3;
                        var v_categoryName;
                        if (editFlag == 0) {
                            // 用户没有编辑
                            v_category1 = data.category1;
                            v_category2 = data.category2;
                            v_category3 = data.category3;
                            v_categoryName = data.categoryInfo;
                        } else {
                            // 用户编辑了
                            v_category1 = $($("select[name='categorySelect']", update)[0]).val();
                            v_category2 = $($("select[name='categorySelect']", update)[1]).val();
                            v_category3 = $($("select[name='categorySelect']", update)[2]).val();
                            v_category4 = $($("select[name='categorySelect']", update)[2]).text();
                            alert(v_category4)
                            var v_category1Name = $($("select[name='categorySelect']", update)[0]).attr("data-categoryname");
                            var v_category2Name = $($("select[name='categorySelect']", update)[1]).attr("data-categoryname");
                            var v_category3Name = $($("select[name='categorySelect']", update)[2]).attr("data-categoryname");
                            v_categoryName = v_category1Name + "-->" + v_category2Name + '-->' + v_category3Name;
                        }

                        var v_param = {};

                        //
                        v_param.category1 = v_category1;
                        v_param.category2 = v_category2;
                        v_param.category3 = v_category3;
                        v_param.categoryInfo = v_categoryName;

                        v_param.productName = v_productName;
                        v_param.price = v_price;
                        v_param.mainImagePath = v_mainImagePath;
                        v_param.producedDate = v_producedDate;
                        v_param.id = id;
                        $.ajax({
                            url: "/product/updateProduct.jhtml",
                            data: v_param,
                            dataType: "json",
                            type: "post",
                            success: function (res) {
                                console.log(res.msg);
                                search();
                            }
                        })
                    }
                }
            }
        })
        //还原
        // $("#updateProduct").html(updateProductDiv);
        uploadFile();
        addTime("update_producedDate");
    }

    var editFlag = 0;

    function editCategory(categoryName) {
        if (editFlag == 0) {
            // 清空分类名的label
            $("#categoryInfoLabel").remove();
            // 改变按钮文字
            $("#cateButtonText").html("取消编辑");
            // 改变标志位
            editFlag = 1;
            // 添加下拉框
            initCategory('update_categoryDiv', 0);
        } else {
            // 删除所有的分类下拉列表
            $("#update_categoryDiv div").remove();
            // 改变按钮文字
            $("#cateButtonText").html("编辑");
            // 回填标签
            //$("#update_categoryDiv", update).append("<label class='col-sm-4 control-label' id='categoryInfoLabel'>"+categoryName+"</label>");
            // append是在指定元素 内部的所有元素的后面 追加。
            // after是在指定元素的 后面 追加。
            $("#categoryLable").after("<label class='col-sm-2 control-label' id='categoryInfoLabel'>" + categoryName + "</label>");
            // 重置标志位
            editFlag = 0;
        }
    }


    /*导出excel*/
    function exportExcel() {
        //
        var v_category1 = $($("select[name='categorySelect']", $("#productForm"))[0]).val();
        var v_category2 = $($("select[name='categorySelect']", $("#productForm"))[1]).val();
        var v_category3 = $($("select[name='categorySelect']", $("#productForm"))[2]).val();

        $('input[name=category1]').val(v_category1);
        $('input[name=category2]').val(v_category2);
        $('input[name=category3]').val(v_category3);

        var productForm = document.getElementById("productForm");
        productForm.action = "/product/exportExcel.jhtml";
        productForm.method = "post";
        productForm.submit();
    }

    /*导出pdf*/
    function exportPdf() {
        var productForm = document.getElementById("productForm");
        productForm.action = "/product/exportPdf.jhtml";
        productForm.method = "post";
        productForm.submit();
    }

    //导出word
    function exportWord() {
        // 获取form表单
        var v_productForm = document.getElementById("productForm");
        // 动态设置action属性
        v_productForm.action = "/product/exportWord.jhtml";
        v_productForm.method = "post";
        v_productForm.submit();
    }
</script>
</html>
