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
	
	/**
	 *  根据类型获得对应参数列表
	 * @param type
	 * @return
	 */
	public List<Parameter> getByType(String type);

	/**
	 *  根据地区code, 获得该地区的信息分享范围
	 * @param map
	 * @return
	 */
	public Parameter getShareScopeByArea(String acode);
	
	/**
	 *  根据地区code 来获得该地区  "能够" 控制的信息分享范围列表
	 * @param map
	 * @return
	 */
	public List<Parameter> getShareScopeListByArea(String acode);
}
