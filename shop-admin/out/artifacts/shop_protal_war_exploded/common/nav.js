window.onload = function () {//文件加载完成后执行 比 jquery初始化慢
}

//初始化会员信息
function initMemberInfo() {
    $.ajax({
        url: 'http://localhost:8086/member/memberInfo',
        success: function (res) {
            if (res.code == 200) {
                var member = res.data;
                if (member) {
                    $("#memberInfo").html("<a href=\"javascript:void(0)\">欢迎 " + member.realName + " 登录</a>");
                    $("#memberInfo").after("<li><a href=\"/view/cart.html\">购物车</a></li>");
                    $("#memberInfo").after("<li><a href=\"javascript:void(0)\" onclick='logout()'>退出</a></li>");
                }
            }
        }
    })
}

//退出 注销
function logout() {
    $.ajax({
        url: 'http://localhost:8086/member/logout',
        type: 'delete',
        beforeSend: function (xhr) {
            xhr.setRequestHeader("x-auth", $.cookie("my_token"))
        },
        success: function (res) {
            if (res.code == 200) {
                //退出成功 删除cookie中的token
                $.removeCookie("my_token", {path: '/'});//与登录同一样需要加 path
                location.href = "/";
            }
        }
    })
}

//ajax
function initAjaxSetup() {
    $.ajaxSetup({
        beforeSend: function (xhr) {
            var my_token = $.cookie("my_token");
            xhr.setRequestHeader("x-auth", my_token);
        }
    })
}

//添加商品
function addProduct(productId, count, flag) {
    $.ajax({
        url: 'http://localhost:8086/carts',
        type: 'post',
        data: {
            'productId': productId,
            'count': count
        },
        success: function (res) {
            if (res.code == 200) {
                if (flag) {
                    window.location.href = '/view/cart.html'
                }
                initCartInfo();
            } else {
                alert(res.msg);
            }
        }
    })
}


var linux_ip = '192.168.214.128';
var linux_nginx_ftp = 'http://' + linux_ip + '/img/'

var nav = '' +
    '<script src="/common/script.js"></script>' +
    '<nav class="navbar navbar-inverse">\n' +
    '    <div class="container-fluid">\n' +
    '        <!-- Brand and toggle get grouped for better mobile display -->\n' +
    '        <div class="navbar-header">\n' +
    '            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"\n' +
    '                    data-target="#bs-example-navbar-collapse-1" aria-expanded="false">\n' +
    '                <span class="sr-only">Toggle navigation</span>\n' +
    '                <span class="icon-bar"></span>\n' +
    '                <span class="icon-bar"></span>\n' +
    '                <span class="icon-bar"></span>\n' +
    '            </button>\n' +
    // '            <a class="navbar-brand" href="/">飞狐电商</a>\n' +   //样式冲突
    '        </div>\n' +
    '\n' +
    '        <!-- Collect the nav links, forms, and other content for toggling -->\n' +
    '        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">\n' +
    '            //<a class="navbar-brand" href="/">飞狐电商</a>\n' +   //样式冲突
    '\n' +
    '\n' +
    '            <ul class="nav navbar-nav" id="menu_ul">\n' +
    '\n' +
    '            </ul>\n' +
    '            <script>\n' +
    '               initAjaxSetup();\n' +
    '               initMemberInfo();' +
    '            </script>\n' +
    '\n' +
    '\n' +
    '            <!-- 右侧导航栏 -->\n' +
    '            <ul class="nav navbar-nav navbar-right">\n' +
    '                <li id="memberInfo"><a href="/view/login.html">登录</a></li>\n' +
    '                <li class="dropdown">\n' +
    '                    <a href="/view/register.html">注册会员</a>\n' +
    '                </li>\n' +
    '            </ul>\n' +
    '        </div><!-- /.navbar-collapse -->\n' +
    '\n' +
    '\n' +
    '    </div><!-- /.container-fluid -->\n' +
    '</nav>';

window.document.write(nav);

