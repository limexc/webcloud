<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户登录</title>
    <link type="text/css" href="${pageContext.request.contextPath}/static/css/login.css">
    <script type="application/javascript" src="${pageContext.request.contextPath}/static/js/jquery/jquery-3.6.0.js"></script>
    <script type="application/javascript" >

    </script>
</head>
<body>
    <div style="">
		<div>
			<h1>登录</h1>
			<form name="loginform" method="post" action="${pageContext.request.contextPath}/login">
				<span>账号</span><br />
                <input name="account" placeholder="请输入邮箱或用户名" id="accountText" type="text"/>
                <br />
                <span>密码</span><br />
				<input name="password" placeholder="密码" id="passwordText" type="password"/>
                <br />
                <span><a href="#">忘记密码</a></span><br />
                <input type="submit" value="登录" />
			</form>

		</div>
    </div>

</body>
</html>
