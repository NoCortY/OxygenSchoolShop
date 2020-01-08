$(function() {
	var registerUrl = '/schoolshop/shop/ownerregister';
	$('#submit').click(function() {
		var localAuth = {};
		var personInfo = {};
		localAuth.userName = $('#userName').val();
		localAuth.password = $('#password').val();
		personInfo.phone = $('#phone').val();
		personInfo.email = $('#email').val();
		personInfo.name = $('#name').val();
		localAuth.personInfo = personInfo;
		var thumbnail = $('#small-img')[0].files[0];
		console.log(thumbnail);
		var formData = new FormData();
		formData.append('thumbnail', thumbnail);
		formData.append('localAuthStr', JSON.stringify(localAuth));
		var verifyCodeActual = $('#j_captcha').val();
		if (!verifyCodeActual) {
			$.toast('请输入验证码！');
			return;
		}
		formData.append("verifyCodeActual", verifyCodeActual);
		$.ajax({
			url : registerUrl,
			type : 'POST',
			data : formData,
			contentType : false,
			processData : false,
			cache : false,
			success : function(data) {
				if (data.success) {
					$.toast('提交成功！');
					window.location.href = '/schoolshop/shop/ownerlogin';
				} else {
					$.toast('提交失败！');
					$('#captcha_img').click();
				}
			}
		});
	});

	$('#back').click(function() {
		window.location.href = '/schoolshop/shop/ownerlogin';
	});
});
