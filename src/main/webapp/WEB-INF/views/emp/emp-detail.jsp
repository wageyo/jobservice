<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<title>残疾人招聘就业网</title>
<link href="${contextPath}/css/style_job.css" rel="stylesheet" type="text/css" />
<!-- 
<link href="${contextPath}/css/body_job.css" rel="stylesheet" type="text/css" />
 -->
<link href="${contextPath}/css/header.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/css/print.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/css/dialog.css" rel="stylesheet" type="text/css" />
<script src="${contextPath}/js/dialog.js" type="text/javascript"></script>
<script src="${contextPath}/js/jquery.js" type="text/javascript"></script>
<script src="${contextPath}/js/Actiondo.js" type="text/javascript"></script>
<style type="text/css">
	.style1 {
		text-align: left;
	}
	
	.imga a {
		
	}
	
	.imga a:link {
		text-decoration: none;
		margin-left: 0px;
	}
	
	.imga a:visited {
		text-decoration: none;
		margin-left: 0px;
	}
	
	.imga a:hover {
		text-decoration: none;
		margin-left: 0px;
	}
	
	.imga a:active {
		text-decoration: none;
		margin-left: 0px;
	}
	
	.xBody {
		width: 742px;
		padding-left: 108px;
		margin: 0 auto;
	}
	
	.txt {
		border: none;
	}
	
	A:hover {
		COLOR: blue;
		TEXT-DECORATION: none;
	}
	/**	弹出窗背景样式	**/
	#bg{
		width:100%;
		height:100%;
		top:0px;
		left:0px;
		position:
		absolute;
		filter: Alpha(opacity=50);
		opacity:0.3; 
		background:#000000; 
		display:none;
	}
	/** 弹出窗样式 **/
	#popbox{
		position:absolute;
		width:400px; 
		height:400px; 
		left:50%; 
		top:50%; 
		margin:-200px 0 0 -200px; 
		display:none; 
		background:#FFFFFF;
		border: 4px solid rgb(173, 177, 142);
		font-family: 微软雅黑;
	}
	
	#popbox div table tr{
		height:30px;
	}
</style>

<script type="text/javascript">
	//         
	var tips;
	var theTop = 0/*这是默认高度,越大越往下*/;
	var old = theTop;
	function initFloatTips() {
		tips = document.getElementById('floatTips');
		moveTips();
	};
	function moveTips() {
		var tt = 50;
		if (window.innerHeight) {
			pos = window.pageYOffset
		} else if (document.documentElement
				&& document.documentElement.scrollTop) {
			pos = document.documentElement.scrollTop
		} else if (document.body) {
			pos = document.body.scrollTop;
		}
		pos = pos - tips.offsetTop + theTop;
		pos = tips.offsetTop + pos / 10;
		if (pos < theTop)
			pos = theTop;
		if (pos != old) {
			tips.style.top = pos + "px";
			tt = 10;
		}
		old = pos;
		setTimeout(moveTips, tt);
	}

	function daYin() {
		window.print();
		return false;
	}
	
	//打开发送邀请弹出窗
	function pupopen(){
        $('#bg').css('display','block');
        $('#popbox').css('display','block');
	}
	
	//关闭发送邀请弹出窗
	function pupclose(){
		$('#bg').css('display','none');
        $('#popbox').css('display','none');
	}
	
	//想某职位发送邀请
	function sendInvite(){
		var rid = '${resume.id}';
		var jid = $('#choosenJob').val();
		if(jid ==0 || '0' == jid){
			alert('请选择推荐的岗位');
			$('#choosenJob').focus();
			return;
		}
		var leaveMessage = $('#leaveMessage').val();
		$.ajax({
			url: '${contextPath}/company/sendInvite',
			type:'post',
			data:{rid:rid,jid:jid,comment:leaveMessage},
			dataType:'json',
			success:function(data){
				if(data.notice == 'success'){
					alert('邀请发送成功!');
					pupclose();	//关闭窗口
				}else{
					alert(data.notice);
				}
			},
			error:function(){
				alert('发送邀请发生错误, 请重新尝试或者联系管理人员.');
			}
			
		});
		
		
		
	}
</script>


</head>
<body>

	<div style="width: 984px; margin: 0 auto; overflow: hidden; position: relative">
		<div style="width: 742px; margin: 0px auto; overflow: hidden; text-align: right;
           position: absolute; left: 106px; top: 15px;" class="disprint">
			<ul style="list-style: none; line-height: 25px;">
				<!-- 
				<li id="LiCom"><a href="${contextPath}/regP" target="_blank">获取应聘联系方式，请联系各地就业指导中心：<strong style="color:Red">0451-55664482</strong> </a></li>
				 -->
			</ul>
		</div>
		<div style="width: 742px; overflow: hidden; float: left; padding-left: 106px;">
			<table style="padding-top: 10px; margin: 0px auto;" align="center" border="0" cellpadding="0" cellspacing="0" width="742">
				<tbody>
					<tr>
						<td width="742">
							<table border="0" cellpadding="0" cellspacing="0" width="740">
								<tbody>
									<tr>
										<td>
											<a href="${contextPath}/index" style="border: 0;color:blue;">首页</a>
										</td>
									</tr>
								</tbody>
							</table></td>
					</tr>
				</tbody>
			</table>
			<table style="padding-top: 10px; margin: 0 auto;" align="center" border="0" cellpadding="0" cellspacing="0" width="742">
				<tbody>
					<tr>
						<td width="742">
							<table height="54" background="${contextPath}/images/banner33.jpg" border="0" cellpadding="0" cellspacing="0" width="742">
								<tbody>
									<tr>
										<td>
											<table border="0" cellpadding="0" cellspacing="0">
												<tbody>
													<tr>
														<td style="text-indent: 20px;">简历标题：</td>
														<td><span> ${resume.title }</span></td>
														<td style="text-indent: 20px;">更新日期：</td>
														<td>
															<c:if test="${resume.updateDate != null}">
																<fmt:formatDate value="${resume.updateDate}" type="both" dateStyle="long" pattern="yyyy-MM-dd hh:mm:ss" var="updateDate" />
															</c:if>
															<span> ${updateDate }</span>
														</td>
													</tr>
												</tbody>
											</table></td>
									</tr>
								</tbody>
							</table>
							<table background="${contextPath}/images/bg51.jpg" border="0" cellpadding="0" cellspacing="0" width="742">
								<tbody>
									<tr>
										<td>
											<table style="margin: 0 auto;" align="center" border="0" cellpadding="0" cellspacing="0" width="690">
												<tbody>
													<tr>
														<td>
															<table height="20" align="center" border="0" cellpadding="0" cellspacing="0" width="690">
																<tbody>
																	<tr>
																		<td>&nbsp;</td>
																	</tr>
																</tbody>
															</table>
															<table class="lanmu2" height="33" align="center" bgcolor="#EBEEF6" border="0" cellpadding="0" cellspacing="0" width="690">
																<tbody>
																	<tr>
																		<td valign="middle" width="568">
																			<p class="p1" id="lbname">基本信息</p></td>
																	</tr>
																</tbody>
															</table>
															<table height="20" align="center" border="0" cellpadding="0" cellspacing="0" width="690">
																<tbody>
																	<tr>
																		<td></td>
																	</tr>
																</tbody>
															</table>
															<table border="0" cellpadding="0" cellspacing="0" width="690">
																<tbody>
																	<tr>
																		<td width="556">
																			<table border="0" style="width:100%;" cellpadding="5" cellspacing="0">
																				<tbody>
																					<tr>
																						<td class="txt" height="25" width="200">姓名：${resume.name }</td>
																						<td class="txt" width="200">性别：${resume.gender }</td>
																						<td class="txt">年龄：${resume.age }</td>
																					</tr>
																					<tr>
																						<td class="txt" height="25"><span class="txt1">学历：${resume.education }</span></td>
																						<td class="txt"><span class="txt1">婚姻状况：${resume.marriage }</span></td>
																						<td class="txt"><span class="txt1">户籍：${resume.homeTown }</span></td>
																					</tr>
																					<tr>
																						<td class="txt" height="25"><span class="txt1">残疾类别：${resume.disabilityCategory }</span></td>
																						<td class="txt"><span class="txt1">残疾等级：${resume.disabilityLevel }</span></td>
																						<td class="txt"><span class="txt1">残疾部位：${resume.disabilityPart }</span></td>
																					</tr>
																					<c:choose>
																						<c:when test="${user.identity == 'admin' || user.id == resume.user.id || user.identity == 'company'}">
																							<tr>
																								<td class="txt" height="25"><span class="txt1">电话：${resume.phone }</span></td>
																								<td class="txt"><span class="txt1">邮箱：${resume.email }</span></td>
																								<td class="txt"><span class="txt1">qq：${resume.qq }</span></td>
																							</tr>
																							<tr>
																								<td height="25" colspan="2" class="txt">居住地：${resume.address }</td>
																								<td class="txt"></td>
																							</tr>
																						</c:when>
																						<c:otherwise>
																							<div id="logins" class="contact_display">
																								<div class="telimg">
																									<img src="${contextPath}/images/tel01.jpg" />
																								</div>
																								<p id="person_tel" class="tel">
																									就业指导中心或企业用户能查看联系信息！请先<a href="${contextPath}/login">登录</a>
																								</p>
																								<p class="telcontent">请在联系对方时告知对方，是在残疾人就业信息网上看到的信息，谢谢！</p>
																							</div>
																						</c:otherwise>
																					</c:choose>
																				</tbody>
																			</table>
																			</td>
																		<td width="134">
																			<img id="ShowResume1_ImgLogo" src="${contextPath}/images/noimg.png"
																			style="height: 135px; width: 110px; border-width: 0px; padding: 2px 2px 2px 2px; border: #EBEEF6 1px solid" />
																		</td>
																	</tr>
																</tbody>
															</table>
															<table height="20" align="center" border="0" cellpadding="0" cellspacing="0" width="690">
																<tbody>
																	<tr>
																		<td>&nbsp;</td>
																	</tr>
																</tbody>
															</table>
															<table class="lanmu2" height="33" align="center" bgcolor="#EBEEF6" border="0" cellpadding="0" cellspacing="0" width="690">
																<tbody>
																	<tr>
																		<td class="txt"><span class="span4">求职意向</span></td>
																	</tr>
																</tbody>
															</table>
															<table height="20" align="center" border="0" cellpadding="0" cellspacing="0" width="690">
																<tbody>
																	<tr>
																		<td></td>
																	</tr>
																</tbody>
															</table>
															<table style="line-height: 25px;" border="0" cellpadding="0" cellspacing="0" width="690">
																<tbody>
																	<tr>
																		<td class="txt" width="80">求职状态：</td>
																		<td>${resume.state }</td>
																	</tr>
																	<tr>
																		<td class="txt">工作类型：</td>
																		<td>${resume.jobNature }</td>
																	</tr>
																	<tr>
																		<td class="txt">从事岗位：</td>
																		<td>${resume.desireJob }</td>
																	</tr>
																	<tr>
																		<td class="txt">工作地点：</td>
																		<td>${resume.desireAddress }</td>
																	</tr>
																	<tr>
																		<td class="txt">其他要求：</td>
																		<td>${resume.provideOther }</td>
																	</tr>
																</tbody>
															</table>
															<table height="20" align="center" border="0" cellpadding="0" cellspacing="0" width="690">
																<tbody>
																	<tr>
																		<td>&nbsp;</td>
																	</tr>
																</tbody>
															</table>
															<table class="lanmu2" height="33" align="center" bgcolor="#EBEEF6" border="0" cellpadding="0" cellspacing="0" width="690">
																<tbody>
																	<tr>
																		<td class="txt"><span class="span4">自我评价</span></td>
																	</tr>
																</tbody>
															</table>
															<table height="20" align="center" border="0" cellpadding="0" cellspacing="0" width="690">
																<tbody>
																	<tr>
																		<td></td>
																	</tr>
																</tbody>
															</table>
															<table style="line-height: 25px; margin: 0 auto" align="center" border="0" cellpadding="0" cellspacing="0" width="650">
																<tbody>
																	<tr>
																		<td width="650">${resume.selfEvaluation }</td>
																	</tr>
																</tbody>
															</table>
															<table height="20" align="center" border="0" cellpadding="0" cellspacing="0" width="690">
																<tbody>
																	<tr>
																		<td>&nbsp;</td>
																	</tr>
																</tbody>
															</table> <!--工作经验-->
															<table class="lanmu2" height="33" align="center" bgcolor="#EBEEF6" border="0" cellpadding="0" cellspacing="0" width="690">
																<tbody>
																	<tr>
																		<td class="txt"><span class="span4">工作经历</span></td>
																	</tr>
																</tbody>
															</table>
															<table style="line-height: 25px; margin: 0 auto" align="center" border="0" cellpadding="0" cellspacing="0" width="650">
																<tbody>
																	<tr>
																		<td width="650">
																			<table style="width:100%;line-height: 20px;" border="0" cellpadding="0" cellspacing="5">
																				<tbody>
																					<tr>
																						<td>
																							<p>工作年数 : ${resume.experience }</p>
																						</td>
																					</tr>
																					<!-- 工作经历 -->
																					<c:forEach items="${resume.workExperienceList }" var="we">
																						<tr>
																							<td>所在公司:</td>
																							<td>${we.companyName }</td>
																							<td>在职时间:</td>
																							<td>${we.workTime }</td>
																						</tr>
																						<tr>
																							<td>所在职位:</td>
																							<td>${we.jobName }</td>
																							<td>离职原因:</td>
																							<td>${we.leaveReason }</td>
																						</tr>
																						<tr>
																							<td >自我评价:</td>
																							<td colspan="3" >
																								${we.evaluation }
																							</td>
																						</tr>
																						<tr>
																							<td colspan="4" style="border-bottom: 1px solid rgb(133, 173, 177);" >
																							</td>
																						</tr>
																					</c:forEach>
																				</tbody>
																			</table>
																			<table height="20" align="center" border="0" cellpadding="0" cellspacing="0" width="690">
																				<tbody>
																					<tr>
																						<td>&nbsp;</td>
																					</tr>
																				</tbody>
																			</table>
																			<table class="lanmu2" height="33" align="center" bgcolor="#EBEEF6" border="0" cellpadding="0" cellspacing="0" width="690">
																				<tbody>
																					<tr>
																						<td class="txt"><span class="span4">教育经历</span></td>
																					</tr>
																				</tbody>
																			</table>
																			<table height="20" align="center" border="0" cellpadding="0" cellspacing="0" width="690">
																				<tbody>
																					<tr>
																						<td></td>
																					</tr>
																				</tbody>
																			</table>
																			<table style="line-height: 25px; margin: 0 auto" align="center" border="0" cellpadding="0" cellspacing="0" width="650">
																				<tbody>
																					<tr>
																						<td width="650">最高学历：${resume.education }</td>
																					</tr>
																					<tr>
																						<td width="650">毕业学校：${resume.school }</td>
																					</tr>
																					<tr>
																						<td width="650">专业：${resume.major }</td>
																					</tr>
																					<tr>
																						<td width="650">特长：${resume.experts }</td>
																					</tr>
																					<tr>
																						<td width="650">接受培训情况：${resume.training }</td>
																					</tr>
																				</tbody>
																			</table>
																			<table height="20" align="center" border="0" cellpadding="0" cellspacing="0" width="690">
																				<tbody>
																					<tr>
																						<td>&nbsp;</td>
																					</tr>
																				</tbody>
																			</table></td>
																	</tr>
																</tbody>
															</table></td>
													</tr>
												</tbody>
											</table>
											<table height="29" background="${contextPath}/images/bottom.jpg" border="0" cellpadding="0" cellspacing="0" width="742">
												<tbody>
													<tr>
														<td></td>
													</tr>
												</tbody>
											</table></td>
									</tr>
								</tbody>
							</table></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	<div id="floatTips" class="disprint" style="width: 106px; float: left; overflow: hidden; padding-top: 133px; padding-left: 2px; position: absolute; top: 4.5px; left: 1038px;">
		<input name="HidComState" id="HidComState" value="0" type="hidden" />
			<!-- 
			<table height="30" background="${contextPath}/images/bottom.gif" border="0" cellpadding="0" cellspacing="0" width="100">
				<tbody>
					<tr>
						<td class="txt2" width="34"><img src="${contextPath}/images/diskette.gif" height="16" width="16">
						</td>
						<td width="66"><a id="LinkButton1" class="span5" href="javascript:void(0)">收藏简历</a></td>
					</tr>
				</tbody>
			</table>
			<table height="5" border="0" cellpadding="0" cellspacing="0">
				<tbody>
					<tr>
						<td></td>
					</tr>
				</tbody>
			</table> -->
			<c:if test="${user != null }">	
				<c:if test="${user.identity == 'admin' }">
					<table height="30" background="${contextPath}/images/bottom.gif" border="0" cellpadding="0" cellspacing="0" width="100">
						<tbody>
							<tr>
								<td class="txt2" width="34"><img src="${contextPath}/images/open-folder.gif" height="16" width="16">
								</td>
								<td width="66">
									<a href='${contextPath }/resume/down_back/${resume.id}'> <span class="span5">下载简历</span></a>
								</td>
							</tr>
						</tbody>
					</table>
				</c:if>
			</c:if>
			<table height="5" border="0" cellpadding="0" cellspacing="0">
				<tbody>
					<tr>
						<td></td>
					</tr>
				</tbody>
			</table>
			<table height="30" background="${contextPath}/images/bottom.gif" border="0" cellpadding="0" cellspacing="0" width="100">
				<tbody>
					<tr>
						<td class="txt2" width="34"><img src="${contextPath}/images/print.gif" height="16" width="16" />
						</td>
						<td width="66"><a href="javascript:window.print()" onclick="window.print();"><span class="span5">打印简历</span> </a></td>
					</tr>
				</tbody>
			</table>
			<!-- 只有企业用户才可以发送邀请 -->
			<c:if test="${user != null }">
				<c:if test="${user.identity == 'company' }">
					<table height="30" background="${contextPath}/images/bottom.gif" border="0" cellpadding="0" cellspacing="0" width="100">
						<tbody>
							<tr>
								<td class="txt2" width="34"><img src="${contextPath}/images/open-folder.gif" height="16" width="16" />
								</td>
								<td width="66"><a href="javascript:void(0);" onclick="pupopen();"><span class="span5">发送邀请</span> </a></td>
							</tr>
						</tbody>
					</table>
				</c:if>
			</c:if>
	</div>
	
	<!-- 以下是弹出窗 -->
	<div id="bg"></div>
	<div id="popbox">
		<!-- 标题div -->
		<div style="width:100%;height:30px;background-color: rgb(121, 126, 86);color: white;font-size: 16px;">
			<div style="width:90%;float:left;">
				<span style="margin-left:10px;">面试邀请</span>
			</div>
			<div style="float:left;width:10%;">
				<a href="javascript:pupclose();">关闭</a>
			</div>
		</div>
		
		<!-- 邀请内容 -->
		<div style="width:90%;margin: 20px 20px auto 20px;font-size:14px;">
			<table>
				<tr>
					<td style="width:70px;">受邀请人:</td>
					<td style="width:150px;">${resume.name }</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>应聘岗位:</td>
					<td>
						<input type="hidden" id="companyId" name="companyId" value="${company.id }" />
						<select name="choosenJob" id="choosenJob">
							<c:forEach items="${jobList }" var="job">
								<option value="${job.id }">${job.name }</option>
							</c:forEach>
						</select>
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>联系人:</td>
					<td>
						${company.contactPerson }
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>联系电话:</td>
					<td>
						${company.telephone }
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>留言内容:</td>
					<td colspan="2" >
						<textarea cols="36" rows="10" name="leaveMessage" id="leaveMessage"></textarea>
					</td>
				</tr>
				<tr>
					<td colspan="3" style="text-align:center;height:40px;">
						<a href="javascript:sendInvite();" style="font-size: 16px;font-weight: bold;border: 2px solid rgb(235, 138, 138);padding: 2px 7px;background-color: rgb(253, 189, 189);">发&nbsp;&nbsp;送</a>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<script type="text/javascript">
		$(document).ready(function() {
			initFloatTips();
		});
	</script>

</body>
</html>