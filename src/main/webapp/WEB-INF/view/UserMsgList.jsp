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
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/index_main.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/index_main_table.css">
<script type="application/javascript" src="${pageContext.request.contextPath}/static/js/spark-md5.js"></script>
<script type="application/javascript" src="${pageContext.request.contextPath}/static/js/jquery-3.6.0.js"></script>
<script type="application/javascript" src="${pageContext.request.contextPath}/static/js/upfile.js"></script>

<!--Google字体-->
<link rel="preconnect" href="https://fonts.gstatic.com">
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+SC:wght@300&display=swap" rel="stylesheet">

<!--Layui框架-->
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/layui/css/layui.css"  media="all">
<script src="${pageContext.request.contextPath}/static/layui/layui.js" charset="utf-8"></script>

<div class="file_tools">

    <div id="create_msg_div"  class="btn_tool_div">
        <a href="${pageContext.request.contextPath}/msg/create" id="a_create" class="btn_tool"  target="info_body">新建工单
            <input type="button" onclick="" />
        </a>
    </div>


</div>

<div class="file_view">

    <!--内容table容器-->
    <table class="layui-hide" id="MsgListTable" lay-filter="MsgListTable"></table>

    <!--头部工具栏-->
    <script type="text/html" id="th_tool">

    </script>

    <script type="text/html" id="barDemo">
        <a class="layui-btn layui-btn-xs" lay-event="show">查看详情</a>
        <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
    </script>


    <script>
        let objdata;

        layui.use('table', function(){
            var table = layui.table;

            //执行一个 table 实例
            table.render({
                id:"MsgListTable",
                //表格table的id属性
                elem: '#MsgListTable',
                height: 'full-60',
                //请求数据接口
                url: '${pageContext.request.contextPath}/msg/usermsglist',
                //要传向后台的每页显示条数

                title: true   //表头
                //,toolbar: true //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
                ,cols: [[
                    //{type:'radio'},
                    //{type: 'checkbox', fixed: 'check'},
                    //{field: 'filetype', title: '类型', align:'center'},
                    {title: "工单编号",field: 'id',width:'5%',type:"numbers"}
                    ,{field: 'title', title: '标题'}
                    ,{field: 'info', title: '内容',align:'center', sort : true}
                    ,{field: 'create_time', title: '提交时间',align:'center', sort : true,templet: '<div>{{ layui.laytpl.toDateString(d.create_time) }}</div>'}
                    ,{field: 'status', title: '状态',align:'center',
                        templet : function(d) {
                            if (d.status===0){
                                return "未处理";
                            }else if (d.status===1){
                                //特定符合格式的扩容请求
                                return "已同意";
                            }else if (d.status===2){
                                //针对所有请求
                                return "已驳回";
                            }else if (d.status===3){
                                //需要管理员手工操作的请求
                                return "正在处理";
                            }
                            return "其他";
                        }}
                    ,{fixed: 'right', title: '操作', align:'center', toolbar: '#barDemo'},
                ]]
                //,page: true


            });



            //监听行工具事件
            table.on('tool(MsgListTable)', function(obj){
                objdata = obj.data;
                //console.log(obj)
                if(obj.event === 'del'){
                    layer.confirm('您确定要删除吗？', function(index){
                        $.ajax({
                            url: '${pageContext.request.contextPath}/msg/del_msg',
                            type: "post",
                            dataType:"json",
                            contentType: 'application/json;charset=UTF-8',
                            data:JSON.stringify({
                                "id":objdata.id
                            }),
                            success : function(data,xhr) {
                                if( data === "ok") {
                                    layer.msg('删除成功', {
                                        icon: 1,//状态图标
                                        Time: 4000//展示时间为4s
                                    });//这里用于检测上传状态是否成功
                                    table.reload('MsgListTable');
                                }else {
                                    layer.msg('删除失败');
                                }
                            }
                        })

                        obj.del();
                        layer.close(index);

                    });

                }else if (obj.event === "show"){

                    layer.open({
                        content:
                            "<span>描述："+objdata.info+"</span><br>"+
                            "<span>回复："+objdata.replay+"</span><br>"+
                            "<span>请求时间："+objdata.create_time+"</span><br>",
                        title: "工单："+objdata.title+"  ID: "+objdata.id,
                        btnAlign: 'c',
                        btn:["关闭"],
                        //关于扩容请求可以直接点击完成，自动进行扩容
                        yes:function(index, layero){
                            layer.close(index); //如果设定了yes回调，需进行手工关闭
                        }

                    });

                }

            });




            //时间戳的处理
            layui.laytpl.toDateString = function(d, format){
                var date = new Date(d)
                    ,ymd = [
                    this.digit(date.getFullYear(), 4)
                    ,this.digit(date.getMonth() + 1)
                    ,this.digit(date.getDate())
                ]
                    ,hms = [
                    this.digit(date.getHours())
                    ,this.digit(date.getMinutes())
                    ,this.digit(date.getSeconds())
                ];

                format = format || 'yyyy-MM-dd HH:mm:ss';

                return format.replace(/yyyy/g, ymd[0])
                    .replace(/MM/g, ymd[1])
                    .replace(/dd/g, ymd[2])
                    .replace(/HH/g, hms[0])
                    .replace(/mm/g, hms[1])
                    .replace(/ss/g, hms[2]);
            };

            //数字前置补零
            layui.laytpl.digit = function(num, length, end){
                var str = '';
                num = String(num);
                length = length || 2;
                for(var i = num.length; i < length; i++){
                    str += '0';
                }
                return num < Math.pow(10, length) ? str + (num|0) : num;
            };




        });

    </script>

</div>