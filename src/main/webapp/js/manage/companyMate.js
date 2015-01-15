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

	
	checkStatus=$(checkStatus).val();
	paramStr += checkStatus; 
	
	var resumeMatchName=$('#companyMatchName').val();
	if(resumeMatchName != null && resumeMatchName != undefined && resumeMatchName != ''){
		
		resumeMatchName=resumeMatchName.substring(0,resumeMatchName.length-1) 
		
	}
	paramStr +="&companyMat="+resumeMatchName;
    window.location.href = getRootPath() + '/manage/company/company_mate_list?' + paramStr;

}
//查看自动匹配数据
function lookJobData(i,id){
	var paramStr = '';
	paramStr += 'id='+id; 
	var	companyMatch=$('#companyMatch').val();
	var resumeMatchName=$('#companyMatchName').val();
	if(companyMatch != null && companyMatch != undefined && companyMatch != ''){
		
		paramStr += companyMatch; 
		
	
		
		if(resumeMatchName != null && resumeMatchName != undefined && resumeMatchName != ''){
			
			resumeMatchName=resumeMatchName.substring(0,resumeMatchName.length-1) 
			
		}
		paramStr +="&companyMat="+resumeMatchName;
		var url = getRootPath() + '/manage/company/company_mate_json?' + paramStr;
		$.ajax({
			url : url,
			type : 'GET',
			success : function(e) {
				$('#main'+id).html(e);
				$('#data'+id).fadeIn();
			},
			dataType : 'html',
			async : false
		});
	}else{
		alert("请选择匹配百分比");
	}

	
//	 $("#tr"+id).show();
//	 $("#tb"+id).show();

}
//隐藏自动匹配数据
function hideJobData(id){
	
//	 $("#tr"+id).hide();
	 $("#data"+id).hide();
}
//参数下拉框点击事件
function selectButtonPer(valueButton,resumeMatchName, nameButton, value, name){
	//给对应控件 赋value值
	$('#'+valueButton).val(value);
	//给对应控件 赋name值
	$('#'+nameButton).text(name);
	$('#'+resumeMatchName).val(name);
}
