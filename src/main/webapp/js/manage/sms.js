/***********************************************
 ***************  短信发送页面脚本 **************
 ***********************************************/
$(document).ready(function(){
	
	//控件赋值, focus和blur事件
	$('textarea').css({'color':'rgb(168, 168, 168)'}).focus(function(){
		var val = $(this).attr('title')
		var currentVal = $(this).val();
		$(this).css('color','rgb(0, 0, 0)');
		if(currentVal == val){
			$(this).val('');
		}
	}).blur(function(){
		var val = $(this).attr('title')
		var currentVal = $(this).val();
		if(currentVal == val || currentVal == '' || currentVal == null || currentVal == undefined){
			$(this).val(val).css('color','rgb(168, 168, 168)');
		}
	}).keydown(function(){
		$('#notice').html($(this).val());
	});
});