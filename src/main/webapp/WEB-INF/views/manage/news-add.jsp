<%@ page language="java" import="java.util.*" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh-cn">
<head>
	
	<title>新增消息/就业指导讯息</title>
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
	<script src="${contextPath}/js/lib/ajaxupload.3.6.js"></script>
	<script src="${contextPath}/js/manage/common.js"></script>
	<script src="${contextPath}/js/manage/widget.js"></script>
	<script src="${contextPath}/js/manage/news.js"></script>
	<script src="${contextPath}/js/manage/upload.js"></script>
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
				<div class="container-fluid">
					<div class="row-fluid">
						<div class="span12">
							<table>
								<thead>
									<tr>
										<td colspan="4">
											增加文章
										</td>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>
											<span style="color:red;">* </span>标题:
										</td>
										<td colspan="3">
											<input name="title" type="text" value="" id="title" />
										</td>
									</tr>
									<tr>
										<td>
											<span style="color:red;">* </span>作者 :
										</td>
										<td colspan="3">
											<input name="author" type="text" value="${user.title }" id="author" />
										</td>
									</tr>
									<tr>
										<td>
											来源 :
										</td>
										<td colspan="3">
											<input name="source" type="text" value="" id="source" />
										</td>
									</tr>
									<tr>
										<td>
											文章类型 :
										</td>
										<td colspan="3">
											<div class="btn-group" >
												<!-- 将值转换为对应显示的参数文本 -->
												<c:forEach items="${pList }" var="p">
													<c:if test="${p.value == targetArticleType }">
														<c:set var="articleTypeName" >${p.name }</c:set>
														<c:set var="articleTypeValue" >${p.value }</c:set>
													</c:if>
												</c:forEach>
												<button class="btn" id="BtnArticleType">${articleTypeName }</button> 
												<input type="hidden" id="articleType" name="articleType" value="${articleTypeValue }"/>
												<button data-toggle="dropdown" class="btn dropdown-toggle"><span class="caret"></span></button>
												<ul class="dropdown-menu">
													<c:forEach items="${pList }" var="p">
														<li>
															<a href="javascript:selectButton('articleType','BtnArticleType','${p.value }','${p.name }');">${p.name }</a>
														</li>
													</c:forEach>
												</ul>
											</div>
										</td>
									</tr>
									<tr>
										<td>
											<span style="color:red;">* </span>文章内容 :
										</td>
										<td>
											<textarea id="content" style="width:650px;height:450px;"></textarea> 
										</td>
									</tr>
									<tr>
										<td>
											文中图片:
										</td>
										<td colspan="3">
											<img id="newsImage" src="" style="height:26px;width:70px;border-width:0px;"/>
											<input type="button" name="file" value="上传图片" id="picFileImport" />
											<input type="hidden" id="imageId" name="imageId" value="" />
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
