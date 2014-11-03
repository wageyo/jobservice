package esd.dao;

import java.util.List;

import esd.bean.WorkExperience;

/**
 * 工作经历接口
 * 
 * @author Administrator
 * 
 */
public interface WorkExperienceDao extends IDao<WorkExperience> {

	// 根据简历id得到所有下属的工作经历
	public List<WorkExperience> getByResume(int rid);

}
