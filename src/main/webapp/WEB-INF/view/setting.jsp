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


<div style="padding-left: 10%">
    <span style="font-size: 24px">头像</span><br>
    <div style="display: inline-block;width: 200px;height: 200px;">
        <a id="user_img">
            <img width="200px" height="200px" src="${sessionScope.get("profile")}">
        </a>
    </div><br><br>
    <span style="font-size: 24px">用户名：${user.username} </span>  <a style="color: red;font-size: 16px" id="change_name_a"> 修改</a> <br><br>
    <span style="font-size: 24px">用户组：${group.name}</span><br><br>
    <span style="font-size: 24px">用户邮箱：${user.email}</span><br><br>
    <span style="font-size: 24px">注册时间：${user.create_at}</span><br><br>
    <span style="font-size: 24px">账户状态：${status}</span><br><br>
    <span style="font-size: 24px">空间大小：${size}</span><br><br>
    <span><a style="color: red;font-size: 24px" id="deluser_a">注销账户</a></span>

</div>

<script>
    layui.use(['layer','jquery'],function () {
        // 定义使用jquery和layer
        let $ = layui.jquery;
        let layer = layui.layer;

        $("#user_img").click(function () {
            alertUp();
        })

        $("#change_name_a").click(function (){
            changeName();
        })

        $("#deluser_a").click(function (){
            deluser();
        })

    })

    function deluser(){
        layer.prompt({
            formType: 0,
            title: '请在下方输入”YES“来确认删除账户'
        }, function(value, index, elem){
            $.ajax({
                url: "${pageContext.request.contextPath}/user/del_user",
                type: "post",
                data:{
                    "enter":value
                },
                success:function (data){
                    if (data==="yes"){
                        layer.msg("账户已删除")
                    }else {
                        layer.msg("删除失败")
                    }
                },
                error:function (){
                    layer.msg("错误")
                }
            })

            layer.close(index);
        });
    }

    function changeName(){
        layer.prompt({
            formType: 0,
            value: "${user.username}",
            title: '设置用户名',
            //area: ['800px', '350px'] //自定义文本域宽高
        }, function(value, index, elem){
            $.ajax({
                url: "${pageContext.request.contextPath}/user/setUserName",
                type: "post",
                data:{
                    "name":value
                },
                success:function (data){
                    if (data==="yes"){
                        layer.msg("修改成功")
                        table.reload('userListTable');
                    }else {
                        layer.msg("修改失败")
                    }
                },
                error:function (){
                    //alert("错误")
                }
            })

            layer.close(index);
        });

    }

    function alertUp(){

        layer.open({
            type: 2,
            title: '上传图像',
            shadeClose: true,
            shade: false,
            maxmin: false, //开启最大化最小化按钮
            area: ['400px', '400px'],
            content: 'http://localhost:8081${pageContext.request.contextPath}/static/pages/upimage.html'
        });

    }


</script>