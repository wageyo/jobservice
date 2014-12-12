package esd.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import esd.common.PoiCreateExcel;
import esd.controller.Constants.Notice;
import esd.service.CookieHelper;
import esd.service.JobService;
import esd.service.KitService;

@Controller
@RequestMapping("/job")
public class JobController {

	private static Logger log = Logger.getLogger(JobController.class);
	@Autowired
	private JobService jobService;

	@RequestMapping("/search")
	public ModelAndView work(HttpServletRequest request,HttpServletResponse response) {
		log.debug(request.getRequestURI());
		ModelAndView mav = new ModelAndView("work/work");
		//先查看request中有没有传过来的acode, 不为空则是第一次进来, 将其中的acode放到cookie中
		String acode= request.getParameter("acode");
		if(acode != null && !"".equals(acode)){
			CookieHelper.setCookie(response, Constants.AREA, acode, Integer.MAX_VALUE);
		}
		return mav;
	}

	// 多条件职位简历
	@RequestMapping(value = "/search/{page}", method = RequestMethod.POST)
	public ModelAndView search(HttpServletRequest request,
			@PathVariable(value = "page") Integer page, HttpServletResponse response) {
		log.info("--- search ---");
		Map<String, Object> entity = new HashMap<String, Object>();
		Job job = new Job();
		//从cookie读取acode
		String acode = CookieHelper.getCookieValue(request, Constants.AREA);
		job.setArea(new Area(acode));
		
		String keyWord = request.getParameter("keyWord");
		if (keyWord != null && !"".equals(keyWord)) {
			job.setName(keyWord);
		}
		String jcCode = request.getParameter("jcCode");
		if (jcCode != null && !"".equals(jcCode)) {
			job.setJobCategory(new JobCategory(jcCode));
		}
		String education = request.getParameter("education");
		if (education != null && !"".equals(education)) {
			job.setEducation(education);
		}
		String jobNature = request.getParameter("jobNature");
		if (jobNature != null && !"".equals(jobNature)) {
			job.setNature(jobNature);
		}
		job.setIsActiveEffectiveTime(true);
		List<Job> jobList = jobService
				.getForListShow(job, page, Constants.SIZE);
		Integer records = jobService.getTotalCount(job,Boolean.TRUE);
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
					map.put("date", KitService.dateForShow(it.getUpdateDate()));
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
	public String getOneForShow(HttpServletRequest request, HttpServletResponse response, RedirectAttributes ra) {
		log.info("--- getOneForShow ---");
		//①先查看request中有没有传过来的acode, 
		String acode= request.getParameter("acode");
		if(acode != null){
			/**** 不为空时, 则表示为从残联网站跳转过来的, 则清除原来可能存在的所有用户, 地区等cookie信息	****/
			CookieHelper.killAllCookie(response, true);
			//②不为空则是第一次进来, 将其中的acode放到cookie中
			CookieHelper.setCookie(response, Constants.AREA, acode, Integer.MAX_VALUE);
		}else{
			//③为空在则检查cookie是中没有地区信息
			acode = CookieHelper.getCookieValue(request, Constants.AREA);
		}
				
		String idStr = request.getParameter("id");
		int id = KitService.getInt(idStr);
		if (id < 0) {
			ra.addFlashAttribute("messageType", "0");
			ra.addFlashAttribute("message", "传递的参数有误!");
			return "redirect:/index";
		}
		// 将job放入到request中
		Job job = jobService.getOneForShow(id);
		request.setAttribute("job", job);
		// 同时将该公司所发布的其他职位也放入到request中
		List<Job> jobList = jobService.getByCompanyForShow(job.getCompany()
				.getId(), 1, 99);
		request.setAttribute("jobList", jobList);
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

//	// 获得职位总个数
//	@RequestMapping("/getTotalCount")
//	@ResponseBody
//	public Map<String, Object> getTotalCount(HttpServletRequest request, HttpServletResponse response) {
//		log.info("--- getTotalCount ---");
//		//从cookie读取acode
//		String acode = CookieHelper.getCookieValue(request, Constants.AREA);
//		//如果地区code为三级, 为防止信息过少, 则自动转成显示本省内信息
//		acode = KitService.getProvinceCode(acode);
//		Map<String, Object> json = new HashMap<String, Object>();
//		Job job = new Job();
//		job.setArea(new Area(acode));
//		int total = jobService.getTotalCount(job,Boolean.TRUE);
//		json.put("totalCount321", total);
//		return json;
//	}

	// 多条件查询职位--给opencms框架使用
	@RequestMapping(value = "/searchForOpenCms", method = RequestMethod.GET)
	@ResponseBody
	public JSONPObject searchForOpenCms(
			@RequestParam(value = "callback") String callback,
			HttpServletRequest req) {
		log.info("--- searchForOpenCms ---");
		//接收从网站群接收来的地区code, 根据他查找所属地区的职位
		String acode = req.getParameter("acode");
		String pageSizeStr = req.getParameter("pageSize");
		// 初始化为10
		Integer pageSize = 10;
		if (pageSizeStr != null && !"".equals(pageSizeStr)) {
			pageSize = Integer.parseInt(pageSizeStr);
		}
		ModelMap map = new ModelMap();
		List<Job> jobList = jobService.getByNew(acode, pageSize);
		map.put("jobList", jobList);
		return new JSONPObject(callback, map);
	}
	
	// 导出所选信息 --不许删
	@RequestMapping(value = "/jobExportSelected", method = RequestMethod.POST  )
	@ResponseBody
	public String ExportSelected(
			@RequestParam(value = "params[]") int params[],
			HttpServletRequest req) {
		log.info("--------  down_multi ----------"+params.toString());
		boolean b = true;
		List<Job> job = new ArrayList<Job>();
		for (int i = 0; i < params.length; i++) {
			job.add(jobService.getOneForShow(params[i]));
		}
		String url = req.getRealPath("/");
		// 创建导出文件夹
		File uploadPath = new File(url + "upload");
		// 导出文件夹
		String exportFolder = uploadPath + File.separator + "job";
		File jobPath = new File(exportFolder);
		if (!(uploadPath.exists())) {
			uploadPath.mkdir();
		}
		if (!(jobPath.exists())) {
			jobPath.mkdir();
		}
		// 创建文件唯一名称
		String uuid = UUID.randomUUID().toString();
		String exportPath = exportFolder + File.separator + uuid + ".xls";
		String FileDownloadPath = "null";
		// 导出文件
		b = PoiCreateExcel.createJobExcel(exportPath, job);
		if (b) {
			String destPath = req.getLocalAddr() + ":" + req.getLocalPort() + req.getContextPath();
			FileDownloadPath = "http://" + destPath + "/upload/job/" + uuid + ".xls";
		}
		return FileDownloadPath;
		
	}
	
	//批量导出信息 --不许删
	@RequestMapping(value = "/jobExportAll", method = RequestMethod.POST  )
	@ResponseBody
	public String ExportAll(Job param,
			HttpServletRequest req) {
		boolean b = true;
		Job paramEntity = new Job();
		String targetName = param.getTargetName();
		String checkStatus = param.getCheckStatus();
		if (checkStatus == null || "".equals(checkStatus)) {
			checkStatus = Constants.CheckStatus.DAISHEN.getValue();
		}
		paramEntity.setName(targetName);
		paramEntity.setCheckStatus(checkStatus);
		Integer total = jobService.getTotalCount(paramEntity,Boolean.TRUE);
		Integer page=1;
		List<Job> job = jobService.getListShowForManage(
				paramEntity, page, total);
		String url = req.getRealPath("/");
	
		// 创建导出文件夹
		File uploadPath = new File(url + "upload");
		// 导出文件夹
		String exportFolder = uploadPath + File.separator + "job";
		File jobPath = new File(exportFolder);
		if (!(uploadPath.exists())) {
			uploadPath.mkdir();
		}
		if (!(jobPath.exists())) {
			jobPath.mkdir();
		}
		// 创建文件唯一名称
		String uuid = UUID.randomUUID().toString();
		String exportPath = exportFolder + File.separator + uuid + ".xls";
		String FileDownloadPath = "null";
		// 导出文件
		b = PoiCreateExcel.createJobExcel(exportPath, job);
		if (b) {
			String destPath = req.getLocalAddr() + ":" + req.getLocalPort() + req.getContextPath();
			FileDownloadPath = "http://" + destPath + "/upload/job/" + uuid + ".xls";
		}
		return FileDownloadPath;
	}

}
