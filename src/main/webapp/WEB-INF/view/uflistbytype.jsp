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
<a style="font-size: 35px" id="a_msg_method">当前的文件类型：<%--${requestScope.get("filetype")}--%></a><br>
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
    layui.use('table', function(){
        var table = layui.table;

        table.render({
            elem: '#file_table'
            //模拟数据
            ,url:'${pageContext.request.contextPath}/info/filelistbytype?filetype=${requestScope.get("filetype")}'
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
            let objdata = obj.data;
            /*获取到Url里面的参数*/
            (function ($) {
                $.getUrlParam = function (name) {
                    let reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
                    let r = window.location.search.substr(1).match(reg);
                    if (r != null) return unescape(r[2]); return null;
                }
            })(jQuery);
            let now_file_type = $.getUrlParam('filetype');
            layer.alert(
                //"<img height='200' width='200' style='display:block;margin:0 auto;' src='${pageContext.request.contextPath}/static/images/dls.jpeg'></img><br>"+
                "<span>文件名："+objdata.vfname+"</span><br>"+
                "<span>文件大小："+objdata.filesize+"</span><br>"+
                "<span>修改时间："+objdata.uptime+"</span><br>",
                //JSON.stringify(data),
                {
                    title: "文件信息："+objdata.vfname,
                    btnAlign: 'c',
                    btn:["下载","查看","分享","删除"],
                    //下载
                    yes:function(index, layero){
                        //do something
                        layer.confirm('下载该文件？', function(index){
                            window.location.href = '${pageContext.request.contextPath}/file/download?ufid='+objdata.id;
                            layer.close(index);

                        });


                        layer.close(index); //如果设定了yes回调，需进行手工关闭
                    },
                    //view_file  查看
                    btn2:function(index, layero){
                        //do something
                        function view_file(type,ufid){
                            let temp_file_view_url ="${pageContext.request.contextPath}/static/pages/fileview.jsp?type="+type+"&ufid="+ufid+"&fid=null"
                            layer.open({
                                type: 2,
                                title: '文件预览',
                                shade: 0,
                                //maxmin: true,
                                area: ['700px','500px'],
                                content:[temp_file_view_url,"no"]
                            });
                        }
                        //根据判断的内容来展示文件
                        //let temp_file_view_url = '${pageContext.request.contextPath}/file/download?ufid='+objdata.id;
                        if (now_file_type==="music"){
                            view_file("music",objdata.id);
                            //alert(now_file_type)
                        }else if (now_file_type==="video"){
                            view_file("video",objdata.id);
                            //alert(now_file_type)
                        }else if (now_file_type==="pic"){
                            view_file("pic",objdata.id);
                            //alert(now_file_type)
                        }else {
                            alert("当前文件暂不支持预览")
                        }

                        layer.close(index); //如果设定了yes回调，需进行手工关闭
                    },
                    //分享
                    btn3:function(index, layero){
                        //do something

                        //Ajax发送信息
                        $.ajax({
                            url:'${pageContext.request.contextPath}/share/getshareurl?ufid='+objdata.id,
                            type:"post",
                            //不知道怎么写对不对，先试试
                            success : function (data){
                                sharecode =data.tips;
                                altShare(data);
                            }
                        })

                        function altShare(data){

                            let baseurlpath = '<%= baseUrlPath%>'
                            if (data.url==='err1') {
                                layui.layer.open({title:"警告！",content:"您输入的信息有误！"});
                            }else if (data.url==="err2"){
                                layui.layer.open(
                                    {
                                        title:"提示！"
                                        ,btn:["复制到粘贴板"]
                                        ,btnAlign: 'c'
                                        ,content:"这个文件已经分享过了！<br />分享地址为：<br />"+
                                            "<textarea name='s_url' rows=2  style='resize: none;width: 100%'>"+baseurlpath+'share/file/'+sharecode+"</textarea>"
                                        ,yes : function (index, layero){
                                            //let shareurl = $(".s_url").text();
                                            //alert(shareurl)
                                            copyUrl();
                                            layer.close(index);
                                        }
                                    })
                            }else if (data.url==="err3"){
                                layui.layer.open({title:"错误！",content:"系统错误！请您稍后再次尝试！"})
                            }else {
                                layui.layer.open({
                                    title:"提示！"
                                    ,btn:["复制到粘贴板"]
                                    ,btnAlign: 'c'
                                    ,content:
                                        "您的分享地址为：<br />"+
                                        "<textarea name='s_url' rows=2  style='resize: none;width: 100%'>"+baseurlpath+'share/file/'+sharecode+"</textarea>"
                                    ,yes : function (index, layero){
                                        copyUrl();
                                        layui.layer.close(index);
                                    }
                                })

                            }

                        }



                        layui.layer.close(index); //如果设定了yes回调，需进行手工关闭
                    },
                    //删除
                    btn4:function(index, layero){
                        del_file(obj,index,layero,objdata);
                        layui.layer.close(index); //如果设定了yes回调，需进行手工关闭
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
                alert("start:"+selectFile.val())

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

    function del_file(obj,index,layero,objdata){
        layer.alert(
            "你确定要删除文件：'"+objdata.vfname+"' 吗？",
            {
                title:"警告！",
                btn:["删除","取消"],
                yes:function (index, layero){
                    $.ajax({
                        url: '${pageContext.request.contextPath}/info/deletfile?action=recycle',
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
                                //table.reload('fileListTable');
                            }
                        }
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