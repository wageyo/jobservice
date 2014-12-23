<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>个人用户登陆</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="keywords" content="残疾人招聘信息,残疾人就业信息,残疾人人才网,残疾人找工作" />
	<meta content="残疾人招聘就业" name="description" />
	
	<link rel="shortcut icon" href="${contextPath}/images/HomePageImage/favicon.ico" type="image/x-icon" />
	
	<link href="${contextPath}/css/Public.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/PublicStatusBar.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/HomePageHeader.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/SetHeaderStyle.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/HomePageFooter.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/Qzzx_Login.css" rel="stylesheet" type="text/css" />
	
	<script type="text/javascript" src="${contextPath}/js/jquery.js"></script>
	<script type="text/javascript" src="${contextPath}/js/common.js"></script>
	<script type="text/javascript" src="${contextPath}/js/verify.js"></script>
	<script type="text/javascript" src="${contextPath}/js/login.js"></script>
	<script type="text/javascript">
		
	</script>
</head>
<body>
	<div id="container" class="container">

		<!-- 顶部工具条栏 -->
		<jsp:include page="../formatter/status-bar.jsp" />

		<!-- 头部导航及图片栏目 -->
		<jsp:include page="../formatter/header.jsp" />
		<div class="clearboth"></div>

		<!-- ******* 中部内容显示区 ******* begin ********** -->
		<div class="LoginContent">
			<div class="Left">
				<div class="Img_Ad"></div>
			</div>
			<div class="Right">
				<!-- 登陆界面 begin-->
				<div class="Login">
					<div class="Login_Head">
						<div class="User_Login" title="用户登录"></div>
					<!-- 	<div class="User_attention">如果是老用户，请先登录！！！</div>	 -->
					</div>
					<div class="clearboth"></div>
					<div class="Login_Content">
						<!-- 登陆form表单 -->
						<form action="${contextPath }/user/login" method="post" onsubmit="return check();" id="formLogin">
							<div class="User">
								<div class="UserName">
									<div class="Name">用户名：</div>
									<div class="Name_print">
										<input name="loginName" type="text" id="loginName" class="user_in" value="请输入用户名" style="color:#ccc" />
										<input type="hidden" id="identity" name="identity" value="person" />
									</div>
									<div id="loginNameImg" class="error">
                                    </div>
								</div>
								<div class="UserPwd">
									<div class="Pwd">密&nbsp;&nbsp;&nbsp;码：</div>
									<div class="Pwd_print">
										<input name="passWord" type="password" id="passWord" class="user_in" />
									</div>
									<div id="passWordImg" class="error">
                                    </div>
								</div>
								<div class="clearboth"></div>
								<div class="Login_Img">
									<div class="L_mg" id="loginSubmit"></div>
									<div class="Secret">
										<a href="HP_RetrievePassWord.aspx?crumbs=001026">忘记密码？</a>
									</div>
								</div>
							</div>
						</form>
						<div class="Boundary"></div>
						<div class="Regester">
							<div class="Att">您还没有成为我们的会员？</div>
							<div class="Regester_Img">
								<a href="${contextPath }/regP"> 
									<img src="${contextPath }/images/login/Register_Free.gif" alt="免费注册" />
								</a>
							</div>
						</div>
						<div class="clearboth"></div>

						<div class="DocDesption"
							style="margin-top:30px; font-size:16px; font-weight:bold;">
							<a href="UserFiles\DocDesption\广东省残疾人就业服务平台-个人功能篇 V1.3.Doc">个人用户使用手册下载</a>
						</div>
					</div>
				</div>
				<!-- 登陆界面 end-->
			</div>
		</div>
		<!-- ******* 中部内容显示区 ******* end ********** -->

		<!-- 尾部footer区 -->
		<div class="clearboth"></div>
		<jsp:include page="../formatter/footer.jsp" />
	</div>
</body>
</html>