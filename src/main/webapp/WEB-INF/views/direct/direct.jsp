<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="keywords" content="残疾人招聘信息,残疾人就业信息,残疾人人才网,残疾人找工作" />
<meta content="残疾人招聘就业" name="description" />
<link href="${contextPath}/css/header.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/css/style_job.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/css/body_job.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/css/search.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${contextPath}/js/jquery.js"></script>
<title>残疾人就业信息网</title>
<script type="text/javascript">
	$(document).ready(function() {
		common.pagination(1);
	});
	common = {};
	common.pagination = function(page) {
		var url = '${contextPath}/direct/search/' + page;
		$.ajax({
			url : url,
			type : 'GET',
			success : function(e) {
				$('#main').html(e);
				$('#data').fadeIn();
			},
			dataType : 'html',
			async : false
		});
	};
</script>
</head>
<body>
	<jsp:include page="../header.jsp" />

	<jsp:include page="../nav.jsp" />


	<div id="xBody">
		<div style="width: 980px; margin-top: 10px; margin-left: 2px; height: 600px;">
			<div style="width: 100%; float: left; height: 600px; border-left: 1px solid #b3cee3;
                border-right: 1px solid #b3cee3; border-bottom: 1px solid #b3cee3">
				<div
					style="width: 100%; height: 37px; line-height: 37px; background-image: url(${contextPath}/images/menu_bg_r16_c7.jpg);
                    color: #0980df; font-family: 微软雅黑; overflow: hidden">
					<div
						style="color: #ffffff; font-family: 宋体; overflow: hidden; text-align: center;
                        padding-top: 2px; margin-left: 10px; font-weight: bold; width: 120px; font-size: 13px;
                        background: url(${contextPath}/images/menu_bg_r16_c2.jpg) no-repeat left top">
						就业指导</div>
				</div>
				<div id="main"></div>
			</div>
			<!--  
			<div style="width: 260px; float: right; height: 600px">
				<div style="width: 254px; float: right; border: 1px solid  #9CC7E7">
					<div style="width: 232px; height: 29px; background-image: url(${contextPath}/images/menu_bg_r17_c19.jpg);
                        padding-left: 20px; padding-top: 10px; color: #0157B4">
						<strong>人才资讯</strong>
					</div>
					<div style="width: 252px;">
						<table style="line-height: 20px">
							<tbody>
								<tr>
									<td style="width:190px"><a href="show_8.html">求职简历</a>
									</td>
									<td style="width:62px">10月13日</td>
								</tr>
								<tr>
									<td style="width:190px"><a href="show_4.html">qqqq</a>
									</td>
									<td style="width:62px">10月13日</td>
								</tr>
								<tr>
									<td style="width:190px"><a href="show_5.html">wwwww</a>
									</td>
									<td style="width:62px">10月13日</td>
								</tr>
								<tr>
									<td style="width:190px"><a href="show_1.html">标题：</a>
									</td>
									<td style="width:62px">10月13日</td>
								</tr>
								<tr>
									<td style="width:190px"><a href="show_3.html">小猪</a>
									</td>
									<td style="width:62px">10月13日</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<div style="width: 254px; float: right; border: 1px solid #9CC7E7; margin-top: 8px">
					<div style="width: 232px; height: 29px; background-image: url(images/menu_bg_r17_c19.jpg);
                        padding-left: 20px; padding-top: 10px; color: #0157B4">
						<strong>工作宝典</strong>
					</div>
					<div style="width: 252px;">
						<table style="line-height: 20px">
							<tbody>
								<tr>
									<td style="width:190px"><a href="show_8.html">求职简历</a>
									</td>
									<td style="width:62px">10月13日</td>
								</tr>
								<tr>
									<td style="width:190px"><a href="show_4.html">qqqq</a>
									</td>
									<td style="width:62px">10月13日</td>
								</tr>
								<tr>
									<td style="width:190px"><a href="show_5.html">wwwww</a>
									</td>
									<td style="width:62px">10月13日</td>
								</tr>
								<tr>
									<td style="width:190px"><a href="show_3.html">小猪</a>
									</td>
									<td style="width:62px">10月13日</td>
								</tr>
								<tr>
									<td style="width:190px"><a href="show_1.html">标题：</a>
									</td>
									<td style="width:62px">10月13日</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>

			</div>
			-->
		</div>
	</div>
	<jsp:include page="../footer.jsp" />
</body>
</html>