/***********************************************
 *******  后台管理页面框架脚本--白名单管理  ********
 ***********************************************/
$(document).ready(function(){
	//初始化所有bootstrap开关
	if($("[name='switchAudit']").length == 1){
		$("[name='switchAudit']").bootstrapSwitch();
	}
	
	$('#whitelistcheck').on('switch-change', function (e, data) {
	    var $el = $(data.el)
	      , value = data.value;
	    alert(e + $el + value);
	});
});
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
	// 跳转提交
	window.location.href = getRootPath() + '/manage/white/white_list?' + paramStr;
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
			url:server.url + 'manage/white/add',
			type:'post',
			dataType:'json',
			data:{
				'title':param.title,
				'domainName':param.domainName,
				'remark':param.remark
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
			url:server.url + 'manage/white/delete/' + objId,
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
			url:server.url + 'manage/white/edit',
			type:'post',
			dataType:'json',
			data:{
				'id':objId,
				'title':param.title,
				'domainName':param.domainName,
				'remark':param.remark
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
	var title = $('#title').val();
	if(title == null || title == ''){
		$('#title').focus();
		return false;
	}
	param.title = title;
	var domainName = $('#domainName').val();
	if(domainName == null || domainName == ''){
		$('#domainName').focus();
		return false;
	}
	param.domainName = domainName;
	var remark = $('#remark').val();
	if(remark != null && remark != ''){
		param.remark = remark;
	}
	return param;
}

//异步更新 白名单开关是否开启方法
function updateSwitch(switchid,switchStatus){
	if(switchStatus){
		switchStatus = 'on';
	}else{
		switchStatus = 'off';
	}
	$.ajax({
		url:'/jobservice/manage/white/update_switch',
		type:'post',
		data:{'id':switchid,'value':switchStatus},
		dataType:'json',
		success:function(data){
			if(data.notice == 'success'){
			//	alert('成功!');
			}else{
				alert(data.notice);
			}
		}
	});
}