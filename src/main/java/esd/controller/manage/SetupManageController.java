package esd.controller.manage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import esd.bean.Area;
import esd.bean.Menu;
import esd.bean.Parameter;
import esd.bean.User;
import esd.controller.Constants;
import esd.service.AreaService;
import esd.service.KitService;
import esd.service.MenuService;
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
	private MenuService menuService;

	@Autowired
	private ParameterService parameterService;

	@Autowired
	private UserService userService;

	@Autowired
	private AreaService areaService;

	// 跳转到 系统设置 页面
	@RequestMapping(value = "/goto_setup", method = RequestMethod.GET)
	public ModelAndView gotosetup(HttpServletRequest request,
			HttpSession session) {
		log.error("goto 系统设置");
		// 获取地区码
		User userObj = (User) session.getAttribute(Constants.USER);
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
			HttpSession session) {
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

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	 * 转到 增加管理员 页面
	 */
	@RequestMapping(value = "/adminUser_add", method = RequestMethod.GET)
	public ModelAndView gotoSuper(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("manage/super-add");
		List<Area> alist = areaService.getProvinceList();
		mav.addObject("provinceList", alist);

		log.debug("goto：增加管理员页面");
		return mav;
	}

	/*
	 * 转到 编辑管理员 页面
	 */
	@RequestMapping(value = "/adminUser_edit/{id}", method = RequestMethod.GET)
	public ModelAndView adminUser_edit(@PathVariable(value = "id") Integer id,
			HttpServletRequest request) {
		// 续传id
		request.setAttribute("id", id);
		// 得到此用户
		User user = userService.getById(id);
		log.info("user" + user);
		ModelAndView mav = new ModelAndView("manage/super-edit");
		// 当前用户所属地区code
		String curCode = user.getArea().getCode();
		mav.addObject("curCode", curCode);
		List<Area> alist = areaService.getProvinceList();
		// 获得省份列表
		mav.addObject("provinceList", alist);
		// 获得市列表
		if (curCode.startsWith("20") || curCode.startsWith("30")) {
			alist = areaService.getCityListByCode(curCode);
			mav.addObject("cityList", alist);
		}
		// 获得县区列表
		if (curCode.startsWith("30")) {
			alist = areaService.getDistrictListByCode(curCode);
			mav.addObject("districtList", alist);
		}
		log.debug("goto：编辑管理员页面");
		return mav;
	}

	/*
	 * 增加管理员用户
	 */
	@RequestMapping(value = "/adminUser_add", method = RequestMethod.POST)
	@ResponseBody
	public Boolean adminUser_add(User params, HttpServletRequest request) {

		try {
			if (params == null) {
				log.debug("增加管理员错误，参数传入失败。");
				return false;
			}
			params.setArea(new Area(params.getPhone()));
			boolean b = userService.saveAdmin(params);
			log.debug("增加管理员用户，执行结果：" + b + "     " + params);
			//

			return b;

		} catch (Exception e) {
			log.debug("增加管理员错误，参数传入失败。");
		}
		return false;
	}

	/*
	 * 编辑 管理员用户
	 */
	@RequestMapping(value = "/adminUser_edit", method = RequestMethod.POST)
	@ResponseBody
	public Boolean adminUser_edit(User params, HttpServletRequest request) {
		try {
			if (params == null) {
				log.debug(" 编辑管理员错误，参数传入失败。");
				return false;
			}
			params.setArea(new Area(params.getPhone()));
			boolean b = userService.update(params);
			log.debug(" 编辑管理员用户，执行结果：" + b + "     " + params);
			return b;
		} catch (Exception e) {
			log.debug(" 编辑管理员错误，参数传入失败。");
		}
		return false;

	}

	/*
	 * 根据ID获取管理员用户 续传ID
	 */
	@RequestMapping(value = "/getadminUser", method = RequestMethod.POST)
	@ResponseBody
	public Object news_view(@RequestParam(value = "id") Integer id,
			HttpServletRequest request) {
		try {
			log.debug(" 返回一个管理员用户" + id);
			User user = userService.getById(id);
			log.debug(" 查看管理员用户" + user);
			return user;
		} catch (Exception e) {
			log.error("查看管理员用户错误");
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * 获取 管理员列表数据
	 */
	@RequestMapping(value = "/super_list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> superList(HttpServletRequest request) {
		log.debug("进入 获取管理员数据控制器");
		Map<String, Object> entity = new HashMap<>();
		try {
			Integer page = Integer.parseInt(request.getParameter("page"));
			Integer rows = Integer.parseInt(request.getParameter("rows"));
			User u = new User();

			u.setIdentity(Constants.Identity.ADMIN.toString());
			List<User> userList = userService.getByPage(u, page, rows);
			Integer total = userService.getTotalCount(u); // 数据总条数
			List<Map<String, Object>> list = new ArrayList<>();
			for (User user : userList) {
				Map<String, Object> jobmap = new HashMap<>();
				jobmap.put("id", user.getId());// id
				jobmap.put("loginname", user.getLoginName());// 账号
				jobmap.put("nick", user.getNickName());// 昵称
				jobmap.put("pwd", user.getPassWord());// 密码
				jobmap.put("title", user.getTitle());// 头部标题
				jobmap.put("area", user.getArea().getName());// 地区
				list.add(jobmap);
			}
			entity.put("total", total);
			entity.put("rows", list);
			log.debug("获取管理员列表数据信息，size():" + total);
		} catch (Exception e) {
			log.error("获取管理员列表数据信息时发生错误。");
			e.printStackTrace();
		}
		return entity;
	}

	/*
	 * 删除 管理员用户
	 */
	@RequestMapping(value = "/adminuser_del", method = RequestMethod.POST)
	@ResponseBody
	public boolean adminuser_del(
			@RequestParam(value = "params[]") Integer params[],
			HttpServletRequest request) {

		if (params == null || params.length <= 0) {

			log.error("dele adminUser error");
			return false;
		}
		// 删除单个
		if (params.length == 1) {
			log.debug("del news dange");
			return userService.delete(params[0]);
		}
		// 删除多个
		else {
			for (int i = 0; i < params.length; i++) {
				userService.delete(params[i]);
			}
			log.debug("del adminUser 多个");
			return true;
		}

	}

	/*
	 * 获取菜单控制器
	 */
	@RequestMapping(value = "/menu", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> jsonMenu(HttpServletRequest request,
			HttpSession session) {
		List<Menu> listMenu = null;
		// 获取地区码
		User userObj = (User) session.getAttribute(Constants.USER);

		log.debug("获取菜单");
		listMenu = menuService.getByAuthority(userObj.getAuthority());

		if (listMenu == null || listMenu.size() <= 0) {
			log.error("获取菜单失败,取出数据为空");
			return null;
		}
		List<Map<String, Object>> list = new ArrayList<>();
		for (int i = 0; i < listMenu.size(); i++) {
			Map<String, Object> m = new HashMap<>();
			m.put("id", listMenu.get(i).getId());
			m.put("text", listMenu.get(i).getText());
			m.put("state", listMenu.get(i).getState());
			m.put("url", listMenu.get(i).getUrl());
			m.put("checked", listMenu.get(i).getChecked());
			m.put("iconCls", listMenu.get(i).getIconCls());
			m.put("children", listMenu.get(i).getChildren());
			m.put("attributes", listMenu.get(i).getAttributes());
			list.add(m);
		}
		log.debug("获取菜单条数为：" + list.size() + "     user:" + userObj);
		return list;
	}

	/*
	 * 获取 开关状态
	 */
	@RequestMapping(value = "/status", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> menuChecked(HttpServletRequest request,
			HttpSession session) {
		// 获取地区码
		User userObj = (User) session.getAttribute(Constants.USER);
		String code = userObj.getArea().getCode();
		if (code == null || code.equals("")) {
			log.error("获取开关状态失败，地区无效");
			return null;
		}
		List<Parameter> listSet = parameterService.getSwitchByArea(code);
		if (listSet.size() <= 0) {
			log.error("获取 开关状态数据为空。" + "     user:" + userObj);
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		for (int i = 0; i < listSet.size(); i++) {
			map.put(listSet.get(i).getName(), listSet.get(i).getValue());
		}

		log.debug(" 获取 开关状态  ：" + map + "     user:" + userObj);
		return map;
	}

}
