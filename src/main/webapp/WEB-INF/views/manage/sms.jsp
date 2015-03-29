<%@ page language="java" import="java.util.*" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh-cn">
<head>
	
	<title>短信发送页面</title>
	<meta http-equiv="keywords" content="残疾人,就业,招聘">
	<meta http-equiv="description" content="残疾人就业招聘网站">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" type="text/css" href="${contextPath}/js/bootstrap/css/bootstrap.css" />
	<link rel="stylesheet" type="text/css" href="${contextPath}/js/bootstrap/css/bootstrap-combined.min.css" />
	<link rel="stylesheet" type="text/css" href="${contextPath}/css/backdoor/main.css" />
	<script src="${contextPath}/js/lib/jquery-1.11.1.js"></script>
	<script src="${contextPath}/js/bootstrap/js/bootstrap.js"></script>
	<script src="${contextPath}/js/lib/ajaxupload.3.6.js"></script>
	<script src="${contextPath}/js/manage/common.js"></script>
	<script src="${contextPath}/js/manage/upload_file.js"></script>
	<script src="${contextPath}/js/verify.js"></script>
	<script src="${contextPath}/js/manage/sms.js"></script>
	
	<style type="text/css">
	
		/**
		  * 顶部--发送按钮和发送号码统计页面 
		  **/
		.send-area-top{
			padding-top: 10px;
			padding-left: 15px;
			margin:5px;
		}
		.send-area-top .send-area-top-txt{
			border-bottom: 1px solid rgb(155, 155, 252);
			width: 717px;
			padding-top: 3px;
			padding-left: 10px;
			margin-top:5px;
			margin-left: 5px;
			float:left;
		}
		
		/**
		 * 中间电话号码列表div样式
		 **/
		.sms-phone-list{
			width:200px;
			height:600px;
			float:left;
		}
		.sms-phone-list .sms-phone-title{
			text-align:center;
		}
		.middle-move-div{
			width:100px;
			height:100%;
			float:left;
			
		}
		.middle-move-div-content{
			margin-top:180px;
			text-align:center;
		}
		.sms-phone-list .target-div, .sms-phone-list .communicate-div{
			border-top: 1px solid rgb(124, 120, 120);
			border-left: 1px solid rgb(124, 120, 120);
			width:100%;
			height:100%;
			overflow:scroll;
		}
		.sms-phone-list ul{
			margin-left: 0px;
		}
		.sms-phone-list ul li{
			list-style:none;
			cursor: pointer;
			padding-left:10px;
		}
		.sms-phone-list ul li:hover{
			background-color:#CEE3F2;
			cursor:pointer;
		}
		.sms-phone-list ul li span{
			display:-moz-inline-box;  
			display:inline-block;  
		}
		
	</style>
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
				<!-- 顶部发送按钮和发送号码统计页面 -->
				<div class="send-area-top" >
					<div style="float:left;">
						<button class="btn btn-info" type="button" onclick="send();">发送给 :</button>
					</div>
					<div class="send-area-top-txt" style="">0个联系人</div>
					<div style="font: 0px/0px sans-serif;clear: both;display: block"> </div>
				</div>
				<!-- 宽高自适应 container -->
				
				<div class="container-fluid">
				
					<div class="row-fluid">
					
						<!-- 左边短信编辑区 begin -->
						<div class="span4">
							<textarea id="shortMessage" style="width: 100%;height: 300px;" title="短信内容不超过300字...">短信内容不超过300字...</textarea>
							<p id="notice">还可以输入300字...</p>
							<p id="noticeMsg"></p>
							<div class="example-area" style="background-image:url(${contextPath}/images/backdoor/excelexample.png);background-repeat: no-repeat;height:160px;padding-top: 160px;color: blue">
								excel表格格式, 姓名为选填项, 可为空.
								
							</div>
						</div>
						<!-- 左边短信编辑区 end -->
					
						<!-- 中间号码录入区 begin -->
						<div class="span8">
							<div class="sms-phone-list">
								<div class="sms-phone-title">
									目标号码
								</div>
								<div class="target-div">
									<ul class="target-ul">
									</ul>
								</div>
							</div>
							
							<div class="middle-move-div">
								<div class="middle-move-div-content">
									<input type="button" onclick="addAll();" value="<<全部添加" /><br/><br/>
									<input type="button" onclick="removeAll();" value="全部移出>>" /><br/><br/><br/><br/>
									<input type="button" name="file" value="上传号码" id="picFileImport" /><br/>
									<input type="button" id="downLoadWrongList" value="下载错误号码" style="font-size: 12px;
										height: 20px;
										width: 85px;
										line-height: 20px;
										text-align: center;
										vertical-align: middle;
										padding: 0px 3px;
										margin-top: 10px;
										color: rgb(116, 116, 116);"/>
								<!-- 	<a href="#" id="downLoadWrongList" style="">下载错误号码</a>	 -->
									
								</div>
							</div>
							
							<div class="sms-phone-list">
								<div class="sms-phone-title">
									通讯录号码
								</div>
								<div class="communicate-div">
									<ul class="communicate-ul">
									<!-- 	<c:forEach items="${smsPhoneList }" var="smsPhone">
											<li phone="${smsPhone.phone }" name="${smsPhone.name }" onclick="toTargetDiv(this);">
												<c:choose>
													<c:when test="${smsPhone.name == null || smsPhone.name == '' }"> ${smsPhone.phone }</c:when>
													<c:otherwise>${smsPhone.name }</c:otherwise>
												</c:choose>
											</li>
										</c:forEach> -->
									</ul>
								</div>
							</div>
							
						</div>
						<!-- 中间号码录入区 end -->
						
						<!-- 遮罩窗体  开始--><a>&nbsp;</a>
						<div id="modal-container-236826" class="modal fade" role="dialog" aria-labelledby="myModalLabel" aria-hidden="false">
							<div class="modal-header">
								 <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
								<h3 id="myModalLabel">
									快速修改联系人
								</h3>
							</div>
							<div class="modal-body">
								<table>
									<tr>
										<td>
											姓名
										</td>
										<td>
											<input type="text" name="zhezhao-name" id="zhezhao-name"/>
										</td>
									</tr>
									<tr>
										<td>
											电话
										</td>
										<td>
											<input type="text" name="zhezhao-phone" id="zhezhao-phone" readonly="readonly"/>
										</td>
									</tr>
								</table>
							</div>
							<div class="modal-footer">
								 <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button> 
								 <button class="btn btn-success" data-dismiss="modal" aria-hidden="true" onclick="editContactPerson();">保存设置</button>
							</div>
						</div>
						<!-- 遮罩窗体  结束-->
						
						
						<!-- 右边通信录区 begin -->
						<div class="span4">
							
							
						</div>
						<!-- 右边通信录区 begin -->
					</div>
						
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
