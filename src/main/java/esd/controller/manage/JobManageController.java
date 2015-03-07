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
import esd.bean.Job;
import esd.bean.JobCategory;
import esd.bean.Parameter;
import esd.bean.User;
import esd.controller.Constants;
import esd.service.AreaService;
import esd.service.CookieHelper;
import esd.service.JobCategoryService;
import esd.service.JobService;
import esd.service.KitService;
import esd.service.ParameterService;
import esd.service.UserService;

/**
 * 招聘信息/职位 后台管理控制器
 * 
 * @author yufu
 * @email ilxly01@126.com 2014-11-5
 */
@Controller
@RequestMapping("/manage/job")
public class JobManageController {
	private static Logger log = Logger.getLogger(JobManageController.class);

	@Autowired
	private UserService<User> userService;
	
	@Autowired
	private AreaService areaService;

	@Autowired
	private JobService jobService;

	@Autowired
	private ParameterService pService;

	@Autowired
	private JobCategoryService jcService;

	// 转到职位管理列表页面
	@RequestMapping(value = "/job_list", method = RequestMethod.GET)
	public ModelAndView list_get(HttpServletRequest request, HttpServletResponse response) {
		log.debug("goto：招聘信息/职位 后台管理 列表");
		Map<String, Object> entity = new HashMap<>();
		String pageStr = request.getParameter("page");
		Integer page = KitService.getInt(pageStr) > 0 ? KitService
				.getInt(pageStr) : 1;
		Integer rows = Constants.SIZE;
		Job paramEntity = new Job();
		// 名称模糊查询
		String targetName = request.getParameter("targetName");
		// 获取设和类型查询条件, 如果传递的参数为空的话, 则默认首先显示 待审核的数据
		String checkStatus = request.getParameter("checkStatus");
		if (checkStatus == null || "".equals(checkStatus)) {
			checkStatus = Constants.CheckStatus.DAISHEN.getValue();
		}
		paramEntity.setName(targetName);
		paramEntity.setCheckStatus(checkStatus);
		
		//获取当前管理员所在地区code
		String userId = CookieHelper.getCookieValue(request, Constants.ADMINUSERID);
		if(userId == null || "".equals(userId)){
			return new ModelAndView("redirect:/loginManage/login");
		}
		Integer uid = Integer.parseInt(userId);
		User user = userService.getById(uid);
		// 根据管理员用户所属地区, 查询他下面所属的所有数据
		String acode = user.getArea().getCode();
		paramEntity.setArea(new Area(acode));

		List<Job> resultList = jobService.getListForManage(paramEntity,
				page, rows);
		Integer total = jobService.getTotalCount(paramEntity,Boolean.FALSE); // 数据总条数
		try {
			List<Map<String, Object>> list = new ArrayList<>();
			log.info("resultList.size()" + resultList.size());
			for (Job tmp : resultList) {
				Map<String, Object> tempMap = new HashMap<>();
				tempMap.put("id", tmp.getId());// id
				tempMap.put("name", tmp.getName());// name
				tempMap.put("hireNumber", tmp.getHireNumber());// 人数
				tempMap.put("salary", tmp.getSalary());// 薪水
				tempMap.put("nature", tmp.getNature());// 职位性质
				tempMap.put("createDate", tmp.getCreateDate());// 创建日期
				tempMap.put("effectiveTime", tmp.getEffectiveTime());// 有效期
				tempMap.put("contactPerson", tmp.getContactPerson()); // 联系人
				tempMap.put("contactTel", tmp.getContactTel()); // 联系电话
				list.add(tempMap);
			}
			entity.put("total", total);
			entity.put("entityList", list);
			log.debug("获取 招聘信息/职位 信息，size():" + total);
		} catch (Exception e) {
			log.error("获取招聘信息/职位 时发生错误。");
			e.printStackTrace();
		}
		// 放入当前页数, 总页数, 职位名, 审核状态
		entity.put("currentPage", page);
		entity.put("totalPage", KitService.getTotalPage(total));
		entity.put("targetName", targetName);
		entity.put("checkStatus", checkStatus);
		entity.put("checkStatusName",
				KitService.getCheckStatusName(checkStatus));
		return new ModelAndView("manage/job-list", entity);
	}

	// 跳转到查看职位页面
	@RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
	public ModelAndView view_object(@PathVariable(value = "id") Integer id,
			HttpServletRequest request) {
		Map<String, Object> entity = new HashMap<String, Object>();
		// 根据id查询对应的数据
		Job obj = jobService.getById(id);
		entity.put("obj", obj);
		// 各种参数
		List<Parameter> plist = pService.getAll();
		entity.put("params", plist);
		// 职位类别
		List<JobCategory> jlist = jcService.getAll();
		entity.put("jcList", jlist);
		// 工作地区
		List<Area> alist = areaService.getProvinceList();
		entity.put("provinceList", alist);
		return new ModelAndView("manage/job-view", entity);
	}

	// 跳转到编辑职位页面
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit_object_get(@PathVariable(value = "id") Integer id,
			HttpServletRequest request) {
		Map<String, Object> entity = new HashMap<String, Object>();
		// 根据id查询对应的数据
		Job obj = jobService.getById(id);
		entity.put("obj", obj);
		// 各种参数
		List<Parameter> plist = pService.getAll();
		entity.put("params", plist);
		// 职位类别
		List<JobCategory> jlist = jcService.getAll();
		entity.put("jcList", jlist);
		// 工作地区
		List<Area> alist = areaService.getProvinceList();
		entity.put("provinceList", alist);
		return new ModelAndView("manage/job-edit", entity);
	}

	// 提交保存编辑的职位
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> edit_object_post(Job param, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(param == null){
			map.put(Constants.NOTICE, "传递的参数为空, 请刷新后重新尝试或联系网站开发人员.");
			return map;
		}
		if (jobService.update(param)) {
			map.put(Constants.NOTICE, Constants.Notice.SUCCESS.getValue());
		} else {
			map.put(Constants.NOTICE, "操作失败, 请联系管理员或网站开发人员");
		}
		return map;
	}
	
	// 拒绝职位通过
	@RequestMapping(value="/refuse/{id}",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> refuse_object(@PathVariable(value = "id") Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		Job refuseEntity = jobService.getById(id);
		refuseEntity
				.setCheckStatus(Constants.CheckStatus.WEITONGGUO.getValue());
		if (jobService.update(refuseEntity)) {
			map.put(Constants.NOTICE, Constants.Notice.SUCCESS.getValue());
		} else {
			map.put(Constants.NOTICE, "操作失败, 请联系管理员或网站开发人员");
		}
		return map;
	}
	
	// 同意职位通过
	@RequestMapping(value="/approve/{id}",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> approve_object(@PathVariable(value = "id") Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		Job refuseEntity = jobService.getById(id);
		refuseEntity
				.setCheckStatus(Constants.CheckStatus.OK.getValue());
		if (jobService.update(refuseEntity)) {
			map.put(Constants.NOTICE, Constants.Notice.SUCCESS.getValue());
		} else {
			map.put(Constants.NOTICE, "操作失败, 请联系管理员或网站开发人员");
		}
		return map;
	}

	// 删除职位
	@RequestMapping(value="/delete/{id}",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delete_object(@PathVariable(value = "id") Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		Boolean bl = jobService.delete(id);
		if (bl) {
			map.put(Constants.NOTICE, Constants.Notice.SUCCESS.getValue());
		} else {
			map.put(Constants.NOTICE, "操作失败, 请联系管理员或网站开发人员");
		}
		return map;
	}

}
