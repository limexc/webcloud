<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <!---->
    <title>网盘注册</title>
    <meta charset="utf-8">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/default.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/register.css">
    <script type="application/javascript" src="${pageContext.request.contextPath}/static/js/jquery-3.6.0.js"></script>
    <script type="application/javascript" src="${pageContext.request.contextPath}/static/js/sendcode.js"></script>
</head>
<body>

<%
    //初步处理一下登陆后还想要注册的用户
    if (session.getAttribute("user")!=null){
        pageContext.forward("/user/main");
    }
%>


    <div id="reg_main">
        <a href="login.jsp">返回</a>
        <h1>注册</h1>
        <div id="reg_form_div">
            <form action="#" method="post" >
                <input class="reg_input" placeholder="用户名" type="text">
                <!--使用ajax控制是否显示-->
                <span id="name_tips" class="reg_tips">该用户名已被注册</span><br/>
                <input class="reg_input" placeholder="邮箱" type="email" id="email" >
                <span id="email_tips" class="reg_tips">该邮箱已被注册</span><br/>
                <input class="reg_input" id="reg_ve" placeholder="验证码" type="text"><button id="sub_ve" >获取验证码</button><br/>
                <span id="veco_tips" class="reg_tips">验证码错误</span><br/>
                <input class="reg_input" placeholder="密码" type="password"><br/>
                <input class="reg_input" placeholder="再次输入密码" type="password">
                <span id="pwd_tips" class="reg_tips">两次输入密码不一致</span><br/>
                <input class="reg_input" id="ck_ag" type="checkbox" name="agreement">
                <span id="ag_text" >我已阅读并同意<a style="color: deeppink" href="${pageContext.request.contextPath}/static/pages/agreement.html">用户协议</a></span>
                <input class="reg_input" type="submit" id="sub_btn" disabled="true" value="注册">
            </form>

        </div>

    </div>

    <div id="background_wrap"></div>

<script type="application/javascript" >
    //前端初步检查是否确认用户协议
    $("#ck_ag").change(function() {
        if($("#ck_ag").prop("checked")){
            $("#sub_btn").attr('disabled',false);//按钮可用
            alert("给你个提示，已经同意了用户协议");
        }
        else{
            $("#sub_btn").attr('disabled',true);//按钮不可用
            alert("给你个提示，没有同意用户协议")
        }
    });

</script>
</body>
</html>
