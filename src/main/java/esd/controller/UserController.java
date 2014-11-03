package esd.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.octo.captcha.service.CaptchaServiceException;

import esd.bean.Company;
import esd.bean.User;
import esd.controller.Constants.Identity;
import esd.controller.Constants.Notice;
import esd.controller.checkcode.CaptchaServiceSingleton;
import esd.service.CompanyService;
import esd.service.KitService;
import esd.service.ParameterService;
import esd.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	private static Logger log = Logger.getLogger(UserController.class);
	@Autowired
	private UserService<User> userService;

	@Autowired
	private CompanyService<Company> companyService;

	@Autowired
	private ParameterService pService;

	// 注册,即保存一个账号
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(HttpServletRequest req, User user, HttpSession session,
			RedirectAttributes redirectAttributes) {
		log.info("--- save ---");
		// 判断验证码是否正确
		String codeStr = req.getParameter("LoginVerifyCode");
		String captchaId = req.getSession().getId();
		try {
			Boolean isResponseCorrect = CaptchaServiceSingleton.getInstance()
					.validateResponseForID(captchaId, codeStr);
			if (!isResponseCorrect) {
				redirectAttributes.addFlashAttribute("messageType", "0");
				redirectAttributes.addFlashAttribute("message", "验证码错误");
				return "redirect:/regP";
			}
		} catch (CaptchaServiceException e) {
			log.error("error in check", e);
		}
		log.info("areaCode " + req.getParameter("area.code") + " || "
				+ user.getArea());
		// if (user != null) {
		// if (user.getArea() == null) {
		// user.setArea(new Area("30230103"));
		// }
		// }
		String identity = user.getIdentity();
		if (Constants.Identity.SUPERADMIN.toString().equals(identity)) {
			user.setAuthority(Constants.Authority.SUPERADMIN.getValue());
		} else if (Constants.Identity.ADMIN.toString().equals(identity)) {
			user.setAuthority(Constants.Authority.ADMIN.getValue());
		} else if (Constants.Identity.COMPANY.toString().equals(identity)) {
			user.setAuthority(Constants.Authority.COMPANY.getValue());
		} else if (Constants.Identity.PERSON.toString().equals(identity)) {
			user.setAuthority(Constants.Authority.PERSON.getValue());
		}
		log.info("注册用户为: " + user);
		boolean bl = userService.save(user);
		log.info("save bl = " + bl);
		if (!bl) {
			if (Constants.Identity.PERSON.toString().equals(user.getIdentity())) {
				return "reg-p";
			} else if (Constants.Identity.COMPANY.toString().equals(
					user.getIdentity())) {
				return "reg-c";
			} else {
				return "redirect:/error";
			}
		}
		// 如果需要审核, 则弹出提示框
		boolean u_bl = pService.getSwitchStatus(
				Constants.Switch.USER.toString(), user.getArea().getCode());
		if (u_bl) {
			redirectAttributes.addFlashAttribute("messageType", "0");
			redirectAttributes
					.addFlashAttribute("message", "注册请求已提交, 请等待管理员审核");
		} else {
			session.setAttribute(Constants.USER, user);
			// 地区码
			session.setAttribute(Constants.AREA, user.getArea());
		}
		return "redirect:/index";
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

	// 登陆
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(User user, HttpServletRequest request,
			HttpSession session, RedirectAttributes ra) {
		// 请求来的uri
		String preURI = request.getHeader("Referer");
		int index = preURI.indexOf("jobservice");
		String path = null;
		log.debug("uri =============  " + preURI + "   ***** *  " + index);
		if (preURI != null) {
			path = preURI.substring(index + 10);
		}
		log.debug("uri\t" + preURI);
		log.debug("index\t" + index);
		log.debug("path\t" + path);
		log.debug("--- login --- user : " + user);

		if (user == null) {
			ra.addFlashAttribute("messageType", "0");
			ra.addFlashAttribute("message", "登录错误");
			return "redirect:" + path;
		}
		// 判断验证码是否正确
		String codeStr = request.getParameter("LoginVerifyCode");
		String captchaId = request.getSession().getId();
		System.out.println("codeStr : " + codeStr + "  captchaId : "
				+ captchaId);
		try {
			Boolean isResponseCorrect = CaptchaServiceSingleton.getInstance()
					.validateResponseForID(captchaId, codeStr);
			if (!isResponseCorrect) {
				ra.addFlashAttribute("messageType", "0");
				ra.addFlashAttribute("message", "验证码错误");
				return "redirect:" + path;
			}
		} catch (CaptchaServiceException e) {
			log.error("error in check", e);
		}
		log.debug("user : " + user);
		user = userService.check(user);
		if (user == null) {
			ra.addFlashAttribute("messageType", "0");
			ra.addFlashAttribute("message", "用户名或密码错误!");
			return "redirect:" + path;
		}
		if (user.getCheckStatus().equals(
				Constants.CheckStatus.DAISHEN.toString())) {
			ra.addFlashAttribute("messageType", "0");
			ra.addFlashAttribute("message", "用户正在审核中, 请稍后登陆!");
			return "redirect:" + path;
		} else if (user.getCheckStatus().equals(
				Constants.CheckStatus.WEITONGGUO.toString())) {
			ra.addFlashAttribute("messageType", "0");
			ra.addFlashAttribute("message", "用户名没有通过审核, 请重新申请!");
			return "redirect:" + path;
		}
		if (user.getIdentity().equals(Identity.COMPANY.toString())) {
			Company company = companyService.getByAccount(user.getId());
			log.debug("company " + company);
			session.setAttribute("company", company);
		}
		log.debug("login: " + user);
		session.setAttribute(Constants.USER, user);
		// 地区码
		session.setAttribute("area", user.getArea());
		return "redirect:/index";
	}

	// 检查一个账号是否存在
	@RequestMapping(value = "/check/{loginName}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> check(@PathVariable("loginName") String loginName) {
		log.info("--- check ---");
		log.info("--- loginName ---" + loginName);
		Map<String, Object> json = new HashMap<String, Object>();
		if (loginName == null || "".equals(loginName)) {
			json.put("notice", Notice.ERROR);
			return json;
		}
		User user = userService.check(loginName);
		if (user == null) {
			// 不存在
			json.put("notice", Notice.SUCCESS.toString());
			return json;
		}
		// 该用户存在
		json.put("notice", Notice.FAILURE.toString());
		return json;
	}

	// 登出
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		log.info("--- logout ---");
		session.invalidate();
		return "redirect:/index";
	}

	// 跳转到个人中心
	@RequestMapping("/goCenter")
	public String goCenter(HttpSession session) {
		log.info("--- goCenter ---");
		User user = (User) session.getAttribute(Constants.USER);
		if (user == null) {
			return "redirect:/index.jsp";
		}
		if (user.getIdentity().equals(Constants.Identity.COMPANY.toString())) {
			Company c = companyService.getByAccount(user.getId());
			// 如未填公司资料, 则跳转到填写公司资料页面
			if (c == null) {
				return "redirect:/secure/company/save";
			}
		}
		return "/" + user.getIdentity() + "/index";
	}

	/**
	 * 接收上传的的头像图片
	 * 
	 * @param pic
	 * @param session
	 */
	@RequestMapping(value = "/uploadPic", method = RequestMethod.POST)
	public void importPic(@RequestParam("pic") CommonsMultipartFile pic,
			HttpServletResponse response, HttpSession session)
			throws IOException {
		// ① response 输出相应内容
		response.setContentType("text/html;charset=utf-8");
		PrintWriter writer = response.getWriter();
		if (pic == null) {
			writer.write(Constants.NOTICE + ":" + "网络发生错误, 上传照片失败.");
			return;
		}
		User user = (User) session.getAttribute(Constants.USER);
		user = userService.getById(user.getId());
		//要更新的对象
		User tempUser = new User();
		tempUser.setId(user.getId());
		tempUser.setUpdateCheck(user.getUpdateCheck());
		tempUser.setHeadImage(pic.getBytes());
		boolean bl = userService.update(tempUser);
		if (bl) {
			writer.write(Constants.Notice.SUCCESS.toString());
		} else {
			writer.write(Constants.NOTICE + ":" + "上传图片失败");
		}
	}

	/**
	 * 上传图片超出最大值时, 弹出的异常
	 * 
	 * @param ex
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@ExceptionHandler(Exception.class)
	public void handlerException(Exception ex, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf8");
		String notice = "error";
		if (ex instanceof MaxUploadSizeExceededException) {
			notice = "文件大小不超过"
					+ getFileMB(((MaxUploadSizeExceededException) ex)
							.getMaxUploadSize());
		} else {
			notice = "上传文件出现错误,错误信息:" + ex.getMessage();
		}
		PrintWriter writer = response.getWriter();
		writer.write(notice);
	}

	/**
	 * 字节转为MB 方法
	 * 
	 * @param byteFile
	 * @return
	 */
	private String getFileMB(long byteFile) {
		if (byteFile == 0) {
			return "0MB";
		}
		long mb = 1024 * 1024;
		return "" + byteFile / mb + "MB";
	}
	
	// 得到上传头像图片的方法
	@RequestMapping("/downloadPic/{id}")
	@ResponseBody
	public byte[] viewWorkerPicGet(@PathVariable(value="id") Integer id) {
		byte[] entity = userService.getHeadImage(id);
		return entity;
	}
}
