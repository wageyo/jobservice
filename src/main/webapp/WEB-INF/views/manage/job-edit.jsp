<%@ page language="java" import="java.util.*" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh-cn">
<head>
	
	<title>编辑/审核职位</title>
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
	<script src="${contextPath}/js/manage/widget.js"></script>
	<script src="${contextPath}/js/manage/job.js"></script>
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
											招聘职位
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
											<span style="color:red;">* </span>职位名称:
										</td>
										<td>
											<input name="jobName" type="text" value="${obj.name }" id="jobName" />
											<input name="objId" type="hidden" value="${obj.id }" id="objId" />
										</td>
										<td>
											<span style="color:red;">* </span>招聘人数 :
										</td>
										<td>
											<input name="hireNumber" type="text" value="${obj.hireNumber }" id="hireNumber" />
										</td>
									</tr>
									<tr>
										<td>
											提供薪资 :
										</td>
										<td>
											<div class="btn-group" >
												<!-- 将值转换为对应显示的参数文本 -->
												<c:choose>
													<c:when test="${obj.salary !=null && obj.salary != ''}">
														<c:forEach items="${params }" var="p">
															<c:if test="${p.type == 'salary' && p.value == obj.salary}">
																<c:set var="salaryName" value="${p.name }"></c:set>
																<c:set var="salaryValue" value="${p.value }"></c:set>
															</c:if>
														</c:forEach>
													</c:when>
													<c:otherwise>
														<c:set var="salaryName" value="请选择"></c:set>
													</c:otherwise>
												</c:choose>
												<button class="btn" id="btnSalary">${salaryName }</button> 
												<input type="hidden" id="salary" name="salary" value="${salaryValue }"/>
												<button data-toggle="dropdown" class="btn dropdown-toggle"><span class="caret"></span></button>
												<ul class="dropdown-menu">
													<c:forEach items="${params }" var="p">
														<c:if test="${p.type == 'salary' }">
															<li>
																<a href="javascript:selectButton('salary','btnSalary','${p.value }','${p.name }');">${p.name }</a>
															</li>
														</c:if>
													</c:forEach>
												</ul>
											</div>
										</td>
										<td>
											<span style="color:red;">* </span>最低学历 :
										</td>
										<td>
											<div class="btn-group" >
												<!-- 将值转换为对应显示的参数文本 -->
												<c:choose>
													<c:when test="${obj.education !=null && obj.education != ''}">
														<c:forEach items="${params }" var="p">
															<c:if test="${p.type == 'education' && p.value == obj.education}">
																<c:set var="educationName" value="${p.name }"></c:set>
																<c:set var="educationValue" value="${p.value }"></c:set>
															</c:if>
														</c:forEach>
													</c:when>
													<c:otherwise>
														<c:set var="educationName" value="请选择"></c:set>
													</c:otherwise>
												</c:choose>
												<button class="btn" id="btnEducation">${educationName }</button> 
												<input type="hidden" id="education" name="education" value="${educationValue }"/>
												<button data-toggle="dropdown" class="btn dropdown-toggle"><span class="caret"></span></button>
												<ul class="dropdown-menu">
													<c:forEach items="${params }" var="p">
														<c:if test="${p.type == 'education' }">
															<li>
																<a href="javascript:selectButton('education','btnEducation','${p.value }','${p.name }');">${p.name }</a>
															</li>
														</c:if>
													</c:forEach>
												</ul>
											</div>
										</td>
									</tr>
									<tr>
										<td>
											<span style="color:red;">* </span>工作经验 :
										</td>
										<td>
											<div class="btn-group" >
												<!-- 将值转换为对应显示的参数文本 -->
												<c:choose>
													<c:when test="${obj.experience !=null && obj.experience != ''}">
														<c:forEach items="${params }" var="p">
															<c:if test="${p.type == 'experience' && p.value == obj.experience}">
																<c:set var="experienceName" value="${p.name }"></c:set>
																<c:set var="experienceValue" value="${p.value }"></c:set>
															</c:if>
														</c:forEach>
													</c:when>
													<c:otherwise>
														<c:set var="experienceName" value="请选择"></c:set>
													</c:otherwise>
												</c:choose>
												<button class="btn" id="btnExperience">${experienceName }</button> 
												<input type="hidden" id="experience" name="experience" value="${experienceValue }"/>
												<button data-toggle="dropdown" class="btn dropdown-toggle"><span class="caret"></span></button>
												<ul class="dropdown-menu">
													<c:forEach items="${params }" var="p">
														<c:if test="${p.type == 'experience' }">
															<li>
																<a href="javascript:selectButton('experience','btnExperience','${p.value }','${p.name }');">${p.name }</a>
															</li>
														</c:if>
													</c:forEach>
												</ul>
											</div>
										</td>
										<td>
											性别 :
										</td>
										<td>
											<div class="btn-group" >
												<!-- 将值转换为对应显示的参数文本 -->
												<c:choose>
													<c:when test="${obj.gender !=null && obj.gender != ''}">
														<c:forEach items="${params }" var="p">
															<c:if test="${p.type == 'gender' && p.value == obj.gender}">
																<c:set var="genderName" value="${p.name }"></c:set>
																<c:set var="genderValue" value="${p.value }"></c:set>
															</c:if>
														</c:forEach>
													</c:when>
													<c:otherwise>
														<c:set var="genderName" value="请选择"></c:set>
													</c:otherwise>
												</c:choose>
												<button class="btn" id="btnGender">${genderName }</button> 
												<input type="hidden" id="gender" name="gender" value="${genderValue }"/>
												<button data-toggle="dropdown" class="btn dropdown-toggle"><span class="caret"></span></button>
												<ul class="dropdown-menu">
													<c:forEach items="${params }" var="p">
														<c:if test="${p.type == 'gender' }">
															<li>
																<a href="javascript:selectButton('gender','btnGender','${p.value }','${p.name }');">${p.name }</a>
															</li>
														</c:if>
													</c:forEach>
												</ul>
											</div>
										</td>
									</tr>
									<tr>
										<td>
											<span style="color:red;">* </span>岗位性质 :
										</td>
										<td>
											<div class="btn-group" >
												<!-- 将值转换为对应显示的参数文本 -->
												<c:choose>
													<c:when test="${obj.nature !=null && obj.nature != ''}">
														<c:forEach items="${params }" var="p">
															<c:if test="${p.type == 'jobNature' && p.value == obj.nature}">
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
														<c:if test="${p.type == 'jobNature' }">
															<li>
																<a href="javascript:selectButton('nature','btnNature','${p.value }','${p.name }');">${p.name }</a>
															</li>
														</c:if>
													</c:forEach>
												</ul>
											</div>
										</td>
										<td>
											添加有效期 :
										</td>
										<td>
											<div class="btn-group" >
												<button class="btn" id="btnEffectiveTime">请选择</button> 
												<input type="hidden" id="effectiveTime " name="effectiveTime" value=""/>
												<button data-toggle="dropdown" class="btn dropdown-toggle"><span class="caret"></span></button>
												<ul class="dropdown-menu">
													<c:forEach items="${params }" var="p">
														<c:if test="${p.type == 'effectiveTime' }">
															<li>
																<a href="javascript:selectButton('effectiveTime','btnEffectiveTime','${p.value }','${p.name }');">${p.name }</a>
															</li>
														</c:if>
													</c:forEach>
												</ul>
											</div>
											<fmt:formatDate value="${obj.effectiveTime }" pattern="yyyy年MM月dd日 hh:mm:ss" var="effectiveTime"/>
											(目前有效期至:${effectiveTime })
										</td>
									</tr>
									<tr>
										<td>
											 <span style="color:red;">* </span>工作地点 :
										</td>
										<td colspan="3">
											<div class="btn-group" >
												<button class="btn" id="areaName1" name="areaName1">${obj.workPlace.name }</button> 
												<input type="hidden" id="areaValue1" name="areaValue1" value="${obj.workPlace.code }"/>
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
									<tr>
										<td>
											<span style="color:red;">* </span>工作种类 :
										</td>
										<td colspan="3">
											<div class="btn-group" >
												<button class="btn" id="jcName1" name="jcName1">${obj.jobCategory.name }</button> 
												<input type="hidden" id="jcValue1" name="jcValue1" value="${obj.jobCategory.code }"/>
												<button data-toggle="dropdown" class="btn dropdown-toggle"><span class="caret"></span></button>
												<ul class="dropdown-menu" id="jcLevel1">
													<c:forEach items="${jcList }" var="p">
														<li>
															<a href="javascript:selectJcLevel1('jcValue','jcName','${p.code }','${p.name }','jcLevel');">${p.name }</a>
														</li>
													</c:forEach>
												</ul>
											</div>
											<div class="btn-group" >
												<button class="btn" id="jcName2" name="jcName2">请选择</button> 
												<input type="hidden" id="jcValue2" name="jcValue2" value=""/>
												<button data-toggle="dropdown" class="btn dropdown-toggle"><span class="caret"></span></button>
												<ul class="dropdown-menu" id="jcLevel2">
													<li>
														<a href="javascript:selectJcLevel2('jcValue','jcName','','请选择','jcLevel');">请选择</a>
													</li>
												</ul>
											</div>
											<div class="btn-group" >
												<button class="btn" id="jcName3" name="jcName3">请选择</button> 
												<input type="hidden" id="jcValue3" name="jcValue3" value=""/>
												<button data-toggle="dropdown" class="btn dropdown-toggle"><span class="caret"></span></button>
												<ul class="dropdown-menu" id="jcLevel3">
													<li>
														<a href="javascript:selectJcLevel3('jcValue','jcName','','请选择');">请选择</a>
													</li>
												</ul>
											</div>
										</td>
									</tr>
									<tr>
										<td>
											其他福利 :
										</td>
										<td colspan="3">
											<c:forEach items="${params }" var="p">
												<c:if test="${p.type == 'benefit' }">
													<input type="checkbox" value="${p.value }" name="provideBenefit" />${p.name }&nbsp;&nbsp;
													</c:if>
											</c:forEach>
										</td>
									</tr>
									<tr>
										<td>
											岗位描述 :
										</td>
										<td colspan="3">
											<textarea id="description" name="description" style="width:700px;height:200px;font-size:12px;">${obj.description }</textarea>
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
											<span style="color:red;">* </span>联系人 :
										</td>
										<td>
											<input id="contactPerson" name="contactPerson" value="${obj.contactPerson }" type="text" />
										</td>
									</tr>
									<tr>
										<td>
											<span style="color:red;">* </span>联系电话 :
										</td>
										<td>
											<input id="contactTel" name="contactTel" value="${obj.contactTel }" type="text" />
										</td>
									</tr>
									<tr>
										<td>
											邮箱 :
										</td>
										<td>
											<input id="contactEmail" name="contactEmail" value="${obj.contactEmail }" type="text" />
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
