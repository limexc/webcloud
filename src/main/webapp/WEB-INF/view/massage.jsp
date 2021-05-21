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
    <table class="layui-hide" id="msgListTable"  lay-filter="msgListTable"></table>
</div>




<script>
    console.log(${requestScope.get("type")})
    layui.use('table', function(){
        let table = layui.table
            ,form = layui.form;
        let objdata;

        table.render({
            elem: '#msgListTable'
            ,url:'${pageContext.request.contextPath}/admin/system/msglist?type=${requestScope.get("type")}'
            ,cellMinWidth: 80
            ,height: 'full-5'
            ,cols: [[
                {field:'id', title:'ID', width:100, unresize: true, sort: true}
                ,{field:'username', title:'用户名', templet: '#usernameTpl'}
                ,{field:'title', title:'标题'}
                ,{field:'create_time', title:'请求时间',minWidth:180,sort: true}
                ,{field:'status', title: '处理状态', minWidth:80,sort: true,
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
                            //暂时保留
                            return "正在处理";
                        }
                        return "其他";
                    }

                }
            ]]
            //,page: true
        });

        //监听行单击事件（双击事件为：rowDouble  单击事件：row）  START
        table.on('row(msgListTable)', function(obj){
            let objdata = obj.data;
            layer.alert(
                "<span>描述："+objdata.info+"</span><br>"+
                "<span>扩容大小："+objdata.size+"</span><br>"+
                "<span>请求时间："+objdata.create_time+"</span><br>",
                //JSON.stringify(data),
                {
                    title: "工单："+objdata.title,
                    btnAlign: 'c',
                    btn:["完成","去处理","驳回"],
                    //关于扩容请求可以直接点击完成，自动进行扩容
                    yes:function(index, layero){
                        //do something
                        status1(obj,index,layero,objdata);
                        layer.close(index); //如果设定了yes回调，需进行手工关闭
                    },
                    //去完成 按键，仅将工单状态置为 正在处理
                    btn2:function(index, layero){
                        //do something
                        status2(obj,index,layero,objdata);

                        layer.close(index); //如果设定了yes回调，需进行手工关闭
                    },

                    //驳回请求
                    btn3:function(index, layero){
                        status3(obj,index,layero,objdata);
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
 <!--0 未处理 1 通过  2 驳回 3 正在处理-->
<script>
    function status1(obj,index,layero,objdata){
        layer.confirm(
            '您确定将此工单置为完成状态吗？<br/>扩容请求点击确定后会自动进行扩容。',
            function(index){
                //将工单id传回
                let objdata = obj.data;
                $.ajax({
                    url:"${pageContext.request.contextPath}/admin/system/set_msg_status",
                    type:"post",
                    dataType:"json",
                    contentType: 'application/json;charset=UTF-8',
                    //{"title":"asd","info":"asd","size":"aa"}
                    data: JSON.stringify({
                        "id":objdata.id,
                        "status":1
                    }),
                    success : function(data,xhr) {
                    if(data === "ok") {
                        layer.prompt({
                            formType: 0,
                            //value: '',
                            title: '回复',
                            area: ['800px', '350px'] //自定义文本域宽高
                        }, function(value, index, elem){
                            $.ajax({
                                url:"${pageContext.request.contextPath}/admin/system/set_msg_status",
                                type:"post",
                                dataType:"json",
                                contentType: 'application/json;charset=UTF-8',
                                //{"title":"asd","info":"asd","size":"aa"}
                                data: JSON.stringify({
                                    "id":objdata.id,
                                    "status":1,
                                    "rep":value
                                })
                            })
                            layer.close(index);
                        });
                        layer.msg('修改成功！', {
                            icon: 1,//状态图标
                            Time: 4000//展示时间为4s
                        });//这里用于检测上传状态是否成功
                        able.reload('msgListTable');
                    }else {
                        layer.msg('提交失败');
                    }
                }
                })


                layer.close(index);
        })

    }
    function status2(obj,index,layero,objdata){
                layer.confirm(
                    '您确定将此工单置为正在处理状态吗？',
                    function(index){
                        //将工单id传回
                        let objdata = obj.data;
                        $.ajax({
                            url:"${pageContext.request.contextPath}/admin/system/set_msg_status",
                            type:"post",
                            dataType:"json",
                            contentType: 'application/json;charset=UTF-8',
                            //{"title":"asd","info":"asd","size":"aa"}
                            data: JSON.stringify({
                                "id":objdata.id,
                                "status":3
                            }),
                            success : function(data,xhr) {
                                if(data === "ok") {
                                    layer.msg('修改成功！', {
                                        icon: 1,//状态图标
                                        Time: 4000//展示时间为4s
                                    });//这里用于检测上传状态是否成功
                                    able.reload('msgListTable');
                                }else {
                                    layer.msg('提交失败');
                                }
                            }
                        })


                        layer.close(index);
                    })

                layer.close(index);

    }
    function status3(obj,index,layero,objdata){
                layer.confirm(
                    '您确定驳回此请求吗？',
                    function(index){
                        layer.confirm(
                            '您确定将此工单置为完成状态吗？<br/>扩容请求点击确定后会自动进行扩容。',
                            function(index){
                                //将工单id传回
                                let objdata = obj.data;
                                $.ajax({
                                    url:"${pageContext.request.contextPath}/admin/system/set_msg_status",
                                    type:"post",
                                    dataType:"json",
                                    contentType: 'application/json;charset=UTF-8',
                                    //{"title":"asd","info":"asd","size":"aa"}
                                    data: JSON.stringify({
                                        "id":objdata.id,
                                        "status":2
                                    }),
                                    success : function(data,xhr) {
                                        if(data === "ok") {
                                            layer.msg('修改成功！', {
                                                icon: 1,//状态图标
                                                Time: 4000//展示时间为4s
                                            });//这里用于检测上传状态是否成功
                                            able.reload('msgListTable');
                                        }else {
                                            layer.msg('提交失败');
                                        }
                                    }
                                })


                                layer.close(index);
                            })

                        layer.close(index);
                    })

                layer.close(index);
    }




</script>