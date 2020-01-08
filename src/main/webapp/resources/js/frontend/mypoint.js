$(function() {
    var loading = false;
    var maxItems = 999;
    var pageSize = 10;
    var listUrl = '/schoolshop/frontend/listusershopmapsbycustomer';

    var pageNum = 1;
    var shopName = '';

    function addItems(pageSize, pageIndex) {
        // 生成新条目的HTML
        var url = listUrl + '?' + 'pageIndex=' + pageIndex + '&pageSize=' + pageSize + '&shopName=' + shopName;
        loading = true;
        $.getJSON(url, function (data) {
            if (data.success) {
                maxItems = data.count;
                var html = '';
                data.userShopMapList.map(function (item, index) {
                    html += ''
                    +   '<div class="card" data-shop-id="'+ item.shopId +'">'
                    +       '<div class="card-header">'+ item.shopName +'</div>'
                    +       '<div class="card-content">'
                    +            '<div class="list-block media-list">'
                    +                '<ul>'
                    +                    '<li class="item-content">'
                    +                       '<div class="item-inner">'
                    +                           '<div class="item-subtitle">本店积分:'+ item.point +'</div>'
                    +                       '</div>'
                    +                   '</li>'
                    +               '</ul>'
                    +           '</div>'
                    +       '</div>'
                    +       '<div class="card-footer">'
							                    + '<p class="color-gray">更新时间'
							+ new Date(item.createTime).Format("yyyy-MM-dd")
							+'</p>'
                    +       '</div>'
                    +   '</div>';
                });
                $('.list-div').append(html);
                var total = $('.list-div .card').length;
                if (total >= maxItems) {
                    // 加载完毕，则注销无限加载事件，以防不必要的加载
                    $.detachInfiniteScroll($('.infinite-scroll'));
                    // 删除加载提示符
                    $('.infinite-scroll-preloader').remove();
                }
                pageNum += 1;
                loading = false;
                $.refreshScroller();
            }
        });
    }
    //预先加载20条
    addItems(pageSize, pageNum);

    $(document).on('infinite', '.infinite-scroll-bottom', function () {
        if (loading) return;
        addItems(pageSize, pageNum);
    });

    $('#search').on('input', function (e) {
        shopName = e.target.value;
        $('.list-div').empty();
        pageNum = 1;
        addItems(pageSize, pageNum);
    });

    
    $('#me').click(function () {
        $.openPanel('#panel-left-demo');
    });

    $.init();
});
