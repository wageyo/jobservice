package esd.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import esd.bean.JobCategory;
import esd.dao.JobCategoryDao;

@Service
public class JobCategoryService {

	@Autowired
	private JobCategoryDao dao;

	// 保存一个对象
	public boolean save(JobCategory jobCategory) {
		return dao.save(jobCategory);
	}

	// 删除一个对象
	public boolean delete(int id) {
		return dao.delete(id);
	}

	// 更新一个对象
	public boolean update(JobCategory jobCategory) {
		return dao.update(jobCategory);
	}

	// 按id查询一个对象
	public Object getById(int id) {
		return dao.getById(id);
	}

	// 按账号本身条件查询该账号是否存在
	public List<JobCategory> getByObj(JobCategory jobCategory) {
		return dao.getByObj(jobCategory);
	}

	/**
	 * 分页查询方法,
	 * 
	 * @param map中为具体的参数
	 *            : 1-类对象, 字段的值即为查询条件; 2-start: 起始页数; 3-size: 返回条数
	 */
	public List<JobCategory> getByPage(Map map) {
		return dao.getByPage(map);
	}

	// 获得最受欢迎职位种类列表
	public List<JobCategory> getPopularJobCategory() {
		Map<String, Object> map = new HashMap<String, Object>();
		JobCategory jc = new JobCategory();
		jc.setMark("hot");
		map.put("jobCategory", jc);
		map.put("start", 0);
		map.put("size", 9999);
		return dao.getByPage(map);
	}

	// 得到所有数据,并将所有数据装到map中, 形成大分类--小分类--详细分类的格式
	public List<JobCategory> getAll() {
		// 职位类别信息
		// List<JobCategory> jcList = dao.getAll();
		// 三级放到2级里面
		// Map category2 = new HashMap();
		// for (JobCategory jc2 : jcList) {
		// String code2 = jc2.getCode();
		// Map category3 = null;
		// if("20".equals(code2.substring(0, 2)) &&
		// !"00".equals(code2.substring(6,8))){
		// category3 = new HashMap();;
		// for (JobCategory jc3 : jcList) {
		// String code3 = jc3.getCode();
		// if("30".equals(code3.substring(0, 2)) && code3.substring(2,
		// 6).equals(code2.substring(2,6))){
		// category3.put(code3, jc3);
		// }
		// }
		// }
		// category2.put(code2, category3);
		// }
		// //二级放到一级里面
		// Map category1 = new HashMap();
		// for (JobCategory jc : jcList) {
		// String code1 = jc.getCode();
		// Map category22 = null;
		// for(Map cate2:category2){
		// if("10".equals(code1.substring(0, 2)) && !"10000000".equals(code1)){
		// category1.put(code1, category2.get(10+code1.substring(beginIndex)))
		// }
		// }
		// }
		return dao.getAll();
	}

	// 获得数据总条数
	public int getTotalCount(Map map) {
		return dao.getTotalCount(map);
	}

	// 得到省级菜单
	public List<JobCategory> getJcLv1() {
		return dao.getSubJc("10______");
	}

	// 根据地区code, 得到下属的市/县
	public List<JobCategory> getSubJc(String code) {
		if (code == null || "".equals(code)) {
			return null;
		}
		String start = code.substring(0, 2);
		String mid;
		if ("10".equals(start)) {
			mid = code.substring(2, 4);
			code = "20" + mid + "____";
		} else if ("20".equals(start)) {
			mid = code.substring(2, 6);
			code = "30" + mid + "__";
		}
		return dao.getSubJc(code);
	}

}
