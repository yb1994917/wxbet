/**
 * Created by majing on 2016/8/12.
 */

$(function () {
    $(document).on("pageInit", ".page-applydetail", function(e, id, page) {
        console.log("详情页面");

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
            $.modal({
                extraClass: 'modal-result',
                verticalButtons: true,
                buttons: [
                    {
                        text: '目标完成',
                        bold: true,
                        onClick: function() {
                            $('.apply-title .tip').addClass('success');
                            $('.apply-title .tip').removeClass('fail cancel');
                            $('.apply-title .tip').css('display','block');
                            $('.applydetail-bar').addClass('over');
                        }
                    },
                    {
                        text: '目标失败',
                        bold: true,
                        onClick: function() {
                            $('.apply-title .tip').addClass('fail');
                            $('.apply-title .tip').removeClass('success cancel');
                            $('.apply-title .tip').css('display','block');
                            $('.applydetail-bar').addClass('over');
                        }
                    },
                    {
                        text: '双方协商，停止打赌',
                        bold: true,
                        onClick: function() {
                            $('.apply-title .tip').addClass('cancel');
                            $('.apply-title .tip').removeClass('fail success');
                            $('.apply-title .tip').css('display','block');
                            $('.applydetail-bar').addClass('over');
                        }
                    }
                ]
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
                '<span class="right"><img src="images/i@2x.png" alt=""></span>' +
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
         * 领取监督礼金
         */
        page.on('tap','.getmoney-button',function () {
            $.modal({
                extraClass: 'modal-getmoney',
                text:'<img src="./images/TC.png">'
            });
            $(this).parent().addClass('over');
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

        $(document).on('tap', '.modal-overlay-visible', function () {
            $.closeModal();
        });
        
    });
    $.init();
});