<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />



<script type="text/javascript">
	$(function() {

		$('#news_grid').datagrid({
			url : '/jobservice/manage/news_list',
			iconCls : 'icon-save',
			pageSize : 30,
			pageList : [ 30, 50, 100, 200 ],
			border : false,//不显示边框
			pagination : true, //是否显示分页栏 
			doSize : true,//在面板被创建的时候将重置大小和重新布局。
			fit : true, //面板大小将自适应父容器
			fitColumns : true,//是否显示横向滚动条，未生效
			nowrap : true,//如果为true，则在同一行中 。设置为true可以提高加载性能 
			rownumbers : true,//显示行号
			striped : true,//奇偶行使用不同背景色
			checkOnSelect : true,//单击不选择复选框
			frozenColumns : [ [ {//复选框
				field : 'sid',
				checkbox : true
			}, ] ],
			queryParams : {//发送额外的参数
				type : 'direct'
			},
			columns : [ [ {
				field : 'id',
				title : 'i00',
				width : 100,
				hidden : 'true'//隐藏列
			}, {
				field : 'title',
				title : '标题',
				width : 300,
				formatter : function(value, row, index) {
					var c = '<a class="name-link" href="javascript:void(0)" onclick="newsManager.openViewNews(' + index + ')">' + value + '</a>';
					return c;
				}
			}, {
				field : 'author',
				title : '作者',
				width : 150
			}, {
				field : 'type',
				title : '文章类型',
				width : 150
			}, {
				field : 'source',
				title : '来源',
				width : 150
			}, {
				field : 'action',
				title : '操作',
				width : 150,
				align : 'center',
				formatter : function(value, row, index) {
					var d = '<a href="javascript:void(0)" class="auditLink" onclick="newsManager.openEditNews(' + index + ')">编辑</a> ';
					var v = '<a href="javascript:void(0)" class="auditLink" onclick="newsManager.singleDel(' + index + ')">删除</a> ';
					return d + v;
				}
			} ] ],
			toolbar : '#news_boolbar'//指定自定义菜单
		});
	});

	newsManager = {};
	newsManager.id;
	newsManager.val;

	/* 
			打开增加文章窗口
	 */
	newsManager.openAddNews = function() {
		/* 添加标签页 */
		view.tabs.addTab("文章", '/jobservice/manage/news_add/-1', true);
	};
	/* 
			打开显示文章窗口
	 */
	newsManager.openViewNews = function(index) {
		$('#news_grid').datagrid("clearSelections");
		$('#news_grid').datagrid("selectRow", index);
		newsManager.id = $('#news_grid').datagrid("getSelections")[0].id;

		$('#newsPanel').window({
			width : 1100,
			href : '/jobservice/manage/news_view',
			height : 650,
			title : '查看文章信息',
			minimizable : false,
			loadingMessage : '正在加载，请稍后。',
			iconCls : 'icon-save',
			modal : true//模态窗口
			,
			onLoad : function() {
				//获取数据
				$.ajax({
					url : '/jobservice/manage/news_view',
					data : {
						'id' : newsManager.id
					},
					type : 'post',
					success : function(data) {
						$("#news-view-title").html(data.title);
						$("#news-view-author").html(data.author);
						$("#news-view-source").html(data.source);

						if (data.type == "direct") {
							$("#news-view-type").html("就业指导");
						}
						if (data.type == "news") {
							$("#news-view-type").html("最新资讯");
						}

						$("#news-view-createDate").html(data.createDate);
						$("#news-view-content").html(data.content);

					},
					error : function() {
						alert("查看文章初始化时发生错误");
					}
				});

			}
		});

	};

	/* 
			打开编辑文章窗口
	 */
	newsManager.openEditNews = function(index) {

		$('#news_grid').datagrid("clearSelections");
		$('#news_grid').datagrid("selectRow", index);
		newsManager.id = $('#news_grid').datagrid("getSelections")[0].id;

		/* 添加标签页 */
		view.tabs.addTab("文章", '/jobservice/manage/news_add/' + newsManager.id, true);

	};

	/* 
		批量删除
	 */
	newsManager.batchDel = function() {
		//获取所有选中列
		var selection = $("#news_grid").datagrid('getSelections');
		//判断选择数目是否大于0
		if (selection.length == 0) {
			alert("未选择任何数据。");
		} else {

			//显示确认删除对话框
			$.messager.confirm('确认', '是否要批量删除' + selection.length + '条数据？', function(r) {
				if (r) {
					var params = new Array();
					for ( var i = 0; i < selection.length; i++) {
						params.push(selection[i].id);
					}

					//调用删除
					newsManager.del(params);
				}
			});
		}

	};

	/* 
	删除单个文章
	 */
	newsManager.singleDel = function(index) {

		$('#news_grid').datagrid("clearSelections");
		$('#news_grid').datagrid("selectRow", index);
		newsManager.id = $('#news_grid').datagrid("getSelections")[0].id;
		$.messager.confirm('确认', '是否要删除？', function(r) {
			if (r) {
				var params = new Array();
				params.push(newsManager.id);

				//调用删除
				newsManager.del(params);
			}
		});

	};

	/*
		删除文章
	 */
	newsManager.del = function(params) {
		$.ajax({
			url : '/jobservice/manage/news_del',
			type : 'post',
			data : {
				'params' : params
			},
			success : function(data) {
				if (data == false) {
					alert("文章删除失败");
				}
				$('#news_grid').datagrid("load");
				//清楚勾选
				$('#news_grid').datagrid('clearSelections');
			},
			error : function() {
				alert("删除文章时发生错误");
			}
		});
	};

	/*
	查询条件
	 */
	$("#newsManager_newsType").change(function() {
		newsManager.val = $("#newsManager_newsType").val();
		if (newsManager.val == "direct") {
			//就业指导
			$('#news_grid').datagrid('load', {
				type : 'direct'
			});
		}
		//最新资讯
		if (newsManager.val == "news") {
			//最新资讯
			$('#news_grid').datagrid('load', {
				type : 'news'
			});
		}

	});
</script>



<!-- 数据表格 -->
<table id="news_grid"></table>

<!-- 自定义菜单 -->
<div id="news_boolbar" class="manage" data-options="fit:false,doSize:true" style="white-space: nowrap;height: 50px;margin-top: 5px">

	<div class="audit-area">
		<div style="float: left;">
			<span class="audit-text">文章类型：</span> <select id="newsManager_newsType" class="audit-select">
				<option value="direct">就业指导</option>
				<option value="news">最新资讯</option>
			</select>
		</div>
		<div style="float: right;">

			<button  onclick="newsManager.openAddNews()">增加</button>
			<button onclick="newsManager.batchDel()">删除</button>

		</div>
	</div>

</div>

<!-- 信息窗口 -->
<div id="newsPanel"></div>

