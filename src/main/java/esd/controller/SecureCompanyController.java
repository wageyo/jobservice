package esd.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import esd.controller.Constants.Notice;
import esd.service.AreaService;
import esd.service.BusinessScopeService;
import esd.service.CompanyService;
import esd.service.CookieHelper;
import esd.service.JobCategoryService;
import esd.service.KitService;
import esd.service.ParameterService;
import esd.service.UserService;

@Controller
@RequestMapping("/secure/company")
public class SecureCompanyController {

	private static Logger log = Logger.getLogger(SecureCompanyController.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	private CompanyService companyService;

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
	public String save(Company company, HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes ra) {
		log.info("--- save post ---");
		if (company == null) {
			ra.addFlashAttribute("messageType", "0");
			ra.addFlashAttribute("message", "传递的数据为空!");
			return "/user/goCenter";
		}
		String userId = CookieHelper.getCookieValue(request, Constants.USERID);
		Integer uid = Integer.parseInt(userId);
		User user = userService.getById(uid);
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
		CookieHelper.setCookie(response, Constants.USERCOMPANYID, String.valueOf(company.getId()));
		ra.addFlashAttribute("messageURL", "/user/goCenter");
		ra.addFlashAttribute("message", "保存公司信息成功!");
		return "redirect:/secure/company/save";
	}

	// 跳转到保存公司信息页面
	@RequestMapping(value = "/save", method = RequestMethod.GET)
	public ModelAndView gotoCompanyAdd(HttpServletRequest request) {
		log.info("----- save get -----");
		ModelAndView mav = new ModelAndView("company/info-add");
		List<Parameter> plist = parameterService.getAll();
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
	public Map<String, Object> delete(HttpServletRequest request) {
		log.info("--- delete ---");
		Map<String, Object> json = new HashMap<String, Object>();
		String idStr = request.getParameter("id");
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
	public String update(Company company, HttpServletRequest request, HttpServletResponse response,
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
		ra.addFlashAttribute("messageURL", "/user/goCenter");
		ra.addFlashAttribute("message", "更新公司信息成功!");
		return "redirect:/secure/company/update";
	}

	// 跳转到企业信息修改页面
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String getCompanyForEdit(HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes ra) {
		log.info("----- save get -----");
		// 先查看是否添加了企业信息, 没有添加的话则提示
		String companyId  = CookieHelper.getCookieValue(request, Constants.USERCOMPANYID);
		if (companyId == null || "".equals(companyId)) {
			ra.addFlashAttribute("messageType", "0");
			ra.addFlashAttribute("message", "填写完公司信息后才可以发布招聘信息");
			return "redirect:/secure/company/save";
		}
		Integer cid = Integer.parseInt(companyId);
		//企业信息
		Company company = companyService.getById(cid);
		request.setAttribute(Constants.Identity.COMPANY.getValue(), company);
		//相关参数
		List<Parameter> plist = parameterService.getAll();
		request.setAttribute("params", plist);
		// 工作地区
		List<Area> alist = areaService.getProvinceList();
		request.setAttribute("provinceList", alist);
		// 经营范围
		List<BusinessScope> blist = bsService.getAll();
		request.setAttribute("bsList", blist);
		return "company/info-edit";
	}

	// 得到当前公司收到的所有简历
	@RequestMapping(value = "/getAllGotResume/{page}", method = RequestMethod.GET)
	public String getAllGotResume(HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes ra, @PathVariable(value = "page") Integer page) {
		// 先查看是否添加了企业信息, 没有添加的话则提示
		String  companyId= CookieHelper.getCookieValue(request, Constants.USERCOMPANYID);
		if (companyId == null || "".equals(companyId)) {
			ra.addFlashAttribute("messageType", "0");
			ra.addFlashAttribute("message", "还没有填写公司信息, 请先填写完公司信息后再进行操作!");
			return "redirect:/secure/company/save";
		}
		Integer cid = Integer.parseInt(companyId);
		Company company = companyService.getById(cid);
		List<Record> list = companyService.getAllGotResume(company.getId(),
				page, Constants.SIZE);
		int totalCount = companyService.getAllGotResumeCount(company.getId());
		request.setAttribute("recordList", list);
		request.setAttribute("totalCount", totalCount);
		request.setAttribute("currentPage", page);
		request.setAttribute(
				"totalPages",
				totalCount % Constants.SIZE == 0 ? (totalCount / Constants.SIZE)
						: (totalCount / Constants.SIZE + 1));
		return "/company/resume-record";
	}

}
