package esd.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import esd.bean.Area;
import esd.bean.Parameter;
import esd.controller.Constants;
import esd.dao.MenuDao;
import esd.dao.ParameterDao;

@Service
public class ParameterService {

	@Autowired
	private ParameterDao dao;

	@Autowired
	private MenuDao mDao;

	public Boolean save(Parameter p) {
		return dao.save(p);
	}

	/**
	 * 根据类型获得对应参数列表
	 * 
	 * @param type
	 * @return
	 */
	public List<Parameter> getByType(String type) {
		return dao.getByType(type);
	}

	/**
	 * 根据地区code获得该地区信息共享范围
	 * 
	 * @param areaCode
	 * @return
	 */
	public Parameter getByType(String type, String areaCode) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("type", type);
		map.put("areaCode", areaCode);
		return dao.getByType1(map);
	}
	
	/**
	 * 根据类型获得对应参数列表
	 * 
	 * @param type
	 * @return
	 */
	public List<Parameter> getByTypeAndArea(String type,String areaCode) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("type", type);
		map.put("areaCode", areaCode);
		return dao.getByType2(map);
	}
	
	/**
	 * 获得所有参数
	 * 
	 * @return
	 */
	public List<Parameter> getAll() {
		System.out.println("************" + dao.getByPage(null).size());
		return dao.getByPage(null);
	}

	/**
	 * 更新参数状态
	 * 
	 * @param id
	 * @param value
	 * @return
	 */
	public boolean update(Parameter parameter) {
		if(parameter == null){
			return false;
		}
		boolean bl = dao.update(parameter);
		return bl;
	}
	
	/**
	 * 更改参数状态
	 * 
	 * @param id
	 * @param value
	 * @return
	 */
	public boolean updateParameter(Parameter parameter) {
		if(parameter == null){
			return false;
		}
		boolean bl = dao.updateSwitch(parameter);
		return bl;
	}
	
	/**
	 * 更改参数状态, 据参数id和value值
	 * 
	 * @param id
	 * @param value
	 * @return
	 */
	public boolean updateParameter(String id, String value) {
		Parameter p = new Parameter();
		p.setId(id);
		p.setValue(value);
		boolean bl = dao.updateSwitch(p);
		return bl;
	}

	/**
	 * 更改开关, 根据开关名称见Constants.Switch 枚举, 地区code, 开否boolean值
	 * 
	 * @param switchName
	 * @param acode
	 * @param switchValue
	 * @return
	 */
	public boolean updateSwitch(String switchName, String acode,
			String switchValue) {
		Parameter p = new Parameter();
		p.setName(switchName);
		p.setArea(new Area(acode));
		if (Constants.SWITCH_ON.equals(switchValue)) {
			p.setValue(Constants.SWITCH_ON);
		} else {
			p.setValue(Constants.SWITCH_OFF);
		}
		// / 更新开关值
		boolean bl = dao.updateSwitch(p);
		return bl;
	}

	/**
	 * 根据开关名称, 地区code得知开关是否打开, 开关名称见Constants.Switch 枚举
	 */
	public boolean getSwitchStatus(String switchName, String acode) {
		if (acode == null || "".equals(acode)) {
			return false;
		}
		Parameter p = new Parameter();
		p.setName(switchName);
		p.setType(Constants.SWITCH);
		p.setArea(new Area(acode));
		// 查询本地区审核开关状态,如果本地区存在审核开关的话, 则使用本地区的
		Parameter temp = dao.getByParam(p);
		if (temp != null) {
			if (Constants.SWITCH_ON.equals(temp.getValue())) {
				return true;
			} else {
				return false;
			}
		}
		// 本地区不存在审核开关, 则查询市级审核开关,如果有的话则使用
		acode = "20" + acode.substring(2, 6) + "00";
		p.setArea(new Area(acode));
		temp = dao.getByParam(p);
		if (temp != null) {
			if (Constants.SWITCH_ON.equals(temp.getValue())) {
				return true;
			} else {
				return false;
			}
		}
		// 市级不存在审核开关, 则查询省级审核开关,如果有的话则使用
		acode = "10" + acode.substring(2, 4) + "0000";
		p.setArea(new Area(acode));
		temp = dao.getByParam(p);
		if (temp != null) {
			if (Constants.SWITCH_ON.equals(temp.getValue())) {
				return true;
			} else {
				return false;
			}
		}
		// 如果都不存在的话.... 好吧, 不用审核了
		return false;
	}

	/**
	 * 按地区code, 获得该地区所有开关状态 name:开关名称, value:开关与否(on-打开, off-关闭)
	 */
	public List<Parameter> getSwitchByArea(String acode) {
		Parameter p = new Parameter();
		p.setType(Constants.SWITCH);
		p.setArea(new Area(acode));
		return dao.getByObj(p);
	}

	/**
	 * 根据参数自身属性条件获取相关的数据列表
	 * 
	 * @param p
	 * @return
	 */
	public List<Parameter> getByParameter(Parameter p) {
		return dao.getByObj(p);
	}
}
