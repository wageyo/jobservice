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
	var reg = /^[0-9]{14}[0-9a-zA-Z]{4}[1-7]{1}[1-4]{1}[B]?[0-9]?/;
	var checkResult = reg.test(loginName);
	//如果不是残疾证号, 则对密码进行校验
	if(!checkResult){
	//密码
		var passWord = $('#passWord').val();
		if(passWord == null || passWord == '' ){
			if(!verify.checkname(passWord)){
				alert('您输入的不是残疾证号, 请输入密码!');
				$('#passWord').focus();
				return false;
			}
		}
	}
	return true;
}