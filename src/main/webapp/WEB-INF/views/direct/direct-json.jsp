<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<div id="data" style="display: none">
	<div style="width: 95%; margin-top:10px;">
		<ul style="padding-bottom: 0px; list-style-type: none; margin: 0px; padding-left: 0px;
                        padding-right: 0px; padding-top: 0px">
		<c:forEach items="${entity.list}" var="item">
			<c:if test="${item.id !=null }">
				<li class="Square" style="line-height: 25px; padding-left: 0px; ">
				<a href="${contextPath }/direct/getOneForShow?id=${item.id}" class="Empa"><span style="margin-left:20px;">${item.title }</span></a>
				<span style="float: right;margin-left:20px;">${item.createDate }</span>
				<span style="float: right;color:gray;">${item.areaName }</span>
			</li>
		</c:if>
		
		</c:forEach>
		</ul>
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