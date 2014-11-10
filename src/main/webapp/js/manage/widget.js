/**
 * 地区三联动按钮-1
 */
// 省级change选择事件
function selectAreaLevel1(areaCode,areaName){
	var url = server.url + 'widget/getSubArea';
	//给一, 二, 三级菜单的 显示按钮和值 变为 请选择
	$('#areaName1').text(areaName);
	$('#areaValue1').val(areaCode);
	$('#areaName2').text('请选择');
	$('#areaValue2').val('');
	$('#areaName3').text('请选择');
	$('#areaValue3').val('');
	//移除二, 三级菜单多余选项
	$('#areaLevel2 li:not(:first)').remove();
	$('#areaLevel3 li:not(:first)').remove();
	$.ajax({
		url : url,
		type : 'GET',
		dataType : 'json',
		data : {
			'code' : areaCode
		},
		success : function(json) {
			//重新赋值
			$.each(json.areaList, function(i, area) {
				$('#areaLevel2').append('<li><a href="javascript:selectAreaLevel2(\'' + area.code+ '\',\'' + area.name + '\');">' + area.name + '</a></li>');
			});
		},
		error : function() {
			alert('操作失败!');
		}
	});
}
//市级change选择事件
function selectAreaLevel2(areaCode,areaName){
	var url = server.url + 'widget/getSubArea';
	//给二, 三级菜单的 显示按钮和值 变为 请选择
	$('#areaName2').text(areaName);
	$('#areaValue2').val(areaCode);
	$('#areaName3').text('请选择');
	$('#areaValue3').val('');
	//移除3级菜单多余选项
	$('#areaLevel3 li:not(:first)').remove();
	$.ajax({
		url : url,
		type : 'GET',
		dataType : 'json',
		data : {
			'code' : areaCode
		},
		success : function(json) {
			//重新赋值
			$.each(json.areaList, function(i, area) {
				$('#areaLevel3').append('<li><a href="javascript:selectAreaLevel3(\'' + area.code+ '\',\'' + area.name + '\');">' + area.name + '</a></li>');
			});
		},
		error : function() {
			alert('操作失败!');
		}
	});
}

//县/区 级change选择事件
function selectAreaLevel3(areaCode,areaName){
	//给三级级菜单控件赋值
	$('#areaName3').text(areaName);
	$('#areaValue3').val(areaCode);
}


/**
 * 职位类别三联动按钮
 */
//一级onchange事件
function selectJcLevel1(jcCode,jcName){
	var url = server.url + 'widget/getSubJc';
	//给一, 二, 三级菜单的 显示按钮和值 变为 请选择
	$('#jcName1').text(jcName);
	$('#jcValue1').val(jcCode);
	$('#jcName2').text('请选择');
	$('#jcValue2').val('');
	$('#jcName3').text('请选择');
	$('#jcValue3').val('');
	//移除2,3级菜单多余选项
	$('#jcLevel2 li:not(:first)').remove();
	$('#jcLevel3 li:not(:first)').remove();
	$.ajax({
		url : url,
		type : 'GET',
		dataType : 'json',
		data : {
			'code' : jcCode
		},
		success : function(json) {
			//重新赋值
			$.each(json.jcList, function(i, jc) {
				$('#jcLevel2').append('<li><a href="javascript:selectJcLevel2(\'' + jc.code+ '\',\'' + jc.name + '\');">' + jc.name + '</a></li>');
			});
		},
		error : function() {
			alert('操作失败!');
		}
	});
}
//二级onchange事件
function selectJcLevel2(jcCode,jcName){
	var url = server.url + 'widget/getSubJc';
	//给二, 三级菜单的 显示按钮和值 变为 请选择
	$('#jcName2').text(jcName);
	$('#jcValue2').val(jcCode);
	$('#jcName3').text('请选择');
	$('#jcValue3').val('');
	//移除3级菜单多余选项
	$('#jcLevel3 li:not(:first)').remove();
	
	$.ajax({
		url : url,
		type : 'GET',
		dataType : 'json',
		data : {
			'code' : jcCode
		},
		success : function(json) {
			//重新赋值
			$.each(json.jcList, function(i, jc) {
				$('#jcLevel3').append('<li><a href="javascript:selectJcLevel3(\'' + jc.code+ '\',\'' + jc.name + '\');">' + jc.name + '</a></li>');
			});
		},
		error : function() {
			alert('操作失败!');
		}
	});
}

//三级onchange事件
function selectJcLevel3(jcCode,jcName){
	//给三级级菜单控件赋值
	$('#jcValue3').val(jcCode);
	$('#jcName3').text(jcName);
}



$(document).ready(function() {
	
});
//生日选择控件
function change_birth(){
	var year = $('#year').val();
	var month = $('#month').val();
	if(month < 10){
		month = '0'+month;
	}
	var day = $('#day').val();
	if(day < 10){
		day = '0'+day;
	}
	var birth = year + '-' + month + '-' + day;
	$('#birth').val(birth);
}
