<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
	<head>
        <!---->
		<meta charset="utf-8">
		<title>文件分享</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/default.css" />
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/sharefile.css">
        <meta name="renderer" content="webkit">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layui/css/layui.css"  media="all">
	</head>
	<body>
        <div class="main_style">
            <div class="head_menu">
                <!--头部导航栏-->
                <ul class="layui-nav">
                    <li class="layui-nav-item"><a href="${pageContext.request.contextPath}/">网盘首页</a></li>
                    <!--登陆后显示用户名-->
                    <li class="layui-nav-item"><a href="">登陆</a></li>
                </ul>


            </div>
            <div class="file_main">

                <div class="file_ico">
                    <img src="" />

                </div>
                <div class="file_info">



                    <div style="font-size: 30px;text-align: center;padding: 56px 0px 20px 0px;">${requestScope.filename}</div>
                    <div>
                        <table width="99%" border="0" align="center" cellspacing="0">
                            <tr>
                                <td width="330" valign="top">
                                    <span class="p7">文件大小：</span>${requestScope.filesize}<br>
                                    <span class="p7">上传时间：</span>${requestScope.uptime}<br>
                                    <span class="p7">分享用户：</span>${requestScope.username}<br>
                                    <span class="p7">文件描述：</span>${requestScope.filetext}<br>
                                </td>
                            </tr>
                        </table>

                        <button type="button" id="download_btn" >下载文件</button>
                    </div>






                </div>
                <div class="file_down">
                    
                </div>
                
            </div>

            
        </div>


        <script src="${pageContext.request.contextPath}/static/layui/layui.js" charset="utf-8"></script>
        <script src="${pageContext.request.contextPath}/static/js/jquery-3.6.0.js" charset="utf-8"></script>

        <script>
            layui.use('element', function(){
                var element = layui.element; //导航的hover效果、二级菜单等功能，需要依赖element模块

                //监听导航点击
                element.on('nav(demo)', function(elem){
                    //console.log(elem)
                    layer.msg(elem.text());
                });
            });

            $("#download_btn").click(function () {
                window.location.href = '${pageContext.request.contextPath}/share/file/download/${requestScope.fileurl}';
            })


        </script>

	</body>
</html>
