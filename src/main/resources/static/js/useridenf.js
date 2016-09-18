/**
 * Created by majing on 2016/8/18.
 */

$(function () {
    $(document).on("pageInit", ".page-idenf", function(e, id, page) {
        console.log("用户判定");

        page.on('tap', '.check-button', function () {
            $(this).parent().addClass('not');
        });

    });
    $.init();
});