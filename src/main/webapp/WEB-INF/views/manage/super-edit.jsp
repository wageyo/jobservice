<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<br /><%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<script type="text/javascript" src="${contextPath}/js/common.js"></script>
<script type="text/javascript" src="${contextPath}/js/verify.js"></script>
<script type="text/javascript" src="${contextPath}/js/widget.js"></script>
<div class="manage">
	<div class="viwe-title">编辑管理员用户</div>
	<div id="" class="system" style="margin-left: 150px;margin-top: 30px">
		<input type="hidden" id="adminUserEditId" value="${id}" />
		<dl>
			<dt>管理员名称：</dt>
			<dd >
				<input id="userName"  type="input" />
			</dd>
		</dl>
		<dl>
			<dt>密码：</dt>
			<dd>
				<input id="passWord"  type=input  />
			</dd>
		</dl>
		<dl>
			<dt>确认密码：</dt>
			<dd >
				<input type="input" id="passWord1" />
			</dd>
		</dl>
		<dl>
			<dt>头部标题文本:</dt>
			<dd >
				<input type="input" id="title" style="width: 500px" name="audit_resume" />
			</dd>
		</dl>
		<dl>
			<dt>地区选择:</dt>
			<dd  style="width: 500px">
				<div id="F_left_0_edit" style="line-height:25px;">
					<select name="area_lv1" id="area_lv1" class="select_border" style="width:100px;">
						<c:forEach items="${provinceList }" var="area">
							<option value="${area.code }" <c:if test="${fn:substring(curCode,2,4) == fn:substring(area.code,2,4) }">selected="selected"</c:if>>${area.name }</option>
						</c:forEach>
					</select> 
					<select name="area_lv2" id="area_lv2" class="select_border" style="width:100px;">
						<option value="">请选择区域</option>
						<c:forEach items="${cityList }" var="area">
							<option value="${area.code }" <c:if test="${fn:substring(curCode,2,6) == fn:substring(area.code,2,6) }">selected="selected"</c:if>>${area.name }</option>
						</c:forEach>
					</select>
					 <select name="area.code" id="area_lv3" class="select_border" style="width:100px;">
						<option value="">请选择区域</option>
						<c:forEach items="${districtList }" var="area">
							<option value="${area.code }" <c:if test="${fn:substring(curCode,2,8) == fn:substring(area.code,2,8) }">selected="selected"</c:if>>${area.name }</option>
						</c:forEach>
					</select>
				</div>
			</dd>
		</dl>
		<br /><dl style="clear: both;">
			
			<dd align="center">
				<div style=" margin-top: 30px; width: 200px;">
					<a class="blocklink" style="margin-top: 20px" href="javascript:void(0)" onclick="adminUserEdit.submit();">确定</a> <a class="blocklink" style="margin-top: 20px" href="javascript:void(0)"
						onclick="adminUserEdit.close();">取消</a>
				</div>
			</dd>
		</dl>

	</div>
</div>
<script type="text/javascript">
	
	var adminUserEdit={};
	adminUserEdit.params={};
	adminUserEdit.code;//地区码
	adminUserEdit.close=function(){
		//关闭面板
		$("#adminUserPanel").window("close");
	};
	/*
		初始化编辑管理员信息
	*/
	adminUserEdit.init=function(){
				$.ajax({
					url:'/jobservice/manage/getadminUser',
					type:'post',
					data:{'id':$("#adminUserEditId").val()},
					success:function(data){
						//初始化编辑信息
								
						$("#loginName").html(data.loginName);//账户名
						$("#userName").val(data.nickName);//管理员名称
						$("#passWord").val(data.passWord);//密码
						$("#passWord1").val(data.passWord);//密码
						$("#title").val(data.title);//标题
					},error:function(){
						alert("初始化编辑管理员信息请求时发生错误.");
					}
				});
	};
	adminUserEdit.submit=function(){
			//判断地区码
			if($("#area_lv3").val()=="" || $("#area_lv3").val()==undefined){
				//判断2
				if($("#area_lv2").val()=="" || $("#area_lv2").val() ==undefined){
					//返回1
					adminUserEdit.code=$("#area_lv1").val();
				}else{
					//返回2
					adminUserEdit.code=$("#area_lv2").val();
				}
			}else{
				//返回3
				adminUserEdit.code=$("#area_lv3").val();
			}
			adminUserEdit.params.id=$("#adminUserEditId").val();//数据id
			adminUserEdit.params.nickName=$("#userName").val();//管理员名称
			adminUserEdit.params.passWord=$("#passWord").val();//密码
			adminUserEdit.params.identity="admin";//账号类型
			adminUserEdit.params.title=$("#title").val();//标题
			adminUserEdit.params.authority="600";//权限值  普通用户
			adminUserEdit.params.phone=adminUserEdit.code;//地区码
			//校验
			
			if(adminUserEdit.params.nickName==undefined || adminUserEdit.params.nickName==""){
				alert("管理员名称为空");
				return false;
			}
			if(adminUserEdit.params.passWord==undefined || adminUserEdit.params.passWord==""){
					alert("密码为空");
					return false;
			}
			if(adminUserEdit.params.passWord!=$("#passWord1").val()){
				alert("两次密码输入不一致");
				return false;
			}else{
					if(adminUserEdit.params.passWord.length<5){
							alert("密码长度不符");
							return false;
					}
			
			}
			if(adminUserEdit.params.title==undefined || adminUserEdit.params.title==""){
				alert("头部标题文本为空");
					return false;
			}
			if($("#area_lv1").val()=="10000000"){
					alert("请选择地区");
					return false;
			}
			
			$.ajax({
						url:'/jobservice/manage/adminUser_edit',
						type:'post',
						data:adminUserEdit.params,
						success:function(data){
						if(data==true){
							alert("用户编辑成功");
							//关闭面板
							$("#adminUserPanel").window("close");
							//刷新列表
							$("#adminUser_grid").datagrid("load");
						}else{
							alert("用户编辑失败");
						}
						},error:function(){
							alert("编辑管理员用户时发生错误");
						}
				});
	};
	$(function(){
		adminUserEdit.init();
	});

</script>

