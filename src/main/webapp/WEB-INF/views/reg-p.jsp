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
<link href="${contextPath}/css/self-define.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${contextPath}/js/jquery.js"></script>
<script type="text/javascript" src="${contextPath}/js/common.js"></script>
<script type="text/javascript" src="${contextPath}/js/verify.js"></script>
<script type="text/javascript" src="${contextPath}/js/widget.js"></script>
<script type="text/javascript" src="${contextPath}/js/check/reg-check.js"></script>
<script type="text/javascript">
function getimgcode(){
	var timestamp=new Date().getTime();
	$("#verifyCode").attr("src","${contextPath}/checkcode/create?"+timestamp);
}
</script>
<title>残疾人就业信息网</title>

</head>
<body>
	<jsp:include page="formatter/header.jsp" />
	<jsp:include page="nav.jsp" />

	<div align="center">
		<div class="clear" style=" height:10px;">&nbsp;</div>
		<div align="center">
			<div id="G_Ys">
				<img src="${contextPath}/images/regstep.gif" /><br />
				<div style="height: 7px;">&nbsp;</div>
			</div>
			<div class="topt"></div>
			<form action="${contextPath }/user/save" method="post" onsubmit="return check()">
				<div class="content8">
					<div class="centent_left">
						<div id="font_sj" style="padding-left: 10px;">
							免费注册<span id="ctl00_ContentPlaceHolder1_Labelh">个人</span>会员
						</div>
						<div id="width">
							<div id="F_left">
								<span class="STYLE1">*&nbsp;</span>用户账号： <input type="hidden" value="person" name="identity" />
							</div>
							<div id="F_left_0">
								<input name="loginName" type="text" id="loginName" class="text_tex" onblur="blur_loginName()" onfocus="focus_loginName()" />
								<span id="loginNameNotice" style="color:red;" >用户名只能为字母, 数字和下划线_, 长度为5-20位 </span>
							</div>
						</div>
						<div id="width">
							<div id="F_left">
								<span class="STYLE1">*&nbsp;</span>登录密码：
							</div>
							<div id="F_left_0">
								<input name="passWord" type="password" id="passWord" class="text_tex" onfocus="focus_passWord()" onblur="blur_passWord()" />
								<span id="passWordNotice" style="color:Red;">密码只能为字母, 数字, 长度为5-20位</span>
							</div>
						</div>
						<div id="width">
							<div id="F_left">
								<span class="STYLE1">*&nbsp;</span>确认密码：
							</div>
							<div id="F_left_0">
								<input name="confirmPassWord" type="password" id="confirmPassWord" class="text_tex" onblur="blur_confirmPassWord()" />
								<span id="confirmPassWordNotice" style="color:Red;"></span>
							</div>
						</div>
						<div id="width">
							<div id="F_left">
								<span class="STYLE1">*&nbsp;</span>联系方式：
							</div>
							<div id="F_left_0">
								<input name="phone" type="text" id="phone" class="text_tex" onfocus="focus_phone()" onblur="blur_phone()"/>
								<span id="phoneNotice" style="color:Red;">联系方式为手机或座机</span>
							</div>
						</div>
						<div id="width">
							<div id="F_left">
								<span class="STYLE1">*&nbsp;</span>电子邮箱：
							</div>
							<div id="F_left_0">
								<input name="email" type="text" id="email" class="text_tex" onfocus="focus_email()" onblur="blur_email()"/>
								<span id="emailNotice" style="color:Red;"></span>
							</div>
						</div>
				<!-- 		<div id="width">
							<div id="F_left">
								<span class="STYLE1">*&nbsp;</span>所在地区：
							</div>
							<div id="F_left_0" style="line-height:25px;">
								<select name="area_lv1" id="area_lv1" class="select_border" style="width:100px;">
										<c:forEach items="${provinceList }" var="area">
											<option value="${area.code }">${area.name }</option>
										</c:forEach>
								</select> 
								<select name="area_lv2" id="area_lv2" class="select_border" style="width:100px;">
									<option value="">请选择区域</option>
								</select> 
								<select name="area.code" id="area_lv3" class="select_border" style="width:100px;">
									<option value="">请选择区域</option>
								</select>
								<br/><span id="passWordNotice" style="color:Red;">为方便残联中心和您沟通,请填写准确的所在地区</span>
							</div>
						</div> -->
						<div id="width">
							<div id="F_left">
								<span class="STYLE1">*&nbsp;</span>验&nbsp;证&nbsp;码：
							</div>
							<div id="F_left_0">
								<input name="LoginVerifyCode" type="text" id="LoginVerifyCode" class="text_tex_Yz" style="color:#C6C6C6;border-width:1px;border-style:solid;height:20px;" /> 
								<a href="javascript:getimgcode()"> 
									<img id="verifyCode"  src="${contextPath}/checkcode/create" alt="验证码,看不清楚?请点击刷新验证码" style="padding-top: 4px; width: 100px;"
										border="0" onclick="return getimgcode()" />
								</a>
								<span id="LoginVerifyCodeNotice" style="color:Red;">*请正确输入验证码</span>
							</div>
						</div>
						<br />
							<div id="width_bou" style="padding-left: 110px; clear: both;">
								<input type="submit" name="btn-submit" value="立即注册" id="btn-submit" tabindex="-1" class="button blue" /><br /> 
								<input id="ckb-agree" type="checkbox" name="ckb-agree" checked="checked" onclick="ckb_agree()" />阅读并同意用户协议 
							</div>`
					</div>
					<div class="centent_right">
						已有账号,&nbsp;<a id="ctl00_ContentPlaceHolder1_HyperLink1" href="${contextPath}/login" style="color:#ED7B2B;">登录</a><br /> <br /> 
							<img id="ctl00_ContentPlaceHolder1_Image1" src="${contextPath}/images/a1.jpg" style="border-width:0px;" />
					</div>
				</div>
			</form>
			

		</div>
		<div>
			<img src="${contextPath}/images/hjt3.png">
		</div>
	</div>

	<jsp:include page="footer.jsp" />


</body>
</html>