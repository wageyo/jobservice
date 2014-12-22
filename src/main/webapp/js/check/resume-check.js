
$(document).ready(function(){
		//新增按钮
		$('#add_we').toggle(
			function(){
				$('#div_we').show();
			},
			function(){
				$('#div_we').hide();
			}	
		);
});
function resume_save(){
	$('#resumeInfo').submit();
}


function resume_check(type) {
	// 标题
	var title = $('#title').val();
	if (title == null || title == '') {
		alert('标题不能为空!');
		$('#title').focus();
		return false;
	}
	// 姓名
	var name = $('#name').val();
	if (name == null || name == '') {
		alert('姓名不能为空!');
		$('#name').focus();
		return false;
	}
	// 出生日期
	var year = $('#year').val();
	if (year == 0 || year == '0') {
		alert('请先选择年份!');
		$('#year').focus();
		return false;
	}
	var month = $('#month').val();
	if (month == 0 || month == '0') {
		alert('请选择月份!');
		$('#month').focus();
		return false;
	}
	var day = $('#day').val();
	if (day == 0 || day == '0') {
		alert('请选择日期!');
		$('#day').focus();
		return false;
	}
	// 电话/手机
/*	var phone = $('#phone').val();
	if (!verify.checkTel(phone)) {
		alert('请检查你输入的电话/手机号码!');
		$('#phone').focus();
		return false;
	}	*/
	// 残疾证号 
	var disabilityCard = $('#disabilityCard').val();
	if (disabilityCard == null || disabilityCard == '') {
		alert('请填写残疾证号!');
		$('#disabilityCard').focus();
		return false;
	}
	// 残疾情况
	var disabilityCategory = $('#disabilityCategory').val();
	if (disabilityCategory == null || disabilityCategory == '') {
		alert('请确定您的残疾情况!');
		$('#disabilityCategory').focus();
		return false;
	}
	// 残疾等级
	var disabilityLevel = $('#disabilityLevel').val();
	if (disabilityLevel == null || disabilityLevel == '') {
		alert('请确定您的残疾等级!');
		$('#disabilityLevel').focus();
		return false;
	}
	// 残疾部位
	var disabilityPart = $('#disabilityPart').val();
	if (disabilityPart == null || disabilityPart == '') {
		alert('请确定您的残疾部位!');
		$('#disabilityPart').focus();
		return false;
	}
	// 职位类别--修改为只有第一级为必填
	var jobCategory_lv1 = $('#jobCategory_lv1').val();
	if (jobCategory_lv1 == null || jobCategory_lv1 == '') {
		alert('请选择工作类别!');
		$('#jobCategory_lv1').focus();
		return false;
	}
	// 地区--修改为只有第一级为必填
	var area_lv1 = $('#area_lv1').val();
	if (area_lv1 == null || area_lv1 == '') {
		alert('请选择期望工作地!');
		$('#area_lv1').focus();
		return false;
	}
	// 学历
	var education = $('#education').val();
	if (education == null || education == '') {
		alert('请选择具体的学历!');
		$('#education').focus();
		return false;
	}
	// 专业
/*	var major = $('#major').val();
	if (major == null || major == '') {
		alert('请填写您的专业!');
		$('#major').focus();
		return false;
	}	*/
	return true;
}


//增加简历中 用于工作经历的js
function add_tr(){
//	var len = $('#tableExperience tr').length;
	var str = '<tr>';
		str += '<td class="textTop" style="text-align:center;">工作经历：</td>';
		str += '<td colspan="2">';
		str += '<p>所在公司：<input name="companyName" type="text" value="" type="text" />在职时间：<input name="workTime" type="text" value="" type="text" /></p>';
		str += '<p>所在职位：<input name="jobName" type="text" value="" type="text" />离职原因：<input name="leaveReason" type="text" value="" type="text" /></p>';
		str += '<p>自我评价：<textarea rows="4" cols="100" name="jobContent" style="width:522px;" onfocus="if (this.value == \'自我评价,所负责的事物等等.\') {this.value = \'\';}" onblur="if (this.value == \'\') {this.value = \'自我评价,所负责的事物等等.\';}">自我评价,所负责的事物等等.</textarea></p>';
		str += '</td>';
		str += '<td style="width:40px;padding:0px; text-align:center;" onclick="del_tr(this)">';
			str += '<a href="javascript:void(0);">删除</a>';
		str += '</td>';
	str += '</tr>';
	$('#tableExperience').append(str);
}

function del_tr(obj){
	var bl = window.confirm('确定要删除此工作经历么? 此操作不可恢复!');
	if(bl){
		$(obj).parent().remove();
	}
}


//修改简历中  工作经历的公用js
function del_edit_we(wid){
//	var rid = $('#rid').val();
	var bl = window.confirm('确定要删除此工作经历么? 此操作不可恢复!');
	if(bl){
		$.ajax({
			url: server.url + '/secure/resume/deleteExperience/'+wid,
			type:'POST',
			dataType:'json',
			success:function(json){
				if(json.notice == 'success'){
					alert('删除成功!');
					//window.location.href='${contextPath}/secure/resume/update?id='+rid;
					window.location.reload();
				}else{
					alert('删除失败了...');
				}
			},
			error:function(){
				alert('操作失败!');
			}
		});
	}
}

//关闭增加div 按钮
function close_add(){
	$('#div_we').hide();
}

//增加单个工作经历
function save_we(){
	var rid = $('#rid').val();
	var new_companyName = $('#new_companyName').val();
	var new_workTime = $('#new_workTime').val();
	var new_jobName = $('#new_jobName').val();
	var new_jobContent = $('#new_jobContent').val();
	var new_leaveReason = $('#new_leaveReason').val();
	$.ajax({
		url: server.url + '/secure/resume/saveExperience',
		type:'POST',
		dataType:'json',
		data:{'resume.id':rid,'companyName':new_companyName,'jobName':new_jobName,'jobContent':new_jobContent,'leaveReason':new_leaveReason},
		success:function(json){
			if(json.notice == 'success'){
				alert('保存成功!');
			//	window.location.href='${contextPath}/secure/resume/update?id='+rid;
				window.location.reload();
			}else{
				alert('保存失败了...');
			}
		},
		error:function(){
			alert('操作失败!');
		}
	});
}

