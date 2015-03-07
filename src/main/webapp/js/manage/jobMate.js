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
    window.location.href = getRootPath() + '/manage/mjb?' + paramStr;
}

//查看自动匹配数据
function lookMatchData(i,id,obj){
	var paramStr = '';
	paramStr += 'id='+id; 
	//匹配条件
	var choosen = $('#searchBar .badge-info[name="mateCondition"]');
	if(choosen == null || choosen == undefined || choosen.length == 0){
		alert("请选择至少一项匹配条件.");
		return;
	}
	//逐个不为空的属性拼接起来
	$(choosen).each(function(){
		var val = $(this).attr('value');
		paramStr += '&'+ val + '=' + val;
	});
	var url = getRootPath() + '/manage/job_match_json?' + paramStr;
	$.ajax({
		url : url,
		type : 'GET',
		success : function(e) {
			//①匹配结果以html代码的形式返回,  放入到<tr>标签中, 然后插入到点中行的下面
			var matchResult = '<tr class="trMatchResult"><td>&nbsp;</td><td colspan="10" style="border: 1px solid #ececec;border-bottom: 3px solid #ececec;">';
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

//匹配条件点击改变样式事件
function conditionClick(obj){
	$(obj).toggleClass('badge-info');
	//条件总个数
	var total= $('#searchBar .label[name="mateCondition"]').length;	
	//已经被选取的条件个数
	var choosen= $('#searchBar .badge-info[name="mateCondition"]').length;
	//如果相等, 则将全选按钮设为选中;反之亦然
	if(total == choosen){
		$('#searchBar #chooseall').addClass('badge-info');
	}else{
		$('#searchBar #chooseall').removeClass('badge-info');
	}
}

//重置所有查询条件
function resetCondition(){
	$('span.badge-info').removeClass('badge-info');
}

//选中全部查询条件
var count=0;
function selectAllCondition(){
	if(count%2 == 0){
		$('#searchBar .label').addClass('badge-info');
	}else{
		$('#searchBar .label').removeClass('badge-info');
	}
	count++;
}
