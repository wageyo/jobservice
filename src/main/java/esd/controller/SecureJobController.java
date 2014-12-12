package esd.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import esd.bean.Area;
import esd.bean.Company;
import esd.bean.Job;
import esd.bean.JobCategory;
import esd.bean.Parameter;
import esd.bean.Resume;
import esd.bean.User;
import esd.controller.Constants.Identity;
import esd.controller.Constants.Notice;
import esd.service.AreaService;
import esd.service.CompanyService;
import esd.service.CookieHelper;
import esd.service.JobCategoryService;
import esd.service.JobService;
import esd.service.KitService;
import esd.service.ParameterService;
import esd.service.PersonService;
import esd.service.RecordService;

@Controller
@RequestMapping("/secure/job")
public class SecureJobController {

	private static Logger log = Logger.getLogger(SecureJobController.class);
	@Autowired
	private JobService jobService;

	@Autowired
	private PersonService personService;

	@Autowired
	private CompanyService<Company> companyService;

	@Autowired
	private ParameterService pService;

	@Autowired
	private JobCategoryService jcService;

	@Autowired
	private AreaService areaService;

	@Autowired
	private RecordService recordService;

	// 保存职位
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Job job,HttpServletRequest request,  HttpServletResponse response,
			RedirectAttributes ra) {
		log.info("--- save post ---");
		log.info("************************************"
				+ request.getParameter("workPlace.code"));
		// 是否为企业用户
		String companyId = CookieHelper.getCookieValue(request, Constants.USERCOMPANYID);
		if (companyId == null || "".equals(companyId)) {
			ra.addFlashAttribute("messageType", "0");
			ra.addFlashAttribute("message", "请先完善公司信息");
			return "/secure/company/save";
		}
		//企业信息
		Integer cid = Integer.parseInt(companyId);
		Company company = companyService.getById(cid);
		log.info("job : " + job);
		if (job == null) {
			ra.addFlashAttribute("messageType", "0");
			ra.addFlashAttribute("notice", "保存的职位信息不能为空,发布失败!");
			return "/secure/job/save";
		}
		// 所属公司
		job.setCompany(company);
		job.setArea(company.getArea());
		// 转化有效天数为有效日期
		job.setEffectiveTime(KitService.getEffectiveTime(job.getEffectiveDays()
				.longValue()));
		boolean bl = jobService.save(job);
		if (!bl) {
			ra.addFlashAttribute("messageType", "0");
			ra.addFlashAttribute("notice", "保存职位出错!");
			return "/secure/job/save";
		}
		log.info("--- 发布职位成功 ---");
		request.setAttribute("notice", Notice.SUCCESS.toString());
		ra.addFlashAttribute("messageURL", "/secure/job/getManage");
		ra.addFlashAttribute("message", "保存职位信息成功!");
		return "redirect:/secure/job/save";
	}

	// 跳转到保存职位
	@RequestMapping(value = "/save", method = RequestMethod.GET)
	public String gotoResumeAdd(HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes ra) {
		log.info("--- save get---");
		// 先查看是否添加了企业信息, 没有添加的话则提示
		String companyId  = CookieHelper.getCookieValue(request, Constants.USERCOMPANYID);
		if (companyId == null || "".equals(companyId)) {
			ra.addFlashAttribute("messageType", "0");
			ra.addFlashAttribute("message", "填写完公司信息后才可以发布招聘信息");
			return "redirect:/secure/company/save";
		}
		//企业信息
		Integer cid = Integer.parseInt(companyId);
		Company company = companyService.getById(cid);
		request.setAttribute("company", company);
		List<Parameter> plist = pService.getAll();
		// 各种参数
		request.setAttribute("params", plist);
		// 职位类别
		List<JobCategory> jlist = jcService.getJcLv1();
		request.setAttribute("jcList", jlist);
		// 工作地区
		List<Area> provinceList = areaService.getProvinceList();
		request.setAttribute("provinceList", provinceList);
		
		String acode = company.getArea().getCode();
		
		// 查看企业信息审核开关是否打开
		boolean bl = pService.getSwitchStatus(Constants.Switch.COMPANY
				.toString(), acode);
		// 如果company审核开关打开的话,验证企业用户信息是否通过了审核
		if (bl) {
			if (company.getCheckStatus() != null) {
				// 如果是 待审核 状态 , 则跳转到个人中心, 等待管理员审核
				if (Constants.CheckStatus.DAISHEN.toString().equals(
						company.getCheckStatus())) {
					ra.addFlashAttribute("messageURL", "/user/goCenter");
					ra.addFlashAttribute("message",
							"您填写的企业信息正在审核中, 暂时还没有发布招聘信息的权限,请等待管理员审核通过后再发布招聘信息.");
					return "redirect:/user/goCenter";
				}
				// 如果是 被打回 状态 , 则跳转到企业信息修改页面
				if (Constants.CheckStatus.WEITONGGUO.toString().equals(
						company.getCheckStatus())) {
					ra.addFlashAttribute("messageURL", "/secure/company/update");
					ra.addFlashAttribute("message", "您填写的企业信息没有通过审核, 请重新填写!");
					return "redirect:/secure/company/update";
				}
			}
		}
		return "company/job-add";
	}

	// 删除一个职位简历
	@RequestMapping("/delete")
	public String delete(HttpServletRequest request) {
		log.info("--- delete ---");
		String idStr = request.getParameter("id");
		int id = KitService.getInt(idStr);
		if (id < 0) {
			request.setAttribute("notice", "获取参数失败!");
			return "forward:/secure/job/getManage";
		}
		boolean bl = jobService.delete(id);
		log.info("jobDelete bl = " + bl);
		if (!bl) {
			request.setAttribute("notice", "删除职位出错!");
			return "forward:/secure/job/getManage";
		}
		log.info("--- 删除职位成功 ---");
		return "forward:/secure/job/getManage";
	}

	// 保存编辑后的职位
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Job job, RedirectAttributes ra) {
		log.info("--- update ---");
		log.info("job " + job);
		// 获得形成的简历对象
		if (job == null) {
			ra.addFlashAttribute("message", "传递数据出错!");
			return "redirect:/secure/job/update";
		}
		boolean bl = jobService.update(job);
		log.info("jobEditSave bl = " + bl);
		if (!bl) {
			ra.addFlashAttribute("messageURL", "/secure/job/getManage");
			ra.addFlashAttribute("message", "更新职位失败!");
			return "redirect:/secure/job/update";
		}
		ra.addFlashAttribute("messageURL", "/secure/job/getManage");
		ra.addFlashAttribute("message", "更新职位信息成功!");
		return "redirect:/secure/job/update";
	}

	// 跳转到修改职位页面
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String getOne(HttpServletRequest request) {
		log.info("--- update get ---");
		String idStr = request.getParameter("id");
		log.info("idStr = " + idStr);
		int id = KitService.getInt(idStr);
		if (id < 0) {
			return "forward:/secure/job/getManage";
		}
		Job job = jobService.getById(id);
		request.setAttribute("job", job);
		List<Parameter> plist = pService.getAll();
		// 各种参数
		request.setAttribute("params", plist);
		// 职位类别
		List<JobCategory> jlist = jcService.getAll();
		request.setAttribute("jcList", jlist);
		// 工作地区
		List<Area> alist = areaService.getProvinceList();
		request.setAttribute("provinceList", alist);
		return "company/job-edit";
	}

	// 根据企业id, 得到该企业所发布的职位
	@RequestMapping("/getByCompany")
	@ResponseBody
	public Map<String, Object> getByCompany(HttpServletRequest request) {
		log.info("--- getByCompany ---");
		Map<String, Object> json = new HashMap<String, Object>();
		// 得到当前企业用户
		String idStr = request.getParameter("companyid");
		int id = KitService.getInt(idStr);
		if (id < 0) {
			json.put("notice", Notice.ERROR.toString());
			return json;
		}
		String startStr = request.getParameter("page");
		int start = KitService.getInt(startStr);
		if (start < 0) {
			json.put("notice", Notice.ERROR.toString());
			return json;
		}
		List<Job> jobList = jobService.getByCompany(start, 1, Constants.SIZE);
		log.info(" jobList.size() = " + jobList.size());
		json.put("notice", Notice.SUCCESS);
		json.put("jobList", jobList);
		return json;
	}

	// 得到当前企业用户的所有职位列表
	@RequestMapping("/getManage")
	public String getManage(HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes ra) {
		log.info("--- getManage ---");
		// 先查看是否添加了企业信息, 没有添加的话则提示
		String companyId  = CookieHelper.getCookieValue(request, Constants.USERCOMPANYID);
		if (companyId == null || "".equals(companyId)) {
			ra.addFlashAttribute("messageType", "0");
			ra.addFlashAttribute("message", "填写完公司信息后才可以发布招聘信息");
			return "redirect:/secure/company/save";
		}
		//企业信息
		Integer cid = Integer.parseInt(companyId);
		Company company = companyService.getById(cid);

		String startStr = request.getParameter("page");
		int start = KitService.getInt(startStr);
		List<Job> jobList = jobService.getByCompany(company.getId(), start,
				Constants.SIZE);
		log.info(" jobList.size() = " + jobList.size());
		request.setAttribute("jobList", jobList);
		int totalCount = jobService.getByCompanyCount(company.getId());
		request.setAttribute("totalCount", totalCount);
		request.setAttribute("currentPage", start);
		request.setAttribute(
				"totalPages",
				totalCount % Constants.SIZE == 0 ? (totalCount / Constants.SIZE)
						: (totalCount / Constants.SIZE + 1));
		return "/company/job-manage";
	}

//	// 获得职位总个数
//	@RequestMapping("/getTotalCount")
//	@ResponseBody
//	public Map<String, Object> getTotalCount(HttpServletRequest request) {
//		log.info("--- getTotalCount ---");
//		String areaCode = request.getParameter("areaCode");
//		if (areaCode == null) {
//			areaCode = "10000000";
//		}
//		Map<String, Object> json = new HashMap<String, Object>();
//		Job job = new Job();
//		job.setArea(new Area(areaCode));
//		int total = jobService.getTotalCount(job,Boolean.TRUE);
//		json.put("totalCount", total);
//		return json;
//	}

	// 向一个职位投递自己的默认的简历
	@RequestMapping("/sendResume")
	@ResponseBody
	public Map<String, Object> sendResume(HttpServletRequest request,
			HttpServletResponse response) {
		log.info("--- sendResume ---");
		Map<String, Object> json = new HashMap<String, Object>();
		String userId = CookieHelper.getCookieValue(request, Constants.USERID);
		if (userId == null || "".equals(userId)) {
			log.info("用户未登陆");
			json.put("notice", "请登录后操作");
			return json;
		}
		String identity = CookieHelper.getCookieValue(request, Constants.USERIDENTITY);
		if (!Identity.PERSON.getValue().equals(identity)) {
			log.info("对不起, 你不是个人用户, 不能投递简历!");
			json.put("notice", "对不起, 你不是个人用户, 不能投递简历!");
			return json;
		}
		// 得到职位id
		String jidStr = request.getParameter("jid");
		int jid = KitService.getInt(jidStr);
		if (jid < 0) {
			json.put("notice", "传递的参数有误.");
			return json;
		}
		Job job = jobService.getById(jid);
		// 得到当前个人用户的默认简历
		Integer uid = Integer.parseInt(userId);
		Resume r = personService.getDefaultResume(uid);
		if (r == null) {
			json.put("notice", "你还没有创建简历哦,创建一份简历后再进行操作!");
			return json;
		}
		// 检查7天内是否投递过, 如果投递过则不可重复投递
		int isSend = recordService.checkSentInSomeDays(uid, jid, null,
				null, Boolean.TRUE);
		if (isSend > 0) {
			json.put("notice", "7天内只能向同一职位投递一次简历, 请稍后操作.");
			return json;
		}
		// 向职位投递简历
		boolean bl = recordService.sendResumeOrInvite(r, job, Boolean.TRUE);
		if (!bl) {
			json.put("notice", "投递简历失败,请重新操作!");
			return json;
		}
		json.put("notice", Notice.SUCCESS.toString());
		return json;
	}
}
