<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/default.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/register.css">
</head>
<body>
    <div id="reg_main">
        <a href="${pageContext.request.contextPath}/login.jsp">返回</a>
        <h1>注册</h1>
        <div id="reg_form_div">
            <form action="#" method="post" >
                <input class="reg_input" placeholder="用户名" type="text">
                <!--使用ajax控制是否显示-->
                <span id="name_tips" class="reg_tips">该用户名已被注册</span><br/>
                <input class="reg_input" placeholder="邮箱" type="email" >
                <span id="email_tips" class="reg_tips">该邮箱已被注册</span><br/>
                <input class="reg_input" id="reg_ve" placeholder="验证码" type="text"><input type="button" id="sub_ve" value="获取验证码"><br/>
                <span id="veco_tips" class="reg_tips">验证码错误</span><br/>
                <input class="reg_input" placeholder="密码" type="password"><br/>
                <input class="reg_input" placeholder="再次输入密码" type="password">
                <span id="pwd_tips" class="reg_tips">两次输入密码不一致</span><br/>
                <input class="reg_input" id="ck_ag" type="checkbox" name="agreement">
                <span id="ag_text" >我已阅读并同意<a>用户协议</a></span>
                <input class="reg_input" type="submit" value="注册">
            </form>

        </div>

    </div>

    <div id="background_wrap"></div>
</body>
</html>
