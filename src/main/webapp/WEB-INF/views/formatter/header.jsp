<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<div id="header" class="HeaderStyle SetHeaderBg">
	<div class="HeaderMainBg SetHeaderWholeBg">
		<div class="HeaderMainLeft SetHeaderLeft">
			<div class="logo">
				<a title="残疾人就业服务网" href="index.htm?crumbs=001"> 
					<img id="imgLogo" src="${contextPath}/images/Logo.png" />
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
					<li><a id="7" class="SelectStyle" name="" title="首页"
						href="${contextPath}/index">首页</a></li>
					<li><a id="165" title="个人求职" href="${contextPath}/emp">个人求职</a>
					</li>
					<li><a id="166" title="单位招聘" href="${contextPath}/work">单位招聘</a>
					</li>
					<li><a id="164" title="服务机构" href="${contextPath}/direct">就业指导</a>
					</li>
					<li><a id="167" title="就业服务" href="${contextPath}/news">最新资讯</a>
					</li>
					<li><a id="192" title="职业培训" href="${contextPath}/contact">联系我们</a>
					</li>
					<li><a id="168" title="就业政策" href="${contextPath}/about">关于本站</a>
					</li>
				</ul>
			</div>
		</div>

	</div>
</div>