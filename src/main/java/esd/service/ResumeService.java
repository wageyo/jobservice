package esd.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import esd.bean.Area;
import esd.bean.JobCategory;
import esd.bean.Parameter;
import esd.bean.Resume;
import esd.bean.UnempManage;
import esd.bean.User;
import esd.bean.WorkExperience;
import esd.controller.Constants;
import esd.dao.AreaDao;
import esd.dao.JobCategoryDao;
import esd.dao.ParameterDao;
import esd.dao.ResumeDao;
import esd.dao.UnempManageDao;
import esd.dao.WorkExperienceDao;

/**
 * 简历操作类
 * 
 * @author Administrator
 * 
 */
@Service
public class ResumeService {
	private static Logger log = Logger.getLogger(ResumeService.class);

	@Autowired
	private ResumeDao dao;

	// @Autowired
	// private EducationBackgroundDao eDao;
	//
	// @Autowired
	// private FamilyMemberDao fDao;

	@Autowired
	private WorkExperienceDao wDao;

	@Autowired
	private UnempManageDao umDao;

	@Autowired
	private PersonService personService;

	@Autowired
	private KitService kitService;

	@Autowired
	private ParameterService pService;

	@Autowired
	private ParameterDao parameterDao;

	@Autowired
	private JobCategoryDao jcDao;

	@Autowired
	private AreaDao aDao;

	// 保存一个对象
	public boolean save(Resume resume) {
		if (resume != null) {
			// 检查个人用户是否已经有默认选中作为投递的简历了,
			// 没有的话, 则将该简历设为默认选中投递的简历
			Resume tempResume = personService.getDefaultResume(resume.getUser()
					.getId());
			if (tempResume == null) {
				resume.setIsDefault(Boolean.TRUE);
			}

			// 有出生日期得到年龄
			if (resume.getBirth() != null && !"".equals(resume.getBirth())) {
				resume.setAge(KitService.getAgeByBirth(resume.getBirth()));
			}

			// 审核处理
			boolean bl = pService.getSwitchStatus(Constants.Switch.RESUME
					.toString(), resume.getArea().getCode());
			// 如果resume审核开关打开的话, 则将resume设置为 待审核 状态
			if (bl) {
				resume.setCheckStatus(Constants.CheckStatus.DAISHEN.toString());
			} else {
				// 如果user审核开关没有打开的话, 则将user设置为 ok 状态
				resume.setCheckStatus(Constants.CheckStatus.OK.toString());
			}
		}
		return dao.save(resume);
	}

	// 删除一个对象
	public boolean delete(int id) {
		return dao.delete(id);
	}

	// 根据账号, 删除附属的简历
	public boolean deleteByUser(Integer uid) {
		Integer result = dao.deleteByUser(uid);
		return result >= 0 ? true : false;
	}

	// 更新一个对象
	public boolean update(Resume resume) {
		if (resume != null) {
			// 重新计算年龄
			if (resume.getBirth() != null && !"".equals(resume.getBirth())) {
				resume.setAge(KitService.getAgeByBirth(resume.getBirth()));
			}
		}
		resume.setUpdateCheck(dao.getUpdateCheck(resume.getId()));
		return dao.update(resume);
	}

	// 按id查询一个对象,用作编辑处理
	public Resume getById(int id) {
		Resume resume = (Resume) dao.getById(id);
		// 户籍所在地
		if (resume.getHukou() != null) {
			if (resume.getHukou().getCode() != null
					&& !"".equals(resume.getHukou().getCode())) {
				Area hukou = aDao.getByCode(resume.getHukou().getCode());
				resume.setHukou(hukou);
			}
		}
		// 期望工作地
		if (resume.getDesireAddress() != null) {
			if (resume.getDesireAddress().getCode() != null
					&& !"".equals(resume.getDesireAddress().getCode())) {
				Area desireAddress = aDao.getByCode(resume.getDesireAddress()
						.getCode());
				resume.setDesireAddress(desireAddress);
			}
		}
		// 目标职位种类code处理
		if (resume.getDesireJob() != null) {
			if (resume.getDesireJob().getCode() != null
					&& !"".equals(resume.getDesireJob().getCode())) {
				JobCategory desireJob = jcDao.getByCode(resume.getDesireJob()
						.getCode());
				resume.setDesireJob(desireJob);
			}
		}
		// 取得工作经历
		resume.setWorkExperienceList(wDao.getByResume(id));
		// 取得职业测评情况
		resume.setUnempManageList(umDao.getByResume(id));
		return resume;
	}

	// 按id查询一个对象, 用做前台展示
	public Resume getOneForShow(int id) {
		Resume r = (Resume) dao.getById(id);
		r = kitService.getForShow(r);
		// 取得工作经历
		r.setWorkExperienceList(wDao.getByResume(r.getId()));
		// 处理为适合前台显示的字段数据
		return r;
	}

	// 按id查询一个对象, 提供给下载
	public Resume getOneForDownLoad(int id) {
		Resume r = (Resume) dao.getById(id);
		// 处理为适合前台显示的字段数据
		r = kitService.getForShow(r);
		// 取得工作经历
		List<WorkExperience> list = wDao.getByResume(id);
		for (int i = 0; i < list.size(); i++) {
			r.getWorkExperienceList().add(list.get(i));
			if (i >= 2) {
				break;
			}
		}
		return r;
	}

	// 根据个人用户的user id, 得到他默认选中的简历
	public Resume getDefaultResume(int userid) {
		Resume r = new Resume();
		r.setIsDefault(Boolean.TRUE);
		r.setUser(new User(userid));
		List<Resume> list = dao.getByObj(r);
		log.info("getDefaultResume list.size() : " + list.size());
		if (list != null) {
			if (list.size() == 1) {
				r = list.get(0);
				return r;
			}
		}
		return null;
	}

	/**
	 * 分页查询方法, 其中的数据没有做处理 管理后台/常用情况--使用
	 * 
	 * @param resume
	 * @param startPage
	 * @param size
	 * @return
	 */
	public List<Resume> getListShowForManage(Resume resume, int startPage,
			int size) {
		if (resume != null) {
			// 期望工作地  处理成适合sql查询的方式
			if (resume.getDesireAddress() != null) {
				if (resume.getDesireAddress().getCode() != null
						&& !"".equals(resume.getDesireAddress().getCode())) {
					String sqlDesireAddress = KitService.desireAddressForResumeSql(resume
							.getDesireAddress().getCode());
					resume.setDesireAddress(new Area(sqlDesireAddress));
				}

			}
			// 目标职位  处理成适合sql查询的方式
			if (resume.getDesireJob() != null) {
				if (resume.getDesireJob().getCode() != null
						&& !"".equals(resume.getDesireJob().getCode())) {
					String sqlDesireJob = KitService
							.jobCategoryCodeForResumeSql(resume.getDesireJob()
									.getCode());
					resume.setDesireJob(new JobCategory(sqlDesireJob));
				}
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resume", resume);
		map.put("start", startPage <= 0 ? Constants.START : (startPage - 1)
				* (size <= 0 ? Constants.SIZE : size));
		map.put("size", size <= 0 ? Constants.SIZE : size);
		List<Resume> list = dao.getByPage(map);
		// 处理为适合前台显示的字段数据
		list = kitService.getForShowResume(list);
		return list;
	}

	/**
	 * 分页查询方法, 其中数据已被处理成适合前台展示的形式 前台--使用
	 * 
	 * @param object
	 * @param startPage
	 * @param size
	 * @return
	 */
	public List<Resume> getForListShow(Resume object, int startPage, int size) {
		if (object != null) {
			// 将地区code转化为适合sql语句的形式, 其中包括先查询一下该地区的就业信息共享范围
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
			// 期望工作地  处理成适合sql查询的方式
			if (object.getDesireAddress() != null) {
				if (object.getDesireAddress().getCode() != null
						&& !"".equals(object.getDesireAddress().getCode())) {
					String sqlDesireAddress = KitService.desireAddressForResumeSql(object
							.getDesireAddress().getCode());
					object.setDesireAddress(new Area(sqlDesireAddress));
				}

			}
			// 目标职位种类code处理
			if (object.getDesireJob() != null) {
				if (object.getDesireJob().getCode() != null
						&& !"".equals(object.getDesireJob().getCode())) {
					String sqlDesireJob = KitService
							.jobCategoryCodeForResumeSql(object.getDesireJob()
									.getCode());
					object.setDesireJob(new JobCategory(sqlDesireJob));
				}
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resume", object);
		map.put("start", startPage <= 0 ? Constants.START : (startPage - 1)
				* (size <= 0 ? Constants.SIZE : size));
		map.put("size", size <= 0 ? Constants.SIZE : size);
		List<Resume> list = dao.getByPage(map);
		// 处理为适合前台显示的字段数据
		list = kitService.getForShowResume(list);
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
	public int getTotalCount(Resume object, Boolean shareScope) {
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
			// 目标职位种类code处理
			if (object.getDesireJob() != null) {
				if (object.getDesireJob().getCode() != null
						&& !"".equals(object.getDesireJob().getCode())) {
					String sqlDesireJob = KitService
							.jobCategoryCodeForResumeSql(object.getDesireJob()
									.getCode());
					object.setDesireJob(new JobCategory(sqlDesireJob));
				}
			}
			// 期望工作地  处理成适合sql查询的方式
			if (object.getDesireAddress() != null) {
				if (object.getDesireAddress().getCode() != null
						&& !"".equals(object.getDesireAddress().getCode())) {
					String sqlDesireAddress = KitService.desireAddressForResumeSql(object
							.getDesireAddress().getCode());
					object.setDesireAddress(new Area(sqlDesireAddress));
				}
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resume", object);
		return dao.getTotalCount(map);
	}

	/**
	 * 得到最新的N个简历
	 * 
	 * @param acode
	 * @param size
	 * @return
	 */
	public List<Resume> getByNew(String acode, int size) {
		Resume resume = new Resume();
		// 将地区code转化为适合sql语句的形式, 其中包括先查询一下该地区的就业信息共享范围; 如不存在则使用默认传递进来的地区code
		if (acode != null) {
			Parameter parameter = parameterDao.getShareScopeByArea(acode);
			if (parameter != null) {
				String areaSql = KitService.getAreaSqlFromShareScope(
						parameter.getValue(), acode);
				resume.setArea(new Area(areaSql));
			}else{
				resume.setArea(new Area(acode));
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resume", resume);
		map.put("start", Constants.START);
		map.put("size", size <= 0 ? Constants.SIZE : size);
		List<Resume> list = dao.getByPage(map);
		// 处理为适合前台显示的字段数据
		list = kitService.getForShowResume(list);
		return list;
	}

	/**
	 * 根据id数据得到简历列表
	 * @param ids
	 * @return
	 */
	public List<Resume> getByIds(Integer[] ids){
		List<Resume> list = dao.getByIds(ids);
		return list;
	}
	
	// 根据用户id, 得到此人简历
	public List<Resume> getByUser(int uid) {
		List<Resume> list = dao.getByUser(uid);
		for (Resume r : list) {
			// 处理为适合前台显示的字段数据
			r = kitService.getForShow(r);
		}
		return list;
	}

	// 根据简历id, 导出该简历,返回下载文件的路径
	public String getBuildResume(int rid, String url) {
		// ①得到数据model
		Resume resume = getOneForDownLoad(rid);
		if (resume == null) {
			return null;
		}
		log.info("resume.workExperience.size() : "
				+ resume.getWorkExperienceList().size());
		int i = resume.getWorkExperienceList().size();
		// ②获得对应地区的模板文件路径
		String templatePath = getTemplatePath(resume.getArea().getCode(), url);
		// ③生成的目标文件路径
		String destPath = "temp" + "/" + "resume.xls";
		// ④向模板中插入原始数据
		Map<String, Object> beans = new HashMap<String, Object>();
		beans.put("resume", resume);
		InputStream is = null;
		XLSTransformer transformer = null;
		try {
			is = new FileInputStream(templatePath);
			transformer = new XLSTransformer();
			HSSFWorkbook workBook = (HSSFWorkbook) transformer.transformXLS(is,
					beans);
			HSSFSheet sheet = workBook.getSheetAt(0);
			sheet.addMergedRegion(new Region(15, (short) 0, (15 + i), (short) 0));
			OutputStream os = new FileOutputStream(url + destPath);
			workBook.write(os);
			is.close();
			os.flush();
			os.close();
			log.info("生成excel文件成功");
			// ⑤返回生成文件的路径
			return destPath;
		} catch (Exception e) {
			e.printStackTrace();
			log.info("生成excel文件发生异常");
			return "error";
		}
	}

	// 根据简历id数组, 导出打成zip包的多个简历
	public String getBuildResume(int[] ids, String url) {
		if (ids == null || ids.length == 0) {
			return null;
		}
		String uuid = UUID.randomUUID().toString();
		// 生成的简历所存放的路径
		// 即temp缓存文件加下生成的一个随机文件夹如 context\\temp\\uuid\\
		String tempPath = url + "temp" + File.separator + uuid;
		// 创建文件夹
		File tempFile = new File(tempPath);
		if (!tempFile.exists()) {
			tempFile.mkdir();
		}
		// 循环生成简历
		for (int i = 0; i < ids.length; i++) {
			int rid = ids[i];
			// 得到简历
			Resume resume = getOneForDownLoad(rid);
			if (resume == null) {
				continue;
			}
			log.info("resume " + resume);
			int len = 0;
			if (resume.getWorkExperienceList() != null) {
				log.info("resume.workExperience.size() : "
						+ resume.getWorkExperienceList().size());
				len = resume.getWorkExperienceList().size();
			}
			Map<String, Object> beans = new HashMap<String, Object>();
			beans.put("resume", resume);
			InputStream is = null;
			XLSTransformer transformer = null;
			try {
				// 获得对应地区的模板文件路径
				String templatePath = getTemplatePath(resume.getArea()
						.getCode(), url);
				is = new FileInputStream(templatePath);
				transformer = new XLSTransformer();
				HSSFWorkbook workBook = (HSSFWorkbook) transformer
						.transformXLS(is, beans);
				HSSFSheet sheet = workBook.getSheetAt(0);
				sheet.addMergedRegion(new Region(15, (short) 0, (15 + len),
						(short) 0));
				// 保存的excel文件命名为： 姓名+(id)
				OutputStream os = new FileOutputStream(tempPath
						+ File.separator + resume.getName() + "("
						+ resume.getId() + ").xls");
				workBook.write(os);
				is.close();
				os.flush();
				os.close();
				log.info("生成excel文件成功");
			} catch (Exception e) {
				log.info("生成excel文件发生异常");
			}
		}
		// 生成的zip压缩文件存放路径,压缩文件名也为上面存放简历文件夹同名zip文件
		// 从简历所在的缓存文件夹tempFile读取所有excel文件, 打包成zip压缩文件
		String destPath = "temp" + "/" + uuid + ".zip";
		// 压缩的目标文件
		File zipFile = new File(url + destPath);
		try {
			ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(
					zipFile));
			// 输入流
			InputStream input = null;
			if (tempFile.isDirectory()) {
				File[] files = tempFile.listFiles();
				for (int i = 0; i < files.length; i++) {
					input = new FileInputStream(files[i]);
					zos.putNextEntry(new ZipEntry(files[i].getName()));
					int temp = 0;
					while ((temp = input.read()) != (-1)) {
						zos.write(temp);
					}
					input.close();
				}
			}
			zos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return destPath;
	}

	// 根据地区code, 返回他所使用的模板路径
	private String getTemplatePath(String acode, String url) {
		// 第一次尝试路径
		String filePath = url + "templates" + File.separator + acode
				+ File.separator + "resume.xls";
		File file = new File(filePath);
		if (file.exists()) {
			return filePath;
		}
		// 本地模板不存在则搜索它的上级模板--市级
		acode = "20" + acode.substring(2, 6) + "00";
		filePath = url + "templates" + File.separator + acode + File.separator
				+ "resume.xls";
		file = new File(filePath);
		if (file.exists()) {
			return filePath;
		}
		// 市级模板不存在则搜索它的上级模板--省级
		acode = "10" + acode.substring(2, 4) + "0000";
		filePath = url + "templates" + File.separator + acode + File.separator
				+ "resume.xls";
		file = new File(filePath);
		if (file.exists()) {
			return filePath;
		}
		// 都不存在, 则使用标准模板
		filePath = url + "templates" + File.separator + "resume.xls";
		return filePath;
	}

	// 保存工作经历
	public boolean save(WorkExperience we) {
		return wDao.save(we);
	}

	// 删除工作经历
	public boolean deleteWorkExperience(int weid) {
		return wDao.delete(weid);
	}

	// 更改工作经历
	public boolean update(WorkExperience we) {
		if (we == null) {
			return false;
		}
		we.setUpdateCheck(wDao.getUpdateCheck(we.getId()));
		return wDao.update(we);
	}

	// 保存职业测评办理
	public boolean save(UnempManage um) {
		return umDao.save(um);
	}

	// 删除职业测评办理
	public boolean deleteUnempManage(int umid) {
		return umDao.delete(umid);
	}

	// 更改职业测评办理
	public boolean update(UnempManage um) {
		if (um == null) {
			return false;
		}
		um.setUpdateCheck(umDao.getUpdateCheck(um.getId()));
		return umDao.update(um);
	}

	// // 保存教育背景
	// public boolean save(EducationBackground eb) {
	// return eDao.save(eb);
	// }
	//
	// // 删除教育背景
	// public boolean deleteEducationBackground(int ebid) {
	// return eDao.delete(ebid);
	// }
	//
	// // 更改教育背景
	// public boolean update(EducationBackground eb) {
	// return eDao.update(eb);
	// }
	//
	// // 保存家庭成员
	// public boolean save(FamilyMember fm) {
	// return fDao.save(fm);
	// }
	//
	// // 删除家庭成员
	// public boolean deleteFamilyMember(int fmid) {
	// return fDao.delete(fmid);
	// }
	//
	// // 更改家庭成员
	// public boolean update(FamilyMember fm) {
	// return fDao.update(fm);
	// }
	//
	//
	// // 录入员--根据id得到一个简历对象
	// public Resume getByIdForRe(int id) {
	// return dao.getByIdForRecorder(id);
	// }
	//
	// // 录入员--根据录入员id, 得到他所管理的所有简历
	// public List<Resume> getByRecorder(int arid, int startPage, int size) {
	// if (startPage <= 0) {
	// startPage = 1;
	// }
	// if (size <= 0) {
	// size = 1;
	// }
	// Map map = new HashMap();
	// map.put("arid", arid);
	// map.put("start", (startPage - 1) * size);
	// map.put("size", size);
	// return dao.getByRecorder(map);
	// }
	//
	// // 根据录入员id, 得到他所管理的所有简历的个数
	// public int getCountByRecorder(int arid) {
	// return dao.getCountByRecorder(arid);
	// }
	//
	// // 根据地区id, 得到一个地区的所有被录入员管理的残疾人列表
	// public List<Resume> getByArea(int aid) {
	// return dao.getByArea(aid);
	// }
	//
	// // 根据地区code, 得到他所管理的所有简历的个数
	// public int getCountByArea(String code) {
	// if (code == null || "".equals(code)) {
	// return 0;
	// }
	// code = KitService.areaCodeForSql(code);
	// return dao.getCountByAreaCode(code);
	// }
	//
	// // 根据地区code, 得到一个地区的所有被录入员管理的残疾人列表
	// public List<Resume> getByArea(String code) {
	// if (code == null || "".equals(code)) {
	// return null;
	// }
	// code = KitService.areaCodeForSql(code);
	// return dao.getByAreaCode(code);
	// }

	// public static void main(String[] args) {
	// SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
	// String d = sdf.format(new Date(System.currentTimeMillis()));
	// log.info(d);
	// }
}
