/**
 * Created by majing on 2016/8/16.
 */

$(function () {
    $(document).on("pageInit", ".page-applydetail", function(e, id, page) {
        console.log("对方参与");

        /**
         * 留言按钮
         */
        page.on('tap','.message-open', function () {
            $.prompt('留言', function (value) {
                $('.message-content').html(value + '&nbsp;');
            });
        });

        /**
         * 玩法规则
         */
        page.on('tap','.open-explain', function () {
            $.modal({
                extraClass: 'modal-explain',
                text: '<div class="content">' +
                '<h3>玩法规则</h3>' +
                '<p class="top"><b>什么是一元鼓励？</b></p>' +
                '<p>好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字</p>' +
                '<p class="top"><b>什么是一元鼓励？</b></p>' +
                '<p>好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字</p>' +
                '<p class="top"><b>什么是一元鼓励？</b></p>' +
                '<p>好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字</p>' +
                '<p class="top"><b>什么是一元鼓励？</b></p>' +
                '<p>好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字好多字</p>' +
                '</div>',
                buttons: [
                    {
                        text: '确定',
                        bold: true
                    }
                ]
            });
        });

        /**
         * 金额变化
         * @type {number}
         */
        var money = 1;
        var maxMoney = 10; //最大金额
        page.on('tap', '.btn.plus', function () {
            if(money < 9){
                money ++;
                $('.btn-content,.add-money').find('.money').html(money);
                $('.add-money').removeClass('over');
            }else if(money == 9){
                money ++;
                $('.btn-content,.add-money').find('.money').html(money);
                $('.add-money').addClass('over');
            }else{
                money = 10;
            }
        });
        page.on('tap', '.btn.reduce', function () {
            if(money > 0){
                money --;
                $('.btn-content,.add-money').find('.money').html(money);
                $('.add-money').removeClass('over');
            }else if(money == 0){
                $('.add-money').addClass('');
            }else{
                money = 1;
            }
        });
        page.on('tap', '.btn-content', function () {
            $.modal({
                extraClass: 'modal-money',
                text:'<p>其他金额</p>'+
                '<div class="input-content">' +
                '<span>金额（元）</span><input type="text"  placeholder="可填写0.1-1000">'+
                '</div><p class="alert">只允许输入数字</p>',
                buttons: [
                    {
                        text: '取消',
                        bold: true
                    },
                    {
                        text: '微信支付',
                        bold: true,
                        close: false,
                        onClick: function() {
                            var num = $('.modal-money .modal-text-input').val();
                            var r = new RegExp(/^\d+$/);
                            console.log(r.test(num));
                            if(r.test(num)){
                                if(num % 10 == 0 && num > 0.1){
                                    $('.btn-content,.add-money').find('.money').html($('.modal-money .modal-text-input').val());
                                    $('.modal-money .alert').removeClass('error');
                                    $.closeModal();
                                }else{
                                    $('.modal-money .alert').html('金额只能为整数');
                                    $('.modal-money .alert').addClass('error');
                                }
                            }else{
                                $('.modal-money .alert').html('只允许输入数字');
                                $('.modal-money .alert').addClass('error');
                            }
                        }
                    }
                ]
            });
        });

        /**
         * tap
         */
        page.on('tap','.dadu-tap',function () {
            $.modal({
                extraClass: 'modal-tap',
                text:'<div class="row no-gutter">' +
                '<div class="col-100 tap-top">' +
                '<p>最有趣又高效的目标激励工具-打赌咯</p>' +
                '</div>' +
                '<div class="col-100 tap-content">' +
                '<p><img src="images/ewm.png" alt=""></p>' +
                '</div>' +
                '</div>'
            });
        });

        /**
         * 点击x元打赌
         */
        page.on('tap','.add-money',function () {
            $.modal({
                extraClass: 'modal-resultover',
                text:'<div class="row no-gutter">' +
                '<div class="col-100 result-top getmoney">' +
                '<p>恭喜你获得</p>' +
                '<p class="money">¥45</p>' +
                '</div>' +
                '<div class="col-100 result-content row">' +
                '<p class="col-100 button-content"><img src="./images/121212.png"></p>' +
                '<p class="sub col-100">对方完成后可对结果进行质疑</p>' +
                '<p class="sub col-100">对方若未完成，则系统将自动转账给你</p>' +
                '</div>'+
                '</div>',
                buttons: [
                    {
                        text: '关闭',
                        bold: true
                    }
                ]
            });
        });

        $(document).on('tap', '.modal-overlay-visible', function () {
            $.closeModal();
        });
    });

    $(document).on('pageInit','.invite-agree',function(e, id, page){
        $.modal({
                extraClass: 'modal-resultover',
                text:'<div class="row no-gutter">' +
                '<div class="col-100 result-top getmoney">' +
                '<p>你正在和他1对1打赌</p>' +
                '<p class="userimg"><span><img src="http://wx.qlogo.cn/mmopen/ajNVdqHZLLCaUUZrJfUCf0IxlD5U4IoAyNyl8sbvUh5SXTq9LunFK78EkCcrGZ81RhTCibqQWX7sYbMN8JxrSmQ/0" /></span></p>' +
                '</div>' +
                '<div class="col-100 result-content row">' +
                '<p class="col-100 button-content"><img src="./images/3232.png"></p>' +
                '<p class="sub col-100">对方拥有判定特权，对结果进行最终判定。</p>' +
                '<p class="sub col-100">记得发图证记录过程哦！</p>' +
                '</div>'+
                '</div>',
                buttons: [
                    {
                        text: '关闭',
                        bold: true
                    }
                ]
            });
    });
    $.init();
});