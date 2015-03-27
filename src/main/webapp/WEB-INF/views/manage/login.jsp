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
	<script src="${contextPath}/js/lib/jquery-1.11.1.js"></script>
	<script src="${contextPath}/js/bootstrap/js/bootstrap.js"></script>
	
</head>

<body>
	
	<!-- 这个页面div -->
	<div class="main-body">
	
		<!-- 头部 div -->
		<div class="manage-header" style="background-color:white;">
			<span style="float:right;"><a href="${contextPath }/index">回到首页</a></span>
		</div>
		
		<!-- 中间主体div 开始-->
		<div class="manage-body" style="background-image: url('/jobservice/images/backdoor/tishi.png');background-position: center;background-repeat: no-repeat;">
			<!-- 中间登陆div  -->
			<form class="form-horizontal" action="${contextPath }/loginManage/login"  method="POST" style="text-align:center;">
				<table style="width: 100%;">
					<tr>
						<td style="width: 40%;height: 160px;">
						 &nbsp;
						</td>
						<td>
						 &nbsp;
						</td>
						<td>
						 &nbsp;
						</td>
						<td style="width: 40%;">
						 &nbsp;
						</td>
					</tr>
					<tr>
						<td>
						 &nbsp;
						</td>
						<td>
							&nbsp;
						</td>
						<td>
							<span>${notice }</span>
						</td>
						<td>
						 &nbsp;
						</td>
					</tr>
					<tr>
						<td>
						 &nbsp;
						</td>
						<td>
							<label for="loginName" style="display:initial;width:60px;">用户名:</label> 
						</td>
						<td>
							<input id="loginName" name="loginName" value="" type="text" />
						</td>
						<td>
						 &nbsp;
						</td>
					</tr>
					<tr>
						<td>
						 &nbsp;
						</td>
						<td>
						 	<label for="passWord" style="display:initial;width:60px;">密&nbsp;&nbsp;&nbsp;&nbsp;码:</label>
						</td>
						<td>
							<input id="passWord" name="passWord" value="" type="password" style=""/>
						</td>
						<td>
						 &nbsp;
						</td>
					</tr>
					<tr>
						<td>
						 &nbsp;
						</td>
						<td colspan="2">
							<button type="submit" class="btn">登陆</button>
						</td>
						<td>
						 &nbsp;
						</td>
					</tr>
					<tr>
						<td>
						 &nbsp;
						</td>
						<td>
						 &nbsp;
						</td>
						<td>
						 &nbsp;
						</td>
						<td>
						 &nbsp;
						</td>
					</tr>
				</table>
			</form>
			
			<!-- 中间登陆div 结束-->
		</div>
		
		<!-- 尾部div -->
		<%@ include file="footer.jsp" %>
	</div>











</body>
</html>
