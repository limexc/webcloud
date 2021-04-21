<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<%
    //关于路径问题，初步的解决方法
    String path = request.getContextPath();
    String basePath = request.getServerName() + ":" + request.getServerPort() + path + "/";
    String baseUrlPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<%
    //初步处理一下未登录就访问的用户
    if (session.getAttribute("user")==null){
        pageContext.forward("/login.jsp");
    }
%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/default.css" />
<script type="application/javascript" src="${pageContext.request.contextPath}/static/js/jquery-3.6.0.js"></script>



<!--Layui框架-->
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/layui/css/layui.css"  media="all">
<script src="${pageContext.request.contextPath}/static/layui/layui.js" charset="utf-8"></script>


<div>
    <div style="display: inline-block;width: 200px;height: 200px;">
        <a id="user_img">
            <img width="200px" height="200px" src="${sessionScope.get("profile")}">
        </a>
    </div><br>
    <span>用户名：${user.username}</span> <a id="change_name_a">修改</a> <br>
    <span>用户组：${group.name}</span><br>
    <span>用户邮箱：${user.email}</span><br>
    <span>注册时间：${user.create_at}</span><br>
    <span>账户状态：${status}</span><br>
    <span>空间大小：${size}</span><br>
    <span style="color: red">注销账户</span>
</div>


<script>
    layui.use(['layer','jquery'],function () {
        // 定义使用jquery和layer
        let $ = layui.jquery;
        let layer = layui.layer;

        $("#user_img").click(function () {
            alertUp();
        })

    })

    function alertUp(){

        layer.open({
            type: 2,
            title: '上传图像',
            shadeClose: true,
            shade: false,
            maxmin: false, //开启最大化最小化按钮
            area: ['400px', '400px'],
            content: 'http://localhost:8080${pageContext.request.contextPath}/static/pages/upimage.html'
        });

    }


</script>