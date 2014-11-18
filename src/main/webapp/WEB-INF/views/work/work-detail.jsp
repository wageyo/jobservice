<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="keywords" content="残疾人招聘信息,残疾人就业信息,残疾人人才网,残疾人找工作" />
<meta content="残疾人招聘就业" name="description" />
<link href="${contextPath}/css/header.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/css/style_job.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/css/body_job.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/css/search.css" rel="stylesheet" type="text/css" />
<title>残疾人就业信息网</title>
<script type="text/javascript" src="${contextPath}/js/jquery.js"></script>
<script type="text/javascript">
	function send_resume(jid){
		$.ajax({
			url:'${contextPath}/secure/job/sendResume',
			type:'POST',
			dataType:'json',
			data:{'jid':jid},
			success:function(json){
				if(json.notice == 'success'){
					alert('投递成功!');
				}else{
					alert(json.notice);
				}
			},
			error:function(){
				alert('操作失败!');
			}
		});
	}
</script>
</head>
<body>
	<jsp:include page="../header.jsp" />

	<jsp:include page="../nav.jsp" />


	<div id="xBody">
		<!--头部结束--->
		<div id="xdAL" style="width: 1px;"></div>
		<!--内容页面开始-->
		<div class="imga">
			<a href="${contextPath}/index" style="border: 0"><img src="${contextPath}/images/c2e34940b08940b48f3255b493b4ba68_zph_2013_ad_full.gif" alt="招聘频道" width="980" height="80"
				border='0'></a>
		</div>

		<div class="xxE1H12">
				<div class="company11">
					<a href="${contextPath }/company/getOneForShow?id=${job.company.id}" >${job.company.name }</a>
					<p>企业性质：${job.company.nature }</p>
					<p>公司地址：${job.company.address }</p>
					<p>联系人：${job.company.contactPerson }</p>
				</div>
		</div>

		<div class="xxE1">

			<div class="xxE1H">
				招聘职位：<span id="ctl00_ctl00_cph_cph_lb_jobname">${job.name }</span>
			</div>


			<div class="xxE1B" style=" margin-top:10px; text-indent:5px;">
				招聘职位：&nbsp;&nbsp;&nbsp;&nbsp;<span id="ctl00_ctl00_cph_cph_lb_jobname">${job.name }</span>
			</div>

			<div class="xxE1B">
				<table class="xxE1Bt">
					<tbody>
						<tr class="job_detail">
							<td class="k1">岗位类别：</td>
							<td colspan="3"><span id="ctl00_ctl00_cph_cph_lb_posttype">${job.jobCategory.name}</span></td>
						</tr>
						<tr class="job_detail">
							<td>招聘人数：</td>
							<td style="width: 260px;"><span id="ctl00_ctl00_cph_cph_lb_n">${job.hireNumber }</span>名</td>
							<td class="k1">更新日期：</td>
							<td>
								<c:if test="${job.updateDate != null}">
									<fmt:formatDate value="${job.updateDate}" type="both" dateStyle="long" pattern="yyyy-MM-dd hh:mm:ss" var="updateDate" />
								</c:if>
								<span id="ctl00_ctl00_cph_cph_lb_vtime">${updateDate }</span>
							</td>
						</tr>
						<tr class="job_detail">
							<td>学历要求：</td>
							<td><span id="ctl00_ctl00_cph_cph_lb_age">${job.education }</span></td>
							<td>性别要求：</td>
							<td><span id="ctl00_ctl00_cph_cph_lb_gender">${job.gender }</span></td>
						</tr>
						<tr class="job_detail">
							<td>工作性质：</td>
							<td><span id="ctl00_ctl00_cph_cph_lb_education">${job.nature }</span></td>
							<td>工龄要求：</td>
							<td><span id="ctl00_ctl00_cph_cph_lb_workage">${job.experience }</span></td>
						</tr>
						<tr class="job_detail">
							<td>工作地区：</td>
							<td>
								<span id="ctl00_ctl00_cph_cph_lb_workplace">
									<c:choose>
										<c:when test="${job.workPlace != null }">
											${job.workPlace.name }
										</c:when>
										<c:otherwise>
											不限
										</c:otherwise>
									</c:choose>
								</span>
							</td>
							<td>薪资待遇：</td>
							<td><span id="ctl00_ctl00_cph_cph_lb_wage">${job.salary }</span></td>
						</tr>
					</tbody>
				</table>
				<div id="xxJbtns">
					<c:if test="${user.identity == 'person' }">
						<input type="image" name="ImageButton1" onclick="send_resume(${job.id })"src="${contextPath}/images/aduo01.gif" style="border-width:0px;" />
					</c:if> 
				</div>
			</div>
		</div>
		<div class="xxE2">
			<div class="xxE2H">职位描述</div>
			<div class="zhimiao">
				<p>${job.description }</p>
			</div>
		</div>
	</div>
	<div class="xxE2">
		<div class="xxE2H" style="margin: 0 auto">联系方式</div>
		<div class="zhimiao">
			<table class="xxE1Bt">
				<tbody>
					<c:if test="${user == null }">
						<tr>
							<td colspan="2">个人会员请 <a href="${contextPath}/login" style="color:blue;">登录</a> 查看联系方式！如果您不是个人会员，请先 <a href="${contextPath }/regP" style="color:blue;">免费注册</a> 成为个人会员！</td>
						</tr>
					</c:if>
					<c:if test="${user != null }">
						<tr >
							<td width="100px" >电话：</td>
							<td><span id="ctl00_ctl00_cph_cph_lb_age">${job.company.telephone }</span></td>
						</tr>
						<tr >
							<td>联系部门：</td>
							<td><span id="ctl00_ctl00_cph_cph_lb_education">${job.company.contactDept }</span></td>
						</tr>
						<tr >
							<td>联系人：</td>
							<td><span id="ctl00_ctl00_cph_cph_lb_workplace">${job.company.contactPerson }</span></td>
						</tr>
					</c:if>
				</tbody>
			</table>
		</div>
	</div>
	<div id="ctl00_ctl00_cph_cph_div_jobs" class="xxE2">
		<div class="xxE2H">本公司的其它招聘信息</div>
		<div class="xxE2B">
			<table class="xxEpl" cellspacing="0">
				<tbody>
					<tr class="zhaopin">
						<th class="t0">职位名称</th>
						<th>招聘人数</th>
						<th>岗位类型</th>
						<th>学历要求</th>
						<th>更新时间</th>
						<th>工作性质</th>
					</tr>
					<c:forEach items="${jobList }" var = "jj">
						<c:if test="${jj.id != job.id }">
							<tr class="zhaopin1">
								<td class="jobs01"><a href="${contextPath }/job/getOneForShow?id=${jj.id}">${jj.name }</a></td>
								<td>${jj.hireNumber }</td>
								<td>${job.jobCategory.name }</td>
								<td>${job.education }</td>
								<td>
									<fmt:formatDate value="${job.updateDate }"  dateStyle="default" pattern="yyyy-MM-dd HH:mm:ss"/>
								</td>
								<td>${job.nature }</td>
							</tr>
						</c:if>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	<jsp:include page="../footer.jsp" />
</body>
</html>