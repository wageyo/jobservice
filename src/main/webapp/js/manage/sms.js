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
	
	//定义绑定上传按钮事件
	var button = $('#picFileImport');

	if(button){
		/*
		 * 异步 上传图片方法函数
		 */
		new AjaxUpload(button, {
			action: server.url + 'manage/sms/uploadexcel',
			name: 'excel',// 更改上传的文件名
			autoSubmit:true,
			type:'POST',
			data: {},
			onSubmit : function(file , ext){
				button.val('上传中...');
				/**
				 *	①验证上传文件格式
				 **/
		//		alert(ext + "  " + (/^(xls|xlsx)$/.test(ext)));
				if(!(/^(xls|xlsx)$/.test(ext))){
					alert('您上传的文件格式不对, 或者不是excel文件, 请重新选择.');
				//	$('#picfileTitle').val('');
					button.val('上传文件');
					return false;
				}
				/**
				 *	②设置上传参数
				 **/
			/**	this.setData({
					'imageId':$('#imageId').val()
				});		*/
				
			},
			onComplete : function(file,response){
				var result = response.substring(0,7);
				if(result == 'success'){
					// 显示总数, 正确数, 错误数
					var total = response.substring(8,response.indexOf('@'));
					var right = response.substring(response.indexOf('@')+1,response.indexOf('*'));
					var wrong = response.substring(response.indexOf('*')+1,response.indexOf('successEnd'));
					var msg = '操作成功, 总计有电话号码: ' + total + '个, 其中正确并导入: ' + right + '个, 错误号码: ' + wrong +'个.';
					if(wrong != null && wrong > 0){
						msg += '点击下载按钮来下载错误列表来查看错误原因.';
						alert(msg);
						var downLoadurl = response.substring(response.indexOf('http:'));
				//		$('#downLoadWrongList').attr('href',downLoadurl).;
						$('#downLoadWrongList').css({'color':'rgb(30, 24, 255)'}).bind('click',function(){
							window.open(downLoadurl);
						});
					}else{
						alert(msg);
					}
					initPhoneList();
				}else{
					alert('上传文件失败, 错误原因:'+response);
				}
				button.val('上传号码');
				this.enable();
			}
		});
	}
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
					content += '<li>';
					content += '<span phone="' + item.phone + '" name="' + item.name + '" style="width:110px;"  onclick="toTargetDiv(this);">';
					if(item.name != null && item.name != ''){
						content += item.name;
					}else{
						content += item.phone;
					}
					content += '</span>'
					content  += '<span style="width:20px;text-align: center;font-size: 16px;color: rgb(166, 166, 166);" onmouseover="mouseoverPhone(this);" onmouseout="mouseoutPhone(this)" onclick="deletePhone(\'' + item.phone + '\',this)">×</span>';
					content  += '</li>';
				});
//				alert(content);
				//先清空, 在添加
				$('.communicate-div .communicate-ul').empty().append(content); 
			}else{
				alert(data.notice);
			}
		}
	});
}

//通讯录删除按钮 鼠标 悬浮事件
function mouseoverPhone(obj){
	$(obj).css({'font-weight':'bold','font-size':'18px','color': 'rgb(0, 0, 0)'});
}
//通讯录删除按钮 鼠标 离开事件
function mouseoutPhone(obj){
	$(obj).css({'font-weight':'normal','font-size':'16px','color': 'rgb(166, 166, 166)'});
}
//异步删除通讯录中的电话, obj为当前点击的对象xxxxxxxxxx
function deletePhone(phone,obj){
	if(phone == null || phone == ''){
		alert('没有选中任何号码, 请刷新页面后重新尝试或联系管理员.');
		return;
	}
/*	if(!window.confirm('确实要删除号码: ' + phone + ' 吗?')){
		return;
	}	*/
	$.ajax({
		url:server.url + 'manage/sms/delete/' + phone,
		type:'post',
		dataType:'json',
		traditional: true,	//加上该属性后, 后台可以正常的取得数组的值
		success:function(data){
			// 删除成功, 则将对应的li剔除掉
			if(data.notice == 'success'){
				if(obj != null && obj != undefined){
					$(obj).parent().remove();
				}else{
					//此时则为全部删除的情况, 清空通讯录里的电话号码
					$('.communicate-div .communicate-ul').html('');
				}
			}else{
				alert(data.notice);
			}
		}
	});
}

//右边通讯录单个li  点击事件
function toTargetDiv(obj){
	var content = '<li phone="'+$(obj).attr('phone')+'" name="'+$(obj).attr('name')+'" onclick="toCommunication(this);">';
	content += $(obj).html();
	content  += '</li>';
	$('.target-div .target-ul').append(content); 
	$(obj).parent().remove();
	//更新上面的计数
	sendNumbers();
}

//左边通讯录单个li  点击事件
function toCommunication(obj){
	var name = $(obj).attr('name');
	var phone = $(obj).attr('phone');
	var content = '<li >';
	content += '<span phone="'+phone+'" name="'+ name +'" onclick="toTargetDiv(this);" style="width:110px;"  onclick="toTargetDiv(this);">';
	if(name != null && name != ''){
		content += name;
	}else{
		content += phone;
	}
	content += '</span>'
	content  += '<span style="width:20px;text-align: center;font-size: 16px;color: rgb(166, 166, 166);" onmouseover="mouseoverPhone(this);" onmouseout="mouseoutPhone(this)" onclick="deletePhone(\'' + phone + '\',this)">×</span>';
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

//全部添加到目标号码中
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

//全部移动到电话簿中
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



