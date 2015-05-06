package esd.dao;

import esd.bean.Menu;

/**
 * menu Dao
 * 
 * @author Administrator
 * 
 */
public interface MenuDao extends IDao<Menu> {

	// 按id查询一个对象
	public Menu getById(String id);

}