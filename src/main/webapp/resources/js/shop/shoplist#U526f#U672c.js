/**
 * 
 */
function getlist(e) {
	$.ajax({
		url : "/myo2o/shop/list",
		type : "get",
		dataType : "json",
		success : function(wholePageData) {
			if (errMsgHandler(wholePageData)) {
				listCustomInfo(wholePageData);
				listShops(e, wholePageData);
			}
			registerStaticPageEvent(e);
		}
	});
}

function listCustomInfo(wholePageData) {
	var employee = wholePageData.user;
	var hasAccountBind = wholePageData.hasAccountBind;
	if (!hasAccountBind) {
		$('#bindAccount').show();
		$('#gap').show();
	} else {
		$('#bindAccount').hide();
		$('#gap').hide();
	}
	if (employee != undefined) {
		$("#username")
				.append(
						$("<div>欢迎您,<font color='#b50606'>" + employee.name
								+ "</font>"));
		var url = "/myo2o/shop/changepsw";
		$("#changePassword").attr("href", url);
	}
}
function listShops(e, wholePageData) {
	var shopList = wholePageData.shopList;
	if (shopList == undefined || shopList.length <= 0) {
		var subBuffer = new StringBuffer();
		subBuffer.append("<div class='increase'><a href=/myo2o/shop/shopedit");
		subBuffer.append(">✚<span>(添加商铺)</span></a></div>");
		$("#shops").append(subBuffer.toString());
	}
	if (shopList != undefined) {
		var listLength = shopList.length;
		var subShopBuffer = new StringBuffer();
		for (var i = 0; i < listLength; i++) {
			var shop = shopList[i];
			if (shop.enableStatus == 0) {
				subShopBuffer
						.append("<div><a href='/myo2o/shop/shopedit?shopId=");
				subShopBuffer.append(shop.shopId);
				subShopBuffer.append("' style='color: black;' ><del>");
				subShopBuffer.append(shop.shopName);
				subShopBuffer.append("</del><span><b>*");
				subShopBuffer.append("审核中");
				subShopBuffer.append("</b></span>");
				subShopBuffer.append('</a></div>');
			} else if (shop.enableStatus == -1) {
				subShopBuffer
						.append("<div><a href='/myo2o/shop/shopmanage?shopId=");
				subShopBuffer.append(shop.shopId);
				subShopBuffer.append("' style='color: black;' ><del>");
				subShopBuffer.append(shop.shopName);
				subShopBuffer.append("</del><span><b>*");
				subShopBuffer.append("商铺非法：" + shop.advice);
				subShopBuffer.append("</b></span>");
				subShopBuffer.append('</a></div>');
			} else {
				subShopBuffer
						.append("<div><a href='/myo2o/shop/shopmanage?shopId=");
				// subShopBuffer.append("<div><a href='myShop.html?shopId=");
				subShopBuffer.append(shop.shopId);
				subShopBuffer.append("'>");
				subShopBuffer.append(shop.shopName);
				subShopBuffer.append("</a></div>");
			}

		}
		$("#shops").append(subShopBuffer.toString());

	}
}
function registerStaticPageEvent(e) {
	$("#dologout").click(function() {
		$.ajax({
			url : "/myo2o/shop/logout",
			type : "post",
			contentType : false,
			processData : false,
			cache : false,
			success : function(data) {
				if (data.success) {
					window.location.href = '/myo2o/shop/ownerlogin';
				}
			},
			error : function(data, error) {
				alert(error);
			}
		});
	});
}
