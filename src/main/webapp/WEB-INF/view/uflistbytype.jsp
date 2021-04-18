<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    //初步处理一下未登录就访问的用户
    if (session.getAttribute("user")==null){
        pageContext.forward("/login.jsp");
    }
%>
<!--css以及js文件引入  start-->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/default.css" />
<script type="application/javascript" src="${pageContext.request.contextPath}/static/js/jquery-3.6.0.js"></script>

<!--Layui框架-->
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/layui/css/layui.css"  media="all">
<script src="${pageContext.request.contextPath}/static/layui/layui.js" charset="utf-8"></script>

<!--css以及js文件引入  end-->



<!--body  start-->

<a style="font-size: 35px" id="a_msg_method">当前的文件类型：${requestScope.get("filetype")}</a><br>


<div class="layui-inline">
    <input class="layui-input" name="selectFile" id="selectFile" autocomplete="off">
</div>
<button class="layui-btn" id="select_btn" data-type="reload">搜索</button>


<table class="layui-hide" id="file_table" lay-filter="file_table"></table>

<!--
<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="download">下载</a>
    <a class="layui-btn layui-btn-xs" lay-event="edit">重命名</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>
-->

<script>
    layui.use('table', function(){
        var table = layui.table;

        table.render({
            elem: '#file_table'
            //模拟数据
            ,url:'${pageContext.request.contextPath}/info/filelistbytype?filetype=${requestScope.get("filetype")}'
            //,url:'/demo/table/user/'
            ,title: '监测任务列表信息'
            ,page:false
            ,cols: [[
                //可以设置点击文件名弹出详细信息
                {title: "ID",width:'5%',type:"numbers"}
                ,{field:'vfname', width:'35%', title: '文件名', sort: true, event :'filename_click',unresize:true}
                ,{field:'filesize', width:'30%', title: '文件大小', sort: true,unresize:true}
                ,{field:'uptime', width:'30%', title: '修改日期', sort: true,unresize:true}
                //,{field:'tools', title: '操作', width: '25%',unresize:true,align:'center', toolbar: '#barDemo'} //minWidth：局部定义当前单元格的最小宽度，layui 2.2.1 新增
                ,{field:'id',hide:true}
            ]]
        });

        //监听行单击事件（双击事件为：rowDouble  单击事件：row）  START
        table.on('row(file_table)', function(obj){
            let data = obj.data;

            layer.alert(
                "<img height='200' width='200' style='display:block;margin:0 auto;' src='${pageContext.request.contextPath}/static/images/dls.jpeg'></img><br>"+
                "<span>文件名："+data.vfname+"</span><br>"+
                "<span>文件大小："+data.filesize+"</span><br>"+
                "<span>修改时间："+data.uptime+"</span><br>",
                //JSON.stringify(data),
                {
                    title: "文件信息："+data.vfname,
                    btnAlign: 'c',
                    btn:["下载","查看","分享","删除"],
                    //下载
                    yes:function(index, layero){
                        //do something
                        alert("下载")
                        layer.close(index); //如果设定了yes回调，需进行手工关闭
                    },
                    //view_file  查看
                    btn2:function(index, layero){
                        //do something
                        alert("查看")
                        layer.close(index); //如果设定了yes回调，需进行手工关闭
                    },
                    //分享
                    btn3:function(index, layero){
                        //do something
                        alert("分享")
                        layer.close(index); //如果设定了yes回调，需进行手工关闭
                    },
                    //删除
                    btn4:function(index, layero){
                        //do something
                        del_file(obj,index,layero,data);
                        layer.close(index); //如果设定了yes回调，需进行手工关闭
                    }

                }

            );

            //标注选中样式
            obj.tr.addClass('layui-table-click').siblings().removeClass('layui-table-click');
        });
        //监听行单击事件（双击事件为：rowDouble） END

        /*
        //监听行工具事件  START
        table.on('tool(file_table)', function(obj){
            let data = obj.data;
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
        //监听行工具事件 END
        */


    });
</script>

<script>

    function del_file(obj,index,layero,data){
        layer.alert(
            "你确定要删除文件：'"+data.vfname+"' 吗？",
            {
                title:"警告！",
                btn:["删除","取消"],
                yes:function (index, layero){
                    $.ajax({
                        url: "#"
                        //做些事情
                    })
                    obj.del();
                    layer.close(index);
                },
                btn2:function (index, layero){
                    layer.close(index);
                }
            }

        )
    }


</script>





<!--body  end-->