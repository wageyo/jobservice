package esd.controller.manage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import esd.bean.Parameter;
import esd.bean.User;
import esd.controller.Constants;
import esd.service.CookieHelper;
import esd.service.KitService;
import esd.service.ParameterService;
import esd.service.UserService;

/**
 * 审核开关 管理控制器
 * 
 * @author yufu
 * @email ilxly01@126.com 2014-11-6
 */
@Controller
@RequestMapping("/manage/setup")
public class SetupManageController {
	private static Logger log = Logger.getLogger(SetupManageController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ParameterService parameterService;

	// 跳转到 系统设置 页面
	@RequestMapping(value = "/goto_setup", method = RequestMethod.GET)
	public ModelAndView gotosetup(HttpServletRequest request,
			HttpServletResponse response) {
		log.error("goto 系统设置");
		// 获取地区码
		String userId = CookieHelper.getCookieValue(request, Constants.ADMINUSERID);
		if(userId == null || "".equals(userId)){
			return new ModelAndView("redirect:/loginManage/login");
		}
		Integer uid = Integer.parseInt(userId);
		User userObj = userService.getById(uid);
		String code = userObj.getArea().getCode();
		if (code == null || "".equals(code)) {
			log.error("获取开关状态失败，地区无效");
			return null;
		}
		List<Parameter> resultList = parameterService.getSwitchByArea(code);
		if (resultList.size() <= 0) {
			log.error("获取 开关状态数据为空。" + "     user:" + userObj);
			return null;
		}
		Map<String, Object> entity = new HashMap<>();
		try {
			List<Map<String, Object>> list = new ArrayList<>();
			for (Parameter tmp : resultList) {
				Map<String, Object> tempMap = new HashMap<>();
				tempMap.put("id", tmp.getId());// id
				tempMap.put("switchValue", tmp.getValue()); // 开始值: on or off
				tempMap.put("switchName",
						KitService.getSwitchName(tmp.getName())); // 开关名称
				list.add(tempMap);
			}
			entity.put("entityList", list);
			log.debug("获取 开关 信息，size():" + list.size());
		} catch (Exception e) {
			log.error("获取开关 时发生错误。");
			e.printStackTrace();
		}
		//设定地区
		//获取  可以  信息共享范围列表
		List<Parameter> pList = parameterService.getByTypeAndArea(code);
		entity.put(Constants.SHARE_SCOPE_SWITCH, pList);
		//获取本地区信息共享范围
		Parameter shareScope = parameterService.getShareScopeByArea(code);
		//如果暂未设定, 则在前台进行提示
		if(shareScope == null){
			shareScope = new Parameter();
			String id = UUID.randomUUID().toString();
			shareScope.setId(id);
			shareScope.setType(Constants.SHARE_SCOPE);
			//初始化为本站内
			shareScope.setName("本站内");
			shareScope.setValue(Constants.SHARE_SCOPE_INIT);
			shareScope.setArea(new Area(code));
			shareScope.setMark(code + "地区  信息共享范围");
			Boolean bl = parameterService.save(shareScope);
			if(!bl){
				entity.put(Constants.NOTICE, "初始化数据失败, 请重新尝试或者联系管理员");
			}
		}
		entity.put(Constants.SHARE_SCOPE, shareScope);
		//获得匹配条数
		List<Parameter> matchedParameterList = parameterService.getByType(Constants.MATCHED_SHOW);
		entity.put(Constants.MATCHED_SHOW, matchedParameterList);
		//检查有没有该地区的匹配显示条数和推送条数, 如果没有的话则创建对应的数据
		Parameter tmpMatchedShowNumber = parameterService.getOnebyTypeAndAcode(Constants.MATCHED_SHOW_NUMBER,code);
		if(tmpMatchedShowNumber == null){
			//显示数量 
			Parameter matchedShowNumber = new Parameter();
			String id = UUID.randomUUID().toString();
			matchedShowNumber.setArea(new Area(code));
			matchedShowNumber.setId(id);
			matchedShowNumber.setType(Constants.MATCHED_SHOW_NUMBER);
			matchedShowNumber.setValue(String.valueOf(Constants.MATCHED_NUMBER_DEFAULT));
			matchedShowNumber.setMark(code + "地区信息匹配显示数量");
			matchedShowNumber.setName("5条");
			parameterService.save(matchedShowNumber);
			//短信推送数量
			Parameter matchedSendNumber = new Parameter();
			id = UUID.randomUUID().toString();
			matchedSendNumber.setArea(new Area(code));
			matchedSendNumber.setId(id);
			matchedSendNumber.setType(Constants.MATCHED_SEND_NUMBER);
			matchedSendNumber.setValue(String.valueOf(Constants.MATCHED_NUMBER_DEFAULT));
			matchedSendNumber.setMark(code + "地区信息匹配推送数量");
			matchedSendNumber.setName("5条");
			parameterService.save(matchedSendNumber);
		}
		Parameter matchedShowNumber = parameterService.getOnebyTypeAndAcode(Constants.MATCHED_SHOW_NUMBER,code);
		Parameter matchedSendNumber= parameterService.getOnebyTypeAndAcode(Constants.MATCHED_SEND_NUMBER,code);
		entity.put(Constants.MATCHED_SHOW_NUMBER, matchedShowNumber);
		entity.put(Constants.MATCHED_SEND_NUMBER, matchedSendNumber);
		
		//获得 残疾证号是否远程校验 开关
		Parameter disabledCheckSwitch = parameterService.getOnebyTypeAndAcode(Constants.DISABLEDCHECKSWITCH, code);
		entity.put(Constants.DISABLEDCHECKSWITCH, disabledCheckSwitch);
		
		return new ModelAndView("manage/setup", entity);
	}

	// 更新 开关状态
	@RequestMapping(value = "/update_switch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> setstatus(HttpServletRequest request,
			HttpServletResponse response) {
		String switchid = request.getParameter("switchid");
		String switchStatus = request.getParameter("switchStatus");
		Map<String, Object> entity = new HashMap<String, Object>();
		if (switchid == null || "".equals(switchid) || switchStatus == null
				|| "".equals(switchStatus)) {
			entity.put(Constants.NOTICE, "传递的参数有误, 请重新尝试或者联系管理员.");
			return entity;
		}
		Parameter pa = new Parameter();
		pa.setId(switchid);
		pa.setValue(switchStatus);
		// 更新
		Boolean bl = parameterService.updateParameter(switchid, switchStatus);
		if (bl) {
			entity.put(Constants.NOTICE, Constants.Notice.SUCCESS.getValue());
		} else {
			entity.put(Constants.NOTICE, "更新开关发生错误, 请重新尝试或者联系管理员.");
		}
		return entity;
	}
	
	// 更新 本地区信息共享范围
	@RequestMapping(value = "/update_share_scope", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> setShare(HttpServletRequest request,
			HttpServletResponse response) {
		String id = request.getParameter("id");
		String shareScopeValue = request.getParameter("shareScopeValue");
		String shareScopeName = request.getParameter("shareScopeName");
		Map<String, Object> entity = new HashMap<String, Object>();
		if (id == null || "".equals(id) || shareScopeValue == null
				|| "".equals(shareScopeValue)) {
			entity.put(Constants.NOTICE, "传递的参数有误, 请重新尝试或者联系管理员.");
			return entity;
		}
		Parameter parameter = new Parameter();
		parameter.setId(id);
		parameter.setName(shareScopeName);
		parameter.setValue(shareScopeValue);
		// 更新
		Boolean bl = parameterService.update(parameter);
		if (bl) {
			entity.put(Constants.NOTICE, Constants.Notice.SUCCESS.getValue());
		} else {
			entity.put(Constants.NOTICE, "更新信息共享范围发生错误, 请重新尝试或者联系管理员.");
		}
		return entity;
	}

	// 更新 推送信息数量
	@RequestMapping(value = "/update_matched", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatched(HttpServletRequest request,
			HttpServletResponse response) {
		String id = request.getParameter("id");
		String value = request.getParameter("matchedValue");
		String name = request.getParameter("matchedName");
		Map<String, Object> entity = new HashMap<String, Object>();
		if (id == null || "".equals(id) || value == null
				|| "".equals(value) || name == null
						|| "".equals(name)) {
			entity.put(Constants.NOTICE, "传递的参数有误, 请重新尝试或者联系管理员.");
			return entity;
		}
		Parameter parameter = new Parameter();
		parameter.setId(id);
		parameter.setValue(value);
		parameter.setName(name);
		// 更新
		Boolean bl = parameterService.update(parameter);
		if (bl) {
			entity.put(Constants.NOTICE, Constants.Notice.SUCCESS.getValue());
		} else {
			entity.put(Constants.NOTICE, "更新推送信息数量发生问题, 请重新尝试或者联系管理员.");
		}
		return entity;
	}
}
