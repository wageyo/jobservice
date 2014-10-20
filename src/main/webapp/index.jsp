<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>残疾人就业平台</title>

</head>
<body>
		<%
			//将网站群传过来的地区code放入到session中
			String acode = (String)request.getAttribute("acode");
			request.getSession().setAttribute("acode", acode);
		%>
		<script type="text/javascript">
			window.top.location.href = '${contextPath}/index?acode=<%=acode %>';
		</script>
</body>
</html>
