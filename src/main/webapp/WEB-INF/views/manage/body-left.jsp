<%@ page language="java" import="java.util.*" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<div class="manage-body-left">
	<div class="container-fluid" style="padding-left:0px;padding-right:0px;">
		<div class="row-fluid">
			<div class="span6" style="width:100%;">
				<ul class="nav nav-list">
				 	<li class="nav-header">
						功能列表
					</li> 
				<!-- 	<li class="active">
						<a href="#">管理员账号管理</a>
					</li>  -->
					<c:forEach items="${menuList }" var="menu">
						<li>
							<a href="${menu.url }">${menu.text }</a>
						</li>
					</c:forEach>
				
				</ul>
			</div>
		</div>
	</div>
</div>