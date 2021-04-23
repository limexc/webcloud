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
    <table class="layui-hide" id="userListTable"></table>

    <script type="text/html" id="switchTpl">
        <input type="checkbox" name="group" value="{{d.id}}" lay-skin="switch" lay-text="管理员|普通用户" lay-filter="groupDemo">
    </script>

    <script type="text/html" id="checkboxTpl">
        <input type="checkbox" name="lock" value="{{d.id}}" title="锁定" lay-filter="lockDemo">
    </script>

    <!--可以再添加一个下拉列表框用来设定空间-->

</div>




<script>
    layui.use('table', function(){
        let table = layui.table
            ,form = layui.form;

        table.render({
            elem: '#userListTable'
            ,url:'/demo/table/user/'
            ,cellMinWidth: 80
            ,height: 'full-5'
            ,cols: [[
                {type:'numbers', title:"序号"}
                ,{field:'id', title:'ID', width:100, unresize: true, sort: true}
                ,{field:'username', title:'用户名', templet: '#usernameTpl'}
                ,{field:'email', title:'邮箱'}
                ,{field:'create_at', title: '注册时间', minWidth:80, sort: true}
                ,{field:'storage', title:'空间大小'}
                ,{field:'group', title:'用户组', width:160, templet: '#switchTpl', unresize: true}
                ,{field:'status', title:'状态', width:110, templet: '#checkboxTpl', unresize: true}
            ]]
            //,page: true
        });

        //监听用户组操作
        form.on('switch(groupDemo)', function(obj){
            layer.tips(this.value + ' ' + this.name + '：'+ obj.elem.checked, obj.othis);
        });

        //监听锁定操作
        form.on('checkbox(lockDemo)', function(obj){
            layer.tips(this.value + ' ' + this.name + '：'+ obj.elem.checked, obj.othis);
        });
    });
</script>