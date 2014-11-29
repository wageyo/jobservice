package esd.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import esd.bean.Area;
import esd.bean.Company;
import esd.bean.Job;
import esd.bean.JobCategory;
import esd.bean.News;
import esd.bean.Parameter;
import esd.bean.Record;
import esd.bean.Resume;
import esd.controller.Constants;
import esd.dao.AreaDao;
import esd.dao.JobCategoryDao;
import esd.dao.ParameterDao;

/**
 * 工具类: ①将前台传进来的对象或者字段转换成适合sql语句的字段 ②将从数据库读取出来的对象或者字段转换成适合前台显示的对象或字段
 * 
 * @author yufu
 * @email ilxly01@126.com 2014-11-5
 */
@Service
public class KitService {

	private static Logger log = Logger.getLogger(KitService.class);
	@Autowired
	private ParameterDao pDao;

	@Autowired
	private JobCategoryDao jcDao;

	@Autowired
	private AreaDao aDao;

	/**
	 * 将日期转换为yyyy-MM-dd格式
	 * 
	 * @param date
	 * @return
	 */
	public static String dateForShow(Date date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}

	/**
	 * 根据审核状态值, 获得对应的审核状态名称(中文)
	 * 
	 * @param checkStatus
	 * @return
	 */
	public static String getCheckStatusName(String checkStatus) {
		if (checkStatus == null || "".equals(checkStatus)) {
			return null;
		}
		if (Constants.CheckStatus.DAISHEN.getValue().equals(checkStatus)) {
			return "待审核";
		}
		if (Constants.CheckStatus.WEITONGGUO.getValue().equals(checkStatus)) {
			return "未通过";
		}
		if (Constants.CheckStatus.OK.getValue().equals(checkStatus)) {
			return "已通过";
		}
		return "未知状态";
	}

	/**
	 * 根据文章类型值, 获得对应类型名称(中文)
	 * 
	 * @param articleType
	 * @return
	 */
	public static String getArticleName(String articleType) {
		if (articleType == null || "".equals(articleType)) {
			return null;
		}
		if (Constants.ARTICLE_TYPE_DIRECT.equals(articleType)) {
			return "就业指导";
		}
		if (Constants.ARTICLE_TYPE_NEWS.equals(articleType)) {
			return "最新资讯";
		}
		return "未知类型";
	}
	
	/**
	 * 根据开关值, 获得对应的开关名称
	 * 
	 * @param switchValue
	 * @return
	 */
	public static String getSwitchName(String switchValue) {
		if (switchValue == null || "".equals(switchValue)) {
			return null;
		}
		if (Constants.Switch.USER.getValue().equals(switchValue)) {
			return "账号信息";
		}
		if (Constants.Switch.RESUME.getValue().equals(switchValue)) {
			return "简历信息";
		}
		if (Constants.Switch.COMPANY.getValue().equals(switchValue)) {
			return "企业信息";
		}
		if (Constants.Switch.JOB.getValue().equals(switchValue)) {
			return "职位信息";
		}
		return "未知类型";
	}

	/**
	 * 根据数据总条数, 得到总页数
	 * 
	 * @param totolCount
	 * @return
	 */
	public static int getTotalPage(int totalCount) {
		return totalCount % Constants.SIZE == 0 ? totalCount / Constants.SIZE
				: (totalCount / Constants.SIZE) + 1;
	}

	public static void main(String[] args) {
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		// Date today = new Date();
		// Long l1 = 20L;
		// System.out.println(sdf.format(new
		// Date(today.getTime()+l1*24*60*60*1000)));
		Boolean bl = null;
		System.out.println(bl);
		System.out.println(KitService.getTotalPage(52));
	}

	/**
	 * 得到有效期截止日期
	 * 
	 * @param effectiveDays
	 * @return
	 */
	public static Date getEffectiveTime(Long effectiveDays) {
		Date today = new Date();
		// 时间为零, 则返回今天
		if (effectiveDays <= 0) {
			return today;
		}
		return new Date(today.getTime() + effectiveDays * 24 * 60 * 60 * 1000);
	}

	/**
	 * 将id转换为数字id
	 * 
	 * @param str
	 * @return
	 */
	public static int getInt(String str) {
		log.info(" str = " + str);
		// 验证非空
		if (str == null) {
			log.info("传递的参数为空!");
			return -1;
		}
		int id;
		try {
			id = Integer.parseInt(str);
			return id;
		} catch (NumberFormatException e) {
			log.info("传递的参数有误!");
			return -1;
		}
	}

	/**
	 * 有三级县区地区code, 提取出所在省份地区code
	 * 
	 * @param districtCode
	 * @return
	 */
	public static String getProvinceCode(String districtCode) {
		if (districtCode == null || "".equals(districtCode)
				|| !districtCode.startsWith("30")) {
			return districtCode;
		}
		String mid = districtCode.substring(2, 4);
		return "10" + mid + "0000";
	}

	public static String getCodeForNews(String code) {
		if (code == null || "".equals(code) || "10000000".equals(code)) {
			return null;
		}
		return "__" + code.substring(2, 4) + "____";
	}

	/**
	 * 处理传进来的地区code, 变成适用于sql语句使用的格式
	 * 
	 * @param code
	 * @return
	 */
	public static String areaCodeForSql(String code) {
		if (code == null || "".equals(code)) {
			return null;
		}
		String start = code.substring(0, 2);
		String mid;
		if ("10000000".equals(code)) {
			code = "30______";
		} else if ("10".equals(start)) {
			mid = code.substring(2, 4);
			code = "__" + mid + "____";
		} else if ("20".equals(start)) {
			mid = code.substring(2, 6);
			code = "__" + mid + "__";
		}
		return code;
	}

	/**
	 * 处理传进来的地区code, 变成适用于sql语句使用的格式,适用于带有信息查询范围的场合
	 * 
	 * @param code
	 * @return
	 */
	public static String areaCodeForSql1(String value, String code) {
		if (code == null || "".equals(code)) {
			return null;
		}
		String mid;
		//全国范围
		if("country".equals(value)){
			code =  "%";
		}else if("privince".equals(value)){
			//全省范围
			mid = code.substring(2, 4);
			code = "__" + mid + "____";
		}else if("city".equals(value)){
			//全市范围
			mid = code.substring(2, 6);
			code = "__" + mid + "__";
		}else if("district".equals(value)){
			//全县/区
			mid = code.substring(2, 8);
			code = "__" + mid + "__";
		}
		return code;
	}
	
	/**
	 * 处理传进来的职位种类code, 变成适用于jobMapper中sql语句使用的格式
	 * 
	 * @param code
	 * @return
	 */
	public static String jobCategoryCodeForJobSql(String code) {
		if (code == null || "".equals(code)) {
			return null;
		}
		String start = code.substring(0, 2);
		String mid;
		if ("10000000".equals(code)) {
			code = null;
		} else if ("10".equals(start)) {
			mid = code.substring(2, 4);
			code = "( job.jccode = '" + code + "' or job.jccode like '20" + mid
					+ "____' or job.jccode like '30" + mid + "____')";
		} else if ("20".equals(start)) {
			mid = code.substring(2, 6);
			code = "( job.jccode = '" + code + "' or job.jccode like '30" + mid
					+ "__')";
		}
		return code;
	}

	/**
	 * 处理传进来的职位种类code, 变成适用于resumeMapper中sql语句使用的格式
	 * 
	 * @param code
	 * @return
	 */
	public static String jobCategoryCodeForResumeSql(String code) {
		if (code == null || "".equals(code)) {
			return null;
		}
		String start = code.substring(0, 2);
		String mid;
		if ("10000000".equals(code)) {
			code = null;
		} else if ("10".equals(start)) {
			mid = code.substring(2, 4);
			code = "( resume.desireJob = '" + code
					+ "' or resume.desireJob like '20" + mid
					+ "____' or resume.desireJob like '30" + mid + "____')";
		} else if ("20".equals(start)) {
			mid = code.substring(2, 6);
			code = "( resume.desireJob = '" + code
					+ "' or resume.desireJob like '30" + mid + "__')";
		}
		return code;
	}

	/**
	 * 根据传进来的日期字符串, 算出年龄
	 * 
	 * @param birth
	 * @return
	 */
	public static int getAgeByBirth(String birth) {
		if (birth == null || "".equals(birth)) {
			return 0;
		}
		// 当前时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String curYear = sdf.format(new Date(System.currentTimeMillis()));
		// 出生日期
		String bornYear = birth.substring(0, 4);
		int age = Integer.parseInt(curYear) - Integer.parseInt(bornYear);
		return age;
	}

	/**
	 * 将resume处理成为适合前台显示用的对象
	 * 
	 * @param resume
	 * @return
	 */
	public Resume getForShow(Resume resume) {
		if (resume == null) {
			return null;
		}
		// 获得所有参数
		List<Parameter> plist = pDao.getByPage(null);
		// 所有职位种类
		List<JobCategory> jclist = jcDao.getAll();
		// 所有地区
		List<Area> alist = aDao.getAll();

		// // 创建时间
		// if (resume.getCreateDate() != null
		// && !"".equals(resume.getCreateDate())) {
		// resume.setCreateDate(dateForShow(resume.getCreateDate()));
		// }
		// // 更新时间
		// if (resume.getUpdateDate() != null
		// && !"".equals(resume.getUpdateDate())) {
		// resume.setUpdateDate(dateForShow(resume.getUpdateDate()));
		// }
		// // 出生日期
		// if (resume.getBirth() != null && !"".equals(resume.getBirth())) {
		// resume.setBirth(dateForShow(resume.getBirth()));
		// }

		// 性别
		if (resume.getGender() != null && !"".equals(resume.getGender())) {
			Parameter p = new Parameter();
			p.setType(Constants.GENDER);
			p.setValue(resume.getGender());
			for (Parameter pa : plist) {
				if (pa.getType().equals(p.getType())
						&& pa.getValue().equals(p.getValue())) {
					p = pa;
					break;
				}
			}
			resume.setGender(p.getName());
		}
		// 户籍所在地
		if (resume.getHukou() != null) {
			if (resume.getHukou().getCode() != null
					&& !"".equals(resume.getHukou().getCode())) {
				Area hukou = aDao.getByCode(resume.getHukou().getCode());
				resume.setHukou(hukou);
			}
		}
		// 户口状况
		if (resume.getHukouStatus() != null
				&& !"".equals(resume.getHukouStatus())) {
			Parameter p = new Parameter();
			p.setType(Constants.HUKOU);
			p.setValue(resume.getHukouStatus());
			for (Parameter pa : plist) {
				if (pa.getType().equals(p.getType())
						&& pa.getValue().equals(p.getValue())) {
					p = pa;
					break;
				}
			}
			resume.setHukouStatus(p.getName());
		}
		// 婚姻状况
		if (resume.getMarriage() != null && !"".equals(resume.getMarriage())) {
			Parameter p = new Parameter();
			p.setType(Constants.MARRIAGE);
			p.setValue(resume.getMarriage());
			for (Parameter pa : plist) {
				if (pa.getType().equals(p.getType())
						&& pa.getValue().equals(p.getValue())) {
					p = pa;
					break;
				}
			}
			resume.setMarriage(p.getName());
		}
		// 残疾类别
		if (resume.getDisabilityCategory() != null
				&& !"".equals(resume.getDisabilityCategory())) {
			Parameter p = new Parameter();
			p.setType(Constants.DISABILITYCATEGORY);
			p.setValue(resume.getDisabilityCategory());
			for (Parameter pa : plist) {
				if (pa.getType().equals(p.getType())
						&& pa.getValue().equals(p.getValue())) {
					p = pa;
					break;
				}
			}
			resume.setDisabilityCategory(p.getName());
		}
		// 残疾等级
		if (resume.getDisabilityLevel() != null
				&& !"".equals(resume.getDisabilityLevel())) {
			Parameter p = new Parameter();
			p.setType(Constants.DISABILITYLEVEL);
			p.setValue(resume.getDisabilityLevel());
			for (Parameter pa : plist) {
				if (pa.getType().equals(p.getType())
						&& pa.getValue().equals(p.getValue())) {
					p = pa;
					break;
				}
			}
			resume.setDisabilityLevel(p.getName());
		}
		// 残疾部位
		if (resume.getDisabilityPart() != null
				&& !"".equals(resume.getDisabilityPart())) {
			Parameter p = new Parameter();
			p.setType(Constants.DISABILITYPART);
			p.setValue(resume.getDisabilityPart());
			for (Parameter pa : plist) {
				if (pa.getType().equals(p.getType())
						&& pa.getValue().equals(p.getValue())) {
					p = pa;
					break;
				}
			}
			resume.setDisabilityPart(p.getName());
		}
		// 职位类型
		if (resume.getJobNature() != null && !"".equals(resume.getJobNature())) {
			Parameter p = new Parameter();
			p.setType(Constants.JOBNATURE);
			p.setValue(resume.getJobNature());
			for (Parameter pa : plist) {
				if (pa.getType().equals(p.getType())
						&& pa.getValue().equals(p.getValue())) {
					p = pa;
					break;
				}
			}
			resume.setJobNature(p.getName());
		}
		// 期望工作地
		if (resume.getDesireAddress() != null) {
			if(resume.getDesireAddress().getCode()!=null && !"".equals(resume.getDesireAddress().getCode())){
				Area desireAddress = aDao.getByCode(resume.getDesireAddress().getCode());
				resume.setDesireAddress(desireAddress);
			}
		}
		// 目标职位种类code处理
		if (resume.getDesireJob() != null) {
			if(resume.getDesireJob().getCode()!=null && !"".equals(resume.getDesireJob().getCode())){
				JobCategory desireJob = jcDao.getByCode(resume.getDesireJob().getCode());
				resume.setDesireJob(desireJob);
			}
		}
		// 期望薪水
		if (resume.getDesireSalary() != null
				&& !"".equals(resume.getDesireSalary())) {
			Parameter p = new Parameter();
			p.setType(Constants.SALARY);
			p.setValue(resume.getDesireSalary());
			for (Parameter pa : plist) {
				if (pa.getType().equals(p.getType())
						&& pa.getValue().equals(p.getValue())) {
					p = pa;
					break;
				}
			}
			resume.setDesireSalary(p.getName());
		}
		// 到岗时间、目前状态
		if (resume.getState() != null && !"".equals(resume.getState())) {
			Parameter p = new Parameter();
			p.setType(Constants.STATE);
			p.setValue(resume.getState());
			for (Parameter pa : plist) {
				if (pa.getType().equals(p.getType())
						&& pa.getValue().equals(p.getValue())) {
					p = pa;
					break;
				}
			}
			resume.setState(p.getName());
		}

		// 学历
		if (resume.getEducation() != null && !"".equals(resume.getEducation())) {
			Parameter p = new Parameter();
			p.setType(Constants.EDUCATION);
			p.setValue(resume.getEducation());
			for (Parameter pa : plist) {
				if (pa.getType().equals(p.getType())
						&& pa.getValue().equals(p.getValue())) {
					p = pa;
					break;
				}
			}
			resume.setEducation(p.getName());
		}
		// 工作经验年限
		if (resume.getExperience() != null
				&& !"".equals(resume.getExperience())) {
			Parameter p = new Parameter();
			p.setType(Constants.EXPERIENCE);
			p.setValue(resume.getExperience());
			for (Parameter pa : plist) {
				if (pa.getType().equals(p.getType())
						&& pa.getValue().equals(p.getValue())) {
					p = pa;
					break;
				}
			}
			resume.setExperience(p.getName());
		}
		// 审核状态
		if (resume.getCheckStatus() != null
				&& !"".equals(resume.getCheckStatus())) {
			Parameter p = new Parameter();
			p.setType(Constants.CHECKSTATUS);
			p.setValue(resume.getCheckStatus());
			for (Parameter pa : plist) {
				if (pa.getType().equals(p.getType())
						&& pa.getValue().equals(p.getValue())) {
					p = pa;
					break;
				}
			}
			resume.setCheckStatus(p.getName());
		}
		return resume;
	}

	/**
	 * 将resume数组处理成为适合前台显示用的对象
	 * 
	 * @param resumeList
	 * @return
	 */
	public List<Resume> getForShowResume(List<Resume> resumeList) {
		if (resumeList == null) {
			return null;
		}
		// 获得所有参数
		List<Parameter> plist = pDao.getByPage(null);
		// 所有职位种类
		List<JobCategory> jclist = jcDao.getAll();
		// 所有地区
		List<Area> alist = aDao.getAll();
		for (Resume resume : resumeList) {
			// // 创建时间
			// if (resume.getCreateDate() != null
			// && !"".equals(resume.getCreateDate())) {
			// resume.setCreateDate(dateForShow(resume.getCreateDate()));
			// }
			// // 更新时间
			// if (resume.getUpdateDate() != null
			// && !"".equals(resume.getUpdateDate())) {
			// resume.setUpdateDate(dateForShow(resume.getUpdateDate()));
			// }
			// // 出生日期
			// if (resume.getBirth() != null && !"".equals(resume.getBirth())) {
			// resume.setBirth(dateForShow(resume.getBirth()));
			// }

			// 性别
			if (resume.getGender() != null && !"".equals(resume.getGender())) {
				Parameter p = new Parameter();
				p.setType(Constants.GENDER);
				p.setValue(resume.getGender());
				for (Parameter pa : plist) {
					if (pa.getType().equals(p.getType())
							&& pa.getValue().equals(p.getValue())) {
						p = pa;
						break;
					}
				}
				resume.setGender(p.getName());
			}
			// 户口状况
			if (resume.getHukouStatus() != null
					&& !"".equals(resume.getHukouStatus())) {
				Parameter p = new Parameter();
				p.setType(Constants.HUKOU);
				p.setValue(resume.getHukouStatus());
				for (Parameter pa : plist) {
					if (pa.getType().equals(p.getType())
							&& pa.getValue().equals(p.getValue())) {
						p = pa;
						break;
					}
				}
				resume.setHukouStatus(p.getName());
			}
			// 婚姻状况
			if (resume.getMarriage() != null
					&& !"".equals(resume.getMarriage())) {
				Parameter p = new Parameter();
				p.setType(Constants.MARRIAGE);
				p.setValue(resume.getMarriage());
				for (Parameter pa : plist) {
					if (pa.getType().equals(p.getType())
							&& pa.getValue().equals(p.getValue())) {
						p = pa;
						break;
					}
				}
				resume.setMarriage(p.getName());
			}
			// 残疾类别
			if (resume.getDisabilityCategory() != null
					&& !"".equals(resume.getDisabilityCategory())) {
				Parameter p = new Parameter();
				p.setType(Constants.DISABILITYCATEGORY);
				p.setValue(resume.getDisabilityCategory());
				for (Parameter pa : plist) {
					if (pa.getType().equals(p.getType())
							&& pa.getValue().equals(p.getValue())) {
						p = pa;
						break;
					}
				}
				resume.setDisabilityCategory(p.getName());
			}
			// 残疾等级
			if (resume.getDisabilityLevel() != null
					&& !"".equals(resume.getDisabilityLevel())) {
				Parameter p = new Parameter();
				p.setType(Constants.DISABILITYLEVEL);
				p.setValue(resume.getDisabilityLevel());
				for (Parameter pa : plist) {
					if (pa.getType().equals(p.getType())
							&& pa.getValue().equals(p.getValue())) {
						p = pa;
						break;
					}
				}
				resume.setDisabilityLevel(p.getName());
			}
			// 残疾部位
			if (resume.getDisabilityPart() != null
					&& !"".equals(resume.getDisabilityPart())) {
				Parameter p = new Parameter();
				p.setType(Constants.DISABILITYPART);
				p.setValue(resume.getDisabilityPart());
				for (Parameter pa : plist) {
					if (pa.getType().equals(p.getType())
							&& pa.getValue().equals(p.getValue())) {
						p = pa;
						break;
					}
				}
				resume.setDisabilityPart(p.getName());
			}
			// 职位类型
			if (resume.getJobNature() != null
					&& !"".equals(resume.getJobNature())) {
				Parameter p = new Parameter();
				p.setType(Constants.JOBNATURE);
				p.setValue(resume.getJobNature());
				for (Parameter pa : plist) {
					if (pa.getType().equals(p.getType())
							&& pa.getValue().equals(p.getValue())) {
						p = pa;
						break;
					}
				}
				resume.setJobNature(p.getName());
			}
			// 期望工作地
			if (resume.getDesireAddress() != null) {
				if(resume.getDesireAddress().getCode()!=null && !"".equals(resume.getDesireAddress().getCode())){
					Area desireAddress = aDao.getByCode(resume.getDesireAddress().getCode());
					resume.setDesireAddress(desireAddress);
				}
			}
			// 目标职位种类code处理
			if (resume.getDesireJob() != null) {
				if(resume.getDesireJob().getCode()!=null && !"".equals(resume.getDesireJob().getCode())){
					JobCategory desireJob = jcDao.getByCode(resume.getDesireJob().getCode());
					resume.setDesireJob(desireJob);
				}
			}
			// 期望薪水
			if (resume.getDesireSalary() != null
					&& !"".equals(resume.getDesireSalary())) {
				Parameter p = new Parameter();
				p.setType(Constants.SALARY);
				p.setValue(resume.getDesireSalary());
				for (Parameter pa : plist) {
					if (pa.getType().equals(p.getType())
							&& pa.getValue().equals(p.getValue())) {
						p = pa;
						break;
					}
				}
				resume.setDesireSalary(p.getName());
			}
			// 到岗时间、目前状态
			if (resume.getState() != null && !"".equals(resume.getState())) {
				Parameter p = new Parameter();
				p.setType(Constants.STATE);
				p.setValue(resume.getState());
				for (Parameter pa : plist) {
					if (pa.getType().equals(p.getType())
							&& pa.getValue().equals(p.getValue())) {
						p = pa;
						break;
					}
				}
				resume.setState(p.getName());
			}

			// 学历
			if (resume.getEducation() != null
					&& !"".equals(resume.getEducation())) {
				Parameter p = new Parameter();
				p.setType(Constants.EDUCATION);
				p.setValue(resume.getEducation());
				for (Parameter pa : plist) {
					if (pa.getType().equals(p.getType())
							&& pa.getValue().equals(p.getValue())) {
						p = pa;
						break;
					}
				}
				resume.setEducation(p.getName());
			}
			// 工作经验年限
			if (resume.getExperience() != null
					&& !"".equals(resume.getExperience())) {
				Parameter p = new Parameter();
				p.setType(Constants.EXPERIENCE);
				p.setValue(resume.getExperience());
				for (Parameter pa : plist) {
					if (pa.getType().equals(p.getType())
							&& pa.getValue().equals(p.getValue())) {
						p = pa;
						break;
					}
				}
				resume.setExperience(p.getName());
			}
			// 审核状态
			if (resume.getCheckStatus() != null
					&& !"".equals(resume.getCheckStatus())) {
				Parameter p = new Parameter();
				p.setType(Constants.CHECKSTATUS);
				p.setValue(resume.getCheckStatus());
				for (Parameter pa : plist) {
					if (pa.getType().equals(p.getType())
							&& pa.getValue().equals(p.getValue())) {
						p = pa;
						break;
					}
				}
				resume.setCheckStatus(p.getName());
			}
		}
		return resumeList;
	}

	/**
	 * 将job处理成为适合前台显示用的对象
	 * 
	 * @param job
	 * @return
	 */
	public Job getForShow(Job job) {
		if (job == null) {
			return null;
		}
		// 获得所有参数
		List<Parameter> plist = pDao.getByPage(null);

		// // 创建时间
		// if (job.getCreateDate() != null && !"".equals(job.getCreateDate())) {
		// job.setCreateDate(dateForShow(job.getCreateDate()));
		// }
		// // 更新时间
		// if (job.getUpdateDate() != null) {
		// job.setUpdateDate(dateForShow(job.getUpdateDate()));
		// }
		// 薪水
		if (job.getSalary() != null && !"".equals(job.getSalary())) {
			Parameter p = new Parameter();
			p.setType(Constants.SALARY);
			p.setValue(job.getSalary());
			for (Parameter pa : plist) {
				if (pa.getType().equals(p.getType())
						&& pa.getValue().equals(p.getValue())) {
					p = pa;
					break;
				}
			}
			job.setSalary(p.getName());
		}else{
			//薪水为空, 则显示不限
			job.setSalary(Constants.NO_LIMIT);
		}
		// 学历
		if (job.getEducation() != null && !"".equals(job.getEducation())) {
			Parameter p = new Parameter();
			p.setType(Constants.EDUCATION);
			p.setValue(job.getEducation());
			for (Parameter pa : plist) {
				if (pa.getType().equals(p.getType())
						&& pa.getValue().equals(p.getValue())) {
					p = pa;
					break;
				}
			}
			job.setEducation(p.getName());
		}
		// 工作经验年限
		if (job.getExperience() != null && !"".equals(job.getExperience())) {
			Parameter p = new Parameter();
			p.setType(Constants.EXPERIENCE);
			p.setValue(job.getExperience());
			for (Parameter pa : plist) {
				if (pa.getType().equals(p.getType())
						&& pa.getValue().equals(p.getValue())) {
					p = pa;
					break;
				}
			}
			job.setExperience(p.getName());
		}
		// 性别
		if (job.getGender() != null && !"".equals(job.getGender())) {
			Parameter p = new Parameter();
			p.setType(Constants.GENDER);
			p.setValue(job.getGender());
			for (Parameter pa : plist) {
				if (pa.getType().equals(p.getType())
						&& pa.getValue().equals(p.getValue())) {
					p = pa;
					break;
				}
			}
			job.setGender(p.getName());
		}
		// 岗位性质
		if (job.getNature() != null && !"".equals(job.getNature())) {
			Parameter p = new Parameter();
			p.setType(Constants.JOBNATURE);
			p.setValue(job.getNature());
			for (Parameter pa : plist) {
				if (pa.getType().equals(p.getType())
						&& pa.getValue().equals(p.getValue())) {
					p = pa;
					break;
				}
			}
			job.setNature(p.getName());
		}
		// 岗位有效期
		if (job.getEffectiveTime() != null) {
			Date today = new Date();
			int cha = job.getEffectiveTime().compareTo(today); // 今天距离 有效期截止日期
																// 剩余的
			job.setEffectiveDays(cha);
		}
		//岗位类别
		if(job.getJobCategory()!=null){
			if(job.getJobCategory().getCode()!= null && !"".equals(job.getJobCategory().getCode())){
				JobCategory jobCategory = jcDao.getByCode(job.getJobCategory().getCode());
				job.setJobCategory(jobCategory);
			}
		}
		// 工作地
		if (job.getWorkPlace() != null) {
			if(job.getWorkPlace().getCode()!= null){
				Area workPlace = aDao.getByCode(job.getWorkPlace().getCode());
				job.setWorkPlace(workPlace);
			}
		}

		// 审核状态
		if (job.getCheckStatus() != null && !"".equals(job.getCheckStatus())) {
			Parameter p = new Parameter();
			p.setType(Constants.CHECKSTATUS);
			p.setValue(job.getCheckStatus());
			for (Parameter pa : plist) {
				if (pa.getType().equals(p.getType())
						&& pa.getValue().equals(p.getValue())) {
					p = pa;
					break;
				}
			}
			job.setCheckStatus(p.getName());
		}
		// 公司性质
		if (job.getCompany() != null) {
			if (job.getCompany().getNature() != null
					&& !"".equals(job.getCompany().getNature())) {
				Parameter p = new Parameter();
				p.setType(Constants.COMPANYNATURE);
				p.setValue(job.getCompany().getNature());
				for (Parameter pa : plist) {
					if (pa.getType().equals(p.getType())
							&& pa.getValue().equals(p.getValue())) {
						p = pa;
						break;
					}
				}
				job.getCompany().setNature(p.getName());
			}
		}
		return job;
	}

	/**
	 * 将job数组处理成为适合前台显示用的对象
	 * 
	 * @param jobList
	 * @return
	 */
	public List<Job> getForShowJob(List<Job> jobList) {
		if (jobList == null) {
			return null;
		}
		// 获得所有参数
		List<Parameter> plist = pDao.getByPage(null);
		for (Job job : jobList) {
			// // 创建时间
			// if (job.getCreateDate() != null &&
			// !"".equals(job.getCreateDate())) {
			// job.setCreateDate(dateForShow(job.getCreateDate()));
			// }
			// // 更新时间
			// if (job.getUpdateDate() != null) {
			// job.setUpdateDate(dateForShow(job.getUpdateDate()));
			// }
			// 薪水
			if (job.getSalary() != null && !"".equals(job.getSalary())) {
				Parameter p = new Parameter();
				p.setType(Constants.SALARY);
				p.setValue(job.getSalary());
				for (Parameter pa : plist) {
					if (pa.getType().equals(p.getType())
							&& pa.getValue().equals(p.getValue())) {
						p = pa;
						break;
					}
				}
				job.setSalary(p.getName());
			}
			// 学历
			if (job.getEducation() != null && !"".equals(job.getEducation())) {
				Parameter p = new Parameter();
				p.setType(Constants.EDUCATION);
				p.setValue(job.getEducation());
				for (Parameter pa : plist) {
					if (pa.getType().equals(p.getType())
							&& pa.getValue().equals(p.getValue())) {
						p = pa;
						break;
					}
				}
				job.setEducation(p.getName());
			}
			// 工作经验年限
			if (job.getExperience() != null && !"".equals(job.getExperience())) {
				Parameter p = new Parameter();
				p.setType(Constants.EXPERIENCE);
				p.setValue(job.getExperience());
				for (Parameter pa : plist) {
					if (pa.getType().equals(p.getType())
							&& pa.getValue().equals(p.getValue())) {
						p = pa;
						break;
					}
				}
				job.setExperience(p.getName());
			}
			// 性别
			if (job.getGender() != null && !"".equals(job.getGender())) {
				Parameter p = new Parameter();
				p.setType(Constants.GENDER);
				p.setValue(job.getGender());
				for (Parameter pa : plist) {
					if (pa.getType().equals(p.getType())
							&& pa.getValue().equals(p.getValue())) {
						p = pa;
						break;
					}
				}
				job.setGender(p.getName());
			}
			// 岗位性质
			if (job.getNature() != null && !"".equals(job.getNature())) {
				Parameter p = new Parameter();
				p.setType(Constants.JOBNATURE);
				p.setValue(job.getNature());
				for (Parameter pa : plist) {
					if (pa.getType().equals(p.getType())
							&& pa.getValue().equals(p.getValue())) {
						p = pa;
						break;
					}
				}
				job.setNature(p.getName());
			}
			// 岗位有效期
			if (job.getEffectiveTime() != null) {
				Date today = new Date();
				int cha = job.getEffectiveTime().compareTo(today); // 今天距离
																	// 有效期截止日期
																	// 剩余的天数
				job.setEffectiveDays(cha);
			}
			//岗位类别
			if(job.getJobCategory()!=null){
				if(job.getJobCategory().getCode()!= null && !"".equals(job.getJobCategory().getCode())){
					JobCategory jobCategory = jcDao.getByCode(job.getJobCategory().getCode());
					job.setJobCategory(jobCategory);
				}
			}
			// 工作地
			if (job.getWorkPlace() != null) {
				if(job.getWorkPlace().getCode()!= null){
					Area workPlace = aDao.getByCode(job.getWorkPlace().getCode());
					job.setWorkPlace(workPlace);
				}
			}
			// 审核状态
			if (job.getCheckStatus() != null
					&& !"".equals(job.getCheckStatus())) {
				Parameter p = new Parameter();
				p.setType(Constants.CHECKSTATUS);
				p.setValue(job.getCheckStatus());
				for (Parameter pa : plist) {
					if (pa.getType().equals(p.getType())
							&& pa.getValue().equals(p.getValue())) {
						p = pa;
						break;
					}
				}
				job.setCheckStatus(p.getName());
			}
			// 公司性质
			if (job.getCompany() != null) {
				if (job.getCompany().getNature() != null
						&& !"".equals(job.getCompany().getNature())) {
					Parameter p = new Parameter();
					p.setType(Constants.COMPANYNATURE);
					p.setValue(job.getCompany().getNature());
					for (Parameter pa : plist) {
						if (pa.getType().equals(p.getType())
								&& pa.getValue().equals(p.getValue())) {
							p = pa;
							break;
						}
					}
					job.getCompany().setNature(p.getName());
				}
			}
		}
		return jobList;
	}

	/**
	 * 将company处理成为适合前台显示用的对象
	 * 
	 * @param company
	 * @return
	 */
	public Company getForShow(Company company) {
		if (company == null) {
			return null;
		}
		// 获得所有参数
		List<Parameter> plist = pDao.getByPage(null);

		// // 创建时间
		// if (company.getCreateDate() != null
		// && !"".equals(company.getCreateDate())) {
		// company.setCreateDate(dateForShow(company.getCreateDate()));
		// }
		// // 更新时间
		// if (company.getUpdateDate() != null) {
		// company.setUpdateDate(dateForShow(company.getUpdateDate()));
		// }
		// 企业规模
		if (company.getScale() != null && !"".equals(company.getScale())) {
			Parameter p = new Parameter();
			p.setType(Constants.SCALE);
			p.setValue(company.getScale());
			for (Parameter pa : plist) {
				if (pa.getType().equals(p.getType())
						&& pa.getValue().equals(p.getValue())) {
					p = pa;
					break;
				}
			}
			company.setScale(p.getName());
		}
		// 企业性质
		if (company.getNature() != null && !"".equals(company.getNature())) {
			Parameter p = new Parameter();
			p.setType(Constants.COMPANYNATURE);
			p.setValue(company.getNature());
			for (Parameter pa : plist) {
				if (pa.getType().equals(p.getType())
						&& pa.getValue().equals(p.getValue())) {
					p = pa;
					break;
				}
			}
			company.setNature(p.getName());
		}
		// 经济类型
		if (company.getEconomyType() != null
				&& !"".equals(company.getEconomyType())) {
			Parameter p = new Parameter();
			p.setType(Constants.ECONOMYTYPE);
			p.setValue(company.getEconomyType());
			for (Parameter pa : plist) {
				if (pa.getType().equals(p.getType())
						&& pa.getValue().equals(p.getValue())) {
					p = pa;
					break;
				}
			}
			company.setEconomyType(p.getName());
		}
		return company;
	}

	/**
	 * 将company数组处理成为适合前台显示用的对象
	 * 
	 * @param companyList
	 * @return
	 */
	public List<Company> getForShowCompany(List<Company> companyList) {
		if (companyList == null) {
			return null;
		}
		// 获得所有参数
		List<Parameter> plist = pDao.getByPage(null);
		for (Company company : companyList) {
			// // 创建时间
			// if (company.getCreateDate() != null
			// && !"".equals(company.getCreateDate())) {
			// company.setCreateDate(dateForShow(company.getCreateDate()));
			// }
			// // 更新时间
			// if (company.getUpdateDate() != null) {
			// company.setUpdateDate(dateForShow(company.getUpdateDate()));
			// }
			// 企业规模
			if (company.getScale() != null && !"".equals(company.getScale())) {
				Parameter p = new Parameter();
				p.setType(Constants.SCALE);
				p.setValue(company.getScale());
				for (Parameter pa : plist) {
					if (pa.getType().equals(p.getType())
							&& pa.getValue().equals(p.getValue())) {
						p = pa;
						break;
					}
				}
				company.setScale(p.getName());
			}
			// 企业性质
			if (company.getNature() != null && !"".equals(company.getNature())) {
				Parameter p = new Parameter();
				p.setType(Constants.COMPANYNATURE);
				p.setValue(company.getNature());
				for (Parameter pa : plist) {
					if (pa.getType().equals(p.getType())
							&& pa.getValue().equals(p.getValue())) {
						p = pa;
						break;
					}
				}
				company.setNature(p.getName());
			}
			// 经济类型
			if (company.getEconomyType() != null
					&& !"".equals(company.getEconomyType())) {
				Parameter p = new Parameter();
				p.setType(Constants.ECONOMYTYPE);
				p.setValue(company.getEconomyType());
				for (Parameter pa : plist) {
					if (pa.getType().equals(p.getType())
							&& pa.getValue().equals(p.getValue())) {
						p = pa;
						break;
					}
				}
				company.setEconomyType(p.getName());
			}
		}
		return companyList;
	}

	/**
	 * 将record投递记录表处理成为适合前台显示用的对象
	 * 
	 * @param record
	 * @return
	 */
	public Record getForShow(Record record) {
		if (record == null) {
			return null;
		}
		// 获得所有参数
		List<Parameter> plist = pDao.getByPage(null);

		// // 创建时间
		// if (record.getCreateDate() != null
		// && !"".equals(record.getCreateDate())) {
		// record.setCreateDate(dateForShow(record.getCreateDate()));
		// }
		// 性别
		if (record.getrGender() != null && !"".equals(record.getrGender())) {
			Parameter p = new Parameter();
			p.setType(Constants.GENDER);
			p.setValue(record.getrGender());
			for (Parameter pa : plist) {
				if (pa.getType().equals(p.getType())
						&& pa.getValue().equals(p.getValue())) {
					p = pa;
					break;
				}
			}
			record.setrGender(p.getName());
		}
		// 残疾类别
		if (record.getrDisabilityCategory() != null
				&& !"".equals(record.getrDisabilityCategory())) {
			Parameter p = new Parameter();
			p.setType(Constants.DISABILITYCATEGORY);
			p.setValue(record.getrDisabilityCategory());
			for (Parameter pa : plist) {
				if (pa.getType().equals(p.getType())
						&& pa.getValue().equals(p.getValue())) {
					p = pa;
					break;
				}
			}
			record.setrDisabilityCategory(p.getName());
		}

		// 学历
		if (record.getrEducation() != null
				&& !"".equals(record.getrEducation())) {
			Parameter p = new Parameter();
			p.setType(Constants.EDUCATION);
			p.setValue(record.getrEducation());
			for (Parameter pa : plist) {
				if (pa.getType().equals(p.getType())
						&& pa.getValue().equals(p.getValue())) {
					p = pa;
					break;
				}
			}
			record.setrEducation(p.getName());
		}
		// 职位薪水
		if (record.getjSalary() != null && !"".equals(record.getjSalary())) {
			Parameter p = new Parameter();
			p.setType(Constants.SALARY);
			p.setValue(record.getjSalary());
			for (Parameter pa : plist) {
				if (pa.getType().equals(p.getType())
						&& pa.getValue().equals(p.getValue())) {
					p = pa;
					break;
				}
			}
			record.setjSalary(p.getName());
		}
		// 岗位性质
		if (record.getjNature() != null && !"".equals(record.getjNature())) {
			Parameter p = new Parameter();
			p.setType(Constants.JOBNATURE);
			p.setValue(record.getjNature());
			for (Parameter pa : plist) {
				if (pa.getType().equals(p.getType())
						&& pa.getValue().equals(p.getValue())) {
					p = pa;
					break;
				}
			}
			record.setjNature(p.getName());
		}
		return record;
	}

	/**
	 * 将record数组投递记录表处理成为适合前台显示用的对象
	 * 
	 * @param recordList
	 * @return
	 */
	public List<Record> getForShowRecord(List<Record> recordList) {
		if (recordList == null) {
			return null;
		}
		// 获得所有参数
		List<Parameter> plist = pDao.getByPage(null);
		for (Record record : recordList) {
			// // 创建时间
			// if (record.getCreateDate() != null
			// && !"".equals(record.getCreateDate())) {
			// record.setCreateDate(dateForShow(record.getCreateDate()));
			// }
			// 性别
			if (record.getrGender() != null && !"".equals(record.getrGender())) {
				Parameter p = new Parameter();
				p.setType(Constants.GENDER);
				p.setValue(record.getrGender());
				for (Parameter pa : plist) {
					if (pa.getType().equals(p.getType())
							&& pa.getValue().equals(p.getValue())) {
						p = pa;
						break;
					}
				}
				record.setrGender(p.getName());
			}
			// 残疾类别
			if (record.getrDisabilityCategory() != null
					&& !"".equals(record.getrDisabilityCategory())) {
				Parameter p = new Parameter();
				p.setType(Constants.DISABILITYCATEGORY);
				p.setValue(record.getrDisabilityCategory());
				for (Parameter pa : plist) {
					if (pa.getType().equals(p.getType())
							&& pa.getValue().equals(p.getValue())) {
						p = pa;
						break;
					}
				}
				record.setrDisabilityCategory(p.getName());
			}

			// 学历
			if (record.getrEducation() != null
					&& !"".equals(record.getrEducation())) {
				Parameter p = new Parameter();
				p.setType(Constants.EDUCATION);
				p.setValue(record.getrEducation());
				for (Parameter pa : plist) {
					if (pa.getType().equals(p.getType())
							&& pa.getValue().equals(p.getValue())) {
						p = pa;
						break;
					}
				}
				record.setrEducation(p.getName());
			}
			// 职位薪水
			if (record.getjSalary() != null && !"".equals(record.getjSalary())) {
				Parameter p = new Parameter();
				p.setType(Constants.SALARY);
				p.setValue(record.getjSalary());
				for (Parameter pa : plist) {
					if (pa.getType().equals(p.getType())
							&& pa.getValue().equals(p.getValue())) {
						p = pa;
						break;
					}
				}
				record.setjSalary(p.getName());
			}
			// 岗位性质
			if (record.getjNature() != null && !"".equals(record.getjNature())) {
				Parameter p = new Parameter();
				p.setType(Constants.JOBNATURE);
				p.setValue(record.getjNature());
				for (Parameter pa : plist) {
					if (pa.getType().equals(p.getType())
							&& pa.getValue().equals(p.getValue())) {
						p = pa;
						break;
					}
				}
				record.setjNature(p.getName());
			}
		}
		return recordList;
	}

	/**
	 * 将news消息处理成为适合前台显示用的对象
	 * 
	 * @param news
	 * @return
	 */
	public News getForShow(News news) {
		if (news == null) {
			return null;
		}
		// // 创建时间
		// if (news.getCreateDate() != null && !"".equals(news.getCreateDate()))
		// {
		// news.setCreateDate(dateForShow(news.getCreateDate()));
		// }
		return news;
	}

	/**
	 * 将news数组投递记录表处理成为适合前台显示用的对象
	 * 
	 * @param newsList
	 * @return
	 */
	public List<News> getForShowNews(List<News> newsList) {
		if (newsList == null) {
			return null;
		}
		// for (News news : newsList) {
		// // 创建时间
		// if (news.getCreateDate() != null
		// && !"".equals(news.getCreateDate())) {
		// news.setCreateDate(dateForShow(news.getCreateDate()));
		// }
		// }
		return newsList;
	}

}
