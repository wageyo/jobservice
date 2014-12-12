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
			<div class="Wellcome">您好</div>
			<div class="ListBtn">
				<ul>
					<li><a href="">联系我们</a></li>
					<li>|</li>
					<li><a id="showATB" title="显示无障碍工具条"
						style="font-weight:bold; color:#0298fc;" href="javascript:void(0)">无障碍工具条</a>
					</li>
				</ul>
			</div>
		</div>
	</div>
</div>

