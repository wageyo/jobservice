package esd.controller;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import esd.bean.Company;
import esd.bean.Job;
import esd.bean.User;
import esd.service.CompanyService;
import esd.service.JobService;
import esd.service.KitService;

/**
 * 企业信息  常用controller
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/company")
public class CompanyController {

	private static Logger log = Logger.getLogger(CompanyController.class);

	@Autowired
	private CompanyService<Company> companyService;

	// @Autowired
	// private ParameterService parameterService;

	@Autowired
	private JobService jobService;

	// @Autowired
	// private AreaService areaService;

	// 根据id得到一个公司对象返回前台以供显示,连带该公司发布的招聘信息
	@RequestMapping("/getOneForShow")
	public String getOneForShow(HttpServletRequest req) {
		log.info("--- getOneForShow ---");
		String idStr = req.getParameter("id");
		int id = KitService.getInt(idStr);
		if (id < 0) {
			req.setAttribute("notice", "传递的参数有误!");
			return "/error";
		}
		Company company = companyService.getOneForShow(id);
		req.setAttribute("company", company);
		List<Job> jobList = jobService.getByCompanyForShow(company.getId(), 1, 10);
		req.setAttribute("jobList", jobList);
		return "work/company-detail";
	}

	// 根据企业id, 得到当前企业用户自身对象
	@RequestMapping("/getOne")
	public String getOne(HttpServletRequest req, HttpSession session) {
		log.info("--- getOne ---");
		User user = (User) session.getAttribute(Constants.USER);
		if (user == null) {
			req.setAttribute("notice", "请登录!");
			return "redirect:/index.jsp";
		}
		Company company = companyService.getByAccount(user.getId());
		req.setAttribute("company", company);
		return "/company/infoEdit";
	}

	// 下载公司招聘信息--返回公司word文件路径, 正常
	@RequestMapping({ "/down_back/{id}" })
	public String down1(@PathVariable(value = "id") Integer id,
			HttpServletRequest req) {
		String url = req.getRealPath("/");
		log.info("****************************");
		String filePath = companyService.getBuildCompany(id, url);
		if(filePath == null){
			return null;
		}
		log.info("filePath  "+filePath);
		if (new File(url+filePath).exists()) {
			String destPath = req.getLocalAddr() + ":" + req.getLocalPort()
					+ req.getContextPath();
			log.info("redirect:http://" + destPath + "/" + filePath);
			return "redirect:http://" + destPath + "/"+filePath;
		}
		return null;
	}
	
	// 批量下载公司 --测试用
		@RequestMapping({ "/down_multi" })
		public String down2(HttpServletRequest req) {
			log.info("--------  down_multi ----------");
			String url = req.getRealPath("/");
			log.info("url : " + url);
			int[] ids = { 40,41,52 };
			String filePath = companyService.getBuildCompany(ids, url);
			log.info("filePath : " + filePath);
			if (new File(url+filePath).exists()) {
				String destPath = req.getLocalAddr() + ":" + req.getLocalPort()
						+ req.getContextPath();
				log.info("destPath : " + destPath);
				return "redirect:http://" + destPath + "/" + filePath;
			}
			return null;
		}
	
	// 根据地区code, 得到本地区所有显示的企业
	// @RequestMapping("/getByArea")
	// @ResponseBody
	// public Map<String, Object> getByArea(HttpServletRequest req) {
	// log.info("--- getByArea ---");
	// Map<String, Object> json = new HashMap<String, Object>();
	// String code = req.getParameter("areaCode");
	// if (code == null || "".equals(code)) {
	// json.put("notice", Notice.ERROR);
	// return json;
	// }
	// String startStr = req.getParameter("start");
	// int start = KitService.getInt(startStr);
	// if (start < 0) {
	// start = 1;
	// }
	// String sizeStr = req.getParameter("size");
	// int size = KitService.getInt(sizeStr);
	// if (size < 0) {
	// size = Constants.SIZE;
	// }
	// List<Company> list = companyService.getByArea(code, start, size);
	// json.put("notice", Notice.SUCCESS);
	// json.put("companyList", list);
	// return json;
	// }

	// 批量删除投递来的简历
	// @RequestMapping("/deleteGotResume")
	// public String deleteGotResume(HttpServletRequest req, HttpSession
	// session) {
	//
	// return null;
	// }

}
