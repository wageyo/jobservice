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
<script type="text/javascript" src="${contextPath}/js/jquery.js"></script>
<script type="text/javascript" src="${contextPath}/js/JScript.js"></script>

<script type="text/javascript">
function getimgcode(){
	var timestamp=new Date().getTime();
	$("#verifyCode").attr("src","${contextPath}/checkcode/create?"+timestamp);
}
</script>
<title>残疾人招聘就业网</title>
</head>
<body>
	<jsp:include page="header.jsp" />
	<jsp:include page="nav.jsp" />

	<div id="hotNews">
		<div class="hotNews_top">
			<h2 class="fl hn_l">会员登录</h2>
			<h2 class="fl hn_c">热点导播</h2>
			<h2 class="fl hn_r">最新资讯</h2>
		</div>
		<div class="hotNews_con">

			<!-- 用户未登陆时显示登陆框 -->
			<c:if test="${user == null }">
				<div id="login">
					<form action="${contextPath }/user/login" method="post" onSubmit="return check();">
						<div id="ctl00_ContentPlaceHolder1_login_1" class="login_1">
							<ul style="width: 160px;">
								<li class="login_li"><input name="loginName" type="text" value="" id="loginName" maxlength="20" class="login_nput" value="请输入您的用户名"
									onfocus="if (this.value == &#39;请输入您的用户名&#39;) {this.value = &#39;&#39;;}" onblur="if (this.value == &#39;&#39;) {this.value = &#39;请输入您的用户名&#39;;}" /></li>
								<li class="login_li"><input name="passWord" type="password" value="" id="passWord" maxlength="20" class="login_nput"
									onfocus="if (this.value == &#39;请输入您的密码&#39;) {this.value = &#39;&#39;;}" onblur="if (this.value == &#39;&#39;) {this.value = &#39;请输入您的密码&#39;;}" /></li>
								<li class="login_li"><input style="margin-top: 10px" name="LoginVerifyCode" type="text" id="LoginVerifyCode" maxlength="5" class="login_nput" value="请输入验证码"
									onfocus="if (this.value == &#39;请输入验证码&#39;) {this.value = &#39;&#39;;}" onblur="if (this.value == &#39;&#39;) {this.value = &#39;请输入验证码&#39;;}" /></li>
							</ul>
							<ul>
								<li id="login_an"><input type="image" id="ctl00_ContentPlaceHolder1_ImageButton1" src="${contextPath}/images/an.jpg" style="border-width:0px;">
								</li>
								<li><span class="AntCod"><a href="javascript:getimgcode()"> <img id="verifyCode" src="${contextPath}/checkcode/create" alt="验证码,看不清楚?请点击刷新验证码"
											style="padding-top: 4px; width: 80px; border: #CCC 0px solid;" border="0">
									</a> </span>
								</li>
							</ul>
						</div>
					</form>
					<script type="text/javascript">
						function check(){
							//用户名
							var loginName = $('#loginName').val();
							if(loginName == null || loginName == '' ){
								alert('请输入用户名!');
								$('#loginName').focus();
								return false;
							}
							//新密码
							var passWord = $('#passWord').val();
							if(passWord == null || passWord == '' ){
						        alert('请输入密码!');
								$('#passWord').focus();
								return false;
							}
							//验证码
							var LoginVerifyCode = $('#LoginVerifyCode').val();
							if(LoginVerifyCode == null || LoginVerifyCode == '' || LoginVerifyCode == '请输入验证码'){
								alert('请输入验证码!');
								$('#LoginVerifyCode').focus();
								return false;
							}
							return true;
						}
					</script>

					<div id="ctl00_ContentPlaceHolder1_login_4" class="login_2">
						<table width="212" border="0" align="center" cellpadding="0" cellspacing="0">
							<tbody>
								<tr>
									<td width="61" class="xcvxvxcv"><a style="cursor:pointer;">找回密码</a></td>
									<td width="89" class="xcvxvxcv"><a style="cursor: pointer;">找回用户</a></td>
								</tr>
							</tbody>
						</table>
					</div>
					<div id="ctl00_ContentPlaceHolder1_login_3" class="login_3">
						<a href="${contextPath}/regP"> <img src="${contextPath}/images/up_1.jpg" border="0">
						</a> <a href="${contextPath}/regC"> <img src="${contextPath}/images/up_2.jpg" border="0">
						</a>
					</div>
				</div>
			</c:if>


			<!-- 用户登录时 -->
			<c:if test="${user != null }">
				<!-- 个人登陆显示的div -->
				<c:if test="${user.identity == 'person' }">
					<div class="loginstatus">
						<ul class="AntAccout_Center_Rithg">
							<li class="A" style="text-align: left">您好，<span id="ctl00_ContentPlaceHolder1_userlable">${user.loginName }</span>：欢迎您！</li>
							<li class="B">您的用户等级：<span> <span id="ctl00_ContentPlaceHolder1_tag">个人</span>会员 </span></li>
							<li class="C" style="text-align: left">注册时间：<span id="ctl00_ContentPlaceHolder1_addtime">${user.createDate }</span>
							</li>
							<li class="D">现在您可以进入<span id="ctl00_ContentPlaceHolder1_uemail"><a href="${contextPath }/user/goCenter" style="font-size:13px;color:red;font-weight:700px;">个人中心</a> </span>来管理您的个人信息或简历</li>

						</ul>
					</div>
				</c:if>

				<!-- 企业登陆显示的div -->
				<c:if test="${user.identity == 'company' }">
					<div class="loginstatus">
						<ul class="AntAccout_Center_Rithg">
							<li class="A" style="text-align: left">您好，<span id="ctl00_ContentPlaceHolder1_userlable">${user.loginName }</span>：欢迎您！</li>
							<li class="B">您的用户等级：<span> <span id="ctl00_ContentPlaceHolder1_tag">企业</span>会员 </span></li>
							<li class="C" style="text-align: left">注册时间：<span id="ctl00_ContentPlaceHolder1_addtime">${user.createDate }</span>
							</li>
							<li class="D">现在您可以进入<span id="ctl00_ContentPlaceHolder1_uemail"><a href="${contextPath }/user/goCenter" style="font-size:13px;color:red;font-weight:700px;">管理中心</a> </span>来管理您发布的招聘信息或者查看收到的简历</li>

						</ul>
					</div>
				</c:if>

				<!-- 管理员登陆显示的div -->
				<c:if test="${user.identity == 'admin' }">
					<div class="loginstatus">
						<ul class="AntAccout_Center_Rithg">
							<li class="A" style="text-align: left">您好，<span id="ctl00_ContentPlaceHolder1_userlable">${user.loginName }</span>：欢迎您！</li>
							<li class="B">您的用户等级：<span> <span id="ctl00_ContentPlaceHolder1_tag">管理员</span> </span></li>
							<li class="C" style="text-align: left">注册时间：<span id="ctl00_ContentPlaceHolder1_addtime">${user.createDate }</span>
							</li>

						</ul>
					</div>
				</c:if>
			</c:if>


			<div class="xian"></div>
			<div id="hotFlash">
				<div id="MainPromotionBanner">
					<div id="SlidePlayer">
						<ul class="Slides">
							<li style=""><a href="javascript:void(0);" title="求职宝典" target="_blank"><img src="${contextPath}/images/02285576946c4c239fea5fd5233dbf0d_baodian.jpg">
							</a></li>
						</ul>
					</div>

				</div>
			</div>
			<div class="xian"></div>
			<div id="newNotice" style="white-space:nowrap;overflow:hidden;">
				<ul>
					<c:forEach items="${newsList }" var="news">
						<li class="arrow"><div class="newNotice_color" style="width: 210px; white-space: nowrap; text-overflow: ellipsis; -o-text-overflow: ellipsis; overflow: hidden;">
								<img src="${contextPath}/images/list_style.png" />
								<a href="${contextPath }/news/getOneForShow?id=${news.id}" title="${news.title }">${news.title }</a>
							</div>
						</li>
					</c:forEach>
				</ul>
			</div>
		</div>
		<div class="hotNews_bot">
			<span class="rd_e_l fl"></span><span class="rd_e_r fr"></span>
		</div>
	</div>
	<!-- 
		<div id="redline">
			<div class="redline_top">
				<h2 class="fl rl_l">
					<span class="cH2">品牌招聘信息</span>
				</h2>
				<h2 class="fr rl_r">
					<a href="${contextPath}/work" target="_blank" class="cH2">更多招聘信息</a>
				</h2>
			</div>
			<div class="redline_con">
				<ul class="pic_ban">
					<c:forEach items="${companyList }" var="company">
						<li style="text-align: center;"><a href="${contextPath }/company/getOneForShow?id=${company.id}"> <img src="${contextPath}/images/453dfd05ecfa42398a11742b0b3ea761_20111111549ad.jpg"
								border="0" width="160" height="40">
						</a>
							<p>招聘：</p>
						</li>
					</c:forEach>
	
				</ul>
			</div>
			<div class="redline_bot">
				<span class="rd_e_l fl"></span><span class="rd_e_r fr"></span>
			</div>
		</div>
	 -->
	<div id="redline">
		<div class="redline_top">
			<h2 class="fl rl_l">
				<span class="cH2">热门招聘企业</span>
			</h2>
			<h2 class="fr rl_r">
				<a href="${contextPath}/work" target="_blank" class="cH2">更多招聘信息</a>
			</h2>
		</div>
		<div class="redline_con">
			<ul class="hotbrand">
				<c:forEach items="${companyList }" var="company">
					<li><img src="${contextPath}/images/hot_icon.gif" align="absbottom"><a class="f14" href="${contextPath }/company/getOneForShow?id=${company.id}">${company.name }</a><br> <img
								src="${contextPath}/images/bro_icon.gif" align="absbottom"> <a class="f14" href="${contextPath }/work" title="${company.businessScope.name }">${company.businessScope.name}</a>
					</li>
				</c:forEach>


			</ul>
		</div>



		<div class="redline_con"></div>
		<div class="redline_bot">
			<span class="rd_e_l fl"></span><span class="rd_e_r fr"></span>
		</div>
	</div>
	<div class="blueline">
		<div class="redline_top blueLineT">
			<h2 class="fl rl_l bgBlue">
				<span class="bH2">最新招聘信息</span>
			</h2>
			<h2 class="fr rl_r bgBlue">
				<a href="${contextPath}/work" target="_blank" class="bH2">更多招聘信息</a>
			</h2>
		</div>
		<div class="redline_con">
			<ul class="latJobs">
				<c:forEach items="${jobList }" var="job">
					<li><span class="fl"> <a class="cBlock" href="${contextPath }/job/getOneForShow?id=${job.id}" target="_blank"> ${job.name }</a>&nbsp; [${job.area.name }]&nbsp;&nbsp; <a
							class="job_jobs" href="${contextPath }/work" title="${job.jobCategory.name }">${job.jobCategory.name }</a>&nbsp; </span> <span class="fr"> ${job.updateDate }</span></li>
				</c:forEach>


			</ul>
		</div>



		<div class="blueLineE">
			<span class="rd_e_l fl bgBlue"></span><span class="rd_e_r fr bgBlue"></span>
		</div>
	</div>
	<div class="blueline">
		<div class="redline_top blueLineT">
			<h2 class="fl rl_l bgBlue">
				<span class="bH2">最新人才信息</span>
			</h2>
			<h2 class="fr rl_r bgBlue">
				<a href="${contextPath}/emp" target="_blank" class="bH2">更多人才信息</a>
			</h2>
		</div>
		<div class="redline_con">
			<ul class="prohr">
				<c:forEach items="${resumeList }" var="resume">
					<li><img src="${contextPath}/images/boy_icon.gif" align="absmiddle" style="float: left; margin-right: 3px;"> <a class="cGary"
							href="${contextPath }/resume/getOneForShow?id=${resume.id}" target="_blank"> ${resume.name } - 学历:${resume.education } - 求职:${resume.desireJob } </a>
					</li>
				</c:forEach>
			</ul>
		</div>
		<div class="blueLineE">
			<span class="rd_e_l fl bgBlue"></span><span class="rd_e_r fr bgBlue"></span>
		</div>
	</div>
	<jsp:include page="links.jsp" />
	</div>

	<jsp:include page="footer.jsp" />


</body>
</html>