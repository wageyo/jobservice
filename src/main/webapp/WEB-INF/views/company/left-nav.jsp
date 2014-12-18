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
			
                <li class="littletitle" style="background: url('${contextPath}/images/Sy_HRsqImage/csg-4f684958a4008.png') no-repeat scroll 4px 4px transparent;" >职位管理</li>
                <li class="" ><a href="${contextPath }/secure/job/save" title="发布新职位">发布新职位</a></li>
                <li class="" ><a href="${contextPath }/secure/job/getManage?page=1" title="职位管理列表">已发布职位</a></li>
                <li class="littletitle" style="background: url('${contextPath}/images/Sy_HRsqImage/csg-4f684958a4008.png') no-repeat scroll 4px -76px transparent;" >应聘信息</li>
                <li class="" ><a href="${contextPath }/secure/company/getAllGotResume/1" title="简历收件箱">简历收件箱</a></li>
                <li class="littletitle" style="background: url('${contextPath}/images/Sy_HRsqImage/csg-4f684958a4008.png') no-repeat scroll 4px -156px transparent;" >控制面板</li>
                <li class="" ><a href="${contextPath }/secure/user/passWordEdit" title="修改登录密码">修改登录密码</a></li>
                <li class="" ><a href="${contextPath }/secure/company/update" title="修改企业信息">修改企业信息</a></li>
            </ul>
        </div>
    </div>
</div>