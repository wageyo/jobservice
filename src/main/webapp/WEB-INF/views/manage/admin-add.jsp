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
	<script src="${contextPath}/js/lib/jquery-1.11.1.js"></script>
	<script src="${contextPath}/js/bootstrap/js/bootstrap.js"></script>
	<script src="${contextPath}/js/lib/ajaxupload.3.6.js"></script>
	<script src="${contextPath}/js/manage/common.js"></script>
	<script src="${contextPath}/js/manage/widget.js"></script>
	<script src="${contextPath}/js/manage/admin.js"></script>
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
				<div class="container-fluid">
					<div class="row-fluid">
						<div class="span12">
						
							<table style="width:750px;">
								<thead>
									<tr>
										<td colspan="4">
											新建管理员账号
										</td>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td style="width:150px;">
											<span style="color:red;">* </span>登陆账号:
										</td>
										<td colspan="3">
											<input name="loginName" type="text" value="" id="loginName" />
										</td>
									</tr>
									<tr>
										<td>
											<span style="color:red;">* </span>登陆密码 :
										</td>
										<td colspan="3">
											<input name="passWord" type="text" value="" id="passWord" />
										</td>
									</tr>
									<tr>
										<td>
											<span style="color:red;">* </span>昵称 :
										</td>
										<td colspan="3">
											<input name="nickName" type="text" value="" id="nickName" />
										</td>
									</tr>
									<tr>
										<td>
											<span style="color:red;">* </span>头部标题文本 :
										</td>
										<td colspan="3">
											<input name="title" type="text" value="" id="title" />
										</td>
									</tr>
									<!-- 创建账号所属地区 开始 -->
									<tr>
										<td>
											 <span style="color:red;">* </span>账号所属地区<br/>
										</td>
										<td colspan="3">
											<div class="btn-group" >
												<button class="btn" id="areaName1" name="areaName1">全国</button> 
												<input type="hidden" id="areaValue1" name="areaValue1" value="10000000"/>
												<button data-toggle="dropdown" class="btn dropdown-toggle"><span class="caret"></span></button>
												<ul class="dropdown-menu" id="areaLevel1">
													<c:forEach items="${provinceList }" var="p">
														<li>
															<a href="javascript:selectAreaLevel1('areaValue','areaName','${p.code }','${p.name }','areaLevel');">${p.name }</a>
														</li>
													</c:forEach>
												</ul>
											</div>
											<div class="btn-group" >
												<button class="btn" id="areaName2" name="areaName2">请选择</button> 
												<input type="hidden" id="areaValue2" name="areaValue2" value=""/>
												<button data-toggle="dropdown" class="btn dropdown-toggle"><span class="caret"></span></button>
												<ul class="dropdown-menu" id="areaLevel2">
													<li>
														<a href="javascript:selectAreaLevel2('areaValue','areaName','','请选择','areaLevel');">请选择</a>
													</li>
												</ul>
											</div>
											<div class="btn-group" >
												<button class="btn" id="areaName3" name="areaName3">请选择</button> 
												<input type="hidden" id="areaValue3" name="areaValue3" value=""/>
												<button data-toggle="dropdown" class="btn dropdown-toggle"><span class="caret"></span></button>
												<ul class="dropdown-menu" id="areaLevel3">
													<li>
														<a href="javascript:selectAreaLevel3('areaValue','areaName','','请选择');">请选择</a>
													</li>
												</ul>
											</div>
										</td>
									</tr>
									<!-- 创建账号所属地区 结束 -->
																			
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
