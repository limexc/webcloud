<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    //初步处理一下未登录就访问的用户
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
<!--
<a style="font-size: 35px" id="a_msg_method">当前的文件类型：${requestScope.get("filetype")}</a><br>
-->

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
    let baseurlpath = '<%= baseUrlPath%>'
    layui.use('table', function(){
        var table = layui.table;

        table.render({
            elem: '#file_table'
            ,url:'${pageContext.request.contextPath}/share/list'
            //模拟数据
            //,url:'/demo/table/user/'
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
                "<span>文件名："+data.vfname+"</span><br>"+
                "<span>文件大小："+data.filesize+"</span><br>"+
                "<span>修改时间："+data.uptime+"</span><br>",
                //JSON.stringify(data),
                {
                    title: "文件信息："+data.vfname,
                    btnAlign: 'c',
                    btn:["查看链接","取消分享"],
                    //查看链接
                    yes:function(index, layero){
                        //do something
                        //alert("查看链接")
                        viewShareUrl(obj,index,layero,data);
                        layer.close(index); //如果设定了yes回调，需进行手工关闭
                    },

                    btn2:function(index, layero){
                        //do something
                        del_share(obj,index,layero,data);
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

        //搜索文件
        var $ = layui.$, active = {
            reload: function(){
                var selectFile = $('#selectFile');
                //执行重载
                table.reload('file_table', {
                    url:"${pageContext.request.contextPath}/info/selectfile?key="+selectFile.val()+"&type=${requestScope.get("filetype")}"
                });
            }
        };

        $('#select_btn').click(function(){
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });

    });
</script>

<script>

    function del_share(obj,index,layero,data){
        layer.alert(
            "你确定要取消共享文件：'"+data.vfname+"' 吗？",
            {
                title:"警告！",
                btn:["是","否"],
                yes:function (index, layero){
                    $.ajax({
                        url: "${pageContext.request.contextPath}/share/delShare",
                        //做些事情
                        type: "post",
                        data: {"ufid":data.id}

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

    function viewShareUrl(obj){
        let sharecode="000000";
        $.ajax({
            url:'${pageContext.request.contextPath}/share/getshareurl?ufid='+obj.data.id,
            type:"post",
            success:function (data){
                sharecode=data.tips;
                layui.layer.alert(
                    "您的分享地址为：<br />"+
                    "<textarea name='s_url' rows=2  style='resize: none;width: 100%'>"+baseurlpath+"share/file/"+sharecode+"</textarea>",
                    {
                        title:"链接",
                        btn:["复制链接"],
                        yes:function (index, layero){
                            copyUrl();
                            layui.layer.close(index);
                        },

                    })
            }
        })

    }


</script>

<script>
    function copyUrl() {
        let S_Url =  document.getElementsByName("s_url");
        S_Url[0].select(); // 选择对象
        document.execCommand("Copy"); // 执行浏览器复制命令
        layer.msg("复制成功");
        //alert("已复制好，可贴粘。");
    }
</script>




<!--body  end-->