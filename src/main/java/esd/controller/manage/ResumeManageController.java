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
import esd.bean.JobCategory;
import esd.bean.Parameter;
import esd.bean.Resume;
import esd.bean.User;
import esd.controller.Constants;
import esd.service.AreaService;
import esd.service.CookieHelper;
import esd.service.JobCategoryService;
import esd.service.KitService;
import esd.service.ParameterService;
import esd.service.ResumeService;
import esd.service.UserService;

/**
 * 简历 后台管理控制器
 * 
 * @author yufu
 * @email ilxly01@126.com 2014-11-5
 */
@Controller
@RequestMapping("/manage/resume")
public class ResumeManageController {
	private static Logger log = Logger.getLogger(ResumeManageController.class);

//	@Value("${templateFile}")
//	private String templateFile;
//
//	@Value("${destFileName}")
//	private String destFileName;

	@Autowired
	private UserService<User> userService;

	@Autowired
	private AreaService areaService;

	@Autowired
	private ResumeService resumeService;
	
	@Autowired
	private ParameterService pService;

	@Autowired
	private JobCategoryService jcService;

	// 转到简历管理列表页面
	@RequestMapping(value = "/resume_list", method = RequestMethod.GET)
	public ModelAndView list_get(HttpServletRequest request) {
		log.debug("goto：简历 后台管理 列表");
		Map<String, Object> entity = new HashMap<>();
		String pageStr = request.getParameter("page");
		Integer page = KitService.getInt(pageStr) > 0 ? KitService
				.getInt(pageStr) : 1;
		Integer rows = Constants.SIZE;
		Resume paramEntity = new Resume();
		// 名称模糊查询
		String targetName = request.getParameter("targetName");
		// 获取设和类型查询条件, 如果传递的参数为空的话, 则默认首先显示 待审核的数据
		String checkStatus = request.getParameter("checkStatus");
		if (checkStatus == null || "".equals(checkStatus)) {
			checkStatus = Constants.CheckStatus.DAISHEN.getValue();
		}
		paramEntity.setTitle(targetName);
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

		List<Resume> resultList = resumeService.getListShowForManage(paramEntity,
				page, rows);
		Integer total = resumeService.getTotalCount(paramEntity,Boolean.FALSE); // 数据总条数
		try {
			List<Map<String, Object>> list = new ArrayList<>();
			System.out.println("resultList.size()" + resultList.size());
			for (Resume tmp : resultList) {
				Map<String, Object> tempMap = new HashMap<>();
				tempMap.put("id", tmp.getId());// id
				tempMap.put("title", tmp.getTitle());// 简历名称
				tempMap.put("name", tmp.getName());// 姓名
				tempMap.put("gender", tmp.getGender());// 性别
				tempMap.put("disabilityCard", tmp.getDisabilityCard());	//残疾证号
				tempMap.put("disabilityCategory", tmp.getDisabilityCategory());	//残疾类别
				tempMap.put("disabilityLevel",tmp.getDisabilityLevel());	//残疾等级
				tempMap.put("phone", tmp.getPhone());	//联系电话
				list.add(tempMap);
			}
			entity.put("total", total);
			entity.put("entityList", list);
			log.debug("获取 简历 信息，size():" + total);
		} catch (Exception e) {
			log.error("获取 简历 时发生错误。");
			e.printStackTrace();
		}
		// 放入当前页数, 总页数, 简历名, 审核状态
		entity.put("currentPage", page);
		entity.put("totalPage", KitService.getTotalPage(total));
		entity.put("targetName", targetName);
		entity.put("checkStatus", checkStatus);
		entity.put("checkStatusName",
				KitService.getCheckStatusName(checkStatus));
		return new ModelAndView("manage/resume-list", entity);
	}

	// 跳转到查看简历页面
	@RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
	public ModelAndView view_object(@PathVariable(value = "id") Integer id,
			HttpServletRequest request) {
		Map<String, Object> entity = new HashMap<String, Object>();
		// 根据id查询对应的数据
		Resume obj = resumeService.getById(id);
		entity.put("obj", obj);
		// 各种参数
		List<Parameter> plist = pService.getAll();
		entity.put("params", plist);
		// 简历类别
		List<JobCategory> jlist = jcService.getAll();
		entity.put("jcList", jlist);
		// 工作地区
		List<Area> alist = areaService.getProvinceList();
		entity.put("provinceList", alist);
		return new ModelAndView("manage/resume-view", entity);
	}

	// 跳转到编辑简历页面
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit_object_get(@PathVariable(value = "id") Integer id,
			HttpServletRequest request) {
		Map<String, Object> entity = new HashMap<String, Object>();
		// 根据id查询对应的数据
		Resume obj = resumeService.getById(id);
		entity.put("obj", obj);
		// 各种参数
		List<Parameter> plist = pService.getAll();
		entity.put("params", plist);
		// 简历类别
		List<JobCategory> jlist = jcService.getAll();
		entity.put("jcList", jlist);
		// 工作地区
		List<Area> alist = areaService.getProvinceList();
		entity.put("provinceList", alist);
		return new ModelAndView("manage/resume-edit", entity);
	}

	// 提交保存编辑的简历
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> edit_object_post(Resume param, HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(param == null){
			map.put(Constants.NOTICE, "传递的参数为空, 请刷新后重新尝试或联系网站开发人员.");
			return map;
		}
		if (resumeService.update(param)) {
			map.put(Constants.NOTICE, Constants.Notice.SUCCESS.getValue());
		} else {
			map.put(Constants.NOTICE, "操作失败, 请联系管理员或网站开发人员");
		}
		return map;
	}
	
	// 拒绝简历通过
	@RequestMapping(value="/refuse/{id}",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> refuse_object(
			@PathVariable(value = "id") Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		Resume refuseEntity = resumeService.getById(id);
		refuseEntity
				.setCheckStatus(Constants.CheckStatus.WEITONGGUO.getValue());
		if (resumeService.update(refuseEntity)) {
			map.put(Constants.NOTICE, Constants.Notice.SUCCESS.getValue());
		} else {
			map.put(Constants.NOTICE, "操作失败, 请联系管理员或网站开发人员");
		}
		return map;
	}
	
	// 同意简历通过
	@RequestMapping(value="/approve/{id}",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> approve_object(
			@PathVariable(value = "id") Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		Resume refuseEntity = resumeService.getById(id);
		refuseEntity
				.setCheckStatus(Constants.CheckStatus.OK.getValue());
		if (resumeService.update(refuseEntity)) {
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
		Boolean bl = resumeService.delete(id);
		if (bl) {
			map.put(Constants.NOTICE, Constants.Notice.SUCCESS.getValue());
		} else {
			map.put(Constants.NOTICE, "操作失败, 请联系管理员或网站开发人员");
		}
		return map;
	}
}
