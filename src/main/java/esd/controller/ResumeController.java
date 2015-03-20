package esd.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.util.JSONPObject;

import esd.bean.Area;
import esd.bean.Company;
import esd.bean.Job;
import esd.bean.Parameter;
import esd.bean.Resume;
import esd.bean.User;
import esd.bean.WhiteList;
import esd.common.PoiCreateExcel;
import esd.service.AreaService;
import esd.service.CompanyService;
import esd.service.CookieHelper;
import esd.service.JobService;
import esd.service.KitService;
import esd.service.ParameterService;
import esd.service.ResumeService;
import esd.service.UserService;
import esd.service.WhiteListService;

@Controller
@RequestMapping("/resume")
public class ResumeController {

	// @Value("${templateFile}")
	// private String templateFile;
	//
	// @Value("${destFileName}")
	// private String destFileName;

	@Autowired
	private UserService<User> userService;

	@Autowired
	private ResumeService resumeService;

	@Autowired
	private JobService jobService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private AreaService areaService;
	
	@Autowired
	private ParameterService parameterService;
	
	@Autowired
	private WhiteListService whiteListService;

	private static Logger log = Logger.getLogger(ResumeController.class);

	// 根据id得到一个简历返回前台
	@RequestMapping("/getOneForShow")
	public String getOneForShow(HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes ra) {
		log.info("--- getOneForShow ---");
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
				cookieAreaCode = Constants.AREACOUNTRY;
			}
			acode = cookieAreaCode;
		}
		// ④得到地区信息对象, 将地区名称放入到cookie中
		Area area = areaService.getByCode(acode);
		CookieHelper.setCookie(response, request, null, area);

		String idStr = request.getParameter("id");
		log.info("idStr = " + idStr);
		int id = KitService.getInt(idStr);
		if (id < 0) {
			return "forward:/resume/search";
		}
		Resume resume = resumeService.getOneForShow(id);
		request.setAttribute("resume", resume);
		// 如果为公司用户访问该简历, 则查询出公司的基本信息
		String companyId = CookieHelper.getCookieValue(request,
				Constants.USERCOMPANYID);
		if (companyId != null && !"".equals(companyId)) {
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
			HttpServletRequest request) {
		log.info("--- searchForOpenCms ---");
		ModelMap map = new ModelMap();
		// 检查白名单功能是否开启
		Parameter whiteList = parameterService
				.getById(Constants.WHITE_LIST_SWITCH);
		// 如果白名单功能开启的话, 则检查请求url地址是否正确
		if (Constants.SWITCH_ON.equals(whiteList.getValue())) {
			String ip = request.getRemoteAddr();	//ip
			String domainName = request.getRemoteHost();	//域名
			WhiteList result = whiteListService.checkWhiteList(ip,domainName);
			log.info("result:  " + result);
			// 如果请求的url地址中包含的域名 不在白名单中, 则跳转到提示拒绝访问页面
			if (result == null) {
				map.put(Constants.NOTICE, "您不在白名单中, 暂时无权访问数据, 请联系网站管理人员.");
				new JSONPObject(callback, map);
			}
		}
		// 接收从网站群接收来的地区code, 根据他查找所属地区的职位
		String acode = request.getParameter("acode");
		String pageSizeStr = request.getParameter("pageSize");
		// 初始化为10
		Integer pageSize = 10;
		if (pageSizeStr != null && !"".equals(pageSizeStr)) {
			pageSize = Integer.parseInt(pageSizeStr);
		}
		// 条件查询得到符合条件的简历
		List<Resume> resumeList = resumeService.getByNew(acode, pageSize);
		map.put("resumeList", resumeList);
		return new JSONPObject(callback, map);
	}

	// 下载简历--返回流, 在IE8下报错
	@RequestMapping({ "/down/{id}" })
	public ResponseEntity<byte[]> down(@PathVariable(value = "id") Integer id,
			HttpServletRequest request) {
		// String url =
		// request.getLocalAddr()+":"+request.getLocalPort()+request.getContextPath();
		String url = request.getSession().getServletContext().getRealPath("/");
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
			HttpServletRequest request) {
		String url = request.getSession().getServletContext().getRealPath("/");
		log.info("****************************");
		String filePath = resumeService.getBuildResume(id, url);
		if (filePath == null) {
			return null;
		}
		log.info("filePath  " + filePath);
		if (new File(url + filePath).exists()) {
			String destPath = request.getLocalAddr() + ":" + request.getLocalPort()
					+ request.getContextPath();
			log.info("redirect:http://" + destPath + "/" + filePath);
			return "redirect:http://" + destPath + "/" + filePath;
		}
		return null;
	}

	// 批量下载简历 --测试用
	@RequestMapping({ "/down_multi" })
	public String down2(HttpServletRequest request) {
		log.info("--------  down_multi ----------");
		String url = request.getSession().getServletContext().getRealPath("/");
		log.info("url : " + url);
		int[] ids = { 152, 153, 154 };
		String filePath = resumeService.getBuildResume(ids, url);
		log.info("filePath : " + filePath);
		if (new File(url + filePath).exists()) {
			String destPath = request.getLocalAddr() + ":" + request.getLocalPort()
					+ request.getContextPath();
			log.info("destPath : " + destPath);
			return "redirect:http://" + destPath + "/" + filePath;
		}
		return null;
	}

	// 下载所选简历信息 --别删
	@RequestMapping(value = "/resumeExportSelected", method = RequestMethod.POST)
	@ResponseBody
	public String ExportSelected(
			@RequestParam(value = "params[]") int params[],
			HttpServletRequest request) {
		log.info("--------  down_multi ----------");
		boolean b = true;
		List<Resume> resumes = new ArrayList<Resume>();
		for (int i = 0; i < params.length; i++) {
			resumes.add(resumeService.getOneForShow(params[i]));
		}

		String url = request.getSession().getServletContext().getRealPath("/");
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
			String destPath = request.getLocalAddr() + ":" + request.getLocalPort()
					+ request.getContextPath();
			FileDownloadPath = "http://" + destPath + "/upload/resumes/" + uuid
					+ ".xls";
		}
		return FileDownloadPath;
	}

	// 批量下载信息 --别删
	@RequestMapping(value = "/resumeExportAll", method = RequestMethod.POST)
	@ResponseBody
	public String ExportAll(Resume param, HttpServletRequest request) {
		boolean b = true;
		Resume paramEntity = new Resume();
		String targetName = param.getTargetName();
		String checkStatus = param.getCheckStatus();
		if (checkStatus == null || "".equals(checkStatus)) {
			checkStatus = Constants.CheckStatus.DAISHEN.getValue();
		}
		paramEntity.setName(targetName);
		paramEntity.setCheckStatus(checkStatus);
		// 获取地区码
		String userId = CookieHelper.getCookieValue(request,
				Constants.ADMINUSERID);
		Integer uid = Integer.parseInt(userId);
		User userObj = userService.getById(uid);
		// 根据管理员用户所属地区, 查询他下面所属的所有数据
		String acode = userObj.getArea().getCode();
		paramEntity.setArea(new Area(acode));
		Integer page = 1;
		List<Resume> resume = resumeService.getListShowForManage(paramEntity,
				page, Integer.MAX_VALUE,Boolean.FALSE);
		String url = request.getSession().getServletContext().getRealPath("/");

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
			String destPath = request.getLocalAddr() + ":"
					+ request.getLocalPort() + request.getContextPath();
			FileDownloadPath = "http://" + destPath + "/upload/resume/" + uuid
					+ ".xls";
		}
		return FileDownloadPath;
	}

}
