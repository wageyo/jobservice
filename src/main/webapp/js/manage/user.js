/***********************************************
 *******  后台管理页面框架脚本--账号/用户管理  ********
 ***********************************************/

//收集参数并进入后台查询方法
function query(page,checkStatus){
	var paramStr = '';	//查询字符串
	var totalPage = $('#totalPage').val();
	if(page == null || page == undefined || page <= 1 || page == ''){
		paramStr += 'page=1'; 
	}else if(page >= totalPage){
		paramStr += 'page=' + totalPage;
	}else{
		paramStr += 'page=' + page;
	}
	var targetName = $('#targetName').val();
	if(targetName != null && targetName != undefined && targetName != ''){
		paramStr += '&targetName=' + targetName; 
	}
	if(checkStatus == null || checkStatus == undefined || checkStatus == ''){
		var checkStatus = $('#checkStatus').val();
		paramStr += '&checkStatus=' + checkStatus; 
	}else{
		paramStr += '&checkStatus=' + checkStatus; 
	}
	// 跳转提交
	window.location.href = getRootPath() + '/manage/user/user_list?' + paramStr;
}
//参数下拉框点击事件
function selectButton(valueButton, nameButton, value, name){
	//给对应控件 赋value值
	$('#'+valueButton).val(value);
	//给对应控件 赋name值
	$('#'+nameButton).text(name);
}

//保存, 通过, 拒绝, 返回  综合方法
function updateEntity(submitType,objId){
	//返回
	if(submitType == 'return'){
		window.history.back();
		return;
	}
	//拒绝
	if(submitType == 'refuse'){
		$.ajax({
			url:server.url + 'manage/user/refuse/'+objId,
			type:'post',
			dataType:'json',
			success:function(data){
				if(data.notice == 'success'){
					alert('操作成功!');
					//重新载入页面--以刷新
					window.location.reload();
					return;
				}else{
					alert(data.notice);
				}
			}
		});
	}
	//通过
	if(submitType == 'approve'){
		$.ajax({
			url:server.url + 'manage/user/approve/'+objId,
			type:'post',
			dataType:'json',
			success:function(data){
				if(data.notice == 'success'){
					alert('操作成功!');
					//重新载入页面--以刷新
					window.location.reload();
					return;
				}else{
					alert(data.notice);
				}
			}
		});
	}
	//保存
	if(submitType == 'save'){
		var param = checkObject();
		if(!param){
			return false;
		}
		$.ajax({
			url:server.url + 'manage/user/edit',
			type:'post',
			dataType:'json',
			data:{
				'id':param.id,
				'loginName':param.loginName,
				'passWord':param.passWord,
				'email':param.email,
				'phone':param.phone,
				'nickName':param.nickName,
				'area.code':param.area
			},
			success:function(data){
				if(data.notice == 'success'){
					alert('保存成功!');
				}else{
					alert(data.notice);
				}
			}
		});
		
	}
	return;
}

//校验提交表单中所有控件方法
function checkObject(){
	var param  = new Object();
	var objId = $('#objId').val();
	if(objId == null || objId == ''){
		alert('传递的参数有误, 请刷新页面重新尝试.');
		return false;
	}
	param.id = objId;
	var loginName = $('#loginName').val();
	if(loginName == null || loginName == ''){
		$('#loginName').focus();
		return false;
	}
	param.loginName = loginName;
	var passWord = $('#passWord').val();
	if(passWord == null || passWord == ''){
		$('#passWord').focus();
		return false;
	}
	param.passWord = passWord;
	var email = $('#email').val();
	if(email == null || email == ''){
		$('#email').focus();
		return false;
	}
	param.email = email;
	var phone = $('#phone').val();
	if(phone == null || phone == ''){
		$('#phone').focus();
		return false;
	}
	param.phone = phone;
	var nickName = $('#nickName').val();
	if(nickName != null && nickName != ''){
		param.nickName = nickName;
	}
	// 账号所属地区
	var area = '';
	var areaValue3 = $('#areaValue3').val();
	var areaValue2 = $('#areaValue2').val();
	var areaValue1 = $('#areaValue1').val();
	if(areaValue3 != null && areaValue3 != ''){
		area = areaValue3;
	}else if(areaValue2 != null && areaValue2 != ''){
		area = areaValue2;
	}else if(areaValue1 != null && areaValue1 != ''){
		area = areaValue1;
	}else{
		alert('工作地点不能为空, 请重新选择.');
		return false;
	}
	param.area = area;
	return param;
}

$(document).ready(function(){
	//定义绑定上传按钮事件
	var button = $('#picFileImport');
	/*
	 * 异步 上传图片方法函数
	 */
	new AjaxUpload(button, {
		action: server.url + 'user/uploadPic',
		name: 'pic',// 更改上传的文件名
		autoSubmit:true,
		type:'POST',
		data: {},
		onSubmit : function(file , ext){
			button.val('图片上传...');
			/**
			 *	①验证上传文件格式
			 **/
			
			if(!(ext && /^(jpg|jpeg|png)$/.test(ext))){
				alert('您上传的文件格式不对, 请重新选择');
				$('#picfileTitle').val('');
				button.val('上传图片');
				return false;
			}
			/**
			 *	②设置上传参数
			 **/
			this.setData({
				'userid':$('#objId').val()
			});
		},
		onComplete : function(file,response){
			if(response != 'success'){
				alert('上传图片失败,'+response);
			}else{
				//刷新新上传的图片
				$('#headImage').attr('src','');
				//读取被编辑用户的id
				var userid = $('#objId').val();
				$('#headImage').attr('src',server.url + 'user/downloadPic/' + userid);
			}
			button.val('上传图片');
			this.enable();
		}
	});
});