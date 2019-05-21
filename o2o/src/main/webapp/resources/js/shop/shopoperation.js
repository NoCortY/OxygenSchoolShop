/**
 *js的作用是1.将后台数据传送到前台填充表单
 *			2.将前台数据返回后台创建店铺等操作
 */
$(function(){
	var shopId = getQueryString("shopId");
	var isEdit = shopId?true:false;
	var initUrl = '/o2o/shopadmin/getshopinitinfo';
	var registerShopUrl = '/o2o/shopadmin/registershop';
	var shopInfoUrl='/o2o/shopadmin/getshopbyid?shopId='+shopId.replace("=","");//获取到的字符串是 =xxx 去掉等于号;
	var editShopUrl = '/o2o/shopadmin/modifyshop';
	if(!isEdit){
		getShopInitInfo();
	}else{
		getShopInfo(shopId);
	}
	//alert(initUrl);
	//getShopInitInfo();
	function getShopInfo(shopId){
		$.getJSON(shopInfoUrl,function(data){
			if(data.success){
				var shop = data.shop;
				$('#shop-name').val(shop.shopName);
				$('#shop-addr').val(shop.shopAddr);
				$('#shop-phone').val(shop.phone);
				$('#shop-desc').val(shop.shopDesc);
				var shopCategory = '<option data-id="'
					+shop.shopCategory.shopCategoryId+'"+selected>'
					+shop.shopCategory.shopCategoryName+'</option>';
				var tempAreaHtml = '';
				data.areaList.map(function(item,index){
					tempAreaHtml += '<option data-id="'+item.areaId+'">'
						+item.areaName+'</option>';
				});
				$('#shop-category').html(shopCategory);
				$('#shop-category').attr('disabled','disabled');
				$('#area-category').html(tempAreaHtml);
				$("#area option[data-id='"+shop.area.areaId+"']").attr("selected","selected");
				
			}
		});
	}
	
	function getShopInitInfo(){
		$.getJSON(initUrl,function(data){
			if(data.success){
				var tempHtml='';
				var tempAreaHtml='';
				data.shopCategoryList.map(function(item,index){
					tempHtml += '<option data-id = "'+item.shopCategoryId+'">'
						+item.shopCategoryName + '</option>'
				});
				data.areaList.map(function(item,index){
					tempAreaHtml += '<option data-id="'+item.areaId+'">'
						+item.areaName+'</option>';
				});
				$('#shop-category').html(tempHtml);
				$('#area-category').html(tempAreaHtml);
			}
		});
		
	}

	$('#submit').click(function(){
		var shop={};
		
		if(isEdit){
			shop.shopId = shopId.replace("=","");//获取到的字符串是 =xxx 去掉等于号;
		}
		shop.shopName = $('#shop-name').val();
		shop.shopAddr = $('#shop-addr').val();
		shop.phone = $('#shop-phone').val();
		shop.shopDesc = $('#shop-desc').val();
		shop.shopCategory = {
				shopCategoryId:$('#shop-category').find('option').not(function(){
					return !this.selected;
				}).data('id')
		};
		shop.area = {
				areaId:$('#area-category').find('option').not(function(){
					return !this.selected;
				}).data('id')
		};
		var shopImg = $("#shop-img")[0].files[0];
		var formData = new FormData();
		formData.append('shopImg',shopImg);
		formData.append('shopStr',JSON.stringify(shop));
		var verifyCodeActual = $('#input_kaptcha').val();
		if(!verifyCodeActual){
			$.toast("请输入验证码!");
			return;
		}
		formData.append('verifyCodeActual',verifyCodeActual);
		$.ajax({
			url :(isEdit?editShopUrl:registerShopUrl),
			type:'POST',
			data:formData,
			contentType:false,
			processData:false,
			cache:false,
			success:function(data){
				if(data.success){
					$.toast('提交成功!');
				}else{
					$.toast('提交失败!'+data.errMsg);
				}
				$('#kaptcha_img').click();
			}
		});
	});
})

