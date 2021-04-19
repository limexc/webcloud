<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>找回密码</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/default.css">
    <link href="${pageContext.request.contextPath}/static/css/findpass.css" rel="stylesheet">
    <script type="application/javascript" src="${pageContext.request.contextPath}/static/js/jquery-3.6.0.js"></script>

    <!--Google字体-->
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+SC:wght@300&display=swap" rel="stylesheet">

    <!--Layui框架-->
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layui/css/layui.css"  media="all">
    <script src="${pageContext.request.contextPath}/static/layui/layui.js" charset="utf-8"></script>

</head>
<body>
    <div id="fd_div_main" >
        <h1>密码找回</h1>
        <div id="fd_div">
            <form id="findpasswd" onsubmit="return false" action="#"  method="post" class="layui-form">

                <input class="fd_btn in_fd"  type="email"  lay-verify="email" name="email" autocomplete="off" placeholder="请输入注册邮箱" /><br>
                <input class="fd_btn in_fd" type="text" lay-verify="required" name="vecode" lay-reqtext="验证码是必填项，岂能为空？" placeholder="请输入验证码" />
                <button class="fd_btn" id="get_ve" onclick="getvecode()">获取验证码</button><br>
                <input class="fd_btn in_fd" type="password"  lay-verify="pass" name="passwd" autocomplete="off" placeholder="请输入新的密码"/><br>
                <button  class="fd_btn" lay-submit lay-filter="sub_btn" >找回密码</button>
            </form>

        </div>


    </div>

    <script>

        layui.use('form', function(){
            var form = layui.form;
            //各种基于事件的操作，下面会有进一步介绍
            //自定义验证规则
            form.verify({
                pass: [
                    /^[\S]{6,12}$/
                    ,'密码必须6到12位，且不能出现空格'
                ]
            });


            // 验证成功后执行操作 [未修改]
            form.on('submit(sub_btn)',function () {
                console.log('验证成功')
                $.ajax({
                    //几个参数需要注意一下
                    type: "POST",//方法类型
                    dataType: "json",//预期服务器返回的数据类型
                    url: "${pageContext.request.contextPath}/system/findpwd",//url
                    data: $('#findpasswd').serialize(),
                    success: function (result) {
                        if (result==="yes"){
                            layui.layer.alert("密码修改成功");
                        }else {
                            layui.layer.alert("请输入正确的旧密码");
                        }

                    },
                    error : function() {
                        alert("异常！");
                    }
                });

            })

        });


        function getvecode(){
            alert("点击了发送邮件")
            //获取用户输入的email

            //判断是否为空或格式是否正确

            //给出判断后的提示

            //发送邮件

            //提示

        }

    </script>

    <!--/system/sendmail-->
    <!--提示该邮箱未注册-->


    <!--加个背景-->
    <div id="background_wrap"></div>
</body>
</html>