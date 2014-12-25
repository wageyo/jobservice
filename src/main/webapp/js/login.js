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

/*function check(){
	return true;
}*/
/*******  登陆用户名和密码校验   ********/
function check(){
	//账号
	var loginName = $('#loginName').val();
	if(!verify.checkname(loginName)){
		alert('用户名只能为5-20为的英文, 数字或下划线_组成.');
		$('#loginName').focus();
		return false;
	}
	//密码
	var passWord = $('#passWord').val();
	if(!verify.checkname(passWord)){
		alert('密码只能为5-20为的英文, 数字或下划线_组成.');
		$('#passWord').focus();
		return false;
	}
	return true;
}