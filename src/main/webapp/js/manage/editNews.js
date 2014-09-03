editNews = {};

editNews.init=function(){
	//实例化编辑器
	alert("editNews");
	 

};
editNews.getContent = function() {
	return UE.getEditor('news-edit-editor').getContent();
};
editNews.edit = function() {
	//获取信息
	var params = {};
	
	params.id = $("#editNewsId").val();//id
	params.title = $("#editNews-title").val();//标题
	params.author = $("#editNews-author").val();//作者
	params.source = $("#editNews-source").val();//来源
	params.type = $("#editNews-type").val();//类型
	params.content = editNews.getContent();//内容
	
	
	if(params.id==""){
		alert("id获取失败。");
		return;
	}
	if (params.title == "") {
		alert("标题为空");
		return;
	}
	if (params.author == "") {
		alert("作者为空");
		return;
	}
	if (params.content == "") {
		alert("内容为空");
		return;
	}
	$.ajax({
		url : '/jobservice/manage/news_edit',
		type : 'post',
		data : params,
		success : function(data) {
			if (data == true) {
				alert('文章编辑成功');
				//刷新列表
				$('#news_grid').datagrid("load");
				//关闭标签页
				view.tabs.removeTab("编辑文章");
			} else {
				alert('编辑文章失败');
			}
		},
		error : function() {
			alert("编辑文章请求失败");
		}
	});
};
