/**
 * 
 */
function StringBuffer() {
	this._strings_ = new Array();
};
StringBuffer.prototype.append = function(str) {
	this._strings_.push(str);
};
StringBuffer.prototype.toString = function() {
	return this._strings_.join("");
};

String.prototype.trim = function() {
	return this.replace(/(^\s*)|(\s*$)/g, "");
}
String.prototype.ltrim = function() {
	return this.replace(/(^\s*)/g, "");
}
String.prototype.rtrim = function() {
	return this.replace(/(\s*$)/g, "");
}

/**
 * return url key-values.
 */
function getRequestParamsFromURL() {
	var url = location.href.replace(/#$/g, '');
	var paramMap = url.substring(url.indexOf("?") + 1, url.length).split("&");
	var paramObj = {};
	for (var i = 0; j = paramMap[i]; i++) {
		paramObj[j.substring(0, j.indexOf("="))] = j.substring(
				j.indexOf("=") + 1, j.length);
	}
	return paramObj;
}
function getRootPath() {

	var curWwwPath = location.href;

	var pathName = location.pathname;
	var pos = curWwwPath.indexOf(pathName);

	var localhostPaht = curWwwPath.substring(0, pos);
	var projectName = pathName
			.substring(0, pathName.substr(1).indexOf('/') + 1);
	return (localhostPaht + projectName + "/");
}
function getImageRootPath() {
	return getRootPath() + "/images/";

}
function validateImage(file, errMsg) {

	var ext = '.jpg.jpeg.gif.bmp.png.';
	if (file == "") {
		errMsg = '请选择图片';
		return false;
	}
	var filename = file.name;
	var f = filename.substr(filename.lastIndexOf('.') + 1).toLowerCase();
	if (ext.indexOf('.' + f + '.') == -1) {
		errMsg = '图片格式不正确';
		return false;
	}
	if (f.size > 5120000) {
		errMsg = '图片大小请不要超过5M';
		return false;
	}
	return true;
}
function validateImages(files, errMsg) {
	if (files == "") {
		errMsg = '请选择图片';
		return false;
	}
	for (var i = 0; i < files.length; i++) {
		var f = files[i];
		var result = validateImage(f, errMsg);
		if (result == false)
			return false;

	}
	return true;
}
function setValue(name, val) {
	if (val != "") {

		var htmlType = $("[name='" + name + "']").attr("type");

		if (htmlType == "text" || htmlType == "textarea"
				|| htmlType == "select" || htmlType == "hidden"
				|| htmlType == "button") {

			$("[name='" + name + "']").val(val);

		} else if (htmlType == "radio") {

			$("input[type=radio][name='" + name + "'][value='" + val + "']")
					.attr("checked", true);

		} else if (htmlType == "checkbox") {

			var vals = val.split(",");
			if (vals.length == 1) {
				$("input[type=checkbox][name='" + name + "']").attr("value",
						vals);
				$("input[type=checkbox][name='" + name + "'][value='Y']").attr(
						"checked", true);
			} else
				for (var i = 0; i < vals.length; i++) {
					$(
							"input[type=checkbox][name='" + name + "'][value='"
									+ vals[i] + "']").attr("checked", true);
				}

		} else
			$("[name='" + name + "']").val(val);

	}

};
function formLoad(formId, jsonData) {
	// $("#"+formId).each(function(){
	// var value = jsonData[this.name];
	// setValue(this.name,value);
	// });
	for ( var name in jsonData) {
		setValue(name, jsonData[name]);
	}

}
function hasNonDigit(str) {
	var pattern = /\D/gi;
	if (pattern.test(str)) {
		return true;
	} else {
		return false;
	}
}