package esd.dao;

import java.util.Map;

/**
 * 企业统计数据Dao接口
 * 
 * @author Administrator
 * 
 */
public interface StatisticsCompanyDao<StatisticsCompany> {

	/**
	 * 根据map中的acode, checkStatus等参数查询出有多少家公司
	 * 
	 * @param map
	 * @return
	 */
	public Integer getCompanyCount(Map<String, Object> map);

	/**
	 * 根据map中的acode, checkStatus等参数查询出有职位数和雇佣人数存放到StatisticsCompany对象中
	 * @param map
	 * @return
	 */
	public StatisticsCompany getJobCount(Map<String,Object> map);
}
