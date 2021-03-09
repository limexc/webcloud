<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Test</title>
    <script type="application/javascript" src="${pageContext.request.contextPath}/static/js/jquery/jquery-3.6.0.js"></script>
    <script type="application/javascript">

        $(function (){
            userinfo();
        })

        function userinfo(){
            $.ajax({
                url:"${pageContext.request.contextPath}/user/listUser",
                //type:"post",
                dataType:"json",
                success:function (data){
                    //清除数据
                    $("#userbody").html("");
                    //添加数据
                    $.each(data,function (i,n){
                        $("#userbody").append("<tr>")
                        .append("<td>"+n.id+"</td>")
                        .append("<td>"+n.name+"</td>")
                        .append("<td>"+n.email+"</td>")
                        .append("</tr>")
                    })
                }

            })
        }
    </script>
</head>
<body>
    <div style="align-content: center">
        <table>
            <thead>
                <tr>
                    <td>id</td>
                    <td>姓名</td>
                    <td>邮件</td>
                </tr>
            </thead>
            <tbody id="userbody">

            </tbody>
        </table>
        <input type="button" value="查询" id="btnLoader">
    </div>

</body>
</html>
