<%@ page language="java" import="java.util.*" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
	
	<title>后台登陆</title>
	<meta http-equiv="keywords" content="残疾人,就业,招聘">
	<meta http-equiv="description" content="残疾人就业招聘网站">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" type="text/css" href="${contextPath}/js/bootstrap/css/bootstrap-combined.min.css" />
	<link rel="stylesheet" type="text/css" href="${contextPath}/css/backdoor/main.css" />
	<script src="${contextPath}/js/bootstrap/js/jquery-1.11.1.js"></script>
	<script src="${contextPath}/js/bootstrap/js/bootstrap.js"></script>
	
</head>

<body>
	
	<!-- 这个页面div -->
	<div class="manage-body">
	
		<!-- 头部 div -->
		<%@ include file="header.jsp" %>
		
		<!-- 中间主体div 开始-->
		<div class="manage-body">
			<!-- 中间登陆div  -->
			<div class="login_background">
				<div class="span12" style="margin-top: 150px;">
					<form class="form-horizontal" action="${contextPath }/loginManage/login"  method="POST" style="text-align:center;">
						<div class="control-group">
							<div class="controls">
								&nbsp;
								<span>${notice }</span>
							</div>
						</div>
						<div class="control-group">
							<div class="controls">
							 	<label for="loginName" style="display:initial;width:60px;">用户名</label> 
								<input id="loginName" name="loginName" value="qinghai" type="text" />
							</div>
						</div>
						<div class="control-group">
							<div class="controls">
							 	<label for="passWord" style="display:initial;width:60px;">密&nbsp;&nbsp;&nbsp;&nbsp;码</label>
								<input id="passWord" name="passWord" value="qinghai" type="password" style=""/>
							</div>
						</div>
						<div class="control-group">
							<div class="controls">
								 <button type="submit" class="btn">登陆</button>
							</div>
						</div>
					</form>
				</div>
			</div>
			<!-- 中间登陆div 结束-->
		</div>
		
		<!-- 尾部div -->
		<%@ include file="footer.jsp" %>
	</div>











</body>
</html>
