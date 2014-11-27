package esd.controller.manage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
	private UserService<User> userService;
	
	@Autowired
	private ParameterService parameterService;

	// 跳转到 系统设置 页面
	@RequestMapping(value = "/goto_setup", method = RequestMethod.GET)
	public ModelAndView gotosetup(HttpServletRequest request,
			HttpServletResponse response) {
		log.error("goto 系统设置");
		// 获取地区码
		String userId = CookieHelper.getCookieValue(request, Constants.USERID);
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

}
