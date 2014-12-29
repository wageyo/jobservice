<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="keywords" content="残疾人招聘信息,残疾人就业信息,残疾人人才网,残疾人找工作" />
	<meta content="残疾人招聘就业" name="description" />
	
	<link rel="shortcut icon" href="${contextPath}/images/HomePageImage/favicon.ico" type="image/x-icon" />
	<link href="${contextPath}/css/style_job.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/Public.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/PublicStatusBar.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/HomePageHeader.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/SetHeaderStyle.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/HomePageFooter.css" rel="stylesheet" type="text/css" />

	<script type="text/javascript" src="${contextPath}/js/jquery.js"></script>
	<title>残疾人就业信息网</title>
	<script type="text/javascript">
	</script>
</head>
<body>
	<jsp:include page="../formatter/status-bar.jsp" />
	<jsp:include page="../formatter/header.jsp" />
	<div id="directBody">
		<div class="MainLeft" style="width:100%;">
			<div class="MainLeftRightHead1">
			
				<!-- 上部公司基本信息区域 begin -->
				<div class="PublicframeTwo ">
	               <div class="PublicframeTwoHeadBar">
	                 <div class="PublicframeTwoHeadBarLeft"></div>
	                   <div class="PublicframeTwoHeadBarTittle">
	                       <span class="PublicframeTwoHeadBarTittleSpan">企业信息</span>
	                   </div>
	                   <div class="PublicframeTwoHeadBarRight">
	                   </div>
	                   <div class="PublicframeTwoHeadBarMore">
	                       <span class="PublicframeTwoHeadBarMoreSpan"></span>
	                   </div>
	               </div>
	               <div class="PublicframeTwoContent" style="width: auto; padding: 3px 5px 5px;" id="">
	                   <div class="MainLeftTwo">
	                       <div class="MainLeftTwoLeft">
	                          <div id="PublicframeOne11" class="PublicframeOne ">
	                          	<div id="main" class="maincontent">
	                          		<a href="${contextPath }/company/getOneForShow?id=${company.id}" style="font-size: 15px;color: rgb(95, 95, 255);font-weight: bold;">${company.name }</a>
									<p>企业性质：${company.nature }</p>
									<p>公司地址：${company.address }</p>
									<p>联系人：${company.contactPerson }</p>
	                          	</div>
							  </div>
	                       </div>
	                   </div>
	                   <div style="clear: both"></div>     
	               </div>
				</div>
				<!-- 上部公司基本信息区域 begin -->
	           
	           <!-- 企业简介 begin -->
	           <div class="PublicframeTwo ">
	               <div class="PublicframeTwoHeadBar">
	                 <div class="PublicframeTwoHeadBarLeft"></div>
	                   <div class="PublicframeTwoHeadBarTittle">
	                       <span class="PublicframeTwoHeadBarTittleSpan">企业简介</span>
	                   </div>
	                   <div class="PublicframeTwoHeadBarRight">
	                   </div>
	                   <div class="PublicframeTwoHeadBarMore">
	                       <span class="PublicframeTwoHeadBarMoreSpan"></span>
	                   </div>
	               </div>
	               <div class="PublicframeTwoContent" style="width: auto; padding: 3px 5px 5px;" id="">
	                   <div class="MainLeftTwo">
	                       <div class="MainLeftTwoLeft">
	                          <div id="PublicframeOne11" class="PublicframeOne ">
	                          	<div id="main" style="padding:10px 30px;">
	                          		<c:choose>
	                          			<c:when test="${company.introduction == null || company.introduction == ''}">
	                          				暂无
	                          			</c:when>
	                          			<c:otherwise>
											${company.introduction }
	                          			</c:otherwise>
	                          		</c:choose>
	                          	</div>
							  </div>
	                       </div>
	                   </div>
	                   <div style="clear: both"></div>     
	               </div>
	           </div>
	           <!-- 企业简介 begin -->
	           
	           <!-- 该公司的招聘信息  begin -->
	           <div class="PublicframeTwo ">
	               <div class="PublicframeTwoHeadBar">
	                 <div class="PublicframeTwoHeadBarLeft"></div>
	                   <div class="PublicframeTwoHeadBarTittle">
	                       <span class="PublicframeTwoHeadBarTittleSpan">该公司的招聘信息</span>
	                   </div>
	                   <div class="PublicframeTwoHeadBarRight">
	                   </div>
	                   <div class="PublicframeTwoHeadBarMore">
	                       <span class="PublicframeTwoHeadBarMoreSpan"></span>
	                   </div>
	               </div>
	               <div class="PublicframeTwoContent" style="width: auto; padding: 3px 5px 5px;" id="">
	                   <div class="MainLeftTwo">
	                       <div class="MainLeftTwoLeft">
	                          <div id="PublicframeOne11" class="PublicframeOne ">
	                          	<div id="main" style="padding:10px 30px;">
	                          		<table id="tablecompany">
	                          			<thead>
	                          				<tr>
												<th class="t0">职位名称</th>
												<th>招聘人数</th>
												<th>岗位类型</th>
												<th>学历要求</th>
												<th>更新时间</th>
												<th>工作性质</th>
	                          				</tr>
	                          			</thead>
										<tbody>
											<c:choose>
												<c:when test="${jobList == null || fn:length(jogList) == 0}">
													<tr>
														<td colspan="6" style="text-align:center;">
															暂时没有招聘信息
														</td>
													</tr>
												</c:when>
												<c:otherwise>
													<c:forEach items="${jobList }" var = "jj">
														<tr>
															<td><a href="${contextPath }/job/getOneForShow?id=${jj.id}">${jj.name }</a></td>
															<td>${jj.hireNumber }</td>
															<td>${job.jobCategory.name }</td>
															<td>${job.education }</td>
															<td>
																<fmt:formatDate value="${job.updateDate }"  dateStyle="default" pattern="yyyy-MM-dd HH:mm:ss"/>
															</td>
															<td>${job.nature }</td>
														</tr>
													</c:forEach>
												</c:otherwise>
											</c:choose>
										</tbody>
									</table>
	                          	</div>
							  </div>
	                       </div>
	                   </div>
	                   <div style="clear: both"></div>     
	               </div>
	           </div>
	           <!-- 该公司的招聘信息  begin -->
		    </div>	
		</div>
	</div>
	
<jsp:include page="../formatter/footer.jsp" />
		
</body>
</html>