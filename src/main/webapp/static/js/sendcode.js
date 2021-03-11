window.onload = function() {
	var getcode = $("#sub_ve").get(0);
	alert("给你给提示"+getcode)
	var str = "秒后重新获取 ";
	
	getcode.onclick = function() {
		alert("点击了")
		var InputEmail = $("#email").val();
		//控制台打印输出一下
		console.log(InputEmail);
		console.log("点击了发送验证码");
		//设置等待时间
		var time = 5;
		//单点击后，将当前选中标签属性置为disable
		console.log("准备置为disable");
		$(this).attr("disabled", true);
		//弹出提示框
		alert("验证码已发送至邮箱，若长时间未收到验证码请查看垃圾邮件");
		//将响应交给后台servlet
		$.post("http://localhost:8080/CloudWeb/sendmail",{
			email:InputEmail
		});
		console.log("已转发至servlet")
		//页面计时
		console.log("开始倒计时");
		var timing = setInterval(function() {
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
		
	}
}