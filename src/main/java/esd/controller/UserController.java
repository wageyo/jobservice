package esd.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.octo.captcha.service.CaptchaServiceException;

import esd.bean.Area;
import esd.bean.Company;
import esd.bean.User;
import esd.controller.Constants.Identity;
import esd.controller.Constants.Notice;
import esd.controller.checkcode.CaptchaServiceSingleton;
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
					if(Constants.Identity.COMPANY.getValue().equals(user.getIdentity())){
						return "redirect:/regC";
					}else{
						return "redirect:/regP";
					}
			}
		} catch (CaptchaServiceException e) {
			log.error("error in check", e);
		}
		String identity = user.getIdentity();
		if (Constants.Identity.ADMIN.getValue().equals(identity)) {
			user.setAuthority(Constants.Authority.ADMIN.getValue());
		} else if (Constants.Identity.COMPANY.getValue().equals(identity)) {
			user.setAuthority(Constants.Authority.COMPANY.getValue());
		} else if (Constants.Identity.PERSON.getValue().equals(identity)) {
			user.setAuthority(Constants.Authority.PERSON.getValue());
		}
		log.info("注册用户为: " + user);
		//设置地区code
		user.setArea(new Area(CookieHelper.getLocalArea(request)));
			
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
			return "redirect:/index";
		} else {
			CookieHelper.setCookie(response, Constants.USERID, String.valueOf(user.getId()));
			CookieHelper.setCookie(response, Constants.USERPASSWORD,user.getPassWord());
			CookieHelper.setCookie(response, Constants.USERNAME,user.getLoginName());
			CookieHelper.setCookie(response, Constants.USERIDENTITY,user.getIdentity());
			CookieHelper.setCookie(response, Constants.USERAUTHORITY,String.valueOf(user.getAuthority()));
			CookieHelper.setCookie(response, Constants.USERREGISTERTIME,KitService.dateForShow(user.getCreateDate()));
			return "redirect:/user/goCenter";
		}
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
	public String login(User user, HttpServletRequest request,HttpServletResponse response, RedirectAttributes ra) {
		if (user == null) {
			ra.addFlashAttribute("messageType", "0");
			ra.addFlashAttribute("message", "登录错误");
			return "redirect:/index";
		}
//		// 判断验证码是否正确
//		String codeStr = request.getParameter("LoginVerifyCode");
//		String captchaId = request.getSession().getId();
//		System.out.println("codeStr : " + codeStr + "  captchaId : "
//				+ captchaId);
//		try {
//			Boolean isResponseCorrect = CaptchaServiceSingleton.getInstance()
//					.validateResponseForID(captchaId, codeStr);
//			if (!isResponseCorrect) {
//				ra.addFlashAttribute("messageType", "0");
//				ra.addFlashAttribute("message", "验证码错误");
//				return "redirect:/index";
//			}
//		} catch (CaptchaServiceException e) {
//			log.error("error in check", e);
//		}
		log.debug("user : " + user);
		user = userService.check(user);
		if (user == null) {
			ra.addFlashAttribute("messageType", "0");
			ra.addFlashAttribute("message", "用户名或密码错误!");
			return "redirect:/index";
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
//		//地区代码设为永久,如果当前cookie中不存在地区code, 才使用当前用户的
//		String acode = CookieHelper.getCookieValue(request, Constants.AREA);
//		if(acode == null || "".equals(acode)){
//			CookieHelper.setCookie(response, Constants.AREA,user.getArea().getCode(),Integer.MAX_VALUE);
//		}
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

	//提供给跨域登陆的方法
	@RequestMapping(value="/crossDomainLogin", method=RequestMethod.GET)
	@ResponseBody
	public JSONPObject crossDomainLogin(@RequestParam(value="callback") String callback,HttpServletRequest request){
		log.info("--- crossDomainLogin ---");
		ModelMap map = new ModelMap();
		//接收从网站群接收来的用户名, 密码
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if(username == null || "".equals(username) || password == null || "".equals(password)){
			map.put(Constants.NOTICE, "用户名或者密码为空.");
			return new JSONPObject(callback, map);
		}
		User user= new User();
		user.setLoginName(username);
		user.setPassWord(password);
		//查看有木有该用户
		User resultUser = userService.check(user);
		//存在该用户, 则将用户名和密码
		if(resultUser != null){
			map.put(Constants.NOTICE, Constants.Notice.SUCCESS);
		}else{
			map.put(Constants.NOTICE, "用户名或者密码错误.");
		}
		return new JSONPObject(callback, map);
	}
}
