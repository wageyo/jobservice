/*********************************************/
/*************** 登陆页面js脚本 ***************/
/*********************************************/

var noticeLoginName = '请输入用户名';
$(function () {
	//用户名焦点获得事件
	$("#loginName").focus(function () {
    	if(noticeLoginName == $(this).val()){
    		$(this).val('');
    	}
	}).blur(function(){
		var loginName = $(this).val();
		if(loginName == null || loginName == ''){
			$(this).val(noticeLoginName);
		}else if(verify.checkname(loginName)){
			$('#loginNameImg').removeClass().addClass('IsOk').show();
		}else{
			$('#loginNameImg').removeClass().addClass('error').show();
		}
	});
	$("#passWord").blur(function(){
		var passWord = $(this).val();
		if(verify.checkname(passWord)){
			$('#passWordImg').removeClass().addClass('IsOk').show();
		}else{
			$('#passWordImg').removeClass().addClass('error').show();
		}
	});
	
	//提交登陆按钮
	$('#loginSubmit').click(function(){
		$('#formLogin').submit();
	});
});

function check(){
	return true;
}