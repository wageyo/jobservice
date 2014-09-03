<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<div id="xFoot">
	<div>
		<a class="sotz" href="${contextPath }/index">首页</a> | 
		<a class="sotz" href="http://www.hrbesd.com" target="_blank">关于我们</a> | 
		<a class="sotz" href="#">意见反馈</a> | 
		<a class="sotz" href="${contextPath }/contact">联系我们</a> | 
		<a href="javascript:alert('建设中');" target="_blank" title="站长统计">站长统计</a> | 
		<a href="${contextPath }/loginmanage/login" title="登陆管理后台" style="color:blue;">管理后台</a>
		
	</div>
	<p>
		<div>© 2013 hrbesd.com, Inc. 哈尔滨亿时代数码科技开发有限公司, All rights reserved. 黑ICP备10202513号</div>
	</p>
</div>
<!--窗口-->
<div id="K60gz" style="display:none;">
	<!--登录弹窗-->
	<div class="pintu_K51">
		<div style="background-color:#eee;">
			<div class="pintu_K51wz">提示信息</div>
			<div style="float: right;">
				<a href="javascript:void(0);" class="alj" onclick="$('#K60gz').fadeOut();"><div style="background:url(${contextPath}/images/qt.gif) -18px -524px no-repeat; width:38px; height:38px;"></div> </a>
			</div>
			<div class="clear"></div>
		</div>
		<div style="text-align: center;padding: 50px 10px 50px 10px">
			<div class="orange">
				<h2>
					<c:if test="${messageType==0}">
						<img src="${contextPath}/images/error.gif" width="45" height="33" />
						<span id="ex_msg">${message}</span>
					</c:if>
					<c:if test="${messageType==null || messageType==1}">
						<img src="${contextPath}/images/zc_33.gif" width="45" height="33" />
						<span id="ex_msg">${message}</span>
					</c:if>
				</h2>
			</div>
		</div>
		<div class="zon_zc_aniu2" style="text-align: center;">
			<c:if test="${messageURL==null}">
				<input type="button" name="confirm" value="确  认" onclick="$('#K60gz').fadeOut();" />
			</c:if>
			<c:if test="${messageURL!=null}">
				<input type="button" name="confirm" value="确  认" onclick="javascript:window.top.location.href='${contextPath}${messageURL}'" />
			</c:if>
		</div>
	</div>
</div>
<!-- 窗口结束 -->
<c:if test="${message!=null}">
	<script type="text/javascript">
		var winWidth = $(window).width();
		var popWidth = $(".pintu_K51").css("width");
		var w = popWidth.replace("px", "");
		var left = (winWidth / 2) - (w / 2);
		$(".pintu_K51").css("left", left + "px");
		$("#K60gz").fadeIn();
		$("#K60gz input[name=confirm]").focus();
		//document.getElementById('K60gz').style.display = 'block';
	</script>
</c:if>