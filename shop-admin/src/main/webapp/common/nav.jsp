<%@ page import="com.fh.shop.admin.util.DistributedSession" %>
<%@ page import="com.fh.shop.admin.util.RedisUtil" %>
<%@ page import="com.fh.shop.admin.util.KeyUtil" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ page import="com.fh.shop.admin.po.user.User" %>
<%@ page import="com.alibaba.fastjson.JSON" %><%--
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

            </ul>
            <script>
                $.ajaxSetup({
                    complete: function (xhr, status) {//success(result,status,xhr)
                        var res = xhr.responseJSON;//获取回调函数返回值
                        if (res) {
                            if (res.code) {//非 null undefined ''判断
                                if (res.code != 200) {
                                    bootbox.alert({
                                        message: "<span class='glyphicon glyphicon-exclamation-sign'></span>" + res.msg,
                                        size: 'small',
                                        title: "提示信息"
                                    });
                                    setTimeout(function () {
                                        bootbox.hideAll();
                                    }, 2000);
                                }
                            }
                        }


                    }
                })
                var menus;
                var menu_html = "";
                var menu_ul;
                initMenu();

                //初始化菜单栏
                function initMenu() {
                    $.ajax({
                        url: '/user/getMenu.jhtml',
                        type: 'post',
                        dataType: 'json',
                        success: function (res) {
                            if (res.code == 200) {
                                menus = res.data;
                                menu_ul = $('#menu_ul');
                                buildMenu(1, 1);
                                //拼接html
                                menu_ul.append(menu_html);
                            } else {
                                alert("导航栏" + res.msg);
                            }
                        },
                        error: function () {
                            alert('菜单栏加载失败');
                        }
                    })
                }

                <!-- 递归实现 -->
                function buildMenu(pid, level) {
                    var children = getChildren(pid);
                    for (var i = 0; i < children.length; i++) {
                        var menu = children[i];
                        var id = menu.id;
                        var menuName = menu.memuName;
                        var fatherId = menu.fatherId;
                        var url = menu.url;

                        var flag = hasChild(id);
                        /* -- 正常情况下菜单id都是跟添加顺序一样的 mysql查询默认id排序 所以不需要判断子父节点的拼接顺序 -- */
                        //判断    id=1 的是唯一的根节点 所有节点都应在此节点之下
                        if (level == 1) {//如果=1 说明此节点为根节点下的节点 直接展示
                            if (flag == false) {//如果没有子节点则直接拼接
                                menu_html += '<li role="presentation"><a href="' + url + '">' + menuName + '</a></li>';
                            } else {//否则为下拉按钮
                                menu_html += '<li role="presentation" class="dropdown">';
                                menu_html += '    <a class="dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">';
                                menu_html += menuName + '<span class="caret"></span>';
                                menu_html += '    </a>';
                                menu_html += '    <ul class="dropdown-menu" id="father_' + id + '">';//拼接下拉选项的id
                                buildMenu(id, ++level);
                                menu_html += '    </ul>';
                                menu_html += '</li>';
                            }

                        } else {//如果!=1 说明此节点为子节点
                            if (flag == false) {//如果没有子节点
                                menu_html += '<li role="presentation"><a href="' + url + '">' + menuName + '</a></li>';
                            } else {//目前不支持二级以上菜单 以支持 将样式设为class="dropdown-submenu"
                                menu_html += '<li role="presentation" class="dropdown-submenu">';
                                menu_html += '    <a href="#" >';
                                menu_html += menuName + '';
                                menu_html += '    </a>';
                                menu_html += '    <ul class="dropdown-menu" id="father_' + id + '">';
                                buildMenu(id, ++level);
                                menu_html += '    </ul>';
                                menu_html += '</li>';
                            }
                        }
                    }
                }

                function getChildren(pid) {
                    var children = [];
                    for (var i = 0; i < menus.length; i++) {
                        var menu = menus[i];
                        if (menu.fatherId == pid)
                            children.push(menu);
                    }
                    return children;
                }

                function hasChild(pid) {
                    for (var i = 0; i < menus.length; i++) {
                        if (pid == menus[i].fatherId)
                            return true;
                    }
                    return false;
                }


                initLoginInfo();

                //初始化登录信息   (菜单栏右上角)
                function initLoginInfo() {
                    $.ajax({
                        url: '/user/loginInfo.jhtml',
                        type: 'post',
                        dataType: 'json',
                        success: function (res) {
                            var user = res.data.user;
                            var lastLoginTime = res.data.lastLoginTime;
                            var html = $('#loginInfo').html();
                            html = html.replace("##todayLogins##", user.todayLogins)
                                .replace("##lastLoginTime##", lastLoginTime ? ("上次登录时间" + lastLoginTime) : "")
                                .replace("##userName##", user.userName);
                            $('#loginInfo').html(html)
                        }
                    })
                }
            </script>


            <!-- 右侧导航栏 -->
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <a href="#" id="loginInfo">

                        <%-- 今天是第${user.todayLogins}次登录
                         <c:if test="${!empty lastLoginTime}">
                             上次登录时间${lastLoginTime}
                         </c:if>
                         欢迎${user.userName}登陆--%>
                        <%--<%
                            String sessionId = DistributedSession.getSessionId(request, response);
                        %>
                           <%
                               String userJson = RedisUtil.get(KeyUtil.buildCurrUserKey(sessionId));
                               User user = JSON.parseObject(userJson, User.class);
                           %>
                        今天是第<%=user.getTodayLogins()%>次登录
                        <c:if test="<%=StringUtils.isNotEmpty(RedisUtil.get(KeyUtil.buildLastLoginTimeKey(sessionId)))%>">
                            上次登录时间<%=RedisUtil.get(KeyUtil.buildLastLoginTimeKey(sessionId))%>
                        </c:if>
                        欢迎<%=user.getUserName()%>登陆--%>

                        今天是第##todayLogins##次登录

                        ##lastLoginTime##

                        欢迎##userName##登陆
                    </a>
                </li>
                <li><a href="/user/logout.jhtml">退出</a></li>
                <li class="dropdown">
                    <a href="/user/toUpdatePassword.jhtml">修改密码</a>
                </li>
            </ul>
        </div><!-- /.navbar-collapse -->


    </div><!-- /.container-fluid -->
</nav>


