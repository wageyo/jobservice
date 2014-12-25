<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
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
<title>残疾人就业信息网</title>
<script type="text/javascript">
	$(document).ready(function() {
		common.pagination(1);
	});
	common = {};
	common.pagination = function(page) {
		var url = '${contextPath}/direct/search/' + page;
		$.ajax({
			url : url,
			type : 'GET',
			success : function(e) {
				$('#main').html(e);
				$('#data').fadeIn();
			},
			dataType : 'html',
			async : false
		});
	};
</script>
</head>
<body>
	<jsp:include page="../formatter/status-bar.jsp" />
	<jsp:include page="../formatter/header.jsp" />
	<div id="directBody">
	<div class="MainLeft">
		<div class="MainLeftRightHead1">
           <div class="PublicframeTwo ">
               <div class="PublicframeTwoHeadBar">
                 <div class="PublicframeTwoHeadBarLeft"></div>
                   <div class="PublicframeTwoHeadBarTittle">
                       <span class="PublicframeTwoHeadBarTittleSpan">就业指导</span>
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
                          	<div id="main"></div>
						  </div>
                       </div>
                   </div>
                   <div style="clear: both"></div>     
               </div>
           </div>
	    </div>	
	</div>
	<div class="MainRight">
      <div class="MainRightOne">
          <div class="PublicframeTwo ">
          	<div class="PublicframeTwoHeadBar">
          		<div class="PublicframeTwoHeadBarLeft"></div>
          		<div class="PublicframeTwoHeadBarTittle">
          			<span class="PublicframeTwoHeadBarTittleSpan">文章搜索</span>
          		</div>
          		<div class="PublicframeTwoHeadBarRight"></div>
          		<div class="PublicframeTwoHeadBarMore">
          			<span class="PublicframeTwoHeadBarMoreSpan"></span>
          		</div>
          	</div>
          	<div class="PublicframeTwoContent" id="">
          		<div class="ArticleSearchFrame">
          		  <div class="Search">
          			<div class="SearchInputText">
          			 <div class="SetFloat InputTextBg">
          			  <input type="text" title="请输入关键字" id="KeyWord" class="InputTextBlank DefaultText" value="请输入关键字" style="color: gray;" />
          			 </div>
          			</div>
          		   </div>
          		 </div>
          		 <div class="ArticleKind">
          		  <div class="ArticleKindTitle">文章类别：</div>
          		  <div class="ArticleSelectFrame">
          		    <select name="ArticleSelect" id="ArticleCategory" class="ArticleSelectDropDown">
          		     <option value="default">--请选择--</option>
          		     <option value="1">职业能力测评</option>
          		     <option value="2">就业指导</option>
          		     <option value="3">培训需求（个人）</option>
          		     <option value="4">面试技巧</option>
          		     <option value="5">职场资讯</option>
          		     <option value="6">面试宝典</option>
          		     <option value="7">安置残疾人优惠政策</option>
          		     <option value="8">培训需求（单位）</option>
          		     <option value="9">薪酬福利</option>
          		     <option value="10">职场观察</option>
          		     <option value="11">残疾人政策法规</option>
          		     <option value="12">职场八卦</option>
          		     <option value="50">一般政策法规</option>
          		     <option value="51"> 就业指导</option>
          		     <option value="53">服务动态</option>
          		    </select>
          		   </div>
          		  </div>
          		  <div class="ArticleTime">
          		    <div class="ArticleTimeTitle">发布时间：</div>
          		    <div class="ArticleTimeFrame">
          		      <select name="ArticleSelect" id="ArticleDateIssued" class="ArticleSelectDropDown">
          		        <option value="default">--请选择--</option>
          		        <option value="1">近一天</option>
          		        <option value="2">近两天</option>
          		        <option value="3">近三天</option>
          		        <option value="7">近一周</option>
          		        <option value="14">近两周</option>
          		        <option value="30">近一月</option>
          		        <option value="60">近两月</option>
          		        <option value="35600">长期有效</option>
          		       </select>
          		     </div>
          		    </div>
          		    <div class="ArticleHot">
          		        <ul>
          		            <li>热门文章搜索：</li>
          		            <li><a href="" style="color: #40a9fe;" title="计算机">计算机</a></li>
          		            <li class="ArticleHotLine">|</li>
          		            <li><a href="" title="业务员"style="color: #40a9fe;">业务员</a></li>
          		            <li class="ArticleHotLine">|</li><li><a href="" style="color: #40a9fe;" title="程序员">程序员</a></li>
          		         </ul>
          		     </div>
          		     <div style="clear: both"></div>
          		     <div class="ArticleBtn" id="SeachBtn"></div>
          		 </div>
            </div>
	      </div> 
		</div>
	</div>
	
	<%--
		<div style="width: 980px; margin-top: 10px; margin-left: 2px; height: 600px;">
			<div style="width: 100%; float: left; height: 600px; border-left: 1px solid #b3cee3;
                border-right: 1px solid #b3cee3; border-bottom: 1px solid #b3cee3">
				<div
					style="width: 100%; height: 37px; line-height: 37px; background-image: url(${contextPath}/images/PublicImage/PublicframeTwoImage/PublicframeTwoHeadBar.jpg);
                    color: #0980df; font-family: 微软雅黑; overflow: hidden">
					<div
						style="color: #ffffff; font-family: 宋体; overflow: hidden; text-align: center;
                        padding-top: 2px; margin-left: 10px; font-weight: bold; width: 120px; font-size: 13px;
                        background: url(${contextPath}/images/PublicImage/PublicframeTwoImage/PublicframeTwoHeadBarTittleSpan.jpg) no-repeat left top">
						就业指导</div>
				</div>
				<div id="main"></div>
			</div>
			--%>
						
			<!--  
			<div style="width: 260px; float: right; height: 600px">
				<div style="width: 254px; float: right; border: 1px solid  #9CC7E7">
					<div style="width: 232px; height: 29px; background-image: url(${contextPath}/images/menu_bg_r17_c19.jpg);
                        padding-left: 20px; padding-top: 10px; color: #0157B4">
						<strong>人才资讯</strong>
					</div>
					<div style="width: 252px;">
						<table style="line-height: 20px">
							<tbody>
								<tr>
									<td style="width:190px"><a href="show_8.html">求职简历</a>
									</td>
									<td style="width:62px">10月13日</td>
								</tr>
								<tr>
									<td style="width:190px"><a href="show_4.html">qqqq</a>
									</td>
									<td style="width:62px">10月13日</td>
								</tr>
								<tr>
									<td style="width:190px"><a href="show_5.html">wwwww</a>
									</td>
									<td style="width:62px">10月13日</td>
								</tr>
								<tr>
									<td style="width:190px"><a href="show_1.html">标题：</a>
									</td>
									<td style="width:62px">10月13日</td>
								</tr>
								<tr>
									<td style="width:190px"><a href="show_3.html">小猪</a>
									</td>
									<td style="width:62px">10月13日</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<div style="width: 254px; float: right; border: 1px solid #9CC7E7; margin-top: 8px">
					<div style="width: 232px; height: 29px; background-image: url(images/menu_bg_r17_c19.jpg);
                        padding-left: 20px; padding-top: 10px; color: #0157B4">
						<strong>工作宝典</strong>
					</div>
					<div style="width: 252px;">
						<table style="line-height: 20px">
							<tbody>
								<tr>
									<td style="width:190px"><a href="show_8.html">求职简历</a>
									</td>
									<td style="width:62px">10月13日</td>
								</tr>
								<tr>
									<td style="width:190px"><a href="show_4.html">qqqq</a>
									</td>
									<td style="width:62px">10月13日</td>
								</tr>
								<tr>
									<td style="width:190px"><a href="show_5.html">wwwww</a>
									</td>
									<td style="width:62px">10月13日</td>
								</tr>
								<tr>
									<td style="width:190px"><a href="show_3.html">小猪</a>
									</td>
									<td style="width:62px">10月13日</td>
								</tr>
								<tr>
									<td style="width:190px"><a href="show_1.html">标题：</a>
									</td>
									<td style="width:62px">10月13日</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>

			</div>
			-->
			
			<%--
		</div>
	--%>
<jsp:include page="../formatter/footer.jsp" />
		
</body>
</html>