package esd.controller.manage;

import java.util.ArrayList;
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
import org.springframework.web.servlet.ModelAndView;

import esd.bean.Area;
import esd.bean.BusinessScope;
import esd.bean.Company;
import esd.bean.Job;
import esd.bean.Parameter;
import esd.bean.Resume;
import esd.bean.User;
import esd.controller.Constants;
import esd.service.BusinessScopeService;
import esd.service.CompanyService;
import esd.service.CookieHelper;
import esd.service.ImageService;
import esd.service.JobService;
import esd.service.KitService;
import esd.service.ParameterService;
import esd.service.ResumeService;
import esd.service.UserService;

/**
 * 企业信息 后台管理控制器
 * 
 * @author yufu
 * @email ilxly01@126.com 2014-11-5
 */
@Controller
@RequestMapping("/manage/company")
public class CompanyManageController {
	private static Logger log = Logger.getLogger(CompanyManageController.class);

//	@Value("${templateFile}")
//	private String templateFile;
//
//	@Value("${destFileName}")
//	private String destFileName;

	@Autowired
	private UserService<User> userService;
	
	@Autowired
	private CompanyService<Company> companyService;

	@Autowired
	private JobService jobService;
	
	@Autowired
	private ParameterService pService;
	
	@Autowired
	private BusinessScopeService bsService; 

	@Autowired
	private ImageService imageService;
	
	@Autowired
	private ResumeService resumeService;
	// 转到企业信息管理列表页面
	@RequestMapping(value = "/company_list", method = RequestMethod.GET)
	public ModelAndView list_get(HttpServletRequest request) {
		log.debug("goto：企业信息 后台管理 列表");
		Map<String, Object> entity = new HashMap<>();
		String pageStr = request.getParameter("page");
		Integer page = KitService.getInt(pageStr) > 0 ? KitService
				.getInt(pageStr) : 1;
		Integer rows = Constants.SIZE;
		Company paramEntity = new Company();
		// 名称模糊查询
		String targetName = request.getParameter("targetName");
		// 获取设和类型查询条件, 如果传递的参数为空的话, 则默认首先显示 待审核的数据
		String checkStatus = request.getParameter("checkStatus");
		if (checkStatus == null || "".equals(checkStatus)) {
			checkStatus = Constants.CheckStatus.DAISHEN.getValue();
		}
		paramEntity.setName(targetName);
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

		List<Company> resultList = companyService.getListShowForManage(
				paramEntity, page, rows);
		Integer total = companyService.getTotalCount(paramEntity, Boolean.TRUE); // 数据总条数
		try {
			List<Map<String, Object>> list = new ArrayList<>();
			for (Company tmp : resultList) {
				Map<String, Object> tempMap = new HashMap<>();
				tempMap.put("id", tmp.getId());// id
				tempMap.put("name", tmp.getName());// 公司名称
				tempMap.put("contactPerson", tmp.getContactPerson());// 联系人
				tempMap.put("telephone", tmp.getTelephone()); // 联系电话
				tempMap.put("nature", tmp.getNature());// 企业性质
				tempMap.put("economyType", tmp.getEconomyType());// 经济类型
				tempMap.put("area", tmp.getArea());// 所属地区
				list.add(tempMap);
			}
			entity.put("total", total);
			entity.put("entityList", list);
			log.debug("获取 企业信息，size():" + total);
		} catch (Exception e) {
			log.error("获取企业信息 时发生错误。");
			e.printStackTrace();
		}
		// 放入当前页数, 总页数, 企业名, 审核状态
		entity.put("currentPage", page);
		entity.put("totalPage", KitService.getTotalPage(total));
		entity.put("targetName", targetName);
		entity.put("checkStatus", checkStatus);
		entity.put("checkStatusName",
				KitService.getCheckStatusName(checkStatus));
		return new ModelAndView("manage/company-list", entity);
	}

	// 跳转到查看企业页面
	@RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
	public ModelAndView view_object(@PathVariable(value = "id") Integer id,
			HttpServletRequest request) {
		Map<String, Object> entity = new HashMap<String, Object>();
		// 根据id查询对应的数据
		Company obj = companyService.getById(id);
		entity.put("obj", obj);
		// 各种参数
		List<Parameter> plist = pService.getAll();
		entity.put("params", plist);
		// 职位类别
		List<BusinessScope> jlist = bsService.getAll();
		entity.put("bsList", jlist);
		return new ModelAndView("manage/company-view", entity);
	}

	// 跳转到编辑企业页面
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit_object_get(@PathVariable(value = "id") Integer id,
			HttpServletRequest request) {
		Map<String, Object> entity = new HashMap<String, Object>();
		// 根据id查询对应的数据
		Company obj = companyService.getById(id);
		entity.put("obj", obj);
		// 各种参数
		List<Parameter> plist = pService.getAll();
		entity.put("params", plist);
		// 职位类别
		List<BusinessScope> jlist = bsService.getAll();
		entity.put("bsList", jlist);
		return new ModelAndView("manage/company-edit", entity);
	}

	// 提交保存编辑的企业
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> edit_object_post(Company param,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (param == null) {
			map.put(Constants.NOTICE, "传递的参数为空, 请刷新后重新尝试或联系网站开发人员.");
			return map;
		}
		if (companyService.update(param)) {
			map.put(Constants.NOTICE, Constants.Notice.SUCCESS.getValue());
		} else {
			map.put(Constants.NOTICE, "操作失败, 请联系管理员或网站开发人员");
		}
		return map;
	}
	
	// 拒绝企业通过
	@RequestMapping(value = "/refuse/{id}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> refuse_object(
			@PathVariable(value = "id") Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		Company refuseEntity = companyService.getById(id);
		refuseEntity
				.setCheckStatus(Constants.CheckStatus.WEITONGGUO.getValue());
		if (companyService.update(refuseEntity)) {
			map.put(Constants.NOTICE, Constants.Notice.SUCCESS.getValue());
		} else {
			map.put(Constants.NOTICE, "操作失败, 请联系管理员或网站开发人员");
		}
		return map;
	}

	// 同意企业通过
	@RequestMapping(value = "/approve/{id}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> approve_object(
			@PathVariable(value = "id") Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		Company refuseEntity = companyService.getById(id);
		refuseEntity.setCheckStatus(Constants.CheckStatus.OK.getValue());
		if (companyService.update(refuseEntity)) {
			map.put(Constants.NOTICE, Constants.Notice.SUCCESS.getValue());
		} else {
			map.put(Constants.NOTICE, "操作失败, 请联系管理员或网站开发人员");
		}
		return map;
	}

	// 删除公司
	@RequestMapping(value="/delete/{id}",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delete_object(@PathVariable(value = "id") Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		//删除对应的图片
		//第一步查询图片ID；
		Company company = companyService.getById(id);
		
		Boolean bl = companyService.delete(id);
		Boolean bl2 = jobService.deleteByCompany(id);

		if (bl&&bl2) {
		    String businessLicense = company.getBusinessLicense();
		    if (businessLicense != null && !"".equals(businessLicense)) {
		    	imageService.deleteId(businessLicense);
			}
		    String institutionalFramework = company.getInstitutionalFramework();
		    if (institutionalFramework != null && !"".equals(institutionalFramework)) {
		    	imageService.deleteId(institutionalFramework);
			}
		    String taxRegistration = company.getTaxRegistration();
		    if (taxRegistration != null && !"".equals(taxRegistration)) {
		    	imageService.deleteId(taxRegistration);
			}
			map.put(Constants.NOTICE, Constants.Notice.SUCCESS.getValue());
		} else {
			map.put(Constants.NOTICE, "操作失败, 请联系管理员或网站开发人员");
		}
		return map;
	}
	
	// 转到自动匹配企业管理列表页面
	@RequestMapping(value = "/company_mate_list", method = RequestMethod.GET)
	public ModelAndView mate_list_get(HttpServletRequest request) {
		Map<String, Object> entity = new HashMap<>();
		String pageStr = request.getParameter("page");
		Integer page = KitService.getInt(pageStr) > 0 ? KitService
				.getInt(pageStr) : 1;
		Integer rows = Constants.SIZE;
		List<Parameter> plist = pService.getAll();
		entity.put("params", plist);
	
		Company paramEntity = new Company();
		String companyMat = request.getParameter("companyMat");
		if (companyMat != null && !"".equals(companyMat)) {
			companyMat=companyMat+"%";
		}

		// 获取地区码
		String userId = CookieHelper.getCookieValue(request, Constants.USERID);
		Integer uid = Integer.parseInt(userId);
		User userObj = userService.getById(uid);
		// 根据管理员用户所属地区, 查询他下面所属的所有数据
		String acode = userObj.getArea().getCode();
		paramEntity.setArea(new Area(acode));

		List<Company> resultList = companyService.getListShowForManage(
				paramEntity, page, rows);
		Integer total = companyService.getTotalCount(paramEntity, Boolean.TRUE); // 数据总条数
		try {
			List<Map<String, Object>> list = new ArrayList<>();
			for (Company tmp : resultList) {
				Map<String, Object> tempMap = new HashMap<>();
				tempMap.put("id", tmp.getId());// id
				tempMap.put("name", tmp.getName());// 公司名称
				tempMap.put("email", tmp.getEmail());
				tempMap.put("contactDept", tmp.getContactDept());
				tempMap.put("contactPerson", tmp.getContactPerson());// 联系人
				tempMap.put("telephone", tmp.getTelephone()); // 联系电话
				tempMap.put("nature", tmp.getNature());// 企业性质
				tempMap.put("economyType", tmp.getEconomyType());// 经济类型
				tempMap.put("area", tmp.getArea());// 所属地区
				//根据id查出公司所发布的职位
				
				List<Job> jobList = jobService.getByCompanyForShow(tmp.getId(), 1,
						jobService.getByCompanyCount(tmp.getId()));
				tempMap.put("jobList",jobList);
				for (Job jobResult : jobList) {
					
					List<Resume> resumeResultList = null ;
					String mark = request.getParameter("mark");
					//判断查询条件是否为空，组成查询条件
					if(mark != null && !"".equals(mark)){
					Resume resumeEntity = new Resume();
					resumeEntity.setArea(new Area(acode));
					String jccode = request.getParameter("jccode");
					if (jccode != null && !"".equals(jccode)) {
						resumeEntity.setDesireJob(jobResult.getJobCategory());
					}
					String work_place = request.getParameter("work_place");
					if (work_place != null && !"".equals(work_place)) {
						resumeEntity.setDesireAddress(jobResult.getWorkPlace());
					}
						
					String nature = request.getParameter("nature");
					if (nature != null && !"".equals(nature)) {
						resumeEntity.setJobNature(jobResult.getNature());
					}
					
					String name = request.getParameter("name");
					if (name != null && !"".equals(name)) {
						resumeEntity.setName(jobResult.getName());
					}
					String education = request.getParameter("education");
					if (education != null && !"".equals(education)) {
						resumeEntity.setEducation(jobResult.getEducation());
					}
					String experience = request.getParameter("experience");
					if (experience != null && !"".equals(experience)) {
						resumeEntity.setExperience(jobResult.getExperience());
					}
					String salary = request.getParameter("salary");
					if (salary != null && !"".equals(salary)) {
						resumeEntity.setDesireSalary(jobResult.getSalary());
					}
					String gender = request.getParameter("gender");
					if (gender != null && !"".equals(gender)) {
						resumeEntity.setGender(jobResult.getGender());
					}
					resumeResultList=resumeService.getForListShow(resumeEntity, 1, Integer.MAX_VALUE);
					}
					tempMap.put("resumeResultList",resumeResultList);
				}
			
				list.add(tempMap);
			}
			entity.put("total", total);
			entity.put("entityList", list);
			log.debug("获取 企业信息，size():" + total);
		} catch (Exception e) {
			log.error("获取企业信息 时发生错误。");
			e.printStackTrace();
		}
		// 放入当前页数, 总页数, 企业名, 审核状态
		entity.put("currentPage", page);
		entity.put("totalPage", KitService.getTotalPage(total));
		entity.put("companyMatchPerName", companyMat);
	
		return new ModelAndView("manage/company-mate-list", entity);
	}
	
	// 转到自动匹配企业管理列表页面
		@RequestMapping(value = "/company_mate_json", method = RequestMethod.GET)
		public ModelAndView mate_list_json(HttpServletRequest request) {
			Map<String, Object> entity = new HashMap<>();
			String id = request.getParameter("id");
			entity.put("companyid", id);
			Integer page = 1;
			Integer rows = Integer.MAX_VALUE;
			Company paramEntity = new Company();
			//百分比
			String companyMat = request.getParameter("companyMat");
			if (companyMat != null && !"".equals(companyMat)) {
				companyMat=companyMat+"%";
			}
			// 获取地区码
			String userId = CookieHelper.getCookieValue(request, Constants.USERID);
			Integer uid = Integer.parseInt(userId);
			User userObj = userService.getById(uid);
			// 根据管理员用户所属地区, 查询他下面所属的所有数据
			String acode = userObj.getArea().getCode();
			paramEntity.setArea(new Area(acode));
			//根据id查出公司所发布的职位
			int idInt=KitService.getInt(id);
			List<Job> jobList = jobService.getByCompanyMate(idInt, 1,
					jobService.getByCompanyCount(idInt));
			try {
				List<Map<String, Object>> list = new ArrayList<>();
				for (Job tmp : jobList) {
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
					List<Resume> resumeResultList = null ;
					String mark = request.getParameter("mark");
					//判断查询条件是否为空，组成查询条件
						if(mark != null && !"".equals(mark)){
						Resume resumeEntity = new Resume();
						resumeEntity.setArea(new Area(acode));
						String jccode = request.getParameter("jccode");
						if (jccode != null && !"".equals(jccode)) {
							resumeEntity.setDesireJob(tmp.getJobCategory());
							
						}
						String work_place = request.getParameter("work_place");
						
						if (work_place != null && !"".equals(work_place)) {
							resumeEntity.setDesireAddress(tmp.getWorkPlace());
						}
							
						String nature = request.getParameter("nature");
						if (nature != null && !"".equals(nature)) {
							String JobNature=KitService.getMateNature(tmp.getNature());
							resumeEntity.setJobNature(JobNature);
						}
						
						String name = request.getParameter("name");
						if (name != null && !"".equals(name)) {
							resumeEntity.setName(tmp.getName());
						}
						String education = request.getParameter("education");
						if (education != null && !"".equals(education)) {
							resumeEntity.setEducation(tmp.getEducation());
						}
						String experience = request.getParameter("experience");
						if (experience != null && !"".equals(experience)) {
							resumeEntity.setExperience(tmp.getExperience());
						}
						String salary = request.getParameter("salary");
						if (salary != null && !"".equals(salary)) {
							resumeEntity.setDesireSalary(tmp.getSalary());
						}
						String gender = request.getParameter("gender");
						if (gender != null && !"".equals(gender)) {
							resumeEntity.setGender(tmp.getGender());
						}
						resumeResultList=resumeService.getForListShow(resumeEntity, 1, Integer.MAX_VALUE);
						}
						tempMap.put("resumeResultList",resumeResultList);
					
					list.add(tempMap);
				}
				entity.put("entityList", list);
			} catch (Exception e) {
				log.error("获取企业信息 时发生错误。");
				e.printStackTrace();
			}
			entity.put("resumeMatchPerName", companyMat);
			return new ModelAndView("manage/company-mate-json", entity);
		}
}
