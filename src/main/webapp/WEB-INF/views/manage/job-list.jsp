<%@ page language="java" import="java.util.*" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh-cn">
<head>
	
	<title>职位管理列表</title>
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
				
					<!-- 上方条件查询框  开始 -->
					<div class="">
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
						<button type="submit" class="btn" onclick="query(null,null);">查找</button>
					</div>
					<!-- 上方条件查询框  结束-->
					
					<!-- 下方结果显示框  开始 -->
					<div class="row-fluid">
						
						<div class="span12">
							<table class="table">
								<thead>
									<tr>
										<th>
											序号
										</th>
										<th>
											职位名称
										</th>
										<th>
											人数
										</th>
										<th>
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
											联系人
										</th>
										<th>
											联系电话
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
								
									<c:forEach items="${entityList }" var="entity" varStatus="row">
										<!-- 隔4行换色 -->
										<tr 
											<c:if test="${row.index % 2 == 0 }">class="info"</c:if>
										>
											<td>
												${(row.index + 1 + (currentPage - 1) * 20) }
											</td>
											<td>
												${entity.name }
											</td>
											<td>
												${entity.hireNumber }
											</td>
											<td>
												${entity.salary }
											</td>
											<td>
												${entity.nature }
											</td>
											<td>
												<fmt:formatDate value="${entity.createDate }" dateStyle="long" pattern="yyyy-MM-dd hh:mm:ss" var="createDate"/>
												${createDate }
											</td>
											<td>
												<fmt:formatDate value="${entity.effectiveTime }" dateStyle="long" pattern="yyyy-MM-dd hh:mm:ss" var="effectiveTime"/>
												${effectiveTime }
											</td>
											<td>
												${entity.contactPerson }
											</td>
											<td>
												${entity.contactTel }
											</td>
											<td>
												<a href="${contextPath }/manage/job/view/${entity.id}">查看</a>
												<a href="${contextPath }/manage/job/edit/${entity.id}">编辑</a> 
												<c:if test="${checkStatus != 'weiTongGuo' }">
													<a href="javascript:updateEntity('refuse','${entity.id }');" style="color:red;">拒绝</a>
												</c:if> 
												<c:if test="${checkStatus != 'ok' }">
													<a href="javascript:updateEntity('approve','${entity.id }');">通过</a>
												</c:if>
											</td>
										</tr>
									</c:forEach>
									
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
									<a href="javascript:query(${currentPage - 1 },null);">上一页</a>
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
													href="javascript:query(${i },null);" 
												</c:otherwise>
											</c:choose>
										>${i }</a>
									</li>
								</c:forEach>
								<li>
									<a href="javascript:query(${currentPage + 1 },null);">下一页</a>
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
