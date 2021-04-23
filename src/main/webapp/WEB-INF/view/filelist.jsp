<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    //初步处理一下未登录就访问主页的用户
    if (session.getAttribute("user")==null){
        pageContext.forward("/login.jsp");
    }
%>
<%
    //关于路径问题，初步的解决方法
    String path = request.getContextPath();
    String basePath = request.getServerName() + ":" + request.getServerPort() + path + "/";
    String baseUrlPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<meta charset="utf-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/layui/css/layui.css">
<script src="${pageContext.request.contextPath}/static/layui/layui.js"></script>

<div style="width: 100%;height: 100%;background-color: white;margin-top: -10px ">
    <table class="layui-hide" id="fileListTable"></table>
</div>




<script>
    layui.use('table', function(){
        let table = layui.table
            ,form = layui.form;

        table.render({
            elem: '#fileListTable'
            ,url:'/demo/table/user/'
            ,cellMinWidth: 80
            ,height: 'full-5'
            ,cols: [[
                {type:'numbers',title:'序号'}
                ,{field:'id', title:'ID', width:100, unresize: true, sort: true}
                ,{field:'filename', title:'文件名', templet: '#usernameTpl'}
                ,{field:'create_time', title:'初次上传时间',minWidth:180}
                ,{field:'filesize', title:'文件大小',sort: true}
                ,{field:'filetype', title: '文件类型', minWidth:80}
            ]]
            //,page: true
        });


    });
</script>