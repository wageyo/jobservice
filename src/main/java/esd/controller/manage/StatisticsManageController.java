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
import esd.bean.Resume;
import esd.bean.StatisticsCompany;
import esd.bean.StatisticsWorker;
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
		// 根据管理员用户所属地区, 查询他下面所属的所有数据
		String acode = userObj.getArea().getCode();
		//查询出该地区的所有下辖子地区(不是孙地区喔!)
		List<HashMap<String, Object>> entityList = new ArrayList<HashMap<String,Object>>();
		entityList.add(getStatisticDate(userObj.getArea()));
		// 为省, 市级地区时才循环查询出该地区的企业及招聘信息数据, 县区级时则不能查询下属的信息了！！！
		if(!acode.startsWith("30")){
			List<Area> subArea = areaService.getSubArea(acode);
			// 先将当前地区信息仿佛list集合中
			for(Area a:subArea){
				entityList.add(getStatisticDate(a));
			}
		}
		entity.put("entityList", entityList);
		return new ModelAndView("manage/statistics", entity);
	}

	public HashMap<String,Object> getStatisticDate(Area area){
		String acode = area.getCode();
		HashMap<String,Object> entity = new HashMap<String,Object>();
		try {
			StatisticsCompany sc = statisticsCompanyService.getByArea(acode);
			sc.setArea(area);
			entity.put("statisticsCompany", sc);
			log.debug("获取的本地区企业/招聘信息数据对象为:" + sc);
		} catch (Exception e) {
			log.error("获取 本地区企业/招聘信息数据 时发生错误。");
			e.printStackTrace();
		}
		// 查询出该地区的残疾人简历及已经就业数据
		try {
			StatisticsWorker sw = new StatisticsWorker();
			sw.setArea(new Area(acode)); // 地区
			// 得到残疾人人数
			User tmpUser = new User();
			tmpUser.setArea(new Area(acode));
			Integer numberUser = userService.getTotalCount(tmpUser);
			sw.setNumberUser(numberUser);
			// 简历总数
			Resume tmpResume = new Resume();
			tmpResume.setArea(new Area(acode));
			
			Integer numberResume = resumeService.getTotalCount(tmpResume,
					Boolean.FALSE);
			sw.setNumberResume(numberResume);
			// 已就业的人数
			tmpResume.setCheckStatus(Constants.CheckStatus.YIJIUYE.getValue());
			Integer numberHired = resumeService.getTotalCount(tmpResume,
					Boolean.FALSE);
			sw.setNumberHired(numberHired);
			entity.put("statisticsWorker", sw);
			log.debug("获取的本地区残疾职工信息数据对象为:" + sw);
		} catch (Exception e) {
			log.error("获取 本地区残疾职工信息数据 时发生错误。");
			e.printStackTrace();
		}
		return entity;
	}
	
}
