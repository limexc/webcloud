<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!--网盘的主页，内容界面。当检查到用户未登录时自动转跳到login界面-->
<!DOCTYPE html>
<html>
<head>
    <!---->
    <meta charset="utf-8">
    <title>主页</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/default.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/index_main.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/index_main_table.css">
    <script type="application/javascript" src="${pageContext.request.contextPath}/static/js/spark-md5.js"></script>
    <script type="application/javascript" src="${pageContext.request.contextPath}/static/js/jquery-3.6.0.js"></script>
    <script type="application/javascript" src="${pageContext.request.contextPath}/static/js/upfile.js"></script>

    <!--Google字体-->
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+SC:wght@300&display=swap" rel="stylesheet">



    <!--Layui框架 第一次，不会用啊！！！淦！！！-->
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layui/css/layui.css"  media="all">
    <script src="${pageContext.request.contextPath}/static/layui/layui.js" charset="utf-8"></script>


</head>
<body>


<%
    //初步处理一下未登录就访问主页的用户
    if (session.getAttribute("user")==null){
        pageContext.forward("/login.jsp");
    }
%>

<%
    //关于路径问题，初步的解决方法
    String path = request.getContextPath();
    String basePath = request.getServerName() + ":" + request.getServerPort() + path + "/";
    String baseUrlPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<div class="head_menu" style="z-index: 999">


    <div class="layui-layout layui-layout-admin">
        <div class="layui-header">
            <div class="layui-logo">
                <h1 style="font-size: 46px;">
                    <a style=" color: lightblue" href="${pageContext.request.contextPath}/info/getfilelist" target="file_info_body">网盘</a>
                </h1>
            </div>
            <!-- 头部区域（可配合layui已有的水平导航） -->

            <!--头部区域结束-->
            <ul class="layui-nav layui-layout-right">
                <li class="layui-nav-item">
                    <a href="javascript:;">
                        <img src="${pageContext.request.contextPath}/static/images/dls.jpeg" class="layui-nav-img">
                        ${user.username}
                    </a>
                    <!--这块使用ajax进行加载-->
                    <dl class="layui-nav-child" id="menu_user_setting">

                    </dl>
                </li>
                <li class="layui-nav-item"><a href="${pageContext.request.contextPath}/system/logout">退了</a></li>
            </ul>
        </div>

</div>

<div class="menu_left">

    <ul class="layui-nav layui-nav-tree layui-nav-side layui-inline" lay-filter="demo" style="margin-top: 60px;">
        <li class="layui-nav-item layui-nav-itemed">
            <a href="javascript:;">我的文件</a>
            <dl class="layui-nav-child">
                <dd><a href="${pageContext.request.contextPath}/info/getfilelist?filetype=dtd" target="file_info_body">文档</a></dd>
                <dd><a href="${pageContext.request.contextPath}/info/getfilelist?filetype=pic" target="file_info_body">图片</a></dd>
                <dd><a href="${pageContext.request.contextPath}/info/getfilelist?filetype=video" target="file_info_body">视频</a></dd>
                <dd><a href="${pageContext.request.contextPath}/info/getfilelist?filetype=music" target="file_info_body">音乐</a></dd>
                <dd><a href="${pageContext.request.contextPath}/info/getfilelist?filetype=other" target="file_info_body">其他</a></dd>
                <!--<dd><a href="">跳转项</a></dd>-->
            </dl>
        </li>
        <li class="layui-nav-item"><a href="javascript:;">我的分享</a></li>
        <li class="layui-nav-item"><a href="javascript:;">容量配额:已使用${sessionScope.get("percentage")}</a></li>

            <div class="layui-progress">
                <div class="layui-progress-bar" lay-percent="${sessionScope.get("percentage")}"></div>
            </div>

        </li>
    </ul>

    </div>


</div>



<div class="file_main">
    <iframe name="file_info_body" frameborder="0" height="100%" width="100%" src="${pageContext.request.contextPath}/info/getfilelist">
        <!--表格数据区域-->
    </iframe>
</div>

<script>
    //注意进度条依赖 element 模块，否则无法进行正常渲染和功能性操作
    layui.use('element', function(){
        var element = layui.element;
    });
</script>

<script>
    load_menu_setting();

    function load_menu_setting() {
        //alert("加载菜单")
        $.ajax({
            url:"${pageContext.request.contextPath}/user/menu_setting",
            type : "post",
            success:function(data) {
                //alert("成功了\n"+data)
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
