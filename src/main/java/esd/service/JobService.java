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
import esd.controller.Constants;
import esd.dao.JobDao;

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
	private ParameterService pService;

	// 保存一个对象
	public boolean save(Job job) {
		boolean bl = pService.getSwitchStatus(Constants.Switch.JOB.toString(),job.getArea().getCode());
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
	public boolean delete(int id) {
		return dao.delete(id);
	}

	// 更新一个对象
	public boolean update(Job job) {
		Integer updateCheck = dao.getUpdateCheck(job.getId());
		job.setUpdateCheck(updateCheck);
		return dao.update(job);
	}

	// 按id查询一个对象,用作编辑处理
	public Job getById(int id) {
		System.out.println("id in service = " + id);
		return (Job) dao.getById(id);
	}

	// 按id查询一个对象, 用做前台展示
	public Job getOneForShow(int id) {
		Job job = (Job) dao.getById(id);
		// 处理为适合前台显示的字段数据
		job = kitService.getForShow(job);
		return job;
	}

	// 分页查询方法,--标准分页方法
	// @param map中为具体的参数
	// 1-类对象, 名称为对应类的小写!!切记切记!! 字段的值即为查询条件; 2-start: 起始索引; 3-size: 返回条数
	public List<Job> getByPage(Job job, int startPage, int size) {
		if (job != null) {
			// 地区code处理
			if (job.getArea() != null) {
				if (job.getArea() != null) {
					job.setArea(new Area(KitService.areaCodeForSql(job
							.getArea().getCode())));
				}
			}
			// 职位种类code处理
			if (job.getJobCategory() != null) {
				if (job.getJobCategory().getCode() != null) {
					job.setJobCategory(new JobCategory(KitService
							.jobCategoryCodeForJobSql(job.getJobCategory()
									.getCode())));
				}
			}
			//如果未设定是否过滤掉过期的招聘信息, 则默认过滤掉
			if(job.getIsActiveEffectiveTime() == null){
				job.setIsActiveEffectiveTime(Boolean.TRUE);
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("job", job);
		map.put("start", startPage <= 0 ? Constants.START : (startPage - 1)
				* (size <= 0 ? Constants.SIZE : size));
		map.put("size", size <= 0 ? Constants.SIZE : size);
		List<Job> list = dao.getByPage(map);
		log.info("jobList.size() = " + list.size());
		return list;
	}

	// 后台审核用方法
	// 分页查询方法,其中数据已被处理成适合前台展示的
	public List<Job> getListShowForManage(Job job, int startPage, int size) {
		if (job != null) {
			// 地区code处理
			if (job.getArea() != null) {
				if (job.getArea() != null) {
					job.setArea(new Area(KitService.areaCodeForSql(job
							.getArea().getCode())));
				}
			}
			// 职位种类code处理
			if (job.getJobCategory() != null) {
				if (job.getJobCategory().getCode() != null) {
					job.setJobCategory(new JobCategory(KitService
							.jobCategoryCodeForJobSql(job.getJobCategory()
									.getCode())));
				}
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("job", job);
		map.put("start", startPage <= 0 ? Constants.START : (startPage - 1)
				* (size <= 0 ? Constants.SIZE : size));
		map.put("size", size <= 0 ? Constants.SIZE : size);
		List<Job> list = dao.getByPage(map);
		// 处理为适合前台显示的字段数据
		list = kitService.getForShowJob(list);
		log.info("jobList.size() = " + list.size());
		return list;
	}

	// 分页查询方法,其中数据已被处理成适合前台展示的
	public List<Job> getForListShow(Job job, int startPage, int size) {
		if (job != null) {
			// 地区code处理
			if (job.getArea() != null) {
				if (job.getArea() != null) {
					job.setArea(new Area(KitService.areaCodeForSql(job
							.getArea().getCode())));
				}
			}
			// 职位种类code处理
			if (job.getJobCategory() != null) {
				if (job.getJobCategory().getCode() != null) {
					job.setJobCategory(new JobCategory(KitService
							.jobCategoryCodeForJobSql(job.getJobCategory()
									.getCode())));
				}
			}
			// 只显示审核通过的
			job.setCheckStatus(Constants.CheckStatus.OK.toString());
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("job", job);
		map.put("start", startPage <= 0 ? Constants.START : (startPage - 1)
				* (size <= 0 ? Constants.SIZE : size));
		map.put("size", size <= 0 ? Constants.SIZE : size);
		List<Job> list = dao.getByPage(map);
		// 处理为适合前台显示的字段数据
		list = kitService.getForShowJob(list);
		log.info("jobList.size() = " + list.size());
		return list;
	}

	// 得到最新的N个职位
	public List<Job> getByNew(String acode, int size) {
		if (size <= 0) {
			size = Constants.SIZE;
		}
		// 处理传进来的地区code, 变成适用于sql语句使用的格式
		if (acode != null) {
			acode = KitService.areaCodeForSql(acode);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		Job job = new Job();
		job.setArea(new Area(acode));
		// 只显示审核通过的
		job.setCheckStatus(Constants.CheckStatus.OK.toString());
		map.put("job", job);
		map.put("start", Constants.START);
		map.put("size", size);
		List<Job> list = dao.getByPage(map);
		// 处理为适合前台显示的字段数据
		list = kitService.getForShowJob(list);
		return list;
	}

	// 获得数据总条数
	public int getTotalCount(Job job) {
		if (job != null) {
			// 地区code处理
			if (job.getArea() != null) {
				if (job.getArea() != null) {
					job.setArea(new Area(KitService.areaCodeForSql(job
							.getArea().getCode())));
				}
			}
			// 职位种类code处理
			if (job.getJobCategory() != null) {
				if (job.getJobCategory().getCode() != null) {
					job.setJobCategory(new JobCategory(KitService
							.jobCategoryCodeForJobSql(job.getJobCategory()
									.getCode())));
				}
			}
			// 只显示审核通过的
			job.setCheckStatus(Constants.CheckStatus.OK.toString());
		}
		if (job == null) {
			job = new Job();
			job.setCheckStatus(Constants.CheckStatus.OK.toString());
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("job", job);
		return dao.getTotalCount(map);
	}

	// 根据公司id, 得到他所发布的职位列表--用作编辑处理
	public List<Job> getByCompany(int cid, int startPage, int size) {
		Job jj = new Job();
		jj.setCompany(new Company(cid));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("job", jj);
		map.put("start", startPage <= 0 ? Constants.START : (startPage - 1)
				* (size <= 0 ? Constants.SIZE : size));
		map.put("size", size <= 0 ? Constants.SIZE : size);
		List<Job> list = dao.getByPage(map);
		for (Job job : list) {
			job = kitService.getForShow(job);
		}
		return list;
	}

	// 根据公司id, 得到他所发布的职位列表--用作前台显示
	public List<Job> getByCompanyForShow(int cid, int startPage, int size) {
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

	// 根据公司id, 得到他所发布的所有职位数量
	public int getByCompanyCount(int cid) {
		Job jj = new Job();
		jj.setCompany(new Company(cid));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("job", jj);
		return dao.getTotalCount(map);

	}

}
