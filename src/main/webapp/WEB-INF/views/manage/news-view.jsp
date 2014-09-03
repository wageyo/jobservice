<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<br /><%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />


<div class="jobView manage">
	<div class="viwe-title">查看文章</div>
	<div id="" style="margin: auto;width: 1000px">
		<dl>
			<dt>标题:</dt>
			<dd id="news-view-title" class="longText"></dd>
		</dl>

		<dl>
			<dt>作者:</dt>
			<dd id="news-view-author" class="longText"></dd>
		</dl>

		<dl>
			<dt>来源:</dt>
			<dd id="news-view-source" class="longText"></dd>
		</dl>

		<dl>
			<dt>文章类型:</dt>
			<dd id="news-view-type" class="longText"></dd>
		</dl>
		
		<dl>
			<dt>发布时间:</dt>
			<dd id="news-view-createDate" class="longText"></dd>
		</dl>

		<dl>
			<dt>正文:</dt>
			<dd>
				<div class="newtextField"  id="news-view-content"></div>

			</dd>
		</dl>



	</div>
</div>


