<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>残疾人就业信息网</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="keywords" content="残疾人招聘信息,残疾人就业信息,残疾人人才网,残疾人找工作" />
	<meta content="残疾人招聘就业" name="description" />
	
	<link href="${contextPath}/css/public.css" rel="stylesheet" type="text/css" />
	
	<script type="text/javascript" src="${contextPath}/js/jquery.js"></script>
	<script type="text/javascript" src="${contextPath}/js/potoschange.js"></script>
	<script type="text/javascript">
		function getimgcode(){
			var timestamp=new Date().getTime();
			$("#verifyCode").attr("src","${contextPath}/checkcode/create?"+timestamp);
		}
	</script>
</head>
<body style="height:1900px;min-height:900px;">
	<div id="container" class="container">
	
		<!-- 顶部工具条栏 -->
		<jsp:include page="status-bar.jsp" />
		
		<!-- 头部导航及图片栏目 -->
		<jsp:include page="header.jsp" />
		<div class="clearboth"></div>
		
		<!-- ******* 中部内容显示区 ******* start ********** -->
		<div id="content" class="content">
			<div>
				<div class="login SetFloatLeft">
					<div class="loginbtn">
						<div title="个人用户登录" id="PersonalTab" class="BlueFirst" style="cursor: auto;"></div>
						<div title="企业用户登录" id="BusinessTab" class="WhiteLast" style="cursor: auto;"></div>
					</div>
					<div class="loginmain">
						<div class="loginmainarae">
							<div>
								<div class="divtext">
									<div>
										<input type="text" value="请输入用户名或身份证" id="UserName" class="text DefaultText" title="请输入用户名或身份证" style="color: Gray;" />
									</div>
									<div class="righttext">
										<input type="text" value="请输入用户密码" id="Password0" class="text DefaultText" title="输入密码" style="color: Gray;" />
									</div>
								</div>
								<div id="LoginBtn" class="loginbtnimg" style="cursor: pointer;"></div>
							</div>
							<div class="clearboth"></div>
							<div class="divchk">
								<div >
									<a style="cursor:pointer;">找回密码</a>
									<%--<span title="自动登录" class="zdword">自动登录</span>
								--%>
								<a style="cursor: pointer;">找回用户</a></div>
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
				<div class="GradualChangeBg SetFloatRight">
					<div class="SetFloatLeft left"></div>
					<div class="SetFloatLeft center">
						<div class="main">
							<div class="Search">
								<div class="SearchTab">
									<ul>
										<li class="SearchTextWhiteBg" title="职位名" style="cursor: auto;">职位名</li>
										<li class="SearchImg">|</li>
										<li class="SearchTextBlueBg" title="单位名" style="cursor: auto;">单位名</li>
									</ul>
								</div>
								<div class="clearboth"></div>
								<div class="SearchInputText">
									<div class="SetFloatLeft InputTextBg">
										<input id="KeyWord" class="InputTextBlank DefaultText" type="text" value="请输入关键字" title="请输入关键字" style="color: Gray;">
									</div>
									<div class="SearchBtn SetFloatLeft">
										<div id="areaBtn" class="SearchDistrictBtn SetFloatLeft" title="选择地区" style="cursor: auto;"> <span>选择地区</span> </div>
										<div class="SearchButtom SetFloatLeft" title="搜索" style="cursor: auto;"></div>
										<div class="SetFloatLeft AdvancedSearch"> <a title="高级搜索" href="HP_AdvancedSearch.aspx">高级搜索</a> </div>
									</div>
								</div>
							</div>
							<div style="*padding-left:6px;">
								<div style=" height:20px;">
									<div class="SetFloatLeft">常用职位搜索：</div>
									<div>
										<ul class="VerticalLineUL">
											<li> <a title="计算机" href="#" style="color: rgb(32, 164, 254);">计算机</a> </li>
											<li class="LiEven">|</li>
											<li> <a title="业务员" href="#" style="color: rgb(32, 164, 254);">业务员</a> </li>
											<li class="LiEven">|</li>
											<li> <a title="程序员" href="#" style="color: rgb(32, 164, 254);">程序员</a> </li>
											<li class="LiEven">|</li>
											<li> <a title="化妆品" href="#" style="color: rgb(32, 164, 254);">化妆品</a> </li>
											<li class="LiEven">|</li>
											<li> <a title="软件工程师" href="#" style="color: rgb(32, 164, 254);">软件工程师</a> </li>
											<li class="LiEven">|</li>
											<li> <a title="设计" href="#" style="color: rgb(32, 164, 254);">设计</a> </li>
											<li class="LiEven">|</li>
											<li> <a title="文员" href="#" style="color: rgb(32, 164, 254);">文员</a> </li>
											<li class="LiEven">|</li>
											<li> <a title="会计" href="#" style="color: rgb(32, 164, 254);">会计</a> </li>
											<li class="LiEven">|</li>
											<li> <a title="项目经理" href="#" style="color: rgb(32, 164, 254);">项目经理</a> </li>
											<li class="LiEven">|</li>
											<li> <a title="客服" href="#" style="color: rgb(32, 164, 254);">客服</a> </li>
											<li class="LiEven">|</li>
										</ul>
									</div>
								</div>
								<div class="clearboth"></div>
								<div class="CitySearch">
									<div class="CitySearchImg SetFloatLeft">热门城市</div>
									<div id="HotCity" class="CitySearchList SetFloatLeft">
										<ul id="hotCity">
											<li> <a name="440100" title="广州" href="#">广州</a> </li>
											<li> <a name="440300" title="深圳" href="#">深圳</a> </li>
											<li> <a name="442000" title="中山" href="#">中山</a> </li>
											<li> <a name="441300" title="惠州" href="#">惠州</a> </li>
											<li> <a name="440500" title="汕头" href="#">汕头</a> </li>
											<li> <a name="441900" title="东莞" href="#">东莞</a> </li>
											<li> <a name="440400" title="珠海" href="#">珠海</a> </li>
											<li> <a name="440600" title="佛山" href="#">佛山</a> </li>
											<li> <a name="445100" title="潮州" href="#">潮州</a> </li>
											<li> <a name="440700" title="江门" href="#">江门</a> </li>
											<li> <a name="440800" title="湛江" href="#">湛江</a> </li>
											<li> <a name="441200" title="肇庆" href="#">肇庆</a> </li>
											<li> <a name="440900" title="茂名" href="#">茂名</a> </li>
											<li> <a name="441500" title="汕尾" href="#">汕尾</a> </li>
											<li> <a name="445200" title="揭阳" href="#">揭阳</a> </li>
										</ul>
									</div>
								</div>
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
						<div class="PublicframeOneHeaderBarRight"> <a target="_blank" href="P_WorkplaceInformation.aspx">更多</a> </div>
					</div>
					<div class="photo">
						<dl id="scrollimg2" class="tagon3">
							<dt class="pic1"> <a title="[简历模板] 英语简历范文" target="_blank" href=""> <img title="[简历模板] 英语简历范文" alt="[简历模板] 英语简历范文" style=" width:255px; height:208px; " src="images/Article/test/6.jpg"> </a> </dt>
							<dd class="tag1"> <a onfocus="imgScroll(1)" onmousemove="imgScroll(1)" target="_blank" title="[简历模板] 英语简历范文" href="">1</a> </dd>
							<dt class="pic2"> <a title="[简历模板] 金融学专业简历范文" target="_blank" href=""> <img title="[简历模板] 金融学专业简历范文" alt="[简历模板] 金融学专业简历范文" style=" width:255px; height:208px; " src="images/Article/test/5.jpg"> </a> </dt>
							<dd class="tag2"> <a onfocus="imgScroll(2)" onmousemove="imgScroll(2)" target="_blank" title="[简历模板] 金融学专业简历范文" href="">2</a> </dd>
							<dt class="pic3"> <a title="[简历模板] 市场营销专业简历样本" target="_blank" href=""> <img title="[简历模板] 市场营销专业简历样本" alt="[简历模板] 市场营销专业简历样本" style=" width:255px; height:208px; " src="images/Article/test/4.jpg"> </a> </dt>
							<dd class="tag3"> <a onfocus="imgScroll(3)" onmousemove="imgScroll(3)" target="_blank" title="[简历模板] 市场营销专业简历样本" href="">3</a> </dd>
							<dt class="pic4"> <a title="[面试]面试承诺是否有效？" target="_blank" href=""> <img title="[面试]面试承诺是否有效？" alt="[面试]面试承诺是否有效？" style=" width:255px; height:208px; " src="images/Article/test/3.jpg"> </a> </dt>
							<dd class="tag4"> <a onfocus="imgScroll(4)" onmousemove="imgScroll(4)" target="_blank" title="[面试]面试承诺是否有效？" href="">4</a> </dd>
							<dt class="pic5"> <a title="[面试]三分钟定面试成败" target="_blank" href=""> <img title="[面试]三分钟定面试成败" alt="[面试]三分钟定面试成败" style=" width:255px; height:208px; " src="images/Article/test/2.jpg"> </a> </dt>
							<dd class="tag5"> <a onfocus="imgScroll(5)" onmousemove="imgScroll(5)" target="_blank" title="[面试]三分钟定面试成败" href="">5</a> </dd>
							<dt class="pic6"> <a title="[面试]面试后关键：如何写好感谢信" target="_blank" href=""> <img title="[面试]面试后关键：如何写好感谢信" alt="[面试]面试后关键：如何写好感谢信" style=" width:255px; height:208px; " src="images/Article/test/1.jpg"> </a> </dt>
							<dd class="tag6"> <a onfocus="imgScroll(6)" onmousemove="imgScroll(6)" target="_blank" title="[面试]面试后关键：如何写好感谢信" href="">6</a> </dd>
						</dl>
					</div>
					<div id="hp_fwdt_id" class="hp_fwdt">
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
						<div class="PublicframeOneHeaderBarRight"> <a target="_blank" href="HP_VocationalTraining.aspx">更多</a> </div>
					</div>
					<div id="hp_ftxx_id">
						<ul>
							<li class="Square"> <a target="_blank" title="最新职业能力测评" href="">最新职业能力测评</a> </li>
							<li class="Square"> <a target="_blank" title="职业胜任能力测评" href="">职业胜任能力测评</a> </li>
							<li class="Square"> <a target="_blank" title="HR必知：培训需求分析五大作用" href="">HR必知：培训需求分析五大作用</a> </li>
							<li class="Square"> <a target="_blank" title="企业培训计划制定五大要点" href="">企业培训计划制定五大要点</a> </li>
							<li class="Square"> <a target="_blank" title="HR必看:如何做好企业培训这道菜" href="">HR必看:如何做好企业培训这道菜</a> </li>
							<li class="Square"> <a target="_blank" title="常见的4种培训需求分析方法" href="">常见的4种培训需求分析方法</a> </li>
							<li class="Square"> <a target="_blank" title="揭秘培训需求分析要点" href="">揭秘培训需求分析要点</a> </li>
							<li class="Square"> <a target="_blank" title="培训需求分析要善于寻找差距" href="">培训需求分析要善于寻找差距</a> </li>
							<li class="Square"> <a target="_blank" title="如何做好培训需求分析？" href="">如何做好培训需求分析？</a> </li>
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
									<tr class="resumeMessageheaderOther">
										<td><a target="_blank" href="">陈**</a></td>
										<td>高中</td>
										<td>视力</td>
										<td>二级</td>
										<td><div>中医科医生</div></td>
									</tr>
									<tr class="resumeMessageheaderOther">
										<td><a target="_blank" href="">邢**</a></td>
										<td>本科</td>
										<td>多重</td>
										<td>一级</td>
										<td><div>平面设计师</div></td>
									</tr>
									<tr class="resumeMessageheaderOther">
										<td><a target="_blank" href="">邱**</a></td>
										<td>初中</td>
										<td>智力</td>
										<td>三级</td>
										<td><div>其他</div></td>
									</tr>
									<tr class="resumeMessageheaderOther">
										<td><a target="_blank" href="">陈**</a></td>
										<td>本科</td>
										<td>多重</td>
										<td>二级</td>
										<td><div></div></td>
									</tr>
									<tr class="resumeMessageheaderOther">
										<td><a target="_blank" href="">邓**</a></td>
										<td>初中</td>
										<td>言语</td>
										<td>二级</td>
										<td><div></div></td>
									</tr>
									<tr class="resumeMessageheaderOther">
										<td><a target="_blank" href="">赵**</a></td>
										<td>大专</td>
										<td>言语</td>
										<td>二级</td>
										<td><div>大学/大专应届毕…</div></td>
									</tr>
									<tr class="resumeMessageheaderOther">
										<td><a target="_blank" href="">李**</a></td>
										<td>初中</td>
										<td>肢体</td>
										<td>三级</td>
										<td><div>其他</div></td>
									</tr>
									<tr class="resumeMessageheaderOther">
										<td><a target="_blank" href="">赖**</a></td>
										<td>初中</td>
										<td>听力</td>
										<td>一级</td>
										<td><div>普工/操作工</div></td>
									</tr>
									<tr class="resumeMessageheaderOther">
										<td><a target="_blank" href="">陈**</a></td>
										<td>初中</td>
										<td>肢体</td>
										<td>三级</td>
										<td><div>普工/操作工</div></td>
									</tr>
									<tr class="resumeMessageheaderOther">
										<td><a target="_blank" href="">夏**</a></td>
										<td>高中</td>
										<td>听力</td>
										<td>一级</td>
										<td><div></div></td>
									</tr>
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
										<th>招聘职位</th>
										<th>日期</th>
									</tr>
									<tr class="resumeMessageheaderOther">
										<td><a target="_blank" href="" title="广东惠而浦家电制品有限公司">广东惠而浦家电制…</a></td>
										<td><a target="_blank" href="" title="生产工">生产工</a></td>
										<td>2014/12/3</td>
									</tr>
									<tr class="resumeMessageheaderOther">
										<td><a target="_blank" href="" title="佛山裕顺福首饰钻石有限公司">佛山裕顺福首饰钻…</a></td>
										<td><a target="_blank" href="" title="普通工">普通工</a></td>
										<td>2014/12/3</td>
									</tr>
									<tr class="resumeMessageheaderOther">
										<td><a target="_blank" href="" title="广东科荣电器有限公司">广东科荣电器有限…</a></td>
										<td><a target="_blank" href="" title="装配工">装配工</a></td>
										<td>2014/12/3</td>
									</tr>
									<tr class="resumeMessageheaderOther">
										<td><a target="_blank" href="" title="佛山市樱顺卫厨用品有限公司">佛山市樱顺卫厨用…</a></td>
										<td><a target="_blank" href="" title="普工">普工</a></td>
										<td>2014/12/3</td>
									</tr>
									<tr class="resumeMessageheaderOther">
										<td><a target="_blank" href="" title="佛山市顺德区今桥首饰有限公司">佛山市顺德区今桥…</a></td>
										<td><a target="_blank" href="" title="普工">普工</a></td>
										<td>2014/12/3</td>
									</tr>
									<tr class="resumeMessageheaderOther">
										<td><a target="_blank" href="" title="佛山市顺德区今桥首饰有限公司">佛山市顺德区今桥…</a></td>
										<td><a target="_blank" href="" title="文员">文员</a></td>
										<td>2014/12/3</td>
									</tr>
									<tr class="resumeMessageheaderOther">
										<td><a target="_blank" href="" title="珠海市意多傢私有限公司">珠海市意多傢私有…</a></td>
										<td><a target="_blank" href="" title="氩弧焊工">氩弧焊工</a></td>
										<td>2014/12/3</td>
									</tr>
									<tr class="resumeMessageheaderOther">
										<td><a target="_blank" href="" title="深圳市华测检测技术股份有限公司顺德分公司">深圳市华测检测技…</a></td>
										<td><a target="_blank" href="" title="助理技术员">助理技术员</a></td>
										<td>2014/12/3</td>
									</tr>
									<tr class="resumeMessageheaderOther">
										<td><a target="_blank" href="" title="佛山南海佳美莉美甲服务部">佛山南海佳美莉美…</a></td>
										<td><a target="_blank" href="" title="美容师">美容师</a></td>
										<td>2014/12/3</td>
									</tr>
									<tr class="resumeMessageheaderOther">
										<td><a target="_blank" href="" title="佛山南海佳美莉美甲服务部">佛山南海佳美莉美…</a></td>
										<td><a target="_blank" href="" title="美甲师">美甲师</a></td>
										<td>2014/12/3</td>
									</tr>
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
									<li class="Square"> <a target="_blank" title="密切注意职场成长中的10个主要障碍" href="">密切注意职场成长中的10个主要障碍</a> </li>
									<li class="Square"> <a target="_blank" title="传说中最好的文章：如何择业、跳槽、成..." href="">传说中最好的文章：如何择业、跳槽、成...</a> </li>
									<li class="Square"> <a target="_blank" title="职业规划：人生一堂必修课" href="">职业规划：人生一堂必修课</a> </li>
									<li class="Square"> <a target="_blank" title="职场规划为了什么" href="">职场规划为了什么</a> </li>
								</ul>
								<a href="" target="_blank">
									<div id="btn_Link" class="LinkButton"></div>
								</a>
							</div>
							<div>
								<a href="" target="_blank">
									<div id="btn_Link1" class="LinkButton1"></div>
								</a>
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
							<li>
								<a title="广州润兴软件有限公司" target="_blank" href="">广州润兴软件有限公司</a> <a class="position" title="网页设计工程师" target="_blank" style="color:#0868C8" href="">网页设计工程师</a>
							</li>
							<li>
								<a title="茂名中燃城市燃气发展有限公司" target="_blank" href="">茂名中燃城市燃气发展有限公司</a> <a class="position" title="厨房杂工" target="_blank" style="color:#0868C8" href="">厨房杂工</a>
							</li>
							<li>
								<a title="茂名华通丰田汽车销售服务有限公司" target="_blank" href="">茂名华通丰田汽车销售服务有限公司</a> <a class="position" title="客服回访专员" target="_blank" style="color:#0868C8" href="">客服回访专员</a>
							</li>
							<li>
								<a title="广东惠而浦家电制品有限公司" target="_blank" href="">广东惠而浦家电制品有限公司</a> <a class="position" title="财务文员" target="_blank" style="color:#0868C8" href="">财务文员</a>
								<a class="position" title="实验室技术员" target="_blank" style="color:#0868C8" href="">实验室技术员</a>
							</li>
							<li>
								<a title="阳西县福利装璜包装加工厂" target="_blank" href="">阳西县福利装璜包装加工厂</a> <a class="position" title="全职" target="_blank" style="color:#0868C8" href="">全职</a>
							</li>
						</ul>
						<div class="clearboth"></div>
					</div>
				</div>
				<div id="List_Tab" style="margin-top: 10px;">
					<div class="clearboth"></div>
					<ul class="TabCss">
						<li id="001"> <span class="firstWhite"></span> <span class="whitebg">计算机/互联网/通信</span> </li>
						<li id="002"> <span class="rightWhiteOfCenter"></span> <span class="whitebg bluebg">会计/金融/银行/保险</span> </li>
						<li id="003"> <span class="rightWhiteOfCenter rightBlueOfCenter"></span> <span class="whitebg bluebg">贸易/消费/制造/营运</span> </li>
						<li id="004"> <span class="rightWhiteOfCenter rightBlueOfCenter"></span> <span class="whitebg bluebg">制药/医疗</span> </li>
						<li id="005"> <span class="rightWhiteOfCenter rightBlueOfCenter"></span> <span class="whitebg bluebg">广告/媒体</span> </li>
						<li id="007"> <span class="rightWhiteOfCenter rightBlueOfCenter"></span> <span class="whitebg bluebg">专业服务/教育/培训</span> </li>
						<li> <span class="lastBlue"></span> <span class=""></span> </li>
					</ul>
					<div class="clearboth"></div>
					<div id="List_Tab_Content" class="TabContentStyle">
						<div id="TabsMainShow">
							<ul>
								<li class=""><a target="_blank" href="">梅州奥美佳电子有限公司</a><a href="" style="color:#0868C8" class="position">普工</a></li>
								<li class=""><a target="_blank" href="">广东道路信息发展有限公司</a><a href="" style="color:#0868C8" class="position">话务员</a></li>
								<li class=""><a target="_blank" href="">愈富制造（河源）有限公司</a><a href="" style="color:#0868C8" class="position">文员</a></li>
								<li class=""><a target="_blank" href="">珠海市为达电子有限公司</a><a href="" style="color:#0868C8" class="position">普工</a></li>
								<li class=""><a target="_blank" href="">肇庆华锋电子铝箔股份有限公司</a><a href="" style="color:#0868C8" class="position">后勤门卫</a><a href="" style="color:#0868C8" class="position">仓库管理员</a></li>
								<li class=""><a target="_blank" href="">肇庆市创源电子有限公司</a><a href="" style="color:#0868C8" class="position">手工</a></li>
								<li class=""><a target="_blank" href="">肇庆市众邦计算机科技有限公司</a><a href="" style="color:#0868C8" class="position">文员</a></li>
								<li class=""><a target="_blank" href="">潮州市蓓蕾电子有限公司</a><a href="" style="color:#0868C8" class="position">男工</a><a href="" style="color:#0868C8" class="position">女工</a></li>
								<li class=""><a target="_blank" href="">潮州市创佳集团有限公司 </a><a href="" style="color:#0868C8" class="position">男工</a><a href="" style="color:#0868C8" class="position">女工</a></li>
								<li class=""><a target="_blank" href="">深圳市盛传网络技术有限公司</a><a href="" style="color:#0868C8" class="position">客服</a></li>
								<li class=""><a target="_blank" href="">广东远光电缆实业有限公司</a><a href="" style="color:#0868C8" class="position">生产工</a></li>
								<li class=""><a target="_blank" href="">珠海启扬数据处理有限公司</a><a href="" style="color:#0868C8" class="position">打字员</a></li>
								<li class=""><a target="_blank" href="">珠海市田森电子科技有限公司</a></li>
								<li class=""><a target="_blank" href="">珠海富润科技有限公司</a><a href="" style="color:#0868C8" class="position">电销专员</a></li>
								<li class=""><a target="_blank" href="">深圳市普顺达科技有限公司</a><a href="" style="color:#0868C8" class="position">普工</a></li>
								<li class=""><a target="_blank" href="">珠海普威科技有限公司</a><a href="" style="color:#0868C8" class="position">.net 软件开发</a></li>
								<li class=""><a target="_blank" href="">珠海普及信息科技有限公司</a></li>
								<li class=""><a target="_blank" href="">珠海安居宝电子科技有限公司</a></li>
								<li class=""><a target="_blank" href="">珠海矽微电子科技有限公司</a></li>
								<li class=""><a target="_blank" href="">珠海市思域软件科技有限公司</a></li>
								<li class=""><a target="_blank" href="">梅州奥美佳电子有限公司</a><a href="" style="color:#0868C8" class="position">普工</a></li>
								<li class=""><a target="_blank" href="">广东道路信息发展有限公司</a><a href="" style="color:#0868C8" class="position">话务员</a></li>
								<li class=""><a target="_blank" href="">愈富制造（河源）有限公司</a><a href="" style="color:#0868C8" class="position">文员</a></li>
								<li class=""><a target="_blank" href="">珠海市为达电子有限公司</a><a href="" style="color:#0868C8" class="position">普工</a></li>
								<li class=""><a target="_blank" href="">肇庆华锋电子铝箔股份有限公司</a><a href="" style="color:#0868C8" class="position">后勤门卫</a><a href="" style="color:#0868C8" class="position">仓库管理员</a></li>
								<li class=""><a target="_blank" href="">肇庆市创源电子有限公司</a><a href="" style="color:#0868C8" class="position">手工</a></li>
								<li class=""><a target="_blank" href="">肇庆市众邦计算机科技有限公司</a><a href="" style="color:#0868C8" class="position">文员</a></li>
								<li class=""><a target="_blank" href="">潮州市蓓蕾电子有限公司</a><a href="" style="color:#0868C8" class="position">男工</a><a href="" style="color:#0868C8" class="position">女工</a></li>
								<li class=""><a target="_blank" href="">潮州市创佳集团有限公司 </a><a href="" style="color:#0868C8" class="position">男工</a><a href="" style="color:#0868C8" class="position">女工</a></li>
								<li class=""><a target="_blank" href="">深圳市盛传网络技术有限公司</a><a href="" style="color:#0868C8" class="position">客服</a></li>
								<li class=""><a target="_blank" href="">梅州奥美佳电子有限公司</a><a href="" style="color:#0868C8" class="position">普工</a></li>
								<li class=""><a target="_blank" href="">广东道路信息发展有限公司</a><a href="" style="color:#0868C8" class="position">话务员</a></li>
								<li class=""><a target="_blank" href="">愈富制造（河源）有限公司</a><a href="" style="color:#0868C8" class="position">文员</a></li>
								<li class=""><a target="_blank" href="">珠海市为达电子有限公司</a><a href="" style="color:#0868C8" class="position">普工</a></li>
								<li class=""><a target="_blank" href="">肇庆华锋电子铝箔股份有限公司</a><a href="" style="color:#0868C8" class="position">后勤门卫</a><a href="" style="color:#0868C8" class="position">仓库管理员</a></li>
								<li class=""><a target="_blank" href="">肇庆市创源电子有限公司</a><a href="" style="color:#0868C8" class="position">手工</a></li>
								<li class=""><a target="_blank" href="">肇庆市众邦计算机科技有限公司</a><a href="" style="color:#0868C8" class="position">文员</a></li>
								<li class=""><a target="_blank" href="">潮州市蓓蕾电子有限公司</a><a href="" style="color:#0868C8" class="position">男工</a><a href="" style="color:#0868C8" class="position">女工</a></li>
								<li class=""><a target="_blank" href="">潮州市创佳集团有限公司 </a><a href="" style="color:#0868C8" class="position">男工</a><a href="" style="color:#0868C8" class="position">女工</a></li>
								<li class=""><a target="_blank" href="">深圳市盛传网络技术有限公司</a><a href="" style="color:#0868C8" class="position">客服</a></li>
								<li class=""><a target="_blank" href="">梅州奥美佳电子有限公司</a><a href="" style="color:#0868C8" class="position">普工</a></li>
								<li class=""><a target="_blank" href="">广东道路信息发展有限公司</a><a href="" style="color:#0868C8" class="position">话务员</a></li>
								<li class=""><a target="_blank" href="">愈富制造（河源）有限公司</a><a href="" style="color:#0868C8" class="position">文员</a></li>
								<li class=""><a target="_blank" href="">珠海市为达电子有限公司</a><a href="" style="color:#0868C8" class="position">普工</a></li>
								<li class=""><a target="_blank" href="">肇庆华锋电子铝箔股份有限公司</a><a href="" style="color:#0868C8" class="position">后勤门卫</a><a href="" style="color:#0868C8" class="position">仓库管理员</a></li>
								<li class=""><a target="_blank" href="">梅州奥美佳电子有限公司</a><a href="" style="color:#0868C8" class="position">普工</a></li>
								<li class=""><a target="_blank" href="">广东道路信息发展有限公司</a><a href="" style="color:#0868C8" class="position">话务员</a></li>
								<li class=""><a target="_blank" href="">愈富制造（河源）有限公司</a><a href="" style="color:#0868C8" class="position">文员</a></li>
								<li class=""><a target="_blank" href="">珠海市为达电子有限公司</a><a href="" style="color:#0868C8" class="position">普工</a></li>
								<li class=""><a target="_blank" href="">肇庆华锋电子铝箔股份有限公司</a><a href="" style="color:#0868C8" class="position">后勤门卫</a><a href="" style="color:#0868C8" class="position">仓库管理员</a></li>
								<li class=""><a target="_blank" href="">肇庆市创源电子有限公司</a><a href="" style="color:#0868C8" class="position">手工</a></li>
							</ul>
						</div>
						<div class="clearboth"></div>
					</div>
					<div class="clearboth"></div>
				</div>
				<div class="clearboth"></div>
				<div class="SetMarginTop">
					<div class="PublicframeTwo SetPositionOne">
						<div class="PublicframeTwoHeadBar">
							<div class="PublicframeTwoHeadBarLeft"></div>
							<div class="PublicframeTwoHeadBarTittle">
								<span class="PublicframeTwoHeadBarTittleSpan" title="简历模板">简历模板</span>
							</div>
							<div class="PublicframeTwoHeadBarRight"></div>
							<div class="PublicframeTwoHeadBarMore">
								<span class="PublicframeTwoHeadBarMoreSpan">
									<a href="HP_ArticleMore.aspx?seachCategory=3&amp;crumbs=001030" target="_blank">更多</a>
								</span>
							</div>
						</div>
						<div class="PublicframeTwoContent listpub">
							<dl class="jdmb">
								<dt> <img src="${contextPath}/images/HomePageImage/临时图/p3.jpg" alt="简历模板"> <span>商业模板</span> </dt>
								<dd> <a title="常见的4种培训需求分析方法" href="" target="_blank">常见的4种培训需求分析方法</a> </dd>
								<dd> <a title="揭秘培训需求分析要点" href="" target="_blank">揭秘培训需求分析要点</a> </dd>
								<dd> <a title="培训需求分析要善于寻找差距" href="" target="_blank">培训需求分析要善于寻找差距</a> </dd>
								<dd> <a title="如何做好培训需求分析？" href="" target="_blank">如何做好培训需求分析？</a> </dd>
								<dd> <a title="培训需求管理要点揭秘" href="" target="_blank">培训需求管理要点揭秘</a> </dd>
							</dl>
							<div class="clearboth"></div>
						</div>
					</div>
					<div class="PublicframeTwo SetPositionOne SetMarginLeft">
						<div class="PublicframeTwoHeadBar">
							<div class="PublicframeTwoHeadBarLeft"></div>
							<div class="PublicframeTwoHeadBarTittle"> <span class="PublicframeTwoHeadBarTittleSpan" title="面试技巧">面试技巧</span> </div>
							<div class="PublicframeTwoHeadBarRight"></div>
							<div class="PublicframeTwoHeadBarMore"> <span class="PublicframeTwoHeadBarMoreSpan"> <a href="HP_ArticleMore.aspx?seachCategory=4&amp;crumbs=001030" target="_blank">更多</a> </span> </div>
						</div>
						<div class="PublicframeTwoContent listpub">
							<dl class="jdmb">
								<dt> <img src="${contextPath}/images/HomePageImage/临时图/p2.jpg" alt="面试技巧"> <span>面试技巧指南</span> </dt>
								<dd> <a title="面试官的常用陷阱手段" href="" target="_blank">面试官的常用陷阱手段</a> </dd>
								<dd> <a title="教你应付8类难缠的面试官" href="" target="_blank">教你应付8类难缠的面试官</a> </dd>
								<dd> <a title="如何与招聘面试官打好交道" href="" target="_blank">如何与招聘面试官打好交道</a> </dd>
								<dd> <a title="打动主考官的6个面试技巧" href="" target="_blank">打动主考官的6个面试技巧</a> </dd>
								<dd> <a title="面试成功掌握十技巧" href="" target="_blank">面试成功掌握十技巧</a> </dd>
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
									<a href="HP_ArticleMore.aspx?seachCategory=5&amp;crumbs=001030" target="_blank">更多</a>
								</span>
							</div>
						</div>
						<div class="PublicframeTwoContent listpub">
							<dl class="jdmb">
								<dt> <img src="${contextPath}/images/HomePageImage/临时图/p1.jpg" alt="职场资讯"> <span>新鲜的资讯信息</span> </dt>
								<dd> <a title="IT培训班进修火引争议" href="" target="_blank">IT培训班进修火引争议</a> </dd>
								<dd> <a title="职业规划有什么作用？" href="" target="_blank">职业规划有什么作用？</a> </dd>
								<dd> <a title="想转行 你准备好了吗?" href="" target="_blank">想转行 你准备好了吗?</a> </dd>
								<dd> <a title="何做出彰显个人能力的求职简历呢" href="" target="_blank">何做出彰显个人能力的求职简历呢</a> </dd>
								<dd> <a title="时刻准备勇闯招聘会" href="" target="_blank">时刻准备勇闯招聘会</a> </dd>
							</dl>
							<div class="clearboth"></div>
						</div>
					</div>
					<div class="clearboth"></div>
				</div>
			</div>
		</div>
		<!-- ******* 中部内容显示区 ******* end ********** -->
		
		<!-- 尾部footer区 -->
		<div class="clearboth"></div>
		<jsp:include page="footer.jsp" />
	</div>

<script type="text/javascript">
		$(function () {
			/*首页、找工作鼠标效果*/
			$("#Mainnav").find("ul").find("li").find("a:not(.Nav_sy)").hover(function () {
				$(this).css("color", "yellow");
			}, function () { $(this).css("color", "white"); });
			/*设置热门搜索字体颜色*/
			$(".VerticalLineUL").find("a").css("color", "#20a4fe");
			$("#PersonalRegister").bind("click", function () {
            	window.location.href = ""; //个人注册
			})
			$("#BusinessRegister").bind("click", function () {
				window.location.href = ""; //企业注册
			})
			/*个人单位切换*/
			$("#PersonalTab").bind("click", function () {
				LoginType = 0; $(this).removeClass().addClass("BlueFirst"); $("#BusinessTab").removeClass().addClass("WhiteLast"); $(".loginmain").removeClass("loginmain2"); $(".LoginRight").removeClass("LoginRight2"); $("#LoginBtn").removeClass("loginbtnimg2"); $("#LinkHrefBtn").attr("href", ""); $("#UserName").val("请输入用户名或身份证");
			}).mouseover(function () { this.style.cursor = "pointer"; }).mouseout(function () { this.style.cursor = "auto"; });
	
			$("#BusinessTab").bind("click", function () {
				LoginType = 1; $(this).removeClass().addClass("BlueLast"); $("#PersonalTab").removeClass().addClass("WhiteFirst "); $(".loginmain").addClass("loginmain2"); $(".LoginRight").addClass("LoginRight2"); $("#LoginBtn").addClass("loginbtnimg2"); $("#LinkHrefBtn").attr("href", ""); $("#UserName").val("请输入用户名或营业执照");
			}).mouseover(function () {
				this.style.cursor = "pointer";
			}).mouseout(function () {
				this.style.cursor = "auto";
			});
			 /*鼠标mouseover效果*/
			$("#LoginBtn").mouseover(function () { this.src = "${contextPath}/images/HomePageImage/LoginImage/HoverLogin.gif"; this.style.cursor = "pointer"; }).mouseout(function () { this.src = "${contextPath}/images/HomePageImage/LoginImage/LoginButtom.gif"; this.style.cursor = "pointer"; })
			$("#PersonalRegister").mouseover(function () { this.style.background = 'url(${contextPath}/images/HomePageImage/LoginImage/HoverPersonalRegister.gif)'; this.style.cursor = "pointer"; }).mouseout(function () { this.style.background = 'url(${contextPath}/images/HomePageImage/LoginImage/PersonalRegister.gif)'; this.style.cursor = "auto"; })
			$("#BusinessRegister").mouseover(function () { this.style.background = 'url(${contextPath}/images/HomePageImage/LoginImage/HoverBusinessRegister.gif)'; this.style.cursor = "pointer"; }).mouseout(function () { this.style.background = 'url(${contextPath}/images/HomePageImage/LoginImage/BusinessRegister.gif)'; this.style.cursor = "auto"; })
		});
		
	</script>
</body>
</html>