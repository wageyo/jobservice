<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>残疾人就业信息网</title>
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
</head>
<body>
<!-- 顶部工具条栏 -->
<jsp:include page="../formatter/status-bar.jsp" />

<!-- 头部导航及图片栏目 -->
<jsp:include page="../formatter/header.jsp" />
<div id="directBody">
	<div class="MainLeft" style="width:988px">
		<div class="MainLeftRightHead1">
           <div class="PublicframeTwo ">
               <div class="PublicframeTwoHeadBar">
                 <div class="PublicframeTwoHeadBarLeft"></div>
                   <div class="PublicframeTwoHeadBarTittle">
                       <span class="PublicframeTwoHeadBarTittleSpan">关于本站</span>
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
		                   	<div style="width: 900px; text-align: left; margin: 50px 100px 150px 330px;line-height: 30px; ">
								<h3 style="font-size: 14px;font-weight: bold;margin: 30px 100px 20px 80px;">残疾人就业信息网</h3>
								<p>本站最终解释权有哈尔滨亿时代数码科技开发有限公司所有。</p>
							</div>    			
						  </div>
                       </div>                     
                   </div>
                   <div style="clear: both">
                   </div>   
               </div>
           </div>
	    </div>	
	</div>
 </div>
		
	<%--
	<div align="center">
		<div class="clearboth" style=" height:10px;">&nbsp;</div>
		<div align="center">
			<div style="width: 900px; text-align: left;">您当前的位置 : 关于本站</div>
			<div class="topt"></div>
			<h3 style="margin-top: 50px;">残疾人就业信息网</h3>
			<div style="width: 900px; text-align: left; margin: 50px 100px 50px 400px;line-height: 30px; ">
				<p>本站最终解释权有哈尔滨亿时代数码科技开发有限公司所有。</p>
			</div>
		</div>
		<div>
			<img src="${contextPath}/images/hjt3.png">
		</div>
	</div>

	--%>
	<jsp:include page="../formatter/footer.jsp" />
</body>
</html>