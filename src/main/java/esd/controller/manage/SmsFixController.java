package esd.controller.manage;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import esd.bean.Area;
import esd.bean.SmsAccount;
import esd.bean.SmsFix;
import esd.bean.User;
import esd.controller.Constants;
import esd.service.CookieHelper;
import esd.service.SmsAccountService;
import esd.service.SmsFixService;
import esd.service.UserService;

/**
 * 推送信息上下文控制器
 * 
 * @author yufu
 * @email ilxly01@126.com 2015-5-5
 */
@Controller
@RequestMapping("/manage/prefix")
public class SmsFixController {
	private static Logger log = Logger.getLogger(SmsFixController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private SmsFixService smsFixService;

	@Autowired
	private SmsAccountService smsAccountService;
	
	// 转到 推送信息控制 页面
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView white_list(HttpServletRequest request) {
		log.debug("goto：推送信息控制");
		Map<String, Object> entity = new HashMap<>();
		// 获取地区码
		String userId = CookieHelper.getCookieValue(request,
				Constants.ADMINUSERID);
		if (userId == null || "".equals(userId)) {
			return new ModelAndView("redirect:/loginManage/login");
		}
		Integer uid = Integer.parseInt(userId);
		User userObj = userService.getById(uid);
		String acode = userObj.getArea().getCode();
		// 根据管理员的地区code, 获得本地区的推送信息上下文
		SmsFix paramEntity = smsFixService.getByArea(acode);
		if (paramEntity == null) {
			paramEntity = smsFixService.getByArea(Constants.AREACOUNTRY);
		}
		entity.put("smsfix", paramEntity);
		//根据管理员地区code, 获得本地区的名商通账号对象
		SmsAccount smsAccount = smsAccountService.getByArea(acode);
		if(smsAccount == null){
			smsAccount = new SmsAccount();
			smsAccount.setArea(new Area(Constants.AREACOUNTRY));
		}
		entity.put("smsAccount", smsAccount);
		return new ModelAndView("manage/prefix", entity);
	}

	// 更新短信推送上下文
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> update(SmsFix smsFix, HttpServletRequest request) {
		log.debug("goto：post  更新短信推送上下文");
		Map<String, Object> result = new HashMap<String, Object>();
		// 获取地区码
		String userId = CookieHelper.getCookieValue(request,
				Constants.ADMINUSERID);
		if (userId == null || "".equals(userId)) {
			result.put(Constants.NOTICE, "管理员未登录, 请登陆后重新尝试.");
			return result;
		}
		Integer uid = Integer.parseInt(userId);
		User userObj = userService.getById(uid);
		// 根据smsfix对象, 如果传递来的为全国code, 则新增该地区的数据; 不是 则更新该地区数据
		SmsFix entity = new SmsFix();
		entity.setLogUser(userObj.getNickName());
		entity.setPrefix(smsFix.getPrefix());
		entity.setSuffix(smsFix.getSuffix());
		entity.setArea(userObj.getArea());
		Boolean bl = Boolean.FALSE;
		if (Constants.AREACOUNTRY.equals(smsFix.getArea().getCode())) {
			bl = smsFixService.save(entity);
		} else {
			SmsFix sf = smsFixService.getByArea(smsFix.getArea().getCode());
			sf.setLogUser(userObj.getNickName());
			sf.setPrefix(smsFix.getPrefix());
			sf.setSuffix(smsFix.getSuffix());
			bl = smsFixService.update(sf);
		}
		if (bl) {
			result.put(Constants.NOTICE, Constants.Notice.SUCCESS.getValue());
		} else {
			result.put(Constants.NOTICE, Constants.Notice.FAILURE.getValue());
		}
		return result;
	}

	// 更新发送短信的名商通账号
	@RequestMapping(value = "/updatesmsaccount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatesmsaccount(SmsAccount smsAccount, HttpServletRequest request) {
		log.debug("goto：post  更新发送短信的名商通账号");
		Map<String, Object> result = new HashMap<String, Object>();
		// 获取地区码
		String userId = CookieHelper.getCookieValue(request,
				Constants.ADMINUSERID);
		if (userId == null || "".equals(userId)) {
			result.put(Constants.NOTICE, "管理员未登录, 请登陆后重新尝试.");
			return result;
		}
		Integer uid = Integer.parseInt(userId);
		User userObj = userService.getById(uid);
		// 根据smsAccount对象, 如果传递来的为全国code, 则新增该地区的数据; 不是 则更新该地区数据
		SmsAccount entity = new SmsAccount();
		entity.setLogUser(userObj.getNickName());
		entity.setUsername(smsAccount.getUsername());
		entity.setPassword(smsAccount.getPassword());
		entity.setArea(userObj.getArea());
		Boolean bl = Boolean.FALSE;
		if (Constants.AREACOUNTRY.equals(smsAccount.getArea().getCode())) {
			bl = smsAccountService.save(entity);
		} else {
			SmsAccount sa = smsAccountService.getByArea(smsAccount.getArea().getCode());
			sa.setLogUser(userObj.getNickName());
			sa.setUsername(smsAccount.getUsername());
			sa.setPassword(smsAccount.getPassword());
			bl = smsAccountService.update(sa);
		}
		if (bl) {
			result.put(Constants.NOTICE, Constants.Notice.SUCCESS.getValue());
		} else {
			result.put(Constants.NOTICE, Constants.Notice.FAILURE.getValue());
		}
		return result;
	}
	
}
