package esd.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import esd.bean.Area;
import esd.bean.JobCategory;
import esd.bean.Menu;
import esd.bean.Parameter;
import esd.bean.User;
import esd.controller.Constants.Notice;
import esd.service.AreaService;
import esd.service.JobCategoryService;
import esd.service.MenuService;
import esd.service.ParameterService;
import esd.service.UserService;

@Controller
@RequestMapping("/widget")
public class WidgetController {

	private static Logger log = Logger.getLogger(WidgetController.class);
	@Autowired
	private JobCategoryService jcService;

	@Autowired
	private AreaService areaService;

	@Autowired
	private ParameterService pService;

	@Autowired
	private MenuService menuService;
	
	@Autowired
	private UserService userService;

	// 获得所有职位种类列表
	@RequestMapping("/getJobCategory")
	@ResponseBody
	public Map<String, Object> getJobCagetegoryList() {
		log.info("--- getJobCategory ---");
		Map<String, Object> json = new HashMap<String, Object>();
		List<JobCategory> jcList = jcService.getAll();
		if (jcList == null || jcList.size() == 0) {
			json.put("notice", Notice.FAILURE.toString());
			return json;
		}
		json.put("notice", Notice.SUCCESS.toString());
		json.put("jobCategoryList", jcList);
		return json;
	}

	// 根据传进来的菜单code获得对象下级的列表
	@RequestMapping(value = "/getSubJc", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getSubJc(HttpServletRequest req) {
		log.info("--- getSubJc ---");
		String code = req.getParameter("code");
		if (code == null) {
			return null;
		}
		Map<String, Object> json = new HashMap<String, Object>();
		List<JobCategory> list = jcService.getSubJc(code);
		json.put("jcList", list);
		return json;
	}

	// 获得最受欢迎职位种类列表
	@RequestMapping("/getPopularJobCategory")
	@ResponseBody
	public Map<String, Object> getPopularJobCategory() {
		log.info("--- getJobCategory ---");
		Map<String, Object> json = new HashMap<String, Object>();
		List<JobCategory> jcList = jcService.getPopularJobCategory(Constants.JOB_CATEGORY_HOT);
		if (jcList == null || jcList.size() == 0) {
			json.put("notice", Notice.FAILURE.toString());
			return json;
		}
		json.put("notice", Notice.SUCCESS.toString());
		json.put("jobCategoryList", jcList);
		return json;
	}

	// 获得所有地区列表
	@RequestMapping("/getArea")
	@ResponseBody
	public Map<String, Object> getAreaList() {
		log.info("--- getArea ---");
		Map<String, Object> json = new HashMap<String, Object>();
		List<Area> list = areaService.getAll();
		if (list == null || list.size() == 0) {
			json.put("notice", Notice.FAILURE.toString());
			return json;
		}
		json.put("notice", Notice.SUCCESS.toString());
		json.put("areaList", list);
		return json;
	}

	// 根据传进来的省,市级code获得对象下级的市,县列表
	@RequestMapping(value = "/getSubArea", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getSubArea(HttpServletRequest req) {
		log.info("--- getSubArea ---");
		String code = req.getParameter("code");
		if (code == null) {
			return null;
		}
		Map<String, Object> json = new HashMap<String, Object>();
		List<Area> list = areaService.getSubArea(code);
		if (list == null) {
			json.put("notice", Notice.FAILURE.toString());
		} else {
			json.put("areaList", list);
		}
		return json;
	}

	// 测试用controller
	@RequestMapping(value = "/1", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> test1() {
		log.info("--- getSubArea ---");
		Map<String, Object> json = new HashMap<String, Object>();
		List<Menu> list = menuService.getByAuthority(Constants.Authority.ADMIN
				.getValue());
		json.put("menuList", list);
		return json;
	}

	// 测试用controller
	@RequestMapping(value = "/2", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> test2() {
		log.info("--- test2 ---");
		Map<String, Object> json = new HashMap<String, Object>();
		List<Parameter> list = pService.getAll();
		json.put("pList", list);
		return json;
	}

	// 测试用controller
		@RequestMapping(value = "/3", method = RequestMethod.GET)
		@ResponseBody
		public Map<String, Object> test3() {
			log.info("--- test2 ---");
			Map<String, Object> json = new HashMap<String, Object>();
//			Parameter p = new Parameter();
//			p.setId("01gdfgdfga091283");
//			p.setArea(new Area("10230000"));
//			p.setName("slkd");
//			p.setType("1111");
//			boolean bl1 = pService.save(p);
//			p.setId("01s0hgjghj1283");
//			boolean bl2 = pService.save(p);
//			p.setId("0512436ertert133");
//			boolean bl3 = pService.save(p);
//			json.put("p", p);
//			json.put("bl1", bl1);
//			json.put("bl2", bl2);
//			json.put("bl3", bl3);
			User user = new User();
			user.setLoginName("98s89ds7f");
			user.setPassWord("s980s9");
			user.setArea(new Area("10230000"));
			user.setIdentity("person");
			user.setAuthority(100);
			boolean bl = userService.save(user);
			json.put("bl", bl);
			json.put(Constants.USER, user);
			return json;
		}
		
		@RequestMapping("/4")
		@ResponseBody
		public Map<String, Object> getJobCagetegoryList1(HttpServletRequest request) {
			Map<String, Object> json = new HashMap<String, Object>();
			json.put("addr", request.getRealPath("/"));
			return json;
		}
}
