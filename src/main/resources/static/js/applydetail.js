/**
 * Created by majing on 2016/8/12.
 */
$(function () {
    $(document).on("pageInit", ".page-applydetail", function(e, id, page) {
        console.log("详情页面");

        /**
         * 关闭分享
         */
        page.on('tap','.content.showshare', function () {
            $('.content').removeClass('showshare');
        });

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
            $(".rules-mask").addClass('show');
            $(".rules-swiper").swiper({
                pagination : '.rules-pagination'
            });
            $(".rules-mask").off('tap').on('tap', '.close-mask', function () {
                $(".rules-mask").removeClass('show');
            })
        });

        /**
         * 设置按钮
         */
        page.on('tap', '.setting-button', function () {
            $.modal({
                extraClass: 'modal-setting',
                text:'<div class="row no-gutter">' +
                '<div class="col-100"><p>参与：</p></div>' +
                '<div class="col-50">' +
                '<label class="user-part"><span class="radio-box checked"></span><input type="radio"  name="user-part" value="0"><span>允许参与</span></label>' +
                '</div>' +
                '<div class="col-50"><label class="user-part"><span class="radio-box"></span><input type="radio"  name="user-part" value="1"><span>停止参与</span></label>' +
                '</div>' +
                '<div class="divider"></div>' +
                '<div class="col-100"><p>谁可见：</p></div>' +
                '<div class="col-100">' +
                '<label class="user-allow"><span class="radio-box checked"></span><input type="radio"  name="user-allow" value="0"><span>公开</span></label>' +
                '</div>' +
                '<div class="col-100">' +
                '<label class="user-allow"><span class="radio-box"></span><input type="radio"  name="user-allow" value="1"><span>私密（仅已参与者可见）</span></label>' +
                '</div>' +
                '</div>',
                buttons: [
                    {
                        text: '确定',
                        bold: true
                    }
                ]
            });
            $('input[name=user-part]').on('change',function () {
                var getval = parseInt($(this).val());
                $(".modal-setting .user-part .radio-box").removeClass('checked');
                if(!$(".modal-setting .user-part").eq(getval).find('.radio-box').hasClass('checked')){
                    $(".modal-setting .user-part").eq(getval).find('.radio-box').addClass('checked');
                }
            });
            $('input[name=user-allow]').on('change',function () {
                var getval = parseInt($(this).val());
                $(".modal-setting .user-allow .radio-box").removeClass('checked');
                if(!$(".modal-setting .user-allow").eq(getval).find('.radio-box').hasClass('checked')){
                    $(".modal-setting .user-allow").eq(getval).find('.radio-box').addClass('checked');
                }
            });
        });

        /**
         * 判定按钮
         */
        page.on('tap', '.result-button', function () {
            var checkval = 0;
            $.modal({
                extraClass: 'modal-result',
                text:'<div class="row no-gutter">' +
                '<div class="col-100">' +
                '<label class="user-result"><span class="radio-box checked"><i class="fa fa-circle-o"></i><i class="fa fa-check-circle"></i></span><input type="radio"  name="user-result" value="0"><span>真高兴，按时完成了目标</span></label>' +
                '</div>' +
                '<div class="col-100">' +
                '<label class="user-result"><span class="radio-box"><i class="fa fa-circle-o"></i><i class="fa fa-check-circle"></i></span><input type="radio"  name="user-result" value="1"><span>很遗憾，这事我没有完成</span></label>' +
                '</div>' +
                '<div class="col-100">' +
                '<label class="user-result"><span class="radio-box"><i class="fa fa-circle-o"></i><i class="fa fa-check-circle"></i></span><input type="radio"  name="user-result" value="2"><span>停止打赌 <i>（剩余3次机会）</i></span></label>' +
                '</div>' +
                '</div>',
                buttons: [
                    {
                        text: '取消',
                        bold: true
                    },
                    {
                        text: '确定',
                        bold: true,
                        onClick: function () {
                            switch (checkval){
                                case 0:
                                    $('.apply-title .tip').addClass('success');
                                    $('.apply-title .tip').removeClass('fail cancel');
                                    $('.apply-title .tip').css('display','block');
                                    $('.applydetail-bar').addClass('over');
                                    break;
                                case 1:
                                    $('.apply-title .tip').addClass('fail');
                                    $('.apply-title .tip').removeClass('success cancel');
                                    $('.apply-title .tip').css('display','block');
                                    $('.applydetail-bar').addClass('over');
                                    break;
                                case 2:
                                    $('.apply-title .tip').addClass('cancel');
                                    $('.apply-title .tip').removeClass('fail success');
                                    $('.apply-title .tip').css('display','block');
                                    $('.applydetail-bar').addClass('over');
                                    break;
                            }
                        }
                    }
                ]
            });
            $('input[name=user-result]').on('change',function () {
                checkval = parseInt($(this).val());
                $(".modal-result .radio-box").removeClass('checked');
                if(!$(".modal-result .user-result").eq(checkval).find('.radio-box').hasClass('checked')){
                    $(".modal-result .user-result").eq(checkval).find('.radio-box').addClass('checked');
                }
            });
        });
        /**
         * 判定结果
         */
        page.on('tap','.resultover-button', function () {
            $.modal({
                extraClass: 'modal-resultover',
                text:'<div class="row no-gutter">' +
                '<div class="col-100 result-top">' +
                '<p>奖池：<span>45</span>元</p>' +
                '</div>' +
                '<div class="col-100 result-gap">' +
                '<span class="left">目标失败，奖金分配情况如下</span>' +
                '<span class="right" style="display: none"><img src="images/i@2x.png" alt=""></span>' +
                '</div><div class="col-100 result-content">' +
                '<ul class="content">' +
                '<li class="row no-gutter">' +
                '<div class="col-10 user-headimg"><span class="user-img"><img src="http://wx.qlogo.cn/mmopen/PiajxSqBRaEIJh62qOdu905hwZBHkGl7ERCPgvMpSfy3IJGIPgpBPMuwFOmp65Hnr3ScMjnmLIzVB5eodyic195w/0" alt=""></span></div>' +
                '<div class="col-70 user-name">葫芦<span class="sub">（发起者）</span>' +
                '</div>' +
                '<div class="col-20 user-money">¥0' +
                '</div>' +
                '</li>' +
                '<li class="row no-gutter">' +
                '<div class="col-10 user-headimg"><span class="user-img"><img src="http://wx.qlogo.cn/mmopen/PiajxSqBRaEIJh62qOdu905hwZBHkGl7ERCPgvMpSfy3IJGIPgpBPMuwFOmp65Hnr3ScMjnmLIzVB5eodyic195w/0" alt=""></span></div>' +
                '<div class="col-70 user-name">葫芦' +
                '</div>' +
                '<div class="col-20 user-money">¥0' +
                '</div>' +
                '</li>' +
                '<li class="row no-gutter">' +
                '<div class="col-10 user-headimg"><span class="user-img"><img src="http://wx.qlogo.cn/mmopen/PiajxSqBRaEIJh62qOdu905hwZBHkGl7ERCPgvMpSfy3IJGIPgpBPMuwFOmp65Hnr3ScMjnmLIzVB5eodyic195w/0" alt=""></span></div>' +
                '<div class="col-70 user-name">葫芦' +
                '</div>' +
                '<div class="col-20 user-money">¥100' +
                '</div>' +
                '</li>' +
                '<li class="row no-gutter">' +
                '<div class="col-10 user-headimg"><span class="user-img"><img src="http://wx.qlogo.cn/mmopen/PiajxSqBRaEIJh62qOdu905hwZBHkGl7ERCPgvMpSfy3IJGIPgpBPMuwFOmp65Hnr3ScMjnmLIzVB5eodyic195w/0" alt=""></span></div>' +
                '<div class="col-70 user-name">葫芦' +
                '</div>' +
                '<div class="col-20 user-money">¥0' +
                '</div>' +
                '</li>' +
                '</ul>' +
                '</div>'+
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
         * 认可
         */
        page.on('tap','.over-content .agree', function () {
            $('.over-content').parents('.applydetail-bar').addClass('over');
        });
        /**
         * 质疑
         */
        page.on('tap','.over-content .refuse', function () {
            $.modal({
                extraClass: 'modal-refuse',
                text:'<p>提交质疑后，系统会扣减对方的信用度，但无法改变本次已判定结果!</p><p>（备注：信用度影响对方能否继续发起打赌）</p>',
                buttons: [
                    {
                        text: '取消',
                        bold: true
                    },
                    {
                        text: '确定',
                        bold: true
                    }
                ]
            });
        });
        /**
         * 分享
         */
        page.on('tap','.clickshare', function () {
            $('.content').addClass('showshare');
        });
        /**
         * 领取监督礼金
         */
        page.on('tap','.getmoney-content .user-button',function () {
            $.modal({
                extraClass: 'modal-resultover',
                text:'<div class="row no-gutter">' +
                '<div class="col-100 result-top getmoney">' +
                '<p>恭喜你获得</p>' +
                '<p class="money">¥45</p>' +
                '</div>' +
                '<div class="col-100 result-content row">' +
                '<h3>监督特权</h3>' +
                '<div class="col-100 button-content">' +
                '<div class="col-50"><button class="button button-success">认可</button></div>' +
                '<div class="col-50"><button class="button button-success">质疑</button></div>' +
                '</div>' +
                '<p class="sub col-100">对方完成后可对结果进行质疑</p>' +
                '<p class="sub col-100">对方若未完成，则系统将自动转账给你</p>' +
                '</div>'+
                '</div>',
                buttons: [
                    {
                        text: '取消',
                        bold: true
                    }
                ]
            });
            $(this).parents('.applydetail-bar').addClass('over');
        });

        page.on('tap','.overmoney-button',function () {
            $.modal({
                extraClass: 'modal-getmoney',
                text:'<img src="./images/TC.png">'
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

        $('.showdetail-bar').hide();
        page.on('click','.overmoney-content button.button', function(){
            $('.showdetail-bar').show();
            $('.applydetail-bar').hide();
        });

        /**
         * 金额变化
         * @type {number}
         */
        var money = 1;
        var maxMoney = 10; //最大金额
        page.on('tap', '.showdetail-bar .btn.plus', function () {
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
        page.on('tap', '.showdetail-bar .btn.reduce', function () {
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
        page.on('tap', '.showdetail-bar .btn-content', function () {
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
         * 点击x元打赌
         */
        page.on('tap','.showdetail-bar .add-money',function () {
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
    $.init();
});