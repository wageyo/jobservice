
function resume_check() {
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
	var phone = $('#phone').val();
	if (!verify.checkTel(phone)) {
		alert('请检查你输入的电话/手机号码!');
		$('#phone').focus();
		return false;
	}
	// 残疾情况
	var disabilityCategory = $('#disabilityCategory').val();
	if (disabilityCategory == null || disabilityCategory == '') {
		alert('请确定您的残疾情况!');
		$('#disabilityCategory').focus();
		return false;
	}
	// 职位类别
	var jobCategory_lv3 = $('#jobCategory_lv3').val();
	if (jobCategory_lv3 == null || jobCategory_lv3 == '') {
		alert('请选择具体的工作类别!');
		$('#jobCategory_lv1').focus();
		return false;
	}
	// 地区
	var area_lv3 = $('#area_lv3').val();
	if (area_lv3 == null || area_lv3 == '') {
		alert('请选择具体的期望工作地!');
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
	var major = $('#major').val();
	if (major == null || major == '') {
		alert('请填写您的专业!');
		$('#major').focus();
		return false;
	}
	return true;
}


//增加简历中 用于工作经历的js
function add_tr(){
	var str = '<tr style="text-align:left;">';
		str += '<td>';
			str += '<p><span class="span_assign_width" style="width:250px;">公司 : <input type="text" size="35" name="companyName"/></span>在职时间 : <input type="text" name="workTime"/> <span style="color:red;"> (格式:2003.2-2005.8)</span></p>';
			str += '<p><span class="span_assign_width" style="width:250px;">职位 : <input type="text" size="35" name="jobName"/></span>离职原因 : <input type="text" size="45" name="leaveReason"/></p>';
			str += '<p><span class="span_assign_width" style="width:36px;">&nbsp;</span><textarea rows="4" cols="100" name="jobContent" onfocus="if (this.value == &#39;自我评价,所负责的事物等等.&#39;) {this.value = &#39;&#39;;}" onblur="if (this.value == &#39;&#39;) {this.value = &#39;自我评价,所负责的事物等等.&#39;;}">自我评价,所负责的事物等等.</textarea></p>';
			str += '<hr/>';
		str += '</td>';
		str += '<td onClick="del_tr(this)" onmouseover="mouseover_td(this);" onmouseout="mouseout_td(this)">';
			str += '<a href="javascript:void(0);">删除</a>';
		str += '</td>';
	str += '</tr>';
	$('#tb').append(str);
}

function del_tr(obj){
	var bl = window.confirm('确定要删除此工作经历么? 此操作不可恢复!');
	if(bl){
		$(obj).parent().remove();
	}
}


//增加, 修改简历中 用于工作经历的公用js
function mouseover_td(obj){
	$(obj).css('text-decoration','underline');
}

function mouseout_td(obj){
	$(obj).css('text-decoration','none');
}

