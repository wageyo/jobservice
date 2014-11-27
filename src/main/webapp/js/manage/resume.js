/***********************************************
 *******  后台管理页面框架脚本--简历管理  ********
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
	window.location.href = getRootPath() + '/manage/resume/resume_list?' + paramStr;
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
			url:server.url + 'manage/resume/refuse/'+objId,
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
			url:server.url + 'manage/resume/approve/'+objId,
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
			url:server.url + 'manage/resume/edit',
			type:'post',
			dataType:'json',
			data:{
				'id':param.id,
				'title':param.title,
				'name':param.name,
				'gender':param.gender,
				'race':param.race,
				'hukouStatus':param.hukouStatus,
				'birth':param.birth,
				'hukou.code':param.hukou,
				'hukouAddress':param.hukouAddress,
				'address':param.address,
				'marriage':param.marriage,
				'phone':param.phone,
				'qq':param.qq,
				'email':param.email,
				'disabilityCard':param.disabilityCard,
				'disabilityCategory':param.disabilityCategory,
				'disabilityLevel':param.disabilityLevel,
				'disabilityPart':param.disabilityPart,
				'shiYeHao':param.shiYeHao,
				'jobNature':param.jobNature,
				'desireSalary':param.desireSalary,
				'desireJob.code':param.desireJob,
				'desireAddress.code':param.desireAddress,
				'provideOther':param.provideOther,
				'state':param.state,
				'education':param.education,
				'major':param.major,
				'school':param.school,
				'experts':param.experts,
				'training':param.training,
				'selfEvaluation':param.selfEvaluation,
				'experience':param.experience,
				'careerTest':param.careerTest,
				'processState':param.processState
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
		if(!confirm('确定要删除该简历信息么, 此操作不可恢复, 请谨慎操作.')){
			return;
		}
		$.ajax({
			url:server.url + 'manage/resume/delete/'+objId,
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

//校验 简历 提交表单中所有控件方法
function checkObject(){
	var param  = new Object();
	var objId = $('#objId').val();
	if(objId == null || objId == ''){
		alert('传递的参数有误, 请刷新页面重新尝试.');
		return false;
	}
	param.id = objId;
	/**
	 * 简历基本信息
	 */
	var title = $('#title').val();
	if(title == null || title == ''){
		$('#title').focus();
		return false;
	}
	param.title = title;
	var resumeName = $('#resumeName').val();
	if(resumeName == null || resumeName == ''){
		$('#resumeName').focus();
		return false;
	}
	param.name = resumeName;
	var gender = $('#gender').val();
	if(gender != null && gender != ''){
		param.gender = gender;
	}
	var race = $('#race').val();
	if(race != null && race != ''){
		param.race = race;
	}
	var hukouStatus = $('#hukouStatus').val();
	if(hukouStatus != null && hukouStatus != ''){
		param.hukouStatus = hukouStatus;
	}
	var year = $('#year').val();
	if(year == null || year == ''){
		$('#year').focus();
		alert('请选择出生年份.');
		return false;
	}
	var month = $('#month').val();
	if(month == null || month == ''){
		$('#month').focus();
		alert('请选择出生月份.');
		return false;
	}
	var day = $('#day').val();
	if(day == null || day == ''){
		$('#day').focus();
		alert('请选择出生日期.');
		return false;
	}
	param.birth = year + '-' + month + '-' + day;
	//户口所在地
	var hukou = '';
	var areaValue3 = $('#areaValue3').val();
	var areaValue2 = $('#areaValue2').val();
	var areaValue1 = $('#areaValue1').val();
	if(areaValue3 != null && areaValue3 != ''){
		hukou = areaValue3;
	}else if(areaValue2 != null && areaValue2 != ''){
		hukou = areaValue2;
	}else if(areaValue1 != null && areaValue1 != ''){
		hukou = areaValue1;
	}else{
		alert('户口所在地不能为空.');
		return false;
	}
	param.hukou = hukou;
	//户口地址
	var hukouAddress = $('#hukouAddress').val();
	if(hukouAddress != null && hukouAddress != ''){
		param.hukouAddress = hukouAddress;
	}
	//现居住地
	var address = $('#address').val();
	if(address != null && address != ''){
		param.address = address;
	}
	//婚姻状况
	var marriage = $('#marriage').val();
	if(marriage != null && marriage != ''){
		param.marriage = marriage;
	}
	
	/**
	 * 联系方式
	 */
	//电话/手机
	var phone = $('#phone').val();
	if(phone != null && phone != ''){
		param.phone = phone;
	}
	//qq
	var qq = $('#qq').val();
	if(qq != null && qq != ''){
		param.qq = qq;
	}
	//邮箱
	var email = $('#email').val();
	if(email != null && email != ''){
		param.email = email;
	}
	
	/**
	 * 残疾情况
	 */
	//残疾证号
	var disabilityCard = $('#disabilityCard').val();
	if(disabilityCard != null && disabilityCard != ''){
		param.disabilityCard = disabilityCard;
	}
	//残疾类别
	var disabilityCategory = $('#disabilityCategory').val();
	if(disabilityCategory != null && disabilityCategory != ''){
		param.disabilityCategory = disabilityCategory;
	}
	//残疾等级
	var disabilityLevel = $('#disabilityLevel').val();
	if(disabilityLevel != null && disabilityLevel != ''){
		param.disabilityLevel = disabilityLevel;
	}
	//残疾部位
	var disabilityPart = $('#disabilityPart').val();
	if(disabilityPart != null && disabilityPart != ''){
		param.disabilityPart = disabilityPart;
	}
	
	/**
	 * 求职意向
	 */
	//失业证号
	var shiYeHao = $('#shiYeHao').val();
	if(shiYeHao != null && shiYeHao != ''){
		param.shiYeHao = shiYeHao;
	}
	//职位类型
	var jobNature = $('#jobNature').val();
	if(jobNature != null && jobNature != ''){
		param.jobNature = jobNature;
	}
	//期望薪水
	var desireSalary = $('#desireSalary').val();
	if(desireSalary != null && desireSalary != ''){
		param.desireSalary = desireSalary;
	}
	//期望职位类别
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
		alert('期望职位不能为空.');
		return false;
	}
	param.desireJob = jcCode;
	//期望工作地点
	var desireAddress = '';
	var desireAddressValue3 = $('#desireAddressValue3').val();
	var desireAddressValue2 = $('#desireAddressValue2').val();
	var desireAddressValue1 = $('#desireAddressValue1').val();
	if(desireAddressValue3 != null && desireAddressValue3 != ''){
		desireAddress = desireAddressValue3;
	}else if(desireAddressValue2 != null && desireAddressValue2 != ''){
		desireAddress = desireAddressValue2;
	}else if(desireAddressValue1 != null && desireAddressValue1 != ''){
		desireAddress = desireAddressValue1;
	}else{
		alert('期望工作地点不能为空, 请重新选择.');
		return false;
	}
	param.desireAddress = desireAddress;
	//其他福利, 暂时不写....
	//
	//
	//
	//其他要求
	var provideOther = $('#provideOther').val();
	if(provideOther != null && provideOther != ''){
		param.provideOther = provideOther;
	}
	var state = $('#state').val();
	if(state != null && state != ''){
		param.state = state;
	}
	
	/**
	 * 教育培训
	 */
	var education = $('#education').val();
	if(education == null || education == ''){
		$('#education').focus();
		return false;
	}
	param.education = education;
	var major = $('#major').val();
	if(major != null && major != ''){
		param.major = major;
	}
	var school = $('#school').val();
	if(school != null && school != ''){
		param.school = school;
	}
	var experts = $('#experts').val();
	if(experts != null && experts != ''){
		param.experts = experts;
	}
	var training = $('#training').val();
	if(training != null && training != ''){
		param.training = training;
	}
	var selfEvaluation = $('#selfEvaluation').val();
	if(selfEvaluation != null && selfEvaluation != ''){
		param.selfEvaluation = selfEvaluation;
	}
	
	/**
	 * 工作经历
	 */
	var experience = $('#experience').val();
	if(experience != null && experience != ''){
		param.experience = experience;
	}
	
	/**
	 * 就业管理
	 */
	var careerTest = $('#careerTest').val();
	if(careerTest != null && careerTest != ''){
		param.careerTest = careerTest;
	}
	var processState = $('#processState').val();
	if(processState != null && processState != ''){
		param.processState = processState;
	}
	return param;
}

//保存从业经历
function saveWorkExperience(){
	var param = checkWorkExperience('Add');
	if(!param){
		return false;
	}
	$.ajax({
		url:server.url + 'secure/resume/saveExperience',
		type:'POST',
		dataType:'json',
		data:{
			'workTime':param.workTime,
			'companyName':param.companyName,
			'jobName':param.jobName,
			'leaveReason':param.leaveReason,
			'jobContent':param.jobContent,
			'resume.id':param.rid
		},
		success:function(json){
			if(json.notice == 'success'){
				alert('保存成功!');
				window.location.reload();
			}else{
				alert('保存失败了...');
			}
		},
		error:function(){
			alert('操作发生错误!');
		}
	});
}

//删除从业经历
function delWorkExperience(weid){
	if(weid == null || weid == ''){
		alert('传递的参数有误, 请刷新页面重新尝试或者联系网站开发人员');
	}
	var confirm = window.confirm("确实要删除此工作经历么, 此操作无法恢复.");
	if(!confirm){
		return;
	}
	$.ajax({
		url:server.url + '/secure/resume/deleteExperience/' + weid,
		type:'POST',
		dataType:'json',
		success:function(json){
			if(json.notice == 'success'){
				alert('删除成功!');
				window.location.reload();
			}else{
				alert('删除失败了...');
			}
		},
		error:function(){
			alert('操作发生错误!');
		}
	});
}

//更新从业经历
function updateWorkExperience(weId,orderId){
	var param = checkWorkExperience(orderId);
	if(!param){
		return false;
	}
	$.ajax({
		url:server.url + 'secure/resume/updateExperience',
		type:'POST',
		dataType:'json',
		data:{
			'id':weId,
			'workTime':param.workTime,
			'companyName':param.companyName,
			'jobName':param.jobName,
			'leaveReason':param.leaveReason,
			'jobContent':param.jobContent
		},
		success:function(json){
			if(json.notice == 'success'){
				alert('更新成功!');
				window.location.reload();
			}else{
				alert('更新失败了...');
			}
		},
		error:function(){
			alert('操作发生错误!');
		}
	});
}

//保存 职业测评情况
function saveUnempManage(){
	var param = checkUnempManage('Add');
	if(!param){
		return false;
	}
	$.ajax({
		url:server.url + 'secure/resume/saveUnempManage',
		type:'POST',
		dataType:'json',
		data:{
			'time':param.time,
			'content':param.content,
			'resume.id':param.rid
		},
		success:function(json){
			if(json.notice == 'success'){
				alert('保存成功!');
				window.location.reload();
			}else{
				alert('保存失败了...');
			}
		},
		error:function(){
			alert('操作发生错误!');
		}
	});
}

//删除 职业测评情况
function delUnempManage(umid){
	if(umid == null || umid == ''){
		alert('传递的参数有误, 请刷新页面重新尝试或者联系网站开发人员');
	}
	var confirm = window.confirm("确实要删除此测评情况么, 此操作无法恢复.");
	if(!confirm){
		return;
	}
	$.ajax({
		url:server.url + '/secure/resume/deleteUnempManage/' + umid,
		type:'POST',
		dataType:'json',
		success:function(json){
			if(json.notice == 'success'){
				alert('删除成功!');
				window.location.reload();
			}else{
				alert('删除失败了...');
			}
		},
		error:function(){
			alert('操作发生错误!');
		}
	});
}

//更新 职业测评情况
function updateUnempManage(umId,orderId){
	var param = checkUnempManage(orderId);
	if(!param){
		return false;
	}
	$.ajax({
		url:server.url + 'secure/resume/updateUnempManage',
		type:'POST',
		dataType:'json',
		data:{
			'id':umId,
			'time':param.time,
			'content':param.content
		},
		success:function(json){
			if(json.notice == 'success'){
				alert('更新成功!');
				window.location.reload();
			}else{
				alert('更新失败了...');
			}
		},
		error:function(){
			alert('操作发生错误!');
		}
	});
}


/**
 * 校验 工作经历 提交表单中所有控件方法
 * @returns
 */
function checkWorkExperience(orderId){
	var param = new Object();
	var objId = $('#objId').val();
	if(objId == null || objId == ''){
		alert('传递的参数有误, 请刷新页面重新尝试.');
		return false;
	}
	param.rid = objId;
	var workTime = $('#workTime' + orderId).val();
	if(workTime != null || workTime != ''){
		param.workTime = workTime;
	}
	var companyName = $('#companyName' + orderId).val();
	if(companyName != null || companyName != ''){
		param.companyName = companyName;
	}
	var jobName = $('#jobName' + orderId).val();
	if(jobName != null || jobName != ''){
		param.jobName = jobName;
	}
	var leaveReason = $('#leaveReason' + orderId).val();
	if(leaveReason != null || leaveReason != ''){
		param.leaveReason = leaveReason;
	}
	var jobContent = $('#jobContent' + orderId).val();
	if(jobContent != null || jobContent != ''){
		param.jobContent = jobContent;
	}
	return param;
}

/**
 * 校验 职业测评情况 提交表单中所有控件方法
 * @returns
 */
function checkUnempManage(orderId){
	var param = new Object();
	var objId = $('#objId').val();
	if(objId == null || objId == ''){
		alert('传递的参数有误, 请刷新页面重新尝试.');
		return false;
	}
	param.rid = objId;
	var time = $('#time' + orderId).val();
	if(time != null || time != ''){
		param.time = time;
	}
	var content = $('#content' + orderId).val();
	if(content != null || content != ''){
		param.content = content;
	}
	return param;
}
