<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="keywords" content="残疾人招聘信息,残疾人就业信息,残疾人人才网,残疾人找工作" />
<meta content="残疾人招聘就业" name="description" />
<link href="${contextPath}/css/header.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/css/style.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/css/user/style.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/css/user/StyleSheet.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${contextPath}/js/jquery.js"></script>
<script type="text/javascript">
	function deletejob(id,title){
		var bl = window.confirm('确实要删除 '+title+' 这份简历吗?');
		if(bl){
			location.href = '${contextPath }/secure/job/delete?id=' + id;
		};
	}
</script>
<title>残疾人就业信息网</title>
</head>
<body>
	<jsp:include page="../header.jsp" />
	<jsp:include page="../nav.jsp" />

	<div class="w988 ptb10 center">
		<jsp:include page="left-nav.jsp" />

		<div class="Air">
			<table class="w770" border="0" cellspacing="0" cellpadding="0" align="center">
				<tbody>
					<tr valign="top">
						<td>
							<table border="0" cellspacing="0" cellpadding="0" class="table_boder" align="center">
								<tbody>
									<tr>
										<td class="uu1" align="left" colspan="2"><b> <span id="ctl00_ContentPlaceHolder1_Label1">职位管理&gt;&gt;职位管理列表</span> </b>
										</td>
									</tr>
								</tbody>
							</table>
						</td>
					</tr>
				</tbody>
			</table>
			<div class="AntAccout_Center table_boder p10 mb10 center">
				<table width="100%" border="0" cellpadding="0" cellspacing="0" align="center">
					<tbody>
						<tr>
							<td align="center" class="line35" id="title55" width="80px" style="text-align:center; text-indent:0px;">序号</td>
							<td align="center" class="line35" id="title55" width="120px" style="text-align:center; text-indent:0px;">职位名称</td>
							<td align="center" class="line35" id="title55" width="115px" style="text-align:center; text-indent:0px;">创建时间</td>
							<td align="center" class="line35" id="title55" width="115px" style="text-align:center; text-indent:0px;">更新时间</td>
							<td align="center" class="line35" id="title55" width="100px" style="text-align:center; text-indent:0px;">审核状态</td>
							<td align="center" class="line35" id="title55" style="text-align:center; text-indent:0px;">操作</td>
						</tr>
						<c:forEach items="${jobList }" var="job" varStatus="i">
							<tr style="border-bottom: 1px solid rgb(164, 218, 183);">
								<td align="center" height="30px;">${i.index + 1 }</td>
								<td align="center"><a href="${contextPath }/job/getOneForShow?id=${job.id}">${job.name }</a>
								</td>
								<c:if test="${job.createDate!=null}">
									<fmt:formatDate value="${job.createDate}" type="both" dateStyle="long" pattern="yyyy-MM-dd HH:mm" var="createDate" />
								</c:if>
								<td align="center">${createDate }</td>
								<c:if test="${job.updateDate!=null}">
									<fmt:formatDate value="${job.updateDate}" type="both" dateStyle="long" pattern="yyyy-MM-dd HH:mm" var="updateDate" />
								</c:if>
								<td align="center">${updateDate }</td>
								<td align="center">${job.checkStatus }</td>
								<td align="center"><a href="${contextPath }/secure/job/update?id=${job.id}">修改</a>&nbsp;&nbsp; <a href="javascript:void(0);" onclick="deletejob(${job.id},'${job.name }')">删除</a>
								</td>
							</tr>
						</c:forEach>
						<!-- 分页 -->
						<tr>
							<td colspan="5">总计有 ${totalCount }条记录 <c:forEach begin="1" end="${totalPages }" var="p">
									<c:if test="${p == currentPage }">
										<b>${p }</b>
									</c:if>
									<c:if test="${p != currentPage }">
										<a href="${contextPath }/secure/job/getManage?page=${p}">${p }</a>
									</c:if>
								</c:forEach>
							</td>
						</tr>
					</tbody>
				</table>
				
			</div>
		</div>

		<div style="clear: both;"></div>
	</div>

	<jsp:include page="../footer.jsp" />
	
	<c:if test="${notice == 'success' }">
		<script type="text/javascript">
			alert('操作成功!');
		</script>
	</c:if>

</body>
</html>