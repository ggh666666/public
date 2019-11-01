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
<!-- vue -->
<!-- 1.x -->
<script src="/js/vue/vue.js"></script>
<!-- 2.x -->
<%--<script src="/js/vue/vue-2.6.10.js"></script>--%>
<script src="/js/axios/axios.js"></script>
<script src="https://cdn.bootcss.com/qs/6.8.0/qs.js"></script>
<span id="app">

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
                                <input type="email" class="form-control" id="productName" v-model="search_name"
                                       name="productName" placeholder="请输入商品名...">
                            </div>
                            <label class="col-sm-2 control-label">价格</label>
                            <div class="col-sm-4">
                                <div class="input-group">
                                    <input type="number" class="form-control" v-model="search_minPrice"
                                           id="search_minPrice" name="search_minPrice"
                                           placeholder="最小价格..."
                                           aria-describedby="basic-addon1">
                                    <span class="input-group-addon" id>
                                        <span class="glyphicon glyphicon-transfer" aria-hidden="true"></span>
                                    </span>
                                    <input type="number" class="form-control" v-model="search_maxPrice"
                                           id="search_maxPrice" name="search_maxPrice"
                                           placeholder="最大价格..."
                                           aria-describedby="basic-addon1">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div align="center">
                                <button class="btn btn-primary" type="button" v-on:click="search()"><i
                                        class="glyphicon glyphicon-search"></i>查询</button>
                                <button class="btn btn-default" type="reset"><i class="glyphicon glyphicon-refresh"></i>重置</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div style="text-align: left; background: #e4b9b9;">
                <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#form_model"
                        data-whatever="@mdo">
                    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>添加
                </button>
                <button type="button" class="btn btn-danger">
                    <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>批量删除
                </button>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-info">
                <div class="panel-heading">商品列表</div>
                <table id="table" class="table">
                    <thead>
                    <tr class="active">
                        <th style="text-align: center;">选择</th>
                        <th style="text-align: center;">id</th>
                        <th style="text-align: center;">商品名称</th>
                        <th style="text-align: center;">价格</th>
                        <th style="text-align: center;">操作</th>
                    </tr>
                    </thead>
                    <tbody id="tbody" style="text-align: center;font-weight: bold;">
                        <!-- 1.x -->
                        <tr v-for="obj in info">
                        <!-- 2.x -->
                        <%--<tr v-for="(obj,index) in info">--%>
                            <!-- 1.x -->
                            <td>{{$index}}</td>
                            <!-- 2.x -->
                            <%--<td>{{index}}</td>--%>
                            <td>{{obj.id}}</td>
                            <td>{{obj.name}}</td>
                            <td>{{obj.price}}</td>
                            <td>
                                <!-- 1.x -->
                                <button type="button" class="btn btn-info" v-on:click="find($index)" data-toggle="modal"
                                        data-target="#form_model" data-whatever="@mdo">
                               <!-- 2.x -->
                                <%--<button type="button" class="btn btn-info" v-on:click="find(index)"  data-toggle="modal" data-target="#form_model" data-whatever="@mdo">--%>
                                    <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
                                </button>
                                <!-- 1.x -->
                                <button type="button" class="btn btn-danger" v-on:click="del($index, obj.id)">
                                <!-- 2.x -->
                                <%--<button type="button" class="btn btn-danger" v-on:click="del(index, obj.id)">--%>
                                    <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>删除
                                </button>
                            </td>
                        </tr>
                    </tbody>
                    <tfoot>
                    <tr class="active">
                        <th style="text-align: center;">选择</th>
                        <th style="text-align: center;">id</th>
                        <th style="text-align: center;">商品名称</th>
                        <th style="text-align: center;">价格</th>
                        <th style="text-align: center;">操作</th>
                    </tr>
                    </tfoot>
                </table>
            </div>
        </div>
    </div>
</div>
<%--添加--%>
<div class="modal fade" id="form_model" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="exampleModalLabel" v-if="!id > 0">商品添加</h4>
                <h4 class="modal-title" id="exampleModalLabel_" v-if="id > 0">商品修改</h4>
            </div>
            <div class="modal-body">
                <form>
                    <div class="form-group">
                        <label for="add_name" class="control-label">商品名:</label>
                        <input type="text" class="form-control" v-model="name" id="add_name">
                        <input type="text" class="form-control" v-model="id" v-show="false" id="add_id">
                        <input type="text" class="form-control" v-model="index" v-show="false" id="add_index">
                    </div>
                    <div class="form-group">
                        <label for="add_price" class="control-label">价格:</label>
                        <textarea class="form-control" v-model="price" id="add_price"></textarea>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal" v-on:click="clear()">Close</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal" v-if="!id > 0"
                        v-on:click="add()">确认</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal" v-if="id > 0" v-on:click="update()">确认</button>
            </div>
        </div>
    </div>
</div>

<%--修改--%>

</span>
</body>
`
<script>
    $(function () {
        //	此事件在模态框被隐藏（并且同时在 CSS 过渡效果完成）之后被触发。
        $('#form_model').on('hidden.bs.modal', function (e) {
            //清空
            vue.clear();
        })
    })

    var vue = new Vue({
        el: '#app',
        data() {//初始化数据时 data(){} 和 data:{}    只能使用其一  2.0中即使不给初始值也需要声明 1.0则不用
            return {
                search_name: null,
                search_minPrice: null,
                search_maxPrice: null,
                index: null,
                id: null,
                name: null,
                price: null,
                info: null
            }
        },
        <!-- 1.x -->
        created() {//1.0 是 created 2.0 是 mounted
            <!-- 2.x -->
            //mounted () {//1.0 是 created 2.0 是 mounted
            axios
                .get('http://localhost:8088/commodity/list.jhtml')
                .then(response = > (this.info = response.data.data)
        )
        .
            catch(function (error) { // 请求失败处理
                console.log(error);
            });
        },
        methods: {
            clear: function () {
                this.index = null;
                this.id = null;
                this.name = null;
                this.price = null;
            },
            search: function () {
                var param = {};
                var name = this.search_name;
                var minPrice = this.search_minPrice;
                var maxPrice = this.search_maxPrice;
                if (name)
                    param.name = name;
                if (minPrice)
                    param.minPrice = minPrice;
                if (maxPrice)
                    param.maxPrice = maxPrice;
                axios
                    .get('http://localhost:8088/commodity/list.jhtml', {//get传参格式:{params:{key:value,key2:value2}}
                        params: param
                    })
                    .then(response = > (this.info = response.data.data)
            )
            .
                catch(function (error) { // 请求失败处理
                    console.log(error);
                });
            },
            add: function () {
                var info = this.info;
                var param = '';
                var name = this.name;
                var price = this.price;
                param += 'name=' + name;
                param += '&';
                param += 'price=' + price;
                var id;
                axios
                    .post('/commodity/add.jhtml', param)
                    .then(function (res) {
                        console.log(res);
                        id = res.data.data;
                        info.push({id: id, name: name, price: price});
                    })
                    .catch(function (error) {//请求失败处理
                        console.log(error);
                    });
            },
            find: function (index) {//回显
                this.index = index;
                var commodity = this.info[index];
                this.id = commodity.id;
                this.name = commodity.name;
                this.price = commodity.price;
                /*axios
                    .post('/commodity/add.jhtml', 'id=' + id)
                    .then(function (res) {
                        var commodity = res.data.data;
                    })
                    .catch(function (error) { //请求失败处理
                        console.log(error);
                    })*/
            },
            update: function () {
                var commodity = this.info[this.index];
                var name = this.name;
                var price = this.price;
                var id = this.id;
                var param = {
                    id: id,
                    name: name,
                    price: price
                };
                axios
                    .post('/commodity/update.jhtml', Qs.stringify(param))//post传参格式:key=value&key2=value2
                    .then(function (res) {
                        console.log(res);
                        //this.users.splice(this.ids.indexOf(id),1,{id:id, name:this.name, age:this.age});
                        commodity.name = name;
                        commodity.price = price;
                    })
                    .catch(function (error) {//请求失败处理
                        console.log(error);
                    });
                //this.users.splice(this.ids.indexOf(id),1,{id:id, name:this.name, age:this.age})
                //清空
            },
            del: function (index, id) {
                axios
                    .post('http://localhost:8088/commodity/delete.jhtml', 'id=' + id)
                    .then(response = > (this.info.splice(index, 1))
            )
            .
                catch(function (error) { // 请求失败处理
                    console.log(error);
                });
                /*axios({
                    method:'post',
                    url:'http://localhost:8088/commodity/delete.jhtml',
                    data:'id=' + id
                })
                    .then(response => (this.info.splice(index,1)))
            .catch(function (error) { // 请求失败处理
                    console.log(error);
                });*/
            }
        }
    })
</script>
</html>
