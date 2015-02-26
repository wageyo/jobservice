package esd.controller.manage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import esd.bean.Area;
import esd.bean.Company;
import esd.bean.Job;
import esd.bean.Parameter;
import esd.bean.Resume;
import esd.bean.User;
import esd.controller.Constants;
import esd.service.CompanyService;
import esd.service.CookieHelper;
import esd.service.JobService;
import esd.service.KitService;
import esd.service.ParameterService;
import esd.service.ResumeService;
import esd.service.UserService;

/**
 * 简历/职位自动匹配 控制器
 * 
 * @author yufu
 * @email ilxly01@126.com 2015-1-16
 */
@Controller
@RequestMapping("/manage")
public class MatchManageController {
	private static Logger log = Logger.getLogger(MatchManageController.class);

	@Autowired
	private UserService<User> userService;

	@Autowired
	private ResumeService resumeService;

	@Autowired
	private CompanyService<Company> companyService;

	@Autowired
	private ParameterService pService;

	@Autowired
	private JobService jobService;

	// 转到 根据简历匹配职位 显示简历页面
	@RequestMapping(value = "/mre", method = RequestMethod.GET)
	public ModelAndView resume_match_list(HttpServletRequest request) {
		log.debug("goto：转到简历 自动匹配 合适的职位 列表页面");
		Map<String, Object> entity = new HashMap<>();
		String pageStr = request.getParameter("page");
		Integer page = KitService.getInt(pageStr) > 0 ? KitService
				.getInt(pageStr) : 1;
		Integer rows = Constants.SIZE;
		List<Parameter> plist =  pService.getByType("resumeMatchPer");
		entity.put("params", plist);
		Resume paramEntity = new Resume();
		
		//得到前台传递的匹配率
		String matchRateStr = request.getParameter("matchRate");
		if(matchRateStr == null || "".equals(matchRateStr)){
			matchRateStr = "0";
		}
		Integer matchRate = Integer.parseInt(matchRateStr);
		
		// 获取地区码
		String userId = CookieHelper.getCookieValue(request,
				Constants.ADMINUSERID);
		Integer uid = Integer.parseInt(userId);
		User userObj = userService.getById(uid);
		// 根据管理员用户所属地区, 查询他下面所属的所有数据
		String acode = userObj.getArea().getCode();
		paramEntity.setArea(new Area(acode));

		List<Resume> resultList = resumeService.getForListShow(paramEntity,
				page, rows);
		Integer total = resumeService.getTotalCount(paramEntity, Boolean.FALSE); // 数据总条数
			entity.put("total", total);
			log.debug("获取 简历 信息，size():" + total);
		List<Map<String, Object>> list = new ArrayList<>();
		for (Resume tmp : resultList) {
				Map<String, Object> tempMap = new HashMap<>();
				tempMap.put("id", tmp.getId());// id
				tempMap.put("title", tmp.getTitle());	//简历标题
				tempMap.put("name", tmp.getName());// 姓名
				tempMap.put("gender", tmp.getGender());//性别	
				tempMap.put("disabilityCard", tmp.getDisabilityCard());	// 残疾证号
				tempMap.put("disabilityCategory", tmp.getDisabilityCategory());// 残疾类别
				tempMap.put("disabilityLevel", tmp.getDisabilityLevel()); // 残疾等级
				tempMap.put("desireSalary", tmp.getDesireSalary());// 期望薪水
				tempMap.put("phone", tmp.getPhone());// 联系电话
				tempMap.put("area", tmp.getArea());// 所属地区
				list.add(tempMap);
			}
			entity.put("total", total);
			entity.put("entityList", list);
		// 放入当前页数, 总页数, 简历名, 审核状态
		entity.put("currentPage", page);
		entity.put("totalPage", KitService.getTotalPage(total));
		entity.put("matchRate", matchRate);
		return new ModelAndView("manage/mre-list", entity);
	}

	// 转到 根据简历匹配职位 结果页面-json
	@RequestMapping(value = "/resume_match_json", method = RequestMethod.GET)
	public ModelAndView resume_match_json(HttpServletRequest request) {
		Map<String, Object> entity = new HashMap<>();
		//简历id,并根据简历id得到对应的简历对象
		String id = request.getParameter("id");
		Resume tmp = resumeService.getById(Integer.parseInt(id));
		// 匹配百分比
		String matchRateStr = request.getParameter("matchRate");
		Integer matchRate = Integer.parseInt(matchRateStr);
		// 获取地区码
		String userId = CookieHelper.getCookieValue(request,
				Constants.ADMINUSERID);
		Integer uid = Integer.parseInt(userId);
		User userObj = userService.getById(uid);
		// 根据管理员用户所属地区, 查询他下面所属的所有数据
		String acode = userObj.getArea().getCode();
		// 以下为根据百分比得出查询条件计算出来的匹配的结果集
		Job paramEntity = new Job();
		paramEntity.setArea(new Area(acode));
		if(matchRate > 0){
			paramEntity.setWorkPlace(tmp.getDesireAddress());
		}
		if(matchRate > 10){
			paramEntity.setJobCategory(tmp.getDesireJob());
		}
		if(matchRate > 20){
			paramEntity.setNature(tmp.getJobNature());
		}
		if(matchRate > 30){
			paramEntity.setAge(String.valueOf(tmp.getAge()));
		}
		if(matchRate > 40){
			paramEntity.setGender(tmp.getGender());
		}
		if(matchRate > 50){
			paramEntity.setEducation(tmp.getEducation());
		}
		if(matchRate > 60){
			paramEntity.setSalary(tmp.getDesireSalary());
		}
		if(matchRate > 70){
			paramEntity.setExperience(tmp.getExperience());
		}
		if(matchRate > 20){
			paramEntity.setName(tmp.getTitle());
		}
		List<Job> mateList = jobService.getListForShow(paramEntity, Constants.START, Constants.SIZE);
		entity.put("entityList", mateList);
		if(mateList !=null){
			entity.put("matchedNumber", mateList.size());
		}
		return new ModelAndView("manage/resume-match-json", entity);
	}

	// 转到 根据职位匹配简历 显示公司列表页面
	@RequestMapping(value = "/mjb", method = RequestMethod.GET)
	public ModelAndView job_match_list(HttpServletRequest request) {
		Map<String, Object> entity = new HashMap<>();
		String pageStr = request.getParameter("page");
		Integer page = KitService.getInt(pageStr) > 0 ? KitService
				.getInt(pageStr) : 1;
		Integer rows = Constants.SIZE;
		List<Parameter> plist = pService.getByType("resumeMatchPer");
		entity.put("params", plist);

		Company paramEntity = new Company();
		//得到前台传递的匹配率
		String matchRateStr = request.getParameter("matchRate");
		if(matchRateStr == null || "".equals(matchRateStr)){
			matchRateStr = "0";
		}
		Integer matchRate = Integer.parseInt(matchRateStr);

		// 获取地区码
		String userId = CookieHelper.getCookieValue(request,
				Constants.ADMINUSERID);
		Integer uid = Integer.parseInt(userId);
		User userObj = userService.getById(uid);
		// 根据管理员用户所属地区, 查询他下面所属的所有数据
		String acode = userObj.getArea().getCode();
		paramEntity.setArea(new Area(acode));

		List<Company> resultList = companyService.getListShowForManage(
				paramEntity, page, rows);
		Integer total = companyService.getTotalCount(paramEntity, Boolean.TRUE); // 数据总条数
		try {
			List<Map<String, Object>> list = new ArrayList<>();
			for (Company tmp : resultList) {
				Map<String, Object> tempMap = new HashMap<>();
				tempMap.put("id", tmp.getId());// id
				tempMap.put("name", tmp.getName());// 公司名称
				tempMap.put("email", tmp.getEmail());
				tempMap.put("contactDept", tmp.getContactDept());
				tempMap.put("contactPerson", tmp.getContactPerson());// 联系人
				tempMap.put("telephone", tmp.getTelephone()); // 联系电话
				tempMap.put("nature", tmp.getNature());// 企业性质
				tempMap.put("economyType", tmp.getEconomyType());// 经济类型
				tempMap.put("area", tmp.getArea());// 所属地区
				// 根据id查出公司所发布的职位
				Integer jobCount = jobService.getByCompanyCount(tmp.getId());
				tempMap.put("jobCount", jobCount);
				list.add(tempMap);
			}
			entity.put("total", total);
			entity.put("entityList", list);
			log.debug("获取 企业信息，size():" + total);
		} catch (Exception e) {
			log.error("获取企业信息 时发生错误。");
			e.printStackTrace();
		}
		// 放入当前页数, 总页数, 企业名, 审核状态
		entity.put("currentPage", page);
		entity.put("totalPage", KitService.getTotalPage(total));
		entity.put("matchRate", matchRate);
		return new ModelAndView("manage/mjb-list", entity);
	}

	// 转到 根据职位匹配简历 结果页面-json
	@RequestMapping(value = "/job_match_json", method = RequestMethod.GET)
	public ModelAndView job_match_json(HttpServletRequest request) {
		Map<String, Object> entity = new HashMap<>();
		String id = request.getParameter("id");
		entity.put("companyid", id);
		// 匹配百分比
		String matchRateStr = request.getParameter("matchRate");
		Integer matchRate = Integer.parseInt(matchRateStr);
		// 获取地区码
		String userId = CookieHelper.getCookieValue(request,
				Constants.ADMINUSERID);
		Integer uid = Integer.parseInt(userId);
		User userObj = userService.getById(uid);
		// 根据管理员用户所属地区, 查询他下面所属的所有数据
		String acode = userObj.getArea().getCode();
		// 根据id查出公司所发布的职位
		int idInt = KitService.getInt(id);
		List<Job> jobList = jobService.getByCompanyMate(idInt, 1,
				jobService.getByCompanyCount(idInt));
		try {
			List<Map<String, Object>> list = new ArrayList<>();
			for (Job tmp : jobList) {
				Map<String, Object> tempMap = new HashMap<>();
				tempMap.put("id", tmp.getId());// id
				tempMap.put("name", tmp.getName());// name
				tempMap.put("hireNumber", tmp.getHireNumber());// 人数
				tempMap.put("salary", tmp.getSalary());// 薪水
				tempMap.put("nature", tmp.getNature());// 职位性质
				tempMap.put("createDate", tmp.getCreateDate());// 创建日期
				tempMap.put("effectiveTime", tmp.getEffectiveTime());// 有效期
				tempMap.put("contactPerson", tmp.getContactPerson()); // 联系人
				tempMap.put("contactTel", tmp.getContactTel()); // 联系电话
				List<Resume> resumeResultList = null;
				// 判断查询条件是否为空，组成查询条件
					Resume paramEntity = new Resume();
					paramEntity.setArea(new Area(acode));
					if(matchRate > 0){
						paramEntity.setDesireAddress(tmp.getWorkPlace());
					}
					if(matchRate > 10){
						paramEntity.setDesireJob(tmp.getJobCategory());
					}
					if(matchRate > 20){
						String JobNature = KitService.getMateNature(tmp
								.getNature());
						paramEntity.setJobNature(JobNature);
					}
					if(matchRate > 30){
						paramEntity.setAge(Integer.parseInt(tmp.getAge()));
					}
					if(matchRate > 40){
						paramEntity.setGender(tmp.getGender());
					}
					if(matchRate > 50){
						paramEntity.setEducation(tmp.getEducation());
					}
					if(matchRate > 60){
						paramEntity.setDesireSalary(tmp.getSalary());
					}
					if(matchRate > 70){
						paramEntity.setExperience(tmp.getExperience());
					}
					if(matchRate > 20){
						paramEntity.setTitle(tmp.getName());
					}
					resumeResultList = resumeService.getForListShow(
							paramEntity, 1, Integer.MAX_VALUE);
				tempMap.put("resumeResultList", resumeResultList);
				System.out.println("matchedNumber " + resumeResultList.size());
				if(resumeResultList !=null){
					tempMap.put("matchedNumber", resumeResultList.size());
				}
				
				list.add(tempMap);
			}
			entity.put("entityList", list);
		} catch (Exception e) {
			log.error("获取企业信息 时发生错误。");
			e.printStackTrace();
		}
		return new ModelAndView("manage/job-match-json", entity);
	}

}
