<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />



<script type="text/javascript">
	$(function() {
	
		$('#adminUser_grid').datagrid({
			url : '/jobservice/manage/super_list',
			iconCls : 'icon-save',
			pageSize :30,
			pageList : [30, 50, 100,200],
			border:false,//不显示边框
			pagination : true, //是否显示分页栏 
			doSize : true,//在面板被创建的时候将重置大小和重新布局。
			fit : true, //面板大小将自适应父容器
			fitColumns : true,//是否显示横向滚动条，未生效
			nowrap : true,//如果为true，则在同一行中 。设置为true可以提高加载性能 
			rownumbers : true,//显示行号
			striped : true,//奇偶行使用不同背景色
		    checkOnSelect:true,//单击不选择复选框
		    frozenColumns : [ [ {//复选框
				field : 'sid',
				checkbox : true
			}, ] ],
			queryParams: {//发送额外的参数
					type: 'direct'
			},
			columns : [ [ {
				field : 'id',
				title : 'i00',
				width : 100,
				hidden : 'true'//隐藏列
			},
			 {
				field : 'loginname',
				title : '账户',
				width : 200
			},{
				field : 'pwd',
				title : '密码',
				width : 150}
			, 
			 {
				field : 'nick',
				title : '管理员名称',
				width : 200},
			{
				field : 'title',
				title : '头部标题',
				width : 350}	, 
			{
				field : 'area',
				title : '地区',
				width : 150}
			, 
			{
				field : 'action',
				title : '操作',
				width : 150,
				align : 'center',
				formatter : function(value, row, index) {
					var d = '<a href="javascript:void(0)" class="auditLink" onclick="adminUser.openEditNews('+index+')">编辑</a> ';
					var v = '<a href="javascript:void(0)" class="auditLink" onclick="adminUser.singleDel(' + index + ')">删除</a> ';
					return  d + v;
				}
			} ] ],
			toolbar : '#adminUser_boolbar'//指定自定义菜单
		});
	});

	adminUser = {};
	adminUser.id;
	adminUser.val;
	
	
	/* 
			打开增加文章窗口
	 */
	adminUser.openAddAdminUser= function() {
			$('#adminUserPanel').window({
				width : 1000,
				href : '/jobservice/manage/adminUser_add',
				height : 500,
				title : '增加管理员用户',
				minimizable : false,
				loadingMessage : '正在加载，请稍后。',
				iconCls : 'icon-save',
				modal : true//模态窗口
		});
	};
	
	
	/* 
			打开编辑文章窗口
	 */
	adminUser.openEditNews= function(index) {
		$('#adminUser_grid').datagrid("clearSelections");
		$('#adminUser_grid').datagrid("selectRow", index);
		adminUser.id = $('#adminUser_grid').datagrid("getSelections")[0].id;
			
		$('#adminUserPanel').window({
				width : 1000,
				href : '/jobservice/manage/adminUser_edit/'+adminUser.id,
				height : 500,
				title : '编辑管理员用户',
				minimizable : false,
				loadingMessage : '正在加载，请稍后。',
				iconCls : 'icon-edit',
				modal : true//模态窗口
		});
		
	
	};
	
	
	
	
	
	/**
	 **批量删除
	 */
	adminUser.batchDel= function() {
			//获取所有选中列
		var selection = $("#adminUser_grid").datagrid('getSelections');
		//判断选择数目是否大于0
		if(selection.length==0){
			alert("未选择任何数据。");
		}else{
		
		//显示确认删除对话框
			$.messager.confirm('确认','是否要批量删除'+selection.length+'条数据？',
				function(r){    
			    if (r){    
				    		var params = new Array();
							for(var i=0;i<selection.length;i++){
								params.push(selection[i].id);
							}
							//调用删除
							adminUser.del(params);
			    }
			});  
		}
	
	};
	
	
	/* 
	删除单个文章
	 */
	adminUser.singleDel= function(index) {
		
			
		$('#adminUser_grid').datagrid("clearSelections");
		$('#adminUser_grid').datagrid("selectRow", index);
		adminUser.id = $('#adminUser_grid').datagrid("getSelections")[0].id;
		$.messager.confirm('确认','是否要删除？',
				function(r){    
			    if (r){    
				    		var params = new Array();
							params.push(adminUser.id);
							//调用删除
							adminUser.del(params);
			    }
			});  
	};
	
	/*
		删除文章
	*/
	adminUser.del=function(params){
					$.ajax({
						url:'/jobservice/manage/adminuser_del',
						type:'post',
						data:{'params':params},
						success:function(data){
							if(data==false){
								alert("管理员用户删除失败");
							}
								$('#adminUser_grid').datagrid("load");
								//清楚勾选
								$('#adminUser_grid').datagrid('clearSelections'); 
						},error:function(){
							alert("管理员用户删除时发生错误");
						}
				});
	};
	


		
	
	


	
</script>



<!-- 数据表格 -->
<table id="adminUser_grid"></table>

<!-- 自定义菜单 -->
<div id="adminUser_boolbar" class="manage" data-options="fit:false,doSize:true" style="white-space: nowrap;height: 50px;margin-top: 5px">

	<div class="audit-area">
		<div style="float: right;">
			<button onclick="adminUser.openAddAdminUser()">增加</button>
			<button onclick="adminUser.batchDel()">删除</button>
		</div>
	</div>

</div>

<!-- 信息窗口 -->
<div id="adminUserPanel"></div>










