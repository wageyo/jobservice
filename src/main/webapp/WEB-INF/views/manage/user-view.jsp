<%@ page language="java" import="java.util.*" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh-cn">
<head>
	
	<title>编辑/审核用户</title>
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
	<script src="${contextPath}/js/manage/user.js"></script>
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
							<table>
								<thead>
									<tr>
										<td colspan="4">
											账户信息
											<span style="float:right;">
												审核状态:
												<c:if test="${obj.checkStatus == 'ok' }">
													审核通过
												</c:if>
												<c:if test="${obj.checkStatus == 'daiShen' }">
													待审核 
												</c:if>
												<c:if test="${obj.checkStatus == 'weiTongGuo' }">
													未通过
												</c:if>
											</span>
										</td>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>
											<span style="color:red;">* </span>登陆账号:
										</td>
										<td>
											<input name="loginName" type="text" value="${obj.loginName }" id="loginName" />
											<input name="objId" type="hidden" value="${obj.id }" id="objId" />
										</td>
										<td>
											<span style="color:red;">* </span>登陆密码 :
										</td>
										<td>
											<input name="passWord" type="text" value="${obj.passWord }" id="passWord" />
										</td>
									</tr>
									<tr>
										<td>
											<span style="color:red;">* </span>注册邮箱 :
										</td>
										<td>
											<input name="email" type="text" value="${obj.email }" id="email" />
										</td>
										<td>
											<span style="color:red;">* </span>联系电话 :
										</td>
										<td>
											<input name="phone" type="text" value="${obj.phone }" id="phone" />
										</td>
									</tr>
									<tr>
										<td>
											用户昵称 :
										</td>
										<td colspan="3">
											<input name="nickName" type="text" value="${obj.nickName }" id="nickName" />
										</td>
									</tr>
									<tr>
										<td>
											头像 :
										</td>
										<td colspan="3">
											<img id="headImage" src="${contextPath }/user/downloadPic/${obj.id}" style="height:90px;width:90px;border-width:0px;" title="90*90格式"/>
											<br/>
										<!-- 	<input type="button" name="file" value="上传图片" id="picFileImport" />	 -->
										</td>
									</tr>
									
									<!-- 更改账号所属地区,仅超级管理员可见   开始 -->
									<c:if test="${user.identity == 'superadmin' }">
										<tr>
											<td>
												 <span style="color:red;">* </span>账号所属地区<br/>(sa可见) :
											</td>
											<td colspan="3">
												<div class="btn-group" >
													<button class="btn" id="areaName1" name="areaName1">${obj.area.name }</button> 
													<input type="hidden" id="areaValue1" name="areaValue1" value="${obj.area.code }"/>
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
									</c:if>	
									<!-- 更改账号所属地区,仅超级管理员可见   结束 -->
																			
									<tr>
										<td colspan="4" style="text-align:center;">
											 <c:if test="${obj.checkStatus != 'ok' }">
											 	<button class="btn btn-success" type="button" onclick="updateEntity('approve','${obj.id}');">通过</button>&nbsp;&nbsp;
											 </c:if>
											 <c:if test="${obj.checkStatus != 'weiTongGuo' }">
											 	<button class="btn btn-danger" type="button" onclick="updateEntity('refuse','${obj.id}');">拒绝</button>&nbsp;&nbsp;
											 </c:if>
											 <button class="btn btn-info" type="button" onclick="updateEntity('return','${obj.id}');">返回</button>&nbsp;&nbsp;
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
