<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<div class="StatusBar">
	<div class="StatusBarMain">
		<div class="left">
			<span>当前位置: </span> <span>首页</span>
		</div>
		<div class="right">
			<div class="Wellcome">您好, ${cookie.username.value }</div>
			<div class="ListBtn">
				<ul class="">
					<li class="">
						<a target="_blank" href="${contextPath }/contact" target="_blank">联系我们</a>
					</li>
					<!-- 没有用户登陆时/登陆的用户为个人用户 -->
					<c:if test="${(cookie.username.value == null || cookie.username.value == '') || (cookie.identity.value != null && cookie.identity.value != '' && cookie.identity.value == 'person')}">
						<li class="">|</li>
						<li class="">
							<a href="${contextPath }/">企业登录</a>
						</li>
					</c:if>
					<!-- 没有用户登陆时/登陆的用户为公司用户 -->
					<c:if test="${(cookie.username.value == null || cookie.username.value == '') || (cookie.identity.value != null && cookie.identity.value != '' && cookie.identity.value == 'company')}">
						<li class="">|</li>
						<li class="">
							<a href="HP_PersonLogin.aspx?crumbs=001003">个人登录</a>
						</li>
					</c:if>
					<c:if test="${cookie.username.value != null || cookie.username.value != ''}">
						<li class="">|</li>
						<li class="">
							<a href="${contextPath}/user/logout">安全退出</a>
						</li>
					</c:if>
			<!-- 		<li class="">|</li>
					<li class="">
						<a href="javascript:void(0)" id="showATB" style="font-weight:bold; color:#0298fc;" title="显示无障碍工具条" hightcontrastoledstyle="font-weight:bold; color:#0298fc;" class="" oledstyle="font-weight:bold; color:#0298fc;">无障碍工具条</a>
					</li>	 -->
				</ul>
			</div>
		</div>
	</div>
</div>

