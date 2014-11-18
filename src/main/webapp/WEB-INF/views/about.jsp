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
<link href="${contextPath}/css/style.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/css/sj.css" rel="stylesheet" type="text/css" />
<title>残疾人就业信息网</title>
</head>
<body>
	<jsp:include page="header.jsp" />
	<jsp:include page="nav.jsp" />

	<div align="center">
		<div class="clear" style=" height:10px;">&nbsp;</div>
		<div align="center">
			<div style="width: 900px; text-align: left;">您当前的位置 : 关于本站</div>
			<div class="topt"></div>
			<h3 style="margin-top: 50px;">残疾人就业信息网</h3>
			<div style="width: 900px; text-align: left; margin: 50px 100px 50px 400px;line-height: 30px; ">
				<p>本站最终解释权有哈尔滨亿时代数码科技开发有限公司所有。</p>
			</div>
		</div>
		<div>
			<img src="${contextPath}/images/hjt3.png">
		</div>
	</div>
	</div>

	<jsp:include page="footer.jsp" />


</body>
</html>