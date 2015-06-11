$(document).ready(function(){
	
	//保存按钮点击事件
	$('#SaveAllBtn').click(function(){
		$('#jobInfo').submit();
	});

});

function check(type){
	//职位名称
	var name = $('#name').val();
	if(name == null || name == ''){
		alert('职位名称不能为空!');
		$('#name').focus();
		return false;
	}
	//招聘人数
	var reg_number = /^[0-9]{1,4}/;
	var hireNumber = $('#hireNumber').val();
	if(!reg_number.test(hireNumber)){
        alert('招聘人数不能为空, 且只能为数字!');
		$('#hireNumber').focus();
		return false;
	}
	//学历
	var education = $('#education').val();
	if(education == null || education == ''){
		alert('请选择最低学历!');
		$('#education').focus();
		return false;
	}
	//工作经验
	var experience = $('#experience').val();
	if(experience == null || experience == ''){
		alert('请选择最低工作经验年限!');
		$('#experience').focus();
		return false;
	}
	//岗位性质
	var nature = $('#nature').val();
	if(nature == null || nature == ''){
		alert('请选择岗位性质!');
		$('#nature').focus();
		return false;
	}
	//有效时间
	if(type == 'save'){
		var effectiveDays = $('#effectiveDays').val();
		if(effectiveDays == null || effectiveDays == ''){
			alert('请选择职位有效期!');
			$('#effectiveDays').focus();
			return false;
		}
	}
/*	//地区
	var area_lv1 = $('#area_lv1').val();
	if(area_lv1 == null || area_lv1 == ''){
		alert('请选择具体的工作地!');
		$('#area_lv1').focus();
		return false;
	}
	//职位类别
	var jobCategory_lv1 = $('#jobCategory_lv1').val();
	if(jobCategory_lv1 == null || jobCategory_lv1 == ''){
		alert('请选择工作类别!');
		$('#jobCategory_lv1').focus();
		return false;
	}*/
	//职位描述
	var name = $('#description').val();
	if(name == null || name == ''){
		alert('职位描述不能为空!');
		$('#description').focus();
		return false;
	}
	//联系人
	var contactPerson = $('#contactPerson').val();
	if(contactPerson == null || contactPerson == ''){
		alert('请选择联系人!');
		$('#contactPerson').focus();
		return false;
	}
	//电话/手机
	var contactTel = $('#contactTel').val();
	if(contactTel == null || contactTel == ''){
        alert('电话/手机号码不能为空!');
		$('#contactTel').focus();
		return false;
	}
/*	if(!verify.checkTel(contactTel)){
        alert('请检查你输入的电话/手机号码!');
		$('#contactTel').focus();
		return false;
	}	*/
	return true;
}

function addEffectiveTime(obj) {
	$("#effectiveDays").val(obj.val());
	if (obj.val() == null || obj.val() == "") {
		$("#effectiveDays").val(0);
	}
	var effectiveTime = $("#effectiveTime2").val();
	var date = new Date(effectiveTime);
	var fomdate = date.format("yyyy-MM-dd hh:mm:ss");
	$("#effectiveTime").val(dateOperator(effectiveTime, obj.val(), "+"));
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
	var cdate = y + "-" + m + "-" + d + " " + h + ":" + min + ":" + s;
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
