package esd.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import esd.common.PoiCreateExcel;
import esd.service.AreaService;
import esd.service.CompanyService;
import esd.service.CookieHelper;
import esd.service.JobService;
import esd.service.KitService;
import esd.service.ParameterService;
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
	
	private static Logger log = Logger.getLogger(ResumeController.class);

	@RequestMapping("/search")
	public ModelAndView resume(HttpServletRequest request,
			HttpServletResponse response) {
		log.debug(request.getRequestURI());
		ModelAndView mav = new ModelAndView("emp/emp");
		// 先查看request中有没有传过来的acode, 不为空则是第一次进来, 将其中的acode放到cookie中
		String acode = request.getParameter("acode");
		if (acode != null && !"".equals(acode)) {
			CookieHelper.setCookie(response, Constants.AREA, acode,
					Integer.MAX_VALUE);
		}
		return mav;
	}

	// 根据id得到一个简历返回前台
	@RequestMapping("/getOneForShow")
	public String getOneForShow(HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes ra) {
		log.info("--- getOneForShow ---");
		// ①从cookie得到地区code, 如没有, 或者不等于昌吉的地区code, 则替换
		String acode = CookieHelper.getCookieValue(request, Constants.AREA);
		String changji = "20652300";
		if (!changji.equals(acode)) {
			/**** 不为空时, 则表示为从残联网站跳转过来的, 则清除原来可能存在的所有用户, 地区等cookie信息 ****/
			CookieHelper.killAllCookie(response, true);
			CookieHelper.setCookie(response, Constants.AREA, changji,
					Integer.MAX_VALUE);
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
		String companyId = CookieHelper.getCookieValue(request, Constants.USERCOMPANYID);
		if (companyId != null &&!"".equals(companyId)) {
				int cid = Integer.parseInt(companyId);
				// 公司基本信息
				Company company = companyService.getById(cid);
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
		return "emp/emp-detail";
	}

	// 多条件查询简历--给opencms框架使用
	@RequestMapping("/searchForOpenCms")
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
		// 条件查询得到符合条件的简历
		List<Resume> resumeList = resumeService.getByNew(acode, pageSize);
		map.put("resumeList", resumeList);
		return new JSONPObject(callback, map);
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
	
	// 下载所选简历信息    --别删
	@RequestMapping(value = "/resumeExportSelected", method = RequestMethod.POST  )
	@ResponseBody
	public String ExportSelected(
			@RequestParam(value = "params[]") int params[],
			HttpServletRequest req) {
		log.info("--------  down_multi ----------");
		boolean b = true;
		List<Resume> resumes = new ArrayList<Resume>();
		for (int i = 0; i < params.length; i++) {
			resumes.add(resumeService.getOneForShow(params[i]));
		}
		
		String url = req.getRealPath("/");
		// 创建导出文件夹
		File uploadPath = new File(url + "upload");
		// 导出文件夹
		String exportFolder = uploadPath + File.separator + "resumes";
		File resumesPath = new File(exportFolder);
		if (!(uploadPath.exists())) {
			uploadPath.mkdir();
		}
		if (!(resumesPath.exists())) {
			resumesPath.mkdir();
		}
		// 创建文件唯一名称
		String uuid = UUID.randomUUID().toString();
		String exportPath = exportFolder + File.separator + uuid + ".xls";
		String FileDownloadPath = "null";
		// 导出文件
		b = PoiCreateExcel.createResumeExcel(exportPath, resumes);
		if (b) {
			String destPath = req.getLocalAddr() + ":" + req.getLocalPort() + req.getContextPath();
			FileDownloadPath = "http://" + destPath + "/upload/resumes/" + uuid + ".xls";
		}
		return FileDownloadPath;
	}
	
	
	
	// 批量下载信息    --别删
		@RequestMapping(value = "/resumeExportAll", method = RequestMethod.POST  )
		@ResponseBody
		public String ExportAll(Resume param,
				HttpServletRequest req) {
			boolean b = true;
			Resume paramEntity = new Resume();
			String targetName = param.getTargetName();
			String checkStatus = param.getCheckStatus();
			if (checkStatus == null || "".equals(checkStatus)) {
				checkStatus = Constants.CheckStatus.DAISHEN.getValue();
			}
			paramEntity.setName(targetName);
			paramEntity.setCheckStatus(checkStatus);
			Integer total = resumeService.getTotalCount(paramEntity,Boolean.FALSE);
			Integer page=1;
			List<Resume> resume = resumeService.getListShowForManage(
					paramEntity, page, total);
			String url = req.getRealPath("/");
		
			// 创建导出文件夹
			File uploadPath = new File(url + "upload");
			// 导出文件夹
			String exportFolder = uploadPath + File.separator + "resume";
			File resumePath = new File(exportFolder);
			if (!(uploadPath.exists())) {
				uploadPath.mkdir();
			}
			if (!(resumePath.exists())) {
				resumePath.mkdir();
			}
			// 创建文件唯一名称
			String uuid = UUID.randomUUID().toString();
			String exportPath = exportFolder + File.separator + uuid + ".xls";
			String FileDownloadPath = "null";
			// 导出文件
			b = PoiCreateExcel.createResumeExcel(exportPath, resume);
			if (b) {
				String destPath = req.getLocalAddr() + ":" + req.getLocalPort() + req.getContextPath();
				FileDownloadPath = "http://" + destPath + "/upload/resume/" + uuid + ".xls";
			}
			return FileDownloadPath;
		}
	
}
