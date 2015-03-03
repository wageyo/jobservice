<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<div style="width:100%">
	<table style="width:100%" >
		<!-- 显示能够匹配上的职位信息 -->
		<thead >
			<tr >
				<th colspan="10">
					匹配结果:
					<c:choose>
						<c:when test="${matchedNumber != null }">${matchedNumber }</c:when>
						<c:otherwise>0</c:otherwise>
					</c:choose>个
				</th>
			</tr>
		</thead>
		<tbody >
			<tr >
				<td>
					序号
				</td>
				<td style="width:20%;">
					职位名称
				</td>
				<td style="width:22%;">
					单位名称
				</td>
				<td>
					招聘人数
				</td>
				<td>
					提供薪资
				</td>
				<td>
					联系电话
				</td>
				<td>
					岗位性质
				</td>
				<td>
					工作地
				</td>
			</tr>
			<!-- ①没有匹配上的信息时 -->
			<c:if test="${entityList == null || entityList[0] == null}">
				<tr style="">
					<td colspan="10" class="warning" style="font-size: 12px;text-align: center;color:red;">
						没有匹配上的招聘信息!
					</td>
				</tr>
			</c:if>
			<!-- ②有匹配信息时 -->
			<c:forEach items="${entityList }" var="entity" varStatus="status">
				<!-- 隔2行换色 -->
				<tr >
					<td>
						${status.index + 1  }
					</td>
					<td>
						<a href="${contextPath }/job/getOneForShow?id=${entity.id}" target="_blank">${entity.name }</a>
					</td>
					<td>
						<a href="${contextPath }/company/getOneForShow?id=${entity.company.id}" target="_blank">${entity.company.name }</a>
					</td>
					<td>
						${entity.hireNumber }
					</td>
					<td>
						${entity.salary }
					</td>
					<td>
						${entity.contactTel }
					</td>
					<td>
						${entity.nature }
					</td>
					<td>
						${entity.workPlace.name }
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>