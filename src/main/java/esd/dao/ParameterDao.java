package esd.dao;

import java.util.List;
import java.util.Map;

import esd.bean.Parameter;

/**
 * 参数dao接口
 * 
 * @author Administrator
 * 
 */
public interface ParameterDao extends IDao<Parameter> {

	/**
	 *  根据类型获得对应参数列表
	 * @param type
	 * @return
	 */
	public List<Parameter> getByType(String type);

	/**
	 *  根据类型获得对应参数
	 * @param map
	 * @return
	 */
	public Parameter getByType1(Map<String,Object> map);
	
	/**
	 *  根据类型获得对应参数
	 * @param map
	 * @return
	 */
	public List<Parameter> getByType2(Map<String,Object> map);
		
	/**
	 *  根据自身对象属性查找一个对象
	 * @param p
	 * @return
	 */
	public Parameter getByParam(Parameter p);

	/**
	 *  更新审核开关方法
	 * @param p
	 * @return
	 */
	public boolean updateSwitch(Parameter p);
}
