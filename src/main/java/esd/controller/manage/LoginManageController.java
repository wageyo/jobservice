package esd.controller.manage;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import esd.bean.User;
import esd.controller.Constants;
import esd.controller.Constants.Authority;
import esd.controller.Constants.Identity;
import esd.service.CompanyService;
import esd.service.JobService;
import esd.service.ResumeService;
import esd.service.UserService;

@Controller
@RequestMapping("/loginmanage")
public class LoginManageController {
	private static Logger log = Logger.getLogger(LoginManageController.class);

	@Autowired
	private CompanyService companyService;

	public CompanyService getCompanyService() {
		return companyService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public JobService getJobService() {
		return jobService;
	}

	public void setJobService(JobService jobService) {
		this.jobService = jobService;
	}

	public ResumeService getResumeService() {
		return resumeService;
	}

	public void setResumeService(ResumeService resumeService) {
		this.resumeService = resumeService;
	}

	@Autowired
	private UserService userService;

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Autowired
	private JobService jobService;
	@Autowired
	private ResumeService resumeService;

	/*
	 * 转到管理 登陆页面
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView goLogin(HttpServletRequest request,
			RedirectAttributes ra) {
		log.debug("转到管理 登陆页面");

		return new ModelAndView("manage/login");
	}

	/*
	 * 转到管理 登陆页面
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, RedirectAttributes ra) {
		Object obj = request.getSession().getAttribute(Constants.USER);
		// 判断是否在别处登录
		if (obj == null) {
			log.debug("进入到后台管理登录页面。");
			String user = request.getParameter("user");
			String pwd = request.getParameter("pwd");
			if (user == null || user.equals("") || pwd == null
					|| pwd.equals("")) {
				log.error(" 登陆管理  参数有误。");
				request.setAttribute("loginInfo", "登陆参数有误");
				return "redirect:/loginmanage/login";
			}
			User userObj = new User();
			userObj.setLoginName(user);
			userObj.setPassWord(pwd);
			User u = userService.check(userObj);
			if (u == null) {
				log.debug("登陆管理登陆失败。");
				ra.addFlashAttribute("loginInfo", "账号或密码错误");
				return "redirect:/loginmanage/login";
			} else {
//				// 非管理员用户登陆
//				if (!Identity.ADMIN.toString().equals(u.getIdentity())) {
//					ra.addFlashAttribute("loginInfo", "权限不足");
//					return "redirect:/loginmanage/login";
//				}
				if (u.getAuthority() != null) {
					if (u.getAuthority() < Authority.ADMIN.getValue()) {
						ra.addFlashAttribute("loginInfo", "权限不足");
						return "redirect:/loginmanage/login";
					}
				}
				request.getSession().setAttribute(Constants.USER, u);
				request.getSession().setAttribute("titleText", u.getTitle());

				log.debug("管理用户登陆成功:" + u);
				return "redirect:/manage/index";
			}
		} else {
			log.debug("检测到用户已经登录，直接跳转到主页");
			return "redirect:/manage/index";
		}
	}
}
