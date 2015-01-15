<%@page import="esd.bean.Job"%>
<%@ page language="java" import="java.util.*" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh-cn">
<head>
	
	<title>简历管理列表1</title>
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
	<script src="${contextPath}/js/manage/resumeMate.js"></script>
</head>

<body>
	<%int sequencePer=1; %>
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
				
					<!-- 上方条件查询框  开始 -->
					<div class="">
						<div class="btn-group" >
								<!-- 将值转换为对应显示的参数文本 -->
								<%--<c:forEach items="${params }" var="p">
									<c:if test="${p.type == 'resumeMatchPer' && p.value == obj.resumeMatchPer}">
										<c:set var="scaleName" value="${p.name }"></c:set>
										<c:set var="scaleValue" value="${p.value }"></c:set>
									</c:if>
								</c:forEach>
								<button class="btn" id="btnScale">${scaleName }</button> 
								<input type="hidden" id="scale" name="scale" value="${scaleValue }"/>
								--%><%--
								<button class="btn" id="areaName2" name="areaName2">请选择</button> 
								
								--%>
					
												<!-- 将值转换为对应显示的参数文本 -->
<c:forEach items="${params }" var="per">
	<c:if test="${per.type == 'resumeMatchPer' && per.name == resumeMatchPerName}">
		<c:set var="resumeMatchPerName" value="${per.name }"></c:set>
		<c:set var="resumeMatchPerValue" value="${per.value }"></c:set>
	</c:if>
</c:forEach>
<button class="btn" id="btnResumeMatchPer">${resumeMatchPerName }</button> 
<input type="hidden" id="resumeMatch" name="resumeMatch" value="${resumeMatchPerValue }"/>
<input type="hidden" id="resumeMatchName" name="resumeMatchName" value="${resumeMatchPerName }"/>
<button data-toggle="dropdown" class="btn dropdown-toggle"><span class="caret"></span></button>
<ul class="dropdown-menu">
	<c:forEach items="${params }" var="per">
		<c:if test="${per.type == 'resumeMatchPer' }">
			<li>
				<a href="javascript:selectButtonPer('resumeMatch','resumeMatchName','btnResumeMatchPer','${per.value }','${per.name }');">${per.name }</a>
			</li>
		</c:if>
	</c:forEach>
</ul>
						
								
								
						<button type="submit" class="btn" onclick="query(null,resumeMatch);">		查找</button>		
								
								</div>
								
								<%--
								<button data-toggle="dropdown" class="btn dropdown-toggle">请选择<span class="caret"></span></button>
								<ul class="dropdown-menu">
									<c:forEach items="${params }" var="p">
										<c:if test="${p.type == 'resumeMatchPer' }">
											<li>
												<a href="javascript:query('null','${p.value }');">${p.name }</a>
											</li>
										</c:if>
									</c:forEach>
								</ul>
							</div>
	
					
					--%><%--
						<input class="input-medium search-query" value="${targetName }" type="text" name="targetName" id="targetName"/> 
						<div class="btn-group" >
							<button class="btn">${checkStatusName }</button> 
							<input type="hidden" id="checkStatus" name="checkStatus" value="${checkStatus }"/>
							<button data-toggle="dropdown" class="btn dropdown-toggle"><span class="caret"></span></button>
							<ul class="dropdown-menu">
								<li>
									<a href="javascript:query(null,'daiShen');">待审核</a>
								</li>
								<li>
									<a href="javascript:query(null,'weiTongGuo');">未通过</a>
								</li>
								<li>
									<a href="javascript:query(null,'ok');">已通过</a>
								</li>
							</ul>
						</div>
						--%>
						<%--<input type="hidden" id="resumeNameHid" name="resumeNameHid" value="${targetName }"  />
						<div style="float:right;">	
							<button type="button" class="btn btn-info" id="resumeExportSelected"  >导出所选</button>	
							<button type="button" class="btn btn-info" id="resumeExportAll"  >导出全部</button>
						</div>
					--%>
					<!-- 上方条件查询框  结束-->
					
					<!-- 下方结果显示框  开始 -->
					<div class="row-fluid">
						
						<div class="span12">
							<table class="table" id="resumetable"  style="padding:0;border-spacing:0px; border-style:none; ">
								<thead  >
									<tr style="background:#5599FF ;color:#ffffff">
										
										<th>
											序号
										</th>
										<th width="90px">
											简历名称
										</th>
										<th width="100px">
											姓名
										</th>
										<th>
											性别
										</th>
										<th>
											残疾证号
										</th>
										<th>
											残疾类别
										</th>
										<th>
											残疾等级
										</th>
										<th>
										  	期待薪资
										</th>
										<th>
											联系方式
										</th>
										<th>
											操作
										</th>
									</tr>
								</thead>
								<tbody style="color:#000000;background: 	#CCDDFF">
									<!-- 没有数据返回时, 显示提示文字 -->
									<c:if test="${entityList[0] == null}">
										<tr>
											<td colspan="10" class="warning" style="color:red; text-align:center;">
												没有找到你需要的数据喔!
											</td>
										</tr>
									</c:if>
									
									<c:forEach items="${entityList }" var="entity" varStatus="row">
										<!-- 隔2行换色 -->
										<tr style="background:#99BBFF;font-size:13px;">
											<td>
												${(row.index + 1 + (currentPage - 1) * 20) }
											</td>
											<td>
												${entity.title }
											</td>
											<td>
												${entity.name }
											</td>
											<td>
												${entity.gender }
											</td>
											<td>
												${entity.disabilityCard }
											</td>
											<td>
												${entity.disabilityCategory }
											</td>
											<td>
												${entity.disabilityLevel }
											</td>
											<td>
												${entity.desireSalary}
											</td>
											<td>
												${entity.phone }
											</td>
											<td>
												<c:if test="${entity.jobResultList[0] != null}">
												<a href="javascript:void(0);" onclick="lookJobData(<%=sequencePer %>)">显示</a>
													<a href="javascript:void(0);" onclick="hideJobData(<%=sequencePer %>)">隐藏</a>
												</c:if>
											<c:if test="${entity.jobResultList[0] == null}">
											没有匹配的数据！
											</c:if>
											</td>
										</tr><%--
										
										<c:if test="${entity.jobResultList[0] == null}">
										  
										   	<tr id="tr<%=i %>" style="display: none">
												<td colspan="10" class="warning" style="color:red; text-align:center;">
													<%=i %>没有找到你需要的数据喔1!<% i=i+1;%>
													                     							 	
												</td>
											</tr>
											
										</c:if>
									
									--%><c:if test="${entity.jobResultList[0] != null}"><%--
										<c:forEach items="${entity.jobResultList }" var="job" varStatus="vs">
										<tr>
										<s:property value="#vs.index+1"/> </td>
										   <td align = "center">${job.name}</td>  
										             <td align = "center">${job.getId()}1111</td>
										             
										             
							         	</tr>
										</c:forEach>
										
										
										
										--%>
							
								<thead id="tr<%=sequencePer %>">
									<tr style="font-size:12px;background:#ffffFF;" >
										<%--<th >
									  		<input id="jobcheckbox" type="checkbox" />  
										</th>
										--%>
										<th>
											序号
										</th>
										<th>
											职位名称
										</th>
										<th >
											人数
										</th>
										<th >
											提供薪资
										</th>
										<th>
											岗位性质
										</th>
										<th>
											发布日期
										</th>
										<th>
											有效期至
										</th>
										<th>
											公司名称
										</th>
										<th>
											联系人
										</th>
										<th>
											联系电话
										</th>
										
										<%--<th>
											操作
										</th>
									--%></tr>
								</thead>
								<tbody id="tb<%=sequencePer %>"><% sequencePer=sequencePer+1;%>
									<c:forEach items="${entity.jobResultList }" var="job" varStatus="vs">
										<!-- 隔2行换色 -->
										<tr style="color:red; text-align:center;font-size:12px;">
										
											<td>
												${(vs.index + 1) }
											</td>
											<td>
												${job.name }
											</td>
											<td>
												${job.hireNumber }
											</td>
											<td>
												${job.salary }
											</td>
											<td>
												${job.nature }
											</td>
											<td>
												<fmt:formatDate value="${job.createDate }" dateStyle="long" pattern="yyyy-MM-dd HH:mm:ss" var="createDate"/>
												${createDate }
											</td>
											<td>
												<fmt:formatDate value="${job.effectiveTime }" dateStyle="long" pattern="yyyy-MM-dd HH:mm:ss" var="effectiveTime"/>
												${effectiveTime }
											</td>
											<td>
												${job.company.name }
											</td>
											<td>
												${job.contactPerson }
											</td>
											<td>
												${job.contactTel }
											</td>
											
										</tr>
									</c:forEach>
								</tbody>
					
									</c:if><%--
									
									</div>
									
									--%></c:forEach>
									
								</tbody>
							</table>
						</div>
							
					</div>
					<!-- 下方结果显示框  开始 -->
					
					<!-- 尾部分页 开始 -->
					<div class="">
						<div class="pagination">
							<ul>
								<li>
									<input type="hidden" id="totalPage" value="${totalPage }" />
									<a href="javascript:query(${currentPage - 1 },resumeMatch);">上一页</a>
								</li>
								<c:forEach begin="1" end="${totalPage }" var="i">
									<li <c:if test="${i == currentPage }">class="active"</c:if>>
										<!-- 当前页面时链接显示灰色 -->
										<a 
											<c:choose>
												<c:when test="${i == currentPage }">
													href="javascript:void(0);" 
												</c:when>
												<c:otherwise>
													href="javascript:query(${i },resumeMatch);" 
												</c:otherwise>
											</c:choose>
										>${i }</a>
									</li>
								</c:forEach>
								<li>
									<a href="javascript:query(${currentPage + 1 },resumeMatch);">下一页</a>
								</li>
							</ul>
						</div>
					</div>
					<!-- 尾部分页 结束 -->
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
