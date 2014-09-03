$(document).ready(function() {
	view.tabs.addIndexTab();
	view.bindMenu();

});

/**
 * 视图类
 */
var view = {};
/**
 * 绑定菜单事件
 */
view.bindMenu = function() {

	// 1.创建树形菜单
	$('#main_menu').tree({
		// 绑定单击事件
		onSelect : function(e, node) {
			
			if (e.attributes!= undefined) {
				// 页面单击，转到实体页面
				var _url = e.attributes.url;
				var _title = e.text;
				/* 添加标签页 */
				view.tabs.addTab(_title, _url, true);
			} else {
				// 文件夹单击，转到首页
				$('#tt').tabs('select', "首页");// 选中并刷新
			}
		}
	});

};

/**
 * 增加标签页函数
 * 
 * @param title
 * @param url
 */
view.tabs = {};
view.tabs.index = 0;
view.tabs.loading = '<div class="panel-loading">Loading...</div>';
/**
 * 新添加tab
 */
view.tabs.addTab = function(title, url, closable) {
	
	if ($('#tt').tabs('exists', title) == true) {
		$('#tt').tabs('select', title);
		view.tabs.update(url);
		return;
	}
	view.index++;
	$('#tt').tabs('add', {
		title : title,
		content : view.tabs.loading,
		closable : closable
	});
	view.tabs.update(url);
};
/**
 * 更新tab
 */
view.tabs.update = function(url) {
	var tab = $('#tt').tabs('getSelected');

	$.ajax({
		url : url,
		type : "GET",
		success : function(data) {
			
			$('#tt').tabs('update', {
				tab : tab,
				options : {
					content : data
				}
			});
		},
		error : function() {
			alert("菜单数据请求错误");
		},
		dataType : "html",
		async : false
	});

};
/**
 * 添加首页
 */
view.tabs.addIndexTab = function() {
	view.tabs.addTab("首页", "/jobservice/manage/prompt", false);

};

/**
 * 关闭所有tab
 */
view.tabs.removeAllTab = function() {
	var tab = $('#tt').tabs('getSelected');
	while (tab != null) {
		var index = $('#tt').tabs('getTabIndex', tab);
		$('#tt').tabs('close', index);
		var tab = $('#tt').tabs('getSelected');
	}
	view.tabs.addIndexTab();
};


/**
 * 关闭tab
 */
view.tabs.removeTab = function(title) {
	$('#tt').tabs('close', title);
};

/*
 * 关闭除当前之外的TAB
 */
view.tabs.notCurrent=function(){
	var prevall = $('.tabs-selected').prevAll();
	var nextall = $('.tabs-selected').nextAll();		
	if(prevall.length>0){
		prevall.each(function(i,n){
			var t=$('a:eq(0) span',$(n)).text();
			if(t != '首页') {
				$('#tt').tabs('close',t);
			}
		});
	}
	if(nextall.length>0) {
		nextall.each(function(i,n){
			var t=$('a:eq(0) span',$(n)).text();
			if(t != 'Home') {
				$('#tt').tabs('close',t);
			}
		});
	}
	
};
