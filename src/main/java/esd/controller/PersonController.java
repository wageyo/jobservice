package esd.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import esd.bean.Area;
import esd.bean.JobCategory;
import esd.bean.Parameter;
import esd.service.AreaService;
import esd.service.JobCategoryService;
import esd.service.ParameterService;

@Controller
@RequestMapping("/person")
public class PersonController {

	private static Logger log = Logger.getLogger(PersonController.class);

	@Autowired
	private ParameterService parameterService;

	@Autowired
	private JobCategoryService jcService;

	@Autowired
	private AreaService areaService;

	// 跳转到添加简历页面
	@RequestMapping("/gotoResumeAdd")
	public ModelAndView gotoResumeAdd() {
		log.info("----- getSentJob -----");
		ModelAndView mav = new ModelAndView("person/resume-add");
		List<Parameter> plist = parameterService.getAll();
		mav.addObject("params", plist);
		// 职位类别
		List<JobCategory> jlist = jcService.getAll();
		mav.addObject("jcList", jlist);
		// 工作地区
		List<Area> alist = areaService.getAll();
		mav.addObject("areaList", alist);
		return mav;
	}

//	// 校验残疾证号是否存在并正确--访问中残联的接口哦~~~
//	@RequestMapping(value = "/checkDisabilityCard/{disabilityCard}/{name}", method = RequestMethod.POST)
//	@ResponseBody
//	public Map<String, Object> checkDisabilityCard(
//			@PathVariable(value = "disabilityCard") String disabilityCard,
//			@PathVariable(value = "name") String name) {
//		log.info("----- checkDisabilityCard -----");
//		Map<String, Object> entity = new HashMap<String, Object>();
//		Boolean bl = checkDisabilityCardService.check(disabilityCard, name);
//		if (bl) {
//			entity.put(Constants.NOTICE, Constants.Notice.SUCCESS.getValue());
//		} else {
//			entity.put(Constants.NOTICE, Constants.Notice.FAILURE.getValue());
//		}
//		return entity;
//	}
}
