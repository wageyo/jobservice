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
	
	var resumeMatchName=$('#resumeMatchName').val();
	if(resumeMatchName != null && resumeMatchName != undefined && resumeMatchName != ''){
		
		resumeMatchName=resumeMatchName.substring(0,resumeMatchName.length-1) 
		
	}
	paramStr +="&resumeMat="+resumeMatchName;
    window.location.href = getRootPath() + '/manage/resume/resume_mate_list1?' + paramStr;

}
//查看自动匹配数据
function lookJobData(i){
	//给对应控件 赋value值
//	document.getElementById("lookJobDataCss").style.display="none";
	
	 $("#tr"+i).show();
	 $("#tb"+i).show();
//	$(lookJobDataCss).style.display="none";
	//给对应控件 赋name值
//	$('#'+nameButton).text(name);
}
//隐藏自动匹配数据
function hideJobData(i){
	
	 $("#tr"+i).hide();
	 $("#tb"+i).hide();
}
//参数下拉框点击事件
function selectButtonPer(valueButton,resumeMatchName, nameButton, value, name){
	//给对应控件 赋value值
	$('#'+valueButton).val(value);
	//给对应控件 赋name值
	$('#'+nameButton).text(name);
	$('#'+resumeMatchName).val(name);
}


