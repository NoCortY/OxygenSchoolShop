$(function() {
    var shopId = 1;
    var listUrl = '/myo2o/shop/listshopauthmapsbyshop?pageIndex=1&pageSize=9999&shopId=' + shopId;
    var deleteUrl = '/myo2o/shop/removeshopauthmap';

    function getList() {
        $.getJSON(listUrl, function (data) {
            if (data.success) {
                var shopauthList = data.shopAuthMapList;
                var tempHtml = '';
                shopauthList.map(function (item, index) {
                    tempHtml += ''
                         +      '<div class="row row-shopauth">'
                         +          '<div class="col-40">'+ item.name +'</div>'
                         +          '<div class="col-20">'+ item.title +'</div>'
                         +          '<div class="col-40">'
                         +              '<a href="#" class="edit" data-employee-id="'+ item.employeeId +'" data-auth-id="'+ item.shopAuthId +'" data-status="'+ item.enableStatus +'">编辑</a>'
                         +              '<a href="#" class="delete" data-employee-id="'+ item.employeeId +'" data-auth-id="'+ item.shopAuthId +'" data-status="'+ item.enableStatus +'">删除</a>'
                         +          '</div>'
                         +      '</div>';
                });
                $('.shopauth-wrap').html(tempHtml);
            }
        });
    }

    getList();

    function deleteItem(id) {
        $.confirm('确定么?', function () {
            $.ajax({
                url: deleteUrl,
                type: 'POST',
                data: {
                    shopAuthId: id,
                },
                dataType: 'json',
                success: function (data) {
                    if (data.success) {
                        $.toast('删除成功！');
                        getList();
                    } else {
                        $.toast('删除失败！');
                    }
                }
            });
        });
    }

    $('.shopauth-wrap').on('click', 'a', function (e) {
        var target = $(e.currentTarget);
        if (target.hasClass('edit')) {
            window.location.href = '/myo2o/shop/shopauthedit?shopauthId=' + e.currentTarget.dataset.authId;
        } else if (target.hasClass('delete')) {
            deleteItem(e.currentTarget.dataset.authId);
        }
    });

    // $('#new').click(function () {
    //     window.location.href = '/myo2o/shop/shopauthedit';
    // });
});