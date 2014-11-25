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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import esd.bean.Area;
import esd.bean.JobCategory;
import esd.bean.Parameter;
import esd.bean.Record;
import esd.bean.Resume;
import esd.bean.UnempManage;
import esd.bean.User;
import esd.bean.WorkExperience;
import esd.controller.Constants.Identity;
import esd.controller.Constants.Notice;
import esd.service.AreaService;
import esd.service.CookieHelper;
import esd.service.JobCategoryService;
import esd.service.KitService;
import esd.service.ParameterService;
import esd.service.PersonService;
import esd.service.ResumeService;
import esd.service.UserService;

@Controller
@RequestMapping("/secure/resume")
public class SecureResumeController {

	@Autowired
	private UserService userService;

	@Autowired
	private ResumeService resumeService;

	@Autowired
	private PersonService personService;

	@Autowired
	private ParameterService parameterService;

	@Autowired
	private JobCategoryService jcService;

	@Autowired
	private AreaService areaService;

	private static Logger log = Logger.getLogger(SecureResumeController.class);

	// 保存简历
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Resume resume,HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		log.info("--- save post---");
		String userId = CookieHelper.getCookieValue(request, Constants.USERID);
		if(userId == null || "".equals(userId)){
			redirectAttributes.addFlashAttribute("messageType", "0");
			redirectAttributes.addFlashAttribute("message", "请刷新页面重新尝试!");
			return "redirect:/index";
		}
		log.info("得到resume为 : " + resume);
		// 形成的简历对象
		if (resume == null) {
			redirectAttributes.addFlashAttribute("messageType", "0");
			redirectAttributes.addFlashAttribute("message", "传递的数据为空!");
			return "redirect:/secure/resume/save";
		}
		// 所属人
		Integer uid = Integer.parseInt(userId);
		User user = userService.getById(uid);
		resume.setUser(user);
		// 所属地区
		resume.setArea(user.getArea());
		boolean bl = resumeService.save(resume);
		log.info("--------个人工作经历------------");
		// // 保存个人工作经历
		String[] workTimes = request.getParameterValues("workTime");
		String[] companyNames = request.getParameterValues("companyName");
		String[] jobNames = request.getParameterValues("jobName");
		String[] jobContents = request.getParameterValues("jobContent");
		String[] leaveReasons = request.getParameterValues("leaveReason");
		if (jobNames != null) {
			for (int i = 0; i < workTimes.length; i++) {
				if (workTimes[i] == null || "".equals(workTimes[i])) {
					continue;
				}
				WorkExperience we = new WorkExperience();
				we.setWorkTime(workTimes[i]);
				we.setCompanyName(companyNames[i]);
				we.setJobName(jobNames[i]);
				we.setLeaveReason(leaveReasons[i]);
				we.setJobContent(jobContents[i]);
				we.setResume(resume);
				resumeService.save(we);
			}
		}
		log.info("bl = " + bl);
		if (!bl) {
			redirectAttributes.addFlashAttribute("messageType", "0");
			redirectAttributes.addFlashAttribute("message", "添加简历失败");
			return "redirect:/secure/resume/save";
		}
		redirectAttributes.addFlashAttribute("messageURL",
				"/secure/resume/getManage");
		redirectAttributes.addFlashAttribute("message", "简历添加完成");
		return "redirect:/secure/resume/save";
	}

	// 跳转到添加简历页面
	@RequestMapping(value = "/save", method = RequestMethod.GET)
	public ModelAndView gotoResumeAdd() {
		log.info("----- save get -----");
		ModelAndView mav = new ModelAndView("person/resume-add");
		List<Parameter> plist = parameterService.getAll();
		// 各种参数
		mav.addObject("params", plist);
		// 职位类别
		List<JobCategory> jlist = jcService.getJcLv1();
		mav.addObject("jcList", jlist);
		// 工作地区
		List<Area> alist = areaService.getProvinceList();
		mav.addObject("provinceList", alist);
		return mav;
	}

	// 删除个人的一份简历
	@RequestMapping("/delete")
	public String delete(HttpServletRequest req) {
		log.info("--- delete ---");
		String idStr = req.getParameter("id");
		int id = KitService.getInt(idStr);
		if (id < 0) {
			return "forward:/secure/resume/getManage";
		}
		boolean bl = resumeService.delete(id);
		log.info("resumeDelete bl = " + bl);
		return "forward:/secure/resume/getManage";
	}

	// 得到一个要更新一份简历
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String update(HttpServletRequest req) {
		log.info("--- update get ---");
		String idStr = req.getParameter("id");
		log.info("idStr = " + idStr);
		int id = KitService.getInt(idStr);
		if (id < 0) {
			return "forward:/secure/resume/getManage";
		}
		Resume resume = resumeService.getById(id);
		req.setAttribute("resume", resume);
		List<Parameter> plist = parameterService.getAll();
		req.setAttribute("params", plist);
		// 职位类别
		List<JobCategory> jlist = jcService.getJcLv1();
		req.setAttribute("jcList", jlist);
		// // 工作地区
		List<Area> alist = areaService.getProvinceList();
		req.setAttribute("provinceList", alist);
		return "/person/resume-edit";
	}

	// 更新简历
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(HttpServletRequest req, Resume resume,
			RedirectAttributes ra) {
		log.info("--- update ---");
		// 获得形成的简历对象
		log.info(resume);
		boolean bl = resumeService.update(resume);
		if (!bl) {
			ra.addFlashAttribute("messagetType", "0");
			ra.addFlashAttribute("message", "修改简历失败!");
			return "redirect:/secure/resume/update";
		}

		log.info("--------个人工作经历------------");
		// // 保存个人工作经历
		String[] wids = req.getParameterValues("wid");
		String[] workTimes = req.getParameterValues("workTime");
		String[] companyNames = req.getParameterValues("companyName");
		String[] jobNames = req.getParameterValues("jobName");
		String[] jobContents = req.getParameterValues("jobContent");
		String[] leaveReasons = req.getParameterValues("leaveReason");
		String[] updateChecks = req.getParameterValues("wUpdateCheck");
//		log.info("wids : " + workTimes);
//		log.info("workTimes : " + workTimes);
//		log.info("companyNames : " + companyNames);
//		log.info("jobNames : " + jobNames);
//		log.info("jobContents : " + jobContents);
		WorkExperience we = null;
		if (wids != null) {
			for (int i = 0; i < wids.length; i++) {
				log.info("wid  = " + wids[i]);
				if (wids[i] == null || "".equals(wids[i])) {
					continue;
				}
				we = new WorkExperience();
				int wid = KitService.getInt(wids[i]);
				we.setId(wid);
				we.setWorkTime(workTimes[i]);
				we.setCompanyName(companyNames[i]);
				we.setJobName(jobNames[i]);
				we.setLeaveReason(leaveReasons[i]);
				we.setJobContent(jobContents[i]);
				we.setResume(resume);
				int updateCheck = KitService.getInt(updateChecks[i]);
				we.setUpdateCheck(updateCheck);
				boolean wbl = resumeService.update(we);
				if (!wbl) {
					ra.addFlashAttribute("messagetType", "0");
					ra.addFlashAttribute("message", "修改简历失败!");
					return "redirect:/secure/resume/update";
				}
			}
		}
		ra.addFlashAttribute("messageURL", "/secure/resume/getManage");
		ra.addFlashAttribute("message", "修改成功!");
		return "redirect:/secure/resume/update";
	}

	// 根据id得到一个简历返回前台
	@RequestMapping("/getOneForShow")
	public String getOneForShow(HttpServletRequest req) {
		log.info("--- getOneForShow ---");
		String idStr = req.getParameter("id");
		log.info("idStr = " + idStr);
		int id = KitService.getInt(idStr);
		if (id < 0) {
			return "forward:/resume/search";
		}
		Resume resume = resumeService.getById(id);
		req.setAttribute("resume", resume);
		return "emp/emp-detail";
	}

	// 得到当前个人用户的所有简历
	@RequestMapping("/getManage")
	public String getManage(HttpServletRequest request, HttpServletResponse response,RedirectAttributes ra) {
		log.info("--- getManage ---");
		// 得到当前用户
		String userId = CookieHelper.getCookieValue(request, Constants.USERID);
		if(userId == null || "".equals(userId)){
			ra.addFlashAttribute("messageType", "0");
			ra.addFlashAttribute("message", "请刷新页面重新尝试!");
			return "/user/goCenter";
		}
		Integer uid = Integer.parseInt(userId);
		List<Resume> resumeList = resumeService.getByUser(uid);
		log.info("user'resume = " + resumeList.size());
		request.setAttribute("resumeList", resumeList);
		return "/person/resume-manage";
	}

	// 根据个人用户的user id, 得到他所有的简历
	@RequestMapping("/getByUser")
	@ResponseBody
	public Map<String, Object> getByUser(HttpServletRequest request) {
		log.info("--- getByUser ---");
		Map<String, Object> json = new HashMap<String, Object>();
		// 得到当前用户
		String idStr = request.getParameter("userid");
		int id = KitService.getInt(idStr);
		if (id < 0) {
			json.put("notice", Notice.ERROR);
			return json;
		}
		List<Resume> resumeList = resumeService.getByUser(id);
		log.info("user'resume = " + resumeList.size());
		json.put("resumeList", resumeList);
		return json;
	}

	// 根据个人用户的user id, 得到他默认选中的简历
	@RequestMapping("/getDefaultResume")
	@ResponseBody
	public Map<String, Object> getDefaultResume(HttpServletRequest request) {
		log.info("--- getDefaultResume ---");
		Map<String, Object> json = new HashMap<String, Object>();
		// 得到当前用户
		String useridStr = request.getParameter("userid");
		int userid = KitService.getInt(useridStr);
		if (userid < 0) {
			json.put("notice", Notice.ERROR);
			return json;
		}
		Resume resume = resumeService.getDefaultResume(userid);
		log.info("default resume = " + resume);
		if (resume == null) {
			json.put("notice", Notice.FAILURE);
			return json;
		}
		json.put("notice", Notice.SUCCESS);
		json.put("resume", resume);
		return json;
	}

	// 得到投递过的职位列表
	@RequestMapping(value = "/getSentJob/{page}", method = RequestMethod.GET)
	public String getSentJob(HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes ra, @PathVariable(value = "page") Integer page) {
		log.info("----- getSentJob -----");
		// 得到当前用户
		String userId = CookieHelper.getCookieValue(request, Constants.USERID);
		if(userId == null || "".equals(userId)){
			ra.addFlashAttribute("messageType", "0");
			ra.addFlashAttribute("message", "请刷新页面重新尝试!");
			return "/user/goCenter";
		}
		String identity = CookieHelper.getCookieValue(request, Constants.USERIDENTITY);
		if (!(Identity.PERSON.getValue()).equals(identity)) {
			ra.addFlashAttribute("message", "权限不足!");
			ra.addFlashAttribute("messageType", "0");
			return "/user/goCenter";
		}

		Integer uid = Integer.parseInt(userId);
		List<Record> recordList = personService.getSentJob(uid, page,
				Constants.SIZE);
		request.setAttribute("recordList", recordList);
		int totalCount = personService.getSentJobCount(uid);
		request.setAttribute("totalCount", totalCount);
		request.setAttribute("currentPage", page);
		request.setAttribute(
				"totalPages",
				totalCount % Constants.SIZE == 0 ? (totalCount / Constants.SIZE)
						: (totalCount / Constants.SIZE + 1));
		return "person/job-record";
	}

	// 得到 收到的面试邀请
	@RequestMapping(value = "/getReceivedInvite/{page}", method = RequestMethod.GET)
	public String getReceivedInvite(HttpServletRequest request,HttpServletResponse response, RedirectAttributes ra,
			@PathVariable(value = "page") Integer page) {
		log.info("----- getSentJob -----");
		
		// 得到当前用户
		String userId = CookieHelper.getCookieValue(request, Constants.USERID);
		if(userId == null || "".equals(userId)){
			ra.addFlashAttribute("messageType", "0");
			ra.addFlashAttribute("message", "请刷新页面重新尝试!");
			return "/user/goCenter";
		}
		String identity = CookieHelper.getCookieValue(request, Constants.USERIDENTITY);
		if (!(Identity.PERSON.getValue()).equals(identity)) {
			ra.addFlashAttribute("message", "权限不足!");
			ra.addFlashAttribute("messageType", "0");
			return "/user/goCenter";
		}
		Integer uid = Integer.parseInt(userId);

		List<Record> recordList = personService.getSengetReceivedInvitetJob(
				uid, page, Constants.SIZE);
		request.setAttribute("recordList", recordList);
		int totalCount = personService.getSengetReceivedInvitetJobCount(uid);
		request.setAttribute("totalCount", totalCount);
		request.setAttribute("currentPage", page);
		request.setAttribute(
				"totalPages",
				totalCount % Constants.SIZE == 0 ? (totalCount / Constants.SIZE)
						: (totalCount / Constants.SIZE + 1));
		return "person/invite-record";
	}

	// 保存工作经历
	@RequestMapping(value = "/saveExperience", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveExperience(WorkExperience we) {
		log.info("--- saveExperience ---");
		Map<String, Object> json = new HashMap<String, Object>();
		// 得到工作经历
		if (we == null) {
			json.put("notice", Notice.FAILURE.toString());
			return json;
		}
		// 保存
		boolean bl = resumeService.save(we);
		if (!bl) {
			json.put("notice", Notice.FAILURE.toString());
		} else {
			json.put("notice", Notice.SUCCESS.toString());
		}
		return json;
	}

	// 删除工作经历
	@RequestMapping("/deleteExperience/{weid}")
	@ResponseBody
	public Map<String, Object> deleteWorkExperience(
			@PathVariable(value = "weid") Integer weid) {
		log.info("--- deleteExperience ---");
		Map<String, Object> json = new HashMap<String, Object>();
		boolean bl = resumeService.deleteWorkExperience(weid);
		if (!bl) {
			json.put("notice", Notice.FAILURE.toString());
			return json;
		}
		json.put("notice", Notice.SUCCESS.toString());
		return json;
	}

	// 更新工作经历
	@RequestMapping(value = "/updateExperience", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateExperience(WorkExperience we) {
		log.info("--- saveExperience ---");
		Map<String, Object> json = new HashMap<String, Object>();
		// 得到工作经历
		if (we == null) {
			json.put("notice", Notice.FAILURE.toString());
			return json;
		}
		// 更新
		boolean bl = resumeService.update(we);
		if (!bl) {
			json.put("notice", Notice.FAILURE.toString());
		} else {
			json.put("notice", Notice.SUCCESS.toString());
		}
		return json;
	}

	// 保存测评办理情况
	@RequestMapping(value = "/saveUnempManage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveUnempManage(UnempManage um) {
		log.info("--- saveUnempManage ---");
		Map<String, Object> json = new HashMap<String, Object>();
		// 得到工作经历
		if (um == null) {
			json.put("notice", Notice.FAILURE.toString());
			return json;
		}
		// 保存
		boolean bl = resumeService.save(um);
		if (!bl) {
			json.put("notice", Notice.FAILURE.toString());
		} else {
			json.put("notice", Notice.SUCCESS.toString());
		}
		return json;
	}

	// 删除测评办理情况
	@RequestMapping("/deleteUnempManage/{umid}")
	@ResponseBody
	public Map<String, Object> deleteUnempManage(
			@PathVariable(value = "umid") Integer umid) {
		log.info("--- deleteUnempManage ---");
		Map<String, Object> json = new HashMap<String, Object>();
		boolean bl = resumeService.deleteUnempManage(umid);
		if (!bl) {
			json.put("notice", Notice.FAILURE.toString());
			return json;
		}
		json.put("notice", Notice.SUCCESS.toString());
		return json;
	}

	// 更新测评办理情况
	@RequestMapping(value = "/updateUnempManage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateUnempManagee(UnempManage um) {
		log.info("--- saveUnempManage ---");
		Map<String, Object> json = new HashMap<String, Object>();
		// 得到测评办理情况
		if (um == null) {
			json.put("notice", Notice.FAILURE.toString());
			return json;
		}
		// 更新
		boolean bl = resumeService.update(um);
		if (!bl) {
			json.put("notice", Notice.FAILURE.toString());
		} else {
			json.put("notice", Notice.SUCCESS.toString());
		}
		return json;
	}

	// // 保存教育背景
	// @RequestMapping("/saveEducation")
	// @ResponseBody
	// public Map<String, Object> saveEducation(HttpServletRequest req) {
	// log.info("--- saveEducation ---");
	// Map<String, Object> json = new HashMap<String, Object>();
	// // 得到当前用户
	// EducationBackground eb = getEducation(req);
	// boolean bl = resumeService.save(eb);
	// if (!bl) {
	// json.put("notice", Notice.FAILURE);
	// return json;
	// }
	// json.put("notice", Notice.SUCCESS);
	// return json;
	// }
	//
	// // 删除教育背景
	// @RequestMapping("/deleteEducation")
	// @ResponseBody
	// public Map<String, Object> deleteEducation(HttpServletRequest req) {
	// log.info("--- deleteEducation ---");
	// Map<String, Object> json = new HashMap<String, Object>();
	// String idStr = req.getParameter("id");
	// int id = KitService.getInt(idStr);
	// if (id < 0) {
	// json.put("notice", Notice.ERROR);
	// return json;
	// }
	// boolean bl = resumeService.deleteEducationBackground(id);
	// if (!bl) {
	// json.put("notice", Notice.FAILURE);
	// return json;
	// }
	// json.put("notice", Notice.SUCCESS);
	// return json;
	// }
	//
	// // 更改教育背景
	// @RequestMapping("/updateEducation")
	// @ResponseBody
	// public Map<String, Object> updateEducation(HttpServletRequest req) {
	// log.info("--- updateEducation ---");
	// Map<String, Object> json = new HashMap<String, Object>();
	// String idStr = req.getParameter("id");
	// int id = KitService.getInt(idStr);
	// if (id < 0) {
	// json.put("notice", Notice.ERROR);
	// return json;
	// }
	// EducationBackground eb = getEducation(req);
	// eb.setId(id);
	// boolean bl = resumeService.update(eb);
	// if (!bl) {
	// json.put("notice", Notice.FAILURE);
	// return json;
	// }
	// json.put("notice", Notice.SUCCESS);
	// return json;
	// }
	//
	// // 得到教育信息
	// private EducationBackground getEducation(HttpServletRequest req) {
	// String time = req.getParameter("time");
	// String school = req.getParameter("school");
	// String major = req.getParameter("major");
	// String education = req.getParameter("education");
	// String certificate = req.getParameter("certificate");
	// String ridStr = req.getParameter("rid");
	// int id = KitService.getInt(ridStr);
	// if (id < 0) {
	// return null;
	// }
	// EducationBackground eb = new EducationBackground();
	// eb.setTime(time);
	// eb.setSchool(school);
	// eb.setMajor(major);
	// eb.setEducation(education);
	// eb.setCertificate(certificate);
	// eb.setResume(new Resume(id));
	// return eb;
	// }
	//
	// // 保存家庭成员
	// @RequestMapping("/saveFamily")
	// @ResponseBody
	// public Map<String, Object> saveFamily(HttpServletRequest req) {
	// log.info("--- saveFamily ---");
	// Map<String, Object> json = new HashMap<String, Object>();
	// // 得到当前用户
	// FamilyMember fm = getFamily(req);
	// boolean bl = resumeService.save(fm);
	// if (!bl) {
	// json.put("notice", Notice.FAILURE);
	// return json;
	// }
	// json.put("notice", Notice.SUCCESS);
	// return json;
	// }
	//
	// // 删除家庭成员
	// @RequestMapping("/deleteFamily")
	// @ResponseBody
	// public Map<String, Object> deleteFamily(HttpServletRequest req) {
	// log.info("--- deleteFamily ---");
	// Map<String, Object> json = new HashMap<String, Object>();
	// String idStr = req.getParameter("id");
	// int id = KitService.getInt(idStr);
	// if (id < 0) {
	// json.put("notice", Notice.ERROR);
	// return json;
	// }
	// boolean bl = resumeService.deleteFamilyMember(id);
	// if (!bl) {
	// json.put("notice", Notice.FAILURE);
	// return json;
	// }
	// json.put("notice", Notice.SUCCESS);
	// return json;
	// }
	//
	// // 更改家庭成员
	// @RequestMapping("/updateFamily")
	// @ResponseBody
	// public Map<String, Object> updateFamily(HttpServletRequest req) {
	// log.info("--- updateFamily ---");
	// Map<String, Object> json = new HashMap<String, Object>();
	// String idStr = req.getParameter("id");
	// int id = KitService.getInt(idStr);
	// if (id < 0) {
	// json.put("notice", Notice.ERROR);
	// return json;
	// }
	// FamilyMember eb = getFamily(req);
	// eb.setId(id);
	// boolean bl = resumeService.update(eb);
	// if (!bl) {
	// json.put("notice", Notice.FAILURE);
	// return json;
	// }
	// json.put("notice", Notice.SUCCESS);
	// return json;
	// }
	//
	// // 获得家庭成员信息
	// private FamilyMember getFamily(HttpServletRequest req) {
	// String relation = req.getParameter("relation");
	// String name = req.getParameter("name");
	// String ageStr = req.getParameter("age");
	// int age = KitService.getInt(ageStr);
	// if (age < 0) {
	// return null;
	// }
	// String unit = req.getParameter("unit");
	// String position = req.getParameter("position");
	// String phone = req.getParameter("phone");
	// String ridStr = req.getParameter("rid");
	// int id = KitService.getInt(ridStr);
	// if (id < 0) {
	// return null;
	// }
	// FamilyMember fm = new FamilyMember();
	// fm.setRelation(relation);
	// fm.setName(name);
	// fm.setAge(age);
	// fm.setUnit(unit);
	// fm.setPosition(position);
	// fm.setPhone(phone);
	// fm.setResume(new Resume(id));
	// return fm;
	//
	// }
	//

	//
	// // 更改工作经历
	// @RequestMapping("/updateExperience")
	// @ResponseBody
	// public Map<String, Object> updateExperience(HttpServletRequest req) {
	// log.info("--- updateExperience ---");
	// Map<String, Object> json = new HashMap<String, Object>();
	// String idStr = req.getParameter("id");
	// int id = KitService.getInt(idStr);
	// if (id < 0) {
	// json.put("notice", Notice.ERROR);
	// return json;
	// }
	// WorkExperience we = getExperience(req);
	// we.setId(id);
	// boolean bl = resumeService.update(we);
	// if (!bl) {
	// json.put("notice", Notice.FAILURE);
	// return json;
	// }
	// json.put("notice", Notice.SUCCESS);
	// return json;
	// }
	//
	// // 获得工作经历
	// private WorkExperience getExperience(HttpServletRequest req) {
	// String companyName = req.getParameter("companyName");
	// String jobName = req.getParameter("jobName");
	// String jobContent = req.getParameter("jobContent");
	// String workTime = req.getParameter("workTime");
	// String evaluation = req.getParameter("evaluation");
	// String ridStr = req.getParameter("rid");
	// int id = KitService.getInt(ridStr);
	// if (id < 0) {
	// return null;
	// }
	// WorkExperience we = new WorkExperience();
	// we.setCompanyName(companyName);
	// we.setJobName(jobName);
	// we.setJobContent(jobContent);
	// we.setWorkTime(workTime);
	// we.setEvaluation(evaluation);
	// we.setResume(new Resume(id));
	// return we;
	// }

}
