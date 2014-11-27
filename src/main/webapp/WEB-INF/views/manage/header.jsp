<%@ page language="java" import="java.util.*" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
	$(document).ready(function(){
		decodeTitle();
	});

	//解码title, nickName
	function decodeTitle(){
		var nickName = decodeURIComponent('${cookie.nickname.value}');
		$('#nickName').html(nickName);
		var title = decodeURIComponent('${cookie.title.value}');
		$('#adminTitle').html(title);
	}
</script>
<div class="manage-header" style="">
	<table style="width:100%;height:100%;background-color: rgb(240, 240, 255);;">
		<tr>
			<td style="line-height: 20px;">
				&nbsp;
			</td>
			<td style="line-height: 20px;">
				&nbsp;
			</td>
			<td style="text-align:right;line-height: 20px;">
				<a href="javascript:void(0);" id="nickName">${cookie.nickname.value } </a>
				<span id="currentTime"></span>
				<span style=""><a href="/jobservice/loginManage/quit">退出</a></span>
			</td>
		</tr>
		<tr>
			<td style="width:5%;">
				&nbsp;
			</td>
			<td >
				<span style="font-size:30px;letter-spacing:10px;" id="adminTitle"></span>
			</td>
			<td>
				&nbsp;
			</td>
		</tr>
		<tr>
			<td colspan="3" style="line-height: 20px;">
				<img src="${contextPath }/images/backdoor/break_bg1.png" />
			</td>
		</tr>
	</table>
	<!-- 显示当前时间的脚本 -->
	<script type="text/javascript">
		$('#currentTime').html(CurentTime());
		var currentTime = setInterval('getCurrntTime()',1000);
		function getCurrntTime(){
			$('#currentTime').html(CurentTime());
		}
		
		function CurentTime()
		{ 
		    var now = new Date();
		   
		    var year = now.getFullYear();       //年
		    var month = now.getMonth() + 1;     //月
		    var day = now.getDate();            //日
		   
		    var hh = now.getHours();            //时
		    var mm = now.getMinutes();          //分
		    var ss = now.getSeconds();
		    var clock = year + "-";
		   
		    if(month < 10)
		        clock += "0";
		    clock += month + "-";
		   
		    if(day < 10)
		        clock += "0";
		    clock += day + " ";
		   
		    if(hh < 10)
		        clock += "0";
		    clock += hh + ":";
		    
		    if (mm < 10) 
		    	clock += '0'; 
		    clock += mm + ":"; 
		    
		    if (ss < 10) 
		    	clock += '0'; 
		    clock += ss + ' '; 
		    
		    return clock;
		}
	</script>
</div>