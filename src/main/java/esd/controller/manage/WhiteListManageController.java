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
 * 白名单管理 后台管理控制器
 * 
 * @author yufu
 * @email ilxly01@126.com 2015-1-23
 */
@Controller
@RequestMapping("/manage/white")
public class WhiteListManageController {
	private static Logger log = Logger
			.getLogger(WhiteListManageController.class);

	@Autowired
	private WhiteListService whiteListService;

	@Autowired
	private ParameterService parameterService;
	
	/*
	 * 转到 白名单列表 页面
	 */
	@RequestMapping(value = "/white_list", method = RequestMethod.GET)
	public ModelAndView white_list(HttpServletRequest request) {
		log.debug("goto：白名单管理");
		Map<String, Object> entity = new HashMap<>();
		String pageStr = request.getParameter("page");
		Integer page = KitService.getInt(pageStr) > 0 ? KitService
				.getInt(pageStr) : 1;
		Integer rows = Constants.SIZE;
		// 未审核职位状态信息
		WhiteList paramEntity = new WhiteList();
		// 名称模糊查询
		String targetName = request.getParameter("targetName");
		paramEntity.setTitle(targetName);

		List<WhiteList> resultList = whiteListService.getByPage(paramEntity,
				page, rows);
		Integer total = whiteListService.getTotalCount(paramEntity); // 数据总条数
		try {

			List<Map<String, Object>> list = new ArrayList<>();
			for (WhiteList tmp : resultList) {

				Map<String, Object> tempMap = new HashMap<>();
				tempMap.put("id", tmp.getId());// id
				tempMap.put("title", tmp.getTitle());// 域名名称
				tempMap.put("domainName", tmp.getDomainName());// 域名
				tempMap.put("ip", tmp.getIp());// ip
				tempMap.put("createDate", tmp.getCreateDate());// 加入时间
				list.add(tempMap);
			}
			entity.put("total", total);
			entity.put("entityList", list);
			log.debug("获取白名单信息，size():" + total);
		} catch (Exception e) {
			log.error("获取 白名单信息时发生错误。");
		}
		// 放入当前页数, 总页数, 职位名, 审核状态
		entity.put("currentPage", page);
		entity.put("totalPage", KitService.getTotalPage(total));
		entity.put("targetName", targetName);
		//白名单功能是否打开开关
		Parameter parameter = parameterService.getById(Constants.WHITE_LIST_SWITCH);
		entity.put("whiteListSwitch", parameter);
		log.debug("转到 白名单列表 页面");
		return new ModelAndView("manage/white-list", entity);
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

	// 删除白名单
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delete_white(
			@PathVariable(value = "id") String id, HttpServletRequest request) {
		Map<String, Object> entity = new HashMap<String, Object>();
		boolean bl = whiteListService.delete(id);
		if (bl) {
			entity.put(Constants.NOTICE, Constants.Notice.SUCCESS.getValue());
		} else {
			entity.put(Constants.NOTICE, "删除白名单出错, 请联系白名单.");
		}
		return entity;
	}

	// 转到 白名单编辑 页面
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView white_edit_get(@PathVariable(value = "id") String id,
			HttpServletRequest request) {
		log.debug("goto：编辑白名单 ");
		Map<String, Object> entity = new HashMap<String, Object>();
		// 根据id查询对应的数据
		WhiteList obj = whiteListService.getById(id);
		entity.put("obj", obj);
		return new ModelAndView("manage/white-edit", entity);
	}

	// 提交白名单编辑
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> white_edit_post(WhiteList params,
			HttpServletRequest request) {
		log.debug("   更新白名单" + params);
		Map<String, Object> entity = new HashMap<String, Object>();
		boolean bl = whiteListService.update(params);
		if (bl) {
			entity.put(Constants.NOTICE, Constants.Notice.SUCCESS.getValue());
		} else {
			entity.put(Constants.NOTICE, "更新白名单出错, 请联系白名单.");
		}
		return entity;
	}

	// 更新白名单开关值
	@RequestMapping(value="/update_switch")
	@ResponseBody
	public Map<String,Object> updateWhiteListSwitch(HttpServletRequest request){
		Map<String,Object> map = new HashMap<String,Object>();
		String value = request.getParameter("value");
		Parameter parameter = new Parameter();
		parameter.setId(Constants.WHITE_LIST_SWITCH);
		parameter.setValue(value);
		Boolean bl = parameterService.update(parameter);
		if(bl){
			map.put(Constants.NOTICE, Constants.Notice.SUCCESS.getValue());
		}else{
			map.put(Constants.NOTICE, Constants.Notice.FAILURE.getValue());
		}
		return map;
	}
}
