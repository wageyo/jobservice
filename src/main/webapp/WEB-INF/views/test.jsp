<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="keywords" content="残疾人招聘信息,残疾人就业信息,残疾人人才网,残疾人找工作" />
<meta content="残疾人招聘就业" name="description" />

<script type="text/javascript" src="${contextPath}/js/jquery.js"></script>
<title>聘人才</title>
<script type="text/javascript">
	$(document).ready(function() {
		common.pagination(1);
		
	});

	common = {};
	common.pagination = function(page) {
		var url = '/jobservice/jsonptest';
		$.ajax({
			url : url,
			type : 'GET',
			success : function(e) {
				alert("efew");
				//alert(e.pi);
			},
			dataType : 'json',
			async : true
		});
	};
</script>
</head>
<body>
grregre
</body>
</html>