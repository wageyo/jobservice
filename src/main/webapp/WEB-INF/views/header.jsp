<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<div id="topheader">
	<c:if test="${user != null }">
		<div class="top">
			<div id="topcontent">
				<div id="topcontentleft">

					<ul id="ctl00_Myheader_yeslogin" class="loginul">
						<li id="lihome">欢迎您回来：<span id="ctl00_Myheader_Label1" style="color: red">${user.loginName }</span>
						</li>
						<c:if test="${user != null }">
							<c:choose>
								<c:when test="${user.identity == 'admin' }">
									<li id="limember"><a title="进入管理后台" href="${contextPath }/manage/index" target="_blank">[进入管理后台]</a></li>
								</c:when>
								<c:otherwise>
									<li id="limember"><a title="进入用户中心" href="${contextPath }/user/goCenter">[进入用户中心]</a></li>
								</c:otherwise>
							</c:choose>
						</c:if>
						<li id="liquit"><a href="${contextPath}/user/logout">退出登陆</a>
						</li>
					</ul>
				</div>
				<div style="float: right;">
					<a href="#" onclick="SetHome(this,window.location)">设为首页</a>|<a href="javascript:addFavorite();">加入收藏</a>|客服热线：<strong style="color:Red">0451-55664482</strong>
				</div>
				<div style="clear: both"></div>
			</div>
		</div>
	</c:if>
	<c:if test="${user == null }">
		<div class="top">
			<div id="topcontent">
				<div id="topcontentleft">
					<ul id="ctl00_Myheader_nologin" class="loginul">

						<li><a title="企业会员注册" href="${contextPath}/regC" target="_blank"> 企业会员注册</a></li>
						<li>|</li>
						<li><a title="个人会员注册" href="${contextPath}/regP" target="_blank"> 个人会员注册</a></li>

					</ul>
				</div>
				<div style="float: right;">
					<a href="#" onclick="SetHome(this,window.location)">设为首页</a> | <a href="javascript:addFavorite();">加入收藏</a> | 技术支持：<strong style="color:Red">0451-55664482</strong>
				</div>
				<div style="clear: both"></div>
			</div>
		</div>
	</c:if>
	<div id="ctl00_Myheader_default71dacom" class="header_cnt">
		<div id="logoheader">
			<div id="logoopen">
				<a href="${contextPath}/index" title="残疾人就业信息网">
				<!-- 如果能显示对应省市的则显示对应省份的logo, 否则显示全国的log -->
				<c:choose>
					<c:when test="${area != null }">
						<c:choose>
							<c:when test="${area.code == '10420000' }">
								<img alt="残疾人就业信息网" src="${contextPath}/images/logoProvince/hubei.png" id="bg2" /> 
							</c:when>
							<c:when test="${area.code == '20652300' }">
								<img alt="残疾人就业信息网" src="${contextPath}/images/logoProvince/cjz.png" id="bg2" /> 
							</c:when>
							<c:when test="${area.code == '10150000' }">
								<img alt="残疾人就业信息网" src="${contextPath}/images/logoProvince/neimenggu.png" id="bg2" /> 
							</c:when>
							<c:when test="${area.code == '20230200' }">
								<img alt="残疾人就业信息网" src="${contextPath}/images/logoProvince/qigihar.png" id="bg2" /> 
							</c:when>
							<c:when test="${area.code == '10630000' }">
								<img alt="残疾人就业信息网" src="${contextPath}/images/logoProvince/qinghai.png" id="bg2" /> 
							</c:when>
							<c:when test="${area.code == '10650000' }">
								<img alt="残疾人就业信息网" src="${contextPath}/images/logoProvince/xinjiang.png" id="bg2" /> 
							</c:when>
							<c:otherwise>
								<img alt="残疾人就业信息网" src="${contextPath}/images/logoProvince/quanguo.png" id="bg2" />
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:otherwise>
							<img alt="残疾人就业信息网" src="${contextPath}/images/logoProvince/quanguo.png" id="bg2" /> 
					</c:otherwise>
				</c:choose>
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