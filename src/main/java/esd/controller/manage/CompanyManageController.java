package esd.controller.manage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import esd.bean.Area;
import esd.bean.Company;
import esd.bean.User;
import esd.controller.Constants;
import esd.service.AreaService;
import esd.service.CompanyService;
import esd.service.JobService;
import esd.service.KitService;
import esd.service.ResumeService;
import esd.service.UserService;

/**
 * 企业信息 后台管理控制器
 * 
 * @author yufu
 * @email ilxly01@126.com 2014-11-5
 */
@Controller
@RequestMapping("/manage/company")
public class CompanyManageController {
	private static Logger log = Logger.getLogger(CompanyManageController.class);

	@Value("${templateFile}")
	private String templateFile;

	@Value("${destFileName}")
	private String destFileName;

	@Autowired
	private UserService userService;

	@Autowired
	private AreaService areaService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private JobService jobService;

	@Autowired
	private ResumeService resumeService;

	// 转到企业信息管理列表页面
	@RequestMapping(value = "/company_list", method = RequestMethod.GET)
	public ModelAndView list_get(HttpServletRequest request, HttpSession session) {
		log.debug("goto：企业信息 后台管理 列表");
		Map<String, Object> entity = new HashMap<>();
		String pageStr = request.getParameter("page");
		Integer page = KitService.getInt(pageStr) > 0 ? KitService
				.getInt(pageStr) : 1;
		Integer rows = Constants.SIZE;
		Company paramEntity = new Company();
		// 名称模糊查询
		String targetName = request.getParameter("targetName");
		// 获取设和类型查询条件, 如果传递的参数为空的话, 则默认首先显示 待审核的数据
		String checkStatus = request.getParameter("checkStatus");
		if (checkStatus == null || "".equals(checkStatus)) {
			checkStatus = Constants.CheckStatus.DAISHEN.getValue();
		}
		paramEntity.setName(targetName);
		paramEntity.setCheckStatus(checkStatus);

		// 获取地区码
		User userObj = (User) session.getAttribute(Constants.USER);
		// 根据管理员用户所属地区, 查询他下面所属的所有数据
		String acode = userObj.getArea().getCode();
		paramEntity.setArea(new Area(acode));

		List<Company> resultList = companyService.getListShowForManage(
				paramEntity, page, rows);
		Integer total = companyService.getTotalCount(paramEntity); // 数据总条数
		try {
			List<Map<String, Object>> list = new ArrayList<>();
			System.out.println("resultList.size()" + resultList.size());
			for (Company tmp : resultList) {
				Map<String, Object> tempMap = new HashMap<>();
				tempMap.put("id", tmp.getId());// id
				tempMap.put("name", tmp.getName());// 公司名称
				tempMap.put("contactPerson", tmp.getContactPerson());// 联系人
				tempMap.put("telephone", tmp.getTelephone());	//联系电话
				tempMap.put("nature", tmp.getNature());// 企业性质
				tempMap.put("economyType", tmp.getEconomyType());// 经济类型
				tempMap.put("area", tmp.getArea());// 所属地区
				list.add(tempMap);
			}
			entity.put("total", total);
			entity.put("entityList", list);
			log.debug("获取 企业信息，size():" + total);
		} catch (Exception e) {
			log.error("获取企业信息 时发生错误。");
			e.printStackTrace();
		}
		// 放入当前页数, 总页数, 职位名, 审核状态
		entity.put("currentPage", page);
		entity.put("totalPage", KitService.getTotalPage(total));
		entity.put("targetName", targetName);
		entity.put("checkStatus", checkStatus);
		entity.put("checkStatusName",
				KitService.getCheckStatusName(checkStatus));
		return new ModelAndView("manage/company-list", entity);
	}

}
