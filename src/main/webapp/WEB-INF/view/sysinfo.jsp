<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/static/layui/css/layui.css">
<script type="application/javascript" src="${pageContext.request.contextPath}/static/js/jquery-3.6.0.js"></script>
<script src="${pageContext.request.contextPath}/static/layui/layui.js"></script>

<%
    //初步处理一下未登录就访问的用户
    if (session.getAttribute("user")==null){
        pageContext.forward("/login.jsp");
    }
%>

<div id="info-panel-div" style=" background-color: white;width: 100%;height: 100%;">

    <div class="layui-card">
        <div class="layui-card-header"><h2>系统信息</h2></div>
        <div class="layui-card-body" style="height: 150px">
            <div class="layuimini-container layuimini-page-anim" style="margin-left:18%;padding-top: 0.3%">
                <div class="layuimini-main layui-top-box">
                    <div class="layui-row layui-col-md12">
                        <div class="layui-col-md3">
                            <div class="col-xs-6 col-md-3">
                                <div class="panel layui-bg-cyan">
                                    <div class="panel-body">
                                        <div class="panel-title">
                                            <h1 style="padding-top: 10px">用户统计</h1>
                                            <br>
                                        </div>
                                        <div class="panel-content">
                                            <h1 class="no-margins" style="text-align:center" id="user_row">1234</h1>
                                            <br>
                                            <h3>系统用户总数</h3>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="layui-col-md3">
                            <div class="col-xs-6 col-md-3">
                                <div class="panel layui-bg-blue">
                                    <div class="panel-body">
                                        <div class="panel-title">
                                            <h1 style="padding-top: 10px">文件数量</h1>
                                            <br>
                                        </div>
                                        <div class="panel-content">
                                            <h1 class="no-margins" style="text-align:center" id="file_row">1234</h1>
                                            <br>
                                            <h3>系统存储文件总数</h3>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="layui-col-md3">
                            <div class="col-xs-6 col-md-3">
                                <div class="panel layui-bg-green">
                                    <div class="panel-body">
                                        <div class="panel-title">
                                            <h1 style="padding-top: 10px">存储空间</h1>
                                            <br>
                                        </div>
                                        <div class="panel-content">
                                            <h1 class="no-margins" style="text-align:center" id="now_size">12GB</h1>
                                            <br>
                                            <h3 id="free_size">存储空间剩余：56GB</h3>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="layui-card">
        <div class="layui-card-header"><h2>最近新注册用户信息(TOP5)</h2></div>
        <div class="layui-card-body">
            <div style="align-content: center;width: 61.5%;height: 53%;margin-left: 18%" >
                <table id="newUserTable" lay-filter="newUserTable"></table>
            </div>

        </div>
    </div>



</div>

<script>
    layui.use('element', function(){
        var element = layui.element;

    });
</script>

<script>
    layui.use('table', function(){
        var table = layui.table;
        $("table").css("width", "100%")

        table.render({
            elem: '#newUserTable'
            ,url: '${pageContext.request.contextPath}/admin/system/newuser' //数据接口
            ,page: false //开启分页
            ,cols: [[ //表头
                {title: "ID",width:'5%',type:"numbers"},
                {field: 'username', title: '用户名', width:'25%'}
                ,{field: 'email', title: '邮箱', width:'25%'}
                ,{field: 'create_at', title: '注册时间', width:'25%'}
                ,{field: 'storage', title: '存储空间', width: '20%'}
            ]]
        });

    });
</script>

<script>
window.onload = function (){
    $.ajax({
        url:"${pageContext.request.contextPath}/admin/system/info",
        type: "post",
        success:function(data) {
            $("#user_row").html(data.user_row);
            $("#file_row").html(data.file_row);
            $("#now_size").html(data.now_size);
            $("#free_size").html("存储空间剩余："+data.free_size);
        },
        error:function (){
            alert("加载失败")
        }
    })
}

</script>