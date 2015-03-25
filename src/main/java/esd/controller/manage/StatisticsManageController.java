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
import esd.bean.Resume;
import esd.bean.StatisticsCompany;
import esd.bean.User;
import esd.controller.Constants;
import esd.service.AreaService;
import esd.service.CookieHelper;
import esd.service.ResumeService;
import esd.service.StatisticsCompanyService;
import esd.service.UserService;

/**
 * 数据统计 后台管理控制器
 * 
 * @author yufu
 * @email ilxly01@126.com 2015-3-7
 */
@Controller
@RequestMapping("/manage/statistics")
public class StatisticsManageController {
	private static Logger log = Logger
			.getLogger(StatisticsManageController.class);

	@Autowired
	private UserService<User> userService;

	@Autowired
	private ResumeService resumeService;

	@Autowired
	private StatisticsCompanyService statisticsCompanyService;

	@Autowired
	private AreaService areaService;

	// 转到数据统计查看页面
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView list_get(HttpServletRequest request) {
		log.debug("*******转到数据统计查看页面********");
		Map<String, Object> entity = new HashMap<>();
		// 获取地区码
		String userId = CookieHelper.getCookieValue(request,
				Constants.ADMINUSERID);
		if (userId == null || "".equals(userId)) {
			return new ModelAndView("redirect:/loginManage/login");
		}
		Integer uid = Integer.parseInt(userId);
		User userObj = userService.getById(uid);
		// 根据管理员用户所属地区, 查询他下面所有所属的子地区(列表中包含本地区哦)
		String acode = userObj.getArea().getCode();
		List<Area> areaList = new ArrayList<Area>();
		// 本地区
		areaList.add(userObj.getArea());
		// 子地区
		List<Area> subArea = areaService.getSubArea(acode);
		if (subArea != null) {
			for (int i = 0; i < subArea.size(); i++) {
				areaList.add(subArea.get(i));
			}
		}
		entity.put("areaList", areaList); // 放到数据模型中
		entity.put("acode", acode); // 当前地区code
		return new ModelAndView("manage/statistics", entity);
	}

	/**
	 * 根据地区code获得该地区的数据
	 * 
	 * @param area
	 * @return
	 */
	@RequestMapping(value = "/getStatistics/{acode}", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> getStatistics(
			@PathVariable(value = "acode") String acode,
			HttpServletRequest request) {
		HashMap<String, Object> entity = new HashMap<String, Object>();
		float[] dataList = new float[7];
		Area area = areaService.getByCode(acode);
		try {
			StatisticsCompany sc = statisticsCompanyService.getByArea(acode);
			int numberCompany = 0;
			if (sc.getNumberCompany() != null) {
				numberCompany = sc.getNumberCompany();
			}
			dataList[0] = numberCompany; // 企业总数
			int numberJob = 0;
			if (sc.getNumberJob() != null) {
				numberJob = sc.getNumberJob();
			}
			dataList[1] = numberJob;// 职位总数
			int numberHire = 0;
			if (sc.getNumberHire() != null) {
				numberHire = sc.getNumberHire();
			}
			dataList[2] = numberHire; // 招聘总人数
			dataList[3] = sc.getAverageJob(); // 平均每家企业发布职位数
			// dataList[4] = sc.getAverageHire(); // 平均每家企业招聘职工数
			log.debug("获取的本地区企业/招聘信息数据对象为:" + sc);
			// 查询出该地区的残疾人简历及已经就业数据
			// 得到残疾人人数
			User tmpUser = new User();
			tmpUser.setArea(area);
			tmpUser.setIdentity(Constants.Identity.PERSON.getValue());
			Integer numberUser = userService.getTotalCount(tmpUser);
			int SNumberUser = 0;
			if (numberUser != null) {
				SNumberUser = numberUser;
			}
			dataList[4] = SNumberUser; // 注册总人数
			// 简历总数
			Resume tmpResume = new Resume();
			tmpResume.setArea(area);

			Integer numberResume = resumeService.getTotalCount(tmpResume,
					Boolean.FALSE);
			int SNumberResume = 0;
			if (numberResume != null) {
				SNumberResume = numberResume;
			}
			dataList[5] = SNumberResume; // 创建简历数
			// 已就业的人数
			tmpResume.setCheckStatus(Constants.CheckStatus.YIJIUYE.getValue());
			Integer numberHired = resumeService.getTotalCount(tmpResume,
					Boolean.FALSE);
			int SNumberHired = 0;
			if (numberHired != null) {
				SNumberHired = numberHired;
			}
			dataList[6] = SNumberHired; // 已就业人数
			// 数据放入到对应的map中
			entity.put("dataList", dataList);
			entity.put("area", area.getName());
		} catch (Exception e) {
			log.error("获取的本地区企业/招聘信息数据 时发生错误。");
			e.printStackTrace();
		}
		return entity;
	}

	// /**
	// * 根据地区code获得该地区的数据-----------原有的备份！！！！！
	// *
	// * @param area
	// * @return
	// */
	// @RequestMapping(value = "/getStatistics/{acode}", method =
	// RequestMethod.POST)
	// @ResponseBody
	// public HashMap<String, Object> getStatistics(
	// @PathVariable(value = "acode") String acode,
	// HttpServletRequest request) {
	// HashMap<String, Object> entity = new HashMap<String, Object>();
	// List<Map<String, Object>> dataList = new ArrayList<Map<String,
	// Object>>();
	// Area area = areaService.getByCode(acode);
	// try {
	// StatisticsCompany sc = statisticsCompanyService.getByArea(acode);
	// String numberCompany ="0";
	// if(sc.getNumberCompany() != null){
	// numberCompany = sc.getNumberCompany()+"";
	// }
	// dataList.add(getMapByData("企业总数",numberCompany, "#a5c2d5")); // 企业总数
	// String numberJob ="0";
	// if(sc.getNumberJob() != null){
	// numberJob = sc.getNumberJob()+"";
	// }
	// dataList.add(getMapByData("职位总数",numberJob, "#cbab4f")); // 职位总数
	// String numberHire ="0";
	// if(sc.getNumberHire() != null){
	// numberHire = sc.getNumberHire()+"";
	// }
	// dataList.add(getMapByData("招聘总人数",numberHire, "#76a871")); // 招聘总人数
	// dataList.add(getMapByData("企业平均职位数", sc.getAverageJob(),
	// "#4267BE")); // 平均每家企业发布职位数
	// dataList.add(getMapByData("企业平均招聘数", sc.getAverageHire(),
	// "#9F2626")); // 平均每家企业招聘职工数
	// log.debug("获取的本地区企业/招聘信息数据对象为:" + sc);
	// // 查询出该地区的残疾人简历及已经就业数据
	// // 得到残疾人人数
	// User tmpUser = new User();
	// tmpUser.setArea(area);
	// tmpUser.setIdentity(Constants.Identity.PERSON.getValue());
	// Integer numberUser = userService.getTotalCount(tmpUser);
	// String SNumberUser ="0";
	// if(numberUser != null){
	// SNumberUser = numberUser+"";
	// }
	// dataList.add(getMapByData("注册残疾人数", SNumberUser,
	// "#9f7961")); // 注册总人数
	// // 简历总数
	// Resume tmpResume = new Resume();
	// tmpResume.setArea(area);
	//
	// Integer numberResume = resumeService.getTotalCount(tmpResume,
	// Boolean.FALSE);
	// String SNumberResume ="0";
	// if(numberResume != null){
	// SNumberResume = numberResume+"";
	// }
	// dataList.add(getMapByData("创建简历数", SNumberResume,
	// "#76a871")); // 创建简历数
	// // 已就业的人数
	// tmpResume.setCheckStatus(Constants.CheckStatus.YIJIUYE.getValue());
	// Integer numberHired = resumeService.getTotalCount(tmpResume,
	// Boolean.FALSE);
	// String SNumberHired ="0";
	// if(numberHired != null){
	// SNumberHired = numberHired+"";
	// }
	// dataList.add(getMapByData("已就业人数", SNumberHired,
	// "#6f83a5")); // 已就业人数
	// //数据放入到对应的map中
	// entity.put("dataList", dataList);
	// entity.put("area", area.getName());
	// entity.put("endScale",
	// maxScale(numberCompany,numberJob,numberHire,SNumberUser,SNumberResume));
	// } catch (Exception e) {
	// log.error("获取的本地区企业/招聘信息数据 时发生错误。");
	// e.printStackTrace();
	// }
	// return entity;
	// }

	/**
	 * 根据传进去的值返回封装成的Map对象
	 * 
	 * @param name
	 * @param value
	 * @param color
	 * @return
	 */
	private Map<String, Object> getMapByData(String name, String value,
			String color) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("value", value);
		map.put("color", color);
		return map;
	}

	/**
	 * 从企业数, 职位数, 雇用人数, 注册残疾人数和发布的简历数中找出最大值并向上取整, 来给前台ichart控件设定最大值--已废弃！！！--by yufu 2015-03-25
	 * 
	 * @param numberCompany
	 * @param numberJob
	 * @param numberHire
	 * @param SNumberUser
	 * @param SNumberResume
	 * @return
	 */
	private int maxScale(String numberCompany, String numberJob,
			String numberHire, String SNumberUser, String SNumberResume) {
		int nc = Integer.parseInt(numberCompany);
		int nj = Integer.parseInt(numberJob);
		int nh = Integer.parseInt(numberHire);
		int su = Integer.parseInt(SNumberUser);
		int sr = Integer.parseInt(SNumberResume);
		int a[] = { nc, nj, nh, su, sr };
		double max = 0;
		for (int i = 1; i < a.length; i++) {
			if (a[i] > max)
				max = a[i];
		}
		// 最大数字扩大1/4, 同时向上取整, 取向上最接近的整数百位
		if (max > 0) {
			max = Math.ceil(max * 1.2 / 100) * 100;
		} else {
			max = 100;
		}
		return (int) max;
	}

//	public static void main(String[] args) {
//		StatisticsManageController sm = new StatisticsManageController();
//		int max = sm.maxScale("19", "32", "193", "402", "390");
//		System.out.println(max);
//	}
}
