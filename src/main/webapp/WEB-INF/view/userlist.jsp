
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Layui</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <<link rel="stylesheet" href="${pageContext.request.contextPath}/static/layui/css/layui.css">

</head>
<body>

<table class="layui-hide" id="test"></table>

<script type="text/html" id="switchTpl">
    <!-- 这里的 checked 的状态只是演示 -->
    <input type="checkbox" name="sex" value="{{d.id}}" lay-skin="switch" lay-text="女|男" lay-filter="sexDemo" {{ d.id == 10003 ? 'checked' : '' }}>
</script>

<script type="text/html" id="checkboxTpl">
    <!-- 这里的 checked 的状态只是演示 -->
    <input type="checkbox" name="lock" value="{{d.id}}" title="锁定" lay-filter="lockDemo" {{ d.id == 10006 ? 'checked' : '' }}>
</script>



<script src="${pageContext.request.contextPath}/static/layui/layui.js"></script>


<script>
    layui.use('table', function(){
        var table = layui.table
            ,form = layui.form;

        table.render({
            elem: '#test'
            ,url:'/demo/table/user/'
            ,cellMinWidth: 80
            ,cols: [[
                {type:'numbers'}
                ,{type: 'checkbox'}
                ,{field:'id', title:'ID', width:100, unresize: true, sort: true}
                ,{field:'username', title:'用户名', templet: '#usernameTpl'}
                ,{field:'city', title:'城市'}
                ,{field:'wealth', title: '财富', minWidth:120, sort: true}
                ,{field:'sex', title:'性别', width:85, templet: '#switchTpl', unresize: true}
                ,{field:'lock', title:'是否锁定', width:110, templet: '#checkboxTpl', unresize: true}
            ]]
            ,page: true
        });

        //监听性别操作
        form.on('switch(sexDemo)', function(obj){
            layer.tips(this.value + ' ' + this.name + '：'+ obj.elem.checked, obj.othis);
        });

        //监听锁定操作
        form.on('checkbox(lockDemo)', function(obj){
            layer.tips(this.value + ' ' + this.name + '：'+ obj.elem.checked, obj.othis);
        });
    });
</script>

</body>
</html>
