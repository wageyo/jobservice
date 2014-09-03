<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<br />
&nbsp;&nbsp;&nbsp;
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />


<script type="text/javascript" charset="utf-8" src="${contextPath}/js/manage/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${contextPath}/js/manage/ueditor/ueditor.all.js"></script>
<script type="text/javascript" charset="utf-8" src="${contextPath}/js/manage/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" charset="utf-8" src="${contextPath}/js/manage/news.js"></script>



<div class="news-list-editor">
<dl >
		<dt class="publish-title">
			<span id="newsTitle"></span> <input type="hidden" id="newsId" value="<%=request.getAttribute("id") %>" />
		</dt>
	</dl>
	<dl >
		<dt>
			标题：<input  id="newsEditor-title"  type="text" />
		</dt>
	</dl>
	<dl>
		<dt>
			作者：<input   id="newsEditor-author" type="text" />
		</dt>

	</dl>
		<dl>
		<dt>
			来源：<input id="newsEditor-source"  type="text" />
		</dt>

	</dl>
			<dl>
		<dt style="float: left;">
			文章类型：
			<select  id="newsEditor-type" >
				<option value="direct">就业指导</option>
				<option value="news">最新资讯</option>
			</select>
		</dt>
		<dt>
			<button style="float: right;" id=""  onclick="newsEditor.back()">返回</button>
			<button style="float: right;" id="newSubmitBut" onclick="newsEditor.submit()">发布</button>
		</dt>
	</dl>
	<dl>
		<dt>
				<script id="news-editor" type="text/plain" style="width:900px;height:250px;"></script>
			   
		</dt>
	</dl>
	
</div>
	
<script type="text/javascript">
	newsEditor.init();
</script>
