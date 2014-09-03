package esd.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import esd.bean.Area;
import esd.bean.JobCategory;
import esd.bean.Parameter;
import esd.bean.Record;
import esd.bean.User;
import esd.service.AreaService;
import esd.service.JobCategoryService;
import esd.service.ParameterService;
import esd.service.PersonService;

@Controller
@RequestMapping("/person")
public class PersonController {

	private static Logger log = Logger.getLogger(PersonController.class);

	@Autowired
	private PersonService personService;
	
	@Autowired
	private ParameterService parameterService;
	
	@Autowired
	private JobCategoryService jcService;
	
	@Autowired
	private AreaService areaService;

	// 得到投递过的职位列表
	@RequestMapping("/getSentJob")
	public String getSentJob(HttpServletRequest req, HttpSession session) {
		log.info("----- getSentJob -----");
		User user = (User) session.getAttribute(Constants.USER);
		if (user == null) {
			return "redirect:/index.jsp";
		}
		List<Record> recordList = personService.getSentJob(user.getId(), 1, 30);
		log.info("recordList.size() = " + recordList);
		req.setAttribute("recordList", recordList);
		return "person/job-record";
	}

	
	
	//跳转到添加简历页面
	@RequestMapping("/gotoResumeAdd")
	public ModelAndView gotoResumeAdd(){
		log.info("----- getSentJob -----");
		ModelAndView mav = new ModelAndView("person/resume-add");
		List<Parameter> plist = parameterService.getAll();
		//残疾类别
		//残疾等级
		//残疾部位
		//户口状况
		//文化程度
		mav.addObject("params", plist);
		//职位类别
		List<JobCategory> jlist = jcService.getAll();
		mav.addObject("jcList", jlist);
		//工作地区
		List<Area> alist = areaService.getAll();
		mav.addObject("areaList", alist);
		return mav;
	}
	// @RequestMapping("/getSentJob")
	// @ResponseBody
	// public Map<String, Object> getSentJob(HttpServletRequest req,
	// HttpSession session) {
	// log.info("----- getSentJob -----");
	// // User user = (User)session.getAttribute(Constants.USER);
	// // if(user == null){
	// // return "redirect:/index.jsp";
	// // }
	// Map<String, Object> json = new HashMap<String, Object>();
	// String uidStr = req.getParameter("uid");
	// int uid = Constants.getInt(uidStr);
	// List<Job> list = personService.getSentJob(uid);
	// log.info("jobList.size() = "+list.size());
	// req.setAttribute("jobList", list);
	// json.put("jobList", list);
	// return json;
	// // return "person/sentJob";
	// }
}
