<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<%
    //关于路径问题，初步的解决方法
    String path = request.getContextPath();
    String basePath = request.getServerName() + ":" + request.getServerPort() + path + "/";
    String baseUrlPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<%
    //初步处理一下未登录就访问的用户
    if (session.getAttribute("user")==null){
        pageContext.forward("/login.jsp");
    }
%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/default.css" />
<script type="application/javascript" src="${pageContext.request.contextPath}/static/js/jquery-3.6.0.js"></script>



<!--Layui框架-->
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/layui/css/layui.css"  media="all">
<script src="${pageContext.request.contextPath}/static/layui/layui.js" charset="utf-8"></script>

<div style="margin: 20% 35%;width: 600px">


    <form class="layui-form" id="setpwd_form" onsubmit="return false" action="#"  method="post">

        <div class="layui-form-item">
            <label class="layui-form-label">旧密码：</label>
            <div class="layui-input-inline">
                <input type="password" name="old_pass" lay-verify="old_pass" placeholder="请输入密码" autocomplete="off" class="layui-input">
            </div>
            <div class="layui-form-mid layui-word-aux">请输入正确密码</div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">新的密码：</label>
            <div class="layui-input-inline">
                <input type="password" name="pass1" lay-verify="pass" placeholder="请输入密码" autocomplete="off" class="layui-input">
            </div>
            <div class="layui-form-mid layui-word-aux">请填写6到12位密码</div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">重新输入：</label>
            <div class="layui-input-inline">
                <input type="password" name="pass2" lay-verify="pass" placeholder="请输入密码" autocomplete="off" class="layui-input">
            </div>
            <div class="layui-form-mid layui-word-aux">请再次输入新密码</div>
        </div>

        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn" lay-submit lay-filter="sub_btn">立即提交</button>
                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
            </div>
        </div>

    </form>


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

            // 验证成功后执行操作
            form.on('submit(sub_btn)',function () {
                console.log('验证成功')
                $.ajax({
                    //几个参数需要注意一下
                    type: "POST",//方法类型
                    url: "${pageContext.request.contextPath}/user/repwd",//url
                    data: $('#setpwd_form').serialize(),
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

    </script>
    
    
    
</div>

