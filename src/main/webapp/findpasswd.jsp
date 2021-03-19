<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>找回密码</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/default.css">
    <link href="${pageContext.request.contextPath}/static/css/findpass.css" rel="stylesheet">
    <!--Google字体-->
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+SC:wght@300&display=swap" rel="stylesheet">

</head>
<body>
    <div id="fd_div_main">
        <h1>密码找回</h1>
        <div id="fd_div">
            <form action="#" method="post">
                <input class="fd_btn in_fd"  type="email" placeholder="请输入注册邮箱" />
                <input class="fd_btn in_fd" type="text" placeholder="请输入验证码" />
                <button class="fd_btn" id="get_ve" >获取验证码</button>
                <input class="fd_btn in_fd" type="password" placeholder="请输入新的密码">
                <input class="fd_btn" type="submit">
            </form>

        </div>


    </div>

    <!--加个背景-->
    <div id="background_wrap"></div>
</body>
</html>