package esd.dao;

import java.util.List;

import esd.bean.UnempManage;

/**
 * 就业管理dao接口
 * 
 * @author Administrator
 * 
 */
public interface UnempManageDao extends IDao<UnempManage> {

	// 根据简历id得到所有下属的职业测评办理情况
	public List<UnempManage> getByResume(int rid);
}
