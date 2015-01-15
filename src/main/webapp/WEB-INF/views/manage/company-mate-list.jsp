<%@ page language="java" import="java.util.*" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh-cn">
<head>
	
	<title>企业管理列表</title>
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
	<script src="${contextPath}/js/manage/companyMate.js"></script>
	
</head>

<body>
	<%int sequenceCom=1; %>
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
						<c:forEach items="${params }" var="perCom">
	<c:if test="${perCom.type == 'resumeMatchPer' && perCom.name == companyMatchPerName}">
		<c:set var="companyMatchPerName" value="${perCom.name }"></c:set>
		<c:set var="companyMatchPerValue" value="${perCom.value }"></c:set>
	</c:if>
</c:forEach>
<button class="btn" id="btncompanyMatchPer">${companyMatchPerName }</button> 
<input type="hidden" id="companyMatch" name="companyMatch" value="${companyMatchPerValue }"/>
<input type="hidden" id="companyMatchName" name="companyMatchName" value="${companyMatchPerName }"/>
<button data-toggle="dropdown" class="btn dropdown-toggle"><span class="caret"></span></button>
<ul class="dropdown-menu">
	<c:forEach items="${params }" var="perCom">
		<c:if test="${perCom.type == 'resumeMatchPer' }">
			<li>
				<a href="javascript:selectButtonPer('companyMatch','companyMatchName','btncompanyMatchPer','${perCom.value }','${perCom.name }');">${perCom.name }</a>
			</li>
		</c:if>
	</c:forEach>
</ul>
						<button type="submit" class="btn" onclick="query(null,companyMatch);">查找</button>
						
					</div>
					<!-- 上方条件查询框  结束-->
					
					<!-- 下方结果显示框  开始 -->
					<div class="row-fluid">
						
						<div class="span12">
							<table class="table" id="t1" style="padding:0;border-spacing:0px; border-style:none; ">
							<%--第一个公司表	标题						--%>
							<thead>
									<tr style="background:#5599FF ;color:#ffffff">
									  <th>
											序号
										</th>
										<th>
											公司名称
										</th>
										<th>
										联系部门
										</th>
										<th>
											联系人
										</th>
										<th>
											联系电话
										</th>
										<th>
											电子邮箱
										</th>
										<th>
											企业性质
										</th>
										<th>
											经济类型
										</th>
										<th>
											所在地区
										</th>
										<th>
											操作
										</th>
									</tr>
								</thead>
								<tbody>
									<!-- 没有数据返回时, 显示提示文字 -->
									<c:if test="${entityList[0] == null}">
										<tr>
											<td colspan="10" class="warning" style="color:red; text-align:center;">
												没有找到你需要的数据喔!
											</td>
										</tr>
									</c:if>
									<%--第一个公司表	数据					--%>
									<c:forEach items="${entityList }" var="entity" varStatus="row">
										<tr style="font-size:13px;background:#ffffff;border-color: #ffffff">
											<td>
												${(row.index + 1 + (currentPage - 1) * 20) }
											</td>
											<td>
												${entity.name }
											</td>
											<td>
												${entity.contactDept }
											</td>
											<td>
												${entity.contactPerson}
											</td>
											<td>
												${entity.telephone }
											</td>
											<td>
												${entity.email }
											</td>
											<td>
												${entity.nature }
											</td>
											<td>
												${entity.economyType }
											</td>
											<td>
												${entity.area.name }
											</td>
											<td>
													<c:if test="${entity.jobList[0] != null}">
												<a href="javascript:void(0);" onclick="lookJobData(<%=sequenceCom %>,${entity.id })">显示</a>
													<a href="javascript:void(0);" onclick="hideJobData(${entity.id })">隐藏</a>
												</c:if>
											<c:if test="${entity.jobList[0] == null}">
											没有匹配的数据！
											</c:if>
											</td>
										</tr>
										<tr >
										<td colspan=10 style="background:#ffffff;border-color: #ffffff;" >
											<div id="main${entity.id }" ></div>
											</td>
										</tr>
									</c:forEach><%--第一个公司表 数据结尾						--%>
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
									<a href="javascript:query(${currentPage - 1 },companyMatch);">上一页</a>
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
													href="javascript:query(${i },companyMatch);" 
												</c:otherwise>
											</c:choose>
										>${i }</a>
									</li>
								</c:forEach>
								<li>
									<a href="javascript:query(${currentPage + 1 },companyMatch);">下一页</a>
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
