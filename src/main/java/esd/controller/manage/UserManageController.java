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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import esd.bean.Area;
import esd.bean.Company;
import esd.bean.User;
import esd.controller.Constants;
import esd.service.AreaService;
import esd.service.CompanyService;
import esd.service.CookieHelper;
import esd.service.JobService;
import esd.service.KitService;
import esd.service.ResumeService;
import esd.service.UserService;

/**
 * 账号/用户 后台管理控制器
 * 
 * @author yufu
 * @email ilxly01@126.com 2014-11-5
 */
@Controller
@RequestMapping("/manage/user")
public class UserManageController {
	private static Logger log = Logger.getLogger(UserManageController.class);

	@Autowired
	private UserService<User> userService;

	@Autowired
	private ResumeService resumeService;

	@Autowired
	private CompanyService<Company> companyService;

	@Autowired
	private JobService jobService;

	@Autowired
	private AreaService areaService;

	// 转到普通用户账号管理列表页面
	@RequestMapping(value = "/user_list", method = RequestMethod.GET)
	public ModelAndView list_get(HttpServletRequest request) {
		log.debug("goto：账号/用户 后台管理 列表");
		Map<String, Object> entity = new HashMap<>();
		String pageStr = request.getParameter("page");
		Integer page = KitService.getInt(pageStr) > 0 ? KitService
				.getInt(pageStr) : 1;
		Integer rows = Constants.SIZE;
		User paramEntity = new User();
		// 名称模糊查询
		String targetName = request.getParameter("targetName");
		// 获取设和类型查询条件, 如果传递的参数为空的话, 则默认首先显示 待审核的数据
		String checkStatus = request.getParameter("checkStatus");
		if (checkStatus == null || "".equals(checkStatus)) {
			checkStatus = Constants.CheckStatus.DAISHEN.getValue();
		}
		paramEntity.setLoginName(targetName);
		paramEntity.setCheckStatus(checkStatus);

		// 获取地区码
		String userId = CookieHelper.getCookieValue(request, Constants.ADMINUSERID);
		if(userId == null || "".equals(userId)){
			return new ModelAndView("redirect:/loginManage/login");
		}
		Integer uid = Integer.parseInt(userId);
		User userObj = userService.getById(uid);
		// 根据管理员用户所属地区, 查询他下面所属的所有数据
		String acode = userObj.getArea().getCode();
		paramEntity.setArea(new Area(acode));

		List<User> resultList = userService.getByPage(paramEntity, page, rows);
		Integer total = userService.getTotalCount(paramEntity); // 数据总条数
		try {
			List<Map<String, Object>> list = new ArrayList<>();
			log.info("resultList.size()" + resultList.size());
			for (User tmp : resultList) {
				Map<String, Object> tempMap = new HashMap<>();
				tempMap.put("id", tmp.getId());// id
				tempMap.put("loginName", tmp.getLoginName());// 账号
				tempMap.put("email", tmp.getEmail());// 邮箱
				tempMap.put("phone", tmp.getPhone());// 联系电话
				tempMap.put("area", tmp.getArea());// 所属地区

				list.add(tempMap);
			}
			entity.put("total", total);
			entity.put("entityList", list);
			log.debug("获取 账号/用户 信息，size():" + total);
		} catch (Exception e) {
			log.error("获取账号/用户 时发生错误。");
			e.printStackTrace();
		}
		// 放入当前页数, 总页数, 普通用户账号名, 审核状态
		entity.put("currentPage", page);
		entity.put("totalPage", KitService.getTotalPage(total));
		entity.put("targetName", targetName);
		entity.put("checkStatus", checkStatus);
		entity.put("checkStatusName",
				KitService.getCheckStatusName(checkStatus));
		return new ModelAndView("manage/user-list", entity);
	}

	// 跳转到查看普通用户账号页面
	@RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
	public ModelAndView view_object(@PathVariable(value = "id") Integer id,
			HttpServletRequest request) {
		Map<String, Object> entity = new HashMap<String, Object>();
		// 根据id查询对应的数据
		User obj = userService.getById(id);
		entity.put("obj", obj);
		// 工作地区
		List<Area> alist = areaService.getProvinceList();
		entity.put("provinceList", alist);
		return new ModelAndView("manage/user-view", entity);
	}

	// 跳转到编辑普通用户账号页面
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit_object_get(@PathVariable(value = "id") Integer id,
			HttpServletRequest request) {
		Map<String, Object> entity = new HashMap<String, Object>();
		// 根据id查询对应的数据
		User obj = userService.getById(id);
		entity.put("obj", obj);
		// 工作地区
		List<Area> alist = areaService.getProvinceList();
		entity.put("provinceList", alist);
		return new ModelAndView("manage/user-edit", entity);
	}

	// 提交保存编辑的普通用户账号
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> edit_object_post(User param,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (param == null) {
			map.put(Constants.NOTICE, "传递的参数为空, 请刷新后重新尝试或联系网站开发人员.");
			return map;
		}
		if (userService.update(param)) {
			map.put(Constants.NOTICE, Constants.Notice.SUCCESS.getValue());
		} else {
			map.put(Constants.NOTICE, "操作失败, 请联系管理员或网站开发人员");
		}
		return map;
	}

	// 拒绝普通用户账号通过
	@RequestMapping(value = "/refuse/{id}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> refuse_object(
			@PathVariable(value = "id") Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		User refuseEntity = userService.getById(id);
		refuseEntity
				.setCheckStatus(Constants.CheckStatus.WEITONGGUO.getValue());
		if (userService.update(refuseEntity)) {
			map.put(Constants.NOTICE, Constants.Notice.SUCCESS.getValue());
		} else {
			map.put(Constants.NOTICE, "操作失败, 请联系管理员或网站开发人员");
		}
		return map;
	}

	// 同意普通用户账号通过
	@RequestMapping(value = "/approve/{id}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> approve_object(
			@PathVariable(value = "id") Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		User refuseEntity = userService.getById(id);
		refuseEntity.setCheckStatus(Constants.CheckStatus.OK.getValue());
		if (userService.update(refuseEntity)) {
			map.put(Constants.NOTICE, Constants.Notice.SUCCESS.getValue());
		} else {
			map.put(Constants.NOTICE, "操作失败, 请联系管理员或网站开发人员");
		}
		return map;
	}

	// 删除普通用户账号
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delete_object(
			@PathVariable(value = "id") Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		User user = userService.getById(id);
		// 个人用户, 则删除下面的简历
		if (Constants.Identity.PERSON.getValue().equals(user.getIdentity())) {
			Boolean bl1 = userService.delete(id);
			Boolean bl2 = resumeService.deleteByUser(id);
			if (bl1 && bl2) {
				map.put(Constants.NOTICE, Constants.Notice.SUCCESS.getValue());
			} else {
				map.put(Constants.NOTICE, "操作失败, 请联系管理员或网站开发人员");
			}
		} else if (Constants.Identity.COMPANY.getValue().equals(
				user.getIdentity())) {
			Company company = companyService.getByAccount(id);
			Boolean bl1 = userService.delete(id);
			Boolean bl2 = companyService.delete(company.getId());
			Boolean bl3 = jobService.deleteByCompany(company.getId());
			if (bl1 && bl2 && bl3) {
				map.put(Constants.NOTICE, Constants.Notice.SUCCESS.getValue());
			} else {
				map.put(Constants.NOTICE, "操作失败, 请联系管理员或网站开发人员");
			}
		}
		return map;
	}
}
