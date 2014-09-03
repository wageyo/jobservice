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
<!-- 管理 通用样式 -->
<link href="${contextPath}/css/manage/main.css" rel="stylesheet" type="text/css" />

<link rel="stylesheet" type="text/css" href="${contextPath}/js/lib/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="${contextPath}/js/lib/themes/icon.css" />

<script type="text/javascript" src="${contextPath}/js/jquery.js"></script>
<script type="text/javascript" src="${contextPath}/js/lib/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/lib/easyui-lang-zh_CN.js"></script>

<script type="text/javascript" src="${contextPath}/js/manage/main.js"></script>
<script type="text/javascript" src="${contextPath}/js/manage/common.js"></script>
<script type="text/javascript">

</script>

<title>残疾人招聘就业后台</title>
</head>

<body class="easyui-layout">

	<!-- 上部分 -->
	<div data-options="region:'north',border:false"  style="height:52px;background:#33ACE4;background-image: url(${contextPath}/images/manager/topl.png); background-repeat:no-repeat;width: 1000px; height: 52px;">
	
			<div style="float:left;">
					<span style="font-size: 30px;margin-left: 20px;color: #FFF;font-family: '黑体'">
						${titleText}
					</span>
					
			</div>
			<div style="float: right;">
				<a href="/jobservice/manage/quit" >
						<img src="${contextPath}/images/manager/exit.png"  style="width:80px;height:40px;margin-right: 10px;margin-top: 5px" alt="退出" title="退出">
						</img>
				</a>
			</div>
	
	</div>

	<!-- 左侧菜单 -->
	<div data-options="region:'west',split:true,title:'目录'" style="width:260px;padding:10px;">


		<!-- 主菜单 -->
		<ul id="main_menu"  class="easyui-tree"  data-options="url:'/jobservice/manage/menu'">   
				 
		</ul>

	</div>


	<!-- 右侧隐藏栏 
	<div data-options="region:'east',split:true,collapsed:true,title:'帮助'" style="width:100px;padding:10px;">east region</div>
	-->
	<!-- 底部 -->
	<div data-options="region:'south',border:true" class="manager-botton" style="height: 27px">
		<div>技术支持： 哈尔滨亿时代数码科技开发有限公司</div>
	</div>

	<!-- 右侧主体部分 -->
	<div data-options="region:'center'">
		<div id="tt" class="easyui-tabs" data-options="tools:'#tab-tools',fit:true,border:false"></div>
	</div>
	<div id="tab-tools">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'" onclick="view.tabs.removeAllTab()"></a>
	</div>
</body>
</html>