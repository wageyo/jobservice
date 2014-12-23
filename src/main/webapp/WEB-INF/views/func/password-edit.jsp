<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!-- 修改密码页面 -->
<form action="${contextPath }/secure/user/passWordEdit" method="post" onsubmit="return check();" id="passWordEdit">
	<ul class="block">
		<li>
			<div class="form0">
				<div style="width: 780px;" class="contraction" id="ModelOneID">
					<div style="display: block;" class="contractioncontent">
						<table class="tbl1" id="EnterpriseinformationForm">
							<tbody>
								<tr>
									<td class="textTop">
										您的用户名：
									</td>
									<td>
										<input id="name" name="name" type="text" value="${cookie.username.value }" type="text" disabled="disabled"/>
									</td>
								</tr>
								<tr>
									<td class="textTop"><em style="display: inline;">* </em>原密码：
									</td>
									<td>
										<input name="oldPassWord" type="password" id="oldPassWord"  value="" />
									</td>
								</tr>
								<tr>
									<td class="textTop"><em style="display: inline;">* </em>新密码：
									</td>
									<td>
										<input name="newPassWord" type="password" id="newPassWord" />请输入英文字母、数字或下划线，长度5-20个字符
									</td>
								</tr>
								<tr>
									<td class="textTop"><em style="display: inline;">* </em>确认密码：
									</td>
									<td>
										<input name="conPassWord" type="password" id="conPassWord"  />
									</td>
								</tr>
							</tbody>
						</table>
					</div>
					<input class="saveBtn" id="SaveAllBtn" type="button" onclick="pwdsubmit();"/>
				</div>
				<div class="clearboth"></div>
			</div></li>
	</ul>
</form>




<script type="text/javascript">
	function check() {
		//旧密码
		var oldPassWord = $('#oldPassWord').val();
		if (oldPassWord == null || oldPassWord == '') {
			alert('请先输入原密码!');
			$('#oldPassWord').focus();
			return false;
		}
		//新密码
		var reg_pwd = /[a-zA-Z0-9]{6,12}/;
		var newPassWord = $('#newPassWord').val();
		if (!reg_pwd.test(newPassWord)) {
			alert('密码格式不正确,请重新填写!');
			$('#newPassWord').focus();
			return false;
		}
		//两次密码是否一致
		var conPassWord = $('#conPassWord').val();
		if (conPassWord != newPassWord) {
			alert('两次输入的密码不一致!');
			$('#conPassWord').focus();
			return false;
		}
		return true;
	}
	
	//提交修改密码
	function pwdsubmit(){
		$('#passWordEdit').submit();
	}
</script>
