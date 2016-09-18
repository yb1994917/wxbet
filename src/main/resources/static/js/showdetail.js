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

        $(document).on('tap', '.modal-overlay-visible', function () {
            $.closeModal();
        });
    });
    $.init();
});