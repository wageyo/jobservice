/***********************************************
 *******  后台管理页面框架脚本--审核开关管理  ********
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

