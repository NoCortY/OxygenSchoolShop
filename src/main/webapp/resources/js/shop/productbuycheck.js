$(function() {
    var shopId = 1;
    var productName = '';

    function getList() {
        var listUrl = '/myo2o/shop/listuserproductmapsbyshop?pageIndex=1&pageSize=9999&shopId=' + shopId + '&productName=' + productName;
        $.getJSON(listUrl, function (data) {
            if (data.success) {
                var userProductMapList = data.userProductMapList;
                var tempHtml = '';
                userProductMapList.map(function (item, index) {
                    tempHtml += ''
                         +      '<div class="row row-productbuycheck">'
                         +          '<div class="col-33">'+ item.productName +'</div>'
                         +          '<div class="col-33 productbuycheck-time">'+ item.createTime +'</div>'
                         +          '<div class="col-33">'+ item.userName +'</div>'
                         +      '</div>';
                });
                $('.productbuycheck-wrap').html(tempHtml);
            }
        });
    }

    $('#search').on('input', function (e) {
        productName = e.target.value;
        $('.productbuycheck-wrap').empty();
        getList();
    });

    getList();






    var myChart = echarts.init(document.getElementById('chart'));

    var option = {
        tooltip : {
            trigger: 'axis',
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        legend: {
            data:['食品','衣物','书籍']
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis : [
            {
                type : 'category',
                data : ['周一','周二','周三','周四','周五','周六','周日']
            }
        ],
        yAxis : [
            {
                type : 'value'
            }
        ],
        series : [
            {
                name:'食品',
                type:'bar',
                data:[120, 132, 101, 134, 290, 230, 220]
            },
            {
                name:'衣物',
                type:'bar',
                data:[60, 72, 71, 74, 190, 130, 110]
            },
            {
                name:'书籍',
                type:'bar',
                data:[62, 82, 91, 84, 109, 110, 120]
            }
        ]
    };

    myChart.setOption(option);






});