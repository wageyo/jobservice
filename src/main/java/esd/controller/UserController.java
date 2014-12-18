package esd.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
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

import esd.bean.Area;
import esd.bean.Company;
import esd.bean.User;
import esd.controller.Constants.Identity;
import esd.controller.Constants.Notice;
import esd.controller.checkcode.CaptchaServiceSingleton;
import esd.service.AreaService;
import esd.service.CompanyService;
import esd.service.CookieHelper;
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

	@Autowired
	private AreaService areaService;
	
	// 注册
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(HttpServletRequest request, User user, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		log.info("--- save ---");
		// 判断验证码是否正确
		String codeStr = request.getParameter("LoginVerifyCode");
		String captchaId = request.getSession().getId();
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
		log.info("areaCode " + request.getParameter("area.code") + " || "
				+ user.getArea());
		String identity = user.getIdentity();
		if (Constants.Identity.ADMIN.getValue().equals(identity)) {
			user.setAuthority(Constants.Authority.ADMIN.getValue());
		} else if (Constants.Identity.COMPANY.getValue().equals(identity)) {
			user.setAuthority(Constants.Authority.COMPANY.getValue());
		} else if (Constants.Identity.PERSON.getValue().equals(identity)) {
			user.setAuthority(Constants.Authority.PERSON.getValue());
		}
		log.info("注册用户为: " + user);
		//如果cookie中有acode, 则使用默认的地区code, 否则使用前台传过来的地区code
		String acode = CookieHelper.getCookieValue(request, Constants.AREA);
		if(acode != null && !"".equals(acode)){
			user.setArea(new Area(acode));
		}
		boolean bl = userService.save(user);
		log.info("save bl = " + bl);
		if (!bl) {
			if (Constants.Identity.PERSON.getValue().equals(user.getIdentity())) {
				return "reg-p";
			} else if (Constants.Identity.COMPANY.getValue().equals(
					user.getIdentity())) {
				return "reg-c";
			} else {
				return "redirect:/error";
			}
		}
		// 如果需要审核, 则弹出提示框
		boolean u_bl = pService.getSwitchStatus(
				Constants.Switch.USER.getValue(), user.getArea().getCode());
		if (u_bl) {
			redirectAttributes.addFlashAttribute("messageType", "0");
			redirectAttributes.addFlashAttribute("message", "注册请求已提交, 请等待管理员审核");
		} else {
			CookieHelper.setCookie(response, Constants.USERID, String.valueOf(user.getId()));
			CookieHelper.setCookie(response, Constants.USERPASSWORD,user.getPassWord());
			CookieHelper.setCookie(response, Constants.USERNAME,user.getLoginName());
			CookieHelper.setCookie(response, Constants.USERIDENTITY,user.getIdentity());
			CookieHelper.setCookie(response, Constants.USERAUTHORITY,String.valueOf(user.getAuthority()));
			CookieHelper.setCookie(response, Constants.USERREGISTERTIME,KitService.dateForShow(user.getCreateDate()));
			//地区代码设为永久
			CookieHelper.setCookie(response, Constants.AREA,user.getArea().getCode(),Integer.MAX_VALUE);
		}
		return "redirect:/index";
	}

	// 根据id得到一个账号对象
	@RequestMapping("/getOne")
	@ResponseBody
	public Map<String, Object> getOne(HttpServletRequest request) {
		log.info("--- getOne ---");
		Map<String, Object> json = new HashMap<String, Object>();
		String idStr = request.getParameter("id");
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
	public String login(HttpServletRequest request,HttpServletResponse response, RedirectAttributes ra) {
		String loginName = request.getParameter("loginName");
		String passWord = request.getParameter("passWord");
		if (loginName == null) {
			ra.addFlashAttribute("messageType", "0");
			ra.addFlashAttribute("message", "用户名为空");
			return "redirect:/index";
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
				return "redirect:/index";
			}
		} catch (CaptchaServiceException e) {
			log.error("error in check", e);
		}
		log.debug("loginName : " + loginName);
		//判断残疾证号/用户名在数据库中是否存在
		User user = userService.check(loginName);
		//1-不存在则初始化用户, 并跳转到设置密码页面
		if (user == null) {
			user = new User();
			user.setLoginName(loginName);
			user.setPassWord(passWord);
			user.setIdentity(Constants.Identity.PERSON.getValue());
			user.setAuthority(Constants.Authority.PERSON.getValue());
			String acode = CookieHelper.getCookieValue(request, Constants.AREA);
			user.setArea(new Area(acode));
			//保存用户
			Boolean bl = userService.save(user);
			if(!bl){
				ra.addFlashAttribute("messageType", "0");
				ra.addFlashAttribute("message", "初始化用户失败, 请联系管理员!");
				return "redirect:/index";
			}else{
				ra.addFlashAttribute("messageType", "1");
				ra.addFlashAttribute("message", "请先设置密码!!");
				//相关的用户信息放到cookie中
				CookieHelper.setCookie(response, Constants.USERID, String.valueOf(user.getId()));
				CookieHelper.setCookie(response, Constants.USERNAME,user.getLoginName());
				CookieHelper.setCookie(response, Constants.USERIDENTITY,user.getIdentity());
				CookieHelper.setCookie(response, Constants.USERAUTHORITY,String.valueOf(user.getAuthority()));
				CookieHelper.setCookie(response, Constants.USERREGISTERTIME,KitService.dateForShow(user.getCreateDate()));
				request.setAttribute(Constants.USERID, user.getId());
				request.setAttribute(Constants.USERNAME, user.getLoginName());
				ra.addFlashAttribute("messageType", "0");
				ra.addFlashAttribute("message", "请先设置密码!!");
				return "person/password-set";
			}
		}
		
		//如果用户没有设置密码, 则强行跳转到设置密码页面
		if(user.getPassWord() == null || "".equals(user.getPassWord())){
			//相关的用户信息放到cookie中
			CookieHelper.setCookie(response, Constants.USERID, String.valueOf(user.getId()));
			CookieHelper.setCookie(response, Constants.USERNAME,user.getLoginName());
			CookieHelper.setCookie(response, Constants.USERIDENTITY,user.getIdentity());
			CookieHelper.setCookie(response, Constants.USERAUTHORITY,String.valueOf(user.getAuthority()));
			CookieHelper.setCookie(response, Constants.USERREGISTERTIME,KitService.dateForShow(user.getCreateDate()));
			request.setAttribute(Constants.USERID, user.getId());
			request.setAttribute(Constants.USERNAME, user.getLoginName());
			ra.addFlashAttribute("messageType", "0");
			ra.addFlashAttribute("message", "请先设置密码!!");
			return "person/password-set";
		}
		if (user.getCheckStatus().equals(
				Constants.CheckStatus.DAISHEN.toString())) {
			ra.addFlashAttribute("messageType", "0");
			ra.addFlashAttribute("message", "用户正在审核中, 请稍后登陆!");
			return "redirect:/index";
		} else if (user.getCheckStatus().equals(
				Constants.CheckStatus.WEITONGGUO.toString())) {
			ra.addFlashAttribute("messageType", "0");
			ra.addFlashAttribute("message", "用户名没有通过审核, 请重新申请!");
			return "redirect:/index";
		}
		//如果输入的密码为空, 则跳转到前台进行提示
		if(passWord == null || "".equals(passWord)){
			ra.addFlashAttribute("messageType", "0");
			ra.addFlashAttribute("message", "残疾证号已经被注册过, 请输入密码!");
			return "redirect:/index";
		}
		if (user.getIdentity().equals(Identity.COMPANY.toString())) {
			Company company = companyService.getByAccount(user.getId());
			log.debug("company " + company);
			if(company != null){
				CookieHelper.setCookie(response, Constants.USERCOMPANYID, String.valueOf(company.getId()));
			}
		}
		log.debug("login: " + user);
		CookieHelper.setCookie(response, Constants.USERID, String.valueOf(user.getId()));
		CookieHelper.setCookie(response, Constants.USERNAME,user.getLoginName());
		CookieHelper.setCookie(response, Constants.USERIDENTITY,user.getIdentity());
		CookieHelper.setCookie(response, Constants.USERAUTHORITY,String.valueOf(user.getAuthority()));
		CookieHelper.setCookie(response, Constants.USERREGISTERTIME,KitService.dateForShow(user.getCreateDate()));
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
			json.put("notice", Notice.SUCCESS.getValue());
			return json;
		}
		// 该用户存在
		json.put("notice", Notice.FAILURE.getValue());
		return json;
	}

	// 登出
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		log.info("--- 登出 ---");
		//杀死除地区code外的所有cookie
		CookieHelper.setCookie(response, Constants.USERID, null, 0);
		CookieHelper.setCookie(response, Constants.USERNAME, null, 0);
		CookieHelper.setCookie(response, Constants.USERPASSWORD, null, 0);
		CookieHelper.setCookie(response, Constants.USERIDENTITY, null, 0);
		CookieHelper.setCookie(response, Constants.USERAUTHORITY, null, 0);
		CookieHelper.setCookie(response, Constants.USERREGISTERTIME, null, 0);
		CookieHelper.setCookie(response, Constants.USERCOMPANYID, null, 0);
		return "redirect:/index";
	}

	// 跳转到个人中心
	@RequestMapping("/goCenter")
	public String goCenter(HttpServletRequest request, HttpServletResponse response) {
		log.info("--- goCenter ---");
		String userId = CookieHelper.getCookieValue(request, Constants.USERID);
		//用户id为空即为未登录
		if (userId == null ||"".equals(userId)) {
			return "redirect:/index";
		}
		String identity = CookieHelper.getCookieValue(request, Constants.USERIDENTITY);
		//如果为公司用户, 且没有填写基本资料, 则跳转到填写公司基本信息页面上
		if (identity.equals(Constants.Identity.COMPANY.getValue())) {
			Integer uid = Integer.parseInt(userId);
			Company c = companyService.getByAccount(uid);
			// 如未填公司资料, 则跳转到填写公司资料页面
			if (c == null) {
				return "redirect:/secure/company/save";
			}
		}
		return "/" +identity + "/index";
	}

	//接收上传的的头像图片
	@RequestMapping(value = "/uploadPic", method = RequestMethod.POST)
	public void importPic(@RequestParam("pic") CommonsMultipartFile pic,HttpServletRequest request,
			HttpServletResponse response)
			throws IOException {
		// ① response 输出相应内容
		response.setContentType("text/html;charset=utf-8");
		PrintWriter writer = response.getWriter();
		if (pic == null) {
			writer.write(Constants.NOTICE + ":" + "网络发生错误, 上传照片失败.");
			return;
		}
		Integer id = KitService.getInt(request.getParameter("userid"));
		//要更新的对象
		User tempUser = new User();
		tempUser.setId(id);
		tempUser.setHeadImage(pic.getBytes());
		boolean bl = userService.update(tempUser);
		if (bl) {
			writer.write(Constants.Notice.SUCCESS.getValue());
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
	
	// 下载头像图片的方法
	@RequestMapping("/downloadPic/{id}")
	@ResponseBody
	public byte[] viewWorkerPicGet(@PathVariable(value="id") Integer id,HttpServletResponse response) {
//		response.addHeader("Content-Type", "image/gif");
		response.setContentType("image/gif");
		byte[] entity = userService.getHeadImage(id);
		return entity;
	}
	
	// 设置密码
	@RequestMapping(value = "/setPassWord", method = RequestMethod.GET)
	public String setPassWordGet(HttpServletRequest request) {
		return "person/password-set";
	}
	// 设置密码
	@RequestMapping(value = "/setPassWord", method = RequestMethod.POST)
	public String setPassWordPost(HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes ra) {
		log.info("--- passWordEdit post---");
		String passWord = request.getParameter("newPassWord");
		String userId = request.getParameter(Constants.USERID);
		Integer uid = Integer.parseInt(userId);
		User user = userService.getById(uid);
		user.setPassWord(passWord);
		boolean bl = userService.update(user);
		log.info("update password bl = " + bl);
		if (!bl) {
			ra.addFlashAttribute("messageType", "0");
			ra.addFlashAttribute("message", "修改密码失败, 请联系管理员.");
			return "redirect:/secure/user/setPassWord";
		}
//			//将基本属性设置到cookie中 
//			CookieHelper.setCookie(response, Constants.USERID, String.valueOf(user.getId()));
//			CookieHelper.setCookie(response, Constants.USERNAME,user.getLoginName());
//			CookieHelper.setCookie(response, Constants.USERIDENTITY,user.getIdentity());
//			CookieHelper.setCookie(response, Constants.USERAUTHORITY,String.valueOf(user.getAuthority()));
//			CookieHelper.setCookie(response, Constants.USERREGISTERTIME,KitService.dateForShow(user.getCreateDate()));
		ra.addFlashAttribute("messageType", "1");
		ra.addFlashAttribute("message", "操作成功!");
		return "redirect:/user/goCenter";
	}
	
}
