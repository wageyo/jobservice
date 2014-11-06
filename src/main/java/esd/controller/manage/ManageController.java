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

	@Autowired
	private JobService jobService;

	@Autowired
	private ResumeService resumeService;

	// 转到管理 主页
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request) {
		return new ModelAndView("manage/index");
	}

	
	
	
		
		
	
	// 转到首页 提示页
	@RequestMapping(value = "/prompt", method = RequestMethod.GET)
	public ModelAndView Prompt(HttpServletRequest request) {

		return new ModelAndView("manage/prompt");
	}






}
