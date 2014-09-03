package esd.dao;

import java.util.List;

import esd.bean.Parameter;

/**
 * 参数dao接口
 * 
 * @author Administrator
 * 
 */
public interface ParameterDao extends IDao<Parameter> {

	// 根据类型获得对应参数列表
	public List<Parameter> getByType(String type);

	// 根据自身对象属性查找一个对象
	public Parameter getByParam(Parameter p);

	// 更新审核开关方法
	public boolean updateSwitch(Parameter p);
}
