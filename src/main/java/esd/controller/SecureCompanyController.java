package esd.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import esd.bean.Area;
import esd.bean.BusinessScope;
import esd.bean.Company;
import esd.bean.JobCategory;
import esd.bean.Parameter;
import esd.bean.Record;
import esd.bean.User;
import esd.controller.Constants.Identity;
import esd.controller.Constants.Notice;
import esd.service.AreaService;
import esd.service.BusinessScopeService;
import esd.service.CompanyService;
import esd.service.JobCategoryService;
import esd.service.KitService;
import esd.service.ParameterService;

@Controller
@RequestMapping("/secure/company")
public class SecureCompanyController {

	private static Logger log = Logger.getLogger(SecureCompanyController.class);

	@Autowired
	private CompanyService<Company> companyService;

	@Autowired
	private ParameterService parameterService;

	@Autowired
	private JobCategoryService jcService;

	@Autowired
	private AreaService areaService;

	@Autowired
	private BusinessScopeService bsService;

	// 保存一个企业用户
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Company company, HttpSession session,
			RedirectAttributes ra) {
		log.info("--- save post ---");
		if (company == null) {
			ra.addFlashAttribute("messageType", "0");
			ra.addFlashAttribute("message", "传递的数据为空!");
			return "/user/goCenter";
		}
		User user = (User) session.getAttribute(Constants.USER);
		//所属人
		company.setUser(user);
		//所属地区
		company.setArea(user.getArea());
		boolean cbl = companyService.save(company);
		if (!cbl) {
			ra.addFlashAttribute("messageType", "0");
			ra.addFlashAttribute("message", "保存公司信息失败, 请重新操作!");
			return "/user/goCenter";
		}
		log.info("company : " + company);
		company = companyService.getById(company.getId());
		session.setAttribute("company", company);
		ra.addFlashAttribute("messageURL", "/user/goCenter");
		ra.addFlashAttribute("message", "保存公司信息成功!");
		return "redirect:/secure/company/save";
	}

	// 跳转到保存公司信息页面
	@RequestMapping(value = "/save", method = RequestMethod.GET)
	public ModelAndView gotoCompanyAdd(HttpServletRequest req) {
		log.info("----- save get -----");
		ModelAndView mav = new ModelAndView("company/info-add");
		List<Parameter> plist = parameterService.getAll();
		// 残疾类别
		// 残疾等级
		// 残疾部位
		// 户口状况
		// 文化程度
		mav.addObject("params", plist);
		// 职位类别
		List<JobCategory> jlist = jcService.getAll();
		mav.addObject("jcList", jlist);
		// 工作地区
		List<Area> alist = areaService.getProvinceList();
		mav.addObject("provinceList", alist);
		// 经营范围
		List<BusinessScope> blist = bsService.getAll();
		mav.addObject("bsList", blist);
		return mav;
	}

	// 删除企业用户
	@RequestMapping("/delete")
	@ResponseBody
	public Map<String, Object> delete(HttpServletRequest req) {
		log.info("--- delete ---");
		Map<String, Object> json = new HashMap<String, Object>();
		String idStr = req.getParameter("id");
		int id = KitService.getInt(idStr);
		if (id < 0) {
			json.put("notice", Notice.ERROR);
			return json;
		}
		boolean bl = companyService.delete(id);
		log.info("companyDelete bl = " + bl);
		if (!bl) {
			json.put("notice", Notice.FAILURE);
			return json;
		}
		json.put("notice", Notice.SUCCESS);
		return json;
	}

	// 保存修改后的企业信息
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Company company, HttpSession session,
			RedirectAttributes ra) {
		log.info("--- update post ---");
		if (company == null) {
			ra.addFlashAttribute("messageType", "0");
			ra.addFlashAttribute("message", "保存的数据不能为空!");
			return "redirect:/secure/company/update";
		}
		boolean bl = companyService.update(company);
		log.info("save bl = " + bl);
		if (!bl) {
			ra.addFlashAttribute("messageType", "0");
			ra.addFlashAttribute("message", "操作失败!");
			return "redirect:/secure/company/update";
		}
		// 重新读取企业信息
		company = companyService.getById(company.getId());
		session.setAttribute("company", company);
		ra.addFlashAttribute("messageURL", "/user/goCenter");
		ra.addFlashAttribute("message", "更新公司信息成功!");
		return "redirect:/secure/company/update";
	}

	// 跳转到企业信息修改页面
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String getCompanyForEdit(HttpServletRequest req,
			HttpSession session, RedirectAttributes ra) {
		log.info("----- save get -----");
		// 先查看是否添加了企业信息, 没有添加的话则提示
		User user = (User) session.getAttribute(Constants.USER);
		Company c = companyService.getByAccount(user.getId());
		if (c == null) {
			return "redirect:/secure/company/save";
		}
		List<Parameter> plist = parameterService.getAll();
		req.setAttribute("params", plist);
		// 工作地区
		List<Area> alist = areaService.getProvinceList();
		req.setAttribute("provinceList", alist);
		// 经营范围
		List<BusinessScope> blist = bsService.getAll();
		req.setAttribute("bsList", blist);
		return "company/info-edit";
	}

	// 根据企业id, 得到当前企业用户自身对象
	// @RequestMapping("/getOne")
	// public String getOne(HttpServletRequest req, HttpSession session) {
	// log.info("--- getOne ---");
	// User user = (User) session.getAttribute(Constants.USER);
	// if (user == null) {
	// req.setAttribute("notice", "请登录!");
	// return "redirect:/index.jsp";
	// }
	// Company company = companyService.getByAccount(user.getId());
	// req.setAttribute("company", company);
	// return "/company/infoEdit";
	// }

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

	// 得到当前公司收到的所有简历
	@RequestMapping(value = "/getAllGotResume/{page}", method = RequestMethod.GET)
	public String getAllGotResume(HttpServletRequest req, HttpSession session,
			RedirectAttributes ra, @PathVariable(value = "page") Integer page) {

		User user = (User) session.getAttribute(Constants.USER);
		if (!user.getIdentity().equals(Identity.COMPANY.toString())) {
			ra.addFlashAttribute("message", "权限不足!");
			ra.addFlashAttribute("messageType", "0");
			return "/user/goCenter";
		}
		// 先查看是否添加了企业信息, 没有添加的话则提示
		Company company = companyService.getByAccount(user.getId());
		if (company == null) {
			ra.addFlashAttribute("messageType", "0");
			ra.addFlashAttribute("message", "还没有填写公司信息, 请先填写完公司信息后再进行操作!");
			return "redirect:/secure/company/save";
		}
		List<Record> list = companyService.getAllGotResume(company.getId(),
				page, Constants.SIZE);
		int totalCount = companyService.getAllGotResumeCount(company.getId());
		req.setAttribute("recordList", list);
		req.setAttribute("totalCount", totalCount);
		req.setAttribute("currentPage", page);
		req.setAttribute(
				"totalPages",
				totalCount % Constants.SIZE == 0 ? (totalCount / Constants.SIZE)
						: (totalCount / Constants.SIZE + 1));
		return "/company/resume-record";
	}

	// 批量删除投递来的简历
	// @RequestMapping("/deleteGotResume")
	// public String deleteGotResume(HttpServletRequest req, HttpSession
	// session) {
	//
	// return null;
	// }
}
