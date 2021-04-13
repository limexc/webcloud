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

<%
    //关于路径问题，初步的解决方法
    String path = request.getContextPath();
    String basePath = request.getServerName() + ":" + request.getServerPort() + path + "/";
    String baseUrlPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<div class="head_menu" style="z-index: 999">


    <div class="layui-layout layui-layout-admin">
        <div class="layui-header">
            <div class="layui-logo">网盘</div>
            <!-- 头部区域（可配合layui已有的水平导航） -->

            <!--头部区域结束-->
            <ul class="layui-nav layui-layout-right">
                <li class="layui-nav-item">
                    <a href="javascript:;">
                        <img src="${pageContext.request.contextPath}/static/images/dls.jpeg" class="layui-nav-img">
                        ${user.username}
                    </a>
                    <dl class="layui-nav-child">
                        <dd><a href="">基本资料</a></dd>
                        <dd><a href="">安全设置</a></dd>
                    </dl>
                </li>
                <li class="layui-nav-item"><a href="${pageContext.request.contextPath}/system/logout">退了</a></li>
            </ul>
        </div>

</div>

<div class="menu_left">
    <!--
    <div id="photo_div"><img id="photo_img" src="${pageContext.request.contextPath}/static/images/dls.jpeg"></div>
    <div id="userinfo_div" style="font: 26px 微软雅黑 ">
        <span>用户名:${user.username}</span>
        <span></span>
        <span><a href="${pageContext.request.contextPath}/system/logout">退出</a></span>
        <span>容量</span>
        <div class="layui-progress">
            进度条,用来显示容量，不用自己画真好...但是想自己调调就要去查文档...
            <div class="layui-progress-bar" lay-percent="40%"></div>
        </div>
        <script>
            //注意进度条依赖 element 模块，否则无法进行正常渲染和功能性操作
            layui.use('element', function(){
                var element = layui.element;
            });
        </script>

    -->

    <ul class="layui-nav layui-nav-tree layui-nav-side layui-inline" lay-filter="demo" style="margin-top: 60px;">
        <li class="layui-nav-item layui-nav-itemed">
            <a href="javascript:;">我的文件</a>
            <dl class="layui-nav-child">
                <dd><a href="javascript:;">文档</a></dd>
                <dd><a href="javascript:;">图片</a></dd>
                <dd><a href="javascript:;">视频</a></dd>
                <dd><a href="javascript:;">音乐</a></dd>
                <dd><a href="javascript:;">其他</a></dd>
                <!--<dd><a href="">跳转项</a></dd>-->
            </dl>
        </li>
        <li class="layui-nav-item"><a href="javascript:;">我的分享</a></li>
        <li class="layui-nav-item"><a href="">容量配额</a></li>

            <div class="layui-progress">
                <div class="layui-progress-bar" lay-percent="40%"></div>
            </div>

        </li>
    </ul>







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

        <div id="uppage"  class="btn_tool_div">
            <a href="#" id="a_uppage" class="btn_tool">上一层
                <input type="button" onclick="" />
            </a>
        </div>


    </div>

    <div class="file_view">
        <!--内容table容器-->
        <table class="layui-hide" id="FileListTable" lay-filter="FileListTable"></table>


        <script type="text/html" id="barDemo">
            <a class="layui-btn layui-btn-xs" lay-event="download">下载</a>
            <a class="layui-btn layui-btn-xs" lay-event="rename">重命名</a>
            <a class="layui-btn layui-btn-xs layui-btn-normal" lay-event="share">分享</a>
            <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
        </script>

        <script src="${pageContext.request.contextPath}/static/js/iconfont.js"></script>


        <script>
            var Catalogue = 0;
            var currentpath = "/";
            var data;
            var vfname;


            layui.use('table', function(){
                var table = layui.table;

                //执行一个 table 实例
                table.render({
                    id:"fileListTable",
                    //表格table的id属性
                    elem: '#FileListTable',
                    height: 'full-200',
                    //请求数据接口
                    url: '${pageContext.request.contextPath}/info/userfile?method=index',
                    //要传向后台的每页显示条数
                    limit:10,

                    done: function (res, curr, count) {
                        //设置table的宽度
                        $("table").css("width", "100%")

                        Catalogue = res.msg["Catalogue"]
                        currentpath = res.msg["currentpath"]
                        data = res.data
                        console.log("Catalogue:"+ Catalogue)
                        console.log("currentpath:"+ currentpath)
                        this.where = {};

                    },

                    //,page:true(自带的这个要注掉)
                    page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
                        layout: ['count', 'prev', 'page', 'next', 'limit', 'refresh', 'skip']//自定义分页布局
                        ,limits:[5,10,15]
                        ,first: false //不显示首页
                        ,last: false //不显示尾页
                    },

                    title: true   //表头
                    ,toolbar: true //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
                    ,cols: [[
                        {type:'radio'},
                        //{type: 'checkbox', fixed: 'check'},
                        //{field: 'filetype', title: '类型', align:'center'},
                        {field: 'vfname', title: '文件名',align:'center', sort : true,event :'fileclick',
                            templet : function(d) {
                                if (d.filesize==="-"){
                                    return "<svg class='icon' aria-hidden='true'><use xlink:href='" + "#icon-folder" + "'></use></svg>&nbsp;"
                                        + "</span class='filenamecolor'>"
                                        + d.vfname
                                        + "</span>";
                                }
                                return "<svg class='icon' aria-hidden='true'><use xlink:href='" + "" + "'></use></svg>&nbsp;"
                                    + "</span class='filenamecolor'>"
                                    + d.vfname
                                    + "</span>";
                            }

                        }
                        ,{field: 'filesize', title: '大小',align:'center', sort : true}
                        ,{field: 'uptime', title: '上传时间',align:'center', sort : true,templet: '<div>{{ layui.laytpl.toDateString(d.uptime) }}</div>'}
                        ,{fixed: 'right', title: '操作', align:'center', toolbar: '#barDemo'},
                        {field: 'id',hide:true}
                    ]]
                    ,page: true


                });


                //监听行工具事件
                table.on('tool(FileListTable)', function(obj){

                    var objdata = obj.data;
                    //console.log(obj)
                    if(obj.event === 'del'){
                        layer.confirm('您确定要删除吗？', function(index){
                            $.ajax({
                                url: '${pageContext.request.contextPath}/info/deletfile',
                                type: "post",
                                dataType:"json",
                                contentType: 'application/json;charset=UTF-8',
                                data:JSON.stringify({
                                    "id":objdata.id
                                }),
                                success : function(data,xhr) {
                                    if(xhr.status === 200) {
                                        layer.msg('删除成功', {
                                            icon: 1,//状态图标
                                            Time: 4000//展示时间为4s
                                        });//这里用于检测上传状态是否成功
                                        parent.layui.table.reload('fileListTable');
                                    }
                                }
                            })

                            obj.del();
                            layer.close(index);

                        });

                    } else if(obj.event === 'download'){
                        //监听下载按钮
                        layer.confirm('下载该文件？', function(index){
                            window.location.href = '${pageContext.request.contextPath}/file/download?ufid='+objdata.id;
                            layer.close(index);

                        });
                    }else if (obj.event === "share"){
                        //文件共享按钮监听
                        //Ajax发送信息
                        $.ajax({
                            url:'${pageContext.request.contextPath}/share/getshareurl?ufid='+objdata.id,
                            type:"post",
                            //不知道怎么写对不对，先试试
                            success : function (data){
                                let baseurlpath = '<%= baseUrlPath%>'
                                if (data.url=='err1') {
                                    layer.open({title:"警告！",content:"您输入的信息有误！"});
                                }else if (data.url=="err2"){
                                    layer.open({title:"提示！",content:"这个文件已经分享过了！<br />分享地址为：<br />"+baseurlpath+'share/file/'+data.tips})
                                }else if (data.url=="err3"){
                                    layer.open({title:"错误！",content:"系统错误！请您稍后再次尝试！"})
                                }else {
                                    layer.alert(
                                        "您的分享地址为：<br />"+baseurlpath+'share/file/'+data.url);
                                }

                            }
                        })
                    }else if (obj.event === "rename"){

                        //prompt层
                        layer.prompt({title: '重命名', formType: 0,value:objdata.vfname}, function(text, index){
                            $.ajax({
                                url:"${pageContext.request.contextPath}/info/rename",
                                type: "post",
                                contentType: 'application/json;charset=UTF-8',
                                data:JSON.stringify({
                                    "id":objdata.id,
                                    "rename":text,
                                    "Catalogue" : Catalogue,
                                    "currentpath" : currentpath
                                }),
                                success : function(data,textStatus,xhr) {
                                    if(xhr.status === 200) {
                                        layer.msg('重命名成功', {
                                            icon: 1,//状态图标
                                            Time: 4000//展示时间为4s
                                        });//这里用于检测上传状态是否成功
                                        parent.layui.table.reload('fileListTable',{
                                            url: "${pageContext.request.contextPath}/info/userfile?method=index",

                                            where: { //设定异步数据接口的额外参数，任意设
                                                "Catalogue": Catalogue,
                                                "currentpath": currentpath,
                                            }
                                        });
                                    }

                                },
                                error: function(e) {
                                    console.log("error")
                                }
                            })
                            layer.close(index);

                        });


                    }else if (obj.event === "fileclick") {
                        alert("你点击了："+objdata.vfname)
                        console.log(obj.data)
                        if (objdata.filesize === "-") {
                            alert(objdata.filesize)
                            alert(Catalogue+" "+currentpath+" "+objdata.vfname)
                            parent.layui.table.reload('fileListTable',
                                {
                                url: "${pageContext.request.contextPath}/info/userfile?method=getSub",
                                where: { //设定异步数据接口的额外参数，任意设
                                    "Catalogue": Catalogue,
                                    "currentpath": currentpath,
                                    "filename": objdata.vfname
                                }
                            });
                        }

                    }

                });

                $("#a_mkdir").click(function (){

                    //prompt层
                    layer.prompt({title: '新建文件夹', formType: 0,value:""}, function(text, index){
                        $.ajax({
                            url:"${pageContext.request.contextPath}/info/userfile?method=getNewFloder",
                            type: "post",
                            contentType: 'application/json;charset=UTF-8',
                            data:JSON.stringify({
                                "id":objdata.id,
                                "name":text,
                                "Catalogue" : Catalogue,
                                "currentpath" : currentpath
                            }),
                            success : function(data,textStatus,xhr) {
                                if(xhr.status === 200) {
                                    layer.msg('文件夹创建成功', {
                                        icon: 1,//状态图标
                                        Time: 4000//展示时间为4s
                                    });//这里用于检测上传状态是否成功
                                    parent.layui.table.reload('fileListTable');
                                }

                            },
                            error: function(e) {
                                console.log("error")
                            }
                        })
                        layer.close(index);

                    });

                })

                //返回上一层
                $("#a_uppage").click(function(){
                    if (Catalogue != 0) {
                        parent.layui.table.reload('fileListTable',{
                            url : "${pageContext.request.contextPath}/info/userfile?method=getSuperior",
                            where : {
                                "Catalogue" : Catalogue,
                                "currentpath" : currentpath,
                            }
                        });
                    }
                    layer.msg('上一级');
                })



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

        <script>
            //注意进度条依赖 element 模块，否则无法进行正常渲染和功能性操作
            layui.use('element', function(){
                var element = layui.element;
            });
        </script>

    </div>

</div>

</body>
</html>
