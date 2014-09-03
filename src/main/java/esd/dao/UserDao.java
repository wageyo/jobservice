package esd.dao;

import java.util.List;
import java.util.Map;

import esd.bean.User;

public interface UserDao extends IDao<User> {

	public User getByUser(User user);

	/**
	 * 获得管理员（分页）
	 * 
	 * @param map
	 * @return
	 */
	List<User> getAdminByPage(Map<String, Object> map);

	/**
	 * 获得管理员（分页）
	 * 
	 * @param map
	 * @return
	 */
	int getAdminTotalCount(Map<String, Object> map);
}
