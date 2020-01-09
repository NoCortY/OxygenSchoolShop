$(function() {
	initializePage();
});

function initializePage() {
	// 加载表格数据
	ajaxTable();
	// 初始化弹出层
	setDialog_edit();
	closeDialog_edit();
	intializeDynamicData();
}

function intializeDynamicData() {
	$.ajax({
		url : "listshopcategorys",
		type : "post",
		dataType : 'json',
		success : function(data) {
			var shopCategoryList = data.rows;
			listShopCategorys(shopCategoryList);
			searchShopInfo();
		}
	});
}

function listShopCategorys(shopCategoryList) {
	if ((shopCategoryList != undefined) && (shopCategoryList.length > 0)) {
		var shopCategoryBuffer = new StringBuffer();
		/**
		 * <option value="categoryId">categoryName</option>
		 */
		for (var i = 0; i < shopCategoryList.length; i++) {
			shopCategoryBuffer.append('<option value="');
			shopCategoryBuffer.append(shopCategoryList[i].shopCategoryId);
			shopCategoryBuffer.append('">');
			shopCategoryBuffer.append(shopCategoryList[i].shopCategoryName);
			shopCategoryBuffer.append('</option>');
		}
		$("#shopManagementFilter_shopCategory").append(
				shopCategoryBuffer.toString());
		$("#shopManagementEdit_shopCategory").append(
				shopCategoryBuffer.toString());
	}
}

function searchShopInfo() {
	var enalbeStatus = $("#enalbeStatusHd").val();
	var shopCategoryId = $("#shopCategoryIdHd").val();
	var searchCondition = $("#searchConditionHd").val();
	if (searchCondition == "byShopName") {
		listShopByName();
	} else if (searchCondition == "byShopId") {
		listShopByShopId();
	} else {
		if (shopCategoryId == "ALL") {
			listShopsByPriority();
		} else {
			listShopsByShopCategory();
		}
	}
}

function listShopByName() {
	var shopName = $("#searchInfoHd").val();
	if (shopName == "") {
		return;
	}
	var urlBuffer = new StringBuffer();
	urlBuffer.append("listshops?shopName=");
	urlBuffer.append(encodeURIComponent(encodeURIComponent(shopName)));
	$('#urlHd').val(urlBuffer.toString());
	var obj = $("#shopManagementTable").datagrid("getPager").pagination(
			"options");
	getDataByPageRows(obj.pageNumber, obj.pageSize);
}

function listShopByShopId() {
	var shopId = $("#searchInfoHd").val();
	if (shopId == "") {
		return;
	}
	var urlBuffer = new StringBuffer();
	urlBuffer.append("searchshopbyid?shopId=");
	urlBuffer.append(shopId);
	$('#urlHd').val(urlBuffer.toString());
	var obj = $("#shopManagementTable").datagrid("getPager").pagination(
			"options");
	getDataByPageRows(obj.pageNumber, obj.pageSize);
}

function listShopsByPriority() {
	var enalbeStatus = $("#enalbeStatusHd").val();
	var urlBuffer = new StringBuffer();
	urlBuffer.append("listshops?enableStatus=");
	urlBuffer.append(enalbeStatus);
	$('#urlHd').val(urlBuffer.toString());
	var obj = $("#shopManagementTable").datagrid("getPager").pagination(
			"options");
	getDataByPageRows(obj.pageNumber, obj.pageSize);
}

function listShopsByShopCategory() {
	var shopCategoryId = $("#shopCategoryIdHd").val();
	var enalbeStatus = $("#enalbeStatusHd").val();
	var urlBuffer = new StringBuffer();
	urlBuffer.append("listshops?shopCategoryId=");
	urlBuffer.append(shopCategoryId);
	urlBuffer.append("&enableStatus=");
	urlBuffer.append(enalbeStatus);
	$('#urlHd').val(urlBuffer.toString());
	var obj = $("#shopManagementTable").datagrid("getPager").pagination(
			"options");
	getDataByPageRows(obj.pageNumber, obj.pageSize);
}

/** --------------table------------------* */
/**
 * 加载表格数据
 */
function ajaxTable() {
	// 加载表格
	$('#shopManagementTable')
			.datagrid(
					{
						toolbar : [ {
							text : '查询条件'
						} ],
						pageNumber : 1,
						loadMsg : '数据加载中,请稍后...',
						pageList : [ 10, 30, 50, 100 ], // 设置每页显示多少条
						onLoadError : function() {
							alert('数据加载失败!');
						},
						queryParams : {// 查询条件
						},
						onClickRow : function(rowIndex, rowData) {
							// 取消选择某行后高亮
							$('#shopManagementTable').datagrid('unselectRow',
									rowIndex);
						},
						onLoadSuccess : function() {
							var value = $('#shopManagementTable').datagrid(
									'getData')['errorMsg'];
							if (value != null) {
								alert("错误消息:" + value);
							}
						}
					}).datagrid('acceptChanges');
	var enableStatusBuffer = new StringBuffer()
	enableStatusBuffer
			.append('<select id="shopManagementFilter_enableStatus" class="easyui-combobox" style="margin :2px; padding :4px;" ');
	enableStatusBuffer
			.append('onchange="changeFilterStatus(this.options[this.options.selectedIndex].value)">');
	enableStatusBuffer
			.append('<option id="shopManagementFilter_ALL" value="">全部</option>');
	enableStatusBuffer
			.append('<option id="shopManagementFilter_YES" value="1">启用</option>');
	enableStatusBuffer
			.append('<option id="shopManagementFilter_APPLY" value="2">待审核</option>');
	enableStatusBuffer
			.append('<option id="shopManagementFilter_NO" value="0">禁用</option></select>');
	var shopCategoryBuffer = new StringBuffer()
	shopCategoryBuffer
			.append('<select id="shopManagementFilter_shopCategory" class="easyui-combobox" style="margin :2px; padding :4px;" ');
	shopCategoryBuffer
			.append('onchange="changeFilterShopCategory(this.options[this.options.selectedIndex].value)">');
	shopCategoryBuffer
			.append('<option id="shopManagementFilter_ALLCATE" value="ALL">全部类别</option></select>');
	var searchConditionBuffer = new StringBuffer()
	searchConditionBuffer
			.append('<select id="shopManagementSearch_searchCondition" class="easyui-combobox" style="margin :2px; padding :4px;" ');
	searchConditionBuffer
			.append('onchange="changeSearchCondition(this.options[this.options.selectedIndex].value)">');
	searchConditionBuffer
			.append('<option id="shopManagementSearch_ALL" value="ALL">按筛选条件查询</option>');
	searchConditionBuffer
			.append('<option id="shopManagementSearch_NAME" value="byShopName">按商铺名称查询</option>');
	searchConditionBuffer
			.append('<option id="shopManagementSearch_SHOPID" value="byShopId">按商铺ID查询</option></select>');
	var searchBoxBuffer = new StringBuffer();
	searchBoxBuffer.append('<input type="text" ');
	searchBoxBuffer
			.append('id="shopManagementSearch_searchBox" onchange="changeSearchInfo()"');
	searchBoxBuffer
			.append(' style="resize: none; width: 200px" onkeydown="searchInfoKeyDown(e)"></input>');
	searchBoxBuffer.append('<input type="button" id="searchBtn" value="搜索"');
	searchBoxBuffer
			.append('style="margin-right: 0.5em;" onclick="searchInfo()"/>');
	$('.datagrid-toolbar').append(enableStatusBuffer.toString());
	$('.datagrid-toolbar').append(shopCategoryBuffer.toString());
	$('.datagrid-toolbar').append(searchConditionBuffer.toString());
	$('.datagrid-toolbar').append(searchBoxBuffer.toString());
	$("#shopManagementSearch_searchBox").hide();
	$("#searchBtn").hide();
	// 获取DataGrid分页组件对象
	var p = $("#shopManagementTable").datagrid("getPager");
	// 设置分页组件参数
	$(p).pagination({
		onSelectPage : function(pageNumber, pageSize) {
			getDataByPageRows(pageNumber, pageSize);
		}
	});
}

function getDataByPageRows(pageNum, rowsLimit) {
	pageNum = pageNum || 1; // 设置默认的页号
	rowsLimit = rowsLimit || 10;// 设置默认的每页记录数
	var urlBuffer = new StringBuffer();
	urlBuffer.append($("#urlHd").val());
	urlBuffer.append("&page=");
	urlBuffer.append(pageNum);
	urlBuffer.append("&rows=");
	urlBuffer.append(rowsLimit);
	$.ajax({
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "post",
		dataType : 'json', // 注意格式是html，不是json
		url : urlBuffer.toString(),
		error : function() { // ajax请求失败
			$.messager.show({
				title : '失败信息',
				msg : '加载内容失败',
				timeout : 0,
				showType : 'fade'
			});
		},
		success : function(jsonObj, textStatus, jqXHR) { // 请求成功，将返回的数据（一页的记录数）绑定到
			// datagrid控件
			$('#shopManagementTable').datagrid('loadData', jsonObj);
		}
	});// ajax
}

function changeFilterStatus(status) {
	$('#enalbeStatusHd').val(status);
	searchShopInfo();
}

function changeFilterShopCategory(shopCategoryId) {
	$('#shopCategoryIdHd').val(shopCategoryId);
	searchShopInfo();
}

function changeSearchCondition(condition) {
	$("#searchConditionHd").val(condition);
	if (condition == "byShopName") {
		$("#enalbeStatusHd").val("");
		$("#shopCategoryIdHd").val("");
		$("#shopManagementFilter_enableStatus").hide();
		$("#shopManagementFilter_shopCategory").hide();
		$("#shopManagementSearch_searchBox").show();
		$("#searchBtn").show();
	} else if (condition == "byShopId") {
		$("#enalbeStatusHd").val("");
		$("#shopCategoryIdHd").val("");
		$("#shopManagementFilter_enableStatus").hide();
		$("#shopManagementFilter_shopCategory").hide();
		$("#shopManagementSearch_searchBox").show();
		$("#searchBtn").show();
	} else {
		$("#enalbeStatusHd").val("ALL");
		$("#shopCategoryIdHd").val("ALL");
		$("#searchInfoHd").val("");
		$("#shopManagementFilter_enableStatus").show();
		$("#shopManagementFilter_shopCategory").show();
		$("#shopManagementSearch_searchBox").hide();
		$("#searchBtn").hide();
	}
	searchShopInfo();
}

function changeSearchInfo() {
	var info = $('#shopManagementSearch_searchBox').val();
	$('#searchInfoHd').val(info);
}

function searchInfoKeyDown(e) {
	if (e.keyCode == 13) {
		searchInfo();
	}
}

function searchInfo() {
	var info = $('#shopManagementSearch_searchBox').val().trim();
	$('#searchInfoHd').val(info);
	if (info == "") {
		return;
	}
	searchShopInfo();
}
/**
 * 设置操作列的信息 参数说明 value 这个可以不管，但是要使用后面 row 和index 这个参数是必须的 row 当前行的数据 index
 * 当前行的索引 从0 开始
 */
function optFormater(value, row, index) {
	var shopName = row.shopName;
	var shopCategoryId = row.shopCategoryId
	var shopId = row.shopId;
	var priority = row.priority;
	var enableStatus = row.enableStatus;
	var advice = "无";
	if (row.advice != undefined) {
		advice = row.advice;
	}
	if(row.advice==undefined||row.advice==""){
		advice="无";
	}
	var params = shopId + "," + priority + ",'" + enableStatus + "','" + advice
			+ "','" + shopName + "'," + shopCategoryId;
	var edit = '<a href="javascript:openDialog_edit(' + params + ')">编辑</a>';
	return edit;
};

/** --------------编辑操作弹出框------------------* */
function changeEnableStatus4Edit(status) {
	if (status == "0") {
		$("#shopManagementEdit_supportProductCategory").val("0");
	} else {
		$("#shopManagementEdit_supportProductCategory").val("1");
	}
}
// 设置弹出框的属性
function setDialog_edit() {
	$('#shopManagementEdit').dialog({
		title : '商铺编辑',
		modal : true, // 模式窗口：窗口背景不可操作
		collapsible : true, // 可折叠，点击窗口右上角折叠图标将内容折叠起来
		resizable : true
	});
}
// 打开对话框
function openDialog_edit(shopId, priority, enableStatus, advice, shopName,
		shopCategoryId) {
	shopManagementEditReset(shopId, priority, enableStatus, advice, shopName,
			shopCategoryId);
	$('#shopManagementEdit').dialog('open');
}
// 关闭对话框
function closeDialog_edit() {
	$('#shopManagementEdit').dialog('close');
}
// 根据用户id查询用户的信息
function shopManagementEditReset(shopId, priority, enableStatus, advice,
		shopName, shopCategoryId) {
	$("#shopManagementEdit_message").html("");
	$("#shopManagementEdit_shopName").val(shopName);
	$("#shopManagementEdit_shopId").val(shopId);
	$("#shopManagementEdit_priority").val(priority);
	$("#shopManagementEdit_enableStatus").val(enableStatus);
	$("#shopManagementEdit_advice").val(advice);
	$("#shopManagementEdit_shopCategory").val(shopCategoryId);
}
// 执行用户编辑操作
function shopManagementEdit() {
	var validateResult = true;
	// easyui 表单验证
	$('#table_shopManagementEdit input').each(function() {
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
	var shop = {};
	shop.shopId = encodeURIComponent($("#shopManagementEdit_shopId").val());
	shop.shopName = encodeURIComponent($("#shopManagementEdit_shopName").val());
	shop.shopCategoryId = encodeURIComponent($(
			"#shopManagementEdit_shopCategory").val());
	shop.priority = encodeURIComponent($("#shopManagementEdit_priority").val());
	shop.enableStatus = encodeURIComponent($("#shopManagementEdit_enableStatus")
			.val());
	shop.advice = encodeURIComponent($("#shopManagementEdit_advice").val());
	if(shop.advice==""||shop.advice.length==0) 
		shop.advice="无";
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		dataType : "json",
		data : {
			shopStr : JSON.stringify(shop)
		},
		url : 'modifyshop',// 请求的action路径
		error : function() {// 请求失败处理函数
			alert('请求失败');
		},
		success : function() {
			var messgage = "修改成功!";
			searchShopInfo();
			$("#shopManagementEdit_message").html(messgage);
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
			searchShopInfo();
		}
	});
}
