<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
	$(function() {
		$('#user_grid').datagrid({
			url : '/jobservice/manage/user_list',
			iconCls : 'icon-save',
			pageSize : 30,
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
			columns : [ [ {
				field : 'id',
				title : 'i00',
				width : 100,
				hidden : 'true'//隐藏列
			}, {
				field : 'loginName',
				title : ' 账号',
				width : 100
				
			}, {
				field : 'passWord',
				title : '密码',
				width : 100
			} ,{
				field : 'identity',
				title : '账号类型',
				width : 100
			}
			, {
				field : 'action',
				title : '操作',
				width : 150,
				align : 'center',
				formatter : function(value, row, index) {
					var d = '<a href="javascript:void(0)" class="auditLink" onclick="userManager.singleAudit('+index+')">通过</a> ';
					var v = '<a href="javascript:void(0)" class="auditLink" onclick="userManager.singleRefusal(' + index + ')">拒绝通过</a> ';
					return  d + v;
				}
			} ] ],
			toolbar : '#user_boolbar'//指定自定义菜单
		});

	});

	userManager = {};
userManager.data;
userManager.jobid;
userManager.val;

	/* 
		查看企业信息框
	 */
	userManager.openViewCompany = function(index) {
			//取消选择所有中所有的行。
							$('#user_grid').datagrid("clearSelections");
							$('#user_grid').datagrid("selectRow", index);
							userManager.data = $('#user_grid').datagrid("getSelections");
							 userManager.jobid=userManager.data[0].id;
							 alert(userManager.jobid);
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
									url:'job/getOneForShowDate',
									type:'post',
									dataType:'json',
									data:{'id':'25'},
									success:function(data){
										if(data==undefined || data=='' || data==""  ||data=="null"){
											alert("用户详细页面初始化数据时发生错误");
											return null;
										}
										
										var json = eval(data);
										$("#name").html(json.name);//职位名称
										$("#hireNumber").html(json.hireNumber);//招聘人数
										$("#salary").html(json.salary);// 薪水
										$("#education").html(json.education);//最低学历
										$("#experience").html(json.experience);//要求的工作经验年限
										$("#gender").html(json.gender);//性别
										$("#description").html(json.description);// 岗位描述
										$("#provideBenefit").html(json.provideBenefit);// 工资和其他福利
										$("#contactPerson").html(json.contactPerson);//联系人
										$("#contactTel").html(json.contactTel);//联系电话
										$("#contactEmail").html(json.contactEmail);// 邮箱
										$("#nature").html(json.nature);// 职位性质
										$("#effectiveTime").html(json.effectiveTime);// 有效期
										$("#bed").html(json.bed);// 是否提供住宿
										$("#lunch").html(json.lunch);// 是否提供工作餐
										
									},error:function(){
										alert("查看用户详细页面初始化数据时发生错误.");
									}
								
								
								});
							
						}
				});
			
	};
	
	/* 
		审核 通过单个
	 */
	userManager.singleAudit= function(index) {
			$('#user_grid').datagrid("clearSelections");
			$('#user_grid').datagrid("selectRow", index);
			userManager.jobid = $('#user_grid').datagrid("getSelections")[0].id;
			
			if(userManager.jobid==undefined){
					alert("审核单个信息时 数据id获取失败");
			}else{
				//组装参数
				var params=new	Array();
				params.push(userManager.jobid );
				userManager.audit(params);
			}
			
	};
	
	
	/* 
		审核 批量通过
	 */
	userManager.batchAudit= function() {
			//获取所有选中列
		var selection = $("#user_grid").datagrid('getSelections');
		//判断选择数目是否大于0
		if(selection.length==0){
			alert("未选择任何数据。");
		}else{
		
		//显示确认删除对话框
			$.messager.confirm('确认','是否要批量审核通过'+selection.length+'条"账户数据"？',
				function(r){    
			    if (r){    
				    		var params = new Array();
							for(var i=0;i<selection.length;i++){
								params.push(selection[i].id);
							}
							userManager.audit(params);
			    }
			});  
		}
	
	};
	
	/* 
		审核 通过
	 */
	userManager.audit= function(params) {
	//取消选择所有中所有的行。
				$.ajax({
						url:'/jobservice/manage/audit_user',
						type:'post',
						data:{'params': params,'type':'ok'},
						success:function(data){
								if(data="true"){
										alert("操作成功");
										//刷新列表
										$('#user_grid').datagrid('reload'); 
									
								}else{
									alert("操作失败");
									//刷新列表
										$('#user_grid').datagrid('reload'); 
								}
								
								//清楚勾选
								$('#resume_grid').datagrid('clearSelections'); 
						},error:function(){
							alert("审核 通过时发生错误");
						}
				});
	};
	
	
	
	
	
	
	
	/* 
		审核 拒绝单个职位
	 */
	userManager.singleRefusal= function(index) {
			$('#user_grid').datagrid("clearSelections");
			$('#user_grid').datagrid("selectRow", index);
			userManager.jobid = $('#user_grid').datagrid("getSelections")[0].id;
			if(userManager.jobid==undefined){
					alert("审核单个信息时 数据id获取失败");
			}else{
				//组装参数
				var params=new	Array();
				params.push(userManager.jobid );
				userManager.refusal(params);
			}
			
	};
	
	
	/* 
		审核 批量拒绝职位
	 */
	userManager.batchRefusal= function() {
			//获取所有选中列
		var selection = $("#user_grid").datagrid('getSelections');
		//判断选择数目是否大于0
		if(selection.length==0){
			alert("未选择任何数据。");
		}else{
		
		//显示确认删除对话框
			$.messager.confirm('确认','是否要批量拒绝通过'+selection.length+'条"账户数据"？',
				function(r){    
			    if (r){    
				    		var params = new Array();
							for(var i=0;i<selection.length;i++){
								params.push(selection[i].id);
							}
							userManager.refusal(params);
			    }
			});  
		}
	
	};
	
	/* 
		审核 拒绝职位
	 */
	userManager.refusal= function(params) {
	//取消选择所有中所有的行。
				$.ajax({
						url:'/jobservice/manage/audit_user',
						type:'post',
						data:{'params': params,'type':'refusal'},
						success:function(data){
								if(data="true"){
										alert("操作成功");
										//刷新列表
										$('#user_grid').datagrid('reload'); 
									
								}else{
									alert("操作失败");
									//刷新列表
										$('#user_grid').datagrid('reload'); 
								}
								//清楚勾选
								$('#resume_grid').datagrid('clearSelections'); 
						},error:function(){
							alert("职位审核  发生错误");
						}
				});
	};
	
	
	
	
	
	
	
	
	
	
	
		/*
			查询条件
		*/
		$("#user_auditStatus").change(function(){
				
				userManager.val=$("#user_auditStatus").val();
				
				if(userManager.val=="daiShen"){
					//待审核
					$('#user_grid').datagrid('load',{
													type: 'daiShen'
												});
						//隐藏列
					$('#user_grid').datagrid('showColumn','action');		
					manageCommon.selectStatus1();					
												
				}
				
				//未通过
				if(userManager.val=="weiTongGuo"){
					//待审核
					$('#user_grid').datagrid('load',{
													type: 'weiTongGuo'
												});
						//隐藏列
					$('#user_grid').datagrid('hideColumn','action');
					manageCommon.selectStatus2();
				}
				
				if(userManager.val=="ok"){
					//通过审核
					$('#user_grid').datagrid('load',{
													type: 'ok'
												});
						//隐藏列
					$('#user_grid').datagrid('hideColumn','action');
					manageCommon.selectStatus3();
				}
	});	
	
</script>



<!-- 数据表格 -->
<table id="user_grid"></table>

<!-- 自定义菜单 -->
<div id="user_boolbar" class="manage" data-options="fit:false,doSize:true" style="white-space: nowrap;height: 50px;margin-top: 5px">
	<div class="audit-area"  >
				<div style="float: left;">
						<span class="audit-text" >审核数据状态：</span> 
						<select id="user_auditStatus" class="audit-select">
									<option value="daiShen">待审核</option>
									<option value="weiTongGuo">未通过</option>
									<option value="ok">已通过</option>
						</select>
			  </div>
			  <div style="float: right;">
			  
						<button class="batchAudit"  onclick="userManager.batchAudit()">通过</button>	
						<button class="batchRefusal" onclick="userManager.batchRefusal()">拒绝通过</button>	
						
			  </div>
	</div>
</div>


