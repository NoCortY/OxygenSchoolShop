/**
 * 下面文档 都是按照UTF-8的编码处理，提供一些特殊前台验证方法。
 * 
 * @author : 敬文涛
 * @version : 1.00
 * @copyright 2009-2011, Chengdu Tianfu Software Park Co., Ltd. Create Time :
 */

// 中文字两个字节
jQuery.validator.addMethod("byteRangeLength",function(value, element, param) {
	var length = value.length;
	for ( var i = 0; i < value.length; i++) {
		if (value.charCodeAt(i) > 127) {
			length++;
		}
	}
	return this.optional(element) || (length >= param[0] && length <= param[1]);
}, "请确保输入的值在6-12个字节之间(一个中文字算2个字节)");

/* 追加自定义验证方法 */
//身份证号码验证
jQuery.validator.addMethod("isIdCardNo", function(value, element) {
	return this.optional(element) ||  checkidcard(value);
}, "请正确输入您的身份证号码");

// 字符验证
jQuery.validator.addMethod("userName", function(value, element) {
	return this.optional(element) || /^[\u0391-\uFFE5\w]+$/.test(value);
}, "用户名只能包括中文字、英文字母、数字和下划线");

// 手机号码验证
jQuery.validator.addMethod("isCellphone", function(value, element) {
	var length = value.length;
	return this.optional(element) || (length == 11 && /^(((13[0-9]{1})|(15[0-9]{1})|18[6,8,9])+\d{8})$/.test(value));
}, "请正确填写您的手机号码");

// 电话号码验证
jQuery.validator.addMethod("isTelephone", function(value, element) {
	var tel = /^0\d{2,3}\-\d{7,8}$/;
	return this.optional(element) || (tel.test(value));
}, "请正确填写您的电话号码");

// 邮政编码验证
jQuery.validator.addMethod("isZipCode", function(value, element) {
	var tel = /^[0-9]{6}$/;
	return this.optional(element) || (tel.test(value));
}, "请正确填写您的邮政编码");
//验证日期先后
jQuery.validator.addMethod("compareDate" , function(value, element, param) {
    //var startDate = jQuery(param).val() + ":00";补全yyyy-MM-dd HH:mm:ss格式
    //value = value + ":00";
    var startDate = jQuery(param).val();
    var date1 = new Date(Date.parse(startDate));
    var date2 = new Date(Date.parse(value));
    return date1 < date2;
}, "日期不合理！");
//汉字验证  by LiBin
jQuery.validator.addMethod("cnvalue", function(value, element) {
	var tel = /^[|\u4e00-\u9fa5]+$/;
	return  this.optional(element) || (tel.test(value));
}, "请输入中文！");


/**
 * 自定义检查身份证号码函数。
 * @param num 身份证号码
 * @return
 */
function checkidcard(num){  
    var len = num.length, re;  
    if (len == 15)  
        re = new RegExp(/^(\d{6})()?(\d{2})(\d{2})(\d{2})(\d{3})$/);  
    else if (len == 18)  
        re = new RegExp(/^(\d{6})()?(\d{4})(\d{2})(\d{2})(\d{3})(\d)$/);  
    else{  
        //alert("请输入15或18位身份证号,您输入的是 "+len+ "位");   
        return false;  
    }  
    var a = num.match(re);  
    if (a != null){  
        if (len==15){  
            var D = new Date("19"+a[3]+"/"+a[4]+"/"+a[5]);  
            var B = D.getYear()==a[3]&&(D.getMonth()+1)==a[4]&&D.getDate()==a[5];  
        }else{  
            var D = new Date(a[3]+"/"+a[4]+"/"+a[5]);  
            var B = D.getFullYear()==a[3]&&(D.getMonth()+1)==a[4]&&D.getDate()==a[5];  
        }  
        if (!B){  
            //alert("输入的身份证号 "+ a[0] +" 里出生日期不对！");   
            return false;  
        }  
    }  
    return true;  
};
