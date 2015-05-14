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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import esd.bean.Area;
import esd.bean.Company;
import esd.bean.Job;
import esd.bean.Parameter;
import esd.bean.Resume;
import esd.bean.SmsAccount;
import esd.bean.User;
import esd.controller.Constants;
import esd.service.CompanyService;
import esd.service.CookieHelper;
import esd.service.JobService;
import esd.service.KitService;
import esd.service.ParameterService;
import esd.service.ResumeService;
import esd.service.SMSService;
import esd.service.SmsAccountService;
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
	private UserService userService;

	@Autowired
	private ResumeService resumeService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private ParameterService pService;

	@Autowired
	private JobService jobService;

	@Autowired
	private ParameterService parameterService;

	@Autowired
	private SMSService smsService;

	@Autowired
	private SmsAccountService smsAccountService;

	// 转到 根据简历匹配职位 显示简历页面
	@RequestMapping(value = "/mre", method = RequestMethod.GET)
	public ModelAndView resume_match_list(HttpServletRequest request) {
		log.debug("goto：转到简历 自动匹配   列表页面");
		Map<String, Object> entity = new HashMap<>();
		String pageStr = request.getParameter("page");
		Integer page = KitService.getInt(pageStr) > 0 ? KitService
				.getInt(pageStr) : 1;
		Integer rows = Constants.SIZE;
		List<Parameter> plist = pService.getByType(Constants.RESUME_MATCH_PER);
		entity.put("params", plist);
		Resume paramEntity = new Resume();
		// 只查找默认投递的, 通过审核的 多余的不查
		paramEntity.setIsDefault(Boolean.TRUE);
		paramEntity.setCheckStatus(Constants.CheckStatus.OK.getValue());
		// 获取地区码
		String userId = CookieHelper.getCookieValue(request,
				Constants.ADMINUSERID);
		Integer uid = Integer.parseInt(userId);
		User userObj = userService.getById(uid);
		// 根据管理员用户所属地区, 查询他下面所属的所有数据
		String acode = userObj.getArea().getCode();
		paramEntity.setArea(new Area(acode));

		List<Resume> resultList = resumeService.getForListShow(paramEntity,
				page, rows, Boolean.FALSE);
		Integer total = resumeService.getTotalCount(paramEntity, Boolean.FALSE); // 数据总条数
		entity.put("total", total);
		log.debug("获取 简历 信息，size():" + total);
		List<Map<String, Object>> list = new ArrayList<>();
		for (Resume tmp : resultList) {
			Map<String, Object> tempMap = new HashMap<>();
			tempMap.put("id", tmp.getId());// id
			tempMap.put("title", tmp.getTitle()); // 简历标题
			tempMap.put("name", tmp.getName());// 姓名
			tempMap.put("gender", tmp.getGender());// 性别
			tempMap.put("disabilityCard", tmp.getDisabilityCard()); // 残疾证号
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
		// 简历id,并根据简历id得到对应的简历对象
		String id = request.getParameter("id");
		Resume tmp = resumeService.getById(Integer.parseInt(id));
		// 根据request中的参数和cookie中的地区信息, 组装起Job查询对象
		Job paramEntity = getParamEntity(request, tmp);
		// 查询本地区设置的推送显示数量, 如果不存在则使用系统默认的推送数量-5
		Integer tuisongNumber = Constants.MATCHED_NUMBER_DEFAULT;
		Parameter pt = parameterService.getOnebyTypeAndAcode(
				Constants.MATCHED_SHOW_NUMBER, acode);
		if (pt != null) {
			tuisongNumber = Integer.parseInt(pt.getValue());
		}
		List<Job> mateList = jobService.getTuiSongList(paramEntity,
				Constants.START, tuisongNumber);
		entity.put("entityList", mateList);
		if (mateList != null) {
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
		List<Parameter> plist = pService.getByType(Constants.RESUME_MATCH_PER);
		entity.put("params", plist);

		Company paramEntity = new Company();
		// 只查找 通过审核的 多余的不查
		paramEntity.setCheckStatus(Constants.CheckStatus.OK.getValue());
		// 获取地区码
		String userId = CookieHelper.getCookieValue(request,
				Constants.ADMINUSERID);
		Integer uid = Integer.parseInt(userId);
		User userObj = userService.getById(uid);
		// 根据管理员用户所属地区, 查询他下面所属的所有数据
		String acode = userObj.getArea().getCode();
		paramEntity.setArea(new Area(acode));

		List<Company> resultList = companyService.getListShowForManage(
				paramEntity, page, rows, Boolean.FALSE);
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
		List<Job> jobList = jobService.getByCompanyMate(idInt, 1,
				Constants.SIZE);
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
				// 查询本地区设置的推送显示数量, 如果不存在则使用系统默认的推送数量-5
				Integer tuisongNumber = Constants.MATCHED_NUMBER_DEFAULT;
				Parameter pt = parameterService.getOnebyTypeAndAcode(
						Constants.MATCHED_SHOW_NUMBER, acode);
				if (pt != null) {
					tuisongNumber = Integer.parseInt(pt.getValue());
				}
				resumeResultList = resumeService.getForListShow(paramEntity, 1,
						tuisongNumber, Boolean.FALSE);
				tempMap.put("resumeResultList", resumeResultList);
				log.info("matchedNumber " + resumeResultList.size());
				if (resumeResultList != null) {
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

	// 向个人发送推送的职位***参数分别为：matchRate-匹配度 mark-推送范围标示符,all为全部， ids-传递的id数组
	@RequestMapping(value = "/sendJob", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> sendJob(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		// ①整理前台传进来的参数, 并判断是全部推送还是选中推送
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
		// 是默认选中的, 切审核通过的! 防止重复发送
		tmp.setIsDefault(Boolean.TRUE);
		tmp.setCheckStatus(Constants.CheckStatus.OK.getValue());
		// 检查本地区是否有名商通的账号, 没有的话则对前台进行提示
		SmsAccount smsAccount = smsAccountService.getByArea(acode);
		if (smsAccount == null) {
			map.put(Constants.NOTICE,
					"您还没有名商通用户账号, 请在名商通官网(http://www.139000.net/)申请账号并充值后, 将账号密码在设置页面添加后使用短信功能.");
			return map;
		}

		// 所有需要推送就业信息的简历
		List<Resume> resumeList = null;
		// ②获取要推送的简历列表
		String type = request.getParameter("type"); // 类型-- all--全部; half--选中的
		// 如果为全部的话
		// 获取选中的id数组, 如果为all的话, 则代表全部推送.
		if ("all".equals(type)) {
			resumeList = resumeService.getListShowForManage(tmp,
					Constants.START, Integer.MAX_VALUE, Boolean.FALSE);
		} else {
			String[] idsStr = request.getParameterValues("ids");
			ids = new Integer[idsStr.length];
			for (int i = 0; i < idsStr.length; i++) {
				ids[i] = Integer.parseInt(idsStr[i]);
			}
			resumeList = resumeService.getByIds(ids);
		}
		// 查询本地区设置的推送数量, 如果不存在则使用系统默认的推送数量-5
		Integer tuisongNumber = Constants.MATCHED_NUMBER_DEFAULT;
		Parameter pt = parameterService.getOnebyTypeAndAcode(
				Constants.MATCHED_SEND_NUMBER, acode);
		if (pt != null) {
			tuisongNumber = Integer.parseInt(pt.getValue());
		}
		// ③向每个 推送职位个数不为零的简历发送推送信息
		int right = 0, wrong = 0;
		// 非法字符集文件地址
		String url = request.getSession().getServletContext().getRealPath("/");
		for (int i = 0; i < resumeList.size(); i++) {
			Resume resume = resumeList.get(i);
			List<Job> sentData = jobService.getTuiSongList(
					getParamEntity(request, resume), Constants.START,
					tuisongNumber);
			if (sentData != null) {
				if (sentData.size() <= 0) {
					continue;
				}
				Boolean bl = smsService.sendTuiSongJob(resume, sentData, url,
						userObj.getNickName(), acode, smsAccount);
				if (bl) {
					right++;
				} else {
					wrong++;
				}
			}
		}
		map.put(Constants.NOTICE, Constants.Notice.SUCCESS.getValue());
		map.put("right", right);
		map.put("wrong", wrong);
		log.info("共发送： " + (right + wrong) + "  条推送信息, 其中发送成功: " + right
				+ " 条,  失败: " + wrong + " 条.");
		return map;
	}

	// 向企业 发送推送的 能够匹配上职位招聘要求 的简历***参数分别为：matchRate-匹配度 mark-推送范围标示符,all为全部，
	// ids-传递的id数组
	@RequestMapping(value = "/sendResume", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> sendResume(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		// ①获取地区码
		String userId = CookieHelper.getCookieValue(request,
				Constants.ADMINUSERID);
		Integer uid = Integer.parseInt(userId);
		User userObj = userService.getById(uid);
		// 根据管理员用户所属地区, 查询他下面所属的所有数据
		String acode = userObj.getArea().getCode();
		// ②检查本地区是否有名商通的账号, 没有的话则对前台进行提示
		SmsAccount smsAccount = smsAccountService.getByArea(acode);
		if (smsAccount == null) {
			map.put(Constants.NOTICE,
					"您还没有名商通用户账号, 请在名商通官网(http://www.139000.net/)申请账号并充值后, 将账号密码在设置页面添加后使用短信功能.");
			return map;
		}

		// ③整理前台传进来的参数, 并判断是全部推送还是选中推送
		// 组装起company查询参数, 管理员地区, 激活的, 审核通过的单位~~
		Company tmp = new Company();
		tmp.setArea(new Area(acode));
		tmp.setIsActive(Boolean.TRUE);
		tmp.setCheckStatus(Constants.CheckStatus.OK.getValue());

		Integer[] ids = null;
		// 所有需要推送简历信息的企业
		List<Company> companyList = null;
		// ②获取要推送简历的公司
		String type = request.getParameter("type"); // 类型-- all--全部; half--选中的
		// 如果为全部的话
		// 获取选中的id数组, 如果为all的话, 则代表全部推送.
		if ("all".equals(type)) {
			companyList = companyService.getListShowForManage(tmp,
					Constants.START, Integer.MAX_VALUE, Boolean.FALSE);
		} else {
			String[] idsStr = request.getParameterValues("ids");
			ids = new Integer[idsStr.length];
			for (int i = 0; i < idsStr.length; i++) {
				ids[i] = Integer.parseInt(idsStr[i]);
			}
			companyList = companyService.getByIds(ids);
		}
		if (companyList == null || companyList.size() == 0) {
			map.put(Constants.NOTICE, "本地区没有可推送求职信息的企业.");
			return map;
		}
		int total = companyList.size(); // 总公司数
		// ③验证公司信息的电话号码是否为手机号, 如果号码不是手机号则不予以发送.
		int wrongphone = 0; // 号码不满足发送规则的
		for (int i = 0; i < companyList.size(); i++) {
			Company c = companyList.get(i);
			// 不符合规则剔除出去
			if (!KitService.checkPhone(c.getTelephone())) {
				wrongphone++;
				companyList.remove(i);
			}
		}
		// 如果没有剩下公司, 即所有公司的号码都没有通过验证, 则返回前台进行提示.
		if (companyList == null || companyList.size() == 0) {
			map.put(Constants.NOTICE, "没有可发送短信的手机号码, 请修正企业联系电话后再尝试.");
			return map;
		}
		// ④循环剩下的公司, 查找他们发布的职位
		// 查询本地区设置的推送显示数量, 如果不存在则使用系统默认的推送数量-5
		Integer tuisongNumber = Constants.MATCHED_NUMBER_DEFAULT; // 本地设置的推送数量
		Parameter pt = parameterService.getOnebyTypeAndAcode(
				Constants.MATCHED_SHOW_NUMBER, acode);
		if (pt != null) {
			tuisongNumber = Integer.parseInt(pt.getValue());
		}
		int right = 0, wrong = 0; // 发送成功数, 失败数
		for (Company company : companyList) {
			// 根据id查出公司所发布的职位
			List<Job> jobList = jobService.getByCompanyMate(company.getId(), 1,
					Constants.SIZE);
			// ⑤查询出所有可以匹配上该公司职位的简历
			// 定义向该公司推送的所有简历~~~
			List<Resume> sendResumeList = new ArrayList<Resume>();
			for (Job jobTmp : jobList) {
				// 判断查询条件是否为空，组成查询条件:本地, 激活的.
				Resume paramEntity = new Resume();
				paramEntity.setArea(new Area(acode));
				paramEntity.setIsActive(Boolean.TRUE);
				paramEntity = getParamEntity(request, jobTmp);
				List<Resume> resumeResultList = resumeService.getForListShow(
						paramEntity, 1, tuisongNumber, Boolean.FALSE);
				// ⑥处理简历结果, 剔除掉重复的, 并且总数量不大于定义的tuisongNumber
				// 如果有匹配上的简历, id不同的 则添加到sendResumeList集合中
				sendResumeList = addNotDuplicateResume(resumeResultList,
						sendResumeList, tuisongNumber);
			}
			// ⑦将简历发送到该公司的手机号码上
			// 非法字符集文件地址
			String url = request.getSession().getServletContext()
					.getRealPath("/");
			Boolean bl = smsService.sendTuiSongJob(company, sendResumeList,
					url, userObj.getNickName(), acode, smsAccount);
			if (bl) {
				right++;
			} else {
				wrong++;
			}
		}
		map.put(Constants.NOTICE, Constants.Notice.SUCCESS.getValue());
		map.put("total", total);
		map.put("wrongphone", wrongphone);
		map.put("right", right);
		map.put("wrong", wrong);
		log.info("总企业数: "+total+ ", 号码不正确导致不能发送的企业数: "+wrongphone +". 总计发送： " + (right + wrong) + "  条推送信息, 其中发送成功: " + right
				+ " 条,  失败: " + wrong + " 条.");
		return map;
	}

	/**
	 * 以简历为参数查询匹配的职位, 按request中的匹配条件来组装查询的job参数对象
	 * 
	 * @param matchRate
	 *            --匹配度
	 * @param tmp
	 *            --从该参数获取对应查询属性值.
	 * @return
	 */
	private Job getParamEntity(HttpServletRequest request, Resume tmp) {
		/********************* 获取地区码 ************************/
		String userId = CookieHelper.getCookieValue(request,
				Constants.ADMINUSERID);
		Integer uid = Integer.parseInt(userId);
		User userObj = userService.getById(uid);
		// 根据管理员用户所属地区, 查询他下面所属的所有数据
		Job paramEntity = new Job();
		paramEntity.setArea(userObj.getArea());
		paramEntity.setCheckStatus(Constants.CheckStatus.OK.getValue());// 只选择审核通过的
		String workPlace = request.getParameter("workPlace");
		if (workPlace != null && !"".equals(workPlace)) {
			paramEntity.setWorkPlace(tmp.getDesireAddress());
		}
		String jobCategory = request.getParameter("jobCategory");
		if (jobCategory != null && !"".equals(jobCategory)) {
			paramEntity.setJobCategory(tmp.getDesireJob());
		}
		String nature = request.getParameter("nature");
		if (nature != null && !"".equals(nature)) {
			paramEntity.setNature(tmp.getJobNature());
		}
		String age = request.getParameter("age");
		if (age != null && !"".equals(age)) {
			paramEntity.setAge(KitService.getAgeByBirth(tmp.getBirth()) + "");
		}
		String gender = request.getParameter("gender");
		if (gender != null && !"".equals(gender)) {
			paramEntity.setGender(tmp.getGender());
		}
		String education = request.getParameter("education");
		if (education != null && !"".equals(education)) {
			paramEntity.setEducation(tmp.getEducation());
		}
		String salay = request.getParameter("salay");
		if (salay != null && !"".equals(salay)) {
			paramEntity.setSalary(tmp.getDesireSalary());
		}
		String experience = request.getParameter("experience");
		if (experience != null && !"".equals(experience)) {
			paramEntity.setExperience(tmp.getExperience());
		}
		String disabilityPart = request.getParameter("disabilityPart");
		if (disabilityPart != null && !"".equals(disabilityPart)) {
			paramEntity.setDisabilityPart(tmp.getDisabilityPart());
		}
		String disabilityLevel = request.getParameter("disabilityLevel");
		if (disabilityLevel != null && !"".equals(disabilityLevel)) {
			paramEntity.setDisabilityLevel(tmp.getDisabilityLevel());
		}
		String disabilityCategory = request.getParameter("disabilityCategory");
		if (disabilityCategory != null && !"".equals(disabilityCategory)) {
			paramEntity.setDisabilityCategory(tmp.getDisabilityCategory());
		}
		String name = request.getParameter("name");
		if (name != null && !"".equals(name)) {
			paramEntity.setName(tmp.getTitle());
		}
		return paramEntity;
	}

	/**
	 * 以职位为参数查询匹配的简历, 按匹配度来返回组装参数完毕的职位
	 * 
	 * @param paramEntity
	 *            --返回的查询实体
	 * @param matchRate
	 *            --匹配度
	 * @param tmp
	 *            --从该参数获取对应查询属性值.
	 * @return
	 */
	private Resume getParamEntity(HttpServletRequest request, Job tmp) {
		/********************* 获取地区码 ************************/
		String userId = CookieHelper.getCookieValue(request,
				Constants.ADMINUSERID);
		Integer uid = Integer.parseInt(userId);
		User userObj = userService.getById(uid);
		// 根据管理员用户所属地区, 查询他下面所属的所有数据
		Resume paramEntity = new Resume();
		paramEntity.setArea(userObj.getArea());
		paramEntity.setCheckStatus(Constants.CheckStatus.OK.getValue());// 只选择审核通过的
		String workPlace = request.getParameter("workPlace");
		if (workPlace != null && !"".equals(workPlace)) {
			paramEntity.setDesireAddress(tmp.getWorkPlace());
		}
		String jobCategory = request.getParameter("jobCategory");
		if (jobCategory != null && !"".equals(jobCategory)) {
			paramEntity.setDesireJob(tmp.getJobCategory());
		}
		String nature = request.getParameter("nature");
		if (nature != null && !"".equals(nature)) {
			paramEntity.setJobNature(tmp.getNature());
		}
		String age = request.getParameter("age");
		if (age != null && !"".equals(age)) {
			if(tmp.getAge()!=null && tmp.getAge()!=""){
				paramEntity.setBirth(KitService.getBirthByAge(Integer.parseInt(tmp
						.getAge())));
			}
		}
		String gender = request.getParameter("gender");
		if (gender != null && !"".equals(gender)) {
			paramEntity.setGender(tmp.getGender());
		}
		String education = request.getParameter("education");
		if (education != null && !"".equals(education)) {
			paramEntity.setEducation(tmp.getEducation());
		}
		String salay = request.getParameter("salay");
		if (salay != null && !"".equals(salay)) {
			paramEntity.setDesireSalary(tmp.getSalary());
		}
		String experience = request.getParameter("experience");
		if (experience != null && !"".equals(experience)) {
			paramEntity.setExperience(tmp.getExperience());
		}
		String name = request.getParameter("name");
		if (name != null && !"".equals(name)) {
			paramEntity.setTitle(tmp.getName());
		}
		return paramEntity;
	}

	/**
	 * 查看sendResumeList中是否有resumeList中id相同的项, id不同的项添到sendResumeList中, 相同的跳过
	 * 
	 * @param resumeList
	 * @return
	 */
	private List<Resume> addNotDuplicateResume(List<Resume> resumeList,
			List<Resume> sendResumeList, Integer tuisongNumber) {
		if (resumeList == null || resumeList.size() == 0) {
			return sendResumeList;
		}
		// 如果已有的简历数大于指定的推送数, 则返回
		if (sendResumeList.size() >= tuisongNumber) {
			return sendResumeList;
		}
		for (Resume resume : resumeList) {
			if (!sendResumeList.contains(resume)) {
				sendResumeList.add(resume);
				// 如果已有的简历数大于指定的推送数, 则返回
				if (sendResumeList.size() >= tuisongNumber) {
					return sendResumeList;
				}
			}
		}
		return sendResumeList;
	}

}
