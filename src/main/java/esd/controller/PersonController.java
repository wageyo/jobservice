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
	
	//跳转到添加简历页面
	@RequestMapping("/gotoResumeAdd")
	public ModelAndView gotoResumeAdd(){
		log.info("----- getSentJob -----");
		ModelAndView mav = new ModelAndView("person/resume-add");
		List<Parameter> plist = parameterService.getAll();
		mav.addObject("params", plist);
		//职位类别
		List<JobCategory> jlist = jcService.getAll();
		mav.addObject("jcList", jlist);
		//工作地区
		List<Area> alist = areaService.getAll();
		mav.addObject("areaList", alist);
		return mav;
	}
}
