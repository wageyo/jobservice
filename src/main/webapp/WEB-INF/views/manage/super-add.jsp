<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<br /><%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<script type="text/javascript" src="${contextPath}/js/common.js"></script>
<script type="text/javascript" src="${contextPath}/js/verify.js"></script>
<script type="text/javascript" src="${contextPath}/js/widget.js"></script>
<script type="text/javascript" src="${contextPath}/js/check/reg-check.js"></script>
<div class="manage">
	<div class="viwe-title">增加管理员用户</div>
	<div id="" class="system" style="margin-left: 150px;margin-top: 30px">
		<dl>
			<dt>登陆账号名：</dt>
			<dd id="">
				<input type="input"  id="loginName"/>
			</dd>
		</dl>
		<dl>
			<dt>管理员名称：</dt>
			<dd id="">
				<input type="input"  id="userName"/>
			</dd>
		</dl>
		<dl>
			<dt>密码：</dt>
			<dd id="">
				<input type="input"  id="passWord" />
			</dd>
		</dl>
		<dl>
			<dt>确认密码：</dt>
			<dd id="">
				<input type="input"  id="passWord1"   />
			</dd>
		</dl>

		<dl>
			<dt>头部标题文本:</dt>
			<dd id="">
				<input type=""input"" id="title" style="width: 500px" name="audit_resume" />
			</dd>
		</dl>
		<dl>
			<dt>地区选择:</dt>
			<dd id="" style="width: 500px">
				<div id="F_left_0" style="line-height:25px;">
					<select name="area_lv1" id="area_lv1" class="select_border" style="width:100px;">
						<c:forEach items="${provinceList }" var="area">
							<option value="${area.code }">${area.name }</option>
						</c:forEach>
					</select> 
					<select name="area_lv2" id="area_lv2" class="select_border" style="width:100px;">
						<option value="">请选择区域</option>
					</select>
					 <select name="area.code" id="area_lv3" class="select_border" style="width:100px;">
						<option value="">请选择区域</option>
					</select>
				</div>
			</dd>
		</dl>
		<dl style="clear: both;">
			<dt></dt>
			<dd align="center">
				<div style=" margin-top: 30px; width: 200px;">
					<a class="blocklink" style="margin-top: 20px" href="javascript:void(0)" onclick="superManager.check();">确定</a>
					<a class="blocklink" style="margin-top: 20px" href="javascript:void(0)" onclick="superManager.close();">取消</a>
				</div>
			</dd>
		</dl>

	</div>
</div>




<script type="text/javascript">
	
	var superManager={};
	superManager.close=function(){
				//关闭面板
				$("#adminUserPanel").window("close");
	};
	superManager.check=function(){
			
			var params = {};
			var code;
			//判断地区码
			if($("#area_lv3").val()=="" || $("#area_lv3").val()==undefined){
				//判断2
				if($("#area_lv2").val()=="" || $("#area_lv2").val() ==undefined){
					//返回1
					code=$("#area_lv1").val();
				}else{
					//返回2
					code=$("#area_lv2").val();
				}
			}else{
				//返回3
				code=$("#area_lv3").val();
			}
			
			params.loginName=$("#loginName").val();//用户名
			params.nickName=$("#userName").val();//管理员名称
			params.passWord=$("#passWord").val();//密码
			params.identity="admin";//账号类型
			params.title=$("#title").val();//标题
			params.authority="600";//权限值  普通用户
			params.phone=code;//地区码
			//校验
			
			if(params.loginName==undefined || params.loginName==""){
				alert("登陆账号名为空");
				return false;
			}else{
				if( !($("#loginName").val().length>4 && $("#loginName").val().length<21 )){
					
					alert("登陆账号长度不符");
					return false;
				}
			}
			if(params.nickName==undefined || params.nickName==""){
				alert("管理员名称为空");
				return false;
			}
			if(params.passWord==undefined || params.passWord==""){
					alert("密码为空");
					return false;
			}
			if(params.passWord!=$("#passWord1").val()){
				alert("两次密码输入不一致");
				return false;
			}else{
					if(params.passWord.length<6){
							alert("密码长度不符");
							return false;
					}
			
			}
			if(params.title==undefined || params.title==""){
				alert("头部标题文本为空");
					return false;
			}
			if($("#area_lv1").val()=="10000000"){
					alert("请选择地区");
					return false;
			}
			
			//用户名重复性校验
			$.ajax({
						url:server.url+'user/check/'+params.loginName,
						dataType:'json',
						type:'get',
						async:false,
						success:function(data){
							if(data.notice=="failure"){
								alert("该用户已存在");
								return false;
							}
							if(data.notice=="success"){
								
								superManager.submit(params);
							}
						},error:function(){
							alert("增加管理员用户校验时发生错误");
						}
				});
			
			
			
	};
	
	superManager.submit=function(params){
		$.ajax({
						url:'/jobservice/manage/adminUser_add',
						type:'post',
						data:params,
						success:function(data){
						if(data==true){
							alert("用户增加成功");
							$("#loginName").val("");//用户名
							$("#userName").val("");//管理员名称
							$("#passWord").val("");//密码
							$("#passWord1").val("");//密码
							$("#title").val("");//标题.
							//关闭面板
							$("#adminUserPanel").window("close");
							//刷新列表
							$("#adminUser_grid").datagrid("load");
							
						}else{
							alert("用户增加失败");
						}
						},error:function(){
							alert("发生错误");
						}
				});
	};

</script>

