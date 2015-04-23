
$(document).ready(function(){

	// 账号校验
	$('#loginName').focus(function(){
		$('#showUserNameMsg').css('color','black').html('由5-20位字母、数字或下划线组成.');
	}).blur(function(){
		var loginName = $(this).val();
		if(!verify.checkname(loginName)){
			$('#showUserNameImg').removeClass('CorrectFormtips ErrorFormtips').addClass('ErrorFormtips');
			$('#showUserNameMsg').css('color','red').html('由5-20位字母、数字或下划线组成.');
			return;
		}
		$.ajax({
			url : server.url + 'user/check/' + loginName,
			dataType : 'json',
			type : 'GET',
			async : false,
			success : function(json) {
				//存在时
				if(json.notice == 'success'){
					$('#showUserNameImg').removeClass('CorrectFormtips ErrorFormtips').addClass('CorrectFormtips');
					$('#showUserNameMsg').css('color','black').html('该用户名可以使用.');
					return false;
				}
				if(json.notice == 'failure'){
					$('#showUserNameImg').removeClass('CorrectFormtips ErrorFormtips').addClass('ErrorFormtips');
					$('#showUserNameMsg').css('color','red').html('该用户名已经存在.');
					return false;
				}
			},
		});
	});
	
	//密码校验
	$('#txtPassWord').focus(function(){
		$('#showPasswordImg').css('color','black');
		$('#showPasswordMsg').html('请输入英文字母、数字或下划线，长度5-20个字符.');
	}).blur(function(){
		var pwd = $(this).val();
		if(!verify.checkname(pwd)){
			$('#showPasswordImg').removeClass('CorrectFormtips ErrorFormtips').addClass('ErrorFormtips');
			$('#showPasswordMsg').css('color','red').html('请输入英文字母、数字或下划线，长度5-20个字符.');
		}else{
			$('#showPasswordImg').removeClass('CorrectFormtips ErrorFormtips').addClass('CorrectFormtips');
			$('#showPasswordMsg').css('color','black').html('密码校验正确.');
		}
	});
	
	//真实姓名
	$('#nickName').focus(function(){
		$('#showNickNameImg').css('color','black');
		$('#showNickNameMsg').html('请输入您的真实姓名, 以方便残联就业管理中心进行管理.');
	}).blur(function(){
		var nickName = $(this).val();
		if(verify.isNull(nickName)){
			$('#showNickNameImg').removeClass('CorrectFormtips ErrorFormtips').addClass('ErrorFormtips');
			$('#showNickNameMsg').css('color','red').html('请输入您的真实姓名, 以方便残联就业管理中心进行管理.');
		}else{
			$('#showNickNameImg').removeClass('CorrectFormtips ErrorFormtips').addClass('CorrectFormtips');
			$('#showNickNameMsg').css('color','black').html('校验正确.');
		}
	});
	
	//确认密码
	$('#passWordConfirm').focus(function(){
		$('#showPassWordConfirmImg').css('color','black');
		$('#showPassWordConfirmMsg').html('请输入英文字母、数字或下划线，长度5-20个字符.');
	}).blur(function(){
		var pwd = $('#txtPassWord').val();
		var conpwd = $(this).val();
		if(conpwd != '' && conpwd != null && conpwd != undefined && conpwd == pwd){
			$('#showPassWordConfirmImg').removeClass('CorrectFormtips ErrorFormtips').addClass('CorrectFormtips');
			$('#showPassWordConfirmMsg').css('color','black').html('确认密码正确.');
		}else{
			$('#showPassWordConfirmImg').removeClass('CorrectFormtips ErrorFormtips').addClass('ErrorFormtips');
			$('#showPassWordConfirmMsg').css('color','red').html('确认密码错误.');
		}
	});
	
	//联系电话
	$('#phone').focus(function(){
		$('#showPhoneImg').css('color','black');
		$('#showPhoneMsg').html('个人用户必须联系方式, 方便残疾人就业指导中心联系你及推送招聘信息用.');
	}).blur(function(){
		var nickName = $(this).val();
		var identity = $('#identity').val();
		//只有个人用户才进行检测真实姓名的操作
		if(identity == 'person'){
			if(verify.isNull(nickName)){
				$('#showPhoneImg').removeClass('CorrectFormtips ErrorFormtips').addClass('ErrorFormtips');
				$('#showPhoneMsg').css('color','red').html('个人用户必须联系方式, 方便残疾人就业指导中心联系你及推送招聘信息用.');
			}else{
				$('#showPhoneImg').removeClass('CorrectFormtips ErrorFormtips').addClass('CorrectFormtips');
				$('#showPhoneMsg').css('color','black').html('校验正确.');
			}
		}
	});
	
	//qq号码
	$('#qq').focus(function(){
		$('#showQqImg').css('color','black');
		$('#showQqMsg').html('qq号码.');
	}).blur(function(){
		var nickName = $(this).val();
		if(nickName != null  && nickName != '' && nickName != undefined){
			if(!verify.isNumber(nickName)){
				$('#showQqImg').removeClass('CorrectFormtips ErrorFormtips').addClass('ErrorFormtips');
				$('#showQqMsg').css('color','red').html('qq号码只能为数字');
			}else{
				$('#showQqImg').removeClass('CorrectFormtips ErrorFormtips').addClass('CorrectFormtips');
				$('#showQqMsg').css('color','black').html('校验正确.');
				$('#email').val($('#qq').val() + '@qq.com');
			}
		}else{
			$('#showQqImg').removeClass('CorrectFormtips ErrorFormtips');
			$('#showQqMsg').css('color','#606060').html('qq号码.');
			$('#email').val('');
		}
	});
	
	//email
	$('#email').focus(function(){
		$('#showEmailImg').css('color','black');
		$('#showEmailMsg').html('请输入英文字母、数字或下划线，长度5-20个字符.');
	}).blur(function(){
		var email = $(this).val();
		if(!verify.isEmail(email)){
			$('#showEmailImg').removeClass('CorrectFormtips ErrorFormtips').addClass('ErrorFormtips');
			$('#showEmailMsg').css('color','red').html('请输入正确的email格式, 以便在您忘记用户名或密码的时候找回使用.');
		}else{
			$.ajax({
				url: server.url + 'user/checkEmail',
				type:'POST',
				dataType:'json',
				data:{'email':email},
				success:function(json){
					if(json.notice == 'success'){
						$('#showEmailImg').removeClass('CorrectFormtips ErrorFormtips').addClass('ErrorFormtips');
						$('#showEmailMsg').css('color','red').html('该邮箱已经存在, 请重新输入.');
					}else{
						$('#showEmailImg').removeClass('CorrectFormtips ErrorFormtips').addClass('CorrectFormtips');
						$('#showEmailMsg').css('color','black').html('邮箱通过验证正确.');
					}
				},
				error:function(){
					$('#showEmailImg').removeClass('CorrectFormtips ErrorFormtips').addClass('ErrorFormtips');
					$('#showEmailMsg').css('color','red').html('验证邮箱发生错误, 请重新刷新页面后重新尝试或者联系管理员.');
				}
			});
			
		}
	});
	
	//验证码
	$('#LoginVerifyCode').focus(function(){
		$('#showCheckCodeImg').css('color','black');
		$('#showCheckCodeMsg').html('请输入验证码.');
	}).blur(function(){
		var val = $(this).val();
		if(val == null || val == '' || val == undefined){
			$('#showCheckCodeImg').removeClass('CorrectFormtips ErrorFormtips').addClass('ErrorFormtips');
			$('#showCheckCodeMsg').css('color','red').html('请输入验证码.');
		}else{
			$('#showCheckCodeImg').removeClass('CorrectFormtips ErrorFormtips').addClass('CorrectFormtips');
			$('#showCheckCodeMsg').css('color','black').html('已输入验证码.');
		}
	});
});


//注册按钮点击事件
function registerSubmit(){
	var email = $('#email').val();
	if(!verify.isEmail(email)){
		$('#showEmailImg').removeClass('CorrectFormtips ErrorFormtips').addClass('ErrorFormtips');
		$('#showEmailMsg').css('color','red').html('请输入正确的email格式, 以便在您忘记用户名或密码的时候找回使用.');
	}else{
		//校验email是否已经存在
		$.ajax({
			url: server.url + 'user/checkEmail',
			type:'POST',
			dataType:'json',
			data:{'email':email},
			success:function(json){
				if(json.notice == 'success'){
					$('#showEmailImg').removeClass('CorrectFormtips ErrorFormtips').addClass('ErrorFormtips');
					$('#showEmailMsg').css('color','red').html('该邮箱已经存在, 请重新输入.');
					$('#email').select();
				}else{
					$('#showEmailImg').removeClass('CorrectFormtips ErrorFormtips').addClass('CorrectFormtips');
					$('#showEmailMsg').css('color','black').html('邮箱通过验证正确.');
					$('#registerForm').submit();
				}
			},
			error:function(){
				$('#showEmailImg').removeClass('CorrectFormtips ErrorFormtips').addClass('ErrorFormtips');
				$('#showEmailMsg').css('color','red').html('验证邮箱发生错误, 请重新刷新页面后重新尝试或者联系管理员.');
			}
		});
		
	}
	
}

//账号信息校验方法
function check(){
	//账号
	var loginName = $('#loginName').val();
	if(!verify.checkname(loginName)){
		$('#showUserNameImg').removeClass('CorrectFormtips ErrorFormtips').addClass('ErrorFormtips');
		$('#showUserNameMsg').css('color','red').html('由5-20位字母、数字或下划线组成.');
		$('#loginName').focus();
		return false;
	}
	//密码
	var passWord = $('#txtPassWord').val();
	if(!verify.checkname(passWord)){
		$('#showPasswordImg').removeClass('CorrectFormtips ErrorFormtips').addClass('ErrorFormtips');
		$('#showPasswordMsg').css('color','red').html('请输入英文字母、数字或下划线，长度5-20个字符.');
		$('#txtPassWord').focus();
		return false;
	}
	//确认密码
	var conpwd = $('#passWordConfirm').val();
	if(conpwd != passWord){
		$('#showPassWordConfirmImg').removeClass('CorrectFormtips ErrorFormtips').addClass('ErrorFormtips');
		$('#showPassWordConfirmMsg').css('color','red').html('确认密码错误.');
		$('#passWordConfirm').focus();
		return false;
	}
	var identity = $('#identity').val();
	//只有个人用户才进行检测真实姓名的操作
	if(identity == 'person'){
		//真实姓名
		var nickName = $('#nickName').val();
		if(verify.isNull(nickName)){
			$('#showNickNameImg').removeClass('CorrectFormtips ErrorFormtips').addClass('ErrorFormtips');
			$('#showNickNameMsg').css('color','red').html('请输入您的真实姓名, 以方便残联就业管理中心进行管理.');
			$('#nickName').focus();
			return false;
		}
	}
	//只有个人用户才进行检测电话号码是否存在
	if(identity == 'person'){
		//电话号码
		var phone = $('#phone').val();
		if(verify.isNull(phone)){
			$('#showPhoneImg').removeClass('CorrectFormtips ErrorFormtips').addClass('ErrorFormtips');
			$('#showPhoneMsg').css('color','red').html('请填写联系方式, 方便残疾人就业指导中心联系你及推送招聘信息用.');
			$('#phone').focus();
			return false;
		}
	}
	//email
	var email = $('#email').val();
	if(!verify.isEmail(email)){
		$('#showEmailImg').removeClass('CorrectFormtips ErrorFormtips').addClass('ErrorFormtips');
		$('#showEmailMsg').css('color','red').html('请输入正确的email格式, 以便在您忘记用户名或密码的时候找回使用.');
		$('#email').focus();
		return false;
	}
	//验证码
	var LoginVerifyCode = $('#LoginVerifyCode').val();
	if(LoginVerifyCode == null || LoginVerifyCode == '' || LoginVerifyCode == undefined){
		$('#showCheckCodeImg').removeClass('CorrectFormtips ErrorFormtips').addClass('ErrorFormtips');
		$('#showCheckCodeMsg').css('color','red').html('请输入验证码.');
		$('#LoginVerifyCode').focus();
		return false;
	}
	return true;
}


