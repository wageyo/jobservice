<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!-- 修改密码页面 -->
<div class="Air">
	<table class="w770" border="0" cellspacing="0" cellpadding="0" align="center">
		<tbody>
			<tr valign="top">
				<td>
					<table border="0" cellspacing="0" cellpadding="0" class="table_boder" align="center">
						<tbody>
							<tr>
								<td class="uu1" align="left" colspan="2"><b> <span id="ctl00_ContentPlaceHolder1_Label1">修改密码</span> </b>
								</td>
							</tr>
						</tbody>
					</table>
				</td>
			</tr>
		</tbody>
	</table>
	<div class="AntAccout_Center table_boder p10 mb10 center">
		<form action="${contextPath }/secure/user/passWordEdit" method="post" onSubmit="return check();">
			<table border="0" cellpadding="0" cellspacing="0" width="100%" align="center">
				<tbody>
					<tr>
						<td colspan="2" align="center" class="line35"><b>修改登录密码</b>
						</td>
					</tr>
					<tr>
						<td align="right" class="line35"><b>用户名 ：</b>
						</td>
						<td align="left"><span id="Label2"> <input name="sid" type="hidden" value="0"> <span id="ctl00_ContentPlaceHolder1_Label3" class="user_name">${user.loginName }</span> <span
									class="red line25"></span>您的用户名 
						</span>
						</td>
					</tr>
					<tr>
						<td align="right" class="line35"><b>旧密码 ： </b>
						</td>
						<td align="left"><input name="oldPassWord" type="password" id="oldPassWord"  value=""class="input_border"><span id="ctl00_ContentPlaceHolder1_RequiredFieldValidator1" style="color:Red;">*请输入原密码</span>
						</td>
					</tr>
					<tr>
						<td align="right" class="line35"><b>新密码 ： </b>
						</td>
						<td align="left"><input name="newPassWord" type="password" id="newPassWord" class="input_border"><span id="ctl00_ContentPlaceHolder1_RequiredFieldValidator1" style="color:Red;">*您的新登陆口令(6~12个字符)</span>
						</td>
					</tr>
					<tr>
						<td align="right" class="line35"><b>确认密码 ： </b>
						</td>
						<td align="left"><input name="conPassWord" type="password" id="conPassWord" class="input_border"> <span id="ctl00_ContentPlaceHolder1_RequiredFieldValidator1" style="color:Red;">*确认密码</span>
						</td>
					</tr>
					<tr>
						<td align="left" colspan="2" class="line35" style="padding-left: 145px;">
							<div id="ValidationSummary1" style="display: none; color: red"></div> <input type="submit" name="ctl00$ContentPlaceHolder1$Button1" value="保 存" id="ctl00_ContentPlaceHolder1_Button1"
							class="btn_input mt5" style="background: url(${contextPath}/images/edit_btn1.gif) no-repeat;" /></td>
					</tr>
				</tbody>
			</table>
		</form>
		<script type="text/javascript">
			function check(){
				//旧密码
				var oldPassWord = $('#oldPassWord').val();
				if(oldPassWord == null || oldPassWord == '' ){
					alert('请先输入原密码!');
					$('#oldPassWord').focus();
					return false;
				}
				//新密码
				var reg_pwd = /[a-zA-Z0-9]{6,12}/;
				var newPassWord = $('#newPassWord').val();
				if(!reg_pwd.test(newPassWord)){
			        alert('密码格式不正确,请重新填写!');
					$('#newPassWord').focus();
					return false;
				}
				//两次密码是否一致
				var conPassWord = $('#conPassWord').val();
				if(conPassWord != newPassWord ){
					alert('两次输入的密码不一致!');
					$('#conPassWord').focus();
					return false;
				}
				return true;
			}
		</script>
	</div>
</div>