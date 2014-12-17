<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<div id="data" style="display: none">
	<div class="list11">
		<table width="990px" border="0" cellspacing="0" cellpadding="0">
		<c:forEach items="${entity.list}" var="item">
			<tr class="list11"><%--
				<td width="29px" height="30">&nbsp;</td>
				--%><td width="200px" class="s13_blue_b" id="tabaleDataStyle">
					<a href='${contextPath }/job/getOneForShow?id=${item.id}'> <span id="Repeater1_ctl00_Label1" style="color: #0868C8;">${item.name}</span> </a>
				</td>
				<td width="203px" class="s13_blue_b" id="tabaleDataStyle">
					 <a href="${contextPath }/company/getOneForShow?id=${item.companyid}"><span id="Repeater1_ctl00_Label2" >${item.company}</span></a>
				</td>
				<td width="122px" class="s13_blue_b" id="tabaleDataStyle">
					<span id="Repeater1_ctl00_Label3" >${item.area}</span>
				</td>
				<td width="145px" class="s13_blue_b" id="tabaleDataStyle">
					<span id="Repeater1_ctl00_Label6" >${item.experience}</span>
				</td>
				<td width="150px" class="s13_blue_b" id="tabaleDataStyle">
					<span id="Repeater1_ctl00_Label7" >${item.date}</span>
				</td>
			</tr>
		</c:forEach>
		</table>
	</div>

	<div style="padding:15px;" class="pages0">
		<span id="lbCount" class="total">共${entity.pagination.records}条记录</span> 
		<span id="lbTotalPage" class="total">总页:${entity.pagination.countPages}页</span> 
		<span id="lbCurPage" class="total">当前页:第${entity.pagination.currentPage}页</span> 
		<c:if test="${entity.pagination.currentPage==1 }">
			<a id="LBUP"  class="total" href="javascript:common.pagination(1);">上页</a>
		</c:if>
		<c:if test="${entity.pagination.currentPage>1 }">
			<a id="LBUP"  class="total" href="javascript:common.pagination(${entity.pagination.currentPage-1 });">上页</a>
		</c:if>
		<c:forEach begin="${entity.pagination.start }" end="${entity.pagination.end }" var="p">
			<c:if test="${entity.pagination.currentPage==p}">
				<a id="LB${p}" class="current" href="javascript:common.pagination(${p});">${p}</a>
			</c:if>
			<c:if test="${entity.pagination.currentPage!=p}">
					<a id="LB${p}" href="javascript:common.pagination(${p});">${p}</a>
			</c:if>
		</c:forEach>
		<c:if test="${entity.pagination.currentPage<entity.pagination.countPages}">
			<a id="LBNEXT" class="total" href="javascript:common.pagination(${entity.pagination.currentPage+1 });">下页</a>
		</c:if>
		<c:if test="${entity.pagination.currentPage==entity.pagination.countPages }">
			<a id="LBNEXT" class="total" href="javascript:common.pagination(${entity.pagination.countPages });">下页</a>
		</c:if>
		<span class="total">到第 
			<select name="LbDPG" onchange="javascript:common.pagination(this.selectedIndex+1)" id="LbDPG">
				<c:forEach begin="1" end="${entity.pagination.countPages }" var="p">
					<option <c:if test="${entity.pagination.currentPage==p}">selected="selected"</c:if> value="${p}">${p}</option>
				</c:forEach>
		</select> 页 </span>
	</div>
</div>