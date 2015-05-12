<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>简历</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="keywords" content="残疾人招聘信息,残疾人就业信息,残疾人人才网,残疾人找工作" />
	<meta content="残疾人招聘就业" name="description" />
	
	<link rel="shortcut icon" href="${contextPath}/images/HomePageImage/favicon.ico" type="image/x-icon" />
	<link href="${contextPath}/css/Public.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/grjl_yulan.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/lockinfotwo.css" rel="stylesheet" type="text/css" />
	
	<script type="text/javascript" src="${contextPath}/js/jquery.js"></script>
	<script type="text/javascript" src="${contextPath}/js/common.js"></script>
	<script type="text/javascript" src="${contextPath}/js/resume.js"></script>
	<script type="text/javascript">
		//解码
		function decodeareaname(){
			var areaname = decodeURIComponent('${cookie.areaname.value}');
			$('#logospanname').html(areaname + '残疾人就业信息网');
		}
	</script>
</head>
<body>
	<div id="container">
		<div class="header">
			<div class="logo" title="返回网站首页" >
				<!-- 如果能显示对应省市的则显示对应省份的logo, 否则显示全国的log -->
				<a title="残疾人就业服务网" href="${contextPath}/index">
					<c:choose>
						<c:when test="${areacode != null && areacode != '' }">
							<img alt="残疾人就业信息网" src="${contextPath}/images/logoProvince/logo.png?timestamp1=${now}" /> 
							<span >${areaname }残疾人就业信息网</span>
						</c:when>
						<c:otherwise>	
							<c:if test="${cookie.areacode.value != null && cookie.areacode.value != '' }">
								<img alt="残疾人就业信息网" src="${contextPath}/images/logoProvince/logo.png?timestamp2=${now}" /> 
								<span id="logospanname"></span>
								<script type="text/javascript">
									decodeareaname();
								</script>
							</c:if> 
						</c:otherwise>
					</c:choose>
				</a>
			</div>
			<div class="headerright">
				<div class="choosemodel"></div>
				<div class="print">
					<a href="#"> </a>
				</div>
			</div>
		</div>
		<div class="content">
			<!-- 基本信息 begin -->
			<div class="lockinfofirst">
				<div class="lockinfofirsttitle" id="divUserName">${resume.title } ${resume.disabilityCategory } ${resume.disabilityLevel }</div>
			<div class="lockinfolist">
				<div class="lockinfolistone">
					<input type="hidden" id="resumeid" value="${resume.id }" />
						<ul>
							<li id="liPWorkexperience">${resume.name }</li>
							<li>|</li>
							<li id="liSex">${resume.gender }</li>
							<li>|</li>
							<li id="liAge">${resume.age }</li>
							<li id="liMarriage"></li>
						</ul>
					</div>
					
					<div class="lockinfolisttwo" id="contact">
						<ul>
						<!-- 	<c:choose>
								<c:when test="${cookie.identity.value == 'admin' || cookie.userid.value  == resume.user.id || cookie.identity.value == 'company'}">	 -->
									<li><div class="listcontent">户口状况: ${resume.hukouStatus };&nbsp;&nbsp;&nbsp;&nbsp;婚姻状况: ${resume.marriage }. </div>
									</li>
									<li><div class="listcontent">户籍地址: ${resume.hukouAddress }</div>
									</li>
									<li><div class="listcontent">现居住地: ${resume.address }</div>
									</li>
									<li><div class="listcontent">手机：${resume.phone } | QQ: ${resume.qq } | 邮箱: ${resume.email }</div>
									</li>
									<li><div class="listcontent">残疾部位：${resume.disabilityPart } | 残疾证号: ${resume.disabilityCard }</div>
									</li>
						<!-- 		</c:when>
								<c:otherwise>
									<li><div class="listcontent" style="line-height: 100px;">想了解此人, 请联系当地残联就业指导中心.</div>
									</li>
								</c:otherwise>
							</c:choose> -->
						</ul>
					</div>
					<div class="clearboth"></div>
				</div>
			<!-- 个人头像功能暂未开发 -->
			<div class="lockinfoimage">
					<div id="divImg" class="lockinfopic">
						<img width="96px" height="108px" src="${contextPath }/images/person/not-open.png" />
					</div>
					<div class="lockinfopictext">个人照片</div>
				</div>
			</div>
			<!-- 基本信息 end -->
			
			<!-- 教育程度  begin -->
			<div class="educationExperience" id="divEducationEx">
				<table cellpadding="0" cellspacing="0">
					<tbody>
						<tr>
							<th colspan="4">教育程度</th>
						</tr>
						<tr>
							<td>学历: ${resume.education }</td>
							<td>毕业院校: ${resume.school }</td>
							<td>专业: ${resume.major }</td>
						</tr>
						<tr>
							<td colspan="3">特长: ${resume.experts }</td>
						</tr>
						<tr>
							<td colspan="3">培训情况或需求: ${resume.training }</td>
						</tr>
						<tr>
							<td colspan="3">自我评价: ${resume.selfEvaluation }</td>
						</tr>
					</tbody>
				</table>
			</div>
			<!-- 教育程度  end -->
			
			<!-- 求职意向 begin -->
			<div class="jobIntension">
				<table cellpadding="0" cellspacing="0">
					<tbody>
						<tr>
							<th colspan="2">求职意向</th>
						</tr>
						<tr>
							<td style="width:110px;">失业登记证号：</td>
							<td id="divDuty">${resume.shiYeHao }</td>
						</tr>
						<tr>
							<td style="width:80px;">到岗时间：</td>
							<td id="divDuty">${resume.state }</td>
						</tr>
						<tr>
							<td>工作性质：</td>
							<td id="divJobtyppeName">${resume.jobNature }</td>
						</tr>
						<tr>
							<td>目标地点：</td>
							<td id="divAddress">${resume.desireAddress.name }</td>
						</tr>
						<tr>
							<td>期望工资：</td>
							<td id="divMoth">${resume.desireSalary }</td>
						</tr>
						<tr>
							<td>目标工作：</td>
							<td id="divPosition">${resume.desireJob.name }</td>
						</tr>
						<tr>
							<td>其他要求：</td>
							<td id="divPosition">${resume.provideOther }</td>
						</tr>
					</tbody>
				</table>
			</div>
			<!-- 求职意向 end -->
			
			<!-- 工作经历 begin -->
			<div class="workExperience" id="divWorkExperience">
				<table cellpadding="0" cellspacing="0">
					<tbody>
						<tr>
							<th colspan="4">工作经验 <span style="color:#606060;font-size:12px;"> 工作年数：${resume.experience }</span></th>
						</tr>
						<tr>
							<td colspan="2">职业测评情况：</td>
							<td class="">${resume.careerTest }</td>
						</tr>
						<tr>
							<td colspan="2">办理情况：</td>
							<td class="">${resume.processState }</td>
						</tr>
						<tr>
							<td colspan="2">工作经历：</td>
						</tr>
						<c:forEach items="${resume.workExperienceList }" var="we" varStatus="status">
							<tr>
								<td rowspan="3" style="width:20px;border-bottom: 1px solid rgb(133, 173, 177);">(${status.index + 1 })</td>
								<td style="width:75px;">所在公司:</td>
								<td style="text-indent:5px;">${we.companyName }</td>
								<td>在职时间: ${we.workTime }</td>
							</tr>
							<tr>
								<td>所在职位:</td>
								<td>${we.jobName }</td>
								<td>离职原因: &nbsp;&nbsp;${we.leaveReason} </td>
							</tr>
							<tr>
								<td style="border-bottom: 1px solid rgb(133, 173, 177);">自我评价:</td>
								<td colspan="2" style="border-bottom: 1px solid rgb(133, 173, 177);text-indent:5px;">
									${we.evaluation }
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<!-- 工作经历 end -->
		</div>
		
				
		<div id="floatTips" class="resumefunctiondiv">
			<input name="HidComState" id="HidComState" value="0" type="hidden" />
			<c:if test="${cookie.identity.value == 'admin' }">
				<table class="resumefunctiontable" border="0" cellpadding="0" cellspacing="0" width="100">
					<tbody>
						<tr>
							<td width="34"><img src="${contextPath}/images/open-folder.gif" height="16" width="16" />
							</td>
							<td width="66">
								<a href='${contextPath }/resume/down_back/${resume.id}'>下载简历</a>
							</td>
						</tr>
					</tbody>
				</table>
			</c:if>
			<table style="height:5px;" border="0" cellpadding="0" cellspacing="0">
				<tbody>
					<tr>
						<td></td>
					</tr>
				</tbody>
			</table>
			<table class="resumefunctiontable" border="0" cellpadding="0" cellspacing="0" width="100">
				<tbody>
					<tr>
						<td width="34"><img src="${contextPath}/images/person/print.gif" height="16" width="16" />
						</td>
						<td width="66"><a href="javascript:window.print()" onclick="window.print();">打印简历</a></td>
					</tr>
				</tbody>
			</table>
			<!-- 只有企业用户才可以发送邀请 -->
			<c:if test="${cookie.identity.value == 'company' }">
				<table class="resumefunctiontable" border="0" cellpadding="0" cellspacing="0" width="100">
					<tbody>
						<tr>
							<td width="34"><img src="${contextPath}/images/person/open-folder.gif" height="16" width="16" />
							</td>
							<td width="66"><a href="javascript:void(0);" onclick="pupopen();">发送邀请</a></td>
						</tr>
					</tbody>
				</table>
			</c:if>
	</div>
	
		<!-- 以下是弹出窗 -->
		<div id="bg"></div>
		<div id="popbox">
			<!-- 标题div -->
			<div class="poptitle">
				<div style="width:90%;float:left;">
					<span style="margin-left:10px;">面试邀请</span>
				</div>
				<div style="float:left;width:10%;">
					<a href="javascript:pupclose();">关闭</a>
				</div>
			</div>
			
			<!-- 邀请内容 -->
			<div class="popcontent">
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
		
		
		
	</div>

</body>
</html>
