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
        
	    page.on('change', '#file-upload', function () {
            var imgFile = this.files[0];

            var reader = new FileReader();
            reader.readAsDataURL(imgFile);
            reader.onload = function (e) {
                $('.uploader-button').append('<span class="img-content"><img src="' + this.result + '" alt="" style="width:100%;height:100%;" /></span>');
                $('#file-upload').val('');
            }
            var uploadFile=$(this).val();
            var formData = new FormData(); 
   		    var ispost=false;
	   		$.each($(this)[0].files, function(i, file) {
	   			var size=file.size/1024;
	   			if(size > 1024*10){
	   				alert('文件超过10M');
   					return;
	   			}
	   			formData.append('imgfile', file);
	   			ispost=true;
	   		});
	   		if(!ispost) return;
	   		$.ajax({
		   	     url: '/upload',
		   	     type: 'POST',
		   	     data: formData,
		   	     async: false,
		   	     cache: false,
		   	     contentType: false,
		   	  	 dataType: 'json',
		   	     enctype: 'multipart/form-data',
		   	     processData: false,
		   	     success: function (data) {
		   	        console.log(data.message);
		   	     	if(!data.status){
	   					alert(data.message);
	   					return;
	   				}
	   				if(data.status){
	   					$('#pic').val(data.data);
	   				}
		   	     },
	   			 error: function(XMLHttpRequest, textStatus, errorThrown) {
                    alert("服务器错误码:"+XMLHttpRequest.status);
                 }
	   	   });
        })

        page.on('tap', '.uploader-button', function (e) {
            if ($('.img-content').size() >= 1) return;
            $('#file-upload').trigger('click');
        });

        page.on('tap', '.img-content', function () {
            var buttons1 = [
                {
                    text: '删除',
                    bold: true,
                    color: 'danger',
                    onClick: function () {
                        $('.uploader-button .img-content').remove();
                    }
                },
                {
                    text: '重新上传',
                    onClick: function () {
                        $('#file-upload').trigger('click');
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
        	$(this).prop("disabled", "disabled");
            var betId = $(this).attr('data_id');
            if (!betId) {
            	$(this).prop("disabled", "");
                $.alert('记录的打赌无效');
                return;
            }
            var content = $.trim($('#apply-des').val());
            if (content.length === 0) {
            	$(this).prop("disabled", "");
                $.alert('请输入文字或上传图片');
                return;
            }

            var jsonParam = { 'betId': betId, 'content': content, 'pic':$('#pic').val()};
            $.ajax({
                'url': '/bet/progress',
                'type': 'post',
                'dataType': 'json',
                'data': jsonParam,
                'success': function (data) {
                    if (data.status) {
                        location.href = '/bet/detail/' + betId;
                    } else {
                    	$(this).prop("disabled", "");
                        $.alert(data.message);
                    }
                },
                'error': function (xhr, errorType, error) {
                	$(this).prop("disabled", "");
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