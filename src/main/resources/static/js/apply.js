/**
 * Created by majing on 2016/8/12.
 */
$(function () {
    $(document).on("pageInit", ".page-apply", function(e, id, page) {
        // 字数检查
        $("#apply-des").on("keyup",function () {
            var getlength = $(this).val().length;
            $(this).parent().find('.nums').html(getlength);
        });
        
        //金额
        $("#apply-amount").on("keyup",function () {
            var amount = $(this).val();
            if (!((/^(\+|-)?\d+$/.test(amount)) && amount > 0)){
            	alert("金额请输入正整数!");  
	   	     	$(this).val("0");  
	   	        return false; 
            } 
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
                    $('#apply-finishTime').val(resolve_datetime(e.value));
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
        });
        
        //创建打赌
        page.on('tap', '#apply-create', function () {
        	var content = $("#apply-des").val();
        	var amount = $("#apply-money").val();
        	var finishTime = $("#apply-finishTime").val();
        	if(content==null||content==""||content==undefined){
        		alert("内容必须填写！");
        		return false;
        	}
        	if(amount==null||amount==""||amount==undefined){
        		alert("金额必须填写！");
        		return false;
        	}
            var jsonParam = { 'content': content, 'amount': amount, 'finishTime': finishTime };
            $.ajax({
                 'url': '/bet/apply',
                 'type': 'post',
                 'dataType': 'json',
                 'data': jsonParam,
                 'success': function (data) {
                     if (data.status) {
                         if(data.orderId) {
 							// 测试环境
 							location.href = "/bet/detail/" + data.data.id;
 						 } else {
 							// 正式环境
 							wx.chooseWXPay({
 								timestamp: data.timeStamp,
 								nonceStr: data.nonceStr,
 								package: data.package,
 								signType: data.signType,
 								paySign: data.paySign,
 								success: function (res) {
 									wx.closeWindow();
 								}
 							});
 						 }
                     } else {
                         $.alert(data.message);
                         return;
                     }
                 },
                 'error': function (xhr, errorType, error) {
                     $.alert('支付发生异常');
                 }
            });
         });
    });
    $.init();
});