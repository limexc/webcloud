<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!--网盘的主页，内容界面。当检查到用户未登录时自动转跳到login界面-->
<!DOCTYPE html>
<html>
<head>
    <!---->
    <meta charset="utf-8">
    <title>主页</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/default.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/index_main.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/index_main_table.css">
    <script type="application/javascript" src="${pageContext.request.contextPath}/static/js/spark-md5.js"></script>
    <script type="application/javascript" src="${pageContext.request.contextPath}/static/js/jquery-3.6.0.js"></script>
    <script type="application/javascript" src="${pageContext.request.contextPath}/static/js/upfile.js"></script>

    <!--Google字体-->
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+SC:wght@300&display=swap" rel="stylesheet">


    <script type="application/javascript">


    </script>

    <!--Layui框架 第一次，不会用啊！！！淦！！！-->
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layui/css/layui.css"  media="all">
    <script src="${pageContext.request.contextPath}/static/layui/layui.js" charset="utf-8"></script>


</head>
<body>


<%
    //初步处理一下未登录就访问主页的用户
    if (session.getAttribute("user")==null){
        pageContext.forward("/login.jsp");
    }
%>

<div class="head_menu">
    <a id="logo_ti">网盘？是的！稳定？不存在的！</a>
</div>

<div class="menu_left">
    <div id="photo_div"><img id="photo_img" src="${pageContext.request.contextPath}/static/images/dls.jpeg"></div>
    <div id="userinfo_div" style="font: 26px 微软雅黑 ">
        <span>用户名:${user.username}</span>
        <span></span>
        <span><a href="${pageContext.request.contextPath}/system/logout">退出</a></span>
        <span>容量</span>


    </div>


</div>



<div class="file_main">
    <div class="file_tools">
        <div id="up_file" class="btn_tool_div">
            <a href="#" id="a_upload" class="btn_tool">上传文件
                <input type="file" onchange="change(this);"/>
            </a>
        </div>
        <div id="mkdir"  class="btn_tool_div">
            <a href="#" id="a_mkdir" class="btn_tool">新建文件夹
                <input type="button" onclick="" />
            </a>
        </div>


    </div>

    <div class="file_view">
        <!--内容table容器-->
        <table class="layui-hide" id="test" lay-filter="test"></table>

        <!--面板右侧功能按键-->
        <script type="text/html" id="barDemo">
            <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
            <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
        </script>


        <script>
            layui.use('table', function(){
                var table = layui.table;

                table.render({
                    elem: '#test'
                    ,url:'/test/table/demo1.json'
                    ,title: '用户数据表'
                    ,cols: [[
                        {type: 'checkbox', fixed: 'left'}
                        ,{field:'ico', title:'类型', width:40, fixed: 'left'}
                        ,{field:'filename', title:'名称', width:120, edit: 'text',sort: true}
                        ,{field:'size', title:'大小', width:80, edit: 'text', sort: true}
                        ,{field:'time', title:'上传时间', width:100,sort: true}
                        ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:150}
                    ]]
                    ,page: true
                });


                //监听行工具事件
                table.on('tool(test)', function(obj){
                    var data = obj.data;
                    //console.log(obj)
                    if(obj.event === 'del'){
                        layer.confirm('真的删除行么', function(index){
                            obj.del();
                            layer.close(index);
                        });
                    } else if(obj.event === 'edit'){
                        layer.prompt({
                            formType: 2
                            ,value: data.email
                        }, function(value, index){
                            obj.update({
                                email: value
                            });
                            layer.close(index);
                        });
                    }
                });
            });
        </script>


    </div>


    <div class="pagelimit">这里是分页</div>
</div>

</body>
</html>
