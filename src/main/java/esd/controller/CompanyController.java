package esd.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import esd.bean.Company;
import esd.bean.Job;
import esd.bean.Resume;
import esd.bean.User;
import esd.service.CompanyService;
import esd.service.CookieHelper;
import esd.service.JobService;
import esd.service.KitService;
import esd.service.RecordService;
import esd.service.ResumeService;

/**
 * 企业信息 常用controller
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping("/company")
public class CompanyController {

	private static Logger log = Logger.getLogger(CompanyController.class);

	@Autowired
	private CompanyService<Company> companyService;

	@Autowired
	private RecordService recordService;

	@Autowired
	private JobService jobService;
	
	@Autowired
	private ResumeService resumeService;

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
		List<Job> jobList = jobService.getByCompanyForShow(company.getId(), 1,
				10);
		req.setAttribute("jobList", jobList);
		return "work/company-detail";
	}

	// 根据企业id, 得到当前企业用户自身对象
	@RequestMapping("/getOne")
	public String getOne(HttpServletRequest request, HttpServletResponse response) {
		log.info("--- getOne ---");
		String companyId  = CookieHelper.getCookieValue(request, Constants.USERCOMPANYID);
		if (companyId == null || "".equals(companyId)) {
			request.setAttribute("notice", "请先登录!");
			return "redirect:/index";
		}
		Integer cid = Integer.parseInt(companyId);
		//企业信息
		Company company = companyService.getById(cid);
		request.setAttribute("company", company);
		return "/company/infoEdit";
	}

	// 下载公司招聘信息--返回公司word文件路径, 正常
	@RequestMapping({ "/down_back/{id}" })
	public String down1(@PathVariable(value = "id") Integer id,
			HttpServletRequest req) {
		String url = req.getRealPath("/");
		log.info("****************************");
		String filePath = companyService.getBuildCompany(id, url);
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

	// 批量下载公司 --测试用
	@RequestMapping({ "/down_multi" })
	public String down2(HttpServletRequest req) {
		log.info("--------  down_multi ----------");
		String url = req.getRealPath("/");
		log.info("url : " + url);
		int[] ids = { 40, 41, 52 };
		String filePath = companyService.getBuildCompany(ids, url);
		log.info("filePath : " + filePath);
		if (new File(url + filePath).exists()) {
			String destPath = req.getLocalAddr() + ":" + req.getLocalPort()
					+ req.getContextPath();
			log.info("destPath : " + destPath);
			return "redirect:http://" + destPath + "/" + filePath;
		}
		return null;
	}

	// 公司向某职位发送简历邀请
	@ResponseBody
	@RequestMapping(value = "/sendInvite", method = RequestMethod.POST)
	public Map<String, Object> sendInvite(HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		int rid = Integer.parseInt(request.getParameter("rid"));
		int jid = Integer.parseInt(request.getParameter("jid"));
		String comment = request.getParameter("comment");
		// 将数据保存在关系表中
		Resume resume = resumeService.getById(rid);
		Job job = jobService.getById(jid);
		job.setMark(comment);	//使用备注字段 将要发送的话带进到record的comment字段中.
		//检查企业用户1天内是否发送过邀请
		int sentInvite = recordService.checkSentInSomeDays(null, null, rid, job.getCompany().getId(), Boolean.FALSE);
		if(sentInvite > 0){
			map.put(Constants.NOTICE, "三天内只能向同一份简历发送一份邀请, 不要重复操作!");
			return map;
		}
		boolean bl = recordService.sendResumeOrInvite(resume, job, Boolean.FALSE);
		if (bl) {
			map.put(Constants.NOTICE, Constants.Notice.SUCCESS.toString());
		} else {
			map.put(Constants.NOTICE, Constants.Notice.FAILURE.toString());
		}
		return map;
	}

}
