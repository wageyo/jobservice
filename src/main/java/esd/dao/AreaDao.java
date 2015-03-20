package esd.dao;

import java.util.List;

import esd.bean.Area;

/**
 * 地名操作Dao接口
 * @author yufu
 * @email ilxly01@126.com
 * 2015-3-20
 */
public interface AreaDao extends IDao<Area>{

	// 根据code得到对象
	public Area getByCode(String code);
	
	//得到所有数据
	public List<Area> getAll();
	
	//根据code得到下属市/ 县
	public List<Area> getSubArea(String code);
	
	
}
