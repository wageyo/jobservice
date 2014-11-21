package esd.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import esd.bean.Company;
import esd.bean.Job;
import esd.bean.JobCategory;
import esd.bean.Resume;
import esd.bean.User;
import esd.service.AreaService;
import esd.service.CompanyService;
import esd.service.JobService;
import esd.service.KitService;
import esd.service.ResumeService;

@Controller
@RequestMapping("/resume")
public class ResumeController {

	// @Value("${templateFile}")
	// private String templateFile;
	//
	// @Value("${destFileName}")
	// private String destFileName;

	@Autowired
	private ResumeService resumeService;

	@Autowired
	private JobService jobService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private AreaService areaService;

	private static Logger log = Logger.getLogger(ResumeController.class);

	@RequestMapping("/search")
	public ModelAndView resume(HttpServletRequest request,HttpSession session) {
		log.debug(request.getRequestURI());
		ModelAndView mav = new ModelAndView("emp/emp");
		//先查看request中有没有传过来的acode, 不为空则是第一次进来, 将其中的acode放到session中
		String acode= request.getParameter("acode");
		if(acode != null && !"".equals(acode)){
			Area area = areaService.getByCode(acode);
			session.setAttribute("area", area);
		}
		return mav;
	}

	// 多条件职位简历
	@RequestMapping(value = "/search/{page}", method = RequestMethod.POST)
	public ModelAndView search(HttpServletRequest req,
			@PathVariable(value = "page") Integer page, HttpSession session) {
		log.info("--- search ---");
		Map<String, Object> entity = new HashMap<String, Object>();
		Resume resume = new Resume();
		//一, 初始acode
		String acode = "10000000";
		//二,从session读取area
		Object obj = session.getAttribute("area");
		if(obj!=null){
			acode = ((Area)obj).getCode();
		}
		//如果地区code为三级, 为防止信息过少, 则自动转成显示本省内信息
		String belongsAcode = KitService.getProvinceCode(acode);
		resume.setArea(new Area(belongsAcode));
				
		String keyWord = req.getParameter("keyWord");
		if (keyWord != null && !"".equals(keyWord)) {
			resume.setTitle(keyWord);
		}
		String jcCode = req.getParameter("jcCode");
		if (jcCode != null && !"".equals(jcCode)) {
			resume.setDesireJob(new JobCategory(jcCode));
		}
		String education = req.getParameter("education");
		if (education != null && !"".equals(education)) {
			resume.setEducation(education);
		}
		String jobNature = req.getParameter("jobNature");
		if (jobNature != null && !"".equals(jobNature)) {
			resume.setJobNature(jobNature);
		}
		String gender = req.getParameter("gender");
		if (gender != null && !"".equals(gender)) {
			resume.setGender(gender);
		}
		List<Resume> resumeList = resumeService.getForListShow(resume, page,
				Constants.SIZE);
		Integer records = resumeService.getTotalCount(resume);
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
					if(it.getDesireJob()!=null){
						if(it.getDesireJob().getName()!=null && !"".equals(it.getDesireJob().getName())){
							map.put("desireJob", it.getDesireJob().getName());
						}
					}else{
						map.put("desireJob", Constants.NO_LIMIT);
					}
					if(it.getDesireAddress()!=null){
						if(it.getDesireAddress().getName()!=null && !"".equals(it.getDesireAddress().getName())){
							map.put("desireAddress", it.getDesireAddress().getName());
						}
					}else{
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

		entity.put("list", list);
		PaginationUtil pagination = new PaginationUtil(page, records);
		entity.put("pagination", pagination.getHandler());
		return new ModelAndView("emp/emp-json", "entity", entity);
	}

	// 多条件查询简历--给opencms框架使用
	@RequestMapping("/searchForOpenCms")
	@ResponseBody
	public JSONPObject searchForOpenCms(
			@RequestParam(value = "callback") String callback,
			HttpServletRequest req) {
		log.info("--- searchForOpenCms ---");
		// //接收从网站群接收来的地区code, 根据他查找所属地区的职位
		String acode = req.getParameter("acode");
		String pageSizeStr = req.getParameter("pageSize");
		// 初始化为10
		Integer pageSize = 10;
		if (pageSizeStr != null && !"".equals(pageSizeStr)) {
			pageSize = Integer.parseInt(pageSizeStr);
		}
		ModelMap map = new ModelMap();
		// 条件查询得到符合条件的简历
		List<Resume> resumeList = resumeService.getByNew(
				KitService.getProvinceCode(acode), pageSize);
		map.put("resumeList", resumeList);
		return new JSONPObject(callback, map);
	}

	// 根据id得到一个简历返回前台
	@RequestMapping("/getOneForShow")
	public String getOneForShow(HttpServletRequest request, HttpSession session,RedirectAttributes ra) {
		log.info("--- getOneForShow ---");
		//①先查看request中有没有传过来的acode, 
		String acode= request.getParameter("acode");
		if(acode != null){
			//②不为空则是第一次进来, 将其中的acode放到session中
			Area area = areaService.getByCode(acode);
			session.setAttribute("area", area);
		}else{
			//③为空在则检查session是中没有地区信息
			Object obj = session.getAttribute("area");
			if(obj!=null){
				acode = ((Area)obj).getCode();
			}
		}
				
		String idStr = request.getParameter("id");
		log.info("idStr = " + idStr);
		int id = KitService.getInt(idStr);
		if (id < 0) {
			return "forward:/resume/search";
		}
		Resume resume = resumeService.getOneForShow(id);
		request.setAttribute("resume", resume);
		// 如果为公司用户访问该简历, 则查询出公司的基本信息
		User user = (User) session.getAttribute(Constants.USER);
		if (user != null) {
			if (Constants.Identity.COMPANY.toString()
					.equals(user.getIdentity())) {
				// 公司基本信息
				Company company = companyService.getByAccount(user.getId());
				if (company == null) {
					ra.addFlashAttribute("messageType", "0");
					ra.addFlashAttribute("message", "请先完善公司信息");
					return "forward:/secure/company/save";
				}
				Company model = new Company();
				model.setId(company.getId());
				model.setContactPerson(company.getContactPerson());
				model.setContactDept(company.getContactDept());
				model.setTelephone(company.getTelephone());
				// 发布的职位信息
				List<Job> list = jobService.getByCompany(company.getId(),
						Constants.START, Constants.SIZE);
				List<Job> jobList = new ArrayList<Job>();
				Job head = new Job();
				head.setId(0);
				head.setName("请选择");
				jobList.add(head);
				for (Job job : list) {
					Job j = new Job();
					j.setId(job.getId());
					j.setName(job.getName());
					jobList.add(j);
				}
				request.setAttribute("company", model);
				request.setAttribute("jobList", jobList);
			}
		}
		return "emp/emp-detail";
	}

	// 下载简历--返回流, 在IE8下报错
	@RequestMapping({ "/down/{id}" })
	public ResponseEntity<byte[]> down(@PathVariable(value = "id") Integer id,
			HttpServletRequest req) {
		// String url =
		// req.getLocalAddr()+":"+req.getLocalPort()+req.getContextPath();
		String url = req.getRealPath("/");
		log.info("****************************");
		String filePath = resumeService.getBuildResume(id, url);
		HttpHeaders headers = new HttpHeaders();
		if (new File(filePath).exists()) {
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			headers.setContentDispositionFormData("attachment", "resume.xls");
			try {
				return new ResponseEntity<byte[]>(
						FileUtils.readFileToByteArray(new File(filePath)),
						headers, HttpStatus.CREATED);
			} catch (IOException e) {
				log.error("", e);
			}
		}
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", "error.txt");
		return null;
	}

	// 下载简历备用方案启用--返回简历路径, 正常
	@RequestMapping({ "/down_back/{id}" })
	public String down1(@PathVariable(value = "id") Integer id,
			HttpServletRequest req) {
		String url = req.getRealPath("/");
		log.info("****************************");
		String filePath = resumeService.getBuildResume(id, url);
		if (filePath == null) {
			return null;
		}
		log.info("filePath  " + filePath);
		if (new File(url + filePath).exists()) {
			String destPath = req.getLocalAddr() + ":" + req.getLocalPort()
					+ req.getContextPath();
			log.info("redirect:http://" + destPath + "/" + filePath);
			return "redirect:http://" + destPath + "/" + filePath;
		}
		return null;
	}

	// 批量下载简历 --测试用
	@RequestMapping({ "/down_multi" })
	public String down2(HttpServletRequest req) {
		log.info("--------  down_multi ----------");
		String url = req.getRealPath("/");
		log.info("url : " + url);
		int[] ids = { 152, 153, 154 };
		String filePath = resumeService.getBuildResume(ids, url);
		log.info("filePath : " + filePath);
		if (new File(url + filePath).exists()) {
			String destPath = req.getLocalAddr() + ":" + req.getLocalPort()
					+ req.getContextPath();
			log.info("destPath : " + destPath);
			return "redirect:http://" + destPath + "/" + filePath;
		}
		return null;
	}
}
