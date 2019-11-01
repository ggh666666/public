<%--
  Created by IntelliJ IDEA.
  Brand: ggh
  Date: 2019/8/24
  Time: 23:05
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

    <div class="row">
        <div class="col-md-12">
            <div style="text-align: left; background: #e4b9b9;">
                <button type="button" class="btn btn-primary" onclick="showAddBrand()">
                    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>添加
                </button>
                <button type="button" class="btn btn-danger">
                    <span class="glyphicon glyphicon-trash" aria-hidden="true">批量删除</span>
                </button>
                <button type="button" class="btn btn-danger" onclick="delCache()">
                    <span class="glyphicon glyphicon-trash" aria-hidden="true">清除缓存</span>
                </button>
            </div>
        </div>
    </div>

    <!-- datatables -->
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-info">
                <div class="panel-heading">品牌列表</div>
                <table id="example" class="table table-striped table-bordered" style="width:100%">
                    <thead>
                    <tr>
                        <th>id</th>
                        <th>品牌名</th>
                        <th>品牌图片</th>
                        <th>自定义排序</th>
                        <th>热销状态</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tfoot>
                    <tr>
                        <th>id</th>
                        <th>品牌名</th>
                        <th>品牌图片</th>
                        <th>自定义排序</th>
                        <th>热销状态</th>
                        <th>操作</th>
                    </tr>
                    </tfoot>
                </table>
            </div>

        </div>
    </div>
</div>

<span id="addBrandSpan" hidden>
    <form class="form-horizontal" action="" id="brandForm">
        <div class="form-group">
            <label for="add_brandName" class="col-sm-2 control-label">品牌名</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" name="brandName" id="add_brandName" placeholder="brandName...">
            </div>
        </div>
        <div class="form-group">
            <label for="add_brandImg" class="col-sm-2 control-label">品牌图片</label>
            <div class="col-sm-10">
                <input type="file" name="multipart" data-ref="add_brandImg" id="add_myfile" class="myfile" value=""/>
                <input type="hidden" id="add_brandImg" name="brandImg" value="">
            </div>
        </div>
        <div class="form-group">
            <label for="add_sort" class="col-sm-2 control-label">自定义排序</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" name="sort" id="add_sort" placeholder="sort...">
            </div>
        </div>
        <div class="form-group">
            <label for="add_sellState" class="col-sm-2 control-label">热销状态</label>
            <div class="col-sm-10">
                <input type="radio" name="add_sellState" value="1">热销
                <input type="radio" name="add_sellState" value="0" checked>不热销
            </div>
        </div>
    </form>

</span>

<span id="updateBrandSpan" hidden>
    <form class="form-horizontal" action="" id="updateBrandForm">
        <div class="form-group">
            <label for="update_brandName" class="col-sm-2 control-label">品牌名</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" name="update_brandName" id="update_brandName"
                       placeholder="brandName...">
            </div>
        </div>
        <div class="form-group">
            <label for="update_brandImg" class="col-sm-2 control-label">品牌图片</label>
            <div class="col-sm-10">
                <input type="file" name="multipart" data-ref="update_brandImg" id="update_myfile" class="myfile"
                       value=""/>
                <input type="hidden" id="update_brandImg" name="update_brandImg" value="">
            </div>
        </div>
        <div class="form-group">
            <label for="update_sort" class="col-sm-2 control-label">自定义排序</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" name="update_sort" id="update_sort" placeholder="sort...">
            </div>
        </div>
        <div class="form-group">
            <label for="update_sellState" class="col-sm-2 control-label">热销状态</label>
            <div class="col-sm-10">
                <input type="radio" name="update_sellState" value="1">热销
                <input type="radio" name="update_sellState" value="0" checked>不热销
            </div>
        </div>
    </form>

</span>
<script>
    var datatable;

    //备份form
    var addBrandSpan;
    var addBrandHtml;
    var updateBrandSpan;
    var updateBrandHtml;
    $(document).ready(function () {
        initDataTables();


        //备份添加和修改span中的内容
        addBrandSpan = $('#addBrandSpan');
        addBrandHtml = addBrandSpan.html();
        addBrandSpan.html("");
        updateBrandSpan = $('#updateBrandSpan');
        updateBrandHtml = updateBrandSpan.html();


    });

    //清除缓存
    function delCache() {
        $.ajax({
            url: '/cache/delCache.jhtml',
            type: 'post',
            data: {'key': 'hotList'},
            dataType: 'json',
            success: function (res) {
                if (res.code == 200)
                    alert(res.msg);
            }
        })
    }

    /*图片上传*/
    function uploadFile(id, fileName, dialog) {
        var s = {
            //上传的地址
            uploadUrl: "/file/upload.jhtml",
            uploadAsync: true, //默认异步上传
            showUpload: false, //是否显示上传按钮,跟随文本框的那个
            showRemove: false, //显示移除按钮,跟随文本框的那个
            showCaption: true,//是否显示标题,就是那个文本框
            showPreview: true, //是否显示预览,不写默认为true
            dropZoneEnabled: false,//是否显示拖拽区域，默认不写为true，但是会占用很大区域
            maxFileCount: 1, //表示允许同时上传的最大文件个数
            enctype: 'multipart/form-data',
            validateInitialCount: true,
            previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
            msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
            allowedFileTypes: ['image'],//配置允许文件上传的类型
            allowedPreviewTypes: ['image'],//配置所有的被预览文件类型
            allowedPreviewMimeTypes: ['jpg', 'png', 'gif'],//控制被预览的所有mime类型
            language: 'zh',
            //上传额外数据
            uploadExtraData: {"oldFileName": fileName}
        }

        //图片回显
        if (id, fileName) {// [第一个参数不影响判断结果]  同 if(fileName) --忘记为啥这么写了--
            // var url = 'http://localhost:8080/images/' + fileName;
            var url = 'https://aly-oss-ggh-test.oss-cn-beijing.aliyuncs.com/images/' + fileName;
            var urlArr = [];
            urlArr.push(url);
            s.initialPreviewAsData = true;//作为数据的初始预览(开启图片预览)
            s.initialPreview = [urlArr];//初始预览  (图片预览的url数组)
        }
        $("#" + id).fileinput(s);
        //异步上传返回结果处理
        $("#" + id).on('fileerror', function (event, data, msg) {
            console.log("fileerror");
            console.log(data);
        });
        //异步上传返回结果处理
        $("#" + id).on("fileuploaded", function (event, data, previewId, index) {
            if (data.response.code == -1)
                alert("上传失败")
            console.log("fileuploaded");
            var ref = $(this).attr("data-ref");
            if (dialog)
                $("#" + ref + "", dialog).val(data.response.data);
            else
                $("#" + ref + "").val(data.response.data);
        });
        //上传前
        $("#" + id).on('filepreupload', function (event, data, previewId, index) {
            console.log("filepreupload");
        });
    }

    //显示添加弹框
    function showAddBrand() {
        var dialog = bootbox.dialog({
            title: '品牌添加',
            message: addBrandHtml,
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
                        //obj.html("");
                        var brandName = $("[name=brandName]").val();//$("[name=brandName]")[1].value
                        var brandImg = $("[name=brandImg]").val();
                        var sort = $("[name=sort]").val();
                        var sellState = $("[name=sellState]:checked").val();
                        var param = {
                            brandName: brandName,
                            brandImg: brandImg,
                            sort: sort,
                            sellState: sellState
                        };
                        $.ajax({
                            url: '/brand/addAjax.jhtml',
                            type: 'post',
                            data: param,
                            dataType: 'text',
                            success: function (res) {
                                alert(res);
                                datatable.ajax.reload();
                            },
                            error: function () {
                                alert('addBrand失败');
                            }
                        });
                        console.log('Custom OK clicked');
                    }
                }
            }
        });
        uploadFile('add_myfile');
    }

    //显示修改弹框
    function showUpdateBrand(id) {
        $.ajax({
            url: '/brand/findBrand.jhtml',
            type: 'post',
            data: {'id': id},
            dataType: 'json',
            success: function (res) {
                if (res.code == 200) {
                    var v_brand = res.data;

                    $('#update_brandName').val(v_brand.brandName);
                    $('#update_brandImg').val(v_brand.brandImg);
                    $('#update_sort').val(v_brand.sort);
                    $('[name=update_sellState]').each(function () {
                        if (this.value == v_brand.sellState) {
                            this.checked = true;
                        }
                    })
                    var dialog = bootbox.dialog({
                        title: '品牌修改',
                        message: $('#updateBrandSpan form'),
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
                                    var brandName = $("#update_brandName", dialog).val();//$("[name=brandName]")[1].value
                                    var brandImg = $("#update_brandImg", dialog).val();
                                    var sort = $("#update_sort", dialog).val();
                                    var sellState = $("[name=update_sellState]:checked", dialog).val();
                                    var param = {
                                        id: id,
                                        brandName: brandName,
                                        brandImg: brandImg,
                                        sort: sort,
                                        sellState: sellState
                                    };
                                    $.ajax({
                                        url: '/brand/updateAjax.jhtml',
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
                                            alert('updateBrand失败');
                                        }
                                    });
                                    console.log('Custom OK clicked');
                                }
                            }
                        }
                    });
                    uploadFile('update_myfile', v_brand.brandImg, dialog);
                    //还原
                    updateBrandSpan.html(updateBrandHtml);
                }
            }
        });
    }

    //删除品牌
    function delBrand(id) {

        bootbox.confirm({
            message: "确认要删除此品牌吗?",
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
                        url: '/brand/delete.jhtml',
                        type: 'post',
                        data: 'id=' + id,
                        dataType: 'json',
                        success: function (res) {
                            if (res.code == 200) {
                                bootbox.alert(res.msg);
                            }
                            datatable.ajax.reload();
                        },
                        error: function () {
                            alert('delBrand失败');
                        }
                    });
                }
            }
        });


    }

    //更新自定义排序
    function updateBrandSort(id) {
        var sort = $('#sort_' + id).val();
        $.ajax({
            url: '/brand/updateBrandSort.jhtml',
            type: 'post',
            data: {'id': id, 'sort': sort},
            dataType: 'json',
            success: function (res) {
                if (res.code == 200)
                    datatable.ajax.reload();
            }
        })
    }

    //更新热销状态
    function updateBrandHot(id, hot) {
        $.ajax({
            url: '/brand/updateBrandHot.jhtml',
            type: 'post',
            data: {'id': id, 'sellState': hot},
            dataType: 'json',
            success: function (res) {
                if (res.code == 200)
                    datatable.ajax.reload();
            }
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
                "url": "/brand/list.jhtml",
                "type": "POST"
                //, "data": {ids: [1,2]}
            },
            "columns": [
                {"data": "id"},
                {"data": "brandName"},
                {
                    "data": "brandImg",
                    "render": function (data, type, row, meta) {
                        return '<img src="https://aly-oss-ggh-test.oss-cn-beijing.aliyuncs.com/images/' + data + '" style="height:150px">';
                        // return '<img src="/images/'+ data +'" style="height:150px">';
                    }
                },
                {
                    "data": "sort",
                    "render": function (data, type, row, meta) {
                        var id = row.id;
                        return '<input value="' + data + '" type="text" class="form-control" id="sort_' + id + '" placeholder="sort...">' +
                            '<button type="button" class="btn btn-primary" onclick="updateBrandSort(' + id + ')">' +
                            '    <span class="glyphicon glyphicon-pencil" aria-hidden="true">更新</span>' +
                            '</button>'
                            ;
                    }
                },
                {
                    "data": "sellState",
                    "render": function (data, type, row, meta) {
                        return data == 1 ? "<span style=\"color:red\" class=\"glyphicon glyphicon-fire\" aria-hidden=\"true\"></span>" : "不热销";
                    }
                },
                {
                    "data": "id",
                    "render": function (data, type, row, meta) {
                        console.log(row);
                        var hot = row.sellState;
                        return '<div class="btn-group" role="group" aria-label="...">' +
                            '<button type="button" class="btn btn-info">' +
                            '    <span class="glyphicon glyphicon-pencil" aria-hidden="true" onclick="showUpdateBrand(' + data + ')">修改</span>' +
                            '</button>' +
                            '<button type="button" class="btn btn-danger">' +
                            '    <span class="glyphicon glyphicon-trash" onclick="delBrand(' + data + ')" aria-hidden="true">删除</span>' +
                            '</button>' +
                            '<button type="button" class="btn btn-' + (hot == 1 ? "warning" : "success") + '">' +
                            '    <span class="glyphicon glyphicon-' + (hot == 1 ? "thumbs-down" : "thumbs-up") + '" onclick="updateBrandHot(' + data + ',' + (hot == 1 ? 0 : 1) + ')" aria-hidden="true">' + (hot == 1 ? "不热销" : "热销") + '</span>' +
                            '</button>' +
                            '</div>'
                            ;
                    }
                }
            ]
        });

    }
</script>
</body>
</html>

