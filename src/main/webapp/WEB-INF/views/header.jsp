<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<div id="topheader">
	<div class="top">
		<div id="topcontent">
			<div id="topcontentleft">
				<ul id="ctl00_Myheader_yeslogin" class="loginul">
					<!-- 用户登陆时 -->
					<c:if test="${cookie.username.value != null && cookie.username.value != ''}">
						<li id="lihome">欢迎您回来：<span id="ctl00_Myheader_Label1" style="color: red">${cookie.username.value }</span>
						</li>
						<c:choose>
							<c:when test="${cookie.identity == 'admin' }">
								<li id="limember"><a title="进入管理后台" href="${contextPath }/manage/index" target="_blank">[进入管理后台]</a></li>
							</c:when>
							<c:otherwise>
								<li id="limember"><a title="进入用户中心" href="${contextPath }/user/goCenter">[进入用户中心]</a></li>
							</c:otherwise>
						</c:choose>
						<li id="liquit"><a href="${contextPath}/user/logout">退出登陆</a>
						</li>
					</c:if>
					<!-- 没有用户登陆时 -->
					<c:if test="${cookie.username.value == null || cookie.username.value == ''}">
						<li><a title="企业会员注册" href="${contextPath}/regC" target="_blank"> 企业会员注册</a></li>
						<li>|</li>
						<li><a title="个人会员注册" href="${contextPath}/regP" target="_blank"> 个人会员注册</a></li>
					</c:if>
				</ul>
			</div>
			<div style="float: right;">
				<a href="#" onclick="SetHome(this,window.location)">设为首页</a>|<a href="javascript:addFavorite();">加入收藏</a>|客服热线：<strong style="color:Red">0451-55664482</strong>
			</div>
			<div style="clear: both"></div>
		</div>
	</div>
	
	<div id="ctl00_Myheader_default71dacom" class="header_cnt">
		<div id="logoheader">
			<div id="logoopen">
				<a href="${contextPath}/index" title="残疾人就业信息网">
					<img alt="残疾人就业信息网" src="${contextPath}/images/logoProvince/hubei.png" id="bg2" /> 
				</a>
			</div>
			<div id="headertext">
				<a href="javascript:void(0);"><img src="${contextPath}/images/7b2b5940424144e8bb123fd92a85f613_top[1].jpg" /> </a>
			</div>
		</div>
	</div>
</div>
	<script type="text/javascript">
		//添加收藏夹
		function addFavorite(){
			var fav_url = 'http://cjrjy.hrbesd.com/';
			var fav_title = '残疾人就业网';
			if(document.all && window.external){
				window.external.addFavorite(fav_url,fav_title);
				alert('添加收藏夹成功!');
			}else if(window.sidebar){
				window.sidebar.addPanel(fav_title,fav_url,"");
				alert('添加收藏夹成功!');
			}else{
				alert('浏览器不支持，请手动CTRL+D加入收藏夹');
			}
		}
	</script>