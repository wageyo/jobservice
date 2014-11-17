/***********************************************
 *******  后台管理页面框架脚本--管理员用户管理  ********
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
	//	var checkStatus = $('#checkStatus').val();
		checkStatus = 'ok';	//管理员账号 同意写死为 ok 状态
		paramStr += '&checkStatus=' + checkStatus; 
	}else{
		paramStr += '&checkStatus=' + checkStatus; 
	}
	// 跳转提交
	window.location.href = getRootPath() + '/manage/admin/admin_list?' + paramStr;
}

// 新增, 修改, 返回  综合方法
function updateEntity(submitType,objId){
	//返回
	if(submitType == 'return'){
		window.history.back();
		return;
	}
	// 新增
	if(submitType == 'add'){
		var param = checkObject();
		if(!param){
			return false;
		}
		$.ajax({
			url:server.url + 'manage/admin/add',
			type:'post',
			dataType:'json',
			data:{
				'loginName':param.loginName,
				'passWord':param.passWord,
				'nickName':param.nickName,
				'title':param.title,
				'area.code':param.area
			},
			success:function(data){
				if(data.notice == 'success'){
					alert('保存成功!');
					query(1,null);
				}else{
					alert(data.notice);
				}
			}
		});
	}
	// 删除
	if(submitType == 'delete'){
		var confirm = window.confirm('此操作不可恢复, 确认删除吗?');
		if(!confirm){
			return;
		}
		$.ajax({
			url:server.url + 'manage/admin/delete/' + objId,
			type:'post',
			dataType:'json',
			data:{},
			success:function(data){
				if(data.notice == 'success'){
					alert('删除成功!');
					window.location.reload();
				}else{
					alert(data.notice);
				}
			}
		});
	}
	//更新
	if(submitType == 'update'){
		var param = checkObject();
		if(!param){
			return false;
		}
		$.ajax({
			url:server.url + 'manage/admin/edit',
			type:'post',
			dataType:'json',
			data:{
				'id':objId,
				'loginName':param.loginName,
				'passWord':param.passWord,
				'nickName':param.nickName,
				'title':param.title,
				'area.code':param.area
			},
			success:function(data){
				if(data.notice == 'success'){
					alert('更新成功!');
					query(1,null);
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
	var nickName = $('#nickName').val();
	if(nickName != null && nickName != ''){
		param.nickName = nickName;
	}
	var title = $('#title').val();
	if(title == null || title == ''){
		$('#title').focus();
		return false;
	}
	param.title = title;
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