<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>设置密码</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="keywords" content="残疾人招聘信息,残疾人就业信息,残疾人人才网,残疾人找工作" />
	<meta content="残疾人招聘就业" name="description" />
	
	<link rel="shortcut icon" href="${contextPath}/images/HomePageImage/favicon.ico" type="image/x-icon" />
	<link href="${contextPath}/css/style_job.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/Public.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/PublicStatusBar.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/HomePageHeader.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/SetHeaderStyle.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/HomePageFooter.css" rel="stylesheet" type="text/css" />

	<script type="text/javascript" src="${contextPath}/js/jquery.js"></script>
	<script type="text/javascript" src="${contextPath}/js/common.js"></script>
	<script type="text/javascript" src="${contextPath}/js/verify.js"></script>
	<script type="text/javascript" src="${contextPath}/js/check/password-set.js"></script>
	<script type="text/javascript">
	</script>
</head>
<body>
		<jsp:include page="../formatter/status-bar.jsp" />
	<jsp:include page="../formatter/header.jsp" />
	<div id="directBody">
		<div class="MainLeft" style="width:100%;">
			<div class="MainLeftRightHead1">
			
			<!-- 找回用户名 begin -->
				<div class="PublicframeTwo ">
	               <div class="PublicframeTwoHeadBar">
	                 <div class="PublicframeTwoHeadBarLeft"></div>
	                   <div class="PublicframeTwoHeadBarTittle">
	                       <span class="PublicframeTwoHeadBarTittleSpan">设置登陆密码</span>
	                   </div>
	                   <div class="PublicframeTwoHeadBarRight">
	                   </div>
	                   <div class="PublicframeTwoHeadBarMore">
	                       <span class="PublicframeTwoHeadBarMoreSpan"></span>
	                   </div>
	               </div>
	               <div class="PublicframeTwoContent" style="width: auto; padding: 3px 5px 5px;" id="">
	                   <div class="MainLeftTwo">
	                       <div class="MainLeftTwoLeft">
	                          <div id="PublicframeOne11" class="PublicframeOne ">
	                          	<div id="main" class="maincontent">
		                          	<form action="${contextPath }/user/setInitPassWord" method="post" onsubmit="return check();">
		                          		<table style="width:100%;line-height:50px;">
		                          			<tr>
		                          				<td style="text-align:right;padding-right:10px;width: 300px;">
		                          					用户名 ： 
		                          				</td>
		                          				<td style="text-align:left;">
		                          					<input type="text" class="input_border" name="loginName" id="loginName" readonly="readonly" 
		                          					<c:choose>
		                          						<c:when test="${tempUserName != null && tempUserName != '' }">
		                          							value="${tempUserName }"
		                          						</c:when>
		                          						<c:otherwise>
		                          							value="${user.loginName }"
		                          						</c:otherwise>
		                          					</c:choose>
		                          					 style="width:200px;padding: 3px;background-color: rgb(240, 240, 240);"/>
		                          				</td>
		                          			</tr>
		                          			<tr>
		                          				<td style="text-align:right;padding-right:10px;">
		                          					<span id="ctl00_ContentPlaceHolder1_RequiredFieldValidator1" style="color:Red;">*</span>
		                          					真实姓名   ： 
		                          				</td>
		                          				<td style="text-align:left;">
		                          				<input name="nickName" type="text" id="nickName" value="${user.nickName }" style="width:200px;padding: 3px;background-color: rgb(240, 240, 240);" class="input_border" />
		                          				<span id="ctl00_ContentPlaceHolder1_RequiredFieldValidator1" style="color:Red;">(校验用, 必填项.)</span>
		                          				</td>
		                          			</tr>
		                          			<tr>
		                          				<td style="text-align:right;padding-right:10px;">
		                          					<span id="ctl00_ContentPlaceHolder1_RequiredFieldValidator1" style="color:Red;">*</span>
		                          					邮箱   ： 
		                          				</td>
		                          				<td style="text-align:left;">
		                          				<input name="email" type="text" id="email" value="${user.email }" style="width:200px;padding: 3px;background-color: rgb(240, 240, 240);" class="input_border" />
		                          				<span id="ctl00_ContentPlaceHolder1_RequiredFieldValidator1" style="color:Red;">(用来找回用户名或密码用, 必填项.)</span>
		                          				</td>
		                          			</tr>
	                          				<tr>
		                          				<td style="text-align:right;padding-right:10px;">
		                          					<span id="ctl00_ContentPlaceHolder1_RequiredFieldValidator1" style="color:Red;">*</span>
		                          					密 码   ： 
		                          				</td>
		                          				<td style="text-align:left;">
		                          				<input name="passWord" type="password" id="newPassWord" style="width:200px;padding: 3px;background-color: rgb(240, 240, 240);" class="input_border" />
		                          				<span id="ctl00_ContentPlaceHolder1_RequiredFieldValidator1" style="color:Red;">(登陆密码6~12个字符)</span>
		                          				</td>
		                          			</tr>
	                          				<tr>
		                          				<td style="text-align:right;padding-right:10px;">
		                          					<span id="ctl00_ContentPlaceHolder1_RequiredFieldValidator1" style="color:Red;">*</span>
		                          					确认密码 ： 
		                          				</td>
		                          				<td style="text-align:left;">
		                          				<input name="conPassWord" type="password" id="conPassWord" class="input_border" style="width:200px;padding: 3px;background-color: rgb(240, 240, 240);" class="input_border" /><span id="ctl00_ContentPlaceHolder1_RequiredFieldValidator1" style="color:Red;" /> 
		                          				<span id="ctl00_ContentPlaceHolder1_RequiredFieldValidator1" style="color:Red;">(确认密码)</span>
		                          				</td>
		                          			</tr>
		                          			<tr>
		                          				<td colspan="2" style="text-align:center;">
		                          					<input type="submit" name="ctl00$ContentPlaceHolder1$Button1" value="保 存" id="ctl00_ContentPlaceHolder1_Button1"
														class="buttonGetBack" style="background: url(${contextPath}/images/edit_btn1.gif) no-repeat;" />
		                          				</td>
		                          			</tr>
		                          		</table>
	                          		
	                          		</form>
	                          	</div>
							  </div>
	                       </div>
	                   </div>
	                   <div style="clear: both"></div>     
	               </div>
				</div>
				<!-- 上部公司基本信息区域 begin -->
	           
	           
		    </div>	
		</div>
	</div>
	
	<jsp:include page="../formatter/footer.jsp" />
	</body>
</html>

