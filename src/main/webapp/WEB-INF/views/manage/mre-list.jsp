<%@page import="esd.bean.Job"%>
<%@ page language="java" import="java.util.*" contentType="text/html"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh-cn">
<head>

<title>简历自动匹配</title>
<meta http-equiv="keywords" content="残疾人,就业,招聘">
<meta http-equiv="description" content="残疾人就业招聘网站">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" type="text/css"
	href="${contextPath}/js/bootstrap/css/bootstrap.css" />
<link rel="stylesheet" type="text/css"
	href="${contextPath}/js/bootstrap/css/bootstrap-combined.min.css" />
<link rel="stylesheet" type="text/css"
	href="${contextPath}/css/backdoor/main.css" />
<script src="${contextPath}/js/bootstrap/js/jquery-1.11.1.js"></script>
<script src="${contextPath}/js/bootstrap/js/bootstrap.js"></script>
<script src="${contextPath}/js/manage/common.js"></script>
<script src="${contextPath}/js/manage/resumeMate.js"></script>
</head>

<body>
	<%
		int sequencePer = 1;
	%>

	<!-- 整个页面div  开始 -->
	<div class="manage-body">
		
		<!-- 头部 div -->
		<%@ include file="header.jsp"%>

		<!-- 中间主体div 开始-->
		<div class="manage-body">
		
			<!-- 左侧菜单div -->
			<%@ include file="body-left.jsp"%>

			<!-- 右侧详细内容div  -->
			<div class="manage-body-right">

				<div class="container-fluid">
					<!-- 上方条件查询框  开始 -->
					<div class="">
						匹配度: 
						<div class="btn-group" >
							<c:forEach items="${params }" var="p">
								<c:if test="${p.value == matchRate }">
									<c:set var="companyMatchPerName">${p.name }</c:set>
								</c:if>
							</c:forEach>
							<button class="btn" id="btncompanyMatchPer">${companyMatchPerName }</button> 
							<input type="hidden" id="matchRate" name="matchRate" value="${matchRate }"/>
							<button data-toggle="dropdown" class="btn dropdown-toggle"><span class="caret"></span></button>
							<ul class="dropdown-menu">
								<c:forEach items="${params }" var="perCom">
									<li>
										<a href="javascript:query(1,'${perCom.value }');">${perCom.name }</a>
									</li>
								</c:forEach>
							</ul>
						</div>
						<div style="float:right;">
						<button type="button" class="btn btn-info" onclick="sendTuiSong(null);">向选中简历推送招聘信息</button>
						<button type="button" class="btn btn-info" onclick="sendTuiSong('all');">向全部简历推送招聘信息</button>
						</div>
					</div>
					<!-- 上方条件查询框  结束-->
						
					<!-- 下方结果显示框  开始 -->
					<div class="row-fluid">
						<div class="span12">
							<table class="table" id="t1" style="padding:0;border-spacing:0px; border-style:none; ">
								<%--第一个公司表	标题						--%>
								<thead>
									<tr style="background:#5599FF ;color:#ffffff">
										<th style="width:2%;">
											<input type="checkbox" id="checkedAll" onclick="selectAll();" />
										</th>
									 	<th style="width:4%;">
											序号
										</th>
										<th style="width:10%;">
											简历名称
										</th>
										<th style="width:8%;">
											姓名
										</th>
										<th style="width:6%;">
											性别
										</th>
										<th style="width:10%;">
											残疾证号
										</th>
										<th style="width:6%;">
											残疾类别
										</th>
										<th style="width:6%;">
											残疾等级
										</th>
										<th style="width:7%;">
											期待薪资
										</th>
										<th style="width:8%;">
											联系方式
										</th>
										<th style="color:yellow;width:10%;">
											查看匹配信息
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
									<!-- 简历列表数据 -->
									<c:forEach items="${entityList }" var="entity" varStatus="row">
										<tr style="font-size:13px;background:#ffffff;border-color: #ffffff">
											<td>
												<input type="checkbox" name="items" onclick="setSelectAll();" value="${entity.id }"/>
											</td>
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
												${entity.gender}
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
												${entity.desireSalary }
											</td>
											<td>
												${entity.phone }
											</td>
											<td>
												<a href="javascript:void(0);" onclick="lookMatchData(1,${entity.id },this)">查看匹配信息</a>
												<a href="javascript:void(0);" onclick="hideMatchData(this);" style="display:none;">隐藏匹配信息</a>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
					<!-- 下方结果显示框  结束 -->
						
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
				
				<!-- 让body-right div的高度跟随内容自动变化 -->
			<div style="font: 0px/0px sans-serif;clear: both;display: block"> </div>
		</div>
		</div>
		<!-- 中间主体div 结束 -->
		
		<!-- 尾部div -->
		<%@ include file="footer.jsp"%>
	</div>
	<!-- 整个页面div  结束 -->
</body>
</html>
