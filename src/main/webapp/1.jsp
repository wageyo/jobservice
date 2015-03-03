<%@page import="esd.bean.Job"%>
<%@ page language="java" import="java.util.*" contentType="text/html"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>My JSP '1.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script src="${contextPath}/js/bootstrap/js/jquery-1.11.1.js"></script>
<script src="${contextPath}/js/manage/resumeMate.js"></script>
  </head>
  
  <body>
    This is my JSP page. <br>
    <input type="checkbox" id="CheckedAll" ><br/>
    <input type="checkbox" name="items" /><br/>
    <input type="checkbox" name="items" /><br/>
    <input type="checkbox" name="items" /><br/>
    <input type="checkbox" name="items" /><br/>
  </body>
</html>
