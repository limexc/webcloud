window.onload = function() {
    var getcode = $("#sub_ve").get(0);
    var str = "秒后重新获取 ";
    //设置等待时间
    var time = 5;

    getcode.onclick = function() {

        var InputEmail = $("#email").val();
        //正则表达式，判断格式。
        var reg = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;

        //判断一下邮箱格式，暂时设置为不为空
        if (InputEmail!=null&&reg.test(InputEmail)){
            //控制台打印输出一下
            console.log(InputEmail+"点击了发送验证码，准备将发送标签置为disable");

            //单点击后，将当前选中标签属性置为disable
            $(this).attr("disabled", true);

            //将响应交给后台servlet
            $.post("http://localhost:8080/CloudWeb/system/sendmail",{
                email:InputEmail
            });
            console.log("请求已发送")
            //弹出提示框
            alert("验证码已发送至邮箱，若长时间未收到验证码请查看垃圾邮件");


            //页面计时
            console.log("开始倒计时");
            let timing = setInterval(function() {
                if(time == 0) {
                    $("#sub_ve").removeAttr("disabled");
                    $("#sub_ve").html("重新发送验证码");
                    clearInterval(timing);
                } else {
                    $("#sub_ve").html(time + str);
                    time--;
                }
                //设置减时时间间隔为1秒
            }, 1000);
        }else {
            alert("请输入正确的邮箱！")
        }


    }
}