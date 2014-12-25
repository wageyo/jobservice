<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
	<script type="text/javascript">
		function searchObj(page){
			$('#page').val(page);
			$('#searchObj').submit();
		}
	</script>
</head>
<body>
	<jsp:include page="../formatter/status-bar.jsp" />
	<jsp:include page="../formatter/header.jsp" />
	<div id="maincontent">
		<div style="margin-top: 10px;">
			<form action="${contextPath }/work" id="searchObj">
				<div id="leftsidebar" style="width: 989px; ">
					<div class="s_search">
						<div class="Search">
							<div class="SearchInputText"
								style="margin-top: 20px;margin-left: 50px;">
								<div class="SetFloatLeft InputTextBgEmp">
									<input name="keyWord" type="text" value="${keyWord }" id="keyWord" title="请输入关键字" class="InputTextBlank DefaultText" style="color: gray;" />
									<input type="hidden" id="page" name="page" />
								</div>
								<div class="SearchBtn SetFloatLeft">
									<input class="SearchButtom SetFloatLeft" type="image" name="btn-search" id="btn-search" onclick="searchObj(1);"
										src="images/HomePageImage/SearchImage/SearchBtn.gif" style="border-width:0px;" />
									<div class="SetFloatLeft AdvancedSearch"></div>
								</div>
							</div>
						</div>
						<div class="pot"></div>
						<ul class="list_condition" style="padding-left: 120px;">
							<li class="list_selt" style=" height:30px;">
								<div>
									<div style="float:left;">工作类别：</div>
									<div class="PublicButtomBar">
										<select name="jobCategory" id="jobCategory" class="dropdownlist" style="width: 170px;height:23px">
											<c:forEach items="${jcList }" var="jc">
												<option value="${jc.code }" <c:if test="${jcCode == jc.code }">selected=selected</c:if>>${jc.name }</option>
											</c:forEach>
										</select>
									</div>
								</div></li>
							<li class="list_selt" style=" height:30px;">
								<div>
									<div style="float:left;">
										学&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;历：</div>
									<div class="PublicButtomBar">
										<select name="education" id="education" class="dropdownlist" style="width: 150px;height:23px">
											<c:forEach items="${params }" var="p">
												<c:if test="${p.type == 'education' }">
													<option value="${p.value }" <c:if test="${education == p.value }">selected=selected</c:if>>${p.name }</option>
												</c:if>
											</c:forEach>
										</select>
									</div>
								</div></li>
							<li class="list_selt" style=" height:30px;">
								<div>
									<div style="float:left;">工作性质：</div>
									<div class="PublicButtomBar">
										<select name="jobNature" id="jobNature" class="dropdownlist" style="width: 150px;height:23px">
											<c:forEach items="${params }" var="p">
												<c:if test="${p.type == 'jobNature' }">
													<option value="${p.value }"  <c:if test="${jobNature == p.value }">selected=selected</c:if>>${p.name }</option>
												</c:if>
											</c:forEach>
										</select>
									</div>
								</div></li>
						</ul>
						<div class="clearboth"></div>
					</div>
				</div>
			</form>
			<div class="advertisement"></div>
		</div>
		<div class="clearboth"></div>
		<div id="contentright" class="contentright">
			<div style="clear: both"></div>
			<div class="zxzw_center">
				<div class="job_list">
					<table width="988px" border="0" cellspacing="0" cellpadding="0"
						style="font-weight: bold;">
						<tr style="background-color: #ECF4FF; text-align: center; background-image: url(images/HomePageImage/SearchImage/center_1px.gif);background-repeat: repeat-x;">
							<td height="26" width="200px" class="s13_blue_b" id="tableHeadStyle">招聘职位</td>
							<td width="203px" class="s13_blue_b" id="tableHeadStyle">公司名称</td>
							<td width="122px" class="s13_blue_b" id="tableHeadStyle">工作地区</td>
							<td width="145px" class="s13_blue_b" id="tableHeadStyle">经验要求</td>
							<td width="150px" class="s13_blue_b" id="tableEndStyle">更新时间</td>
						</tr>
					</table>
					<div id="main">
						<div id="data">
							<!-- 上部显示列表信息  begin -->
							<div class="list11">
								<table width="990px" border="0" cellspacing="0" cellpadding="0">
									<tbody>
										<c:choose>
											<c:when test="${list == null || fn:length(list) == 0}">
												<tr>
													<td colspan="5">
														没有查到您所需要的信息.
													</td>
												</tr>
											</c:when>
											<c:otherwise>
												<c:forEach items="${list }" var="obj" varStatus="status">
													<tr class="list11" <c:if test="${status.index % 2 == 1 }">style="background:#f6f6f6;"</c:if>>
														<td width="200px" class="s13_blue_b" id="tabaleDataStyle">
															<a href="/jobservice/job/getOneForShow?id=${obj.id }"> 
																<span id="Repeater1_ctl00_Label1" style="color: #0868C8;"> ${obj.name } </span> 
															</a>
														</td>
														<td width="203px" class="s13_blue_b" id="tabaleDataStyle">
															<a href="/jobservice/company/getOneForShow?id=${obj.companyid }"> 
																<span id="Repeater1_ctl00_Label2"> ${obj.company } </span> 
															</a>
														</td>
														<td width="122px" class="s13_blue_b" id="tabaleDataStyle">
															<span id="Repeater1_ctl00_Label3">${obj.area }</span>
														</td>
														<td width="145px" class="s13_blue_b" id="tabaleDataStyle">
															<span id="Repeater1_ctl00_Label6">${obj.experience }</span>
														</td>
														<td width="150px" class="s13_blue_b" id="tabaleDataStyle">
															<span id="Repeater1_ctl00_Label7">${obj.date }</span>
														</td>
													</tr>
												</c:forEach>
											</c:otherwise>
										</c:choose>
									</tbody>
								</table>
							</div>
							<!-- 上部显示列表信息  end -->
							
							<!-- 下部显示页码数  begin -->
							<div style="padding:15px;" class="pages0">
								<span id="lbCount" class="total">共${pagination.records}条记录</span> 
								<span id="lbTotalPage" class="total">总页:${pagination.countPages}页</span> 
								<span id="lbCurPage" class="total">当前页:第${pagination.currentPage}页</span> 
								<c:if test="${pagination.currentPage==1 }">
									<a id="LBUP"  class="total" href="javascript:searchObj(1);">上页</a>
								</c:if>
								<c:if test="${pagination.currentPage>1 }">
									<a id="LBUP"  class="total" href="javascript:searchObj(${pagination.currentPage-1 });">上页</a>
								</c:if>
								<c:forEach begin="${pagination.start }" end="${pagination.end }" var="p">
									<c:if test="${pagination.currentPage==p}">
										<a id="LB${p}" class="current" href="javascript:searchObj(${p});">${p}</a>
									</c:if>
									<c:if test="${pagination.currentPage!=p}">
											<a id="LB${p}" href="javascript:searchObj(${p});">${p}</a>
									</c:if>
								</c:forEach>
								<c:if test="${pagination.currentPage<pagination.countPages}">
									<a id="LBNEXT" class="total" href="javascript:searchObj(${pagination.currentPage+1 });">下页</a>
								</c:if>
								<c:if test="${pagination.currentPage==pagination.countPages }">
									<a id="LBNEXT" class="total" href="javascript:searchObj(${pagination.countPages });">下页</a>
								</c:if>
								<span class="total">到第 
									<select name="LbDPG" onchange="javascript:searchObj(this.selectedIndex+1)" id="LbDPG">
										<c:forEach begin="1" end="${pagination.countPages }" var="p">
											<option <c:if test="${pagination.currentPage==p}">selected="selected"</c:if> value="${p}">${p}</option>
										</c:forEach>
								</select> 页 </span>
							</div>
							<!-- 下部显示页码数  end -->
							
						</div>
					</div>
				</div>
			</div>
			<jsp:include page="../formatter/footer.jsp" />
		</div>
	</div>




	<%--

	<div id="xdAC">
	
		<div class="title4">职位搜索</div>
		<div class="xinxi">
			<div id="xdSearch2">
				<!--职业搜索-->
				<div style="width: 80px; height: 80px; float: left; margin: 5px 0 0 5px;">
					<img src="images/search11.gif" />
				</div>
				<div class="sousuo2">

					<p>
						工作类别： <select name="jobCategory" id="jobCategory" class="dropdownlist" style="width: 260px;height:23px">
							<c:forEach items="${jcList }" var="jc">
								<option value="${jc.code }">${jc.name }</option>
							</c:forEach>
						</select>
					</p>
					<p>
						学历要求： <select name="education" id="education" class="dropdownlist" style="width: 65px;height:23px">
							<c:forEach items="${params }" var="p">
								<c:if test="${p.type == 'education' }">
									<option value="${p.value }">${p.name }</option>
								</c:if>
							</c:forEach>
						</select>
					</p>
			<!-- 		<p>
						工作地点：
						<select name="areaCode" id="areaCode" class="dropdownlist" style="width: 130px; margin-right:100px;">
							<option value="">请选择</option>
							<c:forEach items="${areaList }" var="t">
								<option value="${t.code }" <c:if test="${fn:substring(area.code,2,4) == fn:substring(t.code,2,4) }">selected="selected"</c:if>>${t.name }</option>
							</c:forEach>
						</select>
					</p> -->
					<p>
						工作性质： <select name="jobNature" id="jobNature" class="dropdownlist" style="width: 100px;height:23px">
							<c:forEach items="${params }" var="p">
								<c:if test="${p.type == 'jobNature' }">
									<option value="${p.value }">${p.name }</option>
								</c:if>
							</c:forEach>

						</select>
					</p>
					<p>
						关键词： <input name="keyWord" id="keyWord" type="text" id="TextBox1" style="width: 140px;height:19px" />
					</p>
					<p>
						<input type="image" name="btn-search" id="btn-search" onclick="common.pagination(1)" src="images/HomePageImage/SearchImage/SearchBtn.gif" style="border-width:0px;" />
					</p>
				</div>
			</div>
			<div style="padding-left: 250px; line-height: 35px; background-image: url(images/zgz_r2_c2.jpg)">
				<table style="width: 100%;">
					<tr>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;共有：<span id="Label8" style="color:Red;">${totalCount }</span>个工作岗位供您选择
						<c:if test="${cookie.companyid.value == null || cookie.companyid.value == ''}">
							<a href="${contextPath}/regC" style="color: #f00;">我也要发布</a>
						</c:if>
						</td>
						<td></td>
					</tr>
				</table>
			</div>
		</div>
		<div style="clear: both;"></div>
		<div class="xBandEnterpriseH" style="background-image: url(images/PublicImage/PublicframeOneImage/PublicframeOneHeaderBar.jpg); repeat-x">
			<div class="xBandEnterpriseHRight"></div>
			<span style="margin-left: 10px;">最新更新的招聘信息</span>
		</div>
		<div class="xBandEnterpriseK">
			<div id="ctl00_ctl00_cph_cph_div_NewPosts" class="NewPosts">
				<div class="job_list">

					<table width="988px" border="0" cellspacing="0" cellpadding="0" style="font-weight: bold;">
						<tr style="background-color: #ECF4FF;">
							<td width="29" height="30">&nbsp;</td>
							<td height="30" width="200px" class="s13_blue_b"  id="tableHeadStyle">招聘职位</td>
							<td width="203px"  class="s13_blue_b" id="tableHeadStyle">公司名称</td>
							<td width="122px"  class="s13_blue_b" id="tableHeadStyle">工作地区</td>
							<td width="145px" class="s13_blue_b" id="tableHeadStyle">经验要求</td>
							<td width="150px" class="s13_blue_b" id="tableEndStyle" >更新时间</td>
						</tr>
					</table>
					<div id="main"></div>
				</div>
			</div>
		</div>
		
			<jsp:include page="../formatter/footer.jsp" />
			</div>
--%>
</body>
</html>