<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户登录</title>
	<!---->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/login.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/default.css" />
    <script type="application/javascript" src="${pageContext.request.contextPath}/static/js/jquery-3.6.0.js"></script>

    <script type="application/javascript" >



    </script>
	<!--html 测试用-->
	<link rel="stylesheet" type="text/css" href="static/css/login.css">
	<link rel="stylesheet" type="text/css" href="static/css/default.css" />
	<script type="application/javascript" src="static/js/jquery-3.6.0.js"></script>
	
</head>
<body>

	<!--登陆对话框窗体-->
    <div class="login">
        <h1>WEB CLOUD</h1>
		<div class="login_main">
			<form name="loginform" method="post" action="${pageContext.request.contextPath}/login">
                <span class="login_tips">邮箱</span><br />
                <input class="login_input_text" name="account" placeholder="邮箱" id="accountText" type="text"/>
                <br />
                <span class="login_tips">密码</span><br />
				<input class="login_input_text" name="password" placeholder="密码" id="passwordText" type="password"/>
                <br />
                <span class="other_tips"><a id="refind" href="#">忘记密码</a>或<a id="signup" href="${pageContext.request.contextPath}/register.jsp">注册账号</a></span><br />
                <input class="login_btn" type="submit" value="登录" />
			</form>

		</div>
    </div>


    <div id="background_wrap"></div>

</body>
</html>
