$(function() {
	initializePage();
	listHeadTitleManagementInfo();
});
function listHeadTitleManagementInfo() {
	var enableStatus = $('#enalbeStatusHd').val();
	$.ajax({
		url : "listheadlines",
		type : "post",
		dataType : 'json',
		data : {
			enableStatus : enableStatus
		},
		success : function(data) {
			$('#headTitleManagementTable').datagrid('loadData', data);
		}
	});
}
function initializePage() {
	// 加载表格数据
	ajaxTable();

	// 初始化弹出层
	setDialog_add();
	closeDialog_add();
	setDialog_edit();
	closeDialog_edit();
}
/** --------------table------------------* */
/**
 * 加载表格数据
 */
function ajaxTable() {
	// 加载表格
	$('#headTitleManagementTable').datagrid(
			{
				toolbar : [ {// 正上方工具栏
					text : '添加新头条',
					iconCls : 'icon-add',
					handler : function() {
						// 点击工具栏运行的js方法
						openDialog_add();
					}
				}, '-', {
					text : '批量删除',
					iconCls : 'icon-cancel',
					handler : function() {
						batch('delete');
					}
				} ],

				loadMsg : '数据加载中,请稍后...',

				onLoadError : function() {
					alert('数据加载失败!');
				},
				queryParams : {// 查询条件
				},
				onClickRow : function(rowIndex, rowData) {
					// 取消选择某行后高亮
					$('#headTitleManagementTable').datagrid('unselectRow',
							rowIndex);
				},
				onLoadSuccess : function() {
					var value = $('#headTitleManagementTable').datagrid(
							'getData')['errorMsg'];
					if (value != null) {
						alert("错误消息:" + value);
					}
				}
			}).datagrid('acceptChanges');
	var enableStatusBuffer = new StringBuffer()
	enableStatusBuffer
			.append('<select id="headTitleManagementFilter_enableStatus" class="easyui-combobox" style="margin :2px; padding :4px;" ');
	enableStatusBuffer
			.append('onchange="changeFilterStatus(this.options[this.options.selectedIndex].value)">');
	enableStatusBuffer
			.append('<option id="headTitleManagementFilter_ALL" value="">全部</option>');
	enableStatusBuffer
			.append('<option id="headTitleManagementFilter_YES" value="1">启用</option>');
	enableStatusBuffer
			.append('<option id="headTitleManagementFilter_NO" value="0">禁用</option></select>');
	$('.datagrid-toolbar').append(enableStatusBuffer.toString());
}

function changeFilterStatus(status) {
	$('#enalbeStatusHd').val(status);
	listHeadTitleManagementInfo();
}

function imgFormater(value, row, index) {
	var lineImg = row.lineImg;
	return '<img src="' + lineImg + '" width="100px" height="60px">';
}
/**
 * 设置操作列的信息 参数说明 value 这个可以不管，但是要使用后面 row 和index 这个参数是必须的 row 当前行的数据 index
 * 当前行的索引 从0 开始
 */
function optFormater(value, row, index) {
	var lineId = row.lineId;
	var lineName = row.lineName;
	var lineLink = row.lineLink;
	var priority = row.priority;
	var enableStatus = row.enableStatus;
	var params = lineId + ",'" + lineName + "','" + lineLink + "',"
			+ priority + ",'" + enableStatus + "'";
	var edit = '<a href="javascript:openDialog_edit(' + params + ')">编辑</a> | ';
	var del = '<a href="#" onclick="doDel(' + lineId + ')">删除</a>';
	return edit + del;
};

/** --------------添加操作弹出框------------------* */
// 设置弹出框的属性
function setDialog_add() {
	$('#headTitleManagementAdd').dialog({
		title : '头条添加',
		modal : true, // 模式窗口：窗口背景不可操作
		collapsible : true, // 可折叠，点击窗口右上角折叠图标将内容折叠起来
		resizable : true, // 可拖动边框大小
		onClose : function() { // 继承自panel的关闭事件
			headTitleManagementAddReset();
		}
	});
}
// 打开对话框
function openDialog_add() {
	$('#headTitleManagementAdd').dialog('open');
}
// 关闭对话框
function closeDialog_add() {
	$('#headTitleManagementAdd').dialog('close');
}
// 执行用户添加操作
function headTitleManagementAdd() {
	var validateResult = true;
	// easyui 表单验证
	$('#table_headTitleManagementAdd input').each(function() {
		if ($(this).attr('required') || $(this).attr('validType')) {
			if (!$(this).validatebox('isValid')) {
				// 如果验证不通过，则返回false
				validateResult = false;
				return;
			}
		}
	});
	if (validateResult == false) {
		return;
	}
	var pic = $("#headTitleManagementAdd_lineImg").val();
	if (pic.length <= 0) {
		$("#headTitleManagementAdd_message").html("请上传图片！");
		return;
	}
	var headLine = {};
	headLine.lineName = encodeURIComponent($(
			"#headTitleManagementAdd_lineName").val());
	headLine.lineLink = encodeURIComponent($(
			"#headTitleManagementAdd_lineLink").val());
	headLine.priority = encodeURIComponent($("#headTitleManagementAdd_priority")
			.val());
	headLine.enableStatus = encodeURIComponent($(
			"#headTitleManagementAdd_enableStatus").val());
	var options = {
		url : "addheadline",
		type : "post",
		data : {
			headLineStr : JSON.stringify(headLine),
			thumbnail : pic
		},
		error : function(data) {// 请求失败处理函数			
			alert(data);
		},
		success : function() {
			var messgage = "添加成功!";
			listHeadTitleManagementInfo();

			$("#headTitleManagementAdd_message").html(messgage);
		}
	};
	$("#headTitleFormAdd").ajaxSubmit(options);
}
// 用户添加页面数据重置操作
function headTitleManagementAddReset() {
	$("#headTitleManagementAdd_message").html("");
	$("#headTitleManagementAdd_lineName").val("");
	$("#headTitleManagementAdd_lineLink").val("");
	$("#headTitleManagementAdd_priority").val("");
	$("#headTitleManagementAdd_enableStatus").val("0");
	var file = $("#headTitleManagementAdd_lineImg");
	file.after(file.clone().val(""));
	file.remove();
}

/** --------------编辑操作弹出框------------------* */
// 设置弹出框的属性
function setDialog_edit() {
	$('#headTitleManagementEdit').dialog({
		title : '头条编辑',
		modal : true, // 模式窗口：窗口背景不可操作
		collapsible : true, // 可折叠，点击窗口右上角折叠图标将内容折叠起来
		resizable : true
	// 可拖动边框大小
	});
}
// 打开对话框
function openDialog_edit(lineId, lineName, lineLink, priority, enableStatus) {
	headTitleManagementEditReset(lineId, lineName, lineLink, priority,
			enableStatus);
	$('#headTitleManagementEdit').dialog('open');
}
// 关闭对话框
function closeDialog_edit() {
	$('#headTitleManagementEdit').dialog('close');
}
// 根据用户id查询用户的信息
function headTitleManagementEditReset(lineId, lineName, lineLink, priority,
		enableStatus) {
	$("#headTitleManagementEdit_message").html("");
	$("#headTitleManagementEdit_lineId").val(lineId);
	$("#headTitleManagementEdit_lineName").val(lineName);
	$("#headTitleManagementEdit_lineLink").val(lineLink);
	$("#headTitleManagementEdit_priority").val(priority);
	$("#headTitleManagementEdit_enableStatus").val(enableStatus);
	var file = $("#headTitleManagementEdit_lineImg");
	file.after(file.clone().val(""));
	file.remove();
}

// 执行用户编辑操作
function headTitleManagementEdit() {
	var validateResult = true;
	// easyui 表单验证
	$('#table_headTitleManagementEdit input').each(function() {
		if ($(this).attr('required') || $(this).attr('validType')) {
			if (!$(this).validatebox('isValid')) {
				// 如果验证不通过，则返回false
				validateResult = false;
				return;
			}
		}
	});
	if (validateResult == false) {
		return;
	}
	var headLine = {};
	headLine.lineId = encodeURIComponent($("#headTitleManagementEdit_lineId")
			.val());
	headLine.lineName = encodeURIComponent($(
			"#headTitleManagementEdit_lineName").val());
	headLine.lineLink = encodeURIComponent($(
			"#headTitleManagementEdit_lineLink").val());
	headLine.priority = encodeURIComponent($(
			"#headTitleManagementEdit_priority").val());
	headLine.enableStatus = encodeURIComponent($(
			"#headTitleManagementEdit_enableStatus").val());
	var options = {
		type : 'post',
		data : {
			headLineStr : JSON.stringify(headLine)
		},
		url : 'modifyheadline',// 请求的action路径
		error : function() {// 请求失败处理函数
			alert('请求失败');
		},
		success : function() {
			var messgage = "修改成功!";
			listHeadTitleManagementInfo();
			$("#headTitleManagementEdit_message").html(messgage);
		}
	};
	$("#headTitleFormEdit").ajaxSubmit(options);
}

/** --------------执行删除操作------------------* */
function doDel(headLineId) {
	$.messager.confirm('删除提示', '你确定永久删除该数据吗?', function(r) {
		if (r) {
			var url = 'removeheadline?headLineId=' + headLineId;
			changeStatus(url);
		}
	});
}

/**
 * 修改状态的Ajax
 * 
 * @param url
 * @return
 */
function changeStatus(url) {
	$.ajax({
		async : false,
		cache : false,
		type : 'post',
		dataType : "json",
		url : url,// 请求的action路径
		error : function() {// 请求失败处理函数
			alert('请求失败');
		},
		success : function() {
			alert("操作成功");
			listHeadTitleManagementInfo();
		}
	});
}

/**
 * 批量操作
 * 
 * @return
 */
function batch(flag) {
	if ($('#headTitleManagementTable').datagrid('getSelected')) {
		// 首先如果用户选择了数据，则获取选择的数据集合
		var lineNames = [];
		var selectedRow = $('#headTitleManagementTable').datagrid(
				'getSelections');
		var jsonList = [];
		for (var i = 0; i < selectedRow.length; i++) {
			jsonList.push(selectedRow[i].lineId);
			lineNames.push(selectedRow[i].lineName);
		}

		// 删除操作
		$.messager.confirm('删除提示', '你确定永久删除下列消息吗?<br/>' + lineNames.join(','),
				function(r) {
					if (r) {
						var url = 'removeheadlines?headLineIdListStr='
								+ JSON.stringify(jsonList);
						changeStatus(url);
					}
				});
	}
}
