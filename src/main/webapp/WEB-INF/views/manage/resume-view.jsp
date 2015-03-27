<%@ page language="java" import="java.util.*" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh-cn">
<head>
	
	<title>查看/审核简历</title>
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
	<script src="${contextPath}/js/manage/common.js"></script>
	<script src="${contextPath}/js/manage/widget.js"></script>
	<script src="${contextPath}/js/manage/resume.js"></script>
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
											简历信息
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
											<span style="color:red;">* </span>简历标题 :
										</td>
										<td colspan="3">
											<input name="title" type="text" value="${obj.title }" id="title" />
											<input name="objId" type="hidden" value="${obj.id }" id="objId" />
										</td>
									</tr>
									<tr>
										<td>
											<span style="color:red;">* </span>姓名 :
										</td>
										<td>
											<input name="resumeName" type="text" value="${obj.name }" id="resumeName" />
										</td>
										<td>
											<span style="color:red;">* </span>性别 :
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
											民族 :
										</td>
										<td>
											<input name="race" type="text" value="${obj.race }" id="race" />
										</td>
										<td>
											户口状况 :
										</td>
										<td>
											<div class="btn-group" >
												<!-- 将值转换为对应显示的参数文本 -->
												<c:choose>
													<c:when test="${obj.hukouStatus !=null && obj.hukouStatus != ''}">
														<c:forEach items="${params }" var="p">
															<c:if test="${p.type == 'huKou' && p.value == obj.hukouStatus}">
																<c:set var="hukouStatusName" value="${p.name }"></c:set>
																<c:set var="hukouStatusValue" value="${p.value }"></c:set>
															</c:if>
														</c:forEach>
													</c:when>
													<c:otherwise>
														<c:set var="hukouStatusName" value="请选择"></c:set>
													</c:otherwise>
												</c:choose>
												<button class="btn" id="btnHukouStatus">${hukouStatusName }</button> 
												<input type="hidden" id="hukouStatus" name="hukouStatus" value="${hukouStatusValue }"/>
												<button data-toggle="dropdown" class="btn dropdown-toggle"><span class="caret"></span></button>
												<ul class="dropdown-menu">
													<c:forEach items="${params }" var="p">
														<c:if test="${p.type == 'huKou' }">
															<li>
																<a href="javascript:selectButton('hukouStatus','btnHukouStatus','${p.value }','${p.name }');">${p.name }</a>
															</li>
														</c:if>
													</c:forEach>
												</ul>
											</div>
										</td>
									</tr>
									<tr>
										<td>
											<span style="color:red;">* </span>出生日期 :
										</td>
										<td colspan="3">
											<div class="btn-group" >
												<c:choose>
													<c:when test="${obj.birth != '' && obj.birth != null }">
														<c:set var="year">${fn:substring(obj.birth,0,4) }</c:set>
													</c:when>
													<c:otherwise>
														<c:set var="year">请选择</c:set>
													</c:otherwise>
												</c:choose>
												<button class="btn" id="btnYear">${year }</button> 
												<input type="hidden" id="year" name="year" value="${year }"/>
												<button data-toggle="dropdown" class="btn dropdown-toggle"><span class="caret"></span></button>
												<ul class="dropdown-menu">
													<c:forEach begin="1959" end="1998" step="1" var="i">
														<li>
															<a href="javascript:selectButton('year','btnYear','${i }','${i }');">${i }</a>
														</li>
													</c:forEach>
												</ul>
											</div>
											<div class="btn-group" >
												<c:choose>
													<c:when test="${obj.birth != '' && obj.birth != null }">
														<c:set var="month">${fn:substring(obj.birth,5,7) }</c:set>
													</c:when>
													<c:otherwise>
														<c:set var="month">01</c:set>
													</c:otherwise>
												</c:choose>
												<button class="btn" id="btnMonth">${month }</button> 
												<input type="hidden" id="month" name="month" value="${month }"/>
												<button data-toggle="dropdown" class="btn dropdown-toggle"><span class="caret"></span></button>
												<ul class="dropdown-menu">
													<c:forEach begin="1" end="12" step="1" var="i">
														<li>
															<c:choose>
																<c:when test="${i < 10 }">
																	<c:set var="k">0${i }</c:set>
																</c:when>
																<c:otherwise>
																	<c:set var="k">${i }</c:set>
																</c:otherwise>
															</c:choose>
															<a href="javascript:selectButton('month','btnMonth','${k }','${k }');">${k }</a>
														</li>
													</c:forEach>
												</ul>
											</div>
											<div class="btn-group" >
												<c:choose>
													<c:when test="${obj.birth != '' && obj.birth != null }">
														<c:set var="day">${fn:substring(obj.birth,8,10) }</c:set>
													</c:when>
													<c:otherwise>
														<c:set var="day">01</c:set>
													</c:otherwise>
												</c:choose>
												<button class="btn" id="btnDay">${day }</button> 
												<input type="hidden" id="day" name="day" value="${day }"/>
												<button data-toggle="dropdown" class="btn dropdown-toggle"><span class="caret"></span></button>
												<ul class="dropdown-menu">
													<c:forEach begin="1" end="31" step="1" var="i">
														<li>
															<c:choose>
																<c:when test="${i < 10 }">
																	<c:set var="k">0${i }</c:set>
																</c:when>
																<c:otherwise>
																	<c:set var="k">${i }</c:set>
																</c:otherwise>
															</c:choose>
															<a href="javascript:selectButton('day','btnDay','${k }','${k }');">${k }</a>
														</li>
													</c:forEach>
												</ul>
											</div>
										</td>
									</tr>
									<tr>
										<td>
											户籍所在地 :
										</td>
										<td colspan="3">
											<div class="btn-group" >
												<c:choose>
													<c:when test="${obj.hukou != null}">
														<c:set var="areaName1" value="${obj.hukou.name }"></c:set>
														<c:set var="areaValue1" value="${obj.hukou.name }"></c:set>
													</c:when>
													<c:otherwise>
														<c:set var="areaName1" value="请选择"></c:set>
													</c:otherwise>
												</c:choose>
												<button class="btn" id="areaName1" name="areaName1">${areaName1 }</button> 
												<input type="hidden" id="areaValue1" name="areaValue1" value="${areaValue1 }"/>
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
														<a href="javascript:selectAreaLevel3('areaValue','areaName','','请选择','areaLevel');">请选择</a>
													</li>
												</ul>
											</div>
										</td>
									</tr>
									<tr>
										<td>
											户口地址 :
										</td>
										<td colspan="3">
											<input name="hukouAddress" id="hukouAddress" type="text" value="${obj.hukouAddress }" />
										</td>
									</tr>
									<tr>
										<td>
											现居居地 :
										</td>
										<td colspan="3">
											<input name="address" id="address" type="text" value="${obj.address }" />
										</td>
									</tr>
									<tr>
										<td>
											婚姻状况 :
										</td>
										<td colspan="3">
											<div class="btn-group" >
												<!-- 将值转换为对应显示的参数文本 -->
												<c:choose>
													<c:when test="${obj.marriage !=null && obj.marriage != ''}">
														<c:forEach items="${params }" var="p">
															<c:if test="${p.type == 'marriage' && p.value == obj.marriage}">
																<c:set var="marriageName" value="${p.name }"></c:set>
																<c:set var="marriageValue" value="${p.value }"></c:set>
															</c:if>
														</c:forEach>
													</c:when>
													<c:otherwise>
														<c:set var="marriageName" value="请选择"></c:set>
													</c:otherwise>
												</c:choose>
												<button class="btn" id="btnMarriage">${marriageName }</button> 
												<input type="hidden" id="marriage" name="marriage" value="${marriageValue }"/>
												<button data-toggle="dropdown" class="btn dropdown-toggle"><span class="caret"></span></button>
												<ul class="dropdown-menu">
													<c:forEach items="${params }" var="p">
														<c:if test="${p.type == 'marriage' }">
															<li>
																<a href="javascript:selectButton('marriage','btnMarriage','${p.value }','${p.name }');">${p.name }</a>
															</li>
														</c:if>
													</c:forEach>
												</ul>
											</div>
										</td>
									</tr>
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
											电话/手机 :
										</td>
										<td colspan="3">
											<input name="phone" id="phone" type="text" value="${obj.phone }" />
										</td>
									</tr>
									<tr>
										<td>
											联系QQ :
										</td>
										<td colspan="3">
											<input name="qq" id="qq" type="text" value="${obj.qq }" />
										</td>
									</tr>
									<tr>
										<td>
											邮箱 :
										</td>
										<td colspan="3">
											<input name="email" id="email" type="text" value="${obj.email }" />
										</td>
									</tr>
								</tbody>
								<thead>
									<tr>
										<td colspan="4" class="warning">
											残疾情况
										</td>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>
											残疾证号 :
										</td>
										<td colspan="3">
											<input name="disabilityCard" id="disabilityCard" type="text" value="${obj.disabilityCard }" />
										</td>
									</tr>
									<tr>
										<td>
											残疾类别 :
										</td>
										<td colspan="3">
											<div class="btn-group" >
												<!-- 将值转换为对应显示的参数文本 -->
												<c:choose>
													<c:when test="${obj.disabilityCategory !=null && obj.disabilityCategory != ''}">
														<c:forEach items="${params }" var="p">
															<c:if test="${p.type == 'disabilityCategory' && p.value == obj.disabilityCategory}">
																<c:set var="disabilityCategoryName" value="${p.name }"></c:set>
																<c:set var="disabilityCategoryValue" value="${p.value }"></c:set>
															</c:if>
														</c:forEach>
													</c:when>
													<c:otherwise>
														<c:set var="disabilityCategoryName" value="请选择"></c:set>
													</c:otherwise>
												</c:choose>
												<button class="btn" id="btnDisabilityCategory">${disabilityCategoryName }</button> 
												<input type="hidden" id="disabilityCategory" name="disabilityCategory" value="${disabilityCategoryValue }"/>
												<button data-toggle="dropdown" class="btn dropdown-toggle"><span class="caret"></span></button>
												<ul class="dropdown-menu">
													<c:forEach items="${params }" var="p">
														<c:if test="${p.type == 'disabilityCategory' }">
															<li>
																<a href="javascript:selectButton('disabilityCategory','btnDisabilityCategory','${p.value }','${p.name }');">${p.name }</a>
															</li>
														</c:if>
													</c:forEach>
												</ul>
											</div>
										</td>
									</tr>
									<tr>
										<td>
											残疾等级 :
										</td>
										<td colspan="3">
											<div class="btn-group" >
												<!-- 将值转换为对应显示的参数文本 -->
												<c:choose>
													<c:when test="${obj.disabilityLevel !=null && obj.disabilityLevel != ''}">
														<c:forEach items="${params }" var="p">
															<c:if test="${p.type == 'disabilityLevel' && p.value == obj.disabilityLevel}">
																<c:set var="disabilityLevelName" value="${p.name }"></c:set>
																<c:set var="disabilityLevelValue" value="${p.value }"></c:set>
															</c:if>
														</c:forEach>
													</c:when>
													<c:otherwise>
														<c:set var="disabilityLevelName" value="请选择"></c:set>
													</c:otherwise>
												</c:choose>
												<button class="btn" id="btnDisabilityLevel">${disabilityLevelName }</button> 
												<input type="hidden" id="disabilityLevel" name="disabilityLevel" value="${disabilityLevelValue }"/>
												<button data-toggle="dropdown" class="btn dropdown-toggle"><span class="caret"></span></button>
												<ul class="dropdown-menu">
													<c:forEach items="${params }" var="p">
														<c:if test="${p.type == 'disabilityLevel' }">
															<li>
																<a href="javascript:selectButton('disabilityLevel','btnDisabilityLevel','${p.value }','${p.name }');">${p.name }</a>
															</li>
														</c:if>
													</c:forEach>
												</ul>
											</div>
										</td>
									</tr>
									<tr>
										<td>
											残疾部位 :
										</td>
										<td colspan="3">
											<div class="btn-group" >
												<!-- 将值转换为对应显示的参数文本 -->
												<c:choose>
													<c:when test="${obj.disabilityPart !=null && obj.disabilityPart != ''}">
														<c:forEach items="${params }" var="p">
															<c:if test="${p.type == 'disabilityPart' && p.value == obj.disabilityPart}">
																<c:set var="disabilityPartName" value="${p.name }"></c:set>
																<c:set var="disabilityPartValue" value="${p.value }"></c:set>
															</c:if>
														</c:forEach>
													</c:when>
													<c:otherwise>
														<c:set var="disabilityPartName" value="请选择"></c:set>
													</c:otherwise>
												</c:choose>
												<button class="btn" id="btnDisabilityPart">${disabilityPartName }</button> 
												<input type="hidden" id="disabilityPart" name="disabilityPart" value="${disabilityPartValue }"/>
												<button data-toggle="dropdown" class="btn dropdown-toggle"><span class="caret"></span></button>
												<ul class="dropdown-menu">
													<c:forEach items="${params }" var="p">
														<c:if test="${p.type == 'disabilityPart' }">
															<li>
																<a href="javascript:selectButton('disabilityPart','btnDisabilityPart','${p.value }','${p.name }');">${p.name }</a>
															</li>
														</c:if>
													</c:forEach>
												</ul>
											</div>
										</td>
									</tr>
								</tbody>
								<thead>
									<tr>
										<td colspan="4" class="warning">
											求职意向
										</td>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>
											就业失业登记证号 :
										</td>
										<td colspan="3">
											<input name="shiYeHao" id="shiYeHao" type="text" value="${obj.shiYeHao }" />
										</td>
									</tr>
									<tr>
										<td>
											职位类型 :
										</td>
										<td>
											<div class="btn-group" >
												<!-- 将值转换为对应显示的参数文本 -->
												<c:choose>
													<c:when test="${obj.jobNature !=null && obj.jobNature != ''}">
														<c:forEach items="${params }" var="p">
															<c:if test="${p.type == 'jobNature' && p.value == obj.jobNature}">
																<c:set var="jobNatureName" value="${p.name }"></c:set>
																<c:set var="jobNatureValue" value="${p.value }"></c:set>
															</c:if>
														</c:forEach>
													</c:when>
													<c:otherwise>
														<c:set var="jobNatureName" value="请选择"></c:set>
													</c:otherwise>
												</c:choose>
												<button class="btn" id="btnJobNature">${jobNatureName }</button> 
												<input type="hidden" id="jobNature" name="jobNature" value="${jobNatureValue }"/>
												<button data-toggle="dropdown" class="btn dropdown-toggle"><span class="caret"></span></button>
												<ul class="dropdown-menu">
													<c:forEach items="${params }" var="p">
														<c:if test="${p.type == 'jobNature' }">
															<li>
																<a href="javascript:selectButton('jobNature','btnJobNature','${p.value }','${p.name }');">${p.name }</a>
															</li>
														</c:if>
													</c:forEach>
												</ul>
											</div>
										</td>
										<td>
											期望薪水 :
										</td>
										<td>
											<div class="btn-group" >
												<!-- 将值转换为对应显示的参数文本 -->
												<c:choose>
													<c:when test="${obj.desireSalary !=null && obj.desireSalary != ''}">
														<c:forEach items="${params }" var="p">
															<c:if test="${p.type == 'salary' && p.value == obj.desireSalary}">
																<c:set var="desireSalaryName" value="${p.name }"></c:set>
																<c:set var="desireSalaryValue" value="${p.value }"></c:set>
															</c:if>
														</c:forEach>
													</c:when>
													<c:otherwise>
														<c:set var="desireSalaryName" value="请选择"></c:set>
													</c:otherwise>
												</c:choose>
												<button class="btn" id="btnDesireSalary">${desireSalaryName }</button> 
												<input type="hidden" id="desireSalary" name="desireSalary" value="${desireSalaryValue }"/>
												<button data-toggle="dropdown" class="btn dropdown-toggle"><span class="caret"></span></button>
												<ul class="dropdown-menu">
													<c:forEach items="${params }" var="p">
														<c:if test="${p.type == 'salary' }">
															<li>
																<a href="javascript:selectButton('desireSalary','btnDesireSalary','${p.value }','${p.name }');">${p.name }</a>
															</li>
														</c:if>
													</c:forEach>
												</ul>
											</div>
										</td>
									</tr>
									<tr>
										<td>
											<span style="color:red;">* </span>期望职位类别 :
										</td>
										<td colspan="3">
											<div class="btn-group" >
												<!-- 将值转换为对应显示的参数文本 -->
											 	<c:choose>
													<c:when test="${obj.desireJob != null}">
														<c:set var="jcName1" value="${obj.desireJob.name}"></c:set>
														<c:set var="jcValue1" value="${obj.desireJob.code }"></c:set>
													</c:when>
													<c:otherwise>
														<c:set var="jcName1" value="请选择"></c:set>
													</c:otherwise>
												</c:choose>
												<button class="btn" id="jcName1" name="jcName1">${jcName1 }</button> 
												<input type="hidden" id="jcValue1" name="jcValue1" value="${jcValue1 }"/>
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
											 <span style="color:red;">* </span>期望工作地点 :
										</td>
										<td colspan="3">
											<div class="btn-group" >
												<!-- 将值转换为对应显示的参数文本 -->
											 	<c:choose>
													<c:when test="${obj.desireAddress != null}">
														<c:set var="desireAddressName1" value="${obj.desireAddress.name}"></c:set>
														<c:set var="desireAddressValue1" value="${obj.desireAddress.code }"></c:set>
													</c:when>
													<c:otherwise>
														<c:set var="desireAddressName1" value="请选择"></c:set>
													</c:otherwise>
												</c:choose>
												<button class="btn" id="desireAddressName1" name="desireAddressName1">${desireAddressName1 }</button> 
												<input type="hidden" id="desireAddressValue1" name="desireAddressValue1" value="${desireAddressValue1 }"/>
												<button data-toggle="dropdown" class="btn dropdown-toggle"><span class="caret"></span></button>
												<ul class="dropdown-menu" id="desireAddressLevel1">
													<c:forEach items="${provinceList }" var="p">
														<li>
															<a href="javascript:selectAreaLevel1('desireAddressValue','desireAddressName','${p.code }','${p.name }','desireAddressLevel');">${p.name }</a>
														</li>
													</c:forEach>
												</ul>
											</div>
											<div class="btn-group" >
												<button class="btn" id="desireAddressName2" name="desireAddressName2">请选择</button> 
												<input type="hidden" id="desireAddressValue2" name="desireAddressValue2" value=""/>
												<button data-toggle="dropdown" class="btn dropdown-toggle"><span class="caret"></span></button>
												<ul class="dropdown-menu" id="desireAddressLevel2">
													<li>
														<a href="javascript:selectAreaLevel2('desireAddressValue','desireAddressName','','请选择','desireAddressLevel');">请选择</a>
													</li>
												</ul>
											</div>
											<div class="btn-group" >
												<button class="btn" id="desireAddressName3" name="desireAddressName3">请选择</button> 
												<input type="hidden" id="desireAddressValue3" name="desireAddressValue3" value=""/>
												<button data-toggle="dropdown" class="btn dropdown-toggle"><span class="caret"></span></button>
												<ul class="dropdown-menu" id="desireAddressLevel3">
													<li>
														<a href="javascript:selectAreaLevel3('desireAddressValue','desireAddressName','','请选择');">请选择</a>
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
													<input type="checkbox" value="${p.value }" name="benefit" />${p.name }&nbsp;&nbsp;
													</c:if>
											</c:forEach>
										</td>
									</tr>
									<tr>
										<td>
											其他要求 :
										</td>
										<td colspan="3">
											<textarea id="provideOther" name="provideOther" style="width:700px;height:200px;font-size:12px;">${obj.provideOther }</textarea>
										</td>
									</tr>
									<tr>
										<td>
											到岗时间 :
										</td>
										<td colspan="3">
											<div class="btn-group" >
												<!-- 将值转换为对应显示的参数文本 -->
												<c:choose>
													<c:when test="${obj.state !=null && obj.state != ''}">
														<c:forEach items="${params }" var="p">
															<c:if test="${p.type == 'state' && p.value == obj.state}">
																<c:set var="stateName" value="${p.name }"></c:set>
																<c:set var="stateValue" value="${p.value }"></c:set>
															</c:if>
														</c:forEach>
													</c:when>
													<c:otherwise>
														<c:set var="stateName" value="请选择"></c:set>
													</c:otherwise>
												</c:choose>
												<button class="btn" id="btnState">${stateName }</button> 
												<input type="hidden" id="state" name="state" value="${stateValue }"/>
												<button data-toggle="dropdown" class="btn dropdown-toggle"><span class="caret"></span></button>
												<ul class="dropdown-menu">
													<c:forEach items="${params }" var="p">
														<c:if test="${p.type == 'state' }">
															<li>
																<a href="javascript:selectButton('state','btnState','${p.value }','${p.name }');">${p.name }</a>
															</li>
														</c:if>
													</c:forEach>
												</ul>
											</div>
										</td>
									</tr>
								</tbody>	
								<thead>
									<tr>
										<td colspan="4" class="warning">
											教育培训
										</td>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>
											<span style="color:red;">* </span>学历 :
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
										<td>
											专业 :
										</td>
										<td>
											<input id="major" name="major" value="${obj.major }" type="text" />
										</td>
									</tr>
									<tr>
										<td>
											毕业院校 :
										</td>
										<td>
											<input id="school" name="school" value="${obj.school }" type="text" />
										</td>
										<td>
											特长 :
										</td>
										<td>
											<input id="experts" name="experts" value="${obj.experts }" type="text" />
										</td>
									</tr>
									<tr>
										<td>
											培训情况或需求 :
										</td>
										<td colspan="3">
											<input id="training" name="training" value="${obj.training }" type="text" />
										</td>
									</tr>
									<tr>
										<td>
											自我评价 :
										</td>
										<td colspan="3">
											 <textarea id="selfEvaluation" name="selfEvaluation" style="width:700px;height:200px;font-size:12px;">${obj.selfEvaluation }</textarea>
										</td>
									</tr>
								</tbody>
								<thead>
									<tr>
										<td colspan="4" class="warning">
											工作经历
										</td>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>
											工作年限 :
										</td>
										<td colspan="3">
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
									</tr>
									<!-- 从业经历  开始 -->
									<c:if test="${obj.workExperienceList != null }">
										<c:forEach items="${obj.workExperienceList }" var="we" varStatus="i">
											<tr style="border-top: 1px solid rgb(110, 170, 168);">
												<td>
													所在公司 :
												</td>
												<td>
													<input id="companyName" name="companyName" value="${we.companyName }" type="text" readonly="readonly" />
												</td>
												<td>
													在职时间 :
												</td>
												<td>
													<input id="workTime" name="workTime" value="${we.workTime }" type="text" readonly="readonly" />
												</td>
											</tr>
											<tr>
												<td>
													所在岗位 :
												</td>
												<td>
													<input id="jobName" name="jobName" value="${we.jobName }" type="text" readonly="readonly" />
												</td>
												<td>
													离职原因 :
												</td>
												<td>
													<input id="leaveReason" name="leaveReason" value="${we.leaveReason }" type="text" readonly="readonly" />
												</td>
											</tr>
											<tr>
												<td>
													工作成绩/内容 :
												</td>
												<td colspan="3">
													<textarea id="jobContent" name="jobContent" style="width:700px;height:80px;font-size:12px;" readonly="readonly">${we.jobContent }</textarea>
												</td>
											</tr>
										</c:forEach>
									</c:if>
									<!-- 从业经历  结束 -->
									
								</tbody>
								<thead>
									<tr>
										<td colspan="4" class="warning">
											就业管理
										</td>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>
											职业测评情况 :
										</td>
										<td colspan="3">
											<input id="careerTest" name="careerTest" value="${obj.careerTest }" type="text" />
										</td>
									</tr>
									<tr>
										<td>
											就业状况 :
										</td>
										<td colspan="3">
											<div class="btn-group" >
												<!-- 将值转换为对应显示的参数文本 -->
												<c:choose>
													<c:when test="${obj.processState !=null && obj.processState != ''}">
														<c:forEach items="${params }" var="p">
															<c:if test="${p.type == 'processState' && p.value == obj.processState}">
																<c:set var="processStateName" value="${p.name }"></c:set>
																<c:set var="processStateValue" value="${p.value }"></c:set>
															</c:if>
														</c:forEach>
													</c:when>
													<c:otherwise>
														<c:set var="processStateName" value="请选择"></c:set>
													</c:otherwise>
												</c:choose>
												<button class="btn" id="btnProcessState">${processStateName }</button> 
												<input type="hidden" id="processState" name="processState" value="${processStateValue }"/>
												<button data-toggle="dropdown" class="btn dropdown-toggle"><span class="caret"></span></button>
												<ul class="dropdown-menu">
													<c:forEach items="${params }" var="p">
														<c:if test="${p.type == 'processState' }">
															<li>
																<a href="javascript:selectButton('processState','btnProcessState','${p.value }','${p.name }');">${p.name }</a>
															</li>
														</c:if>
													</c:forEach>
												</ul>
											</div>
										</td>
									</tr>
									
									<!-- 测评办理情况 -->
									<tr>
										<td>
											时间 :
										</td>
										<td colspan="3" style="text-align:center;">
											办理情况 :
										</td>
									</tr>
									<c:if test="${obj.unempManageList[0] == null }">
										<tr>
											<td colspan="4" class="warning" style="color:red; text-align:center;">没有相关的办理信息</td>
										</tr>
									</c:if>
									<c:if test="${obj.unempManageList != null }">
										<c:forEach items="${obj.unempManageList }" var="um" varStatus="i">
											<tr>
												<td>
													<input type="text" value="${um.time }" style="width:120px;" readonly="readonly"/>
												</td>
												<td colspan="3">
													<input name="content" value="${um.content }" type="text" style="width:80%;" readonly="readonly"/>
												</td>
											</tr>
										</c:forEach>
									</c:if>
								</tbody>
								<!-- 下部按钮区  开始 -->
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
								<!-- 下部按钮区  结束 -->
							</table>


						</div>
						<!-- class='span12' 结束 -->
						
					</div>
					<!-- class='row' 结束 -->
						
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
