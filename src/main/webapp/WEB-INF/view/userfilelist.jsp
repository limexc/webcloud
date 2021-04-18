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


    <div style="position: absolute;display: inline-block; top:7px;float:right;right: 30px">
        <div class="layui-inline">
            <input class="layui-input" name="selectFile" id="selectFile" autocomplete="off">
        </div>
        <button class="layui-btn" id="select_btn" data-type="reload">搜索</button>
    </div>



</div>

<div class="file_view">

    <!--内容table容器-->
    <table class="layui-hide" id="FileListTable" lay-filter="FileListTable"></table>

    <!--头部工具栏-->
    <script type="text/html" id="th_tool">
        <p>当前的路径：</p>
    </script>

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
        var objdata;

        layui.use('table', function(){
            var table = layui.table;

            //执行一个 table 实例
            table.render({
                id:"fileListTable",
                //表格table的id属性
                elem: '#FileListTable',
                height: 'full-60',
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
                /*
                page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
                  layout: ['count', 'prev', 'page', 'next', 'limit', 'refresh', 'skip']//自定义分页布局
                    ,limits:[5,10,15]
                    ,first: false //不显示首页
                    ,last: false //不显示尾页
                },
                */
                title: true   //表头
                ,toolbar: true //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
                ,cols: [[
                    //{type:'radio'},
                    //{type: 'checkbox', fixed: 'check'},
                    //{field: 'filetype', title: '类型', align:'center'},
                    {title: "ID",width:'5%',type:"numbers"},
                    {field: 'vfname', title: '文件名', sort : true,event :'fileclick',
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
                    ,{field: 'uptime', title: '修改时间',align:'center', sort : true,templet: '<div>{{ layui.laytpl.toDateString(d.uptime) }}</div>'}
                    ,{fixed: 'right', title: '操作', align:'center', toolbar: '#barDemo'},
                    {field: 'id',hide:true}
                ]]
                //,page: true


            });

            //搜索文件
            var $ = layui.$, active = {
                reload: function(){
                    var selectFile = $('#selectFile');
                    alert("start:"+selectFile.val())

                    //执行重载
                    table.reload('fileListTable', {
                        url:"${pageContext.request.contextPath}/info/selectfile?key="+selectFile.val()
                    });
                }
            };

            $('#select_btn').click(function(){
                alert("搜索")
                var type = $(this).data('type');
                active[type] ? active[type].call(this) : '';
            });


            //监听行工具事件
            table.on('tool(FileListTable)', function(obj){

                objdata = obj.data;
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
                                    table.reload('fileListTable');
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
                    if (objdata.filesize==="-"){
                        layer.alert("文件夹暂不支持共享");
                    }else {
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
                                layer.open(
                                    {
                                        title:"提示！"
                                        ,btn:["复制到粘贴板"]
                                        ,btnAlign: 'c'
                                        ,content:"这个文件已经分享过了！<br />分享地址为：<br />"+
                                        "<textarea name='s_url' rows=2  style='resize: none;width: 100%'>"+baseurlpath+'share/file/'+data.tips+"</textarea>"
                                        ,yes : function (index, layero){
                                            //let shareurl = $(".s_url").text();
                                            //alert(shareurl)
                                            copyUrl();
                                            layer.close(index);
                                        }
                                    })
                            }else if (data.url=="err3"){
                                layer.open({title:"错误！",content:"系统错误！请您稍后再次尝试！"})
                            }else {
                                layer.open({
                                    title:"提示！"
                                    ,btn:["复制到粘贴板"]
                                    ,btnAlign: 'c'
                                    ,content:
                                        "您的分享地址为：<br />"+
                                        "<textarea name='s_url' rows=2  style='resize: none;width: 100%'>"+baseurlpath+'share/file/'+data.tips+"</textarea>"
                                    ,yes : function (index, layero){
                                        copyUrl();
                                        layer.close(index);
                                    }
                                })

                            }

                        }
                    })
                    }
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
                                    //parent.layui.table.reload
                                    table.reload('fileListTable',{
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
                        table.reload('fileListTable',
                            {
                                url: "${pageContext.request.contextPath}/info/userfile?method=getSub",
                                where:{
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
                        url:"${pageContext.request.contextPath}/info/userfile?method=getNewFloder&name="+text+"&Catalogue="+Catalogue+"&currentpath="+currentpath,
                        type: "post",
                        contentType: 'application/json;charset=UTF-8',

                        success : function(data,textStatus,xhr) {
                            if(xhr.status === 200) {
                                layer.msg('文件夹创建成功', {
                                    icon: 1,//状态图标
                                    Time: 4000//展示时间为4s
                                });//这里用于检测上传状态是否成功
                                table.reload('fileListTable', {
                                    //这里重载的地址并不正确
                                    url : "${pageContext.request.contextPath}/info/userfile?method=getSuperior",
                                    where: {
                                        "Catalogue": Catalogue-1,
                                        "currentpath": currentpath,
                                    }
                                });
                            }

                        },
                        error: function(XMLHttpRequest) {
                            console.log("错误状态："+XMLHttpRequest.status);
                        }
                    })
                    layer.close(index);

                });

            })

            //返回上一层
            $("#a_uppage").click(function(){
                if (Catalogue != 0) {
                    table.reload('fileListTable',{
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
        function copyUrl() {
            let S_Url =  document.getElementsByName("s_url");
            S_Url[0].select(); // 选择对象
            document.execCommand("Copy"); // 执行浏览器复制命令
            layer.msg("复制成功");
            //alert("已复制好，可贴粘。");
        }
    </script>

</div>