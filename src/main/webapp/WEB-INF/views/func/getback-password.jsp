<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>找回密码</title>
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
	<script type="text/javascript" src="${contextPath}/js/check/get-back.js"></script>
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
	                       <span class="PublicframeTwoHeadBarTittleSpan">找回密码</span>
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
	                          		<table style="width:100%;line-height:50px;">
	                          			<tr>
	                          				<td style="text-align:right;padding-right:10px;">
	                          					请填写您注册时的邮箱: 
	                          				</td>
	                          				<td style="text-align:left;">
	                          					 <input type="text" id="email"  style="width:200px;padding: 3px;background-color: rgb(240, 240, 240);"/>
	                          				</td>
	                          			</tr>
	                          			<tr>
	                          				<td colspan="2" style="text-align:center;">
	                          					<input type="button" class="buttonGetBack" onclick="getBack('password');" value=" 发 送 " style="" />
	                          				</td>
	                          			</tr>
	                          		</table>
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