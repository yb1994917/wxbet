/**
 * Created by majing on 2016/8/19.
 */
$(function () {
    $(document).on("pageInit", ".page-addrecord", function(e, id, page) {
        console.log("添加记录");
        
        page.on('tap','.uploader-button', function (e) {
            $(this).append('<span class="img-content"><img src="images/content-img.png" alt=""></span>');
        });

        page.on('tap','.img-content', function () {
            var buttons1 = [
                {
                    text: '删除',
                    bold: true,
                    color: 'danger',
                    onClick: function() {
                        $('.uploader-button .img-content').remove();
                    }
                },
                {
                    text: '重新上传',
                    onClick: function() {
                        $.alert("你选择了“重新上传“");
                    }
                }
            ];
            var buttons2 = [
                {
                    text: '取消',
                    bg: 'danger'
                }
            ];
            var groups = [buttons1, buttons2];
            $.actions(groups);
        });

        $(document).on('tap', '.modal-overlay-visible', function () {
            $.closeModal();
        });
    });
    $.init();
});