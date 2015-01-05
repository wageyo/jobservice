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
			<div class="Wellcome">您好, <c:if test="${cookie.password.value != null && cookie.password.value != ''}">
			${cookie.username.value }
			</c:if></div>
			<div class="ListBtn">
				<ul class="">
					<li class="">
						<a target="_blank" href="${contextPath }/contact" >联系我们</a>
					</li>
					<!-- 没有用户登陆时/登陆的用户为个人用户时 -->
					<c:if test="${(cookie.password.value  == null ||cookie.password.value == '') || (cookie.identity.value != null && cookie.identity.value != '' && cookie.identity.value == 'person')}">
						<li class="">|</li>
						<li class="">
							<a href="${contextPath }/loginC">企业登录</a>
						</li>
					</c:if>
					<!-- 没有用户登陆时/登陆的用户为公司用户时 -->
					<c:if test="${(cookie.password.value  == null || cookie.password.value  == '') || (cookie.identity.value != null && cookie.identity.value != '' && cookie.identity.value == 'company')}">
						<li class="">|</li>
						<li class="">
							<a href="${contextPath }/loginP">个人登录</a>
						</li>
					</c:if>
					<!-- 没有用户登陆时时-->
					<c:if test="${cookie.password.value  == null || cookie.password.value  == ''}">
						<li class="">|</li>
						<li class="">
							<a href="${contextPath }/loginManage/login" title="登陆管理后台" style="color:rgb(69, 69, 243);">登陆管理后台</a>
						</li>
					</c:if>
					<c:if test="${cookie.username.value != null && cookie.username.value != ''}">
					 <c:if test="${cookie.password.value != null && cookie.password.value != ''}">
						<li class="">|</li>
						<li class="">
							<a href="${contextPath}/user/logout">安全退出</a>
						</li>
						</c:if>
					</c:if>
				</ul>
			</div>
		</div>
	</div>
</div>

