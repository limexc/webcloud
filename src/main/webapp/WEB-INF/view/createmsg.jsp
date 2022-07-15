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
<script type="application/javascript" src="${pageContext.request.contextPath}/static/js/jquery-3.6.0.js"></script>



<!--Layui框架-->
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/layui/css/layui.css"  media="all">
<script src="${pageContext.request.contextPath}/static/layui/layui.js" charset="utf-8"></script>

<div style="margin: 10% 25%;width: 600px">
<h1 style="font-size: 32px;margin-top: -40px">新建工单</h1><br><hr><br>

    <form class="layui-form" action="">
        <div class="layui-form-item">
            <label class="layui-form-label">标题:</label>
            <div class="layui-input-block">
                <input type="text" name="title" required  lay-verify="required" placeholder="请输入标题" autocomplete="off" class="layui-input">
            </div>
        </div>

        <div class="layui-form-item layui-form-text">
            <label class="layui-form-label">详细内容:</label>
            <div class="layui-input-block">
                <textarea name="info" required  lay-verify="required" placeholder="请输入内容，这将作为是否能快速处理的重要参考！" autocomplete="off" class="layui-textarea"></textarea>
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">存储空间:</label>
            <div class="layui-input-inline">
                <input type="text" name="size"  placeholder="扩容后的大小|扩容请求必填" class="layui-input">
            </div>
            <div class="layui-form-mid layui-word-aux">请不要携带小数点，以 MB | GB | TB 结尾</div>
        </div>

        <div class="layui-form-item">
            <!--<label class="layui-form-label">当前时间：<span onload="getCurrentTime()"></span></label>-->
        </div>


        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn" lay-submit lay-filter="formDemo">立即提交</button>
                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
            </div>
        </div>
    </form>

    <script>
        //Demo
        layui.use('form', function(){
            var form = layui.form;

            //监听提交
            form.on('submit(formDemo)', function(data){
                //json格式数据
                $.ajax({
                    url:'${pageContext.request.contextPath}/msg/req_msg',
                    type:"post",
                    dataType:"json",
                    contentType: 'application/json;charset=UTF-8',
                    //{"title":"asd","info":"asd","size":"aa"}
                    data:JSON.stringify(data.field),
                    success : function(data,xhr) {
                        if(data === "ok") {
                            layer.msg('提交工单成功！', {
                                icon: 1,//状态图标
                                Time: 4000//展示时间为4s
                            });//这里用于检测上传状态是否成功

                        }else {
                            layer.msg('提交失败');
                        }
                    }
                })
                //layer.msg(JSON.stringify(data.field));

                return false;
            });
        });
    </script>


    <script>

        /**
         * 获取当前时间 格式：yyyy-MM-dd HH:MM:SS
         */
        function getCurrentTime() {
            var date = new Date();//当前时间
            var month = zeroFill(date.getMonth() + 1);//月
            var day = zeroFill(date.getDate());//日
            var hour = zeroFill(date.getHours());//时
            var minute = zeroFill(date.getMinutes());//分
            var second = zeroFill(date.getSeconds());//秒

            //当前时间
            var curTime = date.getFullYear() + "-" + month + "-" + day
                + " " + hour + ":" + minute + ":" + second;

            return curTime;
        }

        /**
         * 补零
         */
        function zeroFill(i){
            if (i >= 0 && i <= 9) {
                return "0" + i;
            } else {
                return i;
            }
        }

    </script>


</div>

