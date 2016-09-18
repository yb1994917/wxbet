/**
 * Created by majing on 2016/8/12.
 */

$(function () {
    $(document).on("pageInit", ".page-apply", function(e, id, page) {
        console.log("打赌页面");

        // 字数检查
        $("#apply-des").on("keyup",function () {
            var getlength = $(this).val().length;
            $(this).parent().find('.nums').html(getlength);
        });

        // 时间选择器
        $("#apply-datetime").Dadudatetime({
            onOpen: function (e) {
                $('.picker-modal header .pull-left').on('tap',function () {
                    return false;
                });
                //确认之后输出
                $('.picker-modal header .pull-right').on('tap',function () {
                    $('.showdate').html(e.value[0]+' '+e.value[1]+' '+e.value[2]+':'+e.value[3]);
                    console.log(resolve_datetime(e.value));
                });
            }
        });

        //玩法规则
        page.on('tap', '.explain span', function () {
            $.modal({
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
        })
    });
    $.init();
});