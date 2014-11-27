package esd.controller.manage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import esd.bean.Menu;
import esd.service.MenuService;

@Controller
@RequestMapping("/manage")
public class ManageController {
	private static Logger log = Logger.getLogger(ManageController.class);

	@Autowired
	private MenuService menuService;
	
	// 转到管理 主页
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(HttpServletRequest request) {
		log.debug("goto：管理后台  首页");
		return "/manage/index";
	}
	
	//根据权限值获得菜单列表
	@RequestMapping(value="/getMenu/{authority}",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getMenuList(@PathVariable(value="authority") Integer authority, HttpServletRequest request){
		log.debug("----获取 菜单----");
		Map<String,Object> entity = new HashMap<String,Object>();
		List<Menu> list = menuService.getByAuthority(authority);
		entity.put("menuList", list);
		return entity;
	}

}
