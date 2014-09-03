package esd.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import esd.bean.Company;
import esd.bean.User;
import esd.controller.Constants.Notice;
import esd.service.CompanyService;
import esd.service.KitService;
import esd.service.UserService;

@Controller
@RequestMapping("/secure/user")
public class SecureUserController {
	private static Logger log = Logger.getLogger(SecureUserController.class);
	@Autowired
	private UserService<User> userService;

	@Autowired
	private CompanyService<Company> companyService;

	// 修改账号
	@RequestMapping("/edit")
	@ResponseBody
	public Map<String, Object> edit(User user, HttpServletRequest req) {
		log.info("--- edit ---");
		Map<String, Object> json = new HashMap<String, Object>();
		String idStr = req.getParameter("id");
		int id = KitService.getInt(idStr);
		if (id < 0) {
			json.put("notice", Notice.ERROR);
			return json;
		}
		if (user == null) {
			json.put("notice", Notice.ERROR);
			return json;
		}
		user.setId(id);
		User tempUser = userService.check(user.getLoginName());
		if (tempUser != null) {
			json.put("notice", Notice.FAILURE);
			return json;
		}
		boolean bl = userService.update(user);
		log.info("save bl = " + bl);
		if (!bl) {
			json.put("notice", Notice.FAILURE);
			return json;
		}
		// 重新读取管理员信息
		user = userService.getById(id);
		if (user == null) {
			json.put("notice", Notice.ERROR);
			return json;
		}
		json.put("notice", Notice.SUCCESS);
		json.put(Constants.USER, user);
		return json;
	}

	// 根据id得到一个账号对象
	@RequestMapping("/getOne")
	@ResponseBody
	public Map<String, Object> getOne(HttpServletRequest req) {
		log.info("--- getOne ---");
		Map<String, Object> json = new HashMap<String, Object>();
		String idStr = req.getParameter("id");
		int id = KitService.getInt(idStr);
		if (id <= 0) {
			json.put("notice1", Notice.ERROR);
			return json;
		}
		User user = userService.getById(id);
		if (user == null) {
			json.put("notice2", Notice.ERROR);
			return json;
		}
		json.put("notice", Notice.SUCCESS);
		json.put(Constants.USER, user);
		return json;
	}

	// 跳转到修改密码页面修改密码
	@RequestMapping(value = "/passWordEdit", method = RequestMethod.GET)
	public String gotoPassWordEdit(HttpSession session) {
		log.info("--- passWordEdit get---");
		User user = (User) session.getAttribute(Constants.USER);
		return user.getIdentity() + "/password-edit";
	}

	// 修改密码
	@RequestMapping(value = "/passWordEdit", method = RequestMethod.POST)
	public String passWordEdit(HttpServletRequest req, HttpSession session,
			RedirectAttributes ra) {
		log.info("--- passWordEdit post---");
		User user = (User) session.getAttribute(Constants.USER);
		String oldPassWord = req.getParameter("oldPassWord");
		String newPassWord = req.getParameter("newPassWord");
		// 验证原密码是否正确
		User tempUser = new User();
		tempUser.setLoginName(user.getLoginName());
		tempUser.setPassWord(oldPassWord);
		tempUser = userService.check(tempUser);
		if (tempUser == null) {
			ra.addFlashAttribute("messageType", "0");
			ra.addFlashAttribute("message", "原密码不正确, 请重新输入!");
			return "redirect:/secure/user/passWordEdit";
		}
		// 更新密码
		user.setPassWord(newPassWord);
		boolean bl = userService.update(user);
		log.info("update password bl = " + bl);
		if (!bl) {
			ra.addFlashAttribute("messageType", "0");
			ra.addFlashAttribute("message", "操作失败!");
			return "redirect:/secure/user/passWordEdit";
		}
		// 重新读取用户放到session中
		user = userService.getById(user.getId());
		session.setAttribute(Constants.USER, user);
		ra.addFlashAttribute("messageType", "1");
		ra.addFlashAttribute("message", "操作成功!");
		return "redirect:/secure/user/passWordEdit";
	}

}
