<%@ page language="java" import="java.util.*" contentType="text/html"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh-cn">
<head>

	<title>职位管理列表</title>
	<meta http-equiv="keywords" content="残疾人,就业,招聘">
	<meta http-equiv="description" content="残疾人就业招聘网站">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" type="text/css" href="${contextPath}/js/bootstrap/css/bootstrap.css" />
	<link rel="stylesheet" type="text/css" href="${contextPath}/js/bootstrap/css/bootstrap-combined.min.css" />
	<link rel="stylesheet" type="text/css" href="${contextPath}/js/bootstrap-switch-master/bootstrap-switch.css" />
	<link rel="stylesheet" type="text/css" href="${contextPath}/css/backdoor/main.css" />
	
	<script src="${contextPath}/js/bootstrap/js/jquery-1.11.1.js"></script>
	<script src="${contextPath}/js/bootstrap/js/bootstrap.js"></script>
	<script src="${contextPath}/js/bootstrap-switch-master/bootstrap-switch.js"></script>
	<script src="${contextPath}/js/manage/common.js"></script>
	<script src="${contextPath}/js/manage/setup.js"></script>

</head>

<body>

	<!-- 整个页面div  开始 -->
	<div class="manage-body">

		<!-- 头部 div -->
		<%@ include file="header.jsp"%>

		<!-- 中间主体div -->
		<div class="manage-body">

			<!-- 左侧菜单div -->
			<%@ include file="body-left.jsp"%>

			<!-- 右侧详细内容div  开始-->
			<div class="manage-body-right">
			
				<div class="span6" style="margin-top: 80px;margin-left:200px;">
					<h3>审核开关状态</h3>
					<c:forEach items="${entityList }" var="entity">
						<div class="control-group">
							<div class="switch" data-on="success" data-off="warning">
							 	<label for="loginName" style="display:initial;width:60px;margin-right:20px;"> ${entity.switchName }</label> 
								<input type="checkbox" id="${entity.id }" switchid="${entity.id }" name="switchAudit" <c:if test="${entity.switchValue == 'on' }">checked</c:if>>
							</div>
						</div>
					</c:forEach>
				</div>

			</div>
			<!-- 右侧详细内容div  结束-->




			<!-- 让body-right div的高度跟随内容自动变化 -->
			<div style="font: 0px/0px sans-serif;clear: both;display: block">
			</div>
		</div>

		<!-- 尾部div -->
		<%@ include file="footer.jsp"%>

	</div>
	<!-- 整个页面div  结束 -->
</body>
</html>
