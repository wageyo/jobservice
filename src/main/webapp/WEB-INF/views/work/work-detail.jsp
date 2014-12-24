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
	<link href="${contextPath}/css/style_job.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/Public.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/PublicStatusBar.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/HomePageHeader.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/SetHeaderStyle.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/HomePageFooter.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="${contextPath}/js/jquery.js"></script>
<title>残疾人就业信息网</title>
<script type="text/javascript">
	function send_resume(jid){
		$.ajax({
			url:'${contextPath}/secure/job/sendResume',
			type:'POST',
			dataType:'json',
			data:{'jid':jid},
			success:function(json){
				if(json.notice == 'success'){
					alert('投递成功!');
				}else{
					alert(json.notice);
				}
			},
			error:function(){
				alert('操作失败!');
			}
		});
	}
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
	                       <span class="PublicframeTwoHeadBarTittleSpan">招聘公司</span>
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
	                          		<p>企业性质：${job.company.nature }</p>
									<p>公司地址：${job.company.address }</p>
									<p>联系人：${job.company.contactPerson }</p>
	                          	
	                          	</div>
							  </div>
	                       </div>
	                   </div>
	                   <div style="clear: both"></div>     
	               </div>
				</div>
				<!-- 上部公司基本信息区域 begin -->
	           
	           <!-- 招聘基本信息 begin -->
	           <div class="PublicframeTwo ">
	               <div class="PublicframeTwoHeadBar">
	                 <div class="PublicframeTwoHeadBarLeft"></div>
	                   <div class="PublicframeTwoHeadBarTittle">
	                       <span class="PublicframeTwoHeadBarTittleSpan">招聘职位: ${job.name }</span>
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
	                          		<table id="tableworker">
	                          			<thead>
	                          				<tr>
	                          					<th style="width:80px;">招聘职位: </th>
	                          					<th colspan="3">${job.name }</th>
	                          				</tr>
	                          			</thead>
	                          			<tbody>
		                          			<tr>
		                          				<td>岗位类别: </td>
		                          				<td colspan="3">${job.jobCategory.name }</td>
		                          			</tr>
		                          			<tr>
												<td>招聘人数：</td>
												<td>${job.hireNumber } 名</td>
												<td style="width:80px;">更新日期：</td>
												<td>
													<c:if test="${job.updateDate != null}">
														<fmt:formatDate value="${job.updateDate}" type="both" dateStyle="long" pattern="yyyy-MM-dd hh:mm:ss" var="updateDate" />
													</c:if>
													<span >${updateDate }</span>
												</td>
											</tr>
											<tr>
												<td>学历要求：</td>
												<td>${job.education }</td>
												<td>性别要求：</td>
												<td>${job.gender }</td>
											</tr>
											<tr>
												<td>工作性质：</td>
												<td>${job.nature }</td>
												<td>工龄要求：</td>
												<td>${job.experience }</td>
											</tr>
											<tr>
												<td>工作地区：</td>
												<td>
													<c:choose>
														<c:when test="${job.workPlace != null }">
															${job.workPlace.name }
														</c:when>
														<c:otherwise>
															不限
														</c:otherwise>
													</c:choose>
												</td>
												<td>薪资待遇：</td>
												<td>${job.salary }</td>
											</tr>
											<!-- 仅有个人用户登陆时才可见的投递简历按钮 -->
											<c:if test="${cookie.identity.value == 'person' }">
												<tr>
													<td colspan="4" style="text-align:center;height:40px;">
															<input type="image" name="ImageButton1" onclick="send_resume(${job.id })" src="${contextPath}/images/PublicImage/PublicframeTwoImage/sendresume.gif" style="border-width:0px;" />
													</td>
												</tr>
											</c:if>
	                          			</tbody>
	                          		</table>
	                          	</div>
							  </div>
	                       </div>
	                   </div>
	                   <div style="clear: both"></div>     
	               </div>
	           </div>
	           <!-- 招聘基本信息 begin -->
	           
	           <!-- 职位描述 begin -->
	           <div class="PublicframeTwo ">
	               <div class="PublicframeTwoHeadBar">
	                 <div class="PublicframeTwoHeadBarLeft"></div>
	                   <div class="PublicframeTwoHeadBarTittle">
	                       <span class="PublicframeTwoHeadBarTittleSpan">职位描述</span>
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
	                          			<c:when test="${job.description == null || job.description == ''}">
	                          				暂无
	                          			</c:when>
	                          			<c:otherwise>
											${job.description }
	                          			</c:otherwise>
	                          		</c:choose>
	                          	</div>
							  </div>
	                       </div>
	                   </div>
	                   <div style="clear: both"></div>     
	               </div>
	           </div>
	           <!-- 职位描述 begin -->
	           
	           <!-- 联系方式 begin -->
	           <div class="PublicframeTwo ">
	               <div class="PublicframeTwoHeadBar">
	                 <div class="PublicframeTwoHeadBarLeft"></div>
	                   <div class="PublicframeTwoHeadBarTittle">
	                       <span class="PublicframeTwoHeadBarTittleSpan">联系方式</span>
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
	                          			<c:when test="${cookie.username == null || cookie.username == ''}">
	                          				个人会员请 <a href="${contextPath}/loginP" style="color:blue;">登录</a> 查看联系方式！如果您不是个人会员，请先 <a href="${contextPath }/regP" style="color:blue;">免费注册</a> 成为个人会员！
	                          			</c:when>
	                          			<c:otherwise>
											<p>电话: ${job.company.telephone }</p>
											<p>联系部门: ${job.company.contactDept }</p>
											<p>联系人: ${job.company.contactPerson }</p>
	                          			</c:otherwise>
	                          		</c:choose>
	                          	</div>
							  </div>
	                       </div>
	                   </div>
	                   <div style="clear: both"></div>     
	               </div>
	           </div>
	           <!-- 联系方式 begin -->
	           
	           <!-- 该公司的其他招聘信息  begin -->
	           <div class="PublicframeTwo ">
	               <div class="PublicframeTwoHeadBar">
	                 <div class="PublicframeTwoHeadBarLeft"></div>
	                   <div class="PublicframeTwoHeadBarTittle">
	                       <span class="PublicframeTwoHeadBarTittleSpan">该公司的其他招聘信息</span>
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
															暂时没有其他的招聘信息
														</td>
													</tr>
												</c:when>
												<c:otherwise>
													<c:forEach items="${jobList }" var = "jj">
														<c:if test="${jj.id != job.id }">
															<tr class="zhaopin1">
																<td class="jobs01"><a href="${contextPath }/job/getOneForShow?id=${jj.id}">${jj.name }</a></td>
																<td>${jj.hireNumber }</td>
																<td>${job.jobCategory.name }</td>
																<td>${job.education }</td>
																<td>
																	<fmt:formatDate value="${job.updateDate }"  dateStyle="default" pattern="yyyy-MM-dd HH:mm:ss"/>
																</td>
																<td>${job.nature }</td>
															</tr>
														</c:if>
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
	           <!-- 该公司的其他招聘信息  begin -->
		    </div>	
		</div>
	</div>
	
<jsp:include page="../formatter/footer.jsp" />
		
</body>
</html>