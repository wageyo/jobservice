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
<script type="text/javascript">
</script>
<title>残疾人招聘就业网</title>
</head>
<body>
	<jsp:include page="../header.jsp" />

	<jsp:include page="../nav.jsp" />


	<div id="xBody">
		<!--头部结束--->
		<div id="xdAL" style="width: 1px;"></div>
		<!--内容页面开始-->
		<div class="imga">
			<img src="${contextPath}/images/c2e34940b08940b48f3255b493b4ba68_zph_2013_ad_full.gif" alt="招聘频道" width="980" height="80"
				border='0'>
		</div>

		<div class="xxE1H12">
				<div class="company11">
					<a href="${contextPath }/company/getOneForShow?id=${company.id}">${company.name }</a>
					<p>企业性质：${company.nature }</p>
					<p>公司地址：${company.address }</p>
					<p>联系人：${company.contactPerson }</p>
				</div>
		</div>

		<div class="xxE1">

			<div class="xxE1H">
				企业简介：<span id="ctl00_ctl00_cph_cph_lb_jobname">${company.name }</span>
			</div>


			<div class="xxE1B" style=" margin-top:10px; text-indent:5px;">
				简介：&nbsp;&nbsp;&nbsp;&nbsp;<span id="ctl00_ctl00_cph_cph_lb_jobname"></span>
			</div>

			<div class="xxE1B">
				<table class="xxE1Bt">
					<tbody>
						<tr class="job_detail">
							<td colspan="4"><span id="ctl00_ctl00_cph_cph_lb_posttype">${company.introduction }</span></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<div id="ctl00_ctl00_cph_cph_div_jobs" class="xxE2">
		<div class="xxE2H">本公司发布的所有招聘信息</div>
		<div class="xxE2B">
			<table class="xxEpl" cellspacing="0">
				<tbody>
					<tr class="zhaopin">
						<th class="t0">职位名称</th>
						<th>招聘人数</th>
						<th>岗位类型</th>
						<th>学历要求</th>
						<th>更新时间</th>
						<th>工作性质</th>
					</tr>
					<c:forEach items="${jobList }" var="job">
						<tr class="zhaopin1">
							<td class="jobs01"><a href="${contextPath }/job/getOneForShow?id=${job.id}">${job.name }</a>
							</td>
							<td>${job.hireNumber } 名</td>
							<td>${job.jobCategory.name}</td>
							<td>${job.education }</td>
							<td>${job.updateDate }</td>
							<td>${job.nature }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	<jsp:include page="../footer.jsp" />
</body>
</html>