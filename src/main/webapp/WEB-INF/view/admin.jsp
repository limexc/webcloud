<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>管理系统</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layui/css/layui.css">
    <script type="application/javascript" src="${pageContext.request.contextPath}/static/js/jquery-3.6.0.js"></script>
</head>
<body class="layui-layout-body">

<%
    //初步处理一下未登录就访问主页的用户
    if (session.getAttribute("user")==null){
        pageContext.forward("/login.jsp");
    }
%>

<div class="layui-layout layui-layout-admin">
    <div class="layui-header">
        <div class="layui-logo">网盘管理</div>
        <!-- 头部区域（可配合layui已有的水平导航） -->
        <ul class="layui-nav layui-layout-left">
            <li class="layui-nav-item"><a href="${pageContext.request.contextPath}" >返回主页</a></li>
            <li class="layui-nav-item"><a href="${pageContext.request.contextPath}/admin/sysinfopage" target="info_body">系统信息</a></li>
            <li class="layui-nav-item"><a href="${pageContext.request.contextPath}/admin/filelistpage" target="info_body">文件管理</a></li>
            <li class="layui-nav-item"><a href="${pageContext.request.contextPath}/admin/userlistpage" target="info_body">用户管理</a></li>
        </ul>
        <ul class="layui-nav layui-layout-right">
            <li class="layui-nav-item">
                <a href="javascript:;">
                    <img src="${sessionScope.get("profile")}" class="layui-nav-img">
                    ${user.username}
                </a>
                <dl class="layui-nav-child" id="menu_user_setting">
                </dl>
            </li>
            <li class="layui-nav-item"><a href="${pageContext.request.contextPath}/system/logout">退出登陆</a></li>
        </ul>
    </div>

    <div class="layui-side layui-bg-black">
        <div class="layui-side-scroll">
            <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
            <ul class="layui-nav layui-nav-tree"  lay-filter="test">
                <li class="layui-nav-item">
                    <a href="${pageContext.request.contextPath}/admin/sysinfopage" target="info_body">系统信息</a>
                </li>
                <li class="layui-nav-item">
                    <a href="${pageContext.request.contextPath}/admin/filelistpage" target="info_body">文件管理</a>
                </li>
                <li class="layui-nav-item">
                    <a href="${pageContext.request.contextPath}/admin/userlistpage" target="info_body">用户管理</a>
                </li>

                <li class="layui-nav-item">
                    <a href="javascript:;">工单请求</a>
                    <dl class="layui-nav-child">
                        <dd><a href="${pageContext.request.contextPath}/admin/massagepage?type=0" target="info_body">未处理工单</a></dd>
                        <dd><a href="${pageContext.request.contextPath}/admin/massagepage?type=3" target="info_body">正在处理工单</a></dd>
                        <dd><a href="${pageContext.request.contextPath}/admin/massagepage?type=2" target="info_body">已驳回工单</a></dd>
                        <dd><a href="${pageContext.request.contextPath}/admin/massagepage?type=1" target="info_body">已完成工单</a></dd>
                        <!--<dd><a href="">跳转项</a></dd>-->
                    </dl>
                </li>

                <li class="layui-nav-item">
                    <a href="${pageContext.request.contextPath}/admin/sendmailpage" target="info_body">发送邮件</a>
                </li>

            </ul>
        </div>
    </div>

    <div class="layui-body">
        <!-- 内容主体区域 -->
        <div style="padding: 60px; overflow: auto; width: 98%; height: 85%;background-color: cornflowerblue">
            <iframe name="info_body" frameborder="0" height="96%" width="96%" src="${pageContext.request.contextPath}/admin/sysinfopage"></iframe>






        </div>
    </div>

    <div class="layui-footer">
        <!-- 底部固定区域 -->
        © 底部固定区域
    </div>
</div>
<script src="${pageContext.request.contextPath}/static/layui/layui.js"></script>
<script>
    //JavaScript代码区域
    layui.use('element', function(){
        var element = layui.element;

    });
</script>

<script>
    load_menu_setting();

    function load_menu_setting() {
        $.ajax({
            url:"${pageContext.request.contextPath}/user/menu_setting",
            type : "post",
            success:function(data) {
                $("#menu_user_setting").html(data);

            },
            error:function (){
                alert("加载失败")
            }

        })
    }


</script>
</body>
</html>