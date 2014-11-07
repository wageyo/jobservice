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
import esd.bean.Job;
import esd.bean.User;
import esd.controller.Constants;
import esd.service.AreaService;
import esd.service.CompanyService;
import esd.service.JobService;
import esd.service.KitService;
import esd.service.ResumeService;
import esd.service.UserService;

/**
 * 招聘信息/职位 后台管理控制器
 * 
 * @author yufu
 * @email ilxly01@126.com 2014-11-5
 */
@Controller
@RequestMapping("/manage/job")
public class JobManageController {
	private static Logger log = Logger.getLogger(JobManageController.class);

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

	// 转到职位管理列表页面
	@RequestMapping(value = "/job_list", method = RequestMethod.GET)
	public ModelAndView list_get(HttpServletRequest request, HttpSession session) {
		log.debug("goto：招聘信息/职位 后台管理 列表");
		Map<String, Object> entity = new HashMap<>();
		String pageStr = request.getParameter("page");
		Integer page = KitService.getInt(pageStr) > 0 ? KitService
				.getInt(pageStr) : 1;
		Integer rows = Constants.SIZE;
		Job paramEntity = new Job();
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

		List<Job> resultList = jobService.getListShowForManage(paramEntity,
				page, rows);
		Integer total = jobService.getTotalCount(paramEntity); // 数据总条数
		try {
			List<Map<String, Object>> list = new ArrayList<>();
			System.out.println("resultList.size()" + resultList.size());
			for (Job tmp : resultList) {
				Map<String, Object> tempMap = new HashMap<>();
				tempMap.put("id", tmp.getId());// id
				tempMap.put("name", tmp.getName());// name
				tempMap.put("hireNumber", tmp.getHireNumber());// 人数
				tempMap.put("salary", tmp.getSalary());// 薪水
				tempMap.put("nature", tmp.getNature());// 职位性质
				tempMap.put("createDate", tmp.getCreateDate());// 创建日期
				tempMap.put("effectiveTime", tmp.getEffectiveTime());// 有效期
				tempMap.put("contactPerson", tmp.getContactPerson());	//联系人
				tempMap.put("contactTel", tmp.getContactTel());	//联系电话
				list.add(tempMap);
			}
			entity.put("total", total);
			entity.put("entityList", list);
			log.debug("获取 招聘信息/职位 信息，size():" + total);
		} catch (Exception e) {
			log.error("获取招聘信息/职位 时发生错误。");
			e.printStackTrace();
		}
		// 放入当前页数, 总页数, 职位名, 审核状态
		entity.put("currentPage", page);
		entity.put("totalPage", KitService.getTotalPage(total));
		entity.put("targetName", targetName);
		entity.put("checkStatus", checkStatus);
		entity.put("checkStatusName",
				KitService.getCheckStatusName(checkStatus));
		return new ModelAndView("manage/job-list", entity);
	}

}
