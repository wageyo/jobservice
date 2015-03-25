<%@ page language="java" import="java.util.*" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh-cn">
<head>
	
	<title>新建管理员账号</title>
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
	<script src="${contextPath}/js/manage/whitelist.js"></script>
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
				<div class="container">
					<div class="row">
						<div class="span12">
							<table style="width:750px;">
								<thead>
									<tr>
										<td colspan="4">
											新建白名单
										</td>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td style="width:100px;">
											<input type="hidden" id="newsEditType" value="add" />
											<span style="color:red;">* </span>域名标题:
										</td>
										<td style="width:200px;">
											<input name="title" type="text" value="" id="title" />
										</td>
										<td style="width:200px;">
											&nbsp;
										</td>
										<td>
											&nbsp;
										</td>
									</tr>
									<tr>
										<td>
											<span style="color:red;">* </span>域名或IP:
										</td>
										<td>
											<input name="domainName" type="text" value="域名" title="域名" id="domainName"/>
										</td>
										<td>
											<input name="ip" type="text" value="ip" title="ip" id="ip"/>
										</td>
										<td>
											&nbsp;
										</td>
									</tr>
									<tr>
										<td>
											备注 :
										</td>
										<td colspan="3">
											<textarea name="remark" id="remark"  style="width:620px;height:100px;"></textarea>
										</td>
									</tr>
																			
									<tr>
										<td colspan="4" style="text-align:center;">
											 <button class="btn btn-primary" type="button" onclick="updateEntity('add',null);">保存</button>&nbsp;&nbsp;
											 <button class="btn btn-info" type="button" onclick="updateEntity('return',null);">返回</button>&nbsp;&nbsp;
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
