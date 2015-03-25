<%@ page language="java" import="java.util.*" contentType="text/html"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh-cn">
<head>

	<title>系统设置</title>
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
	<div class="main-body">

		<!-- 头部 div -->
		<%@ include file="header.jsp"%>

		<!-- 中间主体div -->
		<div class="manage-body">

			<!-- 左侧菜单div -->
			<%@ include file="body-left.jsp"%>

			<!-- 右侧详细内容div  开始-->
			<div class="manage-body-right">
			
				<div class="span6" style="margin-top: 50px;margin-left:200px;">
					<!-- 审核开关状态设置 -->
					<h3>审核开关状态</h3>
					<c:forEach items="${entityList }" var="entity">
						<div class="control-group">
							<div class="switch" data-on="success" data-off="warning">
							 	<label for="loginName" style="display:initial;width:60px;margin-right:20px;"> ${entity.switchName }</label> 
								<input type="checkbox" id="${entity.id }" switchid="${entity.id }" name="switchAudit" <c:if test="${entity.switchValue == 'on' }">checked</c:if>>
							</div>
						</div>
					</c:forEach>
					<br/>
					<!-- 信息分享范围 -->
					<h3>信息分享范围<span style="font-size:12px;font-weight:normal;">(设定本地区残联就业指导中心网站和就业信息网显示信息的范围)</span></h3>
					<div class="btn-group" >
						<button class="btn" id="btnShareScope">${shareScope.name }</button> 
						<input type="hidden" id="shareScope" name="shareScope" value="${shareScope.value }"/>
						<input type="hidden" id="pid" name="pid" value="${shareScope.id }" />
						<button data-toggle="dropdown" class="btn dropdown-toggle"><span class="caret"></span></button>
						<ul class="dropdown-menu">
							<c:forEach items="${shareScopeSwitch }" var="sc">
								<li>
									<a href="javascript:updateShareScope('${shareScope.id }','${sc.value }','${sc.name }');">${sc.name }</a>
								</li>
							</c:forEach>
						</ul><span >(县区级信息过少,暂不推荐)</span>
					</div>
					<!-- 短信推送条数设置-->
					<h3>短信推送信息设置<span style="font-size:12px;font-weight:normal;">(设定职位匹配信息条数和短信推送条数)</span></h3>
					<label>匹配信息<b>显示</b>条数:</label>
					<div class="btn-group" >
						<button class="btn" id="btnMatchedShowNumber">${matchedShowNumber.name }</button> 
						<button data-toggle="dropdown" class="btn dropdown-toggle"><span class="caret"></span></button>
						<ul class="dropdown-menu">
							<c:forEach items="${matchedShow }" var="ms">
								<li>
									<a href="javascript:updateTuiSong('${matchedShowNumber.id }','${ms.value }','${ms.name }','','btnMatchedShowNumber');">${ms.name }</a>
								</li>
							</c:forEach>
						</ul>
					</div>
					<label>匹配信息<b>推送</b>条数:</label>
					<div class="btn-group" >
						<button class="btn" id="btnMatchedSendNumber">${matchedSendNumber.name }</button> 
						<button data-toggle="dropdown" class="btn dropdown-toggle"><span class="caret"></span></button>
						<ul class="dropdown-menu">
							<c:forEach items="${matchedShow }" var="ms">
								<li>
									<a href="javascript:updateTuiSong('${matchedSendNumber.id }','${ms.value }','${ms.name }','matchedSendNumber','btnMatchedSendNumber');">${ms.name }</a>
								</li>
							</c:forEach>
						</ul>
					</div>
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
