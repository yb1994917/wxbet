/**
 * Created by majing on 2016/8/19.
 */
$(function () {
    $(document).on("pageInit", ".page-addrecord", function(e, id, page) {
        console.log("添加记录");
        
        // 图片选择
	    function chooseImg() {
		  var $img = $('.img-content').find("img");
		  wx.chooseImage({
			  count: 1,
			  sizeType: ['original', 'compressed'],
			  sourceType: ['album', 'camera'],
			  success: function (res) {
				  var localId = res.localIds[0];
				  wx.uploadImage({
					  localId: localId,
					  isShowProgressTips: 1,
					  success: function (res) {
						  var serverId = res.serverId;
						  $img.attr("src", localId);
						  $img.attr("data-serverid", serverId);
					  }
				  });
			  }
		  });
	    }
        
        page.on('tap','.uploader-content', function (e) {
        	//chooseImg();
        	alert("click new");
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
                    	//chooseImg();
                    	alert("click new");
                    	
                    	var imgFile = $('#file-upload').files[0];
                        var reader = new FileReader();
                        reader.readAsDataURL(imgFile);
                        reader.onload = function (e) {
                            $('.uploader-button').append('<span class="img-content"><img src="' + this.result + '" alt="" style="width:100%;height:100%;" /></span>');
                            $('#file-upload').val('');
                        }
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
        
        page.on('tap', '#btn-add', function () {
            var betId = $(this).attr('data_id');
            if (!betId) {
                $.alert('记录的打赌无效');
                return;
            }
            var words = $.trim($('#apply-des').val());
            if (content.length === 0) {
                $.alert('请输入文字或上传图片');
                return;
            }

            var jsonParam = { 'betId': betId, 'content': content, 'serverId':$(this).find(".img-content img").attr("data-serverid")};
            $.ajax({
                'url': '/bet/progress',
                'type': 'post',
                'dataType': 'json',
                'data': jsonParam,
                'success': function (data) {
                    if (data.status === 0) {
                        location.href = '/bet/detail/' + betId;
                    } else {
                        $.alert(data.message);
                    }
                },
                'error': function (xhr, errorType, error) {
                    $.alert('创建失败');
                }
            });
        });

        $(document).on('tap', '.modal-overlay-visible', function () {
            $.closeModal();
        });
    });
    $.init();
});