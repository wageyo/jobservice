<%@ page language="java" import="java.util.*" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh-cn">
<head>
	
	<title>统计数据查询</title>
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
			
			<!-- 右侧详细内容div begin -->
			<div class="manage-body-right">
			
				<div class="container-fluid">
				
					<!-- 下方结果显示框  开始 -->
					<div class="row-fluid">
						
						<!-- 显示企业招聘信息统计数据 begin -->
						<div class="span6" style="width: 90%;text-align: center;">
							<h3 class="text-success">
								企业招聘信息统计数据
							</h3>
						</div>
						<div class="span12" style="width: 90%;">
							<table class="table" >
								<thead>
									<tr class="info">
										<th>
											地区
										</th>
										<th>
											企业总数
										</th>
										<th>
											职位总数
										</th>
										<th>
											招聘总人数
										</th>
										<th>
											平均每家企业发布职位数
										</th>
										<th>
											平均每家企业招聘职工数
										</th>
										<th>
											注册总人数
										</th>
										<th>
											创建简历数
										</th>
										<th>
											已就业人数
										</th>
								<!-- 		<th>
											平均每人拥有简历数
										</th>
										<th>
											就业率
										</th> -->
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${entityList }" var="entity">
										<tr>
											<td>
												${entity.statisticsCompany.area.name }
											</td>
											<td>
												${entity.statisticsCompany.numberCompany }
											</td>
											<td>
												${entity.statisticsCompany.numberJob }
											</td>
											<td>
												${entity.statisticsCompany.numberHire }
											</td>
											<td>
												${entity.statisticsCompany.averageJob }
											</td>
											<td>
												${entity.statisticsCompany.averageHire }
											</td>
											<td>
												${entity.statisticsWorker.numberUser }
											</td>
											<td>
												${entity.statisticsWorker.numberResume }
											</td>
											<td>
												${entity.statisticsWorker.numberHired }
											</td>
									<!-- 		<td>
												${entity.statisticsWorker.averageResume }
											</td>
											<td>
												${entity.statisticsWorker.averageHired }%
											</td> -->
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
						<!-- 显示企业招聘信息统计数据  end -->
						
					</div>
					<!-- 下方结果显示框  开始 -->
					
				</div>
			</div>
			<!-- 右侧详细内容div end  -->
			
			<!-- 让body-right div的高度跟随内容自动变化 -->
			<div style="font: 0px/0px sans-serif;clear: both;display: block"> </div>
		</div>
		
		<!-- 尾部div -->
		<%@ include file="footer.jsp" %>
		
	</div>
	<!-- 整个页面div  结束 -->
</body>
</html>
