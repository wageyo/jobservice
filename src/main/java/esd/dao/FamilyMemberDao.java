package esd.dao;

import java.util.List;

import esd.bean.FamilyMember;

/**
 * 家庭成员接口
 * 
 * @author Administrator
 * 
 */
public interface FamilyMemberDao extends IDao<FamilyMember> {

	// 根据简历id得到所有下属的工作经历
	public List<FamilyMember> getByResume(int rid);

}
