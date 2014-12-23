<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<style type="text/css">
	.littletitle{
		font-weight: bold;
		letter-spacing: 3px;
		color: rgb(94, 168, 156);
		padding-left: 8px;
		font-size:15px;
	}
</style>
<div class="positionleftbg">
    <div class="positionlefttext BlueNoUnderlineHoverNoUnderlineRed">
        <div id="tabs0" class="positiondirectory  positionstyle">
            <ul id="menu0">
			<!-- 	<li class="hover" ><a>职位管理</a></li>	 -->
			
                <li class="littletitle" style="background: url('${contextPath}/images/Sy_HRsqImage/csg-4f684958a4008.png') no-repeat scroll 4px 4px transparent;" >简历管理</li>
                <li class="" ><a href="${contextPath }/secure/resume/save" title="添加简历">添加简历</a></li>
                <li class="" ><a href="${contextPath }/secure/resume/getManage" title="我的简历">我的简历</a></li>
                <li class="littletitle" style="background: url('${contextPath}/images/Sy_HRsqImage/csg-4f684958a4008.png') no-repeat scroll 4px -76px transparent;" >应聘管理</li>
                <li class="" ><a href="${contextPath }/secure/resume/getSentJob/1" title="我的应聘记录">我的应聘记录</a></li>
                <li class="" ><a href="${contextPath }/secure/resume/getReceivedInvite/1" title="面试邀请通知">面试邀请通知</a></li>
                <li class="littletitle" style="background: url('${contextPath}/images/Sy_HRsqImage/csg-4f684958a4008.png') no-repeat scroll 4px -156px transparent;" >控制面板</li>
                <li class="" ><a href="${contextPath }/secure/user/passWordEdit" title="修改登录密码">修改登录密码</a></li>
            </ul>
        </div>
    </div>
</div>