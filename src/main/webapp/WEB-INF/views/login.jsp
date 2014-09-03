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
<link href="${contextPath}/css/cc.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${contextPath}/js/jquery.js"></script>
<script type="text/javascript">
function getimgcode(){
	var timestamp=new Date().getTime();
	$("#LoginVerifyCode").attr("src","${contextPath}/checkcode/create?"+timestamp);
}
</script>
<title>残疾人招聘就业网</title>
</head>
<body>
	<jsp:include page="header.jsp" />
	<jsp:include page="nav.jsp" />

	<div align="center">
		<div class="AntUserLoginPage">
			<div class="topt"></div>
			<div class="content8">
				<div class="topB">
					<img id="Image1" src="${contextPath}/images/user.jpg" style="border-width:0px;">
				</div>
				<!-- 登陆 -->
				<form action="${contextPath }/user/login" method="post">
					<div class="AntUserLoginPage_Right">
						<ul class="AntUserLoginPage_Ul">
							<li class="loginName"><span class="A">用户名：</span> <input name="loginName" type="text" id="loginId" tabindex="1" class="AntInfoTextInput InfoAccountUsername"> <span
									id="RequiredFieldValidator1" style="color:Red;display:none;">*</span>
							</li>
							<li class="loginName"><span class="A">密&nbsp;&nbsp;&nbsp;&nbsp;码：</span> <input name="passWord" type="password" id="loginPwd" tabindex="2" class="AntInfoTextInput InfoAccountUserpwd"><span
									class="AA"><a href="#" target="_blank" id="AntFindPassword">找回密码</a> </span><span id="RequiredFieldValidator2" style="color:Red;display:none;">*</span>
									&nbsp; 
							</li>
							<li class="loginName"><span class="A">验证码&nbsp;：</span><input name="LoginVerifyCode" type="text" id="checkNum" tabindex="3" class="AntInfoTextInput" style="width:75px;background: url(${contextPath}/images/hjt5.gif) no-repeat;">
									<span class="AntCod"><a href="javascript:getimgcode()"> <img id="LoginVerifyCode"  src="${contextPath}/checkcode/create" alt="验证码,看不清楚?请点击刷新验证码" style="padding-top: 4px; width: 100px;"
											border="0" onclick="return getimgcode()">
									</a> </span> <span id="RequiredFieldValidator3" style="color:Red;display:none;">*</span> <a id="LoginVerifyCodeClick" href="javascript:getimgcode()"><font id="LoginVerifyCodeClickHide"> 换一张？</font> </a>
							</li>
							<li style="clear: both" class="AntUserLoginPageSave">
							</li>
							<li class="AntUserLoginPageBottom"><input type="submit" name="InfoAccountLoginSubmit" value="确认登录"
								onclick="javascript:WebForm_DoPostBackWithOptions(new WebForm_PostBackOptions(&quot;InfoAccountLoginSubmit&quot;, &quot;&quot;, true, &quot;&quot;, &quot;&quot;, false, false))"
								id="InfoAccountLoginSubmit">
							</li>
						</ul>
					</div>
				</form>
				<div class="AntUserLoginPage_Left">
					<div class="New_user">
						<a style="float: left; text-align: left; text-indent: 50x;" href="${contextPath }/regP"> 免费注册会员！</a>
					</div>
					<div class="clr"></div>
					<div class="u_one">
						<span>.net人才系统</span>专业打造品质 成就铸造信誉
					</div>
					<div class="u_two">
						<span>注册成为会员</span>享受成为本站强大功能及服务的前提条件
					</div>
					<div class="u_three">
						<span>***.com</span>快速、高效 海量人才 尽在把握
					</div>
				</div>
			</div>
		</div>
		<div style="clear: both;"></div>
		<div class="bot"></div>
	</div>

	<jsp:include page="footer.jsp" />
</body>
</html>