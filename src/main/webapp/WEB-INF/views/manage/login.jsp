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
<link rel="stylesheet" type="text/css" href="${contextPath}/js/lib/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="${contextPath}/js/lib/themes/icon.css" />

<script type="text/javascript" src="${contextPath}/js/jquery.js"></script>
<script type="text/javascript" src="${contextPath}/js/lib/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/lib/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
	$(function() {
		$('#manager_login').dialog({
			title : '后台管理登陆页面',
			width : 400,
			height : 240,
			closed : false,
			cache : false,
			
			modal : true,
			closable:false
		});

	});
</script>


<style type="text/css">
.managerLogin .login {
	margin: 18px auto auto;
}

.managerLogin .login td {
	height: 15px;
}

.managerLogin .login input {
	height: 20px;
}

.managerLogin .login .loginTitle {
	padding-bottom: 7px;
	font-size: 16px;
	font-weight: bold;
}
</style>
<script type="text/javascript">
	manageLogin={};
	manageLogin.user;
	manageLogin.pwd;
	manageLogin.verify=function(){
		manageLogin.user=$("#user").val();
		manageLogin.pwd=$("#pwd").val();
		if(manageLogin.user=='' || manageLogin.user== undefined){
					alert("用户名为空");
					return false;
		}	
		if(manageLogin.user.length<5){
				alert("用户长度不符合");
					return false;
		}
		if(manageLogin.pwd=='' || manageLogin.pwd== undefined){
					alert("密码为空");
					return false;
		}	
		
			if(manageLogin.pwd.length<5){
				alert("密码长度不符合");
					return false;
		}
		
		
		
		return true;
	};
	
	
	
</script>


<title>残疾人招聘就业后台登陆页面</title>
</head>

<body>
	<div id="manager_login" class="managerLogin">
		<form action="/jobservice/loginmanage/login" method="post"  onsubmit="return manageLogin.verify()">
			<table class="login">
				<tr>
					<td colspan="2" class="loginTitle">残疾人招聘就业后台登陆页面</td>
				</tr>
				<tr>
					<td colspan="2" class="loginError red">
					${loginInfo}
					</td>
				</tr>
				<tr>
					<td>用户名：</td>
					<td><input id="user" name="user" type="text" value="" /></td>
				</tr>
				<tr>
					<td>密码：</td>
					<td><input id="pwd" name="pwd" type="password" value="" /></td>
				</tr>
				<tr>
					<td colspan="2" class="loginTitle" align="center" height="35px"><input type="submit" style="height:25px;" value="登陆" />
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>