/***********************************************
 ******  后台管理页面框架脚本--审核开关管理  *****
 ***********************************************/


$(document).ready(function(){
	
});

//预览 方法
function preview(switchid,switchStatus){
	var prefix = $('#prefix').val();
	var middlecontent = $('#middlecontent').html(); 
	var suffix = $('#suffix').val();
	
	var example = '';
	if(prefix != null && prefix != '' && prefix != undefined){
		example += prefix + '<br/>';
	}
	example += middlecontent + '<br/>';
	if(suffix != null && suffix != '' && suffix != undefined){
		example += suffix;
	}
	$('.smsfix-example').html(example);
	
}

//保存上下文
function savesmsfix(acode){
	var prefix = $('#prefix').val();
	var suffix = $('#suffix').val();
	$.ajax({
		url:'/jobservice/manage/prefix/update',
		type:'post',
		data:{'area.code':acode,'prefix':prefix,'suffix':suffix},
		dataType:'json',
		success:function(data){
			if(data.notice == 'success'){
				alert('设置成功!');
				//给对应控件 赋name值
			}else{
				alert(data.notice);
			}
		}
	});
}
