/***********************************************
 ***************  短信发送页面脚本 **************
 ***********************************************/
$(document).ready(function(){

	//初始化电话簿号码列表
	initPhoneList();
	
	//控件赋值, focus和blur事件
	$('#shortMessage').css({'color':'rgb(168, 168, 168)'}).focus(function(){
		var val = $(this).attr('title')
		var currentVal = $(this).val();
		$(this).css('color','rgb(0, 0, 0)');
		if(currentVal == val){
			$(this).val('');
		}
	}).blur(function(){
		var val = $(this).attr('title')
		var currentVal = $(this).val();
		if(currentVal == val || currentVal == '' || currentVal == null || currentVal == undefined){
			$(this).val(val).css('color','rgb(168, 168, 168)');
		}
	}).keyup(function(){
		var alreadyInput = $(this).val().length; //已录入数字数量
		var leftInput = 300 - alreadyInput;//还可以再录入数量
		if(leftInput <= 0){
			leftInput = 0;
		}
		$('#notice').html('还可以输入' + leftInput +'字...');
		if(alreadyInput > 300){
			$(this).val($(this).val().substring(0,300));
		}
	});
});

//初始化电话簿号码列表
function initPhoneList(){
	$.ajax({
		url:server.url + 'manage/sms/getPhoneList',
		type:'post',
		dataType:'json',
		data:{},
		success:function(data){
			if(data.notice == 'success'){
				var content = '';
				$.each(data.phoneList,function(index,item){
					content += '<li phone="' + item.phone + '" name="' + item.name + '" onclick="toTargetDiv(this);">';
					if(item.name != null && item.name != ''){
						content += item.name;
					}else{
						content += item.phone;
					}
					content  += '</li>';
				});
//				alert(content);
				$('.communicate-div .communicate-ul').append(content); 
			}else{
				alert(data.notice);
			}
		}
	});
}
//右边通讯录单个li 鼠标移入,移出, 点击事件
function toTargetDiv(obj){
	var content = '<li phone="'+$(obj).attr('phone')+'" name="'+$(obj).attr('name')+'" onclick="toCommunication(this);">';
	content += $(obj).html();
	content  += '</li>';
	$('.target-div .target-ul').append(content); 
	$(obj).remove();
	//更新上面的计数
	sendNumbers();
}

//左边通讯录单个li 鼠标移入,移出, 点击事件
function toCommunication(obj){
	var content = '<li phone="'+$(obj).attr('phone')+'" name="'+$(obj).attr('name')+'" onclick="toTargetDiv(this);">';
	content += $(obj).html();
	content  += '</li>';
	$('.communicate-div .communicate-ul').append(content); 
	$(obj).remove();
	//更新上面的计数
	sendNumbers();
}

//动态改变上面发送给多少个号码的提示文字
function sendNumbers(){
	var count = $('.target-div .target-ul li').length;
	$('.send-area-top-txt').html(count + '个联系人.');
}

//检查电话号码在此阵列中存在几个
function chenNumbersExists(phone){
	var count = 0;
	$('.phone-ul .phone-li .phone-input-cellnumber').each(function(){
		var itemVal = $(this).val();
		if(itemVal == phone){
			count ++;
		}
	});
	return count;
}

//弹出 遮罩窗体 保存按钮事件
function editContactPerson(){
	//取得遮罩窗体中的 联系人电话和姓名,
	var thisPhone = $('#zhezhao-phone').val();
	var thisName = $('#zhezhao-name').val();
//	alert(thisPhone + ' : ' + thisName);
	//在阵列中查找电话相同的input框, 然后将姓名放到对应的框框内
	$('.phone-ul .phone-li .phone-input-cellnumber').each(function(){
		var currentPhone = $(this).val();
		if(thisPhone == currentPhone){
//			alert('有相等的啊~');
			//名字不为空则放名字, 同时电话隐藏, 名字显示
			if(thisName != '' && thisName != null){
				$(this).hide().next().show().val(thisName);
			//名字为空则  电话显示, 名字隐藏
			}else{
				$(this).show().next().hide().val('');
			}
		}
	});
}

// 发送
function send(){
	//收集所有的通过验证的电话号码
	var phoneList = new Array();	//号码数组×
	var nameList = new Array();		//姓名数组
	//只提取 右边目标栏的电话号码
	var count = $('.target-div .target-ul li').length;
	if(count <= 0){
		alert('你还没有添加任何电话号码, 请添加后再发送.');
		return;
	}
	$('.target-div .target-ul li').each(function(){
		var phone = $(this).attr('phone');
		var name = $(this).attr('name');
		phoneList.push(phone);
		nameList.push(name);
	});
	//异步将姓名，电话号码，短信内容发送到后台
	var shortMessage = $('#shortMessage').val();
	if(shortMessage == '' || shortMessage == null || shortMessage == undefined || shortMessage == $('#shortMessage').attr('title')){
		alert('短信内容不能为空, 请编辑短信.');
		$('#shortMessage').focus();
		return;
	}
	
	$.ajax({
		url:server.url + 'manage/sms/send',
		type:'post',
		dataType:'json',
		data:{
			'phoneList':phoneList,
			'nameList':nameList,
			'shortMessage':shortMessage
		},
		traditional: true,
		success:function(data){
			if(data.notice == 'success'){
				alert('发送成功!');
			}else{
				alert(data.notice);
			}
		}
	});
}

//全部添加addAll
function addAll(){
	var content = '';
	$('.communicate-div .communicate-ul li').each(function(){
		content += '<li phone="'+$(this).attr('phone')+'" name="'+$(this).attr('name')+'" onclick="toCommunication(this);">';
		content += $(this).html();
		content += '</li>';
		
	}).remove();
	$('.target-div .target-ul').append(content); 
	//更新上面的计数
	sendNumbers();
}

//移出全部
function removeAll(){
	var content = '';
	$('.target-div .target-ul li').each(function(){
		content += '<li phone="'+$(this).attr('phone')+'" name="'+$(this).attr('name')+'" onclick="toTargetDiv(this);">';
		content += $(this).html();
		content += '</li>';
	}).remove();
	$('.communicate-div .communicate-ul').append(content); 
	//更新上面的计数
	sendNumbers();
}



// 异步删除通讯录中的电话, obj为当前点击的对象xxxxxxxxxx
function deleteSmsPhone(phone,obj){
	if(phone == null || phone == ''){
		alert('没有选中任何号码, 请刷新页面后重新尝试或联系管理员.');
		return;
	}
	if(!window.confirm('确实要删除号码: ' + phone + ' 吗?')){
		return;
	}
	$.ajax({
		url:server.url + 'manage/sms/delete',
		type:'post',
		dataType:'json',
		data:{
			'phone':phone
		},
		traditional: true,	//加上该属性后, 后台可以正常的取得数组的值
		success:function(data){
			// 删除成功, 则将对应的li隐藏
			if(data.notice == 'success'){
				
			}else{
				alert(data.notice);
			}
		}
	});
}
