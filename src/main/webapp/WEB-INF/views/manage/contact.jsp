<%@ page language="java" import="java.util.*" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh-cn">
<head>
	
	<title>编辑联系我们信息</title>
	<meta http-equiv="keywords" content="残疾人,就业,招聘">
	<meta http-equiv="description" content="残疾人就业招聘网站">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" type="text/css" href="${contextPath}/js/bootstrap/css/bootstrap.css" />
	<link rel="stylesheet" type="text/css" href="${contextPath}/js/bootstrap/css/bootstrap-combined.min.css" />
	<link rel="stylesheet" type="text/css" href="${contextPath}/css/backdoor/main.css" />
	<script src="${contextPath}/js/lib/jquery-1.11.1.js"></script>
	<script src="${contextPath}/js/bootstrap/js/bootstrap.js"></script>
	<script src="${contextPath}/js/lib/ckeditor/ckeditor.js"></script>
	<script src="${contextPath}/js/manage/common.js"></script>
	<script type="text/javascript">
		//更新联系我们的内容
		function updateContact(){
			var content = CKEDITOR.instances.content.getData();
			if(content == null || content == '' || content == undefined){
				alert('联系我们的内容不能为空!');
				return;
			}
			$.ajax({
				url:server.url + 'manage/contact/edit',
				type:'post',
				dataType:'json',
				data:{
					'content':content
				},
				success:function(data){
					if(data.notice == 'success'){
						alert('更新成功!');
					}else{
						alert(data.notice);
					}
				}
			});
		}
		
	</script>
</head>

<body>
	
	<!-- 整个页面div  开始 -->
	<div class="main-body">
	
		<!-- 头部 div -->
		<%@ include file="header.jsp" %>
		
		<!-- 中间主体div -->
		<div class="manage-body">
		
			<!-- 左侧菜单div -->
			<%@ include file="body-left.jsp" %>
			
			<!-- 右侧详细内容div  -->
			<div class="manage-body-right">
				<!-- 宽高自适应 container -->
				<div class="container-fluid">
					<div class="row-fluid">
						<div class="span12">
							<table style="width:100%;">
								<thead>
									<tr>
										<td>
											编辑"联系我们"页面内容
										</td>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td colspan="4">
											<textarea name="contentHidden" id="contentHidden" style="display:none;">${content }</textarea> 
											<textarea name="content" id="content" style="" ></textarea> 
										</td>
										<script type="text/javascript">
											//将textarea初始化为ckeditor文本框
									    	var editor = CKEDITOR.replace('content',{
									    	//	uiColor:'green',	//蓝绿色--cute
									    		resize_enabled  : false,	//是否可调整宽高
									    		height : 600,	//高度
									    		toolbarGroups : [
									    		                 { name: 'clipboard',   groups: [ 'clipboard', 'undo' ] },
									    		                 { name: 'editing',     groups: [ 'find', 'selection', 'spellchecker' ] },
									    		                 { name: 'links' },
									    		                 { name: 'insert' },
									    		                 { name: 'forms' },
									    		                 { name: 'tools' },
									    		                 { name: 'document',    groups: [ 'mode', 'document', 'doctools' ] },
									    		                 { name: 'others' },
									    		                 { name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ] },
									    		                 { name: 'paragraph',   groups: [ 'list', 'indent', 'blocks', 'align' ] },
									    		                 { name: 'styles' },
									    		                 { name: 'colors' },
									    		                 { name: 'about' }
									    		             ],
									    		language:'zh-cn'	//ui控件显示用语言, 简体中文
									    	});
											//给textarea控件赋值
											var content = $('#contentHidden').val();
									    	CKEDITOR.instances.content.setData(content);
										</script>
									</tr>
									<tr>
										<td colspan="4" style="text-align:center;">
											 <button class="btn btn-primary" type="button" onclick="updateContact();">保存修改</button>&nbsp;&nbsp;
										</td>
									</tr>
								</tbody>
							</table>

						
						</div>
						
					</div>
						
				</div>
			</div>
			
			<!-- 让body-right div的高度跟随内容自动变化 -->
			<div style="font: 0px/0px sans-serif;clear: both;display: block"> </div>
		</div>
		
		<!-- 尾部div -->
		<%@ include file="footer.jsp" %>
		
	</div>
	<!-- 整个页面div  结束 -->
</body>
</html>
