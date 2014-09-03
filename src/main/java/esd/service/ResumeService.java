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
import esd.bean.Resume;
import esd.bean.User;
import esd.bean.WorkExperience;
import esd.controller.Constants;
import esd.dao.EducationBackgroundDao;
import esd.dao.FamilyMemberDao;
import esd.dao.ResumeDao;
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

	@Autowired
	private EducationBackgroundDao eDao;

	@Autowired
	private FamilyMemberDao fDao;

	@Autowired
	private WorkExperienceDao wDao;

	@Autowired
	private PersonService personService;

	@Autowired
	private KitService kitService;

	@Autowired
	private ParameterService pService;

	// 保存一个对象
	public boolean save(Resume resume) {
		if (resume != null) {
			// 检查个人用户是否已经有默认选中作为投递的简历了,
			// 没有的话, 则将该简历设为默认选中投递的简历
			Resume tempResume = personService.getDefaultResume(resume.getUser()
					.getId());
			if (tempResume == null) {
				resume.setDefault(true);
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

	// 更新一个对象
	public boolean update(Resume resume) {
		if (resume != null) {
			// 重新计算年龄
			if (resume.getBirth() != null && !"".equals(resume.getBirth())) {
				resume.setAge(KitService.getAgeByBirth(resume.getBirth()));
			}
		}
		return dao.update(resume);
	}

	// 按id查询一个对象,用作编辑处理
	public Resume getById(int id) {
		Resume r = (Resume) dao.getById(id);
		// r.setEducationBackgroundList(eDao.getByResume(r.getId()));
		// r.setFamilyMemberList(fDao.getByResume(r.getId()));
		// 取得工作经历
		r.setWorkExperienceList(wDao.getByResume(r.getId()));
		// int birth = Integer.parseInt(r.getBirth().substring(0, 4));
		// int now = Integer.parseInt(new
		// SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
		// .format(new Date()).substring(0, 4));
		// r.setAge(now - birth);
		return r;
	}

	// 按id查询一个对象, 用做前台展示
	public Resume getOneForShow(int id) {
		Resume r = (Resume) dao.getById(id);
		// 取得工作经历
		r.setWorkExperienceList(wDao.getByResume(r.getId()));
		// 处理为适合前台显示的字段数据
		r = kitService.getForShow(r);
		return r;
	}

	// 按id查询一个对象, 提供给下载
	public Resume getOneForDownLoad(int id) {
		Resume r = (Resume) dao.getById(id);
		// 取得工作经历
		List<WorkExperience> list = wDao.getByResume(id);
		for (int i = 0; i < list.size(); i++) {
			r.getWorkExperienceList().add(list.get(i));
			if (i >= 2) {
				break;
			}
		}
		// 处理为适合前台显示的字段数据
		r = kitService.getForShow(r);
		return r;
	}

	// 根据个人用户的user id, 得到他默认选中的简历
	public Resume getDefaultResume(int userid) {
		Resume r = new Resume();
		r.setDefault(true);
		r.setUser(new User(userid));
		List<Resume> list = dao.getByObj(r);
		log.info("getDefaultResume list.size() : " + list.size());
		if (list != null) {
			if (list.size() == 1) {
				r = list.get(0);
				// 取得教育经历
				// r.setEducationBackgroundList(eDao.getByResume(r.getId()));
				// 取得家庭成员
				// r.setFamilyMemberList(fDao.getByResume(r.getId()));
				// 取得工作经历
				// r.setWorkExperienceList(wDao.getByResume(r.getId()));
				// 计算年龄
				// int birth = Integer.parseInt(r.getBirth().substring(0, 4));
				// int now = Integer.parseInt(new
				// SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
				// .format(new Date()).substring(0, 4));
				// r.setAge(now - birth);
				// 设置生日为yyyy-MM-dd格式
				// r.setBirth(r.getBirth().substring(0,10));
				return r;
			}
			return null;
		}
		return null;
	}

	// 分页查询方法,
	// @param map中为具体的参数
	// : 1-类对象, 名称为对应类的小写!!切记切记!! 字段的值即为查询条件; 2-start: 起始索引; 3-size:返回条数
	public List<Resume> getByPage(Resume resume, int startPage, int size) {
		if (resume != null) {
			// 地区code处理
			if (resume.getArea() != null) {
				if (resume.getArea() != null) {
					resume.setArea(new Area(KitService.areaCodeForSql(resume
							.getArea().getCode())));
				}
			}
			// 目标职位种类code处理
			if (resume.getDesireJob() != null) {
				resume.setDesireJob(KitService.jobCategoryCodeForSql(resume
						.getDesireJob()));
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resume", resume);
		map.put("start", startPage <= 0 ? Constants.START : (startPage - 1)
				* (size <= 0 ? Constants.SIZE : size));
		map.put("size", size <= 0 ? Constants.SIZE : size);
		List<Resume> list = dao.getByPage(map);
		for (Resume r : list) {
			if (r.getUpdateDate() != null) {
				r.setUpdateDate(KitService.dateForShow(r.getUpdateDate()));
			}
		}
		System.out.println("resumeList.size() = " + list.size());
		return list;
	}

	// 后台审核用方法
	// 分页查询方法,其中数据已被处理成适合前台展示的
	public List<Resume> getListShowForManage(Resume resume, int startPage,
			int size) {
		if (resume != null) {
			// 地区code处理
			if (resume.getArea() != null) {
				if (resume.getArea() != null) {
					resume.setArea(new Area(KitService.areaCodeForSql(resume
							.getArea().getCode())));
				}
			}
			// 目标职位种类code处理
			if (resume.getDesireJob() != null) {
				resume.setDesireJob(KitService.jobCategoryCodeForSql(resume
						.getDesireJob()));
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

	// 分页查询方法,其中数据已被处理成适合前台展示的
	public List<Resume> getForListShow(Resume resume, int startPage, int size) {
		if (resume != null) {
			// 地区code处理
			if (resume.getArea() != null) {
				if (resume.getArea() != null) {
					resume.setArea(new Area(KitService.areaCodeForSql(resume
							.getArea().getCode())));
				}
			}
			// 期望工作地
			if (resume.getDesireAddress() != null) {
				resume.setDesireAddress(KitService.areaCodeForSql(resume
						.getDesireAddress()));

			}
			// 目标职位种类code处理
			if (resume.getDesireJob() != null) {
				resume.setDesireJob(KitService.jobCategoryCodeForSql(resume
						.getDesireJob()));
			}
			// 如为特殊声明, 则只显示审核通过的
			if (resume.getCheckStatus() == null
					|| "".equals(resume.getCheckStatus())) {
				resume.setCheckStatus(Constants.CheckStatus.OK.toString());
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

	// 得到最新的N个简历
	public List<Resume> getByNew(String acode, int size) {
		if (size <= 0) {
			size = Constants.SIZE;
		}
		// 处理传进来的地区code, 变成适用于sql语句使用的格式
		if (acode != null) {
			acode = KitService.areaCodeForSql(acode);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		Resume resume = new Resume();
		resume.setArea(new Area(acode));
		// 则只显示审核通过的
		resume.setCheckStatus(Constants.CheckStatus.OK.toString());
		map.put("resume", resume);
		map.put("start", Constants.START);
		map.put("size", size);
		List<Resume> list = dao.getByPage(map);
		// 处理为适合前台显示的字段数据
		list = kitService.getForShowResume(list);
		return list;
	}

	// 获得数据总条数
	public int getTotalCount(Resume resume) {
		if (resume != null) {
			// 地区code处理
			if (resume.getArea() != null) {
				if (resume.getArea() != null) {
					resume.setArea(new Area(KitService.areaCodeForSql(resume
							.getArea().getCode())));
				}
			}
			// 目标职位种类code处理
			if (resume.getDesireJob() != null) {
				resume.setDesireJob(KitService.jobCategoryCodeForSql(resume
						.getDesireJob()));
			}
			// 如为特殊声明, 则只显示审核通过的
			if (resume.getCheckStatus() == null
					|| "".equals(resume.getCheckStatus())) {
				resume.setCheckStatus(Constants.CheckStatus.OK.toString());
			}
		}
		if (resume == null) {
			resume = new Resume();
			resume.setCheckStatus(Constants.CheckStatus.OK.toString());
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resume", resume);
		return dao.getTotalCount(map);
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
		if(resume==null){
			return null;
		}
		log.info("resume.workExperience.size() : "
				+ resume.getWorkExperienceList().size());
		int i = resume.getWorkExperienceList().size();
		// ②获得对应地区的模板文件路径
		String templatePath = getTemplatePath(resume.getArea().getCode(), url);
		// ③生成的目标文件路径
		String destPath = "temp"+"/" + "resume.xls";
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
			OutputStream os = new FileOutputStream(url+destPath);
			workBook.write(os);
			is.close();
			os.flush();
			os.close();
			log.info("生成excel文件成功");
			//⑤返回生成文件的路径
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
				String templatePath = getTemplatePath(resume.getArea().getCode(), url);
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
				// TODO Auto-generated catch block
				log.info("生成excel文件发生异常");
			}
		}
		// 生成的zip压缩文件存放路径,压缩文件名也为上面存放简历文件夹同名zip文件
		// 从简历所在的缓存文件夹tempFile读取所有excel文件, 打包成zip压缩文件
		String destPath = "temp" + "/" + uuid + ".zip";
		// 压缩的目标文件
		File zipFile = new File(url+destPath);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return destPath;
	}

	// 根据地区code, 返回他所使用的模板路径
	private String getTemplatePath(String acode, String url) {
		// 第一次尝试路径
		String filePath = url + "templates"+File.separator+acode + File.separator + "resume.xls";
		File file = new File(filePath);
		if (file.exists()) {
			return filePath;
		}
		// 本地模板不存在则搜索它的上级模板--市级
		acode = "20" + acode.substring(2, 6) + "00";
		filePath = url  + "templates"+File.separator+ acode + File.separator + "resume.xls";
		file = new File(filePath);
		if (file.exists()) {
			return filePath;
		}
		// 市级模板不存在则搜索它的上级模板--省级
		acode = "10" + acode.substring(2, 4) + "0000";
		filePath = url + "templates"+File.separator + acode + File.separator + "resume.xls";
		file = new File(filePath);
		if (file.exists()) {
			return filePath;
		}
		//都不存在, 则使用标准模板
		filePath = url + "templates"+File.separator+ "resume.xls";
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
		return wDao.update(we);
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
	// System.out.println(d);
	// }
}
