<%@ page language="java" import="java.util.*" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh-cn">
<head>
	
	<title>短信发送页面</title>
	<meta http-equiv="keywords" content="残疾人,就业,招聘">
	<meta http-equiv="description" content="残疾人就业招聘网站">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" type="text/css" href="${contextPath}/js/bootstrap/css/bootstrap.css" />
	<link rel="stylesheet" type="text/css" href="${contextPath}/js/bootstrap/css/bootstrap-combined.min.css" />
	<link rel="stylesheet" type="text/css" href="${contextPath}/css/backdoor/main.css" />
	<script src="${contextPath}/js/bootstrap/js/jquery-1.11.1.js"></script>
	<script src="${contextPath}/js/bootstrap/js/bootstrap.js"></script>
	<script src="${contextPath}/js/manage/common.js"></script>
	<script src="${contextPath}/js/manage/sms.js"></script>
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
			
			<!-- 右侧详细内容div  -->
			<div class="manage-body-right">
				<!-- 顶部发送按钮和发送号码统计页面 -->
				<div style="padding-top: 10px;padding-left: 15px;margin:5px;">
					<div style="float:left;">
						<button class="btn btn-primary" type="button" onclick="updateEntity('add',null);">发送给 :</button>
					</div>
					<div style="border: 1px solid;width: 742px;padding-top: 3px;padding-left: 10px;margin-top:5px;float:left;">20个号码</div>
					<div style="font: 0px/0px sans-serif;clear: both;display: block"> </div>
				</div>
				<!-- 宽高自适应 container-fluid -->
				<div class="container-fluid">
					<div class="row-fluid">
						<div class="span4">
							<textarea style="width: 100%;height: 300px;" title="短信内容不超过300字...">短信内容不超过300字...</textarea>
							<p id="notice">你还可以输入300字√ ×</p>
						</div>
						<div class="span4">
							<c:set var="index">1</c:set>
							<c:forEach begin="1" end="3" step="1" var="ulItme" varStatus="ulStatus">
								<ul style="float:left;margin-left: 0px;">
									<c:forEach begin="1" end="20" step="1" var="item" varStatus="status">
										<li style="list-style:none;width:130px;">
											${ index }. <input type="text" class="item-phone" maxlegnth="11" style="border-radius:0px;-webkit-border-radius:0px;-moz-border-radius:0px;margin-bottom:8px;width: 90px;padding: 0px;border-top: none;border-left: none;border-right: none;-webkit-box-shadow:none;-moz-box-shadow:none;box-shadow:none;background-color:none;"/>
											<span>√</span>
										</li>
										<c:set var="index">${index + 1 }</c:set>
									</c:forEach>
								</ul>
							</c:forEach>
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
