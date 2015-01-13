<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>残疾人就业信息网</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="keywords" content="残疾人招聘信息,残疾人就业信息,残疾人人才网,残疾人找工作" />
	<meta content="残疾人招聘就业" name="description" />
	
	<link rel="shortcut icon" href="${contextPath}/images/HomePageImage/favicon.ico" type="image/x-icon" />
	<link href="${contextPath}/css/style_job.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/Public.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/PublicStatusBar.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/HomePageHeader.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/SetHeaderStyle.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/HomePageFooter.css" rel="stylesheet" type="text/css" />

	<script type="text/javascript" src="${contextPath}/js/jquery.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			common.pagination(1);
			
		});
		common = {};
		common.pagination = function(page) {
			var url = '${contextPath}/direct/search/' + page;
			var keyWord = $("input[type='text'][name='keyWord1']").val(); 
			var releaseDate = $('#releaseDate').val();
			$.ajax({
				url : url,
				type : 'GET',
				data :{"keyWord":keyWord,releaseDate:releaseDate},
				success : function(e) {
					$('#main').html(e);
					$('#data').fadeIn();
				},
				dataType : 'html',
				async : false
			});
		};
	</script>
</head>
<body>
	<jsp:include page="../formatter/status-bar.jsp" />
	<jsp:include page="../formatter/header.jsp" />
	<div id="directBody">
	<div class="MainLeft">
		<div class="MainLeftRightHead1">
           <div class="PublicframeTwo ">
               <div class="PublicframeTwoHeadBar">
                 <div class="PublicframeTwoHeadBarLeft"></div>
                   <div class="PublicframeTwoHeadBarTittle">
                       <span class="PublicframeTwoHeadBarTittleSpan">就业指导</span>
                   </div>
                   <div class="PublicframeTwoHeadBarRight">
                   </div>
                   <div class="PublicframeTwoHeadBarMore">
                       <span class="PublicframeTwoHeadBarMoreSpan"></span>
                   </div>
               </div>
               <div class="PublicframeTwoContent" style="width: auto; padding: 3px 5px 5px;" id="">
                   <div class="MainLeftTwo">
                       <div class="MainLeftTwoLeft">
                          <div id="PublicframeOne11" class="PublicframeOne ">
                          	<div id="main"></div>
						  </div>
                       </div>
                   </div>
                   <div style="clear: both"></div>     
               </div>
           </div>
	    </div>	
	</div>
	<div class="MainRight">
      <div class="MainRightOne">
          <div class="PublicframeTwo ">
          	<div class="PublicframeTwoHeadBar">
          		<div class="PublicframeTwoHeadBarLeft"></div>
          		<div class="PublicframeTwoHeadBarTittle">
          			<span class="PublicframeTwoHeadBarTittleSpan">文章搜索</span>
          		</div>
          		<div class="PublicframeTwoHeadBarRight"></div>
          		<div class="PublicframeTwoHeadBarMore">
          			<span class="PublicframeTwoHeadBarMoreSpan"></span>
          		</div>
          	</div>
          	<div class="PublicframeTwoContent" id="">
          		<div class="ArticleSearchFrame">
          		  <div class="Search">
          			<div class="SearchInputText">
          			 <div class="SetFloat InputTextBg">
          			  <input type="text" name="keyWord1"  title="请输入关键字1" id="KeyWord1" class="InputTextBlank DefaultText" value="${keyWord1}" style="color: gray;" />
          			 </div>
          			</div>
          		   </div>
          		 </div>
     
          		  <div class="ArticleTime">
          		    <div class="ArticleTimeTitle">发布时间：</div>
          		    <div class="ArticleTimeFrame">
          		      <select name="releaseDate" id="releaseDate" class="ArticleSelectDropDown">
        		       	<c:forEach items="${params }" var="p1">
							<c:if test="${p1.type == 'releaseDate' }">
								<option value="${p1.value }" >${p1.name }</option>
							</c:if>
						</c:forEach>
          		       </select>
          		     </div>
          		    </div>
          		     <div style="clear: both"></div>
          		     <div class="ArticleBtn" id="SeachBtn"   onclick="common.pagination(1)"></div>
          		 </div>
            </div>
	      </div> 
		</div>
	</div>
	
<jsp:include page="../formatter/footer.jsp" />
		
</body>
</html>