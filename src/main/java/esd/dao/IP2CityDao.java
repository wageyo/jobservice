package esd.dao;

import java.util.List;

import esd.bean.IP2City;

/**
 * 地名操作Dao接口
 * @author Administrator
 *
 */
public interface IP2CityDao extends IDao{

	//得到所有数据
	public List<IP2City> getAll();
}
