<%@ page language="java" import="java.util.*" contentType="text/html"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh-cn">
<head>

	<title>短信上下文设置</title>
	<meta http-equiv="keywords" content="残疾人,就业,招聘">
	<meta http-equiv="description" content="残疾人就业招聘网站">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" type="text/css" href="${contextPath}/js/bootstrap/css/bootstrap.css" />
	<link rel="stylesheet" type="text/css" href="${contextPath}/js/bootstrap/css/bootstrap-combined.min.css" />
	<link rel="stylesheet" type="text/css" href="${contextPath}/js/bootstrap-switch-master/bootstrap-switch.css" />
	<link rel="stylesheet" type="text/css" href="${contextPath}/css/backdoor/main.css" />
	
	<script src="${contextPath}/js/lib/jquery-1.11.1.js"></script>
	<script src="${contextPath}/js/bootstrap/js/bootstrap.js"></script>
	<script src="${contextPath}/js/bootstrap-switch-master/bootstrap-switch.js"></script>
	<script src="${contextPath}/js/manage/common.js"></script>
	<script src="${contextPath}/js/manage/smsfix.js"></script>

</head>

<body>

	<!-- 整个页面div  开始 -->
	<div class="main-body">

		<!-- 头部 div -->
		<%@ include file="header.jsp"%>

		<!-- 中间主体div -->
		<div class="manage-body">

			<!-- 左侧菜单div -->
			<%@ include file="body-left.jsp"%>

			<!-- 右侧详细内容div  开始-->
			<div class="manage-body-right">
			
				<div class="smsfix">
					<div><h3>名商通账号密码设置:</h3></div>
					<div>
						<table>
							<tr>
								<td>账号:</td>
								<td><input type="text" id="smsusername" value="${smsAccount.username }"/></td>
								<td>密码:</td>
								<td><input type="password" id="smspassword" value="${smsAccount.password }"/></td>
								<td><input type="button" id="btnsmssave" value="保  存" onclick="savesmsaccount('${smsAccount.area.code}');"/></td>
							</tr>
						</table>
					</div>
					<div><h3>推送短信上下文设置:</h3></div>
					<div class="smsfix-edit">
						<span class="smsfix-contenttip">上文：</span><textarea id="prefix">${smsfix.prefix }</textarea>
						推送职位范例：<p id="middlecontent">1. 应收管理岗, 顺丰速运（集团）有限公司广西分公司, 广西壮族自治区, 2001-4000元 2. 出纳岗, 顺丰速运（集团）有限公司广西分公司, 广西壮族自治区, 2001-4000元 3. IQC/OQC检测员, 广西威利方舟科技有限公司, 广西壮族自治区, 2001-4000元 </p>
						<span class="smsfix-contenttip">下文：</span><textarea id="suffix">${smsfix.suffix }</textarea>
					</div>
					<div class="sms-middle">
						<input type="button" onclick="preview();" value="预览" />
						<br />
						<input type="button" onclick="savesmsfix('${smsfix.area.code}');"value="保存" />
					</div>
					<div class="smsfix-example" onselectstart="return false;"></div>
				</div>
			</div>
			<!-- 右侧详细内容div  结束-->




			<!-- 让body-right div的高度跟随内容自动变化 -->
			<div style="font: 0px/0px sans-serif;clear: both;display: block">
			</div>
		</div>

		<!-- 尾部div -->
		<%@ include file="footer.jsp"%>

	</div>
	<!-- 整个页面div  结束 -->
</body>
</html>
