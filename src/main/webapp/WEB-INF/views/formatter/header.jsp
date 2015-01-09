<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="requestUrl" value="${pageContext.request.requestURL}" />
<jsp:useBean id="date" class="java.util.Date" />
<fmt:formatDate value="${date}" type="both" dateStyle="long" pattern="yyyyMMddHHmmss" var="now" />
<div id="header" class="HeaderStyle SetHeaderBg">
	<div class="HeaderMainBg SetHeaderWholeBg">
		<div class="HeaderMainLeft SetHeaderLeft">
			<div class="logo">
				<a title="残疾人就业服务网" href="${contextPath}/index"> 
					<!-- 如果能显示对应省市的则显示对应省份的logo, 否则显示全国的log -->
					<c:choose>
						<c:when test="${cookie.area.value != null && cookie.area.value != '' }">
							<c:choose>
								<c:when test="${cookie.area.value != '10000000' }">
									<img alt="残疾人就业信息网" src="${contextPath}/images/logoProvince/${cookie.area.value }.png?timestamp=${now}" /> 
								</c:when>
								<c:otherwise>
									<img alt="残疾人就业信息网" src="${contextPath}/images/logoProvince/10000000.png?timestamp=${now}" id="bg2" />
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
								<img alt="残疾人就业信息网" src="${contextPath}/images/logoProvince/10000000.png?timestamp=${now}" id="bg2" /> 
						</c:otherwise>
					</c:choose>
				</a>
			</div>
		</div>
		<div class="HeaderMainRight SetHeaderRight"></div>
	</div>
	<div class="HeaderMain">
		<div class="HeaderMainRight">
			<div class="HeaderMainRightUp"></div>
		</div>
		<div class="clearboth"></div>

		<div id="mainnav" class="NavigationBar SetNavigationBar">
			<div>
				<ul id="jsmenu">
					<li><a title="首页" <c:if test="${fn:contains(requestUrl,'index') }">class="SelectStyle"</c:if> href="${contextPath}/index">&nbsp;&nbsp;首&nbsp;&nbsp;页&nbsp;&nbsp;</a></li>
					<li><a title="个人求职" <c:if test="${fn:contains(requestUrl,'emp') }">class="SelectStyle"</c:if> href="${contextPath}/emp">个人求职</a></li>
					<li><a title="单位招聘" <c:if test="${fn:contains(requestUrl,'work') }">class="SelectStyle"</c:if> href="${contextPath}/work">单位招聘</a></li>
					<li><a title="服务机构" <c:if test="${fn:contains(requestUrl,'direct') }">class="SelectStyle"</c:if> href="${contextPath}/direct">就业指导</a></li>
					<li><a title="就业服务" <c:if test="${fn:contains(requestUrl,'news') }">class="SelectStyle"</c:if> href="${contextPath}/news">最新资讯</a></li>
					<li><a title="职业培训" <c:if test="${fn:contains(requestUrl,'contact') }">class="SelectStyle"</c:if> href="${contextPath}/contact">联系我们</a></li>
					<li><a title="就业政策" <c:if test="${fn:contains(requestUrl,'about') }">class="SelectStyle"</c:if> href="${contextPath}/about">关于本站</a></li>
				</ul>
			</div>
		</div>

	</div>
</div>