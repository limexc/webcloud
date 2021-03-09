<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Test</title>
    <script src="${pageContext.request.contextPath}/static/js/jquery/jquery-3.6.0.js"></script>
    <script type="application/javascript">

        function userinfo(){
            $.ajax({
                url:"user/listuser",
                type:"post",
                dataType:"json",

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
    </div>

</body>
</html>
