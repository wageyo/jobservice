package esd.controller.manage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import esd.bean.Parameter;
import esd.bean.WhiteList;
import esd.controller.Constants;
import esd.service.KitService;
import esd.service.ParameterService;
import esd.service.WhiteListService;

/**
 * 短信发送  后台管理控制器
 * @author yufu
 * @email ilxly01@126.com
 * 2015-3-20
 */
@Controller
@RequestMapping("/manage/sms")
public class SMSManageController {
	private static Logger log = Logger
			.getLogger(SMSManageController.class);

	@Autowired
	private WhiteListService whiteListService;

	@Autowired
	private ParameterService parameterService;
	
	/*
	 * 转到 白名单列表 页面
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView white_list(HttpServletRequest request) {
		log.debug("goto：短信发送页面");
		Map<String, Object> entity = new HashMap<>();

		return new ModelAndView("manage/sms", entity);
	}

	// 转到 增加白名单 页面
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView white_add_get(HttpServletRequest request) {
		log.debug("goto：增加白名单");
		return new ModelAndView("manage/white-add");
	}

	// 提交 增加白名单
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> white_add_post(WhiteList params,
			HttpServletRequest request) {
		log.debug(" 增加白名单" + params);
		Map<String, Object> result = new HashMap<String, Object>();
		boolean bl = whiteListService.save(params);
		if (bl) {
			result.put(Constants.NOTICE, Constants.Notice.SUCCESS.getValue());
		} else {
			result.put(Constants.NOTICE, Constants.Notice.FAILURE.getValue());
		}
		return result;
	}

}
