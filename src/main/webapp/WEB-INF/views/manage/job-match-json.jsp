<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<div style="width:100%">
	<table style="width:100%" >
		<!-- 以下循环显示没个职位及其对应匹配上的招聘信息 -->
		<c:if test="${entityList[0] != null}">
			<c:forEach items="${entityList }" var="job" varStatus="vs">
				<!-- 显示单个职位的基本信息 -->
				<thead >
					<tr >
						<th>
							匹配结果:
							<c:choose>
								<c:when test="${entity.matchedNumber != null }">${entity.matchedNumber }</c:when>
								<c:otherwise>0</c:otherwise>
							</c:choose>个
						</th>
						<th >
							职位名称: ${job.name }
						</th>
						<th >
							招聘人数: ${job.hireNumber }
						</th>
						<th >
							提供薪资: ${job.salary }
						</th>
						<th>
							岗位性质: ${job.nature }
						</th>
						<th colspan="5">
							<fmt:formatDate value="${job.createDate }" dateStyle="long" pattern="yyyy-MM-dd HH:mm:ss" var="createDate"/>
							发布日期: ${createDate }
						</th>
					</tr>
				</thead>
				<!-- 循环显示匹配上的信息 -->
				<tbody >
					<!-- ①没有匹配信息时 -->
					<c:if test="${job.resumeResultList[0] == null}">
						<tr style="">
							<td colspan="10" class="warning" style="font-size: 12px;text-align: center;color:red;">
								没有匹配的简历!
							</td>
						</tr>
					</c:if>
					<!-- ②有匹配信息时 -->
					<c:forEach items="${job.resumeResultList }" var="entityCom" varStatus="status">
						<!-- 隔2行换色 -->
						<tr >
							<td>
								${status.index + 1  }
							</td>
							<td>
								<a href="${contextPath }/resume/getOneForShow?id=${entityCom.id}" target="_blank">${entityCom.title }</a>
							</td>
							<td>
								${entityCom.name }
							</td>
							<td>
								${entityCom.gender }
							</td>
							<td>
								${entityCom.disabilityCard }
							</td>
							<td>
								${entityCom.disabilityCategory }
							</td>
							<td>
								${entityCom.disabilityLevel }
							</td>
							<td>
								${entityCom.desireSalary }
							</td>
							<td>
								${entityCom.education }
							</td>
							<td>
								${entityCom.phone }
							</td><%--
							<td></td>
						--%></tr>
					</c:forEach>
				</tbody>
			</c:forEach>
		</c:if><%--第二个职位表 是否为空							--%>
	</table>
</div>