$(function() {
	initializePage();
	listShopCategoryManagementInfo();
});
function listShopCategoryManagementInfo() {
	$.ajax({
		url : "listshopcategorys",
		type : "post",
		dataType : 'json',
		success : function(data) {
			$('#shopCategoryManagementTable').datagrid('loadData', data);
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

	$.ajax({
		url : "list1stlevelshopcategorys",
		type : "post",
		dataType : 'json',
		success : function(data) {
			var shopCategoryList = data.data;
			if ((shopCategoryList != undefined)
					&& (shopCategoryList.length > 0)) {
				var shopCategoryBuffer = new StringBuffer();
				/**
				 * <option value="shopCategoryId">shopCategoryName</option>
				 */
				for (var i = 0; i < shopCategoryList.length; i++) {
					shopCategoryBuffer.append('<option value="');
					shopCategoryBuffer
							.append(shopCategoryList[i].shopCategoryId);
					shopCategoryBuffer.append('">');
					shopCategoryBuffer
							.append(shopCategoryList[i].shopCategoryName);
					shopCategoryBuffer.append('</option>');
				}
				shopCategoryBuffer.append('<option value=""></option>');
				$("#shopCategoryManagementAdd_parentId").append(
						shopCategoryBuffer.toString());
				$("#shopCategoryManagementEdit_parentId").append(
						shopCategoryBuffer.toString());
				$("#shopCategoryManagementAdd_parentId").val("");
			}
		}
	});
}
/** --------------table------------------* */
/**
 * 加载表格数据
 */
function ajaxTable() {
	// 加载表格
	$('#shopCategoryManagementTable').datagrid(
			{
				toolbar : [ {// 正上方工具栏
					text : '添加新类别',
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
					$('#shopCategoryManagementTable').datagrid('unselectRow',
							rowIndex);
				},
				onLoadSuccess : function() {
					var value = $('#shopCategoryManagementTable').datagrid(
							'getData')['errorMsg'];
					if (value != null) {
						alert("错误消息:" + value);
					}
				}
			}).datagrid('acceptChanges');
}
function imgFormater(value, row, index) {
	var shopCategoryImg = row.shopCategoryImg;
	return '<img src="' + shopCategoryImg + '" width="100px" height="60px">';
}
/**
 * 设置操作列的信息 参数说明 value 这个可以不管，但是要使用后面 row 和index 这个参数是必须的 row 当前行的数据 index
 * 当前行的索引 从0 开始
 */
function optFormater(value, row, index) {
	var shopCategoryId = row.shopCategoryId;
	var shopCategoryName = row.shopCategoryName;
	var shopCategoryDesc = row.shopCategoryDesc;
	var parentId = row.parentId;
	var priority = row.priority;
	var params = shopCategoryId + ",'" + shopCategoryName + "','"
			+ shopCategoryDesc + "'," + parentId + "," + priority;
	var edit = '<a href="javascript:openDialog_edit(' + params + ')">编辑</a> | ';
	var del = '<a href="#" onclick="doDel(' + shopCategoryId + ')">删除</a>';
	return edit + del;
};

/** --------------添加操作弹出框------------------* */
// 设置弹出框的属性
function setDialog_add() {
	$('#shopCategoryManagementAdd').dialog({
		title : '类别添加',
		modal : true, // 模式窗口：窗口背景不可操作
		collapsible : true, // 可折叠，点击窗口右上角折叠图标将内容折叠起来
		resizable : true, // 可拖动边框大小
		onClose : function() { // 继承自panel的关闭事件
			shopCategoryManagementAddReset();
		}
	});
}
// 打开对话框
function openDialog_add() {
	$('#shopCategoryManagementAdd').dialog('open');
}
// 关闭对话框
function closeDialog_add() {
	$('#shopCategoryManagementAdd').dialog('close');
}
// 执行用户添加操作
function shopCategoryManagementAdd() {
	var validateResult = true;
	// easyui 表单验证
	$('#table_shopCategoryManagementAdd input').each(function() {
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
	var pic = $("#shopCategoryManagementAdd_shopCategoryImg").val();
	if (pic.length <= 0) {
		$("#shopCategoryManagementAdd_message").html("请上传图片！");
		return;
	}
	var shopCategory = {};
	shopCategory.shopCategoryName = encodeURIComponent($(
			"#shopCategoryManagementAdd_shopCategoryName").val());
	shopCategory.shopCategoryDesc = encodeURIComponent($(
			"#shopCategoryManagementAdd_shopCategoryDesc").val());
	shopCategory.parentId = $("#shopCategoryManagementAdd_parentId").val();
	shopCategory.priority = $("#shopCategoryManagementAdd_priority").val();
	var options = {
		type : 'post',
		data : {
			shopCategoryStr : JSON.stringify(shopCategory)
		},
		url : 'addshopcategory',// 请求的action路径
		error : function(data) {// 请求失败处理函数
			alert(data);
		},
		success : function() {
			var messgage = "添加成功!";
			listShopCategoryManagementInfo();
			$("#shopCategoryManagementAdd_message").html(messgage);
		}
	};
	$("#shopCategoryFormAdd").ajaxSubmit(options);
}
// 用户添加页面数据重置操作
function shopCategoryManagementAddReset() {
	$("#shopCategoryManagementAdd_message").html("");
	$("#shopCategoryManagementAdd_shopCategoryName").val("");
	$("#shopCategoryManagementAdd_shopCategoryDesc").val("");
	$("#shopCategoryManagementAdd_parentId").val("");
	$("#shopCategoryManagementAdd_priority").val("");
	var file = $("#shopCategoryManagementAdd_shopCategoryImg");
	file.after(file.clone().val(""));
	file.remove();
}

/** --------------编辑操作弹出框------------------* */
// 设置弹出框的属性
function setDialog_edit() {
	$('#shopCategoryManagementEdit').dialog({
		title : '类别编辑',
		modal : true, // 模式窗口：窗口背景不可操作
		collapsible : true, // 可折叠，点击窗口右上角折叠图标将内容折叠起来
		resizable : true
	// 可拖动边框大小
	});
}
// 打开对话框
function openDialog_edit(shopCategoryId, shopCategoryName, shopCategoryDesc,
		parentId, priority) {
	shopCategoryManagementEditReset(shopCategoryId, shopCategoryName,
			shopCategoryDesc, parentId, priority);
	$('#shopCategoryManagementEdit').dialog('open');
}
// 关闭对话框
function closeDialog_edit() {
	$('#shopCategoryManagementEdit').dialog('close');
}
// 根据用户id查询用户的信息
function shopCategoryManagementEditReset(shopCategoryId, shopCategoryName,
		shopCategoryDesc, parentId, priority) {
	$("#shopCategoryManagementEdit_message").html("");
	$("#shopCategoryManagementEdit_shopCategoryId").val(shopCategoryId);
	$("#shopCategoryManagementEdit_shopCategoryName").val(shopCategoryName);
	$("#shopCategoryManagementEdit_shopCategoryDesc").val(shopCategoryDesc);
	$("#shopCategoryManagementEdit_parentId").val(parentId);
	$("#shopCategoryManagementEdit_priority").val(priority);
	var file = $("#shopCategoryManagementEdit_shopCategoryImg");
	file.after(file.clone().val(""));
	file.remove();
}

// 执行用户编辑操作
function shopCategoryManagementEdit() {
	var validateResult = true;
	// easyui 表单验证
	$('#table_shopCategoryManagementEdit input').each(function() {
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
	var shopCategory = {};
	shopCategory.shopCategoryId = $(
			"#shopCategoryManagementEdit_shopCategoryId").val();
	shopCategory.shopCategoryName = encodeURIComponent($(
			"#shopCategoryManagementEdit_shopCategoryName").val());
	shopCategory.shopCategoryDesc = encodeURIComponent($(
			"#shopCategoryManagementEdit_shopCategoryDesc").val());
	shopCategory.parentId = $("#shopCategoryManagementEdit_parentId").val();
	shopCategory.priority = $("#shopCategoryManagementEdit_priority").val();
	var options = {
		type : 'post',
		data : {
			shopCategoryStr : JSON.stringify(shopCategory)
		},
		url : 'modifyshopcategory',// 请求的action路径
		error : function() {// 请求失败处理函数
			alert('请求失败');
		},
		success : function() {
			var messgage = "修改成功!";
			listShopCategoryManagementInfo();
			$("#shopCategoryManagementEdit_message").html(messgage);
		}
	};
	$("#shopCategoryFormEdit").ajaxSubmit(options);
}

/** --------------执行删除操作------------------* */
function doDel(shopCategoryId) {
	$.messager.confirm('删除提示', '你确定永久删除该数据吗?', function(r) {
		if (r) {
			var url = 'removeshopcategory?shopCategoryId=' + shopCategoryId;
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
			listShopCategoryManagementInfo();
		}
	});
}

/**
 * 批量操作
 * 
 * @return
 */
function batch(flag) {
	if ($('#shopCategoryManagementTable').datagrid('getSelected')) {
		// 首先如果用户选择了数据，则获取选择的数据集合
		var shopCategoryNames = [];
		var selectedRow = $('#shopCategoryManagementTable').datagrid(
				'getSelections');
		var jsonList = [];
		for (var i = 0; i < selectedRow.length; i++) {
			jsonList.push(selectedRow[i].shopCategoryId);
			shopCategoryNames.push(selectedRow[i].shopCategoryName);
		}

		// 删除操作
		$.messager.confirm('删除提示', '你确定永久删除下列消息吗?<br/>'
				+ shopCategoryNames.join(','), function(r) {
			if (r) {
				var url = 'removeshopcategories?shopCategoryIdListStr='
						+ JSON.stringify(jsonList);
				changeStatus(url);
			}
		});
	}
}
