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
                $('.close-picker').on('tap',function () {
                    $("#apply-datetime").picker("close");
                })
            }
        });

        //玩法规则
        page.on('tap', '.explain span', function () {
            $(".rules-mask").addClass('show');
            $(".rules-swiper").swiper({
                pagination : '.rules-pagination'
            });
            $(".rules-mask").off('tap').on('tap', '.close-mask', function () {
                $(".rules-mask").removeClass('show');
            })
        });


         //切换付款方式
        page.on('tap', '.tip span.self a', function () {
            $(this).parents('.tip').addClass('change');
             $(this).parents('.content').find('.list-2').addClass('change');
        });
         page.on('tap', '.tip span.other a', function () {
            $(this).parents('.tip').removeClass('change');
             $(this).parents('.content').find('.list-2').removeClass('change');
        });
    });
    $.init();
});