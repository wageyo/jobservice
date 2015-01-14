<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="keywords" content="残疾人招聘信息,残疾人就业信息,残疾人人才网,残疾人找工作" />
	<meta content="残疾人招聘就业" name="description" />
	
	<link rel="shortcut icon" href="${contextPath}/images/HomePageImage/favicon.ico" type="image/x-icon" />
	<link href="${contextPath}/css/Public.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/style_job.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/PublicStatusBar.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/HomePageHeader.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/SetHeaderStyle.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/HomePageFooter.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${contextPath}/js/jquery.js"></script>
	<title>残疾人就业信息网</title>
	<script type="text/javascript">
		function searchObj(page){
			$('#page').val(page);
			$('#searchObj').submit();
		}
	</script>
	<style type="text/css">
	.list11:hover {
		cursor: pointer;
	}
	</style>
</head>
<body>
	<jsp:include page="../formatter/status-bar.jsp" />
	<jsp:include page="../formatter/header.jsp" />
	
	<div id="maincontent">
      <div style="margin-top: 10px;">
      	<form action="${contextPath }/emp" id="searchObj">
	         <div id="leftsidebar" >
	           <div class="s_search">
	             <div class="Search">
	               <div class="SearchInputText" style="margin-top: 20px;margin-left: 50px;">
	                 <div class="SetFloatLeft InputTextBgEmp">
	                    <input name="keyWord" type="text" id="keyWord" value="${keyWord }"  title="请输入关键字" class="InputTextBlank DefaultText" style="color: gray;"/>
	                    <input type="hidden" id="page" name="page" />
	                 </div>
	                 <div class="SearchBtn SetFloatLeft">
		                <input class="SearchButtom SetFloatLeft"  type="image" name="ImageButton1" id="ImageButton1" 
		                  onclick="searchObj(1);" class="send55" src="images/HomePageImage/SearchImage/SearchBtn.gif" style="border-width:0px;"/>
		                <div class="SetFloatLeft AdvancedSearch"> </div>
	                 </div>
	              </div>
	            </div>
	            <div class="pot"></div>
	            <ul class="list_condition">
	              <li class="list_selt" style=" height:30px;">
	           	    <div>
	                  <div style="float:left;">   就职意向： </div>
	                    <div class="PublicButtomBar">
							<select name="jobCategory" id="jobCategory" style="width: 170px;height:23px">
								<c:forEach items="${jcList }" var="jc">
								  <option value="${jc.code }" <c:if test="${jcCode == jc.code }">selected=selected</c:if>>${jc.name }</option>
								</c:forEach>
							</select>
	                    </div>
	                </div>
	              </li>
	              <li class="list_selt" style=" height:30px;">
	                <div>
	             	 <div style="float:left;">	学&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;历： </div>
	                   <div class="PublicButtomBar">
	                     <select name="education" id="education" style="width: 170px;height:23px">
							<c:forEach items="${params }" var="p">
								<c:if test="${p.type == 'education' }">
									<option value="${p.value }" <c:if test="${education == p.value }">selected=selected</c:if>>${p.name }</option>
								</c:if>
							</c:forEach>
						  </select>
	             	  </div>
	                </div>
	              </li>
	              <li class="list_selt" style=" height:30px;">
	               <div>
	                <div style="float:left;">	性别： </div>
	                 <div class="PublicButtomBar">
	             	  <select name="gender" id="gender" style="width: 100px;height:23px">
						<c:forEach items="${params }" var="p">
						  <c:if test="${p.type == 'gender' }">
							<option value="${p.value }" <c:if test="${gender == p.value }">selected=selected</c:if>>${p.name }</option>
						  </c:if>
						</c:forEach>
					  </select>
	                </div>
	               </div>
	             </li>
	           </ul>
	           <ul class="list_condition">
	             <li class="list_selt" style=" height:30px;">
	               <div>
	                 <div style="float:left;">工作地区：</div>
	                   <div class="PublicButtomBar">
						<select name="acode" id="acode" style="width: 170px;height:23px">
							<option value="">请选择</option>
								<c:forEach items="${areaList }" var="t">
									<option value="${t.code }" <c:if test="${fn:substring(tarArea.code,2,4) == fn:substring(t.code,2,4) }">selected="selected"</c:if>>${t.name }</option>
								</c:forEach>
						</select>
	                   </div>
	               </div>
	             </li>
	             <li class="list_selt" style=" height:30px;">
	               <div>
	                <div style="float:left;">	    	就职方式： </div>
	                  <div class="PublicButtomBar">
					   <select name="jobNature" id="jobNature" style="width: 170px;height:23px">
						<c:forEach items="${params }" var="p">
							<c:if test="${p.type == 'jobNature' }">
								<option value="${p.value }" <c:if test="${jobNature == p.value }">selected=selected</c:if>>${p.name }</option>
							</c:if>
						</c:forEach>
					   </select>
	                  </div>
	               </div>
	            </li>    
	          </ul>
	          <div class="clearboth">  </div>
	        </div>
	       </div>
       </form>
       
       <div class="advertisement"></div>
      </div>
      <div class="clearboth"></div>
      <div id="contentright" class="contentright">
        <div style="clear: both"></div>
        <div class="zxzw_center">
			<div class="job_list">
				<div id="main">
					<div id="data">
						<!-- 上部显示列表信息  begin -->
						<div class="list11">
							<table width="990px" border="0" cellspacing="0" cellpadding="0">
								<thead style="background-color: #ECF4FF; text-align: center;
										background-image: url(images/HomePageImage/SearchImage/center_1px.gif);background-repeat: repeat-x;">
										<tr>
											<th width="90px" height="26" class="s13_blue_b" id="tableHeadStyle">简历标题</th>
											<th width="70px" class="s13_blue_b" id="tableHeadStyle">姓名</th>
											<th width="50px" class="s13_blue_b" id="tableHeadStyle">性别</th>
											<th width="80px" class="s13_blue_b" id="tableHeadStyle">学历</th>
											<th width="70px" class="s13_blue_b" id="tableHeadStyle">专业</th>
											<th width="80px" class="s13_blue_b" id="tableHeadStyle">工作经验</th>
											<th width="100px" class="s13_blue_b" id="tableHeadStyle">求职岗位</th>
											<th width="100px" class="s13_blue_b" id="tableHeadStyle">期望工作地</th>
											<th width="80px" class="s13_blue_b" id="tableEndStyle" style="border-right: 1px solid #c5e2f6;">期望薪资</th>
										</tr>
								</thead>
								<tbody>
									<c:choose>
										<c:when test="${list == null || fn:length(list) == 0}">
											<tr>
												<td colspan="5">
													没有查到您所需要的信息.
												</td>
											</tr>
										</c:when>
										<c:otherwise>
											<c:forEach items="${list }" var="obj" varStatus="status">
												<tr class="list11" <c:if test="${status.index % 2 == 1 }">style="background:#f6f6f6;"</c:if>>
													<td width="90px" class="s13_blue_b" id="tabaleDataStyle" >
														 <input type="hidden" name="id" value="${obj.id }"/>
														 <span id="Repeater1_ctl00_Label2" class="exceedHidden" style="display:-moz-inline-box;display:inline-block; width:98px;" >${obj.title}</span>
													</td>
													<td width="70px" class="s13_blue_b">
														<a href='${contextPath }/resume/getOneForShow?id=${obj.id}' class="exceedHidden" style="width:65px;">${obj.name}</a>
													</td>
													<td width="50px" class="s13_blue_b" id="tabaleDataStyle">
														 <span id="Repeater1_ctl00_Label2" >${obj.gender}</span>
													</td>
													<td width="80px" class="s13_blue_b" id="tabaleDataStyle">
														<span id="Repeater1_ctl00_Label3">${obj.education}</span>
													</td>
													<td width="70px" class="s13_blue_b" id="tabaleDataStyle">
														<span id="Repeater1_ctl00_Label6" class="exceedHidden" style="display:-moz-inline-box;display:inline-block; width:70px;" >${obj.major}</span>
													</td>
													<td width="80" class="s13_blue_b" id="tabaleDataStyle">
														<span id="Repeater1_ctl00_Label7">${obj.experience}</span>
													</td>
													<td width="100" class="s13_blue_b" id="tabaleDataStyle">
														<span id="Repeater1_ctl00_Label7">${obj.desireJob}</span>
													</td>
													<td width="100" class="s13_blue_b" id="tabaleDataStyle">
														<span id="Repeater1_ctl00_Label7">${obj.desireAddress}</span>
													</td>
													<td width="80" class="s13_blue_b" id="tabaleDataStyle">
														<span id="Repeater1_ctl00_Label7">${obj.desireSalary}</span>
													</td>
												</tr>
											</c:forEach>
										</c:otherwise>
									</c:choose>
								</tbody>
							</table>
						</div>
						<!-- 上部显示列表信息  end -->
						
						<!-- 下部显示页码数  begin -->
						<div style="padding:15px;" class="pages0">
							<span id="lbCount" class="total">共${pagination.records}条记录</span> 
							<span id="lbTotalPage" class="total">总页:${pagination.countPages}页</span> 
							<span id="lbCurPage" class="total">当前页:第${pagination.currentPage}页</span> 
							<c:if test="${pagination.currentPage==1 }">
								<a id="LBUP"  class="total" href="javascript:searchObj(1);">上页</a>
							</c:if>
							<c:if test="${pagination.currentPage>1 }">
								<a id="LBUP"  class="total" href="javascript:searchObj(${pagination.currentPage-1 });">上页</a>
							</c:if>
							<c:forEach begin="${pagination.start }" end="${pagination.end }" var="p">
								<c:if test="${pagination.currentPage==p}">
									<a id="LB${p}" class="current" href="javascript:searchObj(${p});">${p}</a>
								</c:if>
								<c:if test="${pagination.currentPage!=p}">
										<a id="LB${p}" href="javascript:searchObj(${p});">${p}</a>
								</c:if>
							</c:forEach>
							<c:if test="${pagination.currentPage<pagination.countPages}">
								<a id="LBNEXT" class="total" href="javascript:searchObj(${pagination.currentPage+1 });">下页</a>
							</c:if>
							<c:if test="${pagination.currentPage==pagination.countPages }">
								<a id="LBNEXT" class="total" href="javascript:searchObj(${pagination.countPages });">下页</a>
							</c:if>
							<span class="total">到第 
								<select name="LbDPG" onchange="javascript:searchObj(this.selectedIndex+1)" id="LbDPG">
									<c:forEach begin="1" end="${pagination.countPages }" var="p">
										<option <c:if test="${pagination.currentPage==p}">selected="selected"</c:if> value="${p}">${p}</option>
									</c:forEach>
							</select> 页 </span>
						</div>
						<!-- 下部显示页码数  end -->
						
					</div>
				
				</div>
			</div>            
         </div>
      </div>
	</div>
	
    <jsp:include page="../formatter/footer.jsp" />
</body>
</html>