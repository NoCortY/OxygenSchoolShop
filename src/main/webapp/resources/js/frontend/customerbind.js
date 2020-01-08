$(function() {
	var bindUrl = '/schoolshop/frontend/bindlocalauth';

	$('#submit').click(function() {
		var userName = $('#username').val();
		var password = $('#psw').val();
		var verifyCodeActual = $('#j_captcha').val();
		var needVerify = false;
		if (!verifyCodeActual) {
			$.toast('请输入验证码！');
			return;
		}
		$.ajax({
			url : bindUrl,
			async : false,
			cache : false,
			type : "post",
			dataType : 'json',
			data : {
				userName : userName,
				password : password,
				verifyCodeActual : verifyCodeActual
			},
			success : function(data) {
				if (data.success) {
					$.toast('绑定成功！');
					window.location.href = 'javascript :history.back(-1);';
				} else {
					$.toast('绑定失败！');
					$('#captcha_img').click();
				}
			}
		});
	});
});