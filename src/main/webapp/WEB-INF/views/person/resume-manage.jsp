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
	
	<script type="text/javascript" src="${contextPath}/js/jquery.js"></script>
	<script type="text/javascript" src="${contextPath}/js/common.js"></script>
	<script type="text/javascript">
		function deleteresume(id,title){
			var bl = window.confirm('确实要删除 '+title+' 这份简历吗?');
			if(bl){
				location.href = '${contextPath }/secure/resume/delete?id=' + id;
			}
			
		}
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
                                    <span class="spanFirst">当前位置:</span><span class="spanSecond">个人管理中心 &gt;&gt; 简历管理 &gt;&gt; 我的简历</span></div>
                                <div class="contractionright">
                                </div>
                                <div class="contractionclick">
                                </div>
                            </div>
                        </div>
                    </div>
                
                    <ul class="block zwgl">
                        <li>
                            <div class="contraction_gl">
                                <div class="PublicTableOne PublicTable " id="PublicTable_zwgl">
                                    <!--UpBar-->
                                    <div class="UpBar">
                                        <div class="Left">
                                            
                                            <div class="Buttom x">
                                            </div>
                                        </div>
                                    </div>
                                    <!--TableMain-->
                                    <div id="TableListShow"  >
                                    	<table class="TableMain"  id="TableList" cellpadding="0" cellspacing="0">
                                    		<tbody>
                                    			<tr class="TheaderBg">
                                    				<th class="firstTh">
                                    					<input class="CheckedAll" id="confirmedCB" type="checkbox" />
                                    				</th>
                                    				<th>序号</th>
                                    				<th>简历标题</th>
                                    				<th>创建时间</th>
                                    				<th>更新时间</th>
                                    				<th>审核状态</th>
                                    				<th>操作</th>
                                   				</tr>
                                   				<c:forEach items="${resumeList }" var="resume" varStatus="i">
													<tr>
														<td><input type="checkbox" /></td>
														<td>${i.index + 1 }</td>
														<td><a href="${contextPath }/resume/getOneForShow?id=${resume.id}">${resume.name }</a>
														</td>
														<c:if test="${resume.createDate!=null}">
															<fmt:formatDate value="${resume.createDate}" type="both" dateStyle="long" pattern="yyyy-MM-dd HH:mm" var="createDate" />
														</c:if>
														<td>${createDate }</td>
														<c:if test="${resume.updateDate!=null}">
															<fmt:formatDate value="${resume.updateDate}" type="both" dateStyle="long" pattern="yyyy-MM-dd HH:mm" var="updateDate" />
														</c:if>
														<td>${updateDate }</td>
														<td>${resume.checkStatus }</td>
														<td>
															<a href="${contextPath }/secure/resume/update?id=${resume.id}">修改</a>&nbsp;&nbsp; <a href="javascript:void(0);" onclick="deleteresume('${resume.id}','${resume.title }')">删除</a>
														</td>
													</tr>
												</c:forEach>
                                   			</tbody>
                                   		</table>
                                   	</div>
                                    <!--DownBar-->
                                    <div class="DownBar">
                                        <div class="Left">
                                        </div>
                                    <!--     <div id="pageList" class="Right">
                                        	总计有 ${totalCount }条记录
                                        	 <c:forEach begin="1" end="${totalPages }" var="p">
												<c:if test="${p == currentPage }">
													<b>${p }</b>
												</c:if>
												<c:if test="${p != currentPage }">
													<a href="${contextPath }/secure/resume/getManage?page=${p}">${p }</a>
												</c:if>
											</c:forEach> -->
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </li>
                    </ul>
                    
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