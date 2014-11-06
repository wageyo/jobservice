<%@ page language="java" import="java.util.*" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh-cn">
<head>
	
	<title>修改密码</title>
	<meta http-equiv="keywords" content="残疾人,就业,招聘">
	<meta http-equiv="description" content="残疾人就业招聘网站">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" type="text/css" href="${contextPath}/js/bootstrap/css/bootstrap.css" />
	<link rel="stylesheet" type="text/css" href="${contextPath}/js/bootstrap/css/bootstrap-combined.min.css" />
	<link rel="stylesheet" type="text/css" href="${contextPath}/css/manage/main.css" />
	<script src="${contextPath}/js/bootstrap/js/jquery-1.11.1.js"></script>
	<script src="${contextPath}/js/bootstrap/js/bootstrap.js"></script>
	<script src="${contextPath}/js/manage/common.js"></script>
	<script src="${contextPath}/js/manage/user.js"></script>
	
</head>

<body>
	
	<!-- 整个页面div  开始 -->
	<div class="manage-body">
	
		<!-- 头部 div -->
		<%@ include file="header.jsp" %>
		
		<!-- 中间主体div -->
		<div class="manage-body">
		
			<!-- 左侧菜单div -->
			<%@ include file="body-left.jsp" %>
			
			<!-- 右侧详细内容div  开始-->
			<div class="manage-body-right">
			
				<div class="container-fluid">
					<div class="span6">
					<form class="form-horizontal">
						<div class="control-group">
							 <label class="control-label" for="inputEmail">邮箱</label>
							<div class="controls">
								<input id="inputEmail" type="text" />
							</div>
						</div>
						<div class="control-group">
							 <label class="control-label" for="inputPassword">密码</label>
							<div class="controls">
								<input id="inputPassword" type="password" />
							</div>
						</div>
						<div class="control-group">
							<div class="controls">
								 <label class="checkbox"><input type="checkbox" /> Remember me</label> <button type="submit" class="btn">登陆</button>
							</div>
						</div>
					</form>
				</div>
				
				
				</div>
			
			
			</div>
			<!-- 右侧详细内容div  结束-->
			<!-- 让body-right div的高度跟随内容自动变化 -->
			<div style="font: 0px/0px sans-serif;clear: both;display: block"> </div>
		</div>
		
		<!-- 尾部div -->
		<%@ include file="footer.jsp" %>
		
	</div>
	<!-- 整个页面div  结束 -->
</body>
</html>
