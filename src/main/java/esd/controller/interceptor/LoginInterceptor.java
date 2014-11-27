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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import esd.controller.Constants;
import esd.service.CookieHelper;

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
		if (request.getRequestURI().indexOf("/secure") != -1) {
			String username = CookieHelper.getCookieValue(request, Constants.USERNAME);
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
		}

		/*
		 * 后台登陆过滤器
		 */
		if (request.getRequestURI().indexOf("/manage") != -1) {
			String username = CookieHelper.getCookieValue(request, Constants.USERNAME);
			String authorityStr = CookieHelper.getCookieValue(request, Constants.USERAUTHORITY);
			Integer authority = 0;
			if(authorityStr  != null && !"".equals(authorityStr)){
				authority = Integer.parseInt(authorityStr);
			}
			if (username == null || authority < Constants.Authority.ADMIN.getValue()) {
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
}
