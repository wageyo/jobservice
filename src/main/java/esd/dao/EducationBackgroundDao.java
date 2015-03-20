package esd.dao;

import java.util.List;

import esd.bean.EducationBackground;

/**
 * 教育经历
 * @author yufu
 * @email ilxly01@126.com
 * 2015-3-20
 */
public interface EducationBackgroundDao extends IDao<EducationBackground> {

	// 根据简历id得到所有下属的工作经历
	public List<EducationBackground> getByResume(int rid);

}
