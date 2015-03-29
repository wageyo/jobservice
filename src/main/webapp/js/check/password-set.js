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

/***
//验证残疾证号并提交form表单方法
function checkAndSubmit(){
	//校验表单内容
	if(!check()){
		return;
	}
	var nickName = $('#nickName').val();	//真实姓名
	var loginName = $('#loginName').val();	//登陆名/残疾证号
	alert('提交啦!');
	//校验残疾证号和内容是否相符
	$.ajax({
		url : server.url + 'person/checkDisabilityCard/' + loginName + '/' + nickName,
		dataType : 'json',
		type : 'POST',
		async : false,
		success : function(json) {
			//存在时
			if(json.notice == 'success'){
				alert('残疾证号符合, 验证通过！');
			}else{
				alert(json.notice);
			}
		},
	});
}
**/
