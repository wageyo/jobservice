<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<br /><%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />


<div class="jobView manage" >
	<div class="viwe-title">查看职位信息</div>
	<div  id="job-view" style="margin: auto;width: 1000px">
		<dl>
			<dt>职位名称:</dt>
			<dd id="name" class="longText"></dd>
		</dl>

		<dl>
			<dt>招聘人数:</dt>
			<dd id="hireNumber"></dd>

			<dt>薪水:</dt>
			<dd id="salary"></dd>

			<dt>最低学历:</dt>
			<dd id="education"></dd>
		</dl>

		<dl>
			<dt>要求的经验年限:</dt>
			<dd id="experience"></dd>

			<dt>性别:</dt>
			<dd id="gender"></dd>
			
			<dt>联系人:</dt>
			<dd id="contactPerson"></dd>
		</dl>
		<dl>
			<dt>联系电话:</dt>
			<dd id="contactTel"></dd>
			<dt>邮箱:</dt>
			<dd id="contactEmail"></dd>
			<dt>职位性质:</dt>
			<dd id="nature"></dd>
		</dl>
		<dl>
			<dt>发布日期:</dt>
			<dd id="createDate"></dd>
			<dt>有效期:</dt>
			<dd id="effectiveTime"></dd>
			<dt>是否提供住宿:</dt>
			<dd id="bed"></dd>
		</dl>
		<dl>

			<dt>是否提供工作餐:</dt>
			<dd id="lunch"></dd>
		</dl>
		<dl>
			<dt>工资和其他福利:</dt>
			<dd >
					<div class="textField" id="provideBenefit"></div>
			</dd>
		</dl>
		
		
		<dl>
			<dt>岗位描述:</dt>
			<dd>
				<div class="textField" id="description"></div>

			</dd>
		</dl>
	</div>
</div>


