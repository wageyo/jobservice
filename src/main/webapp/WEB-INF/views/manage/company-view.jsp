<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<br /><%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<div class="jobView manage">

	<div class="viwe-title">查看企业信息</div>
	<div id="company-view" style="margin: auto;width: 1000px">
		<dl>
			<dt>公司名称:</dt>
			<dd id="name" class="longText"></dd>
		</dl>

		<dl>
			<dt>法人:</dt>
			<dd id="corporateRepresentative"></dd>
			
			<dt> 联系人:</dt>
			<dd id="contactPerson"></dd>

			<dt>联系电话:</dt>
			<dd id="telephone"></dd>
		</dl>

		<dl>
			<dt> 联系部门:</dt>
			<dd id="contactDept"></dd>
			
			<dt>传真:</dt>
			<dd id="fax"></dd>
				<dt>公司邮箱:</dt>
				<dd id="email"></dd>
		</dl>

		<dl>
				
				<dt>组织机构代码:</dt>
				<dd id="organizationCode"></dd>
				<dt>工商登记号码:</dt>
				<dd id="commercialCode"></dd>
			<dt>税务编码</dt>
				<dd id="taxCode"></dd>
			</dl>
			<dl>
				<dt>社保登记证号:</dt>
				<dd id="socialSecurityCode"></dd>
				<dt>网站ID:</dt>
				<dd id="webSiteId"></dd>

				<dt>市劳网号:</dt>
				<dd id="laoWangCode"></dd>
			</dl>
			<dl>
				<dt>企业规模:</dt>
				<dd id="scale"></dd>
				<dt>企业性质:</dt>
				<dd id="nature"></dd>

				<dt> 经济类型:</dt>
				<dd id="economyType"></dd>
			</dl>
			<dl>
					<dt>公司详细地址:</dt>
				<dd id="address" class="longText"></dd>
			</dl>
	
		<dl>
			<dt>公司简介:</dt>
			<dd>
				<div class="textField"  id="introduction">
				</div>
			</dd>
		</dl>
		<dl>
			<dt>备注:</dt>
			<dd>
				<div class="textField"  id="remark">
				</div>
			</dd>
		</dl>
	</div>
</div>


