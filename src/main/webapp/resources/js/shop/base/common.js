/**
 * 
 */

function adjustHeight(htmlElement) {
	var bodywidth = parseInt(htmlElement.offsetWidth);
	if (bodywidth > 480) {
		htmlElement.style.width = "480px";
	}
	if (htmlElement.children.length != 0) {
		var hc = htmlElement.children;
		for (var i = 0; i < hc.length; i++) {
			if (hc[i].id == "blackBackground") {
				var cell = hc[i].children[0];
				var width = parseInt(htmlElement.offsetWidth);
				cell.children[0].style.width = 0.9 * width + "px";
			}
			if (hc[i].id == "picture") {

			}
		}
	}
}
adjustHeight(document.body);
function errMsgHandler(data) {

	if (data == undefined || data == null)
		return true;
	if (data.success == false) {
		processStatus = data.errorCode;
		if (processStatus == -1001) {
			alert(data.errorMsg);

		} else if (processStatus == 1005) {
			alert("非法操作");
			window.location = "login.html";

		} else if (processStatus == 1001) {
			alert("商铺数量已经到上限，不能继续添加！(如有需要可以聯繫管理員增加上限)");
			return false;
		} else if (processStatus == 1002) {
			alert("商品数量已经到上限，不能继续添加！(如有需要可以聯繫管理員增加上限)");
			return false;
		} else if (processStatus == 1003) {
			alert("商品图片数量已经到上限，不能继续添加！(如有需要可以聯繫管理員增加上限)");
			return false;
		} else if (processStatus == 1009 || processStatus == 1010) {
			var err = "请确保您的信息填写正确";
			alert(err);
		} else if (processStatus == 1011) {
			var err = "营业执照已被登记或确保信息填写正确";
			alert(err);
		} else if (processStatus == 1012) {
			alert("商品名称重复啦！");
		} else if (status == 1013) {
			alert("熱門商品数量已经到上限，不能继续添加！(如有需要可以聯繫管理員增加上限)");
		} else if (processStatus == 1014) {
			alert("请确认新加类别没有重复");
		} else if (processStatus == 1200) {
			alert("出错了！");
		}
		return false;
	} else
		return true;
}

/**
 * 身份证号码验证
 */
function isIdCardNo(num) {

	var factorArr = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4,
			2, 1);
	var parityBit = new Array("1", "0", "X", "9", "8", "7", "6", "5", "4", "3",
			"2");
	var varArray = new Array();
	var lngProduct = 0;
	var intCheckDigit;
	var intStrLen = num.length;
	var idNumber = num;
	// initialize
	if ((intStrLen != 15) && (intStrLen != 18)) {
		return false;
	}
	// check and set value
	for (var i = 0; i < intStrLen; i++) {
		varArray[i] = idNumber.charAt(i);
		if ((varArray[i] < '0' || varArray[i] > '9') && (i != 17)) {
			return false;
		} else if (i < 17) {
			varArray[i] = varArray[i] * factorArr[i];
		}
	}

	if (intStrLen == 18) {
		// check date
		var date8 = idNumber.substring(6, 14);
		if (isDate8(date8) == false) {
			return false;
		}
		// calculate the sum of the products
		for (i = 0; i < 17; i++) {
			lngProduct = lngProduct + varArray[i];
		}
		// calculate the check digit
		intCheckDigit = parityBit[lngProduct % 11];
		// check last digit
		if (varArray[17] != intCheckDigit) {
			return false;
		}
	} else { // length is 15
		// check date
		var date6 = idNumber.substring(6, 12);
		if (isDate6(date6) == false) {
			return false;
		}
	}
	return true;
}
function isDate6(sDate) {
	if (!/^[0-9]{6}$/.test(sDate)) {
		return false;
	}
	var year, month, day;
	year = sDate.substring(0, 4);
	month = sDate.substring(4, 6);
	if (year < 1700 || year > 2500)
		return false
	if (month < 1 || month > 12)
		return false
	return true;
}

function isDate8(sDate) {
	if (!/^[0-9]{8}$/.test(sDate)) {
		return false;
	}
	var year, month, day;
	year = sDate.substring(0, 4);
	month = sDate.substring(4, 6);
	day = sDate.substring(6, 8);
	var iaMonthDays = [ 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 ]
	if (year < 1700 || year > 2500)
		return false
	if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0))
		iaMonthDays[1] = 29;
	if (month < 1 || month > 12)
		return false
	if (day < 1 || day > iaMonthDays[month - 1])
		return false
	return true;
}
// 兼容火狐、IE8
// 显示遮罩层
function showMask() {
	$("#mask").css("height", $(document).height());
	$("#mask").css("width", $(document).width());
	$("#mask").css("text-align", "center");
	$("#mask").append("<img src='images/control/loading.gif'>");
	$("#mask").show();
}
// 隐藏遮罩层
function hideMask() {

	$("#mask").hide();
}