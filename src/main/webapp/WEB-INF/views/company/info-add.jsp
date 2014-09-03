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
<script type="text/javascript" src="${contextPath}/js/check/company-check.js"></script>
<title>残疾人招聘就业网</title>
</head>
<body>
	<jsp:include page="../header.jsp" />
	<jsp:include page="../nav.jsp" />

	<div class="w988 ptb10 center">
		<jsp:include page="left-nav.jsp" />

		<div class="Air">
			<table class="w770" border="0" cellspacing="0" cellpadding="0" align="center">
				<tbody>
					<tr valign="top">
						<td>
							<table border="0" cellspacing="0" cellpadding="0" class="table_boder" align="center">
								<tbody>
									<tr>
										<td class="uu1" align="left" colspan="2"><b> <span id="ctl00_ContentPlaceHolder1_Label1">控制面板&gt;&gt;编辑企业信息</span>
										</b></td>
									</tr>
								</tbody>
							</table></td>
					</tr>
				</tbody>
			</table>

			<form action="${contextPath }/secure/company/save" method="post" onSubmit="return company_check();">
				<div class="AntAccout_Center table_boder p10 mb10 center">
					<table width="100%" border="0" cellpadding="0" cellspacing="0" align="center">
						<tbody>
							<tr>
								<td align="right" class="line35" width="140"><span class="red line35_r">*</span><b> 企业名称 ： </b></td>
								<td align="left"><input name="name" type="text" id="name" class="input_border" style="width:200px;"> <span
										id="ctl00_ContentPlaceHolder1_RequiredFieldValidator1" style="display:inline-block;color:Red;border-width:1px;border-style:solid;font-style:italic;visibility:hidden;">企业名称不能为空</span> <br>
								</td>
							</tr>
							<tr>
								<td align="right" class="line35" width="120"><span class="red line35_r">*</span><b> 法人代表 ： </b></td>
								<td align="left"><input name="corporateRepresentative" type="text" id="corporateRepresentative" class="input_border" style="width:200px;"> <span
										id="ctl00_ContentPlaceHolder1_RequiredFieldValidator1" style="display:inline-block;color:Red;border-width:1px;border-style:solid;font-style:italic;visibility:hidden;">企业名称不能为空</span> <br>
								</td>
							</tr>
							<tr>
								<td align="right" class="line35" width="120"><b> 组织机构代码 ： </b></td>
								<td align="left"><input name="organizationCode" type="text" id="ctl00_ContentPlaceHolder1_TextBox1" class="input_border" style="width:200px;"> <span
										id="ctl00_ContentPlaceHolder1_RequiredFieldValidator1" style="display:inline-block;color:Red;border-width:1px;border-style:solid;font-style:italic;visibility:hidden;">企业名称不能为空</span> <br>
								</td>
							</tr>
							<tr>
								<td align="right" class="line35" width="120"><span class="red line35_r">*</span><b> 工商登记号码 ： </b></td>
								<td align="left"><input name="commercialCode" type="text" id="commercialCode" class="input_border" style="width:200px;"> <span
										id="ctl00_ContentPlaceHolder1_RequiredFieldValidator1" style="display:inline-block;color:Red;border-width:1px;border-style:solid;font-style:italic;visibility:hidden;">企业名称不能为空</span> <br>
								</td>
							</tr>
							<tr>
								<td align="right" class="line35" width="120"><b> 税务编码 ： </b></td>
								<td align="left"><input name="taxCode" type="text" id="ctl00_ContentPlaceHolder1_TextBox1" class="input_border" style="width:200px;"> <span
										id="ctl00_ContentPlaceHolder1_RequiredFieldValidator1" style="display:inline-block;color:Red;border-width:1px;border-style:solid;font-style:italic;visibility:hidden;">企业名称不能为空</span> <br>
								</td>
							</tr>
							<tr>
								<td align="right" class="line35" width="120"><b> 社保登记证号 ： </b></td>
								<td align="left"><input name="socialSecurityCode" type="text" id="ctl00_ContentPlaceHolder1_TextBox1" class="input_border" style="width:200px;"> <span
										id="ctl00_ContentPlaceHolder1_RequiredFieldValidator1" style="display:inline-block;color:Red;border-width:1px;border-style:solid;font-style:italic;visibility:hidden;">企业名称不能为空</span> <br>
								</td>
							</tr>
							<tr>
								<td align="right" class="line35" width="120"><b> 网站ID ： </b></td>
								<td align="left"><input name="webSiteId" type="text" id="ctl00_ContentPlaceHolder1_TextBox1" class="input_border" style="width:200px;"> <span
										id="ctl00_ContentPlaceHolder1_RequiredFieldValidator1" style="display:inline-block;color:Red;border-width:1px;border-style:solid;font-style:italic;visibility:hidden;">企业名称不能为空</span> <br>
								</td>
							</tr>
							<tr>
								<td align="right" class="line35" width="120"><b> 市劳网号 ： </b></td>
								<td align="left"><input name="laoWangCode" type="text" id="ctl00_ContentPlaceHolder1_TextBox1" class="input_border" style="width:200px;"> <span
										id="ctl00_ContentPlaceHolder1_RequiredFieldValidator1" style="display:inline-block;color:Red;border-width:1px;border-style:solid;font-style:italic;visibility:hidden;">企业名称不能为空</span> <br>
								</td>
							</tr>
							<tr>
								<td align="right" class="line35"><b>企业规模 ：</b></td>
								<td align="left"><select name="scale" id="ctl00_ContentPlaceHolder1_DropDownList2" class="select_border">
										<c:forEach items="${params }" var="p">
											<c:if test="${p.type == 'scale' }">
												<option value="${p.value }">${p.name }</option>
											</c:if>
										</c:forEach>
								</select></td>
							</tr>
							<tr>
								<td align="right" class="line35"><b>企业性质 ：</b></td>
								<td align="left"><select name="nature" id="ctl00_ContentPlaceHolder1_DropDownList2" class="select_border">
										<c:forEach items="${params }" var="p">
											<c:if test="${p.type == 'companyNature' }">
												<option value="${p.value }">${p.name }</option>
											</c:if>
										</c:forEach>
								</select></td>
							</tr>
							<tr>
								<td align="right" class="line35"><b>经济类型 ：</b></td>
								<td align="left"><select name="economyType" id="ctl00_ContentPlaceHolder1_DropDownList2" class="select_border">
										<c:forEach items="${params }" var="p">
											<c:if test="${p.type == 'economyType' }">
												<option value="${p.value }">${p.name }</option>
											</c:if>
										</c:forEach>

								</select></td>
							</tr>
							<tr>
								<td align="right" class="line35"><b>所属行业 ：</b></td>
								<td align="left"><select name="businessScope.id" id="bid" class="select_border">
										<c:forEach items="${bsList }" var="bs">
											<option value="${bs.id }">${bs.name }</option>
										</c:forEach>
								</select></td>
							</tr>
							<tr>
								<td align="right" class="line35"><b>企业简介 ：</b></td>
								<td align="left"><textarea name="introduction" rows="2" cols="20" id="ctl00_ContentPlaceHolder1_TextBox19" class="input_border"
										style="height:80px;width:450px;font-size: 12px; padding: 6px;"></textarea></td>
							</tr>

							<tr>
								<td <td="" colspan="2" class="line50">您企业的联系方式&nbsp;&nbsp;<span class="red">*号为必须填写项</span></td>
							</tr>
							<tr>
								<td <td="" colspan="2" height="20">&nbsp;</td>
							</tr>
							<tr>
								<td align="right" class="line35"><span class="red line35_r">*</span><b>联系电话 ：</b></td>
								<td align="left"><input name="telephone" type="text" id="telephone" class="input_border" style="width:200px;">
								</td>
							</tr>
							<tr>
								<td align="right" class="line35"><span class="red line35_r">*</span><b>联系人 ：</b></td>
								<td align="left"><input name="contactPerson" type="text" id="contactPerson" class="input_border" style="width:200px;">
								</td>
							</tr>
							<tr>
								<td align="right" class="line35"><b>联系部门 ：</b></td>
								<td align="left"><input name="contactDept" type="text" id="ctl00_ContentPlaceHolder1_TextBox10" class="input_border" style="width:200px;">
								</td>
							</tr>
							<tr>
								<td align="right" class="line35"><b>传真 ：</b></td>
								<td align="left"><input name="fax" type="text" id="ctl00_ContentPlaceHolder1_TextBox5" class="input_border" style="width:200px;">
								</td>
							</tr>
							<tr>
								<td align="right" class="line35"><b>邮箱 ：</b></td>
								<td align="left"><input name="email" type="text" id="ctl00_ContentPlaceHolder1_TextBox2" class="input_border" style="width:200px;">
								</td>
							</tr>
							<tr>
								<td align="right" class="line35"><b>公司地址 ：</b></td>
								<td align="left"><input name="address" type="text" id="ctl00_ContentPlaceHolder1_TextBox2" class="input_border" style="width:200px;">
								</td>
							</tr>
							<tr>
								<td align="left" colspan="2" class="line35" style="padding-left: 130px;"><input type="submit" name="ctl00$ContentPlaceHolder1$Button1" value="保 存" onclick=""
									id="ctl00_ContentPlaceHolder1_Button1" class="btn_input mt5 mr10" style="background: url(${contextPath}/images/edit_btn1.gif) no-repeat;">
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</form>

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