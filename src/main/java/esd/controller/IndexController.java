package esd.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import esd.service.AreaService;
import esd.service.CompanyService;
import esd.service.JobCategoryService;
import esd.service.JobService;
import esd.service.KitService;
import esd.service.NewsService;
import esd.service.ParameterService;
import esd.service.ResumeService;

/**
 * 首页  菜单, 跳转用controller
 * @author Administrator
 *
 */
@Controller
public class IndexController {
	private static Logger log = Logger.getLogger(IndexController.class);

	@Value("${export.template.url}")
	private String exportTemplateUrl;

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
	public ModelAndView index(HttpServletRequest request,HttpSession session) {
		log.debug("=========================" + request.getRequestURI());
		//如果acode为空, 则说明首次进首页, 再查看request中有没有传过来的acode
		String acode= request.getParameter("acode");
		if(acode == null){
			//得到地区code
			Object obj = session.getAttribute("area");
			if(obj!=null){
				acode = ((Area)obj).getCode();
			}
		}
		Area area = areaService.getByCode(acode);
		session.setAttribute("area", area);
		ModelAndView mav = new ModelAndView("index");
		// 得到最新的10个公司
		List<Company> companyList = companyService.getByNew(KitService.getProvinceCode(acode),10);
		mav.addObject("companyList", companyList);
		// 最新的20条招聘信息
		List<Job> jobList = jobService.getByNew(KitService.getProvinceCode(acode),20);
		mav.addObject("jobList", jobList);
		// 最新的21条简历信息
		List<Resume> resumeList = resumeService.getByNew(KitService.getProvinceCode(acode),21);
		mav.addObject("resumeList", resumeList);
		// 最新的6条消息
		List<News> newsList = newsService.getByNew(acode, 6,Constants.ARTICLE_TYPE_NEWS);
		mav.addObject("newsList", newsList);
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
	public ModelAndView work(HttpServletRequest request,HttpSession session) {
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
		int totalCount = jobService.getTotalCount(null);
		mav.addObject("totalCount", totalCount);
		// 读取地区码,放入到session中
		String acode = request.getParameter("acode");
		log.info("acode [" + acode + "]");
		if (acode != null && !"".equals(acode)) {
//			// 由于单个区县信息量过少, 所以当地区为三级的县区时,默认选择本省内的信息
//			if(acode.startsWith("30")){
//				acode = KitService.getProvinceCode(acode);
//			}
			Area area = areaService.getByCode(acode);
			session.setAttribute("area", area);
		}
		return mav;
	}

	//简历列表浏览页面
	@RequestMapping("/emp")
	public ModelAndView emp(HttpServletRequest request,HttpSession session) {
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
		int totalCount = resumeService.getTotalCount(null);
		log.info("*********************************************" + totalCount);
		mav.addObject("totalCount", totalCount);
		// 读取地区码,放入到session中
		String acode = request.getParameter("acode");
		log.info("acode [" + acode + "]");
		if (acode != null && !"".equals(acode)) {
			// 由于单个区县信息量过少, 所以当地区为三级的县区时,默认选择本省内的信息
			if(acode.startsWith("30")){
				acode = KitService.getProvinceCode(acode);
			}
		Area area = areaService.getByCode(acode);
		session.setAttribute("area", area);
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
		 * 前台写方 function jsonp() { var url =
		 * 'http://192.168.170.154:8080/jobservice/jsonptest'; $.ajax({ url :
		 * url, type : 'GET', success : function(e) { alert(e.pi); }, dataType :
		 * 'jsonp', async : true }); };
		 **/
	}
}
