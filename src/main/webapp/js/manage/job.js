/***********************************************
 *******  后台管理页面框架脚本--职位管理  ********
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
	window.location.href = getRootPath() + '/manage/job/job_list?' + paramStr;
}

//  参数下拉框点击事件
function selectButton(valueButton, nameButton, value, name){
	//给对应控件 赋value值
	if (value == 0 || value == "")
		value = 0;
	$('#'+valueButton).val(value);
	//给对应控件 赋name值
	$('#'+nameButton).text(name);
	var effectiveTime = $("#effectiveTime2").val();
	var date = new Date(effectiveTime);
	var fomdate = date.format("yyyy-MM-dd hh:mm:ss");
	$("#effectiveTime3").html(dateOperator(effectiveTime, value, "+"));	
	
	
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
			url:server.url + 'manage/job/refuse/'+objId,
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
		if(!window.confirm('审核通过该职位的同时, 发布该职位的公司也将审核通过, 确定继续吗?')){
			return;
		}
		$.ajax({
			url:server.url + 'manage/job/approve/'+objId,
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
		param.effectiveDays = new Date(param.effectiveDays);
		$.ajax({
			url:server.url + 'manage/job/edit',
			type:'post',
			dataType:'json',
			data:{
				id:param.id,
				name:param.name,
				hireNumber:param.hireNumber,
				salary:param.salary,
				education:param.education,
				experience:param.experience,
				gender:param.gender,
				nature:param.nature,
				workPlace:param.workPlace,
				jobCategory:param.jcCode,
				description:param.description,
				contactPerson:param.contactPerson,
				contactTel:param.contactTel,
				contactEmail:param.contactEmail,
				effectiveDays:param.effectiveTime,
				effectiveTime:param.effectiveDays
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
	//删除
	if(submitType == 'delete'){
		if(!confirm('确定要删除该岗位信息么, 此操作不可恢复, 请谨慎操作.')){
			return;
		}
		$.ajax({
			url:server.url + 'manage/job/delete/'+objId,
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
	var jobName = $('#jobName').val();
	if(jobName == null || jobName == ''){
		$('#jobName').focus();
		return false;
	}
	param.name = jobName;
	var hireNumber = $('#hireNumber').val();
	if(hireNumber == null || hireNumber == ''){
		$('#hireNumber').focus();
		return false;
	}
	param.hireNumber = hireNumber;
	var salary = $('#salary').val();
	if(salary != null || salary != ''){
		param.salary = salary;
	}
	var education = $('#education').val();
	if(education == null || education == ''){
		alert('请选择最低学历.');
		$('#btnEducation').focus();
		return false;
	}
	param.education = education;
	var experience = $('#experience').val();
	if(experience == null || experience == ''){
		alert('请选择工作经验.');
		$('#btnExperience').focus();
		return false;
	}
	param.experience = experience;
	var gender = $('#gender').val();
	if(gender != null || gender != ''){
		param.gender = gender;
	}
	var nature = $('#nature').val();
	if(nature == null || nature == ''){
		alert('请选择岗位性质.');
		$('#btnNature').focus();
		return false;
	}
	param.nature = nature;
	//工作地点
	var workPlace = '';
	var areaValue3 = $('#areaValue3').val();
	var areaValue2 = $('#areaValue2').val();
	var areaValue1 = $('#areaValue1').val();
	if(areaValue3 != null && areaValue3 != ''){
		workPlace = areaValue3;
	}else if(areaValue2 != null && areaValue2 != ''){
		workPlace = areaValue2;
	}else if(areaValue1 != null && areaValue1 != ''){
		workPlace = areaValue1;
	}else{
		alert('工作地点不能为空, 请重新选择.');
		return false;
	}
	param.workPlace = workPlace;
	//工作种类
	var jcCode = '';
	var jcValue3 = $('#jcValue3').val();
	var jcValue2 = $('#jcValue2').val();
	var jcValue1 = $('#jcValue1').val();
	if(jcValue3 != null && jcValue3 != ''){
		jcCode = jcValue3;
	}else if(jcValue2 != null && jcValue2 != ''){
		jcCode = jcValue2;
	}else if(jcValue1 != null && jcValue1 != ''){
		jcCode = jcValue1;
	}else{
		alert('工作种类不能为空, 请重新选择.');
		return false;
	}
	param.jcCode = jcCode;
	var description = $('#description').val();
	if(description != null || description != ''){
		param.description = description;
	}
	var contactPerson = $('#contactPerson').val();
	if(contactPerson == null || contactPerson == ''){
		$('#contactPerson').focus();
		return false;
	}
	param.contactPerson = contactPerson;
	var contactTel = $('#contactTel').val();
	if(contactTel == null || contactTel == ''){
		$('#contactTel').focus();
		return false;
	}
	param.contactTel = contactTel;
	var contactEmail = $('#contactEmail').val();
	if(contactEmail != null || contactEmail != ''){
		param.contactEmail = contactEmail;
	}
	var effectiveTime = $('#effectiveTime').val();
	if(effectiveTime != null || effectiveTime != ''){
		param.effectiveTime = effectiveTime;
	}
	var effectiveDays = $('#effectiveTime2').val();
	if(effectiveDays != null || effectiveDays != ''){
		param.effectiveDays = effectiveDays;
	}
	return param;
}

function dateOperator(date, days, operator) {
	date = date.replace(/-/g, "/"); //更改日期格式
	var nd = new Date(date);
	nd = nd.valueOf();
	if (operator == "+") {
		nd = nd + days * 24 * 60 * 60 * 1000;
	} else if (operator == "-") {
		nd = nd - days * 24 * 60 * 60 * 1000;
	} else {
		return false;
	}
	nd = new Date(nd);
	var y = nd.getFullYear();
	var m = nd.getMonth() + 1;
	var d = nd.getDate();
	var h = nd.getHours();
	var min = nd.getMinutes();
	var s = nd.getSeconds();
	if (m <= 9)
		m = "0" + m;
	if (d <= 9)
		d = "0" + d;
	if (h <= 9)
		h = "0" + h;
	if (min <= 9)
		min = "0" + min;
	if (s <= 9)
		s = "0" + s;
	var cdate = y + "年" + m + "月" + d + "日 " + h + ":" + min + ":" + s;
	return cdate;
}

Date.prototype.format = function(format) {
	var o = {
		"y+" : this.getFullYear(), //year
		"M+" : this.getMonth() + 1, //month 
		"d+" : this.getDate(), //day 
		"h+" : this.getHours(), //hour 
		"m+" : this.getMinutes(), //minute 
		"s+" : this.getSeconds(), //second 
		"q+" : Math.floor((this.getMonth() + 3) / 3), //quarter 
		"S" : this.getMilliseconds()
	//millisecond 
	}

	if (/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	}

	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
					: ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;
}
