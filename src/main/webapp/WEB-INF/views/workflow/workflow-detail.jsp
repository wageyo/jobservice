<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="keywords" content="残疾人招聘信息,残疾人就业信息,残疾人人才网,残疾人找工作" />
<meta content="残疾人招聘就业" name="description" />

<link rel="shortcut icon"
	href="${contextPath}/images/HomePageImage/favicon.ico"
	type="image/x-icon" />
<link href="${contextPath}/css/style_job.css" rel="stylesheet"
	type="text/css" />
<link href="${contextPath}/css/Public.css" rel="stylesheet"
	type="text/css" />
<link href="${contextPath}/css/PublicStatusBar.css" rel="stylesheet"
	type="text/css" />
<link href="${contextPath}/css/HomePageHeader.css" rel="stylesheet"
	type="text/css" />
<link href="${contextPath}/css/SetHeaderStyle.css" rel="stylesheet"
	type="text/css" />
<link href="${contextPath}/css/HomePageFooter.css" rel="stylesheet"
	type="text/css" />
<title>残疾人就业信息网</title>

<script type="text/javascript" src="${contextPath}/js/jquery.js"></script>
</head>
<body>
	<jsp:include page="../formatter/status-bar.jsp" />
	<jsp:include page="../formatter/header.jsp" />
	<div id="directBody">
		<div class="MainLeft">
			<div class="MainLeftRightHead1">
				<div class="PublicframeTwo ">
					<div class="PublicframeTwoContentNew"
						style="width: auto; padding: 3px 5px 5px;">
						<div class="banner">
							<a href="#" title="图片"> <img
								src="${contextPath}/images/ArticleReadImage/article_2.jpg"
								alt="图片" /> </a>
						</div>
						<div style="width: 710px;">
							<div class="news_title">
								<div
									style=" text-align:center; font-size:18px; line-height:36px; font-weight:bold; "
									id="title">${news.title }</div>
							</div>
							<!--news_title-->
							<div class="news_info"
								style="background: url(${contextPath}/images/dot.gif) repeat-x 50% top;">
								<c:if test="${news.createDate != null}">
									<fmt:formatDate value="${news.createDate }" type="both"
										dateStyle="long" pattern="yyyy-MM-dd hh:mm:ss"
										var="createDate" />
								</c:if>
							</div>
							<div>
								<hr
									style="width: 700px; margin: 10px auto; border-style: dotted; border-color:#CCC;" />
								<div class="news_info_l">时间:${createDate }</div>
								<div class="news_info_c">责任编辑:${news.author }</div>
								<div class="news_info_r">来源:${news.source }</div>
							</div>
							<!--news_title-->
							<div class="news_contain"
								style="text-align: left;font-size: 13px;width: 690px;  margin:0 auto;color:#000000;text-indent:0px;margin-bottom: 10px;">
								<!-- 如果存在图片则显示出来 -->
								<c:if test="${news.imageId != null && news.imageId != '' }">
									<p style="text-align:center;">
										<img
											src="${contextPath }/filegags/downloadFile/${news.imageId}"
											style="width:500px;" />
									</p>
								</c:if>
								<p>${news.content }</p>
							</div>
							<!--news_contain-->
							<div class="page_neirong">
								<ul>
								</ul>
							</div>
							<!--news_fenye-->
							<div class="news_ln">
								<!-- 	<div class="news_ln_l">
							没有了 <br> 下一篇：<a href="show_4.html">qqqq</a>
						</div>
						<div class="news_ln_r"></div>
						 -->
							</div>
						</div>
						<div style="clear: both"></div>
					</div>
				</div>
			</div>
		</div>
		<div class="MainRight">
			<div class="MainRightTwo">
				<div class="PublicframeTwo ">
					<div class="PublicframeTwoHeadBar">
						<div class="PublicframeTwoHeadBarLeft"></div>
						<div class="PublicframeTwoHeadBarTittle">
							<span class="PublicframeTwoHeadBarTittleSpan"> <c:if
									test="${news.type == 'news' }">
							最新资讯
						</c:if> <c:if test="${news.type == 'direct' }">
							就业指导
						</c:if> <c:if test="${news.type == 'politicies' }">
							政策法规
						</c:if> <c:if test="${news.type == 'workflow' }">
							工作流程
						</c:if> </span>
						</div>
						<div class="PublicframeTwoHeadBarRight"></div>
						<div class="PublicframeTwoHeadBarMore">
							<span class="PublicframeTwoHeadBarMoreSpan"></span>
						</div>
					</div>
					<div class="PublicframeTwoContent" id="">
						<table style="line-height: 20px;padding-bottom: 10px;">
							<tbody>
								<c:forEach items="${newsList }" var="news">
									<tr>
										<td><div
												style="width: 150px;white-space: nowrap;text-overflow: ellipsis; -o-text-overflow: ellipsis; overflow: hidden;">
												<a href="${contextPath }/news/getOneForShow?id=${news.id}"
													title="${news.title }"> <img
													src="${contextPath}/images/Sy_HRsqImage/Square.jpg"
													style="padding-left: 8px;line-height: 25p;text-align: left;" />
													<span style="margin-left:0px;">${news.title }</span> </a>
											</div>
										</td>
										<td style="width:80px"><c:if
												test="${news.createDate != null}">
												<fmt:formatDate value="${news.createDate}" type="both"
													dateStyle="long" pattern="yyyy-MM-dd" var="createDate" />
											</c:if> <span style="margin-right:0px;">${createDate }</span></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<div style="clear: both"></div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="../formatter/footer.jsp" />
</body>
</html>