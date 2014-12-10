

function check(){
	//账号
	var reg_loginName = /[a-zA-Z0-9_]{5,20}/;
	var loginName = $('#loginName').val();
	if(!reg_loginName.test(loginName)){
		$('#loginNameNotice').html('×');
		$('#loginName').focus();
		return false;
	}
	//密码
	var reg_number = /[a-zA-Z0-9]{5,20}/;
	var passWord = $('#passWord').val();
	if(!reg_number.test(passWord)){
		$('#passWordNotice').html('×');
		$('#passWord').focus();
		return false;
	}
	//两次密码是否一致
	var confirmPassWord = $('#confirmPassWord').val();
	if(confirmPassWord != passWord ){
		$('#confirmPassWordNotice').html('×');
		$('#confirmPassWord').focus();
		return false;
	}
	//电话/手机--暂时去掉所有电话的check项, 只保留非空
	var phone = $('#phone').val();
	if(phone == null || phone == ''){
		$('#phoneNotice').html('×');
		$('#phone').focus();
		return false;
	}
/*	if(!verify.checkTel(phone)){
		$('#phoneNotice').html('×');
		$('#phone').focus();
		return false;
	}	*/
	
	//暂时去掉所有邮箱的check项, 只保留非空
	var email = $('#email').val();
	if(email == null || email == ''){
		$('#email').html('×');
		$('#email').focus();
		return false;
	}
/*	
	if(!verify.isEmail(email)){
		$('#emailNotice').html('请输入正确的邮箱格式,如:tudou@163.com');
		$('#email').focus();
		return false;
	}	*/
	//验证码
	var LoginVerifyCode = $('#LoginVerifyCode').val();
	if(LoginVerifyCode == null || LoginVerifyCode == ''){
		$('#LoginVerifyCodeNotice').html('请填写验证码');
		$('#LoginVerifyCode').focus();
		return false;
	}
	return true;
}

//用户名焦点离开
function blur_loginName(){
	//职位名称
	var reg_loginName = /[a-zA-Z0-9_]{6,16}/;
	var loginName = $('#loginName').val();
	if(!reg_loginName.test(loginName)){
		$('#loginNameNotice').html('×');
		return false;
	}
	$.ajax({
		url : server.url + 'user/check/' + loginName,
		dataType : 'json',
		type : 'GET',
		async : false,
		success : function(json) {
			//存在时
			if(json.notice == 'success'){
				$('#loginNameNotice').html('√');
				//选中时
				$('#btn-submit').removeClass('gray').addClass('blue').removeAttr('disabled');
				return false;
			}
			if(json.notice == 'failure'){
				$('#loginNameNotice').html('该用户名已经存在, 请重新填写一个.');
				//按钮变灰
				$('#btn-submit').removeClass('blue').addClass('gray').attr('disabled','disabled');
				return false;
			}
		},
	});
}

//用户名获得焦点
function focus_loginName(){
	$('#loginNameNotice').html('用户名只能为字母, 数字和下划线_, 长度为5-20位.');
}

//密码焦点获得
function focus_passWord(){
	$('#passWordNotice').html('密码只能为字母, 数字, 长度为5-20位');
}

//密码焦点离开
function blur_passWord(){
	var passWord = $('#passWord').val();
	if(verify.isNumberOr_Letter(passWord)){
		$('#passWordNotice').html('√');
	}else{
		$('#passWordNotice').html('×');
	}
}

//确认密码密码焦点离开
function blur_confirmPassWord(){
	var passWord = $('#passWord').val();
	var confirmPassWord = $('#confirmPassWord').val();
	if(confirmPassWord == null || confirmPassWord == ''){
		$('#confirmPassWordNotice').html('×');
		return false;
	}
	if(passWord == confirmPassWord){
		$('#confirmPassWordNotice').html('√');
	}else{
		$('#confirmPassWordNotice').html('×');
	}
}

//联系电话焦点获得
function focus_phone(){
	$('#phoneNotice').html('联系方式为手机或座机');
}

//联系电话焦点离开
function blur_phone(){
	var phone = $('#phone').val();
	if(phone == null || phone == ''){
		$('#phoneNotice').html('×');
	}else{
		$('#phoneNotice').html('√');
	}
}

//邮箱焦点获得
function focus_email(){
	$('#emailNotice').html('请输入正确的邮箱格式,如:tudou@163.com');
}

//邮箱焦点离开
function blur_email(){
	var email = $('#email').val();
	if(verify.isEmail(email)){
		$('#emailNotice').html('√');
	}else{
		$('#emailNotice').html('×');
	}
}

//验证码焦点获得
function focus_email(){
	$('#LoginVerifyCodeNotice').html('请填写验证码');
}

//验证码焦点离开
function blur_email(){
	var LoginVerifyCode = $('#LoginVerifyCode').val();
	if(LoginVerifyCode == null || LoginVerifyCode == ''){
		$('#LoginVerifyCode').html('×');
	}else{
		$('#LoginVerifyCode').html('√');
	}
}


//复选框来控制提交按钮是否可用
function ckb_agree(){
	var agree = $('#ckb-agree').is(':checked');
	if(agree){
		//选中时
		$('#btn-submit').removeClass('gray').addClass('blue').removeAttr('disabled');
	}else{
		//没选中时
		//按钮变灰
		$('#btn-submit').removeClass('blue').addClass('gray').attr('disabled','disabled');
	}
}