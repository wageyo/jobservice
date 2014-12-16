package esd.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.util.JSONPObject;

import esd.bean.Area;
import esd.bean.Company;
import esd.bean.Job;
import esd.bean.JobCategory;
import esd.bean.News;
import esd.bean.Parameter;
import esd.bean.Resume;
import esd.bean.User;
import esd.controller.Constants.Identity;
import esd.service.AreaService;
import esd.service.CompanyService;
import esd.service.CookieHelper;
import esd.service.JobCategoryService;
import esd.service.JobService;
import esd.service.KitService;
import esd.service.NewsService;
import esd.service.ParameterService;
import esd.service.ResumeService;
import esd.service.UserService;

/**
 * 首页  菜单, 跳转用controller
 * @author Administrator
 *
 */
@Controller
public class IndexController {
	private static Logger log = Logger.getLogger(IndexController.class);

	@Autowired
	private UserService<User> userService;
	
	@Autowired
	private CompanyService<Company> companyService;

	@Autowired
	private JobService jobService;

	@Autowired
	private ResumeService resumeService;

	@Autowired
	private ParameterService parameterService;

	@Autowired
	private JobCategoryService jcService;

	@Autowired
	private AreaService areaService;

	@Autowired
	private NewsService newsService;

	//首页
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response) {
		log.debug("=========================" + request.getRequestURI());
		//如果存在从网站群接收来的用户名, 密码, 则证明是从网站群登陆过来的, 则将该用户的相关信息和登陆一样, 放到cookie中
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if(username != null && !"".equals(username) && password != null && !"".equals(password)){
			User user = userService.check(username);
			/*********** 设置用户信息到cookie中 ************/
			if (user.getIdentity().equals(Identity.COMPANY.toString())) {
				Company company = companyService.getByAccount(user.getId());
				log.debug("company " + company);
				if(company != null){
					CookieHelper.setCookie(response, Constants.USERCOMPANYID, String.valueOf(company.getId()));
				}
			}
			log.debug("login: " + user);
			CookieHelper.setCookie(response, Constants.USERID, String.valueOf(user.getId()));
			CookieHelper.setCookie(response, Constants.USERNAME,user.getLoginName());
			CookieHelper.setCookie(response, Constants.USERIDENTITY,user.getIdentity());
			CookieHelper.setCookie(response, Constants.USERAUTHORITY,String.valueOf(user.getAuthority()));
			CookieHelper.setCookie(response, Constants.USERREGISTERTIME,KitService.dateForShow(user.getCreateDate()));
		}
		
 		//①先查看request中有没有传过来的acode, 
		String acode= request.getParameter("acode");
		if(acode != null && !"".equals(acode)){
			/**** 不为空时, 则表示为从残联网站跳转过来的, 则清除原来可能存在的所有用户, 地区等cookie信息	****/
			CookieHelper.killAllCookie(response, true);
			//②不为空则是第一次进来, 将其中的acode放到cookie中
			CookieHelper.setCookie(response, Constants.AREA, acode, Integer.MAX_VALUE);
		}else{
			//③为空在则检查cookie是中没有地区信息
			acode = CookieHelper.getCookieValue(request, Constants.AREA);
		}
		ModelAndView mav = new ModelAndView("index");
		//得到10个热门职位
		List<JobCategory> hotJobCategory = jcService.getPopularJobCategory(Constants.JOB_CATEGORY_HOT);
		mav.addObject("hotJobCategoryList",hotJobCategory);
		// 得到最新的10个公司
		List<Company> companyList = companyService.getByNew(acode,10);
		mav.addObject("companyList", companyList);
		// 最新的12条热点招聘信息
		List<Job> hotJobList = jobService.getByNew(acode,12);
		mav.addObject("hotJobList", hotJobList);
		// 最新的21条简历信息
		List<Resume> resumeList = resumeService.getByNew(acode,10);
		mav.addObject("resumeList", resumeList);
		// 最新的9条就业指导信息
		List<News> directList = newsService.getByNew(acode, 9,Constants.ARTICLE_TYPE_DIRECT);
		mav.addObject("directList", directList);
		// 最新的9条消息
		List<News> newsList = newsService.getByNew(acode, 9,Constants.ARTICLE_TYPE_NEWS);
		mav.addObject("newsList", newsList);
		//得到6个常用的职位种类
		List<JobCategory> jobCategoryList = jcService.getPopularJobCategory(Constants.JOB_CATEGORY_MAIN);
		mav.addObject("jobCategoryList",jobCategoryList);
		//获得下面按类别显示的51条数据
		Job object = new Job();
		object.setJobCategory(new JobCategory("10010000"));
		List<Job> jobByCategoryResult = jobService.getListForShow(object, Constants.START, 51);
		List<Map<String,Object>> jobByCategoryList = new ArrayList<Map<String,Object>>();
		for(Job job :jobByCategoryResult){
			Map<String,Object> j = new HashMap<String,Object>();
			j.put("jobId", job.getId());
			j.put("jobName", job.getName());
			j.put("companyId", job.getCompany().getId());
			j.put("companyName", job.getCompany().getName());
			jobByCategoryList.add(j);
		}
		mav.addObject("jobByCategoryList",jobByCategoryList);
		return mav;
	}

	/**
	 * 联系我们
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/contact")
	public ModelAndView contact(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("contact");
		return mav;
	}

	/**
	 * 关于本站
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/about")
	public ModelAndView about(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("about");
		return mav;
	}

	//登陆页面
	@RequestMapping("/login")
	public ModelAndView login(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("login");
		return mav;
	}

	//企业用户
	@RequestMapping("/regC")
	public ModelAndView regC(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("reg-c");
		// 工作地区
		List<Area> alist = areaService.getProvinceList();
		mav.addObject("provinceList", alist);
		return mav;
	}

	//个人用户注册
	@RequestMapping("/regP")
	public ModelAndView regP(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("reg-p");
		// 工作地区
		List<Area> alist = areaService.getProvinceList();
		mav.addObject("provinceList", alist);
		return mav;
	}

	//职位列表浏览页面
	@RequestMapping("/work")
	public ModelAndView work(HttpServletRequest request, HttpServletResponse response) {
		log.debug(request.getRequestURI());
		ModelAndView mav = new ModelAndView("work/work");
		List<Parameter> plist = parameterService.getAll();
		// 各种参数
		mav.addObject("params", plist);
		// 职位类别
		List<JobCategory> jlist = jcService.getJcLv1();
		mav.addObject("jcList", jlist);
		// 工作地区
		List<Area> alist = areaService.getProvinceList();
		mav.addObject("areaList", alist);
		// 获得职位总数
		int totalCount = jobService.getTotalCount(null,true);
		mav.addObject("totalCount", totalCount);
		// 如果request中有传过来地区code, 则读取地区码,放入到cookie中
		String acode = request.getParameter("acode");
		log.info("acode [" + acode + "]");
		if (acode != null && !"".equals(acode)) {
			CookieHelper.setCookie(response, Constants.AREA, acode);
		}else{
			acode = CookieHelper.getCookieValue(request, Constants.AREA);
		}
		return mav;
	}

	//简历列表浏览页面
	@RequestMapping("/emp")
	public ModelAndView emp(HttpServletRequest request, HttpServletResponse response) {
		log.debug(request.getRequestURI());
		ModelAndView mav = new ModelAndView("emp/emp");
		List<Parameter> plist = parameterService.getAll();
		// 各种参数
		mav.addObject("params", plist);
		// 职位类别
		List<JobCategory> jlist = jcService.getJcLv1();
		mav.addObject("jcList", jlist);
		// 工作地区
		List<Area> alist = areaService.getProvinceList();
		mav.addObject("areaList", alist);
		// 获得简历总数
		int totalCount = resumeService.getTotalCount(null,Boolean.TRUE);
		log.info("*********************************************" + totalCount);
		mav.addObject("totalCount", totalCount);
		// 如果request中有传过来地区code, 则读取地区码,放入到cookie中
		String acode = request.getParameter("acode");
		log.info("acode [" + acode + "]");
		if (acode != null && !"".equals(acode)) {
			CookieHelper.setCookie(response, Constants.AREA, acode);
		}else{
			acode = CookieHelper.getCookieValue(request, Constants.AREA);
		}
		return mav;
	}

	//最新消息列表浏览页面
	@RequestMapping("/news")
	public ModelAndView news(HttpServletRequest request) {
		log.debug(request.getRequestURI());
		ModelAndView mav = new ModelAndView("news/news");
		return mav;
	}

	//就业指导信息 列表浏览页面
	@RequestMapping("/direct")
	public ModelAndView direct(HttpServletRequest request) {
		log.debug(request.getRequestURI());
		ModelAndView mav = new ModelAndView("direct/direct");
		return mav;
	}

	@RequestMapping("/jsonp")
	public String jsonp() {
		return "test";
	}

	@RequestMapping("/jsonptest")
	@ResponseBody
	public JSONPObject jsonptest(
			@RequestParam(value = "callback") String callback) {
		ModelMap map = new ModelMap();
		map.put("pi", "hello,I'm Fjt");
		return new JSONPObject(callback, map);
		/**
		 * 前台方法写法 
		 * function jsonp() { 
		 * 		var url = 'http://192.168.170.154:8080/jobservice/jsonptest'; 
		 * 		$.ajax({ 
		 * 			url : url, 
		 * 			type : 'GET', 
		 * 			dataType : 'jsonp', 
		 * 			async : true ,
		 * 			success : function(e) { 
		 * 				alert(e.pi); 
		 * 			}
		 * 		}); 
		 * };
		 **/
	}
}
