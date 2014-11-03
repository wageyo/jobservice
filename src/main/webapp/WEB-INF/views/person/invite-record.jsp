<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
		<title>残疾人招聘就业网</title>
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
			                                    <td class="uu1" align="left" colspan="2">
			                                        <b>
			                                            <span id="ctl00_ContentPlaceHolder1_Label1">应聘管理&gt;&gt;我的面试邀请记录</span></b>
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
			                    	 <td align="center" class="line35" id="title55" width="80px" style="text-align:center; text-indent:0px;">
			                          	  序号
			                        </td>
			                        <td align="center" class="line35" id="title55" width="150px" style="text-align:center; text-indent:0px;" >
			                           	 受邀岗位
			                        </td>
			                        <td align="center" class="line35" id="title55" width="150px" style="text-align:center; text-indent:0px;" >
			                        	职位薪水
			                        </td>
			                        <td align="center" class="line35" id="title55" width="150px" style="text-align:center; text-indent:0px;" >
			                        	职位联系人
			                        </td>
			                        <td align="center" class="line35" id="title55" style="text-align:center; text-indent:0px;">
			                        	职位联系电话
			                        </td>
			                    </tr>
			                    <c:forEach items="${recordList }" var="record" varStatus="i">
			                    	<tr style="border-bottom: 1px solid rgb(164, 218, 183);">
				                    	<td align="center" height="30px;">
				                          	  ${i.index + 1 }
				                        </td>
				                        <td align="center">
				                           	 <a href="${contextPath }/job/getOneForShow?id=${record.jID}">${record.jName }</a>
				                        </td>
				                        <td align="center">
				                        	${record.jSalary }
				                        </td>
				                        <td align="center">
				                        	${record.jContactPerson }
				                        </td>
				                        <td align="center">
				                        	${record.jContactTel }
				                        </td>
				                    </tr>
			                    </c:forEach>
			                    <tr>
			                    	<td colspan="5">
			                    		总计有 ${totalCount }条记录
			                    		<c:forEach begin="1" end="${totalPages }" var="p">
			                    			<c:if test="${p == currentPage }">
			                    				<b>${p }</b>
			                    			</c:if>
			                    			<c:if test="${p != currentPage }">
			                    				<a href="${contextPath }/secure/resume/getSentJob/${p}">${p }</a>
			                    			</c:if>
			                    		</c:forEach>
			                    	</td>
			                    </tr>
			                </tbody>
			            </table>
			        </div>
		        
		        
		    </div>
	
	
		</div>
	
		<jsp:include page="../footer.jsp" />
		<c:if test="${notice == 'success' }">
			<script type="text/javascript">
				alert('操作成功!');
			</script>
		</c:if>

	</body>
</html>