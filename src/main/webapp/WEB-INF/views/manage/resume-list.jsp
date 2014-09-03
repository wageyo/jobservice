<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<script type="text/javascript">
	$(function() {
		$('#resume_grid').datagrid({
			url : '/jobservice/manage/resume_list',
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
			columns : [ [ {
				field : 'id',
				title : 'i00',
				width : 100,
				hidden : 'true'//隐藏列
			}, {
				field : 'title',
				title : '简历名称',
				width : 200,
				formatter : function(value, row, index) {
					var c = '<a class="name-link" href="javascript:void(0)" onclick="ruesmeManager.openViewCompany(' + index + ')">' + value + '</a>';
					return c;
				}
			}, {
				field : 'name',
				title : '姓名',
				width : 100
			}, {
				field : 'gender',
				title : '性别',
				width : 100
			}, {
				field : 'identityCard',
				title : '身份证号',
				width : 100
			}, {
				field : 'race',
				title : '民族',
				width : 100
			}, {
				field : 'marriage',
				title : ' 婚姻状况',
				width : 100
			}, {
				field : 'hukou',
				title : '户口所在地',
				width : 100
			}

			, {
				field : 'action',
				title : '操作',
				width : 150,
				align : 'center',
				formatter : function(value, row, index) {
					var d = '<a href="javascript:void(0)" class="auditLink" onclick="ruesmeManager.singleAudit(' + index + ')">通过</a> ';
					var v = '<a href="javascript:void(0)" class="auditLink" onclick="ruesmeManager.singleRefusal(' + index + ')">拒绝通过</a> ';
					var o = '<a href="javascript:void(0)" class="auditLink" onclick="ruesmeManager.singleExport(' + index + ')">导出</a> ';
					return d + v+o;
				}
			} ] ],
			toolbar : '#resume_boolbar'//指定自定义菜单
		});

	});

	ruesmeManager = {};
	ruesmeManager.data;
	ruesmeManager.jobid;
	ruesmeManager.val;
	/* 
		查看企业信息框
	 */
	ruesmeManager.openViewCompany = function(index) {
		//取消选择所有中所有的行。
		$('#resume_grid').datagrid("clearSelections");
		$('#resume_grid').datagrid("selectRow", index);
		ruesmeManager.jobid = $('#resume_grid').datagrid("getSelections")[0].id;
		$('#viewRuesmePanel').window({
			width : 1100,
			href : '/jobservice/manage/resume_view',
			height : 650,
			title : '查看简历信息',
			minimizable : false,
			loadingMessage : '正在加载，请稍后。',
			iconCls : 'icon-save',
			modal : true//模态窗口
			,
			onLoad : function() {
				ruesmeManager.viewInit(ruesmeManager.jobid);
			}
		});

	};

	/* 
		显示信息初始化
	 */
	ruesmeManager.viewInit = function(index) {
		$.ajax({
			url : '/jobservice/manage/getOneForresumeDate',
			type : 'post',
			dataType : 'json',
			data : {
				'id' : index
			},
			success : function(data) {
				if (data==undefined || data == '' || data == "" || data == "null") {
					alert("数据有误");
					return null;
				}

				var json = eval(data);
				//基本信息
				$("#job-view #title").html(json.title);//简历名称
				$("#job-view #name").html(json.name);//姓名
				$("#job-view #gender").html(json.gender);//性别
				
				$("#job-view #birth").html(json.birth);// 出生日期
				$("#job-view #identityCard").html(json.identityCard);//身份证号
				$("#job-view #race").html(json.race);// 民族
				$("#job-view #marriage").html(json.marriage);//婚姻状况
				$("#job-view #hukou").html(json.hukou);// 户口所在地
				$("#job-view #hukouAddress").html(json.hukouAddress);// 户籍地址--即身份证上的地址
				$("#job-view #hukouStatus").html(json.hukouStatus);//户口状况 --本市,农村等类别
				$("#job-view #address").html(json.address);//详细住址/现居住地
				$("#job-view #zipCode").html(json.zipCode);// 邮政编码
				$("#job-view #phone").html(json.phone);// 电话
				$("#job-view #email").html(json.email);// email
				$("#job-view #qq").html(json.qq);// qql
				$("#job-view #disabilityCategory").html(json.disabilityCategory);// 残疾类别
				$("#job-view #disabilityCard").html(json.disabilityCard);//残疾证号
				
				$("#job-view #disabilityLevel").html(json.disabilityLevel);//残疾级别
				$("#job-view #disabilityPart").html(json.disabilityPart);// 残疾部位
				$("#job-view #workAbility").html(json.workAbility);// 有无劳动能力
			
				$("#job-view #homeTown").html(json.homeTown);// 籍贯
				$("#job-view #politicalStatus").html(json.politicalStatus);// 政治面貌
				
					
				$("#job-view #age").html(json.age);// 年龄--由出生日期得到
				$("#job-view #height").html(json.height);//身高 cm
				$("#job-view #weight").html(json.weight);//体重 cm
				
				// 教育背景部分
				$("#job-view #school").html(json.school);//  毕业学校及专业
				$("#job-view #education").html(json.education);//学历
				$("#job-view #major").html(json.major);//专业
				$("#job-view #experts").html(json.experts);// 特长
				 $("#job-view #zhiCheng").html(json.zhiCheng);//职称
				$("#job-view #shiYeHao").html(json.shiYeHao);//就业失业登记证 号
				$("#job-view #training").html(json.training);// 培训情况
				
				// 求职意向
			    $("#job-view #jobNature").html(json.jobNature);// 工作性质
				$("#job-view #desireJob").html(json.desireJob);//期望职位
				$("#job-view #desireAddress").html(json.desireAddress);// 期望工作地点
				$("#job-view #provideOther").html(json.provideOther);//其他
				$("#job-view #state").html(json.state);//目前状态
			
				//工作总年限
				$("#job-view #experience").html(json.experience);// 工作年限
			   $("#job-view #workExperience").html(json.workExperience);// 工作经历
			
				// 自我评价
				$("#job-view #selfEvaluation").html(json.selfEvaluation);//自我评价/ 职业目标
				
	
			},
			error : function() {
				alert("查看简历详细页面初始化数据时发生错误.");
			}
		});

	};

	
	/* 
		审核 通过单个
	 */
	ruesmeManager.singleAudit= function(index) {
			$('#resume_grid').datagrid("clearSelections");
			$('#resume_grid').datagrid("selectRow", index);
			ruesmeManager.jobid = $('#resume_grid').datagrid("getSelections")[0].id;
			
			if(ruesmeManager.jobid==undefined){
					alert("审核单个信息时 数据id获取失败");
			}else{
				//组装参数
				var params=new	Array();
				params.push(ruesmeManager.jobid );
				ruesmeManager.audit(params);
			}
			
	};
	
	
	/* 
		审核 批量通过
	 */
	ruesmeManager.batchAudit= function() {
			//获取所有选中列
		var selection = $("#resume_grid").datagrid('getSelections');
		//判断选择数目是否大于0
		if(selection.length==0){
			alert("未选择任何数据。");
		}else{
		
		//显示确认删除对话框
			$.messager.confirm('确认','是否要批量审核通过'+selection.length+'条"简历数据"？',
				function(r){    
			    if (r){    
				    		var params = new Array();
							for(var i=0;i<selection.length;i++){
								params.push(selection[i].id);
							}
							ruesmeManager.audit(params);
			    }
			});  
		}
	
	};
	
	/* 
		审核 通过
	 */
	ruesmeManager.audit= function(params) {
	//取消选择所有中所有的行。
				$.ajax({
						url:'/jobservice/manage/audit_resume',
						type:'post',
						data:{'params': params,'type':'ok'},
						success:function(data){
								if(data="true"){
										alert("操作成功");
										//刷新列表
										$('#resume_grid').datagrid('reload'); 
									
								}else{
									alert("操作失败");
									//刷新列表
										$('#resume_grid').datagrid('reload'); 
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
	ruesmeManager.singleRefusal= function(index) {
			$('#resume_grid').datagrid("clearSelections");
			$('#resume_grid').datagrid("selectRow", index);
			ruesmeManager.jobid = $('#resume_grid').datagrid("getSelections")[0].id;
			if(ruesmeManager.jobid==undefined){
					alert("审核单个信息时 数据id获取失败");
			}else{
				//组装参数
				var params=new	Array();
				params.push(ruesmeManager.jobid );
				ruesmeManager.refusal(params);
			}
			
	};
	
	
	/* 
		审核 批量拒绝职位
	 */
	ruesmeManager.batchRefusal= function() {
			//获取所有选中列
		var selection = $("#resume_grid").datagrid('getSelections');
		//判断选择数目是否大于0
		if(selection.length==0){
			alert("未选择任何数据。");
		}else{
		
		//显示确认删除对话框
			$.messager.confirm('确认','是否要批量拒绝通过'+selection.length+'条"简历数据"？',
				function(r){    
			    if (r){    
				    		var params = new Array();
							for(var i=0;i<selection.length;i++){
								params.push(selection[i].id);
							}
							ruesmeManager.refusal(params);
			    }
			});  
		}
	
	};
	
	/* 
		审核 拒绝职位
	 */
	ruesmeManager.refusal= function(params) {
	//取消选择所有中所有的行。
				$.ajax({
						url:'/jobservice/manage/audit_resume',
						type:'post',
						data:{'params': params,'type':'refusal'},
						success:function(data){
								if(data="true"){
										alert("操作成功");
										//刷新列表
										$('#resume_grid').datagrid('reload'); 
									
								}else{
									alert("操作失败");
									//刷新列表
										$('#resume_grid').datagrid('reload'); 
								}
								//清楚勾选
								$('#resume_grid').datagrid('clearSelections'); 
						},error:function(){
							alert("简历审核  发生错误");
						}
				});
	};
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
		/*
		查询条件
		*/
		$("#resume_auditStatus").change(function(){
		
				ruesmeManager.val=$("#resume_auditStatus").val();
				
				if(ruesmeManager.val=="daiShen"){
					//待审核
					$('#resume_grid').datagrid('load',{
													type: 'daiShen'
												});
						//隐藏列
					$('#resume_grid').datagrid('showColumn','action');							
					manageCommon.selectStatus1();						
				}
				
				//未通过
				if(ruesmeManager.val=="weiTongGuo"){
					//待审核
					$('#resume_grid').datagrid('load',{
													type: 'weiTongGuo'
												});
						//隐藏列
					$('#resume_grid').datagrid('hideColumn','action');
					manageCommon.selectStatus2();
				}
				
				if(ruesmeManager.val=="ok"){
					//通过审核
					$('#resume_grid').datagrid('load',{
													type: 'ok'
												});
						//隐藏列
					$('#resume_grid').datagrid('hideColumn','action');
					manageCommon.selectStatus3();
				}
	});	
	
	
	
	
	
	
	/*
		批量导出
	 */
	ruesmeManager.BatchExport= function() {
		//获取所有选中列
		var selection = $("#resume_grid").datagrid('getSelections');
		//判断选择数目是否大于0
		if(selection.length==0){
			alert("未选择任何数据。");
		}else{
		
		//显示确认删除对话框
				$.messager.confirm('确认','是否要批量导出'+selection.length+'条"简历数据"？',
				function(r){    
			    if (r){    
				    		var params = new Array();
							for(var i=0;i<selection.length;i++){
								params.push(selection[i].id);
							}
							ruesmeManager.exportData(params);
			    }
			});  
		}
	};
	/*
		单个导出
	 */
	ruesmeManager.singleExport= function(index) {
		//取消选择所有中所有的行。
		$('#resume_grid').datagrid("clearSelections");
		$('#resume_grid').datagrid("selectRow", index);
		ruesmeManager.jobid = $('#resume_grid').datagrid("getSelections")[0].id;
		
		var params = new Array();
		params.push(ruesmeManager.jobid);
						
		ruesmeManager.exportData(params);

		
	};
	/* 
		导出职位
	 */
	ruesmeManager.exportData= function(params) {
			$.ajax({
						url:'/jobservice/manage/export-resume',
						type:'post',
						data:{'params':params},
						success:function(data){
								window.open(data);
								//清楚勾选
								$('#resume_grid').datagrid('clearSelections'); 
						
						},error:function(){
							alert("导出时发生错误");
						}
				});
	};
</script>



<!-- 数据表格 -->
<table id="resume_grid"></table>

<!-- 自定义菜单 -->
<div id="resume_boolbar" class="manage" data-options="fit:false,doSize:true" style="white-space: nowrap;height: 50px;margin-top: 5px">
	<div class="audit-area">
			<div style="float: left;">
						<span class="audit-text" >审核数据状态：</span> 
						<select class="audit-select"  id="resume_auditStatus">
									<option value="daiShen">待审核</option>
									<option value="weiTongGuo">未通过</option>
									<option value="ok">已通过</option>
						</select>
		  </div>
		  <div style="float: right;">
		  				<button class="batchAudit" onclick="ruesmeManager.batchAudit()">通过</button>	
						<button class="batchRefusal"  onclick="ruesmeManager.batchRefusal()">拒绝通过</button>	
						<button onclick="ruesmeManager.BatchExport()">批量导出</button>	
			</div>	
	</div>


<!-- 信息窗口 -->
<div id="viewRuesmePanel"></div>



