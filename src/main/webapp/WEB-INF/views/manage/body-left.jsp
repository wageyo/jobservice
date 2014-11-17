<%@ page language="java" import="java.util.*" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="requestUrl" value="${pageContext.request.requestURL}" />
<style type="text/css">
	a{
		color:#251FAF;
	}
</style>
<div class="manage-body-left" style="background:url('${contextPath}/images/backdoor/ul_bg.jpg') repeat-y;">
	<div class="container-fluid" style="padding-left:0px;padding-right:0px;">
		<div class="row-fluid">
			<div class="span6" style="width:100%;">
				<ul class="nav nav-list">
				 	<li class="nav-header">
						功能列表
					</li> 
				<!--  	<li class="active">
						<a href="#">管理员账号管理</a>
					</li>	 --> 
					<c:forEach items="${menuList }" var="menu">
						
						<!-- 获取menu中的typeName字段 -->
						<li <c:if test="${fn:contains(requestUrl,menu.typeName) }">class="active"</c:if>>
							<a href="${menu.url }">${menu.text }</a>
						</li>
					</c:forEach>
				
				</ul>
			</div>
		</div>
	</div>
</div>