package esd.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
import esd.service.MailService;
import esd.service.NewsService;
import esd.service.ParameterService;
import esd.service.ResumeService;
import esd.service.UserService;

/**
 * 首页 菜单, 跳转用controller
 * 
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

	@Autowired
	private MailService mailService;

	// 首页
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) {
		log.debug("=========================" + request.getRequestURI());
		// 如果存在从网站群接收来的用户名, 密码, 则证明是从网站群登陆过来的, 则将该用户的相关信息和登陆一样, 放到cookie中
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if (username != null && !"".equals(username) && password != null
				&& !"".equals(password)) {
			User user = userService.check(username);
			/*********** 设置用户信息到cookie中 ************/
			if (user.getIdentity().equals(Identity.COMPANY.toString())) {
				Company company = companyService.getByAccount(user.getId());
				log.debug("company " + company);
				if (company != null) {
					CookieHelper.setCookie(response, Constants.USERCOMPANYID,
							String.valueOf(company.getId()));
				}
			}
			log.debug("login: " + user);
			CookieHelper.setCookie(response, request, user, null);
		}
		/**
		 * 处理地区code和如何存放到cookie中的问题
		 */
		// ①先查看request中有没有传过来的acode,
		String acode = request.getParameter("acode");
		if (acode != null && !"".equals(acode)) {
			// ②不为空时, 则表示为从残联网站跳转过来的, 则清除原来可能存在的所有用户, 地区等cookie信息
			CookieHelper.killUserCookie(response, true);
		} else {
			// ③为空在则检查cookie是中没有地区信息, 如果cookie中也没有, 则说明是首次访问首页且不是从网站群跳转过来的,
			// 那么使用全国code
			String cookieAreaCode = CookieHelper.getCookieValue(request,
					Constants.AREACODE);
			if (cookieAreaCode == null || "".equals(cookieAreaCode)) {
				cookieAreaCode = Constants.CURRENT_AREA;
			}
			acode = cookieAreaCode;
		}
		// ④得到地区信息对象, 将地区名称放入到cookie中
		Area area = areaService.getByCode(acode);
		CookieHelper.setCookie(response, request, null, area);

		ModelAndView mav = new ModelAndView("index");
		// 根据地区code 得到本地区下属的 省或市或县区
		List<Area> subAreaList;
		if (Constants.AREACOUNTRY.equals(acode)) {
			subAreaList = areaService.getProvinceList();
		} else {
			subAreaList = areaService.getSubArea(acode);
		}
		mav.addObject("subAreaList", subAreaList);
		// 得到最新的10个公司
		List<Company> companyList = companyService.getByNew(acode, 10);
		mav.addObject("companyList", companyList);
		// 最新的12条热点招聘信息
		List<Job> hotJobList = jobService.getByNew(acode, 12);
		mav.addObject("hotJobList", hotJobList);
		// 最新的21条简历信息
		List<Resume> resumeList = resumeService.getByNew(acode, 10);
		mav.addObject("resumeList", resumeList);
		// 最新的9条就业指导信息
		List<News> directList = newsService.getByNew(acode, 9,
				Constants.ARTICLE_TYPE_DIRECT);
		mav.addObject("directList", directList);
		// 最新的9条消息
		List<News> newsList = newsService.getByNew(acode, 9,
				Constants.ARTICLE_TYPE_NEWS);
		mav.addObject("newsList", newsList);
		// 得到6个常用的职位种类
		List<JobCategory> jobCategoryList = jcService
				.getPopularJobCategory(Constants.JOB_CATEGORY_MAIN);
		mav.addObject("jobCategoryList", jobCategoryList);
		// 获得下面按类别显示的51条数据
		Job object = new Job();
		object.setJobCategory(new JobCategory("10010000"));
		object.setArea(new Area(acode));
		List<Job> jobByCategoryResult = jobService.getListForShow(object,
				Constants.START, 51,Boolean.TRUE);
		List<Map<String, Object>> jobByCategoryList = new ArrayList<Map<String, Object>>();
		for (Job job : jobByCategoryResult) {
			Map<String, Object> j = new HashMap<String, Object>();
			j.put("jobId", job.getId());
			j.put("jobName", job.getName());
			j.put("companyId", job.getCompany().getId());
			j.put("companyName", job.getCompany().getName());
			jobByCategoryList.add(j);
		}
		mav.addObject("jobByCategoryList", jobByCategoryList);
		// 获取5个存在图片的新闻
		List<News> imagesList = newsService.getFiveChangeList(acode);
		mav.addObject("imagesList", imagesList);
		return mav;
	}

	// 联系我们
	@RequestMapping("/contact")
	public ModelAndView contact(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("func/contact");
		return mav;
	}

	// 关于本站
	@RequestMapping("/about")
	public ModelAndView about(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("func/about");
		return mav;
	}

	// 个人登陆页面
	@RequestMapping("/loginP")
	public ModelAndView loginP(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("login/login-p");
		return mav;
	}

	// 企业登陆页面
	@RequestMapping("/loginC")
	public ModelAndView loginC(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("login/login-c");
		return mav;
	}

	// 企业用户注册
	@RequestMapping("/regC")
	public ModelAndView regC(HttpServletRequest request,HttpServletResponse response) {
		/**
		 * 处理地区code和如何存放到cookie中的问题
		 */
		// ①先查看request中有没有传过来的acode,
		String acode = request.getParameter("acode");
		if (acode != null && !"".equals(acode)) {
			// ②不为空时, 则表示为从残联网站跳转过来的, 则清除原来可能存在的所有用户, 地区等cookie信息
			CookieHelper.killUserCookie(response, true);
		} else {
			// ③为空在则检查cookie是中没有地区信息, 如果cookie中也没有, 则说明是首次访问首页且不是从网站群跳转过来的,
			// 那么使用全国code
			String cookieAreaCode = CookieHelper.getCookieValue(request,
					Constants.AREACODE);
			if (cookieAreaCode == null || "".equals(cookieAreaCode)) {
				cookieAreaCode = Constants.CURRENT_AREA;
			}
			acode = cookieAreaCode;
		}
		// ④得到地区信息对象, 将地区名称放入到cookie中
		Area area = areaService.getByCode(acode);
		CookieHelper.setCookie(response, request, null, area);
		ModelAndView mav = new ModelAndView("register/reg-c");
		// 工作地区
		List<Area> alist = areaService.getProvinceList();
		mav.addObject("provinceList", alist);
		return mav;
	}

	// 个人用户注册
	@RequestMapping("/regP")
	public ModelAndView regP(HttpServletRequest request, HttpServletResponse response) {
		/**
		 * 处理地区code和如何存放到cookie中的问题
		 */
		// ①先查看request中有没有传过来的acode,
		String acode = request.getParameter("acode");
		if (acode != null && !"".equals(acode)) {
			// ②不为空时, 则表示为从残联网站跳转过来的, 则清除原来可能存在的所有用户, 地区等cookie信息
			CookieHelper.killUserCookie(response, true);
		} else {
			// ③为空在则检查cookie是中没有地区信息, 如果cookie中也没有, 则说明是首次访问首页且不是从网站群跳转过来的,
			// 那么使用全国code
			String cookieAreaCode = CookieHelper.getCookieValue(request,
					Constants.AREACODE);
			if (cookieAreaCode == null || "".equals(cookieAreaCode)) {
				cookieAreaCode = Constants.CURRENT_AREA;
			}
			acode = cookieAreaCode;
		}
		// ④得到地区信息对象, 将地区名称放入到cookie中
		Area area = areaService.getByCode(acode);
		CookieHelper.setCookie(response, request, null, area);
		ModelAndView mav = new ModelAndView("register/reg-p");
		// 工作地区
		List<Area> alist = areaService.getProvinceList();
		mav.addObject("provinceList", alist);
		return mav;
	}

	// 职位列表浏览页面
	@RequestMapping("/work")
	public ModelAndView work(HttpServletRequest request,
			HttpServletResponse response) {
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
		int totalCount = jobService.getTotalCount(null, true);
		mav.addObject("totalCount", totalCount);

		/**
		 * 处理地区code和如何存放到cookie中的问题
		 */
		// ①先查看request中有没有传过来的acode,
		String acode = request.getParameter("acode");
		if (acode != null && !"".equals(acode)) {
			// ②不为空时, 则表示为从残联网站跳转过来的, 则清除原来可能存在的所有用户, 地区等cookie信息
			CookieHelper.killUserCookie(response, true);
		} else {
			// ③为空在则检查cookie是中没有地区信息, 如果cookie中也没有, 则说明是首次访问首页且不是从网站群跳转过来的,
			// 那么使用全国code
			String cookieAreaCode = CookieHelper.getCookieValue(request,
					Constants.AREACODE);
			if (cookieAreaCode == null || "".equals(cookieAreaCode)) {
				cookieAreaCode = Constants.CURRENT_AREA;
			}
			acode = cookieAreaCode;
		}
		// ④得到地区信息对象, 将地区名称放入到cookie中
		Area area = areaService.getByCode(acode);
		CookieHelper.setCookie(response, request, null, area);

		// 读取职位查询信息
		log.info("--- work job search ---");
		Job job = new Job();
		job.setArea(area);

		String keyWord = request.getParameter("keyWord");
		if (keyWord != null && !"".equals(keyWord)) {
			job.setName(keyWord);
		}
		request.setAttribute("keyWord", keyWord);
		String jcCode = request.getParameter("jobCategory");
		if (jcCode != null && !"".equals(jcCode)) {
			job.setJobCategory(new JobCategory(jcCode));
		}
		request.setAttribute("jcCode", jcCode);
		String education = request.getParameter("education");
		if (education != null && !"".equals(education)) {
			job.setEducation(education);
		}
		request.setAttribute("education", education);
		String jobNature = request.getParameter("jobNature");
		if (jobNature != null && !"".equals(jobNature)) {
			job.setNature(jobNature);
		}
		request.setAttribute("jobNature", jobNature);
		job.setIsActiveEffectiveTime(true);
		String pageStr = request.getParameter("page");
		if (pageStr == null || "".equals(pageStr)) {
			pageStr = "1";
		}
		Integer page = KitService.getInt(pageStr);
		List<Job> jobList = jobService
				.getListForShow(job, page, Constants.SIZE,Boolean.TRUE);
		Integer records = jobService.getTotalCount(job, Boolean.TRUE);
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		if (jobList != null && records != null && records > 0) {
			try {
				for (Iterator<Job> iterator = jobList.iterator(); iterator
						.hasNext();) {
					Job it = (Job) iterator.next();
					log.debug(it.toString());
					Map<String, String> map = new HashMap<String, String>();
					map.put("id", String.valueOf(it.getId()));
					map.put("name", it.getName());
					map.put("company", it.getCompany().getName());
					map.put("companyid", it.getCompany().getId() + "");
					map.put("area", it.getWorkPlace().getName());
					map.put("experience", it.getExperience());
					map.put("date", KitService.dateForShow(it.getUpdateDate()));
					list.add(map);
				}
			} catch (Exception e) {
				log.error("error in list", e);
			}
		}
		mav.addObject("list", list);
		PaginationUtil pagination = new PaginationUtil(page, records);
		mav.addObject("pagination", pagination.getHandler());
		return mav;
	}

	// 简历列表浏览页面
	@RequestMapping("/emp")
	public ModelAndView emp(HttpServletRequest request,
			HttpServletResponse response) {
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
		int totalCount = resumeService.getTotalCount(null, Boolean.TRUE);
		mav.addObject("totalCount", totalCount);

		/**
		 * 处理地区code和如何存放到cookie中的问题
		 */
		// ①先查看request中有没有传过来的acode,
		String acode = request.getParameter("acode");
		if (acode != null && !"".equals(acode)) {
			// ②不为空时, 则表示为从残联网站跳转过来的, 则清除原来可能存在的所有用户, 地区等cookie信息
			CookieHelper.killUserCookie(response, true);
		} else {
			// ③为空在则检查cookie是中没有地区信息, 如果cookie中也没有, 则说明是首次访问首页且不是从网站群跳转过来的,
			// 那么使用全国code
			String cookieAreaCode = CookieHelper.getCookieValue(request,
					Constants.AREACODE);
			if (cookieAreaCode == null || "".equals(cookieAreaCode)) {
				cookieAreaCode = Constants.CURRENT_AREA;
			}
			acode = cookieAreaCode;
		}
		// ④得到地区信息对象, 将地区名称放入到cookie中
		Area area = areaService.getByCode(acode);
		CookieHelper.setCookie(response, request, null, area);

		// 读取简历查询信息
		log.info("--- emp resume search ---");
		Resume resume = new Resume();
		resume.setArea(area);
		request.setAttribute("tarArea", area);

		String keyWord = request.getParameter("keyWord");
		if (keyWord != null && !"".equals(keyWord)) {
			resume.setTitle(keyWord);
		}
		request.setAttribute("keyWord", keyWord);
		String jcCode = request.getParameter("jobCategory");
		if (jcCode != null && !"".equals(jcCode)) {
			resume.setDesireJob(new JobCategory(jcCode));
		}
		request.setAttribute("jcCode", jcCode);
		String education = request.getParameter("education");
		if (education != null && !"".equals(education)) {
			resume.setEducation(education);
		}
		request.setAttribute("education", education);
		String jobNature = request.getParameter("jobNature");
		if (jobNature != null && !"".equals(jobNature)) {
			resume.setJobNature(jobNature);
		}
		request.setAttribute("jobNature", jobNature);
		String gender = request.getParameter("gender");
		if (gender != null && !"".equals(gender)) {
			resume.setGender(gender);
		}
		request.setAttribute("gender", gender);
		String pageStr = request.getParameter("page");
		if (pageStr == null || "".equals(pageStr)) {
			pageStr = "1";
		}
		Integer page = KitService.getInt(pageStr);
		List<Resume> resumeList = resumeService.getForListShow(resume, page,
				Constants.SIZE,Boolean.TRUE);
		Integer records = resumeService.getTotalCount(resume, Boolean.TRUE);
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		if (resumeList != null && records != null && records > 0) {
			try {
				for (Iterator<Resume> iterator = resumeList.iterator(); iterator
						.hasNext();) {
					Resume it = (Resume) iterator.next();
					log.debug(it.toString());
					Map<String, String> map = new HashMap<String, String>();
					map.put("id", String.valueOf(it.getId()));
					map.put("title", it.getTitle());
					map.put("name", it.getName());
					map.put("gender", it.getGender());
					map.put("education", it.getEducation());
					map.put("major", it.getMajor());
					map.put("experience", it.getExperience());
					if (it.getDesireJob() != null) {
						if (it.getDesireJob().getName() != null
								&& !"".equals(it.getDesireJob().getName())) {
							map.put("desireJob", it.getDesireJob().getName());
						}
					} else {
						map.put("desireJob", Constants.NO_LIMIT);
					}
					if (it.getDesireAddress() != null) {
						if (it.getDesireAddress().getName() != null
								&& !"".equals(it.getDesireAddress().getName())) {
							map.put("desireAddress", it.getDesireAddress()
									.getName());
						}
					} else {
						map.put("desireAddress", Constants.NO_LIMIT);
					}
					map.put("desireSalary", it.getDesireSalary());
					list.add(map);
				}
			} catch (Exception e) {
				log.error("error in list", e);
			}
		}
		while (list.size() < Constants.SIZE) {
			Map<String, String> map = new HashMap<String, String>();
			list.add(map);
		}

		mav.addObject("list", list);
		PaginationUtil pagination = new PaginationUtil(page, records);
		mav.addObject("pagination", pagination.getHandler());
		return mav;
	}

	// 最新消息列表浏览页面
	@RequestMapping("/news")
	public ModelAndView news(HttpServletRequest request,
			HttpServletResponse response) {
		log.debug(request.getRequestURI());
		ModelAndView mav = new ModelAndView("news/news");
		List<Parameter> plist = parameterService.getAll();
		// 各种参数
		mav.addObject("params", plist);

		// 获得简历总数
		int totalCount = newsService.getTotalCount(null);
		mav.addObject("totalCount", totalCount);
		// 如果request中有传过来地区code, 本地cookie没有地区code, 则读取地区码,放入到cookie中;
		// 有则直接使用传递过来的
		String acode = request.getParameter("acode");
		String localCode = CookieHelper.getCookieValue(request,
				Constants.AREACODE);
		log.info("acode [" + acode + "]");
		if (acode != null && !"".equals(acode)) {
			if (localCode == null || "".equals(localCode)) {
				CookieHelper.setCookie(response, Constants.AREACODE, acode);
			}
		} else {
			acode = localCode;
		}
		Area area = areaService.getByCode(acode);
		// 读取查询信息

		log.info("--- emp news search ---");
		News news = new News();
		news.setArea(area);
		request.setAttribute("tarArea", area);

		String keyWord = request.getParameter("keyWord");
		if (keyWord != null && !"".equals(keyWord)) {
			news.setTitle(keyWord);
		}
		request.setAttribute("keyWord", keyWord);

		String releaseDateStr = request.getParameter("releaseDate");
		if (releaseDateStr != null && !"".equals(releaseDateStr)) {
			Integer releaseDate = Integer.valueOf(releaseDateStr);
			Date update_Date = KitService.getreleaseTime(releaseDate
					.longValue());
			news.setUpdateDate(update_Date);

		}
		request.setAttribute("releaseDate", releaseDateStr);

		String pageStr = request.getParameter("page");
		if (pageStr == null || "".equals(pageStr)) {
			pageStr = "1";
		}
		Integer page = KitService.getInt(pageStr);
		List<News> newsList = newsService.getForListShow(news, page,
				Constants.SIZE);
		Integer records = newsService.getTotalCount(news);
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		if (newsList != null && records != null && records > 0) {
			try {
				for (Iterator<News> iterator = newsList.iterator(); iterator
						.hasNext();) {
					News it = (News) iterator.next();
					log.debug(it.toString());
					Map<String, String> map = new HashMap<String, String>();
					map.put("id", String.valueOf(it.getId()));
					map.put("title", it.getTitle());
					map.put("content", it.getContent());
					map.put("author", it.getAuthor());
					map.put("source", it.getSource());
					map.put("type", it.getType());
					list.add(map);
				}
			} catch (Exception e) {
				log.error("error in list", e);
			}
		}
		while (list.size() < Constants.SIZE) {
			Map<String, String> map = new HashMap<String, String>();
			list.add(map);
		}

		mav.addObject("list", list);
		PaginationUtil pagination = new PaginationUtil(page, records);
		mav.addObject("pagination", pagination.getHandler());
		return mav;
	}

	// 就业指导信息 列表浏览页面
	@RequestMapping("/direct")
	public ModelAndView direct(HttpServletRequest request,
			HttpServletResponse response) {
		log.debug(request.getRequestURI());
		ModelAndView mav = new ModelAndView("direct/direct");
		List<Parameter> plist = parameterService.getAll();
		// 各种参数
		mav.addObject("params", plist);

		// 获得简历总数
		int totalCount = newsService.getTotalCount(null);
		mav.addObject("totalCount", totalCount);
		// 如果request中有传过来地区code, 本地cookie没有地区code, 则读取地区码,放入到cookie中;
		// 有则直接使用传递过来的
		String acode = request.getParameter("acode");
		String localCode = CookieHelper.getCookieValue(request,
				Constants.AREACODE);
		log.info("acode [" + acode + "]");
		if (acode != null && !"".equals(acode)) {
			if (localCode == null || "".equals(localCode)) {
				CookieHelper.setCookie(response, Constants.AREACODE, acode);
			}
		} else {
			acode = localCode;
		}
		Area area = areaService.getByCode(acode);
		// 读取查询信息

		log.info("--- emp news search ---");
		News news = new News();
		news.setArea(area);
		request.setAttribute("tarArea", area);

		String keyWord = request.getParameter("keyWord");
		if (keyWord != null && !"".equals(keyWord)) {
			news.setTitle(keyWord);
		}
		request.setAttribute("keyWord", keyWord);
		String releaseDateStr = request.getParameter("releaseDate");

		if (releaseDateStr != null && !"".equals(releaseDateStr)) {
			Integer releaseDate = Integer.valueOf(releaseDateStr);
			Date update_Date = KitService.getreleaseTime(releaseDate
					.longValue());
			news.setUpdateDate(update_Date);

		}
		request.setAttribute("releaseDate", releaseDateStr);

		String pageStr = request.getParameter("page");
		if (pageStr == null || "".equals(pageStr)) {
			pageStr = "1";
		}
		Integer page = KitService.getInt(pageStr);
		List<News> newsList = newsService.getForListShow(news, page,
				Constants.SIZE);
		Integer records = newsService.getTotalCount(news);
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		if (newsList != null && records != null && records > 0) {
			try {
				for (Iterator<News> iterator = newsList.iterator(); iterator
						.hasNext();) {
					News it = (News) iterator.next();
					log.debug(it.toString());
					Map<String, String> map = new HashMap<String, String>();
					map.put("id", String.valueOf(it.getId()));
					map.put("title", it.getTitle());
					map.put("content", it.getContent());
					map.put("author", it.getAuthor());
					map.put("source", it.getSource());
					map.put("type", it.getType());
					list.add(map);
				}
			} catch (Exception e) {
				log.error("error in list", e);
			}
		}
		while (list.size() < Constants.SIZE) {
			Map<String, String> map = new HashMap<String, String>();
			list.add(map);
		}

		mav.addObject("list", list);
		PaginationUtil pagination = new PaginationUtil(page, records);
		mav.addObject("pagination", pagination.getHandler());
		return mav;
	}

	// 跳转到找回用户名页面
	@RequestMapping(value = "/getBackUserName", method = RequestMethod.GET)
	public ModelAndView getBackUserNameGet(HttpServletRequest request) {
		log.debug(request.getRequestURI());
		ModelAndView mav = new ModelAndView("func/getback-username");
		return mav;
	}

	// 跳转到找回密码页面
	@RequestMapping(value = "/getBackPassWord", method = RequestMethod.GET)
	public ModelAndView getBackPassWordGet(HttpServletRequest request) {
		log.debug(request.getRequestURI());
		ModelAndView mav = new ModelAndView("func/getback-password");
		return mav;
	}

	// 找回用户名
	@RequestMapping(value = "/getBack", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getBackUserNamePost(HttpServletRequest request) {
		log.debug(request.getRequestURI());
		Map<String, Object> map = new HashMap<String, Object>();
		String email = request.getParameter("email");
		String type = request.getParameter("type"); // 找回类型 username用户名;
													// password密码
		// 根据邮箱查找到对应的注册用户
		User param = new User();
		param.setEmail(email);
		User user = userService.check(param);
		String val = "";
		if ("username".equals(type)) {
			val = user.getLoginName();
		} else if ("password".equals(type)) {
			val = user.getPassWord();
		} else {
			map.put(Constants.NOTICE, "传递的发送类型为空, 请联系管理员.");
			return map;
		}
		Boolean bl = mailService.sendUserNamePwd(email, type, val);
		if (bl) {
			map.put(Constants.NOTICE, Constants.Notice.SUCCESS.getValue());
		} else {
			map.put(Constants.NOTICE, "发送失败 ");
		}
		return map;
	}
	
	//跳转到禁止访问提示页面
	@RequestMapping(value = "/forbid", method = RequestMethod.GET)
	public ModelAndView forbid(HttpServletRequest request) {
		log.debug(request.getRequestURI());
		ModelAndView mav = new ModelAndView("error/forbid");
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
		 * 前台方法写法 function jsonp() { var url =
		 * 'http://192.168.170.154:8080/jobservice/jsonptest'; $.ajax({ url :
		 * url, type : 'GET', dataType : 'jsonp', async : true , success :
		 * function(e) { alert(e.pi); } }); };
		 **/
	}

	@RequestMapping("/footer")
	public String footer(HttpServletRequest request){
		request.setAttribute("message", "我是脚部的提示信息.");
		return "formatter/footer";
	}
	
	
}
