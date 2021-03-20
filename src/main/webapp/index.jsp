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
        <script type="application/javascript" src="${pageContext.request.contextPath}/static/js/spark-md5.js"></script>
        <script type="application/javascript" src="${pageContext.request.contextPath}/static/js/jquery-3.6.0.js"></script>
        <script type="application/javascript" src="${pageContext.request.contextPath}/static/js/upfile.js"></script>

        <!--Google字体-->
        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+SC:wght@300&display=swap" rel="stylesheet">


        <script type="application/javascript">


        </script>

    </head>
    <body>

    <div class="head_menu">
        <a id="logo_ti">网盘？是的！稳定？不存在的！</a>
    </div>

        <div class="menu_left">
            <div id="photo_div"><img id="photo_img" src="${pageContext.request.contextPath}/static/images/dls.jpeg"></div>
            <div id="userinfo_div">
                <span>用户名</span>
                <span>登录日期</span>
                <span>容量</span>

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


            </div>
            <div id="file_head">
                <!--标题等信息-->
                <span>文件名</span>
                <samp>文件大小</samp>
                <span>上传时间</span>
                <span>操作</span>
            </div>
            <div class="file_view">
                
                <div class="file_info">
                    <span>图标</span>
                </div>
                <div class="file_info">
                    <span>文件名</span>
                </div>
                <div class="file_info">
                    <span>文件大小等</span>
                </div>
                
            </div>


            <div class="pagelimit">这里是分页</div>
        </div>

    </body>
</html>
