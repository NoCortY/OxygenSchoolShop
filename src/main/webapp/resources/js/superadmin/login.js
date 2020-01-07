$(function() {
	setDialog();

	// 进入页面，焦点在用户名文本框上
	$("#userName").focus();
});

/**
 * Ajax执行登录操作
 * 
 * @return
 */
function doLogin() {
	var validateResult = true;
	// easyui 表单验证
	$('#loginTable input').each(function() {
		if ($(this).attr('required') || $(this).attr('validType')) {
			if (!$(this).validatebox('isValid')) {
				// 如果验证不通过，则返回false
				validateResult = false;
				return;
			}
		}
	});
	if (validateResult == false) {
		// 如果验证不通过，则不执行登录操作
		return;
	}

	$("#login_msg").html("登录中，请稍后...");
	$.ajax({
		async : false,
		cache : false,
		type : "post",
		dataType : 'json',
		url : "logincheck",// 请求的action路径
		data : {
			userName : $("#userName").val(),
			password : $("#password").val()
		},
		error : function() {// 请求失败处理函数
			alert('请求失败');
		},
		success : function(data) { // 请求成功后处理函数。
			var success = data.success;
			if (success == true) {
				$("#login_msg").html("登录成功");
				window.location = "main";
			} else {// 后台异常处理
				$("#login_msg").html(data.errMsg);
			}
		}
	});
}

/**
 * 执行reset操作
 */
function doReset() {
	$("#userName").val("");
	$("#password").val("");
	$("#login_msg").html("&nbsp;");
}

/** --------------操作弹出框------------------* */
// 设置弹出框的属性
function setDialog() {
	$('#login').dialog({
		title : '用户登录',
		modal : true, // 模式窗口：窗口背景不可操作
		collapsible : true, // 可折叠，点击窗口右上角折叠图标将内容折叠起来
		resizable : true, // 可拖动边框大小
		closable : false
	// 不提供关闭窗口按钮
	});
}