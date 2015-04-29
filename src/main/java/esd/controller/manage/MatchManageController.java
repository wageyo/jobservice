package esd.controller.manage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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
import esd.service.SMSService;
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
	
	@Autowired
	private ParameterService parameterService;
	
	@Autowired
	private SMSService smsService; 

	// 转到 根据简历匹配职位 显示简历页面
	@RequestMapping(value = "/mre", method = RequestMethod.GET)
	public ModelAndView resume_match_list(HttpServletRequest request) {
		log.debug("goto：转到简历 自动匹配   列表页面");
		Map<String, Object> entity = new HashMap<>();
		String pageStr = request.getParameter("page");
		Integer page = KitService.getInt(pageStr) > 0 ? KitService
				.getInt(pageStr) : 1;
		Integer rows = Constants.SIZE;
		List<Parameter> plist =  pService.getByType(Constants.RESUME_MATCH_PER);
		entity.put("params", plist);
		
		Resume paramEntity = new Resume();
		//只查找默认投递简历, 多余的不查
		paramEntity.setIsDefault(Boolean.TRUE);
		// 获取地区码
		String userId = CookieHelper.getCookieValue(request,
				Constants.ADMINUSERID);
		Integer uid = Integer.parseInt(userId);
		User userObj = userService.getById(uid);
		// 根据管理员用户所属地区, 查询他下面所属的所有数据
		String acode = userObj.getArea().getCode();
		paramEntity.setArea(new Area(acode));

		List<Resume> resultList = resumeService.getForListShow(paramEntity,
				page, rows,Boolean.FALSE);
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
		return new ModelAndView("manage/mre-list", entity);
	}

	// 转到 根据简历匹配职位 结果页面-json
	@RequestMapping(value = "/resume_match_json", method = RequestMethod.GET)
	public ModelAndView resume_match_json(HttpServletRequest request) {
		// 获取地区码
		String userId = CookieHelper.getCookieValue(request,
				Constants.ADMINUSERID);
		Integer uid = Integer.parseInt(userId);
		User userObj = userService.getById(uid);
		// 根据管理员用户所属地区, 查询他下面所属的所有数据
		String acode = userObj.getArea().getCode();
		Map<String, Object> entity = new HashMap<>();
		//简历id,并根据简历id得到对应的简历对象
		String id = request.getParameter("id");
		Resume tmp = resumeService.getById(Integer.parseInt(id));
		//根据request中的参数和cookie中的地区信息, 组装起Job查询对象
		Job paramEntity = getParamEntity(request,tmp);
		//查询本地区设置的推送显示数量, 如果不存在则使用系统默认的推送数量-5
		Integer tuisongNumber = Constants.MATCHED_NUMBER_DEFAULT;
		Parameter pt = parameterService.getOnebyTypeAndAcode(Constants.MATCHED_SHOW_NUMBER, acode);
		if(pt!=null){
			tuisongNumber = Integer.parseInt(pt.getValue());
		}
		List<Job> mateList = jobService.getTuiSongList(paramEntity, Constants.START, tuisongNumber);
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
		List<Parameter> plist =  pService.getByType(Constants.RESUME_MATCH_PER);
		entity.put("params", plist);

		Company paramEntity = new Company();

		// 获取地区码
		String userId = CookieHelper.getCookieValue(request,
				Constants.ADMINUSERID);
		Integer uid = Integer.parseInt(userId);
		User userObj = userService.getById(uid);
		// 根据管理员用户所属地区, 查询他下面所属的所有数据
		String acode = userObj.getArea().getCode();
		paramEntity.setArea(new Area(acode));

		List<Company> resultList = companyService.getListShowForManage(
				paramEntity, page, rows,Boolean.FALSE);
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
		return new ModelAndView("manage/mjb-list", entity);
	}

	// 转到 根据职位匹配简历 结果页面-json
	@RequestMapping(value = "/job_match_json", method = RequestMethod.GET)
	public ModelAndView job_match_json(HttpServletRequest request) {
		// 获取地区码
		String userId = CookieHelper.getCookieValue(request,
				Constants.ADMINUSERID);
		Integer uid = Integer.parseInt(userId);
		User userObj = userService.getById(uid);
		// 根据管理员用户所属地区, 查询他下面所属的所有数据
		String acode = userObj.getArea().getCode();
		Map<String, Object> entity = new HashMap<>();
		String id = request.getParameter("id");
		entity.put("companyid", id);
		// 根据id查出公司所发布的职位
		int idInt = KitService.getInt(id);
		List<Job> jobList = jobService.getByCompanyMate(idInt, 1,Constants.SIZE);
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
					paramEntity = getParamEntity(request, tmp);
					//查询本地区设置的推送显示数量, 如果不存在则使用系统默认的推送数量-5
					Integer tuisongNumber = Constants.MATCHED_NUMBER_DEFAULT;
					Parameter pt = parameterService.getOnebyTypeAndAcode(Constants.MATCHED_SHOW_NUMBER, acode);
					if(pt!=null){
						tuisongNumber = Integer.parseInt(pt.getValue());
					}
					resumeResultList = resumeService.getForListShow(
							paramEntity, 1, tuisongNumber,Boolean.FALSE);
				tempMap.put("resumeResultList", resumeResultList);
				log.info("matchedNumber " + resumeResultList.size());
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

	//向个人发送推送的职位***参数分别为：matchRate-匹配度       mark-推送范围标示符,all为全部， ids-传递的id数组
	@RequestMapping(value="/sendJob", method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> sendJob(HttpServletRequest request){
		Map<String,Object> map = new HashMap<String,Object>();
		//①整理前台传进来的参数, 并判断是全部推送还是选中推送
		Integer[] ids = null;
		Resume tmp = new Resume();
		// 获取地区码
		String userId = CookieHelper.getCookieValue(request,
				Constants.ADMINUSERID);
		Integer uid = Integer.parseInt(userId);
		User userObj = userService.getById(uid);
		// 根据管理员用户所属地区, 查询他下面所属的所有数据
		String acode = userObj.getArea().getCode();
		tmp.setArea(new Area(acode));
		tmp.setIsDefault(Boolean.TRUE);	//是默认选中的, 防止重复发送 
		//所有需要推送就业信息的简历
		List<Resume> resumeList= null;
		//②获取要推送的简历列表
		String type = request.getParameter("type");	//类型-- all--全部; half--选中的
		//如果为全部的话
		//获取选中的id数组, 如果为all的话, 则代表全部推送.
		if("all".equals(type)){
			resumeList = resumeService.getListShowForManage(tmp, Constants.START, Integer.MAX_VALUE,Boolean.FALSE);
		}else{
			String[] idsStr = request.getParameterValues("ids");
			ids = new Integer[idsStr.length]; 
			for(int i=0;i<idsStr.length;i++){
				ids[i] = Integer.parseInt(idsStr[i]);
			}
			resumeList = resumeService.getByIds(ids);
		}
		//查询本地区设置的推送显示数量, 如果不存在则使用系统默认的推送数量-5
		Integer tuisongNumber = Constants.MATCHED_NUMBER_DEFAULT;
		Parameter pt = parameterService.getOnebyTypeAndAcode(Constants.MATCHED_SHOW_NUMBER, acode);
		if(pt!=null){
			tuisongNumber = Integer.parseInt(pt.getValue());
		}
		//③向每个 推送职位个数不为零的简历发送推送信息
		int right = 0,wrong=0;
		//非法字符集文件地址
		String url = request.getSession().getServletContext().getRealPath("/");
		for(int i=0;i<resumeList.size();i++){
			Resume resume = resumeList.get(i);
			List<Job> sentData = jobService.getTuiSongList(getParamEntity(request,resume), Constants.START, tuisongNumber);
			if(sentData != null){
				if(sentData.size() <= 0){
					continue;
				}
				Boolean bl = smsService.sendTuiSongJob(resume, sentData,url,userObj.getNickName());
				if(bl){
					right ++;
				}else{
					wrong ++;
				}
			}
		}
		map.put("right", right);
		map.put("wrong", wrong);
		log.info("共发送： "+(right + wrong)+"  条推送信息, 其中发送成功: "+right + " 条,  失败: " + wrong+" 条.");
		return map;
	}

	/**
	 * 以简历为参数查询匹配的职位, 按request中的匹配条件来组装查询的job参数对象
	 * @param matchRate--匹配度
	 * @param tmp--从该参数获取对应查询属性值.
	 * @return
	 */
	private Job getParamEntity(HttpServletRequest request,Resume tmp){
		/********************* 获取地区码************************/
		String userId = CookieHelper.getCookieValue(request,
				Constants.ADMINUSERID);
		Integer uid = Integer.parseInt(userId);
		User userObj = userService.getById(uid);
		// 根据管理员用户所属地区, 查询他下面所属的所有数据
		Job paramEntity = new Job();
		paramEntity.setArea(userObj.getArea());
		paramEntity.setCheckStatus(Constants.CheckStatus.OK.getValue());//只选择审核通过的
		String workPlace = request.getParameter("workPlace");
		if(workPlace!=null && !"".equals(workPlace)){
			paramEntity.setWorkPlace(tmp.getDesireAddress());
		}
		String jobCategory = request.getParameter("jobCategory");
		if(jobCategory!=null && !"".equals(jobCategory)){
			paramEntity.setJobCategory(tmp.getDesireJob());
		}
		String nature = request.getParameter("nature");
		if(nature!=null && !"".equals(nature)){
			paramEntity.setNature(tmp.getJobNature());
		}
		String age = request.getParameter("age");
		if(age!=null && !"".equals(age)){
			paramEntity.setAge(KitService.getAgeByBirth(tmp.getBirth())+"");
		}
		String gender = request.getParameter("gender");
		if(gender!=null && !"".equals(gender)){
			paramEntity.setGender(tmp.getGender());
		}
		String education = request.getParameter("education");
		if(education!=null && !"".equals(education)){
			paramEntity.setEducation(tmp.getEducation());
		}
		String salay = request.getParameter("salay");
		if(salay!=null && !"".equals(salay)){
			paramEntity.setSalary(tmp.getDesireSalary());
		}
		String experience = request.getParameter("experience");
		if(experience!=null && !"".equals(experience)){
			paramEntity.setExperience(tmp.getExperience());
		}
		String disabilityPart = request.getParameter("disabilityPart");
		if(disabilityPart!=null && !"".equals(disabilityPart)){
			paramEntity.setDisabilityPart(tmp.getDisabilityPart());
		}
		String disabilityLevel = request.getParameter("disabilityLevel");
		if(disabilityLevel!=null && !"".equals(disabilityLevel)){
			paramEntity.setDisabilityLevel(tmp.getDisabilityLevel());
		}
		String disabilityCategory = request.getParameter("disabilityCategory");
		if(disabilityCategory!=null && !"".equals(disabilityCategory)){
			paramEntity.setDisabilityCategory(tmp.getDisabilityCategory());
		}
		String name = request.getParameter("name");
		if(name!=null && !"".equals(name)){
			paramEntity.setName(tmp.getTitle());
		}
		return paramEntity;
	}

	/**
	 * 以职位为参数查询匹配的简历, 按匹配度来返回组装参数完毕的职位
	 * @param paramEntity--返回的查询实体
	 * @param matchRate--匹配度
	 * @param tmp--从该参数获取对应查询属性值.
	 * @return
	 */
	private Resume getParamEntity(HttpServletRequest request, Job tmp){
		/********************* 获取地区码************************/
		String userId = CookieHelper.getCookieValue(request,
				Constants.ADMINUSERID);
		Integer uid = Integer.parseInt(userId);
		User userObj = userService.getById(uid);
		// 根据管理员用户所属地区, 查询他下面所属的所有数据
		Resume paramEntity = new Resume();
		paramEntity.setArea(userObj.getArea());
		paramEntity.setCheckStatus(Constants.CheckStatus.OK.getValue());//只选择审核通过的
		String workPlace = request.getParameter("workPlace");
		if(workPlace!=null && !"".equals(workPlace)){
			paramEntity.setDesireAddress(tmp.getWorkPlace());
		}
		String jobCategory = request.getParameter("jobCategory");
		if(jobCategory!=null && !"".equals(jobCategory)){
			paramEntity.setDesireJob(tmp.getJobCategory());
		}
		String nature = request.getParameter("nature");
		if(nature!=null && !"".equals(nature)){
			paramEntity.setJobNature(tmp.getNature());
		}
		String age = request.getParameter("age");
		if(age!=null && !"".equals(age)){
			paramEntity.setBirth(KitService.getBirthByAge(Integer.parseInt(tmp.getAge())));
		}
		String gender = request.getParameter("gender");
		if(gender!=null && !"".equals(gender)){
			paramEntity.setGender(tmp.getGender());
		}
		String education = request.getParameter("education");
		if(education!=null && !"".equals(education)){
			paramEntity.setEducation(tmp.getEducation());
		}
		String salay = request.getParameter("salay");
		if(salay!=null && !"".equals(salay)){
			paramEntity.setDesireSalary(tmp.getSalary());
		}
		String experience = request.getParameter("experience");
		if(experience!=null && !"".equals(experience)){
			paramEntity.setExperience(tmp.getExperience());
		}
		String name = request.getParameter("name");
		if(name!=null && !"".equals(name)){
			paramEntity.setTitle(tmp.getName());
		}
		return paramEntity;
	}
}
