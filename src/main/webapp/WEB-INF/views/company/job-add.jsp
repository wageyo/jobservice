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
<script type="text/javascript" src="${contextPath}/js/check/job-check.js"></script>
<title>残疾人招聘就业网</title>
</head>
<body>
	<jsp:include page="../header.jsp" />
	<jsp:include page="../nav.jsp" />

	<div class="w988 ptb10 center">
		<jsp:include page="left-nav.jsp" />

		<div class="Air">
			<form action="${contextPath }/secure/job/save" method="post" onSubmit="return job_check()">
				<table class="w770" border="0" cellspacing="0" cellpadding="0" align="center">
					<tbody>
						<tr valign="top">
							<td>
								<table border="0" cellspacing="0" cellpadding="0" class="table_boder" align="center">
									<tbody>
										<tr>
											<td class="uu1" align="left" colspan="2"><b> <span id="ctl00_ContentPlaceHolder1_Label1">职位管理&gt;&gt;发布新职位</span>
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
								<td align="right" class="line35"><span class="red line35_r">*</span> <b>职位名称 ：</b></td>
								<td align="left"><input name="name" type="text" id="name" class="input_border" style="width:200px;" /> <span id="ctl00_ContentPlaceHolder1_RequiredFieldValidator1"
									style="color:Red;font-style:italic;visibility:hidden;">职位名称</span></td>
							</tr>
							<tr>
								<td align="right" class="line35"><span class="red line35_r">*</span><b> 招聘人数 ：</b></td>
								<td align="left"><input name="hireNumber" type="text" id="hireNumber" class="input_border" style="width:200px;"> <span id="ctl00_ContentPlaceHolder1_RequiredFieldValidator3"
										style="display:inline-block;color:Red;border-width:1px;border-style:solid;font-style:italic;visibility:hidden;">年龄不能为空</span>
								</td>
							</tr>
							<tr>
								<td align="right" class="line35"><b> 提供薪资 ：</b></td>
								<td><select name="salary" id="salary" class="select_border">
										<c:forEach items="${params }" var="p">
											<c:if test="${p.type == 'salary' }">
												<option value="${p.value }">${p.name }</option>
											</c:if>
										</c:forEach>
								</select></td>
							</tr>
							<tr>
								<td align="right" class="line35"><span class="red line35_r">*</span><b> 最低学历 ：</b></td>
								<td><select name="education" id="education" class="select_border">
										<c:forEach items="${params }" var="p">
											<c:if test="${p.type == 'education' }">
												<option value="${p.value }">${p.name }</option>
											</c:if>
										</c:forEach>
								</select></td>
							</tr>
							<tr>
								<td align="right" class="line35"><span class="red line35_r">*</span><b> 工作经验 ：</b></td>
								<td><select name="experience" id="experience" class="select_border">
										<c:forEach items="${params }" var="p">
											<c:if test="${p.type == 'experience' }">
												<option value="${p.value }">${p.name }</option>
											</c:if>
										</c:forEach>
								</select></td>
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
								<td align="right" class="line35"><span class="red line35_r">*</span><b> 岗位性质 ：</b></td>
								<td align="left"><select name="nature" id="nature" class="select_border">
										<c:forEach items="${params }" var="p">
											<c:if test="${p.type == 'jobNature' }">
												<option value="${p.value }">${p.name }</option>
											</c:if>
										</c:forEach>
								</select></td>
							</tr>
							<tr>
								<td align="right" class="line35"><span class="red line35_r">*</span><b> 有效期 ：</b></td>
								<td align="left"><select name="effectiveDays" id="effectiveDays" class="select_border">
										<c:forEach items="${params }" var="p">
											<c:if test="${p.type == 'effectiveTime' }">
												<option value="${p.value }">${p.name }</option>
											</c:if>
										</c:forEach>
								</select></td>
							</tr>
							<tr>
								<td align="right" class="line35"><span class="red line35_r">*</span><b> 工作地点 ：</b></td>
								<td align="left"><select name="area_lv1" id="area_lv1" class="select_border" style="width:150px;">
										<c:forEach items="${provinceList }" var="area">
											<option value="${area.code }">${area.name }</option>
										</c:forEach>
								</select> <select name="area_lv2" id="area_lv2" class="select_border" style="width:150px;">
										<option value="">请选择城市</option>
								</select> <select name="workPlace" id="area_lv3" class="select_border" style="width:150px;">
										<option value="">请选择区域</option>
								</select>
									<p style="color:red;text-indent: 1em;">请精确到区县级</p>
									</td>
							</tr>
							<tr>
								<td align="right" class="line35"><span class="red line35_r">*</span><b> 工作种类 ：</b></td>
								<td align="left">
									<select name="jobCategory_lv1" id="jobCategory_lv1" class="select_border" style="width:150px;">
										<c:forEach items="${jcList }" var="p">
											<option value="${p.code }">${p.name }</option>
										</c:forEach>
									</select> 
									<select name="jobCategory_lv2" id="jobCategory_lv2" class="select_border" style="width:150px;">
										<option value="">请选择</option>
									</select> 
									<select name="jobCategory_lv3" id="jobCategory_lv3" class="select_border" style="width:150px;">
										<option value="">请选择</option>
									</select>
									<input type="hidden" name="jobCategory.code" id="jobCategoryCode" value="" />
									
								<!-- 	<p style="color:red;text-indent: 1em;">请选择具体的职位</p>	 -->
								</td>
							</tr>
							<tr>
								<td align="right" class="line35"><b> 其他福利 ：</b></td>
								<td align="left" style="padding: 0px 5px 0px 10px;"><c:forEach items="${params }" var="p">
										<c:if test="${p.type == 'benefit' }">
											<input type="checkbox" value="${p.value }" name="provideBenefit" />${p.name }&nbsp;&nbsp;
											</c:if>
									</c:forEach></td>
							</tr>
							<tr>
								<td align="right" class="line35"><b>职位描述 ：</b></td>
								<td align="left" class="ptb5"><textarea name="description" rows="2" cols="20" id="ctl00_ContentPlaceHolder1_TextBox9" class="input_border" style="height:150px;width:450px;"></textarea></td>
							</tr>
							<tr>
								<td colspan="2" align="center" class="line35" id="title55"><b>联系方式</b>
								</td>
							</tr>
							<tr>
								<td align="right" class="line35"><span class="red line35_r">*</span><b>联系人 ：</b>
								</td>
								<td align="left"><input name="contactPerson" value="${company.contactPerson }" type="text" id="contactPerson" class="input_border" style="width:200px;">
								</td>
							</tr>
							<tr>
								<td align="right" class="line35"><span class="red line35_r">*</span><b> 联系电话 ：</b>
								</td>
								<td align="left"><input name="contactTel" value="${company.telephone }" type="text" id="contactTel" class="input_border" style="width:200px;"> <span
										id="ctl00_ContentPlaceHolder1_RequiredFieldValidator2" style="display:inline-block;color:Red;border-width:1px;border-style:solid;font-style:italic;margin-bottom:0px;visibility:hidden;">手机不能为空</span>
								</td>
							</tr>
							<tr>
								<td align="right" class="line35"><b>邮箱 ：</b>
								</td>
								<td align="left"><input name="contactEmail" value="${company.email }" type="text" id="contactEmail" class="input_border" style="width:200px;">
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
			`
			<c:if test="${notice == 'failure' }">
				<script type="text/javascript">
		        		alert('保存失败!');
		        	</script>
			</c:if>


		</div>

		<div style="clear: both;"></div>
	</div>

	<jsp:include page="../footer.jsp" />


</body>
</html>