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
<script type="application/javascript" src="${pageContext.request.contextPath}/static/js/jquery-3.6.0.js"></script>
<script src="${pageContext.request.contextPath}/static/layui/layui.js"></script>

<div style="width: 100%;height: 100%;background-color: white;margin-top: -10px ">
    <table class="layui-hide" id="userListTable" lay-filter="userListTable"></table>

    <script type="text/html" id="switchTpl">
        {{# if(d.group === false){ }}
        <span class="layui-badge layui-bg-green">普通</span>
        {{# } else { }}
        <span class="layui-badge">管理</span>
        {{# } }}
    </script>

    <script type="text/html" id="checkboxTpl">
        {{# if(d.status === true){ }}
        <span class="layui-badge layui-bg-green">启用</span>
        {{# } else { }}
        <span class="layui-badge">禁用</span>
        {{# } }}
    </script>


</div>




<script>
    layui.use('table', function(){
        let table = layui.table
            ,form = layui.form;
        let obj_data;
        table.render({
            elem: '#userListTable'
            ,id:'userListTable'
            ,url:'${pageContext.request.contextPath}/admin/system/userlist'
            ,cellMinWidth: 80
            ,height: 'full-5'
            ,cols: [[
                {type:'numbers', title:"序号"}
                ,{field:'id', title:'ID', width:100, unresize: true, sort: true}
                ,{field:'username', title:'用户名', templet: '#usernameTpl'}
                ,{field:'email', title:'邮箱'}
                ,{field:'create_at', title: '注册时间', minWidth:80, sort: true}
                ,{field:'now_storage', title:'当前已使用空间'}
                ,{field:'storage', title:'空间大小',event:'set_Filesize'}
                ,{field:'group', title:'用户属性', width:160, templet: '#switchTpl', unresize: true,event: 'set_group'}
                ,{field:'status', title:'状态', width:110, templet: '#checkboxTpl', unresize: true,event: 'set_status'}
            ]]
            //,page: true
        });

        table.on('tool(userListTable)', function(obj){
            obj_data = obj.data;
            if (obj.event === 'set_Filesize'){
                //alert("点击了空间大小 :"+objdata.storage)
                layer.prompt({
                    formType: 0,
                    value: obj_data.storage,
                    title: '设置空间大小|请不要携带小数点等非法字符，空间容量以KB|MB|GB|TB结尾',
                    //area: ['800px', '350px'] //自定义文本域宽高
                }, function(value, index, elem){
                    $.ajax({
                        url: "${pageContext.request.contextPath}/admin/system/changeStorage",
                        type: "post",
                        data:{
                            "userid":obj_data.id,
                            "storage":value
                        },
                        success:function (data){
                            if (data==="yes"){
                                layer.msg("修改成功")
                                table.reload('userListTable');
                            }else {
                                layer.msg("修改失败")
                            }
                        },
                        error:function (){
                            alert("错误")
                        }
                    })

                    layer.close(index);
                });

            }else if (obj.event === 'set_group'){
                //layer.
                //弹出提示框，提示是否将组 置为 其 相反的值
                let temp_group;
                if (obj_data.group===true){
                    temp_group = "普通"
                }else {
                    temp_group = "管理"
                }
                //弹出提示框，提示是否将组 置为 其 相反的值
                layer.open({
                    title:'修改用户：'+obj_data.username+" 权限",
                    content: '是否将用户置为'+temp_group,
                    btn:['确定','取消'],
                    yes: function(index, layero){
                        $.ajax({
                            url: "${pageContext.request.contextPath}/admin/system/changeGroup",
                            type: "post",
                            data:{
                                "userid":obj_data.id,
                            },
                            success:function (data){
                                if (data==="yes"){
                                    layer.msg("修改成功")
                                    table.reload('userListTable');
                                }else {
                                    layer.msg("修改失败")
                                }
                            },
                            error:function (){
                                alert("错误")
                            }
                        })

                        layer.close(index);
                    },
                    btn2:function (index, layero){
                        layer.close(index);
                    }
                })
            }else if (obj.event === 'set_status'){
                let temp_status;
                if (obj_data.status===true){
                    temp_status = "禁用"
                }else {
                    temp_status = "启用"
                }
                //弹出提示框，提示是否将组 置为 其 相反的值
                layer.open({
                    title:'修改用户：'+obj_data.username+" 状态",
                    content: '是否将用户置为'+temp_status,
                    btn:['确定','取消'],
                    yes: function(index, layero){
                        $.ajax({
                            url: "${pageContext.request.contextPath}/admin/system/changeStatus",
                            type: "post",
                            data:{
                                "userid":obj_data.id,
                            },
                            success:function (data){
                                if (data==="yes"){
                                    layer.msg("修改成功")
                                    table.reload('userListTable');
                                }else {
                                    layer.msg("修改失败")
                                }
                            },
                            error:function (){
                                alert("错误")
                            }
                        })

                        layer.close(index);
                    },
                    btn2:function (index, layero){
                        layer.close(index);
                    }
                });


            }


        });

    });
</script>