/***********************************************
 *******  后台管理页面框架脚本--简历管理  ********
 ***********************************************/

//收集参数并进入后台查询方法
function query(page,matchRate){
	var paramStr = '';	//查询字符串
	var totalPage = $('#totalPage').val();
	if(page == null || page == undefined || page <= 1 || page == ''){
		paramStr += 'page=1'; 
	}else if(page >= totalPage){
		paramStr += 'page=' + totalPage;
	}else{
		paramStr += 'page=' + page;
	}
	if(matchRate == null || matchRate == '' || matchRate == undefined){
		matchRate = $('#matchRate').val();
	}
	paramStr +="&matchRate="+matchRate;
    window.location.href = getRootPath() + '/manage/mre?' + paramStr;

}
//查看自动匹配数据
function lookMatchData(i,id,obj){
	var paramStr = '';
	paramStr += 'id='+id; 
	var	matchRate=$('#matchRate').val();
	if(matchRate != null && matchRate != undefined && matchRate != '' && matchRate > 0){
		paramStr +="&matchRate="+matchRate;
		var url = getRootPath() + '/manage/resume_match_json?' + paramStr;
		$.ajax({
			url : url,
			type : 'GET',
			success : function(e) {
				//①匹配结果以html代码的形式返回,  放入到<tr>标签中, 然后插入到点中行的下面
				var matchResult = '<tr class="trMatchResult"><td colspan="10" style="border: 1px solid #ececec;border-bottom: 3px solid #ececec;">';
					matchResult += e;	//将放回的html代码div放入到td中
					matchResult += '</td></tr>';
				$(obj).parent().parent().after(matchResult);
				//②自身本来行变粗字体
				$(obj).parent().parent().css('font-weight','bold');
				//③同时 显示按钮隐藏, 隐藏按钮 显示出来.
				$(obj).hide();
				$(obj).next().show();
			},
			dataType : 'html',
			async : false
		});
	}else{
		alert("请选择匹配百分比");
	}
}
//隐藏自动匹配数据
function hideMatchData(obj){
	$(obj).parent().parent().next().remove();
	$(obj).parent().parent().css('font-weight','normal');
	$(obj).hide();
	$(obj).prev().show();
}
//参数下拉框点击事件
function selectButtonPer(valueButton,resumeMatchName, nameButton, value, name){
	//给对应控件 赋value值
	$('#'+valueButton).val(value);
	//给对应控件 赋name值
	$('#'+nameButton).text(name);
	$('#'+resumeMatchName).val(name);
}

//复选框事件
//全选、取消全选的事件
function selectAll(){
	var checked = $('#checkedAll').prop('checked');
	 $(":checkbox").prop("checked", checked);  
}
//子复选框的事件
function setSelectAll(){
	//当没有选中某个子复选框时，SelectAll取消选中
	var checkedsub = $("input[name='items']:checked").length; //获取选中的subcheck的个数
	var chsub = $("input[name='items']").length; //获取subcheck的个数
	var checked = chsub == checkedsub ? true:false;
	$('#checkedAll').prop('checked', checked);
}

//发送推送招聘信息
function sendTuiSong(type){
	var	matchRate=$('#matchRate').val();
	if(matchRate == null || matchRate == undefined || matchRate == '' || matchRate <= 0){
		alert("请选择匹配百分比");
		return;
	}
	//选中的简历
	var arrChk = [];
	if(type != 'all'){
		var checkedsub = $("input[name='items']:checked");
		if(checkedsub.length == 0){
			alert('你还没有选中任何需要推送招聘信息的简历.');
			return;
		}
		$($("input[name='items']:checked")).each(function(){
			arrChk.push($(this).val()); 
		});
	}
	var url = getRootPath() + '/manage/sendJob/' + matchRate + '/' + type;
	alert(url);
	$.ajax({
		url : url,
		type : 'POST',
		data : {'ids':arrChk},
		dataType : 'json',
		success : function(e) {
			alert(e);
		},
		async : false
	});
}


