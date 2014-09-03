package esd.dao;

import java.util.List;

import esd.bean.JobCategory;

/**
 * 职位类别操作Dao接口
 * 
 * @author Administrator
 * 
 */
public interface JobCategoryDao extends IDao {

	// 根据code得到对象
	public JobCategory getByCode(String code);

	// 得到所有数据
	public List<JobCategory> getAll();

	// 根据code得到下属菜单
	public List<JobCategory> getSubJc(String code);
}
