<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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

<script type="text/javascript" src="${contextPath}/js/jquery.js"></script>
<script type="text/javascript" src="${contextPath}/js/common.js"></script>
<script type="text/javascript" src="${contextPath}/js/verify.js"></script>
<script type="text/javascript" src="${contextPath}/js/widget.js"></script>
<script type="text/javascript" src="${contextPath}/js/check/resume-check.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
	});
</script>


<title>残疾人就业信息网</title>
</head>
<body>
	<jsp:include page="../header.jsp" />
	<jsp:include page="../nav.jsp" />

	<div class="w988 ptb10 center">
		<jsp:include page="left-nav.jsp" />

		<div class="Air">
			<form action="${contextPath }/secure/resume/save" method="post" onSubmit="return resume_check()">
				<table class="w770" border="0" cellspacing="0" cellpadding="0" align="center">
					<tbody>
						<tr valign="top">
							<td>
								<table border="0" cellspacing="0" cellpadding="0" class="table_boder" align="center">
									<tbody>
										<tr>
											<td class="uu1" align="left" colspan="2"><b> <span id="ctl00_ContentPlaceHolder1_Label1">简历管理&gt;&gt;添加简历</span>
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
								<td colspan="2" align="center" class="line35" id="title55"><b>基本信息</b><span style="font-weight:normal;margin-left:20px;">带<span class="red line35_r">*</span>的为必填项 </span></td>
							</tr>
							<tr>
								<td align="right" class="line35"><span class="red line35_r">*</span> <b>标题 ：</b></td>
								<td align="left"><input name="title" type="text" id="title" class="input_border" style="width:200px;"> <span id="ctl00_ContentPlaceHolder1_RequiredFieldValidator1"
										style="color:Red;font-style:italic;visibility:hidden;">标题不能为空</span>
								</td>
							</tr>
							<tr>
								<td align="right" class="line35"><span class="red line35_r">*</span> <b>姓名 ：</b></td>
								<td align="left">
									<input name="name" type="text" id="name" class="input_border" style="width:200px;">
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
												<option value="${p.value }">${p.name }</option>
											</c:if>
										</c:forEach>
								</select></td>
							</tr>
							<tr>
								<td align="right" class="line35"><b> 民族 ：</b></td>
								<td align="left"><input name="race" type="text" id="ctl00_ContentPlaceHolder1_TextBox14" class="input_border" style="width:200px;"> <span
										id="ctl00_ContentPlaceHolder1_RequiredFieldValidator3" style="display:inline-block;color:Red;border-width:1px;border-style:solid;font-style:italic;visibility:hidden;">年龄不能为空</span>
								</td>
							</tr>
							<tr>
								<td align="right" class="line35"><span class="red line35_r">*</span><b>出生日期 ：</b></td>
								<td align="left">
									<select name="year" id="year" class="select_border" style="width:70px;">
										<option value="0">请选择</option>
										<c:forEach begin="1953" end="1997" var="i">
												<option value="${i}">${i }年</option>
										</c:forEach>
									</select>
									<select name="month" id="month" class="select_border" style="width:70px;">
										<option value="0">请选择</option>
										<c:forEach begin="1" end="12" var="i">
												<option value="${i}">${i }月</option>
										</c:forEach>
									</select>
									<select name="day" id="day" class="select_border" style="width:70px;" onchange="change_birth()">
										<option value="0">请选择</option>
										<c:forEach begin="1" end="31" var="i">
												<option value="${i}">${i }日</option>
										</c:forEach>
									</select>
									<input type="hidden" name="birth" id="birth" />
								</td>
							</tr>
							<tr>
								<td align="right" class="line35"><b>户口状况 ：</b></td>
								<td align="left">
								<select name="hukouStatus" id="ctl00_ContentPlaceHolder1_DropDownList7" class="select_border">
										<c:forEach items="${params }" var="p">
											<c:if test="${p.type == 'huKou' }">
												<option value="${p.value }">${p.name }</option>
											</c:if>
										</c:forEach>
								</select></td>
							</tr>
							<tr>
								<td align="right" class="line35"><b>户籍所在地 ：</b></td>
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
										<option value="">请选择区域</option>
									</select>
									<p style="color:red;text-indent: 1em;">请精确到区县级</p>
								</td>
							</tr>
							<tr>
								<td align="right" class="line35"><b>婚姻状况 ：</b></td>
								<td align="left"><select name="marriage" id="ctl00_ContentPlaceHolder1_DropDownList7" class="select_border">
										<c:forEach items="${params }" var="p">
											<c:if test="${p.type == 'marriage' }">
												<option value="${p.value }">${p.name }</option>
											</c:if>
										</c:forEach>
								</select></td>
							</tr>
							<tr>
								<td align="right" class="line35"><b>住址 ：</b></td>
								<td align="left"><input name="hukouAddress" type="text" id="ctl00_ContentPlaceHolder1_TextBox12" class="input_border" style="width:300px;">
								</td>
							</tr>
							<tr>
								<td align="right" class="line35"><b>现居居地 ：</b></td>
								<td align="left"><input name="address" type="text" id="ctl00_ContentPlaceHolder1_TextBox22" class="input_border" style="width:200px;">
								</td>
							</tr>
							<tr>
								<td colspan="2" align="center" class="line35" id="title55"><b>联系方式</b></td>
							</tr>
							<tr>
								<td align="right" class="line35"><b> 电话/手机 ：</b></td>
								<td align="left"><input name="phone" type="text" id="phone" class="input_border" style="width:200px;"> <span
										id="ctl00_ContentPlaceHolder1_RequiredFieldValidator2" style="display:inline-block;color:Red;border-width:1px;border-style:solid;font-style:italic;margin-bottom:0px;visibility:hidden;">手机不能为空</span>
								</td>
							</tr>
							<tr>
								<td align="right" class="line35"><b>联系QQ ：</b></td>
								<td align="left"><input name="qq" type="text" id="ctl00_ContentPlaceHolder1_TextBox10" class="input_border" style="width:200px;">
								</td>
							</tr>
							<tr>
								<td align="right" class="line35"><b>邮箱 ：</b></td>
								<td align="left"><input name="email" type="text" id="ctl00_ContentPlaceHolder1_TextBox2" class="input_border" style="width:200px;">
								</td>
							</tr>

							<!-- 残疾情况 -->
							<tr>
								<td colspan="2" align="center" class="line35" id="title55"><b>残疾情况</b></td>
							</tr>
							<tr>
								<td align="right" class="line35"><span class="red line35_r">*</span><b>残疾证号 ：</b></td>
								<td align="left"><input name="disabilityCard" type="text" id="disabilityCard" class="input_border" style="width:200px;">
								</td>
							</tr>
							<tr>
								<td align="right" class="line35"><span class="red line35_r">*</span><b> 残疾类别 ：</b></td>
								<td align="left">
									<select name="disabilityCategory" id="disabilityCategory" class="select_border">
										<c:forEach items="${params }" var="p">
											<c:if test="${p.type == 'disabilityCategory' }">
												<option value="${p.value }">${p.name }</option>
											</c:if>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<td align="right" class="line35"><span class="red line35_r">*</span><b> 残疾等级 ：</b></td>
								<td align="left"><select name="disabilityLevel" id="disabilityLevel" class="select_border">
										<c:forEach items="${params }" var="p">
											<c:if test="${p.type == 'disabilityLevel' }">
												<option value="${p.value }">${p.name }</option>
											</c:if>
										</c:forEach>
								</select></td>
							</tr>
							<tr>
								<td align="right" class="line35"><span class="red line35_r">*</span><b>残疾部位 ：</b></td>
								<td align="left"><select name="disabilityPart" id="disabilityPart" class="select_border">
										<c:forEach items="${params }" var="p">
											<c:if test="${p.type == 'disabilityPart' }">
												<option value="${p.value }">${p.name }</option>
											</c:if>
										</c:forEach>
								</select></td>
							</tr>

							<!-- 求职意向/情况 -->
							<tr>
								<td colspan="2" align="center" class="line35" id="title55"><b>求职意向</b></td>
							</tr>
							<tr>
								<td align="right" class="line35"><b>就业失业登记证号 ：</b></td>
								<td align="left"><input name="shiYeHao" type="text" id="ctl00_ContentPlaceHolder1_TextBox11" class="input_border" style="width:200px;">
								</td>
							</tr>
							<tr>
								<td align="right" class="line35"><b>职位类型 ：</b></td>
								<td align="left">
									<select name="jobNature" id="ctl00_ContentPlaceHolder1_TextBox17" class="select_border">
										<c:forEach items="${params }" var="p">
											<c:if test="${p.type == 'jobNature' }">
												<option value="${p.value }">${p.name }</option>
											</c:if>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<td align="right" class="line35"><span class="red line35_r">*</span><b>期望职位类别 ：</b></td>
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
								<td align="right" class="line35"><span class="red line35_r">*</span><b>期望工作地 ：</b></td>
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
												<option value="${p.value }">${p.name }</option>
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
										style="height:80px;width:450px;font-size: 12px; padding: 6px;"></textarea></td>
							</tr>
							<tr>
								<td align="right" class="line35"><b>到岗时间 ：</b></td>
								<td align="left" style="padding: 0px 5px 0px 10px;"><c:forEach items="${params }" var="p">
										<c:if test="${p.type == 'state' }">
											<input type="radio" name="state" value="${p.value }">${p.name }<br />
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
												<option value="${p.value }">${p.name }</option>
											</c:if>
										</c:forEach>
								</select></td>
							</tr>
							<tr>
								<td align="right" class="line35"><b>专业 ：</b></td>
								<td align="left"><input name="major" type="text" id="major" class="input_border" style="width:200px;" />
								</td>
							</tr>
							<tr>
								<td align="right" class="line35"><b>毕业院校 ：</b></td>
								<td align="left"><input name="school" type="text" id="ctl00_ContentPlaceHolder1_TextBox11" class="input_border" style="width:200px;">
								</td>
							</tr>
							<tr>
								<td align="right" class="line35"><b>特长 ：</b></td>
								<td align="left"><input name="experts" type="text" id="ctl00_ContentPlaceHolder1_TextBox11" class="input_border" style="width:200px;">
								</td>
							</tr>
							<tr>
								<td align="right" class="line35"><b>培训情况或需求 ：</b></td>
								<td align="left"><input name="training" type="text" id="ctl00_ContentPlaceHolder1_TextBox11" class="input_border" style="width:200px;">
								</td>
							</tr>
							<tr>
								<td align="right" class="line35"><b>自我评价 ：</b></td>
								<td align="left" class="ptb5"><textarea name="selfEvaluation" rows="2" cols="20" id="ctl00_ContentPlaceHolder1_TextBox9" class="input_border" style="height:150px;width:450px;"></textarea>
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
												<a href="javascript:add_tr();"><span style="color:blue;">新增</span></a>
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td align="right" class="line35"><b>总工作年限 ：</b></td>
								<td align="left"><select name="experience" id="ctl00_ContentPlaceHolder1_DropDownList1" class="select_border">
										<c:forEach items="${params }" var="p">
											<c:if test="${p.type == 'experience' }">
												<option value="${p.value }">${p.name }</option>
											</c:if>
										</c:forEach>
								</select></td>
							</tr>
							<tr>
								<td align="right" class="line35"><b>职业测评情况 ：</b></td>
								<td align="left"><input name="careerTest" type="text" id="ctl00_ContentPlaceHolder1_TextBox11" class="input_border" style="width:200px;">
								</td>
							</tr>
							<tr>
								<td align="right" class="line35"><b>办理情况 ：</b></td>
								<td align="left"><input name="processState" type="text" id="ctl00_ContentPlaceHolder1_TextBox11" class="input_border" style="width:200px;">
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

		</div>

		<div style="clear: both;"></div>
	</div>

	<jsp:include page="../footer.jsp" />


</body>
</html>