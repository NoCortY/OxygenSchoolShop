$(function(){
	//定义访问后台,获取头条列表以及一级类别列表的URL
	var url='/o2o/frontend/listmainpageinfo';
	$.getJSON(url,function(data){
		if(data.success){
			//获取后台传递过来的头条列表
			var headLineList = data.headLineList;
			var swiperHtml = '';
			//遍历头条列表,并拼接出轮播图组
			headLineList.map(function(item,index){
				swiperHtml += ''+'<div class = "swiper-slide img-wrap">'
						+'<a href = "'+item.lineLink
						+'"external><img class = "banner-img" src="' + item.lineImg
						+'"alt="'+item.lineName+'"></a>'+'</div>';
			});
			//将轮播图组赋值给前端HTML控件
			$('.swiper-wrapper').html(swiperHtml);
			//设定轮播图轮换时间为3秒
			$('.swiper-container').swiper({
				autoplay:3000,
				//用户对轮播图进行操作时是否自动停止autoplay
				autoplayDisableOnInteraction:false
			});
			//获取后台传递过来的大类列表
			var shopCategoryList = data.shopCategoryList;
			var categoryHtml='';
			//遍历大类列表，拼接处两两(col-50)一行的类别
			shopCategoryList.map(function(item,index){
				categoryHtml += ''
					+'<div class="col-50 shop-classify" data-category = '
					+item.shopCategoryId + '>' +'<div class="word">'
					+'<p class = "shop-title">'+item.shopCategoryName
					+'</p>'+'<p class = "shop-desc">'
					+ item.shopCategoryDesc +'</p>' + '</div>'
					+'<div class = "shop-classify-img-wrap">'
					+'<img class = "shop-img" src="'+item.shopCategoryImg
					+'">'+'</div>'+'</div>';
			});
			//将拼接好的类别赋值给前端HTML控件进行展示
			$('.row').html(categoryHtml);
		}
	});
	//若点击"我的",则显示侧边栏
	$('#me').click(function(){
		$.openPanel('#panel-left-demo');
	});
	$('.row').on('click','.shop-classify',function(e){
		var shopCategoryId = e.currentTarget.dataset.category;
		var newUrl = '/o2o/frontend/shoplist?parentId='+shopCategoryId;
		window.location.href=newUrl;
	});
});
