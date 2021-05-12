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
<script type="application/javascript" src="${pageContext.request.contextPath}/static/js/jquery-3.6.0.js"></script>

<div style="width: 100%;height: 100%;background-color: white;margin-top: -10px ">
    <table class="layui-hide" id="fileListTable"  lay-filter="fileListTable"></table>
</div>




<script>
    layui.use('table', function(){
        let table = layui.table
            ,form = layui.form;
        let objdata;

        table.render({
            elem: '#fileListTable'
            ,url:'${pageContext.request.contextPath}/admin/system/filelist'
            ,cellMinWidth: 80
            ,height: 'full-5'
            ,cols: [[
                {type:'numbers',title:'序号'}
                ,{field:'id', title:'ID', width:100, unresize: true, sort: true}
                ,{field:'filename', title:'文件名', templet: '#usernameTpl'}
                ,{field:'create_time', title:'初次上传时间',minWidth:180,sort: true}
                ,{field:'filesize', title:'文件大小',sort: true}
                ,{field:'filetype', title: '文件类型', minWidth:80,sort: true,
                    templet : function(d) {
                        if (d.filetype.startsWith("dtd")){
                            return "文档";
                        }else if (d.filetype.startsWith("pic")){
                            return "图片";
                        }else if (d.filetype.startsWith("video")){
                            return "视频";
                        }else if (d.filetype.startsWith("music")){
                            return "音乐";
                        }else if (d.filetype.startsWith("archive")){
                            return "压缩文档";
                        }else if (d.filetype.startsWith("app")){
                            return "可执行文件";
                        }
                        return "其他";
                    }

                }
            ]]
            //,page: true
        });

        //监听行单击事件（双击事件为：rowDouble  单击事件：row）  START
        table.on('row(fileListTable)', function(obj){
            let objdata = obj.data;
            let now_file_type = objdata.filetype;
            layer.alert(
                //"<img height='200' width='200' style='display:block;margin:0 auto;' src='${pageContext.request.contextPath}/static/images/dls.jpeg'></img><br>"+
                "<span>文件名："+objdata.filename+"</span><br>"+
                "<span>文件大小："+objdata.filesize+"</span><br>"+
                "<span>上传时间："+objdata.create_time+"</span><br>",
                //JSON.stringify(data),
                {
                    title: "文件信息："+objdata.filename,
                    btnAlign: 'c',
                    btn:["下载","查看","删除"],
                    //下载
                    yes:function(index, layero){
                        //do something
                        layer.confirm('下载该文件？', function(index){
                            window.location.href = '${pageContext.request.contextPath}/file/adownload?fid='+objdata.id;
                            layer.close(index);

                        });


                        layer.close(index); //如果设定了yes回调，需进行手工关闭
                    },
                    //view_file  查看
                    btn2:function(index, layero){
                        //do something
                        function view_file(type,fid){
                            //原本是ufid  管理员查看并没有ufid 用fid
                            let temp_file_view_url ="${pageContext.request.contextPath}/static/pages/fileview.jsp?type="+type+"&ufid=Null&fid="+fid
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

                        if (now_file_type.startsWith("music")){
                            view_file("music",objdata.id);
                            //alert(now_file_type)
                        }else if (now_file_type.startsWith("video")){
                            view_file("video",objdata.id);
                            //alert(now_file_type)
                        }else if (now_file_type.startsWith("pic")){
                            view_file("pic",objdata.id);
                            //alert(now_file_type)
                        }else {
                            alert("当前文件暂不支持预览"+now_file_type)
                        }

                        layer.close(index); //如果设定了yes回调，需进行手工关闭
                    },

                    //删除
                    btn3:function(index, layero){
                        del_file(obj,index,layero,objdata);
                        layui.layer.close(index); //如果设定了yes回调，需进行手工关闭
                    }

                }

            );

            //标注选中样式
            obj.tr.addClass('layui-table-click').siblings().removeClass('layui-table-click');
        });
        //监听行单击事件（双击事件为：rowDouble） END


    });
</script>

<script>
    function del_file(obj,index,layero,objdata){
        layer.alert(
            "你确定要删除文件：'"+objdata.filename+"' 吗？",
            {
                title:"警告！",
                btn:["删除","取消"],
                yes:function (index, layero){
                    $.ajax({
                        url: "${pageContext.request.contextPath}/file/del_file",
                        //做些事情
                        type: "post",
                        data:{
                            "fid":objdata.id
                        },
                        success:function (data){
                            if (data.del==="ok"){
                                layer.msg("删除成功")
                            }else {
                                layer.msg("删除失败")
                            }
                        },
                        error:function (){
                            alert("错误")
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