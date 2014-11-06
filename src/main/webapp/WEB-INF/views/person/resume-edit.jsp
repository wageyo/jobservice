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
<link href="${contextPath}/css/header.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/css/style.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/css/user/style.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/css/user/StyleSheet.css" rel="stylesheet" type="text/css" />
<style type="text/css">
	.flow_middle{
		z-index: 998;
		position: absolute;
		border: 1px solid black;
		top: 50%;
		left: 50%;
		width: 640px;
		min-height: 150px;
		margin: 1375px auto auto -310px;
		background-color: rgb(236, 236, 248);
		color: rgb(34, 28, 184);
		padding: 15px;
		display:none;
	}
</style>
<script type="text/javascript" src="${contextPath}/js/jquery.js"></script>
<script type="text/javascript" src="${contextPath}/js/common.js"></script>
<script type="text/javascript" src="${contextPath}/js/verify.js"></script>
<script type="text/javascript" src="${contextPath}/js/widget.js"></script>
<script type="text/javascript" src="${contextPath}/js/check/resume-check.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		//新增按钮
		$('#add_we').toggle(
			function(){
				$('#div_we').show();
			},
			function(){
				$('#div_we').hide();
			}	
		);
		
		//取消按钮
		$('#add_we').toggle(
				function(){
					$('#div_we').show();
				},
				function(){
					$('#div_we').hide();
				}	
			);
		
		
	});
	
	//修改简历中 用于工作经历的公用js
	function del_edit_we(wid){
		var rid = $('#rid').val();
		var bl = window.confirm('确定要删除此工作经历么? 此操作不可恢复!');
		if(bl){
			$.ajax({
				url:'${contextPath}/secure/resume/deleteExperience',
				type:'POST',
				dataType:'json',
				data:{'id':wid},
				success:function(json){
					if(json.notice == 'success'){
						alert('删除成功!');
						window.location.href='${contextPath}/secure/resume/update?id='+rid;
					}else{
						alert('删除失败了...');
					}
				},
				error:function(){
					alert('操作失败!');
				}
			});
		}
	}

	//关闭增加div 按钮
	function close_add(){
		$('#div_we').hide();
	}

	function save_we(){
		var rid = $('#rid').val();
		var new_companyName = $('#new_companyName').val();
		var new_workTime = $('#new_workTime').val();
		var new_jobName = $('#new_jobName').val();
		var new_jobContent = $('#new_jobContent').val();
		var new_leaveReason = $('#new_leaveReason').val();
		$.ajax({
			url:'${contextPath}/secure/resume/saveExperience',
			type:'POST',
			dataType:'json',
			data:{'resume.id':rid,'companyName':new_companyName,'jobName':new_jobName,'jobContent':new_jobContent,'leaveReason':new_leaveReason},
			success:function(json){
				if(json.notice == 'success'){
					alert('保存成功!');
					window.location.href='${contextPath}/secure/resume/update?id='+rid;
				}else{
					alert('保存失败了...');
				}
			},
			error:function(){
				alert('操作失败!');
			}
		});
	}
</script>
<title>残疾人招聘就业网</title>
</head>
<body>
	<jsp:include page="../header.jsp" />
	<jsp:include page="../nav.jsp" />

	<div class="w988 ptb10 center">
		<jsp:include page="left-nav.jsp" />

		<div class="Air">
			<form action="${contextPath }/secure/resume/update" method="post" onsubmit="return resume_check();">
				<table class="w770" border="0" cellspacing="0" cellpadding="0" align="center">
					<tbody>
						<tr valign="top">
							<td>
								<table border="0" cellspacing="0" cellpadding="0" class="table_boder" align="center">
									<tbody>
										<tr>
											<td class="uu1" align="left" colspan="2"><b> <span id="ctl00_ContentPlaceHolder1_Label1">修改简历</span>
											</b></td>
										</tr>
									</tbody>
								</table></td>
						</tr>
					</tbody>
				</table>
				<div class="AntAccout_Center table_boder p10 mb10 center">
					<table width="100%" border="0" cellpadding="0" cellspacing="0" align="center">
						<tbody>
							<tr>
								<td colspan="2" align="center" class="line35" id="title55"><b>基本信息</b></td>
							</tr>
							<tr>
								<td align="right" class="line35"><span class="red line35_r">*</span> <b>标题 ：</b></td>
								<td align="left"><input name="title" type="text" id="title" class="input_border" style="width:200px;" value="${resume.title }" /> <span id="ctl00_ContentPlaceHolder1_RequiredFieldValidator1"
										style="color:Red;font-style:italic;visibility:hidden;">标题不能为空</span>
										<input type="hidden" name="id" id="rid" value="${resume.id }" />
								</td>
							</tr>
							<tr>
								<td align="right" class="line35"><span class="red line35_r">*</span> <b>姓名 ：</b></td>
								<td align="left"><input name="name" type="text" id="name" class="input_border" style="width:200px;" value="${resume.name }" />
									<span style="color:blue;font-style:微软雅黑;">请填写个人真实姓名，以便求职</span>
									 <span id="ctl00_ContentPlaceHolder1_RequiredFieldValidator1"
										style="color:Red;font-style:italic;visibility:hidden;">求职者姓名不能为空</span>
								</td>
							</tr>
							<tr>
								<td align="right" class="line35"><b>性别 ：</b></td>
								<td align="left"><select name="gender" id="gender" class="select_border">
										<c:forEach items="${params }" var="p">
											<c:if test="${p.type == 'gender' }">
												<option value="${p.value }" <c:if test="${resume.gender == p.value }">selected="selected"</c:if>>${p.name }</option>
											</c:if>
										</c:forEach>
								</select></td>
							</tr>
							<tr>
								<td align="right" class="line35"><b> 民族 ：</b></td>
								<td align="left"><input name="race" type="text" id="ctl00_ContentPlaceHolder1_TextBox14" class="input_border" style="width:200px;" value="${resume.race }" /> <span
										id="ctl00_ContentPlaceHolder1_RequiredFieldValidator3" style="display:inline-block;color:Red;border-width:1px;border-style:solid;font-style:italic;visibility:hidden;">年龄不能为空</span>
								</td>
							</tr>
							<tr>
								<td align="right" class="line35"><b>出生日期 ：</b></td>
								<td align="left">
									<select name="year" id="year" class="select_border" style="width:70px;">
										<option value="0">请选择</option>
										<c:forEach begin="1953" end="1997" var="i">
												<option value="${i}" <c:if test="${fn:substring(resume.birth,0,4) == i }">selected="selected"</c:if>>${i }年</option>
										</c:forEach>
									</select>
									<select name="month" id="month" class="select_border" style="width:70px;">
										<option value="0">请选择</option>
										<c:forEach begin="1" end="12" var="i">
												<option value="${i}" <c:if test="${fn:substring(resume.birth,5,7) == i }">selected="selected"</c:if>>${i }月</option>
										</c:forEach>
									</select>
									<select name="day" id="day" class="select_border" style="width:70px;" onchange="change_birth()">
										<option value="0">请选择</option>
										<c:forEach begin="1" end="31" var="i">
												<option value="${i}" <c:if test="${fn:substring(resume.birth,8,10) == i }">selected="selected"</c:if>>${i }日</option>
										</c:forEach>
									</select>
									<input type="hidden" name="birth" id="birth" value="${resume.birth }"/>
								</td>
							</tr>
							<tr>
								<td align="right" class="line35"><b>户口状况 ：</b></td>
								<td align="left"><select name="hukouStatus" id="ctl00_ContentPlaceHolder1_DropDownList7" class="select_border">
										<c:forEach items="${params }" var="p">
											<c:if test="${p.type == 'huKou' }">
												<option value="${p.value }" <c:if test="${resume.hukouStatus == p.value }">selected="selected"</c:if>>${p.name }</option>
											</c:if>
										</c:forEach>
								</select></td>
							</tr>
							<tr>
								<td align="right" class="line35"><span class="red line35_r">*</span><b>户籍所在地 ：</b></td>
								<td align="left">
									<select name="area_lv12" id="area_lv12" class="select_border" style="width:150px;">
										<c:forEach items="${provinceList }" var="area">
												<option value="${area.code }">${area.name }</option>
										</c:forEach>
									</select>
									<select name="area_lv22" id="area_lv22" class="select_border" style="width:150px;">
										<option value="">请选择城市</option>
									</select>
									<select name="homeTown" id="area_lv32" class="select_border" style="width:150px;">
										<option value="${resume.homeTown }">请选择区域</option>
									</select>
									<p style="color:red;text-indent: 1em;">请精确到区县级</p>
								</td>
							</tr>
							<tr>
								<td align="right" class="line35"><b>婚姻状况 ：</b></td>
								<td align="left"><select name="marriage" id="ctl00_ContentPlaceHolder1_DropDownList7" class="select_border">
										<c:forEach items="${params }" var="p">
											<c:if test="${p.type == 'marriage' }">
												<option value="${p.value }" <c:if test="${resume.marriage == p.value }">selected="selected"</c:if>>${p.name }</option>
											</c:if>
										</c:forEach>
								</select></td>
							</tr>
							<tr>
								<td align="right" class="line35"><b>住址 ：</b></td>
								<td align="left"><input name="hukouAddress" type="text" id="ctl00_ContentPlaceHolder1_TextBox12" class="input_border" style="width:300px;" value="${resume.hukouAddress }" />
								</td>
							</tr>
							<tr>
								<td align="right" class="line35"><b>现居居地 ：</b></td>
								<td align="left"><input name="address" type="text" id="ctl00_ContentPlaceHolder1_TextBox22" class="input_border" style="width:200px;" value="${resume.address }" />
								</td>
							</tr>
							<tr>
								<td colspan="2" align="center" class="line35" id="title55"><b>联系方式</b></td>
							</tr>
							<tr>
								<td align="right" class="line35"><span class="red line35_r">*</span><b> 电话/手机 ：</b></td>
								<td align="left">
									<input name="phone" type="text" id="phone" class="input_border" style="width:200px;" value="${resume.phone }" /> 
									<span style="color:blue;font-style:微软雅黑;">参考格式(55664482, 0451-55664482, 13812345678三种)</span>
								</td>
							</tr>
							<tr>
								<td align="right" class="line35"><b>联系QQ ：</b></td>
								<td align="left"><input name="qq" type="text" id="qq" class="input_border" style="width:200px;" value="${resume.qq }" />
								</td>
							</tr>
							<tr>
								<td align="right" class="line35"><b>邮箱 ：</b></td>
								<td align="left"><input name="email" type="text" id="email" class="input_border" style="width:200px;" value="${resume.email }" />
								</td>
							</tr>

							<!-- 残疾情况 -->
							<tr>
								<td colspan="2" align="center" class="line35" id="title55"><b>残疾情况</b></td>
							</tr>
							<tr>
								<td align="right" class="line35"><span class="red line35_r">*</span><b>残疾证号 ：</b></td>
								<td align="left"><input name="disabilityCard" type="text" value="${resume.disabilityCard }" id="disabilityCard" class="input_border" style="width:200px;" />
								</td>
							</tr>
							<tr>
								<td align="right" class="line35"><span class="red line35_r">*</span><b> 残疾类别 ：</b></td>
								<td align="left"><select name="disabilityCategory" id="disabilityCategory" class="select_border">
										<c:forEach items="${params }" var="p">
											<c:if test="${p.type == 'disabilityCategory' }">
												<option value="${p.value }" <c:if test="${resume.disabilityCategory == p.value }">selected="selected"</c:if>>${p.name }</option>
											</c:if>
										</c:forEach>
								</select></td>
							</tr>
							<tr>
								<td align="right" class="line35"><span class="red line35_r">*</span><b> 残疾等级 ：</b></td>
								<td align="left"><select name="disabilityLevel" id="disabilityLevel" class="select_border">
										<c:forEach items="${params }" var="p">
											<c:if test="${p.type == 'disabilityLevel' }">
												<option value="${p.value }" <c:if test="${resume.disabilityLevel == p.value }">selected="selected"</c:if>>${p.name }</option>
											</c:if>
										</c:forEach>
								</select></td>
							</tr>
							<tr>
								<td align="right" class="line35"><span class="red line35_r">*</span><b>残疾部位 ：</b></td>
								<td align="left">
									<select name="disabilityPart" id="disabilityPart" class="select_border">
										<c:forEach items="${params }" var="p">
											<c:if test="${p.type == 'disabilityPart' }">
												<option value="${p.value }" <c:if test="${resume.disabilityPart == p.value }">selected="selected"</c:if>>${p.name }</option>
											</c:if>
										</c:forEach>
									</select>
								</td>
							</tr>

							<!-- 求职意向/情况 -->
							<tr>
								<td colspan="2" align="center" class="line35" id="title55"><b>求职意向</b></td>
							</tr>
							<tr>
								<td align="right" class="line35"><b>就业失业登记证号 ：</b></td>
								<td align="left"><input name="shiYeHao" type="text" id="ctl00_ContentPlaceHolder1_TextBox11" class="input_border" style="width:200px;"  value="${resume.shiYeHao }"/>
								</td>
							</tr>
							<tr>
								<td align="right" class="line35"><b>职位类型：</b></td>
								<td align="left"><select name="jobNature" id="ctl00_ContentPlaceHolder1_TextBox17" class="select_border">
										<c:forEach items="${params }" var="p">
											<c:if test="${p.type == 'jobNature' }">
												<option value="${p.value }" <c:if test="${resume.jobNature == p.value }">selected="selected"</c:if>>${p.name }</option>
											</c:if>
										</c:forEach>
								</select></td>
							</tr>
							<tr>
								<td align="right" class="line35"><b>期望职位类别 ：</b></td>
								<td align="left">
									<select name="jobCategory_lv1" id="jobCategory_lv1" class="select_border" style="width:150px;">
										<c:forEach items="${jcList }" var="p">
											<option value="${p.code }">${p.name }</option>
										</c:forEach>
									</select>
									<select name="jobCategory_lv2" id="jobCategory_lv2" class="select_border" style="width:150px;">
										<option value="">请选择</option>
									</select>
									<select name="desireJob" id="jobCategory_lv3" class="select_border" style="width:150px;">
										<option value="">请选择</option>
									</select>
								<!-- 	<p style="color:red;text-indent: 1em;">请选择具体职位</p>	 -->
								</td>
							</tr>
							<tr>
								<td align="right" class="line35"><b>期望工作地点 ：</b></td>
								<td align="left">
									<select name="area_lv1" id="area_lv1" class="select_border" style="width:150px;">
										<c:forEach items="${provinceList }" var="area">
												<option value="${area.code }">${area.name }</option>
										</c:forEach>
									</select>
									<select name="area_lv2" id="area_lv2" class="select_border" style="width:150px;">
										<option value="">请选择城市</option>
									</select>
									<select name="desireAddress" id="area_lv3" class="select_border" style="width:150px;">
										<option value="">请选择区域</option>
									</select>
								<!-- 	<p style="color:red;text-indent: 1em;">请精确到区县级</p>	 -->
								</td>
							</tr>
							<tr>
								<td align="right" class="line35"><b>期望薪水 ：</b></td>
								<td align="left"><select name="desireSalary" id="ctl00_ContentPlaceHolder1_TextBox17" class="select_border">
										<c:forEach items="${params }" var="p">
											<c:if test="${p.type == 'salary' }">
												<option value="${p.value }" <c:if test="${resume.desireSalary == p.value }">selected="selected"</c:if>>${p.name }</option>
											</c:if>
										</c:forEach>
								</select></td>
							</tr>
							<tr>
								<td align="right" class="line35" style="padding: 0px 5px 0px 10px;"><b>其他福利待遇 ：</b></td>
								<td align="left" style="padding: 0px 5px 0px 10px;"><c:forEach items="${params }" var="p">
										<c:if test="${p.type == 'benefit' }">
											<input type="checkbox" name="${p.value}" value="1">${p.name }&nbsp;&nbsp; 
										</c:if>
									</c:forEach></td>
							</tr>
							<tr>
								<td align="right" class="line35"><b>其他要求 ：</b></td>
								<td align="left"><textarea name="provideOther" rows="2" cols="20" id="ctl00_ContentPlaceHolder1_TextBox19" class="input_border"
										style="height:80px;width:450px;font-size: 12px; padding: 6px;">${resume.provideOther }</textarea></td>
							</tr>
							<tr>
								<td align="right" class="line35"><b>到岗时间 ：</b></td>
								<td align="left" style="padding: 0px 5px 0px 10px;"><c:forEach items="${params }" var="p">
										<c:if test="${p.type == 'state' }">
											<input type="radio" name="state" value="${p.value }" <c:if test="${resume.state == p.value }">checked="checked"</c:if>>${p.name }<br />
										</c:if>
									</c:forEach></td>
							</tr>

							<!-- 教育培训 -->
							<tr>
								<td colspan="2" align="center" class="line35" id="title55"><b>教育培训</b></td>
							</tr>
							<tr>
								<td align="right" class="line35"><span class="red line35_r">*</span><b>学历 ：</b></td>
								<td align="left"><select name="education" id="education" class="select_border">
										<c:forEach items="${params }" var="p">
											<c:if test="${p.type == 'education' }">
												<option value="${p.value }" <c:if test="${resume.education == p.value }">selected="selected"</c:if>>${p.name }</option>
											</c:if>
										</c:forEach>
								</select></td>
							</tr>
							<tr>
								<td align="right" class="line35"><b>专业 ：</b></td>
								<td align="left"><input name="major" type="text"  value="${resume.major }" id="major" class="input_border" style="width:200px;" />
								</td>
							</tr>
							<tr>
								<td align="right" class="line35"><b>毕业院校 ：</b></td>
								<td align="left"><input name="school" type="text" value="${resume.school }" id="ctl00_ContentPlaceHolder1_TextBox11" class="input_border" style="width:200px;" />
								</td>
							</tr>
							<tr>
								<td align="right" class="line35"><b>特长 ：</b></td>
								<td align="left"><input name="experts" type="text" value="${resume.experts }" id="ctl00_ContentPlaceHolder1_TextBox11" class="input_border" style="width:200px;" />
								</td>
							</tr>
							<tr>
								<td align="right" class="line35"><b>培训情况或需求 ：</b></td>
								<td align="left"><input name="training" type="text" value="${resume.training }" id="ctl00_ContentPlaceHolder1_TextBox11" class="input_border" style="width:200px;" />
								</td>
							</tr>
							<tr>
								<td align="right" class="line35"><b>自我评价 ：</b></td>
								<td align="left" class="ptb5"><textarea name="selfEvaluation" rows="2" cols="20" id="ctl00_ContentPlaceHolder1_TextBox9" class="input_border" style="height:150px;width:450px;">${resume.selfEvaluation }</textarea>
								</td>
							</tr>

							<tr>
								<td colspan="2" align="center" class="line35" id="title55"><b>工作经历</b></td>
							</tr>
							<tr>
								<td align="right" class="line35" colspan="2">
									<table id="tb" width="90%">
										<tr>
											<td style="width:570px;text-align:left;">
												此处可以添加多份工作经验,单击右边的 <span style="color:blue;">"新增"</span> 按钮添加额外的工作经验
											</td>
											<td style="text-align:center;">
												<a href="javascript:void(0);" id="add_we"><span style="color:blue;">新增</span></a>
											</td>
										</tr>
										<c:if test="${resume.workExperienceList != null}">
											<c:forEach items="${resume.workExperienceList }" var="we">
								 				<tr style="text-align:left;">
													<td >
														<input type="hidden" name="wid" value="${we.id }" />
														<p><span class="span_assign_width" style="width:250px;">公司 : <input type="text" size="35" name="companyName" value="${we.companyName }"  /></span>在职时间 : <input type="text" name="workTime" value="${we.workTime }"/> <span style="color:red;"> (格式:2003.2-2005.8)</span></p>
														<p><span class="span_assign_width" style="width:250px;">职位 : <input type="text" size="35" name="jobName" value="${we.jobName }"/></span>离职原因 : <input type="text" size="45" name="leaveReason" value="${we.leaveReason }"/></p>
														<p><span class="span_assign_width" style="width:36px;">&nbsp;</span><textarea rows="4" cols="100" name="jobContent">${we.jobContent }</textarea></p>
														<hr/>
													</td>
													<td onClick="del_edit_we(${we.id})" onmouseover="mouseover_td(this);" onmouseout="mouseout_td(this)">
														<a href="javascript:void(0);">删除</a>
													</td>
												</tr>
											</c:forEach>
										</c:if>
									</table>
								</td>
							</tr>
							<tr>
								<td align="right" class="line35"><b>工作年限 ：</b></td>
								<td align="left"><select name="experience" id="ctl00_ContentPlaceHolder1_DropDownList1" class="select_border">
										<c:forEach items="${params }" var="p">
											<c:if test="${p.type == 'experience' }">
												<option value="${p.value }" <c:if test="${resume.experience == p.value }">selected="selected"</c:if> >${p.name }</option>
											</c:if>
										</c:forEach>
								</select></td>
							</tr>
							<tr>
								<td align="right" class="line35"><b>职业测评情况 ：</b></td>
								<td align="left"><input name="careerTest" type="text" value="${resume.careerTest }" id="ctl00_ContentPlaceHolder1_TextBox11" class="input_border" style="width:200px;"  />
								</td>
							</tr>
							<tr>
								<td align="right" class="line35"><b>办理情况 ：</b></td>
								<td align="left"><input name="processState" type="text" value="${resume.processState }" id="ctl00_ContentPlaceHolder1_TextBox11" class="input_border" style="width:200px;" />
								</td>
							</tr>

							<tr>
								<td align="left" colspan="2" class="line35" style="padding-left: 148px;"><input type="submit" value="提 交" id="ctl00_ContentPlaceHolder1_Button1" class="btn_input mt5"
									style="background: url(${contextPath}/images/edit_btn1.gif) no-repeat;" /></td>
							</tr>
						</tbody>
					</table>
				</div>
			</form>
			
			<div id="div_we" class="flow_middle">
				<table id="tb" width="100%">
					<tr>
						<td style="width:570px;">
							此处可以添加工作经验,单击右边的 "保存" 以保存您填写的经历.
						</td>
						<td style="text-align:center;">
							<a href="javascript:save_we();"><span style="color:blue;">保存</span></a>
							<a href="javascript:close_add();"><span style="color:blue;">取消</span></a>
						</td>
					</tr>
	 				<tr>
						<td colspan="2">
							<p><span class="span_assign_width" style="width:250px;">公司 : <input type="text" size="35" id="new_companyName" /></span>在职时间 : <input type="text" id="new_workTime" "/> <span style="color:red;"> (格式:2003.2-2005.8)</span></p>
							<p><span class="span_assign_width" style="width:250px;">职位 : <input type="text" size="35" id="new_jobName" /></span>离职原因 : <input type="text" size="45" id="new_leaveReason"/></p>
							<p><span class="span_assign_width" style="width:36px;">&nbsp;</span><textarea rows="4" cols="100" id="new_jobContent" onfocus="if (this.value == '自我评价,所负责的事物等等.') {this.value = '';}" onblur="if (this.value == '') {this.value = '自我评价,所负责的事物等等.';}">自我评价,所负责的事物等等.</textarea></p>
						</td>
					</tr>
				</table>
			</div>
			
			
		</div>

		<div style="clear: both;"></div>
	</div>

	<jsp:include page="../footer.jsp" />


</body>
</html>