package esd.dao;

import java.util.List;

import esd.bean.Job;

/**
 * 职位类操作Dao接口
 * @author Administrator
 *
 */
public interface JobDao extends IDao<Job>{

	//根据公司id, 得到其所有的职位
	public List<Job> getByCompany(int cID);
	
	//更具公司id, 删除他的所有职位
	Integer deleteByCompany(Integer cid);
}
