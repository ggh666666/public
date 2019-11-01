<%--
  Created by IntelliJ IDEA.
  User: ggh
  Date: 2019/9/2
  Time: 15:05
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
<jsp:include page="/common/nav.jsp"></jsp:include>
<h1>hi</h1>
<div id="node_0">
</div>
<script>
    console.log(factorial(5));

    //阶乘    一个正整数的阶乘（factorial）是所有小于及等于该数的正整数的积，并且0的阶乘为1。自然数n的阶乘写作n!
    function factorial(num) {//1 * 2 * 3... * n
        if (num <= 1) {
            console.log(num);
            return 1;
        }
        console.log(num + " * ");
        return num * factorial(num - 1);//n * n - 1
    }

    //递归 树
    var data = [
        {id: 99, name: '节点1', pid: 0},
        {id: 1, name: '节点1', pid: 99},
        {id: 2, name: '节点11', pid: 1},
        {id: 3, name: '节点12', pid: 1},
        {id: 4, name: '节点13', pid: 1},
        {id: 5, name: '节点2', pid: 99}
    ];
    var prefix = "node_";
    recursionTree(data, 0)

    function recursionTree(data, pid) {
        for (var i = data.length - 1; i >= 0; i--) {
            var node = $('#' + prefix + pid)
            var obj = data[i];
            if (obj.pid == pid) {

                var str = "";
                str += '<ul>';
                str += '<li id="' + prefix + obj.id + '">';
                str += obj.name;
                str += '</li>';
                str += '</ul>';

                node.append(str);
                //删除已拼接的元素
                data.splice(data.indexOf(obj), 1);
                if (data.length > 0)
                    recursionTree(data, obj.id);
            }
        }

    }


    function initTree(pid) {

    }

    function x() {

    }

    //递归 斐波那契数列
    //斐波那契数列（Fibonacci sequence），又称黄金分割数列、因数学家列昂纳多·斐波那契（Leonardoda Fibonacci）以兔子繁殖为例子而引入，故又称为“兔子数列”，
    // 指的是这样一个数列：1、1、2、3、5、8、13、21、34、……
    // 在数学上，斐波那契数列以如下被以递推的方法定义：F(1)=1，F(2)=1, F(n)=F(n-1)+F(n-2)（n>=3，n∈N*）
    // n>=3时 第n个数 = 前两个数字之和 第一个和第二个都为1
    function fibonacci(n) {
        if (n < 1)
            return 0;
        if (n == 1)
            return 1;
        if (n == 2)
            return 1;
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    //测试
    var fibonacci_sequence = [];
    for (var i = 1; i <= 20; i++) {
        fibonacci_sequence.push(fibonacci(i));
    }
    console.log(fibonacci_sequence);


    //高效 优化过的斐波那契
    var obj = {};
    getFB(1000)
    console.log(obj)

    function getFB(n) {
        i++;
        //求n位是多少,就先去obj里面看看,之前求过没有,如果之前求过,就直接取出来使用.
        if (obj[n] != undefined) {
            //如果进到了这里,说明当前这个n位已经求过,已经存进obj里面了
            return obj[n];
        } else {
            //如果进到这里来了,就说明当前这个n位之前没求过,没求过就求呗.
            if (n == 1 || n == 2) {
                obj[n] = 1;
                return 1;
            } else {
                obj[n] = getFB(n - 1) + getFB(n - 2);
                return obj[n];
            }
        }
    }

</script>
</body>
</html>
