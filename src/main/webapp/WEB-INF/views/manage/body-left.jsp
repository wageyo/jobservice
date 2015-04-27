<%@ page language="java" import="java.util.*" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="requestUrl" value="${pageContext.request.requestURL}" />

<link type="text/css" href="${contextPath }/js/lib/jquery-ui-bootstrap/jquery-ui-1.10.0.custom.css" rel="stylesheet" />
            
<style type="text/css">
</style>
<script type="text/javascript">
	$(document).ready(function(){
		
		//显示管理员昵称, 地区
		decodeTitle();
		
		//获得菜单
		getMenu();
	});
	
	//解码adminnickname, adminareaname
	function decodeTitle(){
		var adminNickName = decodeURIComponent('${cookie.adminnickname.value}');
		$('#adminNickName').html(adminNickName);
		var adminAreaName = decodeURIComponent('${cookie.adminareaname.value}');
		$('#adminAreaName').html(adminAreaName);
	}
	
	//获得菜单
	function getMenu(){
		var authority = '${cookie.adminauthority.value}';
		$.ajax({
			url:'${contextPath}/manage/getMenu/' + authority,
			type:'POST',
			dataType:'json',
			success:function(data){
				var content = "";
				// 当前路径
				var requestUrl = '${requestUrl}';
				var tt = requestUrl.substring(requestUrl.indexOf('ge/'),(requestUrl.indexOf('ge/')+6));
				var expandIndex = 999;	// 默认展开的索引
				$.each(data.menuList,function(i,menu){
					content += '<div>';
						content += '<h3><a href="#">' + menu.text +'</a></h3>';
						content += '<div>';
							content += '<ul>';
							$.each(menu.children,function(i,subMenu){
								//如果是当前页面则添加被选中样式
								var ttt = subMenu.url.substring(subMenu.url.indexOf('ge/'),(subMenu.url.indexOf('ge/')+6));
								tt = tt.replace( /^\s+|\s+$/g, "" );
								ttt = ttt.replace( /^\s+|\s+$/g, "" );
								if(tt == ttt){
									content += '<li class="activedmenu"><a style="color:white;"';
									expandIndex = subMenu.id.substring(3,4) - 1;
								}else{
									content += '<li><a';
								}
								content += ' href="' + subMenu.url + '">' + subMenu.text + '</a></li>';
							});
							content += '</ul>';
						content += '</div>';
					content += '</div>';
					
				});
				//构建菜单
				$('#menu-collapse').html(content).accordion({
			        header: "h3",
			        	active:expandIndex
			    });
				//所有二级菜单隐藏
			//	$('#menu-collapse').children().children().filter('.ui-accordion-content').hide();
				//被选中菜单所在的二级菜单 块展开
			//	$('.activedmenu').parent().parent().show();
			},
			error:function(){
				alert('获取菜单失败, 请重新登陆或者刷新页面后重试.');
			}
		});
		
	}
</script>
<div class="manage-body-left" style="">	<!-- background:url('${contextPath}/images/backdoor/ul_bg.jpg') repeat-y; -->
	
	<!-- 头像, 名称等信息div--- begin -->
	<div class="head-title">
		<div class="head-image">
			<img src="${contextPath }/images/backdoor/head_image.png" />
		</div>
		<div class="head-content">
			<span id="adminNickName" style="font-weight: bold;color: rgb(82, 84, 253);"></span><br/>
			<span id="adminAreaName"style="font-size: 8px;color: rgb(131, 131, 131);"></span>
		</div>
	</div>
	<!-- 头像, 名称等信息div--- end -->
	<div style="font: 0px/0px sans-serif;clear: both;display: block"> </div>
	<div id="menu-collapse">
	</div>
	
</div>
<!-- Placed at the end of the document so the pages load faster -->
<script src="${contextPath }/js/lib/jquery-ui-bootstrap/jquery-ui-1.10.0.custom.js" type="text/javascript"></script>
