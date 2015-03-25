<%@ page language="java" import="java.util.*" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh-cn">
<head>
	
	<title>编辑/审核企业信息</title>
	<meta http-equiv="keywords" content="残疾人,就业,招聘">
	<meta http-equiv="description" content="残疾人就业招聘网站">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" type="text/css" href="${contextPath}/js/bootstrap/css/bootstrap.css" />
	<link rel="stylesheet" type="text/css" href="${contextPath}/js/bootstrap/css/bootstrap-combined.min.css" />
	<link rel="stylesheet" type="text/css" href="${contextPath}/css/backdoor/main.css" />
	<link rel="stylesheet" type="text/css" href="${contextPath}/css/jquery-foxibox-0.2.css" />
	<script src="${contextPath}/js/bootstrap/js/jquery-1.11.1.js"></script>
	<script src="${contextPath}/js/bootstrap/js/bootstrap.js"></script>
	<script src="${contextPath}/js/manage/common.js"></script>
	<script src="${contextPath}/js/manage/widget.js"></script>
	<script src="${contextPath}/js/manage/company.js"></script>
	<script src="${contextPath}/js/manage/companyUploadImg.js"></script>
	<script src="${contextPath}/js/lib/ajaxupload.3.6.js"></script>
	
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
							<table>
								<thead>
									<tr>
										<td colspan="4">
											企业信息
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
											<span style="color:red;">* </span>企业名称:
										</td>
										<td>
											<input name="companyName" type="text" value="${obj.name }" id="companyName" />
											<input name="objId" type="hidden" value="${obj.id }" id="objId" />
										</td>
										<td>
											<span style="color:red;">* </span>法人代表 :
										</td>
										<td>
											<input name="corporateRepresentative" type="text" value="${obj.corporateRepresentative }" id="corporateRepresentative" />
										</td>
									</tr>
									<tr>
										<td>
											<span style="color:red;">* </span>组织机构代码:
										</td>
										<td>
											<input name="organizationCode" type="text" value="${obj.organizationCode }" id="organizationCode" />
										</td>
										<td>
											<span style="color:red;">* </span>工商登记号码 :
										</td>
										<td>
											<input name="commercialCode" type="text" value="${obj.commercialCode }" id="commercialCode" />
										</td>
									</tr>
									<tr>
										<td>
											税务编码:
										</td>
										<td>
											<input name="taxCode" type="text" value="${obj.taxCode }" id="taxCode" />
										</td>
										<td>
											社保登记证号 :
										</td>
										<td>
											<input name="socialSecurityCode" type="text" value="${obj.socialSecurityCode }" id="socialSecurityCode" />
										</td>
									</tr>
									<tr>
										<td>
											网站ID:
										</td>
										<td>
											<input name="webSiteId" type="text" value="${obj.webSiteId }" id="webSiteId" />
										</td>
										<td>
											市劳网号 :
										</td>
										<td>
											<input name="laoWangCode" type="text" value="${obj.laoWangCode }" id="laoWangCode" />
										</td>
									</tr>
									<tr>
										<td>
											企业规模 :
										</td>
										<td>
											<div class="btn-group" >
												<!-- 将值转换为对应显示的参数文本 -->
												<c:choose>
													<c:when test="${obj.scale !=null && obj.scale != ''}">
														<c:forEach items="${params }" var="p">
															<c:if test="${p.type == 'scale' && p.value == obj.scale}">
																<c:set var="scaleName" value="${p.name }"></c:set>
																<c:set var="scaleValue" value="${p.value }"></c:set>
															</c:if>
														</c:forEach>
													</c:when>
													<c:otherwise>
														<c:set var="scaleName" value="请选择"></c:set>
													</c:otherwise>
												</c:choose>
												<button class="btn" id="btnScale">${scaleName }</button> 
												<input type="hidden" id="scale" name="scale" value="${scaleValue }"/>
												<button data-toggle="dropdown" class="btn dropdown-toggle"><span class="caret"></span></button>
												<ul class="dropdown-menu">
													<c:forEach items="${params }" var="p">
														<c:if test="${p.type == 'scale' }">
															<li>
																<a href="javascript:selectButton('scale','btnScale','${p.value }','${p.name }');">${p.name }</a>
															</li>
														</c:if>
													</c:forEach>
												</ul>
											</div>
										</td>
										<td>
											企业性质 :
										</td>
										<td>
											<div class="btn-group" >
												<!-- 将值转换为对应显示的参数文本 -->
												<c:choose>
													<c:when test="${obj.nature !=null && obj.nature != ''}">
														<c:forEach items="${params }" var="p">
															<c:if test="${p.type == 'companyNature' && p.value == obj.nature}">
																<c:set var="natureName" value="${p.name }"></c:set>
																<c:set var="natureValue" value="${p.value }"></c:set>
															</c:if>
														</c:forEach>
													</c:when>
													<c:otherwise>
														<c:set var="natureName" value="请选择"></c:set>
													</c:otherwise>
												</c:choose>
												<button class="btn" id="btnNature">${natureName }</button> 
												<input type="hidden" id="nature" name="nature" value="${natureValue }"/>
												<button data-toggle="dropdown" class="btn dropdown-toggle"><span class="caret"></span></button>
												<ul class="dropdown-menu">
													<c:forEach items="${params }" var="p">
														<c:if test="${p.type == 'companyNature' }">
															<li>
																<a href="javascript:selectButton('nature','btnNature','${p.value }','${p.name }');">${p.name }</a>
															</li>
														</c:if>
													</c:forEach>
												</ul>
											</div>
										</td>
									</tr>
									<tr>
										<td>
											经济类型 :
										</td>
										<td>
											<div class="btn-group" >
												<!-- 将值转换为对应显示的参数文本 -->
												<c:choose>
													<c:when test="${obj.economyType !=null && obj.economyType != ''}">
														<c:forEach items="${params }" var="p">
															<c:if test="${p.type == 'economyType' && p.value == obj.economyType}">
																<c:set var="economyTypeName" value="${p.name }"></c:set>
																<c:set var="economyTypeValue" value="${p.value }"></c:set>
															</c:if>
														</c:forEach>
													</c:when>
													<c:otherwise>
														<c:set var="economyTypeName" value="请选择"></c:set>
													</c:otherwise>
												</c:choose>
												<button class="btn" id="btnEconomyType">${economyTypeName }</button> 
												<input type="hidden" id="economyType" name="economyType" value="${economyTypeValue }"/>
												<button data-toggle="dropdown" class="btn dropdown-toggle"><span class="caret"></span></button>
												<ul class="dropdown-menu">
													<c:forEach items="${params }" var="p">
														<c:if test="${p.type == 'economyType' }">
															<li>
																<a href="javascript:selectButton('economyType','btnEconomyType','${p.value }','${p.name }');">${p.name }</a>
															</li>
														</c:if>
													</c:forEach>
												</ul>
											</div>
										</td>
										<td>
											所属行业 :
										</td>
										<td>
											<div class="btn-group" >
												<button class="btn" id="btnBusinessScope">${obj.businessScope.name }</button> 
												<input type="hidden" id="businessScope" name="businessScope" value="${obj.businessScope.id }"/>
												<button data-toggle="dropdown" class="btn dropdown-toggle"><span class="caret"></span></button>
												<ul class="dropdown-menu">
													<c:forEach items="${bsList }" var="p">
														<li>
															<a href="javascript:selectButton('businessScope','btnBusinessScope','${p.id }','${p.name }');">${p.name }</a>
														</li>
													</c:forEach>
												</ul>
											</div>
										</td>
									</tr>
									
									<tr>
										<td>
											<span style="color:red;">* </span>营业执照副本图片：
										</td>
										<td>
											<img id="businessLicenseImg" 
												<c:choose>
													<c:when test="${obj.businessLicense != null && obj.businessLicense != '' }" >
														 src="${contextPath }/image/downloadPic/${obj.businessLicense}" 
													</c:when>
													<c:otherwise>
														 src = "" 
													</c:otherwise>
												</c:choose>
											style="height:26px;width:70px;border-width:0px;"/>
											<input type="button" name="file" value="上传图片" id="businessLicenseImport" />
											<input type="hidden" id="businessLicense" name="businessLicense" value="" />
										
										</td>
										<td>
											<span style="color:red;">* </span>组织机构代码图片：
										</td>
										<td>
											<img id="institutionalFrameworkImg" 
												<c:choose>
													<c:when test="${obj.institutionalFramework != null && obj.institutionalFramework != '' }" >
														 src="${contextPath }/image/downloadPic/${obj.institutionalFramework}" 
													</c:when>
													<c:otherwise>
														 src = "" 
													</c:otherwise>
												</c:choose>
											style="height:26px;width:70px;border-width:0px;"/>
											<input type="button" name="file" value="上传图片" id="institutionalFrameworkImport" />
											<input type="hidden" id="institutionalFramework" name="institutionalFramework" value="" />
										
										</td>
									</tr>
									
									
									<tr>
									<td>
										<span style="color:red;">* </span>税务登记证图片：
									</td>
									<td>
										<img id="taxRegistrationImg" 
											<c:choose>
												<c:when test="${obj.taxRegistration != null && obj.taxRegistration != '' }" >
									src="${contextPath }/image/downloadPic/${obj.taxRegistration}" 
											</c:when>
											<c:otherwise>
												 src = "" 
											</c:otherwise>
										</c:choose>
									style="height:26px;width:70px;border-width:0px;"/>
											<input type="button" name="file" value="上传图片" id="taxRegistrationImport" />
											<input type="hidden" id="taxRegistration" name="taxRegistration" value="" />
										
										</td>
										<td>
										</td>
										<td>
									
										</td>
									</tr>	
									
									<tr>
										<td>
											企业简介 :
										</td>
										<td colspan="3">
											<textarea id="introduction" name="introduction" style="width:700px;height:200px;font-size:12px;">${obj.introduction }</textarea>
										</td>
									</tr>
								</tbody>
								<thead>
									<tr>
										<td colspan="4" class="warning">
											联系方式
										</td>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>
											<span style="color:red;">* </span>联系电话 :
										</td>
										<td>
											<input id="telephone" name="telephone" value="${obj.telephone }" type="text" />
										</td>
									</tr>
									<tr>
										<td>
											<span style="color:red;">* </span>联系人 :
										</td>
										<td>
											<input id="contactPerson" name="contactPerson" value="${obj.contactPerson }" type="text" />
										</td>
									</tr>
									<tr>
										<td>
											联系部门 :
										</td>
										<td>
											<input id="contactDept" name="contactDept" value="${obj.contactDept }" type="text" />
										</td>
									</tr>
									<tr>
										<td>
											传真 :
										</td>
										<td>
											<input id="fax" name="fax" value="${obj.fax }" type="text" />
										</td>
									</tr>
									<tr>
										<td>
											邮箱 :
										</td>
										<td>
											<input id="email" name="email" value="${obj.email }" type="text" />
										</td>
									</tr>
									<tr>
										<td>
											公司地址 :
										</td>
										<td>
											<input id="address" name="address" value="${obj.address }" type="text" />
										</td>
									</tr>
									<tr>
										<td colspan="4" style="text-align:center;">
											 <button class="btn btn-primary" type="button" onclick="updateEntity('save','${obj.id}');">保存</button>&nbsp;&nbsp;
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
