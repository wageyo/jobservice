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
import esd.bean.Company;
import esd.bean.Menu;
import esd.bean.News;
import esd.bean.Parameter;
import esd.bean.User;
import esd.controller.Constants;
import esd.service.AreaService;
import esd.service.KitService;
import esd.service.MenuService;
import esd.service.ParameterService;
import esd.service.UserService;

/**
 * 管理管理员账号 后台管理控制器
 * @author yufu
 * @email ilxly01@126.com
 * 2014-11-5
 */
@Controller
@RequestMapping("/manage/admin")
public class AdminManageController {
	private static Logger log = Logger.getLogger(AdminManageController.class);

	@Autowired
	private UserService<User> userService;
	
	@Autowired
	private AreaService areaService;


	/*
	 * 转到超级页面 管理员列表 页面
	 */
	@RequestMapping(value = "/admin_list", method = RequestMethod.GET)
	public ModelAndView admin_list(HttpServletRequest request) {
		log.debug("goto：管理员管理");
		Map<String, Object> entity = new HashMap<>();
		String pageStr = request.getParameter("page");
		Integer page = KitService.getInt(pageStr) > 0 ? KitService
				.getInt(pageStr) : 1;
		Integer rows = Constants.SIZE;
		// 未审核职位状态信息
		User paramEntity = new User();
		// 名称模糊查询
		String targetName = request.getParameter("targetName");
		paramEntity.setTitle(targetName);
		//查询管理员用户
		paramEntity.setIdentity(Constants.Identity.ADMIN.toString());
		
		// 获取审核状态查询条件
		String checkStatus = request.getParameter("checkStatus");
		// 判断显示类型
		if (checkStatus == null || "".equals(checkStatus)) {
			checkStatus = Constants.CheckStatus.OK.getValue();
		}
		paramEntity.setCheckStatus(checkStatus);
		List<User> resultList = userService.getByPage(
				paramEntity, page, rows);
		Integer total = userService.getTotalCount(paramEntity); // 数据总条数
		try {

			List<Map<String, Object>> list = new ArrayList<>();
			for (User tmp : resultList) {

				Map<String, Object> tempMap = new HashMap<>();
				tempMap.put("id", tmp.getId());// id
				tempMap.put("loginName", tmp.getLoginName());// 账号
				tempMap.put("passWord", tmp.getPassWord());// 密码
				tempMap.put("nickName", tmp.getNickName());// 昵称
				tempMap.put("title", tmp.getTitle());// 头部标题
				tempMap.put("area", tmp.getArea());// 所属地区
				list.add(tempMap);
			}
			entity.put("total", total);
			entity.put("entityList", list);
			log.debug("获取管理员信息，size():" + total );
		} catch (Exception e) {
			log.error("获取 管理员信息时发生错误。");
		}
		//放入当前页数, 总页数, 职位名, 审核状态
		entity.put("currentPage", page);
		entity.put("totalPage", KitService.getTotalPage(total));
		entity.put("targetName", targetName);
		entity.put("checkStatus", checkStatus);
		entity.put("checkStatusName", KitService.getCheckStatusName(checkStatus));
		log.debug("转到 管理员列表 页面");
		return new ModelAndView("manage/admin-list",entity);
	}
	
	// 转到 增加管理员 页面
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView admin_add_get(HttpServletRequest request) {
		log.debug("goto：增加管理员");
		Map<String, Object> entity = new HashMap<String, Object>();
		// 地区
		List<Area> alist = areaService.getProvinceList();
		entity.put("provinceList", alist);
		return new ModelAndView("manage/admin-add",entity);
	}

	// 提交 增加管理员
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> admin_add_post(User params, HttpServletRequest request) {
		log.debug(" 增加管理员" + params);
		Map<String, Object> result = new HashMap<String, Object>();
		boolean bl = userService.saveAdmin(params);
		if(bl){
			result.put(Constants.NOTICE, Constants.Notice.SUCCESS.getValue());
		}else{
			result.put(Constants.NOTICE, Constants.Notice.FAILURE.getValue());
		}
		return result;
	}

	//删除管理员
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> delete_admin(@PathVariable(value = "id") Integer id,
			HttpServletRequest request) {
		Map<String, Object> entity = new HashMap<String, Object>();
		boolean bl = userService.delete(id);
		if(bl){
			entity.put(Constants.NOTICE, Constants.Notice.SUCCESS.getValue());
		}else{
			entity.put(Constants.NOTICE, "删除管理员出错, 请联系管理员.");
		}
		return entity;
	}
	
	// 转到 管理员编辑 页面
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView admin_edit_get(@PathVariable(value = "id") int id,
			HttpServletRequest request) {
		log.debug("goto：编辑管理员 ");
		Map<String, Object> entity = new HashMap<String, Object>();
		// 根据id查询对应的数据
		User obj = userService.getById(id);
		entity.put("obj", obj);
		// 地区
		List<Area> alist = areaService.getProvinceList();
		entity.put("provinceList", alist);
		return new ModelAndView("manage/admin-edit",entity);
	}
	
	// 提交管理员编辑
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> admin_edit_post(User params, HttpServletRequest request) {
		log.debug("   更新管理员" + params);
		Map<String, Object> entity = new HashMap<String, Object>();
		boolean bl = userService.update(params);
		if(bl){
			entity.put(Constants.NOTICE, Constants.Notice.SUCCESS.getValue());
		}else{
			entity.put(Constants.NOTICE, "更新管理员出错, 请联系管理员.");
		}
		return entity;
	}
	
}
