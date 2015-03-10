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
	                       <span class="PublicframeTwoHeadBarTittleSpan">联系我们</span>
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
	                       			<div style="width: 988px; text-align: left; margin: 30px 160px 80px 230px;line-height: 30px; font-size:13px;">
	                       				<p style="font-weight: bold;">通信地址: </p>
										<p>地&nbsp;&nbsp;&nbsp;&nbsp;址：南宁市罗文大道48号广西残疾人事业园</p>
										<p>邮&nbsp;&nbsp;&nbsp;&nbsp;编：530007</p>
										<p style="font-weight: bold;">各科室电话: </p>
										<p>办&nbsp;&nbsp;&nbsp;公&nbsp;&nbsp;&nbsp;室：0771-3186987 3186955（传真）</p>
										<p>就业科管理: 0771-3186950 3186951（就业科）  0771-5595356（残联政务窗口）</p>
										<p>职业指导科: 0771-3186952 3186953</p>
										<p>职业培训科: 0771-3186930 3186931 </p>
										<p>综&nbsp;&nbsp;&nbsp;合&nbsp;&nbsp;&nbsp;科:0771-3186935、3186932 </p>
										<p>邮&nbsp;&nbsp;&nbsp;&nbsp;箱：<a href="mailto:gxjyzx@126.com">gxjyzx@126.com</a>或<a href="mailto:jyzx@gxdpf.org.cn">jyzx@gxdpf.org.cn</a></p>
									</div>                          
							  </div>
	                       </div>                     
	                   </div>
	                   <div style="clear: both"></div>           
					</div>
	           </div>
		    </div>	
		</div>	
	</div>		
		<%--

	<div align="center">
		<div class="clearboth" style=" height:10px;">&nbsp;</div>
		<div align="center">
			<div style="width: 900px; text-align: left;">您当前的位置 : 联系我们</div>
			<div class="topt"></div>
			<h3 style="margin-top: 50px;">哈尔滨亿时代数码科技开发有限公司</h3>
			<div style="width: 900px; text-align: left; margin: 50px 100px 50px 400px;line-height: 30px; ">

				<p>地址：黑龙江省哈尔滨市香坊区红旗大街108号七0三科技创业中心（广瀚大厦7楼707室）</p>
				<p>电话：0451-55664482</p>
				<p style="margin-left:36px;">0451-55663342</p>
				<p>传真：0451-55664482-8018</p>
				<p>网址：www.hrbesd.com</p>
				<p>邮箱：hrbesd@yahoo.com.cn</p>
				<p style="margin-left:36px;">esdbgs@163.com</p>
				<p>市场部资讯电话：0451-55664482-8013/8014</p>
				<p style="margin-left:96px;">0451-55176366</p>
				<p>技术部资讯电话：0451-55664482-8004</p>
			</div>

		</div>

		<div>
			<img src="${contextPath}/images/hjt3.png">
		</div>
	</div>

		--%><!-- 尾部footer区 -->
		<div class="clearboth"></div>
		<jsp:include page="../formatter/footer.jsp" />
</body>
</html>