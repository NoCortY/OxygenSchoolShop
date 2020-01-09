$(function(){
	$("#shopInfo").on('click',function(){
		shopId = getQueryString('shopId');
		window.location.href = '/schoolshop/shop/shopedit?shopId='+shopId;
	});
});