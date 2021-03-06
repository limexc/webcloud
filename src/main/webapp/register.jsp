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

    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layui/css/layui.css"  media="all">
    <script src="${pageContext.request.contextPath}/static/layui/layui.js" charset="utf-8"></script>
</head>
<body>

<%
    //初步处理一下登陆后还想要注册的用户
    if (session.getAttribute("user")!=null){
        pageContext.forward("/user/main");
    }
%>


    <div id="reg_main">
        <a href="${pageContext.request.contextPath}">返回</a>
        <h1>注册</h1>
        <div id="reg_form_div">

                <input class="reg_input" placeholder="用户名" type="text" id="username">
                <!--使用ajax控制是否显示-->
                <span id="name_tips" class="reg_tips"></span><br/>
                <input class="reg_input" placeholder="邮箱" type="email" id="email" >
                <!--使用ajax控制是否显示-->
                <span id="email_tips" class="reg_tips"></span><br/>
                <input class="reg_input" id="reg_ve" placeholder="验证码" type="text"><button id="sub_ve" >获取验证码</button><br/>
                <!--使用ajax控制是否显示验证码错误或超时-->
                <span id="veco_tips" class="reg_tips"></span><br/>
                <input class="reg_input" placeholder="密码" type="password" id="passwd1"><br/>
                <input class="reg_input" placeholder="再次输入密码" type="password" id="passwd2">
                <span id="pwd_tips" class="reg_tips"></span><br/>
                <input class="reg_input" id="ck_ag" type="checkbox" name="agreement">
                <span id="ag_text" >我已阅读并同意<a style="color: deeppink" href="${pageContext.request.contextPath}/static/pages/agreement.html">用户协议</a></span>
                <input class="reg_input" type="submit" id="sub_btn" disabled="true" value="注册">

        </div>

    </div>

    <div id="background_wrap"></div>

<script type="application/javascript" >
    let isOkReg_Name = false;
    let isOkReg_Email =false;
    let isOkReg_passwd = false;
    let isOkReg_regcode = false;
    //前端初步检查是否确认用户协议，以及注册内容是否正确
    $("#ck_ag").change(function() {
        if($("#ck_ag").prop("checked")){
            $("#sub_btn").attr('disabled',false);//按钮可用
            //alert("给你个提示，已经同意了用户协议");
        }
        else{
            $("#sub_btn").attr('disabled',true);//按钮不可用
            //alert("给你个提示，没有同意用户协议")
        }
    });

    layui.use('layer', function(){
        var layer = layui.layer;
    });

    function loadmsg(){
        layer.msg('加载中...', {
            icon: 16,
            shade: 0.01,
            time:1500
        });
    }

    //用于校验的入口
    $(function(){

        $(".reg_input").focus(function(){//得到焦点隐藏提示
            $(".reg_tips").html("");

        });
        //因为这里是class，所以同class间切换不会失去焦点
        $(".reg_input").blur(function(){//失去焦点执行
            let input_id=($(this).attr("id"));
            let funName="validate"+input_id+"()";
            eval(funName);
        });


        $("#sub_btn").click(function (){

            loadmsg();
            validateck_ag();
            validateemail();
            validateusername();
            validatepasswd2();
            alert("邮箱："+isOkReg_Email+" "+"用户名："+isOkReg_Name+" "+"密码："+isOkReg_passwd+" "+"验证码："+isOkReg_regcode)
            if (isOkReg_Email+isOkReg_Name+isOkReg_passwd+isOkReg_regcode===4){

                $.ajax({
                    url:"http://localhost:8081/CloudWeb/system/reg/updata",
                    type:"post",
                    async:true,
                    cache:false,
                    datatype:"json",
                    data:{
                        username:$("#username").val(),
                        email:$("#email").val(),
                        passwd:$("#passwd1").val(),
                    },
                    success:function (data){
                        if(data==="true"){
                            layer.msg("注册成功！<br/>3秒后转跳到登陆界面",{time:3000});

                            setTimeout(reurl,1000*3);
                            function reurl(){
                                window.location.replace("http://localhost:8081/CloudWeb/");
                            }

                        }else if (data==="false"){
                            layer.msg("注册失败！<br/>请检查后重试！")
                        }
                    }


                });
            }else {
                alert("邮箱："+isOkReg_Email+" "+"用户名："+isOkReg_Name+" "+"密码："+isOkReg_passwd+" "+"验证码："+isOkReg_regcode)
            }



        })

    });




    //校验验证码  reg_ve
    function validatereg_ve() {
        if ($("#reg_ve").val().length!==6){
            $("#veco_tips").html("验证码有误");
        }else{
            $.ajax({
                url:"http://localhost:8081/CloudWeb/system/reg/service",
                type:"post",
                async:true,
                cache:false,
                datatype:"json",
                data:{regcode:$("#reg_ve").val(),email:$("#email").val()},
                success:function (data){
                    if(data==="true"){
                        $("#veco_tips").html("验证码正确");
                        isOkReg_regcode = true;
                    }else {
                        $("#veco_tips").html("验证码有误");
                        isOkReg_regcode = false;
                    }

                }
            })


        }
    };

    //校验密码 passwd1 与 passwd2
    function validatepasswd1(){
        let id="passwd1";
        let value=$("#"+id).val();
        if(value===""){
            $("#pwd_tips").html("登录密码不能为空");
            isOkReg_passwd =false;
        }
        if(value.length<6){
            $("#pwd_tips").html("登录密码长度必须大于6");
            isOkReg_passwd =false;
        }

    };


    function validatepasswd2(){

        let id="passwd2";
        let value=$("#"+id).val();
        if(value===""){
            $("#pwd_tips").html("登录确认密码不能为空");
            isOkReg_passwd =false;
        }else if(value.length<6){
            $("#pwd_tips").html("登录确认密码长度必须大于6");
            isOkReg_passwd =false;
        }else if(value!==$("#passwd1").val()){
            $("#pwd_tips").html("两次密码不同");
            isOkReg_passwd =false;
        }else {
            isOkReg_passwd = true;
        }


    };

    function validateck_ag(){
        if ($('#ck_ag').is(':checked')){
            $("#sub_btn").attr('disabled',false);//按钮可用
        } else{
            $("#sub_btn").attr('disabled',true);//按钮不可用
        }
    };

    //用户名校验
    function validateusername(){
        let username = $("#username").val();
        if(username==""){
            $("#name_tips").html("用户名不能为空");
        }else if(username.length<3){
            $("#name_tips").html("用户名长度必须大于3");
        }else {
            alert("发送ajax校验username")
            $.ajax({
                url:"http://localhost:8081/CloudWeb/system/reg/service",
                data:{loginName:username},
                async:true,
                cache:false,
                type:"post",
                datatype:"json",
                error:function(){
                    alert("name error");
                },
                success:function(result){
                    if(result=="false"){
                        $("#name_tips").html("该用户名已存在");
                        isOkReg_Name = false;
                    }else if (result=="true"){
                        isOkReg_Name = true;
                    }
                }
            })

        }

    };


    //校验Email
    function validateemail(){
        let value=$("#email").val();
        if(value===""){
            $("#email_tips").html("请输入邮箱！");
        }else if(!/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/.test(value)){
            $("#email_tips").html("邮箱格式不对");
        }else {
            alert("发送ajax校验Email")
            $.ajax({
                url:"http://localhost:8081/CloudWeb/system/reg/service",
                data:{email:value},
                async:true,
                cache:false,
                type:"post",
                datatype:"json",
                error:function(){
                    alert("email error");
                },
                success:function(result){
                    if(result==="false"){
                        $("#email_tips").html("该邮箱已经注册过了");
                        isOkReg_Email =false;
                    }else if (result==="true"){
                        isOkReg_Email = true;
                    }
                }
            })



        };



    }





</script>
</body>
</html>
