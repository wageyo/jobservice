package esd.controller.manage;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import esd.bean.Area;
import esd.bean.Company;
import esd.bean.Job;
import esd.bean.Resume;
import esd.bean.User;
import esd.controller.Constants;
import esd.service.AreaService;
import esd.service.CompanyService;
import esd.service.JobService;
import esd.service.KitService;
import esd.service.ResumeService;
import esd.service.UserService;

@Controller
@RequestMapping("/manage")
public class ManageController {
	private static Logger log = Logger.getLogger(ManageController.class);

	@Value("${templateFile}")
	private String templateFile;
	@Value("${destFileName}")
	private String destFileName;

	@Autowired
	private UserService userService;
	@Autowired
	private AreaService areaService;

	@Autowired
	private CompanyService companyService;

	public CompanyService getCompanyService() {
		return companyService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public JobService getJobService() {
		return jobService;
	}

	public void setJobService(JobService jobService) {
		this.jobService = jobService;
	}

	public ResumeService getResumeService() {
		return resumeService;
	}

	public void setResumeService(ResumeService resumeService) {
		this.resumeService = resumeService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Autowired
	private JobService jobService;
	@Autowired
	private ResumeService resumeService;

	/*
	 * 离开
	 */
	@RequestMapping(value = "/quit", method = RequestMethod.GET)
	public String quit(HttpServletRequest request) {

		request.getSession().removeAttribute(Constants.USER);
		log.error("管理员用户退出");
		return "redirect:/loginmanage/login";
	}

	/*
	 * 转到管理 主页
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request) {

		return new ModelAndView("manage/index");
	}

	// 转到首页 提示页
	@RequestMapping(value = "/prompt", method = RequestMethod.GET)
	public ModelAndView Prompt(HttpServletRequest request) {

		return new ModelAndView("manage/prompt");
	}

	/*
	 * 转到企业管理页面
	 */
	@RequestMapping(value = "/company_list", method = RequestMethod.GET)
	public ModelAndView company_list(HttpServletRequest request) {
		log.debug("goto：企业管理");
		return new ModelAndView("manage/company-list");
	}

	/*
	 * 转到职位管理列表页面
	 */
	@RequestMapping(value = "/job_list", method = RequestMethod.GET)
	public ModelAndView job_list(HttpServletRequest request) {
		log.debug("goto：职位管理");

		return new ModelAndView("manage/job-list");
	}

	/*
	 * 转到单个职位显示页面
	 */
	@RequestMapping(value = "/job_view", method = RequestMethod.GET)
	public ModelAndView job_view(HttpServletRequest request) {
		log.debug("goto：显示职位管理");

		return new ModelAndView("manage/job-view");
	}

	/**
	 * 转到修改密码页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/password_edit", method = RequestMethod.GET)
	public ModelAndView password_edit_get(HttpServletRequest request) {
		log.debug("goto: 跳转到修改密码页面");
		return new ModelAndView("manage/password-edit");
	}

	/*
	 * 获取 职位管理列表页面数据
	 */
	@RequestMapping(value = "/job_list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> job_lisdatat(HttpServletRequest request) {

		Map<String, Object> entity = new HashMap<>();
		try {
			Integer page = Integer.parseInt(request.getParameter("page"));
			Integer rows = Integer.parseInt(request.getParameter("rows"));

			// 未审核职位状态信息
			Job jobType = new Job();
			String type = request.getParameter("type");

			// 获取地区码
			User userObj = (User) request.getSession().getAttribute(
					Constants.USER);
			String acode = userObj.getArea().getCode();
			// 设置查看地区码条件为管理员所在地区
			jobType.setArea(new Area(acode));
			// 判断显示类型
			// 待审
			if (type == null || "".equals(type)
					|| Constants.CheckStatus.DAISHEN.toString().equals(type)) {

				log.debug("get：职位管理数据    待审");
				jobType.setCheckStatus(Constants.CheckStatus.DAISHEN.toString());
			}
			// 未通过
			if (type != null
					&& Constants.CheckStatus.WEITONGGUO.toString().equals(type)) {
				log.debug("get：职位管理数据    未通过");
				jobType.setCheckStatus(Constants.CheckStatus.WEITONGGUO
						.toString());
			}
			// 通过审核
			if (type != null
					&& Constants.CheckStatus.OK.toString().equals(type)) {
				log.debug("get：职位管理数据    已通过");
				jobType.setCheckStatus(Constants.CheckStatus.OK.toString());
			}

			List<Job> joblist = jobService.getListShowForManage(jobType, page,
					rows);

			Integer total = jobService.getTotalCount(jobType); // 数据总条数
			List<Map<String, Object>> list = new ArrayList<>();
			System.out.println("joblist.size()" + joblist.size());
			for (Job job : joblist) {
				Map<String, Object> jobmap = new HashMap<>();
				jobmap.put("id", job.getId());// id
				jobmap.put("name", job.getName());// name
				jobmap.put("hireNumber", job.getHireNumber());// 人数
				jobmap.put("salary", job.getSalary());// 薪水
				jobmap.put("gender", job.getGender());// 性别
				jobmap.put("nature", job.getNature());// 职位性质
				jobmap.put("createDate", job.getCreateDate());// 创建日期
				jobmap.put("effectiveTime", job.getEffectiveTime());// 有效期
				list.add(jobmap);
			}
			entity.put("total", total);
			entity.put("rows", list);
			log.debug("获取职位信息，size():" + total + "user:" + userObj);
		} catch (Exception e) {
			log.error("获取 管理职位信息时发生错误。");
			e.printStackTrace();
		}
		return entity;
	}

	/*
	 * 获取 企业管理列表页面数据
	 */
	@RequestMapping(value = "/company_list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> companylist(HttpServletRequest request) {
		log.debug("get：企业管理数据");
		Map<String, Object> entity = new HashMap<>();
		try {
			Integer page = Integer.parseInt(request.getParameter("page"));
			Integer rows = Integer.parseInt(request.getParameter("rows"));
			// 未审核职位状态信息
			Company companyType = new Company();
			String type = request.getParameter("type");
			// 获取地区码
			User userObj = (User) request.getSession().getAttribute(
					Constants.USER);
			String acode = userObj.getArea().getCode();
			// 设置查看地区码条件为管理员所在地区
			companyType.setArea(new Area(acode));
			// 判断显示类型
			// 待审
			if (type == null || "".equals(type)
					|| Constants.CheckStatus.DAISHEN.toString().equals(type)) {

				log.debug("get：企业管理数据    待审");
				companyType.setCheckStatus(Constants.CheckStatus.DAISHEN
						.toString());
			}
			// 未通过
			if (type != null
					&& Constants.CheckStatus.WEITONGGUO.toString().equals(type)) {
				log.debug("get：企业管理数据    未通过");
				companyType.setCheckStatus(Constants.CheckStatus.WEITONGGUO
						.toString());
			}
			// 通过审核
			if (type != null
					&& Constants.CheckStatus.OK.toString().equals(type)) {
				log.debug("get：企业管理数据    已通过");
				companyType.setCheckStatus(Constants.CheckStatus.OK.toString());
			}

			List<Company> companylist = companyService.getListShowForManage(
					companyType, page, rows);
			Integer total = companyService.getTotalCount(companyType); // 数据总条数
			List<Map<String, Object>> list = new ArrayList<>();
			for (Company company : companylist) {

				Map<String, Object> jobmap = new HashMap<>();
				jobmap.put("id", company.getId());// id
				jobmap.put("name", company.getName());// 公司名称
				jobmap.put("corporateRepresentative",
						company.getCorporateRepresentative());// 法人
				jobmap.put("contactPerson", company.getContactPerson());// 联系人
				jobmap.put("nature", company.getNature());// 企业性质
				jobmap.put("taxCode", company.getTaxCode());// 税务编码
				jobmap.put("economyType", company.getEconomyType());// 经济类型
				jobmap.put("area", company.getArea().getName());// 所在地区

				list.add(jobmap);
			}
			entity.put("total", total);
			entity.put("rows", list);
			log.debug("获取企业信息，size():" + total + "user:" + userObj);

		} catch (Exception e) {
			log.error("获取 企业信息时发生错误。");
		}
		return entity;
	}

	/*
	 * 获取 简历管理列表页面数据
	 */
	@RequestMapping(value = "/resume_list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> resumelist(HttpServletRequest request) {
		log.debug("get：简历管理数据");
		Map<String, Object> entity = new HashMap<>();
		try {
			Integer page = Integer.parseInt(request.getParameter("page"));
			Integer rows = Integer.parseInt(request.getParameter("rows"));
			// 未审核简历状态信息
			Resume resumeType = new Resume();
			String type = request.getParameter("type");
			// 获取地区码
			User userObj = (User) request.getSession().getAttribute(
					Constants.USER);
			String acode = userObj.getArea().getCode();
			// 设置查看地区码条件为管理员所在地区
			resumeType.setArea(new Area(acode));
			// 判断显示类型
			// 待审
			if (type == null || "".equals(type)
					|| Constants.CheckStatus.DAISHEN.toString().equals(type)) {
				log.debug("get：简历管理数据    待审");
				resumeType.setCheckStatus(Constants.CheckStatus.DAISHEN
						.toString());
			}
			// 未通过
			if (type != null
					&& Constants.CheckStatus.WEITONGGUO.toString().equals(type)) {
				log.debug("get：简历管理数据    未通过");
				resumeType.setCheckStatus(Constants.CheckStatus.WEITONGGUO
						.toString());
			}
			// 通过审核
			if (type != null
					&& Constants.CheckStatus.OK.toString().equals(type)) {
				log.debug("get：简历管理数据    已通过");
				resumeType.setCheckStatus(Constants.CheckStatus.OK.toString());
			}
			List<Resume> resumelist = resumeService.getListShowForManage(
					resumeType, page, rows);
			Integer total = resumeService.getTotalCount(resumeType); // 数据总条数
			List<Map<String, Object>> list = new ArrayList<>();
			for (Resume resume : resumelist) {

				Map<String, Object> resumemap = new HashMap<>();
				resumemap.put("id", resume.getId());// id
				resumemap.put("title", resume.getTitle());// 简历名称
				resumemap.put("name", resume.getName());// 姓名
				resumemap.put("gender", resume.getGender());// 性别
				resumemap.put("identityCard", resume.getBirth());// 身份证号
				resumemap.put("race", resume.getRace());// 民族
				resumemap.put("marriage", resume.getMarriage());// 婚姻状况
				resumemap.put("hukou", resume.getHukou());// 户口所在地
				list.add(resumemap);
			}
			entity.put("total", total);
			entity.put("rows", list);
			log.debug("获取简历信息，size():" + total + "user:" + userObj);

		} catch (Exception e) {

			log.error("获取 简历信息时发生错误。");
		}
		return entity;
	}

	/*
	 * 获取 用户管理列表页面数据
	 */
	@RequestMapping(value = "/user_list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> userlist(HttpServletRequest request) {

		Map<String, Object> entity = new HashMap<>();
		try {
			Integer page = Integer.parseInt(request.getParameter("page"));
			Integer rows = Integer.parseInt(request.getParameter("rows"));
			// 未审核职位状态信息
			User userType = new User();
			String type = request.getParameter("type");

			// 获取地区码
			User userObj = (User) request.getSession().getAttribute(
					Constants.USER);
			String acode = userObj.getArea().getCode();
			// 设置查看地区码条件为管理员所在地区
			userType.setArea(new Area(acode));
			// 判断显示类型
			// 待审
			if (type == null || "".equals(type)
					|| Constants.CheckStatus.DAISHEN.toString().equals(type)) {

				log.debug("get：用户管理数据    待审");
				userType.setCheckStatus(Constants.CheckStatus.DAISHEN
						.toString());
			}
			// 未通过
			if (type != null
					&& Constants.CheckStatus.WEITONGGUO.toString().equals(type)) {
				log.debug("get：用户管理数据    未通过");
				userType.setCheckStatus(Constants.CheckStatus.WEITONGGUO
						.toString());
			}
			// 通过审核
			if (type != null
					&& Constants.CheckStatus.OK.toString().equals(type)) {
				log.debug("get：用户管理数据    已通过");
				userType.setCheckStatus(Constants.CheckStatus.OK.toString());
			}
			List<User> userlist = userService.getByPage(userType, page, rows);

			Integer total = userService.getTotalCount(userType); // 数据总条数
			List<Map<String, Object>> list = new ArrayList<>();
			for (User user : userlist) {
				Map<String, Object> usermap = new HashMap<>();
				if (user.getIdentity().equals(
						Constants.Identity.ADMIN.toString())) {
					// 不取管理员用户数据
					continue;
				}
				if (user.getIdentity().equals(
						Constants.Identity.COMPANY.toString())) {
					usermap.put("identity", "企业企业");// 账号类型
				}
				if (user.getIdentity().equals(
						Constants.Identity.PERSON.toString())) {
					usermap.put("identity", "个人用户");// 账号类型
				}
				usermap.put("id", user.getId());// id
				usermap.put("loginName", user.getLoginName());// 账号
				usermap.put("passWord", user.getPassWord());// 密码
				list.add(usermap);
			}
			entity.put("total", total);
			entity.put("rows", list);
			log.debug("获取用户数据：" + userObj);

		} catch (Exception e) {
			log.error("获取 用户信息时发生错误。");
		}
		return entity;
	}

	@RequestMapping(value = "password_edit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> password_edit_post(HttpServletRequest request,
			HttpSession session) {
		Map<String, Object> notice = new HashMap<String, Object>();
		User user = (User) session.getAttribute(Constants.USER);
		if (user == null) {
			notice.put(Constants.NOTICE, "用户未登录,请登陆后再进行操作");
			return notice;
		}
		String oldPassWord = request.getParameter("oldPassWord");
		String newPassWord = request.getParameter("newPassWord");
		if (!user.getPassWord().equals(oldPassWord)) {
			notice.put(Constants.NOTICE, "原密码不正确, 请重新操作");
			return notice;
		}
		user.setPassWord(newPassWord);
		boolean bl = userService.update(user);
		if (!bl) {
			notice.put(Constants.NOTICE, "更新密码失败");
			return notice;
		}
		notice.put(Constants.NOTICE, bl);
		return notice;
	}

	/*
	 * 1.审核职位
	 */
	@RequestMapping(value = "/audit", method = RequestMethod.POST)
	@ResponseBody
	public boolean audit(@RequestParam(value = "params[]") String params[],
			@RequestParam(value = "type") String type,
			HttpServletRequest request) {
		log.debug("审核职位");
		try {
			String auditType = null;
			if (params == null || type == null) {
				log.error("审核职位  参数有误！");
				return false;
			}
			if (type.equals("ok")) {
				log.error("审核职位通过！");
				auditType = Constants.CheckStatus.OK.toString();
			} else {
				log.error("审核职位未通过！");
				auditType = Constants.CheckStatus.WEITONGGUO.toString();
			}
			// 判断是集合函数单个
			if (params.length == 1) {
				Job job = new Job();
				job.setId(KitService.getInt(params[0]));
				job.setCheckStatus(auditType);
				jobService.update(job);
				log.debug("审核单个职位" + type + "    " + job);
			} else {
				log.debug("审核多个职位" + type + "    ");
				for (int i = 0; i < params.length; i++) {
					Job job = new Job();
					job.setId(KitService.getInt(params[i]));
					job.setCheckStatus(auditType);
					jobService.update(job);
					log.debug("审核多个职位" + type + "    " + job);
				}
			}

			return true;
		} catch (Exception e) {
			log.error("审核职位 出错");
			return false;
		}

	}

	/*
	 * 2.审核企业
	 */
	@RequestMapping(value = "/audit_company", method = RequestMethod.POST)
	@ResponseBody
	public boolean audit_company(
			@RequestParam(value = "params[]") String params[],
			@RequestParam(value = "type") String type,
			HttpServletRequest request) {
		log.debug("审核企业信息");
		try {
			String auditType = null;
			if (params == null || type == null) {
				log.error("审核  参数有误！");
				return false;
			}
			if (type.equals("ok")) {
				log.debug("审核企业通过！");
				auditType = Constants.CheckStatus.OK.toString();
			} else {
				log.debug("审核企业未通过！");
				auditType = Constants.CheckStatus.WEITONGGUO.toString();
			}
			// 判断是集合函数单个
			if (params.length == 1) {
				Company company = new Company();
				company.setId(KitService.getInt(params[0]));
				company.setCheckStatus(auditType);
				companyService.update(company);
				log.debug("审核单个企业信息" + type + "    " + company);
			} else {
				log.debug("审核多个企业信息" + type + "    ");
				for (int i = 0; i < params.length; i++) {
					Company company = new Company();
					company.setId(KitService.getInt(params[i]));
					company.setCheckStatus(auditType);
					companyService.update(company);
					log.debug("审核多个企业信息" + type + "    " + company);
				}
			}
			return true;
		} catch (Exception e) {
			log.error("审核企业信息 出错");
			return false;
		}

	}

	/*
	 * 3.审核简历
	 */
	@RequestMapping(value = "/audit_resume", method = RequestMethod.POST)
	@ResponseBody
	public boolean audit_resume(
			@RequestParam(value = "params[]") String params[],
			@RequestParam(value = "type") String type,
			HttpServletRequest request) {
		log.debug("简历简历信息");
		try {

			String auditType = null;
			if (params == null || type == null) {
				log.error("审核职位  参数有误！");
				return false;
			}
			if (type.equals("ok")) {
				log.error("审核职位通过！");
				auditType = Constants.CheckStatus.OK.toString();
			} else {
				log.error("审核职位未通过！");
				auditType = Constants.CheckStatus.WEITONGGUO.toString();
			}

			// 判断是集合函数单个
			if (params.length == 1) {
				Resume resume = new Resume();
				resume.setId(KitService.getInt(params[0]));
				resume.setCheckStatus(auditType);
				resumeService.update(resume);
				log.debug("审核单个简历" + type + "    " + resume);
			} else {
				log.debug("审核多个简历" + type + "    ");
				for (int i = 0; i < params.length; i++) {
					Resume resume = new Resume();
					resume.setId(KitService.getInt(params[i]));
					resume.setCheckStatus(auditType);
					resumeService.update(resume);
					log.debug("审核多个简历" + type + "    " + resume);
				}
			}

			return true;

		} catch (Exception e) {
			log.error("审核简历 出错");
			e.printStackTrace();
			return false;
		}
	}

	/*
	 * 4.审核用户
	 */
	@RequestMapping(value = "/audit_user", method = RequestMethod.POST)
	@ResponseBody
	public boolean audit_user(
			@RequestParam(value = "params[]") String params[],
			@RequestParam(value = "type") String type,
			HttpServletRequest request) {
		log.debug("审核用户信息");
		try {

			String auditType = null;
			if (params == null || type == null) {
				log.error("审核职位  参数有误！");
				return false;
			}
			if (type.equals("ok")) {
				log.error("审核职位通过！");
				auditType = Constants.CheckStatus.OK.toString();
			} else {
				log.error("审核职位未通过！");
				auditType = Constants.CheckStatus.WEITONGGUO.toString();
			}

			// 判断是集合函数单个
			if (params.length == 1) {
				User user = new User();
				user.setId(KitService.getInt(params[0]));
				user.setCheckStatus(auditType);
				userService.update(user);
				log.debug("审核单个账户" + type + "    " + user);
			} else {
				log.debug("审核多个账户" + type + "    ");
				for (int i = 0; i < params.length; i++) {
					User user = new User();
					user.setId(KitService.getInt(params[i]));
					user.setCheckStatus(auditType);
					userService.update(user);
					log.debug("审核多个账户" + type + "    " + user);
				}
			}
			return true;
		} catch (Exception e) {
			log.error("审核用户 出错");
			return false;
		}
	}

	/*
	 * 转到 简历管理页面
	 */
	@RequestMapping(value = "/resume_list", method = RequestMethod.GET)
	public ModelAndView resume_list(HttpServletRequest request) {
		log.debug("goto：简历管理");
		return new ModelAndView("manage/resume-list");
	}

	/*
	 * 转到 简历详细页面
	 */
	@RequestMapping(value = "/resume_view", method = RequestMethod.GET)
	public ModelAndView resume_view(HttpServletRequest request) {
		log.debug("goto：简历管理详细页");
		return new ModelAndView("manage/resume-view");
	}

	/*
	 * 转到 企业详细页面
	 */
	@RequestMapping(value = "/company_view", method = RequestMethod.GET)
	public ModelAndView company_view(HttpServletRequest request) {
		log.debug("goto：企业详细页");
		return new ModelAndView("manage/company-view");
	}

	/*
	 * 转到用户管理页面
	 */
	@RequestMapping(value = "/user_list", method = RequestMethod.GET)
	public ModelAndView user_list(HttpServletRequest request) {
		log.debug("goto：用户管理");
		return new ModelAndView("manage/user-list");
	}

	/*
	 * 职位获取职位数据 根据id得到一个职位返回前台数据以供显示
	 */
	@RequestMapping(value = "/getOneForShowDate", method = RequestMethod.POST)
	@ResponseBody
	public Object getOneForShowData(HttpServletRequest req) {

		Job job = null;
		log.info("--- getOneForShow ---");
		String idStr = req.getParameter("id");
		int id = KitService.getInt(idStr);
		if (id < 0) {
			req.setAttribute("notice", "传递的参数有误!");
			return "/error";
		}

		try {
			job = jobService.getOneForShow(id);
		} catch (Exception e) {
			log.error("根据id得到一个职位返回前台数据。没有取到数据");
			return null;
		}

		log.info("job" + job);
		return job;
	}

	/*
	 * 获取企业信息数据 根据id得到一个职位返回前台数据以供显示
	 */
	@RequestMapping(value = "/getOneForCompanyDate", method = RequestMethod.POST)
	@ResponseBody
	public Object getOneForCompanyDate(HttpServletRequest req) {

		Company company = null;
		log.info("--- getOneForCompanyDate ---");
		String idStr = req.getParameter("id");
		int id = KitService.getInt(idStr);
		if (id <= 0) {
			log.error("companyViewError:getCompanySize()=0");
			return null;
		}
		try {
			company = companyService.getOneForShow(id);
		} catch (Exception e) {
			log.error("根据id得到一个企业信息返回前台数据。没有取到数据");
			return null;
		}
		log.info("companyView:" + company);
		return company;
	}

	/*
	 * 获取简历信息数据 根据id得到一个简历返回前台数据以供显示
	 */
	@RequestMapping(value = "/getOneForresumeDate", method = RequestMethod.POST)
	@ResponseBody
	public Object getOneForresumeDate(HttpServletRequest req) {

		Resume resume = null;
		log.info("--- getOneForresumeDate ---");
		String idStr = req.getParameter("id");
		int id = KitService.getInt(idStr);
		if (id < 0) {
			req.setAttribute("notice", "获取简历信息传递的参数有误!");
			return "/error";
		}
		try {
			resume = resumeService.getOneForShow(id);
		} catch (Exception e) {
			log.error("根据id得到一个简历信息返回前台数据。没有取到数据");
			return null;
		}
		log.info("resume" + resume);
		return resume;
	}

	/*
	 * 导出简历
	 */
	@RequestMapping(value = "/export-resume", method = RequestMethod.POST)
	@ResponseBody
	public String export_job(@RequestParam(value = "params[]") int params[],
			HttpServletRequest req) {
		String url = req.getRealPath("/");
		String filePath = resumeService.getBuildResume(params, url);
		log.info("resumefilePath : " + filePath);
		if (new File(url + filePath).exists()) {
			String destPath = req.getLocalAddr() + ":" + req.getLocalPort()
					+ req.getContextPath();
			log.info("resumedestPath : " + destPath);
			return "http://" + destPath + "/" + filePath;
		}
		return null;
	}

	/*
	 * 导出企业信息+企业发布的招聘职位信息
	 */
	@RequestMapping(value = "/export-company", method = RequestMethod.POST)
	@ResponseBody
	public String export_company(
			@RequestParam(value = "params[]") int params[],
			HttpServletRequest req) {

		String url = req.getRealPath("/");
		String filePath = companyService.getBuildCompany(params, url);
		log.info("companyfilePath : " + filePath);
		if (new File(url + filePath).exists()) {
			String destPath = req.getLocalAddr() + ":" + req.getLocalPort()
					+ req.getContextPath();
			log.info("companyfilePath : " + destPath);
			return "http://" + destPath + "/" + filePath;
		}
		return null;

	}

}
