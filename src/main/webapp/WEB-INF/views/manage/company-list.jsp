<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />


<script type="text/javascript">
	$(function() {
		$('#company_grid').datagrid({
			url : '/jobservice/manage/company_list',
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
				field : 'name',
				title : '公司名称',
				width : 200,
				formatter : function(value, row, index) {
					var c = '<a class="name-link" href="javascript:void(0)" onclick="companyManager.openViewCompany(' + index + ')">' + value + '</a>';
					return c;
				}
			}, {
				field : 'corporateRepresentative',
				title : '法人',
				width : 100
			} ,{
				field : 'contactPerson',
				title : '联系人',
				width : 100
			}, {
				field : 'nature',
				title : '企业性质',
				width : 100
			} ,{
				field : 'taxCode',
				title : '税务编码',
				width : 100
			} ,{
				field : 'economyType',
				title : '经济类型',
				width : 100
			}
			 ,{
				field : 'area',
				title : '所在地区',
				width : 100
			}
			
			, {
				field : 'action',
				title : '操作',
				width : 150,
				align : 'center',
				formatter : function(value, row, index) {
					var d = '<a href="javascript:void(0)" class="auditLink" onclick="companyManager.singleAudit('+index+')">通过</a> ';
					var v = '<a href="javascript:void(0)" class="auditLink" onclick="companyManager.singleRefusal(' + index + ')">拒绝通过</a> ';
						var o = '<a href="javascript:void(0)" class="auditLink" onclick="companyManager.singleExport(' + index + ')">导出</a> ';
					return  d + v+o;
				}
			} ] ],
			toolbar : '#companymanager_boolbar'//指定自定义菜单
		});

	});

	companyManager = {};
companyManager.data;
companyManager.jobid;
companyManager.val;

	/* 
		查看企业信息框
	 */
	companyManager.openViewCompany = function(index) {
			//取消选择所有中所有的行。
				$('#company_grid').datagrid("clearSelections");
			    $('#company_grid').datagrid("selectRow", index);
				companyManager.jobid = $('#company_grid').datagrid("getSelections")[0].id;
				$('#viewCompanyPanel').window({
					width : 1100,
					href : '/jobservice/manage/company_view',
					height : 650,
					title : '查看企业信息',
					minimizable : false,
					loadingMessage : '正在加载，请稍后。',
					iconCls : 'icon-save',
					modal:true//模态窗口
					,onLoad:function(){
							//调用信息初始化函数
				
							companyManager.viewInit(companyManager.jobid);
							
						}
				});
	};
	/* 
		显示信息初始化
	 */
	companyManager.viewInit=function(index){
			$.ajax({
						url:'/jobservice/manage/getOneForCompanyDate',
						type:'post',
						dataType:'json',
						data:{'id':index},
						success:function(data){
						
										if(data==undefined || data=='' || data==""  || data=="null"){
											alert("显示企业详细信息时，返回数据有误");
											return null;
										}
										var json = eval(data);
										$("#company-view #name").html(json.name);//职位名称
										$("#company-view #corporateRepresentative").html(json.corporateRepresentative);//法人
										$("#company-view #contactPerson").html(json.contactPerson);// 联系人
										$("#company-view #telephone").html(json.telephone);//联系电话
										$("#company-view #contactDept").html(json.contactDept);//联系部门
										$("#company-view #fax").html(json.fax);// 传真
										$("#company-view #email").html(json.email);// 公司邮箱
										$("#company-view #address").html(json.address);// 公司详细地址
										$("#company-view #introduction").html(json.introduction);//公司简介
										$("#company-view #organizationCode").html(json.organizationCode);//组织机构代码
										$("#company-view #commercialCode").html(json.commercialCode);// 工商登记号码
										$("#company-view #taxCode").html(json.taxCode);// 税务编码
										$("#company-view #socialSecurityCode").html(json.socialSecurityCode);// 社保登记证号
										$("#company-view #webSiteId").html(json.webSiteId);// 网站ID
										$("#company-view #laoWangCode").html(json.laoWangCode);// 市劳网号
										$("#company-view #scale").html(json.scale);// 企业规模
										$("#company-view #nature").html(json.nature);// 企业性质
										$("#company-view #economyType").html(json.economyType);// 经济类型
										$("#company-view #remark").html(json.remark);// 备注
										
									},error:function(){
										alert("查看简历详细页面初始化数据时发生错误.");
									}
								});
	};
	
	
	/* 
		审核 通过单个
	 */
	companyManager.singleAudit= function(index) {
			$('#company_grid').datagrid("clearSelections");
			$('#company_grid').datagrid("selectRow", index);
			companyManager.jobid = $('#company_grid').datagrid("getSelections")[0].id;
			
			if(companyManager.jobid==undefined){
					alert("审核单个信息时 数据id获取失败");
			}else{
				//组装参数
				var params=new	Array();
				params.push(companyManager.jobid );
				companyManager.audit(params);
			}
			
	};
	
	
	/* 
		审核 批量通过
	 */
	companyManager.batchAudit= function() {
			//获取所有选中列
		var selection = $("#company_grid").datagrid('getSelections');
		//判断选择数目是否大于0
		if(selection.length==0){
			alert("未选择任何数据。");
		}else{
		
		//显示确认删除对话框
			$.messager.confirm('确认','是否要”批量审核通过“'+selection.length+'条"企业信息数据"？',
				function(r){    
			    if (r){    
				    		var params = new Array();
							for(var i=0;i<selection.length;i++){
								params.push(selection[i].id);
							}
							companyManager.audit(params);
			    }
			});  
		}
	
	};
	
	/* 
		审核 通过
	 */
	companyManager.audit= function(params) {
	//取消选择所有中所有的行。
				$.ajax({
						url:'/jobservice/manage/audit_company',
						type:'post',
						data:{'params': params,'type':'ok'},
						success:function(data){
								if(data="true"){
										alert("操作成功");
										//刷新列表
										$('#company_grid').datagrid('reload'); 
									
								}else{
									alert("操作失败");
									//刷新列表
										$('#company_grid').datagrid('reload'); 
								}
								
								//清楚勾选
								$('#company_grid').datagrid('clearSelections'); 
						},error:function(){
							alert("审核 通过时发生错误");
						}
				});
	};
	
	
	
	
	
	
	
	/* 
		审核 拒绝单个职位
	 */
	companyManager.singleRefusal= function(index) {
			$('#company_grid').datagrid("clearSelections");
			$('#company_grid').datagrid("selectRow", index);
			companyManager.jobid = $('#company_grid').datagrid("getSelections")[0].id;
			if(companyManager.jobid==undefined){
					alert("审核单个信息时 数据id获取失败");
			}else{
				//组装参数
				var params=new	Array();
				params.push(companyManager.jobid );
				companyManager.refusal(params);
			}
			
	};
	
	
	/* 
		审核 批量拒绝职位
	 */
	companyManager.batchRefusal= function() {
			//获取所有选中列
		var selection = $("#company_grid").datagrid('getSelections');
		//判断选择数目是否大于0
		if(selection.length==0){
			alert("未选择任何数据。");
		}else{
		
		//显示确认删除对话框
			$.messager.confirm('确认','是否要"批量拒绝通过"'+selection.length+'条"企业信息数据"？',
				function(r){    
			    if (r){    
				    		var params = new Array();
							for(var i=0;i<selection.length;i++){
								params.push(selection[i].id);
							}
							companyManager.refusal(params);
			    }
			});  
		}
	
	};
	
	/* 
		审核 拒绝职位
	 */
	companyManager.refusal= function(params) {
	//取消选择所有中所有的行。
				$.ajax({
						url:'/jobservice/manage/audit_company',
						type:'post',
						data:{'params': params,'type':'refusal'},
						success:function(data){
								if(data="true"){
										alert("操作成功");
										//刷新列表
										$('#company_grid').datagrid('reload'); 
									
								}else{
									alert("操作失败");
									//刷新列表
										$('#company_grid').datagrid('reload'); 
								}
								
								//清楚勾选
								$('#company_grid').datagrid('clearSelections'); 
						},error:function(){
							alert("职位审核  发生错误");
						}
				});
	};

		/*
		查询条件
		*/
		$("#companymanager_auditStatus").change(function(){
		
				companyManager.val=$("#companymanager_auditStatus").val();
				
				if(companyManager.val=="daiShen"){
					//待审核
					$('#company_grid').datagrid('load',{
													type: 'daiShen'
												});
						//隐藏列
					$('#company_grid').datagrid('showColumn','action');	
					manageCommon.selectStatus1();						
				}
				//未通过
				if(companyManager.val=="weiTongGuo"){
					//待审核
					$('#company_grid').datagrid('load',{
													type: 'weiTongGuo'
												});
						//隐藏列
					$('#company_grid').datagrid('hideColumn','action');
					manageCommon.selectStatus2();
				}
				if(companyManager.val=="ok"){
					//通过审核
					$('#company_grid').datagrid('load',{type: 'ok'});
						//隐藏列
					$('#company_grid').datagrid('hideColumn','action');
					manageCommon.selectStatus3();
				}
	});	
	
	
		/*
		批量导出
	 */
	companyManager.BatchExport= function() {
		//获取所有选中列
		var selection = $("#company_grid").datagrid('getSelections');
		//判断选择数目是否大于0
		if(selection.length==0){
			alert("未选择任何数据。");
		}else{
		
		//显示确认删除对话框
			$.messager.confirm('确认','是否要批量导出'+selection.length+'条"企业数据"？',
				function(r){    
			    if (r){    
				    		var params = new Array();
							for(var i=0;i<selection.length;i++){
								params.push(selection[i].id);
							}
							//调用导出
							companyManager.exportData(params);
			    }
			});  
		}
	};
	/*
		单个导出
	 */
	companyManager.singleExport= function(index) {
		//取消选择所有中所有的行。
		$('#company_grid').datagrid("clearSelections");
		$('#company_grid').datagrid("selectRow", index);
		companyManager.jobid = $('#company_grid').datagrid("getSelections")[0].id;
		
		var params = new Array();
		params.push(companyManager.jobid);
		
		//调用导出
		companyManager.exportData(params);
	};
	
	/* 
		导出职位
	 */
	companyManager.exportData= function(params) {
			$.ajax({
						url:'/jobservice/manage/export-company',
						type:'post',
						data:{'params':params},
						success:function(data){
							window.open(data);
							//清除勾选
							$('#company_grid').datagrid('clearSelections'); 
						},error:function(){
							alert("导出时发生错误");
						}
				});
	};
	
	
</script>



<!-- 数据表格 -->
<table id="company_grid"></table>

<!-- 自定义菜单 -->
<div id="companymanager_boolbar" class="manage" data-options="fit:false,doSize:true" style="white-space: nowrap;height: 50px;margin-top: 5px">
<div class="audit-area">

		<div style="float: left;">
					<span class="audit-text" >审核数据状态：</span> 
					<select id="companymanager_auditStatus" class="audit-select">
						<option value="daiShen">待审核</option>
						<option value="weiTongGuo">未通过</option>
						<option value="ok">已通过</option>
					</select>
		</div>
		<div style="float: right;">
					  <button class="batchAudit" onclick="companyManager.batchAudit()">通过</button>	
					 <button class="batchRefusal"  onclick="companyManager.batchRefusal()">拒绝通过</button>	
					 <button onclick="companyManager.BatchExport()">批量导出</button>	
			</div>


</div>

<!-- 信息窗口 -->
<div id="viewCompanyPanel"></div>



