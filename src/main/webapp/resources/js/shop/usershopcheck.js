$(function() {
    var shopId = 1;
    var userName = '';

    function getList() {
        var listUrl = '/schoolshop/shop/listusershopmapsbyshop?pageIndex=1&pageSize=9999&shopId=' + shopId + '&userName=' + userName;
        $.getJSON(listUrl, function (data) {
            if (data.success) {
                var userShopMapList = data.userShopMapList;
                var tempHtml = '';
                userShopMapList.map(function (item, index) {
                    tempHtml += ''
                         +      '<div class="row row-usershopcheck">'
                         +          '<div class="col-50">'+ item.userName +'</div>'
                         +          '<div class="col-50">'+ item.point +'</div>'
                         +      '</div>';
                });
                $('.usershopcheck-wrap').html(tempHtml);
            }
        });
    }

    $('#search').on('input', function (e) {
        userName = e.target.value;
        $('.usershopcheck-wrap').empty();
        getList();
    });

    getList();
});