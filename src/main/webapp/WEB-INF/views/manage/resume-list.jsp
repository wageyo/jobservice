<%@ page language="java" import="java.util.*" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh-cn">
<head>
	
	<title>简历管理列表</title>
	<meta http-equiv="keywords" content="残疾人,就业,招聘">
	<meta http-equiv="description" content="残疾人就业招聘网站">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" type="text/css" href="${contextPath}/js/bootstrap/css/bootstrap.css" />
	<link rel="stylesheet" type="text/css" href="${contextPath}/js/bootstrap/css/bootstrap-combined.min.css" />
	<link rel="stylesheet" type="text/css" href="${contextPath}/css/backdoor/main.css" />
	<link rel="stylesheet" type="text/css" href="${contextPath}/css/backdoor/main_list.css" />
	<script src="${contextPath}/js/lib/jquery-1.11.1.js"></script>
	<script src="${contextPath}/js/bootstrap/js/bootstrap.js"></script>
	<script src="${contextPath}/js/manage/common.js"></script>
	<script src="${contextPath}/js/manage/resume.js"></script>
		<script type="text/javascript">  
        $(function () {  
            $("#resumeCheckbox").click(function () {  
                if ($(this).is(":checked")) {  
                    //全选  
                    $("#resumetable :checkbox").attr("checked", "checked");  
                }  
                else {  
                    //全不选  
                    $("#resumetable :checkbox").removeAttr("checked");  
                }  
            });  
            //导出所选
            $("#resumeExportSelected").click(function () {  
	            var resume_value =[];//定义一个数组    
	        	$("input[name=resumeCheckbox2]:checked").each(function(){//遍历每一个名字为interest的复选框，其中选中的执行函数     
	           			 resume_value.push($(this).val());//将选中的值添加到数组resume_value中    
	       		}); 
	    		if(resume_value.length!=0){	    		
	            $.ajax({
	    			url:server.url + 'resume/resumeExportSelected',
	    			type:'post',
	    			data:{params:resume_value},
	    			success:function(data){
	    				window.location.href=data;
	    				},
	    			error:function(){
	    				alert("导出时发生错误");
	    				}
	    			});
	    		}else{
	    		      alert("你还没有选择任何内容！");  
	    		}
            });  
            //导出所有
            $("#resumeExportAll").click(function () {    
            	var param  = new Object();
            	param.checkStatus=$('#checkStatus').val();
            	param.targetName=$('#resumeNameHid').val();
                $.ajax({
	    			url:server.url + 'resume/resumeExportAll',
	    			type:'post',
	    			data:{
	    				checkStatus:param.checkStatus,
	    				targetName:param.targetName
	    				},
	    			success:function(data){
	    				window.location.href=data;
	    				},
	    			error:function(){
	    				alert("导出时发生错误");
	    				}
	    			});
            });  
         
        })  
        
    </script>  
</head>

<body>
	
	<!-- 整个页面div  开始 -->
	<div class="main-body">
	
		<!-- 头部 div -->
		<%@ include file="header.jsp" %>
		
		<!-- 中间主体div -->
		<div class="manage-body">
		
			<!-- 左侧菜单div -->
			<%@ include file="body-left.jsp" %>
			
			<!-- 右侧详细内容div  -->
			<div class="manage-body-right">
			
				<div class="container-fluid">
				
					<!-- 上方条件查询框  开始 -->
					<div class="" style="margin-bottom: 5px;">
						<input class="input-medium search-query" value="${targetName }" type="text" name="targetName" id="targetName"/> 
						<div class="btn-group" >
							<button class="btn">${checkStatusName }</button> 
							<input type="hidden" id="checkStatus" name="checkStatus" value="${checkStatus }"/>
							<button data-toggle="dropdown" class="btn dropdown-toggle"><span class="caret"></span></button>
							<ul class="dropdown-menu">
								<li>
									<a href="javascript:query(null,'daiShen');">待审核</a>
								</li>
								<li>
									<a href="javascript:query(null,'weiTongGuo');">未通过</a>
								</li>
								<li>
									<a href="javascript:query(null,'ok');">已通过</a>
								</li>
								<li>
									<a href="javascript:query(null,'yiJiuYe');">已就业</a>
								</li>
							</ul>
						</div>
						<button type="submit" class="btn" onclick="query(null,null);">查找</button>
						<input type="hidden" id="resumeNameHid" name="resumeNameHid" value="${targetName }"  />
						<div style="float:right;">	
							<button type="button" class="btn btn-info" id="resumeExportSelected"  >导出所选</button>	
							<button type="button" class="btn btn-info" id="resumeExportAll"  >导出全部</button>
						</div>
					</div>
					<!-- 上方条件查询框  结束-->
					
					<!-- 下方结果显示框  开始 -->
					<div class="row-fluid">
						
						<div class="span12">
							<table class="table" id="resumetable">
								<thead>
									<tr>
										<th >
										  <input id="resumeCheckbox" type="checkbox" />  
										</th>
										<th>
											序号
										</th>
										<th>
											简历名称
										</th>
										<th>
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
											联系方式
										</th>
										<th>
											操作
										</th>
									</tr>
								</thead>
								<tbody>
									<!-- 没有数据返回时, 显示提示文字 -->
									<c:if test="${entityList[0] == null}">
										<tr>
											<td colspan="11" class="warning" style="color:red; text-align:center;">
												没有找到你需要的数据喔!
											</td>
										</tr>
									</c:if>
									
									<c:forEach items="${entityList }" var="entity" varStatus="row">
										<!-- 隔2行换色 -->
										<tr 
											<c:if test="${row.index % 2 == 0 }">class="info"</c:if>
										>
											<td>
												<input type="hidden" id="companytotal" name="companytotal" value="${entity.id }"/>  
                   							 	<input id="resumeCheckbox2" name="resumeCheckbox2" type="checkbox" value="${entity.id }"/>  
               								</td>
											<td>
												${(row.index + 1 + (currentPage - 1) * 20) }
											</td>
											<td>
												${entity.title }
											</td>
											<td>
												${entity.name }
											</td>
											<td>
												${entity.gender }
											</td>
											<td>
												${entity.disabilityCard }
											</td>
											<td>
												${entity.disabilityCategory }
											</td>
											<td>
												${entity.disabilityLevel }
											</td>
											<td>
												${entity.phone }
											</td>
											<td>
												<a href="${contextPath }/manage/resume/view/${entity.id}">查看</a>
												<a href="${contextPath }/manage/resume/edit/${entity.id}">编辑</a> 
												<c:if test="${checkStatus != 'weiTongGuo' && checkStatus != 'yiJiuYe' }">
													<a href="javascript:updateEntity('refuse','${entity.id }');" style="color:red;">拒绝</a>
												</c:if> 
												<c:if test="${checkStatus != 'ok' && checkStatus != 'yiJiuYe' }">
													<a href="javascript:updateEntity('approve','${entity.id }');">通过</a>
												</c:if>
												<c:if test="${checkStatus == 'ok' }">
													<a href="javascript:updateEntity('yiJiuYe','${entity.id }');">设为已就业</a>
												</c:if>
												<c:if test="${checkStatus == 'yiJiuYe' }">
													<a href="javascript:updateEntity('approve','${entity.id }');">设为未就业</a>
												</c:if>
												<a href="javascript:updateEntity('delete','${entity.id }');">删除</a>
											</td>
										</tr>
									</c:forEach>
									
								</tbody>
							</table>
						</div>
						
					</div>
					<!-- 下方结果显示框  开始 -->
					
					<!-- 尾部分页 开始 -->
					<div class="">
						<div class="pagination">
							<ul>
								<li>
									<input type="hidden" id="totalPage" value="${totalPage }" />
									<a href="javascript:query(${currentPage - 1 },null);">上一页</a>
								</li>
								<c:forEach begin="1" end="${totalPage }" var="i">
									<li <c:if test="${i == currentPage }">class="active"</c:if>>
										<!-- 当前页面时链接显示灰色 -->
										<a 
											<c:choose>
												<c:when test="${i == currentPage }">
													href="javascript:void(0);" 
												</c:when>
												<c:otherwise>
													href="javascript:query(${i },null);" 
												</c:otherwise>
											</c:choose>
										>${i }</a>
									</li>
								</c:forEach>
								<li>
									<a href="javascript:query(${currentPage + 1 },null);">下一页</a>
								</li>
								<li>
									<a>总计  ${total } 条数据</a>
								</li>
							</ul>
						</div>
					</div>
					<!-- 尾部分页 结束 -->
				</div>
			
			
			</div>
			<!-- 让body-right div的高度跟随内容自动变化 -->
			<div style="font: 0px/0px sans-serif;clear: both;display: block"> </div>
		</div>
		
		<!-- 尾部div -->
		<%@ include file="footer.jsp" %>
		
	</div>
	<!-- 整个页面div  结束 -->
</body>
</html>
