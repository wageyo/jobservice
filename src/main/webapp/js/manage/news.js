newsEditor = {};
newsEditor.newsId;
newsEditor.falg="null";//标志位对象
newsEditor.init=function(){
	//实例化编辑器
	UE.getEditor('news-editor');
	//判断是增加还是编辑
	newsEditor.newsId=$("#newsId").val();
	if(newsEditor.newsId ==undefined){
			alert("文章 初始化错误");
		
	}
	
	//增加文章
	if(newsEditor.newsId =="-1"){
			newsEditor.falg="add";
			
			 $("#newsEditor-title").val("");//标题
			 $("#newsEditor-author").val("");//作者
			$("#newsEditor-source").val("");//来源
			$("#newsEditor-type").val("direct");//类型
			
			
			$("#newSubmitBut").html("发布");
			$("#newsTitle").html("增加文章");
			
			
	//修改文章
	}else{
		newsEditor.falg="edit";
		$("#newSubmitBut").html("确定");
		$("#newsTitle").html("编辑文章");
		newsEditor.editInit(newsEditor.newsId);
		
	}
	
};
newsEditor.getContent = function() {
	return UE.getEditor('news-editor').getContent();
};

/*
 * 返回
 * */
newsEditor.back=function(){
	
	/*关闭编辑器*/
	UE.getEditor('news-editor').destroy();
	//关闭标签页
	view.tabs.removeTab("文章");
			
};
/*
 * 页面提交函数
 *    区分 增加文章还是编辑文章，并调用相应提交函数
 * */
newsEditor.submit=function(){
	
	/*增加*/
	if(newsEditor.falg=="add"){
		newsEditor.add();
	}
	/*编辑*/
	if(newsEditor.falg=="edit"){
		newsEditor.edit();
	}
	
	
	
}

/*
 * 增加
 * */
newsEditor.add = function() {
	//获取信息
	var params = {};
	params.title = $("#newsEditor-title").val();//标题
	params.author = $("#newsEditor-author").val();//作者
	params.source = $("#newsEditor-source").val();//来源
	params.type = $("#newsEditor-type").val();//类型
	params.content = newsEditor.getContent();//内容
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
		url : '/jobservice/manage/news_add',
		type : 'post',
		data : params,
		success : function(data) {
			if (data == true) {
				alert('增加成功');
				
				/*关闭编辑器*/
				UE.getEditor('news-editor').destroy();
				//刷新文章列表
				$('#news_grid').datagrid("load");
				//关闭当前页
				view.tabs.removeTab("文章");
			} else {
				alert('增加文章失败');
			}
		},
		error : function() {
			alert("增加文章请求失败");
		}
	});
};


/*编辑文章 初始化信息*/
newsEditor.editInit=function(id){
	
	//1.更改控件信息
	$("#newsTitle").val("编辑文章");
	
	//获取数据
	$.ajax({
		url : '/jobservice/manage/news_view',
		data:{'id':id},
		type:'post',
		success:function(data){
			$("#newsEditor-title").val(data.title);
			$("#newsEditor-author").val(data.author);
			$("#newsEditor-source").val(data.source);
			$("#newsEditor-type").val(data.type);
			UE.getEditor('news-editor').setContent(data.content, true);
		
		},error:function(){
			alert("修改文章 初始化时发生错误");
		}
	});

};


/*
 * 编辑
 * */
newsEditor.edit = function() {
	//获取信息
	var params = {};
	
	params.id = $("#newsId").val();//id
	params.title = $("#newsEditor-title").val();//标题
	params.author = $("#newsEditor-author").val();//作者
	params.source = $("#newsEditor-source").val();//来源
	params.type = $("#newsEditor-type").val();//类型
	params.content = newsEditor.getContent();//内容
	
	
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
				/*关闭编辑器*/
				UE.getEditor('news-editor').destroy();
				//刷新列表
				$('#news_grid').datagrid("load");
				//关闭标签页
				view.tabs.removeTab("文章");
			} else {
				alert('编辑文章失败');
			}
		},
		error : function() {
			alert("编辑文章请求失败");
		}
	});
};
