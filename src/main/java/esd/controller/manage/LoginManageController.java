package esd.controller.manage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import esd.bean.Menu;
import esd.bean.User;
import esd.controller.Constants;
import esd.controller.Constants.Authority;
import esd.service.CompanyService;
import esd.service.JobService;
import esd.service.MenuService;
import esd.service.ResumeService;
import esd.service.UserService;

/**
 *  后台登陆管理控制器
 * @author yufu
 * @email ilxly01@126.com
 * 2014-11-6
 */
@Controller
@RequestMapping("/loginManage")
public class LoginManageController {
	private static Logger log = Logger.getLogger(LoginManageController.class);

	@Autowired
	private CompanyService companyService;

	@Autowired
	private UserService userService;

	@Autowired
	private JobService jobService;
	
	@Autowired
	private ResumeService resumeService;

	@Autowired
	private MenuService menuService;
	
	/*
	 * 转到管理 登陆页面
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView goLogin(HttpServletRequest request,HttpSession session,
			RedirectAttributes ra) {
		log.debug("转到管理 登陆页面");
//		//暂时写死不用登陆 直接到主页
//		List<Menu> menuList = menuService.getByAuthority(Constants.Authority.SUPERADMIN.getValue());
//		session.setAttribute("menuList", menuList);
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
	
	
	// 跳转到转到修改密码页面
	@RequestMapping(value = "/password_edit", method = RequestMethod.GET)
	public ModelAndView password_edit_get(HttpServletRequest request) {
		log.debug("goto: 跳转到修改密码页面");
		return new ModelAndView("manage/password-edit");
	}
	
	//修改密码
	@RequestMapping(value = "password_edit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> password_edit_post(HttpServletRequest request,
			HttpSession session) {
		Map<String, Object> notice = new HashMap<String, Object>();
		User user = (User) session.getAttribute(Constants.USER);
		if (user == null) {
			notice.put(Constants.NOTICE, "用户未登录,请登陆后再进行操作");
			return notice;
		}
		String oldPassWord = request.getParameter("oldPassWord");
		String newPassWord = request.getParameter("newPassWord");
		if (!user.getPassWord().equals(oldPassWord)) {
			notice.put(Constants.NOTICE, "原密码不正确, 请重新操作");
			return notice;
		}
		user.setPassWord(newPassWord);
		boolean bl = userService.update(user);
		if (!bl) {
			notice.put(Constants.NOTICE, "更新密码失败");
			return notice;
		}
		notice.put(Constants.NOTICE, bl);
		return notice;
	}
	
	
	
	
	/*
	 * 退出
	 */
	@RequestMapping(value = "/quit", method = RequestMethod.GET)
	public String quit(HttpServletRequest request,HttpSession session) {
		request.getSession().removeAttribute(Constants.USER);
		log.error("管理员用户退出");
		return "redirect:/loginManage/login";
	}
	
	
	
	
}
