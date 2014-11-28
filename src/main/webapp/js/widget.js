$(document).ready(function() {
	/**
	 * 地区三联动按钮-1
	 */
	// 省级onchange事件
	$('#area_lv1').change(function() {
		var provinceID = $("#area_lv1 option:selected").val();
		var url = server.url + 'widget/getSubArea';
		$.ajax({
			url : url,
			type : 'GET',
			dataType : 'json',
			data : {
				'code' : provinceID
			},
			success : function(json) {
				$('#area_lv2 option:not(:first)').remove();
				$('#area_lv3 option:not(:first)').remove();
				$.each(json.areaList, function(i, area) {
					$('#area_lv2').append('<option value="' + area.code + '">' + area.name + '</option>');
				});
			},
			error : function() {
				alert('操作失败!');
			}
		});
	});

	// 市级onchange事件
	$('#area_lv2').change(function() {
		var provinceID = $("#area_lv2 option:selected").val();
		if(provinceID == null || provinceID == ''){
			$('#area_lv3 option:not(:first)').remove();
			return false;
		}
		var url = server.url + 'widget/getSubArea';
		$.ajax({
			url : url,
			type : 'GET',
			dataType : 'json',
			data : {
				'code' : provinceID
			},
			success : function(json) {
				$('#area_lv3 option:not(:first)').remove();
				$.each(json.areaList, function(i, area) {
					$('#area_lv3').append('<option value="' + area.code + '">' + area.name + '</option>');
				});
			},
			error : function() {
				alert('操作失败!');
			}
		});
	});

	/**
	 * 地区三联动按钮-2
	 */
	// 省级onchange事件
	$('#area_lv12').change(function() {
		var provinceID = $("#area_lv12 option:selected").val();
		var url = server.url + 'widget/getSubArea';
		$.ajax({
			url : url,
			type : 'GET',
			dataType : 'json',
			data : {
				'code' : provinceID
			},
			success : function(json) {
				$('#area_lv22 option:not(:first)').remove();
				$('#area_lv32 option:not(:first)').remove();
				$.each(json.areaList, function(i, area) {
					$('#area_lv22').append('<option value="' + area.code + '">' + area.name + '</option>');
				});
			},
			error : function() {
				alert('操作失败!');
			}
		});
	});

	// 市级onchange事件
	$('#area_lv22').change(function() {
		var provinceID = $("#area_lv22 option:selected").val();
		if(provinceID == null || provinceID == ''){
			$('#area_lv32 option:not(:first)').remove();
			return false;
		}
		var url = server.url + 'widget/getSubArea';
		$.ajax({
			url : url,
			type : 'GET',
			dataType : 'json',
			data : {
				'code' : provinceID
			},
			success : function(json) {
				$('#area_lv32 option:not(:first)').remove();
				$.each(json.areaList, function(i, area) {
					$('#area_lv32').append('<option value="' + area.code + '">' +area.name + '</option>');
				});
			},
			error : function() {
				alert('操作失败!');
			}
		});
	});
	
	/**
	 * 地区二级联动按钮-3
	 */

	// 市级onchange事件
	$('#area_lv23').change(function() {
		var provinceID = $("#area_lv23 option:selected").val();
		if(provinceID == null || provinceID == ''){
			$('#area_lv33 option:not(:first)').remove();
			return false;
		}
		var url = server.url + 'widget/getSubArea';
		$.ajax({
			url : url,
			type : 'GET',
			dataType : 'json',
			data : {
				'code' : provinceID
			},
			success : function(json) {
				$('#area_lv33 option:not(:first)').remove();
				$.each(json.areaList, function(i, area) {
					$('#area_lv33').append('<option value="' + area.code + '">' + area.name + '</option>');
				});
			},
			error : function() {
				alert('操作失败!');
			}
		});
	});
	
	/**
	 * 职位类别三联动按钮
	 */
	// 一级onchange事件
	$('#jobCategory_lv1').change(function() {
		var jcCode = $("#jobCategory_lv1 option:selected").val();
		var url = server.url + 'widget/getSubJc';
		$.ajax({
			url : url,
			type : 'GET',
			dataType : 'json',
			data : {
				'code' : jcCode
			},
			success : function(json) {
				$('#jobCategory_lv2 option:not(:first)').remove();
				$('#jobCategory_lv3 option:not(:first)').remove();
				$.each(json.jcList, function(i, jc) {
					$('#jobCategory_lv2').append('<option value="' + jc.code + '">' + jc.name + '</option>');
				});
				$('#jobCategoryCode').val(jcCode);
			},
			error : function() {
				alert('操作失败!');
			}
		});
	});

	// 二级onchange事件
	$('#jobCategory_lv2').change(function() {
		var jcCode = $("#jobCategory_lv2 option:selected").val();
		var url = server.url + '/widget/getSubJc';
		$.ajax({
			url : url,
			type : 'GET',
			dataType : 'json',
			data : {
				'code' : jcCode
			},
			success : function(json) {
				$('#jobCategory_lv3 option:not(:first)').remove();
				$.each(json.jcList, function(i, jc) {
					$('#jobCategory_lv3').append('<option value="' + jc.code + '">' + jc.name + '</option>');
				});
				$('#jobCategoryCode').val(jcCode);
			},
			error : function() {
				alert('操作失败!');
			}
		});
	});
	
	//三级onchange事件
	$('#jobCategory_lv3').change(function() {
		var jcCode = $("#jobCategory_lv3 option:selected").val();
		$('#jobCategoryCode').val(jcCode);
	});
	

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
