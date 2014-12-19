<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>残疾人就业信息网</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="keywords" content="残疾人招聘信息,残疾人就业信息,残疾人人才网,残疾人找工作" />
	<meta content="残疾人招聘就业" name="description" />
	
	<link href="${contextPath}/css/Public.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/HomePageHeader.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/HomePageFooter.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/PublicStatusBar.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/SetHeaderStyle.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/PublicframeFour.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/PublicTableOne.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/PublicAccordion.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/ManagePositions.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/comp_info_edit.css" rel="stylesheet" type="text/css" />
	
	<script type="text/javascript" src="${contextPath}/js/jquery.js"></script>
	<script type="text/javascript" src="${contextPath}/js/common.js"></script>
	<script type="text/javascript" src="${contextPath}/js/check/company-check.js"></script>
	<script type="text/javascript">
	</script>
</head>
<body>
	<div id="container" class="container">
	
		<!-- 顶部工具条栏 -->
		<jsp:include page="../formatter/status-bar.jsp" />
		
		<!-- 头部导航及图片栏目 -->
		<jsp:include page="../formatter/header.jsp" />
		<div class="clearboth"></div>
		
		<!-- ******* 中部内容显示区 ******* start ********** -->
		<div id="content" class="content">
			
			<!-- 上部区域xx中心提示文字 -->
            <div class="positiontopbg">
                <div class="positiontopleft">
                    <span style="cursor:pointer;" onclick="javascript:window.location.href = '${contextPath}/user/goCenter'">企业管理中心</span></div>
                <div class="positiontopright">
                </div>
            </div>
            
            <!-- 下部具体内容显示   start -->
            <div class="positionbottom">
                <div class="positionleftline">
                </div>
                
                <!----------- 管理中心左边菜单栏  ------start ----------->
                <jsp:include page="left-nav.jsp" />
                <!----------- 管理中心左边菜单栏  ------end ----------->
                
                <div class="positionrightline">
                </div>
                <!----------- 管理中心右边 详细内容显示  ------start ----------->
                <div id="main0" class="positionmiddletext">
                	<div class="form0" style="margin-bottom: -10px; _margin-bottom: -30px;">
                        <div style="width: 780px;" class="contraction" id="ModelOneID">
                            <div class="contractiontop">
                                <div class="contractionleft">
                                </div>
                                <div class="contractiontoptext">
                                    <span class="spanFirst">当前位置:</span><span class="spanSecond">企业管理中心 &gt;&gt; 控制面板 &gt;&gt; 修改企业信息</span></div>
                                <div class="contractionright">
                                </div>
                                <div class="contractionclick">
                                </div>
                            </div>
                        </div>
                    </div>
                	
                	<form action="${contextPath }/secure/company/update" method="post" onsubmit="return check();" id="companyInfo">
	                	<ul class="block">
	                        <li>
	                            <div class="form0">
	                                <div style="width: 780px;" class="contraction" id="ModelOneID">
	                                    <div style="display: block;" class="contractioncontent">
	                                        <table class="tbl1" id="EnterpriseinformationForm">
	                                            <tbody>
		                                            <tr>
		                                                <td class="textTop">
	                                                  		 <em style="display: inline;">* </em>单位名称：
		                                                </td>
		                                                <td>
		                                                	<input id="name" name="name" type="text" value="${company.name }" type="text" />
		                                                	<input type="hidden" name="id" value="${company.id }" />
		                                                </td>
		                                            </tr>
		                                             <tr>
		                                                <td class="textTop">
	                                                  		 <em style="display: inline;">* </em>法人代表：
		                                                </td>
		                                                <td>
		                                                	<input id="corporateRepresentative" name="corporateRepresentative" type="text" value="${company.corporateRepresentative }" type="text" />
		                                                </td>
		                                            </tr>
		                                             <tr>
		                                                <td class="textTop">
	                                                  		 组织机构代码：
		                                                </td>
		                                                <td>
		                                                	<input id="organizationCode" name="organizationCode" type="text" value="${company.organizationCode }" type="text" />
		                                                </td>
		                                            </tr>
		                                             <tr>
		                                                <td class="textTop">
	                                                  		 工商登记号码：
		                                                </td>
		                                                <td>
		                                                	<input id="commercialCode" name="commercialCode" type="text" value="${company.commercialCode }" type="text" />
		                                                </td>
		                                            </tr>
		                                             <tr>
		                                                <td class="textTop">
	                                                  		税务编码：
		                                                </td>
		                                                <td>
		                                                	<input id="taxCode" name="taxCode" type="text" value="${company.taxCode }" type="text" />
		                                                </td>
		                                            </tr>
		                                             <tr>
		                                                <td class="textTop">
	                                                  		 社保登记证号：
		                                                </td>
		                                                <td>
		                                                	<input id="socialSecurityCode" name="socialSecurityCode" type="text" value="${company.socialSecurityCode }" type="text" />
		                                                </td>
		                                            </tr>
		                                             <tr>
		                                                <td class="textTop">
	                                                  		 网站ID：
		                                                </td>
		                                                <td>
		                                                	<input id="webSiteId" name="webSiteId" type="text" value="${company.webSiteId }" type="text" />
		                                                </td>
		                                            </tr>
		                                             <tr>
		                                                <td class="textTop">
	                                                  		 市劳网号：
		                                                </td>
		                                                <td>
		                                                	<input id="laoWangCode" name="laoWangCode" type="text" value="${company.laoWangCode }" type="text" />
		                                                </td>
		                                            </tr>
		                                            <tr>
		                                                <td class="textTop">
		                                                    <em></em><em style="display: inline;">* </em>单位规模：
		                                                </td>
		                                                <td>
		                                                    <select name="scale" id="scale" class="length informationEdits needSyncValue NeedValidate" >
		                                                    	<c:forEach items="${params }" var="p">
																	<c:if test="${p.type == 'scale' }">
																		<option value="${p.value }" <c:if test="${company.scale == p.value }">selected="selected"</c:if>>${p.name }</option>
																	</c:if>
																</c:forEach>
		                                                    </select>
		                                                </td>
		                                            </tr>
		                                            <tr>
		                                                <td class="textTop">
		                                                	单位性质：
		                                                </td>
		                                                <td>
															<select name="nature" id="nature">
																<c:forEach items="${params }" var="p">
																	<c:if test="${p.type == 'companyNature' }">
																		<option value="${p.value }" <c:if test="${company.nature == p.value }">selected="selected"</c:if>>${p.name }</option>
																	</c:if>
																</c:forEach>
															</select>
		                                                </td>
		                                            </tr>
		                                            <tr>
		                                                <td class="textTop">
		                                                	经济类型：
		                                                </td>
		                                                <td>
															 <select name="economyType" id="economyType">
															 	<c:forEach items="${params }" var="p">
																	<c:if test="${p.type == 'economyType' }">
																		<option value="${p.value }" <c:if test="${company.economyType == p.value }">selected="selected"</c:if>>${p.name }</option>
																	</c:if>
																</c:forEach>
															</select>
		                                                </td>
		                                            </tr>
		                                            <tr>
		                                                <td class="textTop">
		                                                    <em style="display: inline;">* </em>所属行业：
		                                                </td>
		                                                <td>
		                                                	<select name="businessScope.id" id="businessScope.id">
															 	<c:forEach items="${bsList }" var="bs">
																	<option value="${bs.id }" <c:if test="${company.businessScope.id == bs.id}">selected="selected"</c:if>>${bs.name }</option>
																</c:forEach>
															</select>
		                                                </td>
		                                            </tr>
		                                            <tr>
		                                                <td class="textTop">
		                                                    <em></em><em style="display: inline;">* </em>单位介绍：
		                                                </td>
		                                                <td>
		                                                    <textarea name="introduction" rows="2" cols="20" style="height:80px;width:619px;font-size: 12px; padding: 6px;">${company.introduction }</textarea>
		                                                </td>
		                                            </tr>
		                                        </tbody></table>
		                                    </div>
		                                </div>
		                                <div class="clearboth">
		                                </div>
		                                <div class="contraction" style="width: 780px;padding-bottom:20px;">
		                                    <div class="contractiontop">
		                                        <div class="contractionleft">
		                                        </div>
		                                        <div class="contractiontoptext">
		                                            <span class="form_title">联系方式</span><span class="spanFirst">(*必填项)</span></div>
		                                        <div class="contractionright">
		                                        </div>
		                                        <div class="contractionclickone">
		                                        </div>
		                                    </div>
		                                    <div class="contractioncontent">
		                                        <table class="tbl1" id="Contactform">
		                                            <tbody><tr>
		                                                <td class="textTop">
		                                                    <em style="display: inline;">* </em>联 系 人：
		                                                </td>
		                                                <td style="border-right:0 none;">
		                                                	<input name="contactPerson" type="text" value="${company.contactPerson }" id="contactPerson" />
		                                                </td>
		                                                <td style="border-left:0 none;">
		                                                	&nbsp;
		                                                </td>
		                                            </tr>
		                                            <tr>
		                                                <td class="textTop">
		                                                    <em style="display: inline;">* </em>联系电话：
		                                                </td>
		                                                <td style="border-right:0 none;">
		                                                	<input name="telephone" type="text" value="${company.telephone }" id="telephone" />
		                                                </td>
		                                                <td style="border-left:0 none;">
		                                                	&nbsp;
		                                                </td>
		                                            </tr>
		                                            <tr>
		                                                <td class="textTop">
															联系部门：
		                                                </td>
		                                                <td style="border-right:0 none;">
		                                                	<input name="contactDept" type="text" value="${company.contactDept }" id="contactDept" />
		                                                </td>
		                                                <td style="border-left:0 none;">
		                                                	&nbsp;
		                                                </td>
		                                            </tr>
		                                            <tr>
		                                                <td class="textTop">
															传真：
		                                                </td>
		                                                <td style="border-right:0 none;">
		                                                	<input name="fax" type="text" value="${company.fax }" id="fax" />
		                                                </td>
		                                                <td style="border-left:0 none;">
		                                                	&nbsp;
		                                                </td>
		                                            </tr>
		                                            <tr>
		                                                <td class="textTop">
		                                                    <em style="display: inline;">* </em>邮箱：
		                                                </td>
		                                                <td style="border-right:0 none;">
		                                                	<input name="email" type="text" value="${company.email }" id="email" />
		                                                </td>
		                                                <td style="border-left:0 none;">
		                                                	&nbsp;
		                                                </td>
		                                            </tr>
		                                            <tr>
		                                                <td class="textTop">
															公司地址：
		                                                </td>
		                                                <td style="border-right:0 none;">
		                                                	<input name="address" type="text" value="${company.address }" id="address" />
		                                                </td>
		                                                <td style="border-left:0 none;">
		                                                	&nbsp;
		                                                </td>
		                                            </tr>
		                                        </tbody>
											</table>
	                                    </div>
	                                    
	                                    <div class="clearboth" style="height: 10px;">
	                                    </div>
	                                    <input class="saveBtn" id="SaveAllBtn" type="button" />
	                                </div>
	                            </div>
	                        </li>
	                    </ul>
					</form>
                    
                    <div class="clearboth">
                    </div>
                </div>
                <!----------- 管理中心右边 详细内容显示  ------end ----------->
                
            </div>
            <!-- 下部具体内容显示   end -->
        




		</div>
		<!-- ******* 中部内容显示区 ******* end ********** -->
		
		<!-- 尾部footer区 -->
		<div class="clearboth"></div>
		<jsp:include page="../formatter/footer.jsp" />
	</div>
</body>
</html>