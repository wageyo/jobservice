<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>个人用户注册</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="keywords" content="残疾人招聘信息,残疾人就业信息,残疾人人才网,残疾人找工作" />
	<meta content="残疾人招聘就业" name="description" />
	
	<link rel="shortcut icon" href="${contextPath}/images/HomePageImage/favicon.ico" type="image/x-icon" />
	<link href="${contextPath}/css/Public.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/HomePageHeader.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/SetHeaderStyle.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/HomePageFooter.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/PublicStatusBar.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/Sy_BusinessRegisterStateOne.css" rel="stylesheet" type="text/css" />
	
	<script type="text/javascript" src="${contextPath}/js/jquery.js"></script>
	<script type="text/javascript" src="${contextPath}/js/common.js"></script>
	<script type="text/javascript" src="${contextPath}/js/verify.js"></script>
	<script type="text/javascript" src="${contextPath}/js/check/reg-check.js"></script>
	<script type="text/javascript">
		function getimgcode(){
			var timestamp=new Date().getTime();
			$("#verifyCode").attr("src","${contextPath}/checkcode/create?"+timestamp);
		}
	</script>
</head>
<body >
	<div id="container" class="container">
	
		<!-- 顶部工具条栏 -->
		<jsp:include page="../formatter/status-bar.jsp" />
		
		<!-- 头部导航及图片栏目 -->
		<jsp:include page="../formatter/header.jsp" />
		<div class="clearboth"></div>
		
		<!-- ******* 中部内容显示区 ******* start ********** -->
		<div id="content" class="content">
			<!-- ******* 注册企业账号 ******* start ********** -->
			<div class="RegisterMain" id="RegisterOne">
	        	<form action="${contextPath }/user/save" method="post" onsubmit="return check();" id="registerForm">
		            <div class="BusinessRegisterText" style="background: url( '${contextPath}/images/BusinessRegister/BusinessRegisterTextPerson.jpg' ) no-repeat left;"> 
		            	<div class="text">如果有疑问，可以致电我们的服务热线。
		            	<a href="${contextPath }/contact" target="_blank" style="color:orange;">详请请点击</a>
		            	</div>
		            </div>
		            <table class="RegisterTable" cellpadding="0" cellspacing="0">
		                <tbody>
		                <tr>
		                    <th colspan="3">账号资料<span style="font-size:10px;font-weight:normal;"> (注：带“<span class="red">*</span>”为必填项)</span></th>
		                </tr>
		                <tr>
		                    <td class="textAlignRight FontSize"><span class="red">*</span>用户名：</td>
		                    <td class="printText">
		                        <div class="print">
		                        	<input name="loginName" id="loginName" class="TextInput" type="text" />
		                        	<input type="hidden" value="person" name="identity"  id="identity"/>
		                        </div>
		                    </td>
		                    <td class="textAlignLeft">
		                    <div id="showUserNameImg" class="SetFloatLeft"></div>
		                    <div id="showUserNameMsg" class="SetFloatLeft">可由5-20位字母、数字或下划线组成,首字母不能是数字</div>
		                    </td>
		                </tr>
		                <tr class="bgColor">
		                    <td class="textAlignRight FontSize"><span class="red">*</span>密码：</td>
		                    <td class="printText">
		                        <div class="print">
		                        	<input name="passWord" id="txtPassWord" class="TextInput" type="password" />
		                        </div>
		                    </td>
		                    <td class="textAlignLeft">
		                    <div id="showPasswordImg" class="SetFloatLeft"></div>
		                    <div id="showPasswordMsg" class="SetFloatLeft">请输入英文字母、数字或下划线，长度5-20个字符</div>
		                    </td>
		                </tr>
		                <tr>
		                    <td class="textAlignRight FontSize"><span class="red">*</span>确认密码：</td>
		                    <td class="printText">
		                        <div class="print">
		                        	<input name="passWordConfirm" id="passWordConfirm" class="TextInput" type="password" />
		                        </div>
		                    </td>
		                    <td class="textAlignLeft">
		                    <div id="showPassWordConfirmImg" class="SetFloatLeft"></div>
		                    <div id="showPassWordConfirmMsg" class="SetFloatLeft">请再次输入密码</div>
		                    </td>
		                </tr>
		                <tr>
		                    <td class="textAlignRight FontSize"><span class="red">*</span>真实姓名：</td>
		                    <td class="printText">
		                        <div class="print">
		                        	<input name="nickName" id="nickName" class="TextInput" type="text" />
		                        </div>
		                    </td>
		                    <td class="textAlignLeft">
		                    <div id="showNickNameImg" class="SetFloatLeft"></div>
		                    <div id="showNickNameMsg" class="SetFloatLeft">请输入您的真实姓名, 以方便残联就业管理中心进行管理.</div>
		                    </td>
		                </tr>
		                <tr>
		                    <td class="textAlignRight FontSize">联系方式：</td>
		                    <td class="printText">
		                        <div class="print">
		                        	<input name="phone" id="phone" class="TextInput" type="text" />
		                        </div>
		                    </td>
		                    <td class="textAlignLeft">
		                    <div id="showPhoneImg" class="SetFloatLeft"></div>
		                    <div id="showPhoneMsg" class="SetFloatLeft">请填写联系方式, 方便残疾人就业指导中心联系你.</div>
		                    </td>
		                </tr> 
		                <tr class="bgColor">
		                    <td style="width:100px" class="textAlignRight FontSize"><span class="red">*</span>Email：</td>
		                    <td style=" width:280px" class="printText">
		                        <div class="print">
		                        	<input name="email" id="email" class="TextInput" maxlength="60" type="text" />
		                        </div>
		                    </td>
		                    <td class="textAlignLeft">
		                    <div id="showEmailImg" class="SetFloatLeft"></div>
		                    <div id="showEmailMsg" class="SetFloatLeft">请填写常用且有效的E-mail地址, 以便在您忘记用户名或密码的时候找回使用.</div>
		                    </td>
		                </tr>
		                <tr class="bgColor">
		                    <td style="width:100px" class="textAlignRight FontSize"><span class="red"></span>验证码：</td>
		                    <td style=" width:280px" class="printText">
		                        <div>
									<img id="verifyCode" src="${contextPath}/checkcode/create" alt="验证码,看不清楚?请点击刷新验证码" style="width: 90px; float: left;" border="0"
										onclick="return getimgcode()" />
		                        	<input name="LoginVerifyCode" id="LoginVerifyCode" class="TextInput" maxlength="60" type="text" style="border: 1px solid rgb(206, 220, 221); width: 142px;margin-top: 3px;height:20px; padding:3px;" />
		                        </div>
		                    </td>
		                    <td class="textAlignLeft">
		                    <div id="showCheckCodeImg" class="SetFloatLeft"></div>
		                    <div id="showCheckCodeMsg" class="SetFloatLeft" >点击图片以刷新</div>
		                    </td>
		                </tr>
		            </tbody></table>
					<div class="RegisterButtom" id="RegisterBtnOne" onclick="registerSubmit();"></div>
	       		</form>
			</div>
	        <!-- ******* 注册企业账号 ******* end ********** -->
	        <div class="clearboth"></div>

		</div>
		<!-- ******* 中部内容显示区 ******* end ********** -->
		
		<!-- 尾部footer区 -->
		<div class="clearboth"></div>
		<jsp:include page="../formatter/footer.jsp" />
	</div>

</body>
</html>