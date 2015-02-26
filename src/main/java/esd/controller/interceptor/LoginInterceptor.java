/*
 * Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
 * 
 * HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package esd.controller.interceptor;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import esd.bean.Parameter;
import esd.bean.User;
import esd.controller.Constants;
import esd.service.CookieHelper;
import esd.service.ParameterService;
import esd.service.UserService;
import esd.service.WhiteListService;

/**
 * 用户登陆过滤器
 * 
 * @author zhangjianzong
 * 
 */
public class LoginInterceptor implements HandlerInterceptor {
	private static Logger logger = Logger.getLogger(LoginInterceptor.class);

	@Value("${login.url}")
	private String loginUrl;

	@Value("${loginManage.url}")
	private String loginManageUrl;

	@Value("${password.set}")
	private String setPassWordUrl;

	@Value("${forbid.url}")
	private String forbidUrl;

	@Autowired
	private UserService userService;

	@Autowired
	private ParameterService parameterService;

	@Autowired
	private WhiteListService whiteListService;

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object arg2, Exception arg3)
			throws Exception {
	}

	public void postHandle(HttpServletRequest request,
			HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
	}

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object arg2) {
//		System.out.println("1 request.getRemoteAddr():  " + request.getRemoteAddr());
//		System.out.println("2 request.getRemoteHost():  " + request.getRemoteHost());
//		System.out.println("3 request.getRemotePort():  " + request.getRemotePort());
//		System.out.println("4 request.getRemoteUser():  " + request.getRemoteUser());
//		System.out.println("5 request.getRequestURL():  " + request.getRequestURL());
//		System.out.println("6 request.getRequestURI():  " + request.getRequestURI());
//		System.out.println("7 request.getServerName():  " + request.getServerName());
		// 检查白名单功能是否开启
		Parameter whiteList = parameterService
				.getById(Constants.WHITE_LIST_SWITCH);
		// 如果白名单功能开启的话, 则检查请求url地址是否正确
		if (Constants.SWITCH_ON.equals(whiteList.getValue())) {
			Integer result = whiteListService.checkWhiteList(request.getRemoteAddr());
			System.out.println("result:  " + result);
			// 如果请求的url地址中包含的域名 不在白名单中, 则跳转到提示拒绝访问页面
			if (result == 0) {
				return false;
			}
		}
		if (request.getRequestURI().indexOf("/secure") != -1) {
			// 未登录则跳转到登陆页面
			String username = CookieHelper.getCookieValue(request,
					Constants.USERNAME);
			if (username == null) {
				PrintWriter out = null;
				try {
					out = response.getWriter();
					StringBuilder builder = new StringBuilder();
					builder.append("<script type=\"text/javascript\" charset=\"UTF-8\">");
					builder.append("window.top.location.href='");
					builder.append(loginUrl);
					builder.append("';");
					builder.append("</script>");
					out.print(builder.toString());
					out.close();
					return false;
				} catch (IOException e) {
					logger.error(e.getMessage());
				} finally {
					if (out != null) {
						out.close();
					}
				}

			}
			// 如果用户密码为空的话, 则跳转到设置密码页面
			User user = userService.check(username);
			if (user.getPassWord() == null || "".equals(user.getPassWord())) {
				PrintWriter out = null;
				try {
					out = response.getWriter();
					StringBuilder builder = new StringBuilder();
					builder.append("<script type=\"text/javascript\" charset=\"UTF-8\">");
					builder.append("window.top.location.href='");
					builder.append(setPassWordUrl);
					builder.append("';");
					builder.append("</script>");
					out.print(builder.toString());
					out.close();
					return false;
				} catch (IOException e) {
					logger.error(e.getMessage());
				} finally {
					if (out != null) {
						out.close();
					}
				}
			}
		}

		/*
		 * 后台登陆过滤器
		 */
		if (request.getRequestURI().indexOf("/manage") != -1) {
			String adminuserid = CookieHelper.getCookieValue(request,
					Constants.ADMINUSERID);
			if (adminuserid != null && !"".equals(adminuserid)) {
				return true;
			}
			String username = CookieHelper.getCookieValue(request,
					Constants.USERNAME);
			String authorityStr = CookieHelper.getCookieValue(request,
					Constants.USERAUTHORITY);
			Integer authority = 0;
			if (authorityStr != null && !"".equals(authorityStr)) {
				authority = Integer.parseInt(authorityStr);
			}
			if (username == null
					|| authority < Constants.Authority.ADMIN.getValue()) {
				logger.error("未登陆状态进入工作区被拦截");
				PrintWriter out = null;
				try {
					out = response.getWriter();
					StringBuilder builder = new StringBuilder();
					builder.append("<script type=\"text/javascript\" charset=\"UTF-8\">");
					builder.append("window.top.location.href='");
					builder.append(loginManageUrl);
					builder.append("';");
					builder.append("</script>");
					out.print(builder.toString());
					out.close();
					return false;
				} catch (IOException e) {
					logger.error(e.getMessage());
				} finally {
					if (out != null) {
						out.close();
					}
				}

			}
		}
		return true;
	}

	//获取远程访问的ip
	
}
