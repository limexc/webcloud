<%--
  Created by IntelliJ IDEA.
  User: ADMIN
  Date: 2021/5/5
  Time: 16:46
  这个应该放在view下，暂时放到这里
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <!--css以及js文件引入  start-->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/default.css" />
    <script type="application/javascript" src="${pageContext.request.contextPath}/static/js/jquery-3.6.0.js"></script>
</head>
<body>
<!--div内通过传入的不同type放入不同的显示标签-->
<div id="view_div" style="clear:both;display:block;margin:auto;width: 100%;height: 100%">

</div>


<script>
    /*获取到Url里面的参数*/
    function GetQueryString(name) {
        let reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)')
        let r = window.location.search.substr(1).match(reg) // search,查询？后面的参数，并匹配正则
        if (r != null) {
            return unescape(r[2])
        }
        return null
    }
    //console.log(GetQueryString('ufid'))
    //console.log(GetQueryString('type'))
    let type = GetQueryString('type');
    let ufid =  GetQueryString('ufid');
    let fid =  GetQueryString('fid');
    let temp_view_url;

    if (ufid==="Null"){
        temp_view_url = '${pageContext.request.contextPath}/file/adownload?fid='+fid;
    }else {
        temp_view_url = '${pageContext.request.contextPath}/file/download?ufid='+ufid;
    }




    function SetViewDiv(type){
        if (type==="music"){
            $("#view_div").html(
                '<audio controls="controls" style="width: 100%;margin-top: 60px">'+
                    '<source src="" type="audio/mp3" id="file_view"/>'+
                    'Your browser does not support this audio format.'+
                '</audio>'
            )
        }else if (type==="video"){
            $("#view_div").html(
                '<video width="100%" height="100%" controls="controls">'+
                    '<source src="" type="video/mp4" id="file_view"/>'+
                '</video>'
            )

        }else if (type==="pic"){
            $("#view_div").html(
                '<img src="" id="file_view" width="100%" height="100%" />'
            )
        }
    }
    SetViewDiv(type);

    $('#file_view').attr('src', temp_view_url);
</script>

</body>
</html>
