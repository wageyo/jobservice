<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<title>残疾人就业信息网</title>

<script type="text/javascript" src="${contextPath}/js/jquery.js"></script>
</head>
<body>
 <jsp:include page="../formatter/status-bar.jsp" />
 <jsp:include page="../formatter/header.jsp" />
 <div id="directBody">
	<div class="MainLeft">
	   <div class="MainLeftRightHead1">
          <div class="PublicframeTwo ">             
             <div class="PublicframeTwoContentNew" style="width: auto; padding: 3px 5px 5px;">        
               <div class="banner">
                    <a href="#" title="图片">
                        <img src="${contextPath}/images/ArticleReadImage/article_2.jpg" alt="图片">
                    </a>
                </div>                         
                <div style="width: 710px;">
					<div class="news_title">					
						<div style=" text-align:center; font-size:18px; line-height:36px; font-weight:bold; " id="title">
							${news.title }
						</div>
					</div>
					<!--news_title-->
					<div class="news_info" style="background: url(${contextPath}/images/dot.gif) repeat-x 50% top;">
						<c:if test="${news.createDate != null}">
							<fmt:formatDate value="${news.createDate }" type="both" dateStyle="long" pattern="yyyy-MM-dd hh:mm:ss" var="createDate" />
						</c:if><%--						
						<div id="authorInfo" style=" text-align:center; width:100%">时间：<span id="pDate">${createDate }</span></div>
						<div id="authorInfo" style=" text-align:center; width:100%">责任编辑:<span id="pDate">${news.author }</span></div>
						<div id="authorInfo" style=" text-align:center; width:100%">来源:<span id="pDate">${news.source }</span></div>
						--%><%--
						<div class="news_info_l">时间:${createDate }</div>
						<div class="news_info_c">责任编辑:${news.author }</div>
						<div class="news_info_r">来源:${news.source }</div>--%>
					</div>
					<div>
						<hr style="width: 700px; margin: 10px auto; border-style: dotted; border-color:#CCC;">
						<div class="news_info_l">时间:${createDate }</div>
						<div class="news_info_c">责任编辑:${news.author }</div>
						<div class="news_info_r">来源:${news.source }</div>
					</div>
					<!--news_title-->
					<div class="news_contain" style="text-align: left;font-size: 13px;width: 690px;  margin:0 auto;color:#000000;text-indent:0px;margin-bottom: 10px;">
						<p>${news.content }</p>
					</div>
					<!--news_contain-->
					<div class="page_neirong">
						<ul>
						</ul>
					</div>
					<!--news_fenye-->
					<div class="news_ln">
						<!-- 	<div class="news_ln_l">
							没有了 <br> 下一篇：<a href="show_4.html">qqqq</a>
						</div>
						<div class="news_ln_r"></div>
						 -->
					</div>
				</div>                       
                <div style="clear: both">
                </div>
               </div>
           </div>
	    </div>	
	</div>
	<div class="MainRight"><%--
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
          			 <input type="text" name="keyWordNews"  title="请输入关键字1" id="keyWordNews" class="InputTextBlank DefaultText" value="${keyWordNews}" style="color: gray;" />
          			</div>
          			</div>
          		   </div>
          		 </div>
          	
          		  <div class="ArticleTime">
          		    <div class="ArticleTimeTitle">发布时间：</div>
          		    <div class="ArticleTimeFrame">
         			 <select name="releaseDate" id="releaseDate" class="ArticleSelectDropDown">
        		       	<c:forEach items="${params }" var="p1">
							<c:if test="${p1.type == 'releaseDate' }">
								<option value="${p1.value }" >${p1.name }</option>
							</c:if>
						</c:forEach>
          		       </select>
          		     </div>
          		    </div>
          		
          		     <div style="clear: both"></div>
          		     <div class="ArticleBtn" id="SeachBtn"  onclick="common.pagination(1)"></div>
          		 </div>
         </div>
      </div>
      --%><div class="MainRightTwo">
          <div class="PublicframeTwo ">
          	<div class="PublicframeTwoHeadBar">
          		<div class="PublicframeTwoHeadBarLeft"></div>
          		<div class="PublicframeTwoHeadBarTittle">
          			<span class="PublicframeTwoHeadBarTittleSpan">
						<c:if test="${news.type == 'news' }">
							最新资讯
						</c:if>
						<c:if test="${news.type == 'direct' }">
							就业指导
						</c:if>
                     </span>
          		</div>
          		<div class="PublicframeTwoHeadBarRight"></div>
          		<div class="PublicframeTwoHeadBarMore">
          			<span class="PublicframeTwoHeadBarMoreSpan"></span>
          		</div>
          	</div>
          	<div class="PublicframeTwoContent" id="">
          		<table style="line-height: 20px;padding-bottom: 10px;">
							<tbody>
								<c:forEach items="${newsList }" var="news">
									<tr>
										<td><div style="width: 150px;white-space: nowrap;text-overflow: ellipsis; -o-text-overflow: ellipsis; overflow: hidden;">
												<a href="${contextPath }/news/getOneForShow?id=${news.id}" title="${news.title }">
												<img src="${contextPath}/images/Sy_HRsqImage/Square.jpg" style="padding-left: 8px;line-height: 25p;text-align: left;" />
												<span style="margin-left:0px;">${news.title }</span> 
												</a>
											</div></td>
										<td style="width:80px">
											<c:if test="${news.createDate != null}">
												<fmt:formatDate value="${news.createDate}" type="both" dateStyle="long" pattern="yyyy-MM-dd" var="createDate" />
											</c:if> 
											<span style="margin-right:0px;">${createDate }</span>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>   	
     		</div>          	
          	<div style="clear: both"></div>
          </div>
       </div>
     </div>
   </div>
	<%--
	<div id="xBody">
		<!--头部结束--->
		<div id="xdAL" style="width: 1px;"></div>
		<!--内容页面开始-->
		<div class="imga">
			<a href="${contextPath }/index" title="招聘频道"><img src="${contextPath}/images/c2e34940b08940b48f3255b493b4ba68_zph_2013_ad_full.gif" alt="招聘频道" width="980" height="80"
				border='0'>
			</a>
		</div>

		<!-- 中间部分div -->
		<div style="width: 980px; margin-top: 10px; height: 100%;">
			<div style="width: 710px; float: left; height: 100%; border-left: 1px solid #b3cee3;
		                border-right: 1px solid #b3cee3; border-bottom: 1px solid #b3cee3">
				<div
					style="width: 710px; height: 37px; line-height: 37px; background-image: url(${contextPath}/images/menu_bg_r16_c7.jpg);
		                    color: #0980df; font-family: 微软雅黑; overflow: hidden">
					<div
						style="color: #ffffff; font-family: 宋体; overflow: hidden; text-align: center;
		                        padding-top: 2px; margin-left: 10px; font-weight: bold; width: 120px; font-size: 13px;
		                        background: url(${contextPath}/images/menu_bg_r16_c2.jpg) no-repeat left top">
						<c:if test="${news.type == 'news' }">
							最新资讯
						</c:if>
						<c:if test="${news.type == 'direct' }">
							就业指导
						</c:if>
					</div>
				</div>
				<div style="width: 710px;">
					<div class="news_title">
						<span class="h1"> ${news.title }</span>
					</div>
					<!--news_title-->
					<div class="news_info" style="background: url(${contextPath}/images/dot.gif) repeat-x 50% top;">
						<c:if test="${news.createDate != null}">
							<fmt:formatDate value="${news.createDate }" type="both" dateStyle="long" pattern="yyyy-MM-dd hh:mm:ss" var="createDate" />
						</c:if> 
						<div class="news_info_l">时间:${createDate }</div>
						<div class="news_info_c">责任编辑:${news.author }</div>
						<div class="news_info_r">来源:${news.source }</div>
					</div>
					<!--news_title-->
					<div class="news_contain" style="font-size: 13px">
						<p>${news.content }</p>
					</div>
					<!--news_contain-->
					<div class="page_neirong">
						<ul>
						</ul>
					</div>
					<!--news_fenye-->
					<div class="news_ln">
						<!-- 	<div class="news_ln_l">
							没有了 <br> 下一篇：<a href="show_4.html">qqqq</a>
						</div>
						<div class="news_ln_r"></div>
						 -->
					</div>
				</div>
			</div>
			<div style="width: 260px; float: right; height: 600px">
				<div style="width: 254px; float: right; border: 1px solid  #9CC7E7">
					<div style="width: 232px; height: 29px; background-image: url(${contextPath}/images/menu_bg_r17_c19.jpg);
		                        padding-left: 20px; padding-top: 5px; color: #0157B4">
						<strong>
							<c:if test="${news.type == 'news' }">
								最新资讯
							</c:if>
							<c:if test="${news.type == 'direct' }">
								就业指导
							</c:if>
						</strong>
					</div>
					<div style="width: 252px;">
						<table style="line-height: 20px">
							<tbody>
								<c:forEach items="${newsList }" var="news">
									<tr>
										<td><div style="width: 150px;white-space: nowrap;text-overflow: ellipsis; -o-text-overflow: ellipsis; overflow: hidden;">
												<a href="${contextPath }/news/getOneForShow?id=${news.id}" title="${news.title }"><span style="margin-left:10px;">${news.title }</span> </a>
											</div></td>
										<td style="width:80px">
											<c:if test="${news.createDate != null}">
												<fmt:formatDate value="${news.createDate}" type="both" dateStyle="long" pattern="yyyy-MM-dd" var="createDate" />
											</c:if> 
											<span style="margin-right:0px;">${createDate }</span>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>


			</div>
		</div>

		<div style="clear: both;"></div>
	</div>
		--%>
	<jsp:include page="../formatter/footer.jsp" />
</body>
</html>