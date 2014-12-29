/*********************************************/
/************ 找回用户名/密码js脚本 ***********/
/*********************************************/

//向邮箱发送用户名/密码功能
function setGetBack(type,email){
	
	//异步提交发送邮箱的方法
	$.ajax({
		url: server.url + 'getBack',
		type:'POST',
		dataType:'json',
		data:{'email':email,'type':type},
		success:function(json){
			if(json.notice == 'success'){
				var notice = '';
				if(type == 'username'){
					notice += '用户名';
				}else{
					notice = '密码';
				}
				notice += '已经发送到您注册的邮箱中, 请到邮箱中查看; 如果收件箱中没有的话, 请查看邮箱的垃圾箱中';
				$('.buttonGetBack').val('发送完成!');
				$('.buttonGetBack').removeAttr('disabled')
				alert(notice);
				$('.buttonGetBack').val('发送');
			}else{
				alert(json.notice);
			}
		},
		error:function(){
			alert('操作失败!');
		}
	});
}


function getBack(type){
	if(type == null || type == '' || type==undefined){
		alert('传递的发送类型参数为空, 请联系管理员.');
		return;
	}
	//校验email
	var email = $('#email').val();
	if(!verify.isEmail(email)){
		alert('您输入的邮箱不符合规则, 请重新输入.');
		$('#email').focus();
		return false;
	}else{
		$.ajax({
			url: server.url + 'user/checkEmail',
			type:'POST',
			dataType:'json',
			data:{'email':email},
			success:function(json){
				if(json.notice == 'success'){
					$('.buttonGetBack').val('发送中...请暂时不要操作.');
					$('.buttonGetBack').attr('disabled',true)
					setGetBack(type,email);
					return true;
				}else{
					alert('该邮箱不存在.');
					$('.buttonGetBack').removeAttr('disabled')
					return false;
				}
			},
			error:function(){
				alert('操作失败!');
				return false;
			}
		});
	}
}

