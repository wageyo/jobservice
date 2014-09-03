package esd.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.util.JSONPObject;

import esd.bean.Area;
import esd.bean.Job;
import esd.bean.JobCategory;
import esd.controller.Constants.Notice;
import esd.service.AreaService;
import esd.service.JobService;
import esd.service.KitService;

@Controller
@RequestMapping("/job")
public class JobController {

	private static Logger log = Logger.getLogger(JobController.class);
	@Autowired
	private JobService jobService;

	@Autowired
	private AreaService areaService;
	
	@RequestMapping("/search")
	public ModelAndView work(HttpServletRequest request) {
		log.debug(request.getRequestURI());
		ModelAndView mav = new ModelAndView("work/work");
		return mav;
	}

	// 多条件职位简历
	@RequestMapping(value = "/search/{page}", method = RequestMethod.POST)
	public ModelAndView search(HttpServletRequest req,
			@PathVariable(value = "page") Integer page, HttpSession session) {
		log.info("--- search ---");
		Map<String, Object> entity = new HashMap<String, Object>();
		Job job = new Job();
		String areaCode = req.getParameter("areaCode");
		log.info("acode [" + areaCode + "]");
		if (areaCode != null && !"".equals(areaCode)) {
			job.setArea(new Area(areaCode));
		} else {
			// 读取地区码
			Object obj_acode = session.getAttribute("acode");
			log.info("obj_acode [" + obj_acode + "]");
			if (obj_acode != null) {
				// 由于单个区县信息量过少, 所以当地区为三级的县区时,默认选择本省内的信息
				String acode = obj_acode.toString();
				log.info("acode [" + acode + "]");
				acode = KitService.getProvinceCode(acode);
				job.setArea(new Area(acode));
			}
		}
		String keyWord = req.getParameter("keyWord");
		if (keyWord != null && !"".equals(keyWord)) {
			job.setName(keyWord);
		}
		String jcCode = req.getParameter("jcCode");
		if (jcCode != null && !"".equals(jcCode)) {
			job.setJobCategory(new JobCategory(jcCode));
		}
		String education = req.getParameter("education");
		if (education != null && !"".equals(education)) {
			job.setEducation(education);
		}
		String jobNature = req.getParameter("jobNature");
		if (jobNature != null && !"".equals(jobNature)) {
			job.setNature(jobNature);
		}
		List<Job> jobList = jobService
				.getForListShow(job, page, Constants.SIZE);
		Integer records = jobService.getTotalCount(job);
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
					map.put("area", it.getArea().getName());
					map.put("experience", it.getExperience());
					map.put("date", it.getUpdateDate());
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

		entity.put("list", list);
		PaginationUtil pagination = new PaginationUtil(page, records);
		entity.put("pagination", pagination.getHandler());
		return new ModelAndView("work/work-json", "entity", entity);
	}

	// 根据id得到一个职位返回前台显示
	@RequestMapping("/getOneForShow")
	public String getOneForShow(HttpServletRequest req, RedirectAttributes ra,
			HttpSession session) {
		log.info("--- getOneForShow ---");
		// 读取地区码,放入到session中
		String acode = req.getParameter("acode");
		log.info("acode [" + acode + "]");
		if (acode != null && !"".equals(acode)) {
//			// 由于单个区县信息量过少, 所以当地区为三级的县区时,默认选择本省内的信息
//			if(acode.startsWith("30")){
//				acode = KitService.getProvinceCode(acode);
//			}
			Area area = areaService.getByCode(acode);
			session.setAttribute("area", area);
		}
		String idStr = req.getParameter("id");
		int id = KitService.getInt(idStr);
		if (id < 0) {
			ra.addFlashAttribute("messageType", "0");
			ra.addFlashAttribute("message", "传递的参数有误!");
			return "redirect:/index";
		}
		// 将job放入到request中
		Job job = jobService.getOneForShow(id);
		req.setAttribute("job", job);
		// 同时将该公司所发布的其他职位也放入到request中
		List<Job> jobList = jobService.getByCompanyForShow(job.getCompany()
				.getId(), 1, 99);
		req.setAttribute("jobList", jobList);
		return "work/work-detail";
	}

	// 根据id得到一个职位返回前台数据以供显示
	@RequestMapping(value = "/getOneForShowData", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getOneForShowData(HttpServletRequest req) {
		Job job = null;
		log.info("--- getOneForShow ---");
		String idStr = req.getParameter("id");
		int id = KitService.getInt(idStr);
		if (id < 0) {
			req.setAttribute("notice", "传递的参数有误!");
			return null;
		}

		try {
			job = jobService.getOneForShow(id);
		} catch (Exception e) {
			log.error("根据id得到一个职位返回前台数据。没有取到数据");
			return null;
		}
		log.info("job" + job);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("job", job);
		return map;
	}

	// 根据企业id, 得到该企业所发布的职位
	@RequestMapping("/getByCompany")
	@ResponseBody
	public Map<String, Object> getByCompany(HttpServletRequest req) {
		log.info("--- getByCompany ---");
		Map<String, Object> json = new HashMap<String, Object>();
		// 得到当前企业用户
		String idStr = req.getParameter("companyid");
		int id = KitService.getInt(idStr);
		if (id < 0) {
			json.put("notice", Notice.ERROR.toString());
			return json;
		}
		List<Job> jobList = jobService.getByCompanyForShow(id, 1,
				Constants.SIZE);
		log.info(" jobList.size() = " + jobList.size());
		json.put("notice", Notice.SUCCESS);
		json.put("jobList", jobList);
		return json;
	}

	// 获得职位总个数
	@RequestMapping("/getTotalCount")
	@ResponseBody
	public Map<String, Object> getTotalCount(HttpServletRequest req,
			HttpSession session) {
		log.info("--- getTotalCount ---");
		String areaCode = req.getParameter("acode");
		if (areaCode == null) {
			areaCode = "10000000";
		}
		Map<String, Object> json = new HashMap<String, Object>();
		Job job = new Job();
		job.setArea(new Area(areaCode));
		int total = jobService.getTotalCount(job);
		json.put("totalCount", total);
		return json;
	}

	// 多条件查询职位--给opencms框架使用
	@RequestMapping(value = "/searchForOpenCms", method = RequestMethod.GET)
	@ResponseBody
	public JSONPObject searchForOpenCms(
			@RequestParam(value = "callback") String callback,
			HttpServletRequest req) {
		log.info("--- searchForOpenCms ---");
		// //接收从网站群接收来的地区code, 根据他查找所属地区的职位
		String acode = req.getParameter("acode");
		String pageSizeStr = req.getParameter("pageSize");
		// 初始化为10
		Integer pageSize =10;
		if(pageSizeStr != null && !"".equals(pageSizeStr)){
			pageSize = Integer.parseInt(pageSizeStr);
		}
		ModelMap map = new ModelMap();
		List<Job> jobList = jobService.getByNew(
				KitService.getProvinceCode(acode), pageSize);
		map.put("jobList", jobList);
		return new JSONPObject(callback, map);
	}

}
