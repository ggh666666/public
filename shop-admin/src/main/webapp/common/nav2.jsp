<%--
  Created by IntelliJ IDEA.
  User: ggh
  Date: 2019/8/28
  Time: 12:39
  To change this template use File | Settings | File Templates.
--%>
<!-- 设置编码 防止中文乱码 -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!-- javacript -->
<jsp:include page="/common/script.jsp"></jsp:include>
<!-- nav 导航条 -->
<style>
    .dropdown-submenu {
        position: relative;
    }

    .dropdown-submenu > .dropdown-menu {
        top: 0;
        left: 100%;
        margin-top: -6px;
        margin-left: -1px;
        -webkit-border-radius: 0 6px 6px 6px;
        -moz-border-radius: 0 6px 6px;
        border-radius: 0 6px 6px 6px;
    }

    .dropdown-submenu:hover > .dropdown-menu {
        display: block;
    }

    .dropdown-submenu > a:after {
        display: block;
        content: " ";
        float: right;
        width: 0;
        height: 0;
        border-color: transparent;
        border-style: solid;
        border-width: 5px 0 5px 5px;
        border-left-color: #ccc;
        margin-top: 5px;
        margin-right: -10px;
    }

    .dropdown-submenu:hover > a:after {
        border-left-color: #fff;
    }

    .dropdown-submenu.pull-left {
        float: none;
    }

    .dropdown-submenu.pull-left > .dropdown-menu {
        left: -100%;
        margin-left: 10px;
        -webkit-border-radius: 6px 0 6px 6px;
        -moz-border-radius: 6px 0 6px 6px;
        border-radius: 6px 0 6px 6px;
    }
</style>
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">飞狐电商后台管理</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">


            <ul class="nav navbar-nav" id="menu_ul">
                <%--<li><a href="/product/toList.jhtml">商品管理 <span class="sr-only">(current)</span></a></li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">品牌管理 <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="/brand/toList.jhtml">品牌列表</a></li>
                        <li><a href="#">品牌添加</a></li>
                        &lt;%&ndash;<li role="separator" class="divider"></li>&ndash;%&gt;<!-- 分割线 -->
                    </ul>
                </li>
                <li><a href="#">分类管理</a></li>
                <li><a href="#">日志管理</a></li>
                <li><a href="/area/toList.jhtml">地区管理</a></li>
                <li class="active"><a href="/user/toList.jhtml?">用户管理</a></li>--%>
            </ul>
            <script>
                initMenu();

                //初始化菜单栏
                function initMenu() {
                    $.ajax({
                        url: '/user/getMenu.jhtml',
                        type: 'post',
                        dataType: 'json',
                        success: function (res) {
                            if (res.code == 200) {
                                var menus = res.data;
                                var menu_ul = $('#menu_ul');
                                for (var i = 0; i < menus.length; i++) {
                                    var str = "";
                                    var menu = menus[i];
                                    var id = menu.id;
                                    var menuName = menu.memuName;
                                    var fatherId = menu.fatherId;
                                    var type = menu.menuType;
                                    var url = menu.url;


                                    /* -- 正常情况下菜单id都是跟添加顺序一样的 mysql查询默认id排序 所以不需要判断子父节点的拼接顺序 -- */
                                    //判断    id=1 的是唯一的根节点 所有节点都应在此节点之下
                                    if (fatherId == 1) {//如果=1 说明此节点为根节点下的节点 直接展示
                                        if (url && url != '#') {//如果=2 说明为按钮
                                            str += '<li role="presentation"><a href="' + url + '">' + menuName + '</a></li>';
                                        } else {//否则为下拉按钮
                                            str += '<li role="presentation" class="dropdown">';
                                            str += '    <a class="dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">';
                                            str += menuName + '<span class="caret"></span>';
                                            str += '    </a>';
                                            str += '    <ul class="dropdown-menu" id="father_' + id + '">';//拼接下拉选项的id
                                            str += '    </ul>';
                                            str += '</li>';
                                        }
                                        //拼接html
                                        menu_ul.append(str);
                                    } else {//如果!=1 说明此节点为子节点
                                        var father_ul = $('#father_' + fatherId);//通过下拉选项的id获取到ul
                                        if (url && url != '#') {//如果=2 说明为按钮
                                            str += '<li role="presentation"><a href="' + url + '">' + menuName + '</a></li>';
                                        } else {//目前不支持二级以上菜单 以支持 将样式设为class="dropdown-submenu"
                                            str += '<li role="presentation" class="dropdown-submenu">';
                                            str += '    <a href="#" >';
                                            str += menuName + '';
                                            str += '    </a>';
                                            str += '    <ul class="dropdown-menu" id="father_' + id + '">';
                                            str += '    </ul>';
                                            str += '</li>';
                                        }
                                        //拼接下拉的子节点
                                        father_ul.append(str);
                                    }
                                }
                            } else {
                                alert("导航栏" + res.msg);
                            }
                        },
                        error: function () {
                            alert('菜单栏加载失败');
                        }
                    })
                }
            </script>


            <ul class="nav navbar-nav navbar-right">
                <li>
                    <a href="#">

                        今天是第${user.todayLogins}次登录
                        <c:if test="${!empty lastLoginTime}">
                            上次登录时间${lastLoginTime}
                        </c:if>
                        欢迎${user.userName}登陆
                    </a>
                </li>
                <li><a href="/user/logout.jhtml">退出</a></li>
            </ul>
        </div><!-- /.navbar-collapse -->


    </div><!-- /.container-fluid -->
</nav>
