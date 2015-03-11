package esd.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import esd.common.disability.CheckCardService;
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
	
	@Autowired
	private CheckCardService checkCardService;

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
			user = userService.getById(user.getId());
			CookieHelper.setCookie(response, request, user, null);
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
	public String login(User userIndex, HttpServletRequest request,HttpServletResponse response, RedirectAttributes ra) {
		String refererUrl = request.getHeader("referer");
		String loginName = userIndex.getLoginName();
	    String passWord = userIndex.getPassWord();
		if (loginName == null) {
			ra.addFlashAttribute("messageType", "0");
			ra.addFlashAttribute("message", "用户名为空");
			return "redirect:"+refererUrl;
		}
		// 判断验证码是否正确
		log.debug("user : " + userIndex);
		User userSql=new User();
		userSql.setLoginName(loginName);
		User user=new User();
		user = userService.check(userSql);
		//判断是否存在该用户
		if (user == null) {
			String check = "(([0-9]{17}([0-9]|X))|([0-9]{15}))[1-7]{1}[1-4]{1}";
			Pattern regex = Pattern.compile(check);  
			Matcher matcher = regex.matcher(loginName);  
			boolean isMatched = matcher.matches();  
			//判断是否符合残疾号登入
			if(isMatched){
				CookieHelper.setCookie(response, Constants.USERNAME,userSql.getLoginName());
				request.setAttribute(Constants.USERNAME, userSql.getLoginName());
				return "person/password-set";
			}else {
				//不符合残疾号登入提示用户名错误
				ra.addFlashAttribute("messageType", "0");
				ra.addFlashAttribute("message", "您的用户名不为残疾证号或者密码错误!");
				return "redirect:"+refererUrl;
			}
		}else{
			//如果有该用户判断是否密码正确
			if(!user.getIdentity().equals(userIndex.getIdentity())){
				ra.addFlashAttribute("messageType", "0");
				ra.addFlashAttribute("message", "用户名或密码错误!");
				return "redirect:"+refererUrl;
			}
			//如果有该用户判断是否密码正确
			if(!user.getPassWord().equals(passWord)){
				ra.addFlashAttribute("messageType", "0");
				ra.addFlashAttribute("message", "密码错误!");
				return "redirect:"+refererUrl;
			}
		}
		if (user.getCheckStatus().equals(
				Constants.CheckStatus.DAISHEN.toString())) {
			ra.addFlashAttribute("messageType", "0");
			ra.addFlashAttribute("message", "用户正在审核中, 请稍后登陆!");
			return "redirect:"+refererUrl;
		} else if (user.getCheckStatus().equals(
				Constants.CheckStatus.WEITONGGUO.toString())) {
			ra.addFlashAttribute("messageType", "0");
			ra.addFlashAttribute("message", "用户名没有通过审核, 请重新申请!");
			return "redirect:"+refererUrl;
		}
		//如果输入的密码为空, 则跳转到前台进行提示
		if(passWord == null || "".equals(passWord)){
		 ra.addFlashAttribute("messageType", "0");
		 ra.addFlashAttribute("message", "残疾证号已经被注册过, 请输入密码!");
			return "redirect:"+refererUrl;
		}
		if (user.getIdentity().equals(Identity.COMPANY.toString())) {
			Company company = companyService.getByAccount(user.getId());
			log.debug("company " + company);
			if(company != null){
				CookieHelper.setCookie(response, Constants.USERCOMPANYID, String.valueOf(company.getId()));
			}
		}
		log.debug("login: " + user);
    	CookieHelper.setCookie(response, request, user, null);
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

	// 检查一个邮箱是否存在
	@RequestMapping(value = "/checkEmail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkEmail(HttpServletRequest request) {
		log.info("--- check ---");
		String email = request.getParameter("email");
		log.info("--- email ---" + email);
		Map<String, Object> json = new HashMap<String, Object>();
		if (email == null || "".equals(email)) {
			json.put("notice", Notice.ERROR);
			return json;
		}
		User param = new User();
		param.setEmail(email);
		User user = userService.check(param);
		if (user != null) {
			// 存在
			json.put("notice", Notice.SUCCESS.getValue());
			return json;
		}
		// 该用户不存在
		json.put("notice", Notice.FAILURE.getValue());
		return json;
	}
	
	// 登出
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		log.info("--- 登出 ---");
		//杀死除地区code外的所有cookie
		CookieHelper.killUserCookie(response, false);
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
		User user = userService.getById(Integer.parseInt(userId));
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
		request.setAttribute("user", user);
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
	
	// 设置密码
	@RequestMapping(value = "/setPassWord", method = RequestMethod.GET)
	public String setPassWordGet(HttpServletRequest request) {
		String 	userNamesString= CookieHelper.getCookieValue(request, Constants.USERNAME);
		request.setAttribute(Constants.USERNAME, userNamesString);
		return "person/password-set";
	}
	
	// 设置密码
	@RequestMapping(value = "/setPassWord", method = RequestMethod.POST)
	public String setPassWordPost(HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes ra) {
		log.info("--- passWordEdit post---");
		String 	userNamesString= CookieHelper.getCookieValue(request, Constants.USERNAME);
		String passWord = request.getParameter("newPassWord");
	    User user = new User();
		user.setLoginName(userNamesString);
		user.setPassWord(passWord);
		user.setIdentity(Constants.Identity.PERSON.getValue());
		user.setAuthority(Constants.Authority.PERSON.getValue());
		String acode = CookieHelper.getCookieValue(request, Constants.AREACODE);
		user.setArea(new Area(acode));
		//保存用户
		Boolean bl = userService.save(user);
		log.info("update password bl = " + bl);
		if (!bl) {
			ra.addFlashAttribute("messageType", "0");
			ra.addFlashAttribute("message", "设置密码失败, 请联系管理员.");
			return "redirect:/secure/user/setPassWord";
		}
		// //将基本属性设置到cookie中
		User userSql=new User();
		userSql.setLoginName(userNamesString);
		User userCookie=new User();
		userCookie = userService.check(userSql);
		CookieHelper.setCookie(response, request, userCookie, null);
		ra.addFlashAttribute("messageType", "1");
		ra.addFlashAttribute("message", "操作成功!");
		return "redirect:/user/goCenter";
	}

	// 进入初始化密码页面
	@RequestMapping(value = "/setInitPassWord", method = RequestMethod.GET)
	public String setInitPassWord(HttpServletRequest request) {
		String tempUserName = request.getParameter("tempUserName");
		request.setAttribute("tempUserName", tempUserName);
		return "person/init-password-set";
	}

	// 提交初始化密码页面
	@RequestMapping(value = "/setInitPassWord", method = RequestMethod.POST)
	public String setInitPassWordPost(User user,HttpServletRequest request,HttpServletResponse response,RedirectAttributes redirectAttributes) {
		//设置地区code
		user.setArea(new Area(CookieHelper.getLocalArea(request)));
		// 设置个人用户身份和权限值
		user.setIdentity(Constants.Identity.PERSON.getValue());
		user.setAuthority(Constants.Authority.PERSON.getValue());

		//如果没有保存成功, 则将该对象原样返回前台, 同时给出提示文字
		request.setAttribute("user", user);

		//远程到中残联网站校验 姓名和残疾证号 
		Boolean bl = checkCardService.check(user.getNickName(), user.getLoginName());
		if(!bl){
			request.setAttribute("messageType", "0");
			request.setAttribute("message", "您的姓名和残疾证号不一致, 请检查后重新输入.");
			return "person/init-password-set";
		}
		
		Boolean bl2 = userService.save(user);
		if(bl2){
			CookieHelper.setCookie(response, request, user, null);
			return "redirect:/user/goCenter";
		}
		redirectAttributes.addFlashAttribute("messageType", "0");
		redirectAttributes.addFlashAttribute("message", "注册请求已提交, 请等待管理员审核");
		return "person/init-password-set";
	}
}
