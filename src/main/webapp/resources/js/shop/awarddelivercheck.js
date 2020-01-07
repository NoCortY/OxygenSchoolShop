$(function() {
	var shopId = 1;
	var awardName = '';

	function getList() {
		var listUrl = '/myo2o/shop/listuserawardmapsbyshop?pageIndex=1&pageSize=9999&shopId='
				+ shopId + '&awardName=' + awardName;
		$.getJSON(listUrl, function(data) {
			if (data.success) {
				var userAwardMapList = data.userAwardMapList;
				var tempHtml = '';
				userAwardMapList.map(function(item, index) {
					tempHtml += '' + '<div class="row row-awarddeliver">'
							+ '<div class="col-33">' + item.awardName
							+ '</div>'
							+ '<div class="col-33 awarddeliver-time">'
							+ new Date(item.createTime).Format("yyyy-MM-dd HH:mm:ss")
							+ '</div>' + '<div class="col-33">' + item.userName
							+ '</div>' + '</div>';
				});
				$('.awarddeliver-wrap').html(tempHtml);
			}
		});
	}

	$('#search').on('input', function(e) {
		awardName = e.target.value;
		$('.awarddeliver-wrap').empty();
		getList();
	});

	getList();
});