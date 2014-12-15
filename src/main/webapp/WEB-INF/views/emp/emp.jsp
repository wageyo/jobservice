<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="keywords" content="残疾人招聘信息,残疾人就业信息,残疾人人才网,残疾人找工作" />
<meta content="残疾人招聘就业" name="description" />
<link href="${contextPath}/css/style_job.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/css/body_job.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/css/search.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/css/public.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${contextPath}/js/jquery.js"></script>
<title>残疾人就业信息网</title>
<script type="text/javascript">
	$(document).ready(function() {
		common.pagination(1);
		common.lineOnclick();
	});
	common = {};
	common.pagination = function(page) {
		var keyWord = $('#keyWord').val();
		var jcCode = $('#jobCategory').val();
		var education = $('#education').val();
	//	var areaCode = $('#areaCode').val();
		var jobNature = $('#jobNature').val();
		var gender = $('#gender').val();
		var url = '${contextPath}/resume/search/' + page;
		$.ajax({
			url : url,
			type : 'POST',
			data : {
				'keyWord' : keyWord,
				'jcCode' : jcCode,
				'education' : education,
		//		'areaCode' : areaCode,
				'jobNature' : jobNature,
				'gender' : gender,
			},
			success : function(e) {
				$('#main').html(e);
				$('#data').fadeIn();
			},
			dataType : 'html',
			async : false
		});
	};
	common.lineOnclick=function(){
		$("tr").click(function(){
			//alert("fewfew");
			//${contextPath }/resume/getOneForShow?id=${item.id}
		});
	}
</script>
<style type="text/css">
.list11:hover {
	cursor: pointer;
}
</style>
</head>
<body>
<div id="container" class="container">
	<jsp:include page="../formatter/status-bar.jsp" />
	<jsp:include page="../header.jsp" />


	<div id="xdAC">
		<div class="imga">
			<img src="${contextPath}/images/c2e34940b08940b48f3255b493b4ba68_zph_2013_ad_full.gif" alt="招聘频道" width="990" height="80" border='0'>
		</div>
		<div class="title4">简历搜索</div>
		<div class="xinxi">
			<div id="xdSearch2">
				<!--职业搜索-->
				<div style="width: 80px; height: 80px; float: left; margin: 5px 0 0 5px;">
					<img src="images/search11.gif">
				</div>
				<div class="sousuo2">
					<p>
						就职意向： 
						<select name="jobCategory" id="jobCategory" style="width: 270px">
							<c:forEach items="${jcList }" var="jc">
								<option value="${jc.code }">${jc.name }</option>
							</c:forEach>
						</select>
					</p>
					<p class="p1">
						学历： <select name="education" id="education" style="width: 100px">
							<c:forEach items="${params }" var="p">
								<c:if test="${p.type == 'education' }">
									<option value="${p.value }">${p.name }</option>
								</c:if>
							</c:forEach>
						</select>
					</p>
			<!-- 		<p>
						工作地区：
						<select name="areaCode" id="areaCode" style="width: 130px">
							<option value="">请选择</option>
							<c:forEach items="${areaList }" var="t">
								<option value="${t.code }"  <c:if test="${fn:substring(area.code,2,4) == fn:substring(t.code,2,4) }">selected="selected"</c:if>>${t.name }</option>
							</c:forEach>
						</select>
					</p> -->
					<p>
						就职方式： <select name="jobNature" id="jobNature" style="width: 100px">
							<c:forEach items="${params }" var="p">
								<c:if test="${p.type == 'jobNature' }">
									<option value="${p.value }">${p.name }</option>
								</c:if>
							</c:forEach>
						</select>
					</p>

					
					<p>
						性别： <select name="gender" id="gender" style="width: 80px">
							<c:forEach items="${params }" var="p">
								<c:if test="${p.type == 'gender' }">
									<option value="${p.value }">${p.name }</option>
								</c:if>
							</c:forEach>

						</select>
					</p>
					<p>
						关键词： <input name="keyWord" type="text" id="keyWord" style="width: 140px">
					</p>
					<p>
						<input type="image" name="ImageButton1" id="ImageButton1" onclick="common.pagination(1)" class="send55" src="images/search_but.gif" style="border-width:0px;">
					</p>
				</div>
			</div>
			<div style="padding-left: 250px; line-height: 35px; background-image: url(images/zgz_r2_c2.jpg)">
				<table style="width: 100%;">
					<tbody>
						<tr>
							<td>&nbsp;&nbsp;&nbsp;&nbsp;共有：<span id="Label8" style="color:Red;">${totalCount }</span>份人才简历供您选择 
							<c:if test="${user == null }">
								<a href="${contextPath}/regP" style="color: #f00;">我也要加入</a>
							</c:if>
							</td>
							<td></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<div style="clear: both;"></div>
		<div class="xBandEnterpriseH" style="background-image: url(images/menu_bg_r16_c7.jpg);
                border-left: 1px solid #AACCEF; border-right: 1px solid #AACCEF;">
			<div class="xBandEnterpriseHRight"></div>
			<span style="margin-left: 10px;">最新更新的招聘信息</span>
		</div>
		<div class="xBandEnterpriseK">
			<div id="ctl00_ctl00_cph_cph_div_NewPosts" class="NewPosts">
				<div class="job_list">

					<table width="990px" border="0" cellspacing="0" cellpadding="0" style="font-weight: bold;">
						<tr style="background-color: #ECF4FF; text-align: center">
							<td width="20" height="30">&nbsp;</td>
							<td width="70px" class="s13_blue_b" style="color: #345478">简历标题</td>
							<td width="70px" class="s13_blue_b" style="color: #345478">姓名</td>
							<td width="50px" class="s13_blue_b" style="color: #345478;">性别</td>
							<td width="80px" class="s13_blue_b" style="color: #345478">学历</td>
							<td width="70px" class="s13_blue_b" style="color: #345478">专业</td>
							<td width="80px" class="s13_blue_b" style="color: #345478;">工作经验</td>
							<td width="100px" class="s13_blue_b" style="color: #345478">求职岗位</td>
							<td width="80px" class="s13_blue_b" style="color: #345478">期望工作地</td>
							<td width="120px" class="s13_blue_b" style="color: #345478">期望薪资</td>
						</tr>
					</table>
					<div id="main"></div>
				</div>
			</div>
		</div>
		<jsp:include page="../footer.jsp" />
		</div>
</body>
</html>