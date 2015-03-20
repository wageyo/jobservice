/***********************************************
 ******  后台管理页面框架脚本--审核开关管理  *****
 ***********************************************/


$(document).ready(function(){
	//初始化所有bootstrap开关
	$("[name='switchAudit']").bootstrapSwitch();
});

//异步更新 审核开关状态方法
function updateSwitch(switchid,switchStatus){
	if(switchStatus){
		switchStatus = 'on';
	}else{
		switchStatus = 'off';
	}
	$.ajax({
		url:'/jobservice/manage/setup/update_switch',
		type:'post',
		data:{'switchid':switchid,'switchStatus':switchStatus},
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

//异步更新信息分享范围
function updateShareScope(id,shareScopeValue,name){
	if(shareScopeValue == 'district'){
		if(!confirm('只显示县区级时信息可能过少, 建议选择所在市或省, 继续吗?')){
			return;
		}
	}
	$.ajax({
		url:'/jobservice/manage/setup/update_share_scope',
		type:'post',
		data:{'id':id,'shareScopeValue':shareScopeValue,'shareScopeName':name},
		dataType:'json',
		success:function(data){
			if(data.notice == 'success'){
				alert('设置成功!');
				//给对应控件 赋name值
				$('#btnShareScope').text(name);
			}else{
				alert(data.notice);
			}
		}
	});
}

//异步推送信息显示/发送条数
function updateTuiSong(id,sendValue,name, type,btnName){
	if(sendValue > 10 && type == 'matchedSendNumber'){
		if(!confirm('短信推送10条以上数据可能会超出最大字符数错误, 仍要继续么?')){
			return;
		}
	}
	$.ajax({
		url:'/jobservice/manage/setup/update_matched',
		type:'post',
		data:{'id':id,'matchedValue':sendValue,'matchedName':name},
		dataType:'json',
		success:function(data){
			if(data.notice == 'success'){
				alert('设置成功!');
				//给对应控件 赋name值
				$('#' + btnName).text(name);
			}else{
				alert(data.notice);
			}
		}
	});
}
