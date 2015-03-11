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
				<td style="width:15%;">
					职位名称
				</td>
				<td style="width:15%;">
					单位名称
				</td>
				<td>
					招聘人数
				</td>
				<td>
					残疾部位
				</td>
				<td>
					残疾等级
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
						<div class="exceedHidden" style="width:145px;">
							<a href="${contextPath }/job/getOneForShow?id=${entity.id}" target="_blank" title="${entity.name }">${entity.name }</a>
						</div>
					</td>
					<td>
						<div class="exceedHidden" style="width:145px;">
							<a href="${contextPath }/company/getOneForShow?id=${entity.company.id}" target="_blank" title="${entity.company.name }">${entity.company.name }</a>
						</div>
					</td>
					<td>
						${entity.hireNumber }
					</td>
					<td>
						<c:if test="${entity.disabilityPart  == null || entity.disabilityPart == ''}">暂无要求</c:if>
						${entity.disabilityPart }
						
					</td>
					<td>
						<c:if test="${entity.disabilityLevel  == null || entity.disabilityLevel == ''}">暂无要求</c:if>
						${entity.disabilityLevel }
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