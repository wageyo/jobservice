package esd.controller.manage;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import esd.bean.Menu;
import esd.bean.User;
import esd.controller.Constants;
import esd.service.MenuService;

@Controller
@RequestMapping("/manage")
public class ManageController {
	private static Logger log = Logger.getLogger(ManageController.class);

	@Autowired
	private MenuService menuService;

	// 转到管理 主页
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(HttpServletRequest request, HttpSession session,
			RedirectAttributes ra) {
		log.debug("goto：管理后台  首页");
		// 读取对应管理员对应的菜单, 放到session中
		// 从session中读取登陆管理员, 如果为空则返回登陆页面
		User userObj = (User) session.getAttribute(Constants.USER);
		if (userObj == null) {
			ra.addFlashAttribute(Constants.NOTICE, "session已过期, 请重新登陆.");
			return "redirect:/manage/login/index";
		}
		List<Menu> menuList = menuService
				.getByAuthority(userObj.getAuthority());
		session.setAttribute("menuList", menuList);
		return "/manage/index";
	}

}
