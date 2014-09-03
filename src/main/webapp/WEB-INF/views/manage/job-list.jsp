<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
	$(function() {
		$('#job_grid').datagrid({
			url : '/jobservice/manage/job_list',
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
						name: 'easyui',
						
			},
			columns : [ [ {
				field : 'id',
				title : 'i00',
				width : 100,
				hidden : 'true'//隐藏列
			}, {
				field : 'name',
				title : '职位名称',
				width : 150,
				formatter : function(value, row, index) {
					var c = '<a class="name-link" href="javascript:void(0)" onclick="jobManager.openViewCompany(' + index + ')">' + value + '</a>';
					return c;
				}
			}, {
				field : 'hireNumber',
				title : '人数',
				width : 100
			} ,{
				field : 'salary',
				title : '薪水',
				width : 100
			}, {
				field : 'gender',
				title : '性别',
				width : 100
			} ,{
				field : 'nature',
				title : '职位性质',
				width : 100
			} ,{
				field : 'createDate',
				title : '发布日期',
				width : 100
			}
			 ,{
				field : 'effectiveTime',
				title : '有效期(天)',
				width : 100
			}
			, {
				field : 'action',
				title : '操作',
				width : 150,
				align : 'center',
				formatter : function(value, row, index) {
					var d = '<a href="javascript:void(0)" class="auditLink" onclick="jobManager.singleAudit('+index+')">通过</a> ';
					var v = '<a href="javascript:void(0)" class="auditLink" onclick="jobManager.singleRefusal(' + index + ')">拒绝通过</a> ';
					//var o = '<a href="javascript:void(0)" class="auditLink" onclick="jobManager.singleExport(' + index + ')">导出</a> ';
					return  d + v;
				}
			} ] ],
			toolbar : '#job_boolbar'//指定自定义菜单
		});
	
	});

	jobManager = {};
	jobManager.jobid;
	jobManager.val;
	/* 
		查看企业信息框
	 */
	 
	jobManager.openViewCompany = function(index) {
			//取消选择所有中所有的行。
			$('#job_grid').datagrid("clearSelections");
			$('#job_grid').datagrid("selectRow", index);
			jobManager.jobid = $('#job_grid').datagrid("getSelections")[0].id;
						
					
				$('#viewJobPanel').window({
					width : 1100,
					href : '/jobservice/manage/job_view',
					height : 650,
					title : '查看职位信息',
					minimizable : false,
					loadingMessage : '正在加载，请稍后。',
					iconCls : 'icon-save',
					modal:true//模态窗口
					,onLoad:function(){
							$.ajax({
									url:'/jobservice/manage/getOneForShowDate',
									type:'post',
									dataType:'json',
									data:{'id': jobManager.jobid},
									success:function(data){
										if(data==undefined || data=='' || data==""  ||data=="null"){
											alert("数据有误");
											return null;
										}
										
										var json = eval(data);
										$("#job-view #name").html(json.name);//职位名称
										$("#job-view #hireNumber").html(json.hireNumber);//招聘人数
										$("#job-view #salary").html(json.salary);// 薪水
										$("#job-view #education").html(json.education);//最低学历
										$("#job-view #experience").html(json.experience);//要求的工作经验年限
										$("#job-view #gender").html(json.gender);//性别
										$("#job-view #description").html(json.description);// 岗位描述
										$("#job-view #provideBenefit").html(json.provideBenefit);// 工资和其他福利
										$("#job-view #contactPerson").html(json.contactPerson);//联系人
										$("#job-view #contactTel").html(json.contactTel);//联系电话
										$("#job-view #contactEmail").html(json.contactEmail);// 邮箱
										$("#job-view #nature").html(json.nature);// 职位性质
										
										$("#job-view #createDate").html(json.createDate);// 发布日期
										$("#job-view #effectiveTime").html(json.effectiveTime);// 有效期
									
										if(json.bed==true){
												
												$("#job-view #bed").html("提供");// 是否提供住宿
										}else{
												$("#job-view #bed").html("不提供");//  是否提供住宿
										}
										
										if(json.lunch==true){
												$("#job-view #lunch").html("提供");// 是否提供工作餐
										}else{
												$("#job-view #lunch").html("不提供");// 是否提供工作餐
										}
									},error:function(){
										alert("查看简历详细页面初始化数据时发生错误.");
									}
								});
						}
				});
	};
	
	/* 
		审核 通过单个
	 */
	jobManager.singleAudit= function(index) {
			$('#job_grid').datagrid("clearSelections");
			$('#job_grid').datagrid("selectRow", index);
			jobManager.jobid = $('#job_grid').datagrid("getSelections")[0].id;
			
			if(jobManager.jobid==undefined){
					alert("审核单个信息时 数据id获取失败");
			}else{
				//组装参数
				var params=new	Array();
				params.push(jobManager.jobid );
				jobManager.audit(params);
			}
			
	};
	
	
	/* 
		审核 批量通过
	 */
	jobManager.batchAudit= function() {
			//获取所有选中列
		var selection = $("#job_grid").datagrid('getSelections');
		//判断选择数目是否大于0
		if(selection.length==0){
			alert("未选择任何数据。");
		}else{
		
		//显示确认删除对话框
			$.messager.confirm('确认','是否要批量审核通过'+selection.length+'条"职位数据"？',
				function(r){    
			    if (r){    
				    		var params = new Array();
							for(var i=0;i<selection.length;i++){
								params.push(selection[i].id);
							}
							jobManager.audit(params);
			    }
			});  
		}
	
	};
	
	/* 
		审核 通过
	 */
	jobManager.audit= function(params) {
	//取消选择所有中所有的行。
				$.ajax({
						url:'/jobservice/manage/audit',
						type:'post',
						data:{'params': params,'type':'ok'},
						success:function(data){
								if(data="true"){
										alert("操作成功");
										//刷新列表
										$('#job_grid').datagrid('reload'); 
									
								}else{
									alert("操作失败");
									//刷新列表
										$('#job_grid').datagrid('reload'); 
								}
								//清楚勾选
								$('#job_grid').datagrid('clearSelections'); 
								
						},error:function(){
							alert("审核 通过时发生错误");
						}
				});
	};
	
	/* 
		审核 拒绝单个职位
	 */
	jobManager.singleRefusal= function(index) {
			$('#job_grid').datagrid("clearSelections");
			$('#job_grid').datagrid("selectRow", index);
			jobManager.jobid = $('#job_grid').datagrid("getSelections")[0].id;
			if(jobManager.jobid==undefined){
					alert("审核单个信息时 数据id获取失败");
			}else{
				//组装参数
				var params=new	Array();
				params.push(jobManager.jobid );
				jobManager.refusal(params);
			}
			
	};
	
	
	/* 
		审核 批量拒绝职位
	 */
	jobManager.batchRefusal= function() {
			//获取所有选中列
		var selection = $("#job_grid").datagrid('getSelections');
		//判断选择数目是否大于0
		if(selection.length==0){
			alert("未选择任何数据。");
		}else{
		
		//显示确认删除对话框
			$.messager.confirm('确认','是否要批量拒绝通过'+selection.length+'条"职位数据"？',
				function(r){    
			    if (r){    
				    		var params = new Array();
							for(var i=0;i<selection.length;i++){
								params.push(selection[i].id);
							}
							jobManager.refusal(params);
			    }
			});  
		}
	
	};
	
	/* 
		审核 拒绝职位
	 */
	jobManager.refusal= function(params) {
	//取消选择所有中所有的行。
				$.ajax({
						url:'/jobservice/manage/audit',
						type:'post',
						data:{'params': params,'type':'refusal'},
						success:function(data){
								if(data="true"){
										alert("操作成功");
										//刷新列表
										$('#job_grid').datagrid('reload'); 
									
								}else{
									alert("操作失败");
									//刷新列表
										$('#job_grid').datagrid('reload'); 
								}
								//清楚勾选
								$('#job_grid').datagrid('clearSelections'); 
						},error:function(){
							alert("职位审核  发生错误");
						}
				});
	};
	
	
	
	
		/*
		查询条件
		*/
		$("#jobManager_auditStatus").change(function(){
		
				jobManager.val=$("#jobManager_auditStatus").val();
				
				if(jobManager.val=="daiShen"){
					//待审核
					$('#job_grid').datagrid('load',{
													type: 'daiShen'
												});
						//隐藏列
					$('#job_grid').datagrid('showColumn','action');							
					manageCommon.selectStatus1();
													
				}
				
				//未通过
				if(jobManager.val=="weiTongGuo"){
					//待审核
					$('#job_grid').datagrid('load',{
													type: 'weiTongGuo'
												});
						//隐藏列
					$('#job_grid').datagrid('hideColumn','action');
					manageCommon.selectStatus2();
				}
				
				if(jobManager.val=="ok"){
					//通过审核
					$('#job_grid').datagrid('load',{
													type: 'ok'
												});
						//隐藏列
					$('#job_grid').datagrid('hideColumn','action');
					manageCommon.selectStatus3();
					
				}
	});	
	
	
	
	/*
		批量导出
	 */
	jobManager.BatchExport= function() {
		//获取所有选中列
		var selection = $("#job_grid").datagrid('getSelections');
		//判断选择数目是否大于0
		if(selection.length==0){
			alert("未选择任何数据。");
		}else{
		
		//显示确认删除对话框
			$.messager.confirm('确认','是否要批量导出'+selection.length+'条"职位数据"？',
				function(r){    
			    if (r){    
				    		var params = new Array();
							for(var i=0;i<selection.length;i++){
								params.push(selection[i].id);
							}
							//调用导出
							jobManager.exportData(params);
			    }
			});  
		}
	};
	/*
		单个导出
	 */
	jobManager.singleExport= function(index) {
		//取消选择所有中所有的行。
		$('#job_grid').datagrid("clearSelections");
		$('#job_grid').datagrid("selectRow", index);
		jobManager.jobid = $('#job_grid').datagrid("getSelections")[0].id;
		
		var params = new Array();
		params.push(jobManager.jobid);
		
		//调用导出
		jobManager.exportData(params);
	};
	
	/* 
		导出职位
	 */
	jobManager.exportData= function(params) {
			$.ajax({
						url:'/jobservice/manage/export-job',
						type:'post',
						data:{'params':params},
						success:function(data){
							window.open(data);
							//清楚勾选
							$('#job_grid').datagrid('clearSelections'); 
						},error:function(){
							alert("导出时发生错误");
						}
				});
	};
	
</script>



<!-- 数据表格 -->
<table id="job_grid"></table>

<!-- 自定义菜单 -->
<div id="job_boolbar" class="manage" data-options="fit:false,doSize:true" style="white-space: nowrap;height: 50px;margin-top: 5px">
	
	<div class="audit-area"  >
				<div style="float: left;">
						<span class="audit-text" >审核数据状态：</span> 
						<select id="jobManager_auditStatus" class="audit-select">
									<option value="daiShen">待审核</option>
									<option value="weiTongGuo">未通过</option>
									<option value="ok">已通过</option>
						</select>
			  </div>
			  <div style="float: right;">
			  
						<button class="batchAudit" onclick="jobManager.batchAudit()">通过</button>	
						<button class="batchRefusal" onclick="jobManager.batchRefusal()">拒绝通过</button>	
						<!-- 
						<button onclick="jobManager.BatchExport()">批量导出</button>	
						 -->
			  </div>
	</div>
	
</div>

<!-- 信息窗口 -->
<div id="viewJobPanel"></div>



