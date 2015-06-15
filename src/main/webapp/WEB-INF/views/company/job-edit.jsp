<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>残疾人就业信息网</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="keywords" content="残疾人招聘信息,残疾人就业信息,残疾人人才网,残疾人找工作" />
	<meta content="残疾人招聘就业" name="description" />
	
	<link rel="shortcut icon" href="${contextPath}/images/HomePageImage/favicon.ico" type="image/x-icon" />
	<link href="${contextPath}/css/Public.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/HomePageHeader.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/HomePageFooter.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/PublicStatusBar.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/SetHeaderStyle.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/PublicframeFour.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/PublicTableOne.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/PublicAccordion.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/ManagePositions.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/comp_info_edit.css" rel="stylesheet" type="text/css" />
	
	<script type="text/javascript" src="${contextPath}/js/jquery.js"></script>
	<script type="text/javascript" src="${contextPath}/js/common.js"></script>
	<script type="text/javascript" src="${contextPath}/js/widget.js"></script>
	<script type="text/javascript" src="${contextPath}/js/check/job-check.js"></script>
	<script type="text/javascript">
	</script>
</head>
<body>
	<div id="container" class="container">
	
		<!-- 顶部工具条栏 -->
		<jsp:include page="../formatter/status-bar.jsp" />
		
		<!-- 头部导航及图片栏目 -->
		<jsp:include page="../formatter/header.jsp" />
		<div class="clearboth"></div>
		
		<!-- ******* 中部内容显示区 ******* start ********** -->
		<div id="content" class="content">
			
			<!-- 上部区域xx中心提示文字 -->
            <div class="positiontopbg">
                <div class="positiontopleft">
                    <span style="cursor:pointer;" onclick="javascript:window.location.href = '${contextPath}/user/goCenter'">企业管理中心</span></div>
                <div class="positiontopright">
                </div>
            </div>
            
            <!-- 下部具体内容显示   start -->
            <div class="positionbottom">
                <div class="positionleftline">
                </div>
                
                <!----------- 管理中心左边菜单栏  ------start ----------->
                <jsp:include page="left-nav.jsp" />
                <!----------- 管理中心左边菜单栏  ------end ----------->
                
                <div class="positionrightline">
                </div>
                <!----------- 管理中心右边 详细内容显示  ------start ----------->
                <div id="main0" class="positionmiddletext">
                	<div class="form0" style="margin-bottom: -10px; _margin-bottom: -30px;">
                        <div style="width: 780px;" class="contraction" id="ModelOneID">
                            <div class="contractiontop">
                                <div class="contractionleft">
                                </div>
                                <div class="contractiontoptext">
                                    <span class="spanFirst">当前位置:</span><span class="spanSecond">企业管理中心 &gt;&gt; 职位管理 &gt;&gt; 修改职位信息</span></div>
                                <div class="contractionright">
                                </div>
                                <div class="contractionclick">
                                </div>
                            </div>
                        </div>
                    </div>
                	
                	<form action="${contextPath }/secure/job/update" method="post" onsubmit="return check('update');" id="jobInfo">
	                	<ul class="block">
	                        <li>
	                            <div class="form0">
	                                <div style="width: 780px;" class="contraction" id="ModelOneID">
	                                    <div style="display: block;" class="contractioncontent">
	                                        <table class="tbl1" id="EnterpriseinformationForm">
	                                            <tbody>
		                                            <tr>
		                                                <td class="textTop">
	                                                  		 <em style="display: inline;">* </em>职位名称：
		                                                </td>
		                                                <td>
		                                                	<input id="name" name="name" type="text" value="${job.name }" type="text" />
		                                                	<input type="hidden" name="id" value="${job.id }" />
		                                                </td>
		                                            </tr>
		                                             <tr>
		                                                <td class="textTop">
	                                                  		 <em style="display: inline;">* </em>招聘人数：
		                                                </td>
		                                                <td>
		                                                	<input id="hireNumber" name="hireNumber" type="text" value="${job.hireNumber }" type="text" />
		                                                </td>
		                                            </tr>
		                                             <tr>
		                                                <td class="textTop">
	                                                  		残疾类别：
		                                                </td>
		                                                <td>
		                                                	<c:forEach items="${params }" var="p">
																<c:if test="${p.type == 'disabilityCategory' && p.value != null && p.value != ''}">
																	<input type="checkbox" name="disabilityCategory" title="${p.name }" value="${p.value }" <c:if test="${fn:contains(job.disabilityCategory, p.value) }">checked="checked"</c:if> />${p.name }&nbsp;
																</c:if>
															</c:forEach>
															
		                                            <!--     	<select name="disabilityCategory" id="disabilityCategory" class="length informationEdits needSyncValue NeedValidate" >
		                                                    	<c:forEach items="${params }" var="p">
																	<c:if test="${p.type == 'disabilityCategory' }">
																		<option value="${p.value }"  <c:if test="${job.disabilityCategory == p.value }">selected="selected"</c:if>>${p.name }</option>
																	</c:if>
																</c:forEach>
		                                                    </select> -->
		                                                </td>
		                                            </tr>
		                                            <tr>
		                                                <td class="textTop">
	                                                  		残疾等级：
		                                                </td>
		                                                <td>
		                                                	<c:forEach items="${params }" var="p">
																<c:if test="${p.type == 'disabilityLevel' && p.value != null && p.value != '' }">
																	<input type="checkbox" name="disabilityLevel" title="${p.name }" value="${p.value }" <c:if test="${fn:contains(job.disabilityLevel, p.value) }">checked="checked"</c:if> />${p.name }&nbsp;
																</c:if>
															</c:forEach>
		                                                <!-- 	<select name="disabilityLevel" id="disabilityLevel" class="length informationEdits needSyncValue NeedValidate" >
		                                                    	<c:forEach items="${params }" var="p">
																	<c:if test="${p.type == 'disabilityLevel' }">
																		<option value="${p.value }"  <c:if test="${job.disabilityLevel == p.value }">selected="selected"</c:if>>${p.name }</option>
																	</c:if>
																</c:forEach>
		                                                    </select> -->
		                                                </td>
		                                            </tr>
		                                            <tr>
		                                                <td class="textTop">
	                                                  		残疾部位：
		                                                </td>
		                                                <td>
															<c:forEach items="${params }" var="p">
																<c:if test="${p.type == 'disabilityPart' && p.value != null && p.value != '' }">
																	<input type="checkbox" name="disabilityPart" title="${p.name }" value="${p.value }" <c:if test="${fn:contains(job.disabilityPart, p.value) }">checked="checked"</c:if> />${p.name }&nbsp;
																</c:if>
															</c:forEach>
		                                                <!--	<select name="disabilityPart" id="disabilityPart" class="length informationEdits needSyncValue NeedValidate" >
		                                                    	<c:forEach items="${params }" var="p">
																	<c:if test="${p.type == 'disabilityPart' }">
																		<option value="${p.value }"  <c:if test="${job.disabilityPart == p.value }">selected="selected"</c:if>>${p.name }</option>
																	</c:if>
																</c:forEach>
		                                                    </select> -->
		                                                </td>
		                                            </tr>
		                                             <tr>
		                                                <td class="textTop">
	                                                  		提供薪资：
		                                                </td>
		                                                <td>
		                                                	<select name="salary" id="salary" class="length informationEdits needSyncValue NeedValidate" >
		                                                    	<c:forEach items="${params }" var="p">
																	<c:if test="${p.type == 'salary' }">
																		<option value="${p.value }" <c:if test="${job.salary == p.value }">selected="selected"</c:if>>${p.name }</option>
																	</c:if>
																</c:forEach>
		                                                    </select>
		                                                </td>
		                                            </tr>
		                                             <tr>
		                                                <td class="textTop">
	                                                  		 <em style="display: inline;">* </em>最低学历：
		                                                </td>
		                                                <td>
		                                                	<select name="education" id="education" class="length informationEdits needSyncValue NeedValidate" >
		                                                    	<c:forEach items="${params }" var="p">
																	<c:if test="${p.type == 'education' }">
																		<option value="${p.value }" <c:if test="${job.education == p.value }">selected="selected"</c:if>>${p.name }</option>
																	</c:if>
																</c:forEach>
		                                                    </select>
		                                                </td>
		                                            </tr>
		                                             <tr>
		                                                <td class="textTop">
	                                                  		<em style="display: inline;">* </em>工作经验：
		                                                </td>
		                                                <td>
		                                                	<select name="experience" id="experience" class="length informationEdits needSyncValue NeedValidate" >
		                                                    	<c:forEach items="${params }" var="p">
																	<c:if test="${p.type == 'experience' }">
																		<option value="${p.value }" <c:if test="${job.experience == p.value }">selected="selected"</c:if>>${p.name }</option>
																	</c:if>
																</c:forEach>
		                                                    </select>
		                                                </td>
		                                            </tr>
		                                             <tr>
		                                                <td class="textTop">
	                                                  		 性别：
		                                                </td>
		                                                <td>
		                                                	<select name="gender" id="gender" class="length informationEdits needSyncValue NeedValidate" >
		                                                    	<c:forEach items="${params }" var="p">
																	<c:if test="${p.type == 'gender' }">
																		<option value="${p.value }" <c:if test="${job.gender == p.value }">selected="selected"</c:if>>${p.name }</option>
																	</c:if>
																</c:forEach>
		                                                    </select>
		                                                </td>
		                                            </tr>
		                                             <tr>
		                                                <td class="textTop">
	                                                  		<em style="display: inline;">* </em>岗位性质：
		                                                </td>
		                                                <td>
		                                                	<select name="nature" id="nature" class="length informationEdits needSyncValue NeedValidate" >
		                                                    	<c:forEach items="${params }" var="p">
																	<c:if test="${p.type == 'jobNature' }">
																		<option value="${p.value }" <c:if test="${job.nature == p.value }">selected="selected"</c:if>>${p.name }</option>
																	</c:if>
																</c:forEach>
		                                                    </select>
		                                                </td>
		                                            </tr>
		                                            <tr>
		                                                <td class="textTop">
	                                                  		<em style="display: inline;">* </em>有效期：
		                                                </td>
		                                                <td>
		                                                	<c:if test="${job.effectiveTime!= null }">
																<fmt:formatDate value="${job.effectiveTime}" type="both" dateStyle="long" pattern="yyyy-MM-dd hh:mm:ss" var="effectiveTime" />
															</c:if>
															<input id="effectiveTime" type="text" value="${effectiveTime}" readonly="readonly"/>
															<input name="effectiveTime" id="effectiveTime2" type="hidden" value="${effectiveTime}" readonly="readonly"/>
		                                              		  &nbsp;添加有效期:
		                                                	<select class="length informationEdits needSyncValue NeedValidate" onchange="addEffectiveTime($(this))">
		                                                    	<c:forEach items="${params}" var="p">
																	<c:if test="${p.type == 'effectiveTime' }">
																		<option value="${p.value }">${p.name }</option>
																	</c:if>
																</c:forEach>
		                                                    </select>
		                                                    <input id="effectiveDays" type="hidden" name="effectiveDays" value="0" readonly="readonly"/>
 <!-- 	<select name="effectiveDays" id="effectiveDays" class="length informationEdits needSyncValue NeedValidate" >
		                                                    	<c:forEach items="${params }" var="p">
																	<c:if test="${p.type == 'effectiveTime' }">
																		<option value="${p.value }">${p.name }</option>
																	</c:if>
																</c:forEach>
		                                                    </select>
		                                                 -->
		                                                </td>
		                                            </tr>
		                                            <tr>
		                                                <td class="textTop">
	                                                  		<em style="display: inline;">* </em>工作地点：
		                                                </td>
		                                                <td>
		                                                	<select name="workPlace.code" id="area_lv1" class="select_border" style="width:150px;">
																<c:forEach items="${provinceList }" var="area">
																	<option value="${area.code }">${area.name }</option>
																</c:forEach>
															</select> <select name="area_lv2" id="area_lv2" class="select_border" style="width:150px;">
																<option value="">请选择城市</option>
															</select> <select name="area_lv3" id="area_lv3" class="select_border" style="width:150px;">
																<option value="">请选择区域</option>
															</select>
		                                                </td>
		                                            </tr>
		                                            <tr>
		                                                <td class="textTop">
	                                                  		<em style="display: inline;">* </em>工作种类：
		                                                </td>
		                                                <td>
		                                                	<select name="jobCategory.code" id="jobCategory_lv1" class="select_border" style="width:150px;">
																<c:forEach items="${jcList }" var="p">
																	<option value="${p.code }">${p.name }</option>
																</c:forEach>
															</select> 
															<select name="jobCategory_lv2" id="jobCategory_lv2" class="select_border" style="width:150px;">
																<option value="">请选择</option>
															</select> 
															<select name="jobCategory_lv3" id="jobCategory_lv3" class="select_border" style="width:150px;">
																<option value="">请选择</option>
															</select>
		                                                </td>
		                                            </tr>
		                                            <tr>
		                                                <td class="textTop">
	                                                  		其他福利：
		                                                </td>
		                                                <td>
		                                                	<c:forEach items="${params }" var="p">
																<c:if test="${p.type == 'benefit' }">
																	<input type="checkbox" value="${p.value }" name="provideBenefit" />${p.name }&nbsp;&nbsp;
																	</c:if>
															</c:forEach>
		                                                </td>
		                                            </tr>
		                                            <tr>
		                                                <td class="textTop">
		                                                    <em></em><em style="display: inline;">* </em>职位描述：
		                                                </td>
		                                                <td>
		                                                    <textarea name="description" id="description" rows="2" cols="20" style="height:80px;width:619px;font-size: 12px; padding: 6px;">${job.description }</textarea>
		                                                </td>
		                                            </tr>
		                                        </tbody></table>
		                                    </div>
		                                </div>
		                                <div class="clearboth">
		                                </div>
		                                <div class="contraction" style="width: 780px;padding-bottom:20px;">
		                                    <div class="contractiontop">
		                                        <div class="contractionleft">
		                                        </div>
		                                        <div class="contractiontoptext">
		                                            <span class="form_title">联系方式</span><span class="spanFirst">(*必填项)</span></div>
		                                        <div class="contractionright">
		                                        </div>
		                                        <div class="contractionclickone">
		                                        </div>
		                                    </div>
		                                    <div class="contractioncontent">
		                                        <table class="tbl1" id="Contactform">
		                                            <tbody><tr>
		                                                <td class="textTop">
		                                                    <em style="display: inline;">* </em>联 系 人：
		                                                </td>
		                                                <td style="border-right:0 none;">
		                                                	<input name="contactPerson" type="text" value="${job.contactPerson }" id="contactPerson" />
		                                                </td>
		                                                <td style="border-left:0 none;">
		                                                	&nbsp;
		                                                </td>
		                                            </tr>
		                                            <tr>
		                                                <td class="textTop">
		                                                    <em style="display: inline;">* </em>联系电话：
		                                                </td>
		                                                <td style="border-right:0 none;">
		                                                	<input name="contactTel" type="text" value="${job.contactTel }" id="contactTel" />
		                                                </td>
		                                                <td style="border-left:0 none;">
		                                                	&nbsp;
		                                                </td>
		                                            </tr>
		                                            <tr>
		                                                <td class="textTop">
															邮箱：
		                                                </td>
		                                                <td style="border-right:0 none;">
		                                                	<input name="contactEmail" type="text" value="${job.contactEmail }" id="contactEmail" />
		                                                </td>
		                                                <td style="border-left:0 none;">
		                                                	&nbsp;
		                                                </td>
		                                            </tr>
		                                        </tbody>
											</table>
	                                    </div>
	                                    
	                                    <div class="clearboth" style="height: 10px;">
	                                    </div>
	                                    <input class="saveBtn" id="SaveAllBtn" type="button" />
	                                </div>
	                            </div>
	                        </li>
	                    </ul>
					</form>
                    
                    <div class="clearboth">
                    </div>
                </div>
                <!----------- 管理中心右边 详细内容显示  ------end ----------->
                
            </div>
            <!-- 下部具体内容显示   end -->
        




		</div>
		<!-- ******* 中部内容显示区 ******* end ********** -->
		
		<!-- 尾部footer区 -->
		<div class="clearboth"></div>
		<jsp:include page="../formatter/footer.jsp" />
	</div>
</body>
</html>