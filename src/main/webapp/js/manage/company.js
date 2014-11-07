/***********************************************
 *******  后台管理页面框架脚本--企业管理  ********
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
	var targetName = $('#targetName').val();
	if(targetName != null && targetName != undefined && targetName != ''){
		paramStr += '&targetName=' + targetName; 
	}
	if(checkStatus == null || checkStatus == undefined || checkStatus == ''){
		var checkStatus = $('#checkStatus').val();
		paramStr += '&checkStatus=' + checkStatus; 
	}else{
		paramStr += '&checkStatus=' + checkStatus; 
	}
	// 跳转提交
	window.location.href = getRootPath() + '/manage/company/company_list?' + paramStr;
}
