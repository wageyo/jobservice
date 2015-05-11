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
	
	//根据id数组得到一组简历列表
	public List<Job> getByIds(Integer[] ids);
	
	/**
	 * 推送职位用：根据job对象查询对象, 返回匹配的职位列表, map中放入公司id-cid, 起始索引start, 返回量size
	 * @param map
	 * @return
	 */
	public List<Job> getJobMate(Map<String,Object> map);
	
	/**
	 * 根据companyId来更新职位的checkStatus状态
	 * @param map--内放入 companyId, checkStatus
	 * @return
	 */
	public Boolean updateCheckStatusByCompany(Map<String,Object> map);
}
