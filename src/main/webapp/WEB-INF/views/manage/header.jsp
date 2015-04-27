<%@ page language="java" import="java.util.*" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
	
</script>
<div class="manage-header" style="">
	<table style="width:100%;">
		<tr>
			<td style="line-height: 20px;padding-left:30px;">
				<span style="font-size:13px;letter-spacing:5px;" id="adminTitle">残疾人就业网管理后台</span>
			</td>
			<td style="line-height: 20px;">
				&nbsp;
			</td>
			<td style="text-align:right;line-height: 20px;">
			<!-- 	<a href="javascript:void(0);" class="adminNickName">${cookie.nickname.value } </a>	 -->
				<span id="currentTime"></span>
				<span style=""><a href="/jobservice/loginManage/quit">退出</a></span>
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