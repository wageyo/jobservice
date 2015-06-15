<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>残疾人就业信息网</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="keywords" content="残疾人招聘信息,残疾人就业信息,残疾人人才网,残疾人找工作" />
	<meta content="残疾人招聘就业" name="description" />
	
	<link rel="shortcut icon" href="${contextPath}/images/HomePageImage/favicon.ico" type="image/x-icon" />
	<link href="${contextPath}/css/Public.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/HomePageHeader.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/SetHeaderStyle.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/PublicStatusBar.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/Login.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/VerticalLineUL.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/PublicSearch.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/PublicframeOne.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/PublicframeTwo.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/hp_fwdt.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/HomePageCSS.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/tabCSS.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/HomePageFooter.css" rel="stylesheet" type="text/css" />
	
	<script type="text/javascript" src="${contextPath}/js/jquery.js"></script>
	<script type="text/javascript" src="${contextPath}/js/common.js"></script>
	<script type="text/javascript" src="${contextPath}/js/verify.js"></script>
	<script type="text/javascript" src="${contextPath}/js/easyTab.js"></script>
	<script type="text/javascript" src="${contextPath}/js/index.js"></script>
	<script type="text/javascript" src="${contextPath}/js/potoschange.js"></script>
	<script type="text/javascript">
		function getimgcode(){
			var timestamp=new Date().getTime();
			$("#verifyCode").attr("src","${contextPath}/checkcode/create?"+timestamp);
		}
	</script>
</head>
<body>
	<div id="container" class="container">
	
		<!-- 顶部工具条栏 -->
		<jsp:include page="formatter/status-bar.jsp" />
		
		<!-- 头部导航及图片栏目 -->
		<jsp:include page="formatter/header.jsp" />
		<div class="clearboth"></div>
		
		<!-- ******* 中部内容显示区 ******* start ********** -->
		<div id="content" class="content">
			<div>
			
				<!-- 登陆前的登陆框  begin -->
				<c:if test="${cookie.userid.value == null || cookie.userid.value == ''}">
					<div class="login SetFloatLeft">
						<div class="loginbtn">
							<div title="个人用户登录" id="PersonalTab" class="BlueFirst" style="cursor: auto;"></div>
							<div title="企业用户登录" id="BusinessTab" class="WhiteLast" style="cursor: auto;"></div>
						</div>
						<div class="loginmain">
							<div class="loginmainarae">
								<form action="${contextPath }/user/login" method="post" onsubmit="return check();">
									<div class="divtext">
										<div>
											<input type="text" value="请输入用户名或残疾证号" id="loginName" name="loginName" class="text DefaultText" title="请输入用户名或残疾证号" style="color: Gray;" />
										</div>
										<div class="righttext">
											<input type="password" value="" id="passWord" name="passWord" class="text DefaultText" title="输入密码" style="color: Gray;" />
											<input type="hidden" id="identity" name="identity" value="person" />
										</div>
									</div>
									<div id="LoginBtn" class="loginbtnimg" style="cursor: pointer;"></div>
								</form>
								<div class="clearboth"></div>
								<div class="divchk">
									<div style="letter-spacing:4px;">
										<a href="${contextPath }/getBackPassWord" style="cursor:pointer;padding-right:20px;">找回密码</a>
										<%--<span title="自动登录" class="zdword">自动登录</span>
									--%>
									<a href="${contextPath }/getBackUserName" style="cursor: pointer;">找回用户</a></div>
								</div>
								<div class="clearboth"></div>
								<div class="registerdiv">
									<div title="注册个人会员" id="PersonalRegister" class="personalregister" 
									style="cursor: auto; background: url(${contextPath}/images/HomePageImage/LoginImage/PersonalRegister.gif);"></div>
									<div title="注册用人单位" id="BusinessRegister" class="businessregister" 
									style="cursor: auto; background: url(${contextPath}/images/HomePageImage/LoginImage/BusinessRegister.gif);"></div>
								</div>
								<div class="clearboth"></div>
							</div>
						</div>
						<div class="LoginRight"></div>
					</div>
				</c:if>
				<!-- 登陆前的登陆框  end -->
				
				<!-- 登陆后的状态栏  begin -->
				<c:if test="${cookie.userid.value != null && cookie.userid.value != ''}">
					<div class="SetFloatLeft PersonalLogined">
						<div class="loginbtn"></div>
						<div class="loginmain">
							<div class="loginmainarae">
								<div class="NameLay">
									<span class="SetFloatLeft"> 欢迎回来，</span>
									<span class="Strong SetFloatLeft">
										<a href="${contextPath }/user/goCenter">
											<span class="exceedHidden" style="display:-moz-inline-box;display:inline-block; width:110px;">${cookie.username.value }</span>
										</a>
									</span>
									<span class="SetFloatRight">
										<a href="${contextPath }/user/logout">[退出]</a>
									</span>
								</div>
								<div class="clearboth">
								</div>
								<!--  -->
									<div class="TimeShow"><!-- 您最近的登陆时间： --><span></span></div>
								<div>
									<!-- 企业用户显示的菜单项目 begin -->
									<c:if test="${cookie.identity.value == 'company' }">
										<div class="RapidEntry">
											<a href="${contextPath }/secure/company/update" title="企业信息">
												<div class="FirstImage"><span>&nbsp;</span></div>
											</a>
											<div>
												<a href="${contextPath }/secure/company/update">企业信息</a>
											</div>
										</div>
										<div class="RapidEntry">
											<a href="${contextPath }/secure/job/getManage?page=1">
												<div title="我发布的职位" class="SecendImage"><span>&nbsp;</span></div>
											</a>
											<div>
												<a href="${contextPath }/secure/job/getManage?page=1" title="职位管理">职位管理</a>
											</div>
										</div>
										<div class="RapidEntry">
											<a href="${contextPath }/secure/company/getAllGotResume/1">
												<div title="应聘简历" class="ThirdImage"><span>&nbsp;</span></div>
											</a>
											<div>
												<a href="${contextPath }/secure/company/getAllGotResume/1" title="收到简历">收到简历</a>
											</div>
										</div>
									</c:if>
									<!-- 企业用户显示的菜单项目 end -->
									<!-- 个人用户显示的菜单项目 begin -->
									<c:if test="${cookie.identity.value == 'person' }">
										<div class="RapidEntry">
											<a href="${contextPath }/secure/resume/getManage" title="我的简历">
												<div class="FirstImage"><span>&nbsp;</span></div>
											</a>
											<div>
												<a href="${contextPath }/secure/resume/getManage">我的简历</a>
											</div>
										</div>
										<div class="RapidEntry">
											<a href="${contextPath }/secure/resume/getReceivedInvite/1">
												<div title="我发布的职位" class="ThirdImage"><span>&nbsp;</span></div>
											</a>
											<div>
												<a href="${contextPath }/secure/resume/getReceivedInvite/1" title="收到邀请">收到邀请</a>
											</div>
										</div>
										<div class="RapidEntry">
											<a href="${contextPath }/secure/user/passWordEdit">
												<div title="应聘简历" class="SecendImage"><span>&nbsp;</span></div>
											</a>
											<div>
												<a href="${contextPath }/secure/user/passWordEdit" title="修改密码">修改密码</a>
											</div>
										</div>
									</c:if>
									<!-- 个人用户显示的菜单项目 end -->
									<!-- 管理员用户显示的菜单项目 begin -->
									<c:if test="${cookie.identity.value == 'admin' || cookie.identity.value == 'superadmin' }">
										<div class="RapidEntry">
											<a href="${contextPath }/manage/index" title="管理后台">
												<div class="FirstImage"><span>&nbsp;</span></div>
											</a>
											<div>
												<a href="${contextPath }/manage/index">管理后台</a>
											</div>
										</div>
									</c:if>
									<!-- 管理员用户显示的菜单项目 end -->
								</div>
							</div>
						</div>
						
						<div class="LoginRight"></div>
					</div>
				</c:if>
				<!-- 登陆后的状态栏   end -->
				
				<div class="GradualChangeBg SetFloatRight">
					<div class="SetFloatLeft left"></div>
					<div class="SetFloatLeft center">
						<div class="main">
							<div class="Search">
								<div class="SearchTab">
									<ul>
										<li class="SearchTextBlueBg" title="职位名" style="cursor: auto;">职位名</li>
									<!-- 	<li class="SearchImg">|</li>
										<li class="SearchTextWhiteBg" title="单位名" style="cursor: auto;">单位名</li>
									 -->
									</ul>
								</div>
								<div class="clearboth"></div>
								<form action="${contextPath }/work" id="searchObj">
									<div class="SearchInputText">
										<div class="SetFloatLeft InputTextBg">
											<input id="keyWord" name="keyWord" class="InputTextBlank DefaultText" type="text" value="请输入职位关键字" title="请输入职位关键字" style="color: Gray;" />
										</div>
										<div class="SearchBtn SetFloatLeft">
										<!-- 	<div id="areaBtn" class="SearchDistrictBtn SetFloatLeft" title="选择地区" style="cursor: auto;"> <span>选择地区</span> </div> -->
											<div class="SearchButtom SetFloatLeft" title="搜索" id="SearchButton" style="cursor: auto;"></div>
										<!-- 	<div class="SetFloatLeft AdvancedSearch"> <a title="高级搜索" href="HP_AdvancedSearch.aspx">高级搜索</a> </div> -->
										</div>
									</div>
								</form>
							</div>
							<div style="*padding-left:6px;">
								<div style=" height:20px;">
									<div class="SetFloatLeft">下辖地区：</div>
									<div>
										<!-- 当前网站部署所在地区code -->
										<c:choose>
											<c:when test="${deployAreaCode != null && deployAreaCode != '' }">
												<c:set var="deployAreaCode">${deployAreaCode }</c:set>
											</c:when>
											<c:otherwise>
												<c:set var="deployAreaCode">${cookie.deployAreaCode.value }></c:set>
											</c:otherwise>
										</c:choose>
										<!-- 当前地区code -->
										<c:choose>
											<c:when test="${areaname != null && areaname != '' }">
												<c:set var="currentlocation">${areacode}</c:set>
											</c:when>
											<c:otherwise>
												<c:set var="currentlocation">${cookie.areacode.value }</c:set>
											</c:otherwise>
										</c:choose>
										<!-- 有当前地区code得到上一级目录 地区code-->
										<c:choose>
											<c:when test="${currentlocation == '10000000'}">
												<c:set var="uplevellocation"></c:set>
											</c:when>
											<c:when test="${fn:startsWith(currentlocation,'10')}">
												<c:set var="uplevellocation">10000000</c:set>
											</c:when>
											<c:when test="${fn:startsWith(currentlocation,'20')}">
												<c:set var="uplevellocation">10${fn:substring(currentlocation,2,4) }0000</c:set>
											</c:when>
											<c:when test="${fn:startsWith(currentlocation,'30')}">
												<c:set var="uplevellocation">20${fn:substring(currentlocation,2,6) }00</c:set>
											</c:when>
											<c:otherwise>
												<c:set var="uplevellocation">10000000</c:set>
											</c:otherwise>
										</c:choose>
										<ul class="VerticalLineUL">
											<!-- 数组总长度 -->
											<c:set var="subAreaListLength">${fn:length(subAreaList) }</c:set>
											<c:forEach items="${subAreaList }" var="subArea" varStatus="status">
												<li> <a title="${subArea.name }" href="${contextPath }/index?acode=${subArea.code}" style="color: rgb(32, 164, 254);">${subArea.name }</a> </li>
												<!-- 最后一个 | 不显示 -->
												<c:if test="${status.index + 1 < subAreaListLength }">
													<li class="LiEven">|</li>
												</c:if>
											</c:forEach>
											<!-- *************************  广西地区限定, 不可以上到上级全国.   *************************** -->
											<c:if test="${uplevellocation >= deployAreaCode }">
												<li class="LiEven">|</li>
												<li> <a title="返回上级地区" href="${contextPath }/index?acode=${uplevellocation}" style="color: rgb(18, 0, 223);">返回上级地区</a> </li>
											</c:if>
										</ul>
									</div>
								</div>
								<div class="clearboth"></div>
							</div>
						</div>
					</div>
					<div class="SetFloatLeft right"></div>
				</div>
				<div class="clearboth"></div>
				<div class="ShadowBg SetFloatRight" style="width: 964px;"></div>
				<div class="clearboth"> </div>
				<div class="PublicframeOne SetMarginTop hp_fwdt" style=" width:707px; float:left; height:255px;">
					<div class="PublicframeOneHeaderBar" style=" width:707px">
						<div class="PublicframeOneHeaderBarLeft">最新资讯</div>
						<div class="PublicframeOneHeaderBarRight"> <a  href="${contextPath}/news">更多</a> </div>
					</div>
					<!-- 图片轮换div -->
					<div class="photo">
						<dl id="scrollimg2" class="tagon3">
							<c:forEach items="${imagesList }" var="n" varStatus="status">
								<dt class="pic${status.index + 1 }"> <a title="${n.title }"  href="${contextPath }/news/getOneForShow?id=${n.id}"> <img title="${n.title }" alt="${n.title }" style=" width:255px; height:208px; " src="${contextPath }/filegags/downloadFile/${n.imageId}" /> </a> </dt>
								<dd class="tag${status.index + 1 }"> <a onfocus="imgScroll(${status.index + 1 })" onmousemove="imgScroll(${status.index + 1 })"  title="${n.title }" href="">${status.index + 1 }</a> </dd>
							</c:forEach>
						</dl>
					</div>
					<div class="hp_fwdt">
						<ul>
							<c:forEach items="${newsList }" var="news">
								<li class="Square2"> 
									<a href="${contextPath }/news/getOneForShow?id=${news.id}" title="${news.title }">${news.title }</a>
								</li>
							</c:forEach>
						
						</ul><div class="clearboth"></div>
					</div>
				</div>
				<div class="PublicframeOne SetMarginTop hp_fwxx" style=" width:274px; height:255px; float:left; margin-left:5px;">
					<div style=" width:274px" class="PublicframeOneHeaderBar">
						<div class="PublicframeOneHeaderBarLeft">就业指导</div>
						<div class="PublicframeOneHeaderBarRight"> <a  href="${contextPath}/direct">更多</a> </div>
					</div>
					<div >
						<ul>
							<c:forEach items="${directList }" var="direct">
								<li class="Square" > 
									<div class="exceedHidden" style="width:230px;">
										<a  title="最新职业能力测评" href="${contextPath }/news/getOneForShow?id=${direct.id}" title="${direct.title }">${direct.title }</a>
									</div> 
								</li>
							</c:forEach>
						</ul>
						<div class="clearboth"></div>
					</div>
				</div>
				<div class="clearboth"></div>
				<div class="SetMarginTop">
					<div class="PublicframeTwo SetPositionOne" style="width:350px;">
						<div class="PublicframeTwoHeadBar" style="width:350px;">
							<div class="PublicframeTwoHeadBarLeft"></div>
							<div class="PublicframeTwoHeadBarTittle"> <span class="PublicframeTwoHeadBarTittleSpan" title="个人求职信息">个人求职信息</span> </div>
							<div class="PublicframeTwoHeadBarRight"></div>
							<div class="PublicframeTwoHeadBarMore"></div>
						</div>
						<div class="PublicframeTwoContent listpub" style="width:348px;height:307px;">
							<table class="resumeMessage">
								<tbody>
									<tr class="resumeMessageheaderOne">
										<th>姓名</th>
										<th>学历</th>
										<th>残疾类别</th>
										<th>残疾等级</th>
										<th>求职岗位</th>
									</tr>
									<c:forEach items="${resumeList }" var="resume">
										<tr class="resumeMessageheaderOther">
											<td>
												<div style="white-space:nowrap; width:50px; text-overflow:ellipsis;-moz-text-overflow: ellipsis; overflow:hidden">
													<a href="${contextPath }/resume/getOneForShow?id=${resume.id}" style="color: #0868C8;">${resume.name }</a>
												</div>
											</td>
											<td>${resume.education }</td>
											<td>${resume.disabilityCategory }</td>
											<td>${resume.disabilityLevel }</td>
											<td>
												<div class="exceedHidden" style="width:100px;">${resume.desireJob.name }</div>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<div class="clearboth"></div>
						</div>
					</div>
					<div class="PublicframeTwo SetPositionOne SetMarginLeft" style="width:350px;margin-left:8px;">
						<div class="PublicframeTwoHeadBar" style="width:350px;">
							<div class="PublicframeTwoHeadBarLeft"></div>
							<div class="PublicframeTwoHeadBarTittle"> <span class="PublicframeTwoHeadBarTittleSpan" title="用人单位聘信息">用人单位招聘信息</span> </div>
							<div class="PublicframeTwoHeadBarRight"></div>
							<div class="PublicframeTwoHeadBarMore"></div>
						</div>
						<div class="PublicframeTwoContent listpub" style="width:348px;height:307px;">
							<table class="resumeMessage">
								<tbody>
									<tr class="resumeMessageheaderOne">
										<th>用人单位名称</th>
										<th>所在行业</th>
										<th>日期</th>
									</tr>
									<c:forEach items="${companyList }" var="company">
										<tr class="resumeMessageheaderOther">
											<td>
												<div class="exceedHidden" style="width:130px;">
													<a  href="${contextPath }/company/getOneForShow?id=${company.id}" title="${company.name }" style="color: #0868C8;">${company.name }</a>
												</div>
											</td>
											<td>
												<div class="exceedHidden" style="width:100px;">
													<a  href="" title="${company.businessScope.name}">${company.businessScope.name}</a>
												</div>
											</td>
											<td><fmt:formatDate value="${company.updateDate }" type="both" pattern="yyyy/MM/dd" /></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<div class="clearboth"></div>
						</div>
					</div>
					<div class="PublicframeTwo SetPositionOne SetMarginLeft" style="width:277px;margin-left:5px;">
						<div class="PublicframeTwoHeadBar" style="width:277px;">
							<div class="PublicframeTwoHeadBarLeft"></div>
							<div class="PublicframeTwoHeadBarTittle"> <span class="PublicframeTwoHeadBarTittleSpan" title="职业培训">职业培训</span> </div>
							<div class="PublicframeTwoHeadBarRight"></div>
							<div class="PublicframeTwoHeadBarMore"></div>
						</div>
						<div class="PublicframeTwoContent listpub" style="width:275px; height:307px;">
							<div>
								<ul>
									<c:forEach items="${directList }" var="direct">
										<li class="Square" > 
											<div class="exceedHidden" style="width:230px;">
											<a  title="最新职业能力测评" href="${contextPath }/news/getOneForShow?id=${direct.id}" title="${direct.title }">${direct.title }</a>
											</div> 
										</li>
									</c:forEach>								
<%--									<li class="Square"> <a  title="密切注意职场成长中的10个主要障碍" href="">密切注意职场成长中的10个主要障碍</a> </li>--%>
<%--									<li class="Square"> <a  title="传说中最好的文章：如何择业、跳槽、成..." href="">传说中最好的文章：如何择业、跳槽、成...</a> </li>--%>
<%--									<li class="Square"> <a  title="职业规划：人生一堂必修课" href="">职业规划：人生一堂必修课</a> </li>--%>
<%--									<li class="Square"> <a  title="职场规划为了什么" href="">职场规划为了什么</a> </li>--%>
								</ul>
								<!-- 
								<a href="" >
									<div id="btn_Link" class="LinkButton"></div>
								</a>
								 -->
							</div>
							<div style="padding-top:30px;">
<%--								<a href="${contextPath }/loginP" >--%>
<%--									<div id="btn_Link1" class="LinkButton1"></div>--%>
<%--								</a>--%>
							</div>
							<div class="clearboth"></div>
						</div>
					</div>
					<div class="clearboth"></div>
				</div>
				<div class="clearboth"></div>
				<div class="clearboth"></div>
				<div class="PublicframeOne SetMarginTop">
					<div class="PublicframeOneHeaderBar">
						<div class="PublicframeOneHeaderBarLeft"> 热点招聘</div>
					</div>
					<div id="List_rdzp">
						<ul>
							<c:forEach items="${hotJobList }" var="job">
								<li>
									<a href="${contextPath }/company/getOneForShow?id=${job.company.id}" title="${job.company.name }">${job.company.name }</a> 
									<a href="${contextPath }/job/getOneForShow?id=${job.id}" class="position" title="${job.name }"  style="color:#0868C8">${job.name }</a>
								</li>
							</c:forEach>
						</ul>
						<div class="clearboth"></div>
					</div>
				</div>
				<div id="List_Tab" style="margin-top: 10px;">
					<div class="clearboth"></div>
					<ul class="TabCss">
						<c:forEach items="${jobCategoryList }" var="jobCategory" varStatus="index">
							<li> 
								<span
									<c:if test="${index.index == 0}">class="firstWhite"</c:if>
									<c:if test="${index.index == 1}">class="rightWhiteOfCenter"</c:if>
									<c:if test="${index.index > 1}">class="rightWhiteOfCenter rightBlueOfCenter" </c:if>
								></span> 
								<span 
									<c:if test="${index.index == 0}">class="whitebg"</c:if>
									<c:if test="${index.index > 0}">class="whitebg bluebg" </c:if>
									onclick="changeShowJob('${jobCategory.code}',this);"
								>${jobCategory.name }</span> 
							</li>
						</c:forEach>
					</ul>
					<div class="clearboth"></div>
					<div id="List_Tab_Content" class="TabContentStyle">
						<div id="TabsMainShow">
							<ul>
							<!-- 一个公司显示多个职位的时候用下面这个样式 -->
							<!-- 	<li class=""><a  href="">肇庆华锋电子铝箔股份有限公司</a><a href="" style="color:#0868C8" class="position">后勤门卫</a><a href="" style="color:#0868C8" class="position">仓库管理员</a></li> -->
							<c:forEach items="${jobByCategoryList }" var="job">
								<li class="">
									<a href="${contextPath }/company/getOneForShow?id=${job.companyId}">${job.companyName }</a>
									<a href="${contextPath }/job/getOneForShow?id=${job.jobId}" style="color:#0868C8" class="position">${job.jobName }</a>
								</li>
							</c:forEach>
							</ul>
						</div>
						<div class="clearboth"></div>
					</div>
					<div class="clearboth"></div>
				</div>
				<div class="clearboth"></div>
				<div class="SetMarginTop">
					<!-- 临时显示相关链接 -->
					<div class="PublicframeTwo SetPositionOne" style="width:100%;">
						<div class="PublicframeTwoHeadBar">
							<div class="PublicframeTwoHeadBarLeft"></div>
							<div class="PublicframeTwoHeadBarTittle">
								<span class="PublicframeTwoHeadBarTittleSpan" title="友情链接">友情链接</span>
							</div>
							<div class="PublicframeTwoHeadBarRight"></div>
							<div class="PublicframeTwoHeadBarMore">
							</div>
						</div>
						<div class="PublicframeTwoContent listpub" style="width:988px;">
							<!-- 引入相关链接列表页面 -->
							<jsp:include page="formatter/links.jsp" />
							<div class="clearboth"></div>
						</div>
					</div>
					
				<!-- 下面三个部分未来再做  begin-->
				<!-- 	<div class="PublicframeTwo SetPositionOne">
						<div class="PublicframeTwoHeadBar">
							<div class="PublicframeTwoHeadBarLeft"></div>
							<div class="PublicframeTwoHeadBarTittle">
								<span class="PublicframeTwoHeadBarTittleSpan" title="简历模板">简历模板</span>
							</div>
							<div class="PublicframeTwoHeadBarRight"></div>
							<div class="PublicframeTwoHeadBarMore">
								<span class="PublicframeTwoHeadBarMoreSpan">
									<a href="HP_ArticleMore.aspx?seachCategory=3&amp;crumbs=001030" >更多</a>
								</span>
							</div>
						</div>
						<div class="PublicframeTwoContent listpub">
							<dl class="jdmb">
								<dt> <img src="${contextPath}/images/HomePageImage/临时图/p3.jpg" alt="简历模板" /> <span>商业模板</span> </dt>
								<dd> <a title="常见的4种培训需求分析方法" href="" >常见的4种培训需求分析方法</a> </dd>
								<dd> <a title="揭秘培训需求分析要点" href="" >揭秘培训需求分析要点</a> </dd>
								<dd> <a title="培训需求分析要善于寻找差距" href="" >培训需求分析要善于寻找差距</a> </dd>
								<dd> <a title="如何做好培训需求分析？" href="" >如何做好培训需求分析？</a> </dd>
								<dd> <a title="培训需求管理要点揭秘" href="" >培训需求管理要点揭秘</a> </dd>
							</dl>
							<div class="clearboth"></div>
						</div>
					</div>
					<div class="PublicframeTwo SetPositionOne SetMarginLeft">
						<div class="PublicframeTwoHeadBar">
							<div class="PublicframeTwoHeadBarLeft"></div>
							<div class="PublicframeTwoHeadBarTittle"> <span class="PublicframeTwoHeadBarTittleSpan" title="面试技巧">面试技巧</span> </div>
							<div class="PublicframeTwoHeadBarRight"></div>
							<div class="PublicframeTwoHeadBarMore"> <span class="PublicframeTwoHeadBarMoreSpan"> <a href="HP_ArticleMore.aspx?seachCategory=4&amp;crumbs=001030" >更多</a> </span> </div>
						</div>
						<div class="PublicframeTwoContent listpub">
							<dl class="jdmb">
								<dt> <img src="${contextPath}/images/HomePageImage/临时图/p2.jpg" alt="面试技巧" /> <span>面试技巧指南</span> </dt>
								<dd> <a title="面试官的常用陷阱手段" href="" >面试官的常用陷阱手段</a> </dd>
								<dd> <a title="教你应付8类难缠的面试官" href="" >教你应付8类难缠的面试官</a> </dd>
								<dd> <a title="如何与招聘面试官打好交道" href="" >如何与招聘面试官打好交道</a> </dd>
								<dd> <a title="打动主考官的6个面试技巧" href="" >打动主考官的6个面试技巧</a> </dd>
								<dd> <a title="面试成功掌握十技巧" href="" >面试成功掌握十技巧</a> </dd>
							</dl>
							<div class="clearboth"></div>
						</div>
					</div>
					<div class="PublicframeTwo SetPositionOne SetMarginLeft">
						<div class="PublicframeTwoHeadBar">
							<div class="PublicframeTwoHeadBarLeft"></div>
							<div class="PublicframeTwoHeadBarTittle">
								<span class="PublicframeTwoHeadBarTittleSpan" title="职场资讯">职场资讯</span>
							</div>
							<div class="PublicframeTwoHeadBarRight"></div>
							<div class="PublicframeTwoHeadBarMore">
								<span class="PublicframeTwoHeadBarMoreSpan">
									<a href="HP_ArticleMore.aspx?seachCategory=5&amp;crumbs=001030" >更多</a>
								</span>
							</div>
						</div>
						<div class="PublicframeTwoContent listpub">
							<dl class="jdmb">
								<dt> <img src="${contextPath}/images/HomePageImage/临时图/p1.jpg" alt="职场资讯" /> <span>新鲜的资讯信息</span> </dt>
								<dd> <a title="IT培训班进修火引争议" href="" >IT培训班进修火引争议</a> </dd>
								<dd> <a title="职业规划有什么作用？" href="" >职业规划有什么作用？</a> </dd>
								<dd> <a title="想转行 你准备好了吗?" href="" >想转行 你准备好了吗?</a> </dd>
								<dd> <a title="何做出彰显个人能力的求职简历呢" href="" >何做出彰显个人能力的求职简历呢</a> </dd>
								<dd> <a title="时刻准备勇闯招聘会" href="" >时刻准备勇闯招聘会</a> </dd>
							</dl>
							<div class="clearboth"></div>
						</div>
					</div> -->
					<!-- 下面三个部分未来再做  end-->
					
					<div class="clearboth"></div>
				</div>
			</div>
		</div>
		<!-- ******* 中部内容显示区 ******* end ********** -->
		
		<!-- 尾部footer区 -->
		<div class="clearboth"></div>
		<jsp:include page="formatter/footer.jsp" />
		
		<!-- 右下角悬浮广告窗口 begin -->
		<div id="aa" style="width:200px;height:150px;border: 3px solid rgb(177, 177, 226);border-radius: 4px;text-align:left;color:black;" >
			<div style="height: 18px;width: 190px;;background-color: rgb(120, 105, 210);padding: 5px;">
				***温馨提示***
				<span style="float:right;" onmouseover="mouseoverCloseBtn(this);" onmouseout="mouseoutCloseBtn(this);" onclick="clickCloseBtn(this);">[<span style="font-size:13px;">关闭</span>]</span>
			</div>
			<div style="width:190px;background-color:white;padding:5px 5px;text-indent:2em;font-size:12px;">
				招聘单位无权向求职者收取任何费用，如有单位在面试过程中向您收取押金、保证金、体检费、材料费、成本费等违规费用，请多加注意，以免上当受骗。
			</div>
		</div>
		
		<script> 
			function mouseoverCloseBtn(obj){
				$(obj).css({'color':'white','cursor':'pointer'});
			}
			function mouseoutCloseBtn(obj){
				$(obj).css({'color':'black'});
			}
			function clickCloseBtn(obj){
				$(obj).parent().parent().hide("slow");
			}
			
			function scrollx(p){ 
				var d = document,dd = d.documentElement,db = d.body,w = window,o = d.getElementById(p.id),ie6 = /msie 6/i.test(navigator.userAgent),style,timer;
				if(o){ 
					o.style.cssText +=";position:"+(p.f&&!ie6?'fixed':'absolute')+";"+(p.l==undefined?'right:0;':'left:'+p.l+'px;')+(p.t!=undefined?'top:'+p.t+'px':'bottom:0');
					if(p.f&&ie6){ 
						o.style.cssText +=';left:expression(documentElement.scrollLeft + '+(p.l==undefined?dd.clientWidth-o.offsetWidth:p.l)+' + "px");top:expression(documentElement.scrollTop +'+(p.t==undefined?dd.clientHeight-o.offsetHeight:p.t)+'+ "px" );'; 
						dd.style.cssText +=';background-image: url(about:blank);background-attachment:fixed;'; 
					}else{ 
						if(!p.f){
							var st = (dd.scrollTop||db.scrollTop),c; 
							c = st - o.offsetTop + (p.t!=undefined?p.t:(w.innerHeight||dd.clientHeight)-o.offsetHeight); 
							if(c!=0){ 
								o.style.top = o.offsetTop + Math.ceil(Math.abs(c)/10)*(c<0?-1:1) + 'px'; 
							}
							w.onresize = w.onscroll = function(){ 
								clearInterval(timer); 
								timer = setInterval(function(){ 
									//双选择为了修复chrome 下xhtml解析时dd.scrollTop为 0 
									var st = (dd.scrollTop||db.scrollTop),c; 
									c = st - o.offsetTop + (p.t!=undefined?p.t:(w.innerHeight||dd.clientHeight)-o.offsetHeight); 
									if(c!=0){ 
										o.style.top = o.offsetTop + Math.ceil(Math.abs(c)/10)*(c<0?-1:1) + 'px'; 
									}else{ 
										clearInterval(timer); 
									} 
								},10); 
							} 
						}
						
					} 
				} 
			} 
			scrollx({ 
				id:'aa' 
			}) 
		/*	scrollx({ 
				id:'bb', 
				l:0, 
				t:200, 
				f:1 
			}) */
			/* 
			id 你要滚动的内容的id 
			l 横坐标的位置 不写为紧贴右边 
			t 你要放在页面的那个位置默认是贴着底边 0是贴着顶边 
			f 1表示固定 不写或者0表示滚动 
			*/ 
			</script>
		
		
		<!-- 右下角悬浮广告窗口 end -->
	</div>
</body>
</html>
