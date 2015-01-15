<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<div id="data${companyid}" style="display: none">
<table style="width:100%" >
<%--第二个职位表 是否为空							--%>
										<c:if test="${entityList[0] != null}">							
										<thead >
										<tr style="background-color:#c3dde0">
										<%--<th >
									  		<input id="jobcheckbox" type="checkbox" />  
										</th>
										--%>
										<th >
											职位序号
										</th>
									<th width="190px">
											职位名称
										</th>
										<th >
											人数
										</th>
										<th >
											提供薪资
										</th>
										<th>
											岗位性质
										</th>
										<th>
											发布日期
										</th>
										<th>
											有效期至
										</th>
										<th width="190px">
											公司名称
										</th>
										<th>
											联系人
										</th>
										<th >
											联系电话
										</th>
										
										<%--<th>
											操作
										</th>
									--%></tr>
								</thead>
								<%--第二个职位表 数据							--%>
								<tbody >
									<c:forEach items="${entityList }" var="job" varStatus="vs">
										<!-- 隔2行换色 -->
											<tr style="background-color:#d4e3e5;">
										
											<td>
												${(vs.index + 1) }
											</td>
											<td>
												${job.name }
											</td>
											<td>
												${job.hireNumber }
											</td>
											<td>
												${job.salary }
											</td>
											<td>
												${job.nature }
											</td>
											<td>
												<fmt:formatDate value="${job.createDate }" dateStyle="long" pattern="yyyy-MM-dd HH:mm:ss" var="createDate"/>
												${createDate }
											</td>
											<td>
												<fmt:formatDate value="${job.effectiveTime }" dateStyle="long" pattern="yyyy-MM-dd HH:mm:ss" var="effectiveTime"/>
												${effectiveTime }
											</td>
											<td>
												${job.company.name }
											</td>
											<td>
												${job.contactPerson }
											</td>
											<td>
												${job.contactTel }
											</td>
											
										</tr>
										<%--第三个简历表 标题							--%>
										<thead>
									<tr style="background-color:#c3dde0">
										
										<th>
											简历序号
										</th>
										<th >
											简历名称
										</th>
										<th >
											姓名
										</th>
										<th>
											性别
										</th>
										<th>
											残疾证号
										</th>
										<th>
											残疾类别
										</th>
										<th>
											残疾等级
										</th>
										<th>
										  	期待薪资
										</th>
										<th>
										  	学历
										</th>
										<th>
											联系方式
										</th>
									
									</tr>
								</thead>
									<tbody>
										<%--第三个简历表 数据							--%>
									<!-- 没有数据返回时, 显示提示文字 -->
									<c:if test="${job.resumeResultList[0] == null}">
										<tr style="background-color:#d4e3e5;">
											<td colspan="10" class="warning" style="color:red; text-align:center;">
												没有找到你需要的数据喔!
											</td>
										</tr>
									</c:if>
									<%--第三个简历表 数据							--%>
									<c:forEach items="${job.resumeResultList }" var="entityCom" varStatus="rowCom">
										<!-- 隔2行换色 -->
										<tr style="background-color:#d4e3e5;">
										
											<td>
												${rowCom.index + 1  }
											</td>
											<td>
												${entityCom.title }
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
									</c:forEach>	<%--第三个简历表 数据							--%>
								</tbody>
					</c:forEach>
									</c:if><%--第二个职位表 是否为空							--%>
									</table>
</div>