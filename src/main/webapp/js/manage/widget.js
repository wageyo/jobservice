$(document).ready(function() {
	
});
/**
 * 地区三联动按钮, 命名规则为inputValueId, btnTextId, dropdownId, inputValue1,btnText2,dropdown3 分别代表省市区三级联动, 其中数字不用传入
 */
// 省级change选择事件
function selectAreaLevel1(inputValueId,btnTextId,areaCode,areaName,dropdownId){
	var url = server.url + 'widget/getSubArea';
	//给一, 二, 三级菜单的 显示按钮和值 变为 请选择
	$('#'+btnTextId+'1').text(areaName);
	$('#'+inputValueId+'1').val(areaCode);
	$('#'+btnTextId+'2').text('请选择');
	$('#'+inputValueId+'2').val('');
	$('#'+btnTextId+'3').text('请选择');
	$('#'+inputValueId+'Value3').val('');
	//移除二, 三级菜单多余选项
	$('#'+dropdownId+'2 li:not(:first)').remove();
	$('#'+dropdownId+'3 li:not(:first)').remove();
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
				$('#'+dropdownId+'2').append('<li><a href="javascript:selectAreaLevel2(\''+inputValueId+'\',\''+btnTextId+'\',\'' + area.code+ '\',\'' + area.name + '\',\''+dropdownId+'\');">' + area.name + '</a></li>');
			});
		},
		error : function() {
			alert('操作失败!');
		}
	});
}
//市级change选择事件
function selectAreaLevel2(inputValueId,btnTextId,areaCode,areaName,dropdownId){
	var url = server.url + 'widget/getSubArea';
	//给二, 三级菜单的 显示按钮和值 变为 请选择
	$('#'+btnTextId+'2').text(areaName);
	$('#'+inputValueId+'2').val(areaCode);
	$('#'+btnTextId+'3').text('请选择');
	$('#'+inputValueId+'3').val('');
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
				$('#'+dropdownId+'3').append('<li><a href="javascript:selectAreaLevel3(\''+inputValueId+'\',\''+btnTextId+'\',\'' + area.code+ '\',\'' + area.name + '\',\''+dropdownId+'\');">' + area.name + '</a></li>');
			});
		},
		error : function() {
			alert('操作失败!');
		}
	});
}

//县/区 级change选择事件
function selectAreaLevel3(inputValueId,btnTextId,areaCode,areaName){
	//给三级级菜单控件赋值
	$('#'+btnTextId+'3').text(areaName);
	$('#'+inputValueId+'3').val(areaCode);
}


/**
 * 职位类别三联动按钮,命名规则为inputValueId, btnTextId, dropdownId, inputValue1,btnText2,dropdown3 分别代表一, 二,三级联动, 其中数字不用传入
 */
//一级onchange事件
function selectJcLevel1(inputValueId,btnTextId,jcCode,jcName,dropdownId){
	var url = server.url + 'widget/getSubJc';
	//给一, 二, 三级菜单的 显示按钮和值 变为 请选择
	$('#'+btnTextId+'1').text(jcName);
	$('#'+inputValueId+'1').val(jcCode);
	$('#'+btnTextId+'2').text('请选择');
	$('#'+inputValueId+'2').val('');
	$('#'+btnTextId+'3').text('请选择');
	$('#'+inputValueId+'3').val('');
	//移除2,3级菜单多余选项
	$('#'+dropdownId+'2 li:not(:first)').remove();
	$('#'+dropdownId+'3 li:not(:first)').remove();
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
				$('#'+dropdownId+'2').append('<li><a href="javascript:selectJcLevel2(\''+inputValueId+'\',\''+btnTextId+'\',\'' + jc.code+ '\',\'' + jc.name + '\',\''+dropdownId+'\');">' + jc.name + '</a></li>');
			});
		},
		error : function() {
			alert('操作失败!');
		}
	});
}

//二级onchange事件
function selectJcLevel2(inputValueId,btnTextId,jcCode,jcName,dropdownId){
	var url = server.url + 'widget/getSubJc';
	//给二, 三级菜单的 显示按钮和值 变为 请选择
	$('#'+btnTextId+'2').text(jcName);
	$('#'+inputValueId+'2').val(jcCode);
	$('#'+btnTextId+'3').text('请选择');
	$('#'+inputValueId+'3').val('');
	//移除3级菜单多余选项
	$('#'+dropdownId+'3 li:not(:first)').remove();
	
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
				$('#'+dropdownId+'3').append('<li><a href="javascript:selectJcLevel3(\''+inputValueId+'\',\''+btnTextId+'\',\'' + jc.code+ '\',\'' + jc.name + '\',\''+dropdownId+'\');">' + jc.name + '</a></li>');
			});
		},
		error : function() {
			alert('操作失败!');
		}
	});
}

//三级onchange事件
function selectJcLevel3(inputValueId,btnTextId,jcCode,jcName){
	//给三级级菜单控件赋值
	$('#'+btnTextId+'3').text(jcName);
	$('#'+inputValueId+'3').val(jcCode);
}


//生日选择控件
function selectBirth(year,month,day){
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
