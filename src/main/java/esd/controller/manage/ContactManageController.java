package esd.controller.manage;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import esd.bean.Area;
import esd.bean.Articles;
import esd.bean.User;
import esd.controller.Constants;
import esd.service.ArticlesService;
import esd.service.CookieHelper;
import esd.service.UserService;

/**
 * 文章(就业指导/最新资讯)后台管理控制器
 * 
 * @author yufu
 * @email ilxly01@126.com 2014-11-5
 */
@Controller
@RequestMapping("/manage/contact")
public class ContactManageController {
	private static Logger log = Logger.getLogger(ContactManageController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private ArticlesService articleService;

	// 跳转到联系我们 信息管理页面
	@RequestMapping(value = "/contact", method = RequestMethod.GET)
	public ModelAndView contact_get(HttpServletRequest request) {
		log.debug("goto：/contact ");
		// 获取当前管理员所在地区code
		String userId = CookieHelper.getCookieValue(request,
				Constants.ADMINUSERID);
		if (userId == null || "".equals(userId)) {
			return new ModelAndView("redirect:/loginManage/login");
		}
		Integer uid = Integer.parseInt(userId);
		User user = userService.getById(uid);
		// 根据管理员用户所属地区, 查询他下面所属的所有数据
		String acode = user.getArea().getCode();
		// 根据地区code得到对应的"联系我们"数据
		Articles contact = articleService.getContact(acode);
		if (contact == null) {
			contact = articleService.getContact(Constants.AREACOUNTRY);
		}
		Map<String, Object> entity = new HashMap<String, Object>();
		entity.put("content", contact.getContent());
		return new ModelAndView("manage/contact", entity);
	}

	// 提交文章编辑
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> news_edit_post(HttpServletRequest request,
			HttpServletResponse response) {
		log.debug(" 更新    联系我们  ");
		String content = request.getParameter("content");
		// 获取当前管理员所在地区code
		String userId = CookieHelper.getCookieValue(request,
				Constants.ADMINUSERID);
		Integer uid = Integer.parseInt(userId);
		User user = userService.getById(uid);
		// 根据管理员用户所属地区, 查询他下面所属的所有数据
		String acode = user.getArea().getCode();
		// 根据地区code得到对应的"联系我们"数据
		Articles contact = articleService.getContact(acode);
		Boolean flag = Boolean.FALSE;
		if (contact == null) {
			contact = new Articles();
			contact.setArea(new Area(acode));
			contact.setContent(content);
			contact.setType(Constants.ARTICLETYPE.CONTACT.getValue());
			flag = articleService.save(contact);
		} else {
			contact.setContent(content);
			flag = articleService.update(contact);
		}
		Map<String, Object> entity = new HashMap<String, Object>();
		if (flag) {
			entity.put(Constants.NOTICE, Constants.Notice.SUCCESS.getValue());
		} else {
			entity.put(Constants.NOTICE, "更新联系我们出错, 请联系管理员.");
		}
		return entity;
	}

}
