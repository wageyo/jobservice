<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<link href="${contextPath}/css/jobnav.css" rel="stylesheet" type="text/css">
	<div id="menu" class="w980">
		<ul id="menu_right">
			<li><a href="${contextPath}/index">首页</a></li>
			<li><a href="${contextPath}/work">找工作</a></li>
			<li><a href="${contextPath}/emp">聘人才</a></li>
			<li><a href="${contextPath}/direct">就业指导</a></li>
			<li><a href="${contextPath}/news">最新资讯</a></li>
			<li><a href="${contextPath}/contact">联系我们</a></li>
			<li><a href="${contextPath}/about">关于本站</a></li>
		</ul>
	</div>