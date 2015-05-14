<%@ page language="java" import="java.util.*" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh-cn">
<head>
	
	<title>职位自动匹配</title>
	<meta http-equiv="keywords" content="残疾人,就业,招聘">
	<meta http-equiv="description" content="残疾人就业招聘网站">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" type="text/css" href="${contextPath}/js/bootstrap/css/bootstrap.css" />
	<link rel="stylesheet" type="text/css" href="${contextPath}/js/bootstrap/css/bootstrap-combined.min.css" />
	<link rel="stylesheet" type="text/css" href="${contextPath}/css/backdoor/main.css" />
	<link rel="stylesheet" type="text/css" href="${contextPath}/css/backdoor/main_list.css" />
	<script src="${contextPath}/js/lib/jquery-1.11.1.js"></script>
	<script src="${contextPath}/js/bootstrap/js/bootstrap.js"></script>
	<script src="${contextPath}/js/manage/common.js"></script>
	<script src="${contextPath}/js/manage/jobMate.js"></script>
	
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
				
						<!-- 上方条件查询框  开始 -->
						<div id="searchBar" style="height:32px;width: 1200px;">
							匹配度条件: 
							<span class="label " onclick="selectAllCondition();" id="chooseall">全 选</span>&nbsp;&nbsp;
							<c:forEach items="${params }" var="perCom">
								<span name="mateCondition" class="label" value="${perCom.value }" onclick="conditionClick(this);">${perCom.name }</span>&nbsp;&nbsp;
							</c:forEach>
							<span class="label badge-inverse" onclick="resetCondition();">重置查询条件</span> 
					 		<div style="float:right;">
								<button type="button" class="btn btn-info" onclick="sendTuiSong('half');">向选中推送</button>
								<button type="button" class="btn btn-info" onclick="sendTuiSong('all');">向全部推送</button>
							</div>
						</div>
						<!-- 上方条件查询框  结束-->
						
						<!-- 下方结果显示框  开始 -->
						<div class="row-fluid">
							
							<div class="span12">
								<table class="table" id="t1" style="padding:0;border-spacing:0px; border-style:none; width: 1200px;">
									<%-- 职位列表数据 --%>
									<thead>
										<tr style="background:#5599FF ;color:#ffffff">
											<th style="width:2%;">
												<input type="checkbox" id="checkedAll" onclick="selectAll();" />
											</th>
										 	<th style="width:4%;">
												序号
											</th>
											<th style="width:20%;">
												公司名称
											</th>
											<th style="width:6%;">
												联系人
											</th>
											<th style="width:10%;">
												联系电话
											</th>
											<th style="width:13%;">
												电子邮箱
											</th>
											<th style="width:7%;">
												企业性质
											</th>
											<th style="width:7%;">
												经济类型
											</th>
											<th style="width:8%;">
												所在地区
											</th>
											<th style="color:yellow;width:10%;">
												匹配信息
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
										<!--第一个公司表	数据					-->
										<c:forEach items="${entityList }" var="entity" varStatus="row">
											<tr style="font-size:13px;background:#ffffff;border-color: #ffffff">
												<td>
													<input type="checkbox" name="items" onclick="setSelectAll();" value="${entity.id }"/>
												</td>
												<td>
													${(row.index + 1 + (currentPage - 1) * 20) }
												</td>
												<td>
													<div class="exceedHidden" style="width:250px;" title="${entity.name }">${entity.name }</div>
												</td>
												<td>
													<div class="exceedHidden" style="width:80px;" title="${entity.contactPerson }">${entity.contactPerson}</div>
												</td>
												<td>
													<div class="exceedHidden" style="width:130px;" title="${entity.telephone }">${entity.telephone }</div>
												</td>
												<td>
													<div class="exceedHidden" style="width:140px;" title="${entity.email }">${entity.email }</div>
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
													<c:if test="${entity.jobCount != null && entity.jobCount != 0}">
														<a href="javascript:void(0);" onclick="lookMatchData(1,${entity.id },this)">查看匹配信息</a>
														<a href="javascript:void(0);" onclick="hideMatchData(this);" style="display:none;">隐藏匹配信息</a>
													</c:if>
													<c:if test="${entity.jobCount == null || entity.jobCount == 0}">
														<span style="color:red;">没有招聘信息</span>
													</c:if>
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
									<li>
									<a>总计  ${total } 条数据</a>
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
		<!-- 尾部div -->
		<%@ include file="footer.jsp" %>
		
	</div>
	<!-- 整个页面div  结束 -->
</body>
</html>
