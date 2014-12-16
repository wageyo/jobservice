<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>残疾人就业信息网</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="keywords" content="残疾人招聘信息,残疾人就业信息,残疾人人才网,残疾人找工作" />
	<meta content="残疾人招聘就业" name="description" />
	
	<link href="${contextPath}/css/public.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/register.css" rel="stylesheet" type="text/css" />
	
	<script type="text/javascript" src="${contextPath}/js/jquery.js"></script>
	<script type="text/javascript">
	</script>
</head>
<body >
	<div id="container" class="container">
	
		<!-- 顶部工具条栏 -->
		<jsp:include page="formatter/status-bar.jsp" />
		
		<!-- 头部导航及图片栏目 -->
		<jsp:include page="formatter/header.jsp" />
		<div class="clearboth"></div>
		
		<!-- ******* 中部内容显示区 ******* start ********** -->
		<div id="content" class="content">

	        <div class="clearboth"></div>
	        <div class="RegisterMain" id="RegisterOne">
	            <div class="RegisterBarStateOne"></div>
	            <div class="BusinessRegisterText"> <div class="text">如果有疑问，可以致电我们的服务热线。<a href="LinkCellPhone.aspx" target="_blank" style="color:orange;">详请请点击</a><!--<span>（020）83325750</span>--></div></div>
	            <table class="RegisterTable" cellpadding="0" cellspacing="0">
	                <tbody><tr>
	                    <td colspan="3"><div> 注：带“<span class="red">*</span>”为必填项</div>
	                    <div class="borderStyle"></div>
	                    </td>
	                </tr>
	                <tr>
	                    <th colspan="3">账号资料</th>
	                </tr>
	                <tr>
	                    <td class="textAlignRight FontSize"><span class="red">*</span>用户名：</td>
	                    <td class="printText">
	                        <div class="print"><input name="EnterpriseUserName" id="EnterpriseUserName" class="TextInput" type="text"></div>
	                    </td>
	                    <td class="textAlignLeft">
	                    <div id="ShowUserNameImg" class="SetFloatLeft"></div>
	                    <div id="ShowUserNameMsg" class="SetFloatLeft">可由4-20位字母、数字或下划线组成,首字母不能是数字</div>
	                    </td>
	                </tr>
	                <tr>
	                    <td class="textAlignRight FontSize"><span class="red">*</span>营业执照：</td>
	                    <td class="printText">
	                        <div class="print"><input name="txtBusinessLicenseCode" id="txtBusinessLicenseCode" class="TextInput" type="text"></div>
	                    </td>
	                    <td class="textAlignLeft">
	                    <div id="ShowOrganizationCodeImg" class="SetFloatLeft"></div>
	                    <div id="ShowOrganizationCodeMsg" class="SetFloatLeft">可由字母、数字组成8-20个字符。请认真填写否则单位审核不通过</div>
	                    </td>
	                </tr>
	                <tr class="bgColor">
	                    <td class="textAlignRight FontSize"><span class="red">*</span>密码：</td>
	                    <td class="printText">
	                        <div class="print"><input name="EnterpriseUserPassword" id="EnterpriseUserPassword" class="TextInput" type="password"></div>
	                    </td>
	                    <td class="textAlignLeft">
	                    <div id="ShowPasswordImg" class="SetFloatLeft"></div>
	                    <div id="ShowPasswordMsg" class="SetFloatLeft">请输入英文字母、数字或下划线，长度6-20个字符,首字母不能为数字</div>
	                    </td>
	                </tr>
	                <tr>
	                    <td class="textAlignRight FontSize"><span class="red">*</span>确认密码：</td>
	                    <td class="printText">
	                        <div class="print"><input name="EnterpriseUserPasswordAgain" id="EnterpriseUserPasswordAgain" class="TextInput" type="password"></div>
	                    </td>
	                    <td class="textAlignLeft">
	                    <div id="ShowPasswordAgainImg" class="SetFloatLeft"></div>
	                    <div id="ShowPasswordAgainMsg" class="SetFloatLeft">请再次输入密码</div>
	                    </td>
	                </tr>
	                <tr class="bgColor">
	                    <td style="width:100px" class="textAlignRight FontSize"><span class="red"></span>Email：</td>
	                    <td style=" width:280px" class="printText">
	                        <div class="print"><input name="EnterpriseEmail" id="EnterpriseEmail" class="TextInput" maxlength="60" type="text"></div>
	                    </td>
	                    <td class="textAlignLeft">
	                    <div id="ShowEmailImg" class="SetFloatLeft"></div>
	                    <div id="ShowEmailMsg" class="SetFloatLeft">请填写常用且有效的E-mail地址</div>
	                    </td>
	                </tr>
	
	            </tbody></table>
	          <div class="RegisterButtom" id="RegisterBtnOne"></div>
	        </div>
	        <div class="clearboth"></div>
	        <div class="RegisterMain" id="RegisterTwo" style="display:none;">
	                <div class="RegisterBarStateTwo">
	                </div>
	                <div class="BusinessRegisterText">
	                    <div class="text">
	                        如果有疑问，可以致电我们的服务热线：<span>（020）83325750</span></div>
	                </div>
	                <table class="RegisterTable" cellpadding="0" cellspacing="0">
	                <tbody><tr>
	                    <td colspan="3"><div> 注：带“<span class="red">*</span>”为必填项</div>
	                    <div class="borderStyle"></div>
	                    </td>
	                </tr>
	                <tr>
	                    <th colspan="3">用人单位信息</th>
	                </tr>
	                    <tr class="bgColor">
	                        <td style="width: 70px;"><span class="red">*</span>单位名称：</td>
	                        <td colspan="2">
	                            <div id="EnterpriseNameShow" class="print SetFloatLeft"><input name="CompanyName" id="CompanyName" class="TextInput" maxlength="60" type="text"></div>
	                            <div id="CompanyNameImg" class="SetFloatLeft" style="margin-left:20px;"></div>
	                             <div id="CompanyNameMsg" class="SetFloatLeft">单位名称一经注册，不可更改！请务必保证与营业执照注册名保持一致（如需更改请联系后台管理员）</div>
	                        </td>
	                    </tr>
	                    <tr>
	                        <td>
	                            <span class="red">*</span>所属行业：
	                        </td>
	                        <td>
	                            <div class="PublicButtomBar SetFloatLeft" style="margin-top:10px;">
	                            <div class="left"></div>
	                                <input id="industryBtn" class="btn" value="选择/修改" style="width:232px;" type="button">
	                            <div class="right"></div>
	                            </div>
	
	                            <div id="industryBtnImg" class="SetFloatLeft" style="margin-left:20px;"></div>
	                             <div id="industryBtnMsg" class="SetFloatLeft"></div>
	                        </td>
	                        <td>
	                        </td>
	                        
	                    </tr>
	                    <tr class="bgColor">
	                        <td>
	                            <span class="red">*</span>单位性质：
	                        </td>
	                        <td>
	                            
	                            <select name="CompanyQuality" id="CompanyQuality" class="CompanyScale SetFloatLeft" style="margin-top:10px;">
		<option selected="selected" value="Defalt">--请选择--</option>
		<option value="SDID_112">国营</option>
		<option value="SDID_113">民营公司</option>
		<option value="SDID_114">外企代表处</option>
		<option value="SDID_115">政府机关</option>
		<option value="SDID_116">事业单位</option>
		<option value="SDID_117">非营利机构</option>
		<option value="SDID_118">外资（欧美）</option>
		<option value="SDID_119">外资（非欧美）</option>
		<option value="SDID_120">合资（欧美）</option>
		<option value="SDID_121">合资（非欧美）</option>
		<option value="SDID_122">有限责任公司</option>
		<option value="SDID_286">股份有限责任公司</option>
		<option value="SDID_287">其他性质</option>
	
	</select>
	                            <div id="CompanyQualityImg" class="SetFloatLeft" style="margin-left:20px;"></div>
	                             <div id="CompanyQualityMsg" class="SetFloatLeft"></div>
	                        </td>
	                        <td>
	                        </td>
	                        
	                    </tr>
	                    <tr>
	                        <td>
	                            <span class="red">*</span>单位规模：
	                        </td>
	                        <td>
	                            <select name="CompanySize" id="CompanySize" class="CompanyScale SetFloatLeft" style="margin-top:10px;">
		<option selected="selected" value="Defalt">--请选择--</option>
		<option value="SDID_126">少于50人</option>
		<option value="SDID_127">50-150人</option>
		<option value="SDID_128">150-500人</option>
		<option value="SDID_129">500人以上</option>
	
	</select>
	                            <div id="CompanySizeImg" class="SetFloatLeft" style="margin-left:20px;"></div>
	                            <div id="CompanySizeMsg" class="SetFloatLeft"></div>
	                        </td>
	                        <td>
	                        </td>
	                        
	                    </tr>
	                    <tr class="bgColor">
	                        <td>
	                            <span class="red">*</span>所在地区：
	                        </td>
	                        <td>
	                            <div id="UpdatePanel1">
		
	                            <select name="ProvinceList" onchange="javascript:setTimeout('__doPostBack(\'ProvinceList\',\'\')', 0)" id="ProvinceList" class="Select SetFloatLeft" style="margin-top:10px;">
			<option selected="selected" value="Defalt">--请选择--</option>
			<option value="440000">广东省</option>
	
		</select>
	                            <select name="CityList" onchange="javascript:setTimeout('__doPostBack(\'CityList\',\'\')', 0)" id="CityList" class="Select SetFloatLeft" style="margin-top:10px;">
			<option selected="selected" value="Defalt">--请选择--</option>
	
		</select>
	                            <select name="DistrictList" id="DistrictList" class="Select SetFloatLeft" style="margin-top:10px;">
			<option selected="selected" value="Defalt">--请选择--</option>
	
		</select>
	                            
	</div>
	
	                             <div id="ProvinceListImg" class="SetFloatLeft" style="margin-left:20px;"></div>
	                            <div id="ProvinceListMsg" class="SetFloatLeft"></div>
	                        </td>
	                        <td>
	                        </td>
	                        
	                    </tr>
	                    <tr>
	                        <td>
	                            <span class="red">*</span>单位介绍：
	                        </td>
	                        <td colspan="2">
	                            <textarea aria-hidden="true" style="display: none;" name="EnterpriseIntroduction" id="EnterpriseIntroduction" class="textarea"></textarea><span class="mceEditor defaultSkin" id="EnterpriseIntroduction_parent" aria-labelledby="EnterpriseIntroduction_voice" role="application"><span id="EnterpriseIntroduction_voice" style="display:none;" class="mceVoiceLabel">{#aria.rich_text_area}</span><table style="width: 580px; height: 250px;" class="mceLayout" id="EnterpriseIntroduction_tbl" role="presentation" cellpadding="0" cellspacing="0"><tbody><tr class="mceFirst" role="presentation"><td role="presentation" class="mceToolbar mceLeft mceFirst mceLast"><div tabindex="-1" id="EnterpriseIntroduction_toolbargroup" role="group" aria-labelledby="EnterpriseIntroduction_toolbargroup_voice"><span role="application"><span id="EnterpriseIntroduction_toolbargroup_voice" class="mceVoiceLabel" style="display:none;">{#advanced.toolbar}</span><table aria-pressed="false" aria-disabled="false" id="EnterpriseIntroduction_toolbar1" class="mceToolbar mceToolbarRow1 Enabled" role="presentation" tabindex="-1" align="" cellpadding="0" cellspacing="0"><tbody><tr><td class="mceToolbarStart mceToolbarStartButton mceFirst"><span><!-- IE --></span></td><td style="position: relative"><a aria-pressed="false" tabindex="-1" role="button" id="EnterpriseIntroduction_bold" href="javascript:;" class="mceButton mceButtonEnabled mce_bold" onmousedown="return false;" onclick="return false;" aria-labelledby="EnterpriseIntroduction_bold_voice" title="粗体 (Ctrl+B)"><span class="mceIcon mce_bold"></span><span class="mceVoiceLabel mceIconOnly" style="display: none;" id="EnterpriseIntroduction_bold_voice">粗体 (Ctrl+B)</span></a></td><td style="position: relative"><a aria-pressed="false" tabindex="-1" role="button" id="EnterpriseIntroduction_italic" href="javascript:;" class="mceButton mceButtonEnabled mce_italic" onmousedown="return false;" onclick="return false;" aria-labelledby="EnterpriseIntroduction_italic_voice" title="斜体 (Ctrl+I)"><span class="mceIcon mce_italic"></span><span class="mceVoiceLabel mceIconOnly" style="display: none;" id="EnterpriseIntroduction_italic_voice">斜体 (Ctrl+I)</span></a></td><td style="position: relative"><a aria-pressed="false" tabindex="-1" role="button" id="EnterpriseIntroduction_underline" href="javascript:;" class="mceButton mceButtonEnabled mce_underline" onmousedown="return false;" onclick="return false;" aria-labelledby="EnterpriseIntroduction_underline_voice" title="底线 (Ctrl+U)"><span class="mceIcon mce_underline"></span><span class="mceVoiceLabel mceIconOnly" style="display: none;" id="EnterpriseIntroduction_underline_voice">底线 (Ctrl+U)</span></a></td><td style="position: relative"><span class="mceSeparator" role="separator" aria-orientation="vertical" tabindex="-1"></span></td><td style="position: relative"><a aria-pressed="false" tabindex="-1" role="button" id="EnterpriseIntroduction_strikethrough" href="javascript:;" class="mceButton mceButtonEnabled mce_strikethrough" onmousedown="return false;" onclick="return false;" aria-labelledby="EnterpriseIntroduction_strikethrough_voice" title="刪除线"><span class="mceIcon mce_strikethrough"></span><span class="mceVoiceLabel mceIconOnly" style="display: none;" id="EnterpriseIntroduction_strikethrough_voice">刪除线</span></a></td><td style="position: relative"><span class="mceSeparator" role="separator" aria-orientation="vertical" tabindex="-1"></span></td><td style="position: relative"><a aria-pressed="false" tabindex="-1" role="button" id="EnterpriseIntroduction_justifyleft" href="javascript:;" class="mceButton mceButtonEnabled mce_justifyleft" onmousedown="return false;" onclick="return false;" aria-labelledby="EnterpriseIntroduction_justifyleft_voice" title="左对齐"><span class="mceIcon mce_justifyleft"></span><span class="mceVoiceLabel mceIconOnly" style="display: none;" id="EnterpriseIntroduction_justifyleft_voice">左对齐</span></a></td><td style="position: relative"><a aria-pressed="false" tabindex="-1" role="button" id="EnterpriseIntroduction_justifycenter" href="javascript:;" class="mceButton mceButtonEnabled mce_justifycenter" onmousedown="return false;" onclick="return false;" aria-labelledby="EnterpriseIntroduction_justifycenter_voice" title="居中"><span class="mceIcon mce_justifycenter"></span><span class="mceVoiceLabel mceIconOnly" style="display: none;" id="EnterpriseIntroduction_justifycenter_voice">居中</span></a></td><td style="position: relative"><a aria-pressed="false" tabindex="-1" role="button" id="EnterpriseIntroduction_justifyright" href="javascript:;" class="mceButton mceButtonEnabled mce_justifyright" onmousedown="return false;" onclick="return false;" aria-labelledby="EnterpriseIntroduction_justifyright_voice" title="右对齐"><span class="mceIcon mce_justifyright"></span><span class="mceVoiceLabel mceIconOnly" style="display: none;" id="EnterpriseIntroduction_justifyright_voice">右对齐</span></a></td><td style="position: relative"><a aria-pressed="false" tabindex="-1" role="button" id="EnterpriseIntroduction_justifyfull" href="javascript:;" class="mceButton mceButtonEnabled mce_justifyfull" onmousedown="return false;" onclick="return false;" aria-labelledby="EnterpriseIntroduction_justifyfull_voice" title="左右对齐"><span class="mceIcon mce_justifyfull"></span><span class="mceVoiceLabel mceIconOnly" style="display: none;" id="EnterpriseIntroduction_justifyfull_voice">左右对齐</span></a></td><td style="position: relative"><a aria-disabled="true" tabindex="-1" role="button" id="EnterpriseIntroduction_undo" href="javascript:;" class="mceButton mce_undo mceButtonDisabled" onmousedown="return false;" onclick="return false;" aria-labelledby="EnterpriseIntroduction_undo_voice" title="恢复 (Ctrl+Z)"><span class="mceIcon mce_undo"></span><span class="mceVoiceLabel mceIconOnly" style="display: none;" id="EnterpriseIntroduction_undo_voice">恢复 (Ctrl+Z)</span></a></td><td style="position: relative"><a aria-disabled="true" tabindex="-1" role="button" id="EnterpriseIntroduction_redo" href="javascript:;" class="mceButton mce_redo mceButtonDisabled" onmousedown="return false;" onclick="return false;" aria-labelledby="EnterpriseIntroduction_redo_voice" title="重做 (Ctrl+Y)"><span class="mceIcon mce_redo"></span><span class="mceVoiceLabel mceIconOnly" style="display: none;" id="EnterpriseIntroduction_redo_voice">重做 (Ctrl+Y)</span></a></td><td class="mceToolbarEnd mceToolbarEndButton mceLast"><span><!-- IE --></span></td></tr></tbody></table></span></div><a href="#" accesskey="z" title="定位到工具列：Alt+Q，定位到编辑区：Alt+Z定位到工具列- Alt+Q，定位到元素路径：Alt+X。" onfocus="tinyMCE.getInstanceById('EnterpriseIntroduction').focus();"><!-- IE --></a></td></tr><tr class="mceLast"><td class="mceIframeContainer mceFirst mceLast"><iframe style="width: 100%; height: 227px;" title="{#aria.rich_text_area}{#advanced.help_shortcut}" src="javascript:&quot;&quot;" id="EnterpriseIntroduction_ifr" frameborder="0"></iframe></td></tr></tbody></table></span>
	                            请填写正确详细的介绍，确保通过审核(2000字以内)
	                        </td>
	                        
	                    </tr>
	                    <tr>
	                        <td>
	                            <span class="red">*</span>联系电话：
	                        </td>
	                        <td>
	                            <div class="print" style="float:left"><input name="Phone" id="Phone" class="TextInput" type="text"></div>
	                            
	                            <div id="PhoneImg" class="SetFloatLeft" style="margin-left:40px;*margin-left:30px;"></div>
	                           <div id="PhoneMsg" class="SetFloatLeft" style=""></div>
	                        </td>
	                        <td class="textAlignLeft">
	                            
	                        </td>
	                        
	                    </tr>
	                    <tr class="bgColor">
	                        <td>
	                            <span class="red">*</span>联&nbsp;&nbsp;系&nbsp;人：
	                        </td>
	                        <td>
	                           <div class="print SetFloatLeft" style="float:left"><input name="LinkmanName" id="LinkmanName" class="TextInput" type="text"></div>
	                           <div style="float:left; margin-left:20px;"> 
	                               <table id="LinkmanCalled" border="0">
		<tbody><tr>
			<td><input id="LinkmanCalled_0" name="LinkmanCalled" value="1" checked="checked" type="radio"><label for="LinkmanCalled_0">先生</label></td><td><input id="LinkmanCalled_1" name="LinkmanCalled" value="0" type="radio"><label for="LinkmanCalled_1">女士</label></td>
		</tr>
	</tbody></table>
	                           </div>
	                           <div id="LinkmanNameImg" class="SetFloatLeft" style="margin-left:40px;*margin-left:30px;"></div>
	                           <div id="LinkmanNameMsg" class="SetFloatLeft" style=""></div>
	                        </td>
	                        <td class="textAlignLeft">             
	                        </td>
	                    </tr>
	                    <tr>
	                        <td>
	                            传真号码：
	                        </td>
	                        <td>
	                            <div class="print SetFloatLeft"><input name="Fax" id="Fax" class="TextInput" type="text"></div>
	                            <div id="FaxImg" class="SetFloatLeft" style="margin-left:40px;*margin-left:30px;"></div>
	                           <div id="FaxMsg" class="SetFloatLeft" style=""></div>
	                        </td>
	                        <td>
	                        </td>
	                        
	                    </tr>
	                    <tr class="bgColor">
	                        <td>
	                            单位网站：
	                        </td>
	                        <td>
	                            <div class="print SetFloatLeft"><input name="EnterpriseWebsite" id="EnterpriseWebsite" class="TextInput" type="text"></div>
	                             <div id="EnterpriseWebsiteImg" class="SetFloatLeft" style="margin-left:40px;*margin-left:30px;"></div>
	                           <div id="EnterpriseWebsiteMsg" class="SetFloatLeft" style=""></div>
	                        </td>
	                        <td>
	                        </td>
	                        
	                    </tr>
	                    <tr>
	                        <td>
	                            邮政编码：
	                        </td>
	                        <td>
	                            <div class="print SetFloatLeft"><input name="Postalcode" id="Postalcode" class="TextInput" type="text"></div>
	                            <div id="PostalcodeImg" class="SetFloatLeft" style="margin-left:40px;*margin-left:30px;"></div>
	                           <div id="PostalcodeMsg" class="SetFloatLeft" style=""></div>
	                        </td>
	                        <td>
	                        </td>
	                        
	                    </tr>
	                    <tr class="bgColor">
	                        <td>
	                            <span class="red">*</span>通讯地址：
	                        </td>
	                        <td>
	                            <div class="print SetFloatLeft"><input name="ContactAddress" id="ContactAddress" class="TextInput" type="text"></div> 
	                            <div id="ContactAddressImg" class="SetFloatLeft" style="margin-left:40px;*margin-left:30px;"></div>
	                            <div id="ContactAddressMsg" class="SetFloatLeft" style=""></div>
	                        </td>
	                        <td>
	                        </td>
	                        
	                    </tr>
	                    <tr class="bgColor">
	                        <td>
	                            <span class="red">*</span>验证码：</td>
	                        <td class="Verification">
	                        <div class="print2"><input name="Verification" id="Verification" class="VerificationInput" type="text"></div>
	                           <div class="ChangeAnother" id="ChangeAnother"><div style=" margin-left:10px; margin-right:10px"><img alt="" src="ValidateCode.aspx" style=" margin-top:20px;* margin-top:0px;"> </div>换一张</div>
	                           <div class="ChangeAnother SetFloatLeft" style="margin-left:34px;"> <input name="AgreeService" id="AgreeService" class="VerticalAlign" type="checkbox">我已阅读<a target="_blank" href="PersonalUserAgreement.htm">服务声明</a></div>
	                            
	                            <span id="Label1"></span>
	                            <div id="VerificationMsg" class="SetFloatLeft" style=" color:Red; margin-left:10px; *margin-top:2px;"></div>
	
	                           </td>
	                        <td>
	                            &nbsp;</td>
	                        
	                    </tr>
	                </tbody></table>
	                <div>
	                   <div class="RegisterSaveBtn" id="RegisterSaveBtn"></div>
	                </div>
	            </div>

		</div>
		<!-- ******* 中部内容显示区 ******* end ********** -->
		
		<!-- 尾部footer区 -->
		<div class="clearboth"></div>
		<jsp:include page="formatter/footer.jsp" />
	</div>

<script type="text/javascript">
		
	</script>
</body>
</html>