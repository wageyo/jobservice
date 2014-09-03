package esd.dao;

import java.util.List;

import esd.bean.EducationBackground;

/**
 * 教育经历
 * 
 * @author Administrator
 * 
 */
public interface EducationBackgroundDao extends IDao<EducationBackground> {

	// 根据简历id得到所有下属的工作经历
	public List<EducationBackground> getByResume(int rid);

}
