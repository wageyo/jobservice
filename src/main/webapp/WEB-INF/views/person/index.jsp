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
<link href="${contextPath}/css/user/style.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/css/user/StyleSheet.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${contextPath}/js/jquery.js"></script>
<title>残疾人招聘就业网</title>
</head>
<body>
	<jsp:include page="../header.jsp" />
	<jsp:include page="../nav.jsp" />

	<div class="w988 ptb10 center">
		<jsp:include page="left-nav.jsp" />

		<div class="Air">
			<table class="w770" border="0" cellspacing="0" cellpadding="0" align="center">
				<tbody>
					<tr valign="top">
						<td>
							<table border="0" cellspacing="0" cellpadding="0" class="table_boder" align="center">
								<tbody>
									<tr>
										<td class="uu1" align="left" colspan="2"><b> <span id="ctl00_ContentPlaceHolder1_Label1">我的会员中心</span> </b></td>
									</tr>
								</tbody>
							</table></td>
					</tr>
				</tbody>
			</table>
			<div class="AntAccout_Center table_boder p10 mb10 center">
				<div class="AntAccout_Center_Warning">
					<span>系统登录信息：</span>
					<div>
						<ul id="AntAccountmq">
							<li></li>
							<li></li>
						</ul>
					</div>
				</div>
				<div class="AntAccout_Center_User" id="w726">
					<div class="AntAccout_Center_Left">
						<img id="ctl00_ContentPlaceHolder1_showImage" src="" style="height:90px;width:90px;border-width:0px;">
							<p>
								<span></span> <input id="Button4" type="button" value="选择" onclick="$('#ctl00_ContentPlaceHolder1_File1').click()"> <input type="submit" name="ctl00$ContentPlaceHolder1$Button2"
									value="修改" id="ctl00_ContentPlaceHolder1_Button2">
							</p>
					</div>

					<input type="file" name="ctl00$ContentPlaceHolder1$File1" id="ctl00_ContentPlaceHolder1_File1" class="cdaaassdf" onchange="$('#File2').val($('#File1').val());"> <input id="File2"
						type="file" class="cdaaassdf">
							<ul class="AntAccout_Center_Rithg">
								<li class="A" style="text-align: left">您好，<span id="ctl00_ContentPlaceHolder1_userlable"></span>：欢迎您！</li>
								<li class="B">您的用户等级：<span> <span id="ctl00_ContentPlaceHolder1_tag">个人</span>会员 </span> <input type="submit" name="ctl00$ContentPlaceHolder1$Button1" value="升级为商家"
									onclick="return confirm('您确认升级为企业会员！此操作是不可返的！');" id="ctl00_ContentPlaceHolder1_Button1">
								</li>
								<li class="C" style="text-align: left">注册时间：<span id="ctl00_ContentPlaceHolder1_addtime">12 4 2013 4:30PM</span>
								</li>
								<li class="D">邮箱：<span id="ctl00_ContentPlaceHolder1_uemail">${user.email }</span>
								</li>

								<li class="F" style="display: none;"><span class="Integral"> <a href="money/jf_add.aspx" target="_self">充值</a> </span></li>
							</ul>
				</div>




				<div class="clear"></div>

			</div>
		</div>
		<!-- 
		<div id="ctl00_ContentPlaceHolder1_geren" class="Air">
			<table class="w770" border="0" cellspacing="0" cellpadding="0" align="center">
				<tbody>
					<tr valign="top">
						<td>
							<table border="0" cellspacing="0" cellpadding="0" class="table_boder" align="center">
								<tbody>
									<tr>
										<td class="uu1" align="left" colspan="2"><b> <span id="ctl00_ContentPlaceHolder1_Label8">热点招聘信息</span> </b></td>
									</tr>
								</tbody>
							</table></td>
					</tr>
				</tbody>
			</table>
			<div class="AntAccout_Center table_boder mb10 center" style="padding: 10px 10px 0;
            width: 746px;">
				<ul class="hotbrand">

					<li><img src="${contextPath}/images/hot_icon.gif" align="absbottom"><a class="f14" href="/Comjoblist_56.html" target="_blank">慈溪易商网络信息技术有限公...</a><br> <img
								src="${contextPath}/images/bro_icon.gif" align="absbottom">
					</li>
					<li><img src="${contextPath}/images/hot_icon.gif" align="absbottom"><a class="f14" href="/Comjoblist_59.html" target="_blank">慈溪锐智网络信息技术有限公...</a><br> <img
								src="${contextPath}/images/bro_icon.gif" align="absbottom"> <a href="/Sejob_74.html" class="cBlock" title="客服人员 3名">客服人员</a>&nbsp;<a href="/Sejob_73.html" class="cBlock" title="销售代表 6名">销售代表</a>&nbsp;
							
					</li>
					<li><img src="${contextPath}/images/hot_icon.gif" align="absbottom"><a class="f14" href="/Comjoblist_60.html" target="_blank">中企动力科技股份有限公司慈...</a><br> <img
								src="${contextPath}/images/bro_icon.gif" align="absbottom"> <a href="/Sejob_81.html" class="cBlock" title="业务员">业务员</a>&nbsp;<a href="/Sejob_80.html" class="cBlock" title="电话营销">电话营销</a>&nbsp;<a
									href="/Sejob_79.html" class="cBlock" title="储备商务经理">储备商务</a>&nbsp; 
					</li>


				</ul>
			</div>
		</div>

		<div style="clear: both;"></div>
		 -->
	</div>

	<jsp:include page="../footer.jsp" />


</body>
</html>