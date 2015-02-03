/*********************************************/
/************ 设置初始密码js脚本 **************/
/*********************************************/

//检查两次密码
function check(){
	//真实姓名
	var nickName = $('#nickName').val();
	if(verify.isNull(nickName)){
		alert('请输入您的真实姓名, 以方便残联就业管理中心进行管理.');
		$('#nickName').focus();
		return false;
	}
	
	//email
	var email = $('#email').val();
	if(!verify.isEmail(email)){
		alert('请输入正确的email格式, 以便在您忘记用户名或密码的时候找回使用.');
		$('#email').focus();
		return false;
	}
	
	//密码
	var reg_pwd = /[a-zA-Z0-9]{6,12}/;
	var newPassWord = $('#newPassWord').val();
	if(!reg_pwd.test(newPassWord)){
		alert('密码格式不正确,请重新填写!');
		$('#newPassWord').focus();
		return false;
	}
	
	//两次密码是否一致
	var conPassWord = $('#conPassWord').val();
	if(conPassWord != newPassWord ){
		alert('两次输入的密码不一致!');
		$('#conPassWord').focus();
		return false;
	}
	return true;
}