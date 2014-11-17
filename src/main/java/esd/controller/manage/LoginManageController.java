package esd.controller.manage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import esd.service.UserService;

/**
 * 后台登陆管理控制器
 * 
 * @author yufu
 * @email ilxly01@126.com 2014-11-6
 */
@Controller
@RequestMapping("/loginManage")
public class LoginManageController {
	private static Logger log = Logger.getLogger(LoginManageController.class);

	@Autowired
	private UserService userService;

	// 转到管理 登陆页面
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView goLogin(HttpServletRequest request,
			HttpSession session, RedirectAttributes ra) {
		log.debug("转到管理 登陆页面");
		return new ModelAndView("manage/login");
	}

	// 转到管理 登陆页面
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, HttpSession session,
			RedirectAttributes ra) {
		Object obj = session.getAttribute(Constants.USER);
		// 判断是否在别处登录
		if (obj == null) {
			log.debug("进入到后台管理登录页面。");
			String loginName = request.getParameter("loginName");
			String passWord = request.getParameter("passWord");
			if (loginName == null || "".equals(loginName) || passWord == null
					|| "".equals(passWord)) {
				log.error(" 登陆管理  账号或密码为空。");
				ra.addFlashAttribute(Constants.NOTICE, "账号或密码错误为空");
				return "redirect:/loginManage/login";
			}
			User userObj = new User();
			userObj.setLoginName(loginName);
			userObj.setPassWord(passWord);
			User u = userService.check(userObj);
			if (u == null) {
				log.debug("登陆管理登陆失败。");
				ra.addFlashAttribute(Constants.NOTICE, "账号或密码错误");
				return "redirect:/loginManage/login";
			} else {
				// // 非管理员用户登陆
				// if (!Identity.ADMIN.toString().equals(u.getIdentity())) {
				// ra.addFlashAttribute(Constants.NOTICE, "权限不足");
				// return "redirect:/loginManage/login";
				// }
				if (u.getAuthority() != null) {
					if (u.getAuthority() < Authority.ADMIN.getValue()) {
						ra.addFlashAttribute(Constants.NOTICE, "权限不足");
						return "redirect:/loginManage/login";
					}
				}
				session.setAttribute(Constants.USER, u);
				session.setAttribute("titleText", u.getTitle());

				log.debug("管理用户登陆成功:" + u);
				return "redirect:/manage/index";
			}
		} else {
			log.debug("检测到用户已经登录，直接跳转到主页");
			return "redirect:/manage/index";
		}
	}

	// 跳转到转到修改密码页面
	@RequestMapping(value = "/password_edit", method = RequestMethod.GET)
	public ModelAndView password_edit_get(HttpServletRequest request) {
		log.debug("goto: 跳转到修改密码页面");
		return new ModelAndView("manage/password-edit");
	}

	// 修改密码
	@RequestMapping(value = "/password_edit", method = RequestMethod.POST)
	public String password_edit_post(HttpServletRequest request,
			HttpSession session, RedirectAttributes ra) {
		User user = (User) session.getAttribute(Constants.USER);
		String return_password_edit = "redirect:/loginManage/password_edit";
		if (user == null) {
			ra.addFlashAttribute(Constants.NOTICE, "用户未登录,请登陆后再进行操作!");
			return return_password_edit;
		}
		String oldPassWord = request.getParameter("oldPassWord");
		String newPassWord = request.getParameter("newPassWord");
		if (!user.getPassWord().equals(oldPassWord)) {
			ra.addFlashAttribute(Constants.NOTICE, "原密码不正确, 请重新操作!");
			return return_password_edit;
		}
		user.setPassWord(newPassWord);
		boolean bl = userService.update(user);
		if (!bl) {
			ra.addFlashAttribute(Constants.NOTICE, "更新密码失败!");
			return return_password_edit;
		}
		ra.addFlashAttribute(Constants.NOTICE, "密码修改成功!");
		return return_password_edit;
	}

	/*
	 * 退出
	 */
	@RequestMapping(value = "/quit", method = RequestMethod.GET)
	public String quit(HttpServletRequest request, HttpSession session) {
		session.invalidate();
		log.error("管理员用户退出");
		return "redirect:/loginManage/login";
	}

}
