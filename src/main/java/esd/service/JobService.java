package esd.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import esd.bean.Area;
import esd.bean.Company;
import esd.bean.Job;
import esd.bean.JobCategory;
import esd.bean.Parameter;
import esd.controller.Constants;
import esd.dao.AreaDao;
import esd.dao.JobCategoryDao;
import esd.dao.JobDao;
import esd.dao.ParameterDao;

/**
 * 职位操作类
 * 
 * @author Administrator
 * 
 */
@Service
public class JobService {
	private static Logger log = Logger.getLogger(JobService.class);
	@Autowired
	private JobDao dao;

	@Autowired
	private KitService kitService;

	@Autowired
	private ParameterService parameterService;

	@Autowired
	private ParameterDao parameterDao;

	@Autowired
	private AreaDao areaDao;

	@Autowired
	private JobCategoryDao jcDao;

	// 保存一个对象
	public boolean save(Job job) {
		boolean bl = parameterService.getSwitchStatus(
				Constants.Switch.JOB.toString(), job.getArea().getCode());
		if (job != null) {
			// 如果user审核开关打开的话, 则将user设置为 待审核 状态
			if (bl) {
				job.setCheckStatus(Constants.CheckStatus.DAISHEN.toString());
			} else {
				// //如果user审核开关没有打开的话, 则将user设置为 ok 状态
				job.setCheckStatus(Constants.CheckStatus.OK.toString());
			}
		}
		return dao.save(job);
	}

	// 删除一个对象
	public boolean delete(Integer id) {
		return dao.delete(id);
	}

	// 根据公司id 删除对应招聘职位
	public Boolean deleteByCompany(Integer cid) {
		Integer result = dao.deleteByCompany(cid);
		return result >= 0 ? true : false;
	}

	// 更新一个对象
	public boolean update(Job job) {
		// 先获得updateCheck
		Integer updateCheck = dao.getUpdateCheck(job.getId());
		job.setUpdateCheck(updateCheck);
		return dao.update(job);
	}

	// 按id查询一个对象,用作编辑处理
	public Job getById(int id) {
		log.info("id in service = " + id);
		Job job = (Job) dao.getById(id);
		// 工作地
		if (job.getWorkPlace() != null) {
			if (job.getWorkPlace().getCode() != null) {
				Area workPlace = areaDao
						.getByCode(job.getWorkPlace().getCode());
				job.setWorkPlace(workPlace);
			}
		}
		// 工作类别
		if (job.getJobCategory() != null) {
			if (job.getJobCategory().getCode() != null
					&& !"".equals(job.getJobCategory().getCode())) {
				JobCategory jobCategory = jcDao.getByCode(job.getJobCategory()
						.getCode());
				job.setJobCategory(jobCategory);
			}
		}
		return job;
	}

	// 按id查询一个对象, 用做前台展示
	public Job getOneForShow(int id) {
		Job job = (Job) dao.getById(id);
		// 处理为适合前台显示的字段数据
		job = kitService.getForShow(job);
		return job;
	}

	/**
	 * 分页查询方法, 其中的数据没有做处理 管理后台/常用情况--使用
	 * 
	 * @param object
	 * @param startPage
	 * @param size
	 * @return
	 */
	public List<Job> getListForManage(Job object, Integer startPage, Integer size) {
		if (object != null) {
			// 职位种类code处理
			if (object.getJobCategory() != null) {
				if (object.getJobCategory().getCode() != null) {
					object.setJobCategory(new JobCategory(KitService
							.jobCategoryCodeForJobSql(object.getJobCategory()
									.getCode())));
				}
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("job", object);
		map.put("start", startPage <= 0 ? Constants.START : (startPage - 1)
				* (size <= 0 ? Constants.SIZE : size));
		map.put("size", size <= 0 ? Constants.SIZE : size);
		List<Job> list = dao.getByPage(map);
		// 处理为适合前台显示的字段数据
		list = kitService.getForShowJob(list);
		log.info("jobList.size() = " + list.size());
		return list;
	}

	/**
	 * 分页查询方法, 其中数据已被处理成适合前台展示的形式 前台--使用
	 * 
	 * @param object
	 * @param startPage
	 * @param size
	 * @param shareScope
	 *             --是否开启共享范围查询, true-前台查询时使用; false-管理后台查询时使用
	 * @return
	 */
	public List<Job> getListForShow(Job object, Integer startPage, Integer size,
			Boolean shareScope) {
		if (object != null) {
			// 将地区code转化为适合sql语句的形式, 其中包括先查询一下该地区的就业信息共享范围
			if(shareScope){
				if (object.getArea() != null) {
					if (object.getArea().getCode() != null
							&& !"".equals(object.getArea().getCode())) {
						Parameter parameter = parameterDao
								.getShareScopeByArea(object.getArea().getCode());
						if (parameter != null) {
							String areaSql = KitService.getAreaSqlFromShareScope(
									parameter.getValue(), object.getArea()
											.getCode());
							object.setArea(new Area(areaSql));
						}
					}
				}
			}
			// 职位种类code处理
			if (object.getJobCategory() != null) {
				if (object.getJobCategory().getCode() != null) {
					object.setJobCategory(new JobCategory(KitService
							.jobCategoryCodeForJobSql(object.getJobCategory()
									.getCode())));
				}
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("job", object);
		map.put("start", startPage <= 0 ? Constants.START : (startPage - 1)
				* (size <= 0 ? Constants.SIZE : size));
		map.put("size", size <= 0 ? Constants.SIZE : size);
		List<Job> list = dao.getByPage(map);
		// 处理为适合前台显示的字段数据
		list = kitService.getForShowJob(list);
		log.info("jobList.size() = " + list.size());
		return list;
	}

	/**
	 * 得到最新的N个职位
	 * 
	 * @param acode
	 * @param size
	 * @return
	 */
	public List<Job> getByNew(String acode, Integer size) {
		Job job = new Job();
		// 将地区code转化为适合sql语句的形式, 其中包括先查询一下该地区的就业信息共享范围; 如不存在则使用默认传递进来的地区code
		if (acode != null) {
			Parameter parameter = parameterDao.getShareScopeByArea(acode);
			if (parameter != null) {
				String areaSql = KitService.getAreaSqlFromShareScope(
						parameter.getValue(), acode);
				job.setArea(new Area(areaSql));
			}else{
				job.setArea(new Area(acode));
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("job", job);
		map.put("start", Constants.START);
		map.put("size", size <= 0 ? Constants.SIZE : size);
		List<Job> list = dao.getByPage(map);
		// 处理为适合前台显示的字段数据
		list = kitService.getForShowJob(list);
		return list;
	}

	/**
	 * 获得数据总条数
	 * 
	 * @param object
	 *            --带有各种查询属性的对象
	 * @param shareScope
	 *            --是否开启共享范围查询, true-前台查询时使用; false-管理后台查询时使用
	 * @return
	 */
	public int getTotalCount(Job object, Boolean shareScope) {
		if (object != null) {
			if (shareScope) {
				// 将地区code转化为适合sql语句的形式, 其中包括先查询一下该地区的就业信息共享范围
				if (object.getArea().getCode() != null
						&& !"".equals(object.getArea().getCode())) {
					Parameter parameter = parameterDao
							.getShareScopeByArea(object.getArea().getCode());
					if (parameter != null) {
						String areaSql = KitService.getAreaSqlFromShareScope(
								parameter.getValue(), object.getArea()
										.getCode());
						object.setArea(new Area(areaSql));
					}
				}
			}
			// 职位种类code处理
			if (object.getJobCategory() != null) {
				if (object.getJobCategory().getCode() != null) {
					object.setJobCategory(new JobCategory(KitService
							.jobCategoryCodeForJobSql(object.getJobCategory()
									.getCode())));
				}
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("job", object);
		return dao.getTotalCount(map);
	}

	/**
	 *  根据公司id, 得到他所发布的职位列表--用作前台显示
	 * @param cid
	 * @param startPage
	 * @param size
	 * @return
	 */
	public List<Job> getByCompanyForShow(Integer cid, Integer startPage, Integer size) {
		Job jj = new Job();
		jj.setCompany(new Company(cid));
		// 只显示审核通过的
		jj.setCheckStatus(Constants.CheckStatus.OK.toString());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("job", jj);
		map.put("start", startPage <= 0 ? Constants.START : (startPage - 1)
				* (size <= 0 ? Constants.SIZE : size));
		map.put("size", size <= 0 ? Constants.SIZE : size);
		List<Job> list = dao.getByPage(map);
		// 处理为适合前台显示的字段数据
		list = kitService.getForShowJob(list);
		return list;
	}

	/**
	 *  根据公司id, 得到他所发布的职位列表--用作编辑处理
	 * @param cid
	 * @param startPage
	 * @param size
	 * @return
	 */
	public List<Job> getByCompany(Integer cid, Integer startPage, Integer size) {
		Map<String, Object> map = new HashMap<String, Object>();
		Job object = new Job();
		map.put("job", object);
		map.put("start", startPage <= 0 ? Constants.START : (startPage - 1)
				* (size <= 0 ? Constants.SIZE : size));
		map.put("size", size <= 0 ? Constants.SIZE : size);
		List<Job> list = dao.getByPage(map);
		list = kitService.getForShowJob(list);
		return list;
	}
	
	/**
	 *  根据公司id, 得到他所发布的所有职位数量
	 * @param cid
	 * @return
	 */
	public Integer getByCompanyCount(int cid) {
		Map<String, Object> map = new HashMap<String, Object>();
		Job object = new Job();
		object.setCompany(new Company(cid));
		map.put("job", object);
		return dao.getTotalCount(map);

	}

	/**
	 *  根据公司id, 得到他所发布的已审核通过的职位列表--用作编辑处理
	 * @param cid
	 * @param startPage
	 * @param size
	 * @return
	 */
	public List<Job> getByCompanyMate(int cid, Integer startPage, Integer size) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cid", cid);
		map.put("start", startPage <= 0 ? Constants.START : (startPage - 1)
				* (size <= 0 ? Constants.SIZE : size));
		map.put("size", size <= 0 ? Constants.SIZE : size);
		List<Job> list = dao.getByCompany(map);
		list = kitService.getForShowJob(list);
		return list;
	}
	
	/**
	 * 根据条件对象, 查询符合推送要求的职位信息
	 * 
	 * @param object
	 * @param startPage
	 * @param size
	 * @return
	 */
	public List<Job> getTuiSongList(Job object, Integer startPage, Integer size) {
		if (object != null) {
			//工作地区code处理
			if(object.getWorkPlace()!=null){
				if(object.getWorkPlace().getCode()!=null){
					object.setWorkPlace(new Area(KitService.workPlaceForJobSql(object.getWorkPlace().getCode())));
				}
			}
			// 职位种类code处理
			if (object.getJobCategory() != null) {
				if (object.getJobCategory().getCode() != null) {
					object.setJobCategory(new JobCategory(KitService
							.jobCategoryCodeForJobSql(object.getJobCategory()
									.getCode())));
				}
			}
			
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("job", object);
		map.put("start", startPage <= 0 ? Constants.START : (startPage - 1)
				* (size <= 0 ? Constants.SIZE : size));
		map.put("size", size <= 0 ? Constants.SIZE : size);
		List<Job> list = dao.getJobMate(map);
		// 处理为适合前台显示的字段数据
		list = kitService.getForShowJob(list);
		log.info("jobList.size() = " + list.size());
		return list;
	}
}
