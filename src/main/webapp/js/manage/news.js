/***********************************************
 *******  后台管理页面框架脚本--用户管理  ********
 ***********************************************/

//收集参数并进入后台查询方法
function query(page,articleType){
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
	if(articleType == null || articleType == undefined || articleType == ''){
		var articleType = $('#articleType').val();
		paramStr += '&articleType=' + articleType; 
	}else{
		paramStr += '&articleType=' + articleType; 
	}
	// 跳转提交
	window.location.href = getRootPath() + '/manage/news/news_list?' + paramStr;
}
