package esd.dao;

import java.util.List;
import java.util.Map;

import esd.bean.Job;

/**
 * 职位类操作Dao接口
 * @author Administrator
 *
 */
public interface JobDao extends IDao<Job>{

	/**
	 * 根据公司id, 得到其所有的职位, map中放入公司id-cid, 起始索引start, 返回量size
	 * @param map
	 * @return
	 */
	public List<Job> getByCompany(Map<String,Object> map);
	
	/**
	 * 根据公司id, 得到其所有的职位总数, map中放入公司id-cid, 起始索引start, 返回量size
	 * @param map
	 * @return
	 */
	public Integer getByCompanyCount(Map<String,Object> map);
	
	//更具公司id, 删除他的所有职位
	Integer deleteByCompany(Integer cid);
}
