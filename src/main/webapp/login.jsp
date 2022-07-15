<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <!---->
    <title>用户登录</title>
    <meta charset="utf-8">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/login.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/default.css" />
    <script type="application/javascript" src="${pageContext.request.contextPath}/static/js/jquery-3.6.0.js"></script>

    <!--Layui框架-->
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layui/css/layui.css"  media="all">
    <script src="${pageContext.request.contextPath}/static/layui/layui.js" charset="utf-8"></script>

    <!--css以及js文件引入  end-->


</head>
<body>

<%
    //初步处理一下登陆后还访问登陆页的用户
    if (session.getAttribute("user")!=null){
        pageContext.forward("/user/main");
    }
%>

	<!--登陆对话框窗体-->
    <div class="login">
        <h1>网 盘</h1>
		<div class="login_main">
			<form name="loginform" method="post" action="${pageContext.request.contextPath}/system/login">
                <span class="login_tips">邮箱</span><br />
                <input class="login_input_text" name="account" placeholder="邮箱" id="accountText" type="text"/>
                <br />
                <span class="login_tips">密码</span><br />
				<input class="login_input_text" name="password" placeholder="密码" id="passwordText" type="password"/>
                <br />
                <span class="other_tips"><a id="refind" href="${pageContext.request.contextPath}/resetpwdpage/">忘记密码</a>或<a id="signup" href="${pageContext.request.contextPath}/register/">注册账号</a></span><br />
                <input class="login_btn" type="submit" value="登录" />
			</form>

		</div>
    </div>
<script>
    let loginmsg = "${requestScope.get("msg")}";

    window.onload = function (){
        if (loginmsg === "err"){
            layui.use("layer",function() {
                var layer = layui.layer;
                layer.msg("用户名或密码错误");
            })
        }
    }

</script>


    <div id="background_wrap"></div>

</body>
</html>
