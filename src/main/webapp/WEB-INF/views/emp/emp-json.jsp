<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<div id="data" style="display: none">
  <div class="list11">
	<table style="width:988px;" border="0" cellpadding="0" cellspacing="0">
		<c:forEach items="${entity.list}" var="item" varStatus="i">
		  <c:if test="${(i.index + 1)%2==0 }">
		      <tr class="list11" style="border-bottom: 1px solid #c0c0c0;background:#f6f6f6;">
				<td width="90px" class="s13_blue_b" id="tabaleDataStyle" >
					 <input type="hidden" name="id" value="${item.id }"/>
					 <span id="Repeater1_ctl00_Label2" >${item.title}</span>
				</td>
				<td width="70px" class="s13_blue_b">
					<a href='${contextPath }/resume/getOneForShow?id=${item.id}'>
					<span id="Repeater1_ctl00_Label1" style="color: #0868C8;font-weight: bold;">${item.name}</span> </a>
				</td>
				<td width="50px" class="s13_blue_b" id="tabaleDataStyle">
					 <span id="Repeater1_ctl00_Label2" >${item.gender}</span>
				</td>
				<td width="80px" class="s13_blue_b" id="tabaleDataStyle">
					<span id="Repeater1_ctl00_Label3">${item.education}</span>
				</td>
				<td width="70px" class="s13_blue_b" id="tabaleDataStyle">
					<span id="Repeater1_ctl00_Label6">${item.major}</span>
				</td>
				<td width="80" class="s13_blue_b" id="tabaleDataStyle">
					<span id="Repeater1_ctl00_Label7">${item.experience}</span>
				</td>
				<td width="100" class="s13_blue_b" id="tabaleDataStyle">
					<span id="Repeater1_ctl00_Label7">${item.desireJob}</span>
				</td>
				<td width="100" class="s13_blue_b" id="tabaleDataStyle">
					<span id="Repeater1_ctl00_Label7">${item.desireAddress}</span>
				</td>
				<td width="80" class="s13_blue_b" id="tabaleDataStyle">
					<span id="Repeater1_ctl00_Label7">${item.desireSalary}</span>
				</td>
			</tr>
		</c:if>
		<c:if test="${(i.index + 1)%2!=0 }">
		   <tr class="list11" style="border-bottom: 1px solid #c0c0c0;">
			<td width="90px" class="s13_blue_b" id="tabaleDataStyle" >
				 <input type="hidden" name="id" value="${item.id }"/>
				 <span id="Repeater1_ctl00_Label2" >${item.title}</span>
			</td>
			<td width="70px" class="s13_blue_b">
				<a href='${contextPath }/resume/getOneForShow?id=${item.id}'>
				 	<span id="Repeater1_ctl00_Label1" style="color: #0868C8;font-weight: bold;">${item.name}</span> 
				 </a>
			</td>
			<td width="50px" class="s13_blue_b" id="tabaleDataStyle">
				 <span id="Repeater1_ctl00_Label2" >${item.gender}</span>
			</td>
			<td width="80px" class="s13_blue_b" id="tabaleDataStyle">
				<span id="Repeater1_ctl00_Label3">${item.education}</span>
			</td>
			<td width="70px" class="s13_blue_b" id="tabaleDataStyle">
				<span id="Repeater1_ctl00_Label6">${item.major}</span>
			</td>
			<td width="80" class="s13_blue_b" id="tabaleDataStyle">
				<span id="Repeater1_ctl00_Label7">${item.experience}</span>
			</td>
			<td width="100" class="s13_blue_b" id="tabaleDataStyle">
				<span id="Repeater1_ctl00_Label7">${item.desireJob}</span>
			</td>
			<td width="100" class="s13_blue_b" id="tabaleDataStyle">
				<span id="Repeater1_ctl00_Label7">${item.desireAddress}</span>
			</td>
			<td width="80" class="s13_blue_b" id="tabaleDataStyle">
				<span id="Repeater1_ctl00_Label7">${item.desireSalary}</span>
			</td>
		</tr>
	   </c:if>
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